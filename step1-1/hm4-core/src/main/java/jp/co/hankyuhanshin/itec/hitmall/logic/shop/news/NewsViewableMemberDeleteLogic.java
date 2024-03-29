/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.news;

/**
 * ニュース表示対象会員情報削除ロジック<br/>
 *
 * @author Yu
 */
public interface NewsViewableMemberDeleteLogic {

    /**
     * ニュース表示対象会員情報削除処理
     *
     * @param newsSeq ニュースSEQ
     * @return 処理件数
     */
    int execute(Integer newsSeq);
}
