// 2023-renew No65 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.restockannounce.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.restockannounce.RestockAnnounceDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.restockannounce.RestockAnnounceSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.restockannounce.RestockAnnounceEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.restockannounce.RestockAnnounceListGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.AssertionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 入荷お知らせ情報リスト取得ロジック<br/>
 *
 * @author Thang Doan (VJP)
 */
@Component
public class RestockAnnounceListGetLogicImpl extends AbstractShopLogic implements RestockAnnounceListGetLogic {

    /**
     * 入荷お知らせDao
     **/
    private final RestockAnnounceDao restockAnnounceDao;

    /**
     * コンストラクタ
     *
     * @param restockAnnounceDao 入荷お知らせDao
     */
    @Autowired
    public RestockAnnounceListGetLogicImpl(RestockAnnounceDao restockAnnounceDao) {
        this.restockAnnounceDao = restockAnnounceDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param restockAnnounceConditionDto 入荷お知らせ検索条件DTO
     * @return 入荷お知らせエンティティリスト
     */
    @Override
    public List<RestockAnnounceEntity> execute(RestockAnnounceSearchForDaoConditionDto restockAnnounceConditionDto) {

        // 引数チェック
        AssertionUtil.assertNotNull("restockAnnounceConditionDto", restockAnnounceConditionDto);

        return restockAnnounceDao.getSearchRestockAnnounceList(restockAnnounceConditionDto,
                                                               restockAnnounceConditionDto.getPageInfo()
                                                                                          .getSelectOptions()
                                                              );
    }
}
// 2023-renew No65 to here
