/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.addressbook.impl;

import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.addressbook.AddressBookEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.addressbook.AddressBookGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.addressbook.AddressBookGetService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * アドレス帳情報取得サービス
 *
 * @author ueshima
 * @version $Revision: 1.3 $
 */
@Service
public class AddressBookGetServiceImpl extends AbstractShopService implements AddressBookGetService {

    /**
     * アドレス帳情報取得ロジック
     */
    private final AddressBookGetLogic addressBookGetLogic;

    @Autowired
    public AddressBookGetServiceImpl(AddressBookGetLogic addressBookGetLogic) {
        this.addressBookGetLogic = addressBookGetLogic;
    }

    /**
     * サービス実行
     *
     * @param memberInfoSeq  会員SEQ
     * @param addressBookSeq アドレス帳SEQ
     * @return アドレス帳エンティティ
     */
    @Override
    public AddressBookEntity execute(Integer memberInfoSeq, Integer addressBookSeq) {

        // 入力パラメタの確認
        ArgumentCheckUtil.assertNotNull("memberInfoSeq", memberInfoSeq);
        ArgumentCheckUtil.assertNotNull("addressBookSeq", addressBookSeq);

        // ロジックの実行
        AddressBookEntity addressBookEntity = addressBookGetLogic.execute(memberInfoSeq, addressBookSeq);
        if (addressBookEntity == null) {
            throwMessage(MSGCD_ADDRESSBOOKENTITYDTO_NULL);
        }
        return addressBookEntity;
    }
}
