// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.loginadvisability;

import java.util.List;

/**
 *
 * <pre>
 * ログイン不可の会員情報取得ロジック
 *
 * @author st75001
 * @version $Revision:$
 */

public interface LoginAdvisabilityGetCanNotLoginMemberLogic {

    /**
     * ログイン不可の会員情報を取得する<br />
     *
     * @param customerNoList 顧客番号リスト
     * @return 顧客番号リスト
     */
    List<Integer> getLoginAdvisabilityGetCanNotLoginMemberLogic(List<Integer> customerNoList);

    /**
     * ログイン不可の会員情報を取得する（会員SEQ）<br />
     *
     * @param memberInfoSeqList 会員SEQリスト
     * @return 会員SEQリスト
     */
    public List<Integer> getLoginAdvisabilityGetCanNotLoginMemberInfoSeqLogic(List<Integer> memberInfoSeqList);

}
// PDR Migrate Customization to here
