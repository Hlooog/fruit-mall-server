package com.hl.fruitmall.controller;


import com.hl.fruitmall.service.CommentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 评论表(Comment)表控制层
 *
 * @author hl
 * @since 2020-11-21 00:33:31
 */
@RestController
@RequestMapping("comment")
public class CommentController {
    /**
     * 服务对象
     */
    @Resource
    private CommentService commentService;

}