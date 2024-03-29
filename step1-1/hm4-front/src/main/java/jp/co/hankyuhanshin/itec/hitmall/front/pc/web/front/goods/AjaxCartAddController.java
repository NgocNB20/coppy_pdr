/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods;

import jp.co.hankyuhanshin.itec.hitmall.api.cart.CartApi;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsAddRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CheckMessageDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfoBase;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.NumberUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.cart.CartDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.cart.CartGoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.cart.ajax.CartResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.validator.HNumberValidator;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * カート追加（Ajax）Controllerクラス
 *
 * @author kaneda
 */
@RestController
@RequestMapping("/")
public class AjaxCartAddController extends AbstractController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AjaxCartAddController.class);

    /**
     * カートAPI
     */
    private final CartApi cartApi;

    /**
     * カート追加（Ajax）Helperクラス
     */
    private final AjaxCartAddJSONHelper jsonHelper;

    /**
     * 変換ユーティリティクラス
     */
    private final ConversionUtility conversionUtility;

    // 2023-renew No14 from here
    /**
     * CommonInfoUtility
     */
    private final CommonInfoUtility commonInfoUtility;
    // 2023-renew No14 to here

    /**
     * バリデーションエラーメッセージ
     */
    protected static final String REQUIRED_MESSAGE_ID =
                    "jp.co.hankyuhanshin.itec.hmbase.validator.HRequiredValidator.INPUT_detail";
    protected static final String NUMBER_MESSAGE_ID =
                    "jp.co.hankyuhanshin.itec.hmbase.validator.HNumberValidator.NOT_NUMBER_detail";
    protected static final String FRACTION_MESSAGE_ID_MAX_ZERO =
                    "jp.co.hankyuhanshin.itec.hmbase.validator.HNumberLengthValidator.FRACTION_MAX_ZERO_detail";
    protected static final String NOT_IN_RANGE_MESSAGE_ID =
                    "jp.co.hankyuhanshin.itec.hmbase.validator.HNumberRangeValidator.NOT_IN_RANGE_detail";
    // 2023-renew No11 from here
    protected static final String MSGCD_GOODS_NOT_FOUND_ERROR = "PDR-2023RENEW-11-001-";
    // 2023-renew No11 to here

    /**
     * コンストラクタ
     */
    @Autowired
    public AjaxCartAddController(CartApi cartApi,
                                 AjaxCartAddJSONHelper jsonHelper,
                                 ConversionUtility conversionUtility,
                                 CommonInfoUtility commonInfoUtility) {
        this.cartApi = cartApi;
        this.jsonHelper = jsonHelper;
        this.conversionUtility = conversionUtility;
        // 2023-renew No14 from here
        this.commonInfoUtility = commonInfoUtility;
        // 2023-renew No14 to here
    }

    /**
     * カート画面へ遷移せずにカート追加する(Ajax通信)
     *
     * @param cartAddModel カート追加（Ajax）Model
     * @return カート追加結果(CartResponseDto)
     */
    @GetMapping("/ajaxCartAdd")
    @ResponseBody
    public CartResponseDto ajaxCartAdd(AjaxCartAddModel cartAddModel) {

        // セッション切れ判定
        boolean okSession = preDoAjax();
        if (!okSession) {
            return jsonHelper.toCartIFResponse(new ArrayList<>(), new ArrayList<>(), null, okSession);
        }

        // パラメータチェック
        List<CheckMessageDto> validatorErrorList = paramCheck(cartAddModel);

        List<CheckMessageDto> errorList = new ArrayList<>();
        if (validatorErrorList != null && validatorErrorList.isEmpty()) {
            // カート商品追加サービスの実行

            String goodsCode = cartAddModel.getGcd();
            BigDecimal count = BigDecimal.valueOf(conversionUtility.toInteger(cartAddModel.getGcnt()));
            String accessUid = getCommonInfo().getCommonInfoBase().getAccessUid();
            String siteType = HTypeSiteType.FRONT_PC.getValue();
            CartGoodsAddRequest cartGoodsAddRequest =
                            jsonHelper.toCartGoodsAddRequest(goodsCode, count, accessUid, siteType,
                                                             // 2023-renew No14 from here
                                                             commonInfoUtility.getMemberInfoEntity(getCommonInfo())
                                                             // 2023-renew No14 from here
                                                            );

            List<CheckMessageDtoResponse> checkMessageDtoResponseList = null;
            try {
                checkMessageDtoResponseList = cartApi.registCartGoodsAdd(cartGoodsAddRequest);
            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                // AppLevelListExceptionへ変換
                addAppLevelListException(e);
                throwMessage();
            }

            errorList = jsonHelper.toCheckMessageDtoList(checkMessageDtoResponseList);
            afterAddProcess(errorList, cartAddModel);
        }

        // カート商品リスト取得サービスの実行

        String accessUid = getCommonInfo().getCommonInfoBase().getAccessUid();
        Integer memberInfoSeq = getCommonInfo().getCommonInfoUser().getMemberInfoSeq();
        String siteType = HTypeSiteType.FRONT_PC.getValue();
        String orderField = "registTime";
        Boolean orderAsc = false;

        CartGoodsGetRequest cartGoodsGetRequest =
                        jsonHelper.toCartGoodsGetRequest(accessUid, memberInfoSeq, siteType, orderField, orderAsc);

        CartDtoResponse cartDtoResponse = null;
        try {
            cartDtoResponse = cartApi.getCartGoods(cartGoodsGetRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        CartDto cartDto = jsonHelper.toCartDto(cartDtoResponse);

        // カート情報を更新する
        CommonInfoBase commonInfoBase = getCommonInfo().getCommonInfoBase();
        commonInfoBase.setCartGoodsSumCount(cartDto.getGoodsTotalCount());
        BigDecimal cartGoodsSumPrice = BigDecimal.ZERO;
        for (CartGoodsDto cartGoodsDto : cartDto.getCartGoodsDtoList()) {
            BigDecimal cartGoodsCount = cartGoodsDto.getCartGoodsEntity().getCartGoodsCount();
            BigDecimal cartGoodsPrice = cartGoodsDto.getGoodsDetailsDto().getGoodsPrice();
            cartGoodsSumPrice = cartGoodsSumPrice.add(cartGoodsCount.multiply(cartGoodsPrice));
        }
        commonInfoBase.setCartGoodsSumPrice(cartGoodsSumPrice);

        // レスポンスの作成
        return jsonHelper.toCartIFResponse(validatorErrorList, errorList, cartDto, okSession);
    }

    /**
     * セッション切れ判定
     *
     * @return セッションがただしいか true:正しい / false:不正
     */
    protected boolean preDoAjax() {
        if (null == this.getCommonInfo()) {
            return false;
        }
        return true;
    }

    /**
     * パラメータ―チェック処理
     *
     * @param cartAddModel カート追加（Ajax）Model
     * @return バリデータエラーメッセージリスト
     */
    protected List<CheckMessageDto> paramCheck(AjaxCartAddModel cartAddModel) {
        List<CheckMessageDto> validatorErrorList = new ArrayList<>();

        /* 商品コード バリデータ */
        if (StringUtils.isEmpty(cartAddModel.getGcd()) || StringUtils.isEmpty(
                        cartAddModel.getGcd().replaceAll("^[\\s　]*", "").replaceAll("[\\s　]*$", ""))) {
            // 必須エラー
            validatorErrorList.add(toCheckMessageDto(MSGCD_GOODS_NOT_FOUND_ERROR, null, true));
        }

        /* 数量 バリデータ */
        // 数値関連Helper取得
        NumberUtility numberUtility = ApplicationContextUtility.getBean(NumberUtility.class);

        if (StringUtils.isEmpty(cartAddModel.getGcnt()) || StringUtils.isEmpty(
                        cartAddModel.getGcnt().replaceAll("^[\\s　]*", "").replaceAll("[\\s　]*$", ""))) {
            // 必須エラー
            validatorErrorList.add(toCheckMessageDto(REQUIRED_MESSAGE_ID, new String[] {"数量"}, true));
        } else if (!numberUtility.isNumber(cartAddModel.getGcnt())) {
            // 数値エラー
            validatorErrorList.add(toCheckMessageDto(NUMBER_MESSAGE_ID, new String[] {"数量"}, true));
        } else if (cartAddModel.getGcnt().matches("\\A[-][0-9]+\\z")) {
            // 数値エラー（正規表現 負の数）
            validatorErrorList.add(
                            toCheckMessageDto(HNumberValidator.MINUS_NUMBER_MESSAGE_ID, new String[] {"数量"}, true));
        } else if (cartAddModel.getGcnt().matches("\\A[-]?[0-9]+\\.[0-9]+\\z")) {
            // 数値エラー（正規表現 整数以外）
            validatorErrorList.add(toCheckMessageDto(FRACTION_MESSAGE_ID_MAX_ZERO, new String[] {"数量"}, true));
        } else if (!cartAddModel.getGcnt().matches("\\A[0-9]+\\z")) {
            // 数値エラー（正規表現）
            validatorErrorList.add(toCheckMessageDto(NUMBER_MESSAGE_ID, new String[] {"数量"}, true));
        } else if (Integer.valueOf(cartAddModel.getGcnt()).intValue() < 1
                   || Integer.valueOf(cartAddModel.getGcnt()).intValue() > 9999) {
            // 数値範囲エラー
            validatorErrorList.add(toCheckMessageDto(NOT_IN_RANGE_MESSAGE_ID, new String[] {"1", "9999", "数量"}, true));
        }

        return validatorErrorList;
    }

    /**
     * カート商品追加サービスの実行後の処理
     *
     * @param errorList    カート商品追加サービスの実行に返されるエラーメッセージ
     * @param cartAddModel カート追加（Ajax）Model
     */
    protected void afterAddProcess(List<CheckMessageDto> errorList, AjaxCartAddModel cartAddModel) {
        if (errorList != null && !errorList.isEmpty()) {
            // カート投入失敗
            for (int i = 0; i < errorList.size(); i++) {
                // エラーメッセージを整形
                CheckMessageDto errorMessage = errorList.get(i);
                errorMessage = toCheckMessageDto(errorMessage.getMessageId(), errorMessage.getArgs(),
                                                 errorMessage.isError()
                                                );
                errorList.set(i, errorMessage);
            }
        }
    }

    /**
     * エラー内容からメッセージリスト作成
     *
     * @param msgCode メッセージコード
     * @param args    パラメータ
     * @param isError エラーフラグ
     * @return エラーメッセージDTO
     */
    protected CheckMessageDto toCheckMessageDto(String msgCode, Object[] args, boolean isError) {
        CheckMessageDto checkMessageDto = ApplicationContextUtility.getBean(CheckMessageDto.class);
        checkMessageDto.setMessageId(msgCode);
        checkMessageDto.setMessage(getMessage(msgCode, args));
        checkMessageDto.setArgs(args);
        checkMessageDto.setError(isError);
        return checkMessageDto;
    }

}
