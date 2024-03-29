/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.orderhistory;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * 注文履歴キャンセルDtoクラス
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
// 2023-renew No68 from here
public class CancelOrderHistoryDto implements Serializable {

    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 事業所名 */
    private String officeName;

    /** 注文日時 */
    private Timestamp orderDate;

    /** 受注番号 */
    private String orderNo;

    /** キャンセル日時 */
    private Timestamp cancelTime;

    /** お届け日（整形済文字列）：yyyy/MM/dd(日) or 固定文言「未定」 */
    private String receiveDate;

    /** お届け先事業所名 */
    private String receiveOfficeName;

    /** お届け先郵便番号 */
    private String receiveZipcode;

    /** お届け先住所(都道府県・市区町村) */
    private String receiveAddress1;

    /** お届け先住所(丁目・番地) */
    private String receiveAddress2;

    /** お届け先住所(建物名・部屋番号) */
    private String receiveAddress3;

    /** お届け先住所(方書1) */
    private String receiveAddress4;

    /** お届け先住所(方書2) */
    private String receiveAddress5;

    /** 支払方法 */
    private String paymentType;

    /** 適用クーポン番号 */
    private String couponNo;

    /** 適用クーポン名 */
    private String couponName;

    /** お支払い合計(税込) */
    private String paymentTotalPrice;

    /** キャンセル対象受注商品リスト */
    private List<CancelOrderHistoryGoodsDto> goodsList;

}
// 2023-renew No68 to here
