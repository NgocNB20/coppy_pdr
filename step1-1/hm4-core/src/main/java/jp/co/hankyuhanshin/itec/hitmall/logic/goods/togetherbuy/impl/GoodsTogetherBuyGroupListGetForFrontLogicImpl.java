// 2023-renew No21 from here
package jp.co.hankyuhanshin.itec.hitmall.logic.goods.togetherbuy.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsTogetherBuyGroupDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsTogetherBuyGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.togetherbuy.GoodsTogetherBuyGroupListGetForFrontLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 商品を一緒に購入する グループリストを取得する フロントロジックを実装する
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Component
public class GoodsTogetherBuyGroupListGetForFrontLogicImpl implements GoodsTogetherBuyGroupListGetForFrontLogic {

    /**
     * よく一緒に購入される商品Daoクラス
     */
    private final GoodsTogetherBuyGroupDao goodsTogetherBuyGroupDao;

    @Autowired
    public GoodsTogetherBuyGroupListGetForFrontLogicImpl(GoodsTogetherBuyGroupDao goodsTogetherBuyGroupDao) {
        this.goodsTogetherBuyGroupDao = goodsTogetherBuyGroupDao;
    }

    /**
     * 実行する
     *
     * @param goodsGroupSeq 商品グループSEQ
     * @param goodsOpenStatusPc 公開状態PC
     * @param excludingFlag よ除外フラグ
     * @param saleStatusPc 販売状態PC
     * @return まとめて購入グループエンティティの商品リスト
     */
    @Override
    public List<GoodsTogetherBuyGroupEntity> execute(Integer goodsGroupSeq,
                                                     String goodsOpenStatusPc,
                                                     String excludingFlag,
                                                     String saleStatusPc) {
        return this.goodsTogetherBuyGroupDao.getGoodsTogetherByStatus(
                        goodsGroupSeq, goodsOpenStatusPc, excludingFlag, saleStatusPc);
    }
}

// 2023-renew No21 to here
