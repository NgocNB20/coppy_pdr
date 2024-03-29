/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.addressbook;

import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.addressbook.AddressBookEntity;

/**
 * アドレス帳情報更新サービス<br/>
 *
 * @author ueshima
 * @version $Revision: 1.3 $
 */
public interface AddressBookUpdateService {

    /**
     * アドレス帳情報更新失敗<br/>
     * <code>MSGCD_ADDRESSBOOK_UPDATE_FAIL</code><br/>
     */
    public static final String MSGCD_ADDRESSBOOK_UPDATE_FAIL = "SMA000401";

    /**
     * サービス実行<br/>
     *
     * @param addressBookEntity アドレス帳エンティティ
     * @return 更新件数
     */
    int execute(AddressBookEntity addressBookEntity);
}
