/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.mail;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailDeliveryStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.dto.cuenote.api.CuenoteApiGetDeliveryResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockDownloadCsvDto;

import java.util.List;
import java.util.stream.Stream;

/**
 * 管理者へメールを送信する<br/>
 *
 * @author st75001
 */
public interface SendAdminMailLogic {

    /**
     * 管理者へ処理結果メールを送信する
     *
     * @param detail 処理結果
     * @param processName 処理名
     * @param mailTemplateType メールテンプレートタイプ
     * @param errFlg エラーフラグ true：エラー、false：正常
     */
    void execute(String detail, String processName, HTypeMailTemplateType mailTemplateType, boolean errFlg);

    /**
     * 配信予約メール処理結果成功内容生成
     */
    public String createSuccessDeliveryReserveMailDetail(Integer sendMailRequestCount,Integer sendMailMemberCount,List<String> skipGoodsCodeList,List<Integer> skipCustomerNoList,List<String> errDetailsList);

    /**
     * 処理結果異常終了内容生成
     *
     * @param exceptionName 発生したException
     * @param processName 処理名
     * @param recoveryMethod リカバリー方法
     */
    public String createFailedMailDetail(String exceptionName, String processName, String recoveryMethod);

    /**
     * 配信情報取得処理結果成功内容生成
     * @param cuenoteApiGetDeliveryResponseDto 配信情報取得APIレスポンスDTO
     */
    public String createSuccessDeliveryConfirmMailDetail(CuenoteApiGetDeliveryResponseDto cuenoteApiGetDeliveryResponseDto, HTypeMailDeliveryStatus deliveryStatus);
}
