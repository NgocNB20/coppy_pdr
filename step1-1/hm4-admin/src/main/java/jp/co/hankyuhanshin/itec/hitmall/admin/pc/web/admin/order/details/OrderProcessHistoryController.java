/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.details;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.PageInfoHelper;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.index.OrderIndexSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.index.OrderIndexEntity;
import jp.co.hankyuhanshin.itec.hitmall.service.order.index.OrderIndexListGetService;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

/**
 * 処理履歴ページアクションクラス<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("/order/details/processhistory")
@Controller
@SessionAttributes(value = "orderProcessHistoryModel")
@PreAuthorize("hasAnyAuthority('ORDER:4')")
public class OrderProcessHistoryController extends AbstractController {
    /**
     * 受注インデックスリスト取得処理
     */
    private OrderIndexListGetService orderIndexListGetService;

    /**
     * 処理履歴ページ用DXO
     */
    private OrderProcessHistoryHelper orderProcesshistoryHelper;

    /**
     * コンストラクタ
     *
     * @param orderProcesshistoryHelper
     * @param orderIndexListGetService
     */
    @Autowired
    public OrderProcessHistoryController(OrderProcessHistoryHelper orderProcesshistoryHelper,
                                         OrderIndexListGetService orderIndexListGetService) {
        this.orderProcesshistoryHelper = orderProcesshistoryHelper;
        this.orderIndexListGetService = orderIndexListGetService;
    }

    /**
     * 初期処理<br/>
     *
     * @param orderProcessHistoryModel
     * @param redirectAttrs
     * @param model
     * @return 自画面
     */
    @GetMapping(value = "/")
    @HEHandler(exception = AppLevelListException.class, returnView = "order/details/processhistory")
    protected String doLoadIndex(@RequestParam(required = false) Optional<String> orderCode,
                                 OrderProcessHistoryModel orderProcessHistoryModel,
                                 RedirectAttributes redirectAttrs,
                                 Model model) {

        String orderCodeData = null;
        if (orderCode.isPresent()) {
            orderCodeData = orderCode.get();
            orderProcessHistoryModel.setOrderCode(orderCodeData);
        }
        if (orderCodeData == null) {
            addMessage(OrderDetailsController.MSGCD_REFERER_FAIL, redirectAttrs, model);
            return "redirect:/error";
        }
        OrderIndexSearchForDaoConditionDto conditionDto =
                        ApplicationContextUtility.getBean(OrderIndexSearchForDaoConditionDto.class);
        conditionDto.setShopSeq(1001);
        conditionDto.setOrderCode(orderCodeData);

        // ページング検索セットアップ
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
        pageInfoHelper.setupPageInfo(conditionDto, "1", Integer.MAX_VALUE, "orderVersionNo", true);
        List<OrderIndexEntity> list = orderIndexListGetService.execute(conditionDto);
        orderProcesshistoryHelper.toPageItems(orderProcessHistoryModel, list);
        return "order/details/processhistory";
    }
}
