/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.restock;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailDeliveryStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReStockStatus;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockSearchResultDto;
import jp.co.hankyuhanshin.itec.hmbase.constant.ValidatorConstants;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 入荷お知らせ商品検索Helper
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Component
public class ReStockHelper extends AbstractReStockHelper {

    /**
     * 検索条件の作成<br/>
     *
     * @param reStockModel 商品モデル
     * @return 商品検索条件Dto
     */
    public ReStockSearchForBackDaoConditionDto toReStockSearchForBackDaoConditionDtoForSearch(ReStockModel reStockModel) {

        ReStockSearchForBackDaoConditionDto reStockSearchForBackDaoConditionDto =
                        ApplicationContextUtility.getBean(ReStockSearchForBackDaoConditionDto.class);
        // メッセージコードリスト初期化
        reStockModel.setMsgCodeList(new ArrayList<>());
        reStockModel.setMsgArgMap(new HashMap<>());

        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // 商品グループコード
        reStockSearchForBackDaoConditionDto.setGoodsGroupCode(reStockModel.getSearchGoodsGroupCode());

        // 商品コード
        reStockSearchForBackDaoConditionDto.setGoodsCode(reStockModel.getSearchGoodsCode());

        // 商品名
        reStockSearchForBackDaoConditionDto.setGoodsName(reStockModel.getSearchGoodsName());

        // 顧客番号
        reStockSearchForBackDaoConditionDto.setCustomerNo(conversionUtility.toInteger(reStockModel.getSearchCustomerNo()));

        // 配信ID
        reStockSearchForBackDaoConditionDto.setDeliveryId(reStockModel.getSearchDeliveryId());

        // 入荷状態
        if (reStockModel.getReStockStatus() != null && reStockModel.getReStockStatus().length > 0) {
            reStockSearchForBackDaoConditionDto.setReStockStatusList(
                            Arrays.asList(reStockModel.getReStockStatus()));
        } else {
            reStockSearchForBackDaoConditionDto.setReStockStatusList(
                            Arrays.asList(HTypeReStockStatus.NO_RESTOCK.getValue(),
                                    HTypeReStockStatus.RESTOCK.getValue()
                                         ));
        }


        // 入荷お知らせメール送信状況
        if (reStockModel.getDeliveryStatus() != null && reStockModel.getDeliveryStatus().length > 0) {
            reStockSearchForBackDaoConditionDto.setDeliveryStatusList(Arrays.asList(reStockModel.getDeliveryStatus()));
        } else {
            reStockSearchForBackDaoConditionDto.setDeliveryStatusList(
                            Arrays.asList(HTypeMailDeliveryStatus.UNDELIVERED.getValue(),
                                    HTypeMailDeliveryStatus.DELIVERING.getValue(),
                                    HTypeMailDeliveryStatus.DELIVERED.getValue(),
                                    HTypeMailDeliveryStatus.FAILED.getValue(),
                                    HTypeMailDeliveryStatus.EXCLUSION.getValue()
                                         ));
        }

        // 入荷日時
        reStockSearchForBackDaoConditionDto.setReStockTimeFrom(
                conversionUtility.toTimeStamp(reStockModel.getReStockTimeFrom()));
        if (reStockModel.getReStockTimeTo() != null) {
            reStockSearchForBackDaoConditionDto.setReStockTimeTo(dateUtility.getEndOfDate(
                    conversionUtility.toTimeStamp(reStockModel.getReStockTimeTo())));
        }
        
        // 商品番号番号（複数番号検索用）
        if (reStockModel.getConditionGoodsCodeList() != null && StringUtil.isNotEmpty(
                reStockModel.getConditionGoodsCodeList().replaceAll("[\\s|　]", ""))) {
            // 検索に有効な文字列が存在する場合
            // 選択肢区切り文字を設定ファイルから取得
            String divchar = PropertiesUtil.getSystemPropertiesValue("order.search.order.code.list.divchar");
            String goodsCodeList = reStockModel.getConditionGoodsCodeList();
            // 空白を削除する
            goodsCodeList = goodsCodeList.replaceAll("[ 　\t\\x0B\f]", "");
            // 2つ以上連続した改行コードを1つにまとめる
            goodsCodeList = goodsCodeList.replaceAll("(" + divchar + "){2,}", "\n");
            // 先頭または最後尾の改行コードを削除する
            goodsCodeList = goodsCodeList.replaceAll("^[" + divchar + "]+|[" + divchar + "]$", "");
            // 検索用複数番号を配列化する
            String[] goodsCodeArray = goodsCodeList.split(divchar);
            if (goodsCodeArray.length > 0) {
                reStockSearchForBackDaoConditionDto.setMultiCodeList(Arrays.asList(goodsCodeArray));

                // 商品番号桁数チェック
                for (String goodsCode : goodsCodeArray) {
                    if (goodsCode.length() > ValidatorConstants.LENGTH_GOODS_CODE_MAXIMUM) {
                        reStockModel.getMsgCodeList().add("AOX000116E");
                        reStockModel.getMsgArgMap().put("AOX000116E", new String[] {"商品番号(複数検索用)"});
                        break;
                    }
                }

                // 商品番号数チェック(最大番号数はプロパティから取得)
                if (goodsCodeArray.length > ReStockModel.CONDITION_GOODS_CODE_LIST_LIMIT) {
                    reStockModel.getMsgCodeList().add("AOX000115E");
                    reStockModel.getMsgArgMap()
                            .put("AOX000115E", new String[] {Integer.toString(
                                    ReStockModel.CONDITION_GOODS_CODE_LIST_LIMIT), "商品番号(複数検索用)"});
                }
            }
        }

        return reStockSearchForBackDaoConditionDto;
    }

