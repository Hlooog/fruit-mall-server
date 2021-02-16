package com.hl.fruitmall.mapper;

import com.hl.fruitmall.entity.bean.Comment;
import com.hl.fruitmall.entity.vo.CommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评论表(Comment)表数据库访问层
 *
 * @author hl
 * @since 2020-11-21 00:33:30
 */
public interface CommentMapper {

    List<CommentVO> selectPage(@Param("id") Integer id,@Param("cur") Integer cur);

    Integer getTotal(@Param("id") Integer id);

    void insert(@Param("comment") Comment comment);
}