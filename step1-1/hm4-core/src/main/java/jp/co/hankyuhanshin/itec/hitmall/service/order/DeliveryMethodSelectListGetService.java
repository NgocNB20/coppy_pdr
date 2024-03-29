/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryDto;

import java.util.List;

/**
 * 配送方法選択可能リスト取得サービス
 *
 * @author negishi
 * @author Kaneko(Itec) 2012/01/31 チケット#2737対応
 */
public interface DeliveryMethodSelectListGetService {

    /**
     * 商品の利用可能配送方法の区切り文字
     */
    public static final String SEPARATOR = "/";

    /**
     * サービス実行<br/>
     * バックで使用
     *
     * @param receiveOrderDto 注文DTO
     * @param available       利用可能、不可能どちらかを指定。null..全部 / true..利用可能のみ / false..利用不可能のみ
     * @return 配送DTOリスト
     */
    List<DeliveryDto> execute(ReceiveOrderDto receiveOrderDto, Boolean available, HTypeSiteType siteType);

    /**
     * サービス実行<br/>
     * バックで使用。
     *
     * @param receiveOrderDto 受注DTO
     * @return 受注DTOリスト
     */
    List<DeliveryDto> execute(ReceiveOrderDto receiveOrderDto);

}
