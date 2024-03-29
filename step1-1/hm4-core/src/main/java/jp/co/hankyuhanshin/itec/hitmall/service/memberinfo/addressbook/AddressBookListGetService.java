/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.addressbook;

import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.addressbook.AddressBookSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.addressbook.AddressBookEntity;

import java.util.List;

/**
 * アドレス帳情報リスト取得サービス<br/>
 *
 * @author ueshima
 * @version $Revision: 1.3 $
 */
public interface AddressBookListGetService {

    /**
     * アドレス帳情報リスト取得<br/>
     *
     * @param addressBookConditionDto アドレス帳検索条件Dto
     * @return アドレス帳エンティティリスト
     */
    List<AddressBookEntity> execute(AddressBookSearchForDaoConditionDto addressBookConditionDto);

}
