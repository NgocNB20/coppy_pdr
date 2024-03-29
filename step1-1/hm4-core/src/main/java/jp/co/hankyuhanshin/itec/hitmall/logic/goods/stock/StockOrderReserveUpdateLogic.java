/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock;

/**
 * 注文確保在庫数更新ロジック
 *
 * @author yamaguchi
 * @version $Revision: 1.2 $
 */
public interface StockOrderReserveUpdateLogic {

    /**
     * 在庫確保モード 定数<br />
     * 通常
     */
    public static final int NOMAL = 0;

    /**
     * 在庫確保モード 定数<br />
     * 差分確保<br />
     * 対象：商品数量 - 前回商品数量 > 0<br />
     * 処理：注文確保在庫数 = 注文確保在庫数 + (商品数量 - 前回商品数量)<br />
     */
    public static final int RESERVE = 1;

    // #2233 START
    /**
     * 在庫確保モード 定数<br />
     * 出荷前在庫戻し
     * 差分確保したものを戻す<br />
     * 対象：商品数量 - 前回商品数量 > 0<br />
     * 処理：注文確保在庫数 = 注文確保在庫数 - (商品数量 - 前回商品数量)<br />
     */
    // #2233 END
    public static final int NOT_RESERVE = 2;

    /**
     * 在庫確保モード 定数<br />
     * 差分在庫戻し<br />
     * 対象：商品数量 - 前回商品数量 < 0<br />
     * 処理：注文確保在庫数 = 注文確保在庫数 + (商品数量 - 前回商品数量)<br />
     */
    public static final int ROLLBACK = 3;

    // #2233 START
    /**
     * 在庫確保モード 定数<br />
     * 出荷後在庫戻し
     * 差分確保したものを戻す<br />
     * 対象：数量が増加した商品<br />
     * 処理：注文確保在庫数 = 注文確保在庫　－　注文数（増加分のみ）<br />
     */
    public static final int NOT_RESERVE_SHIPPED = 8;

    // #2233 END

    /**
     * 実行メソッド<br/>
     *
     * @param orderSeq            受注SEQ
     * @param orderGoodsVersionNo 受注商品連番
     * @param orderConsecutiveNo  注文連番
     * @param recerveMode         在庫確保モード
     * @return 処理件数
     */
    int execute(Integer orderSeq, Integer orderGoodsVersionNo, Integer orderConsecutiveNo, int recerveMode);
}
