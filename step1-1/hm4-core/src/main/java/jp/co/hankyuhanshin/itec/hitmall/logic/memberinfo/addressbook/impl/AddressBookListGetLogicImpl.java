/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.addressbook.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.addressbook.AddressBookDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.addressbook.AddressBookSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.addressbook.AddressBookEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.addressbook.AddressBookListGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * アドレス帳情報リスト取得ロジック<br/>
 *
 * @author ueshima
 * @version $Revision: 1.3 $
 */
@Component
public class AddressBookListGetLogicImpl implements AddressBookListGetLogic {

    /**
     * アドレス帳Dao
     */
    private final AddressBookDao addressBookDao;

    @Autowired
    public AddressBookListGetLogicImpl(AddressBookDao addressBookDao) {
        this.addressBookDao = addressBookDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param addressBookConditionDto アドレス帳検索条件
     * @return アドレス帳エンティティリスト
     */
    @Override
    public List<AddressBookEntity> execute(AddressBookSearchForDaoConditionDto addressBookConditionDto) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("addressBookConditionDto", addressBookConditionDto);

        // ロジックの実行
        return addressBookDao.getSearchAddressBookList(
                        addressBookConditionDto, addressBookConditionDto.getPageInfo().getSelectOptions());
    }

}
