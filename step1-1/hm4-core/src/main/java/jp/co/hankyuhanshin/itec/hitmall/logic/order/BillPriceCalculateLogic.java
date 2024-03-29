/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;

import java.util.List;

/**
 * 請求金額算出ロジック<br/>
 *
 * @author yamaguchi
 * @version $Revision: 1.3 $
 */
public interface BillPriceCalculateLogic {

    /**
     * 配送方法該当なし
     */
    public static final String MSGCD_GETDELIVERY_NULL = "LOO003501";

    /**
     * 決済方法該当なし
     */
    public static final String MSGCD_GETSETTOLEMENT_NULL = "LOO003502";

    /**
     * 配送方法選択不可能エラー
     */
    public static final String MSGCD_GETDELIVERY_ERROR = "LOO003503";

    /**
     * 決済方法選択不可能エラー
     */
    public static final String MSGCD_GETSETTOLEMENT_ERROR = "LOO003504";

    /**
     * 都道府県未入力エラー
     */
    public static final String MSGCD_PREFECTURE_ERROR = "LOO003505";

    /**
     * クーポンのエラーコードのプリフィックス
     */
    public static final String COUPON_ERROR_CODE_PREFIX = "LOD";

    /**
     * 実行メソッド<br/>
     *
     * @param receiveOrderDto 受注DTO
     * @return チェックメッセージDTO（エラー時のみ）
     */
    List<CheckMessageDto> execute(ReceiveOrderDto receiveOrderDto, HTypeSiteType siteType, String adminId);

    /**
     * 実行メソッド<br/>
     *
     * @param receiveOrderDto  受注DTO
     * @param carriageCalcFlag 送料適用フラグ。true..計算前の送料を適用する
     * @return チェックメッセージDTO（エラー時のみ）
     */
    List<CheckMessageDto> execute(ReceiveOrderDto receiveOrderDto,
                                  boolean carriageCalcFlag,
                                  HTypeSiteType siteType,
                                  String adminId);

    /**
     * 実行メソッド<br/>
     *
     * @param receiveOrderDto    受注DTO
     * @param commissionCalcFlag 決済手数料適用フラグ。true..計算前の手数料を適用する
     * @param carriageCalcFlag   送料適用フラグ。true..計算前の送料を適用する
     * @return チェックメッセージDTO（エラー時のみ）
     */
    List<CheckMessageDto> execute(ReceiveOrderDto receiveOrderDto,
                                  boolean commissionCalcFlag,
                                  boolean carriageCalcFlag,
                                  HTypeSiteType siteType,
                                  String adminId);

}
