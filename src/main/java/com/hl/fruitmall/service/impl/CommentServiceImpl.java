package com.hl.fruitmall.service.impl;

import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.entity.vo.CommentVO;
import com.hl.fruitmall.mapper.CommentMapper;
import com.hl.fruitmall.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

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
}