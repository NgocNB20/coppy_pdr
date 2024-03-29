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
 * 新しいお届け先 会員登録ロジック
 * </pre>
 *
 * @author satoh
 */

public interface MemberInfoRegistByNewAddressLogic {

    /**
     * 会員情報登録失敗エラー<br/>
     * <code>MSGCD_MEMBERINFO_REGIST_FAIL</code>
     */
    public static final String MSGCD_MEMBERINFO_REGIST_FAIL = "SMM000101";

    /**
     * 新しいお届け先に入力された情報の<br/>
     * 会員登録を行います。
     *
     * @param memberInfoEntity 会員情報
     * @return 会員SEQ
     */
    Integer execute(MemberInfoEntity memberInfoEntity);

}
// PDR Migrate Customization to here
