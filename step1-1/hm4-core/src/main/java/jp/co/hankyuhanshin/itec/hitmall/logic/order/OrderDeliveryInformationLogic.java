// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetDeliveryInformationRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetDeliveryInformationResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;

import java.util.List;

/**
 * #026 休業日の配送制御<br/>
 *
 * <pre>
 * WEB-API連携 配送情報取得ロジッククラス
 * </pre>
 *
 * @author satoh
 */

public interface OrderDeliveryInformationLogic {

    /**
     * 配送エラー<br/>
     * <code>MSGCD_DELIVERY_ERROR</code>
     */
    public static final String MSGCD_DELIVERY_ERROR = "PDR-0001-001-A-";

    /**
     * WEB-API連携 配送情報取得を行い<br />
     * 結果を返却します。
     *
     * @param orderGoodsEntityList 受注商品エンティティリスト
     * @param reqDto               配送情報取得 リクエストデータ
     * @return 配送情報取得 詳細情報
     */
    WebApiGetDeliveryInformationResponseDetailDto execute(List<OrderGoodsEntity> orderGoodsEntityList,
                                                          WebApiGetDeliveryInformationRequestDto reqDto);

    /**
     * WEB-API連携 配送情報取得を行い<br />
     * 結果を返却します。
     *
     * @param orderGoodsEntityList 受注商品エンティティリスト
     * @param reqDto               配送情報取得 リクエストデータ
     * @param checkMessageDtoList  エラーメッセージ用List
     * @return 配送情報取得 詳細情報
     */
    WebApiGetDeliveryInformationResponseDetailDto execute(List<OrderGoodsEntity> orderGoodsEntityList,
                                                          WebApiGetDeliveryInformationRequestDto reqDto,
                                                          List<CheckMessageDto> checkMessageDtoList);

}
// PDR Migrate Customization to here
