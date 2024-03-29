/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.favorite;

import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.HCNumber;
import jp.co.hankyuhanshin.itec.hitmall.front.base.constant.ValidatorConstants;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.GoodsStockItem;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * 会員お気に入り Model
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class MemberFavoriteModel extends AbstractModel {

    // PDR Migrate Customization from here
    /**
     * 商品番号 正規表現エラー
     * <code>MSGCD_REGEX_GOODS_CODE_FAIL</code>
     */
    public static final String MSGCD_REGEX_GOODS_CODE_FAIL = "{PDR-0002-001-A-W}";
    // PDR Migrate Customization to here

    /**
     * 一覧情報
     */

    /**
     * 削除か追加を判断するモード（URLパラメータ）
     */
    private String md;

    /**
     * お気に入り一覧情報
     */
    // 2023-renew No11 from here
    private List<GoodsStockItem> favoriteItems;
    // 2023-renew No11 to here

    /**
     * 商品コード
     */
    // PDR Migrate Customization from here
    @Pattern(regexp = ValidatorConstants.REGEX_GOODS_CODE, message = MSGCD_REGEX_GOODS_CODE_FAIL)
    // PDR Migrate Customization to here
    private String gcd;

    private String ggcd;

    /**
     * カートからの遷移
     */
    private boolean fromCart;

    /**
     * 商品詳細からの遷移
     */
    private boolean fromGoodsDetails;

    private boolean isPathGgcdFromGoodsDetails;

    // PDR Migrate Customization from here
    /**
     * お気に入り一覧インデックス
     */
    @HCNumber
    public int favoriteIndex;

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

    // 2023-renew No11 from here
    /**
     * アウトレットアイコンのパスを取得する<br/>
     *
     * @return アウトレットアイコンのパス
     */
    public String getOutletIconSrc() {
        return PropertiesUtil.getSystemPropertiesValue("images.icon.path.outlet");
    }
    // 2023-renew No11 from here
    // PDR Migrate Customization to here

    /**
     * 一覧情報の空判定<br/>
     *
     * @return 一覧情報の空判定
     */
    public boolean isFavoriteEmpty() {

        if (favoriteItems == null) {
            return true;
        }
        return favoriteItems.isEmpty();
    }

}
