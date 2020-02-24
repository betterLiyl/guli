package com.guli.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {

    String uploadVideo(MultipartFile file);

    Boolean deleteVodById(String videoSourceId);

    Boolean deleteVodList(List<String> videoIdList);
}
