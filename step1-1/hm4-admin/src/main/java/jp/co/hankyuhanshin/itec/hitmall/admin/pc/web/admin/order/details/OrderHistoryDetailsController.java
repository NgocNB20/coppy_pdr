/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.details;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.conveni.ConvenienceEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.ConvenienceLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.order.ReceiveOrderGetService;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.common.UIComponentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 処理履歴詳細Action
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("/order/details/historydetails")
@Controller
@SessionAttributes(value = "orderHistoryDetailsModel")
@PreAuthorize("hasAnyAuthority('ORDER:4')")
public class OrderHistoryDetailsController extends AbstractController {

    /**
     * 処理履歴詳細ページ用DXO
     */
    private OrderHistoryDetailsHelper orderHistorydetailsHelper;

    /**
     * 受注詳細情報取得サービス
     */
    private ReceiveOrderGetService receiveOrderGetService;

    /**
     * コンビニ名称リスト取得ロジック
     */
    private ConvenienceLogic convenienceLogic;

    /**
     * コンストラクタ
     *
     * @param orderHistorydetailsHelper
     * @param receiveOrderGetService
     * @param convenienceLogic
     */
    @Autowired
    public OrderHistoryDetailsController(OrderHistoryDetailsHelper orderHistorydetailsHelper,
                                         ReceiveOrderGetService receiveOrderGetService,
                                         ConvenienceLogic convenienceLogic) {
        this.orderHistorydetailsHelper = orderHistorydetailsHelper;
        this.receiveOrderGetService = receiveOrderGetService;
        this.convenienceLogic = convenienceLogic;
    }

    /**
     * 初期処理<br/>
     *
     * @param orderHistoryDetailsModel
     * @param redirectAttributes
     * @param model
     * @return 自画面
     */
    @GetMapping(value = "/")
    @HEHandler(exception = AppLevelListException.class, returnView = "order/details/historydetails")
    protected String doLoadIndex(@RequestParam(required = false) Optional<String> orderCode,
                                 @RequestParam(required = false) Optional<Integer> orderVersionNo,
                                 OrderHistoryDetailsModel orderHistoryDetailsModel,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {

        if (!orderCode.isPresent() || !orderVersionNo.isPresent()) {
            addMessage("AOX001601", redirectAttributes, model);
            return "redirect:/error";
        }

        // モデル初期化
        clearModel(OrderHistoryDetailsModel.class, orderHistoryDetailsModel, model);

        String orderCodeData = orderCode.get();
        Integer orderVersionNoData = orderVersionNo.get();
        orderHistoryDetailsModel.setOrderCode(orderCodeData);
        orderHistoryDetailsModel.setOrderVersionNo(orderVersionNoData);

        // 受注詳細情報取得サービス実行
        ReceiveOrderDto receiveOrderDto = receiveOrderGetService.execute(orderCodeData, orderVersionNoData);
        orderHistoryDetailsModel.setModifiedReceiveOrder(receiveOrderDto);
        orderHistoryDetailsModel.setHistoryDetailsFlag(true);

        // コンビニ名称リストをセット
        createConvenienceSelect(orderHistoryDetailsModel);

        // 一つ前の処理履歴番号の受注DTOを取得
        if (orderVersionNoData > 1) {
            orderVersionNoData = orderVersionNoData - 1;
            ReceiveOrderDto preReceiveOrderDto = receiveOrderGetService.execute(orderCodeData, orderVersionNoData);
            orderHistoryDetailsModel.setOriginalReceiveOrder(preReceiveOrderDto);
            // 受注詳細情報ページ反映
            orderHistorydetailsHelper.toPage(orderHistoryDetailsModel, receiveOrderDto);
            orderHistorydetailsHelper.setDiff(orderHistoryDetailsModel);

        } else {

            // 受注詳細情報ページ反映
            orderHistorydetailsHelper.toPage(orderHistoryDetailsModel, receiveOrderDto);

        }

        return "order/details/historydetails";
    }

    /**
     * コンビニプルダウン作成
     */
    protected void createConvenienceSelect(OrderHistoryDetailsModel orderHistoryDetailsModel) {
        orderHistoryDetailsModel.setConvenienceAllList(convenienceLogic.getConvenienceList());
        Map<String, String> convenienceMap = new LinkedHashMap<>();
        for (ConvenienceEntity convenience : orderHistoryDetailsModel.getConvenienceAllList()) {
            convenienceMap.put(convenience.getConveniCode().toString(), convenience.getConveniName());
        }
        orderHistoryDetailsModel.setConvenienceItems(UIComponentUtil.getItemList(convenienceMap));
    }

    /**
     * 受注処理履歴画面へ遷移<br/>
     *
     * @return 受注処理履歴画面
     */
    @PostMapping(value = "/", params = "doProcesshistory")
    public String doProcesshistory(OrderHistoryDetailsModel orderHistoryDetailsModel) {
        return "redirect:/order/details/processhistory/?orderCode=" + orderHistoryDetailsModel.getOrderCode();
    }
}
