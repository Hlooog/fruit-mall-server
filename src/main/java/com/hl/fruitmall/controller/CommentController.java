package com.hl.fruitmall.controller;


import com.hl.fruitmall.common.annotation.PassToken;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.service.CommentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    
    @GetMapping("/page")
    @PassToken
    public R page(@RequestParam("id") Integer id, @RequestParam("cur") Integer cur){
        return commentService.page(id,cur);
    }

}