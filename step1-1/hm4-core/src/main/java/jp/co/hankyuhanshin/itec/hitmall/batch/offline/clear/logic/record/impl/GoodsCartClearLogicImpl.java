/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.batch.offline.clear.logic.record.impl;

import jp.co.hankyuhanshin.itec.hitmall.batch.offline.clear.dao.CartGoodsBatchDao;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.clear.logic.record.AbstractRecordClearLogic;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.clear.logic.record.GoodsCartClearLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * カート商品レコードクリアロジック実装クラス
 *
 * @author kk4625
 * @version $Revision:$
 */
@Component
public class GoodsCartClearLogicImpl extends AbstractRecordClearLogic implements GoodsCartClearLogic {

    /**
     * カート商品Dao
     */
    private final CartGoodsBatchDao cartGoodsBatchDao;

    @Autowired
    public GoodsCartClearLogicImpl(CartGoodsBatchDao cartGoodsBatchDao) {
        this.cartGoodsBatchDao = cartGoodsBatchDao;
    }

    @Override
    public Map<String, String> execute(final Integer shopSeq, final Integer specifiedDays) {

        /* レコード総件数 */
        int recordCount = 0;
        /* 削除件数 */
        int deleteCount = 0;

        try {
            // 指定日数の有効チェック
            if (!this.isEffectiveTime(specifiedDays)) {
                // 処理終了
                LOGGER.warn("指定された日数（" + specifiedDays + "日）は、有効な日数ではありません。");
                return this.makeResultMap(true, recordCount, deleteCount);
            }
            String specifiedDaysStr = String.valueOf(specifiedDays);

            // カート商品レコード総件数取得
            recordCount = cartGoodsBatchDao.getCartGoodsRecordCount(shopSeq);
            // カート商品削除対象件数取得
            if (recordCount > 0) {
                deleteCount = cartGoodsBatchDao.deleteCartGoodsClearRecord(shopSeq, specifiedDaysStr);
                LOGGER.info("レコード総数" + recordCount + "件中、" + deleteCount + "件のレコードを削除しました。");
            } else {
                LOGGER.info("レコードが存在しませんでした。");
            }
            // 正常終了結果Map作成
            return this.makeResultMap(true, recordCount, deleteCount);
        } catch (Exception e) {
            // 異常終了結果Map作成
            LOGGER.warn("カート商品TBLクリア処理中に異常終了しました：", e);
            return this.makeResultMap(false, recordCount, deleteCount);
        }

    }
}
