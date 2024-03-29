/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.batch.offline.clear.logic.record.impl;

import jp.co.hankyuhanshin.itec.hitmall.batch.offline.clear.dao.AdminConfirmMailBatchDao;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.clear.logic.record.AbstractRecordClearLogic;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.clear.logic.record.AdminConfirmMailRecordClearLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 運営者用確認メールレコードクリアロジック実装クラス
 *
 * @author ab53400
 * @version $Revision:$
 */
@Component
public class AdminConfirmMailRecordClearLogicImpl extends AbstractRecordClearLogic
                implements AdminConfirmMailRecordClearLogic {

    /**
     * 運営者用確認メールDao
     */
    private final AdminConfirmMailBatchDao adminConfirmMailBatchDao;

    @Autowired
    public AdminConfirmMailRecordClearLogicImpl(AdminConfirmMailBatchDao adminConfirmMailBatchDao) {
        this.adminConfirmMailBatchDao = adminConfirmMailBatchDao;
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

            // 運営者用確認メールレコード総件数取得
            recordCount = adminConfirmMailBatchDao.getAdminConfirmMailRecordCount(shopSeq);
            // 運営者用確認メール削除対象件数取得
            if (recordCount > 0) {
                deleteCount = adminConfirmMailBatchDao.deleteAdminConfirmMailClearRecord(shopSeq, specifiedDaysStr);
                LOGGER.info("レコード総数" + recordCount + "件中、" + deleteCount + "件のレコードを削除しました。");
            } else {
                LOGGER.info("レコードが存在しませんでした。");
            }
            // 正常終了結果Map作成
            return this.makeResultMap(true, recordCount, deleteCount);
        } catch (Exception e) {
            // 異常終了結果Map作成
            LOGGER.warn("運営者用確認メールTBLクリア処理中に異常終了しました：", e);
            return this.makeResultMap(false, recordCount, deleteCount);
        }

    }
}
