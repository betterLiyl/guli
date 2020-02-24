package com.guli.teacher.client;

import com.guli.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("guli-vod")
@Component
public interface VodClient {
    @DeleteMapping(value = "/vod/{videoSourceId}")
    public Result deleteVodById(@PathVariable("videoSourceId") String videoSourceId);

    @DeleteMapping("/vod/removeList")
    public Result deleteVodList(@RequestParam("videoIdList") List<String> videoIdList);
}