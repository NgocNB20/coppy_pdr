// PDR Migrate Customization from here

/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */
package jp.co.hankyuhanshin.itec.hitmall.service.json.category;

import javax.servlet.http.HttpServletResponse;

/**
 * カテゴリーデータJSON出力サービスインターフェース<br/>
 *
 * @author kato
 */
public interface CategoryDataJsonOutputService {

    /**
     * カテゴリーデータJSON出力<br/>
     *
     * @param sl       開始カテゴリー階層（検索パラメータ）
     * @param el       終了カテゴリー階層（検索パラメータ）
     * @param response レスポンス
     */
    void execute(String sl, String el, HttpServletResponse response);

}

// PDR Migrate Customization to here
