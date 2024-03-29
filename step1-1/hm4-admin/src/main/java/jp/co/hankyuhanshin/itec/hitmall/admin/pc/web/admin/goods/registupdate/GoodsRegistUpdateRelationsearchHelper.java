/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.registupdate;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.AbstractGoodsRegistUpdateHelper;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchResultDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsRelationEntity;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 商品管理：商品登録更新（関連商品設定検索）ページDxo<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class GoodsRegistUpdateRelationsearchHelper extends AbstractGoodsRegistUpdateHelper {

    /**
     * 画面表示・再表示<br/>
     * 初期表示情報をページクラスにセット<br />
     *
     * @param relationsearchModel ページ
     */
    public void toPageForLoad(GoodsRegistUpdateRelationsearchModel relationsearchModel) {
        if (relationsearchModel.getTmpGoodsRelationEntityList() == null
            && relationsearchModel.getRedirectGoodsRelationEntityList() != null) {
            relationsearchModel.setTmpGoodsRelationEntityList(relationsearchModel.getRedirectGoodsRelationEntityList());
        }
    }

    /**
     * 入力情報をページに反映<br/>
     *
     * @param relationsearchModel ページ
     */
    public void toPageForNext(GoodsRegistUpdateRelationsearchModel relationsearchModel) {
        relationsearchModel.setRedirectGoodsRelationEntityList(relationsearchModel.getTmpGoodsRelationEntityList());
    }

    /**
     * 入力情報をページに反映（商品登録更新の他ページへ遷移時）<br/>
     *
     * @param relationsearchModel ページ
     */
    public void toPageForOther(GoodsRegistUpdateRelationsearchModel relationsearchModel) {
        // tmp関連商品エンティティリストをセッションにセット
        relationsearchModel.setGoodsRelationEntityList(relationsearchModel.getTmpGoodsRelationEntityList());
    }

    /**
     * 関連商品情報追加<br/>
     *
     * @param relationsearchModel ページ
     */
    public void toPageForAddRelationGoods(GoodsRegistUpdateRelationsearchModel relationsearchModel) {
        if (relationsearchModel.getGoodsGroupDto() == null
            || relationsearchModel.getGoodsGroupDto().getGoodsGroupEntity() == null
            || relationsearchModel.getTmpGoodsRelationEntityList() == null
            || relationsearchModel.getResultItems() == null) {
            return;
        }

        for (Iterator<GoodsRegistUpdateRelationsearchItem> it =
             relationsearchModel.getResultItems().iterator(); it.hasNext(); ) {
            GoodsRegistUpdateRelationsearchItem item = it.next();
            if (!item.isResultCheck()) {
                continue;
            }

            // 関連商品エンティティを生成してリストにセット
            GoodsRelationEntity goodsRelationEntity = ApplicationContextUtility.getBean(GoodsRelationEntity.class);
            goodsRelationEntity.setGoodsGroupSeq(
                            relationsearchModel.getGoodsGroupDto().getGoodsGroupEntity().getGoodsGroupSeq());
            goodsRelationEntity.setGoodsRelationGroupSeq(item.getResultGoodsGroupSeq());
            goodsRelationEntity.setOrderDisplay(relationsearchModel.getTmpGoodsRelationEntityList().size() + 1);
            goodsRelationEntity.setGoodsGroupCode(item.getResultGoodsGroupCode());
            // 2023-renew No64 from here
            goodsRelationEntity.setGoodsGroupNameAdmin(item.getResultGoodsGroupName());
            // 2023-renew No64 to here
            goodsRelationEntity.setGoodsOpenStatusPC(EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class,
                                                                                   item.getResultGoodsOpenStatusPC()
                                                                                  ));

            // 関連商品DTOをページのtmp関連商品リストに追加
            relationsearchModel.getTmpGoodsRelationEntityList().add(goodsRelationEntity);
        }

        // 関連商品ページへのRedirectScope変数にセット
        relationsearchModel.setRedirectGoodsRelationEntityList(relationsearchModel.getTmpGoodsRelationEntityList());
    }

    /**
     * 検索条件の作成<br/>
     *
     * @param relationsearchModel 関連商品ページ
     * @return 商品グループ検索条件Dto
     */
    public GoodsSearchForBackDaoConditionDto toGoodsGroupSearchForDaoConditionDtoForSearch(
                    GoodsRegistUpdateRelationsearchModel relationsearchModel) {
        GoodsSearchForBackDaoConditionDto conditionDto =
                        ApplicationContextUtility.getBean(GoodsSearchForBackDaoConditionDto.class);

        /* 画面条件 */
        // キーワード
        if (relationsearchModel.getSearchRelationGoodsKeyword() != null) {
            String[] searchKeywordArray = relationsearchModel.getSearchRelationGoodsKeyword().split("[\\s|　]+");

            for (int i = 0; i < searchKeywordArray.length; i++) {
                switch (i) {
                    case 0:
                        conditionDto.setKeywordLikeCondition1(searchKeywordArray[i].trim());
                        break;
                    case 1:
                        conditionDto.setKeywordLikeCondition2(searchKeywordArray[i].trim());
                        break;
                    case 2:
                        conditionDto.setKeywordLikeCondition3(searchKeywordArray[i].trim());
                        break;
                    case 3:
                        conditionDto.setKeywordLikeCondition4(searchKeywordArray[i].trim());
                        break;
                    case 4:
                        conditionDto.setKeywordLikeCondition5(searchKeywordArray[i].trim());
                        break;
                    case 5:
                        conditionDto.setKeywordLikeCondition6(searchKeywordArray[i].trim());
                        break;
                    case 6:
                        conditionDto.setKeywordLikeCondition7(searchKeywordArray[i].trim());
                        break;
                    case 7:
                        conditionDto.setKeywordLikeCondition8(searchKeywordArray[i].trim());
                        break;
                    case 8:
                        conditionDto.setKeywordLikeCondition9(searchKeywordArray[i].trim());
                        break;
                    case 9:
                        conditionDto.setKeywordLikeCondition10(searchKeywordArray[i].trim());
                        break;
                    default:
                        break;
                }
            }
        }

        // カテゴリーパス
        String categoryPath = relationsearchModel.getCategoryPathMap().get(relationsearchModel.getSearchCategoryId());
        conditionDto.setCategoryPath(categoryPath);

        // 商品グループコード
        conditionDto.setGoodsGroupCode(relationsearchModel.getSearchGoodsGroupCode());

        // 商品名
        conditionDto.setGoodsGroupName(relationsearchModel.getSearchGoodsGroupName());

        // サイト種別：PCとモバイル
        conditionDto.setSite("0");

        // ページ情報 limit offset order
        //        conditionDto.setPageInfo(relationsearchModel.getPageInfo());

        conditionDto.setRelationGoodsSearchFlag(true);
        return conditionDto;
    }

    /**
     * 検索結果をページに反映<br/>
     *
     * @param resultDtoList       検索結果リスト
     * @param relationsearchModel ページ
     */
    public void toPageForSearch(List<GoodsSearchResultDto> resultDtoList,
                                GoodsRegistUpdateRelationsearchModel relationsearchModel,
                                GoodsSearchForBackDaoConditionDto conditionDto) {
        // オフセット + 1をNoにセット
        int index = conditionDto.getOffset() + 1;
        List<GoodsRegistUpdateRelationsearchItem> resultItemList = new ArrayList<>();
        for (GoodsSearchResultDto resultDto : resultDtoList) {
            GoodsRegistUpdateRelationsearchItem resultItem =
                            ApplicationContextUtility.getBean(GoodsRegistUpdateRelationsearchItem.class);
            resultItem.setResultDspNo(index++);
            resultItem.setResultGoodsGroupSeq(resultDto.getGoodsGroupSeq());
            resultItem.setResultGoodsGroupCode(resultDto.getGoodsGroupCode());
            // 2023-renew No64 from here
            resultItem.setResultGoodsGroupName(resultDto.getGoodsGroupNameAdmin());
            // 2023-renew No64 to here
            resultItem.setResultGoodsOpenStatusPC(resultDto.getGoodsOpenStatusPC().getValue());
            resultItemList.add(resultItem);
        }
        relationsearchModel.setResultItems(resultItemList);
    }

    /**
     * カテゴリパスマップ<br/>
     *
     * @param relationsearchModel 在庫集計ページ
     * @param list                カテゴリエンティティリスト
     */
    public void setCategoryPathMap(GoodsRegistUpdateRelationsearchModel relationsearchModel,
                                   List<CategoryEntity> list) {
        Map<String, String> map = new HashMap<>();
        for (CategoryEntity entity : list) {
            map.put(entity.getCategoryId(), entity.getCategoryPath());
        }
        relationsearchModel.setCategoryPathMap(map);
    }

}
