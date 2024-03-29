/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.zipcode.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.shop.ZipCodeAddressDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.zipcode.ZipCodeAddressGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.zipcode.ZipCodeAddressGetService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 郵便番号住所情報取得Service<br/>
 *
 * @author natume
 * @version $Revision: 1.3 $
 */
@Service
public class ZipCodeAddressGetServiceImpl extends AbstractShopService implements ZipCodeAddressGetService {

    /**
     * 郵便番号住所情報取得Logic<br/>
     */
    private final ZipCodeAddressGetLogic zipCodeAddressGetLogic;

    @Autowired
    public ZipCodeAddressGetServiceImpl(ZipCodeAddressGetLogic zipCodeAddressGetLogic) {
        this.zipCodeAddressGetLogic = zipCodeAddressGetLogic;
    }

    /**
     * 郵便番号住所情報取得処理<br/>
     *
     * @param zipCode 郵便番号(7桁)
     * @return 郵便番号住所情報Dto
     * @throws Exception
     */
    @Override
    public ZipCodeAddressDto execute(String zipCode) {

        // 入力チェック
        ArgumentCheckUtil.assertNotEmpty("zipCode", zipCode);

        // 郵便番号住所情報取得
        return zipCodeAddressGetLogic.execute(zipCode);
    }

}
