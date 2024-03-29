/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.cart.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAlcoholFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeIndividualDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReserveDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetReserveResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetSaleCheckResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.CalendarNotSelectDateEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.cart.CalendarNotSelectDateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.cart.CartGoodsCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.CartUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.CheckMessageUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PDR#004 01_販売可能商品の制御
 * PDR#005 02_商品在庫数の制御
 * PDR#008 05_心意気価格
 * <p>
 * カート商品チェック
 * カートに投入されている商品内容をチェックし、カート商品チェックメッセージマップを返す
 *
 * @author ozaki
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class CartGoodsCheckLogicImpl extends AbstractShopLogic implements CartGoodsCheckLogic {

    /**
     * DateUtility
     */
    private final DateUtility dateUtility;

    /**
     * 受注業務ユーティリティクラス
     */
    private final OrderUtility orderUtility;

    /**
     * CheckMessageUtility
     */
    private final CheckMessageUtility checkMessageUtility;

    /**
     * 会員情報取得Logic
     */
    private final MemberInfoGetLogic memberInfoGetLogic;
    // PDR Migrate Customization from here
    /**
     * GoodsUtility
     */
    private final GoodsUtility goodsUtility;
    // PDR Migrate Customization to here
    // 2023-renew No14 from here
    /**
     * CartUtility
     */
    private final CartUtility cartUtility;

    /**
     * カレンダー選択不可日付Logic
     */
    private final CalendarNotSelectDateLogic calendarNotSelectDateLogic;
    // 2023-renew No14 to here

    @Autowired
    public CartGoodsCheckLogicImpl(DateUtility dateUtility,
                                   OrderUtility orderUtility,
                                   CheckMessageUtility checkMessageUtility,
                                   MemberInfoGetLogic memberInfoGetLogic,
                                   GoodsUtility goodsUtility,
                                   CartUtility cartUtility,
                                   CalendarNotSelectDateLogic calendarNotSelectDateLogic) {
        this.dateUtility = dateUtility;
        this.orderUtility = orderUtility;
        this.checkMessageUtility = checkMessageUtility;
        this.memberInfoGetLogic = memberInfoGetLogic;
        // PDR Migrate Customization from here
        this.goodsUtility = goodsUtility;
        // PDR Migrate Customization to here
        // 2023-renew No14 from here
        this.cartUtility = cartUtility;
        this.calendarNotSelectDateLogic = calendarNotSelectDateLogic;
        // 2023-renew No14 to here
    }

    /**
     * カート商品チェック
     * カートに投入されている商品内容をチェックし、カート商品チェックメッセージマップを返す
     * <pre>
     * 販売可能商品制御を追加
     * </pre>
     *
     * @param cartDto          カート情報
     * @param siteType         サイト区分
     * @param isLogin          会員ログインしているか否かの判定
     * @param businessType     顧客区分
     * @param confDocumentType 確認書類区分
     * @return エラー情報MAP
     */
    @Override
    public Map<Integer, List<CheckMessageDto>> execute(CartDto cartDto,
                                                       HTypeSiteType siteType,
                                                       Boolean isLogin,
                                                       String businessType,
                                                       String confDocumentType,
                                                       Integer memberInfoSeq) {
        Map<Integer, List<CheckMessageDto>> errorMessageMap = new HashMap<>();

        // (1) パラメータチェック
        // サイト区分が null でないかをチェック
        ArgumentCheckUtil.assertNotNull("siteType", siteType);
        // カートDTOが null の場合、空のマップオブジェクトを返してLogicの処理を終了。
        // カートDTO．カート商品DTOリストが null の場合、空のマップオブジェクトを返してLogicの処理を終了。
        if (cartDto == null || cartDto.getCartGoodsDtoList() == null) {
            return errorMessageMap;
        }

        // (2) 初期処理
        // カート内商品件数 ＝ 0
        // カート商品チェックメッセージマップの初期化を行う
        BigDecimal cartGoodsCount = BigDecimal.ZERO;

        // （パラメータ）カートDTO．カート商品DTOリストの件数分、(3)のカート商品チェックを繰り返す
        // (3) カート商品チェック
        // チェックメッセージDTOリストの初期化を行う
        // 2023-renew No14 from here
        Map<String, BigDecimal> goodsCountMapForcheckSalesPossibleStock = new HashMap<>();
        Map<Integer, BigDecimal> goodsCountMapForCheckPurchasedMax = new HashMap<>();
        // 2023-renew No14 to here

        Timestamp now = dateUtility.getCurrentTime();
        BigDecimal zeroStock = BigDecimal.ZERO;

        // 2023-renew No14 from here
        boolean maxCartGoodsCountFlg = false;
        // 2023-renew No14 to here

        // PDR Migrate Customization from here
        boolean maxCartGoodsPriceFlg = false;
        // PDR Migrate Customization to here

        // 2023-renew No14 from here
        // カレンダー選択不可日付リストを取得
        List<CalendarNotSelectDateEntity> calendarNotSelectDateEntityList = calendarNotSelectDateLogic.execute();

        // セールde予約を含むかどうかをMapに保持
        Map<String, Boolean> receiveFlagMap = new HashMap<>();
        for (CartGoodsDto cartGoodsDto : cartDto.getCartGoodsDtoList()) {
            String goodsCode = goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(
                            cartGoodsDto.getGoodsDetailsDto().getGoodsCode());
            if (receiveFlagMap.containsKey(goodsCode)) {
                // 既にMapに存在する商品コードの場合、セールde予約商品の場合はフラグONに更新
                if (HTypeReserveDeliveryFlag.ON.equals(cartGoodsDto.getCartGoodsEntity().getReserveFlag())) {
                    receiveFlagMap.put(goodsCode, true);
                }
            } else {
                // Mapに存在しない商品コードの場合、フラグON／OFFを保持
                receiveFlagMap.put(
                                goodsCode, HTypeReserveDeliveryFlag.ON.equals(
                                                cartGoodsDto.getCartGoodsEntity().getReserveFlag()));
            }
        }
        // 2023-renew No14 to here

        for (CartGoodsDto cartGoodsDto : cartDto.getCartGoodsDtoList()) {

            List<CheckMessageDto> errorMessageList = new ArrayList<>();

            // tmp数量（在庫チェック用） = カート商品DTO．カート商品エンティティ．数量
            // ・カート商品DTOリストのチェック済みデータ内に、同一商品コードのカート商品DTOがある場合
            // tmp数量（在庫チェック用） += （チェック済み）カート商品DTO．カート商品エンティティ．数量
            BigDecimal tmpCountForCheckSalesPossibleStock = cartGoodsDto.getCartGoodsEntity().getCartGoodsCount();
            // 2023-renew No14 from here
            String goodsCode = goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(
                            cartGoodsDto.getGoodsDetailsDto().getGoodsCode());
            if (goodsCountMapForcheckSalesPossibleStock.containsKey(goodsCode)) {
                tmpCountForCheckSalesPossibleStock = tmpCountForCheckSalesPossibleStock.add(
                                goodsCountMapForcheckSalesPossibleStock.get(goodsCode));
            }
            goodsCountMapForcheckSalesPossibleStock.put(goodsCode, tmpCountForCheckSalesPossibleStock);

            // tmp数量（最大購入数チェック用） = カート商品DTO．カート商品エンティティ．数量
            // ・カート商品DTOリストのチェック済みデータ内に、同一商品SEQのカート商品DTOがある場合
            // tmp数量（最大購入数チェック用） += （チェック済み）カート商品DTO．カート商品エンティティ．数量
            BigDecimal tmpCountForCheckPurchasedMax = cartGoodsDto.getCartGoodsEntity().getCartGoodsCount();
            Integer goodsSeq = cartGoodsDto.getCartGoodsEntity().getGoodsSeq();
            if (goodsCountMapForCheckPurchasedMax.containsKey(goodsSeq)) {
                tmpCountForCheckPurchasedMax =
                                tmpCountForCheckPurchasedMax.add(goodsCountMapForCheckPurchasedMax.get(goodsSeq));
            }
            goodsCountMapForCheckPurchasedMax.put(goodsSeq, tmpCountForCheckPurchasedMax);
            // 2023-renew No14 to here

            // ① 商品詳細DTOチェック
            // ※以下、（ PC/携帯 ）はサイト区分でそれぞれ分岐します
            // 公開状態チェック～在庫チェックの間では、エラーとなった時点で後続のチェックを飛ばす

            // 公開状態チェックから、在庫チェックに到達するまでの間で、エラーとなった場合 true
            boolean checkSkipFlg = false;

            // ・公開状態をチェック
            checkSkipFlg = checkOpenStatus(siteType, now, cartGoodsDto, errorMessageList, checkSkipFlg);

            // ・販売状態をチェック
            checkSkipFlg = checkSaleStatus(siteType, now, cartGoodsDto, errorMessageList, checkSkipFlg);

            // ・販売可能在庫をチェック
            checkSalesPossibleStock(zeroStock, cartGoodsDto, errorMessageList, tmpCountForCheckSalesPossibleStock,
                                    checkSkipFlg,
                                    // 2023-renew No14 from here
                                    receiveFlagMap
                                    // 2023-renew No14 to here
                                   );

            // PDR Migrate Customization from here
            // ・購入制限をチェック
            checkPurchaseLimit(cartGoodsDto, errorMessageList);
            // PDR Migrate Customization to here

            // ・最大購入可能数をチェック
            checkPurchasedMax(cartGoodsDto, errorMessageList, tmpCountForCheckPurchasedMax);

            // 2023-renew No2 from here
            // ・販売可否判定チェック
            checkSaleByWebApi(cartGoodsDto, cartDto.getSaleCheckMap(), errorMessageList);
            // 2023-renew No2 to here

            // ・個別配送方法をチェック
            checkIndividualDeliveryType(cartGoodsDto, false, errorMessageList, siteType);

            // ・酒類フラグ、年齢チェック
            checkAlcohol(cartGoodsDto, errorMessageList, isLogin, memberInfoSeq);

            // 2023-renew No14 from here
            // ⑤ カート内最大商品件数超過チェック
            maxCartGoodsCountFlg = checkMaxCartGoodsCount(cartGoodsDto, cartGoodsCount, errorMessageList,
                                                          maxCartGoodsCountFlg
                                                         );
            // 2023-renew No14 to here

            // PDR Migrate Customization from here
            // ⑥ 合計金額超過チェック
            maxCartGoodsPriceFlg = checkMaxCartGoodsPrice(cartDto, errorMessageList, maxCartGoodsPriceFlg);
            // PDR Migrate Customization to here

            // 2023-renew No14 from here
            // 取りおき可否チェック
            checkReserveAvailability(cartGoodsDto, cartDto.getReserveMap(), calendarNotSelectDateEntityList,
                                     errorMessageList
                                    );
            // 2023-renew No14 to here

            // ⑦ カート商品チェックメッセージをマップに追加する
            // ・チェックメッセージDTOリストが 1件以上ある場合、カート商品チェックメッセージマップに追加する
            // キー項目： カート商品DTO．カート商品エンティティ．カートSEQ
            // 設定値： チェックメッセージDTOリスト
            if (errorMessageList.size() > 0) {
                errorMessageMap.put(cartGoodsDto.getCartGoodsEntity().getCartSeq(), errorMessageList);
            }
        }

        return errorMessageMap;
    }

    // PDR Migrate Customization from here

    /**
     * 購入制限をチェック
     *
     * @param cartGoodsDto     カート商品DTO
     * @param errorMessageList エラーメッセージリスト
     */
    protected void checkPurchaseLimit(CartGoodsDto cartGoodsDto, List<CheckMessageDto> errorMessageList) {

        // 在庫チェックのエラー有無を確認
        for (CheckMessageDto checkMessageDto : errorMessageList) {
            String msgCd = checkMessageDto.getMessageId();
            if (MSGCD_NO_STOCK.equals(msgCd) || MSGCD_LESS_STOCK.equals(msgCd)) {
                return;
            }
        }

        // 在庫チェックエラーなしの場合のみ購入制限チェックを行う
        Integer saleYesNo = cartGoodsDto.getGoodsDetailsDto().getSaleYesNo();

        if (saleYesNo.equals(1)) {
            // 注文不可の場合
            errorMessageList.add(createCheckMessageDto(MSGCD_NO_POSSIBLE, null));
        }
    }

    // PDR Migrate Customization to here

    /**
     * 公開状態をチェック
     * <pre>
     * 心意気商品に関しては非公開状態で登録されるため
     * 「非公開かつ販売」の商品は購入可能にするよう処理を修正
     * </pre>
     *
     * @param siteType         サイト区分
     * @param now              現日時
     * @param cartGoodsDto     カート商品DTO
     * @param errorMessageList エラーメッセージリスト
     * @param skipCheckFlg     チェック処理スキップフラグ
     * @param customParams     案件用引数
     * @return チェック処理スキップフラグ
     */
    protected boolean checkOpenStatus(HTypeSiteType siteType,
                                      Timestamp now,
                                      CartGoodsDto cartGoodsDto,
                                      List<CheckMessageDto> errorMessageList,
                                      boolean skipCheckFlg,
                                      Object... customParams) {

        // ・カート商品DTO．商品詳細DTO．公開状態PCが"非公開" の場合
        // チェックメッセージDTOを作成（公開状態チェックメッセージ<非公開>）し、チェックメッセージDTOリストに追加する。
        // ・カート商品DTO．商品詳細DTO．公開状態PCが"公開中止" の場合
        // チェックメッセージDTOを作成（公開状態チェックメッセージ<公開中止>）し、チェックメッセージDTOリストに追加する。
        HTypeOpenDeleteStatus openStatus = cartGoodsDto.getGoodsDetailsDto().getGoodsOpenStatusPC();
        Timestamp openStartDate = cartGoodsDto.getGoodsDetailsDto().getOpenStartTimePC();
        Timestamp openEndDate = cartGoodsDto.getGoodsDetailsDto().getOpenEndTimePC();

        // ・商品詳細DTO．公開状態PCが"非公開"の場合エラー
        // ・商品詳細DTO．公開状態PCが"公開中止"の場合エラー
        if (HTypeOpenDeleteStatus.OPEN.equals(openStatus)) {
            // ・公開状態チェックOKの場合、公開期間をチェックする
            // ・サーバのシステム日時 ＜ カート商品DTO．商品詳細DTO．公開開始日時（ PC/携帯 ） の場合
            // チェックメッセージDTOを作成（公開期間チェックメッセージ<公開前>）し、チェックメッセージDTOリストに追加する。
            // ・サーバのシステム日時 ＞ カート商品DTO．商品詳細DTO．公開終了日時（ PC/携帯 ） の場合
            // チェックメッセージDTOを作成（公開期間チェックメッセージ<公開終了>）し、チェックメッセージDTOリストに追加する。
            if (openStartDate != null && now.before(openStartDate)) {
                skipCheckFlg = true;
                errorMessageList.add(createCheckMessageDto(MSGCD_OPEN_BEFORE, null));
            } else if (openEndDate != null && now.after(openEndDate)) {
                skipCheckFlg = true;
                errorMessageList.add(createCheckMessageDto(MSGCD_OPEN_END, null));
            }
            // PDR Migrate Customization from here
        } else if (!HTypeOpenDeleteStatus.NO_OPEN.equals(openStatus)) {
            // ・商品詳細DTO．公開状態が"非公開"以外の場合エラー
            // PDR Migrate Customization to here
            skipCheckFlg = true;
            errorMessageList.add(createCheckMessageDto(MSGCD_OPEN_STATUS_HIKOUKAI, null));
        }
        return skipCheckFlg;
    }

    /**
     * 販売状態をチェック
     * <pre>
     * 心意気商品に関しては非販売状態で登録されるため
     * 「公開かつ非販売かつ心意気商品」の商品は購入可能にするよう処理を修正
     * 公開状態のチェックは、このチェック処理が行われる前に済んでいるため、
     * ここでは「非販売状態かつ心意気商品」の場合、購入可能とするよう処理を修正する
     * </pre>
     *
     * @param siteType         サイト区分
     * @param now              現日時
     * @param cartGoodsDto     カート商品DTO
     * @param errorMessageList エラーメッセージリスト
     * @param skipCheckFlg     チェック処理スキップフラグ
     * @param customParams     案件用引数
     * @return チェック処理スキップフラグ
     */
    protected boolean checkSaleStatus(HTypeSiteType siteType,
                                      Timestamp now,
                                      CartGoodsDto cartGoodsDto,
                                      List<CheckMessageDto> errorMessageList,
                                      boolean skipCheckFlg,
                                      Object... customParams) {

        if (skipCheckFlg) {
            return skipCheckFlg;
        }

        // ・カート商品DTO．商品詳細DTO．販売状態PCが"非販売" の場合
        // チェックメッセージDTOを作成（販売状態チェックメッセージ<非販売>）し、チェックメッセージDTOリストに追加する。
        // ・カート商品DTO．商品詳細DTO．販売状態PCが"販売中止" の場合
        // チェックメッセージDTOを作成（販売状態チェックメッセージ<販売中止>）し、チェックメッセージDTOリストに追加する。
        HTypeGoodsSaleStatus saleStatus = cartGoodsDto.getGoodsDetailsDto().getSaleStatusPC();
        Timestamp saleStartDate = cartGoodsDto.getGoodsDetailsDto().getSaleStartTimePC();
        Timestamp saleEndDate = cartGoodsDto.getGoodsDetailsDto().getSaleEndTimePC();

        if (saleStatus.equals(HTypeGoodsSaleStatus.SALE)) {
            // ・販売状態チェックOKの場合、販売期間をチェックする
            // ・サーバのシステム日時 ＜ カート商品DTO．商品詳細DTO．販売開始日時PC の場合
            // チェックメッセージDTOを作成（販売期間チェックメッセージ<販売前>）し、チェックメッセージDTOリストに追加する。
            // ・サーバのシステム日時 ＞ カート商品DTO．商品詳細DTO．販売終了日時PC の場合
            // チェックメッセージDTOを作成（販売期間チェックメッセージ<販売終了>）し、チェックメッセージDTOリストに追加する。
            if (saleStartDate != null && now.before(saleStartDate)) {
                skipCheckFlg = true;
                errorMessageList.add(createCheckMessageDto(MSGCD_SALE_BEFORE, null));
            } else if (saleEndDate != null && now.after(saleEndDate)) {
                skipCheckFlg = true;
                errorMessageList.add(createCheckMessageDto(MSGCD_SALE_END, null));
            }
        } else {
            // PDR Migrate Customization from here
            // 心意気商品の場合、販売状態が非販売でもエラーとしない
            if (!goodsUtility.isEmotionPriceGoods(cartGoodsDto.getGoodsDetailsDto())) {
                skipCheckFlg = true;
                errorMessageList.add(createCheckMessageDto(MSGCD_SALE_STATUS_HIHANBAI, null));
            }
            // PDR Migrate Customization to here
        }
        return skipCheckFlg;
    }

    // PDR Migrate Customization from here

    /**
     * 合計金額超過チェック
     *
     * @param cartDto              カート内合計金額
     * @param errorMessageList     エラーメッセージリスト
     * @param maxCartGoodsPriceFlg 合計金額超過チェックフラグ
     * @param customParams         案件用引数
     * @return maxCartGoodsPriceFlg 合計金額超過チェックフラグ
     */
    protected boolean checkMaxCartGoodsPrice(CartDto cartDto,
                                             List<CheckMessageDto> errorMessageList,
                                             boolean maxCartGoodsPriceFlg,
                                             Object... customParams) {

        if (maxCartGoodsPriceFlg) {
            return maxCartGoodsPriceFlg;
        }

        // 2回目以降はチェックを行わないためtrue
        maxCartGoodsPriceFlg = true;

        // ・カート内合計金額（税込） ＞ 99,999,999（＝システムプロパティから取得） の場合
        // チェックエラーの場合はチェックメッセージDTOを作成（商品合計金額超過チェックメッセージ）し、チェックメッセージDTOリストに追加する。
        String maxCartGoodsPrice = PropertiesUtil.getSystemPropertiesValue(SYS_KEY_MAX_CART_GOODS_PRICE);
        BigDecimal maxPrice = new BigDecimal(maxCartGoodsPrice);
        if (cartDto.getGoodsTotalPrice().compareTo(maxPrice) == 1) {
            errorMessageList.add(createCheckMessageDto(MSGCD_CART_TOTAL_PRICE_MAX_OVER, null));
        }

        return maxCartGoodsPriceFlg;
    }

    // PDR Migrate Customization to here

    /**
     * 販売可能在庫をチェック
     *
     * @param zeroStock        在庫0
     * @param cartGoodsDto     カート商品DTO
     * @param errorMessageList エラーメッセージリスト
     * @param tmpCount         tmp数量
     * @param skipCheckFlg     チェック処理スキップフラグ
     * @param receiveFlagMap   セールde予約を含むかどうかを保持したMap
     * @param customParams     案件用引数
     */
    protected void checkSalesPossibleStock(BigDecimal zeroStock,
                                           CartGoodsDto cartGoodsDto,
                                           List<CheckMessageDto> errorMessageList,
                                           BigDecimal tmpCount,
                                           boolean skipCheckFlg,
                                           // 2023-renew No14 from here
                                           Map<String, Boolean> receiveFlagMap,
                                           // 2023-renew No14 to here
                                           Object... customParams) {

        BigDecimal salesPossibleStock = cartGoodsDto.getGoodsDetailsDto().getSalesPossibleStock();
        // PDR Migrate Customization from here
        String deliveryYesNo = cartGoodsDto.getGoodsDetailsDto().getDeliveryYesNo();
        if (skipCheckFlg || HTypeStockManagementFlag.ON != cartGoodsDto.getGoodsDetailsDto().getStockManagementFlag()
            || ("1".equals(deliveryYesNo)
                // 2023-renew No14 from here
                && !receiveFlagMap.get(goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(
                        cartGoodsDto.getGoodsDetailsDto().getGoodsCode()))
                            // 2023-renew No14 to here
            ) || salesPossibleStock == null) {
            return;
        }
        // PDR Migrate Customization to here

        // ・カート商品DTO．商品詳細DTO．在庫管理が"管理あり"の場合
        // ・カート商品DTO．商品詳細DTO．販売可能在庫数 ≦ 0 の場合
        // チェックエラーの場合はチェックメッセージDTOを作成（在庫切れチェックメッセージ）し、チェックメッセージDTOリストに追加する。
        // ・カート商品DTO．商品詳細DTO．販売可能在庫数 ≦ 0 でなく、 カート商品DTO．商品詳細DTO．販売可能在庫数 ＜
        // tmp数量 の場合
        // チェックエラーの場合はチェックメッセージDTOを作成（在庫不足チェックメッセージ）し、チェックメッセージDTOリストに追加する。
        if (salesPossibleStock.compareTo(zeroStock) <= 0) {
            errorMessageList.add(createCheckMessageDto(MSGCD_NO_STOCK, null));
        } else if (salesPossibleStock.compareTo(tmpCount) < 0) {
            errorMessageList.add(createCheckMessageDto(MSGCD_LESS_STOCK, null));
        }
    }

    /**
     * 最大購入可能数をチェック
     *
     * @param cartGoodsDto     カート商品DTO
     * @param errorMessageList エラーメッセージリスト
     * @param tmpCount         tmp数量
     * @param customParams     案件用引数
     */
    protected void checkPurchasedMax(CartGoodsDto cartGoodsDto,
                                     List<CheckMessageDto> errorMessageList,
                                     BigDecimal tmpCount,
                                     Object... customParams) {
        // ・tmp数量 ＞ カート商品DTO．商品詳細DTO．商品最大購入可能数 の場合
        // チェックエラーの場合はチェックメッセージDTOを作成（最大購入可能数超過チェックメッセージ）し、チェックメッセージDTOリストに追加する。
        if (cartGoodsDto.getGoodsDetailsDto().getPurchasedMax().intValue() > 0
            && tmpCount.intValue() > cartGoodsDto.getGoodsDetailsDto().getPurchasedMax().intValue()) {
            errorMessageList.add(createCheckMessageDto(MSGCD_PURCHASED_MAX_OVER, null));
        }
    }

    /**
     * 個別配送方法をチェック
     *
     * @param cartGoodsDto     カート商品DTO
     * @param errorMessageList エラーメッセージリスト
     * @param isPeriodicOnly   カート内が定期商品のみの場合、true 不明もしくはそうでない場合、false を指定する
     * @param siteType         サイト区分
     */
    protected void checkIndividualDeliveryType(CartGoodsDto cartGoodsDto,
                                               boolean isPeriodicOnly,
                                               List<CheckMessageDto> errorMessageList,
                                               HTypeSiteType siteType) {
        // 複数配送時は、お届け先ごとに配送方法が異なるためチェックしない
        // 但し、定期商品は複数配送対応しないのでチェックが必要
        if (isMultiDeliSite(siteType) && !isPeriodicOnly) {
            return;
        }

        // ・カート商品DTO．商品詳細DTO．商品個別配送品 = "個別配送" の場合
        // チェックエラーの場合はチェックメッセージDTOを作成（個別配送品通知チェックメッセージ）し、チェックメッセージDTOリストに追加する。
        if (HTypeIndividualDeliveryType.ON.equals(cartGoodsDto.getGoodsDetailsDto().getIndividualDeliveryType())) {
            errorMessageList.add(createCheckMessageDto(MSGCD_INDIVIDUAL_DELIVERY, null));
        }
    }

    /**
     * カート内最大商品件数超過チェック
     *
     * @param cartGoodsDto     カート商品Dto
     * @param cartGoodsCount   カート内商品件数
     * @param errorMessageList エラーメッセージリスト
     * @param maxCartGoodsCountFlg カート内最大商品件数超過チェックフラグ
     * @param customParams     案件用引数
     * @return TRUE：超過、FALSE：超過なし
     */
    protected boolean checkMaxCartGoodsCount(CartGoodsDto cartGoodsDto,
                                             BigDecimal cartGoodsCount,
                                             List<CheckMessageDto> errorMessageList,
                                             boolean maxCartGoodsCountFlg,
                                             Object... customParams) {

        // カート内商品件数に１加算する。
        cartGoodsCount = cartGoodsCount.add(new BigDecimal(1));

        // 2023-renew No14 from here
        if (maxCartGoodsCountFlg) {
            // 当チェックは１度でもエラーになった場合、次ループ以降は省略する
            // ※省略しないと次ループ以降もエラーとなり同じエラーメッセージがセットされ続ける
            return maxCartGoodsCountFlg;
        }
        // 2023-renew No14 to here

        // カート内最大商品件数を取得
        // カート内最大商品件数 ＝ （システムプロパティから取得）
        // カート内商品件数 ＞ カート内最大商品件数 の場合
        // チェックエラーの場合はチェックメッセージDTOを作成（カート内最大商品件数チェックメッセージ）し、チェックメッセージDTOリストに追加する。
        String maxCartGoodsCount = PropertiesUtil.getSystemPropertiesValue(SYS_KEY_MAX_CART_GOODS_COUNT);
        BigDecimal maxGoodsCount = new BigDecimal(maxCartGoodsCount);
        if (cartGoodsCount.compareTo(maxGoodsCount) == 1) {
            errorMessageList.add(createCheckMessageDto(MSGCD_CART_GOODS_MAX_OVER, null));
            // 2023-renew No14 from here
            // １度でもエラーになった場合、次ループ以降は省略するためtrue
            maxCartGoodsCountFlg = true;
            // 2023-renew No14 to here
        }

        // 2023-renew No14 from here
        return maxCartGoodsCountFlg;
        // 2023-renew No14 to here
    }

    /**
     * CheckMessageDto作成
     *
     * @param messageId メッセージID
     * @param args      args
     * @return CheckMessageDto
     */
    protected CheckMessageDto createCheckMessageDto(String messageId, Object[] args) {
        CheckMessageDto checkMessageDto = checkMessageUtility.createCheckMessageDto(messageId, args);
        return checkMessageDto;
    }

    /**
     * 複数配送対応対応サイトか判定する
     *
     * @param siteType サイト区分
     * @return true:対応 false:非対応
     */
    protected boolean isMultiDeliSite(HTypeSiteType siteType) {
        return siteType.isMultiDeliSite();
    }

    /**
     * 酒類フラグと年齢をチェック
     * 対象商品が酒類のかつ、会員の年齢が下限年齢の場合は、エラーメッセージを設定します。<br />
     * ゲストの場合、このメソッドではチェックしません。<br />
     *
     * @param cartGoodsDto     商品詳細DTOリスト
     * @param errorMessageList エラーメッセージリスト
     * @param memberInfoSeq    会員SEQ取得
     */
    protected void checkAlcohol(CartGoodsDto cartGoodsDto,
                                List<CheckMessageDto> errorMessageList,
                                Boolean isLogin,
                                Integer memberInfoSeq) {

        // 酒類の備考メッセージ用コード
        if (HTypeAlcoholFlag.ALCOHOL.equals(cartGoodsDto.getGoodsDetailsDto().getAlcoholFlag())) {
            // 会員か判定。会員なら年齢チェック処理実行
            if (isLogin) {
                // 会員情報取得
                MemberInfoEntity memberEntity = memberInfoGetLogic.execute(memberInfoSeq);
                // 年齢チェック
                if (!orderUtility.isAdult(memberEntity.getMemberInfoBirthday())) {
                    Object[] args = new Object[] {cartGoodsDto.getGoodsDetailsDto().getGoodsGroupName()};
                    errorMessageList.add(createCheckMessageDto(MSGCD_ALCOHOL_CHECK_ERROR, args));
                }
            }
        }
    }

    // 2023-renew No2 from here

    /**
     * 販売可否判定チェック
     *
     * @param cartGoodsDto      カート商品DTO
     * @param saleCheckMap      販売可否判定Map
     * @param errorMessageList  エラーメッセージリスト
     * @param customParams      案件用引数
     */
    private void checkSaleByWebApi(CartGoodsDto cartGoodsDto,
                                   Map<String, WebApiGetSaleCheckResponseDetailDto> saleCheckMap,
                                   List<CheckMessageDto> errorMessageList,
                                   Object... customParams) {

        // 販売可否判定チェック
        if (cartUtility.checkSaleByWebApi(cartGoodsDto.getGoodsDetailsDto().getGoodsCode(), saleCheckMap)) {
            errorMessageList.add(createCheckMessageDto(MSGCD_ERROR_SALE_CHECK_NO, null));
        }

    }

    // 2023-renew No2 to here
    // 2023-renew No14 from here

    /**
     * 取りおき可否チェック
     *
     * @param cartGoodsDto                     カート商品DTO
     * @param reserveMap                       取りおき情報MAP
     * @param calendarNotSelectDateEntityList  カレンダー選択不可日付StringList
     * @param errorMessageList                 エラーメッセージリスト
     */
    private void checkReserveAvailability(CartGoodsDto cartGoodsDto,
                                          Map<String, WebApiGetReserveResponseDetailDto> reserveMap,
                                          List<CalendarNotSelectDateEntity> calendarNotSelectDateEntityList,
                                          List<CheckMessageDto> errorMessageList) {

        // 通常のカートINの場合はチェックしない
        if (HTypeReserveDeliveryFlag.OFF.equals(cartGoodsDto.getCartGoodsEntity().getReserveFlag())) {
            return;
        }

        // セールde予約によるカートINの場合のみ、以降の処理を行う。
        if (cartUtility.checkReserveAvailability(cartGoodsDto.getGoodsDetailsDto().getGoodsCode(),
                                                 cartGoodsDto.getCartGoodsEntity().getReserveDeliveryDate(),
                                                 cartGoodsDto.getCartGoodsEntity().getReserveFlag(), reserveMap,
                                                 calendarNotSelectDateEntityList
                                                )) {
            errorMessageList.add(createCheckMessageDto(MSGCD_NOT_RESERVE, null));
        }

    }

    // 2023-renew No14 to here

}
