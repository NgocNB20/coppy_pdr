/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.cart.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.cart.CartGoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.cart.CartGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.cart.CartGoodsCountUpdateLogic;
import jp.co.hankyuhanshin.itec.hmbase.exception.seasar.EmptyRuntimeException;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.AssertionUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * カート商品数量変更<br/>
 * カート商品の数量を更新する。<br/>
 *
 * @author ozaki
 * @version $Revision: 1.4 $
 */
@Component
public class CartGoodsCountUpdateLogicImpl extends AbstractShopLogic implements CartGoodsCountUpdateLogic {

    /**
     * カート商品DAO
     */
    private final CartGoodsDao cartGoodsDao;

    @Autowired
    public CartGoodsCountUpdateLogicImpl(CartGoodsDao cartGoodsDao) {
        this.cartGoodsDao = cartGoodsDao;
    }

    /**
     * カート商品数量変更<br/>
     * カート商品の数量を更新する。<br/>
     *
     * @param cartGoodsDtoList カート商品情報DTOリスト
     * @param memberInfoSeq    会員SEQ
     * @param accessUid        端末識別情報
     * @return 更新件数
     */
    @Override
    public int execute(List<CartGoodsDto> cartGoodsDtoList, Integer memberInfoSeq, String accessUid) {

        // (1) パラメータチェック
        checkParam(cartGoodsDtoList, memberInfoSeq, accessUid);

        // ・会員SEQがnullでない場合
        // 端末識別番号 = null を設定する
        if (memberInfoSeq != null && memberInfoSeq.intValue() != 0) {
            accessUid = null;
        } else {
            memberInfoSeq = 0;
        }

        // (2) DBのカート商品リストを取得
        List<CartGoodsEntity> dbCartGoodsEntityList = getCartGoodsEntityList(memberInfoSeq, accessUid);

        // (3) 数量変更された情報かどうかを判断
        List<CartGoodsDto> cartGoodsDtoListForUpdate = checkQuantityDiff(cartGoodsDtoList, dbCartGoodsEntityList);

        // (4) カート商品更新
        int totalUpdateCount = updateCartGoods(cartGoodsDtoListForUpdate);

        // (5) 戻り値
        // 合計更新件数を返す。
        return totalUpdateCount;
    }

    /**
     * パラメータチェック
     *
     * @param cartGoodsDtoList カート商品DTOリスト
     * @param memberInfoSeq    会員SEQ
     * @param accessUid        端末識別情報
     * @param customParams     案件用引数
     */
    protected void checkParam(List<CartGoodsDto> cartGoodsDtoList,
                              Integer memberInfoSeq,
                              String accessUid,
                              Object... customParams) {

        // カート商品DTOリストが null でないかをチェック
        // 会員SEQと端末識別番号がともにnullでないことをチェック
        AssertionUtil.assertNotNull("cartGoodsDtoList", cartGoodsDtoList);
        if (memberInfoSeq == null && accessUid == null) {
            throw new EmptyRuntimeException("accessUid");
        }
    }

    /**
     * 数量変更された情報かどうかを判断
     *
     * @param memberInfoSeq 会員SEQ
     * @param accessUid     端末識別情報
     * @param customParams  案件用引数
     * @return カート商品エンティティリスト
     */
    protected List<CartGoodsEntity> getCartGoodsEntityList(Integer memberInfoSeq,
                                                           String accessUid,
                                                           Object... customParams) {

        // カート商品Daoのカート商品情報リスト取得を実行する。
        CartGoodsForDaoConditionDto cartgoodsConditionDto = getComponent(CartGoodsForDaoConditionDto.class);
        cartgoodsConditionDto.setShopSeq(null);
        cartgoodsConditionDto.setAccessUid(accessUid);
        cartgoodsConditionDto.setMemberInfoSeq(memberInfoSeq);

        List<CartGoodsEntity> dbCartGoodsEntityList = cartGoodsDao.getCartGoodsList(cartgoodsConditionDto);
        return dbCartGoodsEntityList;
    }

    /**
     * 数量変更された情報かどうかを判断
     *
     * @param cartGoodsDtoList      カート商品DTOリスト
     * @param dbCartGoodsEntityList カート商品エンティティリスト
     * @param customParams          案件用引数
     * @return 差異が確認された商品カートDTOリスト
     */
    protected List<CartGoodsDto> checkQuantityDiff(List<CartGoodsDto> cartGoodsDtoList,
                                                   List<CartGoodsEntity> dbCartGoodsEntityList,
                                                   Object... customParams) {

        // 数量変更対象カート商品DTOリストを初期化する。
        // ・（パラメータ）カート商品DTOリストの件数分、以下の処理を行う
        // ・（パラメータ）カート商品DTO．カートSEQと一致するカート商品エンティティが、（2）で取得したカート商品エンティティリスト内に存在するかをチェック
        // ・カートSEQが一致するものがある場合、数量を比較
        // ・数量が異なる場合、（パラメータ）カート商品DTOを 数量変更対象カート商品DTOリスト に追加
        List<CartGoodsDto> cartGoodsDtoListForUpdate = new ArrayList<>();
        for (int i = 0; i < cartGoodsDtoList.size(); i++) {
            CartGoodsDto cartGoodsDto = cartGoodsDtoList.get(i);
            for (int j = 0; j < dbCartGoodsEntityList.size(); j++) {
                CartGoodsEntity dbCartGoodsEntity = dbCartGoodsEntityList.get(j);

                if (cartGoodsDto.getCartGoodsEntity().getCartSeq().intValue() == dbCartGoodsEntity.getCartSeq()
                                                                                                  .intValue()) {
                    if (cartGoodsDto.getCartGoodsEntity().getCartGoodsCount().intValue()
                        != dbCartGoodsEntity.getCartGoodsCount().intValue()) {
                        cartGoodsDtoListForUpdate.add(cartGoodsDto);
                    }
                }
            }
        }
        return cartGoodsDtoListForUpdate;
    }

    /**
     * カート商品更新
     *
     * @param cartGoodsDtoListForUpdate カート商品DTOリスト
     * @param customParams              案件用引数
     * @return 合計更新件数
     */
    protected int updateCartGoods(List<CartGoodsDto> cartGoodsDtoListForUpdate, Object... customParams) {

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // 合計更新件数 = 0 をセット
        // ・数量変更対象カート商品DTOリストの件数分、以下の処理を行う
        // カート商品Daoのアップデートを実行する。
        // メソッド 更新した数 updateGoodsCount( 数量変更対象カート商品DTO．カートSEQ,
        // 数量変更対象カート商品DTO．数量)
        // 合計更新件数に更新した数を加算する。
        int totalUpdateCount = 0;
        for (int i = 0; i < cartGoodsDtoListForUpdate.size(); i++) {

            Timestamp currentTime = dateUtility.getCurrentTime();

            CartGoodsDto cartGoodsDto = cartGoodsDtoListForUpdate.get(i);
            totalUpdateCount += cartGoodsDao.updateGoodsCount(
                            cartGoodsDto.getCartGoodsEntity().getCartSeq(),
                            cartGoodsDto.getCartGoodsEntity().getCartGoodsCount(), currentTime
                                                             );
        }
        return totalUpdateCount;
    }

}
