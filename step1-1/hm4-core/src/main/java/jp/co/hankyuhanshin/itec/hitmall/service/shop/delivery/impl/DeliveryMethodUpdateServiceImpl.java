/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeliveryMethodType;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryMethodDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodTypeCarriageEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryMethodTypeCarriageDeleteLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryMethodUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.DeliveryMethodDataCheckService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.DeliveryMethodTypeCarriageRegistService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.DeliveryMethodUpdateService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 配送方法更新サービス実装クラス
 *
 * @author negishi
 * @version $Revision: 1.1 $
 */
@Service
public class DeliveryMethodUpdateServiceImpl extends AbstractShopService implements DeliveryMethodUpdateService {

    /**
     * 配送方法データチェックサービス
     */
    private final DeliveryMethodDataCheckService deliveryMethodDataCheckService;

    /**
     * 配送方法更新ロジック
     */
    private final DeliveryMethodUpdateLogic deliveryMethodUpdateLogic;

    /**
     * 配送区分別送料削除ロジック
     */
    private final DeliveryMethodTypeCarriageDeleteLogic deliveryMethodTypeCarriageDeleteLogic;

    /**
     * 配送区分別送料登録サービス
     */
    private final DeliveryMethodTypeCarriageRegistService deliveryMethodTypeCarriageRegistService;

    @Autowired
    public DeliveryMethodUpdateServiceImpl(DeliveryMethodDataCheckService deliveryMethodDataCheckService,
                                           DeliveryMethodUpdateLogic deliveryMethodUpdateLogic,
                                           DeliveryMethodTypeCarriageDeleteLogic deliveryMethodTypeCarriageDeleteLogic,
                                           DeliveryMethodTypeCarriageRegistService deliveryMethodTypeCarriageRegistService) {
        this.deliveryMethodDataCheckService = deliveryMethodDataCheckService;
        this.deliveryMethodUpdateLogic = deliveryMethodUpdateLogic;
        this.deliveryMethodTypeCarriageDeleteLogic = deliveryMethodTypeCarriageDeleteLogic;
        this.deliveryMethodTypeCarriageRegistService = deliveryMethodTypeCarriageRegistService;
    }

    /**
     * サービス実行
     *
     * @param deliveryMethodEntity 配送方法エンティティ
     * @return 更新件数
     */
    @Override
    public int execute(DeliveryMethodEntity deliveryMethodEntity) {
        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("deliveryMethodEntity", deliveryMethodEntity);

        // 配送方法更新ロジック実行
        return deliveryMethodUpdateLogic.execute(deliveryMethodEntity);
    }

    /**
     * サービス実行
     *
     * @param deliveryMethodEntityList 配送方法エンティティリスト
     * @return 更新件数
     */
    @Override
    public int execute(List<DeliveryMethodEntity> deliveryMethodEntityList) {
        // パラメータチェック
        ArgumentCheckUtil.assertNotEmpty("deliveryMethodEntityList", deliveryMethodEntityList);

        // 更新件数
        int count = 0;
        for (DeliveryMethodEntity deliveryMethodEntity : deliveryMethodEntityList) {
            // 更新処理実行
            count += execute(deliveryMethodEntity);
        }

        return count;
    }

    /**
     * サービス実行
     *
     * @param deliveryMethodDetailsDto 配送方法詳細DTO
     * @return 更新件数
     */
    @Override
    public int execute(DeliveryMethodDetailsDto deliveryMethodDetailsDto) {
        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("deliveryMethodDetailsDto", deliveryMethodDetailsDto);
        ArgumentCheckUtil.assertNotNull("deliveryMethodEntity", deliveryMethodDetailsDto.getDeliveryMethodEntity());
        ArgumentCheckUtil.assertNotNull(
                        "deliveryMethodSeq", deliveryMethodDetailsDto.getDeliveryMethodEntity().getDeliveryMethodSeq());

        // 配送方法エンティティ取得
        DeliveryMethodEntity deliveryMethodEntity =
                        CopyUtil.deepCopy(deliveryMethodDetailsDto.getDeliveryMethodEntity());

        // 配送方法データチェックサービス実行
        deliveryMethodDataCheckService.execute(deliveryMethodEntity);

        // 配送方法更新ロジック実行
        int count = deliveryMethodUpdateLogic.execute(deliveryMethodEntity);

        // 全国一律以外の場合
        if (!deliveryMethodEntity.getDeliveryMethodType().equals(HTypeDeliveryMethodType.FLAT)) {
            // 配送区分別送料エンティティリスト取得
            List<DeliveryMethodTypeCarriageEntity> deliveryMethodTypeCarriageEntityList =
                            deliveryMethodDetailsDto.getDeliveryMethodTypeCarriageEntityList();
            // 配送方法SEQ取得
            Integer deliveryMethodSeq = deliveryMethodEntity.getDeliveryMethodSeq();

            // 配送区分別送料削除ロジック実行
            deliveryMethodTypeCarriageDeleteLogic.execute(deliveryMethodSeq);

            // 配送方法SEQ設定
            for (DeliveryMethodTypeCarriageEntity deliveryMethodTypeCarriageEntity : deliveryMethodTypeCarriageEntityList) {
                deliveryMethodTypeCarriageEntity.setDeliveryMethodSeq(deliveryMethodSeq);
            }
            // 配送区分別送料登録ロジック実行
            deliveryMethodTypeCarriageRegistService.execute(deliveryMethodTypeCarriageEntityList);
        }

        return count;
    }
}
