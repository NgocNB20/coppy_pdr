/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.web;

import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.ClientErrorResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.ErrorContent;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfo;
import jp.co.hankyuhanshin.itec.hitmall.front.base.application.AppLevelFacesMessage;
import jp.co.hankyuhanshin.itec.hitmall.front.base.application.HmMessages;
import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelException;
import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hitmall.front.base.newfaces.FacesMessage;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.AppLevelFacesMessageUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationLogUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.BeanUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.exception.ControllerException;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * コントローラ基底クラス
 *
 * @author kn23834
 * @Transactionalでトランザクション制御
 */
@Transactional(rollbackFor = Exception.class)
public abstract class AbstractController {

    /**
     * エラーリスト
     */
    private List<AppLevelException> errorList = new ArrayList<>();

    public static final String FLASH_MESSAGES = "allMessages";

    /**
     * メッセージを追加<br/>
     * ※アトリビュート名を指定してメッセージの出し分けが可能
     *
     * @param severity           メッセージレベル
     * @param messageCode        メッセージコード
     * @param args               メッセージ引数
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @param attributeName      アトリビュート名
     */
    protected static void addMessage(FacesMessage.Severity severity,
                                     String messageCode,
                                     Object[] args,
                                     RedirectAttributes redirectAttributes,
                                     Model model,
                                     String attributeName) {

        //
        // コールスタックを漁って addMessage を行ったクラスを取得する
        //

        Throwable th = new Throwable();
        StackTraceElement ste = th.getStackTrace()[0];

        // AbstractController は発生元クラスとしない
        for (StackTraceElement tmp : th.getStackTrace()) {
            if (AbstractController.class.getName().equals(tmp.getClassName())) {
                continue;
            }
            ste = tmp;
            break;
        }

        // メッセージの取得
        AppLevelFacesMessage message = AppLevelFacesMessageUtil.getAllMessage(messageCode, args);

        // アプリケーションログ出力Helper取得
        ApplicationLogUtility applicationLogUtility = ApplicationContextUtility.getBean(ApplicationLogUtility.class);

        // addMessage のログを出力する
        applicationLogUtility.writeLogicErrorLog(
                        ste.getClassName() + "#" + ste.getMethodName(), messageCode + ":" + message.getMessage());

        // FlashAttributeへメッセージセット
        HmMessages allMessages = new HmMessages();

        Map<String, ?> flashattrsMap = redirectAttributes.getFlashAttributes();
        if (flashattrsMap != null && flashattrsMap.containsKey(attributeName)) {
            allMessages = (HmMessages) flashattrsMap.get(attributeName);
        }
        allMessages.add(message);

        // その後の遷移先がリダイレクトであってもなくても、うまく画面表示できるよう、
        // RedirectAttributes、Model両方にメッセージを設定しておく
        redirectAttributes.addFlashAttribute(attributeName, allMessages);
        model.addAttribute(attributeName, allMessages);
    }

    /**
     * 引数 severity, errorCode, args, causeからControllerExceptionを作成<br/>
     * メソッドの説明・概要<br/>
     *
     * @param severity    エラーレベル
     * @param errorCode   メッセージコード
     * @param args        メッセージ引数
     * @param componentId コンポーネントID
     * @param cause       例外
     * @return ControllerExceptionオブジェクト
     */
    protected static ControllerException createControllerException(FacesMessage.Severity severity,
                                                                   String errorCode,
                                                                   Object[] args,
                                                                   String componentId,
                                                                   Throwable cause) {
        return new ControllerException(severity, errorCode, args, componentId, cause);
    }

    /**
     * コントローラーの例外作成
     *
     * @param severity リハーサル
     * @param message メッセージ
     * @return ControllerException
     */
    protected static ControllerException createControllerException(FacesMessage.Severity severity, String message) {
        return new ControllerException(severity, message);
    }

    /**
     * モデルをクリアする
     */
    public void clearModel(Class<?> clazz, Object targetModel, Model model) {
        clearModel(clazz, targetModel, null, model);
    }

