/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.campaign.modify;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.campaign.CampaignEntity;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign.CampaignGetByCodeService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign.CampaignRedirectUrlCheckService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign.CampaignUpdateService;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.DiffUtil;
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
 *
 * 広告更新Controller
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 *
 */
@SessionAttributes(value = "campaignModifyModel")
@RequestMapping("/campaign")
@Controller
@PreAuthorize("hasAnyAuthority('CAMPAIGN:8')")
public class CampaignModifyController extends AbstractController {

    /** メッセージコード：不正操作 */
    protected static final String MSGCD_ILLEGAL_OPERATION = "ASC000413";

    /**
     * 表示モード(md):list 検索画面の再検索実行<br/>
     */
    public static final String MODE_LIST = "list";

    /**
     * 表示モード:「list」の場合 再検索
     */
    public static final String FLASH_MD = "md";

    /**
     * 確認画面から
     */
    public static final String FLASH_FROM_CONFIRM = "fromConfirm";

    /** 広告更新Helper */
    private CampaignModifyHelper campaignModifyHelper;

    /** 広告取得サービス */
    private CampaignGetByCodeService campaignGetByCodeService;

    /** リダイレクトURLチェックサービス */
    private CampaignRedirectUrlCheckService campaignRedirectUrlCheckService;

    /** 広告更新サービス */
    private CampaignUpdateService campaignUpdateService;

    @Autowired
    public CampaignModifyController(CampaignModifyHelper campaignModifyHelper,
                                    CampaignGetByCodeService campaignGetByCodeService,
                                    CampaignRedirectUrlCheckService campaignRedirectUrlCheckService,
                                    CampaignUpdateService campaignUpdateService) {
        this.campaignModifyHelper = campaignModifyHelper;
        this.campaignGetByCodeService = campaignGetByCodeService;
        this.campaignRedirectUrlCheckService = campaignRedirectUrlCheckService;
        this.campaignUpdateService = campaignUpdateService;
    }

