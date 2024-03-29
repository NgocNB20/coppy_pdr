/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.administrator.authupdate;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.util.IdenticalDataCheckUtil;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.dto.administrator.MetaAuthType;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdminAuthGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.administrator.AdminAuthGroupLogic;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.common.DiffUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * 権限グループ登録画面用 Action クラス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("/administrator/authupdate")
@Controller
@SessionAttributes(value = "authUpdateModel")
@PreAuthorize("hasAnyAuthority('ADMIN:8')")
public class AuthUpdateController extends AbstractController {

    /**
     * メッセージコード定数：更新しようとした権限グループは存在しない
     */
    protected static final String GROUP_NOT_FOUND = "AYP001004";

    /**
     * メッセージコード定数：登録しようとした権限グループは既に存在している
     */
    protected static final String GROUP_ALREADY_EXISTS = "AYP001001";

    /**
     * メッセージコード：更新完了
     */
    protected static final String UPDATE_COMPLETED = "AYP001006";

    protected static final String MSGCD_ILLEGAL_ADMIN_AUTH = "CHECK-IDENTICALDATA-INVALID-E";

    /**
     * 確認画面から
     */
    public static final String FLASH_FROM_CONFIRM = "fromConfirm";

    /**
     * メタ権限情報。DICON で設定された値が自動バインドされる。必ず設定されている必要あり
     */
    // @Binding(bindingType = BindingType.MUST)
    private List<MetaAuthType> metaAuthList;

    /**
     * 権限グループ操作ロジック
     */
    private AdminAuthGroupLogic authLogic;

    /**
     * Helper
     */
    public AuthUpdateHelper updateHelper;

    @Autowired
    public AuthUpdateController(AdminAuthGroupLogic authLogic, AuthUpdateHelper updateHelper) {
        this.authLogic = authLogic;
        this.updateHelper = updateHelper;
        this.metaAuthList = (List<MetaAuthType>) ApplicationContextUtility.getApplicationContext()
                                                                          .getBean("metaAuthTypeList");
    }

    /**
     * 初期表示アクション
     *
     * @return 遷移先クラス
     */
    @GetMapping("")
    @HEHandler(exception = AppLevelListException.class, returnView = "administrator/authupdate/update")
    protected String doLoadIndex(AuthUpdateModel authUpdateModel, RedirectAttributes redirectAttributes, Model model) {

        if (!model.containsAttribute(FLASH_FROM_CONFIRM)) {
            clearModel(AuthUpdateModel.class, authUpdateModel, model);
        }

        AdminAuthGroupEntity group = null;

        // authSeq を受け取っている場合は それを使用してエンティティを取得する
        if (model.containsAttribute("seq")) {
            String sq = (String) model.getAttribute("seq");
            clearModel(AuthUpdateModel.class, authUpdateModel, model);
            authUpdateModel.setSeq(Integer.parseInt(sq));
        }
        if (authUpdateModel.getSeq() != null) {
            Integer authSeq = authUpdateModel.getSeq();
            group = checkExistence(authSeq);
            updateHelper.toPageForLoad(group, metaAuthList, authUpdateModel);

            // 不正操作対策の情報をセットする
            authUpdateModel.setScSeq(authSeq);
            authUpdateModel.setDbSeq(group.getAdminAuthGroupSeq());
        }
        // 権限SEQ必須の画面です。
        if (authUpdateModel.getScSeq() == null) {
            addMessage(MSGCD_ILLEGAL_ADMIN_AUTH, redirectAttributes, model);
            return "redirect:/administrator/auth/";
        }
        return "administrator/authupdate/update";
    }

    /**
     * 編集内容を破棄して権限詳細ページへ遷移
     *
     * @return 遷移先ページクラス
     */
    @PostMapping(value = "/", params = "doCancel")
    public String doCancel(AuthUpdateModel authUpdateModel,
                           RedirectAttributes redirectAttributes,
                           SessionStatus sessionStatus) {
        redirectAttributes.addFlashAttribute("seq", authUpdateModel.getOriginalEntity().getAdminAuthGroupSeq());
        // authenUpdateModel.seq =
        // authenUpdateModel.getOriginalEntity().getAdminAuthGroupSeq();

        // Modelをセッションより破棄
        sessionStatus.setComplete();

        return "redirect:/administrator/auth/detail?seq=" + authUpdateModel.getOriginalEntity().getAdminAuthGroupSeq();
    }

