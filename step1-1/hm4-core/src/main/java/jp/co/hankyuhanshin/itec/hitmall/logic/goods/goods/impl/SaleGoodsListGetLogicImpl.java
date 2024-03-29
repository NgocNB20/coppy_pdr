// 2023-renew No65 from here
package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.sale.SaleGoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.salegoods.SaleGoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.SaleGoodsListGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * セール商品ロジック<br/>
 *
 * @author Thang Doan (VJP)
 */
@Component
public class SaleGoodsListGetLogicImpl implements SaleGoodsListGetLogic {

    /**
     * セール商品Dao
     */
    private final SaleGoodsDao saleGoodsDao;

    @Autowired
    public SaleGoodsListGetLogicImpl(SaleGoodsDao saleGoodsDao) {
        this.saleGoodsDao = saleGoodsDao;
    }

    @Override
    public List<SaleGoodsDto> execute(List<Integer> goodsSeqList) {
        if (CollectionUtil.isEmpty(goodsSeqList)) {
            return new ArrayList<>();
        }
        return saleGoodsDao.getSaleGoodsList(goodsSeqList);
    }
}
// 2023-renew No65 to here