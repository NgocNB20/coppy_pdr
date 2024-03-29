/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order;

import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.reserve.AbstractReserveHelper;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.reserve.ReserveItem;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 注文_セールde予約Helper
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Component
// 2023-renew No14 from here
public class OrderReserveHelper extends AbstractReserveHelper {

    /**
     * コンストラクタ
     */
    @Autowired
    public OrderReserveHelper(GoodsUtility goodsUtility, ConversionUtility conversionUtility, DateUtility dateUtility) {
        super(goodsUtility, conversionUtility, dateUtility);
    }

    /**
     * 前画面からの引継ぎデータを基にセールde予約情報Itemを作成
     *
     * @param orderReserveModel セールde予約画面 Model
     * @param reserveItem セールde予約情報Item（前画面からの引継ぎデータ）
     */
    public void setReserveItem(OrderReserveModel orderReserveModel, ReserveItem reserveItem) {

        // 前画面からの引継ぎデータを保持
        orderReserveModel.setBeforeReserveItem(reserveItem);

        // 画面入力用に引継ぎデータをディープコピーしてセット
        orderReserveModel.setReserveItem(CopyUtil.deepCopy(reserveItem));

        // 最大件数分のセールde予約情報 詳細Itemを追加生成し、初期表示時の行数を設定
        setDefaultReserveItem(orderReserveModel);

    }

}
// 2023-renew No14 to here
