/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry;

import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryGroupEntity;

import java.util.List;

/**
 * お問い合わせ分類リスト取得サービス
 *
 * @author wh4200
 * @version $Revision: 1.3 $
 */
public interface InquiryGroupListGetService {

    /**
     * メソッド概要<br/>
     * メソッドの説明・概要<br/>
     * 対象の問い合わせ分類リストを取得する
     *
     * @param なし
     * @return お問い合わせ分類情報ティティリスト
     */
    List<InquiryGroupEntity> execute();

}
