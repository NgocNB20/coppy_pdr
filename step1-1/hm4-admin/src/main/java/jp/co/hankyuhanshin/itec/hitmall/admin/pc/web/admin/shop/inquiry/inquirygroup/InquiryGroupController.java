/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.inquiry.inquirygroup;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry.InquiryGroupListGetForBackService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry.InquiryGroupListUpdateService;
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

import java.util.List;

@RequestMapping("inquiry/inquirygroup")
@Controller
@SessionAttributes(value = "inquiryGroupModel")
@PreAuthorize("hasAnyAuthority('SHOP:8')")
public class InquiryGroupController extends AbstractController {

    /**
     * Helper
     */
    private final InquiryGroupHelper inquiryGroupHelper;

    /**
     * 検索サービス
     */
    private final InquiryGroupListGetForBackService inquiryGroupListGetService;

    /**
     * 表示順更新サービス
     */
    private final InquiryGroupListUpdateService inquiryGroupListUpdateService;

    @Autowired
    public InquiryGroupController(InquiryGroupHelper inquiryGroupHelper,
                                  InquiryGroupListGetForBackService inquiryGroupListGetService,
                                  InquiryGroupListUpdateService inquiryGroupListUpdateService) {
        this.inquiryGroupHelper = inquiryGroupHelper;
        this.inquiryGroupListGetService = inquiryGroupListGetService;
        this.inquiryGroupListUpdateService = inquiryGroupListUpdateService;
    }

    /**
     * メッセージコード：表示順の保存に成功
     */
    protected static final String MSGCD_SAVE_SUCCESS = "AYD000102";

    /**
     * 初期処理
     *
     * @return 自画面
     */
    @GetMapping("")
    @HEHandler(exception = AppLevelListException.class, returnView = "inquiry/inquirygroup/index")
    protected String doLoad(InquiryGroupModel inquiryGroupModel, Model model) {

        clearModel(InquiryGroupModel.class, inquiryGroupModel, model);

        this.search(inquiryGroupModel);

        return "inquiry/inquirygroup/index";
    }

    /**
     * 登録更新画面へ遷移(登録)
     *
     * @return 登録更新画面
     */
    @PostMapping(value = "", params = "doRegist")
    public String doRegist() {
        //        return jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.inquiry.inquirygroup.InquiryGroupModel.inquiryGroupModel.class;
        return "redirect:/inquiry/inquirygroup/registupdate/";
    }

    //    /**
    //     * 登録更新画面へ遷移(更新)
    //     *
    //     * @return 登録更新画面
    //     */
    //    @PostMapping(value = "", params = "doUpdate")
    //    public String doUpdate(InquiryGroupModel inquiryGroupModel, Model model) {
    //        int index = inquiryGroupModel.getResultIndex();
    //        Integer inquiryGroupSeq = inquiryGroupModel.getResultItems().get(index).getInquiryGroupSeq();
    //        inquiryGroupModel.setInquiryGroupSeq(inquiryGroupSeq);
    //        return "redirect:/inquiry/inquirygroup/registupdate";
    //        return jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.inquiry.inquirygroup.InquiryGroupModel.inquiryGroupModel.class;
    //        return null;
    //    }

    /**
     * 表示順反映
     *
     * @return 自画面
     */
    @PostMapping(value = "", params = "doOnceOrderDisplayUpdate")
    @HEHandler(exception = AppLevelListException.class, returnView = "inquiry/inquirygroup/index")
    public String doOnceOrderDisplayUpdate(InquiryGroupModel inquiryGroupModel,
                                           RedirectAttributes redirectAttributes,
                                           Model model) {

        List<InquiryGroupEntity> list =
                        inquiryGroupHelper.toInquiryGroupEntityListForOrderDisplayUpdate(inquiryGroupModel);
        if (!list.isEmpty()) {
            // 要素があった場合のみ
            inquiryGroupListUpdateService.execute(list);
        }

        // 最新情報取得
        addInfoMessage(MSGCD_SAVE_SUCCESS, null, redirectAttributes, model);

        return "redirect:/inquiry/inquirygroup/";
    }

    /**
     * 表示順変更(一つ上)
     *
     * @return 自画面
     */
    @PostMapping(value = "", params = "doOrderDisplayUp")
    public String doOrderDisplayUp(@Validated InquiryGroupModel inquiryGroupModel, BindingResult error) {
        if (error.hasErrors()) {
            return "inquiry/inquirygroup/index";
        }
        // ラジオボタン選択時のみ対応
        if (inquiryGroupModel.getOrderDisplay() != null) {
            int index = inquiryGroupModel.getOrderDisplay() - 1;
            inquiryGroupHelper.toPageForChangeOrderDisplay(index, index - 1, inquiryGroupModel);
        }

        return "inquiry/inquirygroup/index";
    }

    /**
     * 表示順変更(一つ下)
     *
     * @return 自画面
     */
    @PostMapping(value = "", params = "doOrderDisplayDown")
    public String doOrderDisplayDown(@Validated InquiryGroupModel inquiryGroupModel, BindingResult error) {
        if (error.hasErrors()) {
            return "inquiry/inquirygroup/index";
        }
        // ラジオボタン選択時のみ対応
        if (inquiryGroupModel.getOrderDisplay() != null) {
            int index = inquiryGroupModel.getOrderDisplay() - 1;
            inquiryGroupHelper.toPageForChangeOrderDisplay(index, index + 1, inquiryGroupModel);
        }

        return "inquiry/inquirygroup/index";
    }

    /**
     * 表示順変更(一番上)
     *
     * @return 自画面
     */
    @PostMapping(value = "", params = "doOrderDisplayTop")
    public String doOrderDisplayTop(@Validated InquiryGroupModel inquiryGroupModel, BindingResult error) {
        if (error.hasErrors()) {
            return "inquiry/inquirygroup/index";
        }
        // ラジオボタン選択時のみ対応
        if (inquiryGroupModel.getOrderDisplay() != null) {
            int index = inquiryGroupModel.getOrderDisplay() - 1;
            inquiryGroupHelper.toPageForChangeOrderDisplay(index, 0, inquiryGroupModel);
        }

        return "inquiry/inquirygroup/index";
    }

    /**
     * 表示順変更(一番下)
     *
     * @return 自画面
     */
    @PostMapping(value = "", params = "doOrderDisplayBottom")
    public String doOrderDisplayBottom(@Validated InquiryGroupModel inquiryGroupModel, BindingResult error) {
        if (error.hasErrors()) {
            return "inquiry/inquirygroup/index";
        }
        // ラジオボタン選択時のみ対応
        if (inquiryGroupModel.getOrderDisplay() != null) {
            int index = inquiryGroupModel.getOrderDisplay() - 1;
            inquiryGroupHelper.toPageForChangeOrderDisplay(
                            index, inquiryGroupModel.getResultItems().size() - 1, inquiryGroupModel);
        }
        return "inquiry/inquirygroup/index";
    }

    /**
     * 検索処理
     */
    protected void search(InquiryGroupModel inquiryGroupModel) {
        List<InquiryGroupEntity> list = inquiryGroupListGetService.execute();
        inquiryGroupHelper.toPageForLoad(list, inquiryGroupModel);
    }

}
