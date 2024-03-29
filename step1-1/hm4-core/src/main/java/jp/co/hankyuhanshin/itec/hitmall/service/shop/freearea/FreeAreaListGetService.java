/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.freearea;

import jp.co.hankyuhanshin.itec.hitmall.dto.shop.freearea.FreeAreaSearchDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.FreeAreaEntity;

import java.util.List;

/**
 * フリーエリアリスト取得
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
public interface FreeAreaListGetService {

    /**
     * フリーエリアリスト取得
     *
     * @param conditionDto 検索条件
     * @return フリーエリアエンティティリスト
     */
    List<FreeAreaEntity> execute(FreeAreaSearchDaoConditionDto conditionDto);

}
