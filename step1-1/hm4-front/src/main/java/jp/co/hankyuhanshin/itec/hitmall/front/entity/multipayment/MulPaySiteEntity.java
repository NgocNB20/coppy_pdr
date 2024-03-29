/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.entity.multipayment;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * マルチペイメント用サイト設定
 *
 * @author EntityGenerator
 */
@Data
@Component
@Scope("prototype")
public class MulPaySiteEntity implements Serializable {

    /**
     * シリアル
     */
    private static final long serialVersionUID = -899850354566448635L;

    /**
     * マルチペイメント接続用サイトID
     */
    private String siteId;

    /**
     * マルチペイメント接続用サイトパスワード
     */
    private String sitePassword;

    /**
     * マルチペイメント提供サイト管理画面URL ※hitmall では直接使用しない
     */
    private String siteAccessUrl;

}
