/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.cart.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCartGoodsMergeFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReserveDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dao.cart.CartGoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsForTakeOverDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsRegistCheckDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetSaleCheckResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.cart.CartGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.cart.CalendarNotSelectDateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.cart.CartGoodsRegistCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.CartUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.web.PageInfo;
import jp.co.hankyuhanshin.itec.hmbase.util.AppLevelFacesMessageUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * カート投入チェック<br/>
 * カートに投入されている商品内容をチェックし、カート商品チェックメッセージマップを返す<br/>
 *
 * @author ozaki
 * @author Nishigaki Mio (Itec) 2011/09/01 チケット #2692 対応
 * @author Nishigaki Mio (Itec) 2011/12/20 チケット #2285 対応
 * @author sugita(itec) 2012/01/17 チケット #2777 対応
 * @author matsumoto(itec) 2012/02/06 #2761 対応
 * @author Kaneko (itec) 2012/08/17 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class CartGoodsRegistCheckLogicImpl extends AbstractShopLogic implements CartGoodsRegistCheckLogic {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CartGoodsRegistCheckLogicImpl.class);

    /**
     * カート商品DAO
     */
    private final CartGoodsDao cartGoodsDao;

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
     * 変換Utility取得
     */
    private final ConversionUtility conversionUtility;

    /**
     * カレンダー選択不可日付Logic
     */
    private final CalendarNotSelectDateLogic calendarNotSelectDateLogic;
    // 2023-renew No14 to here

    @Autowired
    public CartGoodsRegistCheckLogicImpl(CartGoodsDao cartGoodsDao,
                                         GoodsUtility goodsUtility,
                                         CartUtility cartUtility,
                                         ConversionUtility conversionUtility,
                                         CalendarNotSelectDateLogic calendarNotSelectDateLogic) {
        this.cartGoodsDao = cartGoodsDao;
        // PDR Migrate Customization from here
        this.goodsUtility = goodsUtility;
        // PDR Migrate Customization to here
        // 2023-renew No14 from here
        this.cartUtility = cartUtility;
        this.conversionUtility = conversionUtility;
        this.calendarNotSelectDateLogic = calendarNotSelectDateLogic;
        // 2023-renew No14 to here
    }

    /**
     * カート投入チェック
     * カートに投入されている商品内容をチェックし、カート商品チェックメッセージマップを返す
     *
     * @param goodsDetailsDto         商品詳細DTO
     * @param count                   数量
     * @param shopSeq                 ショップSEQ
     * @param memberInfoSeq           会員SEQ
     * @param accessUid               端末識別情報
     * @param siteType                サイト区分
     * @param cartGoodsRegistCheckDto カート投入チェックDto
     * @return エラーリスト
     */
    @Override
    public List<CheckMessageDto> execute(GoodsDetailsDto goodsDetailsDto,
                                         BigDecimal count,
                                         Integer shopSeq,
                                         Integer memberInfoSeq,
                                         String accessUid,
                                         HTypeSiteType siteType,
                                         CartGoodsRegistCheckDto cartGoodsRegistCheckDto) {

        List<CheckMessageDto> errorMessageList = new ArrayList<>();

        // (1) パラメータチェック
        checkParam(goodsDetailsDto, memberInfoSeq, accessUid, siteType, errorMessageList);
        if (!errorMessageList.isEmpty()) {
            return errorMessageList;
        }

        // (2) 商品詳細DTOのカート投入チェック
        checkGoodsDetailsDto(goodsDetailsDto, count, shopSeq, memberInfoSeq, accessUid, siteType, errorMessageList,
                             cartGoodsRegistCheckDto.getReserveFlag()
                            );
        if (!errorMessageList.isEmpty()) {
            return errorMessageList;
        }

        // 2023-renew No2 from here
        // (3) 販売可能商品チェック
        checkSaleByWebApi(goodsDetailsDto, cartGoodsRegistCheckDto.getSaleCheckMap(), errorMessageList);
        // 2023-renew No2 to here

        // 2023-renew No14 from here
        // (4) 取りおき可否チェック
        checkReserveAvailability(goodsDetailsDto, cartGoodsRegistCheckDto, errorMessageList);
        // 2023-renew No14 to here

        return errorMessageList;
    }

    // PDR Migrate Customization from here

    /**
     * 一括注文用のカート投入チェック（簡易版）
     * <pre>
     * カートマージなしの場合は以下の場合エラー
     *   ⇒ "カート商品数" ＋ "一括登録商品数" > カート内最大商品数
     *
     * カートマージありの場合は以下の場合エラー
     *   ⇒ カートマージ後の件数("カート商品" ＋ "一括登録商品") > カート内最大商品数
     * </pre>
     *
     * @param registCartGoodsList カート一括登録用の商品情報
     * @param conditionDto        カート商品Dao用検索条件Dto
     */
    @Override
    public void execute(List<CartGoodsForTakeOverDto> registCartGoodsList, CartGoodsForDaoConditionDto conditionDto) {
        // ページングセットアップ
        PageInfo pageInfo = ApplicationContextUtility.getBean(PageInfo.class);
        pageInfo.setPage(1);
        pageInfo.setLimit(Integer.MAX_VALUE);
        pageInfo.setupSelectOptions();
        conditionDto.setPageInfo(pageInfo);

        List<CartGoodsEntity> cartGoodsList = cartGoodsDao.getCartGoodsList(conditionDto);

        // カート内商品数チェック
        if (isCartMerge()) {
            checkWithMerge(cartGoodsList, registCartGoodsList);
        } else {
            checkWithoutMerge(cartGoodsList, registCartGoodsList);
        }
    }

    /**
     * カートマージなしの場合のカート内商品数チェック
     *
     * @param cartGoodsList       カート商品情報
     * @param registCartGoodsList カート一括登録用の商品情報
     */
    protected void checkWithoutMerge(List<CartGoodsEntity> cartGoodsList,
                                     List<CartGoodsForTakeOverDto> registCartGoodsList) {
        int cartGoodsCount = cartGoodsList.size();
        int addGoodsCount = registCartGoodsList.size();
        int maxGoodsCount = getMaxCartGoodsCount();
        if (cartGoodsCount + addGoodsCount > maxGoodsCount) {
            // PDR Migrate Customization from here
            throwMessage(MSGCD_CART_MAX_ITEMS_OVER, new Object[] {maxGoodsCount, cartGoodsCount});
            // PDR Migrate Customization to here
        }
    }

    /**
     * カートマージありの場合のカート内商品数チェック
     *
     * @param cartGoodsList       カート商品情報
     * @param registCartGoodsList カート一括登録用の商品情報
     */
    protected void checkWithMerge(List<CartGoodsEntity> cartGoodsList,
                                  List<CartGoodsForTakeOverDto> registCartGoodsList) {
        // 商品SEQ と オプション入力値をキーに集約する
        Set<String> summaryGoods = new HashSet<>();
        // カート商品
        for (CartGoodsEntity cartGoods : cartGoodsList) {
            summaryGoods.add(StringUtils.join(
                            cartGoods.getGoodsSeq(),
                            // 2023-renew No14 from here
                            cartGoods.getReserveDeliveryDate(),
                            // 2023-renew No14 to here
                            ","
                                             ));
        }
        // カート一括登録用の商品情報
        for (CartGoodsForTakeOverDto cartGoods : registCartGoodsList) {
            summaryGoods.add(StringUtils.join(
                            cartGoods.getGoodsSeq(),
                            // 2023-renew No14 from here
                            cartGoods.getReserveDeliveryDate(),
                            // 2023-renew No14 to here
                            ","
                                             ));
        }
        int maxGoodsCount = getMaxCartGoodsCount();
        if (summaryGoods.size() > maxGoodsCount) {
            // PDR Migrate Customization from here
            throwMessage(MSGCD_CART_MAX_ITEMS_OVER, new Object[] {maxGoodsCount, cartGoodsList.size()});
            // PDR Migrate Customization to here
        }
    }

    /**
     * カートマージありなし設定取得<br/>
     *
     * @return True:マージあり、False：マージなし
     */
    protected boolean isCartMerge() {
        String cartGoodsMergeFlag = PropertiesUtil.getSystemPropertiesValue(CART_GOODS_MERGE);
        return HTypeCartGoodsMergeFlag.CART_MERGE.getValue().equals(cartGoodsMergeFlag);
    }

    /**
     * カート内最大商品件数設定取得<br/>
     * 大まかな流れはPKG標準のソースを流用
     * <p>
     * 心意気商品に関しては非公開状態で登録されるため
     * 「非公開かつ販売」の商品は購入可能にするよう処理を修正
     *
     * @return 最大商品件数
     */
    protected int getMaxCartGoodsCount() {
        String maxCartGoodsCount = PropertiesUtil.getSystemPropertiesValue(SYS_KEY_MAX_CART_GOODS_COUNT);
        BigDecimal maxGoodsCount = new BigDecimal(maxCartGoodsCount);
        return maxGoodsCount.intValue();
    }

    // PDR Migrate Customization to here

    /**
     * パラメータチェック<br/>
     * <pre>
     * ・商品詳細DTOが null でないことをチェック
     * ・会員SEQと端末識別番号がともにnullでないことをチェック
     * ・サイト区分がnullでないことをチェック
     * </pre>
     *
     * @param goodsDetailsDto  商品詳細DTO
     * @param memberInfoSeq    会員SEQ
     * @param accessUid        端末識別情報
     * @param siteType         サイト区分
     * @param errorMessageList エラーメッセージリスト
     * @param customParams     案件用引数
     */
    protected void checkParam(GoodsDetailsDto goodsDetailsDto,
                              Integer memberInfoSeq,
                              String accessUid,
                              HTypeSiteType siteType,
                              List<CheckMessageDto> errorMessageList,
                              Object... customParams) {

        // 商品詳細DTOが null でないことをチェック
        // 会員SEQと端末識別番号がともにnullでないことをチェック
        // サイト区分がnullでないことをチェック
        if (memberInfoSeq == null && accessUid == null) {
            throw new NullPointerException("accessUid");
        }
        ArgumentCheckUtil.assertNotNull("siteType", siteType);

        // 商品存在チェック
        if (goodsDetailsDto == null) {
            errorMessageList.add(makeCheckMessageDto(MSGCD_GOODSDETAILSDTO_NULL, null));
        }
    }

    /**
     * 商品詳細DTOチェック<br/>
     * <pre>
     * ・公開状態チェック
     * ・販売状態チェック
     * ・販売可能在庫チェック
     * ・カート内最大商品数量チェック
     * ・カート内最大商品件数チェック
     * </pre>
     *
     * @param goodsDetailsDto  商品詳細DTO
     * @param count            数量
     * @param shopSeq          ショップSEQ
     * @param memberInfoSeq    会員SEQ
     * @param accessUid        端末識別情報
     * @param siteType         サイト区分
     * @param errorMessageList エラーメッセージリスト
     * @param reserveFlag      取りおきフラグ
     * @param customParams     案件用引数
     */
    protected void checkGoodsDetailsDto(GoodsDetailsDto goodsDetailsDto,
                                        BigDecimal count,
                                        Integer shopSeq,
                                        Integer memberInfoSeq,
                                        String accessUid,
                                        HTypeSiteType siteType,
                                        List<CheckMessageDto> errorMessageList,
                                        HTypeReserveDeliveryFlag reserveFlag,
                                        Object... customParams) {

        // チェックメッセージDtoリストの初期化を行う。
        Timestamp now = new Timestamp(System.currentTimeMillis());

        // (2)-1 公開状態チェック
        checkOpenStatus(goodsDetailsDto, siteType, errorMessageList, now);

        // (2)-2 販売状態チェック
        checkSaleStatus(goodsDetailsDto, siteType, errorMessageList, now);

        // (2)-3-1 カート内最大商品数量チェック
        checkCount(count, errorMessageList);

        // (2)-3-2 カート内最大商品件数チェック
        checkCartGoodsCount(goodsDetailsDto, shopSeq, memberInfoSeq, accessUid, errorMessageList, reserveFlag);

    }

    /**
     * 公開状態をチェック<br/>
     * <pre>
     * </pre>
     *
     * @param goodsDetailsDto  商品詳細DTO
     * @param siteType         サイト区分
     * @param errorMessageList エラーメッセージリスト
     * @param now              現在日付
     * @param customParams     案件用引数
     */
    protected void checkOpenStatus(GoodsDetailsDto goodsDetailsDto,
                                   HTypeSiteType siteType,
                                   List<CheckMessageDto> errorMessageList,
                                   Timestamp now,
                                   Object... customParams) {

        HTypeOpenDeleteStatus openStatus = goodsDetailsDto.getGoodsOpenStatusPC();
        Timestamp openStartTime = goodsDetailsDto.getOpenStartTimePC();
        Timestamp openEndTime = goodsDetailsDto.getOpenEndTimePC();

        // PDR Migrate Customization from here
        // ・商品詳細DTO．公開状態PCが"公開中止"の場合エラー
        if (openStatus.equals(HTypeOpenDeleteStatus.DELETED)) {
            errorMessageList.add(makeCheckMessageDto(MSGCD_OPEN_STATUS_HIKOUKAI, null));
            return;
        }
        // PDR Migrate Customization to here

        // ・公開状態チェックOKの場合、公開期間をチェックする
        // ・サーバのシステム日時 ＜ カート商品DTO．商品詳細DTO．公開開始日時PC の場合エラー
        // ・サーバのシステム日時 ＞ カート商品DTO．商品詳細DTO．公開終了日時PC の場合エラー
        if (openStartTime != null && now.before(openStartTime)) {
            errorMessageList.add(makeCheckMessageDto(MSGCD_OPEN_BEFORE, null));
        } else if (openEndTime != null && now.after(openEndTime)) {
            errorMessageList.add(makeCheckMessageDto(MSGCD_OPEN_END, null));
        }

    }

    /**
     * 販売状態をチェック<br/>
     * <pre>
     * 心意気商品に関しては非販売状態で登録されるため
     * 「公開かつ非販売かつ心意気商品」の商品は購入可能にするよう処理を修正
     * 公開状態のチェックは、このチェック処理が行われる前に済んでいるため、
     * ここでは「非販売状態かつ心意気商品」の場合、購入可能とするよう処理を修正する
     * </pre>
     *
     * @param goodsDetailsDto  商品詳細DTO
     * @param siteType         サイト区分
     * @param errorMessageList エラーメッセージリスト
     * @param now              現在日付
     * @param customParams     案件用引数
     */
    protected void checkSaleStatus(GoodsDetailsDto goodsDetailsDto,
                                   HTypeSiteType siteType,
                                   List<CheckMessageDto> errorMessageList,
                                   Timestamp now,
                                   Object... customParams) {

        HTypeGoodsSaleStatus saleStatus = goodsDetailsDto.getSaleStatusPC();
        Timestamp saleStartTime = goodsDetailsDto.getSaleStartTimePC();
        Timestamp saleEndTime = goodsDetailsDto.getSaleEndTimePC();

        // PDR Migrate Customization from here
        // 心意気商品の場合、販売状態が非販売でもエラーとしない
        if (goodsUtility.isEmotionPriceGoods(goodsDetailsDto)) {
            // ・カート商品DTO．商品詳細DTO．販売状態（ PC/携帯 ）が"販売中止" の場合エラー
            if (saleStatus.equals(HTypeGoodsSaleStatus.DELETED)) {
                errorMessageList.add(makeCheckMessageDto(MSGCD_SALE_STATUS_HIHANBAI, null));
                return;
            }
        } else {
            // ・カート商品DTO．商品詳細DTO．販売状態PCが"非販売" の場合エラー
            // ・カート商品DTO．商品詳細DTO．販売状態PCが"販売中止" の場合エラー
            if (saleStatus.equals(HTypeGoodsSaleStatus.NO_SALE) || saleStatus.equals(HTypeGoodsSaleStatus.DELETED)) {
                errorMessageList.add(makeCheckMessageDto(MSGCD_SALE_STATUS_HIHANBAI, null));
                return;
            }
            // PDR Migrate Customization to here
        }

        // ・販売状態チェックOKの場合、販売期間をチェックする
        // ・サーバのシステム日時 ＜ カート商品DTO．商品詳細DTO．販売開始日時PC の場合エラー
        // ・サーバのシステム日時 ＞ カート商品DTO．商品詳細DTO．販売終了日時PC の場合エラー
        if (saleStartTime != null && now.before(saleStartTime)) {
            errorMessageList.add(makeCheckMessageDto(MSGCD_SALE_BEFORE, null));
        } else if (saleEndTime != null && now.after(saleEndTime)) {
            errorMessageList.add(makeCheckMessageDto(MSGCD_SALE_END, null));
        }
    }

    /**
     * カート内最大商品数量を超えていないかチェック<br/>
     * <pre>
     * </pre>
     *
     * @param count            数量
     * @param errorMessageList エラーメッセージリスト
     * @param customParams     案件用引数
     */
    protected void checkCount(BigDecimal count, List<CheckMessageDto> errorMessageList, Object... customParams) {

        // ・数量 ＞ 9999 の場合 ※カート商品.カート内商品数量がNUMBER(4)のため
        // チェックエラーの場合はチェックメッセージDTOを作成（カート内最大商品数量超過チェックメッセージ）し、チェックメッセージDTOリストに追加する。
        if (count.compareTo(new BigDecimal(9999)) <= 0) {
            return;
        }
        errorMessageList.add(makeCheckMessageDto(MSGCD_CART_GOODS_CNT_MAX_OVER, null));
    }

    /**
     * カート内最大商品件数を超えていないかチェック<br/>
     * <pre>
     * </pre>
     *
     * @param goodsDetailsDto  商品詳細DTO
     * @param shopSeq          ショップSEQ
     * @param memberInfoSeq    会員SEQ
     * @param accessUid        端末識別情報
     * @param errorMessageList エラーメッセージリスト
     * @param reserveFlag      取りおきフラグ
     * @param customParams     案件用引数
     */
    protected void checkCartGoodsCount(GoodsDetailsDto goodsDetailsDto,
                                       Integer shopSeq,
                                       Integer memberInfoSeq,
                                       String accessUid,
                                       List<CheckMessageDto> errorMessageList,
                                       HTypeReserveDeliveryFlag reserveFlag,
                                       Object... customParams) {

        // カート商品Daoのカート商品件数取得処理を実行する。
        // メソッド カート内商品件数 getCartGoodsCount( ショップSEQ, 端末識別情報, 会員SEQ)
        // カート内最大商品件数を取得
        // カート内最大商品件数 ＝ （システムプロパティから取得）
        // カート内商品件数 ≧ カート内最大商品件数 の場合
        // チェックエラーの場合はチェックメッセージDTOを作成（カート内最大商品件数超過チェックメッセージ）し、チェックメッセージDTOリストに追加する。
        int cartGoodsCount = cartGoodsDao.getCartGoodsCount(shopSeq, accessUid, memberInfoSeq);
        String maxCartGoodsCount = PropertiesUtil.getSystemPropertiesValue(SYS_KEY_MAX_CART_GOODS_COUNT);
        BigDecimal maxGoodsCount = new BigDecimal(maxCartGoodsCount);

        // カートに投入した商品の件数と、システムプロパティに設定されているカート内最大商品件数を比較
        if (cartGoodsCount < maxGoodsCount.intValue()) {
            return;
        }

        // 同一商品がカート内にある場合登録件数をマージ
        // カート内同一商品マージを実施する・しないの設定値を取得
        String cartGoodsMergeFlag = PropertiesUtil.getSystemPropertiesValue(CART_GOODS_MERGE);

        // カートマージする場合
        // カートに登録されている件数 = 最大登録件数 の場合に処理実施
        // カートに存在する商品を投入した場合は登録件数オーバーのエラーを表示させず、数量のマージを実施するために下記処理を実施
        if (HTypeCartGoodsMergeFlag.CART_MERGE.getValue().equals(cartGoodsMergeFlag)
            // 2023-renew No14 from here
            // 且つ セールde予約商品でない場合
            && HTypeReserveDeliveryFlag.OFF.equals(reserveFlag)
            // 2023-renew No14 to here
        ) {

            // 同一商品が存在するか確認
            boolean existFlag = checkCartGoodsExist(accessUid, goodsDetailsDto, memberInfoSeq);

            // 同一商品が存在する場合
            // エラーメッセージ表示しない
            if (existFlag) {
                return;
            }
        }
        errorMessageList.add(makeCheckMessageDto(MSGCD_CART_GOODS_MAX_OVER, new Object[] {maxCartGoodsCount}));
    }

    /**
     * カート登録件数マージ<br/>
     * カート内の商品情報（商品番号、オプション入力値、会員番号）を取得し、カート投入商品と比較<br/>
     *
     * @param accessUid       端末識別情報
     * @param goodsDetailsDto 商品詳細DTO
     * @param memberInfoSeq   会員SEQ
     * @return 同一商品存在フラグ
     */
    protected boolean checkCartGoodsExist(String accessUid, GoodsDetailsDto goodsDetailsDto, Integer memberInfoSeq) {

        CartGoodsEntity cartGoodsEntity = ApplicationContextUtility.getBean(CartGoodsEntity.class);

        cartGoodsEntity.setAccessUid(accessUid);
        cartGoodsEntity.setGoodsSeq(goodsDetailsDto.getGoodsSeq());
        cartGoodsEntity.setMemberInfoSeq(memberInfoSeq);

        // 既存カート商品と投入した商品で同一の商品がないか、端末情報・商品SEQ・オプション入力値・会員番号から判断
        List<CartGoodsEntity> tempCartGoodsList = this.cartGoodsDao.getCartGoodsOverlapList(cartGoodsEntity);
        // 同一商品が存在する場合はカートマージするのでtrueを返す。
        if (!tempCartGoodsList.isEmpty()) {
            return true;
        }
        return false;

    }

    // 2023-renew No2 from here

    /**
     * 販売可否判定チェック
     *
     * @param goodsDetailsDto   商品詳細Dto
     * @param saleCheckMap      販売可否判定Map
     * @param errorMessageList  エラーメッセージリスト
     * @param customParams      案件用引数
     */
    private void checkSaleByWebApi(GoodsDetailsDto goodsDetailsDto,
                                   Map<String, WebApiGetSaleCheckResponseDetailDto> saleCheckMap,
                                   List<CheckMessageDto> errorMessageList,
                                   Object... customParams) {

        // 販売可否判定チェック
        if (cartUtility.checkSaleByWebApi(goodsDetailsDto.getGoodsCode(), saleCheckMap)) {
            errorMessageList.add(makeCheckMessageDto(MSGCD_ERROR_SALE_CHECK_NO, null));
        }

    }

    // 2023-renew No2 to here
    // 2023-renew No14 from here

    /**
     * 取りおき可否チェック
     *
     * @param goodsDetailsDto          商品詳細DTO
     * @param cartGoodsRegistCheckDto  カート投入チェックDto
     * @param errorMessageList         エラーメッセージリスト
     * @param customParams             案件用引数
     */
    private void checkReserveAvailability(GoodsDetailsDto goodsDetailsDto,
                                          CartGoodsRegistCheckDto cartGoodsRegistCheckDto,
                                          List<CheckMessageDto> errorMessageList,
                                          Object... customParams) {

        // 通常のカートINの場合はチェックしない
        if (HTypeReserveDeliveryFlag.OFF.equals(cartGoodsRegistCheckDto.getReserveFlag())) {
            return;
        }

        // セールde予約によるカートINの場合のみ、以降の処理を行う。
        if (cartUtility.checkReserveAvailability(goodsDetailsDto.getGoodsCode(), conversionUtility.toTimeStamp(
                                                                 cartGoodsRegistCheckDto.getReserveDeliveryDate()), cartGoodsRegistCheckDto.getReserveFlag(),
                                                 cartGoodsRegistCheckDto.getReserveMap(),
                                                 calendarNotSelectDateLogic.execute()
                                                )) {
            errorMessageList.add(makeCheckMessageDto(MSGCD_NOT_RESERVE, null));
        }

    }

    // 2023-renew No14 to here

    /**
     * CheckMessageDtoを作成
     *
     * @param messageId メッセージID
     * @param args      引数
     * @return CheckMessageDto
     */
    protected CheckMessageDto makeCheckMessageDto(String messageId, Object[] args) {
        CheckMessageDto checkMessageDto = getComponent(CheckMessageDto.class);

        checkMessageDto.setMessageId(messageId);
        checkMessageDto.setMessage(getMessage(messageId));
        checkMessageDto.setArgs(args);

        return checkMessageDto;
    }

    /**
     * メッセージ取得
     *
     * @param errorCode コード
     * @return メッセージ
     */
    protected String getMessage(String errorCode) {
        return AppLevelFacesMessageUtil.getAllMessage(errorCode, null).getMessage();
    }

}
