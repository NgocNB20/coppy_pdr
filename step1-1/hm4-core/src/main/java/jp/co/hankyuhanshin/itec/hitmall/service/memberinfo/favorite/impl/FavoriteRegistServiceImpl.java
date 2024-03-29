/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.favorite.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.favorite.FavoriteEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsDetailsGetByCodeLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.favorite.FavoriteDataCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.favorite.FavoriteRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.favorite.FavoriteUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.favorite.FavoriteRegistService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * お気に入り情報登録サービス<br/>
 *
 * @author ueshima
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Service
public class FavoriteRegistServiceImpl extends AbstractShopService implements FavoriteRegistService {

    /**
     * 商品詳細情報取得（商品コード）ロジック
     */
    private final GoodsDetailsGetByCodeLogic goodsDetailsGetByCodeLogic;

    /**
     * お気に入りデータチェックロジック
     */
    private final FavoriteDataCheckLogic favoriteDataCheckLogic;

    /**
     * お気に入り情報更新ロジック
     */
    private final FavoriteUpdateLogic favoriteUpdateLogic;

    /**
     * お気に入り情報登録ロジック
     */
    private final FavoriteRegistLogic favoriteRegistLogic;

    @Autowired
    public FavoriteRegistServiceImpl(GoodsDetailsGetByCodeLogic goodsDetailsGetByCodeLogic,
                                     FavoriteDataCheckLogic favoriteDataCheckLogic,
                                     FavoriteUpdateLogic favoriteUpdateLogic,
                                     FavoriteRegistLogic favoriteRegistLogic) {
        this.goodsDetailsGetByCodeLogic = goodsDetailsGetByCodeLogic;
        this.favoriteDataCheckLogic = favoriteDataCheckLogic;
        this.favoriteUpdateLogic = favoriteUpdateLogic;
        this.favoriteRegistLogic = favoriteRegistLogic;
    }

    /**
     * サービス実行<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @param goodsCode     商品コード
     * @param siteType      サイト区分
     * @return 登録件数
     */
    @Override
    public int execute(Integer memberInfoSeq, String goodsCode, HTypeSiteType siteType) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("memberInfoSeq", memberInfoSeq);
        ArgumentCheckUtil.assertNotNull("goodsCode", goodsCode);
        ArgumentCheckUtil.assertNotNull("siteType", siteType);

        // 商品検索
        // 公開状態＝公開
        GoodsDetailsDto goodsDetailsDto =
                        goodsDetailsGetByCodeLogic.execute(1001, goodsCode, siteType, HTypeOpenDeleteStatus.OPEN);

        if (goodsDetailsDto == null) {
            // 商品不在エラー
            throwMessage(MSGCD_GOODS_NOT_EXIST);
        }

        // エンティティの作成
        FavoriteEntity favoriteEntity = getComponent(FavoriteEntity.class);
        favoriteEntity.setMemberInfoSeq(memberInfoSeq);
        favoriteEntity.setGoodsSeq(goodsDetailsDto.getGoodsSeq());

        // 既存データ確認
        favoriteDataCheckLogic.execute(favoriteEntity);

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // お気に入り更新処理
        favoriteEntity.setRegistTime(dateUtility.getCurrentTime());
        int result = favoriteUpdateLogic.execute(favoriteEntity);
        if (result == 0) {

            // お気に入り登録処理
            result = favoriteRegistLogic.execute(favoriteEntity);

            // 両方失敗の場合
            if (result == 0) {
                throwMessage(MSGCD_FAVORITE_GOODS_REGIST_FAIL);
            }
        }
        return result;
    }
}
