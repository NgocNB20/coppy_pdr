/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementDto;

import java.util.List;

/**
 * 決済方法選択可能リスト取得サービス
 *
 * @author negishi
 * @author Kaneko(Itec) 2012/01/31 チケット#2737対応
 */
public interface SettlementMethodSelectListGetService {

    /**
     * 商品の利用可能配送方法の区切り文字
     */
    public static final String SEPARATOR = "/";

    /**
     * エラーコード。決済方法がなかった場合<br />
     */
    public static final String MSGCD_NO_SETTLEMENT_METHOD = "LOX000207";

    /**
     * 実行メソッド<br/>
     * フロントで使用
     *
     * @param receiveOrderDto 受注DTO
     * @param available       利用可能、不可能どちらかを指定。null..全部 / true..利用可能のみ / false..利用不可能のみ
     * @param allowCreditFlag クレジット決済許可フラグ　true:クレジット決済許可 false:クレジット決済禁止
     * @return 決済Dtoリスト
     */
    List<SettlementDto> execute(ReceiveOrderDto receiveOrderDto,
                                Boolean available,
                                Boolean allowCreditFlag,
                                String memberInfoId,
                                HTypeSiteType siteType);

    /**
     * 実行メソッド<br/>
     * バックで使用
     *
     * @param receiveOrderDto 受注DTO
     * @param allowCreditFlag クレジット決済許可フラグ　true:クレジット決済許可 false:クレジット決済禁止
     * @return 決済Dtoリスト
     */
    List<SettlementDto> execute(ReceiveOrderDto receiveOrderDto,
                                Boolean allowCreditFlag,
                                String memberInfoId,
                                HTypeSiteType siteType);
}
