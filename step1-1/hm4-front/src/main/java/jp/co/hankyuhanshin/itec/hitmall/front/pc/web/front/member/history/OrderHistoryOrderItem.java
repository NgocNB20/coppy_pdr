// PDR Migrate Customization from here
package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.history;

import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSalableGoodsType;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * PDR#032 配送状況・注文履歴の基幹連携
 * 注文履歴（未出荷）画面 詳細情報アイテムクラス（お届け予定日別一覧）
 * 注文連携Web-APIより取得した情報を画面に表示
 *
 * @author s.kume
 */
@Data
@Component
@Scope("prototype")
public class OrderHistoryOrderItem implements Serializable {

    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 選択商品コード
     */
    private String gcd;

    /**
     * 注文日時
     */
    private Timestamp orderTime;

    /**
     * 注文番号
     */
    private String orderCode;

    /**
     * 受付
     */
    private String receptionTypeName;

    /**
     * お支払い合計(税込)
     */
    private String paymentTotalPrice;

    /**
     * お届け先事業所名
     */
    private String receiveOfficeName;

    /**
     * お届け先郵便番号
     */
    private String receiveZipCode;

    /**
     * お届け先住所(都道府県・市区町村)
     */
    private String receiveAddress1;

    /**
     * お届け先住所(丁目・番地)
     */
    private String receiveAddress2;

    /**
     * お届け先住所(建物名・部屋番号)
     */
    private String receiveAddress3;

    /**
     * お届け先住所(方書1)
     */
    private String receiveAddress4;

    /**
     * お届け先住所(方書2)
     */
    private String receiveAddress5;

    /**
     * お届け日
     */
    private String receiveDate;

    /**
     * お支払い方法
     */
    private String paymentType;

    // 2023-renew No24 from here
    /**
     * クーポンコード
     */
    private String couponCode;

    /**
     * クーポン名
     */
    private String couponName;
    // 2023-renew No24 to here

    // 2023-renew No68 from here
    /**
     * キャンセル可否フラグ
     */
    private boolean cancelYesNo;
    // 2023-renew No68 to here

    /**
     * 申込商品
     */
    private String goodsCode;

    /**
     * 商品名
     */
    private String goodsName;

    /**
     * 数量
     */
    private Integer goodsCount;

    /**
     * 販売可能商品区分
     */
    private HTypeSalableGoodsType salableGoodsType;

    /**
     * 販売状態
     */
    private HTypeGoodsSaleStatus saleStatus;

    /**
     * 公開状態
     */
    private HTypeOpenDeleteStatus openDeleteStatus;

    /**
     * 商品がDBに存在するかのフラグ
     */
    private boolean goodsPresenceFlag;

    /**
     * 注文履歴詳細商品情報リスト
     */
    private List<OrderHistoryGoodsItem> orderHistoryGoodsItems;

    /**
     * 注文履歴詳細商品情報リストのインデックス
     */
    private int orderHistoryGoodsIndex;

    // 2024-renew No06 from here
    /**
     * 注文履歴詳細商品情報
     */
    private OrderHistoryGoodsItem goodsItem;
    // 2024-renew No06 to here

}
// PDR Migrate Customization to here
