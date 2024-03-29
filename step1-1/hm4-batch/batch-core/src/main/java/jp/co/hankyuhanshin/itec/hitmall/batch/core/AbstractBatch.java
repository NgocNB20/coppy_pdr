/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.batch.core;

import jp.co.hankyuhanshin.itec.hitmall.batch.core.entity.BatchTask;
import lombok.Data;
import org.springframework.batch.core.step.tasklet.Tasklet;

import java.io.File;
import java.sql.Timestamp;

@Data
public abstract class AbstractBatch implements Tasklet {

    /**
     * BatchTask エンティティ
     */
    private BatchTask batchTask;

    /**
     * ショップシーケンス。
     */
    private Integer shopSeq;

    /**
     * ユーザ向けレポート文字列
     */
    private StringBuilder reportString;

    /**
     * ターゲットファイル
     */
    private File targetFilePath;

    public AbstractBatch() {
        this.reportString = new StringBuilder();
        this.batchTask = this.initDummyBatchTask();
        this.targetFilePath = new File(batchTask.getTargetFile());
    }

    /**
     * レポート文字列を1行追加する。
     *
     * @param line 1行分のレポート
     */
    protected void report(final String line) {
        this.reportString.append(line).append("\n");
    }

    private BatchTask initDummyBatchTask() {
        BatchTask dummyBatchTask = new BatchTask();
        dummyBatchTask.setTaskId(0);
        dummyBatchTask.setOnline("");
        dummyBatchTask.setBatchType("");
        dummyBatchTask.setBatchName("");
        dummyBatchTask.setTargetHost("");
        dummyBatchTask.setTargetFile("../../csv/shipment-regist-batch/sample-data.csv");
        dummyBatchTask.setExtraArgs("");
        dummyBatchTask.setShopSeq(0);
        dummyBatchTask.setTaskOwner("");
        dummyBatchTask.setVisibleGroup("");
        dummyBatchTask.setInterruptibleGroup("");
        dummyBatchTask.setAcceptTime(new Timestamp(new java.util.Date().getTime()));
        dummyBatchTask.setBatchHost("");
        dummyBatchTask.setTaskStatus("");
        dummyBatchTask.setTaskProgress("");
        dummyBatchTask.setTotalRecord(0);
        dummyBatchTask.setProcessedRecord(0);
        dummyBatchTask.setQuitFlag("");
        dummyBatchTask.setQuitUser("");
        dummyBatchTask.setTerminateTime(new Timestamp(new java.util.Date().getTime()));
        dummyBatchTask.setReportString("");
        dummyBatchTask.setExpiryTime(new Timestamp(new java.util.Date().getTime()));
        return dummyBatchTask;
    }
}
