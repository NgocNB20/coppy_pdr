/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.common;

import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.cart.CartDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractModel;
import lombok.Data;

/**
 * 注文フロー共通Model
 *
 * @author kaneda
 * @author kimura
 */
@Data
public class OrderCommonModel extends AbstractModel {

    /**
     * コンストラクタ
     **/
    public OrderCommonModel() {
        // モデルクリア処理のためprivate変数に直アクセスしない
        setReceiveOrderDto(new ReceiveOrderDto());
    }

    /**
     * 受注Dto（画面間保持用）
     **/
    private ReceiveOrderDto receiveOrderDto;

    /**
     * 会員情報エンティティ
     **/
    private MemberInfoEntity memberInfoEntity;

    /**
     * カートDto
     */
    private CartDto cartDto;

    /**
     * セッション保持トークン
     */
    private String sToken;

    /**
     * ポストトークン
     */
    private String pToken;

    // 2023-renew No14 from here
    /**
     * 受注金額0円判定フラグ
     */
    private boolean orderPriceZero;

    /**
     * チェックボックス：注文内容を確認しました
     */
    private boolean confirmOrder = false;
    // 2023-renew No14 from here

    /**
     * ログインしてるかどうかを判定します。<br />
     * 画面表示のConditionとして使用。<br />
     *
     * @return true..ログインしてる。false..ログインしてない。
     */
    public boolean isLogin() {
        // 共通情報Helper取得
        CommonInfoUtility commonInfoUtility = ApplicationContextUtility.getBean(CommonInfoUtility.class);
        return commonInfoUtility.isLogin(getCommonInfo());
    }

}
