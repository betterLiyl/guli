package com.guli.teacher.service;

import com.guli.teacher.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.teacher.entity.vo.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author lyl
 * @since 2020-02-12
 */
public interface EduSubjectService extends IService<EduSubject> {

    List<String> importEXCL(MultipartFile file);

    List<OneSubject> getTree();

    Boolean saveLevel1(EduSubject subject);

    Boolean saveLevelTwo(EduSubject subject);
}
