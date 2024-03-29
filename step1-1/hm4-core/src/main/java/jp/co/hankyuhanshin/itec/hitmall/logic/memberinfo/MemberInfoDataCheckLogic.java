/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;

/**
 * 会員情報データチェック<br/>
 *
 * @author natume
 * @version $Revision: 1.2 $
 */
public interface MemberInfoDataCheckLogic {

    /**
     * 会員ID重複エラー<br/>
     * <code>MSGCD_MEMBERINFOENTITYDTO_NULL</code>
     */
    public static final String MSGCD_MEMBERINFO_ID_MULTI = "LMM000501";

    /**
     * 会員情報データチェック処理<br/>
     *
     * @param memberInfoEntity 会員エンティティ
     */
    void execute(MemberInfoEntity memberInfoEntity);

    /**
     * 会員情報データチェック処理<br/>
     *
     * @param memberInfoMail   メールアドレス
     * @param memberInfoStatus 会員ステータス
     */
    void execute(String memberInfoMail, HTypeMemberInfoStatus memberInfoStatus);
}
