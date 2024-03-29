/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;

/**
 * 暫定会員情報登録<br/>
 *
 * @author kimura
 */
public interface MemberInfoProvisionalRegistLogic {

    /**
     * 暫定会員情報登録処理<br/>
     * メルマガ登録時に呼ばれるlogic
     *
     * @param memberInfoEntity 会員情報エンティティ
     * @return 登録件数
     */
    int execute(MemberInfoEntity memberInfoEntity);
}
