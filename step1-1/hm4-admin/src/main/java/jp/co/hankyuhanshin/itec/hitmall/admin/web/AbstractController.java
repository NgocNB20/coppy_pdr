/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.web;

import jp.co.hankyuhanshin.itec.hitmall.admin.application.commoninfo.CommonInfo;
import jp.co.hankyuhanshin.itec.hitmall.exception.ControllerException;
import jp.co.hankyuhanshin.itec.hmbase.application.AppLevelFacesMessage;
import jp.co.hankyuhanshin.itec.hmbase.application.HmMessages;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelException;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.newfaces.FacesMessage;
import jp.co.hankyuhanshin.itec.hmbase.util.AppLevelFacesMessageUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationLogUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.BeanUtility;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
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
        addMessage(FacesMessage.SEVERITY_ERROR, messageCode, args, redirectAttributes, model);
    }

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
        addMessage(FacesMessage.SEVERITY_WARN, messageCode, args, redirectAttributes, model);
    }

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
        addMessage(FacesMessage.SEVERITY_INFO, messageCode, args, redirectAttributes, model);
    }

    /**
     * メッセージを追加<br/>
     *
     * @param severity           メッセージレベル
     * @param messageCode        メッセージコード
     * @param args               メッセージ引数
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     */
    protected static void addMessage(FacesMessage.Severity severity,
                                     String messageCode,
                                     Object[] args,
                                     RedirectAttributes redirectAttributes,
                                     Model model) {

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
        if (flashattrsMap != null && flashattrsMap.containsKey(FLASH_MESSAGES)) {
            allMessages = (HmMessages) flashattrsMap.get(FLASH_MESSAGES);
        }
        allMessages.add(message);

        // その後の遷移先がリダイレクトであってもなくても、うまく画面表示できるよう、
        // RedirectAttributes、Model両方にメッセージを設定しておく
        redirectAttributes.addFlashAttribute(FLASH_MESSAGES, allMessages);
        model.addAttribute(FLASH_MESSAGES, allMessages);
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
        // メッセージリストの件数分ループ
        HmMessages allMessages = new HmMessages();
        List<AppLevelException> errorList = eList.getErrorList();
        for (AppLevelException e : errorList) {
            allMessages.add(e.getAppLevelFacesMessage());
        }
        // 遷移先画面がリダイレクトあり／なしにかかわらずメッセージ出力できるよう、
        // RedirectAttributes、Model両方に属性セットする
        redirectAttributes.addFlashAttribute(FLASH_MESSAGES, allMessages);
        model.addAttribute(FLASH_MESSAGES, allMessages);
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
