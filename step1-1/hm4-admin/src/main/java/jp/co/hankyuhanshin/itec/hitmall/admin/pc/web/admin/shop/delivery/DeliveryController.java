/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.AllDeliveryMethodListGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.DeliveryMethodUpdateService;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
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

import java.util.Collections;
import java.util.List;

/**
 * 配送方法設定画面アクションクラス<br/>
 * 画面コード：AYD0001
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("/delivery")
@Controller
@SessionAttributes(value = "deliveryModel")
@PreAuthorize("hasAnyAuthority('SETTING:4')")
public class DeliveryController extends AbstractController {

    /**
     * メッセージコード：不正操作
     */
    protected static final String MSGCD_ILLEGAL_OPERATION = "AYD000101";

    /**
     * メッセージコード：表示順の保存に成功
     */
    protected static final String MSGCD_SAVE_SUCCESS = "AYD000102";

    /**
     * リスト画面から
     */
    public static final String FLASH_FROM_LIST = "fromList";

    /**
     * Helper
     */
    private DeliveryHelper deliveryHelper;

    /**
     * 全配送方法エンティティリスト取得サービス
     */
    private AllDeliveryMethodListGetService allDeliveryMethodListGetService;

    /**
     * 配送方法更新サービス
     */
    private DeliveryMethodUpdateService deliveryMethodUpdateService;

    /**
     * コンストラクタ
     *
     * @param deliveryHelper
     * @param allDeliveryMethodListGetService
     * @param deliveryMethodUpdateService
     */
    @Autowired
    public DeliveryController(DeliveryHelper deliveryHelper,
                              AllDeliveryMethodListGetService allDeliveryMethodListGetService,
                              DeliveryMethodUpdateService deliveryMethodUpdateService) {
        this.deliveryHelper = deliveryHelper;
        this.allDeliveryMethodListGetService = allDeliveryMethodListGetService;
        this.deliveryMethodUpdateService = deliveryMethodUpdateService;
    }

    /**
     * 初期処理
     *
     * @return null
     */
    /**
     * 配送方法設定:初期処理
     *
     * @param deliveryModel 配送方法設定画面モデル
     * @param model         Model
     * @return 配送方法設定画面
     */
    @GetMapping(value = "/")
    @HEHandler(exception = AppLevelListException.class, returnView = "delivery/index")
    public String doLoadIndex(DeliveryModel deliveryModel, Model model) {
        if (!model.containsAttribute(FLASH_FROM_LIST)) {
            clearModel(DeliveryModel.class, deliveryModel, model);
        }

        // 配送方法エンティティリスト取得サービス実行
        List<DeliveryMethodEntity> deliveryMethodEntityList = allDeliveryMethodListGetService.execute();
        // 取得したリストをページに設定
        deliveryHelper.toPageForLoad(deliveryMethodEntityList, deliveryModel);

        return "delivery/index";
    }

    /**
     * 「一つ上へ移動」押下処理
     *
     * @param deliveryModel 配送方法設定画面モデル
     * @param model         Model
     * @return 配送方法設定画面
     */
    @PreAuthorize("hasAnyAuthority('SETTING:8')")
    @PostMapping(value = "/", params = "doMoveOneUp")
    @HEHandler(exception = AppLevelListException.class, returnView = "delivery/index")
    public String doMoveOneUp(@Validated DeliveryModel deliveryModel, BindingResult error, Model model) {

        if (error.hasErrors()) {
            return "delivery/index";
        }

        // セッションチェック
        checkSessionInfo(deliveryModel);
        // 入力値チェック
        checkSelectedValue(deliveryModel);

        List<DeliveryResultItem> deliveryMethodItems = deliveryModel.getDeliveryMethodItems();

        for (int i = 0; i < deliveryMethodItems.size(); i++) {
            DeliveryResultItem deliveryResultItem = deliveryMethodItems.get(i);
            // 選択された配送方法かどうか
            if (deliveryResultItem.getDeliveryMethodRadioValue()
                                  .toString()
                                  .equals(deliveryModel.getDeliveryMethodRadio())) {
                // ２つ目以降の配送方法かどうか
                // 先頭の配送方法の場合は処理しない
                if (i > 0) {
                    // 選択された配送方法の１つ前の配送方法を取得
                    DeliveryResultItem previousDeliveryMethodItem = deliveryMethodItems.get(i - 1);
                    Integer previousOrderDisplay = previousDeliveryMethodItem.getOrderDisplay();

                    previousDeliveryMethodItem.setOrderDisplay(deliveryResultItem.getOrderDisplay());
                    deliveryResultItem.setOrderDisplay(previousOrderDisplay);
                    deliveryMethodItems.set(i, previousDeliveryMethodItem);
                    deliveryMethodItems.set(i - 1, deliveryResultItem);

                }
                break;
            }
        }
        return "delivery/index";
    }

    /**
     * 「一つ下へ移動」押下処理
     *
     * @param deliveryModel 配送方法設定画面モデル
     * @param model         Model
     * @return 配送方法設定画面
     */
    @PreAuthorize("hasAnyAuthority('SETTING:8')")
    @PostMapping(value = "/", params = "doMoveOneDown")
    @HEHandler(exception = AppLevelListException.class, returnView = "delivery/index")
    public String doMoveOneDown(@Validated DeliveryModel deliveryModel, BindingResult error, Model model) {

        if (error.hasErrors()) {
            return "delivery/index";
        }

        // セッションチェック
        checkSessionInfo(deliveryModel);
        // 入力値チェック
        checkSelectedValue(deliveryModel);

        List<DeliveryResultItem> deliveryMethodItems = deliveryModel.getDeliveryMethodItems();

        for (int i = 0; i < deliveryMethodItems.size(); i++) {
            DeliveryResultItem deliveryResultItem = deliveryMethodItems.get(i);
            // 選択された配送方法かどうか
            if (deliveryResultItem.getDeliveryMethodRadioValue()
                                  .toString()
                                  .equals(deliveryModel.getDeliveryMethodRadio())) {
                // 選択された配送方法が最後の配送方法の場合処理しない
                if (i < deliveryMethodItems.size() - 1) {
                    // 選択された配送方法の１つ後の配送方法を取得
                    DeliveryResultItem nextDeliveryMethodItem = deliveryMethodItems.get(i + 1);
                    Integer nextOrderDisplay = nextDeliveryMethodItem.getOrderDisplay();

                    nextDeliveryMethodItem.setOrderDisplay(deliveryResultItem.getOrderDisplay());
                    deliveryResultItem.setOrderDisplay(nextOrderDisplay);
                    deliveryMethodItems.set(i, nextDeliveryMethodItem);
                    deliveryMethodItems.set(i + 1, deliveryResultItem);

                }
                break;
            }
        }
        return "delivery/index";
    }

    /**
     * 「一番上へ移動」押下処理
     *
     * @param deliveryModel 配送方法設定画面モデル
     * @param model         Model
     * @return 配送方法設定画面
     */
    @PreAuthorize("hasAnyAuthority('SETTING:8')")
    @PostMapping(value = "/", params = "doMoveHead")
    @HEHandler(exception = AppLevelListException.class, returnView = "delivery/index")
    public String doMoveHead(@Validated DeliveryModel deliveryModel, BindingResult error, Model model) {

        if (error.hasErrors()) {
            return "delivery/index";
        }

        // セッションチェック
        checkSessionInfo(deliveryModel);
        // 入力値チェック
        checkSelectedValue(deliveryModel);

        moveHead(deliveryModel);
        return "delivery/index";
    }

    /**
     * 「一番下へ移動」押下処理
     *
     * @param deliveryModel 配送方法設定画面モデル
     * @param model         Model
     * @return 配送方法設定画面
     */
    @PreAuthorize("hasAnyAuthority('SETTING:8')")
    @PostMapping(value = "/", params = "doMoveFoot")
    @HEHandler(exception = AppLevelListException.class, returnView = "delivery/index")
    public String doMoveFoot(@Validated DeliveryModel deliveryModel, BindingResult error, Model model) {

        if (error.hasErrors()) {
            return "delivery/index";
        }

        // セッションチェック
        checkSessionInfo(deliveryModel);
        // 入力値チェック
        checkSelectedValue(deliveryModel);

        Collections.reverse(deliveryModel.getDeliveryMethodItems());
        moveHead(deliveryModel);
        Collections.reverse(deliveryModel.getDeliveryMethodItems());
        return "delivery/index";
    }

    /**
     * 「表示順を保存する」押下処理
     *
     * @param deliveryModel 配送方法設定画面モデル
     * @param redirectAttrs RedirectAttributes
     * @param model         Model
     * @return 配送方法設定画面
     */
    @PreAuthorize("hasAnyAuthority('SETTING:8')")
    @PostMapping(value = "/", params = "doOnceSave")
    @HEHandler(exception = AppLevelListException.class, returnView = "delivery/index")
    public String doOnceSave(DeliveryModel deliveryModel, RedirectAttributes redirectAttrs, Model model) {
        // セッションチェック
        checkSessionInfo(deliveryModel);

        // 配送方法更新サービス実行
        deliveryMethodUpdateService.execute(deliveryHelper.toDeliveryMethodEntityListForSave(deliveryModel));
        addInfoMessage(MSGCD_SAVE_SUCCESS, null, redirectAttrs, model);
        redirectAttrs.addFlashAttribute(FLASH_FROM_LIST, true);
        return "redirect:/delivery/";
    }

    /**
     * 入力値のチェック
     *
     * @param deliveryModel 配送方法設定画面モデル
     */
    protected void checkSelectedValue(DeliveryModel deliveryModel) {
        for (DeliveryResultItem deliveryResultItem : deliveryModel.getDeliveryMethodItems()) {
            if (deliveryResultItem.getDeliveryMethodRadioValue()
                                  .toString()
                                  .equals(deliveryModel.getDeliveryMethodRadio())) {
                return;
            }
        }

        throwMessage(MSGCD_ILLEGAL_OPERATION);
    }

    /**
     * 選択された配送方法を先頭に移動します。
     *
     * @param deliveryModel 配送方法設定画面モデル
     */
    protected void moveHead(DeliveryModel deliveryModel) {
        // セッションチェック
        checkSessionInfo(deliveryModel);

        List<DeliveryResultItem> deliveryMethodItems = deliveryModel.getDeliveryMethodItems();
        Integer headOrderDisplay = 0;
        for (int i = 0; i < deliveryMethodItems.size(); i++) {
            DeliveryResultItem deliveryResultItem = deliveryMethodItems.get(i);
            if (i == 0) {
                headOrderDisplay = deliveryResultItem.getOrderDisplay();
            }
            // 選択された配送方法かどうか
            if (deliveryResultItem.getDeliveryMethodRadioValue()
                                  .toString()
                                  .equals(deliveryModel.getDeliveryMethodRadio())) {
                // ２つ目以降の配送方法かどうか
                // 先頭の配送方法の場合は処理しない
                if (i > 0) {
                    // 選択された配送方法があった場所から消して、先頭に移す。
                    DeliveryResultItem selectedDeliveryMethodItem = deliveryMethodItems.remove(i);
                    selectedDeliveryMethodItem.setOrderDisplay(headOrderDisplay);
                    deliveryMethodItems.add(0, selectedDeliveryMethodItem);
                }
                break;

                // 選択された配送方法でない
            } else {
                // 最後の配送方法でない
                if (i < deliveryMethodItems.size() - 1) {
                    // １つ後の配送方法を取得
                    DeliveryResultItem nextDeliveryMethodItem = deliveryMethodItems.get(i + 1);
                    Integer nextOrderDisplay = nextDeliveryMethodItem.getOrderDisplay();
                    deliveryResultItem.setOrderDisplay(nextOrderDisplay);
                }
            }
        }
    }

    /**
     * セッション情報のチェック<br/>
     * 不正操作防止
     *
     * @param deliveryModel 配送方法設定画面モデル
     */
    protected void checkSessionInfo(DeliveryModel deliveryModel) {
        if (deliveryModel.getDeliveryMethodItems() == null) {
            throwMessage(MSGCD_ILLEGAL_OPERATION);
        }
    }
}
