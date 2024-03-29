/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.batch.offline.clear.logic.recordcountreport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * レコード件数取得ロジック基底クラス
 *
 * @author MN7017
 */
public abstract class AbstractRecordCountReportLogic {

    /**
     * ロガー
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractRecordCountReportLogic.class);

    /**
     * 正常文言
     */
    protected static final String RECORDREPORT_COMPLETE = "正常終了";

    /**
     * 異常文言
     */
    protected static final String RECORDREPORT_EROOR = "異常終了";

    /**
     * 結果MAP作成
     *
     * @param result         成功:0, 失敗：1
     * @param allRecordCount 総レコード数
     * @param recordCount    レコード数
     * @param errorMessage   エラーメッセージ
     * @return 結果MAP
     */
    protected Map<String, String> makeResultMap(final boolean result,
                                                final int allRecordCount,
                                                final int recordCount,
                                                final String errorMessage) {

        /** 結果格納Map */
        final Map<String, String> valueMap = new HashMap<>();

        if (result) {
            valueMap.put("STATUS", "0");
            valueMap.put("STATUS_MESSAGE", RECORDREPORT_COMPLETE);
        } else {
            valueMap.put("STATUS", "1");
            valueMap.put("STATUS_MESSAGE", RECORDREPORT_EROOR + errorMessage);
        }

        valueMap.put("RECORD_COUNT", String.valueOf(allRecordCount));
        valueMap.put("SPECIFIEDDAYS_RECORD_COUNT", String.valueOf(recordCount));

        return valueMap;

    }

    /**
     * 指定日数値の有効チェック
     *
     * @param days 指定日数（dicon設定）
     * @return true:有効 false：無効
     */
    protected boolean isEffectiveTime(Integer days) {

        // 指定日数の有効チェック
        if (days == null || days >= 0) {
            return false;
        }

        return true;
    }

}
