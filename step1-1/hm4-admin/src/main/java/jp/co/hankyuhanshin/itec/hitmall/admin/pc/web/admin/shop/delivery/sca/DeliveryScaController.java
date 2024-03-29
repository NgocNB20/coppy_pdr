/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.delivery.sca;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.delivery.validation.group.OnceZipCodeAddGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.delivery.validation.group.ReDisplayGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.delivery.validation.group.ZipCodeSearchGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.PageInfoHelper;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.ZipCodeAddressDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliverySpecialChargeAreaConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliverySpecialChargeAreaResultDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliverySpecialChargeAreaEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.zipcode.ZipCodeAddressGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.DeliveryMethodGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.DeliverySpecialChargeAreaListDeleteService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.DeliverySpecialChargeAreaRegistService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.DeliverySpecialChargeAreaSearchService;
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
 * 配送方法設定：特別料金エリア設定検索画面用コントロール
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("/delivery/sca")
@Controller
@SessionAttributes(value = "deliveryScaModel")
@PreAuthorize("hasAnyAuthority('SETTING:8')")
public class DeliveryScaController extends AbstractController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryScaController.class);

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
    protected static final String MSGCD_ILLEGAL_OPERATION = "AYD000401";

    /**
     * メッセージコード：チェックボックスがチェックされていない
     */
    protected static final String MSGCD_NO_CHECK = "AYD000402";

    /**
     * 配送方法SEQ
     */
    public static final String FLASH_DMCD = "dmcd";

    /**
     * 配送特別料金エリア設定検索画面用Dxo
     */
    private DeliveryScaHelper deliveryScaHelper;

    /**
     * 配送特別料金エリア検索Service
     */
    private DeliverySpecialChargeAreaSearchService deliverySpecialChargeAreaSearchService;

    /**
     * 配送方法取得サービス
     */
    private DeliveryMethodGetService deliveryMethodGetService;

    /**
     * 配送特別料金エリア削除Service
     */
    private DeliverySpecialChargeAreaListDeleteService deliverySpecialChargeAreaListDeleteService;

    /**
     * 配送特別料金エリア登録Service
     */
    private DeliverySpecialChargeAreaRegistService deliverySpecialChargeAreaRegistService;

    /**
     * 郵便番号住所取得ロジック
     */
    private ZipCodeAddressGetLogic addressGetLogic;

    /**
     * コンストラクタ
     *
     * @param deliveryScaHelper
     * @param deliverySpecialChargeAreaSearchService
     * @param deliveryMethodGetService
     * @param deliverySpecialChargeAreaListDeleteService
     * @param deliverySpecialChargeAreaRegistService
     * @param addressGetLogic
     */
    @Autowired
    public DeliveryScaController(DeliveryScaHelper deliveryScaHelper,
                                 DeliverySpecialChargeAreaSearchService deliverySpecialChargeAreaSearchService,
                                 DeliveryMethodGetService deliveryMethodGetService,
                                 DeliverySpecialChargeAreaListDeleteService deliverySpecialChargeAreaListDeleteService,
                                 DeliverySpecialChargeAreaRegistService deliverySpecialChargeAreaRegistService,
                                 ZipCodeAddressGetLogic addressGetLogic) {
        this.deliveryScaHelper = deliveryScaHelper;
        this.deliverySpecialChargeAreaSearchService = deliverySpecialChargeAreaSearchService;
        this.deliveryMethodGetService = deliveryMethodGetService;
        this.deliverySpecialChargeAreaListDeleteService = deliverySpecialChargeAreaListDeleteService;
        this.deliverySpecialChargeAreaRegistService = deliverySpecialChargeAreaRegistService;
        this.addressGetLogic = addressGetLogic;
    }

    /**
     * 初期処理を実行します
     *
     * @param dmcd
     * @param md
     * @param deliveryScaModel
     * @param redirectAttributes
     * @param model
     * @return
     */
    @GetMapping(value = "/")
    @HEHandler(exception = AppLevelListException.class, returnView = "redirect:/delivery/sca")
    public String doLoadIndex(@RequestParam(required = false) Optional<Integer> dmcd,
                              @RequestParam(required = false) Optional<String> prefectureName,
                              DeliveryScaModel deliveryScaModel,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        if (deliveryScaModel.isReloadFlag()) {
            deliveryScaModel.setReloadFlag(false);
            return "delivery/sca/index";
        }

        // モデルのクリア処理
        if (!prefectureName.isPresent()) {
            clearModel(DeliveryScaModel.class, deliveryScaModel, model);
        } else {
            deliveryScaModel.setPrefectureName(prefectureName.get());
        }

        // 都道府県種別設定
        deliveryScaModel.setPrefectureNameItems(EnumTypeUtil.getEnumMap(HTypePrefectureType.class));

        // 不正操作チェック
        if ((deliveryScaModel.getDmcd() == null) && (!dmcd.isPresent() && (!model.containsAttribute(FLASH_DMCD)))) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/delivery/";
        }

        // 配送方法SEQ取得
        if (dmcd.isPresent()) {
            deliveryScaModel.setDmcd(dmcd.get());
        } else if (model.containsAttribute(FLASH_DMCD)) {
            deliveryScaModel.setDmcd((Integer) model.getAttribute(FLASH_DMCD));
        }

        DeliveryMethodEntity resultEntity = deliveryMethodGetService.execute(deliveryScaModel.getDmcd());

        // 不正操作チェック。配送マスタは物理削除されないので、結果がないのはURLパラメータをいじられた以外ありえない。
        if (resultEntity == null) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/delivery/";
        } else {
            deliveryScaHelper.convertToRegistPageForResult(deliveryScaModel, resultEntity);
        }

        // pageNumber,limitを初期化
        deliveryScaModel.setPageNumber(DEFAULT_PNUM);
        deliveryScaModel.setLimit(DEFAULT_LIMIT);

        // 検索処理
        try {
            search(deliveryScaModel, model);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.error("例外処理が発生しました", e);
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/delivery/";
        }

        return "delivery/sca/index";
    }

    /**
     * 選択された都道府県で再検索を行います
     *
     * @param deliveryScaModel
     * @param redirectAttributes
     * @param model
     * @return
     */
    private String reSearch(DeliveryScaModel deliveryScaModel, RedirectAttributes redirectAttributes, Model model) {
        try {
            search(deliveryScaModel, model);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.error("例外処理が発生しました", e);
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/delivery/";
        }
        return "delivery/sca/index";
    }

    /**
     * 一覧を再表示します
     *
     * @param deliveryScaModel
     * @param redirectAttributes
     * @param model
     * @return
     */
    @PostMapping(value = "/", params = "doReDisplay")
    @HEHandler(exception = AppLevelListException.class, returnView = "delivery/sca/index")
    public String doReDisplay(@Validated(ReDisplayGroup.class) DeliveryScaModel deliveryScaModel,
                              BindingResult error,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        if (error.hasErrors()) {
            return "delivery/sca/index";
        }

        // 不正操作チェック
        if (deliveryScaModel.getDmcd() == null) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/delivery/";
        }
        // pageNumber初期化
        deliveryScaModel.setPageNumber(DEFAULT_PNUM);

        return reSearch(deliveryScaModel, redirectAttributes, model);
    }

    /**
     * 画面より選択された情報を削除します
     *
     * @param deliveryScaModel
     * @param model
     * @return
     */
    @PostMapping(value = "/", params = "doOnceDelete")
    @HEHandler(exception = AppLevelListException.class, returnView = "delivery/sca/index")
    public String doOnceDelete(DeliveryScaModel deliveryScaModel, RedirectAttributes redirectAttributes, Model model) {

        // 不正操作チェック
        if (deliveryScaModel.getDmcd() == null) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/delivery/";

        }

        List<DeliverySpecialChargeAreaEntity> deleteList =
                        deliveryScaHelper.convertToDeliverySpecialChargeAreaEntityForDelete(deliveryScaModel);

        if ((deleteList != null) && !deleteList.isEmpty()) {
            deliverySpecialChargeAreaListDeleteService.execute(deleteList);
        } else {
            throwMessage(MSGCD_NO_CHECK);
        }
        reSearch(deliveryScaModel, redirectAttributes, model);
        deliveryScaModel.setReloadFlag(true);
        String dmcdSca = deliveryScaModel.getDmcd() != null ? String.valueOf(deliveryScaModel.getDmcd()) : "";
        String prefectureNameSca = StringUtils.isNotEmpty(deliveryScaModel.getPrefectureName()) ?
                        deliveryScaModel.getPrefectureName() :
                        "";
        return "redirect:/delivery/sca/?dmcd=" + dmcdSca + "&prefectureName=" + prefectureNameSca;
    }

    /**
     * 検索処理
     *
     * @param deliveryScaModel
     * @param model
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    protected void search(DeliveryScaModel deliveryScaModel, Model model)
                    throws IllegalAccessException, InvocationTargetException {
        DeliverySpecialChargeAreaConditionDto conditionDto =
                        ApplicationContextUtility.getBean(DeliverySpecialChargeAreaConditionDto.class);
        deliveryScaHelper.convertToDeliverySpecialChargeAreaConditionDtoForSearch(conditionDto, deliveryScaModel);

        // ページング検索セットアップ
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
        pageInfoHelper.setupPageInfo(conditionDto, deliveryScaModel.getPageNumber(), deliveryScaModel.getLimit());

        List<DeliverySpecialChargeAreaResultDto> resultList =
                        deliverySpecialChargeAreaSearchService.execute(conditionDto);
        deliveryScaHelper.convertToIndexPageItemForResult(resultList, deliveryScaModel, conditionDto);

        // ページャーセットアップ
        pageInfoHelper.setupViewPager(conditionDto, deliveryScaModel);

        deliveryScaModel.setTotalCount(conditionDto.getTotalCount());
    }

    /**
     * ページング処理を行います
     *
     * @param deliveryScaModel
     * @param redirectAttributes
     * @param model
     * @return
     */
    @PostMapping(value = "/", params = "doDisplayChange")
    @HEHandler(exception = AppLevelListException.class, returnView = "delivery/sca/index")
    public String doDisplayChange(DeliveryScaModel deliveryScaModel,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {
        return reSearch(deliveryScaModel, redirectAttributes, model);
    }

    /**
     * 登録画面：初期処理
     *
     * @param deliveryScaModel
     * @param redirectAttributes
     * @param model
     * @return
     */
    @GetMapping(value = "/regist/")
    @HEHandler(exception = AppLevelListException.class, returnView = "redirect:/delivery/sca/")
    public String doLoadRegistIndex(DeliveryScaModel deliveryScaModel,
                                    RedirectAttributes redirectAttributes,
                                    Model model) {

        // 不正操作チェック
        if (deliveryScaModel.getDmcd() == null) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/delivery/";
        }

        // 初期表示処理
        DeliveryMethodEntity resultEntity = deliveryMethodGetService.execute(deliveryScaModel.getDmcd());

        if (resultEntity != null) {
            deliveryScaHelper.convertToRegistPageForResult(deliveryScaModel, resultEntity);
        }

        // 初期化
        deliveryScaModel.setZipCode(null);
        deliveryScaModel.setAddress(null);
        deliveryScaModel.setCarriage(null);

        return "delivery/sca/regist";
    }

    /**
     * 登録画面：配送特別料金を追加登録します
     *
     * @param deliveryScaModel
     * @param error
     * @param redirectAttributes
     * @param sessionStatus
     * @param model
     * @return
     */
    @PostMapping(value = "/regist/", params = "doOnceZipCodeAdd")
    @HEHandler(exception = AppLevelListException.class, returnView = "delivery/sca/regist")
    public String doOnceZipCodeAdd(@Validated(OnceZipCodeAddGroup.class) DeliveryScaModel deliveryScaModel,
                                   BindingResult error,
                                   RedirectAttributes redirectAttributes,
                                   SessionStatus sessionStatus,
                                   Model model) {

        if (error.hasErrors()) {
            return "delivery/sca/regist";
        }

        // 不正操作チェック
        if (deliveryScaModel.getDmcd() == null) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/delivery/";
        }

        // 登録内容を設定
        DeliverySpecialChargeAreaEntity entity =
                        ApplicationContextUtility.getBean(DeliverySpecialChargeAreaEntity.class);
        deliveryScaHelper.convertToDeliverySpecialChargeAreaEntityForRegist(entity, deliveryScaModel);

        // 登録処理を実行
        deliverySpecialChargeAreaRegistService.execute(entity);

        // Modelをセッションより破棄
        sessionStatus.setComplete();

        // 再検索フラグをセット
        redirectAttributes.addFlashAttribute(FLASH_DMCD, deliveryScaModel.getDmcd());

        String prefectureNameSca = StringUtils.isNotEmpty(deliveryScaModel.getPrefectureName()) ?
                        deliveryScaModel.getPrefectureName() :
                        "";
        String dmcdSca = deliveryScaModel.getDmcd() != null ? String.valueOf(deliveryScaModel.getDmcd()) : "";

        if (StringUtils.isNotEmpty(deliveryScaModel.getPrefectureName())) {
            return "redirect:/delivery/sca/?dmcd=" + dmcdSca + "&prefectureName=" + prefectureNameSca;
        } else {
            return "redirect:/delivery/sca/?dmcd=" + dmcdSca;
        }
    }

    /**
     * 登録画面：入力された郵便番号で住所を検索します
     *
     * @param deliveryScaModel
     * @param error
     * @param redirectAttributes
     * @param model
     * @return
     */
    @PostMapping(value = "/regist/", params = "doZipCodeSearch")
    @HEHandler(exception = AppLevelListException.class, returnView = "delivery/sca/regist")
    public String doZipCodeSearch(@Validated(ZipCodeSearchGroup.class) DeliveryScaModel deliveryScaModel,
                                  BindingResult error,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {

        if (error.hasErrors()) {
            return "delivery/sca/regist";
        }

        // 不正操作チェック
        if (deliveryScaModel.getDmcd() == null) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/delivery/";
        }

        List<ZipCodeAddressDto> addressList = addressGetLogic.getAddressList(deliveryScaModel.getZipCode());
        deliveryScaHelper.convertToRegistPageForZipCodeResult(deliveryScaModel, addressList);

        return "delivery/sca/regist";
    }
}
