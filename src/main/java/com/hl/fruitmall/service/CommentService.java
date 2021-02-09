package com.hl.fruitmall.service;

import com.hl.fruitmall.common.uitls.R;

/**
 * 评论表(Comment)表服务接口
 *
 * @author hl
 * @since 2020-11-21 00:33:31
 */
public interface CommentService {

    R page(Integer id, Integer cur);
}