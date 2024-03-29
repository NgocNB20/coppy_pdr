/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.index.OrderIndexEntity;

import java.sql.Timestamp;

/**
 * 受注修正商品確保処理サービス
 *
 * @author USER
 * @version $Revision: 1.1 $
 */
public interface OrderGoodsReserveService {

    /**
     * 在庫確保に失敗しました。商品の在庫を確認してください。
     */
    public static final String MSGCD_ERROR = "LOX000303";

    /**
     * 実行メソッド
     *
     * @param receiveOrderDto  受注Dto
     * @param orderIndexEntity 受注インデックス
     * @param processTime      処理日時
     * @return 新しい受注商品連番
     */
    Integer execute(ReceiveOrderDto receiveOrderDto, OrderIndexEntity orderIndexEntity, Timestamp processTime);
}
