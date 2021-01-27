package com.hl.fruitmall.controller;


import com.hl.fruitmall.service.ComplaintService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 投诉记录表(Complaint)表控制层
 *
 * @author hl
 * @since 2020-11-21 00:33:33
 */
@RestController
@RequestMapping("complaint")
public class ComplaintController {
    /**
     * 服务对象
     */
    @Resource
    private ComplaintService complaintService;

}