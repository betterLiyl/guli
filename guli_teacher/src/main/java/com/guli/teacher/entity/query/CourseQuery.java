package com.guli.teacher.entity.query;

import lombok.Data;

import java.io.Serializable;
@Data
public class CourseQuery  implements Serializable{
    private static final long serialVersionUID = 1L;
    private String subjectId;
    private String subjectParentId;
    private String title;
    private String teacherId;
}
