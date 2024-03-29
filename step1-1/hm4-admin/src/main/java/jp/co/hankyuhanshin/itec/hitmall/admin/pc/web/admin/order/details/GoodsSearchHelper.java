/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.details;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeIndividualDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchResultForOrderRegistDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.helper.order.OrderConversionHelper;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 「新規受注：商品検索」画面のDxo<br/>
 *
 * @author nakamura
 * @author Kaneko (itec) 2012/08/17 UtilからHelperへ変更
 * Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class GoodsSearchHelper {

    /**
     * 商品詳細Dtoへの変換処理。<br />
     * 商品検索アイテムリスト ⇒ 商品詳細Dtoリスト<br />
     *
     * @param pageItems 商品検索アイテムリスト
     * @return 商品詳細Dtoリスト
     */
    public List<GoodsDetailsDto> toGoodsDetailsDtoList(List<GoodsSearchModelItem> pageItems) {

        // リストを生成
        List<GoodsDetailsDto> goodsDetailsDtoList = new ArrayList<>();

        // 検索一覧分ループ
        for (GoodsSearchModelItem goodssearchPageItem : pageItems) {
            // チェック有の場合
            if (goodssearchPageItem.isResultGoodsCheck()) {
                // リストに追加
                goodsDetailsDtoList.add(toGoodsDetailsDto(goodssearchPageItem.getResultGoodsSearchResultDto()));
            }
        }

        // リストを返す
        return goodsDetailsDtoList;
    }

    /**
     * 商品詳細Dtoへの変換処理。<br />
     * 商品検索アイテム ⇒ 商品詳細Dto<br />
     *
     * @param resultDto 商品検索アイテム
     * @return 商品詳細Dto
     */
    protected GoodsDetailsDto toGoodsDetailsDto(GoodsSearchResultForOrderRegistDto resultDto) {
        // 受注商品エンティティを生成
        GoodsDetailsDto goodsDetailsDto = ApplicationContextUtility.getBean(GoodsDetailsDto.class);

        // 商品SEQ
        goodsDetailsDto.setGoodsSeq(resultDto.getGoodsSeq());
        // 商品コード
        goodsDetailsDto.setGoodsCode(resultDto.getGoodsCode());
        // JANコード
        goodsDetailsDto.setJanCode(resultDto.getJanCode());
        // 商品名
        goodsDetailsDto.setGoodsGroupName(resultDto.getGoodsGroupName());
        // 商品単価（税抜）
        goodsDetailsDto.setGoodsPrice(resultDto.getGoodsPrice());
        // 税率
        goodsDetailsDto.setTaxRate(resultDto.getTaxRate());
        // 商品消費税種別 Goods
        goodsDetailsDto.setGoodsTaxType(resultDto.getGoodsTaxType());
        // 無料配送フラグ
        goodsDetailsDto.setFreeDeliveryFlag(resultDto.getFreeDeliveryFlag());
        // 規格値１
        goodsDetailsDto.setUnitValue1(resultDto.getUnitValue1());
        // 規格値２
        goodsDetailsDto.setUnitValue2(resultDto.getUnitValue2());

        goodsDetailsDto.setPreDiscountPrice(resultDto.getPreDiscountPrice());
        // 商品グループコード
        goodsDetailsDto.setGoodsGroupCode(resultDto.getGoodsGroupCode());
        // 在庫管理フラグ
        goodsDetailsDto.setStockManagementFlag(resultDto.getStockManagementFlag());
        // 販売可能在庫数
        goodsDetailsDto.setSalesPossibleStock(resultDto.getSalesPossibleStock());
        // 商品個別配送種別
        goodsDetailsDto.setIndividualDeliveryType(resultDto.getIndividualDeliveryType());
        // 商品納期
        goodsDetailsDto.setDeliveryType(resultDto.getDeliveryType());
        // 受注連携設定01
        goodsDetailsDto.setOrderSetting1(resultDto.getOrderSetting1());
        // 受注連携設定02
        goodsDetailsDto.setOrderSetting2(resultDto.getOrderSetting2());
        // 受注連携設定03
        goodsDetailsDto.setOrderSetting3(resultDto.getOrderSetting3());
        // 受注連携設定04
        goodsDetailsDto.setOrderSetting4(resultDto.getOrderSetting4());
        // 受注連携設定05
        goodsDetailsDto.setOrderSetting5(resultDto.getOrderSetting5());
        // 受注連携設定06
        goodsDetailsDto.setOrderSetting6(resultDto.getOrderSetting6());
        // 受注連携設定07
        goodsDetailsDto.setOrderSetting7(resultDto.getOrderSetting7());
        // 受注連携設定08
        goodsDetailsDto.setOrderSetting8(resultDto.getOrderSetting8());
        // 受注連携設定09
        goodsDetailsDto.setOrderSetting9(resultDto.getOrderSetting9());
        // 受注連携設定10
        goodsDetailsDto.setOrderSetting10(resultDto.getOrderSetting10());

        return goodsDetailsDto;
    }

    /**
     * エンティティへの変換処理。<br />
     * 商品詳細Dtoリスト ⇒ 受注商品エンティティリスト<br />
     *
     * @param goodsDetailsDtoList 商品詳細Dtoリスト
     * @param goodsSearchModel    ページ
     * @return 受注商品エンティティリスト
     */
    public List<OrderGoodsEntity> toOrderGoodsEntityListForOrderGoodsAdd(List<GoodsDetailsDto> goodsDetailsDtoList,
                                                                         GoodsSearchModel goodsSearchModel) {

        // リストを生成
        List<OrderGoodsEntity> orderGoodsEntityList = new ArrayList<>();

        // 引数で渡された商品詳細Dtoリスト分ループ
        for (GoodsDetailsDto goodsDetailsDto : goodsDetailsDtoList) {
            // リストに追加
            orderGoodsEntityList.add(toOrderGoodsEntity(goodsDetailsDto, goodsSearchModel));
        }

        // リストを返す
        return orderGoodsEntityList;
    }

    /**
     * エンティティへの変換処理。<br />
     * 商品詳細Dto ⇒ 受注商品エンティティ<br />
     *
     * @param goodsDetailsDto  商品詳細Dto
     * @param goodsSearchModel ページ
     * @return 受注商品エンティティ
     */
    protected OrderGoodsEntity toOrderGoodsEntity(GoodsDetailsDto goodsDetailsDto, GoodsSearchModel goodsSearchModel) {
        // 注文フロー共通データ変換ヘルパークラスを生成
        OrderConversionHelper orderConversionHelper = ApplicationContextUtility.getBean(OrderConversionHelper.class);

        // 受注商品エンティティを生成
        // ※注文数量初期値は「1」にする
        OrderGoodsEntity orderGoodsEntity = orderConversionHelper.toOrderGoodsEntity(goodsDetailsDto, BigDecimal.ONE);

        return orderGoodsEntity;
    }

    /**
     * Dtoへの変換処理。<br />
     * Pageの検索条件情報 ⇒ 商品Dao用検索条件Dto<br />
     *
     * @param goodsSearchModel 自画面
     * @return 商品Dao用検索条件Dto
     */
    public GoodsSearchForBackDaoConditionDto toGoodsSearchForBackDaoConditionDtoForGoodsSearch(GoodsSearchModel goodsSearchModel) {

        GoodsSearchForBackDaoConditionDto goodsConditionDto =
                        ApplicationContextUtility.getBean(GoodsSearchForBackDaoConditionDto.class);
        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);
        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        /* 画面条件 */
        // サイト
        goodsConditionDto.setSite(goodsSearchModel.getSite());

        // キーワード
        if (goodsSearchModel.getSearchKeyword() != null) {
            String[] searchKeywordArray = goodsSearchModel.getSearchKeyword().split("[\\s|　]+");

            for (int i = 0; i < searchKeywordArray.length; i++) {
                switch (i) {
                    case 0:
                        goodsConditionDto.setKeywordLikeCondition1(searchKeywordArray[i].trim());
                        break;
                    case 1:
                        goodsConditionDto.setKeywordLikeCondition2(searchKeywordArray[i].trim());
                        break;
                    case 2:
                        goodsConditionDto.setKeywordLikeCondition3(searchKeywordArray[i].trim());
                        break;
                    case 3:
                        goodsConditionDto.setKeywordLikeCondition4(searchKeywordArray[i].trim());
                        break;
                    case 4:
                        goodsConditionDto.setKeywordLikeCondition5(searchKeywordArray[i].trim());
                        break;
                    case 5:
                        goodsConditionDto.setKeywordLikeCondition6(searchKeywordArray[i].trim());
                        break;
                    case 6:
                        goodsConditionDto.setKeywordLikeCondition7(searchKeywordArray[i].trim());
                        break;
                    case 7:
                        goodsConditionDto.setKeywordLikeCondition8(searchKeywordArray[i].trim());
                        break;
                    case 8:
                        goodsConditionDto.setKeywordLikeCondition9(searchKeywordArray[i].trim());
                        break;
                    case 9:
                        goodsConditionDto.setKeywordLikeCondition10(searchKeywordArray[i].trim());
                        break;
                    default:
                        // この処理は到達しない
                }
            }
        }

        // カテゴリーパス
        String categoryPath = goodsSearchModel.getCategoryPathMap().get(goodsSearchModel.getSearchCategoryId());
        goodsConditionDto.setCategoryPath(categoryPath);
        // 商品グループコード
        goodsConditionDto.setGoodsGroupCode(goodsSearchModel.getSearchGoodsGroupCode());
        // 商品コード
        goodsConditionDto.setGoodsCode(goodsSearchModel.getSearchGoodsCode());
        // JAN/カタログコード
        goodsConditionDto.setJanCode(goodsSearchModel.getSearchJanCode());
        // 商品名
        goodsConditionDto.setGoodsGroupName(goodsSearchModel.getSearchGoodsGroupName());
        // 商品個別配送種別
        if (goodsSearchModel.getSearchIndividualDeliveryType()) {
            goodsConditionDto.setIndividualDeliveryType(HTypeIndividualDeliveryType.ON);
        } else {
            goodsConditionDto.setIndividualDeliveryType(HTypeIndividualDeliveryType.OFF);
        }
        // 下限価格
        goodsConditionDto.setMinPrice(conversionUtility.toBigDecimal(goodsSearchModel.getSearchMinPrice()));
        // 上限価格
        goodsConditionDto.setMaxPrice(conversionUtility.toBigDecimal(goodsSearchModel.getSearchMaxPrice()));
        // 公開状態
        goodsConditionDto.setGoodsOpenStatus(EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class,
                                                                           goodsSearchModel.getGoodsOpenStatus()
                                                                          ));
        // 公開開始日[from]
        goodsConditionDto.setGoodsOpenStartTimeFrom(
                        conversionUtility.toTimeStamp(goodsSearchModel.getSearchOpenStartTimeFrom()));
        // 公開開始日[to]
        if (goodsSearchModel.getSearchOpenStartTimeTo() != null) {
            goodsConditionDto.setGoodsOpenStartTimeTo(dateUtility.getEndOfDate(
                            conversionUtility.toTimeStamp(goodsSearchModel.getSearchOpenStartTimeTo())));
        }
        // 公開終了日[from]
        goodsConditionDto.setGoodsOpenEndTimeFrom(
                        conversionUtility.toTimeStamp(goodsSearchModel.getSearchOpenEndTimeFrom()));
        // 公開終了日[to]
        if (goodsSearchModel.getSearchOpenEndTimeTo() != null) {
            goodsConditionDto.setGoodsOpenEndTimeTo(dateUtility.getEndOfDate(
                            conversionUtility.toTimeStamp(goodsSearchModel.getSearchOpenEndTimeTo())));
        }
        // 削除商品表示フラグ
        goodsConditionDto.setDeleteStatusDsp(false);
        // 販売状態
        goodsConditionDto.setSaleStatus(EnumTypeUtil.getEnumFromValue(HTypeGoodsSaleStatus.class,
                                                                      goodsSearchModel.getGoodsSaleStatus()
                                                                     ));
        // 販売開始日[from]
        goodsConditionDto.setSaleStartTimeFrom(
                        conversionUtility.toTimeStamp(goodsSearchModel.getSearchSaleStartTimeFrom()));
        // 販売開始日[to]
        if (goodsSearchModel.getSearchSaleStartTimeTo() != null) {
            goodsConditionDto.setSaleStartTimeTo(dateUtility.getEndOfDate(
                            conversionUtility.toTimeStamp(goodsSearchModel.getSearchSaleStartTimeTo())));
        }
        // 販売終了日[from]
        goodsConditionDto.setSaleEndTimeFrom(
                        conversionUtility.toTimeStamp(goodsSearchModel.getSearchSaleEndTimeFrom()));
        // 販売終了日[to]
        if (goodsSearchModel.getSearchSaleEndTimeTo() != null) {
            goodsConditionDto.setSaleEndTimeTo(dateUtility.getEndOfDate(
                            conversionUtility.toTimeStamp(goodsSearchModel.getSearchSaleEndTimeTo())));
        }
        // 登録・更新日時
        String selectDate = goodsSearchModel.getSelectRegitOrUpdate();
        if (selectDate != null) {
            if (selectDate.equals("0")) {
                goodsConditionDto.setRegistTimeFrom(
                                conversionUtility.toTimeStamp(goodsSearchModel.getSearchRegOrUpTimeFrom()));
                if (goodsSearchModel.getSearchRegOrUpTimeTo() != null) {
                    goodsConditionDto.setRegistTimeTo(dateUtility.getEndOfDate(
                                    conversionUtility.toTimeStamp(goodsSearchModel.getSearchRegOrUpTimeTo())));
                }
            } else if (selectDate.equals("1")) {
                goodsConditionDto.setUpdateTimeFrom(
                                conversionUtility.toTimeStamp(goodsSearchModel.getSearchRegOrUpTimeFrom()));
                if (goodsSearchModel.getSearchRegOrUpTimeTo() != null) {
                    goodsConditionDto.setUpdateTimeTo(dateUtility.getEndOfDate(
                                    conversionUtility.toTimeStamp(goodsSearchModel.getSearchRegOrUpTimeTo())));
                }
            }
        }
        return goodsConditionDto;
    }

    /**
     * Dtoへの変換処理。<br />
     * Pageの検索条件情報 ⇒ 商品Dao用検索条件Dto<br />
     *
     * @param goodsSearchModel 自画面
     * @return 商品Dao用検索条件Dto
     */
    public GoodsSearchForBackDaoConditionDto toGoodsSearchForBackDaoConditionDtoForGoodsSearchAjax(GoodsSearchModel goodsSearchModel) {

        GoodsSearchForBackDaoConditionDto goodsConditionDto =
                        ApplicationContextUtility.getBean(GoodsSearchForBackDaoConditionDto.class);
        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);
        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        /* 画面条件 */
        // サイト
        goodsConditionDto.setSite(goodsSearchModel.getSite());

        // キーワード
        if (goodsSearchModel.getSearchKeyword() != null) {
            String[] searchKeywordArray = goodsSearchModel.getSearchKeyword().split("[\\s|　]+");

            for (int i = 0; i < searchKeywordArray.length; i++) {
                switch (i) {
                    case 0:
                        goodsConditionDto.setKeywordLikeCondition1(searchKeywordArray[i].trim());
                        break;
                    case 1:
                        goodsConditionDto.setKeywordLikeCondition2(searchKeywordArray[i].trim());
                        break;
                    case 2:
                        goodsConditionDto.setKeywordLikeCondition3(searchKeywordArray[i].trim());
                        break;
                    case 3:
                        goodsConditionDto.setKeywordLikeCondition4(searchKeywordArray[i].trim());
                        break;
                    case 4:
                        goodsConditionDto.setKeywordLikeCondition5(searchKeywordArray[i].trim());
                        break;
                    case 5:
                        goodsConditionDto.setKeywordLikeCondition6(searchKeywordArray[i].trim());
                        break;
                    case 6:
                        goodsConditionDto.setKeywordLikeCondition7(searchKeywordArray[i].trim());
                        break;
                    case 7:
                        goodsConditionDto.setKeywordLikeCondition8(searchKeywordArray[i].trim());
                        break;
                    case 8:
                        goodsConditionDto.setKeywordLikeCondition9(searchKeywordArray[i].trim());
                        break;
                    case 9:
                        goodsConditionDto.setKeywordLikeCondition10(searchKeywordArray[i].trim());
                        break;
                    default:
                        // この処理は到達しない
                }
            }
        }

        // 商品グループコード
        goodsConditionDto.setGoodsGroupCode(goodsSearchModel.getSearchGoodsGroupCode());
        // 商品コード
        goodsConditionDto.setGoodsCode(goodsSearchModel.getSearchGoodsCode());
        // JAN/カタログコード
        goodsConditionDto.setJanCode(goodsSearchModel.getSearchJanCode());
        // 商品名
        goodsConditionDto.setGoodsGroupName(goodsSearchModel.getSearchGoodsGroupName());

        return goodsConditionDto;
    }

    /**
     * Pageへの変換処理。<br />
     * 商品検索結果Dtoのリスト ⇒ Pageの検索結果リスト<br />
     *
     * @param goodsSearchResultDtoList 検索結果リスト
     * @param goodsSearchModel         ページ
     */
    public void toPageForSearch(List<GoodsSearchResultForOrderRegistDto> goodsSearchResultDtoList,
                                GoodsSearchModel goodsSearchModel,
                                GoodsSearchForBackDaoConditionDto conditionDto) {

        // オフセット + 1をNoにセット
        int index = conditionDto.getOffset() + 1;
        List<GoodsSearchModelItem> resultItemList = new ArrayList<>();
        for (GoodsSearchResultForOrderRegistDto goodsSearchResultDto : goodsSearchResultDtoList) {
            int resultNo = index++;
            resultItemList.add(toGoodsSearchPageItem(goodsSearchResultDto, resultNo));
        }
        goodsSearchModel.setResultItems(resultItemList);
    }

    /**
     * Pageへの変換処理。<br />
     * 商品検索結果Dto ⇒ Pageの検索結果<br />
     *
     * @param goodsSearchResultDto 検索結果
     * @param resultNo             行番号
     * @return goodssearchPageItem アイテム
     */
    protected GoodsSearchModelItem toGoodsSearchPageItem(GoodsSearchResultForOrderRegistDto goodsSearchResultDto,
                                                         int resultNo) {

        GoodsSearchModelItem goodssearchPageItem = ApplicationContextUtility.getBean(GoodsSearchModelItem.class);

        goodssearchPageItem.setResultGoodsSearchResultDto(goodsSearchResultDto);
        goodssearchPageItem.setResultNo(resultNo);
        goodssearchPageItem.setGoodsGroupCode(goodsSearchResultDto.getGoodsGroupCode());
        goodssearchPageItem.setResultGoodsCode(goodsSearchResultDto.getGoodsCode());
        goodssearchPageItem.setResultJanCode(goodsSearchResultDto.getJanCode());
        goodssearchPageItem.setResultGoodsGroupName(goodsSearchResultDto.getGoodsGroupName());
        goodssearchPageItem.setResultUnitValue1(goodsSearchResultDto.getUnitValue1());
        goodssearchPageItem.setResultUnitValue2(goodsSearchResultDto.getUnitValue2());
        goodssearchPageItem.setResultGoodsPrice(goodsSearchResultDto.getGoodsPrice());
        goodssearchPageItem.setResultIndividualDeliveryType(
                        goodsSearchResultDto.getIndividualDeliveryType().getValue());
        goodssearchPageItem.setResultStockManagementFlag(goodsSearchResultDto.getStockManagementFlag().getValue());
        goodssearchPageItem.setResultSalesPossibleStock(goodsSearchResultDto.getSalesPossibleStock());
        return goodssearchPageItem;
    }

    /**
     * チェックされた商品の商品検索結果Dtoのリストを作成<br/>
     *
     * @param goodsSearchModel ページ
     * @return 選択済みの商品SEQリスト
     */
    public List<Integer> toGoodsSeqList(GoodsSearchModel goodsSearchModel) {

        // 選択商品SEQリストを生成
        List<Integer> goodsSeqList = new ArrayList<>();

        // 検索一覧分ループ
        for (GoodsSearchModelItem goodssearchPageItem : goodsSearchModel.getResultItems()) {

            // チェック有の場合
            if (goodssearchPageItem.isResultGoodsCheck()) {

                // 選択商品SEQリストに追加
                goodsSeqList.add(goodssearchPageItem.getResultGoodsSearchResultDto().getGoodsSeq());
            }
        }

        // リストを返す
        return goodsSeqList;
    }

    /**
     * 在庫数マップを作成<br/>
     * メソッドの説明・概要<br/>
     *
     * @param goodsSearchResultDtoList 商品検索結果
     * @return 在庫数マップ（key:商品SEQ、value:在庫管理有の場合在庫数、在庫管理無の場合「－」）
     */
    public HashMap<Integer, String> toStockMap(List<GoodsSearchResultForOrderRegistDto> goodsSearchResultDtoList) {

        HashMap<Integer, String> stockMap = new HashMap<>();

        for (GoodsSearchResultForOrderRegistDto goodsSearchResultDto : goodsSearchResultDtoList) {

            // 商品SEQを取得
            Integer goodsSeq = goodsSearchResultDto.getGoodsSeq();

            /* 販売可能在庫数欄に入る値を作成 */

            String strSalesPossibleStock = "";
            // 在庫管理ありの場合
            if (HTypeStockManagementFlag.ON.getValue()
                                           .equals(goodsSearchResultDto.getStockManagementFlag().getValue())) {
                // 変換Helper取得
                ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);
                strSalesPossibleStock = conversionUtility.toString(
                                new DecimalFormat("#,###").format(goodsSearchResultDto.getSalesPossibleStock()));

                // 在庫管理なしの場合
            } else {
                strSalesPossibleStock = "－";
            }

            // マップにセット
            stockMap.put(goodsSeq, strSalesPossibleStock);

        }

        return stockMap;
    }

    /**
     * 作業用受注DTOの受注商品リストへ商品を追加<br/>
     *
     * @param goodsSearchModel     商品検索ページ
     * @param orderGoodsEntityList 追加受注商品リスト
     */
    public void addOrderGoodsToWorkReceiveOrderDto(GoodsSearchModel goodsSearchModel,
                                                   List<OrderGoodsEntity> orderGoodsEntityList) {
        ReceiveOrderDto modified = goodsSearchModel.getModifiedReceiveOrder();
        List<OrderGoodsEntity> list = modified.getOrderDeliveryDto().getOrderGoodsEntityList();
        if (list != null && !list.isEmpty()) {
            Integer orderConsecutiveNo = list.get(0).getOrderConsecutiveNo();
            for (OrderGoodsEntity entity : orderGoodsEntityList) {
                entity.setOrderConsecutiveNo(orderConsecutiveNo);
            }
            list.addAll(orderGoodsEntityList);
        }

    }

    /**
     * カテゴリパスマップ<br/>
     *
     * @param model 在庫集計ページ
     * @param list  カテゴリエンティティリスト
     */
    public void setCategoryPathMap(GoodsSearchModel model, List<CategoryEntity> list) {
        Map<String, String> map = new HashMap<>();
        for (CategoryEntity entity : list) {
            map.put(entity.getCategoryId(), entity.getCategoryPath());
        }
        model.setCategoryPathMap(map);
    }
}
