// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;

/**
 * PDR#011 08_データ連携（顧客情報）<br/>
 *
 * <pre>
 * EC会員情報更新ロジック
 * </pre>
 *
 * @author satoh
 * @version $Revision:$
 */

public interface MemberInfoEcUpdateLogic {

    /**
     * EC会員情報の更新を行います。
     *
     * @param memberInfoPdrEntity 会員情報Entity
     */
    void execute(MemberInfoEntity memberInfoPdrEntity);

}
// PDR Migrate Customization to here
