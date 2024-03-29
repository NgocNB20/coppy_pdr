// 2023-renew No21 from here
package jp.co.hankyuhanshin.itec.hitmall.logic.goods.togetherbuy;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsTogetherBuyGroupEntity;

import java.util.List;

/**
 * よく一緒に購入される商品グループリストを取得し、フロントロジックを取得します
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
public interface GoodsTogetherBuyGroupListGetForFrontLogic {

    /**
     * 実行する
     *
     * @param goodsGroupSeq 商品グループSEQ
     * @param goodsOpenStatusPc 公開状態PC
     * @param excludingFlag よ除外フラグ
     * @param saleStatusPc 販売状態PC
     * @return まとめて購入グループエンティティの商品リスト
     */
    List<GoodsTogetherBuyGroupEntity> execute(Integer goodsGroupSeq,
                                              String goodsOpenStatusPc,
                                              String excludingFlag,
                                              String saleStatusPc);
}
// 2023-renew No21 to here
