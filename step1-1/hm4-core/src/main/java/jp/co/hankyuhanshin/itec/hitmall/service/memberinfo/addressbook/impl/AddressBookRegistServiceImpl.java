/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.addressbook.impl;

import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.addressbook.AddressBookEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.addressbook.AddressBookDataCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.addressbook.AddressBookRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.addressbook.AddressBookRegistService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * アドレス帳情報登録サービス<br/>
 *
 * @author ueshima
 * @version $Revision: 1.3 $
 */
@Service
public class AddressBookRegistServiceImpl extends AbstractShopService implements AddressBookRegistService {

    /**
     * アドレス帳データチェックロジック
     */
    private final AddressBookDataCheckLogic addressBookDataCheckLogic;

    /**
     * アドレス帳情報登録ロジック
     */
    private final AddressBookRegistLogic addressBookRegistLogic;

    @Autowired
    public AddressBookRegistServiceImpl(AddressBookDataCheckLogic addressBookDataCheckLogic,
                                        AddressBookRegistLogic addressBookRegistLogic) {
        this.addressBookDataCheckLogic = addressBookDataCheckLogic;
        this.addressBookRegistLogic = addressBookRegistLogic;
    }

    /**
     * アドレス帳情報登録処理<br/>
     *
     * @param addressBookEntity アドレス帳エンティティ
     * @return 登録件数
     */
    @Override
    public int execute(AddressBookEntity addressBookEntity) {

        // パラメタチェック
        ArgumentCheckUtil.assertNotNull("addressBookEntity", addressBookEntity);

        // 既存データの確認
        addressBookDataCheckLogic.execute(addressBookEntity);

        // ロジックの実行
        int result = addressBookRegistLogic.execute(addressBookEntity);
        if (result == 0) {
            throwMessage(MSGCD_ADDRESSBOOK_INSERT_FAIL);
        }
        return result;
    }
}
