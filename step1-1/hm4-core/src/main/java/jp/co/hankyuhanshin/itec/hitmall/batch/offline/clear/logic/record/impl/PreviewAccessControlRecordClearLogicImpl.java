/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.batch.offline.clear.logic.record.impl;

import jp.co.hankyuhanshin.itec.hitmall.batch.offline.clear.logic.record.AbstractRecordClearLogic;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.clear.logic.record.PreviewAccessControlRecordClearLogic;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * プレビューアクセス制御レコードクリアロジック<br/>
 *
 * @author itogawa
 */
@Component
public class PreviewAccessControlRecordClearLogicImpl extends AbstractRecordClearLogic
                implements PreviewAccessControlRecordClearLogic {

    @Override
    public Map<String, String> execute(Integer specifiedDays) {

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

            // 確認メール削除対象件数取得
            if (recordCount > 0) {
                LOGGER.info("レコード総数" + recordCount + "件中、" + deleteCount + "件のレコードを削除しました。");
            } else {
                LOGGER.info("レコードが存在しませんでした。");
            }
            // 正常終了結果Map作成
            return this.makeResultMap(true, recordCount, deleteCount);
        } catch (Exception e) {
            // 異常終了結果Map作成
            LOGGER.warn("プレビューアクセス制御TBLクリア処理中に異常終了しました：", e);
            return this.makeResultMap(false, recordCount, deleteCount);
        }
    }
}
