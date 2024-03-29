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
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.addressbook.AddressBookDataCheckLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * アドレス帳データチェックロジック<br/>
 *
 * @author ueshima
 * @version $Revision: 1.4 $
 */
@Component
public class AddressBookDataCheckLogicImpl extends AbstractShopLogic implements AddressBookDataCheckLogic {

    /**
     * アドレス帳Dao
     */
    private final AddressBookDao addressBookDao;

    @Autowired
    public AddressBookDataCheckLogicImpl(AddressBookDao addressBookDao) {
        this.addressBookDao = addressBookDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param addressBookEntity アドレス帳エンティティ
     */
    @Override
    public void execute(AddressBookEntity addressBookEntity) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("addressBookEntity", addressBookEntity);

        // アドレス帳最大登録件数超過チェック
        checkMaxCount(addressBookEntity);

        // アドレス帳名称重複チェック
        checkAddressBookName(addressBookEntity);

        // 例外スロー
        if (hasErrorList()) {
            throwMessage();
        }
    }

    /**
     * アドレス帳最大登録件数超過チェック<br/>
     *
     * @param addressBookEntity アドレス帳エンティティ
     */
    protected void checkMaxCount(AddressBookEntity addressBookEntity) {

        int addressBookCount = addressBookDao.getAddressBookCount(addressBookEntity.getMemberInfoSeq());

        // 新規登録の場合
        if (addressBookEntity.getAddressBookSeq() == null) {
            addressBookCount++;
        }

        // システム値取得
        int addressBookMaxCount = Integer.parseInt(PropertiesUtil.getSystemPropertiesValue("addressbook.max"));
        if (addressBookCount > addressBookMaxCount) {
            addErrorMessage(MSGCD_ADDRESSBOOK_MAX_COUNT_FAIL, new Object[] {addressBookMaxCount});
        }
    }

    /**
     * アドレス帳名称重複チェック<br/>
     *
     * @param addressBookEntity アドレス帳エンティティ
     */
    protected void checkAddressBookName(AddressBookEntity addressBookEntity) {

        // 既存データの検索
        AddressBookEntity addressBook = addressBookDao.getAddressBookByName(addressBookEntity.getMemberInfoSeq(),
                                                                            addressBookEntity.getAddressBookName()
                                                                           );

        // 重複確認
        boolean duplicateflg = false;
        if (addressBook != null) {

            if (addressBookEntity.getAddressBookSeq() == null) {
                // 追加時
                duplicateflg = true;
            } else if (addressBookEntity.getAddressBookSeq().intValue() != addressBook.getAddressBookSeq().intValue()) {
                // 更新時
                duplicateflg = true;
            }
        }
        if (duplicateflg) {
            addErrorMessage(MSGCD_DUPLICATE_ADDRESSBOOK_NAME);
        }
    }

}