    /**
     * モデルをクリアする
     *
     * @param clazz          モデルクラス
     * @param targetModel    クリア対象のモデル
     * @param excludedFields クリア除外対象フィールド
     * @param model          Model
     */
    public void clearModel(Class<?> clazz, Object targetModel, String[] excludedFields, Model model) {
        // BeanUtility#clearBean実行
        BeanUtility beanUtility = ApplicationContextUtility.getBean(BeanUtility.class);
        beanUtility.clearBean(clazz, targetModel, excludedFields);
    }

    /**
     * エラーメッセージの有無判定
     *
     * @return true..有、false..無
     */
    protected boolean hasErrorMessage() {
        return errorList != null && !errorList.isEmpty();
    }

    /**
     * 各Messages.propertiesファイルからメッセージを取得する<br/>
     * ※messageCodeにメッセージタイプを指定しない場合は、
     * F → E → W → I で存在する順番に取得する
     *
     * @param messageCode メッセージコード
     * @param args        メッセージ引数
     * @return メッセージ
     */
    protected String getMessage(String messageCode, Object[] args) {
        return AppLevelFacesMessageUtil.getAllMessage(messageCode, args).getMessage();
    }

    /**
     * エラーメッセージの追加<br/>
     *
     * @param controllerException
     */
    protected void addErrorMessage(ControllerException controllerException) {
        if (errorList == null) {
            errorList = new ArrayList<>();
        }
        errorList.add(controllerException);
    }

    /**
     * エラーメッセージの追加<br/>
     * <p>
     * ControllerExceptionを作成し、errorListに追加
     *
     * @param messageCode メッセージコード
     */
    protected void addErrorMessage(String messageCode) {
        addErrorMessage(messageCode, null);
    }

    /**
     * エラーメッセージの追加<br/>
     * <p>
     * ControllerExceptionを作成し、errorListに追加
     *
     * @param messageCode メッセージコード
     * @param args        メッセージ引数
     */
    protected void addErrorMessage(String messageCode, Object[] args) {
        addErrorMessage(messageCode, args, null, null);
    }

    /**
     * エラーメッセージの追加<br/>
     * <p>
     * ControllerExceptionを作成し、errorListに追加
     * メソッドの説明・概要<br/>
     *
     * @param messageCode メッセージコード
     * @param args        メッセージ引数
     * @param cause       例外
     */
    protected void addErrorMessage(String messageCode, Object[] args, Throwable cause) {
        addErrorMessage(messageCode, args, null, cause);
    }

    /**
     * エラーメッセージの追加<br/>
     * <p>
     * ControllerExceptionを作成し、errorListに追加
     * メソッドの説明・概要<br/>
     *
     * @param messageCode メッセージコード
     * @param args        メッセージ引数
     * @param componentId コンポーネントID
     * @param cause       例外
     */
    protected void addErrorMessage(String messageCode, Object[] args, String componentId, Throwable cause) {
        addErrorMessage(createControllerException(FacesMessage.SEVERITY_ERROR, messageCode, args, componentId, cause));
    }

    /**
     * エラーを発生させる(以降の処理中断)<br/>
     * 現在溜まっているメッセージからAppLevelListExceptionを作成し、<br/>
     * 例外をスローする<br/>
     * メッセージがない場合は、空のAppLevelListExceptionをスローする<br/>
     *
     * @throws AppLevelListException メッセージがある場合にスロー
     * @throws RuntimeException      メッセージがない場合にスロー
     */
    protected void throwMessage() throws AppLevelListException, RuntimeException {
        throw new AppLevelListException(errorList);
    }

    /**
     * 引数 errorCode からActionExceptionを作成し、AppLevelListExceptionにセットし、スロー<br/>
     *
     * @param errorCode エラーコード
     * @throws AppLevelListException 作成した例外をスロー
     */
    protected void throwMessage(String errorCode) throws AppLevelListException {
        throwMessage(errorCode, null);
    }

    /**
     * 引数 errorCode args からActionExceptionを作成し、AppLevelListExceptionにセットし、スロー<br/>
     *
     * @param errorCode エラーコード
     * @param args      エラーメッセージ引数
     * @throws AppLevelListException 作成した例外をスロー
     */
    protected void throwMessage(String errorCode, Object[] args) throws AppLevelListException {
        throwMessage(errorCode, args, null);
    }

