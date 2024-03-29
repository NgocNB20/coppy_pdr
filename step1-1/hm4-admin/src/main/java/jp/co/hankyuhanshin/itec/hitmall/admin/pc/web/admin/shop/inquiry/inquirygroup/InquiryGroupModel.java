/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.inquiry.inquirygroup;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
public class InquiryGroupModel extends AbstractModel {

    /**
     * コンストラクタ<br/>
     * 初期値の設定<br/>
     */
    public InquiryGroupModel() {
        super();
    }

    /**
     * 検索一覧<br/>
     */

    private List<InquiryGroupPageItem> resultItems;

    /**
     * 検索一覧のインデックス
     */
    private int resultIndex;

    /**
     * 表示順
     */
    @NotNull(message = "{ASI000404W}")
    private Integer orderDisplay;

    /**
     * 表示順(各行)
     */
    private Integer orderDisplayRadio;

    /**
     * 問い合わせ分類名称
     */
    private String inqueryGroupName;
    /**
     * 公開状態
     */
    private HTypeOpenDeleteStatus openStatus;
    /**
     * 問い合わせ分類SEQ
     */
    private Integer inquiryGroupSeq;
    /**
     * ショップSEQ
     */
    private Integer shopSeq;

    /************************************
     **  判断処理
     ************************************/

    /**
     * 検索結果表示判定<br/>
     *
     * @return true=検索結果がnull以外(0件リスト含む), false=検索結果がnull
     */
    public boolean isResult() {
        return getResultItems() != null;
    }
}
