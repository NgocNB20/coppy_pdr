/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hmbase.exception;

import jp.co.hankyuhanshin.itec.hmbase.newfaces.FacesMessage;
import jp.co.hankyuhanshin.itec.hmbase.util.AppLevelFacesMessageUtil;

/**
 * LogicException<br/>
 * Logicで発生する例外クラス<br/>
 *
 * @author natume
 * @version $Revision: 1.1 $
 */
public class LogicException extends AppLevelException {

    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 空のLogicExceptionを作成する<br/>
     */
    public LogicException() {
        super();
    }

    /**
     * コンストラクタ概要<br/>
     * コンストラクタの説明・概要<br/>
     *
     * @param messageCode メッセージコード
     */
    public LogicException(String messageCode) {
        this(FacesMessage.SEVERITY_ERROR, messageCode, null, null);
    }

    /**
     * コンストラクタ概要<br/>
     * コンストラクタの説明・概要<br/>
     *
     * @param severity    エラーレベル
     * @param messageCode メッセージコード
     * @param args        メッセージ引数
     */
    public LogicException(FacesMessage.Severity severity, String messageCode, Object[] args) {
        this(severity, messageCode, args, null);
    }

    /**
     * コンストラクタ概要<br/>
     * コンストラクタの説明・概要<br/>
     *
     * @param severity    エラーレベル
     * @param messageCode メッセージコード
     * @param args        メッセージ引数
     * @param cause       例外
     */
    public LogicException(FacesMessage.Severity severity, String messageCode, Object[] args, Throwable cause) {
        super(cause);
        super.setAppLevelFacesMessage(AppLevelFacesMessageUtil.getAllMessage(messageCode, args));
    }

}
