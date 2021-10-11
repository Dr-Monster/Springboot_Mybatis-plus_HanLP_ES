package com.controller;

import com.service.SegmentWordsService;
import com.utils.MultipartFileToFileUtil;
import com.vo.AjaxJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@RestController
@RequestMapping("/segmentWordsController")
public class SegmentWordsConroller {

    @Autowired
    SegmentWordsService segmentWordsService;

    /**
     * 文件上传
     *
     * @param request
     * @param multipartFile
     */
    @PostMapping(value = "/segmentWordsFileUpload")
    @ResponseBody
    public AjaxJson segmentWordsFileUpload(HttpServletRequest request, @RequestParam(value = "file") MultipartFile
            multipartFile) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            File file = MultipartFileToFileUtil.multipartFileToFile(multipartFile);
            segmentWordsService.getSegmentwords(file.getPath());
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;
    }
}
