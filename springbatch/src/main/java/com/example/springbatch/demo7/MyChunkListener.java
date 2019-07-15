package com.example.springbatch.demo7;

import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.scope.context.ChunkContext;

public class MyChunkListener{
    @BeforeChunk
    public void beforeChunk(ChunkContext chunkContext) throws Exception {
        System.out.println(chunkContext.getStepContext().getStepName()+ " before ====chunk");
    }

    public void onError(Exception ex) throws Exception {

    }
    @AfterChunk
    public void afterChunk(ChunkContext chunkContext) throws Exception {
        System.out.println(chunkContext.getStepContext().getStepName()+ " after ====chunk");
    }
}
