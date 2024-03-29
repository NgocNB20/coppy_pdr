/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.addressbook;

import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.addressbook.AddressBookEntity;

/**
 * アドレス帳データチェックロジック<br/>
 *
 * @author ueshima
 * @version $Revision: 1.2 $
 */
public interface AddressBookDataCheckLogic {

    /**
     * アドレス帳最大登録件数超過エラー<br/>
     * <code>MSGCD_ADDRESSBOOK_MAX_COUNT_FAIL</code>
     */
    public static final String MSGCD_ADDRESSBOOK_MAX_COUNT_FAIL = "LMA000601";

    /**
     * アドレス帳名称の重複エラー<br/>
     * <code>MSGCD_DUPLICATE_ADDRESSBOOK_NAME</code>
     */
    public static final String MSGCD_DUPLICATE_ADDRESSBOOK_NAME = "LMA000602";

    /**
     * ロジック実行<br/>
     *
     * @param addressBookEntity アドレス帳エンティティ
     */
    void execute(AddressBookEntity addressBookEntity);
}
