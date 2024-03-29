/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order;

import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PDR#015 12_WebAPI<br/>
 *
 * <pre>
 * WEB-API連携取得結果DTOクラス
 * プロモーション 詳細情報
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class WebApiGetPromotionResponseDetailDto implements Serializable {
    // PDR Migrate Customization from here
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 金額情報リスト */
    private List<WebApiGetPromotionResponsePriceDto> priceInfo;

    /** 同梱情報リスト */
    private List<WebApiGetPromotionResponseBundleDto> bundleInfo;

    /** 値引率情報リスト */
    private List<WebApiGetPromotionResponseDiscountInfoDto> discountInfo;

    /** クーポン以外プロモーション適用結果 */
    private Integer promApplyFlag;

    /** クーポンプロモーション適用結果 */
    private Integer couponApplyFlag;

    /** クーポン名称 */
    private String couponName;

    /**
     * プロモーション 金額情報リストを<br/>
     * 出荷予定日(String型:yyyyMMdd) + 配達指定日(String型:yyyyMMdd) + 入荷予定日(String型:yyyyMMdd)をキーにしたMAPで返却します。
     *
     * @return 出荷予定日等をキーにしたプロモーション 金額情報MAP
     */
    public Map<String, WebApiGetPromotionResponsePriceDto> getPriceInfoMap() {

        if (this.priceInfo == null || this.priceInfo.size() == 0) {
            // 空を返却
            return new HashMap<>();
        }

        Map<String, WebApiGetPromotionResponsePriceDto> map = new HashMap<>();

        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        for (WebApiGetPromotionResponsePriceDto dto : this.priceInfo) {

            // 出荷予定日
            String shipmentDate;
            if (dto.getShipmentDate() == null) {
                shipmentDate = WebApiGetPromotionInformationCreateRequestDto.SHIPPINGDATE_FIXED;
            } else {
                shipmentDate = dateUtility.format(dto.getShipmentDate(), DateUtility.YMD);
            }

            // 2023-renew No14 from here
            // 配達指定日
            String deliveryDesignatedDay;
            if (dto.getDeliveryDesignatedDay() == null) {
                deliveryDesignatedDay = WebApiGetPromotionInformationCreateRequestDto.DELIVERYDESIGNATEDDAY_FIXED;
            } else {
                deliveryDesignatedDay = dateUtility.format(dto.getDeliveryDesignatedDay(), DateUtility.YMD);
            }
            // 2023-renew No14 to here

            // 入荷予定日
            String stockDate;
            if (dto.getStockDate() != null && shipmentDate.equals(
                            WebApiGetPromotionInformationCreateRequestDto.STOCKDATE_FIXED)) {
                stockDate = dateUtility.format(dto.getStockDate(), DateUtility.YMD);
            } else {
                stockDate = WebApiGetPromotionInformationCreateRequestDto.STOCKDATE_FIXED;
            }

            // 2023-renew No14 from here
            // 出荷予定日 + 配達指定日 + 入荷予定日
            String key = shipmentDate + deliveryDesignatedDay + stockDate;
            // 2023-renew No14 to here
            map.put(key, dto);
        }
        return map;
    }

    /**
     * プロモーション 同梱情報リストを<br/>
     * 出荷予定日(String型:yyyyMMdd) + 配達指定日(String型:yyyyMMdd) + 入荷予定日(String型:yyyyMMdd)をキーにしたMAPで返却します。
     *
     * @return 出荷予定日等をキーにしたプロモーション 同梱情報MAP
     */
    public Map<String, List<WebApiGetPromotionResponseBundleDto>> getBundleInfoMap() {

        if (this.bundleInfo == null || this.bundleInfo.size() == 0) {
            // 空を返却
            return new HashMap<>();
        }

        Map<String, List<WebApiGetPromotionResponseBundleDto>> map = new HashMap<>();

        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        for (WebApiGetPromotionResponseBundleDto dto : this.bundleInfo) {

            // 出荷予定日
            String shipmentDate;
            if (dto.getShipmentDate() == null) {
                shipmentDate = WebApiGetPromotionInformationCreateRequestDto.SHIPPINGDATE_FIXED;
            } else {
                shipmentDate = dateUtility.format(dto.getShipmentDate(), DateUtility.YMD);
            }

            // 2023-renew No14 from here
            // 配達指定日
            String deliveryDesignatedDay;
            if (dto.getDeliveryDesignatedDay() == null) {
                deliveryDesignatedDay = WebApiGetPromotionInformationCreateRequestDto.DELIVERYDESIGNATEDDAY_FIXED;
            } else {
                deliveryDesignatedDay = dateUtility.format(dto.getDeliveryDesignatedDay(), DateUtility.YMD);
            }
            // 2023-renew No14 to here

            // 入荷予定日
            String stockDate;
            if (dto.getStockDate() == null) {
                stockDate = WebApiGetPromotionInformationCreateRequestDto.STOCKDATE_FIXED;
            } else {
                stockDate = dateUtility.format(dto.getStockDate(), DateUtility.YMD);
            }

            // 2023-renew No14 from here
            // 出荷予定日 + 配達指定日 + 入荷予定日
            String key = shipmentDate + deliveryDesignatedDay + stockDate;
            // 2023-renew No14 to here

            // 同梱情報
            List<WebApiGetPromotionResponseBundleDto> bundleDtoGoodsList = map.get(key);
            if (bundleDtoGoodsList == null || bundleDtoGoodsList.isEmpty()) {
                bundleDtoGoodsList = new ArrayList<>();
            }
            bundleDtoGoodsList.add(dto);

            map.put(key, bundleDtoGoodsList);
        }
        return map;
    }

    /**
     * 値引率情報リストを<br/>
     * 受注連番をキーにしたMAPで返却します。
     *
     * @return 受注連番をキーにした値引率情報Map
     */
    public Map<String, WebApiGetPromotionResponseDiscountInfoDto> getDiscountInfoMap() {

        if (this.discountInfo == null || this.discountInfo.size() == 0) {
            // 空を返却
            return new HashMap<>();
        }

        Map<String, WebApiGetPromotionResponseDiscountInfoDto> map = new HashMap<>();

        for (WebApiGetPromotionResponseDiscountInfoDto dto : this.discountInfo) {
            map.put(dto.getOrderSerial(), dto);
        }

        return map;
    }

    // PDR Migrate Customization to here

}
