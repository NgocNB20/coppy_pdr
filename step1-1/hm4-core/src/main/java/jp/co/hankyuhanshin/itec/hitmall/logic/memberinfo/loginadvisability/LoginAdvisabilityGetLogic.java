// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.loginadvisability;

import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.loginadvisability.LoginAdvisabilityEntity;

/**
 * PDR#023 顧客番号でのログイン<br/>
 *
 * <pre>
 * 会員情報取得ロジック
 *
 * 会員ID 又は 顧客番号で会員情報を取得するメソッドを追加
 * 電話番号から会員情報を取得するメソッドを追加
 * 電話番号、顧客番号から会員情報を取得するメソッドを追加
 * </pre>
 *
 * @author satoh
 * @version $Revision:$
 */

public interface LoginAdvisabilityGetLogic {

    /**
     * 会員情報を取得する<br />
     *
     * @param memberInfoPdrEntity 会員情報エンティティ
     * @return ログイン可否判定エンティティ
     */
    LoginAdvisabilityEntity getLoginAdvisabilityPdrEntityByMemberInfo(MemberInfoEntity memberInfoPdrEntity);

}
// PDR Migrate Customization to here
