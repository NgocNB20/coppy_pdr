/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.goods.stock;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockDownloadCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockSearchResultDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.stock.StockEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

/**
 * 在庫情報Dao
 *
 * @author thang
 * @version $Revision: 1.0 $
 */
@Dao
@ConfigAutowireable
public interface StockDao {

    /**
     * 追加<br/>
     *
     * @param stockEntity 在庫エンティティ
     * @return 追加件数
     */
    @Insert(excludeNull = true)
    int insert(StockEntity stockEntity);

    /**
     * 更新<br/>
     *
     * @param stockEntity 在庫エンティティ
     * @return 更新件数
     */
    @Update
    int update(StockEntity stockEntity);

    /**
     * 削除<br/>
     *
     * @param stockEntity 在庫エンティティ
     * @return 削除件数
     */
    @Delete
    int delete(StockEntity stockEntity);

    /**
     * エンティティ取得<br/>
     *
     * @param goodsSeq 商品SEQ
     * @return 在庫情報エンティティ
     */
    @Select
    StockEntity getEntity(Integer goodsSeq);

    /**
     * 在庫情報リスト取得<br/>
     *
     * @param goodsSeqList 商品SEQリスト
     * @return 在庫情報DTOリスト
     */
    @Select
    List<StockDto> getStockList(List<Integer> goodsSeqList);

    /**
     * 一括在庫引当<br/>
     *
     * @param orderSeq            受注SEQ
     * @param orderGoodsVersionNo 受注商品連番
     * @return 更新件数
     */
    @Update(sqlFile = true)
    int updateStock(Integer orderSeq, Integer orderGoodsVersionNo);

    /**
     * 一括在庫引当戻し<br/>
     *
     * @param orderSeq            受注SEQ
     * @param orderGoodsVersionNo 受注商品連番
     * @return 更新件数
     */
    @Update(sqlFile = true)
    int updateStockRollback(Integer orderSeq, Integer orderGoodsVersionNo);

    /**
     * 一括在庫引当戻し<br/>
     *
     * @param orderSeq            受注SEQ
     * @param orderGoodsVersionNo 受注商品連番
     * @param orderConsecutiveNo  注文連番
     * @return 更新件数
     */
    @Update(sqlFile = true)
    int updateStockRollbackByOrderConsecutiveNo(Integer orderSeq,
                                                Integer orderGoodsVersionNo,
                                                Integer orderConsecutiveNo);

    /**
     * 一括在庫再引当<br/>
     *
     * @param preOrderSeq            前回受注SEQ
     * @param preOrderGoodsVersionNo 前回受注商品連番
     * @param orderSeq               受注SEQ
     * @param orderGoodsVersionNo    受注商品連番
     * @param orderConsecutiveNo     注文連番
     * @param reserveMode            在庫確保モード (0=通常モード, 1=在庫確保モード, 2=確保差分戻しモード, 3=在庫戻しモード)
     * @return 更新件数
     */
    @Update(sqlFile = true)
    int updateStockAgainByOrderConsecutiveNo(Integer preOrderSeq,
                                             Integer preOrderGoodsVersionNo,
                                             Integer orderSeq,
                                             Integer orderGoodsVersionNo,
                                             Integer orderConsecutiveNo,
                                             int reserveMode);

    /**
     * 一括在庫再引当(出荷後用)<br/>
     *
     * @param orderSeq            受注SEQ
     * @param orderGoodsVersionNo 受注商品連番
     * @param orderConsecutiveNo  注文連番
     * @param reserveMode         在庫確保モード (8=出荷後在庫戻し, 9=出荷後在庫引き当て)
     * @return 更新件数
     */
    @Update(sqlFile = true)
    int updateShipedStockAgainByOrderConsecutiveNo(Integer orderSeq,
                                                   Integer orderGoodsVersionNo,
                                                   Integer orderConsecutiveNo,
                                                   int reserveMode);

    /**
     * 在庫検索結果リスト取得<br/>
     *
     * @param conditionDto  在庫検索条件DTO
     * @param selectOptions 検索オプション
     * @return 在庫検索結果DTOリスト
     */
    @Select
    List<StockSearchResultDto> getSearchStockList(StockSearchForDaoConditionDto conditionDto,
                                                  SelectOptions selectOptions);

    /**
     * 在庫情報　入庫登録<br/>
     *
     * @param shopSeq         ショップSEQ
     * @param goodscode       商品コード
     * @param supplementCount 入庫数
     * @return 更新件数
     */
    @Update(sqlFile = true)
    int updateStockSupplement(int shopSeq, String goodscode, BigDecimal supplementCount);

    /**
     * 在庫検索結果一括CSVダウンロード<br/>
     *
     * @param conditionDto 在庫検索条件DTO
     * @return ダウンロード取得件数
     */
    @Select
    Stream<StockDownloadCsvDto> exportCsvByStockSearchForDaoConditionDto(StockSearchForDaoConditionDto conditionDto);

    /**
     * 一括在庫出荷<br/>
     *
     * @param orderSeq            受注SEQ
     * @param orderGoodsVersionNo 受注履歴番号
     * @param orderConsecutiveNo  注文連番
     * @param mode                更新モード 0=通常, 1=差分
     * @return 更新件数
     */
    @Update(sqlFile = true)
    int updateStockShipment(Integer orderSeq, Integer orderGoodsVersionNo, Integer orderConsecutiveNo, Integer mode);

    /**
     * 一括出荷済み在庫戻し<br/>
     *
     * @param orderSeq            受注SEQ
     * @param orderGoodsVersionNo 受注履歴番号
     * @param orderConsecutiveNo  注文連番
     * @return 更新件数
     */
    @Update(sqlFile = true)
    int updateStockShipmentRollback(Integer orderSeq, Integer orderGoodsVersionNo, Integer orderConsecutiveNo);

    /**
     * 在庫詳細情報取得<br/>
     *
     * @param goodsSeq 商品SEQ
     * @return 在庫詳細Dto
     */
    @Select
    StockDetailsDto getStockDetailsByGoodsSeq(Integer goodsSeq);
}
