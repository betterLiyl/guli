package com.guli.teacher.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.result.Result;
import com.guli.teacher.entity.EduCourse;
import com.guli.teacher.entity.EduTeacher;
import com.guli.teacher.service.EduCourseService;
import com.guli.teacher.service.EduTeacherService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teacher/front")
@CrossOrigin
public class FrontTeacherController {
    @Autowired
   private EduTeacherService teacherService;
    @Autowired
    private EduCourseService courseService;


    @ApiOperation(value = "分页讲师列表")
    @GetMapping(value = "getAllTeacherList/{page}/{limit}")
    public Result getAllPageTeacher(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit){

        Page<EduTeacher> pageParam = new Page<>(page, limit);

        Map<String, Object> map = teacherService.getAllTeacherList(pageParam);

        return  Result.ok().data(map);
    }


    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping(value = "{id}")
    public Result getById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){

        //查询讲师信息
        EduTeacher teacher = teacherService.getById(id);

        //根据讲师id查询这个讲师的课程列表
        List<EduCourse> courseList = courseService.selectByTeacherId(id);

        return Result.ok().data("teacher", teacher).data("courseList", courseList);
    }
}
