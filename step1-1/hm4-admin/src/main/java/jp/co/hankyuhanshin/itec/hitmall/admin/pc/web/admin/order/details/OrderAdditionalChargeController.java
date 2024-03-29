/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.details;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.ajax.MessageUtils;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.ajax.ValidatorMessage;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.ReceiveOrderCheckLogic;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * 追加料金ページアクション<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("/order/details/additionalcharge")
@Controller
@SessionAttributes(value = "orderAdditionalChargeModel")
@PreAuthorize("hasAnyAuthority('ORDER:8')")
public class OrderAdditionalChargeController extends AbstractController {
    /**
     * 追加料金ページ用helper
     */
    private OrderAdditionalChargeHelper orderAdditionalchargeHelper;

    /**
     * 受注可能チェックロジック
     */
    private ReceiveOrderCheckLogic receiveOrderCheckLogic;

    /**
     * コンストラクタ
     *
     * @param orderAdditionalchargeHelper
     * @param receiveOrderCheckLogic
     */
    @Autowired
    public OrderAdditionalChargeController(OrderAdditionalChargeHelper orderAdditionalchargeHelper,
                                           ReceiveOrderCheckLogic receiveOrderCheckLogic) {
        this.orderAdditionalchargeHelper = orderAdditionalchargeHelper;
        this.receiveOrderCheckLogic = receiveOrderCheckLogic;
    }

    /**
     * 初期処理<br/>
     *
     * @param orderAdditionalChargeModel
     * @param model
     * @return 自画面
     */
    @GetMapping(value = "")
    @HEHandler(exception = AppLevelListException.class, returnView = "order/details/additionalcharge")
    protected String doLoadIndex(OrderAdditionalChargeModel orderAdditionalChargeModel, Model model) {

        ReceiveOrderDto modified = null;
        if (model.containsAttribute(DetailsUpdateModel.FLASH_UPDATE_MODEL)) {
            DetailsUpdateModel detailsUpdateModel =
                            (DetailsUpdateModel) model.getAttribute(DetailsUpdateModel.FLASH_UPDATE_MODEL);
            modified = detailsUpdateModel.getModifiedReceiveOrder();
            orderAdditionalChargeModel.setOriginalReceiveOrder(detailsUpdateModel.getOriginalReceiveOrder());
            orderAdditionalChargeModel.setModifiedReceiveOrder(modified);
            orderAdditionalChargeModel.setOrderCode(detailsUpdateModel.getOrderCode());
        }

        if (modified == null) {
            throwMessage("AOX001101");
            //            return "redirect:/error";
        } else {
            orderAdditionalChargeModel.setInputAdditionalDetailsName(null);
            orderAdditionalChargeModel.setInputAdditionalDetailsPrice(null);
        }
        ReceiveOrderDto additionalChargeModified = CopyUtil.deepCopy(modified);
        receiveOrderCheckLogic.execute(additionalChargeModified, modified, true, false, false, null, HTypeSiteType.BACK,
                                       null, getCommonInfo().getCommonInfoAdministrator().getAdministratorId()
                                      );
        orderAdditionalChargeModel.setAdditionalChargeModified(additionalChargeModified);

        orderAdditionalchargeHelper.toPage(orderAdditionalChargeModel);
        return "order/details/additionalcharge";
    }

    /**
     * 初期処理<br/>
     *
     * @param orderAdditionalChargeModel
     * @param detailsUpdateModel
     * @param model
     * @return 自画面
     */
    @PostMapping(value = "")
    @ResponseBody
    protected ResponseEntity<?> doLoadIndexAjax(OrderAdditionalChargeModel orderAdditionalChargeModel,
                                                Model model,
                                                @RequestBody DetailsUpdateModel detailsUpdateModel) {
        if (!ObjectUtils.isEmpty(detailsUpdateModel)) {
            model.addAttribute(DetailsUpdateModel.FLASH_UPDATE_MODEL, detailsUpdateModel);
        }
        ReceiveOrderDto modified = null;
        if (model.containsAttribute(DetailsUpdateModel.FLASH_UPDATE_MODEL)) {
            modified = detailsUpdateModel.getModifiedReceiveOrder();
            orderAdditionalChargeModel.setOriginalReceiveOrder(detailsUpdateModel.getOriginalReceiveOrder());
            orderAdditionalChargeModel.setModifiedReceiveOrder(modified);
            orderAdditionalChargeModel.setOrderCode(detailsUpdateModel.getOrderCode());
        }
        if (modified == null) {
            List<ValidatorMessage> list = new ArrayList<>();
            MessageUtils.getAllMessage(list, "AOX001101", null);
            return ResponseEntity.badRequest().body(list);
        }
        orderAdditionalChargeModel.setInputAdditionalDetailsName(null);
        orderAdditionalChargeModel.setInputAdditionalDetailsPrice(null);
        ReceiveOrderDto additionalChargeModified = CopyUtil.deepCopy(modified);
        receiveOrderCheckLogic.execute(additionalChargeModified, modified, true, false, false, null, HTypeSiteType.BACK,
                                       null, getCommonInfo().getCommonInfoAdministrator().getAdministratorId()
                                      );
        orderAdditionalChargeModel.setAdditionalChargeModified(additionalChargeModified);
        orderAdditionalchargeHelper.toPage(orderAdditionalChargeModel);
        return ResponseEntity.ok(orderAdditionalChargeModel);
    }

