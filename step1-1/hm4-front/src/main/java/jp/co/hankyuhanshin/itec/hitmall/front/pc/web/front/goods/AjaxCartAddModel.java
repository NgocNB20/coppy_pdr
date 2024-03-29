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
 * カート追加（Ajax）Modelクラス<br/>
 *
 * @author kaneda
 */
@Data
public class AjaxCartAddModel extends AbstractModel {

    /**
     * 商品コード
     */
    private String gcd;

    /**
     * 商品数量
     */
    private String gcnt;
}
