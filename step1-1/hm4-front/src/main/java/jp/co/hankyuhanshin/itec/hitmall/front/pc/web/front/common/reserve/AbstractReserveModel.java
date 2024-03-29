/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.reserve;

import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.GoodsStockItem;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractModel;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

/**
 * セールde予約画面 Model
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Data
// 2023-renew No14 from here
public class AbstractReserveModel extends AbstractModel {

    /** メッセージコード：システムエラー */
    public static final String MSGCD_SYSTEM_ERR = "PDR-0015-001-A-";

    /** メッセージコード：リクエストパラメータなし */
    public static final String MSGCD_ERROR_NON_REQUEST_PARAM = "PDR-0047-002-A-";

    /** メッセージコード：商品データなし 又は 公開中止、公開前、公開終了 */
    public static final String MSGCD_ERROR_NON_OPEN_GOODS = "AGX000201";

    /** メッセージコード：非販売、販売前、販売終了 */
    public static final String MSGCD_ERROR_NON_SALE_GOODS = "PDR-2023RENEW-14-005-";

    /** メッセージコード：販売可否判定．販売可否判定結果 = 「0:販売不可」 */
    public static final String MSGCD_ERROR_SALE_CHECK_NO = "PDR-2023RENEW-14-006-";

    /** メッセージコード：セールde予約不可（取りおき不可） */
    public static final String MSGCD_ERROR_NOT_RESERVE_ITEM = "PDR-2023RENEW-14-004-";

    /** メッセージコード：お届け希望日が全て未入力の場合*/
    public static final String MSGCD_ERROR_NON_RESERVE_DELIVERY_DATE = "PDR-0028-010-A-";

    /** メッセージコード：セールde予約可能商品の合計数量が最大合計購入可能数を超えた場合エラー */
    public static final String MSGCD_ERROR_RESERVE_DELIVERY_MAX_TOTAL_GOODS_COUNT = "PDR-0214-001-A-";

    /**
     * セールde予約可能商品（取りおき可能商品） 最大合計購入可能数:9999
     */
    public static final BigDecimal RESERVE_DELIVERY_MAX_TOTAL_GOODS_COUNT = new BigDecimal(9999);

    /**
     * 商品コード（リクエストパラメータ保持用）
     */
    private String gcd;

    /**
     * 商品グループSEQ
     */
    private Integer goodsGroupSeq;

    /**
     * 商品SEQ
     */
    private Integer goodsSeq;

    /**
     * 商品コード（表示用）
     */
    private String goodsCode;

    /**
     * 商品画像
     */
    private String goodsImage;

    /**
     * 画像のsrc属性
     */
    private String goodsImageSrc;

    /**
     * 画像のalt属性
     */
    private String goodsImageAlt;

    /**
     * 商品名
     */
    private String goodsName;

    /**
     * 規格1表示フラグ
     */
    private boolean viewUnit1;

    /**
     * 規格タイトル1
     */
    private String unitTitle1;

    /**
     * 規格値1
     */
    private String unitValue1;

    /**
     * 規格2表示フラグ
     */
    private boolean viewUnit2;

    /**
     * 規格タイトル2
     */
    private String unitTitle2;

    /**
     * 規格値2
     */
    private String unitValue2;

    /**
     * 単位
     */
    private String unit;

    /**
     * セール価格が存在するかどうか
     */
    private boolean existSalePrice;

    /**
     * 商品Itemリスト（金額表示用）
     */
    private List<GoodsStockItem> goodsItems;

    /**
     * 過去注文数量
     */
    private Integer reserveDeliveryGoodsHistoryCount;

    /**
     * 予約可能開始日
     */
    private String possibleReserveFromDay;

    /**
     * 予約可能終了日
     */
    private String possibleReserveToDay;

    /**
     * カレンダー選択不可日付List
     * ※yyyy/MM/dd型
     */
    private List<String> calendarNotSelectDateList;

    /**
     * セールde予約情報Item（画面入力用）
     */
    @Valid
    private ReserveItem reserveItem;

    /**
     * 画面表示フラグ
     *
     * @return True:正常（エラーあり当画面表示も含む）、False：異常（当画面表示不可）
     */
    public boolean isDispFlg() {
        return ObjectUtils.isNotEmpty(reserveItem);
    }

}
// 2023-renew No14 to here
