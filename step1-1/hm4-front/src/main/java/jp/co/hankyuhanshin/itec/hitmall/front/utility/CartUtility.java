/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.utility;

import jp.co.hankyuhanshin.itec.hitmall.front.dto.cart.CartGoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.cart.CartGoodsEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * カート業務Utility
 *
 * @author kaneda
 */
@Component
public class CartUtility {

    // PDR Migrate Customization from here
    /**
     * 個別配送商品エラー
     */
    private static final String MSGCD_INDIVIDUAL_DELIVERY = "LCC000617";
    // PDR Migrate Customization to here

    /**
     * コンストラクタ<br/>
     */
    @Autowired
    public CartUtility() {
        // nop
    }

    /**
     * カート商品チェックサービスの結果から、処理続行を許可する内容か否かを判断します。
     * <pre>
     * カート商品チェックサービスでは「ラッピング商品」に関するチェックは実施していない。
     * 当メソッドでラッピング商品のチェックを実施し、
     * そのチェック結果と、カート商品チェックの結果より、処理続行の判断を行う。
     * </pre>
     *
     * @param messageMap       メッセージマップ
     * @param cartGoodsDtoList カート商品DTOリスト
     * @return true..許可しない / false..許可する
     */
    public boolean isErrorAbortProcessing(Map<Integer, List<CheckMessageDto>> messageMap,
                                          List<CartGoodsDto> cartGoodsDtoList) {

        // カート商品チェックOKの場合は空のマップ情報が設定されるためfalseを設定し処理を終了する
        if (messageMap == null || messageMap.isEmpty()) {
            return false;
        }

        // 個別配送商品の場合、以下の追加チェックが必要
        String messageId;
        Integer goodsSeq;
        Integer cartSeq;
        for (CartGoodsDto cartGoodsDto : cartGoodsDtoList) {
            // カート商品エンティティを取得
            CartGoodsEntity cartGoodsEntity = cartGoodsDto.getCartGoodsEntity();
            // 商品SEQ取得
            goodsSeq = cartGoodsEntity.getGoodsSeq();
            // カートSEQ取得
            cartSeq = cartGoodsEntity.getCartSeq();
            // メッセージマップからカートSEQに該当するメッセージリストを取得
            List<CheckMessageDto> checkMessageDtoList = messageMap.get(cartSeq);
            if (checkMessageDtoList != null) {
                for (CheckMessageDto checkMessageDto : checkMessageDtoList) {
                    messageId = checkMessageDto.getMessageId();
                    // エラーチェック（個別配送商品エラーでない）
                    if (!StringUtils.isEmpty(messageId) && !MSGCD_INDIVIDUAL_DELIVERY.equals(messageId)) {
                        // 個別配送商品以外のエラーがあった時点で処理中断
                        return true;
                    }
                    // 個別配送商品と別の商品は同時購入はできない
                    if (MSGCD_INDIVIDUAL_DELIVERY.equals(messageId)) {
                        for (CartGoodsDto cartGoodsDto2 : cartGoodsDtoList) {
                            if (!cartGoodsDto2.getCartGoodsEntity().getGoodsSeq().equals(goodsSeq)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
