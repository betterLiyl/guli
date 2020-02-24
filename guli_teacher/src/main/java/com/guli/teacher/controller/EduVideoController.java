package com.guli.teacher.controller;


import com.guli.common.result.Result;
import com.guli.teacher.entity.EduVideo;
import com.guli.teacher.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author lyl
 * @since 2020-02-16
 */
@RestController
@RequestMapping("/video")
@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService videoService;

    @PostMapping("save")
    public Result save(@RequestBody EduVideo video) {
        boolean save = videoService.save(video);
        if (save) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    @GetMapping("{id}")
    public Result getVideoById(@PathVariable String id) {
        EduVideo video = videoService.getById(id);
        return Result.ok().data("video", video);
    }

    @PutMapping("update")
    public Result update(@RequestBody EduVideo video) {
        boolean b = videoService.updateById(video);
        if (b) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    @DeleteMapping("{id}")
    public Result deleteById(@PathVariable String id) {
        Boolean flag = videoService.deleteVideoById(id);
        if (flag) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }
}

