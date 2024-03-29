/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */
package jp.co.hankyuhanshin.itec.hitmall.batch.offline.clear.logic.file;

import java.util.Map;

/**
 * テンポラリファイルクリアロジックI/F
 *
 * @author MN7017
 * @version $Revision:$
 */
public interface TemporaryFileClearLogic {

    /**
     * 一時ファイル削除処理
     *
     * @param forderPath    削除対象ファイルが含まれるディレクトリ配列
     * @param specifiedDays 削除対象となる日付
     * @return 処理結果マップ
     */
    Map<String, String> execute(final String[] forderPath, final Integer specifiedDays);

}
