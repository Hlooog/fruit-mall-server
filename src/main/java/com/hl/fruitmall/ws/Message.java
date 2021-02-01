package com.hl.fruitmall.ws;

import lombok.Data;

/**
 * @author Hl
 * @create 2021/1/26 21:26
 */
@Data
public class Message {
    private long id;
    private String toPhone;
    private String toName;
    private String fromPhone;
    private String fromName;
    private String avatar;
    private String content;
}
