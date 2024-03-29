/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.group;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupImageRegistUpdateDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsRelationEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsTogetherBuyGroupEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品グループ登録更新<br/>
 *
 * @author hirata
 * @version $Revision: 1.5 $
 */
public interface GoodsGroupRegistUpdateService {

    /**
     * 処理種別（画面からの登録更新）<br/>
     * <code>PROCESS_TYPE_FROM_SCREEN</code>
     */
    public static final int PROCESS_TYPE_FROM_SCREEN = 0;

    /**
     * 処理種別（CSVからの登録更新）<br/>
     * <code>PROCESS_TYPE_FROM_CSV</code>
     */
    public static final int PROCESS_TYPE_FROM_CSV = 1;

    /**
     * 処理件数マップ　商品グループSEQ<br/>
     * <code>GOODS_GROUP_SEQ</code>
     */
    public static final String GOODS_GROUP_SEQ = "GoodsGroupSeq";

    /**
     * 処理件数マップ　商品グループ登録件数<br/>
     * <code>GOODS_GROUP_REGIST</code>
     */
    public static final String GOODS_GROUP_REGIST = "GoodsGroupRegist";

    /**
     * 処理件数マップ　商品グループ表示登録件数<br/>
     * <code>GOODS_GROUP_DISPLAY_REGIST</code>
     */
    public static final String GOODS_GROUP_DISPLAY_REGIST = "GoodsGroupDisplayRegist";

    /**
     * 処理件数マップ　商品グループ人気登録件数<br/>
     * <code>GOODS_GROUP_POPULARITY_REGIST</code>
     */
    public static final String GOODS_GROUP_POPULARITY_REGIST = "GoodsGroupPopularityRegist";

    /**
     * 処理件数マップ　商品グループ更新件数<br/>
     * <code>GOODS_GROUP_UPDATE</code>
     */
    public static final String GOODS_GROUP_UPDATE = "GoodsGroupUpdate";

    /**
     * 処理件数マップ　商品グループ表示更新件数<br/>
     * <code>GOODS_GROUP_DISPLAY_UPDATE</code>
     */
    public static final String GOODS_GROUP_DISPLAY_UPDATE = "GoodsGroupDisplayUpdate";

    /**
     * 処理件数マップ　商品グループ画像ファイル登録件数<br/>
     * <code>GOODS_GROUP_IMAGE_FILE_DELETE</code>
     */
    public static final String GOODS_GROUP_IMAGE_FILE_DELETE = "GoodsGroupImageFileDelete";

    /**
     * 処理件数マップ　商品グループ画像ファイル削除件数<br/>
     * <code>GOODS_GROUP_IMAGE_FILE_REGIST</code>
     */
    public static final String GOODS_GROUP_IMAGE_FILE_REGIST = "GoodsGroupImageFileRegist";

    /**
     * 処理件数マップ　在庫登録件数<br/>
     * <code>STOCK_REGIST</code>
     */
    public static final String STOCK_REGIST = "StockRegist";

    /**
     * 処理件数マップ　ワーニングメッセージ<br/>
     * <code>WARNING_MESSAGE</code>
     */
    public static final String WARNING_MESSAGE = "WarningMessage";

    /**
     * 実行メソッド<br/>
     *
     * @param goodsGroupDto                  商品グループDto
     * @param goodsRelationEntityList        関連商品エンティティリスト
     * @param goodsRelationEntityList        よく一緒に購入される商品エンティティリスト
     * @param goodsGroupImageRegistUpdateDto 商品グループ画像登録更新用DTOリスト（処理種別=CSV時はnull）
     * @param processType                    処理種別（画面/CSV）
     * @return 処理件数マップ
     */
    Map<String, Object> execute(GoodsGroupDto goodsGroupDto,
                                List<? extends GoodsRelationEntity> goodsRelationEntityList,
                                // 2023-renew No21 from here
                                List<? extends GoodsTogetherBuyGroupEntity> goodsTogetherBuyGroupEntityList,
                                // 2023-renew No21 to here
                                List<GoodsGroupImageRegistUpdateDto> goodsGroupImageRegistUpdateDto,
                                int processType,
                                String administratorName);

    // PDR Migrate Customization from here

    /**
     * 実行メソッド<br/>
     *
     * @param goodsGroupDto                  商品グループDto
     * @param goodsRelationEntityList        関連商品エンティティリスト
     * @param goodsRelationEntityList        よく一緒に購入される商品エンティティリスト
     * @param goodsGroupImageRegistUpdateDto 商品グループ画像登録更新用DTOリスト（処理種別=CSV時はnull）
     * @param processType                    処理種別（画面/CSV）
     * @param emotionFlg                     心意気商品登録更新用フラグ
     * @return 処理件数マップ
     */
    Map<String, Object> execute(GoodsGroupDto goodsGroupDto,
                                List<? extends GoodsRelationEntity> goodsRelationEntityList,
                                // 2023-renew No21 from here
                                List<? extends GoodsTogetherBuyGroupEntity> goodsTogetherBuyGroupEntityList,
                                // 2023-renew No21 to here
                                List<GoodsGroupImageRegistUpdateDto> goodsGroupImageRegistUpdateDto,
                                int processType,
                                boolean emotionFlg,
                                String administratorName);

    /**
     * 商品グループDto価格更新<br/>
     *
     * @param goodsGroupDto 商品グループDto
     * @param customParams  案件用引数
     */
    void calculateGoodsGroupPrice(GoodsGroupDto goodsGroupDto, Object... customParams);
    // PDR Migrate Customization to here
}
