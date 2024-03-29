/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.icon.impl;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsInformationIconEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.icon.GoodsInformationIconDeleteLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.icon.GoodsInformationIconLockLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.icon.AbstractGoodsInformationIconEditService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.icon.GoodsInformationIconDeleteService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * アイコン更新
 *
 * @author shibuya
 * @author Kaneko (itec) 2012/08/16 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Service
public class GoodsInformationIconDeleteServiceImpl extends AbstractGoodsInformationIconEditService
                implements GoodsInformationIconDeleteService {

    /**
     * アイコン行ロック
     */
    private final GoodsInformationIconLockLogic goodsInformationIconLockLogic;
    /**
     * アイコン削除Logic
     */
    private final GoodsInformationIconDeleteLogic goodsInformationIconDeleteLogic;

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsInformationIconDeleteServiceImpl.class);

    @Autowired
    public GoodsInformationIconDeleteServiceImpl(GoodsInformationIconLockLogic goodsInformationIconLockLogic,
                                                 GoodsInformationIconDeleteLogic goodsInformationIconDeleteLogic) {
        this.goodsInformationIconLockLogic = goodsInformationIconLockLogic;
        this.goodsInformationIconDeleteLogic = goodsInformationIconDeleteLogic;
    }

    /**
     * アイコン更新処理
     *
     * @param iconEntity 商品インフォメーションアイコンエンティティ
     * @return 処理結果マップ
     */
    @Override
    public int execute(GoodsInformationIconEntity iconEntity) {
        // パラメータチェック
        this.paramCheck(iconEntity);

        // 対象アイコンのアイコンSEQ
        Integer iconSeq = iconEntity.getIconSeq();

        // ロック
        goodsInformationIconLockLogic.execute(iconSeq);

        // 削除処理
        int deleteCount = this.deleteIconEntity(iconEntity);

        return deleteCount;
    }

    /**
     * アイコン削除
     *
     * @param iconEntity 商品インフォメーションアイコンエンティティ
     * @return 処理件数
     */
    protected int deleteIconEntity(GoodsInformationIconEntity iconEntity) {
        int result = goodsInformationIconDeleteLogic.execute(iconEntity);

        return result;
    }

    /**
     * パラメータチェック
     *
     * @param iconEntity 商品インフォメーションアイコン
     */
    protected void paramCheck(GoodsInformationIconEntity iconEntity) {

        ArgumentCheckUtil.assertNotNull("iconEntity", iconEntity);
        ArgumentCheckUtil.assertGreaterThanZero("iconSeq", iconEntity.getIconSeq());
        ArgumentCheckUtil.assertGreaterThanZero("shopSeq", iconEntity.getShopSeq());
        ArgumentCheckUtil.assertNotEmpty("iconName", iconEntity.getIconName());
        ArgumentCheckUtil.assertNotEmpty("iconColorCode", iconEntity.getColorCode());

    }
}
