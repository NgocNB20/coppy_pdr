/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.goods.goods;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsImageOpenstatusDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsImageEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

/**
 * 商品規格画像Daoクラス
 *
 * @author thang
 */
@Dao
@ConfigAutowireable
public interface GoodsImageDao {

    /**
     * 規格画像登録
     *
     * @param goodsImageEntity 商品画像
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(GoodsImageEntity goodsImageEntity);

    /**
     * 規格画像更新
     *
     * @param goodsImageEntity 商品画像
     * @return 処理件数
     */
    @Update
    int update(GoodsImageEntity goodsImageEntity);

    /**
     * 規格画像削除
     *
     * @param goodsGroupSeq 商品グループSEQ
     * @param goodsSeq      商品SEQ
     * @return 処理件数
     */
    @Delete(sqlFile = true)
    int deleteEntity(Integer goodsGroupSeq, Integer goodsSeq);

    /**
     * 規格画像リスト取得
     *
     * @param goodsGroupSeqList 商品グループSEQリスト
     * @return 規格画像リスト
     */
    @Select
    List<GoodsImageEntity> getGoodsImageListByGoodsGroupSeqList(List<Integer> goodsGroupSeqList);

    /**
     * 規格画像リスト取得
     *
     * @param goodsSeqList 商品SEQリスト
     * @return 規格画像リスト
     */
    @Select
    List<GoodsImageEntity> getGoodsImageListByGoodsSeqList(List<Integer> goodsSeqList);

    /**
     * 規格画像リスト取得
     *
     * @param goodsGroupCode 商品グループコード
     * @return 規格画像リスト
     */
    @Select
    List<GoodsImageOpenstatusDto> getGoodsImageOpenStatusListByCode(String goodsGroupCode);

    /**
     * 全商品画像を取得します。<br/>
     *
     * @param offset 取得開始件数
     * @param limit  最大取得件数
     * @return GoodsImageOpenstatusDto
     */
    @Select
    List<GoodsImageOpenstatusDto> getGoodsImageOpenstatusList(int offset, int limit);
}
