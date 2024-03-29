/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.exception;

import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelException;
import jp.co.hankyuhanshin.itec.hmbase.newfaces.FacesMessage;
import jp.co.hankyuhanshin.itec.hmbase.util.AppLevelFacesMessageUtil;

/**
 * ControllerException<br/>
 * Controllerで発生する例外クラス<br/>
 *
 * @author kaneda
 */
public class ControllerException extends AppLevelException {

    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 空のControllerExceptionを作成<br/>
     */
    public ControllerException() {
        super();
    }

    /**
     * FacesMessage.SEVERITY_ERRORと引数messageCodeからControllerExceptionを作成<br/>
     *
     * @param messageCode メッセージコード
     */
    public ControllerException(String messageCode) {
        this(FacesMessage.SEVERITY_ERROR, messageCode, null);
    }

    /**
     * 引数 severity, messageCode, args からControllerExceptionを作成<br/>
     *
     * @param severity    エラーレベル
     * @param messageCode メッセージコード
     * @param args        メッセージ引数
     */
    public ControllerException(FacesMessage.Severity severity, String messageCode, Object[] args) {
        this(severity, messageCode, args, null, null);
    }

    /**
     * 引数 severity, messageCode, args, cause からControllerExceptionを作成<br/>
     *
     * @param severity    エラーレベル
     * @param messageCode メッセージコード
     * @param args        メッセージ引数
     * @param componentId コンポーネントID
     * @param cause       例外
     */
    public ControllerException(FacesMessage.Severity severity,
                               String messageCode,
                               Object[] args,
                               String componentId,
                               Throwable cause) {
        super(cause);
        this.setAppLevelFacesMessage(AppLevelFacesMessageUtil.getAllMessage(messageCode, args, componentId));
    }

}
