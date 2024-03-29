/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;

/**
 * カテゴリ削除<br/>
 *
 * @author kimura
 * @version $Revision: 1.1 $
 */
public interface CategoryRemoveService {

    /**
     * カテゴリ画像削除失敗エラー<br/>
     * <code>MSGCD_CATEGORYIMAGE_DELETE_FAIL</code>
     */
    public static final String MSGCD_CATEGORYIMAGE_DELETE_FAIL = "SGC001111";

    /**
     * カテゴリの削除<br/>
     *
     * @param categoryId カテゴリID
     * @return 件数
     */
    int execute(String categoryId, HTypeSiteType siteType);

}
