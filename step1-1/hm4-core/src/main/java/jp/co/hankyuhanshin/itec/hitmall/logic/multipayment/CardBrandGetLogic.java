/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment;

import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.CardBrandEntity;

import java.util.List;

/**
 * カードブランド情報取得ロジック<br/>
 *
 * @author ito
 */
public interface CardBrandGetLogic {

    /**
     * カードブランド情報を取得<br/>
     *
     * @param cardBrandCode クレジットカード会社コード
     * @return カードブランド情報エンティティ
     */
    CardBrandEntity execute(String cardBrandCode);

    /**
     * カードブランドリスト取得<br/>
     *
     * @param frontDisplayFlag Front表示フラグ
     * @return カードブランド情報リスト
     */
    List<CardBrandEntity> execute(boolean frontDisplayFlag);

}
