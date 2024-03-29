/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodTypeCarriageEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.math.BigDecimal;
import java.util.List;

/**
 * 配送区分別送料Dao
 *
 * @author thang
 * @version $Revision: 1.0 $
 */
@Dao
@ConfigAutowireable
public interface DeliveryMethodTypeCarriageDao {

    /**
     * インサート
     *
     * @param deliveryMethodTypeCarriageEntity 配送区分別送料
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(DeliveryMethodTypeCarriageEntity deliveryMethodTypeCarriageEntity);

    /**
     * アップデート
     *
     * @param deliveryMethodTypeCarriageEntity 配送区分別送料
     * @return 処理件数
     */
    @Update
    int update(DeliveryMethodTypeCarriageEntity deliveryMethodTypeCarriageEntity);

    /**
     * デリート
     *
     * @param deliveryMethodTypeCarriageEntity 配送区分別送料
     * @return 処理件数
     */
    @Delete
    int delete(DeliveryMethodTypeCarriageEntity deliveryMethodTypeCarriageEntity);

    /**
     * エンティティ取得
     *
     * @param deliveryMethodSeq 配送方法SEQ
     * @param prefectureType    都道府県種別
     * @param maxPrice          上限金額
     * @return 配送区分別送料エンティティ
     */
    @Select
    DeliveryMethodTypeCarriageEntity getEntity(Integer deliveryMethodSeq, String prefectureType, BigDecimal maxPrice);

    /**
     * 一括削除
     *
     * @param deliveryMethodSeq 配送方法SEQ
     * @return 処理件数
     */
    @Delete(sqlFile = true)
    int deleteList(Integer deliveryMethodSeq);

    /**
     * エンティティリスト取得
     *
     * @param deliveryMethodSeq 配送方法SEQ
     * @return エンティティリスト
     */
    @Select
    List<DeliveryMethodTypeCarriageEntity> getEntityList(Integer deliveryMethodSeq);

}
