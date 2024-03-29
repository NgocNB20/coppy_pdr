/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.order.goods;

import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.sql.Timestamp;
import java.util.List;

/**
 * 受注商品Daoクラス
 *
 * @author thang
 * @version $Revision: 1.0 $
 */
@Dao
@ConfigAutowireable
public interface OrderGoodsDao {

    /**
     * インサート
     *
     * @param orderGoodsEntity 受注商品エンティティ
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(OrderGoodsEntity orderGoodsEntity);

    /**
     * アップデート
     *
     * @param orderGoodsEntity 受注商品エンティティ
     * @return 処理件数
     */
    @Update
    int update(OrderGoodsEntity orderGoodsEntity);

    /**
     * デリート
     *
     * @param orderGoodsEntity 受注商品エンティティ
     * @return 処理件数
     */
    @Delete
    int delete(OrderGoodsEntity orderGoodsEntity);

    /**
     * エンティティ取得
     *
     * @param orderSeq            受注SEQ
     * @param orderGoodsVersionNo 受注商品連番
     * @param goodsSeq            商品SEQ
     * @param orderDisplay        表示順
     * @return 受注商品エンティティ
     */
    @Select
    OrderGoodsEntity getEntity(Integer orderSeq, Integer orderGoodsVersionNo, Integer goodsSeq, Integer orderDisplay);

    /**
     * 受注商品リスト取得
     *
     * @param orderSeq            受注SEQ
     * @param orderGoodsVersionNo 受注商品連番
     * @return 受注商品エンティティリスト
     */
    @Select
    List<OrderGoodsEntity> getOrderGoodsList(Integer orderSeq, Integer orderGoodsVersionNo);

    /**
     * 配送先ごとの受注商品リストを取得
     *
     * @param orderSeq            受注SEQ
     * @param orderGoodsVersionNo 受注商品連番
     * @param orderConsecutiveNo  注文連番
     * @return List&lt;OrderGoodsEntity&gt; 受注商品Entityリスト
     */
    @Select
    List<OrderGoodsEntity> getDtoListEachOfDelivery(Integer orderSeq,
                                                    Integer orderGoodsVersionNo,
                                                    Integer orderConsecutiveNo);

    /**
     * 受注商品リスト削除
     *
     * @param orderSeq            受注SEQ
     * @param orderGoodsVersionNo 受注商品連番
     * @return 削除件数
     */
    @Delete(sqlFile = true)
    int deleteOrderGoodsList(Integer orderSeq, Integer orderGoodsVersionNo);

    /**
     * 受注商品リスト削除
     *
     * @param orderSeq            受注SEQ
     * @param orderGoodsVersionNo 受注商品連番
     * @param orderConsecutiveNo  注文連番
     * @return 削除件数
     */
    @Delete(sqlFile = true)
    int deleteOrderGoodsListByOrderConsecutiveNo(Integer orderSeq,
                                                 Integer orderGoodsVersionNo,
                                                 Integer orderConsecutiveNo);

    /**
     * 在庫開放対象データ取得
     *
     * @param shopSeq       ショップSEQ
     * @param thresholdTime 閾日時
     * @return オーダーSEQのリスト
     */
    @Select
    List<Integer> getUnorderOrderSeqList(Integer shopSeq, Timestamp thresholdTime);

    /**
     * 受注予約商品の最遅の販売開始日を取得する<br/>
     * <li>予約商品の「最遅の販売開始日（PC）」と「最遅の販売開始日（MB）」を比較し、開始の早いの最遅を取得</li>
     *
     * @param orderSeq           受注SEQ
     * @param orderConsecutiveNo 注文連番
     * @return 予約商品販売開始日
     */
    @Select
    Timestamp getOrderReservationGoodsLatestSaleStartTime(Integer orderSeq, Integer orderConsecutiveNo);
}
