/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2023 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.card;

import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.HCHankaku;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.confirm.MemberRegistedCardItem;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractModel;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * カード情報画面 Model
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Data
// 2023-renew No60 from here
public class MemberCardModel extends AbstractModel {

    // << ご登録カード一覧
    /**
     * 登録済みカード情報Itemリスト
     */
    private List<MemberRegistedCardItem> registedCardItems;

    /**
     * 登録済みカード インデックス（削除用）
     */
    private int registedCardIndex;

    /**
     * 登録済みカードが存在するかどうか
     *
     * @return true..存在する / false..存在しない
     */
    public boolean isRegistedCard() {
        return CollectionUtils.isEmpty(registedCardItems);
    }
    // >>

    // << 新しいカードを登録する
    /**
     * クレジットカード会社（入力値）
     */
    private String cardBrand;

    /**
     * クレジットカード会社リスト（選択肢）
     */
    private Map<String, String> cardBrandItems;

    /**
     * カード番号（入力値）
     */
    @HCHankaku
    private String cardNumber;

    /**
     * 有効期限（月）（入力値）
     */
    private String expirationDateMonth;

    /**
     * 有効期限リスト（月）（選択肢）
     */
    private Map<String, String> expirationDateMonthItems;

    /**
     * 有効期限（年）（入力値）
     */
    private String expirationDateYear;

    /**
     * 有効期限リスト（年）（選択肢）
     */
    private Map<String, String> expirationDateYearItems;

    /**
     * トークン
     */
    private String token;

    /**
     * カード預かり通信用トークン
     */
    private String keepToken;

    /**
     * PAYGENTトークン決済  マーチャントID
     */
    private String merchantId;

    /**
     * トークン生成鍵
     */
    @HVSpecialCharacter(allowPunctuation = true)
    private String paygentTokenKey;
    // >>

}
// 2023-renew No60 to here
