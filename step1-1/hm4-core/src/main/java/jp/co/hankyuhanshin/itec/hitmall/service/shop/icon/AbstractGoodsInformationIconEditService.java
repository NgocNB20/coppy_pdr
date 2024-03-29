/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.icon;

import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;

/**
 * アイコンの登録・更新・削除のベースとなる抽象クラス
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
public abstract class AbstractGoodsInformationIconEditService extends AbstractShopService {

    /**
     * 戻り値マップのキー：商品インフォメーションアイコン登録件数 value:int
     */
    public static final String GOODS_INFORMATION_ICON_REGIST = "GoodsInformationIconRegist";
    /**
     * 戻り値マップのキー：商品インフォメーションアイコン更新件数 value:int
     */
    public static final String GOODS_INFORMATION_ICON_UPDATE = "GoodsInformationIconUpdate";
    /**
     * 戻り値マップのキー：商品インフォメーションアイコン削除件数 value:int
     */
    public static final String GOODS_INFORMATION_ICON_DELETE = "GoodsInformationIconDelete";

    /**
     * 戻り値マップのキー：商品インフォメーションアイコン画像 ファイル登録件数 value:int
     */
    public static final String GOODS_INFORMATION_ICON_IMAGE_FILE_REGIST = "GoodsInformationIconImageFileRegist";
    /**
     * 戻り値マップのキー：商品インフォメーションアイコン画像 ファイル削除件数 value:int
     */
    public static final String GOODS_INFORMATION_ICON_IMAGE_FILE_DELETE = "GoodsInformationIconImageFileDelete";

    /**
     * 戻り値マップのキー：商品インフォメーションアイコン画像削除件数 value:int
     */
    public static final String GOODS_INFORMATION_ICON_IMAGE_DELETE = "GoodsInformationIconImageDelete";
    /**
     * 戻り値マップのキー：商品インフォメーションアイコン画像登録件数 value:int
     */
    public static final String GOODS_INFORMATION_ICON_IMAGE_REGIST = "GoodsInformationIconImageRegist";
    /**
     * 戻り値マップのキー：商品インフォメーションアイコン画像更新件数 value:int
     */
    public static final String GOODS_INFORMATION_ICON_IMAGE_UPDATE = "GoodsInformationIconImageUpdate";

}
