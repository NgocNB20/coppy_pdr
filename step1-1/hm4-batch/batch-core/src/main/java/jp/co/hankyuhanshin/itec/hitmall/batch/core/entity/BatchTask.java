/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.batch.core.entity;

import java.sql.Timestamp;

//import org.seasar.dao.annotation.tiger.Bean;
//import org.seasar.dao.annotation.tiger.Column;
//import org.seasar.dao.annotation.tiger.Id;
//import org.seasar.dao.annotation.tiger.IdType;

/**
 * バッチ処理タスク情報エンティティクラス
 *
 * @author Morimichi Tomo
 */
//@Bean(table = "BATCHTASK")
public final class BatchTask {

    /**
     * taskId
     */
    //    @Id(value = IdType.SEQUENCE, sequenceName = "batchtask_taskid_seq")
    //    @Column("TASKID")
    private Integer taskId;

    /**
     * ONLINE
     */
    //    @Column("BATCHDERIVE")
    private String online;

    /**
     * BATCHTYPE
     */
    //    @Column("BATCHTYPE")
    private String batchType;

    /**
     * BATCHNAME
     */
    //    @Column("BATCHNAME")
    private String batchName;

    /**
     * TARGETHOST
     */
    //    @Column("TARGETHOST")
    private String targetHost;

    /**
     * TARGETFILE
     */
    //    @Column("TARGETFILE")
    private String targetFile;

    /**
     * EXTRAARGS
     */
    //    @Column("EXTRAARGS")
    private String extraArgs;

    /**
     * SHOPSEQ
     */
    //    @Column("SHOPSEQ")
    private Integer shopSeq;

    /**
     * TASKOWNER
     */
    //    @Column("TASKOWNER")
    private String taskOwner;

    /**
     * VISIBLEGROUP
     */
    //    @Column("VISIBLEGROUP")
    private String visibleGroup;

    /**
     * INTERRUPTIBLEGROUP
     */
    //    @Column("INTERRUPTIBLEGROUP")
    private String interruptibleGroup;

    /**
     * ACCEPTTIME
     */
    //    @Column("ACCEPTTIME")
    private Timestamp acceptTime;

    /**
     * BATCHHOST
     */
    //    @Column("BATCHHOST")
    private String batchHost;

    /**
     * TASKSTATUS
     */
    //    @Column("TASKSTATUS")
    private String taskStatus;

    /**
     * TASKPROGRESS
     */
    //    @Column("TASKPROGRESS")
    private String taskProgress;

    /**
     * TOTALRECORD
     */
    //    @Column("TOTALRECORD")
    private Integer totalRecord;

    /**
     * PROCESSEDRECORD
     */
    //    @Column("PROCESSEDRECORD")
    private Integer processedRecord;

    /**
     * QUITFLAG
     */
    //    @Column("QUITFLAG")
    private String quitFlag;

    /**
     * QUITUSER
     */
    //    @Column("QUITUSER")
    private String quitUser;

    /**
     * TERMINATETIME
     */
    //    @Column("TERMINATETIME")
    private Timestamp terminateTime;

    /**
     * REPORTSTRING
     */
    //    @Column("REPORTSTRING")
    private String reportString;

    /**
     * EXPIRYTIME
     */
    //    @Column("EXPIRYTIME")
    private Timestamp expiryTime;

    /**
     * コンストラクタ
     */
    public BatchTask() {
    }

    /**
     * 初期登録用ミニマムコンストラクタ
     *
     * @param argOnline     ONLINE
     * @param argBatchType  BATCHTYPE
     * @param argBatchName  BATCHNAME
     * @param argTargetHost TARGETHOST
     */
    public BatchTask(final String argOnline,
                     final String argBatchType,
                     final String argBatchName,
                     final String argTargetHost) {
        this.online = argOnline;
        this.batchType = argBatchType;
        this.batchName = argBatchName;
        this.targetHost = argTargetHost;
    }

    /**
     * @return the taskId
     */
    public Integer getTaskId() {
        return taskId;
    }

    /**
     * @param argTaskId the taskId to set
     */
    public void setTaskId(final Integer argTaskId) {
        this.taskId = argTaskId;
    }

    /**
     * @return the online
     */
    public String getOnline() {
        return online;
    }

    /**
     * @param argOnline the online to set
     */
    public void setOnline(final String argOnline) {
        this.online = argOnline;
    }

    /**
     * @return the batchType
     */
    public String getBatchType() {
        return batchType;
    }

    /**
     * @param argBatchType the batchType to set
     */
    public void setBatchType(final String argBatchType) {
        this.batchType = argBatchType;
    }

    /**
     * @return the batchName
     */
    public String getBatchName() {
        return batchName;
    }

    /**
     * @param argBatchName the batchName to set
     */
    public void setBatchName(final String argBatchName) {
        this.batchName = argBatchName;
    }

    /**
     * @return the targetHost
     */
    public String getTargetHost() {
        return targetHost;
    }

