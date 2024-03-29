/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.category;

/**
 * 現在のMAXSEQを取得
 *
 * @author kimura
 * @version $Revision: 1.1 $
 */
public interface CategoryGetMaxCategorySeqService {

    /**
     * 現在のMAXSEQを取得<br/>
     *
     * @return MAXSEQ
     */
    int execute();

}
