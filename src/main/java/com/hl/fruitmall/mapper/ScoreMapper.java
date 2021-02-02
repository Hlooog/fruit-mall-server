package com.hl.fruitmall.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @author Hl
 * @create 2021/2/2 21:41
 */
public interface ScoreMapper {
    Double get(@Param("id") Integer id);

    void insert(@Param("id") Integer id, @Param("score") Double score);

    void delete(@Param("id") Integer id);
}
