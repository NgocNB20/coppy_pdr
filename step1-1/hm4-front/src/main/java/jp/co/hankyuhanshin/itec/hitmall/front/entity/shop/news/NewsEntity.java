/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.news;

import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOpenStatus;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * ニュースクラス
 *
 * @author EntityGenerator
 */
@Data
@Component
@Scope("prototype")
public class NewsEntity implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * ニュースSEQ（必須）
     */
    private Integer newsSeq;

    /**
     * ショップSEQ (FK)（必須）
     */
    private Integer shopSeq;

    /**
     * ニュースタイトル-PC
     */
    private String titlePC;

    /**
     * ニュースタイトル-スマートフォン
     */
    private String titleSP;

    /**
     * ニュースタイトル-携帯
     */
    private String titleMB;

    /**
     * ニュース本文-PC
     */
    private String newsBodyPC;

    /**
     * ニュース本文-スマートフォン
     */
    private String newsBodySP;

    /**
     * ニュース本文-携帯
     */
    private String newsBodyMB;

    /**
     * ニュースURL-PC
     */
    private String newsUrlPC;

    /**
     * ニュースURL-スマートフォン
     */
    private String newsUrlSP;

    /**
     * ニュースURL-携帯
     */
    private String newsUrlMB;

    /**
     * ニュース公開状態PC（必須）
     */
    private HTypeOpenStatus newsOpenStatusPC = HTypeOpenStatus.NO_OPEN;

    /**
     * ニュース公開状態携帯（必須）
     */
    private HTypeOpenStatus newsOpenStatusMB = HTypeOpenStatus.NO_OPEN;

    /**
     * ニュース公開開始日時pc
     */
    private Timestamp newsOpenStartTimePC;

    /**
     * ニュース公開終了日時pc
     */
    private Timestamp newsOpenEndTimePC;

    /**
     * ニュース公開開始日時携帯
     */
    private Timestamp newsOpenStartTimeMB;

    /**
     * ニュース公開終了日時携帯
     */
    private Timestamp newsOpenEndTimeMB;

    /**
     * ニュース日時（必須）
     */
    private Timestamp newsTime;

    /**
     * 登録日時（必須）
     */
    private Timestamp registTime;

    /**
     * 更新日時（必須）
     */
    private Timestamp updateTime;

    /**
     * ニュース詳細PC
     */
    private String newsNotePC;

    /**
     * ニュース詳細スマートフォン
     */
    private String newsNoteSP;

    /**
     * ニュース詳細モバイル
     */
    private String newsNoteMB;

}
