package com.example.springbatch.work;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.util.ClassUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * HASEE
 * 2019/8/13 13:41
 */
public class FlatFileSortItemReader<T> extends AbstractItemCountingItemStreamItemReader<T> implements ResourceAwareItemReaderItemStream<T>, InitializingBean {
    private boolean initRead = false; // 第一次读标志
    private List<T> result = new ArrayList<>();
    private BufferedReader reader;
    private int lineCount = 0;
    private String[] commnets = {"#"};// 注释

    public FlatFileSortItemReader() {
        setName(ClassUtils.getShortName(FlatFileItemReader.class));
    }

    @Override
    public void setResource(Resource resource) {

    }

    @Override
    protected T doRead() throws Exception {
        if (!initRead){
            result = readThenSort();
            lineCount =result.size();
            initRead = true;
        }
        return null;
    }

    private List readThenSort() {
        List<String> lines = new ArrayList<>();
        return lines;
    }

    private String readLine() {
        try {
            String line = reader.readLine();
            lineCount++;
            while (isComment(line)) {
                line = reader.readLine();
                lineCount++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断此行是否注释
     * @param line
     * @return
     */
    private boolean isComment(String line) {
        for (String pre : commnets) {
            if (line.startsWith(pre)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void doOpen() throws Exception {

    }

    @Override
    protected void doClose() throws Exception {

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
