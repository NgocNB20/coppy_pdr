package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.inquiry;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.PageInfoHelper;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeInquiryStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeInquiryType;
import jp.co.hankyuhanshin.itec.hitmall.dto.inquiry.InquirySearchDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.inquiry.InquirySearchResultDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry.InquiryGroupListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry.InquirySearchResultListGetService;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * InquiryController Class
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("/inquiry")
@Controller
@SessionAttributes(value = "inquiryModel")
@PreAuthorize("hasAnyAuthority('SHOP:4')")
public class InquiryController extends AbstractController {

    /**
     * デフォルトページ番号
     */
    private static final String DEFAULT_PNUM = "1";

    /**
     * デフォルト：ソート項目
     */
    private static final String DEFAULT_ORDER_FIELD = "firstInquiryTime";

    /**
     * デフォルト：ソート条件(昇順/降順)
     */
    private static final boolean DEFAULT_ORDER_ASC = false;

    /**
     * 表示モード:「list」の場合 再検索
     */
    public static final String MODE_LIST = "list";

    public static final String FLASH_MAIL = "mail";

    /**
     * 問い合わせ検索画面のHelper
     */
    private final InquiryHelper inquiryHelper;

    /**
     * 問い合わせ分類取得ロジック
     */
    private final InquiryGroupListGetLogic inquiryGroupListGetLogic;

    /**
     * 問い合わせ検索サービス
     */
    private final InquirySearchResultListGetService inquirySearchResultListGetService;

    @Autowired
    public InquiryController(InquiryHelper inquiryHelper,
                             InquiryGroupListGetLogic inquiryGroupListGetLogic,
                             InquirySearchResultListGetService inquirySearchResultListGetService) {
        this.inquiryHelper = inquiryHelper;
        this.inquiryGroupListGetLogic = inquiryGroupListGetLogic;
        this.inquirySearchResultListGetService = inquirySearchResultListGetService;
    }

    /**
     * 初期表示<br/>
     *
     * @return 自画面
     */
    @GetMapping("/")
    @HEHandler(exception = AppLevelListException.class, returnView = "inquiry/index")
    public String doLoadIndex(@RequestParam(required = false) Optional<String> md,
                              @RequestParam(required = false) Optional<String> id,
                              InquiryModel inquiryModel,
                              Model model) {

        if (id.isPresent()) {

            inquiryModel.setResultInquiryMemberInfoMail(Objects.requireNonNull(id.get()));

            /* 会員詳細からの遷移 */
            // 検索条件の会員ID(メールアドレス)に「メールアドレス」をセット
            // 会員詳細画面で問い合わせ検索用メールアドレスをもち、リダイレクトスコープで取得
            inquiryModel.setSearchMemberInfoMail(inquiryModel.getResultInquiryMemberInfoMail());
            inquiryModel.setResultInquiryMemberInfoMail(null);

            // 検索
            this.search(inquiryModel, model);

        } else {

            // 通常処理

            // 再検索の場合
            if (md.isPresent() && MODE_LIST.equals(md.get())) {
                // 再検索を実行
                search(inquiryModel, model);
            } else {
                clearModel(InquiryModel.class, inquiryModel, model);
            }
        }
        initComponentValue(inquiryModel);

        return "inquiry/index";

    }

    /**
     * 問い合わせ分類設定画面へ遷移
     *
     * @return 問い合わせ分類設定画面
     */
    @PostMapping(value = "/", params = "doInquiryGroup")
    public String doInquiryGroup() {
        return "redirect:/inquiry/inquirygroup";
    }

    /**
     * 検索
     *
     * @param inquiryModel
     * @param error
     * @param model
     * @return 自画面
     */
    @PostMapping(value = "/", params = "doInquirySearch")
    @HEHandler(exception = AppLevelListException.class, returnView = "inquiry/index")
    public String doInquirySearch(@Validated InquiryModel inquiryModel, BindingResult error, Model model) {
        if (error.hasErrors()) {
            return "inquiry/index";
        }

        // ページング関連項目初期化（limitは画面プルダウンで指定されてくる）
        inquiryModel.setPageNumber(DEFAULT_PNUM);
        inquiryModel.setOrderField(DEFAULT_ORDER_FIELD);
        inquiryModel.setOrderAsc(DEFAULT_ORDER_ASC);

        // 検索
        this.search(inquiryModel, model);

        return "inquiry/index";
    }

    /**
     * 検索結果の表示切替<br/>
     *
     * @param inquiryModel
     * @param model
     * @return 自画面
     */
    @PostMapping(value = "/", params = "doDisplayChange")
    @HEHandler(exception = AppLevelListException.class, returnView = "inquiry/index")
    public String doDisplayChange(@Validated InquiryModel inquiryModel, BindingResult error, Model model) {
        if (error.hasErrors()) {
            return "inquiry/index";
        }

        search(inquiryModel, model);
        return "inquiry/index";
    }

    /**
     * 検索処理
     *
     * @param inquiryModel
     * @param model
     */
    private void search(InquiryModel inquiryModel, Model model) {

        // 検索条件作成
        InquirySearchDaoConditionDto conditionDto =
                        inquiryHelper.toInquirySearchDaoConditionDtoDtoForSearch(inquiryModel);

        // ページング検索セットアップ
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
        pageInfoHelper.setupPageInfo(conditionDto, inquiryModel.getPageNumber(), inquiryModel.getLimit(),
                                     inquiryModel.getOrderField(), inquiryModel.isOrderAsc()
                                    );

        // 検索
        List<InquirySearchResultDto> resultList = inquirySearchResultListGetService.execute(conditionDto);

        // 反映
        inquiryHelper.toPageForSearch(resultList, inquiryModel, conditionDto);

        // ページャーセットアップ
        pageInfoHelper.setupViewPager(conditionDto, inquiryModel);

        // 件数セット
        inquiryModel.setTotalCount(conditionDto.getTotalCount());

    }

    /**
     * プルダウンアイテム情報を取得
     *
     * @param inquiryModel お問い合わせ検索モデル
     */
    private void initComponentValue(InquiryModel inquiryModel) {
        // set Search Inquiry Status
        inquiryModel.setSearchInquiryStatusItems(EnumTypeUtil.getEnumMap(HTypeInquiryStatus.class));

        // set Search Inquiry Type
        Map<String, String> inquiryTypeMap = new HashMap<>();
        inquiryTypeMap.put(HTypeInquiryType.GENERAL.getValue(), HTypeInquiryType.GENERAL.getLabel());
        inquiryModel.setSearchInquiryTypeItems(inquiryTypeMap);

        // set Search Inquiry Group Seq
        Integer shopSeq = getCommonInfo().getCommonInfoBase().getShopSeq();
        List<InquiryGroupEntity> list = inquiryGroupListGetLogic.execute(shopSeq);
        Map<String, String> searchInquiryGroupSeqMap = new HashMap<>();
        for (InquiryGroupEntity entity : list) {
            searchInquiryGroupSeqMap.put(entity.getInquiryGroupSeq().toString(), entity.getInquiryGroupName());
        }
        inquiryModel.setSearchInquiryGroupSeqItems(searchInquiryGroupSeqMap);
    }
}
