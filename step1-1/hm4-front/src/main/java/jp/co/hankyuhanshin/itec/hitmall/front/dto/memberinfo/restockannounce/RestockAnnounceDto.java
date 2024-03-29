// 2023-renew No65 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.dto.memberinfo.restockannounce;

import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.icon.GoodsInformationIconDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goodsgroup.GoodsGroupImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.restockannounce.RestockAnnounceEntity;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * 入荷お知らせDtoクラス
 *
 * @author Thang Doan (VJP)
 */
@Data
@Component
@Scope("prototype")
public class RestockAnnounceDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 入荷お知らせエンティティ
     */
    private RestockAnnounceEntity restockAnnounceEntity;

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
// 2023-renew No65 to here
