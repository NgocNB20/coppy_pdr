/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.relation.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsRelationSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.relation.GoodsRelationListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.relation.OpenRelatedGoodsListGetService;
import jp.co.hankyuhanshin.itec.hitmall.web.PageInfo;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 公開関連商品情報取得<br/>
 * 公開されている関連商品の一覧情報を取得します。<br/>
 *
 * @author ozaki
 */
@Service
public class OpenRelatedGoodsListGetServiceImpl extends AbstractShopService implements OpenRelatedGoodsListGetService {

    /**
     * 関連商品情報取得ロジッククラス
     */
    private final GoodsRelationListGetLogic goodsRelationListGetLogic;

    @Autowired
    public OpenRelatedGoodsListGetServiceImpl(GoodsRelationListGetLogic goodsRelationListGetLogic) {
        this.goodsRelationListGetLogic = goodsRelationListGetLogic;
    }

    /**
     * 公開関連商品情報取得<br/>
     * 公開されている関連商品の一覧情報を取得します。<br/>
     *
     * @param conditionDto 関連商品検索条件DTO
     * @return 関連商品リスト情報DTO
     */
    @Override
    public List<GoodsGroupDto> execute(GoodsRelationSearchForDaoConditionDto conditionDto, Integer limit) {
        ArgumentCheckUtil.assertNotNull("conditionDto", conditionDto);

        conditionDto.setSiteType(HTypeSiteType.FRONT_PC);
        conditionDto.setOpenStatus(HTypeOpenDeleteStatus.OPEN);

        PageInfo pageInfo = ApplicationContextUtility.getBean(PageInfo.class);
        pageInfo.setLimit(limit);
        pageInfo.setSkipCountFlg(true);
        pageInfo.setupSelectOptions();
        conditionDto.setPageInfo(pageInfo);

        // ・関連商品詳細情報リスト取得処理を実行する
        List<GoodsGroupDto> goodsGroupDtoList = goodsRelationListGetLogic.execute(conditionDto);

        return goodsGroupDtoList;
    }

}
