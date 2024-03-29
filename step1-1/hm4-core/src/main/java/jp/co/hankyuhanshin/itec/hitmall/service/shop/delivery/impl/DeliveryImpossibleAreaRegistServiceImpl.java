/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.impl;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryImpossibleAreaEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryImpossibleAreaGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryImpossibleAreaRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.zipcode.ZipCodeAddressGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.DeliveryImpossibleAreaRegistService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 配送不可能エリア登録Serviceインターフェース
 *
 * @author $author$
 * @version $Revision: 1.1 $
 */
@Service
public class DeliveryImpossibleAreaRegistServiceImpl extends AbstractShopService
                implements DeliveryImpossibleAreaRegistService {

    /**
     * 配送不可能エリア登録Logic
     */
    private final DeliveryImpossibleAreaRegistLogic deliveryImpossibleAreaRegistLogic;

    /**
     * 配送不可能エリアエンティティ取得Logic
     */
    private final DeliveryImpossibleAreaGetLogic deliveryImpossibleAreaGetLogic;

    /**
     * 郵便番号住所情報取得Logic
     */
    private final ZipCodeAddressGetLogic zipCodeAddressGetLogic;

    @Autowired
    public DeliveryImpossibleAreaRegistServiceImpl(DeliveryImpossibleAreaRegistLogic deliveryImpossibleAreaRegistLogic,
                                                   DeliveryImpossibleAreaGetLogic deliveryImpossibleAreaGetLogic,
                                                   ZipCodeAddressGetLogic zipCodeAddressGetLogic) {
        this.deliveryImpossibleAreaRegistLogic = deliveryImpossibleAreaRegistLogic;
        this.deliveryImpossibleAreaGetLogic = deliveryImpossibleAreaGetLogic;
        this.zipCodeAddressGetLogic = zipCodeAddressGetLogic;
    }

    /**
     * 配送特別料金エリア登録処理を行います
     *
     * @param entity DeliveryImpossibleAreaEntity
     * @return int 処理結果
     */
    @Override
    public int execute(DeliveryImpossibleAreaEntity entity) {
        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("entity", entity);
        ArgumentCheckUtil.assertNotNull("deliveryMethodSeq", entity.getDeliveryMethodSeq());
        ArgumentCheckUtil.assertNotNull("zipCode", entity.getZipCode());

        /** 存在チェック */
        // 郵便番号
        zipCodeAddressGetLogic.execute(entity.getZipCode());

        // 配送特別料金エリア
        DeliveryImpossibleAreaEntity resultEntity =
                        deliveryImpossibleAreaGetLogic.execute(entity.getDeliveryMethodSeq(), entity.getZipCode());

        if (resultEntity != null) {
            throwMessage("SSD001001");
        }

        return deliveryImpossibleAreaRegistLogic.execute(entity);
    }
}
