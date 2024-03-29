/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.zipcode;

import jp.co.hankyuhanshin.itec.hitmall.dto.shop.ZipCodeAddressDto;

/**
 * 郵便番号住所情報取得Service<br/>
 *
 * @author natume
 * @version $Revision: 1.3 $
 */
public interface ZipCodeAddressGetService {

    /**
     * 郵便番号住所情報取得処理<br/>
     *
     * @param zipCode 郵便番号(7桁)
     * @return 郵便番号住所情報Dto
     * @throws Exception
     */
    ZipCodeAddressDto execute(String zipCode);
}
