/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.impl;

import com.gmo_pg.g_pay.client.PaymentClient;
import com.gmo_pg.g_pay.client.common.PaymentException;
import com.gmo_pg.g_pay.client.output.TradedCardOutput;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCardRegistType;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.HmPaymentClientInput;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.HmTradedCardInput;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.CardBrandEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.CardBrandGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.CardBrandGetMaxCardBrandSeqLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.CardBrandRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.TradedCardLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoUpdateService;
import jp.co.hankyuhanshin.itec.hitmall.utility.MulPayUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ZenHanConversionUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author s_nose
 */
@Component
public class TradedCardLogicImpl extends AbstractShopLogic implements TradedCardLogic {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TradedCardLogicImpl.class);

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
     * カードブランド情報取得ロジック
     */
    private final CardBrandGetLogic cardBrandGetLogic;

    /**
     * MAXカードブランドSEQ取得ロジック
     */
    private final CardBrandGetMaxCardBrandSeqLogic cardBrandGetMaxCardBrandSeqLogic;

    /**
     * カードブランド情報登録ロジック
     */
    private final CardBrandRegistLogic cardBrandRegistLogic;

    /**
     * マルペイUtility取得
     */
    private final MulPayUtility mulPayUtility;

    @Autowired
    public TradedCardLogicImpl(MemberInfoGetService memberInfoGetService,
                               MemberInfoUpdateService memberInfoUpdateService,
                               PaymentClient paymentClient,
                               CardBrandGetLogic cardBrandGetLogic,
                               CardBrandGetMaxCardBrandSeqLogic cardBrandGetMaxCardBrandSeqLogic,
                               CardBrandRegistLogic cardBrandRegistLogic,
                               MulPayUtility mulPayUtility) {
        this.memberInfoGetService = memberInfoGetService;
        this.memberInfoUpdateService = memberInfoUpdateService;
        this.paymentClient = paymentClient;
        this.cardBrandGetLogic = cardBrandGetLogic;
        this.cardBrandGetMaxCardBrandSeqLogic = cardBrandGetMaxCardBrandSeqLogic;
        this.cardBrandRegistLogic = cardBrandRegistLogic;
        this.mulPayUtility = mulPayUtility;
    }

    /**
     * 実行メソッド
     *
     * @param dto     受注DTO
     * @param orderId 受注番号
     * @return カード参照出力パラメータ
     */
    @Override
    public TradedCardOutput execute(ReceiveOrderDto dto, String orderId) {
        // 会員SEQ取得
        Integer memberInfoSeq = dto.getOrderSummaryEntity().getMemberInfoSeq();
        // 決済代行会社会員IDの取得
        String paymentMemberId = mulPayUtility.createPaymentMemberId(memberInfoSeq);

        TradedCardOutput res = new TradedCardOutput();

        HmTradedCardInput input = new HmTradedCardInput();

        // 会員 ID
        input.setMemberId(paymentMemberId);
        // オーダーID
        input.setOrderId(orderId);

        // カード登録連番モード
        input.setSeqMode(HmPaymentClientInput.SEQ_MODE_LOGICAL);

        // 名義人
        input.setHolderName(null);

        try {
            res = paymentClient.doTradedCard(input);
        } catch (PaymentException pe) {
            LOGGER.error("例外処理が発生しました", pe);
            // 呼び元でエラーハンドリング実施
            throwMessage(MSGCD_PAYMENT_COM_FAIL, null, pe);
        }

        if (!res.isErrorOccurred()) {
            MemberInfoEntity memberInfoEntity = memberInfoGetService.execute(memberInfoSeq);
            // クレジットカード保持種別を更新する
            memberInfoEntity.setPaymentCardRegistType(HTypeCardRegistType.AUTHENTICATED);

            memberInfoUpdateService.execute(memberInfoEntity);
            // カードブランド登録
            registCardBrand(res.getForward());
        }

        return res;
    }

    /**
     * カードブランド登録
     * GMOからの戻り値：カード会社コードがカードブランドに登録されていない場合、
     * 不明カード会社としてダミーデータを登録する
     *
     * @param forward GMOから帰ってきたクレジットカード会社コード
     */
    protected void registCardBrand(String forward) {
        // クレジットカード会社コード取得
        String cardBrandCode = forward;

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
        // 全角、半角の変換Helper取得
        ZenHanConversionUtility zenHanConversionUtility =
                        ApplicationContextUtility.getBean(ZenHanConversionUtility.class);
        // カードブランド表示名携帯
        cardBrandEntity.setCardBrandDisplayMb(zenHanConversionUtility.toHankaku(cardBrandName) + cardBrandSeq);

        // カードブランド情報登録ロジック実行
        cardBrandRegistLogic.execute(cardBrandEntity);
    }
}
