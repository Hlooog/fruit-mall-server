package com.hl.fruitmall.service;

import com.hl.fruitmall.common.uitls.R;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Hl
 * @create 2020/11/30 20:18
 */
public interface OSSService {

    R uploadFile(MultipartFile file);

    R delete(List<String> list);
}
