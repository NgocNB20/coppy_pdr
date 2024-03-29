/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods;

import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractModel;
import lombok.Data;

/**
 * ユニサーチ商品取得（Ajax）Modelクラス<br/>
 *
 * @author tk32120
 */
@Data
public class AjaxUkUniSearchGoodsGetModel extends AbstractModel {
    /**
     * カテゴリーID
     */
    private String cid;
}
