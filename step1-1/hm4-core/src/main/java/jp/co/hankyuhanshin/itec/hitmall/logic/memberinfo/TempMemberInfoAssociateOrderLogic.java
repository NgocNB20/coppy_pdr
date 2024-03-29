/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;

/**
 * 仮会員情報受注情報紐付けロジック<br/>
 *
 * @author s_nose
 */
public interface TempMemberInfoAssociateOrderLogic {

    /**
     * 仮会員情報受注情報紐付け<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @param orderSeq      受注SEQ
     */
    void execute(Integer memberInfoSeq,
                 Integer orderSeq,
                 String memberInfoId,
                 HTypeSiteType siteType,
                 HTypeDeviceType deviceType,
                 String userAgent,
                 String administratorLastName,
                 String administratorFirstName);

}
