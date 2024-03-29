/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.favorite.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.favorite.FavoriteDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.favorite.FavoriteEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.favorite.FavoriteDataCheckLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * お気に入りデータチェックロジック<br/>
 *
 * @author ueshima
 * @version $Revision: 1.4 $
 */
@Component
public class FavoriteDataCheckLogicImpl extends AbstractShopLogic implements FavoriteDataCheckLogic {

    /**
     * お気に入りDao
     */
    private final FavoriteDao favoriteDao;

    @Autowired
    public FavoriteDataCheckLogicImpl(FavoriteDao favoriteDao) {
        this.favoriteDao = favoriteDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param favoriteEntity お気に入りエンティティ
     */
    @Override
    public void execute(FavoriteEntity favoriteEntity) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("favoriteEntity", favoriteEntity);

        // お気に入り商品の重複確認
        List<Integer> goodsSeqList = favoriteDao.getGoodsSeqList(favoriteEntity.getMemberInfoSeq());

        // お気に入り登録されていない場合
        if (!goodsSeqList.contains(favoriteEntity.getGoodsSeq())) {
            goodsSeqList.add(favoriteEntity.getGoodsSeq());
        }

        // お気に入り最大登録数超過チェック
        int favoriteGoodsMax = Integer.parseInt(PropertiesUtil.getSystemPropertiesValue("favorite.goods.max"));
        if (goodsSeqList.size() > favoriteGoodsMax) {
            addErrorMessage(MSGCD_FAVORITE_GOODS_MAX_COUNT_FAIL, new Object[] {favoriteGoodsMax});
        }

        // 例外出力
        if (hasErrorList()) {
            throwMessage();
        }
    }

    @Override
    public List<FavoriteEntity> getFavoriteEntityListForGoods(Integer memberInfoSeq, List<GoodsDto> goodsDtoList) {

        if (memberInfoSeq == null || memberInfoSeq == 0) {
            return null;
        }
        if (CollectionUtil.isEmpty(goodsDtoList)) {
            return null;
        }

        List<Integer> goodsSeqList = new ArrayList<>();
        for (GoodsDto goodsDto : goodsDtoList) {
            goodsSeqList.add(goodsDto.getGoodsEntity().getGoodsSeq());
        }

        return favoriteDao.getFavoriteEntityList(memberInfoSeq, goodsSeqList);
    }

    @Override
    public List<FavoriteEntity> getFavoriteEntityListForCart(Integer memberInfoSeq, CartDto cartDto) {

        if (memberInfoSeq == null || memberInfoSeq == 0) {
            return null;
        }
        if (CollectionUtil.isEmpty(cartDto.getCartGoodsDtoList())) {
            return null;
        }

        List<Integer> goodsSeqList = new ArrayList<>();
        for (CartGoodsDto cartGoodsDto : cartDto.getCartGoodsDtoList()) {
            goodsSeqList.add(cartGoodsDto.getGoodsDetailsDto().getGoodsSeq());
        }

        return favoriteDao.getFavoriteEntityList(memberInfoSeq, goodsSeqList);
    }
}
