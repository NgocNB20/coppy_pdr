/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.icon.impl;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsInformationIconEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.icon.GoodsInformationIconRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.icon.AbstractGoodsInformationIconEditService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.icon.GoodsInformationIconRegistService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * アイコン登録
 *
 * @author shibuya
 * @author Kaneko (itec) 2012/08/16 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Service
public class GoodsInformationIconRegistServiceImpl extends AbstractGoodsInformationIconEditService
                implements GoodsInformationIconRegistService {

    /**
     * アイコン登録Logic
     */
    private final GoodsInformationIconRegistLogic goodsInformationIconRegistLogic;

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsInformationIconRegistServiceImpl.class);

    @Autowired
    public GoodsInformationIconRegistServiceImpl(GoodsInformationIconRegistLogic goodsInformationIconRegistLogic) {
        this.goodsInformationIconRegistLogic = goodsInformationIconRegistLogic;
    }

    /**
     * アイコン登録
     *
     * @param iconEntity 商品インフォメーションアイコンエンティティ
     * @return 処理結果マップ
     */
    @Override
    public int execute(GoodsInformationIconEntity iconEntity) {
        // パラメータチェック
        // this.paramCheck(iconEntity, iconRegistUpdateList);
        this.paramCheck(iconEntity);
        // 共通情報チェック
        Integer shopSeq = 1001;

        // 共通情報セット
        iconEntity.setShopSeq(shopSeq);

        // 登録処理
        int resultCntIconEntity = this.registIconEntity(iconEntity);

        return resultCntIconEntity;
    }

    /**
     * 商品インフォメーションアイコン登録
     *
     * @param iconEntity 商品インフォメーションアイコンエンティティ
     * @return 登録されたアイコン
     */
    protected int registIconEntity(GoodsInformationIconEntity iconEntity) {
        int result = goodsInformationIconRegistLogic.execute(iconEntity);
        if (result == 0) {
            throwMessage(MSGCD_GOODSINFORMATIONICON_REGIST_FAIL);
        }
        return result;
    }

    /**
     * パラメータチェック
     *
     * @param iconEntity 商品インフォメーションアイコン
     */
    protected void paramCheck(GoodsInformationIconEntity iconEntity) {

        ArgumentCheckUtil.assertNotNull("iconEntity", iconEntity);
        ArgumentCheckUtil.assertNotEmpty("iconName", iconEntity.getIconName());
        ArgumentCheckUtil.assertNotEmpty("iconColorCode", iconEntity.getColorCode());
    }
}
