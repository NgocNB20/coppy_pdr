/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.icon.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.icon.GoodsInformationIconDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.icon.GoodsInformationIconDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsInformationIconEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.icon.GoodsInformationIconDetailsDtoListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.icon.GoodsInformationIconListGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * アイコンリスト取得(実装)
 *
 * @author shibuya
 * @version $Revision: 1.3 $
 */
@Service
public class GoodsInformationIconListGetServiceImpl extends AbstractShopService
                implements GoodsInformationIconListGetService {

    /**
     * アイコン詳細Dtoリスト取得ロジック
     */
    private final GoodsInformationIconDetailsDtoListGetLogic goodsInformationIconDetailsDtoListGetLogic;

    @Autowired
    public GoodsInformationIconListGetServiceImpl(GoodsInformationIconDetailsDtoListGetLogic goodsInformationIconDetailsDtoListGetLogic) {
        this.goodsInformationIconDetailsDtoListGetLogic = goodsInformationIconDetailsDtoListGetLogic;
    }

    /**
     * アイコンリスト取得
     *
     * @return 店舗内の全アイコン詳細Dto
     */
    @Override
    public List<GoodsInformationIconDto> execute() {

        // 共通情報の取得
        Integer shopSeq = 1001;

        // ロジックの実行
        List<GoodsInformationIconDetailsDto> resultList = goodsInformationIconDetailsDtoListGetLogic.execute(shopSeq);

        List<GoodsInformationIconDto> iconDtoList = this.createIconDtoList(resultList);

        return iconDtoList;
    }

    /**
     * アイコン詳細Dto → アイコンDto
     *
     * @param resultList DB取得結果
     * @return アイコンDtoリスト
     */
    protected List<GoodsInformationIconDto> createIconDtoList(List<GoodsInformationIconDetailsDto> resultList) {

        List<GoodsInformationIconDto> iconDtoList = new ArrayList<>();

        GoodsInformationIconEntity iconEntity = null;

        GoodsInformationIconDto iconDto = null;
        Integer oldIconSeq = null;
        for (GoodsInformationIconDetailsDto iconDetailsDto : resultList) {
            // アイコンSEQ不一致
            if (!(iconDetailsDto.getIconSeq().equals(oldIconSeq))) {

                if (oldIconSeq != null) {
                    // 初回ループでなければ、前回のアイコン情報をリストに追加
                    iconDto = getComponent(GoodsInformationIconDto.class);
                    iconDto.setGoodsInformationIconEntity(iconEntity);

                    iconDtoList.add(iconDto);
                }

                // 今回のアイコンSEQに紐付くアイコン情報を作成
                iconEntity = getComponent(GoodsInformationIconEntity.class);
                iconEntity.setIconSeq(iconDetailsDto.getIconSeq());
                iconEntity.setShopSeq(iconDetailsDto.getShopSeq());
                iconEntity.setIconName(iconDetailsDto.getIconName());
                iconEntity.setColorCode(iconDetailsDto.getColorCode());
                iconEntity.setOrderDisplay(iconDetailsDto.getOrderDisplay());
            }

            oldIconSeq = iconDetailsDto.getIconSeq();
        }

        // 最後のループ分の情報をセット
        if (iconEntity != null) {
            iconDto = getComponent(GoodsInformationIconDto.class);
            iconDto.setGoodsInformationIconEntity(iconEntity);

            iconDtoList.add(iconDto);
        }

        return iconDtoList;
    }
}
