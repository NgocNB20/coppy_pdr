/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.dto.memberinfo.favorite;

import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.icon.GoodsInformationIconDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goodsgroup.GoodsGroupImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.favorite.FavoriteEntity;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * お気に入りDtoクラス
 *
 * @author DtoGenerator
 * @version $Revision: 1.4 $
 */
@Data
@Component
@Scope("prototype")
public class FavoriteDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * お気に入りエンティティ
     */
    private FavoriteEntity favoriteEntity;

    /**
     * 商品詳細DTO
     */
    private GoodsDetailsDto goodsDetailsDto;

    /**
     * 商品グループ画像エンティティリスト
     */
    private List<GoodsGroupImageEntity> goodsGroupImageEntityList;

    /**
     * アイコン詳細DTO
     */
    private List<GoodsInformationIconDetailsDto> goodsInformationIconDetailsDtoList;

    /**
     * 在庫表示
     */
    private String stockStatus;
}
