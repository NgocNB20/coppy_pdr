/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2023 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.card;

import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CardBrandGetResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CardBrandResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CardInfoRegistRequest;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeFrontDisplayFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.multipayment.ComResultDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.multipayment.CardBrandEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.confirm.MemberRegistedCardItem;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * カード情報画面 Helper
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
// 2023-renew No60 from here
@Component
public class MemberCardHelper {

    /**
     * 日付関連Utility
     */
    private final DateUtility dateUtility;

    /**
     * コンストラクタ
     */
    @Autowired
    public MemberCardHelper(DateUtility dateUtility) {
        this.dateUtility = dateUtility;
    }

    /**
     * 登録済みカード情報Itemリストに変換
     *
     * @param cardInfo   決済通信結果返却用Dto
     * @param memberCardModel 会員情報の支払い Model
     */
    protected void setRegistedPaygentCardInfo(ComResultDto cardInfo, MemberCardModel memberCardModel) {

        if (ObjectUtils.isEmpty(cardInfo) || CollectionUtils.isEmpty(cardInfo.getResultMapList())) {
            return;
        }

        List<MemberRegistedCardItem> registedCardItemList = new ArrayList<>();
        // 登録済みのカード情報複数件表示
        for (Map<String, String> registCardInfo : cardInfo.getResultMapList()) {

            MemberRegistedCardItem registedCardItem = ApplicationContextUtility.getBean(MemberRegistedCardItem.class);

            // 顧客カードID
            registedCardItem.setCardId(registCardInfo.get(ComResultDto.KEY_CUSTOMER_CARD_ID));

            // カード番号
            registedCardItem.setCardNumber(registCardInfo.get(ComResultDto.KEY_CARD_NUMBER));
            String cardValidTerm = registCardInfo.get(ComResultDto.KEY_CARD_VALID_TERM);
            // 有効期限：月
            registedCardItem.setExpirationDateMonth(cardValidTerm.substring(0, 2));

            // 有効期限：年(現在年上二桁 + paygentから取得した値 )
            registedCardItem.setExpirationDateYear(
                            dateUtility.getCurrentY().substring(0, 2) + cardValidTerm.substring(2));

            // カード会社
            registedCardItem.setCardBrand(registCardInfo.get(ComResultDto.KEY_CARD_BRAND));

            registedCardItemList.add(registedCardItem);
        }
        // カード情報リストをページにセット
        memberCardModel.setRegistedCardItems(registedCardItemList);
    }

    /**
     * クレジットカードブランドEntityリストに変換
     *
     * @param cardBrandGetResponse カードブランドリスト取得レスポンス
     * @return クレジットカードブランドEntityリスト
     */
    public List<CardBrandEntity> toCardBrandEntityList(CardBrandGetResponse cardBrandGetResponse) {

        if (ObjectUtils.isEmpty(cardBrandGetResponse) || CollectionUtils.isEmpty(
                        cardBrandGetResponse.getCardBrandList())) {
            return new ArrayList<>();
        }

        List<CardBrandEntity> cardBrandEntityList = new ArrayList<>();
        for (CardBrandResponse cardBrandEntityResponse : cardBrandGetResponse.getCardBrandList()) {
            CardBrandEntity cardBrandEntity = ApplicationContextUtility.getBean(CardBrandEntity.class);
            cardBrandEntity.setCardBrandSeq(cardBrandEntityResponse.getCardBrandSeq());
            cardBrandEntity.setCardBrandCode(cardBrandEntityResponse.getCardBrandCode());
            cardBrandEntity.setCardBrandName(cardBrandEntityResponse.getCardBrandName());
            cardBrandEntity.setCardBrandDisplayPc(cardBrandEntityResponse.getCardBrandDisplay());
            cardBrandEntity.setOrderDisplay(cardBrandEntityResponse.getOrderDisplay());
            cardBrandEntity.setInstallment(cardBrandEntityResponse.getInstallment());
            cardBrandEntity.setBounusSingle(cardBrandEntityResponse.getBounusSingle());
            cardBrandEntity.setBounusInstallment(cardBrandEntityResponse.getBounusInstallment());
            cardBrandEntity.setRevolving(cardBrandEntityResponse.getRevolving());
            cardBrandEntity.setInstallmentCounts(cardBrandEntityResponse.getInstallmentCounts());
            cardBrandEntity.setFrontDisplayFlag(EnumTypeUtil.getEnumFromValue(HTypeFrontDisplayFlag.class,
                                                                              cardBrandEntityResponse.getFrontDisplayFlag()
                                                                             ));
            cardBrandEntityList.add(cardBrandEntity);
        }

        return cardBrandEntityList;
    }

    /**
     * カード情報登録リクエストに変換
     *
     * @param memberCardModel カード情報画面 Model
     * @param memberInfoEntity 会員エンティティ
     * @return カード情報登録リクエスト
     */
    public CardInfoRegistRequest toCardInfoRegisterRequest(MemberCardModel memberCardModel,
                                                           MemberInfoEntity memberInfoEntity) {
        CardInfoRegistRequest cardInfoRegistRequest = new CardInfoRegistRequest();

        cardInfoRegistRequest.setMemberInfoSeq(memberInfoEntity.getMemberInfoSeq());
        cardInfoRegistRequest.setCustomerNo(memberInfoEntity.getCustomerNo());
        cardInfoRegistRequest.setCardBrand(memberCardModel.getCardBrand());
        cardInfoRegistRequest.setCardNumber(memberCardModel.getCardNumber());
        cardInfoRegistRequest.setExpirationDateYear(memberCardModel.getExpirationDateYear());
        cardInfoRegistRequest.setExpirationDateMonth(memberCardModel.getExpirationDateMonth());
        cardInfoRegistRequest.setKeepToken(memberCardModel.getKeepToken());

        return cardInfoRegistRequest;
    }

}
// 2023-renew No60 to here
