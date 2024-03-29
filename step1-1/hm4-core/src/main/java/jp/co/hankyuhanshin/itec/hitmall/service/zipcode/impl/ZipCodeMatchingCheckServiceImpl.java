/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.zipcode.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.zipcode.ZipCodeDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.ZipCodeAddressDto;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.zipcode.ZipCodeMatchingCheckService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 郵便番号整合性チェックService<br/>
 *
 * @author ozaki
 * @version $Revision: 1.0 $
 */
@Service
public class ZipCodeMatchingCheckServiceImpl extends AbstractShopService implements ZipCodeMatchingCheckService {

    /**
     * 郵便番号Dao
     */
    private final ZipCodeDao zipCodeDao;

    @Autowired
    public ZipCodeMatchingCheckServiceImpl(ZipCodeDao zipCodeDao) {
        this.zipCodeDao = zipCodeDao;
    }

    /**
     * 郵便番号整合性チェック処理<br/>
     * 郵便番号と都道府県が一致しているかチェックを行う。<br/>
     *
     * @param zipCode    郵便番号(7桁)
     * @param prefecture 都道府県名
     * @return true 郵便番号と都道府県名が一致している場合 true
     */
    @Override
    public boolean execute(String zipCode, String prefecture) {

        // 入力チェック
        ArgumentCheckUtil.assertNotEmpty("zipCode", zipCode);
        ArgumentCheckUtil.assertNotEmpty("prefecture", prefecture);

        // 郵便番号に紐づく住所を取得し、その中に引数で指定された都道府県名が含まれているかをチェックする。
        List<ZipCodeAddressDto> zipCodeEntityList = zipCodeDao.getAddressList(zipCode);
        for (ZipCodeAddressDto dto : zipCodeEntityList) {
            if (dto.getPrefectureName().equals(prefecture)) {
                return true;
            }
        }

        // どちらのデータ上にも存在しなかった場合は、郵便番号と都道府県名が一致していない。
        return false;
    }

    // DIフィールド
}
