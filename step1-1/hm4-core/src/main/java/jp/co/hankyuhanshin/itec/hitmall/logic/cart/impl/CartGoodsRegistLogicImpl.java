/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.cart.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCartGoodsMergeFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReserveDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.dao.cart.CartGoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.cart.CartGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.cart.CartGoodsRegistLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * カート商品登録<br/>
 * カートに商品を登録する。<br/>
 *
 * @author ozaki
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class CartGoodsRegistLogicImpl extends AbstractShopLogic implements CartGoodsRegistLogic {

    /**
     * カート商品登録可能数量(テーブル定義参照)
     */
    private static final BigDecimal MAX_CART_GOODS_COUNT = new BigDecimal(9999);

    /**
     * カート商品DAO
     */
    private final CartGoodsDao cartGoodsDao;

    @Autowired
    public CartGoodsRegistLogicImpl(CartGoodsDao cartGoodsDao) {
        this.cartGoodsDao = cartGoodsDao;
    }

    /**
     * カート商品登録<br/>
     * カートに商品を登録する。<br/>
     *
     * @param cartGoodsEntity カート商品エンティティ
     * @return 登録件数
     */
    @Override
    public int execute(CartGoodsEntity cartGoodsEntity) {

        // (1) パラメータチェック
        // カート商品エンティティがnullでないことをチェック
        ArgumentCheckUtil.assertNotNull("cartGoodsEntity", cartGoodsEntity);

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // (2) 登録日、更新日をセット
        // サーバの現在日時を下記項目にセットする
        // ・カート商品エンティティ．登録日時
        // ・カート商品エンティティ．更新日時
        Timestamp now = dateUtility.getCurrentTime();
        cartGoodsEntity.setRegistTime(now);
        cartGoodsEntity.setUpdateTime(now);

        // カート内同一商品マージを実施する・しないの設定値を取得
        String cartGoodsMergeFlag = PropertiesUtil.getSystemPropertiesValue(CART_GOODS_MERGE);
        int result;

        // 同一商品カートマージする場合
        if (HTypeCartGoodsMergeFlag.CART_MERGE.getValue().equals(cartGoodsMergeFlag)
            // 2023-renew No14 from here
            // 且つ セールde予約商品でない場合
            && HTypeReserveDeliveryFlag.OFF.equals(cartGoodsEntity.getReserveFlag())
            // 2023-renew No14 to here
        ) {

            // (3) カート商品既存在チェック
            // ・カート商品EntityDtoの情報と合致するレコードを取得
            // ※必ず0or1件のみしか取得できないはずだが、主キーでのSELECTではない為DB制約上Listでの取得とする。
            List<CartGoodsEntity> tempCartGoodsList = this.cartGoodsDao.getCartGoodsOverlapList(cartGoodsEntity);

            // 既に同一商品がカートに投入済みかどうかで登録処理を分岐
            // ・存在しない場合は普通にINSERTを実行
            // ・既に存在する場合、カートの一番上に該当商品を移動する必要がある(更新日時の更新)
            if (tempCartGoodsList.isEmpty()) {

                // (4) カート商品登録
                // ・カート商品EntityDtoを登録
                result = this.cartGoodsDao.insert(cartGoodsEntity);

            } else {

                // (4) カート商品更新
                // ・該当カート商品の数量,更新日時,付属品を最新化
                // ※付属品については、付加されたときのみ更新する
                CartGoodsEntity newCartGoodsEntityDto = tempCartGoodsList.get(0);

                // 数量の足し込み
                newCartGoodsEntityDto.setCartGoodsCount(addCartGoodsCount(newCartGoodsEntityDto.getCartGoodsCount(),
                                                                          cartGoodsEntity.getCartGoodsCount()
                                                                         ));

                // 更新日時:後で入れた商品の日時を更新日時と登録日時に設定
                newCartGoodsEntityDto.setRegistTime(cartGoodsEntity.getRegistTime());
                newCartGoodsEntityDto.setUpdateTime(cartGoodsEntity.getUpdateTime());

                result = this.cartGoodsDao.updateCartgoodsCountMerge(newCartGoodsEntityDto);

            }
        }

        // 同一商品カートマージしない場合
        // 2023-renew No14 from here
        // 又は セールde予約商品の場合
        // 2023-renew No14 to here
        else {
            // (3) カート商品エンティティ登録
            // カート商品Daoのインサート処理を実行する。
            // DAO CartGoodsDao
            // メソッド 登録した数 insert(カート商品エンティティ)
            result = cartGoodsDao.insert(cartGoodsEntity);

        }
        // (6) 戻り値
        // 登録した数を返す
        return result;
    }

    /**
     * 数量加算処理<br/>
     * 加算した結果が9999を超える場合、9999をセットする<br/>
     * ※DB制約上の理由(カラム桁数=4)
     *
     * @param originalCount 元の数量
     * @param selectCount   追加する数量
     * @return 加算された数量
     */
    public BigDecimal addCartGoodsCount(BigDecimal originalCount, BigDecimal selectCount) {

        // 数量を加算
        // ※フロント商品詳細以外からのカート投入は数量を指定出来ないため、必ず1になっている
        BigDecimal addCartGoodsCount = originalCount.add(selectCount);
        if (MAX_CART_GOODS_COUNT.compareTo(addCartGoodsCount) < 0) {
            // 加算した結果が登録可能数量を超える場合、登録可能数量に置き換え
            addCartGoodsCount = MAX_CART_GOODS_COUNT;

        }
        return addCartGoodsCount;
    }

}
