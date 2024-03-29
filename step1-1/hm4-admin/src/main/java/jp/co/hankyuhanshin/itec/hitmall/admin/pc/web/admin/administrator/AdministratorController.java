/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.administrator;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.dto.administrator.AdministratorSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdministratorEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.administrator.AdminLogic;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
 * 運営者検索・詳細・削除確認コントローラー
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("/administrator")
@Controller
@SessionAttributes(value = "administratorModel")
@PreAuthorize("hasAnyAuthority('ADMIN:4')")
public class AdministratorController extends AbstractController {

    /**
     * 表示モード:「list」の場合 再検索
     */
    public static final String FLASH_MD = "inputMd";

    /**
     * 表示モード(md):list 検索画面の再検索実行
     */
    public static final String MODE_LIST = "list";

    /**
     * 表示モード(md):new
     */
    public static final String MODE_NEW = "new";

    /**
     * デフォルト：ソート項目
     */
    private static final String DEFAULT_ORDER_FIELD = "administratorId";

    /**
     * デフォルト：ソート項目
     */
    private static final boolean DEFAULT_ORDER_ASC = true;

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AdministratorController.class);

    /**
     * 運営者検索ページHelper<br/>
     */
    private AdministratorHelper administratorHelper;

    /**
     * 運営者情報操作ロジック
     */
    private AdminLogic adminLogic;

    /**
     * コンストラクター
     *
     * @param administratorHelper
     * @param adminLogic
     */
    @Autowired
    public AdministratorController(AdministratorHelper administratorHelper, AdminLogic adminLogic) {
        this.administratorHelper = administratorHelper;
        this.adminLogic = adminLogic;
    }

    /************************************
     ** 運営者検索用
     ************************************/
    /**
     * 運営者検索：初期表示
     *
     * @param administratorModel
     * @param model
     * @return
     */
    @GetMapping(value = "/")
    @HEHandler(exception = AppLevelListException.class, returnView = "administrator/index")
    protected String doLoadIndex(AdministratorModel administratorModel,
                                 @RequestParam(required = false) String mode,
                                 Model model) {

        // 運営者検索Dto取得
        AdministratorSearchForDaoConditionDto administratorConditionDto =
                        ApplicationContextUtility.getBean(AdministratorSearchForDaoConditionDto.class);
        // 再検索以外か判定
        if (!StringUtils.equals(MODE_LIST, mode)) {
            // モデル初期化
            clearModel(AdministratorModel.class, administratorModel, model);
            // 初期ソートセット
            administratorModel.setOrderField(DEFAULT_ORDER_FIELD);
            administratorModel.setOrderAsc(DEFAULT_ORDER_ASC);
        }
        search(administratorModel, administratorConditionDto);

        return "administrator/index";
    }

    /**
     * 検索処理<br/>
     *
     * @param administratorModel
     */
    protected void search(AdministratorModel administratorModel,
                          AdministratorSearchForDaoConditionDto administratorConditionDto) {

        // 検索条件、ソート条件を設定
        administratorConditionDto.setShopSeq(getCommonInfo().getCommonInfoBase().getShopSeq());
        administratorConditionDto.setOrderField(administratorModel.getOrderField());
        administratorConditionDto.setOrderAsc(administratorModel.isOrderAsc());

        // 取得
        List<AdministratorEntity> adminList = adminLogic.getList(administratorConditionDto);

        // ページにセット
        administratorHelper.toPageForSearch(adminList, administratorModel);
    }

    /**
     * 検索<br/>
     *
     * @param administratorModel
     * @return 自画面
     */
    @PostMapping(value = "/", params = "doSearch")
    public String doSearch(AdministratorModel administratorModel) {
        return "administrator/index";
    }

    /**
     * 表示切替<br/>
     *
     * @param administratorModel
     * @return 自画面
     */
    @PostMapping(value = "/", params = "doDisplayChange")
    @HEHandler(exception = AppLevelListException.class, returnView = "administrator/index")
    public String doDisplayChange(AdministratorModel administratorModel) {
        // 運営者検索Dto取得
        AdministratorSearchForDaoConditionDto administratorConditionDto =
                        ApplicationContextUtility.getBean(AdministratorSearchForDaoConditionDto.class);
        search(administratorModel, administratorConditionDto);
        return "administrator/index";
    }

    /**
     * 運営者登録
     *
     * @param administratorModel
     * @param redirectAttributes
     * @return 運営者登録ページ
     */
    @PreAuthorize("hasAnyAuthority('ADMIN:8')")
    @PostMapping(value = "/", params = "doAdministratorRegist")
    public String doAdministratorRegist(AdministratorModel administratorModel, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(FLASH_MD, MODE_NEW);
        return "redirect:/administrator/regist/";
    }

    /************************************
     ** 運営者詳細用
     ************************************/
    /**
     * 初期処理
     *
     * @param administratorSeq
     * @param administratorModel
     * @param model
     * @return 運営者詳細
     */
    @GetMapping(value = "/details")
    @HEHandler(exception = AppLevelListException.class, returnView = "redirect:/administrator/")
    protected String doLoadDetails(@RequestParam(required = false) Optional<Integer> administratorSeq,
                                   AdministratorModel administratorModel,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {

        // 運営者SEQ必須の画面です。
        if (administratorSeq.isEmpty()) {
            addMessage(AdministratorModel.MSGCD_ADMINISTRATOR_NO_DATA, redirectAttributes, model);
            return "redirect:/administrator/";
        }

        administratorModel.setAdministratorSeq(administratorSeq.get());

        AdministratorEntity administratorEntity = null;
        try {
            // 運営者詳細取得サービス実行
            administratorEntity = adminLogic.getAdministrator(administratorModel.getAdministratorSeq());
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            // 運営者詳細情報が取得できなかった
            throwMessage(AdministratorModel.MSGCD_ADMINISTRATOR_NO_DATA);
            return "redirect:/administrator/";
        }

        // ページに反映
        if (administratorEntity != null) {
            administratorHelper.toPageForLoadDetails(administratorEntity, administratorModel);
        } else {
            clearModel(AdministratorModel.class, administratorModel, model);
        }

        return "administrator/details";
    }

    /**
     * 一覧に戻る
     *
     * @param administratorModel
     * @param redirectAttrs
     * @return 管理者情報一覧画面
     */
    @PostMapping(value = "/details", params = "doAdministratorBack")
    public String doAdministratorBack(AdministratorModel administratorModel, RedirectAttributes redirectAttrs) {
        // 検索条件復元用情報をセットし、運営者検索画面に遷移
        redirectAttrs.addFlashAttribute(FLASH_MD, MODE_LIST);
        return "redirect:/administrator/";
    }

    /************************************
     ** 運営者削除確認用
     ************************************/
    /**
     * 初期処理
     *
     * @param administratorModel
     * @param model
     * @return 運営者削除確認
     */
    @PreAuthorize("hasAnyAuthority('ADMIN:8')")
    @GetMapping(value = "/deleteConfirm")
    @HEHandler(exception = AppLevelListException.class, returnView = "administrator/deleteConfirm")
    protected String doLoadDeleteConfirm(@RequestParam(required = false) Optional<String> administratorSeq,
                                         AdministratorModel administratorModel,
                                         RedirectAttributes redirectAttributes,
                                         Model model) {

        // 運営者SEQを取得
        if (administratorSeq.isPresent()) {
            administratorModel.setAdministratorSeq(Integer.parseInt(administratorSeq.get()));
        }

        // 運営者SEQ必須の画面です。
        if (administratorModel.getAdministratorSeq() == null) {
            return "redirect:/error";
        }

        AdministratorEntity administratorDetailsEntity = null;
        try {
            // 運営者詳細取得サービス実行
            administratorDetailsEntity = adminLogic.getAdministrator(administratorModel.getAdministratorSeq());
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            // 運営者詳細情報が取得できなかった
            addMessage(AdministratorModel.MSGCD_ADMINISTRATOR_NO_DATA, redirectAttributes, model);
            return "redirect:/error";
        }

        // ページに反映
        administratorHelper.toPageForLoadDeleteConfirm(administratorDetailsEntity, administratorModel);

        // 自画面
        return "administrator/deleteConfirm";
    }

    /**
     * 運営者情報削除処理
     *
     * @param administratorModel
     * @param redirectAttrs
     * @param sessionStatus
     * @param model
     * @return 運営者検索画面
     */
    @PreAuthorize("hasAnyAuthority('ADMIN:8')")
    @PostMapping(value = "/deleteConfirm", params = "doOnceFinishDelete")
    @HEHandler(exception = AppLevelListException.class, returnView = "administrator/deleteConfirm")
    public String doOnceFinishDelete(AdministratorModel administratorModel,
                                     RedirectAttributes redirectAttrs,
                                     SessionStatus sessionStatus,
                                     Model model) {

        AdministratorEntity administratorEntity = administratorModel.getAdministratorEntity();

        // ログ出力用
        String administratorId = getCommonInfo().getCommonInfoAdministrator().getAdministratorId();
        String adminId = administratorEntity.getAdministratorId();

        Integer administratorSeq = getCommonInfo().getCommonInfoAdministrator().getAdministratorSeq();
        Integer adminSeq = administratorEntity.getAdministratorSeq();

        try {
            // 運営者登録サービスの実行
            adminLogic.delete(administratorEntity);

            // 検索条件復元用情報をセットし、運営者検索画面に遷移
            redirectAttrs.addFlashAttribute(FLASH_MD, MODE_LIST);

            // ログ出力
            LOGGER.warn("[運営者操作]操作運営者ID:" + administratorId + " 対象ID:" + adminId + " 操作:削除" + " 処理結果:成功");
        } catch (Exception e) {
            // ログ出力
            LOGGER.warn("[運営者操作]操作運営者ID:" + administratorId + " 対象ID:" + adminId + " 操作:削除" + " 処理結果:失敗", e);
            throw e;
        }

        // Modelをセッションより破棄
        sessionStatus.setComplete();

        // セッションクリアし、ログアウト画面へ遷移する
        if (administratorSeq.equals(adminSeq)) {
            SecurityContextHolder.clearContext();
        }

        return "redirect:/administrator/";
    }

}
