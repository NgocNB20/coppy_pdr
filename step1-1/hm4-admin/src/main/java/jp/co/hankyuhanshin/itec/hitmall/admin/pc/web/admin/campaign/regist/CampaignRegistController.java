/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.campaign.regist;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.campaign.CampaignEntity;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign.CampaignDataCheckService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign.CampaignGetByCodeService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign.CampaignRedirectUrlCheckService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign.CampaignRegistService;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import org.apache.commons.lang3.ObjectUtils;
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

import java.util.Optional;

/**
 *
 * 広告新規登録Controller
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 *
 */
@RequestMapping("/campaign")
@Controller
@SessionAttributes(value = "campaignRegistModel")
@PreAuthorize("hasAnyAuthority('CAMPAIGN:8')")
public class CampaignRegistController extends AbstractController {

    /** メッセージコード：不正操作 */
    protected static final String MSGCD_ILLEGAL_OPERATION = "ASC000413";

    /**
     * 表示モード(md):list 検索画面の再検索実行<br/>
     */
    public static final String MODE_LIST = "list";

    /**
     * 確認画面から
     */
    public static final String FLASH_FROM_CONFIRM = "fromConfirm";

    /** 広告新規登録Helper */
    private CampaignRegistHelper campaignRegistHelper;

    /** 広告データ存在チェックサービス */
    private CampaignDataCheckService campaignDataCheckService;

    /** 広告データ取得サービス */
    private CampaignGetByCodeService campaignGetByCodeService;

    /** リダイレクトURLチェックサービス */
    private CampaignRedirectUrlCheckService campaignRedirectUrlCheckService;

    /** 広告登録サービス */
    private CampaignRegistService campaignRegistService;

    @Autowired
    public CampaignRegistController(CampaignRegistHelper campaignRegistHelper,
                                    CampaignDataCheckService campaignDataCheckService,
                                    CampaignGetByCodeService campaignGetByCodeService,
                                    CampaignRedirectUrlCheckService campaignRedirectUrlCheckService,
                                    CampaignRegistService campaignRegistService) {
        this.campaignRegistHelper = campaignRegistHelper;
        this.campaignDataCheckService = campaignDataCheckService;
        this.campaignGetByCodeService = campaignGetByCodeService;
        this.campaignRedirectUrlCheckService = campaignRedirectUrlCheckService;
        this.campaignRegistService = campaignRegistService;
    }

