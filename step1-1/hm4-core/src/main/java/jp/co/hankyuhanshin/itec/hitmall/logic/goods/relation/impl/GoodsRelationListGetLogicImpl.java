/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.relation.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsRelationDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsRelationSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsRelationEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.relation.GoodsRelationListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.web.PageInfo;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 関連商品詳細情報リスト取得<br/>
 * 対象商品の関連商品リスト（商品Dtoリスト）を取得する。<br/>
 *
 * @author ozaki
 * @version $Revision: 1.5 $
 */
@Component
public class GoodsRelationListGetLogicImpl extends AbstractShopLogic implements GoodsRelationListGetLogic {

    /**
     * 関連商品DAO
     */
    private final GoodsRelationDao goodsRelationDao;

    /**
     * 商品グループリスト取得ロジッククラス
     */
    private final GoodsGroupListGetLogic goodsGroupListGetLogic;

    @Autowired
    public GoodsRelationListGetLogicImpl(GoodsRelationDao goodsRelationDao,
                                         GoodsGroupListGetLogic goodsGroupListGetLogic) {
        this.goodsRelationDao = goodsRelationDao;
        this.goodsGroupListGetLogic = goodsGroupListGetLogic;
    }

    /**
     * 関連商品詳細情報リスト取得<br/>
     *
     * @param goodsRelationSearchForDaoConditionDto 関連商品Dao用検索条件Dto
     * @return 関連商品情報リスト
     */
    @Override
    public List<GoodsGroupDto> execute(GoodsRelationSearchForDaoConditionDto goodsRelationSearchForDaoConditionDto) {

        List<GoodsGroupDto> goodsGroupDtoList = new ArrayList<>();

        // (1) パラメータチェック
        // 関連商品Dao用検索条件DTOが null でないことをチェック
        ArgumentCheckUtil.assertNotNull("goodsRelationSearchForDaoConditionDto", goodsRelationSearchForDaoConditionDto);

        // (2) 関連商品リスト取得
        // 関連商品Daoの関連商品マップ検索処理を実行する。
        List<GoodsRelationEntity> goodsRelationList = goodsRelationDao.getSearchGoodsRelation(
                        goodsRelationSearchForDaoConditionDto,
                        goodsRelationSearchForDaoConditionDto.getPageInfo().getSelectOptions()
                                                                                             );
        if (CollectionUtil.isEmpty(goodsRelationList)) {
            return goodsGroupDtoList;
        }

        // (3) 商品詳細DTO取得
        // (2) で取得した関連商品エンティティリストから関連商品エンティティ．商品SEQのリストを作成する。
        List<Integer> goodsGroupSeqList = new ArrayList<>();
        for (GoodsRelationEntity entity : goodsRelationList) {
            goodsGroupSeqList.add(entity.getGoodsRelationGroupSeq());
        }

        // 共通ロジックを使用するので、コンディションを入れ替え
        GoodsGroupSearchForDaoConditionDto conditionDto =
                        changeCondition(goodsRelationSearchForDaoConditionDto, goodsGroupSeqList);

        PageInfo pageInfo = ApplicationContextUtility.getBean(PageInfo.class);
        pageInfo.setLimit(goodsRelationSearchForDaoConditionDto.getPageInfo().getLimit());
        pageInfo.setSkipCountFlg(true);
        pageInfo.setupSelectOptions();

        conditionDto.setPageInfo(pageInfo);
        // 商品の詳細情報取得
        goodsGroupDtoList = goodsGroupListGetLogic.execute(conditionDto);

        if (CollectionUtil.isEmpty(goodsGroupDtoList)) {
            return goodsGroupDtoList;
        }

        // (5) 戻り値
        // 商品情報が取得できた場合は、関連商品の表示順に並び変えて商品グループDTOリストを返却する
        return sortRelationOrderDisplay(goodsGroupSeqList, goodsGroupDtoList);

    }

    /**
     * コンディションを入れ替え<br/>
     * ※商品グループDTOリスト取得のためのロジックが異なるコンディションクラスなので入れ替える
     *
     * @param goodsRelationSearchForDaoConditionDto 関連商品Dao用検索条件Dto
     * @param goodsGroupSeqList                     商品グループSEQリスト
     * @return 商品グループDAO用検索条件DTO
     */
    protected GoodsGroupSearchForDaoConditionDto changeCondition(GoodsRelationSearchForDaoConditionDto goodsRelationSearchForDaoConditionDto,
                                                                 List<Integer> goodsGroupSeqList) {
        GoodsGroupSearchForDaoConditionDto conditionDto =
                        ApplicationContextUtility.getBean(GoodsGroupSearchForDaoConditionDto.class);
        conditionDto.setGoodsGroupSeqList(goodsGroupSeqList);
        conditionDto.setMemberRank(goodsRelationSearchForDaoConditionDto.getMemberRank());
        conditionDto.setAuthenticatedSaleIdList(goodsRelationSearchForDaoConditionDto.getSaleId());
        conditionDto.setSiteType(goodsRelationSearchForDaoConditionDto.getSiteType());
        conditionDto.setOpenStatus(goodsRelationSearchForDaoConditionDto.getOpenStatus());
        return conditionDto;
    }

    /**
     * 商品グループDtoリストを関連商品の並び順でソートする
     *
     * @param goodsGroupSeqList 関連商品の並び順を保持した商品グループSEQリスト
     * @param goodsGroupDtoList 並び変え前の商品グループDtoリスト
     * @return 関連商品の並び順に並び替えた商品グループDtoリスト
     */
    protected List<GoodsGroupDto> sortRelationOrderDisplay(List<Integer> goodsGroupSeqList,
                                                           List<GoodsGroupDto> goodsGroupDtoList) {

        List<GoodsGroupDto> sortGoodsGroupDtoList = new ArrayList<>();
        for (Integer goodsGroupSeq : goodsGroupSeqList) {
            for (GoodsGroupDto goodsGroupDto : goodsGroupDtoList) {
                // 並び順を保持した商品グループSEQ と 商品グループDto が一致する場合は、返却用の商品グループDtoリストに追加する
                if (goodsGroupSeq.compareTo(goodsGroupDto.getGoodsGroupEntity().getGoodsGroupSeq()) == 0) {
                    sortGoodsGroupDtoList.add(goodsGroupDto);
                    break;
                }
            }
        }
        return sortGoodsGroupDtoList;

    }
}
