package com.hl.fruitmall.entity.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Hl
 * @create 2021/2/10 19:37
 */
@Data
@AllArgsConstructor
public class IScore implements Serializable {
    private static final long serialVersionUID = -4443217478857045002L;
    private Integer id;
    private Integer userId;
    private double allScore;
    private double userScore;
    private double varietyScore;
    private double monthlyScore;
}
