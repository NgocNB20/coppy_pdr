/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hmbase.service;

import jp.co.hankyuhanshin.itec.hmbase.application.AppLevelFacesMessage;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelException;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.exception.ServiceException;
import jp.co.hankyuhanshin.itec.hmbase.newfaces.FacesMessage;
import jp.co.hankyuhanshin.itec.hmbase.util.AppLevelFacesMessageUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * Service基底クラス<br/>
 *
 * @author natume
 * @author Nishigaki (Itec) 2011/02/07 #2642 対応
 */
public class AbstractService {

    /**
     * エラー保持リスト
     */
    private List<AppLevelException> errorList;

    /**
     * Warning Message List
     */
    public List<AppLevelFacesMessage> warnMessageList;

    /**
     * getComponent()<br/>
     *
     * @param <T>          コンポーネントの型
     * @param componentKey コンポーネントクラス
     * @return コンポーネント
     */
    protected <T> T getComponent(final Class<T> componentKey) {
        return ApplicationContextUtility.getBean(componentKey);
    }

    /**
     * 例外をスロー
     *
     * @param messageCode メッセージコード
     * @throws AppLevelListException スロー例外
     */
    protected void throwMessage(String messageCode) throws AppLevelListException {
        throwMessage(messageCode, null, null);
    }

    /**
     * 例外をスロー
     *
     * @param messageCode メッセージコード
     * @param args        メッセージ引数
     * @throws AppLevelListException スロー例外
     */
    protected void throwMessage(String messageCode, Object[] args) throws AppLevelListException {
        throwMessage(messageCode, args, null);
    }

    /**
     * 例外をスロー
     *
     * @param messageCode メッセージコード
     * @param args        メッセージ引数
     * @param cause       例外
     * @throws AppLevelListException スロー例外
     */
    protected void throwMessage(String messageCode, Object[] args, Throwable cause) throws AppLevelListException {
        ServiceException serviceException =
                        createServiceException(FacesMessage.SEVERITY_ERROR, messageCode, args, cause);
        addErrorMessage(serviceException);
        throwMessage();
    }

    /**
     * エラーリストを追加<br/>
     *
     * @param appLevelListException AppLevelListException
     */
    protected void addErrorMessage(AppLevelListException appLevelListException) {
        if (appLevelListException == null || appLevelListException.getErrorList().isEmpty()) {
            return;
        }
        if (errorList == null) {
            errorList = new ArrayList<>();
        }
        errorList.addAll(appLevelListException.getErrorList());
    }

    /**
     * ServiceExceptionを作成し、errorListに追加
     *
     * @param serviceException サービス例外
     */
    protected void addErrorMessage(ServiceException serviceException) {
        if (errorList == null) {
            errorList = new ArrayList<>();
        }
        errorList.add(serviceException);
    }

    /**
     * ServiceExceptionを作成し、errorListに追加
     *
     * @param messageCode メッセージコード
     */
    protected void addErrorMessage(String messageCode) {
        addErrorMessage(messageCode, null, null);
    }

    /**
     * ServiceExceptionを作成し、errorListに追加
     *
     * @param messageCode メッセージコード
     * @param args        メッセージ引数
     */
    protected void addErrorMessage(String messageCode, Object[] args) {
        addErrorMessage(messageCode, args, null);
    }

    /**
     * ServiceExceptionを作成し、errorListに追加
     * メソッドの説明・概要<br/>
     *
     * @param messageCode メッセージコード
     * @param args        メッセージ引数
     * @param cause       エラー例外
     */
    protected void addErrorMessage(String messageCode, Object[] args, Throwable cause) {
        addErrorMessage(createServiceException(FacesMessage.SEVERITY_ERROR, messageCode, args, cause));
    }

    /**
     * エラーリストがある場合に例外をスロー
     *
     * @throws AppLevelListException メッセージがある場合にスロー
     * @throws RuntimeException      メッセージがない場合にスロー
     */
    protected void throwMessage() throws AppLevelListException, RuntimeException {

        if (!hasErrorMessage()) {
            throw new RuntimeException("エラーメッセージはありません");
        }
        throw new AppLevelListException(errorList);
    }

    /**
     * エラーリストの有無判定
     *
     * @return エラー 有=true 無=false
     */
    protected boolean hasErrorMessage() {
        return errorList != null && !errorList.isEmpty();
    }

    /**
     * ServiceException作成<br/>
     *
     * @param severity    Severity
     * @param messageCode メッセージコード
     * @param args        メッセージ引数
     * @param cause       エラー例外
     * @return サービス例外
     */
    protected ServiceException createServiceException(FacesMessage.Severity severity,
                                                      String messageCode,
                                                      Object[] args,
                                                      Throwable cause) {
        return new ServiceException(severity, messageCode, args, cause);
    }

    /**
     * エラーリストをクリアする<br/>
     */
    public void clearErrorList() {
        if (errorList != null) {
            errorList.clear();
        }
    }

    /**
     * エラーリストを取得する<br/>
     *
     * @return エラーリスト
     */
    public List<AppLevelException> getErrorList() {
        return errorList;
    }

    /**
     * Add Warn message<br/>
     *
     * @param messageCode message Code
     * @param args        arguments for message
     */
    public void addWarnMessage(String messageCode, Object[] args) {
        AppLevelFacesMessage appLevelFacesMessage = AppLevelFacesMessageUtil.getAllMessage(messageCode, args, null);
        if (warnMessageList == null) {
            warnMessageList = new ArrayList<>();
        }
        warnMessageList.add(appLevelFacesMessage);
    }

    /**
     * method to check if message is present in messageList <br/>
     *
     * @return true if message present else false
     */
    public boolean hasWarnMessageList() {
        return warnMessageList != null && !warnMessageList.isEmpty();
    }
}