    /**
     * 引数 errorCode args cause からActionExceptionを作成し、AppLevelListExceptionにセットし、スロー<br/>
     * エラーレベルは、FacesMessage.SEVERITY_ERROR<br/>
     *
     * @param errorCode エラーコード
     * @param args      エラーメッセージ引数
     * @param cause     例外
     * @throws AppLevelListException 作成した例外をスロー
     */
    protected void throwMessage(String errorCode, Object[] args, Throwable cause) throws AppLevelListException {
        throwMessage(errorCode, args, null, cause);
    }

    /**
     * 引数 errorCode args cause からActionExceptionを作成し、AppLevelListExceptionにセットし、スロー<br/>
     * エラーレベルは、FacesMessage.SEVERITY_ERROR<br/>
     *
     * @param errorCode   エラーコード
     * @param args        エラーメッセージ引数
     * @param componentId コンポーネントID
     * @param cause       例外
     * @throws AppLevelListException 作成した例外をスロー
     */
    protected void throwMessage(String errorCode, Object[] args, String componentId, Throwable cause)
                    throws AppLevelListException {
        ControllerException controllerException =
                        createControllerException(FacesMessage.SEVERITY_ERROR, errorCode, args, componentId, cause);
        addErrorMessage(controllerException);
        throwMessage();
    }

    /**
     * メッセージを追加<br/>
     *
     * @param messageCode        メッセージコード
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     */
    protected void addMessage(String messageCode, RedirectAttributes redirectAttributes, Model model) {
        addMessage(messageCode, null, redirectAttributes, model);
    }

    // 2023-renew No24 from here

    /**
     * メッセージを追加<br/>
     * ※アトリビュート名を指定してメッセージの出し分けが可能
     *
     * @param messageCode        メッセージコード
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @param attributeName      アトリビュート名
     */
    protected void addMessage(String messageCode,
                              RedirectAttributes redirectAttributes,
                              Model model,
                              String attributeName) {
        addMessage(messageCode, null, redirectAttributes, model, attributeName);
    }

    // 2023-renew No24 to here

    /**
     * メッセージを追加<br/>
     * SEVERITY_ERRORのレベルでメッセージを設定<br/>
     *
     * @param messageCode        メッセージコード
     * @param args               メッセージ引数
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     */
    protected void addMessage(String messageCode, Object[] args, RedirectAttributes redirectAttributes, Model model) {
        addMessage(FacesMessage.SEVERITY_ERROR, messageCode, args, redirectAttributes, model, FLASH_MESSAGES);
    }

    // 2023-renew No24 from here

    /**
     * メッセージを追加<br/>
     * SEVERITY_ERRORのレベルでメッセージを設定<br/>
     * ※アトリビュート名を指定してメッセージの出し分けが可能
     *
     * @param messageCode        メッセージコード
     * @param args               メッセージ引数
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @param attributeName      アトリビュート名
     */
    protected void addMessage(String messageCode,
                              Object[] args,
                              RedirectAttributes redirectAttributes,
                              Model model,
                              String attributeName) {
        addMessage(FacesMessage.SEVERITY_ERROR, messageCode, args, redirectAttributes, model, attributeName);
    }

    // 2023-renew No24 to here

