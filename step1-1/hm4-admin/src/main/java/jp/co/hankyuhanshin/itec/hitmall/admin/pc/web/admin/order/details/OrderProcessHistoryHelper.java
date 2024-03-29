/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.details;

import jp.co.hankyuhanshin.itec.hitmall.entity.order.index.OrderIndexEntity;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 処理履歴ページ用DXO<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class OrderProcessHistoryHelper {

    /**
     * ページアイテムに値を設定<br/>
     *
     * @param page            処理履歴ページ
     * @param indexEntityList 受注インデックスエンティティリスト
     */
    public void toPageItems(OrderProcessHistoryModel orderProcesshistoryModel, List<OrderIndexEntity> indexEntityList) {

        List<OrderProcessHistoryModelItem> itemList = new ArrayList<>();
        Integer orderSeq = 0;
        for (OrderIndexEntity entity : indexEntityList) {
            OrderProcessHistoryModelItem item = ApplicationContextUtility.getBean(OrderProcessHistoryModelItem.class);
            orderSeq = entity.getOrderSeq();
            item.setOrderVersionNo(entity.getOrderVersionNo());
            item.setProcessPersonName(entity.getProcessPersonName());
            item.setProcessTime(entity.getProcessTime());
            item.setProcessType(entity.getProcessType());
            itemList.add(item);
        }
        orderProcesshistoryModel.setOrderSeq(orderSeq);
        orderProcesshistoryModel.setProcesshistoryPageItems(itemList);
    }

}
