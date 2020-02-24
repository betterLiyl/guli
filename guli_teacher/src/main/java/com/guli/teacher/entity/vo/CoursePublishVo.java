package com.guli.teacher.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CoursePublishVo implements Serializable {

    private String id;//课程ID

    private String title;//课程名称

    private String subjectParentTitle;//一级类目

    private String subjectTitle;//二级类目

    private String lessonNum;//课时

    private String teacherName;//讲师名称

    private String price;//课程价格

    private String cover;//封面
}
