/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.impl;

import com.gmo_pg.g_pay.client.PaymentClient;
import com.gmo_pg.g_pay.client.common.PaymentException;
import com.gmo_pg.g_pay.client.output.SaveMemberOutput;
import com.gmo_pg.g_pay.client.output.SearchMemberOutput;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.CardDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.HmSaveMemberInput;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.SaveMemberLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.SearchMemberLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoUpdateService;
import jp.co.hankyuhanshin.itec.hitmall.utility.MulPayUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author s_nose
 */
@Component
public class SaveMemberLogicImpl extends AbstractShopLogic implements SaveMemberLogic {
    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SaveMemberLogicImpl.class);

    /**
     * マルペイUtility取得
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
     * ＰＧカード決済サービスクライアント
     */
    private final PaymentClient paymentClient;

    /**
     * 会員照会Logic
     */
    private final SearchMemberLogic searchMemberLogic;

    @Autowired
    public SaveMemberLogicImpl(MulPayUtility mulPayUtility,
                               MemberInfoGetService memberInfoGetService,
                               MemberInfoUpdateService memberInfoUpdateService,
                               PaymentClient paymentClient,
                               SearchMemberLogic searchMemberLogic) {
        this.mulPayUtility = mulPayUtility;
        this.memberInfoGetService = memberInfoGetService;
        this.memberInfoUpdateService = memberInfoUpdateService;
        this.paymentClient = paymentClient;
        this.searchMemberLogic = searchMemberLogic;
    }

    /**
     * 実行メソッド
     *
     * @param dto 受注DTO
     * @return カード参照出力パラメータ
     */
    @Override
    public SaveMemberOutput execute(ReceiveOrderDto dto) {

        // 会員SEQ取得
        Integer memberInfoSeq = dto.getOrderSummaryEntity().getMemberInfoSeq();

        // 決済代行会員ID作成
        String paymentMemberId = mulPayUtility.createPaymentMemberId(memberInfoSeq);

        // 既に登録済みの場合は処理しない。
        if (isRegistedMember(paymentMemberId)) {
            return null;
        }

        SaveMemberOutput res = new SaveMemberOutput();
        HmSaveMemberInput input = new HmSaveMemberInput();

        // 会員IDセット
        input.setMemberId(paymentMemberId);
        // 会員名
        input.setMemberName(null);

        try {
            res = paymentClient.doSaveMember(input);
        } catch (PaymentException pe) {
            LOGGER.error("例外処理が発生しました", pe);
            // 呼び元でエラーハンドリング実施
            throwMessage(MSGCD_PAYMENT_COM_FAIL, null, pe);
        }

        if (!res.isErrorOccurred()) {
            // エラー発生無ければ会員情報を更新
            updateMemberInfo(memberInfoSeq, res.getMemberId());
        }
        return res;
    }

    /**
     * 実行メソッド<br />
     *
     * @param cardDto カードDto
     * @return 会員登録出力パラメータ
     */
    @Override
    // @RequiresNewTx
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public SaveMemberOutput execute(CardDto cardDto) {

        // 既に登録済みの場合は処理しない。
        if (isRegistedMember(cardDto.getPaymentMemberId())) {
            return null;
        }

        SaveMemberOutput res = new SaveMemberOutput();
        HmSaveMemberInput input = new HmSaveMemberInput();

        // 会員ID
        input.setMemberId(cardDto.getPaymentMemberId());
        // 会員名
        input.setMemberName(null);

        try {
            res = paymentClient.doSaveMember(input);
        } catch (PaymentException pe) {
            LOGGER.error("例外処理が発生しました", pe);
            // 呼び元でエラーハンドリング実施
            throwMessage(MSGCD_PAYMENT_COM_FAIL, null, pe);
        }

        if (!res.isErrorOccurred()) {
            // エラー発生無ければ会員情報を更新
            updateMemberInfo(cardDto.getMemberInfoSeq(), res.getMemberId());
        }

        return res;
    }

    /**
     * 会員情報を更新する<br/>
     *
     * @param memberInfoSeq   会員SEQ
     * @param paymentMemberId 取得した決済代行会員ID
     */
    protected void updateMemberInfo(Integer memberInfoSeq, String paymentMemberId) {
        MemberInfoEntity memberInfoEntity = memberInfoGetService.execute(memberInfoSeq);
        // 決済代行会員IDを更新する
        memberInfoEntity.setMemberInfoSeq(memberInfoSeq);
        memberInfoEntity.setPaymentMemberId(paymentMemberId);

        memberInfoUpdateService.execute(memberInfoEntity);
    }

    /**
     * 会員が既に登録されているか確認する<br/>
     *
     * @param paymentMemberId 決済代行会員ID
     * @return true:登録済み false:未登録
     */
    protected boolean isRegistedMember(String paymentMemberId) {
        // 会員照会
        SearchMemberOutput res = searchMemberLogic.execute(paymentMemberId);

        if (res.getMemberList().isEmpty()) {
            return false;
        }
        return true;
    }
}
