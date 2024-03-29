/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.restock;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DownloadTopGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReStockStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 入荷お知らせ商品詳細ページ
 *
 * @author st75001
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ReStockDetailsModel extends AbstractModel {

    /**
     * コンストラクタ<br/>
     * 初期値の設定<br/>
     */
    public ReStockDetailsModel() {
        super();
    }

    /************************************
     ** Redirect受け渡し項目
     ************************************/
    /**
     * キー（商品SEQ+入荷状態+配信ID+配信状況)<br/>
     */
    private String redirectKey;

    /**
     * 再利用フラグ<br/>
     */
    private String redirectRecycle;

    /**
     * 処理モード<br/>
     * 再検索を行う場合は、md=listのパラメータを付与する。<br/>
     */
    private String md;

    /**
     * 基本情報
     */

    /**
     * 商品Seq
     */
    private Integer goodsSeq;

    /**
     * 商品番号
     */
    private String goodsCode;

    /**
     * 商品名
     */
    private String goodsName;

    /**
     * 入荷状態
     */
    private HTypeReStockStatus reStockStatus;

    /**
     * 配信ID
     */
    private String deliveryId;

    /**
     * 入荷日時
     */
    private Timestamp reStockTime;

    /**
     * メッセージ引数マップ
     */
    private Map<String, String[]> msgArgMap;

    /**
     * 一覧<br/>
     */
    private List<ReStockDetailsResultItem> resultItems;

    /**
     * 行番号<br/>
     */
    private int resultIndex;

    /**
     * CSVダウンロード件数限界値
     */
    private Integer csvLimit;

    /**
     * 選択出力タイプ<br />
     */
    @NotEmpty(message = "{HRequiredValidator.REQUIRED_detail}", groups = DownloadTopGroup.class)
    private String reStockOutDataSelectTop;

    /**
     * 選択出力タイプアイテム<br />
     */
    private Map<String, String> reStockOutDataSelectTopItems;

    /**
     * 検索結果表示判定<br/>
     *
     * @return true=検索結果がnull以外(0件リスト含む), false=検索結果がnull
     */
    public boolean isResult() {
        return getResultItems() != null;
    }
}
