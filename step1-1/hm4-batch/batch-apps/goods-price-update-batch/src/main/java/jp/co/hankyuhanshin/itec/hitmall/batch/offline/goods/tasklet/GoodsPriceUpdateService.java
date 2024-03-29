package jp.co.hankyuhanshin.itec.hitmall.batch.offline.goods.tasklet;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsPriceInfoUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupDisplayUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupUpdateLogic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商品価格更新サービス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Service
public class GoodsPriceUpdateService {

    /**
     * 商品情報更新ロジック
     */
    private final GoodsPriceInfoUpdateLogic goodsPriceInfoUpdateLogic;
    /**
     * 商品グループ更新
     */
    private final GoodsGroupUpdateLogic goodsGroupUpdateLogic;
    /**
     * 商品グループ表示更新
     */
    private final GoodsGroupDisplayUpdateLogic goodsGroupDisplayUpdateLogic;

    /**
     * コンストラクター
     *
     * @param goodsPriceInfoUpdateLogic    商品情報更新ロジック
     * @param goodsGroupUpdateLogic        商品グループ更新
     * @param goodsGroupDisplayUpdateLogic 商品グループ表示更新
     */
    public GoodsPriceUpdateService(GoodsPriceInfoUpdateLogic goodsPriceInfoUpdateLogic,
                                   GoodsGroupUpdateLogic goodsGroupUpdateLogic,
                                   GoodsGroupDisplayUpdateLogic goodsGroupDisplayUpdateLogic) {
        this.goodsPriceInfoUpdateLogic = goodsPriceInfoUpdateLogic;
        this.goodsGroupUpdateLogic = goodsGroupUpdateLogic;
        this.goodsGroupDisplayUpdateLogic = goodsGroupDisplayUpdateLogic;
    }

    /**
     * 商品テーブル更新
     *
     * @param entity 商品エンティティ
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void goodsUpdate(GoodsEntity entity) {
        goodsPriceInfoUpdateLogic.execute(entity);
    }

    /**
     * 商品グループ・商品グループ表示テーブル更新
     *
     * @param goodsGroupDto 商品グループDto
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void goodsGroupUpdate(GoodsGroupDto goodsGroupDto) {
        // 商品グループテーブル更新
        goodsGroupUpdateLogic.execute(goodsGroupDto.getGoodsGroupEntity());
        // 商品グループ表示テーブル更新
        goodsGroupDisplayUpdateLogic.execute(goodsGroupDto.getGoodsGroupDisplayEntity());
    }

}
