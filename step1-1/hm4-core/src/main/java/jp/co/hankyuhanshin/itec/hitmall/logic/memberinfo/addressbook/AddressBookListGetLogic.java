/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.addressbook;

import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.addressbook.AddressBookSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.addressbook.AddressBookEntity;

import java.util.List;

/**
 * アドレス帳情報リスト取得ロジック<br/>
 *
 * @author ueshima
 * @version $Revision: 1.3 $
 */
public interface AddressBookListGetLogic {

    /**
     * ロジック実行<br/>
     *
     * @param addressBookConditionDto アドレス帳検索条件DTO
     * @return アドレス帳エンティティリスト
     */
    List<AddressBookEntity> execute(AddressBookSearchForDaoConditionDto addressBookConditionDto);
}
