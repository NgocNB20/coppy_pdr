/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.favorite;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.favorite.FavoriteSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.favorite.FavoriteSearchResultDto;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * 商品管理：お気に入り商品検索Helper
 *
 * @author takashima
 */
@Component
public class FavoriteHelper {

    /**
     * 検索条件の作成<br/>
     *
     * @param favoriteModel お気に入り商品モデル
     * @return お気に入り商品検索条件Dto
     */
    public FavoriteSearchForBackDaoConditionDto toFavoriteSearchForBackDaoConditionDtoSearch(FavoriteModel favoriteModel) {

        FavoriteSearchForBackDaoConditionDto favoriteSearchForBackDaoConditionDto =
                        ApplicationContextUtility.getBean(FavoriteSearchForBackDaoConditionDto.class);
        // メッセージコードリスト初期化
        favoriteModel.setMsgCodeList(new ArrayList<>());
        favoriteModel.setMsgArgMap(new HashMap<>());

        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // 商品グループコード
        favoriteSearchForBackDaoConditionDto.setGoodsGroupCode(favoriteModel.getSearchGoodsGroupCode());

        // 商品コード
        favoriteSearchForBackDaoConditionDto.setGoodsCode(favoriteModel.getSearchGoodsCode());

        // 商品名
        favoriteSearchForBackDaoConditionDto.setGoodsGroupNameAdmin(favoriteModel.getSearchGoodsGroupNameAdmin());

        // 顧客番号
        if (StringUtils.isNotEmpty(favoriteModel.getSearchCustomerNo())) {
            favoriteSearchForBackDaoConditionDto.setCustomerNo(
                            conversionUtility.toInteger(favoriteModel.getSearchCustomerNo()));
        }
        // セール状態
        if (favoriteModel.getFavoriteSaleStatusArray() != null
            && favoriteModel.getFavoriteSaleStatusArray().length > 0) {
            favoriteSearchForBackDaoConditionDto.setFavoriteSaleStatusList(
                            Arrays.asList(favoriteModel.getFavoriteSaleStatusArray()));
        }

        // セールコード
        favoriteSearchForBackDaoConditionDto.setSaleCode(favoriteModel.getSearchSaleCode());

        // セール開始日（FROM）
        favoriteSearchForBackDaoConditionDto.setSearchSaleStartTimeFrom(
                        conversionUtility.toTimeStamp(favoriteModel.getSearchSaleStartTimeFrom()));
        // セール開始日（TO）
        if (favoriteModel.getSearchSaleStartTimeTo() != null) {
            favoriteSearchForBackDaoConditionDto.setSearchSaleStartTimeTo(dateUtility.getEndOfDate(
                            conversionUtility.toTimeStamp(favoriteModel.getSearchSaleStartTimeTo())));
        }

        // セール終了日（FROM）
        favoriteSearchForBackDaoConditionDto.setSearchSaleEndTimeFrom(
                        conversionUtility.toTimeStamp(favoriteModel.getSearchSaleEndTimeFrom()));
        // セール終了日（TO）
        if (favoriteModel.getSearchSaleEndTimeTo() != null) {
            favoriteSearchForBackDaoConditionDto.setSearchSaleEndTimeTo(dateUtility.getEndOfDate(
                            conversionUtility.toTimeStamp(favoriteModel.getSearchSaleEndTimeTo())));
        }

        // 商品番号（複数番号検索用）
        if (favoriteModel.getConditionOrderCodeList() != null && StringUtil.isNotEmpty(
                        favoriteModel.getConditionOrderCodeList().replaceAll("[\\s|　]", ""))) {
            // 検索に有効な文字列が存在する場合
            // 選択肢区切り文字を設定ファイルから取得
            String divchar = PropertiesUtil.getSystemPropertiesValue("order.search.order.code.list.divchar");
            String goodsCodeList = favoriteModel.getConditionOrderCodeList();
            // 空白を削除する
            goodsCodeList = goodsCodeList.replaceAll("[ 　\t\\x0B\f]", "");
            // 2つ以上連続した改行コードを1つにまとめる
            goodsCodeList = goodsCodeList.replaceAll("(" + divchar + "){2,}", "\n");
            // 先頭または最後尾の改行コードを削除する
            goodsCodeList = goodsCodeList.replaceAll("^[" + divchar + "]+|[" + divchar + "]$", "");
            // 検索用複数番号を配列化する
            String[] goodsCodeArray = goodsCodeList.split(divchar);
            if (goodsCodeArray.length > 0) {
                favoriteSearchForBackDaoConditionDto.setGoodsCodeList(Arrays.asList(goodsCodeArray));

                // 商品番号桁数チェック
                for (String goodsCode : goodsCodeArray) {
                    if (goodsCode.length() > 20) {
                        favoriteModel.getMsgCodeList().add("AOX000116E");
                        favoriteModel.getMsgArgMap().put("AOX000116E", new String[] {"商品番号(複数検索用)"});
                        break;
                    }
                }

                // 商品番号数チェック(最大番号数はプロパティから取得)
                if (goodsCodeArray.length > favoriteModel.CONDITION_GOODS_CODE_LIST_LIMIT) {
                    favoriteModel.getMsgCodeList().add("AOX000115E");
                    favoriteModel.getMsgArgMap()
                                 .put(
                                                 "AOX000115E", new String[] {Integer.toString(
                                                                 FavoriteModel.CONDITION_GOODS_CODE_LIST_LIMIT),
                                                                 "商品番号(複数検索用)"});
                }
            }
        }

        return favoriteSearchForBackDaoConditionDto;
    }

