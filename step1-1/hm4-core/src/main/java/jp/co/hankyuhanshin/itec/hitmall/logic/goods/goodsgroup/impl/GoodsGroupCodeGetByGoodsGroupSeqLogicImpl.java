// PDR Migrate Customization from here
package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsGroupDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupCodeGetByGoodsGroupSeqLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GoodsGroupCodeGetByGoodsGroupSeqLogicImpl extends AbstractShopLogic
                implements GoodsGroupCodeGetByGoodsGroupSeqLogic {

    /** 商品グループDao */
    private final GoodsGroupDao goodsGroupDao;

    @Autowired
    public GoodsGroupCodeGetByGoodsGroupSeqLogicImpl(GoodsGroupDao goodsGroupDao) {
        this.goodsGroupDao = goodsGroupDao;
    }

    /**
     * 商品グループSEQから商品グループコードを取得する<br/>
     *
     * @param goodsGroupSeq 商品グループSEQ
     * @return 商品グループコード
     */
    @Override
    public String execute(Integer goodsGroupSeq) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("goodsGroupSeq", goodsGroupSeq);

        // 商品グループエンティティ取得
        GoodsGroupEntity entity = goodsGroupDao.getEntity(goodsGroupSeq);

        // 商品グループ情報が存在するか
        if (entity == null) {
            return null;
        }

        return entity.getGoodsGroupCode();
    }
}
// PDR Migrate Customization to here
