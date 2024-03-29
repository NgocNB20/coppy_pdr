/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.entity.shop.news;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.SequenceGenerator;
import org.seasar.doma.Table;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * ニュースクラス
 *
 * @author EntityGenerator
 */
@Entity
@Table(name = "News")
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
    @Column(name = "newsSeq")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequence = "newsSeq")
    private Integer newsSeq;

    /**
     * ショップSEQ (FK)（必須）
     */
    @Column(name = "shopSeq")
    private Integer shopSeq;

    /**
     * ニュースタイトル-PC
     */
    @Column(name = "titlePC")
    private String titlePC;

    /**
     * ニュースタイトル-スマートフォン
     */
    @Column(name = "titleSP")
    private String titleSP;

    /**
     * ニュースタイトル-携帯
     */
    @Column(name = "titleMB")
    private String titleMB;

    /**
     * ニュース本文-PC
     */
    @Column(name = "newsBodyPC")
    private String newsBodyPC;

    /**
     * ニュース本文-スマートフォン
     */
    @Column(name = "newsBodySP")
    private String newsBodySP;

    /**
     * ニュース本文-携帯
     */
    @Column(name = "newsBodyMB")
    private String newsBodyMB;

    /**
     * ニュースURL-PC
     */
    @Column(name = "newsUrlPC")
    private String newsUrlPC;

    /**
     * ニュースURL-スマートフォン
     */
    @Column(name = "newsUrlSP")
    private String newsUrlSP;

    /**
     * ニュースURL-携帯
     */
    @Column(name = "newsUrlMB")
    private String newsUrlMB;

    /**
     * ニュース公開状態PC（必須）
     */
    @Column(name = "newsOpenStatusPC")
    private HTypeOpenStatus newsOpenStatusPC = HTypeOpenStatus.NO_OPEN;

    /**
     * ニュース公開状態携帯（必須）
     */
    @Column(name = "newsOpenStatusMB")
    private HTypeOpenStatus newsOpenStatusMB = HTypeOpenStatus.NO_OPEN;

    /**
     * ニュース公開開始日時pc
     */
    @Column(name = "newsOpenStartTimePC")
    private Timestamp newsOpenStartTimePC;

    /**
     * ニュース公開終了日時pc
     */
    @Column(name = "newsOpenEndTimePC")
    private Timestamp newsOpenEndTimePC;

    /**
     * ニュース公開開始日時携帯
     */
    @Column(name = "newsOpenStartTimeMB")
    private Timestamp newsOpenStartTimeMB;

    /**
     * ニュース公開終了日時携帯
     */
    @Column(name = "newsOpenEndTimeMB")
    private Timestamp newsOpenEndTimeMB;

    /**
     * ニュース日時（必須）
     */
    @Column(name = "newsTime")
    private Timestamp newsTime;

    /**
     * 登録日時（必須）
     */
    @Column(name = "registTime", updatable = false)
    private Timestamp registTime;

    /**
     * 更新日時（必須）
     */
    @Column(name = "updateTime")
    private Timestamp updateTime;

    /**
     * ニュース詳細PC
     */
    @Column(name = "newsNotePC")
    private String newsNotePC;

    /**
     * ニュース詳細スマートフォン
     */
    @Column(name = "newsNoteSP")
    private String newsNoteSP;

    /**
     * ニュース詳細モバイル
     */
    @Column(name = "newsNoteMB")
    private String newsNoteMB;

}
