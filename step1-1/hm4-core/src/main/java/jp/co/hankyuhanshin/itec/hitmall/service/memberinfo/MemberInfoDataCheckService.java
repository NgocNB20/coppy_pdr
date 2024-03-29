/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;

/**
 * 会員情報データチェックサービス
 *
 * @author negishi
 * @version $Revision: 1.1 $
 */
public interface MemberInfoDataCheckService {

    /**
     * サービス実行
     *
     * @param memberInfoEntity 会員情報エンティティ
     */
    void execute(MemberInfoEntity memberInfoEntity);

}
