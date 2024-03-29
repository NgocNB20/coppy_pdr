/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * PDR#015 12_WebAPI
 *
 * <pre>
 * WEB-API連携取得結果DTOクラス
 * 注文履歴取得 共通詳細情報
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class WebApiShipmentOrderHistoryResponseBaseDetailDto implements Serializable {
    // PDR Migrate Customization from here
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 受注番号 */
    private Integer orderNo;

    /** 受付方法 */
    private String receptionTypeName;

    /** 注文日時 */
    private Timestamp orderDate;

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

    /** お届け日 */
    private Timestamp receiveDate;

    /** 支払方法 */
    private String paymentType;

    /** お支払い合計(税込) */
    private String paymentTotalPrice;

    // 2023-renew No24 from here
    /** 適用クーポン番号 */
    private String couponNo;

    /** 適用クーポン名 */
    private String couponName;
    // 2023-renew No24 to here

    // 2023-renew No68 from here
    /** キャンセル可否フラグ */
    private Integer cancelYesNo;
    // 2023-renew No68 to here

    /** 商品情報 */
    private List<WebApiOrderHistoryResponseGoodsInfoDto> goodsList;

    // Web-API項目外

    /** 商品詳細情報Map */
    private Map<String, GoodsDetailsDto> goodsDetailsMap;
    // PDR Migrate Customization to here
}
