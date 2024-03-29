/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.saleannounce;

import jp.co.hankyuhanshin.itec.hitmall.dto.saleannounce.SaleAnnounceAddImportListDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.saleannounce.SaleAnnounceMailEntity;

import java.util.List;

/**
 * セールお知らせメールアドレス帳登録リスト取得<br/>
 *
 * @author takashima
 * @version $Revision: 1.2 $
 */
public interface SaleAddImportListGetLogic {

    /**
     * 実行メソッド<br/>
     * @param  keyList 商品SEQと会員SEQをまとめたキー
     * @return セールお知らせメールアドレス帳登録Dtoリスト
     */
    List<SaleAnnounceAddImportListDto> execute(List<String> keyList);
}
