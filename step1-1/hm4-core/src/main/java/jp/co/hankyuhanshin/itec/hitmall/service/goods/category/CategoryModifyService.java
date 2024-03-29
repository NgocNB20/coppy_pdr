/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryDto;

/**
 * カテゴリ修正<br/>
 *
 * @author kimura
 * @version $Revision: 1.1 $
 */
public interface CategoryModifyService {

    /**
     * カテゴリの修正<br/>
     *
     * @param categoryDto カテゴリ情報DTO
     * @return 件数
     */
    int execute(CategoryDto categoryDto);

}
