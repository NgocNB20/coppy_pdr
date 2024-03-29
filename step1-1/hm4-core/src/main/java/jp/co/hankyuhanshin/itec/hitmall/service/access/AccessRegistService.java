/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.access;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAccessType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;

/**
 *
 * アクセス情報登録<br/>
 * 内容別にアクセス情報を登録する<br/>
 *
 * @author kimura
 * @version $Revision: 1.5 $
 *
 */
public interface AccessRegistService {

    /**
     * アクセス情報登録<br/>
     * 内容別にアクセス情報を登録する<br/>
     * ・商品アクセス数記録機能<br/>
     * ・商品カート投入回数記録機能<br/>
     * ・受注回数記録機能<br/>
     * ・受注個数記録機能<br/>
     * ・受注金額記録機能<br/>
     * ・キャンペーンアクセス数記録機能<br/>
     * ・会員入会数<br/>
     * ・会員退会数<br/>
     * ・メルマガ入会数<br/>
     * ・メルマガ退会数<br/>
     * ・セッション<br/>
     * ・トップPV<br/>
     * @param accessType アクセス種別
     * @param goodsGroupSeq 商品SEQまたは、商品ｸﾞﾙｰﾌﾟSEQ(商品アクセス回数、商品カート投入回数、受注個数、受注金額の場合のみ有効、それ以外はnullを指定)
     * @param accessCount アクセス数(商品アクセス回数、受注個数、受注金額の場合のみ有効、それ以外はnullを指定)
     * @return 更新・登録件数
     */
    int execute(HTypeAccessType accessType,
                Integer goodsGroupSeq,
                Integer accessCount,
                String accessUid,
                HTypeSiteType siteType,
                String campaignCode);
}
