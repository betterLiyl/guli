package com.guli.oss.controller;

import com.guli.common.result.Result;
import com.guli.oss.service.FileService;
import com.guli.oss.util.ConstantPropertiesUtil;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("oss")
@CrossOrigin
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("file/upload")
    public Result upload(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file,

            @ApiParam(name = "host", value = "文件上传路径", required = false)
            @RequestParam(value = "host", required = false) String host) {
        if(!StringUtils.isEmpty(host)){
            ConstantPropertiesUtil.FILE_HOST = host;
        }
        try {
            String url = fileService.upload(file);
            return Result.ok().data("url", url);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

}
