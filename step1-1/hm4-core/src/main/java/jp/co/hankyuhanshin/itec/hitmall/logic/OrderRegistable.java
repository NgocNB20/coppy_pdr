/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;

/**
 * 注文内容をもとに受注情報を登録する抽象クラス<br/>
 * 受注登録処理を行なう場合、このクラスを継承してください。<br/>
 *
 * @author ozaki
 * @author matsumoto(itec) 2012/02/06 #2761 対応
 */
public interface OrderRegistable {

    /**
     * 在庫確保失敗エラーコード level=error
     */
    public static final String MSGCD_UPDATE_STOCK_FAIL = "LOX000101";
    /**
     * 注文登録失敗
     */
    public static final String MSGCD_ORDER_REGIST_FAIL = "LOX000102";
    /**
     * 在庫確保失敗-SQL/SYSTEM系
     */
    public static final String MSGCD_UPDATE_STOCK_SYS_FAIL = "LOX000103";

    /**
     * 必須項目、未入力エラー（HTTP_ACCEPT、HTTP_USER_AGENT、使用端末情報）
     */
    public static final String MSGCD_REQUIRED_FAIL = "LOX000105";
    /**
     * クレジット通信失敗（コネクション接続系エラー）
     */
    public static final String MSGCD_ENTRYTRAN_FAIL = "LOX000106";
    /**
     * クレジット通信失敗（通信先サーバーエラー）
     */
    public static final String MSGCD_EXECTRAN_FAIL = "LOX000107";
    /**
     * コンビニ通信失敗（コネクション接続系エラー）
     */
    public static final String MSGCD_CONVENI_RESERVATION_FAIL = "LOX000108";
    /**
     * Pay-easy通信失敗（コネクション接続系エラー）
     */
    public static final String MSGCD_PAYEASY_RESERVATION_FAIL = "LOX000109";
    /**
     * 在庫解放済エラーコード
     */
    public static final String MSGCD_STOCK_RELASED_FAIL = "LOX000110";

    /**
     * 入力したカードが不適切な場合エラー：PKG-3554-014-L-
     */
    public static final String MSGCD_ENTRY_CARD_ERR = "PKG-3554-014-L-";

    /**
     * GMOの結果にエラーが含まれている場合エラー：PKG-3554-015-L-
     */
    public static final String MSGCD_GMO_INPUT_ERR = "PKG-3554-015-L-";

    /**
     * 同時注文排他エラー
     */
    public static final String MSGCD_SIMULTANEOUSORDEREXCLUSION_ERR = "PKG-3555-012-L-";

    /**
     * 受注情報登録の前処理。<br/>
     * 受注情報を登録する際の受注ＳＥＱ採番と在庫確保を行ないます。<br/>
     *
     * @param receiveOrderDto 受注DTO
     */
    void execute(ReceiveOrderDto receiveOrderDto,
                 String memberInfoId,
                 HTypeSiteType siteType,
                 HTypeDeviceType deviceType,
                 Integer memberInfoSeq,
                 String userAgent,
                 String administratorLastName,
                 String administratorFirstName);
}
