/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsInformationIconDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.icon.GoodsInformationIconDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsInformationIconGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品アイコン表示情報リストを取得する。<br/>
 *
 * @author ozaki
 * @version $Revision: 1.4 $
 */
@Component
public class GoodsInformationIconGetLogicImpl extends AbstractShopLogic implements GoodsInformationIconGetLogic {

    /**
     * 商品インフォメーションDAO
     */
    private final GoodsInformationIconDao goodsInformationIconDao;

    @Autowired
    public GoodsInformationIconGetLogicImpl(GoodsInformationIconDao goodsInformationIconDao) {
        this.goodsInformationIconDao = goodsInformationIconDao;
    }

    /**
     * 商品インフォメーションアイコン表示情報リスト取得<br/>
     * 商品インフォメーションアイコン表示情報エンティティ情報のリストを取得する<br/>
     *
     * @param informationIconSeqList 対象商品インフォメーションアイコンリスト
     * @return 表示情報
     */
    @Override
    public List<GoodsInformationIconDetailsDto> execute(List<Integer> informationIconSeqList) {

        // (1) パラメータチェック
        // infomationIconSeqListがnull又はサイズがゼロでないかをチェック
        ArgumentCheckUtil.assertNotEmpty("informationIconSeqList", informationIconSeqList);

        // (2) 商品グループ情報取得
        // （パラメータ）ショップSEQ、（パラメータ）商品グループコードをもとに、商品グループエンティティを取得する
        // 商品グループDaoの商品グループ取得処理を実行する。
        List<GoodsInformationIconDetailsDto> goodsInformationIconDtoList =
                        goodsInformationIconDao.getInformationIconList(informationIconSeqList);
        if (goodsInformationIconDtoList == null) {
            goodsInformationIconDtoList = new ArrayList<>();
        }

        return goodsInformationIconDtoList;
    }
}
