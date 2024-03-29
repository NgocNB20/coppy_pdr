// 2023-renew AddNo5 from here
package jp.co.hankyuhanshin.itec.hitmall.service.goods.group.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.group.GoodsGroupListGetService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsGroupListGetServiceImpl extends AbstractShopService implements GoodsGroupListGetService {

    /**
     * 商品グループ情報取得ロジッククラス
     */
    private final GoodsGroupListGetLogic goodsGroupListGetLogic;

    @Autowired
    public GoodsGroupListGetServiceImpl(GoodsGroupListGetLogic goodsGroupListGetLogic) {
        this.goodsGroupListGetLogic = goodsGroupListGetLogic;
    }

    /**
     * 商品グループ情報リスト取得
     *
     * @param goodsGroupCodes 商品グループコードリスト
     * @return
     */
    @Override
    public List<GoodsGroupDto> execute(List<String> goodsGroupCodes) {
        ArgumentCheckUtil.assertNotEmpty("goodsGroupCodes", goodsGroupCodes);
        return goodsGroupListGetLogic.execute(goodsGroupCodes);
    }
}
// 2023-renew AddNo5 to here