    /**
     * 検索結果をページに反映<br/>
     *
     * @param favoriteSearchResultDtoList 検索結果リスト
     * @param conditionDto             検索条件Dto
     * @param favoriteModel               お気に入り商品モデル
     */
    public void toPageForSearch(List<FavoriteSearchResultDto> favoriteSearchResultDtoList,
                                FavoriteSearchForBackDaoConditionDto conditionDto,
                                FavoriteModel favoriteModel) {

        // オフセット + 1をNoにセット
        int index = conditionDto.getOffset() + 1;

        List<FavoriteResultItem> resultItemList = new ArrayList<>();

        for (FavoriteSearchResultDto favoriteSearchResultDto : favoriteSearchResultDtoList) {
            FavoriteResultItem favoriteResultItem = ApplicationContextUtility.getBean(FavoriteResultItem.class);
            favoriteResultItem.setResultNo(index++);
            favoriteResultItem.setResultGoodsSeq(favoriteSearchResultDto.getGoodsSeq());
            favoriteResultItem.setResultMemberInfoSeq(favoriteSearchResultDto.getMemberInfoSeq());
            favoriteResultItem.setGoodsGroupCode(favoriteSearchResultDto.getGoodsGroupCode());
            favoriteResultItem.setResultGoodsCode(favoriteSearchResultDto.getGoodsCode());
            favoriteResultItem.setResultCustomerNo(favoriteSearchResultDto.getCustomerNo());
            favoriteResultItem.setResultSaleStatus(favoriteSearchResultDto.getSaleStatus());
            favoriteResultItem.setResultSaleCd(favoriteSearchResultDto.getSaleCd());
            favoriteResultItem.setResultSaleTo(favoriteSearchResultDto.getSaleTo());

            resultItemList.add(favoriteResultItem);
        }
        favoriteModel.setResultItems(resultItemList);

        // 件数セット
        favoriteModel.setTotalCount(conditionDto.getTotalCount());
    }

    /**
     * チェックされた商品SEQリストを作成<br/>
     *
     * @param favoriteModel 商品検索ページ
     * @return 選択済みの商品SEQリスト
     */
    public List<String> toFavoriteSeqList(FavoriteModel favoriteModel) {

        List<String> favoriteSeqList = new ArrayList<>();

        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        for (Iterator<FavoriteResultItem> it = favoriteModel.getResultItems().iterator(); it.hasNext(); ) {
            FavoriteResultItem favoriteResultItem = it.next();
            if (favoriteResultItem.isResultFavoriteCheck()) {
                favoriteSeqList.add(conversionUtility.toString(
                                conversionUtility.toString(favoriteResultItem.getResultGoodsSeq())
                                + conversionUtility.toString(favoriteResultItem.getResultMemberInfoSeq())));
            }
        }
        return favoriteSeqList;
    }
}
