package com.hl.fruitmall.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.hl.fruitmall.common.enums.ExceptionEnum;
import com.hl.fruitmall.common.exception.GlobalException;
import com.hl.fruitmall.common.uitls.R;
import com.hl.fruitmall.service.OSSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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

    @Override
    public R delete(List<String> list) {
        OSS ossClient = null;
        for (int i = 0; i < list.size(); i++) {
            String url = list.get(i);
            url = url.substring(url.indexOf("/", 8) + 1);
            list.set(i, url);
        }
        try {
            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
            DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(list));
//            deleteObjectsResult.getDeletedObjects();
        } finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }
        return R.ok();
    }

    @Override
    public String uploadExcel(ByteArrayOutputStream outputStream, String name) {
        String format = new SimpleDateFormat("yyyy/MM/dd").
                format(new Date());
        String fileName = format + "/" + name;
        OSS ossClient = null;
        try {
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            ossClient.putObject(bucketName, fileName, inputStream);
            ossClient.shutdown();
            String url = "https://" + bucketName + "." + endpoint + "/" + fileName;
            return url;
        } catch (Exception e) {
            throw new GlobalException(ExceptionEnum.SYSTEM_UNKNOW_EXCEPTION);
        }
    }
}
