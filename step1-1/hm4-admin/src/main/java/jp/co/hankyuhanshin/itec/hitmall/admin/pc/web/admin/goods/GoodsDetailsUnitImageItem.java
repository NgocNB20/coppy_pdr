/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUnitImageFlag;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 商品管理：商品詳細ページ 商品規格画像アイテム<br/>
 *
 * @author thang
 */
@Data
@Component
@Scope("prototype")
public class GoodsDetailsUnitImageItem implements Serializable {

    /**
     * シリアルバージョンID<br/>
     */
    private static final long serialVersionUID = 1L;

    /************************************
     ** 商品規格項目
     ************************************/
    /**
     * No<br/>
     */
    private Integer imageDspNo;

    /**
     * 商品SEQ<br/>
     */
    private Integer goodsSeq;

    /**
     * 商品コード<br/>
     */
    private String goodsCode;

    /**
     * 規格画像パス<br/>
     */
    private String unitImagePath;

    // 2023-renew No76 from here
    /**
     * 規格画像有無
     */
    private HTypeUnitImageFlag unitImageFlag;
    // 2023-renew No76 to here

    /**
     * 商品画像有無判定<br/>
     *
     * @return true=画像パスがある場合
     */
    public boolean isExistImage() {
        return !StringUtils.isEmpty(unitImagePath);
    }

    // 2023-renew No76 from here

    /**
     * 規格画像有無判定
     *
     * @return true:規格画像あり false:規格画像なし
     */
    public boolean isValidUnitImage() {
        return HTypeUnitImageFlag.ON.equals(this.unitImageFlag);
    }

    // 2023-renew No76 to here
}
