/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment;

import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.CardDto;

/**
 * カード預かり情報を登録・更新するLogicクラス<br/>
 *
 * @author s_tsuru
 */
public interface CardRegistUpdateLogic {

    /**
     * 入力したカードが不適切な場合エラー：
     */
    public static final String MSGCD_ENTRY_CARD_ERR = "PKG-3554-012-L-";

    /**
     * GMOの結果にエラーが含まれている場合エラー：PKG-3554-013-L-
     */
    public static final String MSGCD_GMO_INPUT_ERR = "PKG-3554-013-L-";

    /**
     * 実行メソッド<br/>
     *
     * @param cardDto カードDto
     */
    void execute(CardDto cardDto);
}
