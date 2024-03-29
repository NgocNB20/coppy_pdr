/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.web;

import jp.co.hankyuhanshin.itec.hitmall.api.goods.GoodsApi;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsUnitDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.GoodsUnitListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 規格２プルダウン取得コントローラー<br/>
 * 規格１プルダウンの選択値をもとに、紐づく規格２を取得し、プルダウンに設定する
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@RestController
@RequestMapping("/")
public class GoodsUnitSearchController extends AbstractController {

    /**
     * ログ
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsUnitSearchController.class);

    /**
     * マップに格納するプルダウンラベルのkey名
     */
    public static final String KEY_OF_LABEL = "label";

    /**
     * マップに格納するプルダウン値のkey名
     */
    public static final String KEY_OF_VALUE = "value";

    /**
     * 商品API
     */
    private final GoodsApi goodsApi;

    /**
     * 変換Utility
     */
    private final ConversionUtility conversionUtility;

    @Autowired
    public GoodsUnitSearchController(GoodsApi goodsApi, ConversionUtility conversionUtility) {
        this.goodsApi = goodsApi;
        this.conversionUtility = conversionUtility;
    }

    /**
     * 規格値2を取得する
     *
     * @param unitGgcd 商品グループコード
     * @param unit1Gcd 選択した規格値1の商品コード
     * @return 規格値2(JSON)
     */
    @GetMapping("/searchGoodsUnit2")
    @ResponseBody
    public Map<String, String> searchGoodsUnit2(@RequestParam(name = "unitGgcd", required = false) String unitGgcd,
                                                @RequestParam(name = "unit1Gcd", required = false) String unit1Gcd) {

        // 返却用Map
        Map<String, String> unit2Map = new LinkedHashMap<>();

        // 規格１が選択されていない場合、空のマップを返却
        if (StringUtils.isEmpty(unit1Gcd)) {
            return unit2Map;
        }

        // 判定用List
        List<String> tmpUnitValue2List = new ArrayList<>();

        try {

            // 規格値検索サービスを実行し、規格値情報Dtoを取得
            GoodsUnitListGetRequest goodsUnitListGetRequest = new GoodsUnitListGetRequest();
            goodsUnitListGetRequest.setGcd(unit1Gcd);
            goodsUnitListGetRequest.setGgcd(unitGgcd);

            List<GoodsUnitDtoResponse> goodsUnitDtoResponseList = null;
            try {
                goodsUnitDtoResponseList = goodsApi.getGoodsUnitList(goodsUnitListGetRequest);
            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                // AppLevelListExceptionへ変換
                addAppLevelListException(e);
                throwMessage();
            }

            // Dtoから返却用Mapを作成
            for (GoodsUnitDtoResponse goodsUnit2Dto : goodsUnitDtoResponseList) {

                // 販売、在庫チェック
                boolean isSale = this.isGoodsSale(goodsUnit2Dto);
                boolean isStock = this.isGoodsStock(goodsUnit2Dto);

                // 非販売または、在庫切れの場合、処理なし
                if (!isSale) {
                    continue;
                }

                if (tmpUnitValue2List.contains(goodsUnit2Dto.getUnitValue2())) {
                    continue;
                }

                String unitValue2 = null;
                if (isStock) {
                    unitValue2 = goodsUnit2Dto.getUnitValue2();
                } else {
                    GoodsUtility goodsUtility = ApplicationContextUtility.getBean(GoodsUtility.class);
                    unitValue2 = goodsUtility.addNoStockMessage(goodsUnit2Dto.getUnitValue2());
                }

                unit2Map.put(goodsUnit2Dto.getGoodsCode(), unitValue2);

                // 判定用Listに追加
                tmpUnitValue2List.add(goodsUnit2Dto.getUnitValue2());
            }
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            unit2Map = null;
        }

        return unit2Map;
    }

    /**
     * 商品在庫チェック<br/>
     *
     * @param goodsUnitDto 商品規格Dto
     * @return 在庫あり:true,在庫なし:false
     */
    protected boolean isGoodsStock(GoodsUnitDtoResponse goodsUnitDto) {
        // 在庫の確認
        if (HTypeStockManagementFlag.OFF.equals(EnumTypeUtil.getEnumFromValue(HTypeStockManagementFlag.class,
                                                                              goodsUnitDto.getStockManagementFlag()
                                                                             ))) {
            // 在庫管理なし
            return true;
        } else if (goodsUnitDto.getSalesPossibleStock().compareTo(goodsUnitDto.getRemainderFewStock()) == 1) {
            // 在庫あり
            return true;
        } else if (goodsUnitDto.getSalesPossibleStock().intValue() > 0) {
            // 在庫残少
            return true;
        }
        return false;
    }

    /**
     * 商品在庫チェック<br/>
     *
     * @param goodsUnitDto 商品規格Dto
     * @return 販売可能:true,販売不可:false
     */
    protected boolean isGoodsSale(GoodsUnitDtoResponse goodsUnitDto) {

        HTypeGoodsSaleStatus goodsSaleStatus =
                        EnumTypeUtil.getEnumFromValue(HTypeGoodsSaleStatus.class, goodsUnitDto.getSaleStatus());
        Timestamp saleStartTime = conversionUtility.toTimeStamp(goodsUnitDto.getSaleStartTime());
        Timestamp saleEndTime = conversionUtility.toTimeStamp(goodsUnitDto.getSaleEndTime());

        GoodsUtility goodsUtility = ApplicationContextUtility.getBean(GoodsUtility.class);
        if (goodsUtility.isGoodsSales(goodsSaleStatus, saleStartTime, saleEndTime)) {
            return true;
        }
        return false;
    }

}
