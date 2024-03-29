package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.news;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.PageInfoHelper;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.NewsSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.news.NewsEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.news.NewsDeleteLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.news.NewsGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.news.NewsForBackListGetService;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

/**
 * ニュース検索画面
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("/news")
@Controller
@SessionAttributes(value = "newsModel")
@PreAuthorize("hasAnyAuthority('SHOP:4')")
public class NewsController extends AbstractController {

    /**
     * デフォルトページ番号
     */
    private static final String DEFAULT_PNUM = "1";

    /**
     * デフォルト：ソート項目
     */
    private static final String DEFAULT_ORDER_FIELD = "newsTime";

    /**
     * デフォルト：ソート条件(昇順/降順)
     */
    private static final boolean DEFAULT_ORDER_ASC = false;

    /**
     * ニュース削除成功メッセージコード<br/>
     * <code>MSGCD_NEWS_DELETE_SUCCESS</code>
     */
    public static final String MSGCD_NEWS_DELETE_SUCCESS = "ASN000103I";

    /**
     * ニュース削除失敗メッセージコード<br/>
     * <code>MSGCD_NEWS_DELETE_FAIL</code>
     */
    public static final String MSGCD_NEWS_DELETE_FAIL = "ASN000104W";

    /**
     * 表示モード:「list」の場合 再検索
     */
    public static final String FLASH_MD = "md";

    /**
     * 表示モード(md):list 検索画面の再検索実行
     */
    public static final String MODE_LIST = "list";

    /**
     * ニュース検索ページDxo
     */
    private NewsHelper newsHelper;
    /**
     * ニュース検索サービス
     */
    private NewsForBackListGetService newsForBackListGetService;
    /**
     * ニュース情報取得ロジック
     */
    private NewsGetLogic newsGetLogic;
    /**
     * ニュース削除ロジック
     */
    private NewsDeleteLogic newsDeleteLogic;

    @Autowired
    public NewsController(NewsHelper newsHelper,
                          NewsForBackListGetService newsForBackListGetService,
                          NewsGetLogic newsGetLogic,
                          NewsDeleteLogic newsDeleteLogic) {
        this.newsHelper = newsHelper;
        this.newsForBackListGetService = newsForBackListGetService;
        this.newsGetLogic = newsGetLogic;
        this.newsDeleteLogic = newsDeleteLogic;
    }

    /**
     * 初期表示
     *
     * @return 自画面
     */
    @GetMapping("/")
    @HEHandler(exception = AppLevelListException.class, returnView = "news/index")
    public String doLoadIndex(@RequestParam(required = false) Optional<String> md, NewsModel newsModel, Model model) {

        // 再検索の場合
        if (model.containsAttribute(FLASH_MD) || (md.isPresent())) {
            // 再検索を実行
            search(newsModel, model);
        } else {
            clearModel(NewsModel.class, newsModel, model);
        }
        initComponentValue(newsModel);
        return "news/index";
    }

    /**
     * ニュース検索
     *
     * @return 自画面
     */
    @PostMapping(value = "/", params = "doNewsSearch")
    @HEHandler(exception = AppLevelListException.class, returnView = "news/index")
    public String doNewsSearch(@Validated NewsModel newsModel,
                               BindingResult error,
                               RedirectAttributes redirectAttributes,
                               Model model) {
        if (error.hasErrors()) {
            return "news/index";
        }
        // ページング関連項目初期化（limitは画面プルダウンで指定されてくる）
        newsModel.setPageNumber(DEFAULT_PNUM);
        newsModel.setOrderField(DEFAULT_ORDER_FIELD);
        newsModel.setOrderAsc(DEFAULT_ORDER_ASC);

        // 検索
        this.search(newsModel, model);
        return "news/index";
    }

    /**
     * 表示順変更
     *
     * @return 自画面
     */
    @PostMapping(value = "/", params = "doDisplayChange")
    @HEHandler(exception = AppLevelListException.class, returnView = "news/index")
    public String doDisplayChange(@Validated NewsModel newsModel, BindingResult error, Model model) {
        if (error.hasErrors()) {
            return "news/index";
        }

        // 検索
        search(newsModel, model);
        return "news/index";
    }

    /**
     * ニュース削除変更
     *
     * @return 自画面
     */
    @PostMapping(value = "/", params = "doNewsDelete")
    @HEHandler(exception = AppLevelListException.class, returnView = "news/index")
    public String doNewsDelete(NewsModel newsModel, RedirectAttributes redirectAttributes, Model model) {
        // 共通情報の取得
        Integer shopSeq = getCommonInfo().getCommonInfoBase().getShopSeq();
        // 削除対象ニュース情報取得
        NewsEntity entity = newsGetLogic.execute(shopSeq, newsModel.getDeleteNewsSeq());

        int result = 0;
        if (entity != null) {
            result = newsDeleteLogic.execute(entity);
        }

        // 削除失敗（削除済み）
        if (result == 0) {
            // 削除失敗メッセージ登録
            addMessage(MSGCD_NEWS_DELETE_FAIL, null, redirectAttributes, model);
        } else {
            // 削除成功メッセージ登録
            addInfoMessage(MSGCD_NEWS_DELETE_SUCCESS, null, redirectAttributes, model);
        }

        // 削除後再検索
        search(newsModel, model);
        return "news/index";
    }

    /**
     * ニュース登録更新画面へ遷移(登録)
     *
     * @return ニュース登録更新画面
     */
    @PreAuthorize("hasAnyAuthority('SHOP:8')")
    @PostMapping(value = "/", params = "doRegist")
    public String doRegist() {
        return "redirect:/news/registupdate";
    }

    /**
     * 検索
     *
     * @param newsModel
     * @param model
     */
    protected void search(NewsModel newsModel, Model model) {

        // 検索条件作成
        NewsSearchForBackDaoConditionDto conditionDto =
                        newsHelper.toNewsSearchForBackDaoConditionDtoForSearch(newsModel);

        // ページング検索セットアップ
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
        pageInfoHelper.setupPageInfo(conditionDto, newsModel.getPageNumber(), newsModel.getLimit(),
                                     newsModel.getOrderField(), newsModel.isOrderAsc()
                                    );

        // 検索
        List<NewsEntity> newsEntityList = newsForBackListGetService.execute(conditionDto);

        // ページへ反映
        newsHelper.toPageForSearch(newsEntityList, newsModel, conditionDto);

        // ページャーセットアップ
        pageInfoHelper.setupViewPager(conditionDto, newsModel);

        // 件数セット
        newsModel.setTotalCount(conditionDto.getTotalCount());
    }

    private void initComponentValue(NewsModel newsModel) {
        // プルダウンアイテム情報を取得
        newsModel.setSearchNewsOpenStatusItems(EnumTypeUtil.getEnumMap(HTypeOpenStatus.class));
    }
}
