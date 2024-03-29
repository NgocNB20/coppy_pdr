/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.administrator.authdelete;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.util.IdenticalDataCheckUtil;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.dto.administrator.MetaAuthType;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdminAuthGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.administrator.AdminAuthGroupLogic;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * 権限グループ削除確認画面 Action クラス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("/administrator/auth/delete")
@Controller
@SessionAttributes(value = "administratorAuthDeleteModel")
@PreAuthorize("hasAnyAuthority('ADMIN:8')")
public class AdministratorAuthDeleteController extends AbstractController {

    /**
     * メッセージコード：不正遷移
     */
    protected static final String BAD_TRANSITION = "AYP001003";

    /**
     * メッセージコード：権限グループ使用中
     */
    protected static final String GROUP_IN_USE = "AYP001002";

    /**
     * メッセージコード：該当権限グループなし
     */
    protected static final String GROUP_NOT_FOUND = "AYP001004";

    /**
     * メッセージコード：削除完了
     */
    protected static final String DELETE_COMPLETED = "AYP001007";

    public static final String FLASH_SEQ = "seq";

    /**
     * 権限グループ情報操作ロジック
     */
    public AdminAuthGroupLogic authLogic;

    /**
     * Helper
     */
    public AdministratorAuthDeleteHelper administratorAuthDeleteHelper;

    /**
     * メタ権限情報。DICON で設定された値が自動バインドされる。必ず設定されている必要あり
     */
    public List<MetaAuthType> metaAuthList;

    /**
     * コンストラクタ
     *
     * @param authLogic
     * @param administratorAuthDeleteHelper
     */
    @Autowired
    public AdministratorAuthDeleteController(AdminAuthGroupLogic authLogic,
                                             AdministratorAuthDeleteHelper administratorAuthDeleteHelper) {
        this.authLogic = authLogic;
        this.administratorAuthDeleteHelper = administratorAuthDeleteHelper;
        this.metaAuthList = (List<MetaAuthType>) ApplicationContextUtility.getApplicationContext()
                                                                          .getBean("metaAuthTypeList");
    }

    /**
     * 初期表示処理
     *
     * @return 遷移先ページ
     */
    @GetMapping(value = "")
    @HEHandler(exception = AppLevelListException.class, returnView = "administrator/authdelete/delete")
    protected String doLoadIndex(AdministratorAuthDeleteModel administratorAuthDeleteModel,
                                 BindingResult error,
                                 RedirectAttributes redirectAttrs,
                                 Model model) {

        // リダイレクトスコープの authSeq が消える前にコピー
        if (model.containsAttribute("seq")) {
            administratorAuthDeleteModel.setAdminAuthGroupSeq((String) model.getAttribute(FLASH_SEQ));
        }
        // if (administratorAuthDeleteModel.getSeq() != null) {
        // administratorAuthDeleteModel.setAdminAuthGroupSeq(administratorAuthDeleteModel.getSeq().toString());
        // }

        // 不正遷移チェック
        checkBadTransition(administratorAuthDeleteModel);

        // 対象レコード存在確認
        AdminAuthGroupEntity group = checkExistence(administratorAuthDeleteModel);

        // ページ初期化
        administratorAuthDeleteHelper.toPageForLoad(group, metaAuthList, administratorAuthDeleteModel);

        return "administrator/authdelete/delete";
    }

    /**
     * 権限詳細ページへ遷移
     *
     * @return 遷移先ページクラス
     */
    @PostMapping(value = "/", params = "doGoBack")
    @HEHandler(exception = AppLevelListException.class, returnView = "administrator/authdelete/delete")
    public String doGoBack(AdministratorAuthDeleteModel administratorAuthDeleteModel) {

        // 不正遷移チェック
        checkBadTransition(administratorAuthDeleteModel);

        administratorAuthDeleteModel.seq = Integer.parseInt(administratorAuthDeleteModel.getAdminAuthGroupSeq());

        return "redirect:/administrator/auth/detail";
    }

    /**
     * 権限グループ削除実行
     *
     * @return 遷移先ページクラス
     */
    @PostMapping(value = "/", params = "doOnceDelete")
    @HEHandler(exception = AppLevelListException.class, returnView = "administrator/authdelete/delete")
    @HEHandler(exception = DataIntegrityViolationException.class, returnView = "redirect:/administrator/auth",
               messageCode = GROUP_IN_USE)
    public String doOnceDelete(AdministratorAuthDeleteModel administratorAuthDeleteModel,
                               RedirectAttributes redirectAttributes,
                               Model model) {

        // 実行前処理
        String check = preDoAction(administratorAuthDeleteModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        // 不正遷移チェック
        checkBadTransition(administratorAuthDeleteModel);

        // 対象レコード存在確認
        AdminAuthGroupEntity group = checkExistence(administratorAuthDeleteModel);

        authLogic.delete(group);

        addInfoMessage(DELETE_COMPLETED, null, redirectAttributes, model);

        return "redirect:/administrator/auth";
    }

    /**
     * 処理対象レコードの存在確認と取得
     *
     * @return 権限グループ
     */
    @HEHandler(exception = AppLevelListException.class, returnView = "redirect:/administrator/auth")
    protected AdminAuthGroupEntity checkExistence(AdministratorAuthDeleteModel administratorAuthDeleteModel) {

        // 削除対象の権限グループ情報
        AdminAuthGroupEntity group = authLogic.getAdminAuthGroup(
                        Integer.valueOf(administratorAuthDeleteModel.getAdminAuthGroupSeq()));

        // 対象の権限グループが存在しない場合
        if (group == null) {
            throwMessage(GROUP_NOT_FOUND);
        }

        return group;
    }

    /**
     * 不正遷移チェック
     */
    @HEHandler(exception = AppLevelListException.class, returnView = "redirect:/administrator/auth")
    protected void checkBadTransition(AdministratorAuthDeleteModel administratorAuthDeleteModel) {

        // 正常な画面遷移でなかった場合
        if (administratorAuthDeleteModel.getAdminAuthGroupSeq() == null) {
            throwMessage(BAD_TRANSITION);
        }
    }

    /**
     * アクション実行前処理
     *
     * @param administratorAuthDeleteModel
     * @param redirectAttributes
     * @param model
     */
    public String preDoAction(AdministratorAuthDeleteModel administratorAuthDeleteModel,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        // 不正操作チェック
        if (!IdenticalDataCheckUtil.checkIdentical(
                        administratorAuthDeleteModel.getScSeq(), administratorAuthDeleteModel.getDbSeq())) {
            addMessage(IdenticalDataCheckUtil.MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/administrator/auth";
        }
        return null;
    }
}
