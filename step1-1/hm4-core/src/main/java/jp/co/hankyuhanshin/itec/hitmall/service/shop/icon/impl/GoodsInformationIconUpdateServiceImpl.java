/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.icon.impl;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsInformationIconEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.icon.GoodsInformationIconLockLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.icon.GoodsInformationIconUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.icon.AbstractGoodsInformationIconEditService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.icon.GoodsInformationIconUpdateService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.FileOperationUtility;
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
public class GoodsInformationIconUpdateServiceImpl extends AbstractGoodsInformationIconEditService
                implements GoodsInformationIconUpdateService {

    /**
     * アイコン行ロック
     */
    private final GoodsInformationIconLockLogic goodsInformationIconLockLogic;

    /**
     * アイコン更新Logic
     */
    private final GoodsInformationIconUpdateLogic goodsInformationIconUpdateLogic;

    /**
     * ファイル操作Helper
     */
    private final FileOperationUtility fileOperationUtility;

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsInformationIconUpdateServiceImpl.class);

    @Autowired
    public GoodsInformationIconUpdateServiceImpl(GoodsInformationIconLockLogic goodsInformationIconLockLogic,
                                                 GoodsInformationIconUpdateLogic goodsInformationIconUpdateLogic,
                                                 FileOperationUtility fileOperationUtility) {
        this.goodsInformationIconLockLogic = goodsInformationIconLockLogic;
        this.goodsInformationIconUpdateLogic = goodsInformationIconUpdateLogic;
        this.fileOperationUtility = fileOperationUtility;
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

        // 対象アイコンのアイコンSEQを取得
        Integer iconSeq = iconEntity.getIconSeq();

        // 行ロック
        goodsInformationIconLockLogic.execute(iconSeq);

        // 更新処理
        int updateCount = this.updateIconEntity(iconEntity);

        return updateCount;
    }

    /**
     * アイコン更新
     *
     * @param iconEntity 商品インフォメーションアイコンエンティティ
     * @return 処理件数
     */
    protected int updateIconEntity(GoodsInformationIconEntity iconEntity) {
        int result = goodsInformationIconUpdateLogic.execute(iconEntity);
        if (result == 0) {
            throwMessage(MSGCD_GOODSINFORMATIONICON_UPDATE_FAIL);
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
        ArgumentCheckUtil.assertGreaterThanZero("iconSeq", iconEntity.getIconSeq());
        ArgumentCheckUtil.assertGreaterThanZero("shopSeq", iconEntity.getShopSeq());
        ArgumentCheckUtil.assertNotEmpty("iconName", iconEntity.getIconName());
        ArgumentCheckUtil.assertNotEmpty("iconColorCode", iconEntity.getColorCode());
    }
}
