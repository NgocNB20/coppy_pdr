/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.administrator.authregister;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.dto.administrator.MetaAuthType;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdminAuthGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.administrator.AdminAuthGroupLogic;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

/**
 * 権限グループ登録画面用 Action クラス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("/administrator/authregister")
@Controller
@SessionAttributes(value = "authRegisterModel")
@PreAuthorize("hasAnyAuthority('ADMIN:8')")
public class AuthRegisterController extends AbstractController {

    /**
     * メッセージコード定数：登録しようとした権限グループは既に存在している
     */
    protected static final String GROUP_ALREADY_EXISTS = "AYP001001";

    /**
     * メッセージコード：登録完了
     */
    protected static final String REGISTER_COMPLETED = "AYP001005";

    /**
     * 新規登録モードであることを識別する為の値
     */
    protected static final String MD_NEW = "new";

    /**
     * 確認画面から
     */
    public static final String FLASH_FROM_CONFIRM = "fromConfirm";

    /**
     * メタ権限情報。DICON で設定された値が自動バインドされる。必ず設定されている必要あり
     */
    //    @Binding(bindingType = BindingType.MUST)
    private List<MetaAuthType> metaAuthList;

    /**
     * 権限グループ操作ロジック
     */
    private AdminAuthGroupLogic authLogic;

    /**
     * Helper
     */
    private AuthRegisterHelper registerHelper;

    @Autowired
    public AuthRegisterController(AdminAuthGroupLogic authLogic, AuthRegisterHelper registerHelper) {
        this.authLogic = authLogic;
        this.registerHelper = registerHelper;
        this.metaAuthList = (List<MetaAuthType>) ApplicationContextUtility.getApplicationContext()
                                                                          .getBean("metaAuthTypeList");
    }

    /**
     * 初期表示アクション
     *
     * @return 遷移先クラス
     */
    @GetMapping("")
    protected String doLoadIndex(@RequestParam(required = false) Optional<String> md,
                                 AuthRegisterModel authRegisterModel,
                                 Model model) {

        if (!model.containsAttribute(FLASH_FROM_CONFIRM)) {
            clearModel(AuthRegisterModel.class, authRegisterModel, model);
        }

        // 新規登録モードの場合、初期化を行う。
        if ((md.isPresent() && MD_NEW.equals(md.get()) || (authRegisterModel.getAuthItems() == null))) {
            registerHelper.toPageForLoad(this.metaAuthList, authRegisterModel);
        }

        return "administrator/authregister/register";
    }

    /**
     * 画面描画直前にTeedaより呼び出されるメソッド<br/>
     * 注意事項<br/>
     * １．prerenderはバリデーションエラー発生時にも呼び出される<br/>
     * doアクションが成功していることを前提にしたコードを記述してはならない。<br/>
     * ２．doアクションの後処理として利用しない<br/>
     * 　doアクションの後処理用には postDoActionメソッドが提供されているのでそちらを利用すること<br/>
     *
     * @return 遷移先クラス
     */
    public String prerender(AuthRegisterModel authRegisterModel, Model model) {
        //        prerender(authenRegistModel, model);

        // サブアプリケーションスコープ消失時の対応
        // doLoadと同様の処理を行い、バリデーションエラー発生時でも権限種別が画面に表示されるようにする。
        if (authRegisterModel.getAuthItems() == null) {
            registerHelper.toPageForLoad(metaAuthList, authRegisterModel);
        }

        return null;
    }

    /**
     * 権限グループ登録確認画面へ遷移するアクション
     *
     * @return 遷移先クラス
     */
    @PostMapping(value = "/", params = "doConfirm")
    @HEHandler(exception = AppLevelListException.class, returnView = "administrator/authregister/register")
    public String doConfirm(@Validated AuthRegisterModel authRegisterModel,
                            BindingResult error,
                            RedirectAttributes redirectAttributes) {

        if (error.hasErrors()) {
            return "administrator/authregister/register";
        }

        // 登録可能チェック
        checkNotExistence(authRegisterModel);
        redirectAttributes.addFlashAttribute("authRegisterModel", authRegisterModel);
        return "redirect:/administrator/authregister/confirm";
    }

    /**
     * 権限一覧画面へ遷移するアクション
     *
     * @return 遷移先クラス
     */
    @PostMapping(value = "/", params = "doCancel")
    public String doCancel() {
        return "redirect:/administrator/auth";
    }

    /**
     * 初期表示アクション
     *
     * @return 遷移先ページ
     */
    @GetMapping("/confirm")
    protected String doLoadIndex(AuthRegisterModel authRegisterModel, Model model) {
        //        super.doLoad();
        if (model.containsAttribute("authRegisterModel")) {
            authRegisterModel = (AuthRegisterModel) model.getAttribute("authRegisterModel");
            if (authRegisterModel != null) {
                registerHelper.setLevelName(authRegisterModel);
            }
        }

        return "administrator/authregister/confirm";
    }

    /**
     * 入力画面へ戻るアクション
     *
     * @return 遷移先ページ
     */
    @PostMapping(value = "/confirm", params = "doGoBack")
    public String doGoBack(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(FLASH_FROM_CONFIRM, true);
        return "redirect:/administrator/authregister";
    }

    /**
     * 権限グループ登録アクション<br />
     *
     * @return 遷移先ページ
     */
    @PostMapping(value = "/confirm", params = "doOnceRegister")
    @HEHandler(exception = AppLevelListException.class, returnView = "administrator/authregister/register")
    @HEHandler(exception = DataIntegrityViolationException.class, returnView = "administrator/authregister/confirm",
               messageCode = GROUP_ALREADY_EXISTS)
    public String doOnceRegister(AuthRegisterModel authRegisterModel,
                                 RedirectAttributes redirectAttributes,
                                 Model model,
                                 SessionStatus sessionStatus) {

        // ページ上にある入力情報から登録用 DTO を作成する
        AdminAuthGroupEntity group = registerHelper.toAdminAuthGroupEntityForRegister(authRegisterModel);

        // ユーザのショップSEQをDTOへセットする
        group.setShopSeq(getCommonInfo().getCommonInfoBase().getShopSeq());

        // 登録する
        authLogic.register(group);

        addInfoMessage(REGISTER_COMPLETED, null, redirectAttributes, model);

        // Modelをセッションより破棄
        sessionStatus.setComplete();

        return "redirect:/administrator/auth";
    }

    /**
     * 登録しようとしている権限グループが存在しないことを確認する
     */
    protected void checkNotExistence(AuthRegisterModel authRegisterModel) {

        Integer shopSeq = getCommonInfo().getCommonInfoBase().getShopSeq();
        String groupName = authRegisterModel.getAuthGroupDisplayName();

        AdminAuthGroupEntity entity = authLogic.getAdminAuthGroup(shopSeq, groupName);

        // 既に同一権限グループが存在する場合
        if (entity != null) {
            throwMessage(GROUP_ALREADY_EXISTS);
        }
    }
}
