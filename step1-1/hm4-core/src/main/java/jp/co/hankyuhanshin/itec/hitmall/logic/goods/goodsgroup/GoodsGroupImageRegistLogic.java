/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupImageRegistUpdateDto;

import java.util.List;
import java.util.Map;

/**
 * 商品グループ画像登録<br/>
 *
 * @author hirata
 * @version $Revision: 1.2 $
 */
public interface GoodsGroupImageRegistLogic {
    // LGP0011

    /**
     * 処理件数マップ　商品グループ画像登録件数<br/>
     * <code>GOODS_GROUP_IMAGE_REGIST</code>
     */
    public static final String GOODS_GROUP_IMAGE_REGIST = "GoodsGroupImageRegist";

    /**
     * 処理件数マップ　商品グループ画像更新件数<br/>
     * <code>GOODS_GROUP_IMAGE_UPDATE</code>
     */
    public static final String GOODS_GROUP_IMAGE_UPDATE = "GoodsGroupImageUpdate";

    /**
     * 処理件数マップ　商品グループ画像削除件数<br/>
     * <code>GOODS_GROUP_IMAGE_DELETE</code>
     */
    public static final String GOODS_GROUP_IMAGE_DELETE = "GoodsGroupImageDelete";

    /**
     * 処理件数マップ　削除画像ファイルパスリスト<br/>
     * <code>DELETE_IMAGE_FILE_PATH_LIST</code>
     */
    public static final String DELETE_IMAGE_FILE_PATH_LIST = "DeleteImageFilePathList";

    /**
     * 処理件数マップ　登録画像ファイルパスリスト<br/>
     * <code>REGIST_IMAGE_FILE_PATH_LIST</code>
     */
    public static final String REGIST_IMAGE_FILE_PATH_LIST = "RegistImageFilePathList";

    /**
     * 商品グループSEQ不一致エラー<br/>
     * <code>MSGCD_GOODSGROUP_MISMATCH_FAIL</code>
     */
    public static final String MSGCD_GOODSGROUP_MISMATCH_FAIL = "LGP001101";

    /**
     * ファイル存在エラー<br/>
     * <code>MSGCD_FILE_EXIST_FAIL</code>
     */
    public static final String MSGCD_FILE_EXIST_FAIL = "LGP001102";

    /**
     * 商品グループ画像登録<br/>
     *
     * @param goodsGroupSeq                      商品グループSEQ
     * @param goodsGroupCode                     商品グループコード
     * @param goodsGroupImageRegistUpdateDtoList 商品グループ画像登録更新用DTOリスト
     * @return 処理件数マップ
     */
    Map<String, Object> execute(Integer goodsGroupSeq,
                                String goodsGroupCode,
                                List<GoodsGroupImageRegistUpdateDto> goodsGroupImageRegistUpdateDtoList);
}
