/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.addressbook.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.addressbook.AddressBookDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.addressbook.AddressBookListDeleteLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * アドレス帳情報リスト削除ロジック<br/>
 *
 * @author ueshima
 * @version $Revision: 1.4 $
 */
@Component
public class AddressBookListDeleteLogicImpl extends AbstractShopLogic implements AddressBookListDeleteLogic {

    /**
     * アドレス帳Dao
     */
    private final AddressBookDao addressBookDao;

    @Autowired
    public AddressBookListDeleteLogicImpl(AddressBookDao addressBookDao) {
        this.addressBookDao = addressBookDao;
    }

    /**
     * アドレス帳情報削除処理<br/>
     *
     * @param memberInfoSeq      会員SEQ
     * @param addressBookSeqList アドレス帳SEQリスト
     * @return 削除件数
     */
    @Override
    public int execute(Integer memberInfoSeq, List<Integer> addressBookSeqList) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("memberInfoSeq", memberInfoSeq);
        ArgumentCheckUtil.assertNotEmpty("addressBookSeqList", addressBookSeqList);

        return addressBookDao.deleteList(memberInfoSeq, addressBookSeqList);
    }

    /**
     * アドレス帳情報削除処理<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @return 削除件数
     */
    @Override
    public int execute(Integer memberInfoSeq) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("memberInfoSeq", memberInfoSeq);

        // 削除
        return addressBookDao.deleteListByMemberInfoSeq(memberInfoSeq);
    }

}