    /**
     * 追加料金追加処理<br/>
     *
     * @param orderAdditionalChargeModel
     * @return 自画面
     */
    @PostMapping(value = "/", params = "doAdd")
    @HEHandler(exception = AppLevelListException.class, returnView = "order/details/additionalcharge")
    public String doAdd(@Validated OrderAdditionalChargeModel orderAdditionalChargeModel,
                        BindingResult error,
                        RedirectAttributes redirectAttributes,
                        Model model) {

        // エラーチェック
        if (error.hasErrors()) {
            return "order/details/additionalcharge";
        }

        if (orderAdditionalChargeModel.getModifiedReceiveOrder() == null) {
            throwMessage("AOX001101");
            return "redirect:/error";
        }
        orderAdditionalchargeHelper.addOrderAdditionalCharge(orderAdditionalChargeModel);
        ReceiveOrderDto modified = orderAdditionalChargeModel.getAdditionalChargeModified();
        receiveOrderCheckLogic.execute(modified, modified, true, false, false, null, HTypeSiteType.BACK, null,
                                       getCommonInfo().getCommonInfoAdministrator().getAdministratorId()
                                      );
        orderAdditionalchargeHelper.toPage(orderAdditionalChargeModel);
        return "order/details/additionalcharge";
    }

    /**
     * 「キャンセル」ボタン押下時の処理<br/>
     *
     * @return 受注詳細修正ページ
     */
    @PostMapping(value = "/", params = "doCancel")
    public String doCancel(OrderAdditionalChargeModel orderAdditionalChargeModel,
                           RedirectAttributes redirectAttrs,
                           Model model) {
        // 受注詳細修正ページへ遷移
        redirectAttrs.addFlashAttribute(DetailsUpdateModel.FLASH_ADDITIONAL_CHARGE_MODEL, orderAdditionalChargeModel);
        return "redirect:/order/detailsupdate/";
    }

    /**
     * ページ内で追加した追加料金情報を反映して<br/>
     * 受注詳細修正画面へ遷移<br/>
     *
     * @param orderAdditionalChargeModel
     * @return 受注詳細修正画面
     */
    @PostMapping(value = "/", params = "doUpdate")
    public String doUpdate(OrderAdditionalChargeModel orderAdditionalChargeModel,
                           RedirectAttributes redirectAttrs,
                           Model model) {

        orderAdditionalchargeHelper.toWorkReceiveOrderDto(orderAdditionalChargeModel);
        redirectAttrs.addFlashAttribute(DetailsUpdateModel.FLASH_ADDITIONAL_CHARGE_MODEL, orderAdditionalChargeModel);
        return "redirect:/order/detailsupdate/";
    }

    /**
     * ページ内で追加した追加料金情報を反映して<br/>
     * 受注詳細修正画面へ遷移<br/>
     *
     * @param orderAdditionalChargeModel
     * @return 受注詳細修正画面
     */
    @PostMapping(value = "/doUpdate")
    @ResponseBody
    public ResponseEntity<?> doUpdateAjax(@Validated OrderAdditionalChargeModel orderAdditionalChargeModel,
                                          BindingResult error) {
        // エラーチェック
        if (error.hasErrors()) {
            List<ValidatorMessage> mapError = MessageUtils.getMessageErrorFromBindingResult(error);
            return ResponseEntity.badRequest().body(mapError);
        }
        if (orderAdditionalChargeModel.getModifiedReceiveOrder() == null) {
            List<ValidatorMessage> list = new ArrayList<>();
            MessageUtils.getAllMessage(list, "AOX001101", null);
            return ResponseEntity.badRequest().body(list);
        }
        orderAdditionalchargeHelper.addOrderAdditionalCharge(orderAdditionalChargeModel);
        ReceiveOrderDto modified = orderAdditionalChargeModel.getAdditionalChargeModified();
        receiveOrderCheckLogic.execute(modified, modified, true, false, false, null, HTypeSiteType.BACK, null,
                                       getCommonInfo().getCommonInfoAdministrator().getAdministratorId()
                                      );
        orderAdditionalchargeHelper.toPage(orderAdditionalChargeModel);
        orderAdditionalchargeHelper.toWorkReceiveOrderDto(orderAdditionalChargeModel);
        return ResponseEntity.ok(orderAdditionalChargeModel);
    }
}
