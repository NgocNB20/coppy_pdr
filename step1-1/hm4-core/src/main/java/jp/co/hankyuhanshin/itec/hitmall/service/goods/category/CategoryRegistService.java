/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryDto;

/**
 * カテゴリ登録<br/>
 *
 * @author kimura
 * @version $Revision: 1.3 $
 */
public interface CategoryRegistService {

    /**
     * カテゴリの登録<br/>
     *
     * @param categoryDto      カテゴリ情報DTO
     *                         (当サービスでは、List<CategoryDto>を処理対象としない)
     * @param parentCategoryId 親カテゴリID
     * @return 件数
     */
    int execute(CategoryDto categoryDto, String parentCategoryId);

}
