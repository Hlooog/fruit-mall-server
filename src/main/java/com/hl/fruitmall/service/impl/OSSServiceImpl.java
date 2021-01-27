package com.hl.fruitmall.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.hl.fruitmall.common.enums.ExceptionEnum;
import com.hl.fruitmall.common.exception.GlobalException;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.service.OSSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author Hl
 * @create 2020/11/30 20:18
 */
@Service
@Slf4j
public class OSSServiceImpl implements OSSService {

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.bucketName}")
    private String bucketName;


    @Override
    public R uploadFile(MultipartFile file) {
        // 读取图片
        // 生成文件目录
        String format = new SimpleDateFormat("yyyy/MM/dd").
                format(new Date());
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String originName = uuid + file.getName();
        String fileName = format + "/" + originName;
        OSS ossClient = null;
        try {
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            InputStream inputStream = file.getInputStream();
            ossClient.putObject(bucketName, fileName, inputStream);
            ossClient.shutdown();
            String url = "https://" + bucketName + "." + endpoint + "/" + fileName;
            return R.ok(url);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new GlobalException(ExceptionEnum.SYSTEM_UNKNOW_EXCEPTION.getCode(),
                    ExceptionEnum.SYSTEM_UNKNOW_EXCEPTION.getMsg());
        }
    }
}
