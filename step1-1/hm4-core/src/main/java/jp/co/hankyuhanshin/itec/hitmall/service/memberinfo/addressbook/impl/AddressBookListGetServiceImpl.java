/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.addressbook.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.addressbook.AddressBookSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.addressbook.AddressBookEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.addressbook.AddressBookListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.addressbook.AddressBookListGetService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * アドレス帳情報リスト取得サービス<br/>
 *
 * @author ueshima
 * @version $Revision: 1.3 $
 */
@Service
public class AddressBookListGetServiceImpl extends AbstractShopService implements AddressBookListGetService {

    /**
     * アドレス帳情報リスト取得ロジック
     */
    private final AddressBookListGetLogic addressBookListGetLogic;

    @Autowired
    public AddressBookListGetServiceImpl(AddressBookListGetLogic addressBookListGetLogic) {
        this.addressBookListGetLogic = addressBookListGetLogic;
    }

    /**
     * アドレス帳情報リスト取得<br/>
     *
     * @param addressBookConditionDto アドレス帳検索条件Dto
     * @return アドレス帳エンティティリスト
     */
    @Override
    public List<AddressBookEntity> execute(AddressBookSearchForDaoConditionDto addressBookConditionDto) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("addressBookConditionDto", addressBookConditionDto);

        // ロジックの実行
        return addressBookListGetLogic.execute(addressBookConditionDto);
    }
}
