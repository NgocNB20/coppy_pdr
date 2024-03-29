/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.campaign.report;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DisplayChangeGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.PageInfoHelper;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.dto.access.AccessInfoSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.access.CampaignEffectDto;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign.CampaignCsvListGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign.CampaignListGetService;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.exception.FileDownloadException;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.apache.commons.collections.CollectionUtils;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 広告効果測定画面
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("/campaign/report")
@SessionAttributes(value = "campaignReportModel")
@Controller
@PreAuthorize("hasAnyAuthority('CAMPAIGN:4')")
public class CampaignReportController extends AbstractController {

    /**
     * ログ
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CampaignReportController.class);

    /**
     * 広告効果測定：不正操作
     */
    private static final String ILLEGAL_OPERATION_MSCD = "AGG000103";

    /**
     * 広告効果測定：デフォルトページ番号
     */
    private static final String DEFAULT_REPORT_SEARCH_PNUM = "1";

    /**
     * 広告効果測定：デフォルト：ソート項目
     */
    private static final String DEFAULT_REPORT_SEARCH_ORDER_FIELD = "campaignCode";

    /**
     * 広告効果測定：デフォルト：ソート条件(昇順/降順)
     */
    private static final boolean DEFAULT_REPORT_SEARCH_ORDER_ASC = true;

    /**
     * 広告効果リスト取得
     */
    private final CampaignListGetService campaignListGetService;

    /**
     * 広告効果CSV出力
     */
    private final CampaignCsvListGetService campaignCsvListGetService;

    /**
     * 広告効果測定Helper
     */
    private final CampaignReportHelper campaignReportHelper;

    /**
     * コンストラクタ
     */
    @Autowired
    public CampaignReportController(CampaignListGetService campaignListGetService,
                                    CampaignCsvListGetService campaignCsvListGetService,
                                    CampaignReportHelper campaignReportHelper) {
        this.campaignListGetService = campaignListGetService;
        this.campaignCsvListGetService = campaignCsvListGetService;
        this.campaignReportHelper = campaignReportHelper;
    }

    /**
     * 初期表示<br/>
     * 期間に本日の日付をセットします<br/>
     *
     * @param campaignReportModel
     * @param model
     * @return class
     */
    @GetMapping("/")
    @HEHandler(exception = AppLevelListException.class, returnView = "coupon/index")
    protected String doLoadIndex(CampaignReportModel campaignReportModel, Model model) {
        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // 本日の日付を取得し、期間にセット
        Timestamp currentDate = dateUtility.getCurrentDate();

        campaignReportModel.setCampaignDateFrom(dateUtility.formatYmdWithSlash(currentDate));
        campaignReportModel.setCampaignDateTo(dateUtility.formatYmdWithSlash(currentDate));

        return "campaign/report/index";
    }

    /**
     * キャンペーンを検索する。<br />
     *
     * <pre>
     * 画面に入力された検索条件を元に検索結果を表示する。
     * </pre>
     *
     * @param campaignReportModel
     * @param model
     * @return 自画面
     */
    @PostMapping(value = "/", params = "doSearch")
    @HEHandler(exception = AppLevelListException.class, returnView = "campaign/report/index")
    protected String doSearch(@Validated CampaignReportModel campaignReportModel,
                              BindingResult error,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        if (error.hasErrors()) {
            return "campaign/report/index";
        }

        // ページング関連項目初期化（limitは画面プルダウンで指定されてくる）
        campaignReportModel.setPageNumber(DEFAULT_REPORT_SEARCH_PNUM);
        campaignReportModel.setOrderField(DEFAULT_REPORT_SEARCH_ORDER_FIELD);
        campaignReportModel.setOrderAsc(DEFAULT_REPORT_SEARCH_ORDER_ASC);

        // 検索処理を行う
        search(campaignReportModel, model, redirectAttributes);

        return "campaign/report/index";

    }

