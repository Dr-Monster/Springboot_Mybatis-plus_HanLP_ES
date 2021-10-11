package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.KeyWords;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import com.mapper.KeyWordsMapper;
import com.service.KeyWordsService;
import com.utils.PoiUtil;
import com.vo.PackData;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.ooxml.extractor.POIXMLTextExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class KeyWordsServiceImpl extends ServiceImpl<KeyWordsMapper, KeyWords> implements KeyWordsService {

    @Autowired
    KeyWordsMapper keyWordsMapper;

    /**
     * 关键字抽取
     */
    @Override
    public void getKeywords(String filePath)  {
        if(filePath.endsWith(".doc") || filePath.endsWith(".docx")){
            //word
            String content = PoiUtil.readWordFile(filePath);
            getContentKeywords(content);
        }else if(filePath.endsWith(".xls") || filePath.endsWith(".xlsx")){
            //excel
            Map<String , String[][]> stringMap = PoiUtil.readExcelFile(filePath);
            getExcelContentKeywords(stringMap);
        }
    }

    @Override
    public List<KeyWords> getKeyWordsList(PackData packData) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        Page<KeyWords> page = new Page(packData.getPageIndex() , packData.getPageSize());
        IPage<KeyWords> iPage = keyWordsMapper.selectPage(page , queryWrapper);
        return iPage.getRecords();
    }

    @Override
    public int getKeyWordSize(PackData packData) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        return keyWordsMapper.selectCount(queryWrapper);
    }

    /**
     * 关键字抽取
     */
    public void getContentKeywords(String content) {
        List<String> termList = new ArrayList<String>(content.length());
        try {
            termList = HanLP.extractKeyword(content , content.length());
        } catch (Exception e) {
            e.printStackTrace();
        }

        KeyWords keyWords = new KeyWords();
        for(String item : termList){
            keyWords.setKeyword(item);
            keyWordsMapper.insert(keyWords);
        }
    }


    /**
     * 关键字抽取 excel
     */
    public void getExcelContentKeywords(Map<String , String[][]> stringMap){
        for (Map.Entry<String , String[][]> item : stringMap.entrySet()) {
            String[][] content = item.getValue();
            for (String[] row : content) {
                for (String cell : row) {
                    getContentKeywords(cell);
                }
            }
        }
    }
}
