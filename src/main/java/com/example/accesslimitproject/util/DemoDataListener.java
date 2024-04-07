package com.example.accesslimitproject.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.example.accesslimitproject.domain.DemoData;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class DemoDataListener extends AnalysisEventListener<DemoData> {

    private static final int BATCH_COUNT = 100;
    private List<DemoData> cachedDataList = Lists.newArrayListWithExpectedSize(BATCH_COUNT);

    public DemoDataListener(){
        log.info("DemoDataListener init");
    }
    @Override
    public void invoke(DemoData data, AnalysisContext analysisContext) {
        log.info("解析到一条数据：{}", JSON.toJSONString(data));
        cachedDataList.add(data);
        if(cachedDataList.size()>=BATCH_COUNT){
            saveData();
            cachedDataList = Lists.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData();
        log.info("所有数据解析完成！");
    }

    private void saveData() {
        log.info("{}条数据，开始存储数据库！", cachedDataList.size());
        log.info("存储数据库成功！");
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        log.info("解析到一条头数据：{}", JSON.toJSONString(headMap));
    }
}
