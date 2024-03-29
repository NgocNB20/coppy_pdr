// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.addressbook.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.addressbook.AddressBookDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.addressbook.AddressBookEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.addressbook.AddressBookGetEntityByCustomerNoLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * PDR#011 #037 顧客情報・住所情報の取り込み<br/>
 * 住所録取得ロジック(顧客番号)実装クラス<br/>
 *
 * @author s.kume
 */
@Component
public class AddressBookGetEntityByCustomerNoLogicImpl extends AbstractShopLogic
                implements AddressBookGetEntityByCustomerNoLogic {

    /**
     * 住所録Dao
     */
    private final AddressBookDao addressBookDao;

    @Autowired
    public AddressBookGetEntityByCustomerNoLogicImpl(AddressBookDao addressBookDao) {
        this.addressBookDao = addressBookDao;
    }

    /**
     * /**
     * 顧客番号から住所録情報を取得する<br/>
     *
     * @param customerNo 顧客番号
     * @return 住所録エンティティ
     */
    public AddressBookEntity execute(Integer customerNo) {
        // 引数チェック
        ArgumentCheckUtil.assertNotNull("customerNo", customerNo);

        // 顧客番号でエンティティ取得
        return addressBookDao.getEntityByCustomerNo(customerNo);
    }
}
// PDR Migrate Customization to here
