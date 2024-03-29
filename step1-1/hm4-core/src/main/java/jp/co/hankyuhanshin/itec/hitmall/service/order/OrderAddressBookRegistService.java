/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order;

import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.addressbook.AddressBookEntity;

/**
 * 注文時、アドレス帳登録サービス
 *
 * @author negishi
 * @version $Revision: 1.1 $
 */
public interface OrderAddressBookRegistService {

    /**
     * 注文時登録用のアドレス帳の名前をプロパティファイルから取得するためのキー
     */
    public static final String ADDRESS_BOOK_NAME = "order.regist.addressbook.name";

    /**
     * 実行メソッド
     *
     * @param addressBookEntity アドレス帳エンティティ
     * @return 登録件数
     */
    int execute(AddressBookEntity addressBookEntity, Integer memberInfoSeq);

}
