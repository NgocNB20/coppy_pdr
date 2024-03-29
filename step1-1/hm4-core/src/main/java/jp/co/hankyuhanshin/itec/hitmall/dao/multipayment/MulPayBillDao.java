/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.multipayment;

import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.MulPayBillResultDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayBillEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * マルチペイメント請求Daoクラス<br/>
 *
 * @author thang
 */
@Dao
@ConfigAutowireable
public interface MulPayBillDao {

    /**
     * インサート<br/>
     *
     * @param mulPayBillEntity マルチペイメント請求
     * @return 登録件数
     */
    @Insert(excludeNull = true)
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    int insert(MulPayBillEntity mulPayBillEntity);

    /**
     * アップデート<br/>
     *
     * @param mulPayBillEntity マルチペイメント請求
     * @return 登録件数
     */
    @Update
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    int update(MulPayBillEntity mulPayBillEntity);

    /**
     * 受注SEQからエンティティの取得<br/>
     * 指定された受注の最新のトランザクション履歴を取得<br/>
     *
     * @param orderSeq 受注SEQ
     * @return マルチペイメント請求エンティティ
     */
    @Select
    MulPayBillEntity getLatestEntity(Integer orderSeq);

    /**
     * 取引ID、取引パスワードからエンティティの取得<br/>
     * 指定された取引の最新のトランザクション履歴を取得<br/>
     *
     * @param accessId   取引ID
     * @param accessPass 取引パスワード
     * @return マルチペイメント請求エンティティ
     */
    @Select
    MulPayBillEntity getLatestEntityByAccessInfo(String accessId, String accessPass);

    /**
     * オーダーIDからエンティティの取得<br/>
     * 指定のオーダーIDから最新のトランザクション履歴を取得<br/>
     *
     * @param orderId オーダーID
     * @return マルチペイメント請求エンティティ
     */
    @Select
    MulPayBillEntity getLatestEntityByOrderId(String orderId);

    /**
     * オーダーID・取引IDからエンティティの取得<br/>
     * 指定のオーダーID・取引IDから最新のトランザクション履歴を取得<br/>
     *
     * @param orderId  オーダーID
     * @param accessId 取引ID
     * @return マルチペイメント請求エンティティ
     */
    @Select
    MulPayBillEntity getLatestEntityByOrderIdAndAccessId(String orderId, String accessId);

    /**
     * 引数の値とorderIdが前方一致するデータのうち降順にした先頭1レコードを取得
     *
     * @param orderCodeWithPrefix オーダーID（前方一致）
     * @return エンティティ
     */
    @Select
    MulPayBillEntity getLatestEntityByOrderCodeWithPrefix(String orderCodeWithPrefix);

    /**
     * マルチペイメント請求情報取得
     *
     * @param orderSeq       受注SEQ
     * @param orderVersionNo 受注履歴番号
     * @return マルチペイメント請求情報
     */
    @Select
    MulPayBillEntity getMulPayBill(Integer orderSeq, Integer orderVersionNo);

    /**
     * 受注SEQをもとにのクレジットのマルチペイメント通信用orderIdを取得します。<br/>
     *
     * @param orderSeq 受注SEQ
     * @return クレジットのマルチペイメント請求用orderId
     */
    @Select
    String getLatestCreditOrderId(Integer orderSeq);

    /**
     * オーソリ期限日（決済日付＋オーソリ保持期間）の取得。<br/>
     * <pre>
     * マルチペイメント請求テーブル．処理区分　=　'AUTH'（オーソリ）
     * </pre>
     *
     * @param authoryHoldPeriod オーソリ保持期間（日）
     * @param orderSeq          受注SEQ
     * @return オーソリ期限日（決済日付＋オーソリ保持期間)
     */
    @Select
    Timestamp getAuthoryLimitDate(String authoryHoldPeriod, Integer orderSeq);

    /**
     * 与信枠確保未解放受注リスト取得
     *
     * @param thresholdTime 現在時間-登録からの経過時間
     * @param specifiedDay  現在時間-登録からの経過時間
     * @return 受注請求リスト
     */
    @Select
    List<MulPayBillResultDto> getReserveCreditLineMulPayBillList(Timestamp thresholdTime, Timestamp specifiedDay);

    /**
     * オーダーIDからエンティティの取得<br/>
     * 指定のオーダーIDから、AmazonPayment入金確認ステータスが 9 以外の最新のトランザクション履歴を取得<br/>
     *
     * @param orderSeq オーダーSEQ
     * @return マルチペイメント請求エンティティ
     */
    @Select
    MulPayBillEntity getLatestEntityExceptAmazonPayErrorOccured(Integer orderSeq);
}
