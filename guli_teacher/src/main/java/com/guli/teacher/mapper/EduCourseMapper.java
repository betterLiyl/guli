package com.guli.teacher.mapper;

import com.guli.teacher.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guli.teacher.entity.vo.CoursePublishVo;
import com.guli.teacher.entity.vo.CourseWebVo;

import java.util.Map;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author lyl
 * @since 2020-02-14
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishVo getCoursePublishVoById(String id);

    Map<String,Object> getMapById(String id);

    CourseWebVo selectInfoWebById(String id);
}
