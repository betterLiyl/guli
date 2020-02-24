package com.guli.teacher.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.result.Result;
import com.guli.teacher.entity.EduCourse;
import com.guli.teacher.entity.query.CourseQuery;
import com.guli.teacher.entity.vo.CoursePublishVo;
import com.guli.teacher.entity.vo.CourseVo;
import com.guli.teacher.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author lyl
 * @since 2020-02-14
 */
@RestController
@RequestMapping("/course")
@CrossOrigin
public class EduCourseController {
   @Autowired
    private EduCourseService eduCourseService;

    //保存基本信息
    @PostMapping("saveVo")
    public Result save(@RequestBody CourseVo vo){
        String courseId = eduCourseService.saveVo(vo);
        return Result.ok().data("courseId",courseId);
    }
    @PutMapping("updateVo")
    public Result update(@RequestBody CourseVo vo){
        Boolean flag = eduCourseService.updateVo(vo);
        if(flag){
            return Result.ok();
        }else {
            return Result.error();
        }
    }
    /**
     * 根据课程id获取课程的信息
     */
    @GetMapping("{id}")
    public Result getCourseVoById(@PathVariable String id){
        CourseVo vo = eduCourseService.getCourseVoById(id);
        return Result.ok().data("courseInfo",vo);
    }
    /**
     * 根据搜索条件分页查询
     */
    @PostMapping("{page}/{limit}")
    public Result getPageList(
            @PathVariable Integer page,
            @PathVariable Integer limit,
            @RequestBody CourseQuery courseQuery){
         try {
             Page<EduCourse> objectPage = new Page<>(page, limit);
             eduCourseService.getPageList(objectPage,courseQuery);
             return Result.ok().data("rows",objectPage.getRecords()).data("total",objectPage.getTotal());
         } catch (Exception e) {
              e.printStackTrace();
              return Result.error();
         }
    }

    /**
     * 根据id删除课程信息
     */
    @DeleteMapping("{id}")
    public Result deleteById(@PathVariable String id){
        Boolean flag = eduCourseService.deleteById(id);
        if(flag){
            return Result.ok();
        }else {
            return Result.error();
        }
    }
    /**
     * 根据课程Id查询课程Vo对象
     * @param id
     * @return
     */
    @GetMapping("vo/{id}")
    public Result getCoursePublishVoById(@PathVariable String id){
       //方法一
        // CoursePublishVo vo = eduCourseService.getCoursePublishVoById(id);
      //方法二 使用map返回数据
        Map<String,Object> map = eduCourseService.getMapById(id);

        //return Result.ok().data("item",vo);
        return Result.ok().data(map);

    }

    @PutMapping("/updateStatusById/{id}")
    public Result updateByStatusById(@PathVariable String id){
        Boolean flag = eduCourseService.updateStatusById(id);
        if(flag){
            return Result.ok();
        } else{
            return Result.error();
        }
    }

}

