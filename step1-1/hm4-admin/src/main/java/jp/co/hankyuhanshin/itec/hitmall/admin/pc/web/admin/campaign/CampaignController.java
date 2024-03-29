package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.campaign;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.PageInfoHelper;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.campaign.CampaignSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.campaign.CampaignEntity;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign.CampaignCsvListGetByCodeService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign.CampaignListGetByCodeService;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.exception.FileDownloadException;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * 広告検索コントローラー<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("/campaign")
@Controller
@SessionAttributes(value = "campaignModel")
@PreAuthorize("hasAnyAuthority('CAMPAIGN:4')")
public class CampaignController extends AbstractController {

    /**
     * ログ
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CampaignController.class);

    /**
     * デフォルトページ番号
     */
    private static final String DEFAULT_PNUM = "1";

    /**
     * 表示モード:「list」の場合 再検索
     */
    public static final String FLASH_MD = "md";

    /**
     * デフォルト：キャンペーンコード
     */
    private static final String DEFAULT_ORDER_FIELD = "campaignCode";

    /**
     * デフォルト：ソート条件(昇順/降順)
     */
    private static final boolean DEFAULT_ORDER_ASC = true;

    /**
     * 広告検索ページDxo
     */
    private final CampaignHelper campaignHelper;

    /**
     * 広告取得サービス
     */
    private final CampaignListGetByCodeService campaignListGetByCodeService;

    /**
     * 広告CSV出力サービス
     */
    private final CampaignCsvListGetByCodeService campaignCsvListGetByCodeService;

    public CampaignController(CampaignHelper campaignHelper,
                              CampaignListGetByCodeService campaignListGetByCodeService,
                              CampaignCsvListGetByCodeService campaignCsvListGetByCodeService) {
        this.campaignHelper = campaignHelper;
        this.campaignListGetByCodeService = campaignListGetByCodeService;
        this.campaignCsvListGetByCodeService = campaignCsvListGetByCodeService;
    }

    /**
     * 検索<br/>
     */
    private void search(CampaignModel campaignModel) {

        // 検索条件作成
        campaignHelper.toConditionDto(campaignModel);

        CampaignSearchForDaoConditionDto campaignSearchDto = campaignModel.getConditionDto();

        // ページング検索セットアップ
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
        pageInfoHelper.setupPageInfo(campaignSearchDto, campaignModel.getPageNumber(), campaignModel.getLimit(),
                                     campaignModel.getOrderField(), campaignModel.isOrderAsc()
                                    );

        // 取得
        List<CampaignEntity> list = campaignListGetByCodeService.execute(campaignSearchDto);

        // 画面に反映
        campaignHelper.toPageList(list, campaignModel);

        // ページャーセットアップ
        pageInfoHelper.setupViewPager(campaignSearchDto, campaignModel);

        campaignModel.setTotalCount(campaignSearchDto.getTotalCount());
    }

    @GetMapping("/")
    protected String doLoadIndex(@RequestParam(required = false) Optional<String> md,
                                 CampaignModel campaignModel,
                                 Model model) {

        // 再検索の場合
        if (model.containsAttribute(FLASH_MD) || (md.isPresent())) {
            // 再検索を実行
            search(campaignModel);
        } else {
            clearModel(CampaignModel.class, campaignModel, model);
        }

        return "campaign/list";
    }

    /**
     * 検索<br/>
     *
     * @return class
     */
    @PostMapping(value = "/", params = "doSearch")
    @HEHandler(exception = AppLevelListException.class, returnView = "campaign/list")
    public String doSearch(@Validated CampaignModel campaignModel, BindingResult error, Model model) {

        if (error.hasErrors()) {
            return "campaign/list";
        }

        // ページング関連項目初期化（limitは画面プルダウンで指定されてくる）
        campaignModel.setPageNumber(DEFAULT_PNUM);
        campaignModel.setOrderField(DEFAULT_ORDER_FIELD);
        campaignModel.setOrderAsc(DEFAULT_ORDER_ASC);

        this.search(campaignModel);
        return "campaign/list";
    }

    /**
     * CSV出力<br/>
     *
     * @return class
     */
    @PreAuthorize("hasAnyAuthority('CAMPAIGN:8')")
    @PostMapping(value = "/", params = "doCsvDownload")
    @HEHandler(exception = AppLevelListException.class, returnView = "campaign/list")
    @HEHandler(exception = FileDownloadException.class, returnView = "campaign/list")
    public void doCsvDownload(CampaignModel campaignModel, HttpServletResponse response, Model model) {

        campaignHelper.toConditionDto(campaignModel);

        // 検索条件に基づいて会員CSV一括出力
        try {
            this.campaignCsvListGetByCodeService.execute(campaignModel.getConditionDto(), response);
        } catch (IOException e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new FileDownloadException(model);
        }

    }

    /**
     * ソート<br/>
     *
     * @return class
     */
    @PostMapping(value = "/", params = "doDisplayChange")
    @HEHandler(exception = AppLevelListException.class, returnView = "campaign/list")
    public String doDisplayChange(CampaignModel campaignModel) {
        search(campaignModel);
        return "campaign/list";
    }

    /**
     * プロパティのクリア<br/>
     *
     * @return class
     */
    @PreAuthorize("hasAnyAuthority('CAMPAIGN:8')")
    @PostMapping(value = "/", params = "doFinish")
    public String doFinish() {
        return "redirect:/campaign/regist/";
    }

}
