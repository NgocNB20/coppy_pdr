/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.impl;

import com.gmo_pg.g_pay.client.PaymentClient;
import com.gmo_pg.g_pay.client.common.PaymentException;
import com.gmo_pg.g_pay.client.output.SaveCardOutput;
import com.gmo_pg.g_pay.client.output.SearchCardOutput;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCardRegistType;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.CardDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.HmPaymentClientInput;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.HmSaveCardInput;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.CardBrandEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.CardBrandGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.CardBrandGetMaxCardBrandSeqLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.CardBrandRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.SaveCardLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.SearchCardLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoUpdateService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ZenHanConversionUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * カード登録通信Logic実装クラス
 *
 * @author s_nose
 */
@Component
public class SaveCardLogicImpl extends AbstractShopLogic implements SaveCardLogic {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SaveCardLogicImpl.class);

    /**
     * 会員取得Sevice
     */
    private final MemberInfoGetService memberInfoGetService;

    /**
     * 会員更新Sevice
     */
    private final MemberInfoUpdateService memberInfoUpdateService;

    /**
     * カード参照Logic
     */
    private final SearchCardLogic searchCardLogic;

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

    @Autowired
    public SaveCardLogicImpl(MemberInfoGetService memberInfoGetService,
                             MemberInfoUpdateService memberInfoUpdateService,
                             SearchCardLogic searchCardLogic,
                             PaymentClient paymentClient,
                             CardBrandGetLogic cardBrandGetLogic,
                             CardBrandGetMaxCardBrandSeqLogic cardBrandGetMaxCardBrandSeqLogic,
                             CardBrandRegistLogic cardBrandRegistLogic) {
        this.memberInfoGetService = memberInfoGetService;
        this.memberInfoUpdateService = memberInfoUpdateService;
        this.searchCardLogic = searchCardLogic;
        this.paymentClient = paymentClient;
        this.cardBrandGetLogic = cardBrandGetLogic;
        this.cardBrandGetMaxCardBrandSeqLogic = cardBrandGetMaxCardBrandSeqLogic;
        this.cardBrandRegistLogic = cardBrandRegistLogic;
    }

    /**
     * 実行メソッド
     *
     * @param cardDto カードDto
     * @return カード参照出力パラメータ
     */
    @Override
    // @RequiresNewTx
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public SaveCardOutput execute(CardDto cardDto) {

        SaveCardOutput res = new SaveCardOutput();
        HmSaveCardInput input = new HmSaveCardInput();

        // 会員ID
        input.setMemberId(cardDto.getPaymentMemberId());
        // カード番号
        // input.setCardNo(cardDto.cardNumber);
        // 有効期限
        input.setExpire(cardDto.getExpirationDate());
        // カード登録連番モード
        input.setSeqMode(HmPaymentClientInput.SEQ_MODE_LOGICAL);
        // カード登録連番
        // PKG #3734対応
        if (cardDto.isRegistCredit() && isUpdate(cardDto.getPaymentMemberId())) {
            input.setCardSeq(HmPaymentClientInput.CARD_SEQ);
        }
        // トークン
        input.setToken(cardDto.getToken());

        try {
            res = paymentClient.doSaveCard(input);
        } catch (PaymentException pe) {
            LOGGER.error("例外処理が発生しました", pe);
            // 呼び元でエラーハンドリング実施
            this.throwMessage(MSGCD_PAYMENT_COM_FAIL, null, pe);
        }

        if (res.getErrList() == null || res.getErrList().size() == 0) {
            MemberInfoEntity memberInfoEntity = memberInfoGetService.execute(cardDto.getMemberInfoSeq());
            // クレジットカード保持種別を更新する
            memberInfoEntity.setPaymentCardRegistType(HTypeCardRegistType.REGISTERED);
            memberInfoEntity.setPaymentMemberId(cardDto.getPaymentMemberId());

            memberInfoUpdateService.execute(memberInfoEntity);
            // カードブランド登録
            registCardBrand(res.getForward());

        }

        return res;
    }

    /**
     * 更新するか判定する<br/>
     *
     * @param paymentMemberId 決済代行会員ID
     * @return true:更新 false:新規
     */
    protected boolean isUpdate(String paymentMemberId) {
        SearchCardOutput result = searchCardLogic.execute(paymentMemberId);
        return !result.getCardList().isEmpty();
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
        String cardBrandName = PropertiesUtil.getSystemPropertiesValue("auto.regist.cardbrandname");
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
