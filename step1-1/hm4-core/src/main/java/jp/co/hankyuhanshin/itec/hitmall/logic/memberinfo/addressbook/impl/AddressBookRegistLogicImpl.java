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
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.addressbook.AddressBookRegistLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * アドレス帳情報登録ロジック<br/>
 *
 * @author ueshima
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class AddressBookRegistLogicImpl extends AbstractShopLogic implements AddressBookRegistLogic {

    /**
     * アドレス帳Dao
     */
    private final AddressBookDao addressBookDao;

    @Autowired
    public AddressBookRegistLogicImpl(AddressBookDao addressBookDao) {
        this.addressBookDao = addressBookDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param addressBookEntity アドレス帳エンティティ
     * @return 登録件数
     */
    @Override
    public int execute(AddressBookEntity addressBookEntity) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("addressBookEntity", addressBookEntity);

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // 登録・更新日時の設定
        Timestamp currentTime = dateUtility.getCurrentTime();
        addressBookEntity.setRegistTime(currentTime);
        addressBookEntity.setUpdateTime(currentTime);

        // アドレス帳の登録
        return addressBookDao.insert(addressBookEntity);
    }

}
