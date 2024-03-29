/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUploadType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupEntity;

import java.util.List;

/**
 * 商品グループ詳細取得<br/>
 *
 * @author ozaki
 * @version $Revision: 1.5 $
 */
public interface GoodsGroupDetailsGetByCodeLogic {

    // LGP0003

    /**
     * 商品グループ詳細取得<br/>
     * 商品グループコードから、商品グループ詳細DTOを取得する。<br/>
     *
     * @param shopSeq        ショップSEQ
     * @param goodsGroupCode 商品グループコード
     * @param goodsCode      商品コード
     * @param siteType       サイト区分
     * @param openStatus     公開状態
     * @return 商品グループ情報DTO
     * @see jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupDetailsGetByCodeLogic#execute(Integer, String, String, HTypeSiteType, HTypeGoodsOpenStatus, Boolean)
     * @see jp.co.hankyuhanshin.itec.hitmall.service.goods.group.GoodsGroupGetService#executeForPreview(Integer, String, HTypeSiteType)
     */
    GoodsGroupDto execute(Integer shopSeq,
                          String goodsGroupCode,
                          String goodsCode,
                          HTypeSiteType siteType,
                          HTypeOpenDeleteStatus openStatus);

    /**
     * 商品グループ詳細取得<br/>
     * 商品グループコードから、商品グループ詳細DTOを取得する。<br/>
     *
     * @param shopSeq        ショップSEQ
     * @param goodsGroupCode 商品グループコード
     * @param goodsCode      商品コード
     * @param siteType       サイト区分
     * @param openStatus     公開状態
     * @param imageGetFlag   取得フラグ true..全商品規格画像 false..デフォルト商品規格画像
     * @return 商品グループ情報DTO
     */
    GoodsGroupDto execute(Integer shopSeq,
                          String goodsGroupCode,
                          String goodsCode,
                          HTypeSiteType siteType,
                          HTypeOpenDeleteStatus openStatus,
                          Boolean imageGetFlag);

    // PDR Migrate Customization from here

    /**
     * 商品グループ情報取得<br/>
     *
     * @param shopSeq        ショップSEQ
     * @param goodsGroupCode 商品グループコード
     * @param goodsCode      商品コード
     * @param siteType       サイト区分
     * @param openStatus     公開状態
     * @param customParams   案件用引数
     * @return 商品グループエンティティ
     */
    public GoodsGroupEntity getGoodsGroup(Integer shopSeq,
                                          String goodsGroupCode,
                                          String goodsCode,
                                          HTypeSiteType siteType,
                                          HTypeOpenDeleteStatus openStatus,
                                          Object... customParams);

    /**
     * アップロード種別取得<br/>
     *
     * @param csvUploadType アップロード種別
     */
    void setUploadType(HTypeUploadType csvUploadType);

    // 2023-renew No92 from here
    /**
     * 商品グループ詳細取得<br/>
     *
     * @param goodsCodes    商品コードリスト
     * @return  商品グループ情報DTOリスト
     */
    List<GoodsGroupDto> execute(List<String> goodsCodes);
    // 2023-renew No92 to here
    // PDR Migrate Customization to here
}
