/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.utility;

import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * アンケート機能のUtility
 *
 * @author Pham Quanng Dieu
 */
@Component
public class QuestionnaireUtility {

    /**
     * 変換ユーティリティクラス
     */
    public final ConversionUtility conversionUtility;

    @Autowired
    public QuestionnaireUtility(ConversionUtility conversionUtility) {
        this.conversionUtility = conversionUtility;
    }

    /**
     * @return 注文フローに表示するアンケートのアンケートKey
     */
    public String getQuestionnaireKeyOrder() {
        return "order";
    }

    /**
     * @return 設問選択肢の区切り文字
     */
    public String getChoicesDivchar() {
        return ConversionUtility.DIV_CHAR_CRLF;
    }

    /**
     * 設問選択肢を分割する
     *
     * @param choices 設問選択肢
     * @return 設問選択肢の配列
     */
    public String[] splitChoices(String choices) {
        return conversionUtility.toDivArray(choices, getChoicesDivchar());
    }

}
