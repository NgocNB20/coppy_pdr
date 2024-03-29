/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.cart.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCartGoodsMergeFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReserveDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.dao.cart.CartGoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsForTakeOverDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.cart.CartGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.cart.CartGoodsAllDeleteLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.cart.CartGoodsListDeleteLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.cart.CartClearService;
import jp.co.hankyuhanshin.itec.hitmall.web.PageInfo;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.AssertionUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * カートクリアサービス
 *
 * @author ozaki
 * @version $Revision: 1.1 $
 */
@Service
public class CartClearServiceImpl extends AbstractShopService implements CartClearService {

    /**
     * カート全商品削除ロジック（今すぐお届けのみ）
     */
    private final CartGoodsAllDeleteLogic cartGoodsAllDeleteLogic;

    /**
     * カート商品DAO
     */
    private final CartGoodsDao cartGoodsDao;

    /**
     * カート商品リスト削除
     */
    private final CartGoodsListDeleteLogic cartGoodsListDeleteLogic;

    @Autowired
    public CartClearServiceImpl(CartGoodsAllDeleteLogic cartGoodsAllDeleteLogic,
                                CartGoodsDao cartGoodsDao,
                                CartGoodsListDeleteLogic cartGoodsListDeleteLogic) {
        this.cartGoodsAllDeleteLogic = cartGoodsAllDeleteLogic;
        this.cartGoodsDao = cartGoodsDao;
        this.cartGoodsListDeleteLogic = cartGoodsListDeleteLogic;
    }

    /**
     * カート全商品削除（今すぐお届けのみ）
     *
     * @param accessUid     端末識別情報
     * @param memberInfoSeq 会員SEQ
     */
    @Override
    public void execute(String accessUid, Integer memberInfoSeq) {

        // (1) 共通情報チェック
        // ・ショップSEQ ： null（or空文字）の場合 処理を終了する
        // ・端末識別番号 ： null（or空文字）の場合 処理を終了する
        Integer shopSeq = 1001;
        AssertionUtil.assertNotEmpty("accessUid", accessUid);

        // ・会員SEQ ：
        // null（or 0）以外の場合 (2)の処理には会員SEQをパラメータに設定する
        // null（or 0）の場合 (2)の処理には端末識別番号をパラメータに設定する
        if (memberInfoSeq != null && memberInfoSeq.intValue() != 0) {
            accessUid = null;
        } else {
            memberInfoSeq = 0;
        }

        // (2) カート全商品削除処理実行
        cartGoodsAllDeleteLogic.execute(shopSeq, memberInfoSeq, accessUid);

    }

    // PDR Migrate Customization from here

    /**
     * 一括注文用のカートクリア
     * <pre>
     * カートマージありの場合は、一括登録する商品が既にカートに投入済みの場合は、カート投入前にカートから削除しておく。
     * 例）
     * 一括登録： 商品A × 2, 商品B × 2
     * カート： 商品A × 1, 商品C × 1
     *   ↓ 投入後は以下のようにする
     * 商品A × 2, 商品B × 2, 商品C × 1
     *
     * カートマージなしの場合は処理しない
     * </pre>
     *
     * @param registCartGoodsList カート一括登録用の商品情報
     */
    @Override
    public void execute(List<CartGoodsForTakeOverDto> registCartGoodsList, Integer memberInfoSeq, String accessUid) {

        if (CollectionUtils.isEmpty(registCartGoodsList)) {
            return;
        }

        // 2023-renew No14 from here
        // セールde予約登録による削除かどうか
        boolean isReserve = isReserve(registCartGoodsList);

        // 一括登録時、カートマージありの設定であれば、CartGoodsRegistLogicImpl側で制御するためスキップ
        // セールde予約登録時はスキップせずに必ずここで削除処理を行う
        if (!isReserve && isCartMerge()) {
            return;
        }
        // 2023-renew No14 to here

        // カート商品情報を取得
        CartGoodsForDaoConditionDto conditionDto = ApplicationContextUtility.getBean(CartGoodsForDaoConditionDto.class);

        conditionDto.setShopSeq(1001);
        conditionDto.setMemberInfoSeq(memberInfoSeq);
        conditionDto.setAccessUid(accessUid);

        // ページングセットアップ
        PageInfo pageInfo = ApplicationContextUtility.getBean(PageInfo.class);
        pageInfo.setLimit(Integer.MAX_VALUE);
        pageInfo.setSkipCountFlg(true);
        pageInfo.setupSelectOptions();
        conditionDto.setPageInfo(pageInfo);

        List<CartGoodsEntity> cartGoodsList = cartGoodsDao.getCartGoodsList(conditionDto);

        // 一括登録する商品はカートから削除する
        List<Integer> cartGoodsSeqList = new ArrayList<>();
        for (CartGoodsEntity cartGoods : cartGoodsList) {
            for (CartGoodsForTakeOverDto registCartGoods : registCartGoodsList) {
                if (!cartGoods.getGoodsSeq().equals(registCartGoods.getGoodsSeq())) {
                    continue;
                }

                // 2023-renew No14 from here
                if (isReserve) {
                    // セールde予約登録による削除の場合、取りおきフラグOFFのカート商品は削除しない
                    if (HTypeReserveDeliveryFlag.OFF.equals(cartGoods.getReserveFlag())) {
                        continue;
                    }
                } else {
                    // 通常のカート一括登録による削除の場合、取りおきフラグONのカート商品は削除しない
                    if (HTypeReserveDeliveryFlag.ON.equals(cartGoods.getReserveFlag())) {
                        continue;
                    }
                }
                // 2023-renew No14 to here

                cartGoodsSeqList.add(cartGoods.getCartSeq());
                break;
            }
        }

        if (CollectionUtils.isNotEmpty(cartGoodsSeqList)) {
            cartGoodsListDeleteLogic.execute(
                            cartGoodsSeqList, conditionDto.getAccessUid(), conditionDto.getMemberInfoSeq());
        }

    }

    /**
     * カートマージありなし設定取得<br/>
     *
     * @return True:マージあり、False：マージなし
     */
    protected boolean isCartMerge() {
        String cartGoodsMergeFlag = PropertiesUtil.getSystemPropertiesValue(CART_GOODS_MERGE);
        return HTypeCartGoodsMergeFlag.CART_MERGE.getValue().equals(cartGoodsMergeFlag);
    }

    // PDR Migrate Customization to here
    // 2023-renew No14 from here

    /**
     * セールde予約登録による削除かどうか
     *
     * @return True:セールde予約による削除、False：一括登録による削除
     */
    protected boolean isReserve(List<CartGoodsForTakeOverDto> registCartGoodsList) {
        boolean isReserve = false;
        for (CartGoodsForTakeOverDto registCartGoods : registCartGoodsList) {
            // リスト内の取りおきフラグは全て同じなので、1件目で判定可能
            isReserve = HTypeReserveDeliveryFlag.ON.equals(registCartGoods.getReserveFlag());
            break;
        }
        return isReserve;
    }

    // 2023-renew No14 to here

}
