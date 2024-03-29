/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.impl;

import com.gmo_pg.g_pay.client.output.SaveCardOutput;
import com.gmo_pg.g_pay.client.output.SaveMemberOutput;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.CardDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.CardRegistUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.SaveCardLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.SaveMemberLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.MulPayUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * カード預かり情報を登録・更新するLogicクラス<br/>
 *
 * @author s_tsuru
 */
@Component
public class CardRegistUpdateLogicImpl extends AbstractShopLogic implements CardRegistUpdateLogic {

    /**
     * 会員登録Logic
     */
    private final SaveMemberLogic saveMemberLogic;

    /**
     * カード登録・更新Logic
     */
    private final SaveCardLogic saveCardLogic;

    @Autowired
    public CardRegistUpdateLogicImpl(SaveMemberLogic saveMemberLogic, SaveCardLogic saveCardLogic) {
        this.saveMemberLogic = saveMemberLogic;
        this.saveCardLogic = saveCardLogic;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param cardDto カードDto
     */
    @Override
    public void execute(CardDto cardDto) {

        // 会員が登録済みでない場合、登録実行
        SaveMemberOutput saveMemberOutput = saveMemberLogic.execute(cardDto);

        if (saveMemberOutput != null && saveMemberOutput.isErrorOccurred()) {
            // エラーが返ってきた場合スロー
            throwMessage(MSGCD_GMO_INPUT_ERR);
        }

        // カード登録更新
        SaveCardOutput saveCardOutput = saveCardLogic.execute(cardDto);
        MulPayUtility mulPayUtility = ApplicationContextUtility.getBean(MulPayUtility.class);

        // エラーが発生していればスローする
        if (saveCardOutput.isErrorOccurred()) {
            if (MulPayUtility.ERR_CARD.equals(mulPayUtility.getErrorType(saveCardOutput))) {
                throwMessage(MSGCD_ENTRY_CARD_ERR);
            } else {
                throwMessage(MSGCD_GMO_INPUT_ERR);
            }
        }

    }
}
