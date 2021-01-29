package com.hl.fruitmall.service.impl;

import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.entity.vo.CommodityPageVO;
import com.hl.fruitmall.mapper.CommodityMapper;
import com.hl.fruitmall.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 水果商品表(Commodity)表服务实现类
 *
 * @author hl
 * @since 2020-11-21 00:33:35
 */
@Service("commodityService")
public class CommodityServiceImpl implements CommodityService {

    @Autowired
    private CommodityMapper commodityMapper;

    @Override
    public R page(Integer id) {
        List<CommodityPageVO> list = commodityMapper.selectPage(id);
        return R.ok(new HashMap<String,Object>(){
            {
                put("data", list);
            }
        });
    }

    @Override
    public R off(Integer id) {
        commodityMapper.updateByField("id",id,"is_on_shelf",0);
        return R.ok();
    }
}