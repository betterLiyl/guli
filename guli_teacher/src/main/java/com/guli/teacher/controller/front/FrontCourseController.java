package com.guli.teacher.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.result.Result;
import com.guli.teacher.entity.EduCourse;
import com.guli.teacher.entity.vo.CourseWebVo;
import com.guli.teacher.entity.vo.OneChapter;
import com.guli.teacher.service.EduChapterService;
import com.guli.teacher.service.EduCourseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/course/front")
@CrossOrigin
public class FrontCourseController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @ApiOperation(value = "根据ID查询课程详情")
    @GetMapping(value = "{courseId}")
    public Result getById(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId){

        //查询课程信息和讲师信息
        CourseWebVo courseWebVo = courseService.selectInfoWebById(courseId);

        //查询当前课程的章节信息
        List<OneChapter> chapterVoList = chapterService.getChapterAndVideoById(courseId);

        return Result.ok().data("course", courseWebVo).data("chapterVoList", chapterVoList);
    }

    @ApiOperation(value = "分页课程列表")
    @GetMapping(value = "getPageCourseList/{page}/{limit}")
    public Result pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit){

        Page<EduCourse> pageParam = new Page<>(page, limit);

        Map<String, Object> map = courseService.pageListWeb(pageParam);

        return  Result.ok().data(map);
    }
}
