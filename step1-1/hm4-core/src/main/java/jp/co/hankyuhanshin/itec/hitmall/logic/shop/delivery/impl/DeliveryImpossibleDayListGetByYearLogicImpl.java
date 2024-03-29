/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.DeliveryImpossibleDayDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryImpossibleDaySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryImpossibleDayEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryImpossibleDayListGetByYearLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * お届け不可日リスト取得ロジック<br/>
 *
 * @author Author: ty32113
 */
@Component
public class DeliveryImpossibleDayListGetByYearLogicImpl extends AbstractShopLogic
                implements DeliveryImpossibleDayListGetByYearLogic {

    /**
     * お届け不可日Dao
     */
    private final DeliveryImpossibleDayDao deliveryImpossibleDayDao;

    @Autowired
    public DeliveryImpossibleDayListGetByYearLogicImpl(DeliveryImpossibleDayDao deliveryImpossibleDayDao) {
        this.deliveryImpossibleDayDao = deliveryImpossibleDayDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param deliveryImpossibleDaySearchForDaoConditionDto お届け不可日検索条件DTO
     * @return お届け不可日エンティティリスト
     */
    @Override
    public List<DeliveryImpossibleDayEntity> execute(DeliveryImpossibleDaySearchForDaoConditionDto deliveryImpossibleDaySearchForDaoConditionDto) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull(
                        "deliveryImpossibleDaySearchForDaoConditionDto", deliveryImpossibleDaySearchForDaoConditionDto);

        // お届け不可日の検索
        return deliveryImpossibleDayDao.getSearchDeliveryImpossibleDayList(
                        deliveryImpossibleDaySearchForDaoConditionDto,
                        deliveryImpossibleDaySearchForDaoConditionDto.getPageInfo().getSelectOptions()
                                                                          );
    }

}
