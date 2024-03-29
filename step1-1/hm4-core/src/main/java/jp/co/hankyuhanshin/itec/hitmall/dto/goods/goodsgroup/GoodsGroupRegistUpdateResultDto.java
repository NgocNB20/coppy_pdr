/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.dto.common.FileRegistDto;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * 商品グループ登録更新 結果Dto<br/>
 * 商品グル―プ登録更新サービスの結果を保管するDto
 *
 * @author s_tsuru
 */
@Data
@Component
@Scope("prototype")
public class GoodsGroupRegistUpdateResultDto implements Serializable {

    /**
     * SerialVersionUID<br/>
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品グループ登録件数
     */
    private int resultGoodsGroupRegist = 0;

    /**
     * 商品グループ表示登録件数
     */
    private int resultGoodsGroupDisplayRegist = 0;

    /**
     * 商品グループ人気登録件数
     */
    private int resultGoodsGroupPopularityRegist = 0;

    /**
     * 商品グループ更新件数
     */
    private int resultGoodsGroupUpdate = 0;

    /**
     * 商品グループ表示更新件数
     */
    private int resultGoodsGroupDisplayUpdate = 0;

    /**
     * カテゴリ登録商品登録件数
     */
    private int resultCategoryGoodsRegist = 0;

    /**
     * カテゴリ登録商品削除件数
     */
    private int resultCategoryGoodsDelete = 0;

    /**
     * 関連商品登録件数
     */
    private int resultGoodsRelationRegist = 0;

    /**
     * 関連商品更新件数
     */
    private int resultGoodsRelationUpdate = 0;

    /**
     * 関連商品削除件数
     */
    private int resultGoodsRelationDelete = 0;

    // 2023-renew No21 from here
    /**
     * よく一緒に購入される商品登録件数
     */
    private int resultGoodsTogetherBuyGroupRegist = 0;

    /**
     * よく一緒に購入される商品更新件数
     */
    private int resultGoodsTogetherBuyGroupUpdate = 0;

    /**
     * よく一緒に購入される商品削除件数
     */
    private int resultGoodsTogetherBuyGroupDelete = 0;
    // 2023-renew No21 to here

    /**
     * 商品グループ画像登録件数
     */
    private int resultGoodsGroupImageRegist = 0;

    /**
     * 商品グループ画像更新件数
     */
    private int resultGoodsGroupImageUpdate = 0;

    /**
     * 商品グループ画像削除件数
     */
    private int resultGoodsGroupImageDelete = 0;

    /**
     * 削除画像ファイルパスリスト
     */
    private List<String> deleteImageFilePathList = null;

    /**
     * 登録画像ファイルパスリスト
     */
    private List<FileRegistDto> registImageFilePathList = null;

    /**
     * 商品登録件数
     */
    private int resultGoodsRegist = 0;

    /**
     * 商品更新件数
     */
    private int resultGoodsUpdate = 0;

    /**
     * 在庫設定登録件数
     */
    private int resultStockSettingRegist = 0;

    /**
     * 在庫設定更新件数
     */
    private int resultStockSettingUpdate = 0;

    /**
     * 在庫登録件数
     */
    private int resultStockRegist = 0;

    /**
     * 商品グループ画像ファイル登録件数
     */
    private int resultGoodsGroupImageFileRegist = 0;

    /**
     * 商品グループ画像ファイル削除件数
     */
    private int resultGoodsGroupImageFileDelete = 0;

    /**
     * ワーニングメッセージ(ActionメッセージIDのカンマ区切り)
     */
    private String warningMessage = "";

    /**
     * 商品規格画像登録件数
     */
    private int resultGoodsUnitImageRegist = 0;

    /**
     * 商品規格画像更新件数
     */
    private int resultGoodsUnitImageUpdate = 0;

    /**
     * 商品規格画像削除件数
     */
    private int resultGoodsUnitImageDelete = 0;

    /**
     * 削除規格画像ファイルパスリスト
     */
    private List<String> deleteUnitImageFilePathList = null;

    /**
     * 登録規格画像ファイルパスリスト
     */
    private List<FileRegistDto> registUnitImageFilePathList = null;

    /**
     * 商品規格画像ファイル登録件数
     */
    private int resultGoodsUnitImageFileRegist = 0;

    /**
     * 商品規格画像ファイル削除件数
     */
    private int resultGoodsUnitImageFileDelete = 0;
}
