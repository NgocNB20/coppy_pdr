/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.shop.top;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.ShopEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.top.GoodsRankingEntity;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * トップ画面初期表示用Dtoクラス<br/>
 *
 * @author s_tsuru
 */
@Data
@Component
@Scope("prototype")
public class TopInitialaizeInfoDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 受注ランキングリスト
     */
    private List<GoodsRankingEntity> orderRankingList;

    /**
     * クリックランキングリスト
     */
    private List<GoodsRankingEntity> clickRankingList;

    /**
     * ショップEntity
     */
    private ShopEntity shopEntity;

}