    /**
     * 検索結果をページに反映<br/>
     *
     * @param reStockSearchResultDtoList 検索結果リスト
     * @param conditionDto             検索条件Dto
     * @param reStockModel               商品モデル
     */
    public void toPageForSearch(List<ReStockSearchResultDto> reStockSearchResultDtoList,
                                ReStockSearchForBackDaoConditionDto conditionDto,
                                ReStockModel reStockModel) {

        // オフセット + 1をNoにセット
        int index = conditionDto.getOffset() + 1;

        List<ReStockResultItem> resultItemList = new ArrayList<>();
        for (ReStockSearchResultDto reStockSearchResultDto : reStockSearchResultDtoList) {
            ReStockResultItem reStockResultItem = ApplicationContextUtility.getBean(ReStockResultItem.class);
            reStockResultItem.setResultKey(reStockSearchResultDto.getKey());
            reStockResultItem.setResultNo(index++);
            reStockResultItem.setResultGoodsSeq(reStockSearchResultDto.getGoodsSeq());
            reStockResultItem.setResultGoodsGroupCode(reStockSearchResultDto.getGoodsGroupCode());
            reStockResultItem.setResultGoodsCode(reStockSearchResultDto.getGoodsCode());
            reStockResultItem.setResultRegistMemberCount(reStockSearchResultDto.getRegistMemberCount());
            reStockResultItem.setResultReStockStatus(reStockSearchResultDto.getReStockStatus().getValue());
            reStockResultItem.setResultDeliveryId(reStockSearchResultDto.getDeliveryId());
            reStockResultItem.setResultDeliveryStatus(reStockSearchResultDto.getDeliveryStatus().getValue());
            reStockResultItem.setResultReStockTime(reStockSearchResultDto.getReStockTime());

            resultItemList.add(reStockResultItem);
        }
        reStockModel.setResultItems(resultItemList);

        // 件数セット
        reStockModel.setTotalCount(conditionDto.getTotalCount());
    }

    /**
     * チェックされた入荷お知らせ商品リストを作成<br/>
     *
     * @param reStockModel 入荷お知らせ商品検索ページ
     * @return 選択済みの商品リスト
     */
    public List<String> toKeyList(ReStockModel reStockModel) {

        List<String> keyList = new ArrayList<>();

        for (ReStockResultItem reStockResultItem : reStockModel.getResultItems()) {
            if (reStockResultItem.isResultReStockCheck()) {
                keyList.add(reStockResultItem.getResultKey());
            }
        }
        return keyList;
    }

    /**
     * チェックされた入荷お知らせメール送信リストを作成<br/>
     *
     * @param reStockModel 入荷お知らせ商品検索ページ
     * @return 選択済みの商品リスト
     */
    public List<ReStockResultItem> toSendMailList(ReStockModel reStockModel) {

        List<ReStockResultItem> tmpReStockResultItem = new ArrayList<>();

        for (ReStockResultItem reStockResultItem : reStockModel.getResultItems()) {
            if (reStockResultItem.isResultReStockCheck()) {
                // チェックされた商品に紐づく配信リストを取得する。
                tmpReStockResultItem.add(reStockResultItem);
            }
        }
        return tmpReStockResultItem;
    }
}
