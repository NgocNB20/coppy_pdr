/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.restock;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockDetailsResultDto;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 入荷お知らせ商品詳細Helper
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Component
public class ReStockDetailsHelper extends AbstractReStockHelper {

    /**
     * 結果をページに反映<br/>
     *
     * @param reStockSearchResultDtoList 検索結果リスト
     * @param reStockDetailsModel               商品モデル
     */
    public void toPageForDetails(List<ReStockDetailsResultDto> reStockSearchResultDtoList,
                                ReStockDetailsModel reStockDetailsModel) {

        // 基本情報をセット（データの一件目を利用）
        reStockDetailsModel.setGoodsSeq(reStockSearchResultDtoList.get(0).getGoodsSeq());
        reStockDetailsModel.setGoodsCode(reStockSearchResultDtoList.get(0).getGoodsCode());
        reStockDetailsModel.setGoodsName(reStockSearchResultDtoList.get(0).getGoodsName());
        reStockDetailsModel.setReStockStatus(reStockSearchResultDtoList.get(0).getReStockStatus());
        reStockDetailsModel.setDeliveryId(reStockSearchResultDtoList.get(0).getDeliveryId());
        reStockDetailsModel.setReStockTime(reStockSearchResultDtoList.get(0).getReStockTime());

        //  1をNoにセット
        int index = 1;

        // 顧客情報をセット
        List<ReStockDetailsResultItem> resultItemList = new ArrayList<>();
        for (ReStockDetailsResultDto reStockSearchResultDto : reStockSearchResultDtoList) {
            ReStockDetailsResultItem reStockDetailsResultItem = ApplicationContextUtility.getBean(ReStockDetailsResultItem.class);
            reStockDetailsResultItem.setResultKey(reStockSearchResultDto.getDetailsKey());
            reStockDetailsResultItem.setResultNo(index++);
            reStockDetailsResultItem.setMemberInfoSeq(reStockSearchResultDto.getMemberInfoSeq());
            reStockDetailsResultItem.setCustomerNo(reStockSearchResultDto.getCustomerNo());
            reStockDetailsResultItem.setMemberInfoLastName(reStockSearchResultDto.getMemberInfoLastName());
            reStockDetailsResultItem.setMemberInfoId(reStockSearchResultDto.getMemberInfoId());
            reStockDetailsResultItem.setRegistTime(reStockSearchResultDto.getRegistTime());
            reStockDetailsResultItem.setDeliveryStatus(reStockSearchResultDto.getDeliveryStatus().getValue());
            reStockDetailsResultItem.setDeliveryStatus(reStockSearchResultDto.getDeliveryStatus().getValue());
            reStockDetailsResultItem.setVersionNo(reStockSearchResultDto.getVersionNo());

            resultItemList.add(reStockDetailsResultItem);
        }
        reStockDetailsModel.setResultItems(resultItemList);

    }

    /**
     * チェックされた入荷お知らせ商品リストを作成<br/>
     *
     * @param reStockDetailsModel 入荷お知らせ商品検索ページ
     * @return 選択済みの商品リスト
     */
    public List<String> toKeyList(ReStockDetailsModel reStockDetailsModel) {

        List<String> keyList = new ArrayList<>();

        for (ReStockDetailsResultItem reStockDetailsResultItem : reStockDetailsModel.getResultItems()) {
            if (reStockDetailsResultItem.isResultReStockCheck()) {
                keyList.add(reStockDetailsResultItem.getResultKey());
            }
        }
        return keyList;
    }

    /**
     * チェックされた入荷お知らせメール送信リストを作成<br/>
     *
     * @param reStockDetailsModel 入荷お知らせ商品検索ページ
     * @return 選択済みの商品リスト
     */
    public List<ReStockDetailsResultItem> toSendMailList(ReStockDetailsModel reStockDetailsModel) {

        List<ReStockDetailsResultItem> tmpReStockDetailsResultItem = new ArrayList<>();

        for (ReStockDetailsResultItem reStockDetailsResultItem : reStockDetailsModel.getResultItems()) {
            if (reStockDetailsResultItem.isResultReStockCheck()) {
                // チェックされた商品に紐づく配信リストを取得する。
                tmpReStockDetailsResultItem.add(reStockDetailsResultItem);
            }
        }
        return tmpReStockDetailsResultItem;
    }
}
