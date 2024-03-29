/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.multipayment;

import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.MulPayNotificationReceiverLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * マルチペイメント決済結果通知受付コントローラ
 *
 * @author yt23807
 */
@RestController
@RequestMapping("/mulPayNotification")
public class MulPayNotificationReceiverController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MulPayNotificationReceiverController.class);

    /**
     * マルチペイメント決済結果通知受付Logic
     */
    private final MulPayNotificationReceiverLogic mulPayNotificationReceiverLogic;

    /**
     * コンストラクタ
     *
     * @param mulPayNotificationReceiverLogic MulPayNotificationReceiverLogic
     */
    public MulPayNotificationReceiverController(MulPayNotificationReceiverLogic mulPayNotificationReceiverLogic) {
        this.mulPayNotificationReceiverLogic = mulPayNotificationReceiverLogic;
    }

    /**
     * 決済受付（GETメソッド）　※呼び出しNG
     *
     * @param req HTTPリクエスト
     */
    @GetMapping("")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void recieveGet(HttpServletRequest req) {
        LOGGER.error("許可されていないリクエストメソッドです： " + req.getMethod());
        return;
    }

    /**
     * 決済受付（POSTメソッド）　※呼び出しNG
     *
     * @param req HTTPリクエスト
     * @return レスポンス文字列（0）
     */
    @PostMapping(value = "", produces = "text/plain")
    @ResponseStatus(HttpStatus.OK)
    public String recieve(HttpServletRequest req) {
        // マルチペイメント決済結果入金受付登録更新実行
        try {
            mulPayNotificationReceiverLogic.execute(req);
        } catch (Exception e) {
            LOGGER.error("入金受付情報の登録中に発生したエラーにより処理を中断しました。", e);
        }

        // ■□■□GMOクライアントへレスポンス返却
        return "0";
    }
}
