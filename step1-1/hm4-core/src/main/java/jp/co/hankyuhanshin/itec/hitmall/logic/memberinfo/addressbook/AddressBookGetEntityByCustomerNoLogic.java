// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.addressbook;

import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.addressbook.AddressBookEntity;

/**
 * PDR#011 #037 顧客情報・住所情報の取り込み<br/>
 * 住所録取得ロジック(顧客番号)<br/>
 *
 * @author s.kume
 */
public interface AddressBookGetEntityByCustomerNoLogic {

    /**
     * /**
     * 顧客番号から住所録情報を取得する<br/>
     *
     * @param customerNo 顧客番号
     * @return 住所録エンティティ
     */
    AddressBookEntity execute(Integer customerNo);
}
// PDR Migrate Customization to here
