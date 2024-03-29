// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.cart;

/**
 * メンバーのカートをロックします<br/>
 *
 * @author is40701
 */

public interface MemberCartLockLogic {

    /**
     * メンバーのカートをロックします。
     *
     * @param memberInfoSeq 会員SEQ
     * @return SELECT実行結果
     */
    int execute(Integer memberInfoSeq);

}
// PDR Migrate Customization to here