    /**
     * 初期表示
     *
     * @param campaignCode
     * @param campaignModifyModel
     * @param redirectAttributes
     * @param model
     *
     * @return 入力画面
     */
    @GetMapping(value = "/modify")
    @HEHandler(exception = AppLevelListException.class, returnView = "campaign/modify")
    protected String doLoadIndex(@RequestParam(required = false) Optional<String> campaignCode,
                                 CampaignModifyModel campaignModifyModel,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {

        if (!model.containsAttribute(FLASH_FROM_CONFIRM)) {
            clearModel(CampaignModifyModel.class, campaignModifyModel, model);
        }

        campaignModifyModel.setNormality(true);
        // ゲットパラメータ値を設定
        if (campaignCode.isPresent()) {
            CampaignEntity campaignEntity = new CampaignEntity();

            campaignEntity.setCampaignCode(campaignCode.get());

            campaignModifyModel.setCampaignEntity(campaignEntity);
        }

        // セッション値チェック
        if (campaignModifyModel.getCampaignEntity().getCampaignCode() == null) {
            return "redirect:/error";
        }

        // 広告存在チェック
        if (campaignGetByCodeService.execute(campaignModifyModel.getCampaignEntity().getCampaignCode()) == null) {
            campaignModifyHelper.init(campaignModifyModel);
            addMessage("ASC000402", redirectAttributes, model);
            return "redirect:/error";
        }

        // 確認画面からの遷移の場合は、セッション情報を表示
        if (campaignModifyModel.isConfirm()) {
            campaignModifyHelper.init(campaignModifyModel);
            return "campaign/modify/index";
        }
        CampaignEntity entity =
                        campaignGetByCodeService.execute(campaignModifyModel.getCampaignEntity().getCampaignCode());
        campaignModifyHelper.init(campaignModifyModel, entity);

        // 変更前情報
        campaignModifyModel.setOriginalCampaignEntity(CopyUtil.deepCopy(entity));

        return "campaign/modify/index";
    }

    /**
     * 次のページ
     *
     * @param campaignModifyModel
     * @param error
     * @param redirectAttributes
     * @param model
     *
     * @return 入力画面
     */
    @PostMapping(value = "/modify", params = "doNext")
    @HEHandler(exception = AppLevelListException.class, returnView = "campaign/modify/index")
    public String doNext(@Validated CampaignModifyModel campaignModifyModel,
                         BindingResult error,
                         RedirectAttributes redirectAttributes,
                         Model model) {

        if (error.hasErrors()) {
            return "campaign/modify/index";
        }

        // 不正操作チェック
        if (!campaignModifyModel.isNormality()) {
            // 再検索フラグをセット
            campaignModifyModel.setMd(MODE_LIST);
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/campaign/";
        }

        // リダイレクトURLチェック
        if (!campaignRedirectUrlCheckService.execute(campaignModifyModel.getRedirectUrl())) {
            // 広告URLと一致するようなリダイレクトURLは無限ループとなるので、登録不可とする
            campaignModifyModel.setConfirm(true);
            throwMessage("ASC000414W");
        }

        // セッション値チェック
        if (campaignModifyModel.getCampaignEntity().getCampaignCode() == null) {
            return "redirect:/campaign/index";
        }

        campaignModifyHelper.toCampaignEntity(campaignModifyModel);

        return "redirect:/campaign/modifyconfirm";
    }

    /**
     * 初期表示
     *
     * @param campaignModifyModel
     * @param redirectAttributes
     * @param model
     *
     * @return 入力画面
     */
    @GetMapping(value = "/modifyconfirm")
    @HEHandler(exception = AppLevelListException.class, returnView = "campaign/modifyconfirm")
    protected String doLoadModifyConfirm(CampaignModifyModel campaignModifyModel,
                                         RedirectAttributes redirectAttributes,
                                         Model model) {

        // セッション値チェック
        if (campaignModifyModel.getCampaignEntity().getCampaignCode() == null) {
            return "campaign/modify/confirm";
        }

        campaignModifyHelper.init(campaignModifyModel);

        // 広告存在チェック
        if (campaignGetByCodeService.execute(campaignModifyModel.getCampaignEntity().getCampaignCode()) == null) {
            addMessage("ASC000402", redirectAttributes, model);
            return "redirect:/error";
        }

        // 入力値からエンティティを作成（変更後データ）
        CampaignEntity modifiedCampaignEntity = campaignModifyModel.getCampaignEntity();

        // 変更前データと変更後データの差異リスト作成
        List<String> modifiedList =
                        DiffUtil.diff(campaignModifyModel.getOriginalCampaignEntity(), modifiedCampaignEntity);
        campaignModifyModel.setModifiedList(modifiedList);

        return "campaign/modify/confirm";
    }

    /**
     * 更新処理
     *
     * @param campaignModifyModel
     * @param redirectAttributes
     * @param model
     *
     * @return 入力画面
     */
    @GetMapping(value = "/modifyconfirm", params = "doOnceModify")
    public String doOnceModify(CampaignModifyModel campaignModifyModel,
                               RedirectAttributes redirectAttributes,
                               SessionStatus sessionStatus,
                               Model model) {
        // 登録処理を実行し、一覧に戻ることを示すフラグをセットする
        campaignModifyModel.setNormality(false);

        // セッション値チェック
        if (campaignModifyModel.getCampaignEntity().getCampaignCode() == null) {
            return "redirect:/campaign/index";
        }

        // 広告存在チェック
        if (campaignGetByCodeService.execute(campaignModifyModel.getCampaignEntity().getCampaignCode()) == null) {
            addMessage("ASC000402", redirectAttributes, model);
            return "redirect:/error";
        }

        // 処理チェック
        if (campaignUpdateService.execute(campaignModifyModel.getCampaignEntity()) != 1) {
            addMessage("ASC000406", redirectAttributes, model);
            return "redirect:/error";
        }

        addInfoMessage("ASC000410", null, redirectAttributes, model);

        // 再検索フラグをセット
        redirectAttributes.addFlashAttribute(FLASH_MD, MODE_LIST);

        // Modelをセッションより破棄
        sessionStatus.setComplete();

        return "redirect:/campaign/";
    }

    /**
     * 戻る
     *
     * @param campaignModifyModel
     *
     * @return 入力画面
     */
    @GetMapping(value = "/modifyconfirm", params = "doReturn")
    public String doReturn(CampaignModifyModel campaignModifyModel, RedirectAttributes redirectAttributes) {
        campaignModifyModel.setConfirm(true);
        redirectAttributes.addFlashAttribute(FLASH_FROM_CONFIRM, true);
        return "redirect:/campaign/modify";
    }

}
