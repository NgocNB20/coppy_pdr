/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.batch.offline.clear.logic.recordcountreport.impl;

import jp.co.hankyuhanshin.itec.hitmall.batch.offline.clear.dao.AccessSearchKeywordBatchDao;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.clear.logic.recordcountreport.AbstractRecordCountReportLogic;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.clear.logic.recordcountreport.AccessSearchKeywordGetRecordCountLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 検索キーワードロジック実装クラス
 *
 * @author kk4625
 */
@Component
public class AccessSearchKeywordGetRecordCountLogicImpl extends AbstractRecordCountReportLogic
                implements AccessSearchKeywordGetRecordCountLogic {

    /**
     * アクセス情報Dao
     */
    private final AccessSearchKeywordBatchDao accessSearchKeywordBatchDao;

    @Autowired
    public AccessSearchKeywordGetRecordCountLogicImpl(AccessSearchKeywordBatchDao accessSearchKeywordBatchDao) {
        this.accessSearchKeywordBatchDao = accessSearchKeywordBatchDao;
    }

    @Override
    public Map<String, String> execute(final Integer shopSeq, final Integer specifiedDays) {

        /* レコード総件数 */
        int recordCountAll = 0;
        /* 指定日レコード件数 */
        int recordCount = 0;

        try {
            // 指定日数の有効チェック
            if (!this.isEffectiveTime(specifiedDays)) {
                // 処理終了
                LOGGER.warn("指定された日数（" + specifiedDays + "日）は、有効な日数ではありません。");
                return this.makeResultMap(true, recordCountAll, recordCount, null);
            }
            String specifiedDaysStr = String.valueOf(specifiedDays);

            // 検索キーワードレコード総件数取得
            recordCountAll = accessSearchKeywordBatchDao.getAccessSearchKeywordRecordAllCount(shopSeq);
            // 検索キーワード削除対象件数取得
            if (recordCountAll > 0) {
                recordCount = accessSearchKeywordBatchDao.getAccessSearchKeywordRecordCount(shopSeq, specifiedDaysStr);
                LOGGER.info("レコード総数" + recordCountAll + "中、指定日数（" + specifiedDays + "）経過レコードは" + recordCount
                            + "件存在します。");
            } else {
                LOGGER.info("レコードが存在しませんでした。");
            }
            // 正常終了結果Map作成
            return this.makeResultMap(true, recordCountAll, recordCount, null);
        } catch (Exception e) {
            // 異常終了結果Map作成
            LOGGER.warn("検索キーワードTBLクリア処理中に異常終了しました：", e);
            return this.makeResultMap(false, recordCountAll, recordCount, null);
        }

    }
}
