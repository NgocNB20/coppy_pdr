/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.category;

/**
 * カテゴリ内商品の並び順変更<br/>
 *
 * @author kimura
 * @version $Revision: 1.1 $
 */
public interface CategoryModifyGoodsOrderService {

    /**
     * カテゴリの並び順変更<br/>
     *
     * @param categorySeq      カテゴリSEQ
     * @param fromOrderDisplay From並び順
     * @param toOrderDisplay   To並び順
     * @return 件数
     */
    int execute(Integer categorySeq, Integer fromOrderDisplay, Integer toOrderDisplay);

}
