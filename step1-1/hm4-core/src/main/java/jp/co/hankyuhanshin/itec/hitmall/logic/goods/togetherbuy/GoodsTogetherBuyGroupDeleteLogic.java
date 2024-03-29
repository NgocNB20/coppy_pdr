// 2023-renew No21 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.goods.togetherbuy;

/**
 * 一緒によく購入される商品削除<br/>
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
public interface GoodsTogetherBuyGroupDeleteLogic {

    /**
     * 一緒によく購入される商品削除<br/>
     *
     * @param registMethod           登録方法
     * @return 削除件数
     */
    int execute(String registMethod);
}
// 2023-renew No21 to here
