/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.cart;

import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractModel;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.List;

/**
 * 一括注文画面 Model
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
// PDR Migrate Customization from here
public class CatalogModel extends AbstractModel {

    /**
     * 一括注文商品リスト
     */
    @Valid
    private List<CatalogModelItem> catalogItems;

    /**
     * スクロール位置記憶
     */
    private String position;
    
    // 2023-renew No2 from here
    /**
     * カートインボタンをイン処理
     */
    private Integer isAddCartGoods;
    // 2023-renew No2 to here

}
// PDR Migrate Customization to here