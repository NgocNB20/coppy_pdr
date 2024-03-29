/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.addressbook;

import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.addressbook.AddressBookEntity;

/**
 * アドレス帳情報取得サービス<br/>
 *
 * @author ueshima
 * @version $Revision: 1.3 $
 */
public interface AddressBookGetService {

    /**
     * アドレス帳情報取得失敗<br/>
     * <code>MSGCD_ADDRESSBOOKENTITYDTO_NULL</code>
     */
    public static final String MSGCD_ADDRESSBOOKENTITYDTO_NULL = "SMA000301";

    /**
     * サービス実行<br/>
     *
     * @param memberInfoSeq  会員SEQ
     * @param addressBookSeq アドレス帳SEQ
     * @return アドレス帳エンティティ
     */
    AddressBookEntity execute(Integer memberInfoSeq, Integer addressBookSeq);

}
