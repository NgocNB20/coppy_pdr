/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 配送方法設定画面DXO
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class DeliveryHelper {

    /**
     * ページへの変換処理
     *
     * @param deliveryMethodEntityList 配送方法エンティティリスト
     * @param deliveryModel            配送方法設定画面ページ
     */
    public void toPageForLoad(List<DeliveryMethodEntity> deliveryMethodEntityList, DeliveryModel deliveryModel) {
        List<DeliveryResultItem> deliveryMethodItems = new ArrayList<>();
        DeliveryResultItem deliveryResultItem = null;
        // 配送方法エンティティリスト分ループ
        Integer orderDisplay = 1;
        for (DeliveryMethodEntity deliveryMethodEntity : deliveryMethodEntityList) {
            // 表示アイテムクラスを作成
            deliveryResultItem = ApplicationContextUtility.getBean(DeliveryResultItem.class);

            deliveryResultItem.setDeliveryMethodEntity(deliveryMethodEntity);
            deliveryResultItem.setDeliveryMethodRadioValue(deliveryMethodEntity.getDeliveryMethodSeq());
            // deliveryResultItem.setOrderDisplay(deliveryMethodEntity.getOrderDisplay());
            deliveryResultItem.setOrderDisplay(orderDisplay);
            deliveryResultItem.setDeliveryMethodSeq(deliveryMethodEntity.getDeliveryMethodSeq());
            deliveryResultItem.setDeliveryMethodName(deliveryMethodEntity.getDeliveryMethodName());
            deliveryResultItem.setOpenStatusPC(deliveryMethodEntity.getOpenStatusPC());
            deliveryResultItem.setOpenStatusMB(deliveryMethodEntity.getOpenStatusMB());
            deliveryResultItem.setDeliveryMethodType(deliveryMethodEntity.getDeliveryMethodType());

            // リストに貯める
            deliveryMethodItems.add(deliveryResultItem);

            orderDisplay++;
        }

        // 作ったリストをページに設定
        deliveryModel.setDeliveryMethodItems(deliveryMethodItems);
    }

    /**
     * 配送方法エンティティリストへの変換処理
     *
     * @param deliveryModel 配送方法設定画面ページ
     * @return 配送方法エンティティリスト
     */
    public List<DeliveryMethodEntity> toDeliveryMethodEntityListForSave(DeliveryModel deliveryModel) {
        // 返却用のリストを作成
        List<DeliveryMethodEntity> deliveryMethodEntityList = new ArrayList<>();

        for (DeliveryResultItem deliveryResultItem : deliveryModel.getDeliveryMethodItems()) {
            DeliveryMethodEntity deliveryMethodEntity = deliveryResultItem.getDeliveryMethodEntity();
            // 画面で並べ替えた表示順を設定
            deliveryMethodEntity.setOrderDisplay(deliveryResultItem.getOrderDisplay());
            // リストに追加
            deliveryMethodEntityList.add(deliveryMethodEntity);
        }

        return deliveryMethodEntityList;
    }
}
