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
 * 公開カテゴリパスリスト取得<br/>
 *
 * @author ozaki
 * @version $Revision: 1.1 $
 */
public interface OpenCategoryPassListGetService {

    /**
     * カテゴリSEQパスを元にカテゴリDTOのリストを取得する
     *
     * @param categorySeqPath カテゴリSEQパス
     * @return カテゴリ詳細DTOリスト
     */
    List<CategoryDetailsDto> execute(String categorySeqPath);

}
