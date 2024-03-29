/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.orderhistory.OrderHistoryGoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.AuthTimeLimitOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderCSVDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchOrderGoodsResultDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchOrderResultDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSummarySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.summary.OrderSummaryForHistoryDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsTogetherBuyGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 受注サマリDaoクラス
 *
 * @author thang
 */
@Dao
@ConfigAutowireable
public interface OrderSummaryDao {

    /**
     * インサート<br/>
     *
     * @param orderSummaryEntity 受注サマリ
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(OrderSummaryEntity orderSummaryEntity);

    /**
     * アップデート<br/>
     *
     * @param orderSummaryEntity 受注サマリ
     * @return 処理件数
     */
    @Update
    int update(OrderSummaryEntity orderSummaryEntity);

    /**
     * デリート<br/>
     *
     * @param orderSummaryEntity 受注サマリ
     * @return 処理件数
     */
    @Delete
    int delete(OrderSummaryEntity orderSummaryEntity);

    /**
     * エンティティ取得<br/>
     *
     * @param orderSeq 受注SEQ
     * @return 受注サマリエンティティ
     */
    @Select
    OrderSummaryEntity getEntity(Integer orderSeq);

    /**
     * 受注番号から受注サマリ情報取得<br/>
     *
     * @param orderCode 受注番号
     * @return 受注サマリエンティティ
     */
    @Select
    OrderSummaryEntity getEntityByOrderCode(String orderCode);

    /**
     * 受注番号から受注サマリDto取得<br/>
     *
     * @param orderCode 受注番号
     * @return OrderSummaryForHistoryDto
     */
    @Select
    OrderSummaryForHistoryDto getHistoryDtoByCode(String orderCode);

    /**
     * 受注サマリリスト取得<br/>
     *
     * @param conditionDto 検索条件DTO
     * @return 受注サマリエンティティリスト
     */
    @Select
    List<OrderSummaryEntity> getSearchOrderSummaryList(OrderSummarySearchForDaoConditionDto conditionDto);

    /**
     * 受注サマリリスト取得<br/>
     * 決済、配送方法情報含む
     *
     * @param conditionDto  検索条件DTO
     * @param selectOptions 検索オプション
     * @return 受注サマリエンティティリスト
     */
    @Select
    List<OrderSummaryEntity> getSearchAllOrderSummaryList(OrderSummarySearchForDaoConditionDto conditionDto,
                                                          SelectOptions selectOptions);

    /**
     * 受注サマリ件数取得
     *
     * @param memberInfoSeq 会員SEQ
     * @return 件数
     */
    @Select
    int getCountByMemberInfoSeq(Integer memberInfoSeq);

    /**
     * 受注番号別受注情報を取得<br/>
     *
     * @param conditionDto  受注検索一覧用条件DTO
     * @param selectOptions 検索オプション
     * @return 受注サマリエンティティリスト
     */
    @Select
    List<OrderSearchOrderResultDto> getOrderSearchOrderResultList(OrderSearchConditionDto conditionDto,
                                                                  SelectOptions selectOptions);

    /**
     * 商品別受注情報を取得(グルーピング済み)<br/>
     *
     * @param conditionDto 受注検索一覧用条件DTO
     * @return OrderSearchOrderGoodsResultDtoリスト
     */
    @Select
    List<OrderSearchOrderGoodsResultDto> getOrderSearchOrderGoodsResultList(OrderSearchConditionDto conditionDto,
                                                                            SelectOptions selectOptions);

    /**
     * 排他制御エンティティ取得<br/>
     *
     * @param orderSeq 受注SEQ
     * @return 受注サマリエンティティ
     */
    @Select
    OrderSummaryEntity getEntityForUpdatePrimaryKey(Integer orderSeq);

    /**
     * 排他制御エンティティ取得<br/>
     *
     * @param orderCode      受注コード
     * @param orderVersionNo 受注履歴連番
     * @param shopSeq        ショップSEQ
     * @return 受注サマリエンティティ
     */
    @Select
    OrderSummaryEntity getEntityForUpdateCode(String orderCode, Integer orderVersionNo, Integer shopSeq);

    /**
     * 排他制御エンティティリスト取得<br/>
     *
     * @param orderSeqList 受注SEQリスト
     * @return 受注サマリエンティティリスト
     */
    @Select
    List<OrderSummaryEntity> getEntityListForUpdateSeq(List<Integer> orderSeqList);

    /**
     * 排他制御エンティティリスト取得<br/>
     *
     * @param orderCodeList 受注番号リスト
     * @param shopSeq       ショップSEQ
     * @return 受注サマリエンティティリスト
     */
    @Select
    List<OrderSummaryEntity> getEntityListForUpdateCode(List<String> orderCodeList, Integer shopSeq);

