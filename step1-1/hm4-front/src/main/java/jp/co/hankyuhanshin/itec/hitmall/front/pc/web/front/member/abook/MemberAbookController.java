/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.abook;

import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.MemberInfoApi;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.AddressBookEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.AddressBookRegistRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.AddressBookUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.ZipcodeApi;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.ResultFlagResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.ZipCodeMatchingCheckRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.WebapiApi;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiDelDestinationRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetDestinationRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetDestinationResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.member.WebApiDelDestinationRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.member.WebApiGetDestinationResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.addressbook.AddressBookEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

/**
 * アドレス帳画面 Controller
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@RequestMapping("/member/abook")
@Controller
@SessionAttributes(value = "memberAbookModel")
public class MemberAbookController extends AbstractController {

    /**
     * ログ
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MemberAbookController.class);

    /**
     * モデルクリア時のクリア対象外フィールド
     */
    protected static final String[] CLEAR_EXCLUDED_FIELDS = {"pnum", "limit"};

    /**
     * 不正遷移
     */
    protected static final String MSGCD_REFERER_FAIL = "AMA000201";

    /**
     * 都道府県のプルダウンいじられた
     */
    protected static final String MSGCD_ILLEGAL_PREFECTURE = "AMA000202";

    /**
     * 都道府県と郵便番号の整合性エラー ※W付与
     */
    protected static final String MSGCD_PREFECTURE_CONSISTENCY = "AMA000203W";

    /**
     * 都道府県フィールド名
     **/
    protected static final String FILED_NAME_PREFECTURE = "prefecture";

    /**
     * helper
     */
    private final MemberAbookHelper memberAbookHelper;

    /**
     * 会員Api
     */
    private final MemberInfoApi memberInfoApi;

    /**
     * WEB-APIApi
     */
    private final WebapiApi webapiApi;

    /**
     * 郵便番号住所情報Api
     */
    private final ZipcodeApi zipcodeApi;

    /**
     * コンストラクタ
     *
     * @param memberAbookHelper アドレス帳 Helperクラス
     * @param memberInfoApi
     * @param webapiApi
     * @param zipcodeApi
     */
    @Autowired
    public MemberAbookController(MemberAbookHelper memberAbookHelper,
                                 MemberInfoApi memberInfoApi,
                                 WebapiApi webapiApi,
                                 ZipcodeApi zipcodeApi) {
        this.memberAbookHelper = memberAbookHelper;
        this.memberInfoApi = memberInfoApi;
        this.webapiApi = webapiApi;
        this.zipcodeApi = zipcodeApi;
    }

    /**
     * 一覧画面：初期処理
     *
     * @param memberAbookModel   アドレス帳Model
     * @param redirectAttributes RedirectAttributes
     * @param model              Model
     * @return 一覧画面
     */
    // PDR Migrate Customization from here
    @GetMapping(value = {"/", "/index.html"})
    // PDR Migrate Customization to here
    @HEHandler(exception = AppLevelListException.class, returnView = "member/abook/index")
    protected String doLoadIndex(MemberAbookModel memberAbookModel,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {

        // アドレス帳Modelをクリア
        clearModel(MemberAbookModel.class, memberAbookModel, CLEAR_EXCLUDED_FIELDS, model);

        // PDR Migrate Customization from here
        // アドレス帳一覧の検索
        searchAddressBookList(memberAbookModel, model);
        // PDR Migrate Customization to here

        return "member/abook/index";
    }

    /**
     * 一覧画面：削除処理
     *
     * @param memberAbookModel   アドレス帳Model
     * @param redirectAttributes
     * @param model              Model
     * @return 一覧画面
     */
    @PostMapping(value = {"/", "/index.html"}, params = "doOnceDelete")
    @HEHandler(exception = AppLevelListException.class, returnView = "member/abook/index")
    public String doOnceDelete(MemberAbookModel memberAbookModel, RedirectAttributes redirectAttributes, Model model) {

        try {
            int index = memberAbookModel.getAddressBookIndex();
            if (memberAbookModel.getAddressBookItems().size() > index) {
                // PDR Migrate Customization from here
                Integer receiveCustomerNo = memberAbookModel.getAddressBookItems().get(index).getReceiveCustomerNo();

                // 削除モード
                if (receiveCustomerNo != null) {
                    // WEB-API連携 お届け先削除
                    WebApiDelDestinationRequestDto reqDto =
                                    ApplicationContextUtility.getBean(WebApiDelDestinationRequestDto.class);

                    CommonInfoUtility commonInfoUtility = ApplicationContextUtility.getBean(CommonInfoUtility.class);

                    // 顧客番号
                    reqDto.setCustomerNo(commonInfoUtility.getCustomerNo(getCommonInfo()));

                    // お届け先顧客番号
                    reqDto.setReceiveCustomerNo(receiveCustomerNo);

                    WebApiDelDestinationRequest request = new WebApiDelDestinationRequest();
                    request.setCustomerNo(reqDto.getCustomerNo());
                    request.setReceiveCustomerNo(reqDto.getReceiveCustomerNo());
                    // Web-API実行
                    try {
                        webapiApi.deleteDestinaltion(request);
                    } catch (HttpClientErrorException e) {
                        LOGGER.error("例外処理が発生しました", e);
                        // AppLevelListExceptionへ変換
                        addAppLevelListException(e);
                        throwMessage();
                    }
                    // PDR Migrate Customization to here
                }
            }

        } finally {
            // アドレス帳一覧の検索
            searchAddressBookList(memberAbookModel, model);

        }

        return "member/abook/index";
    }

    /**
     * 詳細画面：初期表示
     *
     * @param memberAbookModel アドレス帳Model
     * @param model            Model
     * @return 詳細画面
     */
    @GetMapping(value = {"/detail", "/detail.html"})
    @HEHandler(exception = AppLevelListException.class, returnView = "member/abook/index")
    protected String doLoadDetail(MemberAbookModel memberAbookModel, Model model) {
        // チェックエラー発生後のリダイレクト時→セッションに保持したModelをそのまま使いたいので、何もせずreturn
        if (model.containsAttribute(FLASH_MESSAGES)) {
            return "member/abook/detail";
        }

        // URLパラメタの取得
        Integer addressBookSeq = memberAbookModel.getAbid();

        // アドレス帳Modelをクリア
        clearModel(MemberAbookModel.class, memberAbookModel, CLEAR_EXCLUDED_FIELDS, model);

        Map<String, String> prefectureTypeItems = EnumTypeUtil.getEnumMap(HTypePrefectureType.class, true);
        memberAbookModel.setPrefectureItems(prefectureTypeItems);

        MemberInfoGetRequest request = new MemberInfoGetRequest();
        request.setMemberInfoSeq(memberAbookModel.getMemberInfoSeq());

        // モード判定
        if (addressBookSeq == null) {

            // 登録モード設定
            memberAbookModel.setRegistMode(true);

        } else {
            // 更新モード設定
            memberAbookModel.setRegistMode(false);

            // アドレス帳情報の取得
            AddressBookEntity addressBookEntity = null;
            try {
                AddressBookEntityResponse addressBookEntityResponse = null;
                try {
                    addressBookEntityResponse = memberInfoApi.getAddressbook(addressBookSeq, request);
                } catch (HttpClientErrorException e) {
                    LOGGER.error("例外処理が発生しました", e);
                    // AppLevelListExceptionへ変換
                    addAppLevelListException(e);
                    throwMessage();
                }
                memberAbookHelper.toAddressBookEntity(addressBookEntityResponse);
            } catch (AppLevelListException e) {
                LOGGER.error("例外処理が発生しました", e);
                return "redirect:/error.html";
            }

            // ページへの反映
            memberAbookHelper.toPageForLoad(addressBookEntity, memberAbookModel);
        }

        return "member/abook/detail";
    }

    /**
     * 詳細画面：アドレス帳登録処理
     *
     * @param memberAbookModel アドレス帳Model
     * @param error            BindingResult
     * @param model            Model
     * @return 一覧画面
     */
    @PostMapping(value = {"/detail", "/detail.html"}, params = "doOnceAddressBookRegist")
    @HEHandler(exception = AppLevelListException.class, returnView = "member/abook/detail")
    public String doOnceAddressBookRegist(@Validated MemberAbookModel memberAbookModel,
                                          BindingResult error,
                                          Model model) {

        // 都道府県と郵便番号が不一致の場合
        if (!checkPrefectureAndZipCode(memberAbookModel)) {
            error.rejectValue(FILED_NAME_PREFECTURE, MSGCD_PREFECTURE_CONSISTENCY);
        }

        if (error.hasErrors()) {
            return "member/abook/detail";
        }

        // 都道府県の入力チェック。POSTの値の書き換えチェック
        checkPrefecture(memberAbookModel);

        // Entityの作成
        AddressBookEntity addressBookEntity =
                        memberAbookHelper.toAddressBookEntityForAddressBookRegist(memberAbookModel);
        AddressBookRegistRequest request = memberAbookHelper.toAddressBookRegistRequest(addressBookEntity);
        // サービスの実行
        try {
            memberInfoApi.registAddressbook(request);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        // 一覧に遷移
        return "redirect:/member/abook/index.html";
    }

    /**
     * 詳細画面：アドレス帳更新処理
     *
     * @param memberAbookModel   アドレス帳Model
     * @param error              BindingResult
     * @param redirectAttributes RedirectAttributes
     * @param model              Model
     * @return 一覧画面
     */
    @PostMapping(value = {"/detail", "/detail.html"}, params = "doOnceAddressBookUpdate")
    @HEHandler(exception = AppLevelListException.class, returnView = "member/abook/detail")
    public String doOnceAddressBookUpdate(@Validated MemberAbookModel memberAbookModel,
                                          BindingResult error,
                                          RedirectAttributes redirectAttributes,
                                          Model model) {

        // 都道府県と郵便番号が不一致の場合
        if (!checkPrefectureAndZipCode(memberAbookModel)) {
            error.rejectValue(FILED_NAME_PREFECTURE, MSGCD_PREFECTURE_CONSISTENCY);
        }

        if (error.hasErrors()) {
            return "member/abook/detail";
        }

        if (memberAbookModel.getAddressBookSeq() == null) {
            addMessage(MSGCD_REFERER_FAIL, redirectAttributes, model);
            return "redirect:/error.html";
        }

        // 都道府県の入力チェック。POSTの値の書き換えチェック
        checkPrefecture(memberAbookModel);

        // Entityの作成
        AddressBookEntity addressBookEntity =
                        memberAbookHelper.toAddressBookEntityForAddressBookUpdate(memberAbookModel);
        AddressBookUpdateRequest request = memberAbookHelper.toAddressBookUpdateRequest(addressBookEntity);
        // サービスの実行
        try {
            memberInfoApi.updateAddressbook(request);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
        // 一覧に遷移
        return "redirect:/member/abook/index.html";
    }

    // PDR Migrate Customization from here

    /**
     * 住所録一覧の検索
     *
     * @param memberAbookModel アドレス帳Model
     * @param model            Model
     */
    protected void searchAddressBookList(MemberAbookModel memberAbookModel, Model model) {

        // WEB-API連携 お届け先参照
        WebApiGetDestinationResponseDto responseGetDestinationDto = executeWebApiGetDestination();

        // ページの反映
        memberAbookHelper.toPageForLoad(responseGetDestinationDto, memberAbookModel);
    }
    // PDR Migrate Customization to here

    /**
     * 都道府県の入力チェック
     * POSTされるプルダウンの値の書き換え対策
     *
     * @param memberAbookModel
     */
    protected void checkPrefecture(MemberAbookModel memberAbookModel) {

        Map<String, String> prefectureMap = EnumTypeUtil.getEnumMap(HTypePrefectureType.class, true);
        boolean existFlag = prefectureMap.containsKey(memberAbookModel.getPrefecture());
        if (!existFlag) {
            throwMessage(MSGCD_ILLEGAL_PREFECTURE);
        }

    }

    /**
     * 都道府県と郵便番号の不整合チェック
     *
     * @param memberAbookModel
     */
    protected boolean checkPrefectureAndZipCode(MemberAbookModel memberAbookModel) {

        // nullの場合service未実行(必須チェックは別タスク)
        if (StringUtils.isEmpty(memberAbookModel.getZipCode1()) || StringUtils.isEmpty(memberAbookModel.getZipCode2())
            || StringUtils.isEmpty(memberAbookModel.getPrefecture())) {
            return true;
        }

        ZipCodeMatchingCheckRequest request = new ZipCodeMatchingCheckRequest();
        request.setZipCode(memberAbookModel.getZipCode1() + memberAbookModel.getZipCode2());
        request.setPrefecture(memberAbookModel.getPrefecture());

        ResultFlagResponse checkZipcodeMatching = null;
        try {
            checkZipcodeMatching = zipcodeApi.registCheckZipcodeMatching(request);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        // 郵便番号住所情報取得サービス実行
        return checkZipcodeMatching.getResultFlag();
    }

    // PDR Migrate Customization from here

    /**
     * Web-APIを実行し、レスポンス情報を返す
     *
     * @return お届け先取得Web-APIレスポンスDto
     */
    protected WebApiGetDestinationResponseDto executeWebApiGetDestination() {

        CommonInfoUtility commonInfoUtility = ApplicationContextUtility.getBean(CommonInfoUtility.class);

        WebApiGetDestinationRequest request = new WebApiGetDestinationRequest();
        request.setCustomerNo(commonInfoUtility.getCustomerNo(getCommonInfo()));
        WebApiGetDestinationResponse responseDto = null;
        try {
            // Web-API実行
            responseDto = webapiApi.getDestination(request);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
        return memberAbookHelper.toWebApiGetDestinationResponseDto(responseDto);
    }
    // PDR Migrate Customization to here

}
