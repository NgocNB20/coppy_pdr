package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.salegoods.SaleGoodsDto;

import java.util.List;

/**
 * セール商品ロジック
 *
 * @author Thang Doan (VJP)
 */
public interface SaleGoodsListGetLogic {

    List<SaleGoodsDto> execute(List<Integer> goodsSeqList);
}
