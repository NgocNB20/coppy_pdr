/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.details;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCancelFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEmergencyFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUseConveni;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailSendDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.conveni.ConvenienceEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.memo.OrderMemoEntity;
import jp.co.hankyuhanshin.itec.hitmall.helper.mailtemplate.OrderTransformHelper;
import jp.co.hankyuhanshin.itec.hitmall.helper.mailtemplate.Transformer;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.SettlememtMismatchCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.ConvenienceLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderGoodsMixedTaxCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.ReceiveOrderUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.order.ReceiveOrderGetService;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelException;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.UIComponentUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 受注詳細アクション<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("/order/details")
@Controller
@SessionAttributes(value = "orderDetailsModel")
@PreAuthorize("hasAnyAuthority('ORDER:4')")
public class OrderDetailsController extends AbstractController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDetailsController.class);

    /**
     * 不正操作<br/>
     */
    public static final String MSGCD_REFERER_FAIL = "AOX000901";

    /**
     * 管理用メモ桁数オーバーエラー<br/>
     */
    public static final String MSGCD_MEMO_LENGTH_OVER_ERROR = "AOX000902";

    /**
     * 再オーソリ成功
     */
    public static final String MSGCD_REAUTHORY_SUCCESS = "AOX000903";

    /**
     * 再オーソリ失敗
     */
    public static final String MSGCD_REAUTHORY_FAIL = "AOX000904";

    /**
     * キャンセル受注更新モードメッセージ
     */
    public static final String MSGCD_UPDATE_CANCELED_ORDER = "AOX000905";

    /**
     * キャンセル受注更新成功メッセージ
     */
    public static final String MSGCD_UPDATE_CANCELED_ORDER_SUCCESS = "AOX000906";

    /**
     * 管理用メモ 桁数
     */
    public static final int LENGTH_MEMO = 2000;

    /**
     * 同一商品、税率混在メッセージ
     */
    public static final String MSGCD_MULTIPLE_TAX_RATE_IN_SINGLE_GOODS = "PKG-4081-001-A-";

    /**
     * 請求情報の不整合チェックLogic
     */
    private SettlememtMismatchCheckLogic settlememtMismatchCheckLogic;

    /**
     * 受注詳細ページ変換
     */
    private OrderDetailsHelper orderDetailsHelper;

    /**
     * 受注詳細情報取得サービス
     */
    private ReceiveOrderGetService receiveOrderGetService;

    /**
     * コンビニ名称リスト取得ロジック
     */
    private ConvenienceLogic convenienceLogic;

    /**
     * 受注修正ロジック
     */
    private ReceiveOrderUpdateLogic receiveOrderUpdateLogic;

    /**
     * 同一商品、税率混在チェックLogic
     */
    private OrderGoodsMixedTaxCheckLogic orderGoodsMixedTaxCheckLogic;

    /**
     * DateUtility instance
     */
    private DateUtility dateUtility;

    /**
     * コンストラクタ
     *
     * @param settlememtMismatchCheckLogic
     * @param orderDetailsHelper
     * @param receiveOrderGetService
     * @param convenienceLogic
     * @param receiveOrderUpdateLogic
     * @param orderGoodsMixedTaxCheckLogic
     * @param dateUtility
     */
    @Autowired
    public OrderDetailsController(SettlememtMismatchCheckLogic settlememtMismatchCheckLogic,
                                  OrderDetailsHelper orderDetailsHelper,
                                  ReceiveOrderGetService receiveOrderGetService,
                                  ConvenienceLogic convenienceLogic,
                                  ReceiveOrderUpdateLogic receiveOrderUpdateLogic,
                                  OrderGoodsMixedTaxCheckLogic orderGoodsMixedTaxCheckLogic,
                                  DateUtility dateUtility) {
        this.settlememtMismatchCheckLogic = settlememtMismatchCheckLogic;
        this.orderDetailsHelper = orderDetailsHelper;
        this.receiveOrderGetService = receiveOrderGetService;
        this.convenienceLogic = convenienceLogic;
        this.receiveOrderUpdateLogic = receiveOrderUpdateLogic;
        this.orderGoodsMixedTaxCheckLogic = orderGoodsMixedTaxCheckLogic;
        this.dateUtility = dateUtility;
    }

    /**
     * 入力画面：初期処理
     *
     * @param orderCode
     * @param from
     * @param md
     * @param orderDetailsModel
     * @param redirectAttributes
     * @param model
     * @return
     */
    @GetMapping(value = "")
    @HEHandler(exception = AppLevelListException.class, returnView = "order/details/details")
    protected String doLoadIndex(@RequestParam(required = false) Optional<String> orderCode,
                                 @RequestParam(required = false) Optional<String> from,
                                 @RequestParam(required = false) Optional<String> md,
                                 OrderDetailsModel orderDetailsModel,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {

        String orderCodeData = null;
        clearModel(OrderDetailsModel.class, orderDetailsModel, new String[] {"orderCode", "from", "md"}, model);

        if (orderCode.isPresent()) {
            orderCodeData = orderCode.get();
        } else if (model.containsAttribute(DetailsUpdateModel.FLASH_ORDERCODE)) {
            orderCodeData = (String) model.getAttribute(DetailsUpdateModel.FLASH_ORDERCODE);
        }

        if (orderCodeData == null) {
            addMessage(MSGCD_REFERER_FAIL, redirectAttributes, model);
            return "redirect:/error";
        }

        if (from.isPresent()) {
            orderDetailsModel.setFrom(from.get());
        }

        // 受注詳細情報取得サービス実行
        ReceiveOrderDto receiveOrderDto = null;
        try {
            receiveOrderDto = receiveOrderGetService.execute(orderCodeData);
        } catch (AppLevelListException e) {
            LOGGER.error("例外処理が発生しました", e);
            List<AppLevelException> eList = e.getErrorList();
            for (AppLevelException ape : eList) {
                addMessage(ape.getMessageCode(), ape.getArgs(), redirectAttributes, model);
            }
            return "redirect:/error";
        }

        // 同一商品内に複数の税率が混在するかチェック
        List<CheckMessageDto> checkMessageDtoList =
                        orderGoodsMixedTaxCheckLogic.checkOrderGoodsMixedTax(receiveOrderDto,
                                                                             MSGCD_MULTIPLE_TAX_RATE_IN_SINGLE_GOODS
                                                                            );
        // 複数の税率が混在する場合、警告を表示
        for (CheckMessageDto errorList : checkMessageDtoList) {
            addInfoMessage(errorList.getMessageId(), errorList.getArgs(), redirectAttributes, model);
        }

        // セッションに保存
        orderDetailsModel.setReceiveOrderDto(receiveOrderDto);

        // コンビニ名称リストをセット
        createConvenienceSelect(orderDetailsModel);

        // 受注詳細情報ページ反映
        orderDetailsHelper.toPage(orderDetailsModel, receiveOrderDto);

        // キャンセル受注の場合、修正項目を設定
        setCancelOrderEditValue(orderDetailsModel, redirectAttributes, model);

        if (orderDetailsModel.getFrom() == null) {
            orderDetailsModel.setFrom("order");
            // fromが「order」でない場合、検索条件を破棄する為、mdに空文字をセット
        } else if (!orderDetailsModel.getFrom().equals("order")) {
            orderDetailsModel.setMd("");
        }

        return "order/details/details";
    }

    /**
     * 画面が描画される直前に呼び出される
     *
     * @param orderCode
     * @param from
     * @param md
     * @param orderDetailsModel
     * @param redirectAttributes
     * @param model
     * @return
     */
    public String prerender(Optional<String> orderCode,
                            Optional<String> from,
                            Optional<String> md,
                            OrderDetailsModel orderDetailsModel,
                            RedirectAttributes redirectAttributes,
                            Model model) {

        // メールを送信している場合はdoLoad()を呼び出す
        if (orderDetailsModel.isMailSentFlag()) {
            doLoadIndex(orderCode, from, md, orderDetailsModel, redirectAttributes, model);
        }
        return "";
    }

    /**
     * キャンセル受注の修正可能項目に値を設定する
     *
     * @param orderDetailsModel
     * @param redirectAttributes
     * @param model
     */
    protected void setCancelOrderEditValue(OrderDetailsModel orderDetailsModel,
                                           RedirectAttributes redirectAttributes,
                                           Model model) {
        ReceiveOrderDto receiveOrderDto = orderDetailsModel.getReceiveOrderDto();
        // キャンセル受注の更新かどうか("cou"は CanceledOrderUpdateの略)
        if (HTypeCancelFlag.ON == receiveOrderDto.getOrderSummaryEntity().getCancelFlag() && orderDetailsModel.getMd()
                                                                                                              .equals("cou")) {
            orderDetailsModel.setCanceledOrderUpdate(true);
            OrderMemoEntity orderMemoEntity = receiveOrderDto.getOrderMemoEntity();
            if (orderMemoEntity != null) {
                orderDetailsModel.setEditMemo(orderMemoEntity.getMemo());
            }
            addInfoMessage(MSGCD_UPDATE_CANCELED_ORDER, null, redirectAttributes, model);
        } else {
            orderDetailsModel.setCanceledOrderUpdate(false);
        }
    }

    /**
     * メールテンプレート選択画面に遷移
     *
     * @param orderDetailsModel
     * @param redirectAttributes
     * @param model
     * @return
     */
    @PostMapping(value = "/", params = "doMailTemplate")
    public String doMailTemplate(@Validated OrderDetailsModel orderDetailsModel,
                                 BindingResult error,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {

        // エラーチェック
        if (error.hasErrors()) {
            return "order/details/details";
        }

        // セッションの受注情報取得
        ReceiveOrderDto receiveOrderDto = orderDetailsModel.getReceiveOrderDto();

        // 受注情報がない場合 エラーページ
        if (receiveOrderDto == null || receiveOrderDto.getOrderPersonEntity() == null
            || receiveOrderDto.getOrderPersonEntity().getOrderMail() == null) {
            addMessage(MSGCD_REFERER_FAIL, redirectAttributes, model);
            return "redirect:/error";
        }

        // メール情報を作成 Dxo
        MailSendDto mailSendDto = ApplicationContextUtility.getBean(MailSendDto.class);
        mailSendDto.setApplication(MailSendDto.ORDER);
        mailSendDto.setDisplayName("受注詳細");
        mailSendDto.setMailDtoList(new ArrayList<>());
        mailSendDto.setAvailableTemplateTypeList(new ArrayList<>());
        mailSendDto.setOrderSeq(receiveOrderDto.getOrderIndexEntity().getOrderSeq());

        // 定期注文：テンプレートタイプを設定 定期注文確認（初回）/定期注文確認/出荷/コンビニ督促/コンビニ期限切れ/受注汎用/汎用
        // 通常注文：テンプレートタイプを設定 注文確認/出荷/コンビニ督促/コンビニ期限切れ/受注汎用/汎用
        List<HTypeMailTemplateType> availableTemplateTypeList = new ArrayList<>();
        if (HTypeOrderType.PERIODIC == receiveOrderDto.getOrderSummaryEntity().getOrderType()) {
            availableTemplateTypeList.add(HTypeMailTemplateType.PERIODIC_ORDER_CONFIRMATION_FIRST);
            availableTemplateTypeList.add(HTypeMailTemplateType.PERIODIC_ORDER_CONFIRMATION);
        } else {
            availableTemplateTypeList.add(HTypeMailTemplateType.ORDER_CONFIRMATION);
        }
        availableTemplateTypeList.add(HTypeMailTemplateType.SHIPMENT_NOTIFICATION);
        availableTemplateTypeList.add(HTypeMailTemplateType.SETTLEMENT_REMINDER);
        availableTemplateTypeList.add(HTypeMailTemplateType.SETTLEMENT_EXPIRATION_NOTIFICATION);
        availableTemplateTypeList.add(HTypeMailTemplateType.GENERAL_ORDER);
        //不使用 ※エラーになるので残しておく
        // 完了報告の場合
        //        availableTemplateTypeList.add(HTypeMailTemplateType.GENERAL);

        mailSendDto.setAvailableTemplateTypeList(availableTemplateTypeList);

        // 受注情報から作成
        MailDto mailDto = ApplicationContextUtility.getBean(MailDto.class);
        mailDto.setToList(Collections.singletonList(receiveOrderDto.getOrderPersonEntity().getOrderMail()));

        // メール用値マップの作成
        Transformer transformer = ApplicationContextUtility.getBean(OrderTransformHelper.class);
        Map<String, Object> mailContentsMap = new HashMap<>();
        mailContentsMap.put("mailContentsMap", transformer.toValueMap(receiveOrderDto));

        mailDto.setMailContentsMap(mailContentsMap);
        mailSendDto.getMailDtoList().add(mailDto);

        // 画面情報からMailSendDtoに変換してページに設定。

        redirectAttributes.addFlashAttribute("mailSendDto", mailSendDto);

        return "redirect:/mailtemplate/send/select";
    }

    /**
     * コンビニプルダウン作成
     */
    protected void createConvenienceSelect(OrderDetailsModel orderDetailsModel) {
        OrderUtility orderUtility = ApplicationContextUtility.getBean(OrderUtility.class);
        orderDetailsModel.setConvenienceAllList(convenienceLogic.getConvenienceList());
        Map<String, String> convenienceMap = new LinkedHashMap<>();
        for (ConvenienceEntity convenience : orderDetailsModel.getConvenienceAllList()) {
            if (HTypeUseConveni.ON == convenience.getUseFlag()) {
                convenienceMap.put(convenience.getConveniCode().toString(), orderUtility.getConveniName(convenience));
            }
        }
        orderDetailsModel.setConvenienceItems(UIComponentUtil.getItemList(convenienceMap));
    }

    /**
     * 再オーソリボタン押下時処理<br/>
     *
     * <pre>
     * ①再オーソリ文言（yyyy/mm/dd　再オーソリを実行）を管理用メモに設定する
     * ②支払方法や、利用金額など全く変更がない状態で受注修正を行う
     *  注文チェック、料金計算は不要⇒注文時点の商品情報/金額で再オーソリをかける必要がある為。
     * </pre>
     *
     * @param orderDetailsModel
     * @param redirectAttributes
     * @param model
     * @return 受注詳細画面
     */
    @PreAuthorize("hasAnyAuthority('ORDER:8')")
    @PostMapping(value = "/", params = "doReAuthory")
    @HEHandler(exception = AppLevelListException.class, returnView = "order/details/details")
    public String doReAuthory(@Validated OrderDetailsModel orderDetailsModel,
                              BindingResult error,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        // エラーチェック
        if (error.hasErrors()) {
            return "order/details/details";
        }

        // セッションの受注情報取得
        ReceiveOrderDto receiveOrderDto = orderDetailsModel.getReceiveOrderDto();

        // 受注情報がない場合 エラーページ
        if (receiveOrderDto == null || receiveOrderDto.getOrderPersonEntity() == null
            || receiveOrderDto.getOrderPersonEntity().getOrderMail() == null) {
            addMessage(MSGCD_REFERER_FAIL, redirectAttributes, model);
            return "redirect:/error";
        }
        // 管理用メモ登録文言を作成
        receiveOrderDto = setAuthoryMemo(receiveOrderDto);

        // 管理用メモの桁数チェック
        if (LENGTH_MEMO < receiveOrderDto.getOrderMemoEntity().getMemo().length()) {
            throwMessage(MSGCD_MEMO_LENGTH_OVER_ERROR);
        }

        // 請求情報の不整合チェック処理の初期処理
        settlememtMismatchCheckLogic.initReAuth(receiveOrderDto.getOrderSummaryEntity().getOrderCode());
        try {
            // 再オーソリフラグを立てる
            receiveOrderDto.setReAuthoryFlag(true);
            // 再オーソリ処理の実行
            receiveOrderUpdateLogic.execute(receiveOrderDto, null, HTypeSiteType.BACK, null, null, null,
                                            getCommonInfo().getCommonInfoAdministrator().getAdministratorLastName(),
                                            getCommonInfo().getCommonInfoAdministrator().getAdministratorFirstName()
                                           );

            // 請求情報の不整合チェック
            settlememtMismatchCheckLogic.execute();

            // 再オーソリ成功メッセージ表示
            addInfoMessage(MSGCD_REAUTHORY_SUCCESS, null, redirectAttributes, model);

            redirectAttributes.addFlashAttribute(DetailsUpdateModel.FLASH_ORDERCODE, orderDetailsModel.getOrderCode());
            return "order/details/details";

        } catch (Throwable th) {
            LOGGER.error("例外処理が発生しました", th);
            // 請求情報の不整合チェック
            settlememtMismatchCheckLogic.execute(th);

            if (th instanceof AppLevelListException) {
                List<AppLevelException> errorList = ((AppLevelListException) th).getErrorList();
                for (AppLevelException ae : errorList) {
                    addErrorMessage(ae.getMessageCode(), ae.getArgs(), ae);
                }
                throwMessage();
            }

            throw th;
        }
    }

    /**
     * 再オーソリ実施時に登録する管理メモを設定する<br/>
     *
     * @param receiveOrderDto 受注Dto
     * @return 受注Dto
     */
    protected ReceiveOrderDto setAuthoryMemo(ReceiveOrderDto receiveOrderDto) {

        // 受注メモDtoの取得
        OrderMemoEntity orderMemoEntity = receiveOrderDto.getOrderMemoEntity();

        // 受注メモDtoがnullの場合、新規作成
        if (orderMemoEntity == null) {
            orderMemoEntity = ApplicationContextUtility.getBean(OrderMemoEntity.class);
            orderMemoEntity.setOrderSeq(receiveOrderDto.getOrderSummaryEntity().getOrderSeq());
            receiveOrderDto.setOrderMemoEntity(orderMemoEntity);
        }

        // メモの設定
        orderMemoEntity.setMemo(getAutyhryMemo(orderMemoEntity.getMemo()));

        return receiveOrderDto;
    }

    /**
     * 再オーソリ実施時に登録する管理メモを作成する<br/>
     * 「yyyy/mm/dd 再オーソリを実行」
     *
     * @param memo 現在登録されているメモ
     * @return 再オーソリ実施時に登録する管理メモ
     */
    protected String getAutyhryMemo(String memo) {
        StringBuilder authoryMemo = new StringBuilder();
        // 現在のメモが設定されている場合は、改行を設定
        if (memo != null) {
            authoryMemo.append(memo);
            authoryMemo.append(System.getProperty("line.separator"));
        }

        // 実施日及び固定文言を設定
        authoryMemo.append(MessageFormat.format(PropertiesUtil.getSystemPropertiesValue("order.reauthory.memo"),
                                                new Object[] {dateUtility.formatYmdWithSlash(
                                                                dateUtility.getCurrentDate())}
                                               ));
        return authoryMemo.toString();
    }

    /**
     * キャンセル受注を更新する<br/>
     *
     * @param orderDetailsModel
     * @param redirectAttributes
     * @param model
     * @return 受注詳細画面（自分）
     */
    @PostMapping(value = "/", params = "doOnceUpdateCanceledOrder")
    @HEHandler(exception = AppLevelListException.class, returnView = "order/details/details")
    public String doOnceUpdateCanceledOrder(@Validated OrderDetailsModel orderDetailsModel,
                                            BindingResult error,
                                            RedirectAttributes redirectAttributes,
                                            Model model) {

        // エラーチェック
        if (error.hasErrors()) {
            return "order/details/details";
        }

        ReceiveOrderDto modified = CopyUtil.deepCopy(orderDetailsModel.getReceiveOrderDto());

        // 受注メモDtoの取得
        OrderMemoEntity orderMemoEntity = modified.getOrderMemoEntity();

        // 受注メモDtoがnullの場合、新規作成
        if (orderMemoEntity == null) {
            orderMemoEntity = ApplicationContextUtility.getBean(OrderMemoEntity.class);
            orderMemoEntity.setOrderSeq(modified.getOrderSummaryEntity().getOrderSeq());
            modified.setOrderMemoEntity(orderMemoEntity);
        }
        // メモの設定
        orderMemoEntity.setMemo(orderDetailsModel.getEditMemo());

        // 請求決済エラー解消チェックボックスにチェックありのとき、受注請求.異常フラグをOFFに設定
        if (orderDetailsModel.isCancelOfEmergency()) {
            modified.getOrderBillEntity().setEmergencyFlag(HTypeEmergencyFlag.OFF);
        }

        // 更新処理起動
        receiveOrderUpdateLogic.executeCancelOrderUpdate(modified, null, HTypeSiteType.BACK, null, null, null,
                                                         getCommonInfo().getCommonInfoAdministrator()
                                                                        .getAdministratorLastName(),
                                                         getCommonInfo().getCommonInfoAdministrator()
                                                                        .getAdministratorFirstName()
                                                        );

        // キャンセル受注更新モード解除
        orderDetailsModel.setMd("list");
        addInfoMessage(MSGCD_UPDATE_CANCELED_ORDER_SUCCESS, null, redirectAttributes, model);

        redirectAttributes.addFlashAttribute(DetailsUpdateModel.FLASH_ORDERCODE, orderDetailsModel.getOrderCode());
        return "redirect:/order/details/";
    }
}
