/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryDetailsDto;

import java.util.List;

/**
 * 公開カテゴリ情報リスト取得<br/>
 *
 * @author ozaki
 * @version $Revision: 1.1 $
 */
public interface OpenCategoryListGetService {

    /**
     * カテゴリSEQリストを元にカテゴリDTOのリストを取得する<br/>
     *
     * @param categorySeqList カテゴリSEQリスト
     * @return カテゴリ詳細DTOリスト
     */
    List<CategoryDetailsDto> execute(List<Integer> categorySeqList);

}
