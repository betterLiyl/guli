package com.guli.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.common.exception.EduException;
import com.guli.teacher.entity.EduChapter;
import com.guli.teacher.entity.EduVideo;
import com.guli.teacher.entity.vo.OneChapter;
import com.guli.teacher.entity.vo.TwoVideo;
import com.guli.teacher.mapper.EduChapterMapper;
import com.guli.teacher.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.teacher.service.EduVideoService;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author lyl
 * @since 2020-02-14
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<OneChapter> getChapterAndVideoById(String id) {

        //定义一个章节集合
        List<OneChapter> oneChapterList = new ArrayList<>();
        QueryWrapper<EduChapter> wr = new QueryWrapper<>();
        wr.eq("course_id", id);
        wr.orderByAsc("sort", "id");
        //先查询章节列表集合
        List<EduChapter> chapterList = baseMapper.selectList(wr);
        //再遍历章节集合，获取每个章节ID
        for (EduChapter eduChapter : chapterList) {
            OneChapter oneChapter = new OneChapter();
            BeanUtils.copyProperties(eduChapter, oneChapter);
            //再根据每个章节的ID查询节点的列表
            QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
            videoWrapper.eq("chapter_id", oneChapter.getId());
            videoWrapper.orderByAsc("sort", "id");
            List<EduVideo> eduVideoList = eduVideoService.list(videoWrapper);
            //把小节的列表添加章节中
            for (EduVideo eduVideo : eduVideoList) {
                TwoVideo twoVideo = new TwoVideo();
                BeanUtils.copyProperties(eduVideo, twoVideo);
                oneChapter.getChildren().add(twoVideo);
            }
            oneChapterList.add(oneChapter);
        }

        return oneChapterList;
    }

    @Override
    public boolean saveChapter(EduChapter chapter) {
        if (chapter == null) {
            return false;
        }
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("title", chapter.getTitle());
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new EduException(20001, "章节已存在");
        }
        int insert = baseMapper.insert(chapter);
        return insert == 1;
    }

    @Override
    public Boolean updateChapter(EduChapter chapter) {
        if (chapter == null) {
            return false;
        }
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("title", chapter.getTitle());
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new EduException(20001, "章节已存在");
        }
        int i = baseMapper.updateById(chapter);
        return i == 1;
    }

    @Override
    public boolean removeChapterById(String id) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", id);
        List<EduVideo> list = eduVideoService.list(wrapper);
        if (list.size() != 0) {
            throw new EduException(20001, "请先删除小结");
        }
        int i = baseMapper.deleteById(id);

        return i == 1;
    }
}
