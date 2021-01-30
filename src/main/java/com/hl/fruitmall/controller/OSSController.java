package com.hl.fruitmall.controller;

import com.hl.fruitmall.common.annotation.PassToken;
import com.hl.fruitmall.common.annotation.VerificationToken;
import com.hl.fruitmall.common.enums.RoleEnum;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.service.OSSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Hl
 * @create 2020/11/30 20:15
 */
@RestController()
@RequestMapping("/oss")
@Slf4j
public class OSSController {

    @Autowired
    private OSSService ossService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/upload")
    @PassToken
    public R uploadFile(@RequestParam("file") MultipartFile file) {
        return ossService.uploadFile(file);
    }

    @DeleteMapping("/delete")
    @VerificationToken(roleType = RoleEnum.USER)
    public R delete(@RequestBody List<String> list){
        return ossService.delete(list);
    }
}
