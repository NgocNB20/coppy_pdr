/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryImpossibleDayCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryImpossibleDaySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryImpossibleDayEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * お届け不可日Daoクラス
 *
 * @author thang
 */
@Dao
@ConfigAutowireable
public interface DeliveryImpossibleDayDao {

    /**
     * インサート<br/>
     *
     * @param deliveryImpossibleDay お届け不可日エンティティ
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(DeliveryImpossibleDayEntity deliveryImpossibleDay);

    /**
     * アップデート<br/>
     *
     * @param deliveryImpossibleDay お届け不可日エンティティ
     * @return 処理件数
     */
    @Update
    int update(DeliveryImpossibleDayEntity deliveryImpossibleDay);

    /**
     * デリート<br/>
     *
     * @param deliveryImpossibleDay お届け不可日エンティティ
     * @return 処理件数
     */
    @Delete
    int delete(DeliveryImpossibleDayEntity deliveryImpossibleDay);

    /**
     * お届け不可日情報取得<br/>
     *
     * @param year              年
     * @param date              年月日
     * @param deliveryMethodSeq 配送方法SEQ
     * @return お届け不可日エンティティDTO
     */
    @Select
    DeliveryImpossibleDayEntity getDeliveryImpossibleDayByYearAndDate(Integer year,
                                                                      Date date,
                                                                      Integer deliveryMethodSeq);

    /**
     * お届け不可日情報リスト取得<br/>
     *
     * @param dto           お届け不可日検索条件
     * @param selectOptions 検索オプション
     * @return お届け不可日エンティティDTOリスト
     */
    @Select
    List<DeliveryImpossibleDayEntity> getSearchDeliveryImpossibleDayList(DeliveryImpossibleDaySearchForDaoConditionDto dto,
                                                                         SelectOptions selectOptions);

    /**
     * お届け不可日CSVダウンロード<br/>
     *
     * @param year              年
     * @param deliveryMethodSeq 配送方法SEQ
     * @return ダウンロード取得
     */
    @Select
    Stream<DeliveryImpossibleDayCsvDto> exportCsvSearchDeliveryImpossibleDayList(Integer year,
                                                                                 Integer deliveryMethodSeq);

    /**
     * お届け不可日情報の削除<br/>
     *
     * @param deliveryImpossibleDay お届け不可日エンティティ
     * @return 更新件数
     */
    @Delete(sqlFile = true)
    int deleteDeliveryImpossibleDayByYear(DeliveryImpossibleDayEntity deliveryImpossibleDay);

    /**
     * お届け不可日情報を日付から件数取得
     *
     * @param date              指定日付
     * @param deliveryMethodSeq 配送方法SEQ
     * @return 件数
     */
    @Select
    int getCountByDate(Date date, Integer deliveryMethodSeq);
}
