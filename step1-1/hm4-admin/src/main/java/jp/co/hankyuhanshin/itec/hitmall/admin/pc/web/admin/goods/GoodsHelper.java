package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchResultDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 商品管理：商品検索Helper
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Component
public class GoodsHelper {

    /**
     * カテゴリパスマップ<br/>
     *
     * @param goodsModel 商品モデル
     * @param list       カテゴリエンティティリスト
     */
    public void setCategoryPathMap(GoodsModel goodsModel, List<CategoryEntity> list) {
        Map<String, String> map = new HashMap<>();
        for (CategoryEntity dto : list) {
            map.put(dto.getCategoryId(), dto.getCategoryPath());
        }
        goodsModel.setCategoryPathMap(map);
    }

    /**
     * 検索条件の作成<br/>
     *
     * @param goodsModel 商品モデル
     * @return 商品検索条件Dto
     */
    public GoodsSearchForBackDaoConditionDto toGoodsSearchForBackDaoConditionDtoForSearch(GoodsModel goodsModel) {

        GoodsSearchForBackDaoConditionDto goodsSearchForBackDaoConditionDto =
                        ApplicationContextUtility.getBean(GoodsSearchForBackDaoConditionDto.class);

        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // カテゴリID 検索には使用しない
        goodsSearchForBackDaoConditionDto.setCategoryId(goodsModel.getSearchCategoryId());
        // カテゴリーパス
        String categoryPath = goodsModel.getCategoryPathMap().get(goodsModel.getSearchCategoryId());
        goodsSearchForBackDaoConditionDto.setCategoryPath(categoryPath);

        // 商品グループコード
        goodsSearchForBackDaoConditionDto.setGoodsGroupCode(goodsModel.getSearchGoodsGroupCode());

        // 商品コード
        goodsSearchForBackDaoConditionDto.setGoodsCode(goodsModel.getSearchGoodsCode());

        // JANコード
        goodsSearchForBackDaoConditionDto.setJanCode(goodsModel.getSearchJanCode());

        // カタログコード
        goodsSearchForBackDaoConditionDto.setCatalogCode(goodsModel.getSearchCatalogCode());

        // 下限販売可能在庫数
        if (goodsModel.getSearchMinSalesPossibleStockCount() != null) {
            goodsSearchForBackDaoConditionDto.setMinSalesPossibleStock(
                            new BigDecimal(goodsModel.getSearchMinSalesPossibleStockCount()));
        } else {
            goodsSearchForBackDaoConditionDto.setMinSalesPossibleStock(null);
        }

        // 上限販売可能在庫数
        if (goodsModel.getSearchMaxSalesPossibleStockCount() != null) {
            goodsSearchForBackDaoConditionDto.setMaxSalesPossibleStock(
                            new BigDecimal(goodsModel.getSearchMaxSalesPossibleStockCount()));
        } else {
            goodsSearchForBackDaoConditionDto.setMaxSalesPossibleStock(null);
        }

        // 価格（税抜）
        if (goodsModel.getSearchMinPrice() != null) {
            goodsSearchForBackDaoConditionDto.setMinPrice(new BigDecimal(goodsModel.getSearchMinPrice()));
        } else {
            goodsSearchForBackDaoConditionDto.setMinPrice(null);
        }

        if (goodsModel.getSearchMaxPrice() != null) {
            goodsSearchForBackDaoConditionDto.setMaxPrice(new BigDecimal(goodsModel.getSearchMaxPrice()));
        } else {
            goodsSearchForBackDaoConditionDto.setMaxPrice(null);
        }

        // 商品名
        goodsSearchForBackDaoConditionDto.setGoodsGroupName(goodsModel.getSearchGoodsGroupName());

        // サイト区分
        goodsSearchForBackDaoConditionDto.setSite(goodsModel.getSite());

        // 公開状態
        if (goodsModel.getGoodsOpenStatusArray() != null && goodsModel.getGoodsOpenStatusArray().length > 0) {
            goodsSearchForBackDaoConditionDto.setGoodsOpenStatusList(
                            Arrays.asList(goodsModel.getGoodsOpenStatusArray()));
        } else {
            goodsSearchForBackDaoConditionDto.setGoodsOpenStatusList(
                            Arrays.asList(HTypeOpenDeleteStatus.OPEN.getValue(),
                                          HTypeOpenDeleteStatus.NO_OPEN.getValue(),
                                          HTypeOpenDeleteStatus.DELETED.getValue()
                                         ));
        }

        // 販売状態
        if (goodsModel.getGoodsSaleStatusArray() != null && goodsModel.getGoodsSaleStatusArray().length > 0) {
            goodsSearchForBackDaoConditionDto.setSaleStatusList(Arrays.asList(goodsModel.getGoodsSaleStatusArray()));
        } else {
            goodsSearchForBackDaoConditionDto.setSaleStatusList(
                            Arrays.asList(HTypeGoodsSaleStatus.SALE.getValue(), HTypeGoodsSaleStatus.NO_SALE.getValue(),
                                          HTypeGoodsSaleStatus.DELETED.getValue()
                                         ));
        }

        // 登録・更新日時
        String selectDate = goodsModel.getSelectRegistOrUpdate();
        if (selectDate != null) {
            if (selectDate.equals("0")) {
                goodsSearchForBackDaoConditionDto.setRegistTimeFrom(
                                conversionUtility.toTimeStamp(goodsModel.getSearchRegOrUpTimeFrom()));
                if (goodsModel.getSearchRegOrUpTimeTo() != null) {
                    goodsSearchForBackDaoConditionDto.setRegistTimeTo(dateUtility.getEndOfDate(
                                    conversionUtility.toTimeStamp(goodsModel.getSearchRegOrUpTimeTo())));
                }
            } else if (selectDate.equals("1")) {
                goodsSearchForBackDaoConditionDto.setUpdateTimeFrom(
                                conversionUtility.toTimeStamp(goodsModel.getSearchRegOrUpTimeFrom()));
                if (goodsModel.getSearchRegOrUpTimeTo() != null) {
                    goodsSearchForBackDaoConditionDto.setUpdateTimeTo(dateUtility.getEndOfDate(
                                    conversionUtility.toTimeStamp(goodsModel.getSearchRegOrUpTimeTo())));
                }
            }
        }

        return goodsSearchForBackDaoConditionDto;
    }

