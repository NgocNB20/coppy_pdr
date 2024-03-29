// 2023-renew No65 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.restockannounce.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.restockannounce.RestockAnnounceEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsEntityGetByGoodsCodeLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.restockannounce.RestockAnnounceDataCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.restockannounce.RestockAnnounceRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.restockannounce.RestockAnnounceRegistService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 入荷お知らせ情報登録サービス<br/>
 *
 * @author Thang Doan (VJP)
 */
@Service
public class RestockAnnounceRegistServiceImpl extends AbstractShopService implements RestockAnnounceRegistService {

    /**
     * 商品エンティティ取得ロジック
     */
    private final GoodsEntityGetByGoodsCodeLogic goodsEntityGetByGoodsCodeLogic;

    /**
     * 入荷お知らせデータチェックロジック
     */
    private final RestockAnnounceDataCheckLogic restockAnnounceDataCheckLogic;
    /**
     * 入荷お知らせ情報登録ロジック
     */
    private final RestockAnnounceRegistLogic restockAnnounceRegistLogic;

    /**
     * コンストラクタ
     */
    @Autowired
    public RestockAnnounceRegistServiceImpl(GoodsEntityGetByGoodsCodeLogic goodsEntityGetByGoodsCodeLogic,
                                            RestockAnnounceDataCheckLogic restockAnnounceDataCheckLogic,
                                            RestockAnnounceRegistLogic restockAnnounceRegistLogic) {
        this.goodsEntityGetByGoodsCodeLogic = goodsEntityGetByGoodsCodeLogic;
        this.restockAnnounceDataCheckLogic = restockAnnounceDataCheckLogic;
        this.restockAnnounceRegistLogic = restockAnnounceRegistLogic;
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

        // 商品エンティティ検索
        GoodsEntity goodsEntity = goodsEntityGetByGoodsCodeLogic.execute(1001, goodsCode);
        if (goodsEntity == null) {
            // 商品不在エラー
            throwMessage(MSGCD_GOODS_NOT_EXIST);
        }

        // エンティティの作成
        RestockAnnounceEntity restockAnnounceEntity = getComponent(RestockAnnounceEntity.class);
        restockAnnounceEntity.setMemberInfoSeq(memberInfoSeq);
        restockAnnounceEntity.setGoodsSeq(goodsEntity.getGoodsSeq());

        // 既存データ確認
        restockAnnounceDataCheckLogic.execute(restockAnnounceEntity);

        // 登録若しくは更新を行う
        int result = restockAnnounceRegistLogic.execute(restockAnnounceEntity);
        if (result == 0) {
            // 登録エラー
            throwMessage(RestockAnnounceRegistService.MSGCD_RESTOCK_ANNOUNCE_GOODS_REGIST_FAIL);
        }

        return result;
    }
}
// 2023-renew No65 to here
