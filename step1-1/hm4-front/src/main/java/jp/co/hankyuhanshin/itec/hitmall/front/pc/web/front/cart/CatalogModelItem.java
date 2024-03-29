/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.cart;

import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.HCHankaku;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.HCNumber;
import jp.co.hankyuhanshin.itec.hitmall.front.base.constant.ValidatorConstants;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.cart.validation.group.DoAddCartGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.cart.validation.group.DoGetGoodsInfoGroup;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

/**
 * 一括注文画面アイテム Model
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
// PDR Migrate Customization from here
public class CatalogModelItem implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 一括注文商品リストインデックス
     */
    private int catalogIndex;

    /**
     * 商品番号
     */
    @Pattern(regexp = ValidatorConstants.REGEX_GOODS_CODE, message = "{PDR-0002-001-A-W}",
             groups = {DoGetGoodsInfoGroup.class, DoAddCartGroup.class})
    @Length(max = ValidatorConstants.LENGTH_GOODS_CODE_MAXIMUM,
            groups = {DoGetGoodsInfoGroup.class, DoAddCartGroup.class})
    @HCHankaku
    private String goodsCode;

    /**
     * 商品Seq
     */
    private Integer goodsSeq;

    /**
     * 商品名・記号
     */
    private String namePc;

    /**
     * 注文数
     */
    @HCNumber
    private String orderQuantity;

    /**
     * 規格タイトル1
     */
    private String unitTitle1;

    /**
     * 規格値1
     */
    private String unitValue1;

    /**
     * 規格タイトル2
     */
    private String unitTitle2;

    /**
     * 規格値2
     */
    private String unitValue2;

    /**
     * 商品グループSeq
     */
    private Integer goodsGroupSeq;

    // 2023-renew No2 from here
    /**
     * 商品画像アイテム
     */
    private List<String> goodsImageItems;

    /**
     * 商品グループ画像をセット（サムネイル画像）
     *
     * @return goodsName
     */
    public String getGoodsGroupImageThumbnailAlt() {
        String name = null;
        if (StringUtils.isNotEmpty(namePc)) {
            name = namePc;
        }
        return name;
    }
    
    /**
     * 規格1の有無判定
     *
     * @return true=有、false=無
     */
    public boolean isGoodsImageItems() {
        return !CollectionUtils.isEmpty(goodsImageItems);
    }
    // 2023-renew No2 to here
    /**
     * 規格1の有無判定
     *
     * @return true=有、false=無
     */
    public boolean isUnit1() {
        return StringUtils.isNotEmpty(unitTitle1);
    }

    /**
     * 規格2の有無判定
     *
     * @return true=有、false=無
     */
    public boolean isUnit2() {
        return StringUtils.isNotEmpty(unitTitle2);
    }

    /**
     * 一括注文商品リストインデックスを取得する
     *
     * @return 一括注文商品リストインデックス
     */
    public int getRowNumber() {
        return catalogIndex + 1;
    }

}
// PDR Migrate Customization to here
