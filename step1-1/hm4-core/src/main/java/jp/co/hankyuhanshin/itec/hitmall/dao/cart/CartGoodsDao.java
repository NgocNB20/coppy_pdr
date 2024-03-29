/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.cart;

import jp.co.hankyuhanshin.itec.hitmall.application.commoninfo.CommonInfoCartDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.cart.CartGoodsEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * カート商品Daoクラス
 *
 * @author thang
 * @version $Revision: 1.0 $
 */
@Dao
@ConfigAutowireable
public interface CartGoodsDao {

    /**
     * 追加
     *
     * @param cartGoodsEntity カート商品エンティティ
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(CartGoodsEntity cartGoodsEntity);

    /**
     * 更新
     *
     * @param cartGoodsEntity カート商品エンティティ
     * @return 処理件数
     */
    @Update
    int update(CartGoodsEntity cartGoodsEntity);

    /**
     * 削除
     *
     * @param cartGoodsEntity カート商品エンティティ
     * @return 処理件数
     */
    @Delete
    int delete(CartGoodsEntity cartGoodsEntity);

    /**
     * エンティティ取得
     *
     * @param cartSeq カートSEQ
     * @return カート商品エンティティ
     */
    @Select
    CartGoodsEntity getEntity(Integer cartSeq);

    /**
     * 複数削除
     *
     * @param cartSeqList   カートSEQリスト
     * @param accessUid     端末識別情報
     * @param memberInfoSeq 会員SEQ
     * @return 削除件数
     */
    @Delete(sqlFile = true)
    int deleteList(List<Integer> cartSeqList, String accessUid, Integer memberInfoSeq);

    /**
     * カート全削除
     * ※reserveFlag（取りおきフラグ）≠1（取りおき不可）が対象
     *   → セールde予約商品は振分商品は発生しないので、カート画面表示時の再投入は行わないため。
     *
     * @param shopSeq       ショップSEQ
     * @param accessUid     端末識別情報
     * @param memberInfoSeq 会員SEQ
     * @return 削除件数
     */
    @Delete(sqlFile = true)
    int deleteShopMemberCart(Integer shopSeq, String accessUid, Integer memberInfoSeq);

    /**
     * カート内商品件数取得
     *
     * @param shopSeq       ショップSEQ
     * @param accessUid     端末識別情報
     * @param memberInfoSeq 会員SEQ
     * @return カート内商品件数
     */
    @Select
    int getCartGoodsCount(Integer shopSeq, String accessUid, Integer memberInfoSeq);

    /**
     * カート数量変更
     *
     * @param cartSeq        カートSEQ
     * @param cartGoodsCount カート商品数量
     * @param updateTime     更新日時
     * @return 更新件数
     */
    @Update(sqlFile = true)
    int updateGoodsCount(Integer cartSeq, BigDecimal cartGoodsCount, Timestamp updateTime);

    /**
     * 会員情報更新
     *
     * @param shopSeq             ショップSEQ
     * @param accessUid           端末識別情報
     * @param memberInfoSeq       会員SEQ
     * @param changeMemberInfoSeq 更新対象会員SEQ
     * @return 更新件数
     */
    @Update(sqlFile = true)
    int updateMemberInfo(Integer shopSeq, String accessUid, Integer memberInfoSeq, Integer changeMemberInfoSeq);

    /**
     * カート商品情報リスト取得
     *
     * @param cartGoodsConditionDto カート商品検索条件DTO
     * @return カート商品エンティティリスト
     */
    @Select
    List<CartGoodsEntity> getCartGoodsList(CartGoodsForDaoConditionDto cartGoodsConditionDto);

    /**
     * カート内重複商品情報リスト取得(既存在チェック)<br/>
     * <pre>
     * ※必ず1件のみしか取得できないはずだが、
     * 主キーでのSELECTではない為
     * DB制約上Listでの取得とする。
     * </pre>
     *
     * ※reserveFlag（取りおきフラグ）≠1（取りおき不可）が対象
     *   → セールde予約商品はお届け希望日別で同商品のレコードが発生するため重複可
     *
     * @param cartGoodsEntity カート商品Entity
     * @return カート商品EntityDtoリスト
     */
    @Select
    List<CartGoodsEntity> getCartGoodsOverlapList(CartGoodsEntity cartGoodsEntity);

    /**
     * カート内同一商品マージ<br/>
     * <pre>
     * カートに同一商品が存在し、商品数をマージする場合、登録日時を更新する。
     * ※PKGでは登録日時の降順で表示
     * </pre>
     *
     * @param cartGoodsEntity カート商品Entity
     * @return 更新件数
     */
    @Update(sqlFile = true)
    int updateCartgoodsCountMerge(CartGoodsEntity cartGoodsEntity);

    /**
     * ログイン後カート内同一商品削除<br/>
     * 商品削除<br/>
     *
     * ※reserveFlag（取りおきフラグ）≠1（取りおき不可）が対象
     *   → セールde予約商品はログイン後にしかカートINできないため
     *
     * @param shopSeq       ショップSEQ
     * @param memberInfoSeq 会員SEQ
     * @return 削除件数
     */
    @Delete(sqlFile = true)
    int deleteSameOldCartGoods(Integer shopSeq, Integer memberInfoSeq);

    /**
     * カートIN合計数量取得<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @param accessUid     端末識別情報
     * @return カート数量
     */
    @Select
    CommonInfoCartDto getCommonInfoCart(Integer memberInfoSeq, String accessUid);

    /**
     * メンバーのカートをロック<br/>
     *
     * @param shopSeq       ショップSEQ
     * @param memberInfoSeq 会員SEQ
     * @return SELECT結果
     */
    @Select
    // PDR Migrate Customization from here
    List<CartGoodsEntity> lock(Integer shopSeq, Integer memberInfoSeq);
    // PDR Migrate Customization to here
}
