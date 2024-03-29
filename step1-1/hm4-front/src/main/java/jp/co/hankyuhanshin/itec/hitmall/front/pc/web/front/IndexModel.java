/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front;

import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractModel;
import lombok.Data;

import java.util.List;

/**
 * トップ画面 Model
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Data
public class IndexModel extends AbstractModel {

    /**
     * ニュースリスト
     */
    public List<IndexModelItem> newsItems;

    // PDR Migrate Customization from here
    /**
     * 購入済み商品リスト
     */
    public List<IndexModelItem> puchasedGoodsItems;

    // PDR Migrate Customization to here

    /**
     * ニュース存在チェック<br/>
     *
     * @return true:存在する
     */
    public boolean isNewsExists() {
        return CollectionUtil.isNotEmpty(newsItems);
    }

    // PDR Migrate Customization from here

    /**
     * ログイン判定<br/>
     *
     * @return true=ログイン済、false=未ログイン
     */
    public boolean isMemberLogin() {
        // 共通情報Helper取得
        CommonInfoUtility commonInfoUtility = ApplicationContextUtility.getBean(CommonInfoUtility.class);
        return commonInfoUtility.isLogin(getCommonInfo());
    }

    /**
     * 購入済み商品が存在するか<br/>
     *
     * @return true:存在する / false:存在
     */
    public boolean isPuchasedGood() {
        if (puchasedGoodsItems != null && puchasedGoodsItems.size() > 0) {
            return true;
        }
        return false;
    }
    // PDR Migrate Customization to here
}
