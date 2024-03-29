/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.icon;

import jp.co.hankyuhanshin.itec.hitmall.dto.icon.GoodsInformationIconDto;

/**
 * アイコン取得
 *
 * @author shibuya
 * @version $Revision: 1.2 $
 */
public interface GoodsInformationIconGetService {

    /* メッセージ SSM0004 */
    /**
     * 商品インフォメーションアイコン取得失敗エラー<br/>
     * <code>MSGCD_GOODSINFORMATIONICON_GET_FAIL</code>
     */
    String MSGCD_GOODSINFORMATIONICON_GET_FAIL = "SSM000401";

    /**
     * 商品インフォメーションアイコン取得失敗エラー<br/>
     * <code>MSGCD_GOODSINFORMATIONICONIMAGE_GET_FAIL</code>
     */
    String MSGCD_GOODSINFORMATIONICONIMAGE_GET_FAIL = "SSM000402";

    /**
     * アイコン取得
     *
     * @param iconSeq アイコンSEQ
     * @return 指定アイコンのアイコンDto
     */
    GoodsInformationIconDto execute(Integer iconSeq);
}
