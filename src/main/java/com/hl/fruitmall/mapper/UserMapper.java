package com.hl.fruitmall.mapper;

import com.hl.fruitmall.entity.bean.User;
import com.hl.fruitmall.entity.vo.UserPageVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户表(User)表数据库访问层
 *
 * @author hl
 * @since 2020-11-21 00:33:41
 */
public interface UserMapper {

    User selectByField(@Param("field") String field, @Param("value") Object value);

    List<UserPageVO> page(@Param("cur") int cur,
                          @Param("key") String key,
                          @Param("start") Date start,
                          @Param("end") Date end);

    Integer getTotal(@Param("key") String key,
                     @Param("start") Date start,
                     @Param("end") Date end,
                     @Param("type") Integer type);

    void updateBanTime(@Param("id") Integer id,
                       @Param("banTime") Date banTime,
                       @Param("violation") Integer violation);

    void updateByField(@Param("field1") String field1,
                       @Param("value1") Object value1,
                       @Param("field2") String field,
                       @Param("value2") Object value);

    List<UserPageVO> getListByFiled(@Param("field") String field,
                                    @Param("value") Integer value);

    List<UserPageVO> pageMerchant(@Param("cur") int cur,
                                  @Param("key") String key,
                                  @Param("start") Date start,
                                  @Param("end") Date end);

    void insert(@Param("user") User user);

    void update(@Param("user") User user);

    void insertBatch(@Param("list") List<User> list);

    List<Map<Date, Integer>> getReport();

}