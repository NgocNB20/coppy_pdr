/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 会員詳細画面注文履歴情報<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class MemberInfoDetailsOrderItem implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * No
     */
    public Integer resultNo;

    /**
     * 受注履歴番号
     */
    public Integer orderVersionNo;

    /**
     * 受注コード
     */
    public String orderCode;

    /**
     * 受注日時
     */
    public Timestamp orderTime;

    /**
     * 受注金額
     */
    public BigDecimal orderPrice;

    /**
     * 決済方法名
     */
    public String settlementMethodName;

    /**
     * 受注状態
     */
    public String orderStatus;

    /**
     * 受注サイト
     */
    public HTypeSiteType orderSiteType;
}
