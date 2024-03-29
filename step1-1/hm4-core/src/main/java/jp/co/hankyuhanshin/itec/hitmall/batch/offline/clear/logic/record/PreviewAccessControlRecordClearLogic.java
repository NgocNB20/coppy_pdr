/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.batch.offline.clear.logic.record;

import java.util.Map;

/**
 * プレビューアクセス制御レコードクリアロジック<br/>
 *
 * @author itogawa
 */
public interface PreviewAccessControlRecordClearLogic {

    /**
     * 実行処理
     *
     * @param specifiedDays 指定日数
     * @return 処理結果Map
     */
    Map<String, String> execute(Integer specifiedDays);

}
