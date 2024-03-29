/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.inquiry.inquirygroup;

import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryGroupEntity;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InquiryGroupHelper {

    /**
     * 検索結果をページに反映<br/>
     *
     * @param inquiryGroupEntityList 検索結果リスト
     * @param indexPage              問い合わせ分類一覧画面
     */
    public void toPageForLoad(List<InquiryGroupEntity> inquiryGroupEntityList, InquiryGroupModel indexPage) {

        List<InquiryGroupPageItem> pageItemList = new ArrayList<>();
        // 表示順はリストindex+1を表示順とする
        int orderDisplay = 1;
        for (InquiryGroupEntity inquiryGroupEntity : inquiryGroupEntityList) {
            InquiryGroupPageItem item = ApplicationContextUtility.getBean(InquiryGroupPageItem.class);
            item.setInquiryGroupSeq(inquiryGroupEntity.getInquiryGroupSeq());
            item.setInqueryGroupName(inquiryGroupEntity.getInquiryGroupName());
            item.setOpenStatus(inquiryGroupEntity.getOpenStatus());
            item.setShopSeq(inquiryGroupEntity.getShopSeq());

            item.setOrderDisplayRadio(orderDisplay++);

            pageItemList.add(item);
        }

        indexPage.setResultItems(pageItemList);
    }

    /**
     * 表示順更新用に、画面情報から更新用問い合わせ分類エンティティを作成
     *
     * @param indexPage 問い合わせ分類一覧表示
     * @return 更新用問い合わせ分類エンティティリスト
     */
    public List<InquiryGroupEntity> toInquiryGroupEntityListForOrderDisplayUpdate(InquiryGroupModel indexPage) {

        List<InquiryGroupPageItem> resultList = indexPage.getResultItems();
        List<InquiryGroupEntity> updateList = new ArrayList<>();

        for (InquiryGroupPageItem indexPageItem : resultList) {
            InquiryGroupEntity entity = ApplicationContextUtility.getBean(InquiryGroupEntity.class);
            entity.setInquiryGroupSeq(indexPageItem.getInquiryGroupSeq());
            entity.setShopSeq(indexPageItem.getShopSeq());
            entity.setInquiryGroupName(indexPageItem.getInqueryGroupName());
            entity.setOpenStatus(indexPageItem.getOpenStatus());
            entity.setOrderDisplay(indexPageItem.getOrderDisplayRadio());

            updateList.add(entity);
        }

        return updateList;
    }

    /**
     * 表示順を変更する(セッション情報)
     * ただし変更後の表示順が範囲外の場合は処理なし
     *
     * @param index     変更前の表示順(実際のindex)
     * @param addIndex  変更後の表示順
     * @param indexPage ページ
     */
    public void toPageForChangeOrderDisplay(int index, int addIndex, InquiryGroupModel indexPage) {
        List<InquiryGroupPageItem> inquiryGroupList = indexPage.getResultItems();

        // 変更可能な範囲であれば、表示順変更
        if (0 <= addIndex && addIndex <= inquiryGroupList.size() - 1) {
            InquiryGroupPageItem item = inquiryGroupList.remove(index);
            inquiryGroupList.add(addIndex, item);
            Integer selectedInquiryGroupSeq = item.getInquiryGroupSeq();

            // 表示順を再設定
            int orderDisplay = 1;
            for (InquiryGroupPageItem indexPageItem : inquiryGroupList) {
                indexPageItem.setOrderDisplayRadio(orderDisplay);

                if (selectedInquiryGroupSeq.equals(indexPageItem.getInquiryGroupSeq())) {
                    // 選択値保持
                    indexPage.setOrderDisplay(orderDisplay);
                }

                orderDisplay++;
            }
        }
    }
}
