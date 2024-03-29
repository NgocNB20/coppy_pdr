/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic;

import com.gmo_pg.g_pay.client.output.EntryExecTranOutput;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBillStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBillType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCancelFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCarrierType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEmergencyFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeProcessType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeRepeatPurchaseType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReservationDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalesFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeTotalingType;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.CardDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderTempDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.CardBrandEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderReceiptOfMoneyEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.delivery.OrderDeliveryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.index.OrderIndexEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.member.SimultaneousOrderExclusionEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.orderperson.OrderPersonEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.settlement.OrderSettlementEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.coupon.CouponEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.CardBrandGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.CardBrandGetMaxCardBrandSeqLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.CardBrandRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.CardRegistUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.CreditEntryExecTranLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.DeleteCardLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.SaveMemberLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.TradedCardLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.NewOrderSeqGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderBillRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderDeliveryRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderGoodsListDeleteLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderIndexRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderPersonRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderReceiptOfMoneyRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderReserveStockHoldLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderReserveStockReleaseLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSettlementRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryCountGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.member.SimultaneousOrderExclusionGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.member.SimultaneousOrderExclusionRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.member.SimultaneousOrderExclusionUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.MulPayUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelException;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ZenHanConversionUtility;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 注文内容をもとに受注情報を登録する抽象クラス<br/>
 * 受注登録処理を行なう場合、このクラスを継承してください。<br/>
 *
 * @author ozaki
 * @author marutani チケット #2192 対応
 * @author tomo (itec) 2011/08/24 #2719「マルチペイメント決済通信エラーになると引き当てた在庫が開放されない」対応
 * @author Nishigaki Mio (itec) 2011/09/01 チケット #2525 対応
 * @author Kaneko (itec) 2011/09/08 #2726 対応
 * @author matsumoto (itec) 2011/12/28 #2769 対応
 * @author matsumoto(itec) 2012/02/06 #2761 対応
 * @author Kaneko (itec) 2012/08/09 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 * @author Kaneko(itec) 2012/09/26 UTF-8のプロパティファイルを読み込めるように対応
 * @author tomo(itec) 2012/09/27    InstantMailをInstantMailHelperへ変更。
 */
@Component
@Data
public abstract class AbstractOrderRegisterLogic extends AbstractShopLogic implements OrderRegistable {

