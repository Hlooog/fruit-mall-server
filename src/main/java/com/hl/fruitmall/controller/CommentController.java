package com.hl.fruitmall.controller;


import com.hl.fruitmall.common.annotation.PassToken;
import com.hl.fruitmall.common.annotation.VerificationToken;
import com.hl.fruitmall.common.enums.RoleEnum;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.service.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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

    @PostMapping("/add")
    @VerificationToken(roleType = RoleEnum.USER)
    public R add(@RequestBody Map<String,Object> map, HttpServletRequest request){
        return commentService.add(map,request);
    }

}