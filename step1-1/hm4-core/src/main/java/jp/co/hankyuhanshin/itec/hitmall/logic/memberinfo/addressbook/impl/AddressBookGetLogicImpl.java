/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.addressbook.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.addressbook.AddressBookDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.addressbook.AddressBookEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.addressbook.AddressBookGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * アドレス帳情報取得ロジック<br/>
 *
 * @author ueshima
 * @version $Revision: 1.4 $
 */
@Component
public class AddressBookGetLogicImpl extends AbstractShopLogic implements AddressBookGetLogic {

    /**
     * アドレス帳Dao
     */
    private final AddressBookDao addressBookDao;

    @Autowired
    public AddressBookGetLogicImpl(AddressBookDao addressBookDao) {
        this.addressBookDao = addressBookDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param memberInfoSeq  会員SEQ
     * @param addressBookSeq アドレス帳SEQ
     * @return アドレス帳エンティティ
     */
    @Override
    public AddressBookEntity execute(Integer memberInfoSeq, Integer addressBookSeq) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("memberInfoSeq", memberInfoSeq);
        ArgumentCheckUtil.assertNotNull("addressBookSeq", addressBookSeq);

        // ロジックの実行
        return addressBookDao.getEntity(addressBookSeq, memberInfoSeq);
    }

}