    /**
     * @param argTargetHost the targetHost to set
     */
    public void setTargetHost(final String argTargetHost) {
        this.targetHost = argTargetHost;
    }

    /**
     * @return the targetFile
     */
    public String getTargetFile() {
        return targetFile;
    }

    /**
     * @param argTargetFile the targetFile to set
     */
    public void setTargetFile(final String argTargetFile) {
        this.targetFile = argTargetFile;
    }

    /**
     * @return the extraArgs
     */
    public String getExtraArgs() {
        return extraArgs;
    }

    /**
     * @param argExtraArgs the extraArgs to set
     */
    public void setExtraArgs(final String argExtraArgs) {
        this.extraArgs = argExtraArgs;
    }

    /**
     * @return the shopSeq
     */
    public Integer getShopSeq() {
        return shopSeq;
    }

    /**
     * @param argShopSeq the shopSeq to set
     */
    public void setShopSeq(final Integer argShopSeq) {
        this.shopSeq = argShopSeq;
    }

    /**
     * @return the taskOwner
     */
    public String getTaskOwner() {
        return taskOwner;
    }

    /**
     * @param argTaskOwner the taskOwner to set
     */
    public void setTaskOwner(final String argTaskOwner) {
        this.taskOwner = argTaskOwner;
    }

    /**
     * @return the visibleGroup
     */
    public String getVisibleGroup() {
        return visibleGroup;
    }

    /**
     * @param argVisibleGroup the visibleGroup to set
     */
    public void setVisibleGroup(final String argVisibleGroup) {
        this.visibleGroup = argVisibleGroup;
    }

    /**
     * @return the interruptibleGroup
     */
    public String getInterruptibleGroup() {
        return interruptibleGroup;
    }

    /**
     * @param argInterruptibleGroup the interruptibleGroup to set
     */
    public void setInterruptibleGroup(final String argInterruptibleGroup) {
        this.interruptibleGroup = argInterruptibleGroup;
    }

    /**
     * @return the acceptTime
     */
    public Timestamp getAcceptTime() {
        return acceptTime;
    }

    /**
     * @param argAcceptTime the acceptTime to set
     */
    public void setAcceptTime(final Timestamp argAcceptTime) {
        this.acceptTime = argAcceptTime;
    }

    /**
     * @return the batchHost
     */
    public String getBatchHost() {
        return batchHost;
    }

    /**
     * @param argBatchHost the batchHost to set
     */
    public void setBatchHost(final String argBatchHost) {
        this.batchHost = argBatchHost;
    }

    /**
     * @return the taskStatus
     */
    public String getTaskStatus() {
        return taskStatus;
    }

    /**
     * @param argTaskStatus the taskStatus to set
     */
    public void setTaskStatus(final String argTaskStatus) {
        this.taskStatus = argTaskStatus;
    }

    /**
     * @return the taskProgress
     */
    public String getTaskProgress() {
        return taskProgress;
    }

    /**
     * @param argTaskProgress the taskProgress to set
     */
    public void setTaskProgress(final String argTaskProgress) {
        this.taskProgress = argTaskProgress;
    }

    /**
     * @return the totalRecord
     */
    public Integer getTotalRecord() {
        return totalRecord;
    }

    /**
     * @param argTotalRecord the taskProgress to set
     */
    public void setTotalRecord(final Integer argTotalRecord) {
        this.totalRecord = argTotalRecord;
    }

    /**
     * @return the processedRecord
     */
    public Integer getProcessedRecord() {
        return processedRecord;
    }

    /**
     * @param argProcessedRecord the processedRecord to set
     */
    public void setProcessedRecord(final Integer argProcessedRecord) {
        this.processedRecord = argProcessedRecord;
    }

    /**
     * @return the quitFlag
     */
    public String getQuitFlag() {
        return quitFlag;
    }

    /**
     * @param argQuitFlag the quitFlag to set
     */
    public void setQuitFlag(final String argQuitFlag) {
        this.quitFlag = argQuitFlag;
    }

    /**
     * @return the quitUser
     */
    public String getQuitUser() {
        return quitUser;
    }

    /**
     * @param argQuitUser the quitUser to set
     */
    public void setQuitUser(final String argQuitUser) {
        this.quitUser = argQuitUser;
    }

    /**
     * @return the terminateTime
     */
    public Timestamp getTerminateTime() {
        return terminateTime;
    }

    /**
     * @param argTerminateTime the terminateTime to set
     */
    public void setTerminateTime(final Timestamp argTerminateTime) {
        this.terminateTime = argTerminateTime;
    }

    /**
     * @return the reportString
     */
    public String getReportString() {
        return reportString;
    }

    /**
     * @param argReportString the reportString to set
     */
    public void setReportString(final String argReportString) {
        this.reportString = argReportString;
    }

    /**
     * @return the expiryTime
     */
    public Timestamp getExpiryTime() {
        return expiryTime;
    }

    /**
     * @param argExpiryTime the expiryTime to set
     */
    public void setExpiryTime(final Timestamp argExpiryTime) {
        this.expiryTime = argExpiryTime;
    }
}
