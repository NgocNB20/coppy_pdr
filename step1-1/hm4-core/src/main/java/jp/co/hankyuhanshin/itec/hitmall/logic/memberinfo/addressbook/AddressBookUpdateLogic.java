/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.addressbook;

import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.addressbook.AddressBookEntity;

/**
 * アドレス帳情報更新ロジック<br/>
 *
 * @author ueshima
 * @version $Revision: 1.2 $
 */
public interface AddressBookUpdateLogic {

    /**
     * ロジック実行<br/>
     *
     * @param addressBookEntity アドレス帳エンティティ
     * @return 更新件数
     */
    int execute(AddressBookEntity addressBookEntity);
}
