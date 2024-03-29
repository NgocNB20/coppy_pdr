// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.salesadvisability.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.salesadvisability.SalesAdvisabilityDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.salesadvisability.CheckConfDocumentLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * PDR#437 【1.7次】基幹リニューアル対応　【要望No.24】　販売可否条件の変更<br/>
 *
 * <pre>
 * 確認書類設定可否判定ロジック
 * </pre>
 *
 * @author k.uehara
 */
@Component
public class CheckConfDocumentLogicImpl extends AbstractShopLogic implements CheckConfDocumentLogic {

    /** 販売可否判定Dao */
    private final SalesAdvisabilityDao salesAdvisabilityDao;

    @Autowired
    public CheckConfDocumentLogicImpl(SalesAdvisabilityDao salesAdvisabilityDao) {
        this.salesAdvisabilityDao = salesAdvisabilityDao;
    }

    /**
     * 確認書類が設定可能なものであるかチェックする。 <br/>
     *
     * @param businessType     顧客区分
     * @param confDocumentType 確認書類
     * @return 設定不可の場合false
     */
    public boolean execute(String businessType, String confDocumentType) {

        // 顧客区分と確認書類で販売可否判定マスタからデータを取得出来ない場合エラー
        if (salesAdvisabilityDao.getCountSettableConfDocument(businessType, confDocumentType) == 0) {
            return false;
        }
        return true;

    }
}
// PDR Migrate Customization to here
