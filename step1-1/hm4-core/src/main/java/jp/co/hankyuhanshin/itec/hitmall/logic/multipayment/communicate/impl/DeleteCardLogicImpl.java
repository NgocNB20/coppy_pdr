/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.impl;

import com.gmo_pg.g_pay.client.PaymentClient;
import com.gmo_pg.g_pay.client.common.PaymentException;
import com.gmo_pg.g_pay.client.output.DeleteCardOutput;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCardRegistType;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.CardDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.HmDeleteCardInput;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.HmPaymentClientInput;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.DeleteCardLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoUpdateService;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * カード削除通信ロジック
 */
@Component
public class DeleteCardLogicImpl extends AbstractShopLogic implements DeleteCardLogic {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteCardLogicImpl.class);

    /**
     * 会員取得Sevice
     */
    private final MemberInfoGetService memberInfoGetService;

    /**
     * 会員更新Sevice
     */
    private final MemberInfoUpdateService memberInfoUpdateService;

    /**
     * PGカード決済サービスクライアント
     */
    private final PaymentClient paymentClient;

    @Autowired
    public DeleteCardLogicImpl(MemberInfoGetService memberInfoGetService,
                               MemberInfoUpdateService memberInfoUpdateService,
                               PaymentClient paymentClient) {
        this.memberInfoGetService = memberInfoGetService;
        this.memberInfoUpdateService = memberInfoUpdateService;
        this.paymentClient = paymentClient;
    }

    /**
     * ロジック実行
     *
     * @param receiveOrderDto 受注Dto
     * @return 結果
     */
    @Override
    public DeleteCardOutput execute(ReceiveOrderDto receiveOrderDto) {
        HmDeleteCardInput input = new HmDeleteCardInput();
        DeleteCardOutput output = new DeleteCardOutput();

        // 会員 ID
        input.setMemberId(receiveOrderDto.getOrderTempDto().getPaymentMemberId());
        // カード登録連番モード
        input.setSeqMode(HmPaymentClientInput.SEQ_MODE_LOGICAL);
        // カード登録連番
        input.setCardSeq(HmPaymentClientInput.CARD_SEQ);

        try {
            output = paymentClient.doDeleteCard(input);
        } catch (PaymentException pe) {
            LOGGER.error("例外処理が発生しました", pe);
            // 呼び元でエラーハンドリング実施
            throwMessage(MSGCD_PAYMENT_COM_FAIL, null, pe);
        }

        if (!output.isErrorOccurred()) {
            MemberInfoEntity memberInfoEntity =
                            memberInfoGetService.execute(receiveOrderDto.getOrderSummaryEntity().getMemberInfoSeq());
            // クレジットカード保持種別を更新する
            memberInfoEntity.setPaymentCardRegistType(HTypeCardRegistType.UNREGISTERED);

            memberInfoUpdateService.execute(memberInfoEntity);
        }

        return output;
    }

    /**
     * 実行メソッド
     *
     * @param cardDto      カードDto
     * @param dbUpdateFlag DB更新フラグ true:カード情報変更後会員情報TBLを更新します false:更新しません
     * @return 結果
     */
    @Override
    public DeleteCardOutput execute(CardDto cardDto, boolean dbUpdateFlag) {

        // GMOに会員登録していなければ処理終了
        if (!isRegistedMember(cardDto)) {
            return null;
        }
        HmDeleteCardInput input = new HmDeleteCardInput();
        DeleteCardOutput output = new DeleteCardOutput();
        // 会員ID
        input.setMemberId(cardDto.getPaymentMemberId());
        // カード登録連番モード
        input.setSeqMode(HmPaymentClientInput.SEQ_MODE_LOGICAL);
        // カード登録連番
        input.setCardSeq(HmPaymentClientInput.CARD_SEQ);

        try {
            output = paymentClient.doDeleteCard(input);
        } catch (PaymentException pe) {
            LOGGER.error("例外処理が発生しました", pe);
            // 呼び元でエラーハンドリング実施
            throwMessage(MSGCD_PAYMENT_COM_FAIL, null, pe);
        }

        if (!output.isErrorOccurred() && dbUpdateFlag) {
            MemberInfoEntity memberInfoEntity = memberInfoGetService.execute(cardDto.getMemberInfoSeq());
            // クレジットカード保持種別を更新する
            memberInfoEntity.setPaymentCardRegistType(HTypeCardRegistType.UNREGISTERED);

            memberInfoUpdateService.execute(memberInfoEntity);
        }

        return output;
    }

    /**
     * 会員が既に登録されているか確認する<br/>
     *
     * @param cardDto カードDto
     * @return true:登録 false:未登録
     */
    protected boolean isRegistedMember(CardDto cardDto) {
        // 決済代行会員IDが未設定の場合処理終了
        if (StringUtil.isEmpty(cardDto.getPaymentMemberId())) {
            return false;
        }

        return true;
    }

}
