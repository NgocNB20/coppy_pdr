/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;

/**
 * 会員情報登録<br/>
 *
 * @author natume
 * @version $Revision: 1.2 $
 */
public interface MemberInfoRegistLogic {

    /**
     * 会員情報登録処理<br/>
     *
     * @param memberInfoEntity 会員情報エンティティ
     * @return 登録件数
     */
    int execute(MemberInfoEntity memberInfoEntity);
}
