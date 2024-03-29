/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.delivery.dia;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.delivery.validation.group.OnceZipCodeAddGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.delivery.validation.group.ReDisplayGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.delivery.validation.group.ZipCodeSearchGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.PageInfoHelper;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.ZipCodeAddressDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryImpossibleAreaConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryImpossibleAreaResultDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryImpossibleAreaEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.zipcode.ZipCodeAddressGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.DeliveryImpossibleAreaListDeleteService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.DeliveryImpossibleAreaRegistService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.DeliveryImpossibleAreaSearchService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.DeliveryMethodGetService;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

/**
 * 配送方法設定：配送不可能エリア設定検索画面用コントロール
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("/delivery/dia")
@Controller
@SessionAttributes(value = "deliveryDiaModel")
@PreAuthorize("hasAnyAuthority('SETTING:8')")
public class DeliveryDiaController extends AbstractController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryDiaController.class);

    /**
     * デフォルトページ番号
     */
    private static final String DEFAULT_PNUM = "1";

    /**
     * デフォルト最大表示件数
     */
    private static final int DEFAULT_LIMIT = 100;

    /**
     * メッセージコード：不正操作
     */
    protected static final String MSGCD_ILLEGAL_OPERATION = "AYD000601";

    /**
     * メッセージコード：チェックボックスがチェックされていない
     */
    protected static final String MSGCD_NO_CHECK = "AYD000602";

    /**
     * 配送方法SEQ
     */
    public static final String FLASH_DMCD = "dmcd";

    /**
     * 配送不可能エリア設定検索画面用Dxo
     */
    private DeliveryDiaHelper deliveryDiaHelper;

    /**
     * 配送不可能エリア検索Service
     */
    private DeliveryImpossibleAreaSearchService deliveryImpossibleAreaSearchService;

    /**
     * 配送方法取得サービス
     */
    private DeliveryMethodGetService deliveryMethodGetService;

    /**
     * 配送不可能エリア削除Service
     */
    private DeliveryImpossibleAreaListDeleteService deliveryImpossibleAreaListDeleteService;

    /**
     * 配送特別料金エリア登録Service
     */
    private DeliveryImpossibleAreaRegistService deliveryImpossibleAreaRegistService;

    /**
     * 郵便番号住所取得ロジック
     */
    private ZipCodeAddressGetLogic addressGetLogic;

    /**
     * コンストラクタ
     *
     * @param deliveryDiaHelper
     * @param deliveryImpossibleAreaSearchService
     * @param deliveryMethodGetService
     * @param deliveryImpossibleAreaListDeleteService
     * @param deliveryImpossibleAreaRegistService
     * @param addressGetLogic
     */
    @Autowired
    public DeliveryDiaController(DeliveryDiaHelper deliveryDiaHelper,
                                 DeliveryImpossibleAreaSearchService deliveryImpossibleAreaSearchService,
                                 DeliveryMethodGetService deliveryMethodGetService,
                                 DeliveryImpossibleAreaListDeleteService deliveryImpossibleAreaListDeleteService,
                                 DeliveryImpossibleAreaRegistService deliveryImpossibleAreaRegistService,
                                 ZipCodeAddressGetLogic addressGetLogic) {
        this.deliveryDiaHelper = deliveryDiaHelper;
        this.deliveryImpossibleAreaSearchService = deliveryImpossibleAreaSearchService;
        this.deliveryMethodGetService = deliveryMethodGetService;
        this.deliveryImpossibleAreaListDeleteService = deliveryImpossibleAreaListDeleteService;
        this.deliveryImpossibleAreaRegistService = deliveryImpossibleAreaRegistService;
        this.addressGetLogic = addressGetLogic;
    }

    /**
     * 初期処理を実行します
     *
     * @param dmcd
     * @param md
     * @param deliveryDiaModel
     * @param redirectAttributes
     * @param model
     * @return
     */
    @GetMapping(value = "/")
    @HEHandler(exception = AppLevelListException.class, returnView = "delivery/dia/index")
    public String doLoadIndex(@RequestParam(required = false) Optional<Integer> dmcd,
                              @RequestParam(required = false) Optional<String> prefectureName,
                              DeliveryDiaModel deliveryDiaModel,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        if (deliveryDiaModel.isReloadFlag()) {
            deliveryDiaModel.setReloadFlag(false);
            return "delivery/dia/index";
        }

        // モデルのクリア処理
        if (!prefectureName.isPresent()) {
            clearModel(DeliveryDiaModel.class, deliveryDiaModel, model);
        } else {
            deliveryDiaModel.setPrefectureName(prefectureName.get());
        }

        // 都道府県種別設定
        deliveryDiaModel.setPrefectureNameItems(EnumTypeUtil.getEnumMap(HTypePrefectureType.class));

        // 不正操作チェック
        if ((deliveryDiaModel.getDmcd() == null) && (!dmcd.isPresent() && (!model.containsAttribute(FLASH_DMCD)))) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/delivery/";
        }

        // 配送方法SEQ取得
        if (dmcd.isPresent()) {
            deliveryDiaModel.setDmcd(dmcd.get());
        } else if (model.containsAttribute(FLASH_DMCD)) {
            deliveryDiaModel.setDmcd((Integer) model.getAttribute(FLASH_DMCD));
        }

        DeliveryMethodEntity resultEntity = deliveryMethodGetService.execute(deliveryDiaModel.getDmcd());

        // 不正操作チェック。配送マスタは物理削除されないので、結果がないのはURLパラメータをいじられた以外ありえない。
        if (resultEntity == null) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/delivery/";
        } else {
            deliveryDiaHelper.convertToRegistPageForResult(deliveryDiaModel, resultEntity);
        }

        // pageNumber,limitを初期化
        deliveryDiaModel.setPageNumber(DEFAULT_PNUM);
        deliveryDiaModel.setLimit(DEFAULT_LIMIT);

        // 検索処理
        try {
            search(deliveryDiaModel, model);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.error("例外処理が発生しました", e);
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/delivery/";
        }

        return "delivery/dia/index";
    }

    /**
     * 選択された都道府県で再検索を行います
     *
     * @param deliveryDiaModel
     * @param model
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private String reSearch(DeliveryDiaModel deliveryDiaModel, Model model)
                    throws IllegalAccessException, InvocationTargetException {
        search(deliveryDiaModel, model);
        return "delivery/dia/index";
    }

    /**
     * 一覧を再表示します
     *
     * @param deliveryDiaModel
     * @param redirectAttributes
     * @param model
     * @return
     */
    @PostMapping(value = "/", params = "doReDisplay")
    @HEHandler(exception = AppLevelListException.class, returnView = "delivery/dia/index")
    public String doReDisplay(@Validated(ReDisplayGroup.class) DeliveryDiaModel deliveryDiaModel,
                              BindingResult error,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        if (error.hasErrors()) {
            return "delivery/dia/index";
        }

        // 不正操作チェック
        if (deliveryDiaModel.getDmcd() == null) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/delivery/";
        }

        // pageNumber初期化
        deliveryDiaModel.setPageNumber(DEFAULT_PNUM);

        try {
            return reSearch(deliveryDiaModel, model);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.error("例外処理が発生しました", e);
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/delivery/";
        }
    }

    /**
     * 画面より選択された情報を削除します
     *
     * @param deliveryDiaModel
     * @param model
     * @return
     */
    @PostMapping(value = "/", params = "doOnceDelete")
    @HEHandler(exception = AppLevelListException.class, returnView = "delivery/dia/index")
    public String doOnceDelete(DeliveryDiaModel deliveryDiaModel, RedirectAttributes redirectAttributes, Model model) {

        // 不正操作チェック
        if (deliveryDiaModel.getDmcd() == null) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/delivery/";
        }

        List<DeliveryImpossibleAreaEntity> deleteList =
                        deliveryDiaHelper.convertToDeliveryImpossibleAreaEntityForDelete(deliveryDiaModel);

        if ((deleteList != null) && !deleteList.isEmpty()) {
            deliveryImpossibleAreaListDeleteService.execute(deleteList);
        } else {
            throwMessage(MSGCD_NO_CHECK);
        }

        try {
            reSearch(deliveryDiaModel, model);
            deliveryDiaModel.setReloadFlag(true);
            String dmcdDia = deliveryDiaModel.getDmcd() != null ? String.valueOf(deliveryDiaModel.getDmcd()) : "";
            String prefectureNameDia = StringUtils.isNotEmpty(deliveryDiaModel.getPrefectureName()) ?
                            deliveryDiaModel.getPrefectureName() :
                            "";
            return "redirect:/delivery/dia/?dmcd=" + dmcdDia + "&prefectureName=" + prefectureNameDia;
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.error("例外処理が発生しました", e);
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/delivery/";
        }

    }

    /**
     * 検索処理
     *
     * @param deliveryDiaModel
     * @param model
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    protected void search(DeliveryDiaModel deliveryDiaModel, Model model)
                    throws IllegalAccessException, InvocationTargetException {
        DeliveryImpossibleAreaConditionDto conditionDto =
                        ApplicationContextUtility.getBean(DeliveryImpossibleAreaConditionDto.class);
        deliveryDiaHelper.convertToDeliveryImpossibleAreaConditionDtoForSearch(conditionDto, deliveryDiaModel);

        // ページング検索セットアップ
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
        pageInfoHelper.setupPageInfo(conditionDto, deliveryDiaModel.getPageNumber(), deliveryDiaModel.getLimit());

        List<DeliveryImpossibleAreaResultDto> resultList = deliveryImpossibleAreaSearchService.execute(conditionDto);

        deliveryDiaHelper.convertToIndexPageItemForResult(resultList, deliveryDiaModel, conditionDto);

        // ページャーセットアップ
        pageInfoHelper.setupViewPager(conditionDto, deliveryDiaModel);

        deliveryDiaModel.setTotalCount(conditionDto.getTotalCount());
    }

    /**
     * ページング処理を行います
     *
     * @param deliveryDiaModel
     * @param redirectAttributes
     * @param model
     * @return
     */
    @PostMapping(value = "/", params = "doDisplayChange")
    @HEHandler(exception = AppLevelListException.class, returnView = "delivery/dia/index")
    public String doDisplayChange(DeliveryDiaModel deliveryDiaModel,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {
        try {
            return reSearch(deliveryDiaModel, model);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.error("例外処理が発生しました", e);
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/delivery/";
        }
    }

    /**
     * 登録画面：初期処理
     *
     * @param deliveryDiaModel
     * @param redirectAttributes
     * @param model
     * @return
     */
    @GetMapping(value = "/regist/")
    @HEHandler(exception = AppLevelListException.class, returnView = "delivery/dia/index")
    public String doLoadRegistIndex(DeliveryDiaModel deliveryDiaModel,
                                    RedirectAttributes redirectAttributes,
                                    Model model) {

        // 不正操作チェック
        if (deliveryDiaModel.getDmcd() == null) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/delivery/";
        }

        // 初期表示処理
        DeliveryMethodEntity resultEnity = deliveryMethodGetService.execute(deliveryDiaModel.getDmcd());

        if (resultEnity != null) {
            deliveryDiaHelper.convertToRegistPageForResult(deliveryDiaModel, resultEnity);
        }

        // 初期化
        deliveryDiaModel.setZipCode(null);
        deliveryDiaModel.setAddress(null);

        return "delivery/dia/regist";
    }

    /**
     * 登録画面：配送特別料金を追加登録します
     *
     * @param deliveryDiaModel
     * @param error
     * @param redirectAttributes
     * @param sessionStatus
     * @param model
     * @return
     */
    @PostMapping(value = "/regist/", params = "doOnceZipCodeAdd")
    @HEHandler(exception = AppLevelListException.class, returnView = "delivery/dia/regist")
    public String doOnceZipCodeAdd(@Validated(OnceZipCodeAddGroup.class) DeliveryDiaModel deliveryDiaModel,
                                   BindingResult error,
                                   RedirectAttributes redirectAttributes,
                                   SessionStatus sessionStatus,
                                   Model model) {

        if (error.hasErrors()) {
            return "delivery/dia/regist";
        }

        // 不正操作チェック
        if (deliveryDiaModel.getDmcd() == null) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/delivery/";
        }

        // 登録内容を設定
        DeliveryImpossibleAreaEntity entity = ApplicationContextUtility.getBean(DeliveryImpossibleAreaEntity.class);
        deliveryDiaHelper.convertToDeliveryImpossibleAreaEntityForRegist(entity, deliveryDiaModel);

        // 登録処理を実行
        deliveryImpossibleAreaRegistService.execute(entity);

        // Modelをセッションより破棄
        sessionStatus.setComplete();

        // 再検索フラグをセット
        redirectAttributes.addFlashAttribute(FLASH_DMCD, deliveryDiaModel.getDmcd());

        String dmcdDia = deliveryDiaModel.getDmcd() != null ? String.valueOf(deliveryDiaModel.getDmcd()) : "";
        String prefectureNameDia = StringUtils.isNotEmpty(deliveryDiaModel.getPrefectureName()) ?
                        deliveryDiaModel.getPrefectureName() :
                        "";

        if (StringUtils.isNotEmpty(deliveryDiaModel.getPrefectureName())) {
            return "redirect:/delivery/dia/?dmcd=" + dmcdDia + "&prefectureName=" + prefectureNameDia;
        } else {
            return "redirect:/delivery/dia/?dmcd=" + dmcdDia;
        }
    }

    /**
     * 登録画面：入力された郵便番号で住所を検索します
     *
     * @param deliveryDiaModel
     * @param error
     * @param redirectAttributes
     * @param model
     * @return
     */
    @PostMapping(value = "/regist/", params = "doZipCodeSearch")
    @HEHandler(exception = AppLevelListException.class, returnView = "delivery/dia/regist")
    public String doZipCodeSearch(@Validated(ZipCodeSearchGroup.class) DeliveryDiaModel deliveryDiaModel,
                                  BindingResult error,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {
        if (error.hasErrors()) {
            return "delivery/dia/regist";
        }

        // 不正操作チェック
        if (deliveryDiaModel.getDmcd() == null) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/delivery/";
        }

        List<ZipCodeAddressDto> addressList = addressGetLogic.getAddressList(deliveryDiaModel.getZipCode());
        deliveryDiaHelper.convertToRegistPageForZipCodeResult(deliveryDiaModel, addressList);

        return "delivery/dia/regist";
    }

}