    /**
     * 初期表示
     *
     * @param campaignCode
     * @param campaignRegistModel
     * @param redirectAttributes
     * @param model
     *
     * @return 入力画面
     */
    @GetMapping(value = "/regist")
    @HEHandler(exception = AppLevelListException.class, returnView = "campaign/regist/index")
    protected String doLoadIndex(@RequestParam(required = false) Optional<String> campaignCode,
                                 CampaignRegistModel campaignRegistModel,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {

        if (!model.containsAttribute(FLASH_FROM_CONFIRM)) {
            clearModel(CampaignRegistModel.class, campaignRegistModel, model);
        }

        if (campaignCode.isPresent()) {
            campaignRegistModel.setRegistFlg(false);
        } else {
            campaignRegistModel.setRegistFlg(true);
        }

        campaignRegistModel.setNormality(true);

        // 確認画面から遷移の場合
        if (ObjectUtils.isNotEmpty(campaignRegistModel) && ObjectUtils.isNotEmpty(
                        campaignRegistModel.getCampaignEntity())
            && campaignRegistModel.getCampaignEntity().getCampaignCode() != null && campaignRegistModel.isConfirm()) {
            campaignRegistHelper.init(campaignRegistModel);

            return "campaign/regist/index";
        }

        // ゲットパラメータ値を設定
        if (!campaignCode.isPresent()) {
            campaignRegistModel.setButton(false);

            return "campaign/regist/index";
        }

        // 広告存在チェック
        CampaignEntity campaignEntity = campaignGetByCodeService.execute(campaignCode.get());
        if (campaignEntity == null) {
            addWarnMessage("ASC000402", null, redirectAttributes, model);
            campaignRegistModel.setCampaignCode(null);
            campaignRegistModel.setButton(false);
            return "campaign/regist/index";
        }

        campaignRegistHelper.init(campaignRegistModel, campaignEntity);
        campaignRegistModel.setButton(true);

        return "campaign/regist/index";
    }

    /**
     * 次のページ
     *
     * @param campaignRegistModel
     * @param error
     * @param redirectAttributes
     * @param model
     *
     * @return 入力画面
     */
    @PostMapping(value = "/regist", params = "doNext")
    @HEHandler(exception = AppLevelListException.class, returnView = "campaign/regist/index")
    public String doNext(@Validated CampaignRegistModel campaignRegistModel,
                         BindingResult error,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        // 不正操作チェック
        if (!campaignRegistModel.isNormality()) {
            throwMessage(MSGCD_ILLEGAL_OPERATION);
            return "redirect:/error";
        }

        if (error.hasErrors()) {
            return "campaign/regist/index";
        }

        campaignRegistHelper.toCampaignEntity(campaignRegistModel);

        // リダイレクトURLチェック
        if (!campaignRedirectUrlCheckService.execute(campaignRegistModel.getRedirectUrl())) {
            // 広告URLと一致するようなリダイレクトURLは無限ループとなるので、登録不可とする
            campaignRegistModel.setConfirm(true);
            addMessage("ASC000414W", null, redirectAttributes, model);
            return "campaign/regist/index";
        }

        // 広告コードの重複チェック
        if (campaignDataCheckService.execute(campaignRegistModel.getCampaignEntity().getCampaignCode())) {
            addMessage("ASC000401", null, redirectAttributes, model);
            return "campaign/regist/index";
        }

        return "redirect:/campaign/registconfirm";
    }

    /**
     * キャンセルボタン押下処理
     *
     * @param campaignRegistModel
     * @param redirectAttrs
     * @param model
     *
     * @return 運営者検索画面
     */
    @PostMapping(value = "/regist", params = "doCancel")
    public String doCancel(CampaignRegistModel campaignRegistModel, RedirectAttributes redirectAttrs, Model model) {
        // 検索条件復元用情報をセットし、運営者検索画面に遷移
        redirectAttrs.addFlashAttribute(MODE_LIST);

        return "redirect:/campaign/";
    }

    /**
     * 初期表示
     *
     * @param campaignRegistModel
     * @param redirectAttributes
     * @param model
     *
     * @return 入力画面
     */
    @GetMapping(value = "/registconfirm")
    protected String doLoadRegistConfirm(CampaignRegistModel campaignRegistModel,
                                         RedirectAttributes redirectAttributes,
                                         Model model) {

        // セッション値チェック
        if (campaignRegistModel.getCampaignEntity().getCampaignCode() == null) {
            return "redirect:/campaign/regist";
        }

        campaignRegistHelper.init(campaignRegistModel);

        return "campaign/regist/confirm";
    }

    /**
     * 新規登録処理
     *
     * @param campaignRegistModel
     * @param sessionStatus
     * @param redirectAttributes
     * @param model
     *
     * @return 入力画面
     */
    @PostMapping(value = "/registconfirm", params = "doOnceRegist")
    @HEHandler(exception = AppLevelListException.class, returnView = "campaign/registconfirm")
    public String doOnceRegist(CampaignRegistModel campaignRegistModel,
                               SessionStatus sessionStatus,
                               RedirectAttributes redirectAttributes,
                               Model model) {

        // セッション値チェック
        if (campaignRegistModel.getCampaignEntity().getCampaignCode() == null) {

            return "redirect:/campaign/";
        }

        // 登録処理を実行し、一覧に戻ることをしめすフラグをセットする
        campaignRegistModel.setNormality(false);

        campaignRegistHelper.init(campaignRegistModel);

        // 処理チェック
        if (campaignRegistService.execute(campaignRegistModel.getCampaignEntity()) != 1) {
            // 処理エラー
            addMessage("ASC000405", redirectAttributes, model);

            return "redirect:/error";
        }

        addInfoMessage("ASC000408", null, redirectAttributes, model);

        return "redirect:/campaign/";
    }

    /**
     * 戻る
     *
     * @param campaignRegistModel
     * @param redirectAttributes
     * @param model
     *
     * @return 入力画面
     */
    @PostMapping(value = "/registconfirm", params = "doReturn")
    public String doReturn(CampaignRegistModel campaignRegistModel,
                           RedirectAttributes redirectAttributes,
                           Model model) {

        campaignRegistModel.setConfirm(true);
        redirectAttributes.addFlashAttribute(FLASH_FROM_CONFIRM, true);

        return "redirect:/campaign/regist";
    }
}
