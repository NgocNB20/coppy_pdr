/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeProcessType;

/**
 * 受注サマリ更新受注インデックス登録Logicインターフェース
 *
 * @author $author$
 * @version $Revision: 1.1 $
 */
public interface OrderIndexInsertAndOrderSummaryUpdateLogic {
    /**
     * 受注サマリを更新し受注インデックスへ登録処理を行います
     *
     * @param orderSeq          Integer
     * @param shopSeq           Integer
     * @param processPersonName String
     * @param processType       HTypeProcessType
     * @return boolean
     */
    boolean execute(Integer orderSeq, Integer shopSeq, String processPersonName, HTypeProcessType processType);
}