    /**
     * 権限グループ登録確認画面へ遷移するアクション
     *
     * @return 遷移先クラス
     */
    @PostMapping(value = "/", params = "doConfirm")
    @HEHandler(exception = AppLevelListException.class, returnView = "administrator/authupdate/update")
    public String doConfirm(@Validated AuthUpdateModel authUpdateModel,
                            BindingResult error,
                            RedirectAttributes redirectAttributes,
                            Model model) {
        if (error.hasErrors()) {
            return "administrator/authupdate/update";
        }
        // 実行前処理
        String check = preDoAction(authUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        // 対象レコード存在確認
        checkExistence(authUpdateModel.getOriginalEntity().getAdminAuthGroupSeq());

        // 同一名グループの非存在を確認
        checkNotExistence(authUpdateModel);

        updateHelper.setLevelName(authUpdateModel);

        return "redirect:/administrator/authupdate/confirm";
    }

    /**
     * 初期表示アクション
     *
     * @return 遷移先ページ
     */
    @GetMapping("/confirm")
    protected String doLoadConfirm(AuthUpdateModel authUpdateModel) {

        // ブラウザバックの場合、処理しない
        if (StringUtils.isEmpty(authUpdateModel.getAuthGroupDisplayName())) {
            return "redirect:/administrator/auth";
        }

        //差分チェック用に、変更後データを作成
        authUpdateModel.setModifiedEntity(updateHelper.toAdminAuthGroupEntityForUpdate(authUpdateModel));

        // 変更前データと変更後データの差異リスト作成
        List<String> modifiedList =
                        DiffUtil.diff(authUpdateModel.getOriginalEntity(), authUpdateModel.getModifiedEntity());
        authUpdateModel.setModifiedList(modifiedList);

        // 不正操作対策の情報をセットする
        authUpdateModel.setDbSeq(authUpdateModel.getOriginalEntity().getAdminAuthGroupSeq());
        return "administrator/authupdate/confirm";
    }

    /**
     * 入力画面へ戻るアクション
     *
     * @return 遷移先ページ
     */
    @PostMapping(value = "/confirm", params = "doGoBack")
    public String doGoBack(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(FLASH_FROM_CONFIRM, true);
        return "redirect:/administrator/authupdate";
    }

    /**
     * AdminAuthGroupEntity
     * 権限グループ登録アクション<br />
     *
     * @return 遷移先ページ
     */
    @PostMapping(value = "/confirm", params = "doOnceUpdate")
    @HEHandler(exception = AppLevelListException.class, returnView = "administrator/authupdate/confirm")
    public String doOnceUpdate(AuthUpdateModel authUpdateModel,
                               RedirectAttributes redirectAttributes,
                               Model model,
                               SessionStatus sessionStatus) {

        // 更新対象エンティティが存在することを確認する
        checkExistenceConfirm(authUpdateModel);

        // 同名別権限グループが存在しないことを確認する
        checkNotExistenceConfirm(authUpdateModel);

        // ページ上にある入力情報から登録用 DTO を作成する
        AdminAuthGroupEntity group = updateHelper.toAdminAuthGroupEntityForUpdate(authUpdateModel);

        // 更新する
        authLogic.update(group);

        addInfoMessage(UPDATE_COMPLETED, null, redirectAttributes, model);

        // Modelをセッションより破棄
        sessionStatus.setComplete();

        return "redirect:/administrator/auth";
    }

    /**
     * 処理対象レコードの存在確認と取得
     *
     * @param authSeq 権限グループSEQ
     * @return 権限グループ
     */
    @HEHandler(exception = AppLevelListException.class, returnView = "redirect:/administrator/auth")
    protected AdminAuthGroupEntity checkExistence(Integer authSeq) {

        // 削除対象の権限グループ情報
        AdminAuthGroupEntity group = authLogic.getAdminAuthGroup(authSeq);

        // 対象の権限グループが存在しない場合
        if (group == null) {
            throwMessage(GROUP_NOT_FOUND);
        }

        return group;
    }

    /**
     * 同名の別権限グループが存在しないことを確認する
     */
    protected void checkNotExistence(AuthUpdateModel authUpdateModel) {

        Integer shopSeq = getCommonInfo().getCommonInfoBase().getShopSeq();
        String groupName = authUpdateModel.getAuthGroupDisplayName();

        AdminAuthGroupEntity entity = authLogic.getAdminAuthGroup(shopSeq, groupName);

        // 同名の別権限グループが存在する場合
        if (entity != null && !entity.getAdminAuthGroupSeq()
                                     .equals(authUpdateModel.getOriginalEntity().getAdminAuthGroupSeq())) {
            throwMessage(GROUP_ALREADY_EXISTS);
        }
    }

    /**
     * 処理対象レコードの存在確認と取得
     *
     * @return 権限グループ
     */
    @HEHandler(exception = AppLevelListException.class, returnView = "redirect:/administrator/auth")
    protected AdminAuthGroupEntity checkExistenceConfirm(AuthUpdateModel authUpdateModel) {

        // 権限グループ情報取得
        AdminAuthGroupEntity group =
                        authLogic.getAdminAuthGroup(authUpdateModel.getOriginalEntity().getAdminAuthGroupSeq());

        // 対象の権限グループが存在しない場合
        if (group == null) {
            throwMessage(GROUP_NOT_FOUND);
        }

        return group;
    }

    /**
     * 同名の別権限グループが存在しないことを確認する
     */
    protected void checkNotExistenceConfirm(AuthUpdateModel authUpdateModel) {

        Integer shopSeq = getCommonInfo().getCommonInfoBase().getShopSeq();
        String groupName = authUpdateModel.getAuthGroupDisplayName();

        AdminAuthGroupEntity entity = authLogic.getAdminAuthGroup(shopSeq, groupName);

        // 同名の別権限グループが存在する場合
        if (entity != null && !entity.getAdminAuthGroupSeq()
                                     .equals(authUpdateModel.getOriginalEntity().getAdminAuthGroupSeq())) {
            throwMessage(GROUP_ALREADY_EXISTS);
        }
    }

    /**
     * アクション実行前処理
     *
     * @param authUpdateModel
     * @param redirectAttributes
     * @param model
     */
    public String preDoAction(AuthUpdateModel authUpdateModel, RedirectAttributes redirectAttributes, Model model) {
        // 不正操作チェック
        if (!IdenticalDataCheckUtil.checkIdentical(authUpdateModel.getScSeq(), authUpdateModel.getDbSeq())) {
            addMessage(IdenticalDataCheckUtil.MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/administrator/auth/";
        }
        return null;
    }
}
