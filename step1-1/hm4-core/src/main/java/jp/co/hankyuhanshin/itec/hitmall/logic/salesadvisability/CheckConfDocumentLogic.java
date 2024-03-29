// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.salesadvisability;

/**
 * PDR#437 【1.7次】基幹リニューアル対応　【要望No.24】　販売可否条件の変更<br/>
 *
 * <pre>
 * 確認書類設定可否判定ロジック
 * </pre>
 *
 * @author k.uehara
 */
public interface CheckConfDocumentLogic {

    /**
     * 確認書類が設定可能なものであるかチェックする。 <br/>
     *
     * @param businessType     顧客区分
     * @param confDocumentType 確認書類
     * @return 設定不可の場合false
     */
    boolean execute(String businessType, String confDocumentType);
}
// PDR Migrate Customization to here
