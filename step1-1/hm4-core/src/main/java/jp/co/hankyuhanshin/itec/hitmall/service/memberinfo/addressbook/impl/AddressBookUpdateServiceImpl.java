/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.addressbook.impl;

import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.addressbook.AddressBookEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.addressbook.AddressBookDataCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.addressbook.AddressBookUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.addressbook.AddressBookUpdateService;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.AssertionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * アドレス帳情報更新サービス<br/>
 *
 * @author ueshima
 * @version $Revision: 1.3 $
 */
@Service
public class AddressBookUpdateServiceImpl extends AbstractShopService implements AddressBookUpdateService {

    /**
     * アドレス帳データチェックロジック
     */
    private final AddressBookDataCheckLogic addressBookDataCheckLogic;

    /**
     * アドレス帳情報更新ロジック
     */
    private final AddressBookUpdateLogic addressBookUpdateLogic;

    @Autowired
    public AddressBookUpdateServiceImpl(AddressBookDataCheckLogic addressBookDataCheckLogic,
                                        AddressBookUpdateLogic addressBookUpdateLogic) {
        this.addressBookDataCheckLogic = addressBookDataCheckLogic;
        this.addressBookUpdateLogic = addressBookUpdateLogic;
    }

    /**
     * サービス実行<br/>
     *
     * @param addressBookEntity アドレス帳エンティティ
     * @return 更新件数
     */
    @Override
    public int execute(AddressBookEntity addressBookEntity) {

        // パラメタチェック
        AssertionUtil.assertNotNull("addressBookEntity", addressBookEntity);

        // 既存データの確認
        addressBookDataCheckLogic.execute(addressBookEntity);

        // ロジックの実行
        int result = addressBookUpdateLogic.execute(addressBookEntity);
        if (result == 0) {
            throwMessage(MSGCD_ADDRESSBOOK_UPDATE_FAIL);
        }
        return result;
    }
}
