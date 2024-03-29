/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.favorite.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockStatusType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockStatusDisplayConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.favorite.FavoriteDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockStatusDisplayGetRealStatusLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.favorite.FavoriteGoodsStockStatusGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

/**
 * お気に入り商品の在庫状態取得ロジック<br/>
 *
 * @author Kaneko　2013/03/01
 */
@Component
public class FavoriteGoodsStockStatusGetLogicImpl extends AbstractShopLogic
                implements FavoriteGoodsStockStatusGetLogic {

    /**
     * リアルタイム在庫状況判定ロジック
     */
    private StockStatusDisplayGetRealStatusLogic stockStatusDisplayGetRealStatusLogic;

    /**
     * コンストラクタ
     *
     * @param stockStatusDisplayGetRealStatusLogic
     */
    @Autowired
    public FavoriteGoodsStockStatusGetLogicImpl(StockStatusDisplayGetRealStatusLogic stockStatusDisplayGetRealStatusLogic) {
        this.stockStatusDisplayGetRealStatusLogic = stockStatusDisplayGetRealStatusLogic;

    }

    /**
     * お気に入り商品の在庫状態の設定。<br/>
     * <pre>
     * 商品の公開状態、公開期間、販売状態、販売期間、在庫数条件に基づいて在庫状態を決定する。
     * 在庫状態判定の詳細は「26_HM3_共通部仕様書_在庫状態表示条件.xls」参照。
     * </pre>
     *
     * @param favoriteDtoList お気に入り商品DTOリスト
     * @return お気に入り商品DTOリスト内の最大優先度の在庫状態
     */
    @Override
    public List<FavoriteDto> execute(List<FavoriteDto> favoriteDtoList) {

        for (FavoriteDto favoriteDto : favoriteDtoList) {

            GoodsDetailsDto goodsDetailsDto = favoriteDto.getGoodsDetailsDto();

            HTypeStockStatusType currentStatus = getCurrentStockStatus(goodsDetailsDto);

            favoriteDto.setStockStatus(EnumTypeUtil.getValue(currentStatus));

        }
        // 在庫状態を返却
        return favoriteDtoList;
    }

    /**
     * リアルタイムの在庫状況を取得<br/>
     *
     * @param goodsDetailsDto 商品詳細DTO
     * @return リアルタイムの在庫状況
     */
    protected HTypeStockStatusType getCurrentStockStatus(GoodsDetailsDto goodsDetailsDto) {

        StockStatusDisplayConditionDto condition =
                        ApplicationContextUtility.getBean(StockStatusDisplayConditionDto.class);

        // 販売可能在庫数
        condition.setSalesPossibleStock(goodsDetailsDto.getSalesPossibleStock());
        // 残少表示在庫数
        condition.setRemainderFewStock(goodsDetailsDto.getRemainderFewStock());
        // 在庫管理フラグ
        condition.setStockManagementFlag(goodsDetailsDto.getStockManagementFlag());
        // 公開状態
        HTypeOpenDeleteStatus openStatus = null;
        // 公開開始日
        Timestamp openStartTime = null;
        // 公開終了日
        Timestamp openEndTime = null;
        // 販売状態、販売開始日、販売終了日
        condition.setSaleStatus(goodsDetailsDto.getSaleStatusPC());
        condition.setSaleStartTime(goodsDetailsDto.getSaleStartTimePC());
        condition.setSaleEndTime(goodsDetailsDto.getSaleEndTimePC());

        openStatus = goodsDetailsDto.getGoodsOpenStatusPC();
        openStartTime = goodsDetailsDto.getOpenStartTimePC();
        openEndTime = goodsDetailsDto.getOpenEndTimePC();

        // 在庫状態を決定
        // お気に入り商品リストでは公開状態も考慮する
        HTypeStockStatusType currentStatus =
                        stockStatusDisplayGetRealStatusLogic.execute(condition, openStatus, openStartTime, openEndTime);
        return currentStatus;
    }

}
