/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order;

import jp.co.hankyuhanshin.itec.hmbase.util.AppLevelFacesMessageUtil;
import org.springframework.stereotype.Component;

/**
 * 出荷アップロード画面Helper
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class OrderShipmentUploadHelper {

    /**
     * モデル内容を初期化
     *
     * @param model 出荷アップロードモデル
     */
    public void clearPage(OrderShipmentUploadModel model) {
        //        model.setRegistUploadFile(null);
        model.setSavedTempraryFile(null);
        model.setInfoMsg(null);
    }

    /**
     * 非同期処理登録用タスクオブジェクトの作成
     *
     * @param model モデル
     * @return タスクオブジェクト
     */
    //    public BatchTaskEntity toBatchTask(OrderShipmentUploadModel model) {
    //
    //        // ネットワークHelper取得
    //        NetworkUtility networkUtility = ApplicationContextUtility.getBean(NetworkUtility.class);
    //
    //        // 日付関連Helper取得
    //        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
    //
    //        // バッチ処理タスクエンティティ
    //        BatchTaskEntity task = ApplicationContextUtility.getBean(BatchTaskEntity.class);
    //
    //        // ONLINE
    //        task.setOnline(HTypeBatchDerive.ONLINE.getValue());
    //
    //        // バッチタイプ
    //        task.setBatchType("SHIPMENT_REGIST");
    //
    //        // バッチ名
    //        task.setBatchName("出荷アップロード");
    //
    //        // 受付ホスト
    //        task.setTargetHost(networkUtility.getLocalHostName());
    //
    //        // ショップSEQ
    //        task.setShopSeq(model.getCommonInfo().getCommonInfoBase().getShopSeq());
    //
    //        // 管理者ID
    //        task.setTaskOwner(model.getCommonInfo().getCommonInfoAdministrator().getAdministratorId());
    //
    //        // 受付時間
    //        task.setAcceptTime(dateUtility.getCurrentTime());
    //
    //        /* 追加 */
    //
    //        // タスク状態 未処理
    //        task.setTaskStatus(HTypeTaskStatus.UNTREATED.getValue());
    //
    //        // 処理キャンセル要求 要求なし
    //        task.setQuitFlag(HTypeQuitFlag.UNDEMAND.getValue());
    //
    //        return task;
    //    }

    /**
     * インフォメーションメッセージ設定
     *
     * @param model 出荷アップロードモデル
     * @param msgId メッセージID
     * @param args  引数
     */
    public void setInfoMessage(OrderShipmentUploadModel model, String msgId, Object[] args) {
        String infoMessage = AppLevelFacesMessageUtil.getAllMessage(msgId, args).getMessage();
        model.setInfoMsg(infoMessage);
    }
}
