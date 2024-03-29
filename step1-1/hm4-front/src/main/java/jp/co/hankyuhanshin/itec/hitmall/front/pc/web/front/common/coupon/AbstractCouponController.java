/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.coupon;

import jp.co.hankyuhanshin.itec.hitmall.api.webapi.WebapiApi;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiAddCouponRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiAddCouponResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetCouponListRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetCouponListResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfoBase;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiAddCouponResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiAddCouponResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetCouponListResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetCouponListResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.coupon.CouponHelper;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractController;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * クーポン関連抽象コントローラー
 *
 * @author ota-s5
 */
// 2023-renew No24 from here
public class AbstractCouponController extends AbstractController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCouponController.class);

    /**
     * cookie関連の処理でエラーが発生した場合
     */
    public static final String MSGCD_COOKIE_ERR = "PDR-0436-010-A-";

    /**
     * アトリビュート名（クーポン取得成功ダイアログ用）
     */
    public static final String COUPON_NAME_DIALOG_ATTRIBUTE = "couponNameAdd";

    /**
     * Cookieキー接頭辞
     */
    public static final String COOKIE_KEY_PREFIX = "couponCode";

    /**
     * Cookie値区切り
     */
    public static final String COOKIE_VALUE_SEPARATOR = "/";
    /**
     * WEB-APIApi
     */
    private final WebapiApi webapiApi;

    /**
     * クーポンHelper
     */
    private final CouponHelper couponHelper;

    /**
     * CommonInfoUtility
     */
    private final CommonInfoUtility commonInfoUtility;

    /**
     * コンストラクタ
     *
     * @param webapiApi WEB-APIApi
     * @param couponHelper クーポンHelper
     * @param commonInfoUtility 共通情報ヘルパークラス
     */
    @Autowired
    public AbstractCouponController(WebapiApi webapiApi,
                                    CouponHelper couponHelper,
                                    CommonInfoUtility commonInfoUtility) {
        this.webapiApi = webapiApi;
        this.couponHelper = couponHelper;
        this.commonInfoUtility = commonInfoUtility;
    }

    /** ***********　Protected Method Start　*********** **/

    /**
     * クーポン関連のメッセージ表示用のアトリビュート名を取得する
     *
     * @return アトリビュート名
     */
    protected String getAttributeName() {
        // デフォルトは他メッセージと同じにしておく。
        // クーポン関連だけメッセージ表示を出し分けしたい場合は、継承先でオーバーライドして変更する
        return FLASH_MESSAGES;
    }

    /**
     * 利用可能クーポン一覧取得APIの呼び出しを行い、セッション情報の利用可能クーポン一覧数を更新する
     * ※エラーが発生した場合、throwはせずに画面表示項目を生かしたままエラーメッセージを表示する
     *
     * @param model              モデル
     * @param redirectAttributes リダイレクトアトリビュート
     * @return 利用可能クーポン一覧取得 詳細情報
     */
    protected List<WebApiGetCouponListResponseDetailDto> executeWebApiGetCouponListAndSetCouponCount(Model model,
                                                                                                     RedirectAttributes redirectAttributes) {
        // 利用可能クーポン一覧取得APIの呼び出し
        WebApiGetCouponListResponseDto webApiGetCouponListResponseDto =
                        executeWebApiGetCouponList(model, redirectAttributes);

        // セッション情報の利用可能クーポン一覧数を更新
        commonInfoUtility.setCouponCount(getCommonInfo(), webApiGetCouponListResponseDto);

        List<WebApiGetCouponListResponseDetailDto> info;
        if (ObjectUtils.isEmpty(webApiGetCouponListResponseDto)) {
            info = new ArrayList<>();
        } else {
            info = webApiGetCouponListResponseDto.getInfo();
        }

        return info;
    }

    /**
     * クーポン取得APIの呼び出しを行い、取得したクーポン名をダイアログ用に保持する
     * ※エラーが発生した場合、throwはせずに画面表示項目を生かしたままエラーメッセージを表示する
     *
     * @param couponCode クーポンコード
     * @param model              モデル
     * @param redirectAttributes リダイレクトアトリビュート
     * @return true：取得成功、 false：取得失敗
     */
    protected boolean executeWebApiAddCouponAndSetCouponName(String couponCode,
                                                             Model model,
                                                             RedirectAttributes redirectAttributes) {
        boolean isAdd = false;

        // クーポン取得APIの呼び出し
        WebApiAddCouponResponseDto webApiAddCouponResponseDto =
                        executeWebApiAddCoupon(couponCode, model, redirectAttributes);

        if (ObjectUtils.isNotEmpty(webApiAddCouponResponseDto)) {
            for (WebApiAddCouponResponseDetailDto webApiAddCouponResponseDetailDto : webApiAddCouponResponseDto.getInfo()) {
                // 取得したクーポン名をダイアログ用に保持
                model.addAttribute(COUPON_NAME_DIALOG_ATTRIBUTE, webApiAddCouponResponseDetailDto.getCouponName());
                isAdd = true;
                break;
            }
        }

        return isAdd;
    }

    /**
     * 選択中のクーポンコードを取得する
     *
     * @param optionValue        オプション値（クーポンコード用）
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @param request            リクエスト
     */
    protected String getSelectCouponCode(String optionValue,
                                         RedirectAttributes redirectAttributes,
                                         Model model,
                                         HttpServletRequest request) {

        String selectCouponCode = null;

        if (StringUtil.isNotEmpty(optionValue)) {
            // GETパラメータ（optionValue）にクーポンコードが存在する場合、その値を返す
            // ※設計にはないが、カート画面の既存実装に存在するので一応残しておく
            selectCouponCode = optionValue;
        } else {
            // cookieに保持されたクーポンコードを返す
            try {
                selectCouponCode = getCookieCouponCode(request);
            } catch (Exception e) {
                LOGGER.error("例外処理が発生しました", e);
                addWarnMessage(MSGCD_COOKIE_ERR, new String[] {}, redirectAttributes, model, getAttributeName());
                selectCouponCode = null;
            }
        }

        return selectCouponCode;
    }

    /**
     * セッション情報のクーポン関連情報を設定する
     * （基幹マスタに存在しない場合、セッションクリア）
     *
     * @param couponCode クーポンコード
     * @param webApiGetCouponListResponseDetailDtoList 利用可能クーポン一覧取得 詳細情報
     * @param request リクエスト
     * @param response レスポンス
     * @return 設定したクーポン名（基幹マスタに存在しない場合、NULL）
     */
    protected String setSessionCouponInfo(String couponCode,
                                          List<WebApiGetCouponListResponseDetailDto> webApiGetCouponListResponseDetailDtoList,
                                          HttpServletRequest request,
                                          HttpServletResponse response) {
        String couponName = null;

        // マップ変換
        Map<String, WebApiGetCouponListResponseDetailDto> couponInfoMap = new HashMap<>();
        webApiGetCouponListResponseDetailDtoList.forEach(item -> couponInfoMap.put(item.getCouponNo(), item));

        if (couponInfoMap.containsKey(couponCode)) {
            // マップに存在する場合、セッション情報のクーポン関連情報を設定
            WebApiGetCouponListResponseDetailDto dto = couponInfoMap.get(couponCode);
            CommonInfoBase commonInfoBase = getCommonInfo().getCommonInfoBase();
            commonInfoBase.setCouponCode(dto.getCouponNo());
            commonInfoBase.setCouponName(dto.getCouponName());
            commonInfoBase.setCouponConditions(dto.getCouponConditions());
            commonInfoBase.setCouponExplain(dto.getCouponExplain());
            couponName = dto.getCouponName();
        } else {
            // 存在しない場合、cookie/セッション等に保持されたクーポン関連情報を一括削除
            deleteCouponInfo(couponCode, request, response);
        }

        return couponName;
    }

    /**
     * cookie、セッション等に保持されたクーポン関連情報を一括削除する
     *
     * @param couponCode クーポンコード
     * @param request リクエスト
     * @param response レスポンス
     * @return
     */
    protected void deleteCouponInfo(String couponCode, HttpServletRequest request, HttpServletResponse response) {
        // cookieから削除
        try {
            deleteCookieCouponCode(couponCode, request, response);
        } catch (Exception e) {
            // cookieに残っても実害はないのでログだけ吐いて何もしない
            LOGGER.error("例外処理が発生しました", e);
        }

        // セッションから削除
        clearSessionCouponInfo();
    }

    /**
     * セッション情報のクーポン関連情報を削除する
     */
    protected void clearSessionCouponInfo() {
        CommonInfoBase commonInfoBase = getCommonInfo().getCommonInfoBase();
        commonInfoBase.setCouponCode(null);
        commonInfoBase.setCouponName(null);
        commonInfoBase.setCouponConditions(null);
        commonInfoBase.setCouponExplain(null);
    }

    /** ***********　Protected Method End　*********** **/
    /** ***********　Private Method Start　*********** **/

    /**
     * WEB-API連携 利用可能クーポン一覧取得
     * 結果を返却します。
     * ※エラーが発生した場合、throwはせずに画面表示項目を生かしたままエラーメッセージを表示する
     * ※モデル,リダイレクトアトリビュートがNullの場合、エラー時は何もしない（ログ出力のみ）
     *
     * @param model              モデル
     * @param redirectAttributes リダイレクトアトリビュート
     * @return 利用可能クーポン一覧取得
     */
    private WebApiGetCouponListResponseDto executeWebApiGetCouponList(Model model,
                                                                      RedirectAttributes redirectAttributes) {

        WebApiGetCouponListRequest webApiGetCouponListRequest = new WebApiGetCouponListRequest();
        webApiGetCouponListRequest.setCustomerNo(commonInfoUtility.getCustomerNo(getCommonInfo()));

        WebApiGetCouponListResponse webApiGetCouponListResponse = null;
        try {
            webApiGetCouponListResponse = webapiApi.getCouponList(webApiGetCouponListRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.warn("基幹との通信にて例外処理が発生しました", e);
            if (model != null && redirectAttributes != null) {
                addMessage(model, redirectAttributes, buildMessageFromHttpClientErrorException(e), getAttributeName());
            }
        }

        return couponHelper.toWebApiGetCouponListResponseDto(webApiGetCouponListResponse);
    }

    /**
     * WEB-API連携 クーポン取得
     * 結果を返却します。
     * ※エラーが発生した場合、throwはせずに画面表示項目を生かしたままエラーメッセージを表示する
     * ※モデル,リダイレクトアトリビュートがNullの場合、エラー時は何もしない（ログ出力のみ）
     *
     * @param couponCode クーポンコード
     * @param model              モデル
     * @param redirectAttributes リダイレクトアトリビュート
     * @return WEB -API連携レスポンスDTO クーポン取得
     */
    private WebApiAddCouponResponseDto executeWebApiAddCoupon(String couponCode,
                                                              Model model,
                                                              RedirectAttributes redirectAttributes) {

        WebApiAddCouponRequest webApiAddCouponRequest = new WebApiAddCouponRequest();
        webApiAddCouponRequest.setCouponNo(couponCode);
        webApiAddCouponRequest.setCustomerNo(commonInfoUtility.getCustomerNo(getCommonInfo()));

        WebApiAddCouponResponse webApiAddCouponResponse = null;
        try {
            webApiAddCouponResponse = webapiApi.addCoupon(webApiAddCouponRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            if (model != null && redirectAttributes != null) {
                addMessage(model, redirectAttributes, buildMessageFromHttpClientErrorException(e), getAttributeName());
            }
        }

        return couponHelper.toWebApiAddCouponResponseDto(webApiAddCouponResponse);
    }

    /**
     * cookieに保持されたクーポンコードを取得する
     *
     * @param request リクエスト
     */
    private String getCookieCouponCode(HttpServletRequest request) {

        String couponCode = null;
        String cookieItem;
        String userId = getCommonInfo().getCommonInfoUser().getMemberInfoId();

        // パラメータを設定
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cook : cookies) {
                if (cook.getName().startsWith(COOKIE_KEY_PREFIX)) {
                    cookieItem = cook.getValue();
                    if (cookieItem != null) {
                        String[] cookieValue = cookieItem.split(COOKIE_VALUE_SEPARATOR);
                        if (userId.equals(cookieValue[0])) {
                            couponCode = cookieValue[1];
                        }
                    }
                }
            }
        }
        return couponCode;
    }

    /**
     * cookieに保持されたクーポンコードを削除する
     *
     * @param couponCode クーポンコード
     * @param request リクエスト
     * @param response レスポンス
     * @return
     */
    private void deleteCookieCouponCode(String couponCode, HttpServletRequest request, HttpServletResponse response) {

        if (StringUtil.isEmpty(couponCode)) {
            return;
        }

        // cookieに保持された値
        String cookieValue = getCommonInfo().getCommonInfoUser().getMemberInfoId() + "/" + couponCode;

        // パラメータを設定
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cook : cookies) {
                if (cook.getValue().equals(cookieValue)) {
                    // 登録内容をcookieクラスに設定
                    Cookie cookCouponCode = new Cookie(cook.getName(), couponCode);
                    // 参照可能パスを設定
                    cookCouponCode.setPath("/");
                    // 保持期間を設定
                    cookCouponCode.setMaxAge(0);
                    // cookieを登録
                    response.addCookie(cookCouponCode);
                }
            }
        }
    }

    /** ***********　Private Method End　*********** **/

}
// 2023-renew No24 to here
