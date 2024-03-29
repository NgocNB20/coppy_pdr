/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.details;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchConditionDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import java.util.List;

/**
 * 処理履歴ページ<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderProcessHistoryModel extends AbstractModel {

    /**
     * 検索条件（セッション）<br/>
     */
    private OrderSearchConditionDto conditionDto;

    /**
     * 受注インデックスリスト
     */
    @Valid
    private List<OrderProcessHistoryModelItem> processhistoryPageItems;

    /**
     * 受注履歴番号（表示）
     */
    private String orderCode;

    /**
     * 受注SEQ（セッション）
     */
    private Integer orderSeq;

    /**
     * 受注履歴連番 パラメータ用
     */
    private Integer versionNo;

    /**
     * キャンセルフラグ
     */
    private boolean cancel = false;
}
