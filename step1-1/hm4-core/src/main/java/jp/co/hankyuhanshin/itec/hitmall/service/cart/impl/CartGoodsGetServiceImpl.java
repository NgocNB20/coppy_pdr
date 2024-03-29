/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.cart.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReserveDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.cart.CartGoodsCalculateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.cart.CartGoodsListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.cart.CartGoodsGetService;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * カート情報取得クラス<br/>
 * カート商品情報リストを取得する。<br/>
 *
 * @author ozaki
 */
@Service
public class CartGoodsGetServiceImpl extends AbstractShopService implements CartGoodsGetService {

    /**
     * カート商品リスト取得ロジック
     */
    private final CartGoodsListGetLogic cartGoodsListGetLogic;

    /**
     * カート金額計算ロジッククラス
     */
    private final CartGoodsCalculateLogic cartGoodsCalculateLogic;

    @Autowired
    public CartGoodsGetServiceImpl(CartGoodsListGetLogic cartGoodsListGetLogic,
                                   CartGoodsCalculateLogic cartGoodsCalculateLogic) {
        this.cartGoodsListGetLogic = cartGoodsListGetLogic;
        this.cartGoodsCalculateLogic = cartGoodsCalculateLogic;
    }

    /**
     * カート情報取得<br/>
     * カート情報を取得する。<br/>
     *
     * @param accessUid     端末識別情報
     * @param siteType      サイト区分
     * @param memberInfoSeq 会員SEQ
     * @return カートDTO
     */
    @Override
    public CartDto execute(String accessUid, HTypeSiteType siteType, Integer memberInfoSeq) {
        return execute(accessUid, siteType, memberInfoSeq, "registTime", null, null);
    }

    /**
     * カート情報取得<br/>
     * カート情報を取得する。<br/>
     *
     * @param accessUid 端末識別情報
     * @param siteType  サイト区分
     * @param memberInfoSeq 会員SEQ
     * @param orderField ソート項目
     * @param goodsSeq 商品SEQ
     * @param reserveFlag 取りおきフラグ
     * @return カートDTO
     */
    @Override
    public CartDto execute(String accessUid,
                           HTypeSiteType siteType,
                           Integer memberInfoSeq,
                           String orderField,
                           Integer goodsSeq,
                           HTypeReserveDeliveryFlag reserveFlag) {
        // (1) 共通情報チェック
        Integer shopSeq = 1001;

        // (2) カート商品リスト取得処理実行
        CartGoodsForDaoConditionDto conditionDto = ApplicationContextUtility.getBean(CartGoodsForDaoConditionDto.class);
        conditionDto.setShopSeq(shopSeq);
        conditionDto.setAccessUid(accessUid);
        conditionDto.setSiteType(siteType);
        conditionDto.setMemberInfoSeq(memberInfoSeq);
        conditionDto.setOrderField(orderField);
        conditionDto.setOrderAsc(false);
        // 2023-renew No14 from here
        conditionDto.setGoodsSeq(goodsSeq);
        conditionDto.setReserveFlag(reserveFlag);
        // 2023-renew No14 to here

        CartDto cartDto = cartGoodsListGetLogic.execute(conditionDto);
        // PDR Migrate Customization from here
        // (3) カート商品金額算出 削除
        // 後にWEB-API連携で取得した商品の金額等で算出しなおすため、ここでの算出は行わない。
        // PDR Migrate Customization to here
        return cartDto;
    }

}
