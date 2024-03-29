/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.favorite;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.favorite.FavoriteDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.favorite.FavoriteSearchForDaoConditionDto;

import java.util.List;

/**
 * お気に入り情報リスト取得サービス<br/>
 *
 * @author ueshima
 * @version $Revision: 1.3 $
 */
public interface FavoriteListGetService {

    /**
     * お気に入り情報リスト取得処理<br/>
     * <p>
     * ログイン会員のお気に入り情報を取得する。<br/>
     *
     * @param favoriteConditionDto お気に入り検索条件Dto
     * @param siteType             サイト区分
     * @return お気に入りDTOリスト
     */
    List<FavoriteDto> execute(FavoriteSearchForDaoConditionDto favoriteConditionDto, HTypeSiteType siteType);

}
