package com.guli.teacher.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.result.Result;
import com.guli.teacher.entity.EduTeacher;
import com.guli.teacher.entity.query.TeacherQuery;
import com.guli.teacher.service.EduTeacherService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author lyl
 * @since 2020-02-09
 */
@CrossOrigin
@RestController
@RequestMapping("/teacher")
@ApiModel(value = "讲师操作")
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("/list")
    private Result getTeacherList() {
        try {
            List<EduTeacher> list = eduTeacherService.list(null);
            return Result.ok().data("items", list);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @ApiOperation(value = "删除讲师")
    @DeleteMapping("{id}")
    public Result deleteTeacherById(
            @ApiParam(name = "id", value = "讲师id", required = true)
            @PathVariable(value = "id") String id) {
        try {
            eduTeacherService.removeById(id);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();

        }
    }


    @ApiOperation(value = "分页条件查询讲师信息")
    @PostMapping("/{page}/{limit}")
    public Result getTeacherByPage(
            @ApiParam(name = "page", value = "当前页", required = true)
            @PathVariable(value = "page") Integer page,
            @ApiParam(name = "limit", value = "每页显示记录数量", required = true)
            @PathVariable(value = "limit") Integer limit,
            @ApiParam(name = "teacherQuery", value = "查询对象", required = false)
            @RequestBody TeacherQuery teacherQuery
    ) {
        try {
            Page<EduTeacher> eduTeacherPage = new Page<>(page, limit);
            eduTeacherService.pageQuery(eduTeacherPage, teacherQuery);
            List<EduTeacher> records = eduTeacherPage.getRecords();
            long total = eduTeacherPage.getTotal();
            return Result.ok().data("total", total).data("rows", records);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }


    @ApiOperation(value = "新增讲师")
    @PostMapping("/save")
    public Result insertTeacher(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher teacher) {
        try {
            eduTeacherService.save(teacher);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @ApiOperation(value = "根据id获取讲师信息")
    @GetMapping("{id}")
    public Result getTeacherById(
            @ApiParam(name = "id", value = "讲师Id", required = true)
            @PathVariable(value = "id") String id) {
        try {
            EduTeacher teacher = eduTeacherService.getById(id);
            return Result.ok().data("teacher", teacher);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @ApiOperation(value = "根据id修改讲师信息")
    @PutMapping("/update")
    public Result updateTeacherById(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher teacher) {
        try {
            eduTeacherService.updateById(teacher);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }
}

