/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */
package jp.co.hankyuhanshin.itec.hitmall.dao.salesadvisability;

import jp.co.hankyuhanshin.itec.hitmall.entity.salesadvisability.SalesAdvisabilityEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

/**
 * PDR#437 【1.7次】基幹リニューアル対応　【要望No.24】　販売可否条件の変更<br/>
 *
 * <pre>
 * 販売可否判定Dao
 * </pre>
 *
 * @author Pham Quang Dieu
 */
@Dao
@ConfigAutowireable
public interface SalesAdvisabilityDao {
    // PDR Migrate Customization from here

    /**
     * インサート<br/>
     *
     * @param salesAdvisabilityEntity 販売可否判定
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(SalesAdvisabilityEntity salesAdvisabilityEntity);

    /**
     * アップデート<br/>
     *
     * @param salesAdvisabilityEntity 販売可否判定
     * @return 処理件数
     */
    @Update
    int update(SalesAdvisabilityEntity salesAdvisabilityEntity);

    /**
     * デリート<br/>
     *
     * @param salesAdvisabilityEntity 販売可否判定
     * @return 処理件数
     */
    @Delete
    int delete(SalesAdvisabilityEntity salesAdvisabilityEntity);

    /**
     * エンティティ取得<br/>
     *
     * @param salesAdvisabilitySeq 販売可否判定SEQ
     * @return 受注サマリエンティティ
     */
    @Select
    SalesAdvisabilityEntity getEntity(Integer salesAdvisabilitySeq);

    /**
     * 設定可能な確認書類の件数（顧客区分と確認書類の組み合わせ件数）を取得する<br/>
     *
     * @param businessType     顧客区分
     * @param confDocumentType 確認書類区分
     * @return 取得件数
     */
    @Select
    int getCountSettableConfDocument(String businessType, String confDocumentType);

    /**
     * ログイン会員の情報から販売可能商品区分のリストを取得。
     *
     * @param businessType     顧客区分
     * @param confDocumentType 確認書類区分
     * @return 販売可能商品区分リスト
     */
    @Select
    List<String> getGoodsClassTypeListByLoginMemberInfo(String businessType, String confDocumentType);

    /**
     * ログイン会員に販売可能な歯科専売品の件数を取得する。
     *
     * @param businessType     顧客区分
     * @param confDocumentType 確認書類区分
     * @return 販売可能な専売品件数
     */
    @Select
    int getCountDentalMonopolySalesFlgIsSalesOn(String businessType, String confDocumentType);

    /**
     * ログイン会員の情報からSEQのリストを取得。
     *
     * @param businessType     顧客区分
     * @param confDocumentType 確認書類区分
     * @return 販売可能商品区分リスト
     */
    @Select
    List<Integer> getSeqListByLoginMemberInfo(String businessType, String confDocumentType);
    // PDR Migrate Customization to here
}
