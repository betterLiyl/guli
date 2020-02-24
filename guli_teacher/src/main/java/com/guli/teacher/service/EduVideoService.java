package com.guli.teacher.service;

import com.guli.teacher.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author lyl
 * @since 2020-02-16
 */
public interface EduVideoService extends IService<EduVideo> {

    Boolean deleteVideoById(String id);

    /**
     * 根据课程Id删除（多个）小节
     * @param courseId
     * @return
     */
    Boolean deleteVideoByCourseId(String courseId);
}
