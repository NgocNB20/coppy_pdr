/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.multipayment;

import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayResultEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

/**
 * マルチペイメント決済結果Daoクラス<br/>
 *
 * @author thang
 * @version $Revision: 1.0 $
 */
@Dao
@ConfigAutowireable
public interface MulPayResultDao {

    /**
     * インサート<br/>
     *
     * @param mulPayResultEntity マルチペイメント決済結果
     * @return 登録件数
     */
    @Insert(excludeNull = true)
    int insert(MulPayResultEntity mulPayResultEntity);

    /**
     * アップデート<br/>
     *
     * @param mulPayResultEntity マルチペイメント決済結果
     * @return 更新件数
     */
    @Update
    int update(MulPayResultEntity mulPayResultEntity);

    /**
     * 同一データ検索
     *
     * @param orderId オーダーID
     * @param status  ステータス
     * @param amount  金額
     * @param errCode エラーコード
     * @param errInfo エラー詳細コード
     * @return マルチペイメント決済結果設定エンティティ
     */
    @Select
    int checkSameNotificationRecord(String orderId, String status, Integer amount, String errCode, String errInfo);

    /**
     * 決済結果リスト取得<br/>
     *
     * @param shopSeq ショップSEQ
     * @return マルチペイメント決済結果リスト
     */
    @Select
    List<MulPayResultEntity> getUnprocessedPaySuccessEntityList(Integer shopSeq);

}
