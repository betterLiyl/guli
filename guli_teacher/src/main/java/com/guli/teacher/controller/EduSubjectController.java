package com.guli.teacher.controller;


import com.guli.common.result.Result;
import com.guli.teacher.entity.EduSubject;
import com.guli.teacher.entity.vo.OneSubject;
import com.guli.teacher.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author lyl
 * @since 2020-02-12
 */
@RestController
@RequestMapping("/subject")
@CrossOrigin
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;

    @PostMapping("import")
    public Result importSubject(MultipartFile file) {
        List<String> mesList = eduSubjectService.importEXCL(file);
        if (mesList.size() == 0) {
            return Result.ok();
        } else {
            return Result.error().data("messageList", mesList);
        }
    }

    @GetMapping("list")
    public Result treeSubject(){
        try {
            List<OneSubject> list = eduSubjectService.getTree();
            return Result.ok().data("subjectList",list);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable  String id){
        boolean b = eduSubjectService.removeById(id);
        if(b){
            return Result.ok();
        } else {
            return Result.error();
        }
    }
    @PostMapping("saveLevelOne")
    public Result saveLevelOne(@RequestBody EduSubject subject){
        Boolean flag = eduSubjectService.saveLevel1(subject);
        if(flag){
            return Result.ok();
        }else {
            return Result.error();
        }
    }
    @PostMapping("saveLevelTwo")
    public Result saveLevelTwo(@RequestBody EduSubject subject){
        Boolean flag = eduSubjectService.saveLevelTwo(subject);
        if(flag){
            return Result.ok();
        }else {
            return Result.error();
        }
    }
}

