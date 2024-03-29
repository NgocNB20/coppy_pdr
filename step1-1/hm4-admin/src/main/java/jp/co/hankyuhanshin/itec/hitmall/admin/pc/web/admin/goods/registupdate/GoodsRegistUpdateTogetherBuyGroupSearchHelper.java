// 2023-renew No21 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.registupdate;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.AbstractGoodsRegistUpdateHelper;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeRegisterMethodType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchResultDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsTogetherBuyGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商品管理：商品登録更新（よく一緒に購入される商品設定検索）ページDxo<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class GoodsRegistUpdateTogetherBuyGroupSearchHelper extends AbstractGoodsRegistUpdateHelper {

    /**
     * 画面表示・再表示<br/>
     * 初期表示情報をページクラスにセット<br />
     *
     * @param goodsRegistUpdateTogetherBuyGroupSearchModel ページ
     */
    public void toPageForLoad(GoodsRegistUpdateTogetherBuyGroupSearchModel goodsRegistUpdateTogetherBuyGroupSearchModel) {
        if (goodsRegistUpdateTogetherBuyGroupSearchModel.getTmpGoodsTogetherBuyGroupEntityList() == null
            && goodsRegistUpdateTogetherBuyGroupSearchModel.getRedirectGoodsTogetherBuyGroupEntityList() != null) {
            goodsRegistUpdateTogetherBuyGroupSearchModel.setTmpGoodsTogetherBuyGroupEntityList(
                            goodsRegistUpdateTogetherBuyGroupSearchModel.getRedirectGoodsTogetherBuyGroupEntityList());
        }
    }

    /**
     * 入力情報をページに反映<br/>
     *
     * @param goodsRegistUpdateTogetherBuyGroupSearchModel ページ
     */
    public void toPageForNext(GoodsRegistUpdateTogetherBuyGroupSearchModel goodsRegistUpdateTogetherBuyGroupSearchModel) {
        goodsRegistUpdateTogetherBuyGroupSearchModel.setRedirectGoodsTogetherBuyGroupEntityList(
                        goodsRegistUpdateTogetherBuyGroupSearchModel.getTmpGoodsTogetherBuyGroupEntityList());
    }

    /**
     * 入力情報をページに反映（商品登録更新の他ページへ遷移時）<br/>
     *
     * @param goodsRegistUpdateTogetherBuyGroupSearchModel ページ
     */
    public void toPageForOther(GoodsRegistUpdateTogetherBuyGroupSearchModel goodsRegistUpdateTogetherBuyGroupSearchModel) {
        // tmpよく一緒に購入される商品エンティティリストをセッションにセット
        goodsRegistUpdateTogetherBuyGroupSearchModel.setGoodsTogetherBuyGroupEntityList(
                        goodsRegistUpdateTogetherBuyGroupSearchModel.getTmpGoodsTogetherBuyGroupEntityList());
    }

    /**
     * よく一緒に購入される商品情報追加<br/>
     *
     * @param goodsRegistUpdateTogetherBuyGroupSearchModel ページ
     */
    public void toPageForAddGoodsTogetherBuyGroup(GoodsRegistUpdateTogetherBuyGroupSearchModel goodsRegistUpdateTogetherBuyGroupSearchModel) {
        if (goodsRegistUpdateTogetherBuyGroupSearchModel.getGoodsGroupDto() == null
            || goodsRegistUpdateTogetherBuyGroupSearchModel.getGoodsGroupDto().getGoodsGroupEntity() == null
            || goodsRegistUpdateTogetherBuyGroupSearchModel.getTmpGoodsTogetherBuyGroupEntityList() == null
            || goodsRegistUpdateTogetherBuyGroupSearchModel.getResultItems() == null) {
            return;
        }

        int sizeMethodTypeAuto =
                        (int) goodsRegistUpdateTogetherBuyGroupSearchModel.getTmpGoodsTogetherBuyGroupEntityList()
                                                                          .stream()
                                                                          .filter(goodsTogetherBuyGroupEntity -> goodsTogetherBuyGroupEntity.getRegistMethod()
                                                                                                                                            .equals(HTypeRegisterMethodType.BATCH))
                                                                          .count();

        for (Iterator<GoodsRegistUpdateTogetherBuyGroupSearchItem> it =
             goodsRegistUpdateTogetherBuyGroupSearchModel.getResultItems().iterator(); it.hasNext(); ) {
            GoodsRegistUpdateTogetherBuyGroupSearchItem item = it.next();
            if (!item.isResultCheck()) {
                continue;
            }

            // よく一緒に購入される商品エンティティを生成してリストにセット
            GoodsTogetherBuyGroupEntity goodsTogetherBuyGroupEntity =
                            ApplicationContextUtility.getBean(GoodsTogetherBuyGroupEntity.class);
            goodsTogetherBuyGroupEntity.setGoodsGroupSeq(goodsRegistUpdateTogetherBuyGroupSearchModel.getGoodsGroupDto()
                                                                                                     .getGoodsGroupEntity()
                                                                                                     .getGoodsGroupSeq());
            goodsTogetherBuyGroupEntity.setGoodsTogetherBuyGroupSeq(item.getResultGoodsGroupSeq());
            goodsTogetherBuyGroupEntity.setOrderDisplay(
                            goodsRegistUpdateTogetherBuyGroupSearchModel.getTmpGoodsTogetherBuyGroupEntityList().size()
                            + 1 - sizeMethodTypeAuto);
            goodsTogetherBuyGroupEntity.setRegistMethod(HTypeRegisterMethodType.BACK);
            goodsTogetherBuyGroupEntity.setGoodsGroupCode(item.getResultGoodsGroupCode());
            goodsTogetherBuyGroupEntity.setGoodsGroupName(item.getResultGoodsGroupName());
            goodsTogetherBuyGroupEntity.setGoodsOpenStatusPC(EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class,
                                                                                           item.getResultGoodsOpenStatusPC()
                                                                                          ));

            // よく一緒に購入される商品DTOをページのtmpよく一緒に購入される商品リストに追加
            goodsRegistUpdateTogetherBuyGroupSearchModel.getTmpGoodsTogetherBuyGroupEntityList()
                                                        .add(goodsTogetherBuyGroupEntity);
        }

        List<GoodsTogetherBuyGroupEntity> sortList =
                        goodsRegistUpdateTogetherBuyGroupSearchModel.getTmpGoodsTogetherBuyGroupEntityList()
                                                                    .stream()
                                                                    .sorted((o1, o2) -> Integer.compare(
                                                                                    o1.getOrderDisplay(),
                                                                                    o2.getOrderDisplay()
                                                                                                       ))
                                                                    .collect(Collectors.toList());
        goodsRegistUpdateTogetherBuyGroupSearchModel.setTmpGoodsTogetherBuyGroupEntityList(sortList);

        // よく一緒に購入される商品ページへのRedirectScope変数にセット
        goodsRegistUpdateTogetherBuyGroupSearchModel.setRedirectGoodsTogetherBuyGroupEntityList(
                        goodsRegistUpdateTogetherBuyGroupSearchModel.getTmpGoodsTogetherBuyGroupEntityList());
    }

    /**
     * 検索条件の作成<br/>
     *
     * @param goodsRegistUpdateTogetherBuyGroupSearchModel よく一緒に購入される商品ページ
     * @return 商品グループ検索条件Dto
     */
    public GoodsSearchForBackDaoConditionDto toGoodsGroupSearchForDaoConditionDtoForSearch(
                    GoodsRegistUpdateTogetherBuyGroupSearchModel goodsRegistUpdateTogetherBuyGroupSearchModel) {
        GoodsSearchForBackDaoConditionDto conditionDto =
                        ApplicationContextUtility.getBean(GoodsSearchForBackDaoConditionDto.class);

        /* 画面条件 */
        // キーワード
        if (goodsRegistUpdateTogetherBuyGroupSearchModel.getSearchGoodsTogetherBuyGroupKeyword() != null) {
            String[] searchKeywordArray =
                            goodsRegistUpdateTogetherBuyGroupSearchModel.getSearchGoodsTogetherBuyGroupKeyword()
                                                                        .split("[\\s|　]+");

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
        String categoryPath = goodsRegistUpdateTogetherBuyGroupSearchModel.getCategoryPathMap()
                                                                          .get(goodsRegistUpdateTogetherBuyGroupSearchModel.getSearchCategoryId());
        conditionDto.setCategoryPath(categoryPath);

        // 商品グループコード
        conditionDto.setGoodsGroupCode(
                        goodsRegistUpdateTogetherBuyGroupSearchModel.getSearchGoodsTogetherBuyGroupCode());

        // 商品名
        conditionDto.setGoodsGroupName(
                        goodsRegistUpdateTogetherBuyGroupSearchModel.getSearchGoodsTogetherBuyGroupName());

        // サイト種別：PCとモバイル
        conditionDto.setSite("0");

        conditionDto.setGoodsTogetherBuyGroupSearchFlag(true);
        return conditionDto;
    }

    /**
     * 検索結果をページに反映<br/>
     *
     * @param resultDtoList       検索結果リスト
     * @param goodsRegistUpdateTogetherBuyGroupSearchModel ページ
     * @param conditionDto ページ
     */
    public void toPageForSearch(List<GoodsSearchResultDto> resultDtoList,
                                GoodsRegistUpdateTogetherBuyGroupSearchModel goodsRegistUpdateTogetherBuyGroupSearchModel,
                                GoodsSearchForBackDaoConditionDto conditionDto) {
        // オフセット + 1をNoにセット
        int index = conditionDto.getOffset() + 1;
        List<GoodsRegistUpdateTogetherBuyGroupSearchItem> resultItemList = new ArrayList<>();
        for (GoodsSearchResultDto resultDto : resultDtoList) {
            GoodsRegistUpdateTogetherBuyGroupSearchItem resultItem =
                            ApplicationContextUtility.getBean(GoodsRegistUpdateTogetherBuyGroupSearchItem.class);
            resultItem.setResultDspNo(index++);
            resultItem.setResultGoodsGroupSeq(resultDto.getGoodsGroupSeq());
            resultItem.setResultGoodsGroupCode(resultDto.getGoodsGroupCode());
            resultItem.setResultGoodsGroupName(resultDto.getGoodsGroupNameAdmin());
            resultItem.setResultGoodsOpenStatusPC(resultDto.getGoodsOpenStatusPC().getValue());
            if (resultDto.getRegistMethod() != null) {
                resultItem.setRegistMethod(resultDto.getRegistMethod().getValue());
            }
            resultItemList.add(resultItem);
        }
        goodsRegistUpdateTogetherBuyGroupSearchModel.setResultItems(resultItemList);
    }

    /**
     * カテゴリパスマップ<br/>
     *
     * @param goodsRegistUpdateTogetherBuyGroupSearchModel 在庫集計ページ
     * @param list                カテゴリエンティティリスト
     */
    public void setCategoryPathMap(GoodsRegistUpdateTogetherBuyGroupSearchModel goodsRegistUpdateTogetherBuyGroupSearchModel,
                                   List<CategoryEntity> list) {
        Map<String, String> map = new HashMap<>();
        for (CategoryEntity entity : list) {
            map.put(entity.getCategoryId(), entity.getCategoryPath());
        }
        goodsRegistUpdateTogetherBuyGroupSearchModel.setCategoryPathMap(map);
    }

}
// 2023-renew No21 to here