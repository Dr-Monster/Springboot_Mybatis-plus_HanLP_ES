package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.KeyWords;
import com.entity.SegmentWords;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import com.mapper.SegmentWordsMapper;
import com.service.SegmentWordsService;
import com.utils.PoiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SegmentWordsServiceImpl extends ServiceImpl<SegmentWordsMapper , SegmentWords> implements SegmentWordsService {

    @Autowired
    SegmentWordsMapper segmentWordsMapper;

    /**
     * 智能分词
     */
    @Override
    public void getSegmentwords(String filePath) {

        if(filePath.endsWith(".doc") || filePath.endsWith(".docx")){
            //word
            String content = PoiUtil.readWordFile(filePath);
            getContentSegmentwords(content);
        }else if(filePath.endsWith(".xls") || filePath.endsWith(".xlsx")){
            //excel
            Map<String , String[][]> stringMap = PoiUtil.readExcelFile(filePath);
            getExcelContentSegmentwords(stringMap);
        }
    }

    /**
     * word
     * @param content
     */
    public void getContentSegmentwords(String content){
        List<Term> termList = new ArrayList<Term>(content.length());
        try {
            termList = HanLP.segment(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SegmentWords segmentWords = new SegmentWords();
        for(Term item : termList){
            segmentWords.setSegmentWords(item.word);
            segmentWordsMapper.insert(segmentWords);
        }
    }


    /**
     * excel
     * @param stringMap
     */
    public void getExcelContentSegmentwords(Map<String , String[][]> stringMap){
        for (Map.Entry<String , String[][]> item : stringMap.entrySet()) {
            String[][] content = item.getValue();
            for (String[] row : content) {
                for (String cell : row) {
                    getContentSegmentwords(cell);
                }
            }
        }
    }
}
