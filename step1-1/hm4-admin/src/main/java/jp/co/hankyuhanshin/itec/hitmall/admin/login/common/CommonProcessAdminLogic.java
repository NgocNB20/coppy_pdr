/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.login.common;

import jp.co.hankyuhanshin.itec.hitmall.admin.application.commoninfo.CommonInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 共通処理：バック<br/>
 *
 * @author natume
 * @version $Revision: 1.2 $
 */
public interface CommonProcessAdminLogic {

    /**
     * 共通情報取得処理<br/>
     *
     * @param request  リクエスト
     * @param response レスポンス
     * @return 共通情報
     * @throws IOException 例外
     */
    CommonInfo execute(HttpServletRequest request, HttpServletResponse response);
}