    /**
     * メッセージコード：利用ポイント不足エラー
     */
    protected static final String MSGCD_USEPART_EXCEED_ACTIVATEPOINT = "POINT-0001-010-L-E";

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractOrderRegisterLogic.class);

    /**
     * 変換Utility
     */
    protected final ConversionUtility conversionUtility;

    /**
     * 全角、半角の変換Utility
     */
    protected final ZenHanConversionUtility zenHanConversionUtility;

    /**
     * 日付関連Utility
     */
    protected final DateUtility dateUtility;

    /**
     * 受注関連Utility
     */
    protected final OrderUtility orderUtility;

    /**
     * マルチペイメントUtility
     */
    protected final MulPayUtility mulPayUtility;

    /**
     * 新規受注SEQ取得ロジック
     */
    protected final NewOrderSeqGetLogic newOrderSeqGetLogic;

    /**
     * 受注サマリカウント取得ロジック
     */
    protected final OrderSummaryCountGetLogic orderSummaryCountGetLogic;

    /**
     * 受注サマリ登録ロジック
     */
    protected final OrderSummaryRegistLogic orderSummaryRegistLogic;

    /**
     * 受注ご注文主登録ロジック
     */
    protected final OrderPersonRegistLogic orderPersonRegistLogic;

    /**
     * 受注配送登録ロジック
     */
    protected final OrderDeliveryRegistLogic orderDeliveryRegistLogic;

    /**
     * 受注決済登録ロジック
     */
    protected final OrderSettlementRegistLogic orderSettlementRegistLogic;

    /**
     * 受注請求登録ロジック
     */
    protected final OrderBillRegistLogic orderBillRegistLogic;

    /**
     * 受注入金登録ロジック
     */
    protected final OrderReceiptOfMoneyRegistLogic orderReceiptOfMoneyRegistLogic;

    /**
     * 受注インデックス登録ロジック
     */
    protected final OrderIndexRegistLogic orderIndexRegistLogic;

    /**
     * 在庫確保ロジック
     */
    protected final OrderReserveStockHoldLogic orderReserveStockHoldLogic;

    /**
     * 在庫解放ロジック
     */
    protected final OrderReserveStockReleaseLogic orderReserveStockReleaseLogic;

    /**
     * 受注商品リスト削除ロジック
     */
    protected final OrderGoodsListDeleteLogic orderGoodsListDeleteLogic;

    /**
     * カードブランド情報取得ロジック
     */
    protected final CardBrandGetLogic cardBrandGetLogic;

    /**
     * MAXカードブランドSEQ取得ロジック
     */
    protected final CardBrandGetMaxCardBrandSeqLogic cardBrandGetMaxCardBrandSeqLogic;

    /**
     * カードブランド情報登録ロジック
     */
    protected final CardBrandRegistLogic cardBrandRegistLogic;

    /**
     * カード削除通信ロジック
     */
    protected final DeleteCardLogic deleteCardLogic;

    /**
     * カード登録Logic
     */
    protected final CardRegistUpdateLogic cardRegistUpdateLogic;

    /**
     * クレジット取引登録・決済通信ロジック
     */
    protected final CreditEntryExecTranLogic creditEntryExecTranLogic;

    /**
     * 同時注文排他情報取得ロジック
     */
    protected final SimultaneousOrderExclusionGetLogic simultaneousOrderExclusionGetLogic;

    /**
     * 同時注文排他情報登録ロジック
     */
    protected final SimultaneousOrderExclusionRegistLogic simultaneousOrderExclusionRegistLogic;

    /**
     * 同時注文排他情報更新ロジック
     */
    protected final SimultaneousOrderExclusionUpdateLogic simultaneousOrderExclusionUpdateLogic;

    /**
     * 会員登録ロジック
     */
    protected final SaveMemberLogic saveMemberLogic;

    /**
     * 決済後カード登録ロジック
     */
    protected final TradedCardLogic tradedCardLogic;

    @Autowired
    public AbstractOrderRegisterLogic(ConversionUtility conversionUtility,
                                      ZenHanConversionUtility zenHanConversionUtility,
                                      DateUtility dateUtility,
                                      OrderUtility orderUtility,
                                      MulPayUtility mulPayUtility,
                                      NewOrderSeqGetLogic newOrderSeqGetLogic,
                                      OrderSummaryCountGetLogic orderSummaryCountGetLogic,
                                      OrderSummaryRegistLogic orderSummaryRegistLogic,
                                      OrderPersonRegistLogic orderPersonRegistLogic,
                                      OrderDeliveryRegistLogic orderDeliveryRegistLogic,
                                      OrderSettlementRegistLogic orderSettlementRegistLogic,
                                      OrderBillRegistLogic orderBillRegistLogic,
                                      OrderReceiptOfMoneyRegistLogic orderReceiptOfMoneyRegistLogic,
                                      OrderIndexRegistLogic orderIndexRegistLogic,
                                      OrderReserveStockHoldLogic orderReserveStockHoldLogic,
                                      OrderReserveStockReleaseLogic orderReserveStockReleaseLogic,
                                      OrderGoodsListDeleteLogic orderGoodsListDeleteLogic,
                                      CardBrandGetLogic cardBrandGetLogic,
                                      CardBrandGetMaxCardBrandSeqLogic cardBrandGetMaxCardBrandSeqLogic,
                                      CardBrandRegistLogic cardBrandRegistLogic,
                                      DeleteCardLogic deleteCardLogic,
                                      CardRegistUpdateLogic cardRegistUpdateLogic,
                                      CreditEntryExecTranLogic creditEntryExecTranLogic,
                                      SimultaneousOrderExclusionGetLogic simultaneousOrderExclusionGetLogic,
                                      SimultaneousOrderExclusionRegistLogic simultaneousOrderExclusionRegistLogic,
                                      SimultaneousOrderExclusionUpdateLogic simultaneousOrderExclusionUpdateLogic,
                                      SaveMemberLogic saveMemberLogic,
                                      TradedCardLogic tradedCardLogic) {

        this.conversionUtility = conversionUtility;
        this.zenHanConversionUtility = zenHanConversionUtility;
        this.dateUtility = dateUtility;
        this.orderUtility = orderUtility;
        this.mulPayUtility = mulPayUtility;
        this.newOrderSeqGetLogic = newOrderSeqGetLogic;
        this.orderSummaryCountGetLogic = orderSummaryCountGetLogic;
        this.orderSummaryRegistLogic = orderSummaryRegistLogic;
        this.orderPersonRegistLogic = orderPersonRegistLogic;
        this.orderDeliveryRegistLogic = orderDeliveryRegistLogic;
        this.orderSettlementRegistLogic = orderSettlementRegistLogic;
        this.orderBillRegistLogic = orderBillRegistLogic;
        this.orderReceiptOfMoneyRegistLogic = orderReceiptOfMoneyRegistLogic;
        this.orderIndexRegistLogic = orderIndexRegistLogic;
        this.orderReserveStockHoldLogic = orderReserveStockHoldLogic;
        this.orderReserveStockReleaseLogic = orderReserveStockReleaseLogic;
        this.orderGoodsListDeleteLogic = orderGoodsListDeleteLogic;
        this.cardBrandGetLogic = cardBrandGetLogic;
        this.cardBrandGetMaxCardBrandSeqLogic = cardBrandGetMaxCardBrandSeqLogic;
        this.cardBrandRegistLogic = cardBrandRegistLogic;
        this.deleteCardLogic = deleteCardLogic;
        this.cardRegistUpdateLogic = cardRegistUpdateLogic;
        this.creditEntryExecTranLogic = creditEntryExecTranLogic;
        this.simultaneousOrderExclusionGetLogic = simultaneousOrderExclusionGetLogic;
        this.simultaneousOrderExclusionRegistLogic = simultaneousOrderExclusionRegistLogic;
        this.simultaneousOrderExclusionUpdateLogic = simultaneousOrderExclusionUpdateLogic;
        this.saveMemberLogic = saveMemberLogic;
        this.tradedCardLogic = tradedCardLogic;
    }

    /**
     * 受注情報登録の処理。<br/>
     * 受注情報を登録する際の受注ＳＥＱ採番と在庫確保を行ないます。<br/>
     *
     * @param receiveOrderDto 受注DTO
     */
    @Override
    public void execute(ReceiveOrderDto receiveOrderDto,
                        String memberInfoId,
                        HTypeSiteType siteType,
                        HTypeDeviceType deviceType,
                        Integer memberInfoSeq,
                        String userAgent,
                        String administratorLastName,
                        String administratorFirstName) {

        try {

            // 前処理
            // →在庫確保や決済会社との通信を行うなど
            LOGGER.debug("受注登録前処理を実行");
            this.beforeProcess(receiveOrderDto, siteType, deviceType);

            // 本処理
            // →受注情報を登録するなど
            LOGGER.debug("受注登録本処理を実行");
            this.process(receiveOrderDto, memberInfoId, siteType, memberInfoSeq, userAgent, administratorLastName,
                         administratorFirstName
                        );

            // 後処理
            LOGGER.debug("受注登録後処理を実行");
            this.afterProcess(receiveOrderDto, memberInfoSeq);

        } catch (AppLevelListException e) {

            LOGGER.error("受注登録処理時に例外が発生", e);

            // 在庫解放処理
            rollbackOrderGoods(receiveOrderDto);

            // 管理者へ受注登録失敗を通知するメールを送信
            orderUtility.sendAdministratorErrorMail(receiveOrderDto, e);

            throw e;
        } catch (RuntimeException e) {

            LOGGER.error("受注登録処理時に例外が発生", e);

            // 在庫解放処理
            rollbackOrderGoods(receiveOrderDto);

            // 管理者へ受注登録失敗を通知するメールを送信
            orderUtility.sendAdministratorErrorMail(receiveOrderDto, e);

            throw e;
        }

    }

    /**
     * 受注情報登録の前処理。<br/>
     * 受注情報を登録する際の受注ＳＥＱ採番と在庫確保を行ないます。<br/>
     *
     * @param receiveOrderDto 受注DTO
     */
    protected void beforeProcess(ReceiveOrderDto receiveOrderDto, HTypeSiteType siteType, HTypeDeviceType deviceType) {

        // パラメータチェック
        argumentCheck(receiveOrderDto);

        // 受注SEQ採番
        getNewOrderSeq(receiveOrderDto, siteType, deviceType);

        // 在庫確保処理
        try {
            // 受注商品の登録と在庫の確保
            registOrderGoodsAndStock(receiveOrderDto);
        } catch (Exception e) {

            if (e instanceof AppLevelListException) {
                throw (AppLevelListException) e;
            }

            LOGGER.error("在庫確保処理でエラーが発生しました。", e);
            throwMessage(MSGCD_UPDATE_STOCK_SYS_FAIL, null, e);
        }

    }

    /**
     * 受注情報登録処理<br/>
     * 注文内容をもとに受注情報の登録を行ないます。<br/>
     * このメソッドは、前処理で受注番号の採番と在庫確保が行なわれていることが前提です。
     *
     * @param receiveOrderDto 受注DTO
     */
    protected void process(ReceiveOrderDto receiveOrderDto,
                           String memberInfoId,
                           HTypeSiteType siteType,
                           Integer memberInfoSeq,
                           String userAgent,
                           String administratorLastName,
                           String administratorFirstName) {

        try {

            // 定期注文の場合は定期元帳を登録する
            if (receiveOrderDto.isPeriodicOrderFlag()) {
                // 初回決済方法
                HTypeSettlementMethodType firstSettlement =
                                receiveOrderDto.getOrderSettlementEntity().getSettlementMethodType();
                // 次回決済方法
                HTypeSettlementMethodType nextSettlement =
                                receiveOrderDto.getNextSettlementMethodEntity().getSettlementMethodType();

                // 初回がクレジット以外、次回の決済方法がクレジットの場合はカード登録を行う
                if (HTypeSettlementMethodType.CREDIT != firstSettlement
                    && HTypeSettlementMethodType.CREDIT == nextSettlement) {

                    // 受注情報よりカード預かり情報を作成する
                    CardDto cardDto = createCardDto(receiveOrderDto, memberInfoSeq, memberInfoId, siteType);
                    // 入力されたカード情報にて有効性チェックを行う
                    EntryExecTranOutput output = creditEntryExecTranLogic.executeCheck(cardDto);
                    if (output.getEntryTranOutput().isErrorOccurred() || output.getExecTranOutput().isErrorOccurred()) {
                        List<CheckMessageDto> checkMessageDtoList = creditEntryExecTranLogic.checkOutput(output);
                        for (CheckMessageDto dto : checkMessageDtoList) {
                            addErrorMessage(dto.getMessageId(), dto.getArgs());
                        }
                        throwMessage();
                    }

                    try {
                        OrderTempDto tmpDto = receiveOrderDto.getOrderTempDto();
                        // 登録済みカードがあり、今回はそのカード使用しない かつ、別のカードを保存する場合、
                        // 登録済みカードを削除する
                        if (tmpDto.isRegistCredit() && !tmpDto.isUseRegistCardFlg()) {
                            deleteCardLogic.execute(receiveOrderDto);
                        }
                        // 登録済みカードがなく、カードを保存する場合、会員登録を行う
                        if (!tmpDto.isRegistCredit() && tmpDto.isSaveFlg()) {
                            saveMemberLogic.execute(receiveOrderDto);
                        }
                        // カードを登録する
                        if (!tmpDto.isUseRegistCardFlg()) {
                            tradedCardLogic.execute(receiveOrderDto, output.getExecTranOutput().getOrderId());
                        }

                    } catch (AppLevelListException app) {
                        LOGGER.error("例外処理が発生しました", app);
                        // スローされたメッセージを付け替える
                        for (AppLevelException e : app.getErrorList()) {
                            if (e.getMessageCode().startsWith(CardRegistUpdateLogic.MSGCD_ENTRY_CARD_ERR)) {
                                throwMessage(MSGCD_ENTRY_CARD_ERR);
                            } else {
                                throwMessage(MSGCD_GMO_INPUT_ERR);
                            }
                        }
                    }
                }
            }

            // 受注サマリー登録
            registOrderSummary(receiveOrderDto, memberInfoId, siteType, memberInfoSeq, userAgent);
            // 受注ご注文主登録
            registOrderPerson(receiveOrderDto);
            // 受注配送登録
            registOrderDelivery(receiveOrderDto);
            // 受注決済登録
            registOrderSettlement(receiveOrderDto);
            // 受注請求登録
            registOrderBill(receiveOrderDto);
            // 受注入金登録（前請求のクレジット決済の場合のみ処理を行なう）
            HTypeSettlementMethodType settlementMethodType =
                            receiveOrderDto.getOrderSettlementEntity().getSettlementMethodType();
            HTypeBillType billType = receiveOrderDto.getOrderSettlementEntity().getBillType();
            if (settlementMethodType.equals(HTypeSettlementMethodType.CREDIT) && billType.equals(
                            HTypeBillType.PRE_CLAIM)) {
                registOrderReceiptOfMoney(receiveOrderDto);
            }
            // その他受注情報登録
            registOther(receiveOrderDto);
            // 受注インデックス登録
            registOrderIndex(receiveOrderDto, administratorLastName, administratorFirstName);
            // カードブランド登録（クレジット決済の場合のみ処理を行なう）
            if (settlementMethodType.equals(HTypeSettlementMethodType.CREDIT)) {
                registCardBrand(receiveOrderDto);
            }
            // クーポン利用がない場合は処理終了
            if (orderUtility.isMember(memberInfoId, siteType, receiveOrderDto) && receiveOrderDto.getCoupon() != null) {
                // 同時注文排他チェックを行う
                checkSimultaneousOrderExclusion(receiveOrderDto);
                // 同時注文排他情報を登録、更新する
                registUpdateSimultaneousOrderExclusion(receiveOrderDto);
            }

        } catch (Exception e) {

            // #2719
            // 在庫戻し処理を除去。
            // 受注登録メソッド内で在庫戻し処理は行わない。
            // 呼び出し元で確保した商品を在庫に戻す処理を行う。

            // #2719
            // メール送信処理を除去。
            // ここではGMOが返したエラー内容を管理者に通知するメッセージは送信しない
            // 呼び出し元で受注登録に失敗したメールを管理者に送る。

            // エラーメッセージが登録済みかつAppLevelListException型の場合は、そのままスロー
            if (e instanceof AppLevelListException) {
                throw (AppLevelListException) e;
            }

            // 同時注文排他エラー
            if (e instanceof DataAccessException) {
                throwMessage(MSGCD_SIMULTANEOUSORDEREXCLUSION_ERR);
            }

            LOGGER.error("注文登録処理でエラーが発生しました。登録した受注商品と、引き当てた在庫を戻します。", e);
            throwMessage(MSGCD_ORDER_REGIST_FAIL);
        }
    }

    /**
     * 確保している商品を解放する処理
     *
     * @param order 受注DTO
     */
    protected void rollbackOrderGoods(ReceiveOrderDto order) {

        List<OrderGoodsEntity> goodsList = orderUtility.getALLGoodsEntityList(order);

        if (goodsList == null || goodsList.isEmpty()) {
            LOGGER.debug("確保している在庫はありません。");
            return;
        }

        Integer orderSeq = order.getOrderSummaryEntity().getOrderSeq();
        Integer versionNo = goodsList.get(0).getOrderGoodsVersionNo();
        LOGGER.debug(String.format("確保した在庫を解放します。orderSeq=%d, orderGoodsVersionNo=%d", orderSeq, versionNo));

        orderReserveStockReleaseLogic.execute(orderSeq, versionNo);

    }

    /**
     * 受注情報登録の後処理
     *
     * @param receiveOrderDto 受注DTO
     * @param memberInfoSeq 会員SEQ
     */
    protected void afterProcess(ReceiveOrderDto receiveOrderDto, Integer memberInfoSeq) {
        // 特に処理無し
        // 必要に応じて適宜サブクラスでオーバーライドする
    }

    /**
     * 引数チェック
     *
     * @param receiveOrderDto 受注DTO
     */
    protected void argumentCheck(ReceiveOrderDto receiveOrderDto) {
        ArgumentCheckUtil.assertNotNull("receiveOrderDto", receiveOrderDto);
        // ArgumentCheckUtil.assertNotEmpty("orderGoodsEntityList",
        // receiveOrderDto.orderGoodsEntityList);
        // ArgumentCheckUtil.assertNotNull("deliveryMethodEntity",
        // receiveOrderDto.deliveryMethodEntity);
        // ArgumentCheckUtil.assertNotNull("deliveryMethodSeq",
        // receiveOrderDto.deliveryMethodEntity.getDeliveryMethodSeq());
        ArgumentCheckUtil.assertNotNull("settlementMethodEntity", receiveOrderDto.getSettlementMethodEntity());
        ArgumentCheckUtil.assertNotNull(
                        "settlementMethodSeq", receiveOrderDto.getSettlementMethodEntity().getSettlementMethodSeq());
    }

    /**
     * 受注SEQ採番<br/>
     * 受注SEQを採番し、受注サマリに設定する。<br/>
     *
     * @param receiveOrderDto 受注DTO
     */
    protected void getNewOrderSeq(ReceiveOrderDto receiveOrderDto, HTypeSiteType siteType, HTypeDeviceType deviceType) {
        // 受注SEQ採番
        Integer newOrderSeq = newOrderSeqGetLogic.execute();
        // 現在日時取得
        Timestamp currentTime = dateUtility.getCurrentTime();
        // 受注コード作成
        String orderCode = orderUtility.generateOrderCode(newOrderSeq, currentTime);
        // キャリア種別取得
        HTypeCarrierType carrierType = null;
        if (siteType.isFront()) {
            // フロントサイトの場合登録直前にPCサイトとSPサイトが切替される場合がある為、再度サイト種別を取得しなおす
            receiveOrderDto.getOrderSummaryEntity().setOrderSiteType(siteType);
            receiveOrderDto.getOrderSummaryEntity().setOrderDeviceType(deviceType);
        } else {
            // バックからの注文時はPC
            carrierType = HTypeCarrierType.PC;
        }
        // 受注履歴連番
        Integer orderVersionNo = 1;

        // 取得した情報＆初期値を受注サマリーにセット
        receiveOrderDto.getOrderSummaryEntity().setOrderSeq(newOrderSeq);
        receiveOrderDto.getOrderSummaryEntity().setOrderCode(orderCode);
        receiveOrderDto.getOrderSummaryEntity().setCarrierType(carrierType);
        receiveOrderDto.getOrderSummaryEntity().setOrderTime(currentTime);
        receiveOrderDto.getOrderSummaryEntity().setOrderVersionNo(orderVersionNo);
        receiveOrderDto.getOrderSummaryEntity().setReceiptPriceTotal(new BigDecimal(0));
    }

    /**
     * 受注商品の登録と在庫の確保を行う<br/>
     * 新規トランザクションで実行
     *
     * @param receiveOrderDto 受注DTO
     */
    public void registOrderGoodsAndStock(ReceiveOrderDto receiveOrderDto) {
        orderReserveStockHoldLogic.execute(receiveOrderDto);
    }

    /**
     * 受注サマリ登録
     *
     * @param receiveOrderDto 受注DTO
     */
    protected void registOrderSummary(ReceiveOrderDto receiveOrderDto,
                                      String memberInfoId,
                                      HTypeSiteType siteType,
                                      Integer commonInfoMemberInfoSeq,
                                      String userAgent) {
        // 注文DTOから受注サマリエンティティを取得
        OrderSummaryEntity orderSummaryEntity = receiveOrderDto.getOrderSummaryEntity();

        // 会員SEQ
        Integer memberInfoSeq = null;
        if (siteType.isBack() && receiveOrderDto.getOrderPersonEntity().getMemberInfoSeq() != null) {
            // バック会員
            memberInfoSeq = receiveOrderDto.getOrderPersonEntity().getMemberInfoSeq();
        } else if (StringUtils.isNotEmpty(memberInfoId)) {
            // フロント会員
            memberInfoSeq = commonInfoMemberInfoSeq;
        } else {
            // ゲスト
            memberInfoSeq = 0;
        }
        orderSummaryEntity.setMemberInfoSeq(memberInfoSeq);

        // 売上フラグ
        orderSummaryEntity.setSalesFlag(HTypeSalesFlag.OFF);
        // キャンセルフラグ
        orderSummaryEntity.setCancelFlag(HTypeCancelFlag.OFF);

        // 受注状態
        HTypeSettlementMethodType settlementMethodType =
                        receiveOrderDto.getOrderSettlementEntity().getSettlementMethodType();
        HTypeBillType billType = receiveOrderDto.getOrderSettlementEntity().getBillType();
        if (!settlementMethodType.equals(HTypeSettlementMethodType.CREDIT) && billType.equals(
                        HTypeBillType.PRE_CLAIM)) {
            // 選択された決済タイプがクレジット以外の前請求の場合、『入金確認中』を設定
            orderSummaryEntity.setOrderStatus(HTypeOrderStatus.PAYMENT_CONFIRMING);
        } else {
            // 選択された決済タイプがクレジット、もしくは後請求の場合、『商品準備中』を設定
            orderSummaryEntity.setOrderStatus(HTypeOrderStatus.GOODS_PREPARING);
        }

        // リピート購入種別
        if (memberInfoSeq == null || memberInfoSeq.intValue() == 0) {
            // ゲストの場合は、『ゲスト』を設定
            orderSummaryEntity.setRepeatPurchaseType(HTypeRepeatPurchaseType.GUEST);
        } else {
            int count = orderSummaryCountGetLogic.execute(memberInfoSeq);
            if (count == 0) {
                // 会員で以前に購入が無い場合は、『会員初回購入』を設定
                orderSummaryEntity.setRepeatPurchaseType(HTypeRepeatPurchaseType.MEMBER_FIRST);
            } else {
                // 会員で以前に購入がある場合は、『会員２回目以降購入』を設定
                orderSummaryEntity.setRepeatPurchaseType(HTypeRepeatPurchaseType.MEMBER_REPEATER);
            }
        }

        // 入金累計（前請求クレジットの時のみ設定）
        if (settlementMethodType.equals(HTypeSettlementMethodType.CREDIT) && billType.equals(HTypeBillType.PRE_CLAIM)) {
            orderSummaryEntity.setReceiptPriceTotal(orderSummaryEntity.getOrderPrice());
        }

        // 設計書修正必要 OZAKI
        orderSummaryEntity.setSettlementMailRequired(
                        receiveOrderDto.getSettlementMethodEntity().getSettlementMailRequired());

        // 割引決済利用時は受注状態を【商品準備中とする】
        if (settlementMethodType == HTypeSettlementMethodType.DISCOUNT) {
            orderSummaryEntity.setOrderStatus(HTypeOrderStatus.GOODS_PREPARING);
        }

        orderSummaryEntity.setUserAgent(userAgent);

        // 注文種別
        orderSummaryEntity.setOrderType(orderUtility.getOrderType(receiveOrderDto));

        // 受注サマリ登録ロジック実行
        orderSummaryRegistLogic.execute(orderSummaryEntity);

    }

    /**
     * 受注ご注文主登録
     *
     * @param receiveOrderDto 受注DTO
     */
    protected void registOrderPerson(ReceiveOrderDto receiveOrderDto) {
        // 受注サマリエンティティを取得
        OrderSummaryEntity orderSummaryEntity = receiveOrderDto.getOrderSummaryEntity();
        // 注文DTOから受注ご注文主エンティティを取得
        OrderPersonEntity orderPersonEntity = receiveOrderDto.getOrderPersonEntity();

        // ショップSEQ
        orderPersonEntity.setShopSeq(orderSummaryEntity.getShopSeq());
        // 会員SEQ
        orderPersonEntity.setMemberInfoSeq(orderSummaryEntity.getMemberInfoSeq());
        // 受注SEQ
        orderPersonEntity.setOrderSeq(orderSummaryEntity.getOrderSeq());
        // 受注ご注文主連番
        orderPersonEntity.setOrderPersonVersionNo(1);

        // 受注ご注文主登録ロジック実行
        orderPersonRegistLogic.execute(orderPersonEntity);
    }

    /**
     * 受注配送登録
     *
     * @param receiveOrderDto 受注DTO
     */
    protected void registOrderDelivery(ReceiveOrderDto receiveOrderDto) {
        // 受注サマリエンティティを取得
        OrderSummaryEntity orderSummaryEntity = receiveOrderDto.getOrderSummaryEntity();
        // 注文DTOから受注配送エンティティを取得
        OrderDeliveryEntity orderDeliveryEntity = receiveOrderDto.getOrderDeliveryDto().getOrderDeliveryEntity();
        // ショップSEQ
        orderDeliveryEntity.setShopSeq(orderSummaryEntity.getShopSeq());
        // 受注SEQ
        orderDeliveryEntity.setOrderSeq(orderSummaryEntity.getOrderSeq());
        // 受注配送連番
        orderDeliveryEntity.setOrderDeliveryVersionNo(1);
        // 予約配送フラグ
        orderDeliveryEntity.setReservationDeliveryFlag(HTypeReservationDeliveryFlag.OFF);
        // 受注配送登録ロジック実行
        orderDeliveryRegistLogic.execute(orderDeliveryEntity);
    }

    /**
     * 受注決済登録
     *
     * @param receiveOrderDto 受注DTO
     */
    protected void registOrderSettlement(ReceiveOrderDto receiveOrderDto) {
        // 受注サマリエンティティを取得
        OrderSummaryEntity orderSummaryEntity = receiveOrderDto.getOrderSummaryEntity();
        // 受注決済エンティティを取得
        OrderSettlementEntity orderSettlementEntity = receiveOrderDto.getOrderSettlementEntity();

        // ショップSEQ
        orderSettlementEntity.setShopSeq(orderSummaryEntity.getShopSeq());
        // 受注日付
        orderSettlementEntity.setOrderDate(orderSummaryEntity.getOrderTime());
        // 受注SEQ
        orderSettlementEntity.setOrderSeq(orderSummaryEntity.getOrderSeq());
        // 受注決済連番
        orderSettlementEntity.setOrderSettlementVersionNo(1);
        // 受注コード
        orderSettlementEntity.setOrderCode(orderSummaryEntity.getOrderCode());
        // PDR Migrate Customization from here
        // キャリア種別
        orderSettlementEntity.setCarrierType(orderSummaryEntity.getCarrierType());
        // PDR Migrate Customization to here
        // 集計種別
        orderSettlementEntity.setTotalingType(HTypeTotalingType.ORDER);
        // 売上フラグ
        orderSettlementEntity.setSalesFlag(HTypeSalesFlag.OFF);
        // 処理日時
        orderSettlementEntity.setProcessTime(orderSummaryEntity.getOrderTime());

        // 処理日（yyyyMMdd）
        String[] ymdArray = conversionUtility.toYmdArray(orderSummaryEntity.getOrderTime());
        orderSettlementEntity.setProcessYmd(ymdArray[0] + ymdArray[1] + ymdArray[2]);
        // 処理月（yyyyMM）
        orderSettlementEntity.setProcessYm(ymdArray[0] + ymdArray[1]);
        // 都道府県種別
        orderSettlementEntity.setPrefectureType(receiveOrderDto.getOrderPersonEntity().getPrefectureType());
        // ご注文主性別
        orderSettlementEntity.setOrderSex(receiveOrderDto.getOrderPersonEntity().getOrderSex());
        // ご注文主年代
        orderSettlementEntity.setOrderAgeType(receiveOrderDto.getOrderPersonEntity().getOrderAgeType());
        // リピート購入種別
        orderSettlementEntity.setRepeatPurchaseType(orderSummaryEntity.getRepeatPurchaseType());
        // 受注サイト種別
        orderSettlementEntity.setOrderSiteType(orderSummaryEntity.getOrderSiteType());
        // 受注デバイス種別
        orderSettlementEntity.setOrderDeviceType(orderSummaryEntity.getOrderDeviceType());

        // クレジットカード会社コード
        HTypeSettlementMethodType settlementMethodType =
                        receiveOrderDto.getOrderSettlementEntity().getSettlementMethodType();
        if (HTypeSettlementMethodType.CREDIT == settlementMethodType) {
            MulPayBillEntity mulPayBillEntity = receiveOrderDto.getMulPayBillEntity();
            // PDR Migrate Customization from here
            if (mulPayBillEntity != null) {
                orderSettlementEntity.setCreditCompanyCode(mulPayBillEntity.getForward());
            }
            // PDR Migrate Customization to here
        }

        // 受注決済登録ロジック実行
        orderSettlementRegistLogic.execute(orderSettlementEntity);
    }

    // =================================================================
    // ※ registOrderGoodsメソッドは、OrderReserveStockHoldLogicに移動しました
    // =================================================================

    /**
     * 受注請求登録
     *
     * @param receiveOrderDto 受注DTO
     */
    protected void registOrderBill(ReceiveOrderDto receiveOrderDto) {
        // 受注サマリエンティティを取得
        OrderSummaryEntity orderSummaryEntity = receiveOrderDto.getOrderSummaryEntity();
        // 受注請求エンティティを取得
        OrderBillEntity orderBillEntity = receiveOrderDto.getOrderBillEntity();
        // 決済方法エンティティを取得
        SettlementMethodEntity settlementMethodEntity = receiveOrderDto.getSettlementMethodEntity();

        // ショップSEQ
        orderBillEntity.setShopSeq(orderSummaryEntity.getShopSeq());
        // 受注SEQ
        orderBillEntity.setOrderSeq(orderSummaryEntity.getOrderSeq());
        // 受注請求連番
        orderBillEntity.setOrderBillVersionNo(1);
        // 請求日時
        orderBillEntity.setBillTime(orderSummaryEntity.getOrderTime());
        // 請求状態
        HTypeBillType billType = receiveOrderDto.getOrderSettlementEntity().getBillType();
        if (billType.equals(HTypeBillType.PRE_CLAIM)) {
            // 前請求の時は「請求済み」
            orderBillEntity.setBillStatus(HTypeBillStatus.BILL_CLAIM);
        } else {
            // 後請求の時は「未請求」
            orderBillEntity.setBillStatus(HTypeBillStatus.BILL_NO_CLAIM);
        }
        // クレジット決済の場合、定期注文の請求エラー時以外は「支払期限猶予日数」、「期限後ｷｬﾝｾﾙ可能日数」を 0 で初期化
        if (HTypeSettlementMethodType.CREDIT == settlementMethodEntity.getSettlementMethodType()) {
            if (!(HTypeOrderType.PERIODIC == orderSummaryEntity.getOrderType()
                  && HTypeEmergencyFlag.ON == orderBillEntity.getEmergencyFlag())) {
                settlementMethodEntity.setPaymentTimeLimitDayCount(0);
                settlementMethodEntity.setCancelTimeLimitDayCount(0);
            }
        }
        // 支払期限日
        Timestamp paymentTimeLimitDate =
                        calcPaymentTimeLimitDate(settlementMethodEntity, orderSummaryEntity.getOrderTime());
        orderBillEntity.setPaymentTimeLimitDate(paymentTimeLimitDate);
        // キャンセル可能日
        Timestamp cancelableDate = calcCancelableDate(settlementMethodEntity, paymentTimeLimitDate);
        orderBillEntity.setCancelableDate(cancelableDate);

        // クレジットカード会社コード
        HTypeSettlementMethodType settlementMethodType =
                        receiveOrderDto.getOrderSettlementEntity().getSettlementMethodType();
        if (HTypeSettlementMethodType.CREDIT == settlementMethodType) {
            MulPayBillEntity mulPayBillEntity = receiveOrderDto.getMulPayBillEntity();
            // PDR Migrate Customization from here
            if (mulPayBillEntity != null) {
                orderBillEntity.setCreditCompanyCode(mulPayBillEntity.getForward());
            }
            // PDR Migrate Customization to here
        }

        // 受注請求登録ロジック実行
        orderBillRegistLogic.execute(orderBillEntity);
    }

    /**
     * 支払期限日を計算する。<br/>
     * <pre>
     * 受注日時に支払期限猶予日数を加算した値(支払期限日)を返す。
     * 支払期限猶予日数がnullまたは0の場合はnullを返す。
     * 　</pre>
     *
     * @param entity       決済方法エンティティ
     * @param orderTime    受注日時
     * @param customParams 案件用引数
     * @return 支払期限日
     */
    protected Timestamp calcPaymentTimeLimitDate(SettlementMethodEntity entity,
                                                 Timestamp orderTime,
                                                 Object... customParams) {
        // 支払期限猶予日数取得
        Integer paymentTimeLimitDayCount = entity.getPaymentTimeLimitDayCount();
        if (paymentTimeLimitDayCount == null || paymentTimeLimitDayCount == 0) {
            return null;
        }
        // 受注日時に支払期限猶予日数を加算
        Timestamp paymentTimeLimitDate = dateUtility.getAmountDayTimestamp(paymentTimeLimitDayCount, true, orderTime);
        return paymentTimeLimitDate;
    }

    /**
     * キャンセル可能日を計算する。<br/>
     * <pre>
     * 支払期限日時に期限後取消猶予日数を加算した値(キャンセル可能日)を返す。
     * 支払期限猶予日数がnullまたは0の場合はnullを返す。
     * 期限後取消猶予日数がnullの場合はnullを返す。
     * </pre>
     *
     * @param entity               決済方法エンティティ
     * @param paymentTimeLimitDate 支払期限日
     * @param customParams         案件用引数
     * @return キャンセル可能日
     */
    protected Timestamp calcCancelableDate(SettlementMethodEntity entity,
                                           Timestamp paymentTimeLimitDate,
                                           Object... customParams) {
        // 支払期限猶予日数取得
        Integer paymentTimeLimitDayCount = entity.getPaymentTimeLimitDayCount();
        if (paymentTimeLimitDayCount == null || paymentTimeLimitDayCount == 0) {
            return null;
        }
        // 期限後取消猶予日数取得
        Integer cancelTimeLimitDayCount = entity.getCancelTimeLimitDayCount();
        if (cancelTimeLimitDayCount == null) {
            return null;
        }
        // 支払期限日時に期限後取消猶予日数を加算
        Timestamp cancelableDate =
                        dateUtility.getAmountDayTimestamp(cancelTimeLimitDayCount, true, paymentTimeLimitDate);
        return cancelableDate;
    }

    /**
     * 受注入金登録
     *
     * @param receiveOrderDto 受注DTO
     */
    protected void registOrderReceiptOfMoney(ReceiveOrderDto receiveOrderDto) {
        // 受注サマリエンティティを取得
        OrderSummaryEntity orderSummaryEntity = receiveOrderDto.getOrderSummaryEntity();
        // 受注入金エンティティを取得
        OrderReceiptOfMoneyEntity orderReceiptOfMoneyEntity = getComponent(OrderReceiptOfMoneyEntity.class);

        // ショップSEQ
        orderReceiptOfMoneyEntity.setShopSeq(orderSummaryEntity.getShopSeq());
        // 受注SEQ
        orderReceiptOfMoneyEntity.setOrderSeq(orderSummaryEntity.getOrderSeq());
        // 受注入金連番
        orderReceiptOfMoneyEntity.setOrderReceiptOfMoneyVersionNo(1);
        // 入金日時
        orderReceiptOfMoneyEntity.setReceiptTime(orderSummaryEntity.getOrderTime());
        // 処理日（yyyyMMdd）
        String[] ymdArray = conversionUtility.toYmdArray(orderSummaryEntity.getOrderTime());
        // 入金年月日
        orderReceiptOfMoneyEntity.setReceiptYmd(ymdArray[0] + ymdArray[1] + ymdArray[2]);
        // 入金年月
        orderReceiptOfMoneyEntity.setReceiptYm(ymdArray[0] + ymdArray[1]);
        // 入金金額
        orderReceiptOfMoneyEntity.setReceiptPrice(orderSummaryEntity.getOrderPrice());
        // 入金累計
        orderReceiptOfMoneyEntity.setReceiptPriceTotal(orderSummaryEntity.getOrderPrice());
        // 受注日時
        orderReceiptOfMoneyEntity.setOrderTime(orderSummaryEntity.getOrderTime());
        // 受注サイト種別
        orderReceiptOfMoneyEntity.setOrderSiteType(orderSummaryEntity.getOrderSiteType());
        // 決済方法SEQ
        orderReceiptOfMoneyEntity.setSettlementMethodSeq(orderSummaryEntity.getSettlementMethodSeq());

        if (receiveOrderDto.getOrderReceiptOfMoneyEntityList() == null) {
            receiveOrderDto.setOrderReceiptOfMoneyEntityList(new ArrayList<>());
        }
        receiveOrderDto.getOrderReceiptOfMoneyEntityList().add(orderReceiptOfMoneyEntity);

        // 受注入金登録ロジック実行
        orderReceiptOfMoneyRegistLogic.execute(orderReceiptOfMoneyEntity);

    }

    /**
     * その他情報を登録（サブクラスで実装）
     *
     * @param receiveOrderDto 受注DTO
     */
    protected void registOther(ReceiveOrderDto receiveOrderDto) {
        // 特に処理無し
        // 必要に応じて適宜サブクラスでオーバーライドする
    }

    /**
     * 受注インデックス登録
     *
     * @param receiveOrderDto 受注DTO
     */
    protected void registOrderIndex(ReceiveOrderDto receiveOrderDto,
                                    String administratorLastName,
                                    String administratorFirstName) {

        // 受注インデックスエンティティを作成
        OrderIndexEntity orderIndexEntity = receiveOrderDto.getOrderIndexEntity();
        if (orderIndexEntity == null) {
            orderIndexEntity = getComponent(OrderIndexEntity.class);
        }
        OrderBillEntity orderBillEntity = receiveOrderDto.getOrderBillEntity();

        // 設計書修正必要（受注SEQ,ショップSEQ,処理日時） OZAKI
        // 受注SEQ
        orderIndexEntity.setOrderSeq(receiveOrderDto.getOrderSummaryEntity().getOrderSeq());
        // ショップSEQ
        orderIndexEntity.setShopSeq(receiveOrderDto.getOrderSummaryEntity().getShopSeq());
        // 処理日時
        orderIndexEntity.setProcessTime(receiveOrderDto.getOrderSummaryEntity().getOrderTime());
        // 受注履歴連番
        orderIndexEntity.setOrderVersionNo(receiveOrderDto.getOrderSummaryEntity().getOrderVersionNo());
        // 受注ご注文主連番
        orderIndexEntity.setOrderPersonVersionNo(receiveOrderDto.getOrderPersonEntity().getOrderPersonVersionNo());
        // 受注決済連番
        orderIndexEntity.setOrderSettlementVersionNo(
                        receiveOrderDto.getOrderSettlementEntity().getOrderSettlementVersionNo());
        // 受注請求連番
        orderIndexEntity.setOrderBillVersionNo(orderBillEntity.getOrderBillVersionNo());
        // 受注入金連番（前請求のクレジット決済の場合のみ処理を行なう）
        HTypeSettlementMethodType settlementMethodType =
                        receiveOrderDto.getOrderSettlementEntity().getSettlementMethodType();
        HTypeBillType billType = receiveOrderDto.getOrderSettlementEntity().getBillType();
        if (settlementMethodType.equals(HTypeSettlementMethodType.CREDIT) && billType.equals(HTypeBillType.PRE_CLAIM)) {
            OrderReceiptOfMoneyEntity orderReceiptOfMoneyEntity =
                            receiveOrderDto.getOrderReceiptOfMoneyEntityList().get(0);
            orderIndexEntity.setOrderReceiptOfMoneyVersionNo(
                            orderReceiptOfMoneyEntity.getOrderReceiptOfMoneyVersionNo());
        }

        // 受注配送連番
        OrderDeliveryDto orderDeliveryDto = receiveOrderDto.getOrderDeliveryDto();
        orderIndexEntity.setOrderDeliveryVersionNo(
                        orderDeliveryDto.getOrderDeliveryEntity().getOrderDeliveryVersionNo());
        // 受注商品連番
        OrderGoodsEntity orderGoodsEntity = orderDeliveryDto.getOrderGoodsEntityList().get(0);
        orderIndexEntity.setOrderGoodsVersionNo(orderGoodsEntity.getOrderGoodsVersionNo());

        // 処理種別
        orderIndexEntity.setProcessType(HTypeProcessType.ORDER);

        // 担当者名(管理者注文時のみ)
        if (StringUtils.isNotEmpty(administratorLastName) && StringUtils.isNotEmpty(administratorFirstName)) {
            orderIndexEntity.setProcessPersonName(administratorLastName + " " + administratorFirstName);
        }

        // 設計書修正必要 OZAKI
        orderIndexEntity.setSettlementMailRequired(receiveOrderDto.getOrderSummaryEntity().getSettlementMailRequired());

        // 注文インデックスにクーポン情報を設定
        // クーポン情報
        CouponEntity coupon = receiveOrderDto.getCoupon();
        if (coupon != null) {
            // クーポン未使用時は登録不要
            orderIndexEntity.setCouponSeq(coupon.getCouponSeq());
            orderIndexEntity.setCouponVersionNo(coupon.getCouponVersionNo());
        }

        // 受注サマリの保留フラグをコピー
        orderIndexEntity.setWaitingFlag(receiveOrderDto.getOrderSummaryEntity().getWaitingFlag());
        // 保留理由を設定
        orderIndexEntity.setOrderWaitingMemo(receiveOrderDto.getOrderWaitingMemo());

        // 受注インデックス登録ロジック実行
        orderIndexRegistLogic.execute(orderIndexEntity);
    }

    /**
     * カードブランド登録
     * GMOからの戻り値：カード会社コードがカードブランドに登録されていない場合、
     * 不明カード会社としてダミーデータを登録する
     *
     * @param receiveOrderDto 受注DTO
     */
    protected void registCardBrand(ReceiveOrderDto receiveOrderDto) {
        // クレジットカード会社コード取得
        MulPayBillEntity mulPayBillEntity = receiveOrderDto.getMulPayBillEntity();
        String cardBrandCode = mulPayBillEntity.getForward();
        if (StringUtils.isEmpty(cardBrandCode)) {
            return;
        }

        // クレジットカード会社コードからカードブランド情報を取得
        CardBrandEntity cardBrandEntity = cardBrandGetLogic.execute(cardBrandCode);
        // カードブランド情報登録済みの場合は処理終了
        if (cardBrandEntity != null) {
            return;
        }

        // カードブランドエンティティを作成
        cardBrandEntity = getComponent(CardBrandEntity.class);

        // MAXカードブランドSEQ取得
        int cardBrandSeq = cardBrandGetMaxCardBrandSeqLogic.execute();

        // カードブランドSEQ
        cardBrandEntity.setCardBrandSeq(++cardBrandSeq);
        // クレジットカード会社コード
        cardBrandEntity.setCardBrandCode(cardBrandCode);
        // カードブランド名
        String cardBrandName = PropertiesUtil.getLabelPropertiesValue("auto.regist.cardbrandname");
        // カードブランド名よりクレジットカード会社コードの桁数が大きくなる可能性があるため
        // カードブランドSEQを付与
        cardBrandEntity.setCardBrandName(cardBrandName + cardBrandSeq);
        // カードブランド表示名PC
        cardBrandEntity.setCardBrandDisplayPc(cardBrandName + cardBrandSeq);
        // カードブランド表示名携帯
        cardBrandEntity.setCardBrandDisplayMb(zenHanConversionUtility.toHankaku(cardBrandName) + cardBrandSeq);

        // カードブランド情報登録ロジック実行
        cardBrandRegistLogic.execute(cardBrandEntity);

    }

    /**
     * カード預かり情報登録更新Dtoを作成する
     *
     * @param receiveOrderDto 受注Dto
     * @return カード預かり情報登録更新Dto
     */
    protected CardDto createCardDto(ReceiveOrderDto receiveOrderDto,
                                    Integer memberInfoSeqRequest,
                                    String memberInfoId,
                                    HTypeSiteType siteType) {

        CardDto cardDto = ApplicationContextUtility.getBean(CardDto.class);

        Integer memberInfoSeq = orderUtility.getOrderMemberInfoSeq(memberInfoId, memberInfoSeqRequest, siteType,
                                                                   receiveOrderDto
                                                                  );

        OrderTempDto orderTempDto = receiveOrderDto.getOrderTempDto();
        // 会員SEQ
        cardDto.setMemberInfoSeq(memberInfoSeq);
        // 決済代行会員ID
        cardDto.setPaymentMemberId(mulPayUtility.createPaymentMemberId(memberInfoSeq));
        // カード番号
        cardDto.setCardNumber(orderTempDto.getCardNo());
        // 有効期限
        cardDto.setExpirationDate(orderTempDto.getExpire());
        cardDto.setSecurityCode(orderTempDto.getSecurityCode());
        // 受注SEQ
        cardDto.setOrderSeq(receiveOrderDto.getOrderSummaryEntity().getOrderSeq());
        // 受注履歴連番
        cardDto.setOrderVersionNo(receiveOrderDto.getOrderSummaryEntity().getOrderVersionNo());
        // 受注コード
        cardDto.setOrderCode(receiveOrderDto.getOrderSummaryEntity().getOrderCode());
        // 登録されたカードがあるならtrue
        cardDto.setRegistCredit(orderTempDto.isRegistCredit());
        // この決済で登録済みカードを使用するならtrue
        cardDto.setUseRegistCardFlg(orderTempDto.isUseRegistCardFlg());
        // トークンをセット
        cardDto.setToken(orderTempDto.getToken());

        return cardDto;
    }

    /**
     * 同時注文の排他チェックを行う<br/>
     *
     * @param receiveOrderDto 受注Dto
     */
    protected void checkSimultaneousOrderExclusion(ReceiveOrderDto receiveOrderDto) {

        // 同時注文排他情報を取得
        SimultaneousOrderExclusionEntity oldeExclusionEntity = receiveOrderDto.getSimultaneousOrderExclusionEntity();
        SimultaneousOrderExclusionEntity newExclusionEntity = simultaneousOrderExclusionGetLogic.execute(
                        receiveOrderDto.getOrderSummaryEntity().getMemberInfoSeq());

        if (oldeExclusionEntity == null || oldeExclusionEntity.getMemberInfoSeq() == null) {
            // 注文情報なし ⇒注文情報ありの場合エラー
            if (newExclusionEntity != null && newExclusionEntity.getMemberInfoSeq() != null) {
                throwMessage(MSGCD_SIMULTANEOUSORDEREXCLUSION_ERR);
            }
        } else {
            // 同会員の、同時注文排他情報が更新されている場合はエラー
            if (oldeExclusionEntity.getVersionNo().compareTo(newExclusionEntity.getVersionNo()) != 0) {
                throwMessage(MSGCD_SIMULTANEOUSORDEREXCLUSION_ERR);
            }

        }
    }

    /**
     * 同時注文排他情報の登録、更新を行う<br/>
     *
     * @param receiveOrderDto 受注Dto
     */
    protected void registUpdateSimultaneousOrderExclusion(ReceiveOrderDto receiveOrderDto) {

        // 同時注文排他情報を取得
        SimultaneousOrderExclusionEntity oldeExclusionEntity = receiveOrderDto.getSimultaneousOrderExclusionEntity();
        if (oldeExclusionEntity == null || oldeExclusionEntity.getMemberInfoSeq() == null) {
            oldeExclusionEntity = ApplicationContextUtility.getBean(SimultaneousOrderExclusionEntity.class);
            oldeExclusionEntity.setMemberInfoSeq(receiveOrderDto.getOrderSummaryEntity().getMemberInfoSeq());
            simultaneousOrderExclusionRegistLogic.execute(oldeExclusionEntity);
        } else {
            simultaneousOrderExclusionUpdateLogic.execute(oldeExclusionEntity);
        }
    }

}
