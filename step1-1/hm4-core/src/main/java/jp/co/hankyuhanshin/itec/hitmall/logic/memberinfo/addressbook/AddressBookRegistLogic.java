/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.addressbook;

import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.addressbook.AddressBookEntity;

/**
 * アドレス帳情報登録ロジック<br/>
 *
 * @author ueshima
 * @version $Revision: 1.2 $
 */
public interface AddressBookRegistLogic {

    /**
     * ロジック実行<br/>
     *
     * @param addressBookEntity アドレス帳エンティティ
     * @return 登録件数
     */
    int execute(AddressBookEntity addressBookEntity);
}
