/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.shop.settlement;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Script;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 決済方法Daoクラス<br/>
 *
 * @author thang
 * @version $Revision: 1.0 $
 */
@Dao
@ConfigAutowireable
public interface SettlementMethodDao {

    /**
     * インサート<br/>
     *
     * @param settlementMethodEntity 決済方法エンティティ
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(SettlementMethodEntity settlementMethodEntity);

    /**
     * アップデート<br/>
     *
     * @param settlementMethodEntity 決済方法エンティティ
     * @return 処理件数
     */
    @Update
    int update(SettlementMethodEntity settlementMethodEntity);

    /**
     * デリート<br/>
     *
     * @param settlementMethodEntity 決済方法エンティティ
     * @return 処理件数
     */
    @Delete
    int delete(SettlementMethodEntity settlementMethodEntity);

    /**
     * エンティティ取得<br/>
     *
     * @param settlementMethodSeq 決済方法SEQ
     * @return 決済方法エンティティ
     */
    @Select
    SettlementMethodEntity getEntity(Integer settlementMethodSeq);

    /**
     * メソッド概要<br/>
     * メソッドの説明・概要<br/>
     *
     * @param shopSeq                 ショップSEQ
     * @param settlementMethodSeqList 決済方法SEQリスト
     * @return 決済方法リスト
     */
    @Select
    List<SettlementMethodEntity> getOpenSettlementMethodListBySeqList(Integer shopSeq,
                                                                      List<Integer> settlementMethodSeqList);

    /**
     * 決済方法の一覧と金額別の送料を返却
     * <pre>
     * 金額別の送料については、該当する上限金額の内、最安値の上限金額の送料を返却
     * </pre>
     *
     * @param conditionDto 検索条件
     * @return 決済方法詳細リスト
     */
    @Select
    List<SettlementDetailsDto> getSearchSettlementDetailsList(SettlementSearchForDaoConditionDto conditionDto);

    /**
     * エンティティリスト取得<br/>
     *
     * @param shopSeq ショップSEQ
     * @return 決済方法エンティティリスト(表示順昇順)
     */
    @Select
    List<SettlementMethodEntity> getSettlementMethodList(Integer shopSeq);

    /**
     * 選択項目用リスト取得<br/>
     *
     * @param shopSeq ショップSEQ
     * @return 問い合わせ結果を格納したMap
     */
    @Select
    List<Map<String, Object>> getItemMapList(Integer shopSeq);

    /**
     * テーブルロック<br />
     */
    @Script
    void updateLockTableShareModeNowait();

    /**
     * 決済方法表示順更新<br/>
     *
     * @param settlementMethodSeq 決済方法SEQ
     * @param shopSeq             ショップSEQ
     * @param orderDisplay        表示順
     * @param updateTime          更新日時
     * @return 処理件数
     */
    @Update(sqlFile = true)
    int updateOrderDisplay(Integer settlementMethodSeq, Integer shopSeq, Integer orderDisplay, Timestamp updateTime);

    /**
     * 決済方法エンティティ取得<br/>
     *
     * @param settlementMethodSeq 決済方法SEQ
     * @param shopSeq             ショップSEQ
     * @return 決済方法エンティティ
     */
    @Select
    SettlementMethodEntity getSettlementMethodEntity(Integer settlementMethodSeq, Integer shopSeq);

    /**
     * 表示順の最大値を取得
     *
     * @param shopSeq ショップSEQ
     * @return 表示順の最大値
     */
    @Select
    Integer getMaxOrderDisplay(Integer shopSeq);

    /**
     * 決済方法同名件数取得<br/>
     *
     * @param settlementMethodSeq  決済方法SEQ
     * @param shopSeq              ショップSEQ
     * @param settlementMethodName 決済方法名
     * @return 同名の件数（指定決済方法SEQを省く）
     */
    @Select
    int getSameNameCount(Integer settlementMethodSeq, Integer shopSeq, String settlementMethodName);

    /**
     * メソッド概要<br/>
     * メソッドの説明・概要<br/>
     *
     * @param settlementMethodSeqList 決済方法SEQリスト
     * @param shopSeq                 ショップSEQ
     * @param notInSeqList            除外する決済
     * @param deliveryMethodSeqList   配送方法SEQリスト
     * @return 決済方法詳細リスト
     */
    @Select
    List<SettlementDetailsDto> getsettlementDetailsListForLp(List<Integer> settlementMethodSeqList,
                                                             Integer shopSeq,
                                                             Integer[] notInSeqList,
                                                             List<Integer> deliveryMethodSeqList);

    /**
     * エンティティ取得<br/>
     *
     * @param settlementMethodName 決済方法名
     * @return 決済方法エンティティ
     */
    @Select
    SettlementMethodEntity getEntityBySettlementMethodName(String settlementMethodName);

    /**
     * エンティティ取得<br/>
     *
     * @param settlementMethodType 決済方法種別
     * @return 決済方法エンティティ
     */
    @Select
    SettlementMethodEntity getEntityBySettlementMethodType(String settlementMethodType);

    /**
     * 決済方法区分をキーとしてエンティティ取得<br/>
     *
     * @param shopSeq              ショップSEQ
     * @param settlementMethodType 決済方法種別
     * @param openStatus           公開状態
     * @return 決済方法エンティティ
     */
    @Select
    SettlementMethodEntity getEntityBySettlementMethodTypeAndOpenStatus(Integer shopSeq,
                                                                        HTypeSettlementMethodType settlementMethodType,
                                                                        HTypeOpenStatus openStatus);
}
