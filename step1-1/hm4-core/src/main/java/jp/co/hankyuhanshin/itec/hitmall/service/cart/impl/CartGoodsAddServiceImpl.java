/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.cart.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsRegistCheckDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.cart.CartGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.cart.CartGoodsRegistCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.cart.CartGoodsRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsDetailsGetByCodeLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.cart.CartGoodsAddService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * カート商品追加サービス
 *
 * @author ozaki
 * @author Nishigaki Mio (Itec) 2011/09/01 チケット #2692 対応
 */
@Service
public class CartGoodsAddServiceImpl extends AbstractShopService implements CartGoodsAddService {

    /**
     * 商品詳細情報取得ロジック
     */
    private final GoodsDetailsGetByCodeLogic goodsDetailsGetByCodeLogic;

    /**
     * カート投入可能チェックロジック
     */
    private final CartGoodsRegistCheckLogic cartGoodsRegistCheckLogic;

    /**
     * カート商品登録ロジック
     */
    private final CartGoodsRegistLogic cartGoodsRegistLogic;

    // 2023-renew No14 from here
    /**
     * 変換Utility
     */
    private final ConversionUtility conversionUtility;
    // 2023-renew No14 to here

    @Autowired
    public CartGoodsAddServiceImpl(GoodsDetailsGetByCodeLogic goodsDetailsGetByCodeLogic,
                                   CartGoodsRegistCheckLogic cartGoodsRegistCheckLogic,
                                   CartGoodsRegistLogic cartGoodsRegistLogic,
                                   ConversionUtility conversionUtility) {
        this.goodsDetailsGetByCodeLogic = goodsDetailsGetByCodeLogic;
        this.cartGoodsRegistCheckLogic = cartGoodsRegistCheckLogic;
        this.cartGoodsRegistLogic = cartGoodsRegistLogic;
        // 2023-renew No14 from here
        this.conversionUtility = conversionUtility;
        // 2023-renew No14 to here
    }

    /**
     * カート情報に商品を追加する。
     *
     * @param goodsCode 商品コード
     * @param count     数量
     * @param accessUid 端末識別情報
     * @param memberInfoSeq 会員SEQ
     * @param siteType      サイト区分
     * @param cartGoodsRegistCheckDto カート投入チェックDto
     * @return エラー商品リスト
     */
    @Override
    public List<CheckMessageDto> execute(String goodsCode,
                                         BigDecimal count,
                                         String accessUid,
                                         Integer memberInfoSeq,
                                         HTypeSiteType siteType,
                                         // 2023-renew No14 from here
                                         CartGoodsRegistCheckDto cartGoodsRegistCheckDto
                                         // 2023-renew No14 to here
                                        ) {

        // ・商品コード ： null（or空文字）の場合 エラーとして処理を終了する
        // ・数量 ： null（or 0 ）の場合 エラーとして処理を終了する
        ArgumentCheckUtil.assertNotEmpty("goodsCode", goodsCode);
        ArgumentCheckUtil.assertGreaterThanZero("count", count);

        // (2) 共通情報チェック
        // ・ショップSEQ ： null（or 0）の場合 処理を終了する
        // ・端末識別番号 ： null（or空文字）の場合 処理を終了する
        Integer shopSeq = 1001;
        if (accessUid == null || accessUid.equals("")) {
            return null;
        }

        // (3) 商品情報取得処理実行
        // ・カートに投入される商品の情報を取得する
        // ※『logic基本設計書（商品詳細情報取得（商品コード））.xls』参照
        // Logic GoodsDetailsGetByCodeLogic
        // パラメータ ショップSEQ
        // 商品コード
        // 戻り値 商品詳細Dto
        GoodsDetailsDto goodsDetailsDto = goodsDetailsGetByCodeLogic.execute(shopSeq, goodsCode, null, null);

        // (4) カート投入可能チェック
        // ・カートに投入される商品の情報から投入可能な商品であるかをチェックする
        // ※『logic基本設計書（カート投入可能チェック）.xls』参照
        // Logic CartGoodsRegistCheckLogic
        // パラメータ 商品詳細Dto
        // 数量 = パラメータ.数量
        // ショップSEQ = 共通情報.ショップSEQ
        // 端末識別情報 = 共通情報.端末識別情報
        // 会員SEQ = 共通情報.会員SEQ
        // サイト区分 = 共通情報.サイト区分
        // 戻り値 エラーメッセージリスト
        List<CheckMessageDto> errorList =
                        cartGoodsRegistCheckLogic.execute(goodsDetailsDto, count, shopSeq, memberInfoSeq, accessUid,
                                                          siteType,
                                                          // 2023-renew No14 from here
                                                          cartGoodsRegistCheckDto
                                                          // 2023-renew No14 to here
                                                         );

        // エラーがあれば処理終了
        if (CollectionUtil.getSize(errorList) > 0) {
            return errorList;
        }

        // (6) カート商品登録処理実行
        // ・カート商品エンティティを作成
        // ・カート商品登録処理実行
        // ※『logic基本設計書（カート商品登録）.xls』参照
        // Logic CartGoodsRegistLogic
        // パラメータ カート商品エンティティ
        // 戻り値 処理件数
        // [処理概要]
        // ・カート商品DBへカート商品情報を登録する
        // 2023-renew No14 from here
        CartGoodsEntity cartGoodsEntity = setGoodsEntityInfo(count, goodsDetailsDto, accessUid, memberInfoSeq, shopSeq,
                                                             // 2023-renew No14 from here
                                                             cartGoodsRegistCheckDto
                                                             // 2023-renew No14 to here
                                                            );
        // 2023-renew No14 to here
        cartGoodsRegistLogic.execute(cartGoodsEntity);
        return errorList;
    }

    /**
     * カート商品エンティティ作成<br/>
     * 商品エンティティの情報をカート商品エンティティーに設定します。<br/>
     *
     * @param count                    数量
     * @param goodsDetailsDto          商品詳細情報DTO
     * @param accessUid                端末識別ID
     * @param memberInfoSeq            会員SEQ
     * @param shopSeq                  ショップSEQ
     * @param cartGoodsRegistCheckDto  カート投入チェックDto
     * @return カート商品エンティティ
     */
    protected CartGoodsEntity setGoodsEntityInfo(BigDecimal count,
                                                 GoodsDetailsDto goodsDetailsDto,
                                                 String accessUid,
                                                 Integer memberInfoSeq,
                                                 Integer shopSeq,
                                                 // 2023-renew No14 from here
                                                 CartGoodsRegistCheckDto cartGoodsRegistCheckDto
                                                 // 2023-renew No14 to here
                                                ) {

        CartGoodsEntity cartGoodsEntity = ApplicationContextUtility.getBean(CartGoodsEntity.class);
        cartGoodsEntity.setAccessUid(accessUid);
        if (memberInfoSeq != null) {
            cartGoodsEntity.setMemberInfoSeq(memberInfoSeq);
        }
        cartGoodsEntity.setGoodsSeq(goodsDetailsDto.getGoodsSeq());
        cartGoodsEntity.setCartGoodsCount(count);
        // 2023-renew No14 from here
        cartGoodsEntity.setReserveDeliveryDate(
                        conversionUtility.toTimeStamp(cartGoodsRegistCheckDto.getReserveDeliveryDate()));
        if (cartGoodsRegistCheckDto.getReserveFlag() != null) {
            cartGoodsEntity.setReserveFlag(cartGoodsRegistCheckDto.getReserveFlag());
        }
        // 2023-renew No14 to here
        cartGoodsEntity.setShopSeq(shopSeq);
        return cartGoodsEntity;
    }

}
