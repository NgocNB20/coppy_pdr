/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.addressbook;

import java.util.List;

/**
 * アドレス帳情報リスト削除ロジック<br/>
 *
 * @author ueshima
 * @version $Revision: 1.2 $
 */
public interface AddressBookListDeleteLogic {

    /**
     * アドレス帳情報削除処理<br/>
     *
     * @param memberInfoSeq      会員SEQ
     * @param addressBookSeqList アドレス帳SEQリスト
     * @return 削除件数
     */
    int execute(Integer memberInfoSeq, List<Integer> addressBookSeqList);

    /**
     * アドレス帳情報削除処理<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @return 削除件数
     */
    int execute(Integer memberInfoSeq);
}
