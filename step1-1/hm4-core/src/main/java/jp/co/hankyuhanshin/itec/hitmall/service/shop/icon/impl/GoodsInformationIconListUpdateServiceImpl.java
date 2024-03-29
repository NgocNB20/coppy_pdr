/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.icon.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.icon.GoodsInformationIconDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsInformationIconEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.icon.GoodsInformationIconListOrderDisplayUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.icon.AbstractGoodsInformationIconEditService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.icon.GoodsInformationIconListUpdateService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * アイコンリスト更新
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
@Service
public class GoodsInformationIconListUpdateServiceImpl extends AbstractGoodsInformationIconEditService
                implements GoodsInformationIconListUpdateService {

    /**
     * アイコン表示順更新
     */
    private final GoodsInformationIconListOrderDisplayUpdateLogic goodsInformationIconListOrderDisplayUpdateLogic;

    @Autowired
    public GoodsInformationIconListUpdateServiceImpl(GoodsInformationIconListOrderDisplayUpdateLogic goodsInformationIconListOrderDisplayUpdateLogic) {
        this.goodsInformationIconListOrderDisplayUpdateLogic = goodsInformationIconListOrderDisplayUpdateLogic;
    }

    /**
     * アイコンリスト更新
     *
     * @param iconDtoList アイコンDtoリスト
     * @return 処理件数
     */
    @Override
    public int execute(List<GoodsInformationIconDto> iconDtoList) {
        // パラメータチェック
        this.paramCheck(iconDtoList);

        // 更新処理
        int updateCount = this.updateIconDtoList(iconDtoList);

        return updateCount;
    }

    /**
     * 更新処理
     *
     * @param iconDtoList アイコンDtoリスト
     * @return 処理件数
     */
    protected int updateIconDtoList(List<GoodsInformationIconDto> iconDtoList) {

        List<GoodsInformationIconEntity> entity = this.createEntity(iconDtoList);

        int result = goodsInformationIconListOrderDisplayUpdateLogic.execute(entity);

        return result;
    }

    /**
     * 商品インフォメーションアイコンエンティティリストを作成
     *
     * @param iconDtoList アイコンDtoリスト
     * @return 商品インフォメーションアイコンエンティティリスト
     */
    protected List<GoodsInformationIconEntity> createEntity(List<GoodsInformationIconDto> iconDtoList) {
        List<GoodsInformationIconEntity> entityList = new ArrayList<>();
        for (GoodsInformationIconDto goodsInformationIconDto : iconDtoList) {
            entityList.add(goodsInformationIconDto.getGoodsInformationIconEntity());
        }

        return entityList;
    }

    /**
     * パラメータチェック
     *
     * @param iconDtoList アイコンDtoリスト
     */
    protected void paramCheck(List<GoodsInformationIconDto> iconDtoList) {

        ArgumentCheckUtil.assertNotNull("iconDtoList", iconDtoList);
        for (GoodsInformationIconDto goodsInformationIconDto : iconDtoList) {
            GoodsInformationIconEntity entity = goodsInformationIconDto.getGoodsInformationIconEntity();

            ArgumentCheckUtil.assertNotNull("entity", entity);

            ArgumentCheckUtil.assertGreaterThanZero("iconSeq", entity.getIconSeq());
            ArgumentCheckUtil.assertGreaterThanZero("shopSeq", entity.getShopSeq());
            ArgumentCheckUtil.assertNotEmpty("iconName", entity.getIconName());
            ArgumentCheckUtil.assertNotEmpty("colorCode", entity.getColorCode());
        }
    }
}
