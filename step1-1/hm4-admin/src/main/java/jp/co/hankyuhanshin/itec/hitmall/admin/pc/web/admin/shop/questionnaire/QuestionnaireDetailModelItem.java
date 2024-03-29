/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.questionnaire;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReplyRequiredFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReplyType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReplyValidatePattern;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * アンケート詳細モデルイテム<br />
 *
 * @author matsuda
 */
@Data
@Component
@Scope("prototype")
public class QuestionnaireDetailModelItem implements Serializable {

    /**
     * シリアルバージョンUID
     */
    private static final long serialVersionUID = 1L;

    /*
     * 画面項目（設問設定一覧）データ項目
     */
    /**
     * 設問設定一覧データ：No
     */
    private Integer dspNo;

    /**
     * 設問設定一覧データ：表示順
     */
    private Integer orderDisplay;

    /**
     * 設問設定一覧データ：設問内容
     */
    private String question;

    /**
     * 設問設定一覧データ：公開状態
     */
    private HTypeOpenDeleteStatus openStatus;

    /**
     * 設問設定一覧データ：必須
     */
    private HTypeReplyRequiredFlag replyRequiredFlag;

    /**
     * 設問設定一覧データ：形式
     */
    private HTypeReplyType replyType;

    /**
     * 設問設定一覧データ：文字種
     */
    private HTypeReplyValidatePattern replyValidatePattern;

    /**
     * 設問設定一覧データ：桁数
     */
    private Integer replyMaxLength;

    /**
     * 設問設定一覧データ：選択肢
     */
    private String choices;

    /**
     * 設問設定一覧データ：選択肢リスト（画面表示用）
     */
    private String[] choicesDispItems;

    /*
     * 画面項目以外
     */
    /**
     * 設問設定一覧データ：アンケート設問SEQ
     */
    private Integer questionSeq;

}
