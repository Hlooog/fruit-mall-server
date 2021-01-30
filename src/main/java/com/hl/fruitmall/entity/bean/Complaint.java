package com.hl.fruitmall.entity.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 投诉记录表(Complaint)表实体类
 *
 * @author hl
 * @since 2020-11-21 00:33:32
 */
@SuppressWarnings("serial")
@Data
public class Complaint implements Serializable {
    private static final long serialVersionUID = 1L;
    //主键id
    private Integer id;
    //投诉人
    private Integer complainant;
    //被投诉人
    private Integer respondent;
    //被投诉人身份
    private Integer role;
    //投诉内容
    private String content;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;

}