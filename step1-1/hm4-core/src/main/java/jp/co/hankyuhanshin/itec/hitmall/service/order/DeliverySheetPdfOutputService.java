/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order;

/**
 * 納品書出力サービスインターフェース
 *
 * @author $author$
 * @author Kaneko(Itec) 2011/05/18 #1960
 */
public interface DeliverySheetPdfOutputService {

    /**
     * 出力件数0件時のエラーメッセージ
     */
    final String OUTPUT_EMPTY = "SOO001301";

    /**
     * 納品書を出力します
     *
     * @param conditionDto 検索条件
     * @return 出力件数
     */
    int execute(Object conditionDto);
}
