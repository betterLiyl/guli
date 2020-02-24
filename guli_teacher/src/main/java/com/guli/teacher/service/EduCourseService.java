package com.guli.teacher.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.teacher.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.teacher.entity.query.CourseQuery;
import com.guli.teacher.entity.vo.CoursePublishVo;
import com.guli.teacher.entity.vo.CourseVo;
import com.guli.teacher.entity.vo.CourseWebVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author lyl
 * @since 2020-02-14
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveVo(CourseVo vo);

    CourseVo getCourseVoById(String id);

    Boolean updateVo(CourseVo vo);

    void getPageList(Page<EduCourse> objectPage, CourseQuery courseQuery);

    Boolean deleteById(String id);

    Boolean updateStatusById(String id);

    CoursePublishVo getCoursePublishVoById(String id);

    Map<String,Object> getMapById(String id);

    List<EduCourse> selectByTeacherId(String id);

    Map<String,Object> pageListWeb(Page<EduCourse> pageParam);

    CourseWebVo selectInfoWebById(String courseId);


    /**
     * 更新课程浏览数
     * @param id
     */
    void updatePageViewCount(String id);
}
