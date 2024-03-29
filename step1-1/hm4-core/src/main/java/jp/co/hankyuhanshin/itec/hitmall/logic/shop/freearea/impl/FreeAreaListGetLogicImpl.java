/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.freearea.FreeAreaDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.freearea.FreeAreaSearchDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.FreeAreaEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea.FreeAreaListGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * フリーエリアリスト取得
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
@Component
public class FreeAreaListGetLogicImpl extends AbstractShopLogic implements FreeAreaListGetLogic {

    /**
     * フリーエリアDao
     */
    private final FreeAreaDao freeAreaDao;

    @Autowired
    public FreeAreaListGetLogicImpl(FreeAreaDao freeAreaDao) {
        this.freeAreaDao = freeAreaDao;
    }

    /**
     * フリーエリアリスト取得
     *
     * @param conditionDto 検索条件
     * @return フリーエリアエンティティリスト
     */
    @Override
    public List<FreeAreaEntity> execute(FreeAreaSearchDaoConditionDto conditionDto) {
        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("FreeAreaSearchDaoConditionDto", conditionDto);

        // フリーエリア情報リスト取得
        return freeAreaDao.getFreeAreaDtoExceptBodyList(conditionDto, conditionDto.getPageInfo().getSelectOptions());
    }
}
