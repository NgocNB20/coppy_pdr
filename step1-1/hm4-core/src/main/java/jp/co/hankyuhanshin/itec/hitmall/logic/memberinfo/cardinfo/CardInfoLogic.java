/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.cardinfo;

import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.ComResultDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;

/**
 * PDR#011 08_データ連携（顧客情報）<br/>
 * <p>
 * 【決済オプション部品（ペイジェント）】<br />
 * カード情報操作（取得・登録・削除）ロジック
 * <pre>
 * 指定カード情報削除ロジック追加
 * </pre>
 *
 * @author s.kume
 */
// Paygent Customization from here
public interface CardInfoLogic {

    /**
     * 会員SEQからカード情報を取得
     *
     * @param memberInfoSeq 会員SEQ
     * @return クレジットカード情報参照結果
     */
    ComResultDto getCardInfo(Integer memberInfoSeq);

    /**
     * クレジットカード情報登録処理<br/>
     * <pre>
     * 【カード未登録の場合】
     *    新しいカード情報を登録する
     * 【カード登録済みの場合】
     *    登録済みカード情報をペイジェントから削除して、
     *    新しいカード情報を登録する
     * </pre>
     *
     * @param receiveOrderDto 受注情報
     * @param isMemInfoUpd    会員情報更新フラグ
     */
    void registCardInfo(ReceiveOrderDto receiveOrderDto, boolean isMemInfoUpd);

    /**
     * クレジットカード情報削除処理
     *
     * @param memberInfoSeq 会員SEQ
     * @param isMemInfoUpd  会員情報更新フラグ
     * @return 削除した会員の決済代行会員ID
     */
    String deleteCardInfo(Integer memberInfoSeq, boolean isMemInfoUpd);

    // PDR Migrate Customization from here

    /**
     * 指定クレジットカード情報削除処理
     *
     * @param memberInfoSeq 会員SEQ
     * @param cardId        クレジットカードId
     * @return 削除した会員の決済代行会員ID
     */
    String deleteDesignateCardInfo(Integer memberInfoSeq, String cardId);

    // PDR Migrate Customization to here
}
// Paygent Customization to here
