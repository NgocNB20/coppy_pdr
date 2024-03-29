/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.constant;

import jp.co.hankyuhanshin.itec.hitmall.front.base.constant.ValidatorConstants;

/**
 * PDR#10 07_データ連携（商品情報）新規商品情報の正規表現追加<br/>
 * 入力規制の情報を集約したクラス<br/>
 * <pre>
 * クール便適用期間用の正規表現追加
 * </pre>
 *
 * @author s.kume
 *
 */
public class ValidatorConstantsPdr extends ValidatorConstants {

    /**
     * クール便適用期間の正規表現(一括アップロード用)<br/>
     * 値が"MM/dd"形式であるかどうかだけ判定し、厳密な日付チェックは行わない<br/>
     */
    public static final String REGEX_COOLlSEND = "^(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])$";
}
