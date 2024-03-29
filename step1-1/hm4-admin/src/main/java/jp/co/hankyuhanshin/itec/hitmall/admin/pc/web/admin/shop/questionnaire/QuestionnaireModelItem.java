/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.questionnaire;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteMapFlag;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 受注一覧検索結果画面情報<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class QuestionnaireModelItem implements Serializable {

    /**
     * シリアルバージョンUID
     */
    private static final long serialVersionUID = 1L;

    /*
     * 画面項目（検索結果一覧）データ項目
     */
    /**
     * No
     */
    private Integer resultNo;

    /**
     * アンケートSEQ
     */
    private Integer questionnaireSeq;

    /**
     * アンケートキー
     */
    private String questionnaireKey;

    /**
     * アンケート名称
     */
    private String name;

    /**
     * アンケート公開状態PC
     */
    private String openStatusPc;

    /**
     * アンケート公開開始日時
     */
    private Timestamp openStartTime;

    /**
     * アンケート公開終了日時
     */
    private Timestamp openEndTime;

    /**
     * 回答数
     */
    private Integer replyCount;

    /**
     * サイトマップ出力
     */
    // PDR Migrate Customization from here
    private HTypeSiteMapFlag siteMapFlag;
    // PDR Migrate Customization to here

    /**
     * アンケート表示状態
     */
    private boolean displayStatus;

    /**
     * @return true = 未集計
     */
    public boolean isEmptyReplyCount() {
        return replyCount == null;
    }

}
