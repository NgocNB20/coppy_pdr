/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.administrator.update;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAdministratorStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePasswordNeedChangeFlag;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdministratorEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.administrator.AdminLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.division.DivisionMapGetService;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.DiffUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 運営者情報変更入力・確認画面コントローラー
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("/administrator/update")
@Controller
@SessionAttributes(value = "administratorUpdateModel")
@PreAuthorize("hasAnyAuthority('ADMIN:8')")
public class AdministratorUpdateController extends AbstractController {

    /**
     * 運営者更新ページ
     */
    public static final String FLASH_UPDATE_MODEL = "administratorUpdateModel";

    /**
     * 運営者確認ページ
     */
    public static final String FLASH_UPDATE_CONFIRM_MODEL = "updateConfirmModel";

    /**
     * 確認画面から
     */
    public static final String FLASH_FROM_CONFIRM = "fromConfirm";

    /**
     * メッセージコード：不正操作
     */
    protected static final String MSGCD_ILLEGAL_OPERATION = "AYO000304";

    protected static final String MSGCD_ILLEGAL_OPERATION_ADMIN = "AYO000302";

    /**
     * メッセージコード：パスワードが過去4回の変更と一致する
     */
    protected static final String MSGCD_ADMINISTRATOR_PASSWORD_VALIDATION_FAIL = "HM34-3737-003-A-";

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AdministratorUpdateController.class);

    /**
     * メッセージコード：権限グループが存在しない
     */
    protected static final String GROUP_NOT_FOUND = "AYO000801";

    /**
     * 表示モード:「list」の場合 再検索
     */
    public static final String FLASH_MD = "md";

    /**
     * 表示モード(md):list 検索画面の再検索実行
     */
    public static final String MODE_LIST = "list";

    /**
     * 新規運営者登録ページHelper
     */
    private AdministratorUpdateHelper helper;

    /**
     * 運営者情報データチェックサービス
     */
    private AdminLogic adminLogic;

    private DivisionMapGetService divisionMapGetService;

    /**
     * コンストラクター
     *
     * @param helper
     * @param adminLogic
     * @param divisionMapGetService
     */
    @Autowired
    public AdministratorUpdateController(AdministratorUpdateHelper helper,
                                         AdminLogic adminLogic,
                                         DivisionMapGetService divisionMapGetService) {
        this.helper = helper;
        this.adminLogic = adminLogic;
        this.divisionMapGetService = divisionMapGetService;
    }

    /************************************
     ** 運営者情報変更入力画面用
     ************************************/
    /**
     * 初期処理
     *
     * @return 新規運営者登録画面
     */
    @GetMapping("")
    @HEHandler(exception = AppLevelListException.class, returnView = "administrator/update/index")
    protected String doLoadIndex(@RequestParam(required = false) Optional<String> administratorSeq,
                                 AdministratorUpdateModel administratorUpdateModel,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {

        initComponentValue(administratorUpdateModel);

        if (!model.containsAttribute(FLASH_FROM_CONFIRM)) {
            clearModel(AdministratorUpdateModel.class, administratorUpdateModel, model);
            initComponentValue(administratorUpdateModel);
        }

        // 詳細画面から
        if (administratorSeq.isPresent()) {
            administratorUpdateModel.setAdministratorSeq(Integer.parseInt(administratorSeq.get()));
        }

        // 確認画面から
        if (model.containsAttribute(FLASH_UPDATE_CONFIRM_MODEL)) {
            administratorUpdateModel = (AdministratorUpdateModel) model.getAttribute(FLASH_UPDATE_CONFIRM_MODEL);
        }

        // 運営者SEQ必須の画面です。
        if (administratorUpdateModel.getAdministratorSeq() == null) {
            addMessage(MSGCD_ILLEGAL_OPERATION_ADMIN, redirectAttributes, model);
            return "redirect:/administrator/";
        }

        // 確認画面からの遷移の場合は、セッション情報を表示
        if (administratorUpdateModel.isEditFlag()) {
            // 必須項目がある場合 自画面表示 ない場合再取得
            if (!checkInput(administratorUpdateModel)) {
                // ページに反映
                helper.toPageForLoad(administratorUpdateModel.getModifiedEntity(), administratorUpdateModel);

                // 確認画面フラグを初期化
                administratorUpdateModel.setEditFlag(false);

                return "administrator/update/index";
            }

            // 確認画面フラグを初期化
            administratorUpdateModel.setEditFlag(false);
        }

        AdministratorEntity administratorEntity = null;
        try {
            // 運営者詳細取得サービス実行
            administratorEntity = adminLogic.getAdministrator(administratorUpdateModel.getAdministratorSeq());
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            // 運営者詳細情報が取得できなかった
            addMessage(AdministratorUpdateModel.MSGCD_ADMINISTRATOR_NO_DATA, redirectAttributes, model);
            return "redirect:/administrator/";
        }

        // ページに反映
        helper.toPageForLoad(administratorEntity, administratorUpdateModel);

        return "administrator/update/index";
    }

    /**
     * キャンセルボタン押下処理
     *
     * @return 運営者検索画面
     */
    @PostMapping(value = "/", params = "doCancel")
    public String doCancel(AdministratorUpdateModel administratorUpdateModel) {
        administratorUpdateModel.setAdministratorSeq(
                        administratorUpdateModel.getOriginalEntity().getAdministratorSeq());
        return "redirect:/administrator/details?administratorSeq=" + administratorUpdateModel.getAdministratorSeq();
    }

    /**
     * 運営者修正確認画面<br/>
     *
     * @return 運営者修正確認画面
     */
    @PostMapping(value = "/", params = "doConfirm")
    @HEHandler(exception = AppLevelListException.class, returnView = "administrator/update/index")
    public String doConfirm(@Validated AdministratorUpdateModel administratorUpdateModel,
                            BindingResult error,
                            RedirectAttributes redirectAttrs,
                            Model model) {

        if (error.hasErrors()) {
            return "administrator/update/index";
        }

        // 不正操作が行われたかどうか
        if (checkInput(administratorUpdateModel)) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttrs, model);
            return "redirect:/administrator/";
        }

        // 状態チェック
        if (EnumTypeUtil.getEnumFromValue(
                        HTypeAdministratorStatus.class, administratorUpdateModel.getAdministratorStatus()) == null) {
            throwMessage(AdministratorUpdateModel.MSGCD_ADMINISTRATOR_STATUS_ERROR);
        }

        // 画面から運営者詳細DTOに変換
        AdministratorEntity modifiedEntity = helper.toAdministratorEntityForUpdate(administratorUpdateModel);

        // 同一ユーザを更新しようとしているか確認する
        Integer adminSeq = modifiedEntity.getAdministratorSeq();
        Integer shopSeq = getCommonInfo().getCommonInfoBase().getShopSeq();
        String userId = modifiedEntity.getAdministratorId();
        boolean dupe = adminLogic.isSameAdmin(adminSeq, shopSeq, userId);
        if (!dupe) {
            this.throwMessage(AdminLogic.MSGCD_ADMINISTRATOR_UPDATE_FAIL);
        }

        if (administratorUpdateModel.getAdministratorPassword() != null) {
            modifiedEntity.setPasswordNeedChangeFlag(HTypePasswordNeedChangeFlag.ON);
        }
        // 運営者詳細DTOをページに設定
        administratorUpdateModel.setModifiedEntity(modifiedEntity);

        redirectAttrs.addFlashAttribute(FLASH_UPDATE_MODEL, administratorUpdateModel);

        // 運営者修正確認画面へ遷移
        return "redirect:/administrator/update/confirm";
    }

    /**
     * 必須項目を全てチェック<br/>
     *
     * @return true=不正、false=正常
     */
    protected boolean checkInput(AdministratorUpdateModel administratorUpdateModel) {
        if (administratorUpdateModel.getModifiedEntity() == null) {
            return true;
        }

        return false;
    }

    /**
     * コンポーネント値初期化
     *
     * @param administratorUpdateModel
     */
    private void initComponentValue(AdministratorUpdateModel administratorUpdateModel) {
        administratorUpdateModel.setAdministratorStatusItems(EnumTypeUtil.getEnumMap(HTypeAdministratorStatus.class));
        administratorUpdateModel.setAdministratorGroupSeqItems(divisionMapGetService.getAdminAuthGroupMapList());
    }

    /************************************
     ** 運営者情報変更確認画面用
     ************************************/
    /**
     * 初期処理<br/>
     *
     * @return 自画面
     */
    @GetMapping("/confirm")
    public String doLoadConfirm(AdministratorUpdateModel administratorUpdateModel, Model model) {

        // 不正遷移チェック 必須項目の有無でエラーページへ遷移
        if (model.containsAttribute(FLASH_UPDATE_MODEL)) {
            administratorUpdateModel = (AdministratorUpdateModel) model.getAttribute(FLASH_UPDATE_MODEL);
        }

        // ブラウザバックの場合、処理しない
        if (administratorUpdateModel == null) {
            return "redirect:/error";
        }

        // 必須項目を全てチェックし、不正遷移かどうかをチェック
        if (checkInputConfirm(administratorUpdateModel)) {
            return "redirect:/error";
        }

        // 確認画面まできましたよ
        administratorUpdateModel.setEditFlag(true);

        // 入力情報を画面に反映
        helper.toPageForLoad(administratorUpdateModel.getModifiedEntity(), administratorUpdateModel);

        // 変更前データと変更後データの差異リスト作成
        List<String> modifiedList = DiffUtil.diff(administratorUpdateModel.getOriginalEntity(),
                                                  administratorUpdateModel.getModifiedEntity()
                                                 );
        administratorUpdateModel.setModifiedList(modifiedList);

        return "administrator/update/confirm";
    }

    /**
     * 新規運営者登録入力画面へ<br/>
     *
     * @return 新規運営者登録入力画面
     */
    @PostMapping(value = "/confirm", params = "doIndex")
    public String doIndex(AdministratorUpdateModel administratorUpdateModel, RedirectAttributes redirectAttrs) {
        // 入力画面へ
        redirectAttrs.addFlashAttribute(FLASH_UPDATE_CONFIRM_MODEL, administratorUpdateModel);
        redirectAttrs.addFlashAttribute(FLASH_FROM_CONFIRM, true);
        return "redirect:/administrator/update";
    }

    /**
     * 運営者情報登録処理<br/>
     *
     * @return 運営者詳細画面
     */
    @PostMapping(value = "/confirm", params = "doOnceFinishUpdate")
    @HEHandler(exception = AppLevelListException.class, returnView = "redirect:/administrator/update")
    public String doOnceFinishUpdate(AdministratorUpdateModel administratorUpdateModel,
                                     RedirectAttributes redirectAttrs,
                                     SessionStatus sessionStatus,
                                     Model model) {

        // ロジックでエンティティ情報が修正されても影響されないようにエンティティのクローンを作成する
        // ※パスワードの暗号化等で必ずロジック内で修正される
        AdministratorEntity entityForUpdate = CopyUtil.deepCopy(administratorUpdateModel.getModifiedEntity());

        // ログ出力用
        String administratorId = getCommonInfo().getCommonInfoAdministrator().getAdministratorId();
        String adminId = entityForUpdate.getAdministratorId();

        try {
            // 運営者登録サービスの実行
            adminLogic.update(entityForUpdate);

            // 検索条件復元用情報をセットし、運営者検索画面に遷移
            redirectAttrs.addFlashAttribute(FLASH_MD, MODE_LIST);

            // ログ出力
            LOGGER.warn("[運営者操作]操作運営者ID:" + administratorId + " 対象ID:" + adminId + " 操作:変更" + " 処理結果:成功");
        } catch (Exception e) {
            // ログ出力
            LOGGER.warn("[運営者操作]操作運営者ID:" + administratorId + " 対象ID:" + adminId + " 操作:変更" + " 処理結果:失敗", e);
            throw e;
        }

        // Modelをセッションより破棄
        sessionStatus.setComplete();

        return "redirect:/administrator/";
    }

    /**
     * 必須項目を全てチェックし、不正遷移かどうかをチェック<br/>
     *
     * @return true=不正、false=正常
     */
    protected boolean checkInputConfirm(AdministratorUpdateModel administratorUpdateModel) {

        // ID
        if (administratorUpdateModel.getAdministratorId() == null) {
            return true;
        }

        // 氏名(姓)
        if (administratorUpdateModel.getAdministratorLastName() == null) {
            return true;
        }

        // フリガナ(セイ)
        if (administratorUpdateModel.getAdministratorLastKana() == null) {
            return true;
        }

        // 状態
        if (administratorUpdateModel.getAdministratorStatus() == null) {
            return true;
        }

        // グループ
        if (administratorUpdateModel.getAdministratorGroupSeq() == null) {
            return true;
        }

        return false;
    }

}
