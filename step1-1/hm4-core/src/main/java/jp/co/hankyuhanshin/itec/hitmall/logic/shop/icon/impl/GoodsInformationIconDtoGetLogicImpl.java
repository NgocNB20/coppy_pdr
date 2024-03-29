/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.icon.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsInformationIconDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.icon.GoodsInformationIconDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsInformationIconEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.icon.GoodsInformationIconDtoGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * アイコンDto取得
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
@Component
public class GoodsInformationIconDtoGetLogicImpl extends AbstractShopLogic implements GoodsInformationIconDtoGetLogic {

    /**
     * 商品インフォメーションアイコンDao
     */
    private final GoodsInformationIconDao goodsInformationIconDao;

    @Autowired
    public GoodsInformationIconDtoGetLogicImpl(GoodsInformationIconDao goodsInformationIconDao) {

        this.goodsInformationIconDao = goodsInformationIconDao;
    }

    /**
     * アイコン取得処理
     *
     * @param iconSeq アイコンSEQ
     * @param shopSeq ショップSEQ
     * @return アイコンDTO
     */
    @Override
    public GoodsInformationIconDto execute(Integer iconSeq, Integer shopSeq) {

        // パラメータチェック
        this.checkParam(iconSeq);

        // アイコン情報取得
        GoodsInformationIconEntity entity = goodsInformationIconDao.getEntityByShopSeq(iconSeq, shopSeq);

        GoodsInformationIconDto iconDto = this.createIconDto(entity);

        return iconDto;
    }

    /**
     * アイコンDtoを作成
     *
     * @param entity 商品インフォメーションアイコンエンティティ
     * @return アイコンDto
     */
    protected GoodsInformationIconDto createIconDto(GoodsInformationIconEntity entity) {
        GoodsInformationIconDto iconDto = super.getComponent(GoodsInformationIconDto.class);

        iconDto.setGoodsInformationIconEntity(entity);

        return iconDto;
    }

    /**
     * パラメータチェック
     *
     * @param iconSeq アイコンSEQ
     */
    protected void checkParam(Integer iconSeq) {
        ArgumentCheckUtil.assertGreaterThanZero("iconSeq", iconSeq);

    }

}
