/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupImageEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品グループ画像登録(データのみ、画像ファイル操作は行わない CSV用)<br/>
 *
 * @author hirata
 * @version $Revision: 1.1 $
 */
public interface GoodsGroupImageRegistDataOnlyLogic {
    // LGP0022

    /**
     * 処理件数マップ　商品グループ画像登録件数<br/>
     * <code>GOODS_GROUP_IMAGE_REGIST</code>
     */
    public static final String GOODS_GROUP_IMAGE_REGIST = GoodsGroupImageRegistLogic.GOODS_GROUP_IMAGE_REGIST;

    /**
     * 処理件数マップ　商品グループ画像更新件数<br/>
     * <code>GOODS_GROUP_IMAGE_UPDATE</code>
     */
    public static final String GOODS_GROUP_IMAGE_UPDATE = GoodsGroupImageRegistLogic.GOODS_GROUP_IMAGE_UPDATE;

    /**
     * 処理件数マップ　商品グループ画像削除件数<br/>
     * <code>GOODS_GROUP_IMAGE_DELETE</code>
     */
    public static final String GOODS_GROUP_IMAGE_DELETE = GoodsGroupImageRegistLogic.GOODS_GROUP_IMAGE_DELETE;

    /**
     * 商品グループSEQ不一致エラー<br/>
     * <code>MSGCD_GOODSGROUP_MISMATCH_FAIL</code>
     */
    public static final String MSGCD_GOODSGROUP_MISMATCH_FAIL =
                    GoodsGroupImageRegistLogic.MSGCD_GOODSGROUP_MISMATCH_FAIL;

    /**
     * 処理件数マップ　削除画像ファイルパスリスト<br/>
     * <code>DELETE_IMAGE_FILE_PATH_LIST</code>
     */
    public static final String DELETE_IMAGE_FILE_PATH_LIST = GoodsGroupImageRegistLogic.DELETE_IMAGE_FILE_PATH_LIST;

    /**
     * ファイル存在エラー<br/>
     * <code>MSGCD_FILE_EXIST_FAIL</code>
     */
    public static final String MSGCD_FILE_EXIST_FAIL = GoodsGroupImageRegistLogic.MSGCD_FILE_EXIST_FAIL;

    /**
     * 商品グループ画像登録<br/>
     *
     * @param goodsGroupSeq             商品グループSEQ
     * @param goodsGroupCode            商品グループコード
     * @param goodsGroupImageEntityList 商品グループ画像エンティティリスト
     * @return 処理件数マップ
     */
    Map<String, Object> execute(Integer goodsGroupSeq,
                                String goodsGroupCode,
                                List<GoodsGroupImageEntity> goodsGroupImageEntityList);
}
