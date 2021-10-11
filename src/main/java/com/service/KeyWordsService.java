package com.service;

import com.entity.KeyWords;
import com.vo.PackData;

import java.io.InputStream;
import java.util.List;

public interface KeyWordsService {

    public void getKeywords(String filePath);

    public List<KeyWords> getKeyWordsList(PackData packData);

    public int getKeyWordSize(PackData packData);
}
