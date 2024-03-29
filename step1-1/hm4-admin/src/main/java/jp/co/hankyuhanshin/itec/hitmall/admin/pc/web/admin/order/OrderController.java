/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DisplayChangeGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.validation.CouponValidator;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.validation.group.OrderSearchGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.validation.group.OutputGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.validation.group.SelectShipmentRegistGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.validation.group.SelectionOutput1Group;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.validation.group.SelectionOutput2Group;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.validation.group.SelectionShipmentRegistOutput1Group;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.validation.group.SelectionShipmentRegistOutput2Group;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.validation.group.ShipmentSearchGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.PageInfoHelper;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.constant.BatchName;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBillStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBillType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDate;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderOutData;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderSite;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePaymentStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePerson;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSelectMapOrderStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSelectionOrderOutData;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSelectionShipmentRegistOutData;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeShipmentStatus;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchOrderGoodsResultDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchOrderResultDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ShipmentRegistDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.batch.BatchJobInsertLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.division.DivisionMapGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.OrderSearchOrderGoodsListGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.OrderSearchOrderListGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.ShipmentListRegistService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.ShipmentSearchRegistService;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelException;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.ListUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 受注検索アクション<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("/order")
@Controller
@SessionAttributes(value = "orderModel")
@PreAuthorize("hasAnyAuthority('ORDER:4')")
public class OrderController extends AbstractController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    /**
     * 受注検索：NONE_LIMIT
     */
    private static final int NONE_LIMIT = -1;

    /**
     * 受注検索：デフォルトページ番号
     */
    private static final String DEFAULT_ORDERSEARCH_PNUM = "1";

    /**
     * 受注検索：デフォルト：ソート項目
     */
    private static final String DEFAULT_ORDERSEARCH_ORDER_FIELD = "orderTime";

    /**
     * 受注検索商品：デフォルト：商品管理番号
     */
    private static final String DEFAULT_GOODSSEARCH_ORDER_FIELD = "goodsGroupCode";

    /**
     * 受注検索：デフォルト：ソート条件(昇順/降順)
     */
    private static final boolean DEFAULT_ORDERSEARCH_ORDER_ASC = false;

    /**
     * 受注検索：デフォルト：最大表示件数
     */
    private static final int DEFAULT_ORDERSEARCH_LIMIT = 100;

    /**
     * 表示モード(md):list 検索画面の再検索実行
     */
    public static final String MODE_LIST = "list";

    /**
     * 受注CSVのファイルパス
     */
    public static final String ORDERCSV_FILE_PATH = "orderCsvAsynchronous.file.path";

    public static final String MESSAGE_VALID_DOWNLOAD_ORDERCSV = "ORDER-ORDERCSV-001-E";

    public static final String MESSAGE_VALID_DOWNLOAD_ORDERCSV_NOT_EXIST = "ORDER-ORDERCSV-002-E";

    /**
     * 受注検索helper
     */
    private final OrderHelper orderHelper;

    /**
     * 受注検索（受注一覧）取得サービス
     */
    private final OrderSearchOrderListGetService orderSearchOrderListGetService;

    /**
     * 出荷リスト登録サービス
     */
    private final ShipmentListRegistService shipmentListRegistService;

    /**
     * 検索条件出荷登録サービス
     */
    private final ShipmentSearchRegistService shipmentSearchRegistService;

    /**
     * 受注検索（商品別一覧）取得サービス
     */
    private final OrderSearchOrderGoodsListGetService orderSearchOrderGoodsListGetService;

    /**
     * 分類リスト取得サービス
     */
    private final DivisionMapGetService divisionMapGetService;

    /**
     * 決済方法用動的バリデータ
     */
    private final CouponValidator couponValidator;

    /**
     * バッチのJobLauncher
     */
    private final JobLauncher jobLauncher;

    /**
     * バッチのリクエストデータ登録ロジック
     */
    private final BatchJobInsertLogic batchJobInsertLogic;

    /**
     * コンストラクター
     *
     * @param orderHelper
     * @param orderSearchOrderListGetService
     * @param shipmentListRegistService
     * @param shipmentSearchRegistService
     * @param divisionMapGetService
     * @param couponValidator
     * @param batchJobInsertLogic
     */
    @Autowired
    public OrderController(OrderHelper orderHelper,
                           OrderSearchOrderListGetService orderSearchOrderListGetService,
                           ShipmentListRegistService shipmentListRegistService,
                           ShipmentSearchRegistService shipmentSearchRegistService,
                           OrderSearchOrderGoodsListGetService orderSearchOrderGoodsListGetService,
                           DivisionMapGetService divisionMapGetService,
                           CouponValidator couponValidator,
                           BatchJobInsertLogic batchJobInsertLogic) {
        this.orderHelper = orderHelper;
        this.orderSearchOrderListGetService = orderSearchOrderListGetService;
        this.shipmentListRegistService = shipmentListRegistService;
        this.shipmentSearchRegistService = shipmentSearchRegistService;
        this.orderSearchOrderGoodsListGetService = orderSearchOrderGoodsListGetService;
        this.divisionMapGetService = divisionMapGetService;
        this.couponValidator = couponValidator;
        this.jobLauncher = ApplicationContextUtility.getApplicationContext()
                                                    .getBean("jobLauncherAsync", JobLauncher.class);
        this.batchJobInsertLogic = batchJobInsertLogic;
    }

    @InitBinder("orderModel")
    public void initBinder(WebDataBinder error) {
        // 検索条件クーポンの動的バリデータをセット
        error.addValidators(couponValidator);
    }

    /**
     * アクション実行前に処理結果表示をクリア
     *
     * @param orderModel 受注検索モデル
     */
    public void preDoAction(OrderModel orderModel) {
        orderModel.setCheckMessageItems(null);
        if (orderModel.getPageNumber() == null) {
            orderModel.setPageNumber(DEFAULT_ORDERSEARCH_PNUM);
        }
    }

    /**
     * 初期処理<br/>
     *
     * @return 自画面
     */
    @GetMapping(value = "/")
    public String doLoadIndex(@RequestParam(required = false) Optional<String> md,
                              @RequestParam(required = false) Optional<Integer> memberInfoSeq,
                              OrderModel orderModel,
                              BindingResult error,
                              RedirectAttributes redirectAttrs,
                              Model model) {

        // 会員詳細画面からの遷移の場合、パラメータで取得した会員SEQを条件に検索を行う
        if (md.isPresent() && MODE_LIST.equals(md.get()) || memberInfoSeq.isPresent()) {
            // 会員詳細 ⇒ 受注詳細 ⇒ 戻る の流れで
            // 実施する際に、pageLimitが未設定のケースがあったため、こちらのロジックを追加する
            if (orderModel.getLimit() == 0) {
                orderModel.setLimit(orderModel.getPageDefaultLimitModel());
            }
            orderHelper.toOrderSearchWhenMemberInfoDetails(orderModel);
            doOrderSearch(orderModel, error, model);
        } else {
            clearModel(OrderModel.class, orderModel, model);
        }

        // プルダウンアイテム情報を取得
        initComponentValue(orderModel);

        // 実行前処理
        preDoAction(orderModel);

        return "order/index";
    }

    /**
     * 画面表示切替処理<br/>
     *
     * @return 自画面
     */
    @PostMapping(value = "/", params = "doDisplayChange")
    @HEHandler(exception = AppLevelListException.class, returnView = "order/index")
    public String doDisplayChange(@Validated(DisplayChangeGroup.class) OrderModel orderModel,
                                  BindingResult error,
                                  Model model) {

        // 実行前処理
        preDoAction(orderModel);

        if (error.hasErrors()) {
            return "order/index";
        }

        // 検索結果チェック
        resultListCheck(orderModel);
        if (orderModel.isGoodsSearch()) {
            // 受注検索商品一覧取得処理実行
            searchGoods(true, orderModel, model);
        } else {
            // 受注検索受注一覧取得処理実行
            searchOrder(true, orderModel, model);
        }

        return "order/index";
    }

    /* Search Action */

    /**
     * 受注番号別一覧表示処理<br/>
     *
     * @return 自画面
     */
    @PostMapping(value = "/", params = "doOrderSearch")
    @HEHandler(exception = AppLevelListException.class, returnView = "order/index")
    public String doOrderSearch(@Validated(OrderSearchGroup.class) OrderModel orderModel,
                                BindingResult error,
                                Model model) {

        // 実行前処理
        preDoAction(orderModel);

        if (error.hasErrors()) {
            return "order/index";
        }

        // 受注検索受注一覧取得処理実行
        searchOrder(false, orderModel, model);

        orderModel.onOrderSearch();

        return "order/index";
    }

    /**
     * 商品番号別一覧表示処理<br/>
     *
     * @return 自画面
     */
    @PostMapping(value = "/", params = "doOrderGoodsSearch")
    @HEHandler(exception = AppLevelListException.class, returnView = "order/index")
    public String doOrderGoodsSearch(@Validated(OrderSearchGroup.class) OrderModel orderModel,
                                     BindingResult error,
                                     Model model) {

        // 実行前処理
        preDoAction(orderModel);

        if (error.hasErrors()) {
            return "order/index";
        }

        // 受注検索商品一覧取得処理実行
        searchGoods(false, orderModel, model);

        orderModel.onGoodsSearch();

        return "order/index";
    }

    /**
     * 出荷登録用一覧表示処理<br/>
     *
     * @return 自画面
     */
    @PreAuthorize("hasAnyAuthority('ORDER:8')")
    @PostMapping(value = "/", params = "doShipmentSearch")
    @HEHandler(exception = AppLevelListException.class, returnView = "order/index")
    public String doShipmentSearch(@Validated(ShipmentSearchGroup.class) OrderModel orderModel,
                                   BindingResult error,
                                   Model model) {

        // 実行前処理
        preDoAction(orderModel);

        if (error.hasErrors()) {
            return "order/index";
        }

        orderModel.onShipmentRegister();

        // 受注検索受注一覧取得処理実行
        searchOrder(false, orderModel, model);

        return "order/index";
    }

    /**
     * 出荷登録・選択出力
     * <p>
     * 選択されている出荷登録CSVを出力します(ヘッダ側)
     * <p>
     * CSV：出荷登録CSV<br/>
     *
     * @return Class&lt;?&gt;
     */
    @PreAuthorize("hasAnyAuthority('ORDER:8')")
    @PostMapping(value = "/", params = "doSelectionShipmentRegistOutput1")
    @HEHandler(exception = AppLevelListException.class, returnView = "order/index")
    public String doSelectionShipmentRegistOutput1(
                    @Validated(SelectionShipmentRegistOutput1Group.class) OrderModel orderModel,
                    BindingResult error,
                    Model model) {

        // 実行前処理
        preDoAction(orderModel);

        if (error.hasErrors()) {
            return "order/index";
        }

        // 検索結果チェック
        resultListCheck(orderModel);

        // CSV出力
        shipmentRegistOutput(orderModel.getShipmentRegistData1(), orderModel);
        return "order/index";
    }

    /**
     * 出荷登録・選択出力
     * <p>
     * 選択されている出荷登録CSVを出力します(フッタ側)
     * <p>
     * CSV：出荷登録CSV<br/>
     *
     * @return Class&lt;?&gt;
     */
    @PreAuthorize("hasAnyAuthority('ORDER:8')")
    @PostMapping(value = "/", params = "doSelectionShipmentRegistOutput2")
    @HEHandler(exception = AppLevelListException.class, returnView = "order/index")
    public String doSelectionShipmentRegistOutput2(
                    @Validated(SelectionShipmentRegistOutput2Group.class) OrderModel orderModel,
                    BindingResult error,
                    Model model) {

        // 実行前処理
        preDoAction(orderModel);

        if (error.hasErrors()) {
            return "order/index";
        }

        // 検索結果チェック
        resultListCheck(orderModel);

        // CSV出力
        shipmentRegistOutput(orderModel.getShipmentRegistData2(), orderModel);
        return "order/index";
    }

    /**
     * 出荷登録処理<br />
     *
     * @return 自画面
     */
    @PreAuthorize("hasAnyAuthority('ORDER:8')")
    @PostMapping(value = "/", params = "doSelectShipmentRegist")
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    @HEHandler(exception = AppLevelListException.class, returnView = "order/index")
    public String doSelectShipmentRegist(@Validated(SelectShipmentRegistGroup.class) OrderModel orderModel,
                                         BindingResult error,
                                         Model model) {

        // 実行前処理
        preDoAction(orderModel);

        if (error.hasErrors()) {
            return "order/index";
        }

        // 検索結果チェック
        resultListCheck(orderModel);

        // 出荷登録パラメータ作成
        List<ShipmentRegistDto> shipmentRegistDtoList = orderHelper.toShipmentRegistDtoForRegist(orderModel);

        if (ListUtils.isEmpty(shipmentRegistDtoList)) {
            orderHelper.setFinishPageItem("AOX000501W", null, orderModel);
            return "order/index";
        }

        try {
            // 出荷リスト登録処理実行
            CommonInfoUtility commonInfoUtility = ApplicationContextUtility.getBean(CommonInfoUtility.class);
            List<CheckMessageDto> checkMessageDtoList = shipmentListRegistService.execute(shipmentRegistDtoList,
                                                                                          commonInfoUtility.getAdministratorName(
                                                                                                          getCommonInfo())
                                                                                         );
            orderModel.setCheckMessageItems(checkMessageDtoList);
        } catch (AppLevelListException e) {
            LOGGER.error("例外処理が発生しました", e);
            List<AppLevelException> errorList = e.getErrorList();
            for (AppLevelException ae : errorList) {
                orderHelper.addFinishPageItem(ae.getMessageCode(), ae.getArgs(), orderModel);
            }
        }

        // 同ページを同条件で再検索
        searchOrder(true, orderModel, model);

        return "order/index";
    }

    /**
     * 出荷全件登録<br/>
     *
     * @return 自画面
     */
    @PreAuthorize("hasAnyAuthority('ORDER:8')")
    @PostMapping(value = "/", params = "doAllShipmentRegist")
    @HEHandler(exception = AppLevelListException.class, returnView = "order/index")
    public String doAllShipmentRegist(@Validated OrderModel orderModel, BindingResult error, Model model) {

        // 実行前処理
        preDoAction(orderModel);

        if (error.hasErrors()) {
            return "order/index";
        }

        // 検索結果チェック
        resultListCheck(orderModel);

        OrderSearchConditionDto orderSearchListConditionDto = orderModel.getOrderSearchConditionDto();
        OrderSearchConditionDto copyCondition = CopyUtil.deepCopy(orderSearchListConditionDto);
        copyCondition.getPageInfo().setLimit(NONE_LIMIT);
        // 全件出荷登録flagを設定する
        copyCondition.setAllShipmentRegistFlag(true);

        // メール送信のデフォルト設定をHTMLに設定
        try {
            // 検索条件出荷登録処理実行
            CommonInfoUtility commonInfoUtility = ApplicationContextUtility.getBean(CommonInfoUtility.class);
            List<CheckMessageDto> checkMessageDtoList = shipmentSearchRegistService.execute(copyCondition, true,
                                                                                            commonInfoUtility.getAdministratorName(
                                                                                                            getCommonInfo())
                                                                                           );
            orderModel.setCheckMessageItems(checkMessageDtoList);
        } catch (AppLevelListException e) {
            LOGGER.error("例外処理が発生しました", e);
            List<AppLevelException> errorList = e.getErrorList();
            for (AppLevelException ae : errorList) {
                orderHelper.addFinishPageItem(ae.getMessageCode(), ae.getArgs(), orderModel);
            }
        }

        // 同ページを同条件で再検索
        searchOrder(true, orderModel, model);

        return "order/index";
    }

    /**
     * 出荷データアップロード画面遷移<br/>
     *
     * @return 出荷データアップロード画面
     */
    @PreAuthorize("hasAnyAuthority('ORDER:8')")
    @PostMapping(value = "/", params = "doShipmentUpload")
    public String doShipmentUpload(OrderModel orderModel, Model model) {
        // 実行前処理
        preDoAction(orderModel);
        return "redirect:/order/shipmentUpload/";
    }

    /**
     * 全件CSV出力
     *
     * @param orderModel
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ORDER:8')")
    @PostMapping(value = "/", params = "doOutput")
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    @HEHandler(exception = AppLevelListException.class, returnView = "order/index")
    public String doOutput(@Validated(OutputGroup.class) OrderModel orderModel, BindingResult error) {
        // 実行前処理
        preDoAction(orderModel);

        if (error.hasErrors()) {
            return "order/index";
        }
        // 検索条件作成
        OrderSearchConditionDto orderSearchConditionDto = orderHelper.toOrderSearchListConditionDto(orderModel);
        orderSearchConditionDto.setShopSeq(this.getCommonInfo().getCommonInfoBase().getShopSeq());

        // 検索条件の最新化 エラーがある場合は、終了
        if (orderModel.getMsgCodeList() != null && !orderModel.getMsgCodeList().isEmpty()) {
            List<String> msgCodeList = orderModel.getMsgCodeList();
            for (String messageCode : msgCodeList) {
                // メッセージ引数マップに一致するメッセージコードがある場合は引数として設定する
                if (orderModel.getMsgArgMap().containsKey(messageCode)) {
                    throwMessage(messageCode, orderModel.getMsgArgMap().get(messageCode));
                } else {
                    throwMessage(messageCode);
                }
            }
        }

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = null;
        try {
            json = ow.writeValueAsString(orderSearchConditionDto);
        } catch (JsonProcessingException e) {
            // jsonへの変換に失敗した場合
            LOGGER.error("jsonへの変換に失敗しました。受注CSVの生成は実行しておりません。", e);
            throwMessage(MESSAGE_VALID_DOWNLOAD_ORDERCSV);
        }

        String administratorId = this.getCommonInfo().getCommonInfoAdministrator().getAdministratorId();
        String shopSeq = this.getCommonInfo().getCommonInfoBase().getShopSeq().toString();
        // リクエストデータをDBに登録
        Integer requestCode = batchJobInsertLogic.execute(new ArrayList<String>(Arrays.asList(json)));

        // 非同期バッチをキックする
        Job job = ApplicationContextUtility.getApplicationContext()
                                           .getBean(BatchName.BATCH_ORDER_CSV_ASYNCHRONOUS, Job.class);

        try {
            // 非同期の為、結果はバッチ側で処理
            jobLauncher.run(
                            job, new JobParametersBuilder().addString("administratorId", administratorId)
                                                           .addString("shopSeq", shopSeq)
                                                           .addString("jobParam", requestCode.toString())
                                                           .toJobParameters());
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            // jobLauncherの起動に失敗した場合
            LOGGER.error("jobLauncherの起動に失敗しました。受注CSVの生成は実行しておりません。", e);
            throwMessage(MESSAGE_VALID_DOWNLOAD_ORDERCSV);
        }

        orderModel.setSelectOrderCSVFlag(true);
        return "order/index";
    }

    /**
     * 受注CSVダウンロード（一覧上部ボタン）<br/>
     *
     * @param orderModel
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ORDER:8')")
    @PostMapping(value = "/", params = "doSelectionOutput1")
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    @HEHandler(exception = AppLevelListException.class, returnView = "order/index")
    public String doSelectionOutput1(@Validated(SelectionOutput1Group.class) OrderModel orderModel,
                                     BindingResult error) {
        // 実行前処理
        preDoAction(orderModel);

        if (error.hasErrors()) {
            return "order/index";
        }
        // CSV選択ダウンロード
        downloadCheckedCsv(orderModel);

        orderModel.setSelectOrderCSVFlag(true);
        return "order/index";
    }

    /**
     * 受注CSVダウンロード（一覧下部ボタン）<br/>
     *
     * @param orderModel
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ORDER:8')")
    @PostMapping(value = "/", params = "doSelectionOutput2")
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    @HEHandler(exception = AppLevelListException.class, returnView = "order/index")
    public String doSelectionOutput2(@Validated(SelectionOutput2Group.class) OrderModel orderModel,
                                     BindingResult error) {
        // 実行前処理
        preDoAction(orderModel);

        if (error.hasErrors()) {
            return "order/index";
        }
        // CSV選択ダウンロード
        downloadCheckedCsv(orderModel);

        orderModel.setSelectOrderCSVFlag(true);
        return "order/index";
    }

    /**
     * 受注CSV非同期処理結果のダウンロード<br/>
     *
     * @param file 対象ファイル名
     * @return ファイル出力先パス
     */
    @PreAuthorize("hasAnyAuthority('ORDER:8')")
    @PostMapping(value = "/", params = "doDownload")
    @HEHandler(exception = AppLevelListException.class, returnView = "redirect:/error")
    public ResponseEntity<byte[]> doDownload(@RequestParam(required = false) String file) {

        Path path = Paths.get(PropertiesUtil.getSystemPropertiesValue(ORDERCSV_FILE_PATH) + file);
        // ファイルの存在確認
        if (!Files.exists(path)) {
            throwMessage(MESSAGE_VALID_DOWNLOAD_ORDERCSV_NOT_EXIST);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.add("Content-Disposition", "attachment;filename=\"" + file + "\"");

        try {
            return new ResponseEntity<>(Files.readAllBytes(path), headers, HttpStatus.OK);
        } catch (IOException e) {
            LOGGER.error("例外処理が発生しました", e);
            throwMessage(MESSAGE_VALID_DOWNLOAD_ORDERCSV_NOT_EXIST);
            return null;
        }
    }

    /**
     * 受注番号別一覧・選択出力
     *
     * @param orderModel
     * @return
     */
    protected void downloadCheckedCsv(OrderModel orderModel) {

        // 検索結果チェック
        resultListCheck(orderModel);

        List<Integer> orderSeqList = orderHelper.convertToListForSearch(orderModel);
        Integer shopSeq = this.getCommonInfo().getCommonInfoBase().getShopSeq();
        String administratorId = this.getCommonInfo().getCommonInfoAdministrator().getAdministratorId();

        // チェック選択なし
        if (orderSeqList.isEmpty()) {
            throwMessage("AOX000109");
        }

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = null;
        String shopSeqPrm = shopSeq.toString();
        try {
            json = ow.writeValueAsString(orderSeqList);
        } catch (JsonProcessingException e) {
            // jsonへの変換に失敗した場合
            LOGGER.error("jsonへの変換に失敗しました。受注CSVの生成は実行しておりません。", e);
            throwMessage(MESSAGE_VALID_DOWNLOAD_ORDERCSV);
        }

        // リクエストデータをDBに登録
        List<String> tmp = new ArrayList<String>();
        tmp.add(json);
        tmp.add(shopSeqPrm);
        Integer requestCode = batchJobInsertLogic.execute(tmp);

        // 非同期バッチをキックする
        Job job = ApplicationContextUtility.getApplicationContext()
                                           .getBean(BatchName.BATCH_ORDER_CSV_ASYNCHRONOUS, Job.class);

        try {
            // 非同期の為、結果はバッチ側で処理
            jobLauncher.run(
                            job, new JobParametersBuilder().addString("administratorId", administratorId)
                                                           .addString("shopSeq", shopSeq.toString())
                                                           .addString("jobParam", requestCode.toString())
                                                           .toJobParameters());
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            // jobLauncherの起動に失敗した場合
            LOGGER.error("jobLauncherの起動に失敗しました。受注CSVの生成は実行しておりません。", e);
            throwMessage(MESSAGE_VALID_DOWNLOAD_ORDERCSV);
        }
    }

    /**
     * 出荷登録CSV出力処理
     *
     * @param getShipmentRegistData 出力タイプ
     */
    protected void shipmentRegistOutput(String getShipmentRegistData, OrderModel orderModel) {

        // 検索結果チェック
        resultListCheck(orderModel);

        // プルダウンチェック
        if (StringUtil.isEmpty(getShipmentRegistData)) {
            this.throwMessage("AOX000109");
        }

        // 選択された行をCSVDtoに変換
        List<String> orderSeqList = orderHelper.convertToListForSearchShipment(orderModel);
        if (orderSeqList.isEmpty()) {
            this.throwMessage("AOX000109");
        }
        // CSV出力
        // 出荷登録CSV出力は不要機能のため削除
        //        shipmentRegistCsvDownLoadService.execute(orderSeqList);
    }

    /**
     * 受注検索受注一覧取得処理実行
     *
     * @param isDisplayChange 表示変更フラグ
     */
    private void searchOrder(boolean isDisplayChange, OrderModel orderModel, Model model) {

        toOrderSearchListConditionDto(isDisplayChange, orderModel, true);

        // 検索条件作成
        OrderSearchConditionDto orderSearchConditionDto = orderModel.getOrderSearchConditionDto();

        // ページング検索セットアップ
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
        pageInfoHelper.setupPageInfo(orderSearchConditionDto, orderModel.getPageNumber(), orderModel.getLimit(),
                                     orderModel.getOrderField(), orderModel.isOrderAsc()
                                    );

        // 受注検索受注一覧取得サービスを実行
        List<OrderSearchOrderResultDto> resultList = orderSearchOrderListGetService.execute(orderSearchConditionDto);

        // 検索結果をindexPageに反映
        orderHelper.toPageForSearch(resultList, orderModel, orderSearchConditionDto);

        // 検索アクションで種別を保存
        String searchActionType = null;
        if (orderModel.isShipmentRegister()) {
            searchActionType = OrderModel.SEARCH_ACTION_TYPE_SHIPMENT;

        } else {
            searchActionType = OrderModel.SEARCH_ACTION_TYPE_ORDER;
        }

        orderModel.getOrderSearchConditionDto().setSearchActionType(searchActionType);

        // ページャーセットアップ
        pageInfoHelper.setupViewPager(orderSearchConditionDto, orderModel);
    }

    /**
     * 受注検索商品一覧取得処理実行
     *
     * @param isDisplayChange 表示変更フラグ
     */
    protected void searchGoods(boolean isDisplayChange, OrderModel orderModel, Model model) {

        toOrderSearchListConditionDto(isDisplayChange, orderModel, false);

        // 検索条件作成
        OrderSearchConditionDto orderSearchConditionDto = orderModel.getOrderSearchConditionDto();

        // ページング検索セットアップ
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
        pageInfoHelper.setupPageInfo(orderSearchConditionDto, orderModel.getPageNumber(), orderModel.getLimit(),
                                     orderModel.getOrderField(), orderModel.isOrderAsc()
                                    );

        // 受注検索商品別一覧取得サービスを実行
        List<OrderSearchOrderGoodsResultDto> resultList =
                        orderSearchOrderGoodsListGetService.execute(orderSearchConditionDto);
        // 検索結果をindexPageに反映
        orderHelper.toPageForSearchGoods(resultList, orderModel, orderSearchConditionDto);

        // カスタムアラート用検索アクション種別保存
        orderModel.getOrderSearchConditionDto().setSearchActionType(orderModel.SEARCH_ACTION_TYPE_GOODS);

        // ページャーセットアップ
        pageInfoHelper.setupViewPager(orderSearchConditionDto, orderModel);
    }

    /**
     * 受注検索条件設定
     *
     * @param isDisplayChange 表示変更フラグ
     * @param orderModel      受注検索モデル
     */
    protected void toOrderSearchListConditionDto(boolean isDisplayChange,
                                                 OrderModel orderModel,
                                                 boolean isSearchOrder) {

        if (!isDisplayChange) {
            orderModel.setPageNumber(DEFAULT_ORDERSEARCH_PNUM);
            // ソート条件設定
            setDefaultSort(orderModel, isSearchOrder);
            // 検索条件作成
            orderHelper.toOrderSearchListConditionDtoForPage(orderModel);
        } else {
            // 検索対象のページ情報を設定
            orderHelper.toOrderSearchListConditionDtoDisplayChange(orderModel);
        }

        if (orderModel.getMsgCodeList() != null && !orderModel.getMsgCodeList().isEmpty()) {
            List<String> msgCodeList = orderModel.getMsgCodeList();
            for (String messageCode : msgCodeList) {
                // メッセージ引数マップに一致するメッセージコードがある場合は引数として設定する
                if (orderModel.getMsgArgMap().containsKey(messageCode)) {
                    throwMessage(messageCode, orderModel.getMsgArgMap().get(messageCode));
                } else {
                    throwMessage(messageCode);
                }
            }
        }
    }

    /**
     * デフォルトソート条件を設定<br/>
     */
    protected void setDefaultSort(OrderModel orderModel, boolean isSearchOrder) {
        if (!isSearchOrder) {
            orderModel.setOrderField(DEFAULT_GOODSSEARCH_ORDER_FIELD);
            orderModel.setOrderAsc(!DEFAULT_ORDERSEARCH_ORDER_ASC);
        } else {
            orderModel.setOrderField(DEFAULT_ORDERSEARCH_ORDER_FIELD);
            orderModel.setOrderAsc(DEFAULT_ORDERSEARCH_ORDER_ASC);
        }
    }

    /**
     * 受注検索結果リストが空でないことをチェックする<br/>
     * (ブラウザバック後の選択出力などでの不具合防止のため)<br/>
     */
    protected void resultListCheck(OrderModel orderModel) {
        if (!orderModel.isResult()) {
            return;
        }
        OrderModelItem item = orderModel.getResultItems().get(0);
        if (StringUtil.isEmpty(item.getOrderCode()) && StringUtil.isEmpty(item.getResultGoodsCode())) {
            orderModel.setResultItems(null);
            this.throwMessage("AOX000112");
        }
    }

    /**
     * プルダウンアイテム情報を取得
     *
     * @param orderModel 受注検索モデル
     */
    private void initComponentValue(OrderModel orderModel) {

        orderModel.setOrderSiteTypeArrayItems(EnumTypeUtil.getEnumMap(HTypeOrderSite.class));
        orderModel.setOrderTypeArrayItems(EnumTypeUtil.getEnumMap(HTypeOrderType.class));
        orderModel.setSettlememntItems(divisionMapGetService.getSettlementMapList());
        orderModel.setBillTypeItems(EnumTypeUtil.getEnumMap(HTypeBillType.class));
        orderModel.setBillStatusItems(EnumTypeUtil.getEnumMap(HTypeBillStatus.class));
        orderModel.setOrderStatusItems(EnumTypeUtil.getEnumMap(HTypeSelectMapOrderStatus.class));
        orderModel.setTimeTypeItems(EnumTypeUtil.getEnumMap(HTypeDate.class));
        orderModel.setOrderPersonItems(EnumTypeUtil.getEnumMap(HTypePerson.class));
        orderModel.setDeliveryItems(divisionMapGetService.getDeliveryMapList());
        orderModel.setShipmentStatusItems(EnumTypeUtil.getEnumMap(HTypeShipmentStatus.class));
        orderModel.setPaymentStatusItems(EnumTypeUtil.getEnumMap(HTypePaymentStatus.class));

        Map<String, String> tmpOrderOutDataSelectItems = EnumTypeUtil.getEnumMap(HTypeSelectionOrderOutData.class);
        tmpOrderOutDataSelectItems.remove("0", "納品書");
        tmpOrderOutDataSelectItems.remove("1", "受注明細");
        orderModel.setOrderOutData1Items(tmpOrderOutDataSelectItems);
        orderModel.setOrderOutData2Items(tmpOrderOutDataSelectItems);

        orderModel.setShipmentRegistData1Items(EnumTypeUtil.getEnumMap(HTypeSelectionShipmentRegistOutData.class));
        orderModel.setShipmentRegistData2Items(EnumTypeUtil.getEnumMap(HTypeSelectionShipmentRegistOutData.class));

        Map<String, String> tmpOrderOutDataItems = EnumTypeUtil.getEnumMap(HTypeOrderOutData.class);
        tmpOrderOutDataItems.remove("0", "納品書");
        tmpOrderOutDataItems.remove("1", "受注明細");
        tmpOrderOutDataItems.remove("2", "受注商品別明細");
        tmpOrderOutDataItems.remove("4", "出荷登録CSV");
        tmpOrderOutDataItems.remove("5", "入金登録CSV");
        tmpOrderOutDataItems.remove("7", "受注商品CSV");
        orderModel.setOrderOutDataItems(tmpOrderOutDataItems);
    }
}
