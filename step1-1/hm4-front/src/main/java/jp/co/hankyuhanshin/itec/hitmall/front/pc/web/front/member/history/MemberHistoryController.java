// 廃止不要機能
///*
// * Project Name : HIT-MALL4
// *
// * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
// *
// */
//
//package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.history;
//
//import java.util.List;
//import java.util.regex.Pattern;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.SessionAttributes;
//import org.springframework.web.bind.support.SessionStatus;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
//import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSettlementMethodType;
//import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDetailsDto;
//import jp.co.hankyuhanshin.itec.hitmall.front.dto.memberinfo.orderhistory.OrderHistoryListDto;
//import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.OrderSummarySearchForDaoConditionDto;
//import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.ReceiveOrderForHistoryDto;
//import jp.co.hankyuhanshin.itec.hitmall.front.entity.conveni.ConvenienceEntity;
//import jp.co.hankyuhanshin.itec.hitmall.logic.order.ConvenienceLogic;
//import jp.co.hankyuhanshin.itec.hitmall.service.goods.goods.GoodsDetailsListGetService;
//import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.orderhistory.OrderHistoryDetailsGetService;
//import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.orderhistory.OrderHistoryListGetService;
//import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractController;
//import jp.co.hankyuhanshin.itec.hitmall.front.web.PageInfoHelper;
//import jp.co.hankyuhanshin.itec.hitmall.front.base.constant.ValidatorConstants;
//import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
//import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
//
///**
// * 注文履歴 Controller
// *
// * @author kimura
// */
//@RequestMapping("/member/history")
//@Controller
//@SessionAttributes(value = "memberHistoryModel")
//public class MemberHistoryController extends AbstractController {
//
//    /**
//     * 注文履歴一覧：デフォルトページ番号
//     */
//    public static final String DEFAULT_HISTORY_PNUM = "1";
//    /**
//     * 注文履歴一覧：１ページ当たりのデフォルト最大表示件数
//     */
//    public static final int DEFAULT_HISTORY_LIMIT = 5;
//    /**
//     * モデルクリア時のクリア対象外フィールド
//     */
//    public static final String[] CLEAR_EXCLUDED_FIELDS = {"pnum", "limit"};
//
//    /**
//     * メッセージコード：不正遷移
//     */
//    public static final String MSGCD_REFERER_FAIL = "AMH000201";
//
//    /**
//     * 　メッセージコード：セッション情報無し、ブラウザバックなど
//     */
//    protected static final String MSGCD_ILLEGAL_OPERATION = "PKG-3556-005-A-";
//
//    /**
//     * パラメータ受注コードの正規表現
//     */
//    protected static final String OCD_REGEXP = "^[A-Z0-9]{12}$";
//
//    /**
//     * 注文履歴Helper
//     */
//    private MemberHistoryHelper memberHistoryHelper;
//
//    /**
//     * 注文履歴情報リスト取得サービス
//     */
//    private OrderHistoryListGetService orderHistoryListGetService;
//
//    /**
//     * 注文履歴詳細情報取得サービス
//     */
//    private OrderHistoryDetailsGetService orderHistoryDetailsGetService;
//
//    /**
//     * 商品詳細情報リスト取得サービス
//     */
//    private GoodsDetailsListGetService goodsDetailsListGetService;
//
//    /**
//     * コンビニ名称取得ロジック
//     */
//    private ConvenienceLogic convenienceLogic;
//
//    /**
//     * コンストラクタ
//     *
//     * @param orderHistoryDetailsGetService
//     * @param goodsDetailsListGetService
//     * @param convenienceLogic
//     * @param memberHistoryHelper
//     * @param orderHistoryListGetService
//     */
//    @Autowired
//    public MemberHistoryController(OrderHistoryDetailsGetService orderHistoryDetailsGetService, GoodsDetailsListGetService goodsDetailsListGetService, ConvenienceLogic convenienceLogic, MemberHistoryHelper memberHistoryHelper, OrderHistoryListGetService orderHistoryListGetService) {
//        this.orderHistoryDetailsGetService = orderHistoryDetailsGetService;
//        this.goodsDetailsListGetService = goodsDetailsListGetService;
//        this.convenienceLogic = convenienceLogic;
//        this.memberHistoryHelper = memberHistoryHelper;
//        this.orderHistoryListGetService = orderHistoryListGetService;
//    }
//
//    /**
//     * 一覧画面：初期処理
//     *
//     * @param memberHistoryModel 会員注文履歴Model
//     * @param model              Model
//     * @return 一覧画面
//     */
//    @GetMapping(value = {"/", "/index.html"})
//    @HEHandler(exception = AppLevelListException.class, returnView = "member/history/index")
//    protected String doLoadIndex(MemberHistoryModel memberHistoryModel, Model model) {
//
//        // ページング情報初期化
//        if (memberHistoryModel.getPnum() == null) {
//            memberHistoryModel.setPnum(DEFAULT_HISTORY_PNUM);
//        }
//        if (memberHistoryModel.getLimit() == 0) {
//            memberHistoryModel.setLimit(DEFAULT_HISTORY_LIMIT);
//        }
//
//        // モデル初期化
//        clearModel(MemberHistoryModel.class, memberHistoryModel, CLEAR_EXCLUDED_FIELDS, model);
//
//        // 注文履歴一覧の検索
//        searchHistoryList(memberHistoryModel, model);
//
//        return "member/history/index";
//    }
//
//    /**
//     * 詳細画面：初期処理
//     *
//     * @param ocd                　注文履歴URLパラメータ
//     * @param memberHistoryModel 注文履歴Model
//     * @param redirectAttributes
//     * @param sessionStatus
//     * @param model
//     * @return 詳細画面
//     */
//    @GetMapping(value = {"/detail", "/detail.html"})
//    @HEHandler(exception = AppLevelListException.class, returnView = "redirect:/member/history/index.html")
//    protected String doLoadDetail(@RequestParam(required = false) String ocd, MemberHistoryModel memberHistoryModel, RedirectAttributes redirectAttributes, SessionStatus sessionStatus, Model model) {
//
//        if (StringUtils.isEmpty(ocd)) {
//            // URLパラメータが不足した遷移の場合（session破棄ができないため、こちらで制御）
//            addMessage(getMsgcdRefererFail(), redirectAttributes, model);
//            return getBackpageClass();
//        }
//
//        // 認証画面から遷移した時や、URLにクエリーがついている場合は ocd に値が存在するが
//        // 当画面でリロードを行うと ocd にはないため、保管用の変数から取得する。
//        String orderCode = memberHistoryModel.getOcd() == null ? memberHistoryModel.getSaveOcd() : memberHistoryModel.getOcd();
//
//        // orderCodeがnullの場合は、明示的にエラーページへ遷移させる
//        if (StringUtils.isEmpty(orderCode)) {
//            return "redirect:/error.html";
//        }
//
//        if (!Pattern.matches(ValidatorConstants.REGEX_ORDER_CODE, orderCode)) {
//            addMessage(getMsgcdRefererFail(), redirectAttributes, model);
//            return getBackpageClass();
//        }
//
//        return doLoadOrder(orderCode, memberHistoryModel, redirectAttributes, sessionStatus, model);
//    }
//
//    /**
//     * 購入履歴表示処理
//     *
//     * @param orderCode          お申込み番号
//     * @param memberHistoryModel
//     * @param redirectAttributes
//     * @param sessionStatus
//     * @param model
//     * @return 遷移先画面
//     */
//    private String doLoadOrder(String orderCode, MemberHistoryModel memberHistoryModel, RedirectAttributes redirectAttributes, SessionStatus sessionStatus, Model model) {
//
//        // 受注データを取得
//        Integer memberInfoSeq = getCommonInfo().getCommonInfoUser().getMemberInfoSeq();
//
//        ReceiveOrderForHistoryDto receiveOrderForHistoryDto = null;
//        try {
//            receiveOrderForHistoryDto = orderHistoryDetailsGetService.execute(memberInfoSeq, orderCode);
//        } catch (AppLevelListException e) {
//            // 注文情報が取得できない場合、画面の表示制御ができないのでエラー画面に飛ばす
//            addMessage(e.getErrorList().get(0).getMessageCode(), redirectAttributes, model);
//            return "redirect:/error.html";
//        }
//
//        // 注文履歴情報の会員情報についてチェック
//        if (checkOrderMember(receiveOrderForHistoryDto)) {
//            addMessage(OrderHistoryDetailsGetService.MSGCD_MEMBER_DEFERENT_ORDER, redirectAttributes, model);
//            return "redirect:/error.html";
//        }
//
//        // 受注詳細情報リスト取得
//        List<GoodsDetailsDto> goodsDetailsList = goodsDetailsListGetService.execute(memberHistoryHelper.getOrderGoodsSeqList(receiveOrderForHistoryDto));
//
//        // コンビニ名称の取得
//        String conveniName = null;
//        if (HTypeSettlementMethodType.CONVENIENCE.equals(receiveOrderForHistoryDto.getSettlementMethodEntity().getSettlementMethodType())) {
//            conveniName = createConvenience(receiveOrderForHistoryDto.getMulPayBillEntity().getConvenience());
//        }
//
//        // ページへの反映
//        memberHistoryHelper.toPageForLoadDetail(receiveOrderForHistoryDto, goodsDetailsList, conveniName, memberHistoryModel);
//
//        // pnum保持のためにセッション破棄をやめる
//        //// Modelをセッションより破棄
//        //sessionStatus.setComplete();
//
//        return prerender(memberHistoryModel, redirectAttributes, model);
//    }
//
//    /**
//     * 注文履歴一覧の検索<br/>
//     *
//     * @param memberHistoryModel 注文履歴Model
//     * @param model              Model
//     */
//    protected void searchHistoryList(MemberHistoryModel memberHistoryModel, Model model) {
//
//        // PageInfoヘルパー取得
//        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
//
//        OrderSummarySearchForDaoConditionDto conditionDto = memberHistoryHelper.toOrderSummarySearchForDaoConditionDtoForLoad(memberHistoryModel);
//
//        // ページングセットアップ
//        conditionDto = pageInfoHelper.setupPageInfo(conditionDto, memberHistoryModel.getPnum(), memberHistoryModel.getLimit());
//
//        List<OrderHistoryListDto> resultList = orderHistoryListGetService.execute(conditionDto);
//        memberHistoryHelper.toPageForLoad(resultList, conditionDto, memberHistoryModel);
//
//        // ぺージャーセットアップ
//        pageInfoHelper.setupViewPager(conditionDto, model);
//    }
//
//    /**
//     * 注文履歴情報の会員情報についてチェックを行う
//     * <pre>
//     * アクセス中の会員の注文履歴かをチェック
//     * </pre>
//     *
//     * @param dto 受注Dto
//     * @return true：上記チェックでエラーがある場合
//     */
//    protected boolean checkOrderMember(ReceiveOrderForHistoryDto dto) {
//
//        Integer memberInfoSeq = getCommonInfo().getCommonInfoUser().getMemberInfoSeq();
//
//        if (!dto.getOrderSummaryDto().getMemberInfoSeq().equals(memberInfoSeq)) {
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * @return 戻り先ページ取得
//     */
//    public String getBackpageClass() {
//        return "redirect:/member/history/index.html";
//    }
//
//    /**
//     * エラーメッセージ取得
//     *
//     * @return MSGCD_REFERER_FAIL
//     */
//    public String getMsgcdRefererFail() {
//        return MSGCD_REFERER_FAIL;
//    }
//
//    /**
//     * コンビニプルダウン作成<br/>
//     *
//     * @param convenienceCode コンビニコード
//     * @return コンビニ名称
//     */
//    protected String createConvenience(String convenienceCode) {
//        List<ConvenienceEntity> convenienceList = convenienceLogic.getConvenienceList();
//        for (ConvenienceEntity convenience : convenienceList) {
//            if (convenienceCode.equals(convenience.getConveniCode())) {
//                return convenience.getConveniName();
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 表示前処理
//     *
//     * @param memberHistoryModel
//     * @param redirectAttributes
//     * @param model
//     * @return 自画面
//     */
//    public String prerender(MemberHistoryModel memberHistoryModel, RedirectAttributes redirectAttributes, Model model) {
//
//        if (isSessionCheck(memberHistoryModel)) {
//            // 注文情報が取得できない場合、画面の表示制御ができないのでエラー画面に飛ばす
//            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
//            return "redirect:/error.html";
//        }
//
//        // return super.prerender();
//        return "member/history/detail";
//    }
//
//    /**
//     * ブラウザバックなどで戻った場合に必要なセッション情報が消えているかチェックをする<br/>
//     *
//     * @return true:エラーあり  false:エラー無し
//     */
//    protected boolean isSessionCheck(MemberHistoryModel memberHistoryModel) {
//
//        if (StringUtils.isEmpty(memberHistoryModel.getOrderCode())) {
//            // 注文番号が取得できない場合エラー
//            return true;
//        }
//        return false;
//    }
//}
