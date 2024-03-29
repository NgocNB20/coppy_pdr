/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.entity.access;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAccessType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * アクセス情報クラス
 *
 * @author EntityGenerator
 * @version $Revision: 1.2 $
 */
@Entity
@Table(name = "AccessInfo")
@Data
@Component
@Scope("prototype")
public class AccessInfoEntity implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** ショップSEQ（必須） */
    @Column(name = "shopSeq")
    @Id
    private Integer shopSeq;

    /** アクセス種別（必須） */
    @Column(name = "accessType")
    @Id
    private HTypeAccessType accessType;

    /** アクセス日（必須） */
    @Column(name = "accessDate")
    @Id
    private Timestamp accessDate;

    /** サイト種別（必須） */
    @Column(name = "siteType")
    @Id
    private HTypeSiteType siteType = HTypeSiteType.FRONT_PC;

    /** デバイス種別（必須） */
    @Column(name = "deviceType")
    @Id
    private HTypeDeviceType deviceType = HTypeDeviceType.PC;

    /** 商品グループSEQ（必須） */
    @Column(name = "goodsGroupSeq")
    @Id
    private Integer goodsGroupSeq;

    /** キャンペーンコード（必須） */
    @Column(name = "campaignCode")
    @Id
    private String campaignCode;

    /** 端末識別情報（必須） */
    @Column(name = "accessUid")
    @Id
    private String accessUid;

    /** アクセス数（必須） */
    @Column(name = "accessCount")
    private Integer accessCount = new Integer(0);

    /** 登録日時（必須） */
    @Column(name = "registTime")
    private Timestamp registTime;

    /** 更新日時（必須） */
    @Column(name = "updateTime")
    private Timestamp updateTime;

}
