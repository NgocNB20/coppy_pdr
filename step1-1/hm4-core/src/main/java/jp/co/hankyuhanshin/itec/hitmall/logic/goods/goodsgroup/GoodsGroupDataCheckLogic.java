/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsRelationEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsTogetherBuyGroupEntity;

import java.util.List;

/**
 * 商品グループデータチェック<br/>
 *
 * @author hirata
 * @version $Revision: 1.7 $
 */
public interface GoodsGroupDataCheckLogic {
    // LGP0006

    /**
     * 商品グループコード重複エラー<br/>
     * <code>MSGCD_GOODSGROUPCODE_REPETITION_FAIL</code>
     */
    public static final String MSGCD_GOODSGROUPCODE_REPETITION_FAIL = "LGP000601";

    /**
     * カテゴリなしエラー<br/>
     * <code>MSGCD_CATEGORY_NONE_FAIL</code>
     */
    public static final String MSGCD_CATEGORY_NONE_FAIL = "LGP000602";

    /**
     * 関連商品グループなしエラー<br/>
     * <code>MSGCD_RELATION_GOODSGROUP_NONE_FAIL</code>
     */
    public static final String MSGCD_RELATION_GOODSGROUP_NONE_FAIL = "LGP000603";

    // 2023-renew No21 from here
    /**
     * よく一緒に購入される商品グループなしエラー<br/>
     * <code>MSGCD_GOODS_TOGETHER_BUY_GROUP_NONE_FAIL</code>
     */
    public static final String MSGCD_GOODS_TOGETHER_BUY_GROUP_NONE_FAIL = "PDR-2023RENEW-21-006-";
    // 2023-renew No21 to here

    /**
     * 全商品削除エラー<br/>
     * <code>MSGCD_ALL_GOODS_DELETED_FAIL</code>
     */
    public static final String MSGCD_ALL_GOODS_DELETED_FAIL = "LGP000604";

    /**
     * インフォメーションアイコンPCなしエラー<br/>
     * <code>MSGCD_INFORMATIONICONPC_NONE_FAIL</code>
     */
    public static final String MSGCD_INFORMATIONICONPC_NONE_FAIL = "LGP000609";

    /**
     * 登録カテゴリ重複登録エラー<br/>
     * <code>MSGCD_CATEGORY_MULTI_REGIST_FAIL</code>
     */
    public static final String MSGCD_CATEGORY_MULTI_REGIST_FAIL = "LGP000611";

    /**
     * 関連商品重複登録エラー<br/>
     * <code>MSGCD_GOODSRELATION_MULTI_REGIST_FAIL</code>
     */
    public static final String MSGCD_GOODSRELATION_MULTI_REGIST_FAIL = "LGP000612";

    // 2023-renew No21 from here
    /**
     * よく一緒に購入される商品重複登録エラー<br/>
     * <code>MSGCD_GOODS_TOGETHER_BUY_GROUP_MULTI_REGIST_FAIL</code>
     */
    public static final String MSGCD_GOODS_TOGETHER_BUY_GROUP_MULTI_REGIST_FAIL = "PDR-2023RENEW-21-005-";
    // 2023-renew No21 to here

    /**
     * 商品グループ更新時の新着日付未設定エラー<br/>
     * <code>MSGCD_WHATSNEWDATE_NONE_FAIL</code>
     */
    public static final String MSGCD_WHATSNEWDATE_NONE_FAIL = "LGP000613";

    /**
     * 商品グループデータチェック<br/>
     * 商品グループDTOの登録・更新前チェックを行う。<br/>
     *
     * @param goodsGroupDto           商品グループDto
     * @param goodsRelationEntityList 関連商品エンティティリスト
     * @param goodsTogetherBuyGroupEntityList よく一緒に購入される商品エンティティリスト
     * @param shopSeq                 ショップSEQ
     */
    // 2023-renew No21 from here
    void execute(GoodsGroupDto goodsGroupDto, List<GoodsRelationEntity> goodsRelationEntityList, List<GoodsTogetherBuyGroupEntity> goodsTogetherBuyGroupEntityList, Integer shopSeq);
    // 2023-renew No21 to here

    /**
     * 商品が利用可能か検査する
     * <p></p>
     *
     * @param goodsGroupSeq 商品グループSEQ
     * @param siteType      PC/MB/Admin
     * @return trueの場合は指定した商品を利用可能.それ以外は不可
     */
    boolean isAvailable(Integer goodsGroupSeq, HTypeSiteType siteType);
}
