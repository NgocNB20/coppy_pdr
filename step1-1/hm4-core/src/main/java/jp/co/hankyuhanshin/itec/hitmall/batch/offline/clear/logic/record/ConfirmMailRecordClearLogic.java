/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.batch.offline.clear.logic.record;

import java.util.Map;

/**
 * 確認メールレコードクリアロジックI/F
 *
 * @author MN7017
 * @version $Revision:$
 */
public interface ConfirmMailRecordClearLogic {

    /**
     * 実行処理
     *
     * @param shopSeq       ショップSEQ
     * @param specifiedDays 指定日数
     * @return 処理結果Map
     */
    Map<String, String> execute(final Integer shopSeq, Integer specifiedDays);

}
