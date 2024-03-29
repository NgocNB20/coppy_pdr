/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.goods.sale;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.salegoods.SaleGoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.sale.SaleGoodsEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

/**
 * セール商品Daoクラス
 *
 * @author st75001
 */
@Dao
@ConfigAutowireable
public interface SaleGoodsDao {

    /**
     * デリート
     *
     * @param SaleGoodsEntity セール商品エンティティ
     * @return 処理件数
     */
    @Delete
    int delete(SaleGoodsEntity SaleGoodsEntity);

    /**
     * インサート
     *
     * @param SaleGoodsEntity セール商品エンティティ
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(SaleGoodsEntity SaleGoodsEntity);

    /**
     * アップデート
     *
     * @param SaleGoodsEntity セール商品エンティティ
     * @return 処理件数
     */
    @Update
    int update(SaleGoodsEntity SaleGoodsEntity);

    /**
     * エンティティ取得
     *
     * @param goodsSeq 商品SEQ
     * @return セール商品エンティティ
     */
    @Select
    SaleGoodsEntity getEntity(Integer goodsSeq);
    // 2023-renew No65 from here
    /**
     * セール商品情報一覧取得
     *
     * @param goodsSeqList 商品SEQリスト
     * @return セール商品エンティティリスト
     */
    @Select
    List<SaleGoodsDto> getSaleGoodsList(List<Integer> goodsSeqList);
    // 2023-renew No65 to here
}
