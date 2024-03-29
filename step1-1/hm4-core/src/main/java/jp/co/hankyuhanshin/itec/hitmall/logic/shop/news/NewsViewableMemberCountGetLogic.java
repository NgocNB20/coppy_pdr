/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.news;

/**
 * ニュース表示対象会員件数取得ロジック<br/>
 *
 * @author Yu
 */
public interface NewsViewableMemberCountGetLogic {
    /**
     * ニュース表示対象会員件数取得処理
     *
     * @param newsSeq ニュースSEQ
     * @return 件数
     */
    int execute(Integer newsSeq);
}
