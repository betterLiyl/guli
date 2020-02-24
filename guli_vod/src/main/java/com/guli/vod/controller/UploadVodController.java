package com.guli.vod.controller;

import com.guli.common.result.Result;
import com.guli.vod.service.VodService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/vod")
@CrossOrigin
public class UploadVodController {

    @Autowired
    private VodService videoService;

    @PostMapping("upload")
    public Result uploadVideo(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file) throws Exception {

        String videoSourceId = videoService.uploadVideo(file);
        return Result.ok().message("视频上传成功").data("videoSourceId", videoSourceId);
    }

    /**
     * 根据视频id删除云端视频
     * @param videoSourceId
     * @return
     */
    @DeleteMapping("{videoSourceId}")
    public Result deleteVodById(@PathVariable("videoSourceId") String videoSourceId){
        Boolean flag = videoService.deleteVodById(videoSourceId);
        if(flag){
            return Result.ok();
        }
        return Result.error();
    }/**
     * 根据多个视频id删除云端视频
     * @param videoIdList
     * @return
     */
    @DeleteMapping("removeList")
    public Result deleteVodList(@RequestParam("videoIdList") List<String> videoIdList){
        Boolean flag = videoService.deleteVodList(videoIdList);
        if(flag){
            return Result.ok();
        }
        return Result.error();
    }
}
