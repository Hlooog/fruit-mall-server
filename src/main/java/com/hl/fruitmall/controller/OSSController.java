package com.hl.fruitmall.controller;

import com.hl.fruitmall.common.annotation.PassToken;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.service.OSSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Hl
 * @create 2020/11/30 20:15
 */
@RestController()
@RequestMapping("/oss")
@Slf4j
public class OSSController {

    @Autowired
    OSSService ossService;

    @PostMapping("/upload")
    @PassToken
    public R uploadFile(@RequestParam("file") MultipartFile file) {
        return ossService.uploadFile(file);
    }
}
