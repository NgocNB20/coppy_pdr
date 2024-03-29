/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.cardinfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCardRegistType;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.ComResultDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.cardinfo.CardInfoLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.CreditCardInfoTranLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoUpdateService;
import jp.co.hankyuhanshin.itec.hitmall.utility.ComTransactionUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.MulPayUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * PDR#011 08_データ連携（顧客情報）<br/>
 * 【決済オプション部品（ペイジェント）】<br />
 * カード情報操作（取得・登録・削除）ロジック
 * <pre>
 * 指定カード情報削除ロジック追加
 * </pre>
 *
 * @author s.kume
 */
@Component
// Paygent Customization from here
public class CardInfoLogicImpl extends AbstractShopLogic implements CardInfoLogic {

    /**
     * 通信ユーティリティ
     */
    private final ComTransactionUtility comTransactionUtility;

    /**
     * 日付ユーティリティ
     */
    private final DateUtility dateUtility;

    /**
     * マルチペイメントユーティリティ
     */
    private final MulPayUtility mulPayUtility;

    /**
     * 会員取得Sevice
     */
    private final MemberInfoGetService memberInfoGetService;

    /**
     * 会員更新Sevice
     */
    private final MemberInfoUpdateService memberInfoUpdateService;

    /**
     * クレジットカード情報お預かり用通信処理
     */
    private final CreditCardInfoTranLogic creditCardInfoTranLogic;

    @Autowired
    public CardInfoLogicImpl(ComTransactionUtility comTransactionUtility,
                             DateUtility dateUtility,
                             MulPayUtility mulPayUtility,
                             MemberInfoGetService memberInfoGetService,
                             MemberInfoUpdateService memberInfoUpdateService,
                             CreditCardInfoTranLogic creditCardInfoTranLogic) {
        this.comTransactionUtility = comTransactionUtility;
        this.dateUtility = dateUtility;
        this.mulPayUtility = mulPayUtility;
        this.memberInfoGetService = memberInfoGetService;
        this.memberInfoUpdateService = memberInfoUpdateService;
        this.creditCardInfoTranLogic = creditCardInfoTranLogic;
    }

    @Override
    public ComResultDto getCardInfo(Integer memberInfoSeq) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("memberInfoSeq", memberInfoSeq);

        // 会員情報を取得
        MemberInfoEntity memberInfoEntity = memberInfoGetService.execute(memberInfoSeq);

        ComResultDto regResult = creditCardInfoTranLogic.doGetCardInfoTran(memberInfoEntity.getPaymentMemberId());

