package com.guli.statistics.controller;


import com.guli.common.result.Result;
import com.guli.statistics.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author lyl
 * @since 2020-02-19
 */
@RestController
@RequestMapping("/statistics")
@CrossOrigin
public class DailyController {
    @Autowired
    private DailyService dailyService;

    @PostMapping("{day}")
    public Result createStatisticsByDate(@PathVariable String day) {
        dailyService.createStatisticsByDay(day);
        return Result.ok();
    }

    @GetMapping("show-chart/{begin}/{end}/{type}")
    public Result showChart(@PathVariable String begin,@PathVariable String end,@PathVariable String type){
        Map<String, Object> map = dailyService.getChartData(begin, end, type);
        return Result.ok().data(map);
    }
}

