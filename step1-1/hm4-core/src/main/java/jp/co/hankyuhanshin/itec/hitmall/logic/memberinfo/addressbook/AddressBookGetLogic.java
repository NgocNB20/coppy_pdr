/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.addressbook;

import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.addressbook.AddressBookEntity;

/**
 * アドレス帳情報取得ロジック<br/>
 *
 * @author ueshima
 * @version $$
 */
public interface AddressBookGetLogic {

    /**
     * ロジック実行<br/>
     *
     * @param memberInfoSeq  会員SEQ
     * @param addressBookSeq アドレス帳SEQ
     * @return アドレス帳エンティティ
     */
    AddressBookEntity execute(Integer memberInfoSeq, Integer addressBookSeq);
}
