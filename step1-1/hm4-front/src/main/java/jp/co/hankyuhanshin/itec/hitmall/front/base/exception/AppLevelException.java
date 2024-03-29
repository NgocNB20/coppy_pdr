/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.base.exception;

import jp.co.hankyuhanshin.itec.hitmall.front.base.application.AppLevelFacesMessage;
import jp.co.hankyuhanshin.itec.hitmall.front.base.newfaces.FacesException;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.AppLevelFacesMessageUtil;

/**
 * AppLevelException<br/>
 * アプリでの発生するException
 *
 * @author natume
 * @version $Revision: 1.1 $
 */
public class AppLevelException extends FacesException {

    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = 1L;

    /**
     * メッセージ<br/>
     */
    private AppLevelFacesMessage appLevelFacesMessage;

    /**
     * 空のAppLevelExceptionを作成<br/>
     */
    public AppLevelException() {
        super();
    }

    /**
     * 引数 cause からAppLevelExceptionを作成<br/>
     *
     * @param cause 例外
     */
    public AppLevelException(Throwable cause) {
        super(cause);
    }

    /**
     * 引数 messageCode からAppLevelExceptionを作成<br/>
     *
     * @param messageCode メッセージコード
     */
    public AppLevelException(String messageCode) {
        this(messageCode, null);
    }

    /**
     * 引数 messageCode, args からAppLevelExceptionを作成<br/>
     *
     * @param messageCode メッセージコード
     * @param args        メッセージ引数
     */
    public AppLevelException(String messageCode, Object[] args) {
        this(messageCode, args, null);
    }

    /**
     * 引数 messageCode, args, cause からAppLevelExceptionを作成<br/>
     *
     * @param messageCode メッセージコード
     * @param args        メッセージ引数
     * @param cause       例外
     */
    public AppLevelException(String messageCode, Object[] args, Throwable cause) {
        this(messageCode, args, null, cause);
    }

    /**
     * 引数 messageCode, args, cause からAppLevelExceptionを作成<br/>
     *
     * @param messageCode メッセージコード
     * @param args        メッセージ引数
     * @param componentId コンポーネントID
     * @param cause       例外
     */
    public AppLevelException(String messageCode, Object[] args, String componentId, Throwable cause) {
        super(cause);
        this.appLevelFacesMessage = AppLevelFacesMessageUtil.getAllMessage(messageCode, args, componentId);
    }

    /**
     * メッセージの取得<br/>
     * 詳細メッセージがある場合は、詳細メッセージを、<br/>
     * ない場合は、概要メッセージを返す<br/>
     *
     * @return String メッセージ
     */
    @Override
    public String getMessage() {
        StringBuilder message = new StringBuilder();
        if (appLevelFacesMessage != null) {
            //            message.append(appLevelFacesMessage.getMessageCode());
            //            message.append(":");
            if (appLevelFacesMessage.getDetail() != null) {
                message.append(appLevelFacesMessage.getDetail());
            } else {
                message.append(appLevelFacesMessage.getSummary());
            }
        }
        return message.toString();
    }

    /**
     * メッセージコード<br/>
     * このExceptionのメッセージコードを返す<br/>
     *
     * @return String メッセーコード
     */
    public String getMessageCode() {
        return this.appLevelFacesMessage.getMessageCode();
    }

    /**
     * メッセージ引数<br/>
     * このExceptionのメッセージ引数を返す<br/>
     *
     * @return Object[] メッセージ引数
     */
    public Object[] getArgs() {
        return this.appLevelFacesMessage.getArgs();
    }

    /**
     * @return the appLevelFacesMessage
     */
    public AppLevelFacesMessage getAppLevelFacesMessage() {
        return appLevelFacesMessage;
    }

    /**
     * @param appLevelFacesMessage the appLevelFacesMessage to set
     */
    public void setAppLevelFacesMessage(AppLevelFacesMessage appLevelFacesMessage) {
        this.appLevelFacesMessage = appLevelFacesMessage;
    }

}
