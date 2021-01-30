package com.hl.fruitmall.mapper;

import com.hl.fruitmall.entity.bean.WithdrawalRecord;
import com.hl.fruitmall.entity.vo.WithdrawRecordVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 提现记录表(WithdrawalRecord)表数据库访问层
 *
 * @author hl
 * @since 2020-11-21 00:33:48
 */
public interface WithdrawalRecordMapper {

    void insert(@Param("bo") WithdrawalRecord withdrawalRecord);

    List<WithdrawRecordVO> page(@Param("phone") String phone,
                                @Param("cur") Integer cur,
                                @Param("start") Date start,
                                @Param("end") Date end);

    Integer getTotal(@Param("phone") String phone,
                     @Param("start") Date start,
                     @Param("end") Date end);
}