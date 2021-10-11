package com.controller;


import com.alibaba.fastjson.JSON;
import com.service.KeyWordsService;
import com.utils.MultipartFileToFileUtil;
import com.vo.AjaxJson;
import com.vo.PackData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@RestController
@RequestMapping("/keyWordsController")
public class KeyWordsController {

    @Autowired
    KeyWordsService keyWordsService;

    /**
     * 文件上传
     * @param request
     * @param multipartFile
     */
    @PostMapping(value = "/keyWordsFileUpload")
    @ResponseBody
    public AjaxJson keyWordsFileUpload(HttpServletRequest request, @RequestParam(value="file") MultipartFile
            multipartFile) {
        AjaxJson ajaxJson = new AjaxJson();
        try{
            File file = MultipartFileToFileUtil.multipartFileToFile(multipartFile);
            keyWordsService.getKeywords(file.getPath());
            ajaxJson.setSuccess(true);
        }catch (Exception e){
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;
    }


    @PostMapping(value = "/getKeyWordsList")
    public String getKeyWordsList(HttpServletRequest request) {
        PackData packData = new PackData();
        int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
        int pageSize = Integer.valueOf(request.getParameter("pageSize"));
        packData.setPageIndex(pageIndex);
        packData.setPageSize(pageSize);
        int total = keyWordsService.getKeyWordSize(packData);
        String listJson = JSON.toJSONString(keyWordsService.getKeyWordsList(packData));
        String string = "{\"total\" :" + total + ", \"obj\" :" + listJson + "}";
        return string;
    }
}
