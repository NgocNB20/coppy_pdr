/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goods.GoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsDetailsMapGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryMethodSelectListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.ReceiverDateGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea.FreeAreaTargetGoodsCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * フリーエリア対象商品チェック
 *
 * @author h_hakogi
 */
@Component
public class FreeAreaTargetGoodsCheckLogicImpl extends AbstractShopLogic implements FreeAreaTargetGoodsCheckLogic {

    /**
     * 商品Dao
     */
    private final GoodsDao goodsDao;

    /**
     * GoodsDetailsMapGetLogic
     */
    private final GoodsDetailsMapGetLogic goodsDetailsMapGetLogic;

    /**
     * DeliveryMethodSelectListGetLogic
     */
    private final DeliveryMethodSelectListGetLogic deliveryMethodSelectListGetLogic;

    /**
     * ReceiverDateGetLogic
     */
    private final ReceiverDateGetLogic receiverDateGetLogic;

    /**
     * SettlementMethodListGetLogic
     */
    private final SettlementMethodListGetLogic settlementMethodListGetLogic;

    /**
     * 変換Utility
     */
    private final ConversionUtility conversionUtility;

    /**
     * OrderUtility
     */
    private final OrderUtility orderUtility;

    /**
     * GoodsUtility
     */
    private final GoodsUtility goodsUtility;

    @Autowired
    public FreeAreaTargetGoodsCheckLogicImpl(GoodsDao goodsDao,
                                             GoodsDetailsMapGetLogic goodsDetailsMapGetLogic,
                                             DeliveryMethodSelectListGetLogic deliveryMethodSelectListGetLogic,
                                             ReceiverDateGetLogic receiverDateGetLogic,
                                             SettlementMethodListGetLogic settlementMethodListGetLogic,
                                             ConversionUtility conversionUtility,
                                             OrderUtility orderUtility,
                                             GoodsUtility goodsUtility) {
        this.goodsDao = goodsDao;
        this.goodsDetailsMapGetLogic = goodsDetailsMapGetLogic;
        this.deliveryMethodSelectListGetLogic = deliveryMethodSelectListGetLogic;
        this.receiverDateGetLogic = receiverDateGetLogic;
        this.settlementMethodListGetLogic = settlementMethodListGetLogic;
        this.conversionUtility = conversionUtility;
        this.orderUtility = orderUtility;
        this.goodsUtility = goodsUtility;

    }

    /**
     * フリーエリア対象商品チェック<br />
     * 登録/更新前のデータチェックを行います。<br />
     *
     * @param targetGoods 対象商品
     */
    @Override
    public void execute(String targetGoods) {

        // 対象商品が指定されていない場合は処理終了
        if (targetGoods == null || targetGoods.isEmpty()) {
            return;
        }

        // 対象商品番号リスト作成
        List<String> targetGoodsList = Arrays.asList(conversionUtility.toDivArray(targetGoods));

        // 商品番号の重複チェック
        checkDeplicateTargetGoods(targetGoodsList);

        // 商品番号の最大件数チェック
        checkMaxCountTargetGoods(targetGoodsList);

        // 商品番号の存在チェック
        checkExistTargetGoods(targetGoodsList);

        // エラーを表示
        if (hasErrorList()) {
            throwMessage();
        }

        // エラーを表示
        if (hasErrorList()) {
            throwMessage();
        }

        return;
    }

    /**
     * 商品番号の重複チェック
     *
     * @param targetGoodsList 対象商品リスト
     */
    protected void checkDeplicateTargetGoods(List<String> targetGoodsList) {
        // 対象商品番号リスト作成
        dataDuplicationCheck(targetGoodsList, MSGCD_DUPLICATION_TARGET_GOODS);
    }

    /**
     * 商品番号の最大件数チェック
     *
     * @param targetGoodsList 対象商品リスト
     */
    protected void checkMaxCountTargetGoods(List<String> targetGoodsList) {

        // 対象商品番号の最大件数取得
        int targetgoodsMaxCount =
                        Integer.parseInt(PropertiesUtil.getSystemPropertiesValue("freearea.targetgoods.max.count"));

        if (targetGoodsList.size() > targetgoodsMaxCount) {
            addErrorMessage(MSGCD_MAX_TARGET_GOODS_OVER, new Object[] {targetgoodsMaxCount});
        }
    }

    /**
     * 商品番号の存在チェック
     *
     * @param targetGoodsList 対象商品リスト
     */
    protected void checkExistTargetGoods(List<String> targetGoodsList) {

        // DB存在チェック用リスト取得
        List<String> checkGoodsList = goodsDao.getEntityListByGoodsCodeList(targetGoodsList);
        for (String goodsCode : targetGoodsList) {
            if (!checkGoodsList.contains(goodsCode)) {
                addErrorMessage(MSGCD_NOT_EXIST_TARGET_GOODS, new Object[] {goodsCode});
            }
        }
    }

    /**
     * 入力データ重複チェック処理。
     *
     * @param checkList   重複チェックリスト
     * @param messageCode メッセージコード
     */
    protected void dataDuplicationCheck(List<String> checkList, String messageCode) {
        Set<String> checkSet = new HashSet<>();
        Set<String> messageSet = new HashSet<>();
        for (String data : checkList) {
            if (checkSet.contains(data)) {
                if (!messageSet.contains(data)) {
                    messageSet.add(data);
                    addErrorMessage(messageCode, new Object[] {data});
                }
            } else {
                checkSet.add(data);
            }
        }
    }
}
