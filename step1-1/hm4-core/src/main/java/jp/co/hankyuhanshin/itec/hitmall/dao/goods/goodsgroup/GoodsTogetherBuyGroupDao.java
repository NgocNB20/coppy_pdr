// 2023-renew No21 from here
package jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsTogetherBuyGroupEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

/**
 * よく一緒に購入される商品Daoクラス
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Dao
@ConfigAutowireable
public interface GoodsTogetherBuyGroupDao {

    /**
     * インサート
     *
     * @param goodsTogetherBuyGroupEntity よく一緒に購入される商品エンティティ
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(GoodsTogetherBuyGroupEntity goodsTogetherBuyGroupEntity);

    /**
     * アップデート
     *
     * @param goodsTogetherBuyGroupEntity よく一緒に購入される商品エンティティ
     * @return 処理件数
     */
    @Update
    int update(GoodsTogetherBuyGroupEntity goodsTogetherBuyGroupEntity);

    /**
     * デリート
     *
     * @param goodsTogetherBuyGroupEntity よく一緒に購入される商品エンティティ
     * @return 処理件数
     */
    @Delete
    int delete(GoodsTogetherBuyGroupEntity goodsTogetherBuyGroupEntity);

    /**
     * エンティティ取得
     *
     * @param goodsGroupSeq         商品グループSEQ
     * @param goodsTogetherBuyGroupSeq よく一緒に購入されている商品グループSEQ
     * @return よく一緒に購入されている商品エンティティ
     */
    @Select
    GoodsTogetherBuyGroupEntity getEntity(Integer goodsGroupSeq, Integer goodsTogetherBuyGroupSeq);

    /**
     * よく一緒に購入されている商品DTOリスト取得<br />
     * 指定した商品グループのよく一緒に購入されている商品DTO
     *
     * @param goodsGroupSeq 商品グループSEQ
     * @return よく一緒に購入されている商品エンティティリスト
     */
    @Select
    List<GoodsTogetherBuyGroupEntity> getGoodsTogetherBuyGroupListByGoodsGroupSeq(Integer goodsGroupSeq);

    /**
     * デリート
     *
     * @param registMethod 登録方法
     * @return 削除件数
     */
    @Delete(sqlFile = true)
    int deleteByRegistMethod(String registMethod);

    /**
     * よく一緒に購入される商品 ステータス別に購入DTO
     *
     * @param goodsGroupSeq 商品グループSEQ
     * @param goodsOpenStatusPc 公開状態PC
     * @param excludingFlag よ除外フラグ
     * @param saleStatusPc 販売状態PC
     * @return まとめて購入グループエンティティの商品リスト
     */
    @Select
    List<GoodsTogetherBuyGroupEntity> getGoodsTogetherByStatus(Integer goodsGroupSeq,
                                                               String goodsOpenStatusPc,
                                                               String excludingFlag,
                                                               String saleStatusPc);
}
// 2023-renew No21 to here
