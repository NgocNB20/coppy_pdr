/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.cuenoteapi.pdr.common;

import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;

/**
 * CUENOTE-API連携 API情報保持クラス<br/>
 * @author tt32117
 */
public class ApiInfo {

    /** メソッド名 */
    private String methodName = "";

    /** WebAPI名 */
    private String webApiName = "";

    /** ストアド名 */
    private String storedName = "";

    /** ストアド返却値（ステータス） */
    private int storedCode;

    /** ストアド返却値（処理時間） */
    private String qTime;

    /** エラー発生時にログに記載するインプット情報 */
    private String errorKeyInfoLog;

    /** 例外オブジェクト */
    private Exception exception;

    /** ロガー */
    private Logger log;

    /** コンテキスト */
    private ServletContext context;

    /** エラー発生時にメールに記載するインプット情報(個人情報を除去) */
    private String errorKeyInfoMail;

    /**
     * コンストラクタ
     *
     * @param methodName メソッド名
     * @param webApiName WebAPI名
     * @param context コンテキスト
     * @param log ロガー
     */
    public ApiInfo(String methodName, String webApiName, ServletContext context, Logger log) {
        this.methodName = methodName;
        this.webApiName = webApiName;
        this.context = context;
        this.log = log;

    }

    /**
     * @return the methodName
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * @param methodName the methodName to set
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * @return the webApiName
     */
    public String getWebApiName() {
        return webApiName;
    }

    /**
     * @param webApiName the webApiName to set
     */
    public void setWebApiName(String webApiName) {
        this.webApiName = webApiName;
    }

    /**
     * @return the storedName
     */
    public String getStoredName() {
        return storedName;
    }

    /**
     * @param storedName the storedName to set
     */
    public void setStoredName(String storedName) {
        this.storedName = storedName;
    }

    /**
     * @return the storedCode
     */
    public int getStoredCode() {
        return storedCode;
    }

    /**
     * @param storedCode the storedCode to set
     */
    public void setStoredCode(int storedCode) {
        this.storedCode = storedCode;
    }

    /**
     * @return the qTime
     */
    public String getQTime() {
        return qTime;
    }

    /**
     * @param qTime the qTime to set
     */
    public void setQTime(String qTime) {
        this.qTime = qTime;
    }

    /**
     * @return the errorMsg
     */
    public String getErrorMsg() {
        return PropManeger.getMessage(context, methodName + ".error");
    }

    /**
     * @return the exception
     */
    public Exception getException() {
        return exception;
    }

    /**
     * @param exception the exception to set
     */
    public void setException(Exception exception) {
        this.exception = exception;
    }

    /**
     * @return the log
     */
    public Logger getLog() {
        return log;
    }

    /**
     * @param log the log to set
     */
    public void setLog(Logger log) {
        this.log = log;
    }

    /**
     * @return the context
     */
    public ServletContext getContext() {
        return context;
    }

    /**
     * @param context the context to set
     */
    public void setContext(ServletContext context) {
        this.context = context;
    }

    /**
     * @return the errorKeyInfoLog
     */
    public String getErrorKeyInfoLog() {
        return errorKeyInfoLog;
    }

    /**
     * @return the errorKeyInfoMail
     */
    public String getErrorKeyInfoMail() {
        return errorKeyInfoMail;
    }

    /**
     * @param errorKeyInfoMail the errorKeyInfoMail to set
     */
    public void setErrorKeyInfoMail(String errorKeyInfoMail) {
        this.errorKeyInfoMail = errorKeyInfoMail;
    }

    /**
     * @param errorKeyInfoLog the errorKeyInfoLog to set
     */
    public void setErrorKeyInfoLog(String errorKeyInfoLog) {
        this.errorKeyInfoLog = errorKeyInfoLog;
    }
}
