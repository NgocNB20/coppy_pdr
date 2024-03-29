package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.campaign.delete;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.campaign.CampaignEntity;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign.CampaignDeleteService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign.CampaignGetByCodeService;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * クーポン削除コントロールクラス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@SessionAttributes(value = "campaignDeleteModel")
@RequestMapping("/campaign")
@Controller
@PreAuthorize("hasAnyAuthority('CAMPAIGN:8')")
public class CampaignDeleteController extends AbstractController {

    /**
     * 検索画面からの遷移時に情報が取得できなかった場合エラー
     */
    public static final String MSGCD_NOT_GET_COUPONDATA = "ACP000401";

    /**
     * 既に削除されているクーポンを削除した場合エラー
     */
    public static final String MSGCD_PREVIOUSLY_DELETE = "ACP000403";

    /**
     * 削除完了メッセージ
     */
    public static final String MSGCD_DELETE_COMPLETE = "ACP000404";

    /**
     * 表示モード(md):list 検索画面の再検索実行
     */
    public static final String MODE_LIST = "list";

    /**
     * 表示モード:「list」の場合 再検索
     */
    public static final String FLASH_MD = "md";

    /**
     * クーポン削除Helper
     */
    private final CampaignDeleteHelper campaignDeleteHelper;

    /**
     *  キャンペーン削除
     */
    private final CampaignDeleteService campaignDeleteService;

    /**
     *  キャンペーン取得
     */
    private final CampaignGetByCodeService campaignGetByCodeService;

    /**
     * コンストラクタ
     *
     * @param campaignDeleteHelper
     * @param campaignDeleteService
     * @param campaignGetByCodeService
     */
    @Autowired
    public CampaignDeleteController(CampaignDeleteHelper campaignDeleteHelper,
                                    CampaignDeleteService campaignDeleteService,
                                    CampaignGetByCodeService campaignGetByCodeService) {
        this.campaignDeleteHelper = campaignDeleteHelper;
        this.campaignDeleteService = campaignDeleteService;
        this.campaignGetByCodeService = campaignGetByCodeService;
    }

    /**
     * 画面初期表示処理
     *
     * @param campaignDeleteModel
     * @param model
     * @return 削除画面
     */
    @GetMapping(value = "/delete")
    @HEHandler(exception = AppLevelListException.class, returnView = "campaign/delete/index")
    public String doLoadIndex(@RequestParam(required = false) String campaignCode,
                              CampaignDeleteModel campaignDeleteModel,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        campaignDeleteModel.setCampaignCode(campaignCode);
        CampaignEntity campaignEntity = campaignGetByCodeService.execute(campaignDeleteModel.getCampaignCode());

        // 他のユーザによる削除対応
        // 指定されたクーポンSEQに対応するクーポンが存在しない場合、検索画面に遷移しエラーメッセージを表示する
        if (campaignEntity == null) {
            addMessage(MSGCD_NOT_GET_COUPONDATA, redirectAttributes, model);
            return "redirect:/campaign/";
        }

        // クーポン情報をページにセットする
        campaignDeleteHelper.toPageForLoad(campaignEntity, campaignDeleteModel);

        return "campaign/delete/index";
    }

    /**
     * クーポン削除処理を行う。<br />
     *
     * <pre>
     * クーポン削除チェック後、クーポンを物理削除する。
     * </pre>
     *
     * @return 検索画面
     */
    @PostMapping(value = "/delete", params = "doOnceDelete")
    @HEHandler(exception = AppLevelListException.class, returnView = "campaign/delete/index")
    public String doOnceRegist(CampaignDeleteModel campaignDeleteModel,
                               RedirectAttributes redirectAttrs,
                               SessionStatus sessionStatus,
                               Model model) {

        // Modelをセッションより破棄
        sessionStatus.setComplete();

        // クーポン削除
        if (campaignDeleteService.execute(campaignDeleteModel.getCampaignEntity()) == 0) {
            // クーポンの削除件数が0件の場合は既に削除済みとする
            addMessage(
                            MSGCD_PREVIOUSLY_DELETE,
                            new Object[] {campaignDeleteModel.getCampaignEntity().getCampaignCode().trim()},
                            redirectAttrs, model
                      );
            return "redirect:/campaign/";
        }

        // 登録完了後メッセージを表示する
        addInfoMessage(MSGCD_DELETE_COMPLETE, new String[] {"クーポン"}, redirectAttrs, model);

        // 検索画面に遷移する
        redirectAttrs.addFlashAttribute(FLASH_MD, MODE_LIST);

        return "redirect:/campaign/";
    }

    /**
     * キャンセルボタン押下時に検索画面へ遷移する。<br />
     *
     * <pre>
     * 検索条件を元に再検索を行う。
     * </pre>
     *
     * @return 検索画面
     */
    @PostMapping(value = "/delete", params = "doCancel")
    public String doCancel(CampaignDeleteModel campaignDeleteModel,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        redirectAttributes.addFlashAttribute(FLASH_MD, MODE_LIST);
        return "redirect:/campaign/";
    }

}
