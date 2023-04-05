package com.yanghi.haimusic.controller;

import com.yanghi.haimusic.service.CommonService;
import com.yanghi.haimusic.utils.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

import java.util.Map;

/**
 * 复合控制器
 * @author 泗安
 */
@RestController
@RequestMapping("/api/common")
@Validated
public class CommonController {

    @Resource(name = "commonServiceImpl")
    private CommonService commonService;

    @GetMapping("/search")
    public Result<Map<String, Object>> returnSearchResult(
            @NotBlank(message = "搜索关键词不能为空") @RequestParam("keyword") String keyword
    ){
       return commonService.getSearchSongAndSongSheetAndSingerAndVideoResultByKeyword(keyword);
    }
}
