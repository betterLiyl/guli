package com.guli.teacher.controller;


import com.guli.common.result.Result;
import com.guli.teacher.entity.EduChapter;
import com.guli.teacher.entity.vo.OneChapter;
import com.guli.teacher.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author lyl
 * @since 2020-02-14
 */
@RestController
@RequestMapping("/chapter")
@CrossOrigin
public class EduChapterController {

  @Autowired
  private EduChapterService eduChapterService;
    /**
     * 根据课程的id获取章节和小结列表
     */

    @GetMapping("{courseId}")
    public Result getChapterAndVideoById(@PathVariable String courseId){
        List<OneChapter> list = eduChapterService.getChapterAndVideoById(courseId);
        return Result.ok().data("list",list);
    }

    @PostMapping("save")
    public Result save(@RequestBody EduChapter chapter){
        boolean save = eduChapterService.saveChapter(chapter);
        if (save){
            return Result.ok();
        }else {
            return Result.error();
        }
    }
    @GetMapping("get/{id}")
    public Result getChapterById(@PathVariable String id){
        EduChapter chapter = eduChapterService.getById(id);
        return Result.ok().data("chapter",chapter);
    }
    @PutMapping("update")
    public Result  updateChapterById(@RequestBody EduChapter chapter){
        Boolean b = eduChapterService.updateChapter(chapter);
        if(b){
            return Result.ok();
        }else {
            return Result.error();
        }
    }
    @DeleteMapping("{id}")
    public Result deleteById(@PathVariable String id){
        boolean b = eduChapterService.removeChapterById(id);
        if(b){
            return Result.ok();
        }else {
            return Result.error();
        }
    }
}

