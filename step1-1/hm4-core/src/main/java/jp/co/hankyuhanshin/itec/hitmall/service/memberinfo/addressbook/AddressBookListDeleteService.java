/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.addressbook;

import java.util.List;

/**
 * アドレス帳情報リスト削除サービス<br/>
 *
 * @author ueshima
 * @version $Revision: 1.3 $
 */
public interface AddressBookListDeleteService {

    /**
     * サービス実行<br/>
     *
     * @param memberInfoSeq      会員SEQ
     * @param addressBookSeqList アドレス帳SEQリスト
     * @return 削除件数
     */
    int execute(Integer memberInfoSeq, List<Integer> addressBookSeqList);

    /**
     * サービス実行<br/>
     *
     * @param addressBookSeq 会員SEQ
     * @return 削除件数
     */
    int execute(Integer addressBookSeq, Integer memberInfoSeq);

}
