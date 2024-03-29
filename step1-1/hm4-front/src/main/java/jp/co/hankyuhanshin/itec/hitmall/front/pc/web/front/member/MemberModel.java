/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member;

import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.GoodsStockItem;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractModel;
import lombok.Data;

import java.util.List;

/**
 * 会員メニューModel
 *
 * @author kaneda
 */
@Data
public class MemberModel extends AbstractModel {

    /**
     * 会員氏名(姓)
     */
    private String lastName;

    /**
     * 会員氏名(名)
     */
    private String firstName;

    // 2023-renew No65 from here
    /**
     * 入荷お知らせ情報リスト表示最大件数
     */
    private int restockAnnounceGoodsLimit;

    /**
     * 入荷お知らせ情報リスト
     */
    private List<GoodsStockItem> restockAnnounceGoodsItems;

    /**
     * お気に入り情報リスト表示最大件数
     */
    private int favoriteAnnounceGoodsLimit;

    /**
     * お気に入り情報リスト
     */
    private List<GoodsStockItem> favoriteAnnounceGoodsItems;

    /**
     * お気に入り商品リスト(":"区切り)
     */
    @HVSpecialCharacter(allowPunctuation = true)
    private String restockAnnounceGoodsCodeList;


    /**
     * 入荷お知らせ情報表示チェック<br/>
     *
     * @return true:表示
     */
    public boolean isViewRestockAnnounceGoods() {
        return CollectionUtil.isNotEmpty(restockAnnounceGoodsItems);
    }

    /**
     *  お気に入り情報表示チェック<br/>
     * @return
     */
    public boolean isViewFavoriteAnnounceGoods() {
        return CollectionUtil.isNotEmpty(favoriteAnnounceGoodsItems);
    }

    /**
     * SALEアイコン画像のパスを取得する<br/>
     *
     * @return SALEアイコン画像のパス
     */
    public String getNewIconSrc() {
        return PropertiesUtil.getSystemPropertiesValue("images.icon.path.new");
    }

    /**
     * 新着アイコン画像のパスを取得する<br/>
     *
     * @return 新着アイコン画像のパス
     */
    public String getSaleIconSrc() {
        return PropertiesUtil.getSystemPropertiesValue("images.icon.path.sale");
    }

    /**
     * 取りおき可能アイコンのパスを取得する<br/>
     *
     * @return 取りおき可能アイコンのパス
     */
    public String getReserveIconSrc() {
        return PropertiesUtil.getSystemPropertiesValue("images.icon.path.reserve");
    }

    /**
     * アウトレットアイコンのパスを取得する<br/>
     *
     * @return アウトレットアイコンのパス
     */
    public String getOutletIconSrc() {
        return PropertiesUtil.getSystemPropertiesValue("images.icon.path.outlet");
    }
    // 2023-renew No65 to here
}
