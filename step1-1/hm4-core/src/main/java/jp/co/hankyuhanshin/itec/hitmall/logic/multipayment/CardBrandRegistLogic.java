/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment;

import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.CardBrandEntity;

/**
 * カードブランド情報登録ロジック<br/>
 *
 * @author ito
 * @version $Revision:$
 */
public interface CardBrandRegistLogic {

    /**
     * カードブランド情報を登録<br/>
     *
     * @param cardBrandEntity カードブランド情報エンティティ
     * @return 登録件数
     */
    int execute(CardBrandEntity cardBrandEntity);
}
