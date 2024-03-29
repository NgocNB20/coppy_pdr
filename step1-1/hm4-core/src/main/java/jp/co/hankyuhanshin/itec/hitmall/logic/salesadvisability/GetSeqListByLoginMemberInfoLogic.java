// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.salesadvisability;

import java.util.List;

/**
 * PDR#437 【1.7次】基幹リニューアル対応　【要望No.24】　販売可否条件の変更<br/>
 *
 * <pre>
 * 販売可否SEQリスト取得ロジック
 * </pre>
 *
 * @author k.uehara
 */
public interface GetSeqListByLoginMemberInfoLogic {

    /**
     * 販売可否SEQリストを取得 <br/>
     *
     * @param commonInfo 共通情報
     * @return 販売可否SEQリスト
     */
    List<Integer> execute(String businessType, String confDocumentType);

}
// PDR Migrate Customization to here
