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
import jp.co.hankyuhanshin.itec.hitmall.logic.salesadvisability.GetSeqListByLoginMemberInfoLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * PDR#437 【1.7次】基幹リニューアル対応　【要望No.24】　販売可否条件の変更
 *
 * <pre>
 * 販売可否SEQ取得ロジック
 * </pre>
 *
 * @author k.uehara
 */
@Component
public class GetSeqListByLoginMemberInfoLogicImpl extends AbstractShopLogic
                implements GetSeqListByLoginMemberInfoLogic {

    /** 販売可否判定 */
    private final SalesAdvisabilityDao salesAdvisabilityDao;

    /** エラーメッセージ：販売可否判定が取得できなかった場合エラー */
    public static final String MSGCD_NOT_GET_SALESADVISABILITY = "PDR-0437-021-L-E";

    @Autowired
    public GetSeqListByLoginMemberInfoLogicImpl(SalesAdvisabilityDao salesAdvisabilityDao) {
        this.salesAdvisabilityDao = salesAdvisabilityDao;
    }

    /**
     * 販売可否SEQリストを取得
     *
     * @param businessType     お届け先顧客区分
     * @param confDocumentType 確認書類
     * @return 販売可否SEQリスト
     */
    public List<Integer> execute(String businessType, String confDocumentType) {
        List<Integer> salesAdvisabilitySeqList =
                        salesAdvisabilityDao.getSeqListByLoginMemberInfo(businessType, confDocumentType);

        if (salesAdvisabilitySeqList == null || salesAdvisabilitySeqList.size() == 0
            || salesAdvisabilitySeqList.isEmpty()) {
            clearErrorList();
            throwMessage(MSGCD_NOT_GET_SALESADVISABILITY);
        }

        return salesAdvisabilitySeqList;

    }
}
// PDR Migrate Customization to here
