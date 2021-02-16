package com.hl.fruitmall.service.impl;

import com.alibaba.fastjson.JSON;
import com.hl.fruitmall.common.enums.OrderStatusEnum;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.common.uitls.TokenUtils;
import com.hl.fruitmall.config.RabbitConfig;
import com.hl.fruitmall.entity.bean.Comment;
import com.hl.fruitmall.entity.bean.Commodity;
import com.hl.fruitmall.entity.bean.IScore;
import com.hl.fruitmall.entity.vo.BackstageOrderVO;
import com.hl.fruitmall.entity.vo.CommentVO;
import com.hl.fruitmall.mapper.CommentMapper;
import com.hl.fruitmall.mapper.CommodityMapper;
import com.hl.fruitmall.mapper.OrderInfoMapper;
import com.hl.fruitmall.service.CommentService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author hl
 * @since 2020-11-21 00:33:31
 */
@Service("commentService")
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private CommodityMapper commodityMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public R page(Integer id, Integer cur) {
        List<CommentVO> list = commentMapper.selectPage(id,(cur - 1) * 10);
        Integer total = commentMapper.getTotal(id);
        return R.ok(new HashMap<String, Object>() {
            {
                put("data", list);
                put("total", total);
            }
        });
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public R add(Map<String, Object> map, HttpServletRequest request) {
        Integer id = (Integer) map.get("id");
        String content = (String) map.get("content");
        Object o = map.get("score");
        float score = 0.0f;
        if (o instanceof Integer) {
            score = ((Integer) o).floatValue();
        } else if (o instanceof Double) {
            score = ((Double) o).floatValue();
        }
        Integer userId = TokenUtils.getId(request);
        BackstageOrderVO backstageOrderVO = orderInfoMapper.selectById(id);
        // 添加评论
        Comment comment = new Comment(userId,
                backstageOrderVO.getCommodityId(),
                content,score,
                backstageOrderVO.getSpecification(),
                backstageOrderVO.getWeight(),
                backstageOrderVO.getQuantity());
        commentMapper.insert(comment);
        // 修改订单状态
        orderInfoMapper.updateStatus("id", id, OrderStatusEnum.COMMENTED.getCode());

        // 修改商品评分
        Commodity commodity = commodityMapper.selectById(backstageOrderVO.getCommodityId());
        commodity.calScore(score);
        commodityMapper.updateScore(commodity.getId(),commodity.getScore());
        // 修改redis搜索引擎分值
        long day = TimeUnit.DAYS.toMillis(1);
        long time = new Date().getTime();
        long allScore, userScore,varietyScore,monthlyScore;
        if (score >= 4) {
            allScore = day;
            userScore = time * 20;
            varietyScore = 20;
            monthlyScore = day * 20;
        } else if (score >= 2) {
            allScore = day / 2;
            userScore = time * 10;
            varietyScore = 10;
            monthlyScore = day * 10;
        } else {
            allScore = - (day / 2);
            userScore = - (time * 10);
            varietyScore = 0;
            monthlyScore = 0;
        }
        IScore iScore = new IScore(commodity.getId(),
                userId,
                allScore,
                userScore,
                varietyScore,
                monthlyScore);
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_RECORD,
                RabbitConfig.ROUTING_RECORD, JSON.toJSONString(iScore));
        return R.ok();
    }
}