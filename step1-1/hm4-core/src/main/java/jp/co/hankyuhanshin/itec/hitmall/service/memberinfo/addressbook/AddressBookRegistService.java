/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.addressbook;

import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.addressbook.AddressBookEntity;

/**
 * アドレス帳情報登録サービス<br/>
 *
 * @author ueshima
 * @version $Revision: 1.3 $
 */
public interface AddressBookRegistService {

    /**
     * アドレス帳情報登録失敗<br/>
     * <code>MSGCD_ADDRESSBOOK_INSERT_FAIL</code>
     */
    public static final String MSGCD_ADDRESSBOOK_INSERT_FAIL = "SMA000201";

    /**
     * アドレス帳情報登録処理<br/>
     *
     * @param addressBookEntity アドレス帳エンティティ
     * @return 登録件数
     */
    int execute(AddressBookEntity addressBookEntity);
}
