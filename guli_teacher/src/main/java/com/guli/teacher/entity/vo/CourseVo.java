package com.guli.teacher.entity.vo;

import com.guli.teacher.entity.EduCourse;
import com.guli.teacher.entity.EduCourseDescription;
import lombok.Data;

@Data
public class CourseVo {
    private EduCourse eduCourse;
    private EduCourseDescription eduCourseDesc;
}
