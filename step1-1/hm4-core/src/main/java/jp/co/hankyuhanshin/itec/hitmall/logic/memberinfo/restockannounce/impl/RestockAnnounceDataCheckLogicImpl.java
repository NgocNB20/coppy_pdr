// 2023-renew No65 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.restockannounce.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.restockannounce.RestockAnnounceDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.restockannounce.RestockAnnounceEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.restockannounce.RestockAnnounceDataCheckLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 入荷お知らせデータチェックロジック<br/>
 *
 * @author Thang Doan (VJP)
 */
@Component
public class RestockAnnounceDataCheckLogicImpl extends AbstractShopLogic implements RestockAnnounceDataCheckLogic {

    /**
     * 入荷お知らDAO
     */
    private final RestockAnnounceDao restockAnnounceDao;

    @Autowired
    public RestockAnnounceDataCheckLogicImpl(RestockAnnounceDao restockAnnounceDao) {
        this.restockAnnounceDao = restockAnnounceDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param restockAnnounceEntity 入荷お知らエンティティ
     */
    @Override
    public void execute(RestockAnnounceEntity restockAnnounceEntity) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("restockAnnounceEntity", restockAnnounceEntity);

        // 入荷お知らせ商品の重複確認
        List<Integer> goodsSeqList = restockAnnounceDao.getGoodsSeqList(restockAnnounceEntity.getMemberInfoSeq());

        // 入荷お知らせ登録されていない場合
        if (!goodsSeqList.contains(restockAnnounceEntity.getGoodsSeq())) {
            goodsSeqList.add(restockAnnounceEntity.getGoodsSeq());
        }

        // 入荷お知らせ最大登録数超過チェック
        int notificationGoodsMax =
                        Integer.parseInt(PropertiesUtil.getSystemPropertiesValue("restockannounce.goods.max"));
        if (goodsSeqList.size() > notificationGoodsMax) {
            addErrorMessage(MSGCD_RESTOCK_ANNOUNCE_GOODS_MAX_COUNT_FAIL, new Object[] {notificationGoodsMax});
        }

        // 例外出力
        if (hasErrorList()) {
            throwMessage();
        }
    }
}
// 2023-renew No65 to here