    /**
     * 検索結果の表示切替<br/>
     *
     * @return 自画面
     */
    @PostMapping(value = "/", params = "doDisplayChange")
    @HEHandler(exception = AppLevelListException.class, returnView = "campaign/report/index")
    public String doDisplayChange(@Validated(DisplayChangeGroup.class) CampaignReportModel campaignReportModel,
                                  BindingResult error,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {

        if (error.hasErrors()) {
            return "campaign/report/index";
        }

        // 検索結果チェック
        resultListCheck(campaignReportModel);

        // 検索条件作成
        search(campaignReportModel, model, redirectAttributes);

        return "campaign/report/index";
    }

    /**
     * キャンペーンレポートCSV出力処理を実行します
     *
     * @param campaignReportModel
     * @param error
     * @param model
     * @return
     */
    @PreAuthorize("hasAnyAuthority('CAMPAIGN:8')")
    @PostMapping(value = "/", params = "doCsvDownload")
    @HEHandler(exception = AppLevelListException.class, returnView = "campaign/report/index")
    @HEHandler(exception = FileDownloadException.class, returnView = "campaign/report/index")
    public void doCsvDownload(@Validated CampaignReportModel campaignReportModel,
                              BindingResult error,
                              HttpServletResponse response,
                              Model model) {

        if (error.hasErrors()) {
            throw new FileDownloadException(model);
        }

        AccessInfoSearchForDaoConditionDto accessInfoSearchForDaoConditionDto =
                        campaignReportHelper.toConditionDto(campaignReportModel);

        //CSVダウンロードサービスから取得する
        try {
            campaignCsvListGetService.execute(accessInfoSearchForDaoConditionDto, response);
        } catch (IOException e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new FileDownloadException(model);
        }

    }

    /**
     * 検索<br/>
     *
     * @param campaignReportModel
     * @param model
     * @param redirectAttributes
     */
    protected void search(CampaignReportModel campaignReportModel, Model model, RedirectAttributes redirectAttributes) {

        AccessInfoSearchForDaoConditionDto accessInfoSearchForDaoConditionDto =
                        campaignReportHelper.toConditionDto(campaignReportModel);

        // PageInfo更新
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
        pageInfoHelper.setupPageInfo(accessInfoSearchForDaoConditionDto, campaignReportModel.getPageNumber(),
                                     campaignReportModel.getLimit(), campaignReportModel.getOrderField(),
                                     campaignReportModel.isOrderAsc()
                                    );

        List<CampaignEffectDto> list = campaignListGetService.execute(accessInfoSearchForDaoConditionDto);

        if (list == null) {
            list = new ArrayList<>();
        }

        // 表示件数設定値より多い場合はメッセージを表示
        if (CollectionUtils.isNotEmpty(list)) {
            if (campaignReportModel.getLimit() > 0) {
                if (accessInfoSearchForDaoConditionDto.getTotalCount() > campaignReportModel.getLimit()) {
                    // メッセージを設定
                    addWarnMessage("ASC000407", new Object[] {campaignReportModel.getLimit()}, redirectAttributes,
                                   model
                                  );
                }
            }
        }
        campaignReportHelper.toPageLoad(list, accessInfoSearchForDaoConditionDto, campaignReportModel);

        // ページャーセットアップ
        pageInfoHelper.setupViewPager(accessInfoSearchForDaoConditionDto, campaignReportModel);
    }

    /**
     * 広告効果測定結果リストが空でないことをチェックする<br/>
     * (ブラウザバック後の選択出力などでの不具合防止のため)<br/>
     */
    protected void resultListCheck(CampaignReportModel campaignReportModel) {
        if (!campaignReportModel.isResult() || CollectionUtils.isEmpty(campaignReportModel.getResultItems())) {
            return;
        }

        if (StringUtil.isEmpty(campaignReportModel.getResultItems().get(0).getCampaignCode())) {
            campaignReportModel.setResultItems(null);
            this.throwMessage(ILLEGAL_OPERATION_MSCD);
        }
    }
}
