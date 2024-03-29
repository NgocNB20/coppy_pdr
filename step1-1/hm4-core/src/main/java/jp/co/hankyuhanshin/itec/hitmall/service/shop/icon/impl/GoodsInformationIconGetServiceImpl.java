/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.icon.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.icon.GoodsInformationIconDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.icon.GoodsInformationIconDtoGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.icon.GoodsInformationIconGetService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * アイコン取得
 *
 * @author shibuya
 * @version $Revision: 1.2 $
 */
@Service
public class GoodsInformationIconGetServiceImpl extends AbstractShopService implements GoodsInformationIconGetService {

    /**
     * アイコン詳細取得
     */
    private final GoodsInformationIconDtoGetLogic goodsInformationIconDtoGetLogic;

    @Autowired
    public GoodsInformationIconGetServiceImpl(GoodsInformationIconDtoGetLogic goodsInformationIconDtoGetLogic) {
        this.goodsInformationIconDtoGetLogic = goodsInformationIconDtoGetLogic;
    }

    /**
     * アイコンDtoを取得
     *
     * @param iconSeq アイコンSEQ
     * @return アイコンDto
     */
    @Override
    public GoodsInformationIconDto execute(Integer iconSeq) {

        // パラメータチェック
        this.checkParam(iconSeq);

        // 共通情報の取得
        Integer shopSeq = 1001;

        // アイコンDto取得
        GoodsInformationIconDto iconDto = goodsInformationIconDtoGetLogic.execute(iconSeq, shopSeq);
        if (iconDto.getGoodsInformationIconEntity() == null) {
            throwMessage(MSGCD_GOODSINFORMATIONICON_GET_FAIL);
        }

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
