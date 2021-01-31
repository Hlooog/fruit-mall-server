package com.hl.fruitmall.mapper;

import com.hl.fruitmall.entity.vo.VarietyVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Hl
 * @create 2021/1/31 16:36
 */
public interface VarietyMapper {
    List<VarietyVO> get();

    void insert(@Param("variety") VarietyVO variety);
}
