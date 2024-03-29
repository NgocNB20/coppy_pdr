/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.addressbook.AddressBookSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.addressbook.AddressBookEntity;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.addressbook.AddressBookListGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.addressbook.AddressBookRegistService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.OrderAddressBookRegistService;
import jp.co.hankyuhanshin.itec.hitmall.web.PageInfo;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 注文時、アドレス帳登録サービス
 *
 * @author negishi
 * @version $Revision: 1.3 $
 */
@Service
public class OrderAddressBookRegistServiceImpl extends AbstractShopService implements OrderAddressBookRegistService {

    /**
     * アドレス帳リスト取得サービス
     */
    private final AddressBookListGetService addressBookListGetService;

    /**
     * アドレス帳登録サービス
     */
    private final AddressBookRegistService addressBookRegistService;

    @Autowired
    public OrderAddressBookRegistServiceImpl(AddressBookListGetService addressBookListGetService,
                                             AddressBookRegistService addressBookRegistService) {

        this.addressBookListGetService = addressBookListGetService;
        this.addressBookRegistService = addressBookRegistService;
    }

    /**
     * 注文時、アドレス帳登録サービス
     *
     * @param addressBookEntity アドレス帳エンティティ
     * @return 登録件数
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public int execute(AddressBookEntity addressBookEntity, Integer memberInfoSeq) {
        // パラメタチェック
        ArgumentCheckUtil.assertNotNull("addressBookEntity", addressBookEntity);

        // システムプロパティから登録アドレス帳名を取得
        String addressBookName = PropertiesUtil.getSystemPropertiesValue(ADDRESS_BOOK_NAME);

        // プロパティファイルに設定されていない場合は、画面から入力された姓＋名にする。
        if (addressBookName == null || addressBookName.isEmpty()) {
            addressBookName = addressBookEntity.getAddressBookName();
        }

        // アドレス帳リスト取得サービス実行
        List<AddressBookEntity> addressBookEntityList =
                        addressBookListGetService.execute(createAddressBookSearchForDaoConditionDto(memberInfoSeq));

        // アドレス帳名リスト作成
        List<String> addressBookNameList = new ArrayList<>();
        for (AddressBookEntity addressBookEntity2 : addressBookEntityList) {
            addressBookNameList.add(addressBookEntity2.getAddressBookName());
        }

        // 重複しないアドレス帳名を作成
        for (int index = 2; ; index++) {
            if (!addressBookNameList.contains(addressBookName)) {
                break;
            }

            if (!addressBookNameList.contains(addressBookName + index)) {
                addressBookName = addressBookName + index;
                break;
            }
        }

        // アドレス帳名を設定
        addressBookEntity.setAddressBookName(addressBookName);
        // 会員情報SEQを設定
        addressBookEntity.setMemberInfoSeq(memberInfoSeq);

        // アドレス帳登録サービス実行
        return addressBookRegistService.execute(addressBookEntity);
    }

    /**
     * アドレス帳Dao用検索条件Dtoを作成
     *
     * @return アドレス帳Dao用検索条件Dto
     */
    protected AddressBookSearchForDaoConditionDto createAddressBookSearchForDaoConditionDto(Integer memberInfoSeq) {
        AddressBookSearchForDaoConditionDto addressBookConditionDto =
                        getComponent(AddressBookSearchForDaoConditionDto.class);
        addressBookConditionDto.setMemberInfoSeq(memberInfoSeq);

        // ページングセットアップ
        PageInfo pageInfo = ApplicationContextUtility.getBean(PageInfo.class);
        pageInfo.setPage(1);
        pageInfo.setLimit(Integer.MAX_VALUE);
        pageInfo.setupSelectOptions();
        addressBookConditionDto.setPageInfo(pageInfo);

        return addressBookConditionDto;
    }

}
