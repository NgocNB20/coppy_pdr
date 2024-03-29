/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order;

import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.common.DeliveryNowItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.common.DependingOnReceiptItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.common.ReserveDeliveryItem;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractModel;
import lombok.Data;
import org.apache.commons.collections.MapUtils;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 配送方法選択画面Model
 *
 * @author ota-s5
 */
@Data
public class DeliveryModel extends AbstractModel {

    // PDR Migrate Customization from here
    // << お届け先
    /**
     * 事業所名
     */
    private String receiverLastName;

    /**
     * 電話番号
     */
    private String receiverTel;

    /**
     * 郵便番号（左側）
     */
    private String receiverZipCode1;

    /**
     * 郵便番号（右側）
     */
    private String receiverZipCode2;

    /**
     * 市区郡
     */
    private String receiverAddress1;

    /**
     * 町村・番地
     */
    private String receiverAddress2;

    /**
     * マンション・建物名 ＋ 部屋番号
     */
    private String receiverAddress3;
    // >>

    // << 配送日時の指定
    /**
     * お届け希望日選択値
     */
    private String deliveryDate;

    /**
     * お届け予定日 リスト
     */
    private Map<String, String> deliveryDateItems;

    /**
     * 配達時間指定 ヤマト 表示フラグ
     */
    private boolean viewReceiverTimeZoneYamato;

    /**
     * 配達指定時間ヤマト リスト
     */
    private Map<String, String> receiverTimeZoneYamatoItems;

    /**
     * 配達時間指定 ヤマト
     */
    private String receiverTimeZoneYamato;

    /**
     * 配達時間指定日本郵便 表示フラグ
     */
    private boolean viewReceiverTimeZoneJapanPost;

    /**
     * 配達指定時間日本郵便 リスト
     */
    private Map<String, String> receiverTimeZoneJapanPostItems;

    /**
     * 配達指定時間日本郵便
     */
    private String receiverTimeZoneJapanPost;

    /**
     * 日付毎に指定されたお届け時間帯コード(データ形式　yyyyMMdd:1|2_yyyyMMdd:1|2|3)
     */
    private String receiverTimeZoneCodes;

    /**
     * チェック用日付毎に指定されたお届け時間帯コード(データ形式　yyyyMMdd={1, 2})
     */
    private Map<String, List<String>> chkReceiverTimeZoneCodes;

    /**
     * お届け希望日、時間帯指定エリア非表示フラグ
     */
    private boolean hideDeliveryAssignFlag;

    /**
     * お届け希望日、時間帯指定可否
     */
    private boolean deliveryAssignFlag;
    // >>

    // << 今すぐお届け分
    /**
     * 今すぐお届け分
     */
    @Valid
    private List<DeliveryNowItem> deliveryNowItems;
    // >>

    // 2023-renew No14 from here
    // << セールde予約商品
    /**
     * セールde予約アイテム MAP
     */
    private Map<String, List<ReserveDeliveryItem>> reserveDeliveryItemMap;

    /**
     * セールde予約のインデックス代わりの商品コード（変更用）
     */
    private String goodsCodeIndex;
    // >>
    // 2023-renew No14 to here

    // << 入庫次第お届け分
    /**
     * 入庫次第お届け分
     */
    @Valid
    private List<DependingOnReceiptItem> dependingOnReceiptItems;
    // >>

    /**
     * 変更ボタン非表示フラグ
     */
    private boolean hideNextPayment;

    /**
     * コンディション
     * 利用可能な配送方法があるか
     *
     * @return true..ある / false..ない
     */
    public boolean existDeliveryMethod;

    /**
     * 今すぐお届け商品が存在するかどうか
     *
     * @return true..今すぐお届け商品あり
     */
    public boolean isViewDeliveryNowGoods() {
        return CollectionUtil.isNotEmpty(deliveryNowItems);
    }

    /**
     * 取りおき可能な商品が存在するかどうか
     *
     * @return true..取りおき可能商品あり
     */
    public boolean isViewReserveDeliveryGoods() {
        // 2023-renew No14 from here
        return MapUtils.isNotEmpty(reserveDeliveryItemMap);
        // 2023-renew No14 to here
    }

    /**
     * 入荷次第お届けの商品が存在するかどうか
     *
     * @return true..入荷次第お届け商品あり
     */
    public boolean isViewDependingOnReceipt() {
        return CollectionUtil.isNotEmpty(dependingOnReceiptItems);
    }

    // PDR Migrate Customization to here

}
