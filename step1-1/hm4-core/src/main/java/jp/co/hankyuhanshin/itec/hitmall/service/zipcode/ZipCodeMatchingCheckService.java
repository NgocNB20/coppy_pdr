/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.zipcode;

/**
 * 郵便番号整合性チェックService<br/>
 *
 * @author ozaki
 * @version $Revision: 1.0 $
 */
public interface ZipCodeMatchingCheckService {

    /**
     * 郵便番号整合性チェック処理<br/>
     *
     * @param zipCode    郵便番号(7桁)
     * @param prefecture 都道府県名
     * @return true チェックOK
     * @throws Exception
     */
    boolean execute(String zipCode, String prefecture);
}
