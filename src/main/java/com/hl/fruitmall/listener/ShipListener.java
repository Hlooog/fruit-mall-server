package com.hl.fruitmall.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.hl.fruitmall.entity.vo.BackstageOrderVO;
import com.hl.fruitmall.mapper.OrderInfoMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hl
 * @create 2021/2/24 9:27
 */
public class ShipListener extends AnalysisEventListener<BackstageOrderVO> {

    private OrderInfoMapper orderInfoMapper;

    List<Map<Integer,String>> list = null;

    public ShipListener(OrderInfoMapper orderInfoMapper) {
        this.list = new ArrayList<>();
        this.orderInfoMapper = orderInfoMapper;
    }

    @Override
    public void invoke(BackstageOrderVO backstageOrderVO,
                       AnalysisContext analysisContext) {
        if (backstageOrderVO.getTrackNumber() != null) {
            Map<Integer,String> map = new HashMap<>();
            map.put(backstageOrderVO.getId(), backstageOrderVO.getTrackNumber());
            list.add(map);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        orderInfoMapper.updateBatch(list);
        list.clear();
        list = null;
    }
}
