package com.guli.teacher.service;

import com.guli.teacher.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.teacher.entity.vo.OneChapter;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author lyl
 * @since 2020-02-14
 */
public interface EduChapterService extends IService<EduChapter> {

    List<OneChapter> getChapterAndVideoById(String id);

    boolean saveChapter(EduChapter chapter);

    Boolean updateChapter(EduChapter chapter);

    boolean removeChapterById(String id);
}
