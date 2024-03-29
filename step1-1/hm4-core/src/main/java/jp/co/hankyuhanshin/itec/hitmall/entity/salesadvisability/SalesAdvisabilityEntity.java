/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.entity.salesadvisability;

import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Table;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * PDR#437 【1.7次】基幹リニューアル対応　【要望No.24】　販売可否条件の変更<br/>
 *
 * <pre>
 * 販売可否判定エンティティ
 * </pre>
 *
 * @author k.uehara
 */
@Entity
@Table(name = "MemberInfo")
@Data
@Component
@Scope("prototype")
public class SalesAdvisabilityEntity implements Serializable {
    // PDR Migrate Customization from here
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** 販売可否判定SEQ */
    @Column(name = "salesAdvisabilitySeq")
    private Integer salesAdvisabilitySeq;

    /** ログイン状態 */
    @Column(name = "loginType")
    private String loginType;

    /** 歯科専売可否フラグ */
    @Column(name = "dentalMonopolySalesFlg")
    private String dentalMonopolySalesFlg;

    /** 顧客区分 */
    @Column(name = "businessType")
    private String businessType;

    /** 確認書類 */
    @Column(name = "confDocumentType")
    private String confDocumentType;

    /** 薬品区分 */
    @Column(name = "goodsClassType")
    private String goodsClassType;

    /** 販売可能商品区分 */
    @Column(name = "salableGoodsType")
    private String salableGoodsType;
    // PDR Migrate Customization to here
}
