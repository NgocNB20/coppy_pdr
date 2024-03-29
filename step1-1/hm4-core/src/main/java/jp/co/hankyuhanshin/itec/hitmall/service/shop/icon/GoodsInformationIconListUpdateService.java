/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.icon;

import jp.co.hankyuhanshin.itec.hitmall.dto.icon.GoodsInformationIconDto;

import java.util.List;

/**
 * アイコンリスト更新
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
public interface GoodsInformationIconListUpdateService {

    /* メッセージ SSM0002 */

    /**
     * アイコンリスト更新
     *
     * @param iconDtoList アイコンDtoリスト
     * @return 処理件数
     */
    int execute(List<GoodsInformationIconDto> iconDtoList);
}
