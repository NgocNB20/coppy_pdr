/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo;

import java.util.List;

/**
 * メール不要の会員情報取得ロジック<br/>
 *
 * @author st75001
 * @version $Revision: 1.2 $
 */
public interface NoMailRequiredMemberInfoLogic {

    /**
     * メール不要の会員情報を取得する<br/>
     *
     * @param customerNoList 顧客番号リスト
     * @return 登録件数
     */
    List<Integer> getNoMailRequiredMemberInfoLogic(List<Integer> customerNoList);

    /**
     * メール不要の会員情報を取得する(会員SEQ)<br/>
     *
     * @param memberInfoSeqList 会員SEQリスト
     * @return 登録件数
     */
    List<Integer> getNoMailRequiredMemberInfoSeqLogic(List<Integer> memberInfoSeqList);
}