    /**
     * 警告メッセージを追加<br/>
     * SEVERITY_WARNのレベルでメッセージを設定<br/>
     *
     * @param messageCode        メッセージコード
     * @param args               メッセージ引数
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     */
    protected void addWarnMessage(String messageCode,
                                  Object[] args,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {
        addMessage(FacesMessage.SEVERITY_WARN, messageCode, args, redirectAttributes, model, FLASH_MESSAGES);
    }

    // 2023-renew No24 from here

    /**
     * 警告メッセージを追加<br/>
     * SEVERITY_WARNのレベルでメッセージを設定<br/>
     * ※アトリビュート名を指定してメッセージの出し分けが可能
     *
     * @param messageCode        メッセージコード
     * @param args               メッセージ引数
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @param attributeName      アトリビュート名
     */
    protected void addWarnMessage(String messageCode,
                                  Object[] args,
                                  RedirectAttributes redirectAttributes,
                                  Model model,
                                  String attributeName) {
        addMessage(FacesMessage.SEVERITY_WARN, messageCode, args, redirectAttributes, model, attributeName);
    }

    // 2023-renew No24 to here

    /**
     * 情報メッセージを追加<br/>
     * SEVERITY_INFOのレベルでメッセージを設定<br/>
     *
     * @param messageCode        メッセージコード
     * @param args               メッセージ引数
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     */
    protected void addInfoMessage(String messageCode,
                                  Object[] args,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {
        addMessage(FacesMessage.SEVERITY_INFO, messageCode, args, redirectAttributes, model, FLASH_MESSAGES);
    }

    // 2023-renew No24 from here

    /**
     * 情報メッセージを追加<br/>
     * SEVERITY_INFOのレベルでメッセージを設定<br/>
     * ※アトリビュート名を指定してメッセージの出し分けが可能
     *
     * @param messageCode        メッセージコード
     * @param args               メッセージ引数
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @param attributeName      アトリビュート名
     */
    protected void addInfoMessage(String messageCode,
                                  Object[] args,
                                  RedirectAttributes redirectAttributes,
                                  Model model,
                                  String attributeName) {
        addMessage(FacesMessage.SEVERITY_INFO, messageCode, args, redirectAttributes, model, attributeName);
    }

    /**
     * サービス層からスローされたAppLevelListExceptionを
     * return "redirect:/***" で任意画面へ遷移させたい時のメッセージ設定処理<br/>
     * <pre>
     * AppLevelListExceptionのもつエラーメッセージを、
     * Model、RedirectAttributesにセットします。
     * </pre>
     *
     * @param eList              AppLevelListException
     * @param redirectAttributes RedirectAttributes
     * @param model              Model
     */
    protected void setAllMessages(AppLevelListException eList, RedirectAttributes redirectAttributes, Model model) {
        setAllMessages(eList, redirectAttributes, model, FLASH_MESSAGES);
    }

    // 2023-renew No24 to here

    /**
     * サービス層からスローされたAppLevelListExceptionを
     * return "redirect:/***" で任意画面へ遷移させたい時のメッセージ設定処理<br/>
     * <pre>
     * AppLevelListExceptionのもつエラーメッセージを、
     * Model、RedirectAttributesにセットします。
     * </pre>
     * ※アトリビュート名を指定してメッセージの出し分けが可能
     *
     * @param eList              AppLevelListException
     * @param redirectAttributes RedirectAttributes
     * @param model              Model
     * @param attributeName      アトリビュート名
     */
    protected void setAllMessages(AppLevelListException eList,
                                  RedirectAttributes redirectAttributes,
                                  Model model,
                                  String attributeName) {
        // メッセージリストの件数分ループ
        HmMessages allMessages = new HmMessages();
        List<AppLevelException> errorList = eList.getErrorList();
        for (AppLevelException e : errorList) {
            allMessages.add(e.getAppLevelFacesMessage());
        }
        // 遷移先画面がリダイレクトあり／なしにかかわらずメッセージ出力できるよう、
        // RedirectAttributes、Model両方に属性セットする
        redirectAttributes.addFlashAttribute(attributeName, allMessages);
        model.addAttribute(attributeName, allMessages);
    }

    /**
     * AppLevelExceptionのリストを保持するException
     *
     * @param e HttpClientErrorException
     */
    public void addAppLevelListException(HttpClientErrorException e) {

        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        ClientErrorResponse clientError =
                        conversionUtility.toObject(e.getResponseBodyAsString(), ClientErrorResponse.class);

        Map<String, List<ErrorContent>> messages = clientError.getMessages();

        if (MapUtils.isNotEmpty(messages)) {
            for (Map.Entry<String, List<ErrorContent>> entry : messages.entrySet()) {
                String key = entry.getKey();
                List<ErrorContent> errorResponseList = entry.getValue();
                // エラーリスト分、ループ
                if (key.equals("common")) {
                    for (ErrorContent errorResponse : errorResponseList) {
                        addErrorMessageApi(errorResponse.getMessage());
                    }
                }
            }
        }
    }

    /**
     * APIのエラーメッセージの追加
     *
     * @param message メッセージ
     */
    protected void addErrorMessageApi(String message) {
        ControllerException controllerException = createControllerException(FacesMessage.SEVERITY_ERROR, message);
        addErrorMessage(controllerException);
    }

    /**
     * クライアントのエラーを処理する.
     *
     * @param e exception
     * @return メッセージ
     */
    public static String buildMessageFromHttpClientErrorException(HttpClientErrorException e) {
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);
        ClientErrorResponse clientError =
                        conversionUtility.toObject(e.getResponseBodyAsString(), ClientErrorResponse.class);

        Map<String, List<ErrorContent>> messages = clientError.getMessages();

        // 文字列を格納する枠を生成
        StringBuilder sb = new StringBuilder();

        if (MapUtils.isNotEmpty(messages)) {
            for (Map.Entry<String, List<ErrorContent>> entry : messages.entrySet()) {
                String key = entry.getKey();
                List<ErrorContent> errorResponseList = entry.getValue();
                // エラーリスト分、ループ
                if (key.equals("common")) {
                    for (ErrorContent errorContent : errorResponseList) {
                        String errorMessageRes = errorContent.getMessage();
                        if (!StringUtils.isEmpty(errorMessageRes)) {
                            // エラーメッセージを格納
                            sb.append(errorMessageRes);
                            sb.append("<br />");
                        }
                    }
                }
            }
        }
        return sb.toString();
    }

    // 2023-renew No24 from here

    /**
     * メッセージの追加
     * ※コードではなく、直接メッセージ本文で指定する
     * （throwはしないので、画面表示項目を生かしたままメッセージを表示できる）
     *
     * @param model              モデル
     * @param redirectAttributes リダイレクトアトリビュート
     * @param message            内容
     */
    public static void addMessage(Model model, RedirectAttributes redirectAttributes, String message) {
        addMessage(model, redirectAttributes, message, FLASH_MESSAGES);
    }

    /**
     * メッセージの追加
     * ※コードではなく、直接メッセージ本文で指定する
     * （throwはしないので、画面表示項目を生かしたままメッセージを表示できる）
     * ※アトリビュート名を指定してメッセージの出し分けが可能
     *
     * @param model              モデル
     * @param redirectAttributes リダイレクトアトリビュート
     * @param message            内容
     * @param attributeName      アトリビュート名
     */
    public static void addMessage(Model model,
                                  RedirectAttributes redirectAttributes,
                                  String message,
                                  String attributeName) {
        // FlashAttributeへメッセージセット
        HmMessages allMessages = new HmMessages();
        AppLevelFacesMessage appLevelFacesMessage = new AppLevelFacesMessage();
        appLevelFacesMessage.setDetail(message);

        Map<String, ?> flashattrsMap = redirectAttributes.getFlashAttributes();
        if (flashattrsMap.containsKey(attributeName)) {
            allMessages = (HmMessages) flashattrsMap.get(attributeName);
        } else if (model.containsAttribute(attributeName)) {
            allMessages.addAll((HmMessages) model.getAttribute(attributeName));
        }
        allMessages.add(appLevelFacesMessage);

        // その後の遷移先がリダイレクトであってもなくても、うまく画面表示できるよう、
        // RedirectAttributes、Model両方にメッセージを設定しておく
        redirectAttributes.addFlashAttribute(attributeName, allMessages);
        model.addAttribute(attributeName, allMessages);
    }

    // 2023-renew No24 to here

    /**
     * エラーリストをクリアする<br/>
     */
    public void clearErrorList() {
        if (errorList != null) {
            errorList = null;
        }
    }

    /**
     * エラーリストを取得する<br/>
     *
     * @return エラーリスト
     */
    public List<AppLevelException> getErrorList() {
        return errorList;
    }

    /**
     * 共通情報の取得<br/>
     *
     * @return 共通情報
     */
    protected CommonInfo getCommonInfo() {
        return ApplicationContextUtility.getBean(CommonInfo.class);
    }
}