    /**
     * エンティティリスト取得<br/>
     *
     * @param orderSeqList 受注SEQリスト
     * @return 受注サマリエンティティリスト
     */
    @Select
    List<OrderSummaryEntity> getEntityListByOrderSeqList(List<Integer> orderSeqList);

    /**
     * 受注CSV情報を取得<br/>
     * チェックボックスで選択して出力する場合に使用
     *
     * @param orderSeqList 受注SEQリスト
     * @param shopSeq      ショップSEQ
     * @return ダウンロード取得
     */
    @Select
    Stream<OrderCSVDto> getOrderSearchOrderCSVListByOrderSeq(List<Integer> orderSeqList, Integer shopSeq);

    /**
     * 受注CSV情報を取得<br/>
     * CSV全件出力する場合に使用
     *
     * @param conditionDto 受注検索一覧用条件DTO
     * @return ダウンロード取得
     */
    @Select
    Stream<OrderCSVDto> getOrderSearchOrderCSVList(OrderSearchConditionDto conditionDto);

    /**
     * 受注履歴商品リスト取得<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @param startTime     受注日時開始
     * @param endTime       受注日時終了
     * @return 受注履歴商品リスト
     */
    @Select
    List<OrderHistoryGoodsDto> getOrderHistoryGoodsList(Integer memberInfoSeq, Timestamp startTime, Timestamp endTime);

    /**
     * オーソリ期限切れ間近受注一覧を取得する
     *
     * @param currentDate         処理日
     * @param authoryHoldPeriod   オーソリ保持期間（日）
     * @param mailSendStartPeriod メール送信開始期間（日）
     * @return オーソリ期限切れ間近受注リスト
     */
    @Select
    List<AuthTimeLimitOrderDto> getAuthLimitOrderList(Timestamp currentDate,
                                                      String authoryHoldPeriod,
                                                      String mailSendStartPeriod);

    /**
     * 仮ポイント有効化対象対象の受注サマリリストを取得。
     *
     * @param checkeDate 有効化チェックの基準日
     * @return 仮ポイント有効化対象対象の受注サマリリスト
     */
    @Select
    List<OrderSummaryEntity> getEntityListForPointActivation(Timestamp checkeDate);

    /**
     * 会員の購入回数取得<br/>
     * 試供品の購入回数を取得する<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @param goodsSeqList  チェック対象の商品SEQリスト
     * @param shopSeq       ショップSEQ
     * @return 商品グループごとの購入回数
     */
    @Select
    List<Map<String, Object>> getPurchasedCountByMemberSeq(Integer memberInfoSeq,
                                                           List<Integer> goodsSeqList,
                                                           Integer shopSeq);

    /**
     * 会員のクーポン利用回数を取得<br/>
     *
     * @param memberInfoSeq   会員SEQ
     * @param couponSeq       クーポンSEQ
     * @param couponStartTime クーポン開始期間
     * @return クーポン利用回数
     */
    @Select
    Integer getCouponCountByMemberInfoSeq(Integer memberInfoSeq, Integer couponSeq, Timestamp couponStartTime);

    /**
     * お申込み番号、ご注文主電話番号から受注サマリ情報取得<br/>
     *
     * @param orderCode お申込み番号
     * @param orderTel  ご注文主電話番号
     * @param shopSeq   ショップSEQ
     * @return 受注サマリエンティティDto
     */
    @Select
    OrderSummaryEntity getEntityForGestOrderLogin(String orderCode, String orderTel, Integer shopSeq);

    // PDR Migrate Customization from here
    //     /**
    //      * PDR#013 09_データ連携（受注データ）<br/>
    //      *
    //      * <pre>
    //      * 受注番号SEQ取得 追加
    //      * </pre>
    //      *
    //      */

    /**
     * 受注番号SEQを取得する<br/>
     * 受注番号採番のために必要な受注番号Seq取得<br/>
     *
     * @return 受注番号SEQ
     */
    @Select
    Integer getOrderNoSeqNextVal();

    // PDR Migrate Customization to here

    // 2023-renew No26 from here

    /**
     * 受注サマリ件数取得
     *
     * @param memberInfoSeq 会員SEQ
     * @return 件数
     */
    @Select
    int getCountByMemberInfoSeqAndCurrentDay(Integer memberInfoSeq);

    // 2023-renew No26 to here
    // 2023-renew No21 from here
    /**
     * よく一緒に購入される商品クラスリスト取得
     *
     * @param goodsGroupSeq 商品グループSEQ
     * @return よく一緒に購入される商品クラスリスト
     */
    @Select
    List<GoodsTogetherBuyGroupEntity> getGoodsTogetherBuy(Integer goodsGroupSeq);
    // 2023-renew No21 to here
}
