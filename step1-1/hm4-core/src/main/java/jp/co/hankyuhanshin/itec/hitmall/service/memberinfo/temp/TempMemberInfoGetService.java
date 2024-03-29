/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.temp;

import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.TempMemberInfoDto;

/**
 * 仮会員情報取得サービス<br/>
 *
 * @author natume
 */
public interface TempMemberInfoGetService {

    /**
     * 有効な確認メール取得エラー<br/>
     * <code>MSGCD_CONFIRMMAILENTITYDTO_NULL</code>
     */
    String MSGCD_CONFIRMMAILENTITYDTO_NULL = "SMT000201";

    /**
     * 受注サマリ情報取得エラー<br/>
     * <code>MSGCD_ORDERSUMMARYENTITYDTO_NULL</code>
     */
    String MSGCD_ORDERSUMMARYENTITYDTO_NULL = "SMT000202";

    /**
     * 受注インデックス情報取得エラー<br/>
     * <code>MSGCD_ORDERINDEXENTITYDTO_NULL</code>
     */
    String MSGCD_ORDERINDEXENTITYDTO_NULL = "SMT000203";

    /**
     * ご注文主情報取得エラー<br/>
     * <code>MSGCD_ORDERPERSONENTITYDTO_NULL</code>
     */
    String MSGCD_ORDERPERSONENTITYDTO_NULL = "SMT000204";

    /**
     * 仮会員情報を取得する<br/>
     *
     * @param password パスワード
     * @return 会員エンティティ
     */
    TempMemberInfoDto execute(String password);
}
