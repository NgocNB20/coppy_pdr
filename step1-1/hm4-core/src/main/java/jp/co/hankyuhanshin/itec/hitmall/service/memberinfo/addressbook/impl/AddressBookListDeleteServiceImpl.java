/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.addressbook.impl;

import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.addressbook.AddressBookListDeleteLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.addressbook.AddressBookListDeleteService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * アドレス帳情報リスト削除サービス<br/>
 *
 * @author ueshima
 * @version $Revision: 1.4 $
 */
@Service
public class AddressBookListDeleteServiceImpl extends AbstractShopService implements AddressBookListDeleteService {

    /**
     * アドレス帳情報リスト削除ロジック
     */
    private final AddressBookListDeleteLogic addressBookListDeleteLogic;

    @Autowired
    public AddressBookListDeleteServiceImpl(AddressBookListDeleteLogic addressBookListDeleteLogic) {
        this.addressBookListDeleteLogic = addressBookListDeleteLogic;
    }

    /**
     * サービス実行<br/>
     *
     * @param addressBookSeq アドレス帳SEQ
     * @return 削除件数
     */
    @Override
    public int execute(Integer addressBookSeq, Integer memberInfoSeq) {
        // 入力パラメタの確認
        ArgumentCheckUtil.assertNotNull("addressBookSeq", addressBookSeq);

        List<Integer> addressBookSeqList = new ArrayList<>(1);
        addressBookSeqList.add(addressBookSeq);

        return this.execute(memberInfoSeq, addressBookSeqList);
    }

    /**
     * サービス実行<br/>
     *
     * @param memberInfoSeq      会員SEQ
     * @param addressBookSeqList アドレス帳SEQリスト
     * @return 削除件数
     */
    @Override
    public int execute(Integer memberInfoSeq, List<Integer> addressBookSeqList) {

        // 入力パラメタの確認
        ArgumentCheckUtil.assertNotNull("memberInfoSeq", memberInfoSeq);
        ArgumentCheckUtil.assertNotEmpty("addressBookSeqList", addressBookSeqList);

        // ロジックの実行
        return addressBookListDeleteLogic.execute(memberInfoSeq, addressBookSeqList);
    }
}
