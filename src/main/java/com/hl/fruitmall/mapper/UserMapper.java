package com.hl.fruitmall.mapper;

import com.hl.fruitmall.entity.bean.User;
import com.hl.fruitmall.entity.vo.UserPageVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 用户表(User)表数据库访问层
 *
 * @author hl
 * @since 2020-11-21 00:33:41
 */
public interface UserMapper {

    User selectByField(@Param("field") String field,@Param("value") String value);

    List<UserPageVO> page(@Param("cur") int cur,
                          @Param("key") String key,
                          @Param("start") Date start,
                          @Param("end") Date end);

    Integer getTotal(@Param("key") String key,
                     @Param("start") Date start,
                     @Param("end") Date end);
}