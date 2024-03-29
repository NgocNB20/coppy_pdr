/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsImageEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品規格画像登録<br/>
 *
 * @author thang
 * @version $Revision: 1.0 $
 */
public interface GoodsImageRegistLogic {

    /**
     * 商品規格画像登録件数<br/>
     * <code>GOODS_IMAGE_REGIST</code>
     */
    String GOODS_IMAGE_REGIST = "GoodsImageRegist";

    /**
     * 商品規格画像更新件数<br/>
     * <code>GOODS_IMAGE_UPDATE</code>
     */
    String GOODS_IMAGE_UPDATE = "GoodsImageUpdate";

    /**
     * 商品規格画像更新件数<br/>
     * <code>GOODS_IMAGE_DELETE</code>
     */
    String GOODS_IMAGE_DELETE = "GoodsImageDelete";

    /**
     * 処理件数マップ　削除画像ファイルパスリスト<br/>
     * <code>DELETE_UNIT_IMAGE_FILE_PATH_LIST</code>
     */
    String DELETE_UNIT_IMAGE_FILE_PATH_LIST = "DeleteUnitImageFilePathList";

    /**
     * 処理件数マップ　登録画像ファイルパスリスト<br/>
     * <code>REGIST_UNIT_IMAGE_FILE_PATH_LIST</code>
     */
    String REGIST_UNIT_IMAGE_FILE_PATH_LIST = "RegistUnitImageFilePathList";

    /**
     * 商品規格画像登録<br/>
     * @param goodsGroupSeq         商品グループSEQ
     * @param goodsImageEntityList  商品規格画像一覧
     * @return 処理件数マップ
     */
    Map<String, Object> execute(Integer goodsGroupSeq, List<GoodsImageEntity> goodsImageEntityList);
}
