package com.guli.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.teacher.entity.EduCourse;
import com.guli.teacher.entity.EduCourseDescription;
import com.guli.teacher.entity.query.CourseQuery;
import com.guli.teacher.entity.vo.CoursePublishVo;
import com.guli.teacher.entity.vo.CourseVo;
import com.guli.teacher.entity.vo.CourseWebVo;
import com.guli.teacher.mapper.EduCourseMapper;
import com.guli.teacher.service.EduChapterService;
import com.guli.teacher.service.EduCourseDescriptionService;
import com.guli.teacher.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.teacher.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author lyl
 * @since 2020-02-14
 */
@Service
@Transactional
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;
    @Autowired
    private EduVideoService videoService;
    @Autowired
    private EduChapterService chapterService;

    @Override
    public String saveVo(CourseVo vo) {
        //添加课程
        baseMapper.insert(vo.getEduCourse());
        //获取课程id
        String courseId = vo.getEduCourse().getId();
        //添加课程描述
        vo.getEduCourseDesc().setId(courseId);
        eduCourseDescriptionService.save(vo.getEduCourseDesc());
        return courseId;
    }

    @Override
    public CourseVo getCourseVoById(String id) {
        CourseVo vo = new CourseVo();
        EduCourse eduCourse = baseMapper.selectById(id);
        if (eduCourse == null) {
            return vo;
        }
        vo.setEduCourse(eduCourse);
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(id);
        if (eduCourseDescription == null) {
            return vo;
        }
        vo.setEduCourseDesc(eduCourseDescription);
        return vo;
    }

    @Override
    public Boolean updateVo(CourseVo vo) {
        if (StringUtils.isEmpty(vo.getEduCourse().getId())) {
            return false;
        }
        int i = baseMapper.updateById(vo.getEduCourse());
        if (i <= 0) {
            return false;
        }
        vo.getEduCourseDesc().setId(vo.getEduCourse().getId());
        boolean b = eduCourseDescriptionService.updateById(vo.getEduCourseDesc());
        return b;
    }

    @Override
    public void getPageList(Page<EduCourse> objectPage, CourseQuery courseQuery) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if (courseQuery == null) {
            baseMapper.selectPage(objectPage, wrapper);
        }
        String subjectId = courseQuery.getSubjectId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String teacherId = courseQuery.getTeacherId();
        String title = courseQuery.getTitle();
        if (!StringUtils.isEmpty(subjectId)) {
            wrapper.eq("subject_id", subjectId);
        }
        if (!StringUtils.isEmpty(subjectParentId)) {
            wrapper.eq("subject_parent_id", subjectParentId);
        }
        if (!StringUtils.isEmpty(teacherId)) {
            wrapper.eq("teacher_id", teacherId);
        }
        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        baseMapper.selectPage(objectPage, wrapper);
    }

    @Override
    public Boolean deleteById(String id) {
        //根据id删除所有视频
        videoService.deleteVideoByCourseId(id);
        //根据id删除所有章节
        chapterService.removeChapterById(id);
        // 删除描述
        boolean b = eduCourseDescriptionService.removeById(id);
        if (!b) {
            return false;
        }
        int i = baseMapper.deleteById(id);
        return i == 1;
    }

    @Override
    public Boolean updateStatusById(String id) {
        EduCourse course = new EduCourse();
        course.setId(id);
        course.setStatus("Normal");
        int i = baseMapper.updateById(course);

        return i == 1;
    }

    @Override
    public CoursePublishVo getCoursePublishVoById(String id) {
        CoursePublishVo vo = baseMapper.getCoursePublishVoById(id);
        return vo;
    }

    @Override
    public Map<String, Object> getMapById(String id) {

        Map<String, Object> map = baseMapper.getMapById(id);
        return map;
    }
    /**
     * 根据讲师id查询当前讲师的课程列表
     * @param id
     * @return
     */
    @Override
    public List<EduCourse> selectByTeacherId(String id) {

        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("teacher_id", id);
        //按照最后更新时间倒序排列
        queryWrapper.orderByDesc("gmt_modified");

        List<EduCourse> courses = baseMapper.selectList(queryWrapper);
        return courses;
    }

    /**
     * 分页查询所有课程
     * @param pageParam
     * @return
     */
    @Override
    public Map<String, Object> pageListWeb(Page<EduCourse> pageParam) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_modified");

        baseMapper.selectPage(pageParam, queryWrapper);

        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Transactional
    @Override
    public CourseWebVo selectInfoWebById(String id) {
        this.updatePageViewCount(id);
        return baseMapper.selectInfoWebById(id);
    }
    @Override
    public void updatePageViewCount(String id) {
       EduCourse course = baseMapper.selectById(id);
        course.setViewCount(course.getViewCount() + 1);
        baseMapper.updateById(course);
    }
}
