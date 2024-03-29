/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.details;

import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVBothSideSpace;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCarrierType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEmergencyFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalesFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.SpaceValidateMode;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.sql.Timestamp;

/**
 * 受注詳細ページ<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderDetailsModel extends AbstractOrderDetailsModel {

    /**
     * 受注情報Dto<br/>
     */
    private ReceiveOrderDto receiveOrderDto;

    /**
     * キャンセル受注の更新モードかどうか<br/>
     * true..更新モード
     */
    private boolean canceledOrderUpdate;

    /**
     * メモ（入力）
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE)
    @HVSpecialCharacter(allowPunctuation = true)
    @Length(min = 0, max = 2000)
    private String editMemo;

    /**
     * 検索条件保持判定用<br />
     * 遷移元パッケージを格納<br />
     * チケット対応#2743対応
     */
    private String from;

    private String md = "list";

    /**
     * 検索条件
     */
    private OrderSearchConditionDto conditionDto;

    /**
     * チェックメッセージ
     */
    private String checkErrorMessage;

    /**
     * 受注コード（必須）
     */
    private String orderCode;

    /**
     * 受注SEQ（必須）
     */
    private Integer orderSeq;

    // //////// 受注インデックス
    /**
     * 処理日時
     */
    private Timestamp processTime;

    // //////// 受注サマリー

    /**
     * 受注履歴連番（必須）
     */
    private Integer orderVersionNo;

    /**
     * 売上日時
     */
    private Timestamp salesTime;

    /**
     * 売上フラグ（必須）
     */
    private HTypeSalesFlag salesFlag = HTypeSalesFlag.OFF;

    /**
     * キャリア種別（必須）
     */
    private HTypeCarrierType carrierType;

    /**
     * メール送信済みフラグ
     */
    private boolean mailSentFlag;

    /**
     * 請求決済エラー解消フラグ
     */
    private boolean cancelOfEmergency = false;

    /**
     * 請求決済エラー解消チェックボックス表示フラグ
     *
     * <pre>
     * GMO経由AmazonPay決済で、出荷後の受注キャンセルが「受付→エラー」となった場合に
     * 異常フラグをOFFにするための「請求決済エラー解消」チェックボックスを表示する。
     * 表示条件
     * １．キャンセル受注の更新モードである
     * ２．異常フラグがONである
     * （キャンセル受注の更新モードが true ならば、キャンセルフラグも必ず ON であるため条件式には含めない）
     * </pre>
     *
     * @return true:表示 false:非表示
     */
    public boolean isCancelOfEmergencyView() {
        return canceledOrderUpdate && emergencyFlag == HTypeEmergencyFlag.ON;
    }

    /**
     * @param mailSentFlag the mailSentFlag to set
     */
    public void setMailSentFlag(boolean mailSentFlag) {
        if (mailSentFlag) {
            this.mailSentFlag = mailSentFlag;
        }
    }
}