    /**
     * 検索結果をページに反映<br/>
     *
     * @param goodsSearchResultDtoList 検索結果リスト
     * @param conditionDto             検索条件Dto
     * @param goodsModel               商品モデル
     */
    public void toPageForSearch(List<GoodsSearchResultDto> goodsSearchResultDtoList,
                                GoodsSearchForBackDaoConditionDto conditionDto,
                                GoodsModel goodsModel) {

        // オフセット + 1をNoにセット
        int index = conditionDto.getOffset() + 1;

        List<GoodsResultItem> resultItemList = new ArrayList<>();
        for (GoodsSearchResultDto goodsSearchResultDto : goodsSearchResultDtoList) {
            GoodsResultItem goodsResultItem = ApplicationContextUtility.getBean(GoodsResultItem.class);
            goodsResultItem.setResultNo(index++);
            goodsResultItem.setResultGoodsSeq(goodsSearchResultDto.getGoodsSeq());
            goodsResultItem.setGoodsGroupCode(goodsSearchResultDto.getGoodsGroupCode());
            goodsResultItem.setResultGoodsCode(goodsSearchResultDto.getGoodsCode());
            // 2023-renew No64 from here
            goodsResultItem.setResultGoodsGroupName(goodsSearchResultDto.getGoodsGroupNameAdmin());
            // 2023-renew No64 to here
            goodsResultItem.setResultUnitValue1(goodsSearchResultDto.getUnitValue1());
            goodsResultItem.setResultUnitValue2(goodsSearchResultDto.getUnitValue2());
            goodsResultItem.setResultUnitValue2(goodsSearchResultDto.getUnitValue2());
            goodsResultItem.setResultGoodsOpenStatusPC(goodsSearchResultDto.getGoodsOpenStatusPC().getValue());
            goodsResultItem.setResultGoodsOpenStartTimePC(goodsSearchResultDto.getOpenStartTimePC());
            goodsResultItem.setResultGoodsOpenEndTimePC(goodsSearchResultDto.getOpenEndTimePC());
            goodsResultItem.setResultSaleStatusPC(goodsSearchResultDto.getSaleStatusPC().getValue());
            goodsResultItem.setResultSaleStartTimePC(goodsSearchResultDto.getSaleStartTimePC());
            goodsResultItem.setResultSaleEndTimePC(goodsSearchResultDto.getSaleEndTimePC());
            goodsResultItem.setResultGoodsPrice(goodsSearchResultDto.getGoodsPrice());
            goodsResultItem.setResultStockManagementFlag(goodsSearchResultDto.getStockmanagementflag().getValue());
            goodsResultItem.setResultSalesPossibleStock(goodsSearchResultDto.getSalesPossibleStock());
            goodsResultItem.setResultRealStock(goodsSearchResultDto.getRealStock());
            goodsResultItem.setResultIndividualDeliveryType(
                            goodsSearchResultDto.getIndividualDeliveryType().getValue());

            resultItemList.add(goodsResultItem);
        }
        goodsModel.setResultItems(resultItemList);

        // 件数セット
        goodsModel.setTotalCount(conditionDto.getTotalCount());
    }

    /**
     * チェックされた商品SEQリストを作成<br/>
     *
     * @param goodsModel 商品検索ページ
     * @return 選択済みの商品SEQリスト
     */
    public List<Integer> toGoodsSeqList(GoodsModel goodsModel) {

        List<Integer> goodsSeqList = new ArrayList<>();

        for (Iterator<GoodsResultItem> it = goodsModel.getResultItems().iterator(); it.hasNext(); ) {
            GoodsResultItem goodsResultItem = it.next();
            if (goodsResultItem.isResultGoodsCheck()) {
                goodsSeqList.add(goodsResultItem.getResultGoodsSeq());
            }
        }
        return goodsSeqList;
    }
}