        return regResult;
    }

    /**
     * クレジットカード情報登録処理<br/>
     * <pre>
     *    新しいカード情報を登録する
     *    カード情報削除処理を削除
     * </pre>
     *
     * @param receiveOrderDto 受注情報
     * @param isMemInfoUpd    会員情報更新フラグ
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void registCardInfo(ReceiveOrderDto receiveOrderDto, boolean isMemInfoUpd) {

        // 会員SEQ
        Integer memberInfoSeq = receiveOrderDto.getOrderSummaryEntity().getMemberInfoSeq();

        // PDR Migrate Customization from here
        //        /**
        //         * PDR#011 08_データ連携（顧客情報）<br/>
        //         * 【決済オプション部品（ペイジェント）】<br />
        //         * カード情報操作（取得・登録・削除）ロジック
        //         * <pre>
        //         * 指定カード情報削除ロジック追加
        //         * </pre>
        //         *
        //         */
        // PDR Migrate Customization from here
        // 顧客番号
        Integer memberCustomerNo = receiveOrderDto.getOrderPersonEntity().getCustomerNo();
        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("customerNo", memberCustomerNo);

        // ペイジェントのカード情報を削除 削除

        // 新規に決済代行会員IDを作成する
        // サブシステム側と連携を行うため、決済代行会員IDは顧客番号を設定
        String customerNo = mulPayUtility.createPaymentMemberId(memberCustomerNo);
        // PDR Migrate Customization to here

        // ペイジェントにカード情報を登録
        String cardNumber = receiveOrderDto.getOrderTempDto().getCardNo();
        String cardValidTerm = receiveOrderDto.getOrderTempDto().getExpireMonth() + receiveOrderDto.getOrderTempDto()
                                                                                                   .getExpireYear();

        // 預かりカード用トークン　※ 決済用トークンではない方
        String keepToken = receiveOrderDto.getOrderTempDto().getKeepToken();

        ComResultDto regResult =
                        creditCardInfoTranLogic.doRegistCardInfoTran(customerNo, cardNumber, cardValidTerm, keepToken);

        if (comTransactionUtility.isErrorOccurred(regResult)) {
            CheckMessageDto messageDto = regResult.getMessageList().get(0);
            throwMessage(messageDto.getMessageId(), messageDto.getArgs());
        }

        if (isMemInfoUpd) {
            // カード情報の登録が成功した場合、決済代行会社会員IDを保存、決済代行会社カード保持種別を1で更新する
            updateEntity(memberInfoSeq, customerNo, HTypeCardRegistType.REGISTERED);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public String deleteCardInfo(Integer memberInfoSeq, boolean isMemInfoUpd) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("memberInfoSeq", memberInfoSeq);

        // 会員情報を取得
        MemberInfoEntity memberInfoEntity = memberInfoGetService.execute(memberInfoSeq);

        // 登録済みの場合、削除処理を実施する
        if (HTypeCardRegistType.REGISTERED.equals(memberInfoEntity.getPaymentCardRegistType())) {

            // ペイジェントのカード情報を削除
            ComResultDto result = creditCardInfoTranLogic.doDeleteCardInfoTran(memberInfoEntity.getPaymentMemberId());

            if (comTransactionUtility.isErrorOccurred(result) && !comTransactionUtility.isNotFoundCardInfo(result)) {
                CheckMessageDto messageDto = result.getMessageList().get(0);
                throwMessage(messageDto.getMessageId(), messageDto.getArgs());
            }

            if (isMemInfoUpd) {
                // カード情報の削除が成功した場合、決済代行会社カード保持種別を0で更新する
                updateEntity(memberInfoSeq, memberInfoEntity.getPaymentMemberId(), HTypeCardRegistType.UNREGISTERED);
            }
            return memberInfoEntity.getPaymentMemberId();
        }
        return null;
    }

    /**
     * 会員カードお預かり情報を指定された状態に更新<br/>
     *
     * @param memberInfoSeq         会員SEQ
     * @param paymentMemberId       決済代行会社会員ID
     * @param paymentCardRegistType 決済代行会社カード保持種別
     */
    protected void updateEntity(Integer memberInfoSeq,
                                String paymentMemberId,
                                HTypeCardRegistType paymentCardRegistType) {

        MemberInfoEntity memberInfoEntity = memberInfoGetService.execute(memberInfoSeq);
        memberInfoEntity.setPaymentMemberId(paymentMemberId);
        memberInfoEntity.setPaymentCardRegistType(paymentCardRegistType);

        memberInfoUpdateService.execute(memberInfoEntity);
    }

    // PDR Migrate Customization from here

    /**
     * 指定されたカード情報を削除する<br/>
     * カードIDを渡すことで単一のカード情報を渡す<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @param cardId        クレジットカードID
     * @return 削除した会員の決済代行会員ID
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public String deleteDesignateCardInfo(Integer memberInfoSeq, String cardId) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("memberInfoSeq", memberInfoSeq);

        // 会員情報を取得
        MemberInfoEntity memberInfoEntity = memberInfoGetService.execute(memberInfoSeq);

        // 登録済みの場合、削除処理を実施する
        if (HTypeCardRegistType.REGISTERED.equals(memberInfoEntity.getPaymentCardRegistType())) {

            // 指定したペイジェントのカード情報を削除
            ComResultDto result =
                            creditCardInfoTranLogic.doDeleteDesignateCardInfoTran(memberInfoEntity.getPaymentMemberId(),
                                                                                  cardId
                                                                                 );

            if (comTransactionUtility.isErrorOccurred(result)) {
                CheckMessageDto messageDto = result.getMessageList().get(0);
                throwMessage(messageDto.getMessageId(), messageDto.getArgs());
            }

            // サブシステムとの連携のため
            // 顧客カード数が0枚の場合でも、決済代行会社カード保持種別は更新を行わない
            return memberInfoEntity.getPaymentMemberId();
        }
        return null;
    }
    // PDR Migrate Customization to here
}
// Paygent Customization to here
