// 2023-renew No65 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.restockannounce;

import jp.co.hankyuhanshin.itec.hitmall.front.base.constant.ValidatorConstants;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.GoodsStockItem;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * 入荷お知らせ一覧 Model
 *
 * @author hung.leviet
 * @version $Revision: 1.0 $
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class MemberRestockAnnounceModel extends AbstractModel {

    /**
     * 削除か追加を判断するモード（URLパラメータ）
     */
    private String md;

    /**
     * 入荷お知らせ一覧情報
     */
    private List<GoodsStockItem> restockAnnounceItems;

    /**
     * 商品コード
     */
    @Pattern(regexp = ValidatorConstants.REGEX_GOODS_CODE, message = "{PDR-0002-001-A-W}")
    private String gcd;
    
    private boolean saleIconFlag;

    private boolean reserveIconFlag;

    private boolean newIconFlag;

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
     * 一覧情報の空判定<br/>
     *
     * @return 一覧情報の空判定
     */
    public boolean isRestockAnnounceEmpty() {

        if (restockAnnounceItems == null) {
            return true;
        }
        return restockAnnounceItems.isEmpty();
    }

}
// 2023-renew No65 to here
