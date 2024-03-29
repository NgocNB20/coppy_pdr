/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.icon;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.dto.icon.GoodsInformationIconDto;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.icon.GoodsInformationIconDeleteService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.icon.GoodsInformationIconListGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.icon.GoodsInformationIconListUpdateService;
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

/**
 * 店舗管理：アイコン設定アクション>
 *
 * @author Thang
 */
@RequestMapping("/icon")
@Controller
@SessionAttributes(value = "iconModel")
@PreAuthorize("hasAnyAuthority('SHOP:4')")
public class IconController extends AbstractController {

    /**
     * helper
     */
    private final IconHelper iconHelper;

    /**
     * アイコンリスト取得サービス
     */
    private final GoodsInformationIconListGetService goodsInformationIconListGetService;

    /**
     * アイコンリスト更新サービス
     */
    private final GoodsInformationIconListUpdateService goodsInformationIconListUpdateService;

    /**
     * アイコン削除サービス
     */
    private final GoodsInformationIconDeleteService goodsInformationIconDeleteService;

    /**
     * メッセージコード：表示順の保存に成功
     */
    protected static final String MSGCD_SAVE_SUCCESS = "AYD000102";

    /**
     * 変更・登録画面から遷移
     */
    public static final String FLASH_DO_UPDATE = "doUpdate";

    @Autowired
    public IconController(GoodsInformationIconListGetService goodsInformationIconListGetService,
                          GoodsInformationIconListUpdateService goodsInformationIconListUpdateService,
                          GoodsInformationIconDeleteService goodsInformationIconDeleteService,
                          IconHelper iconHelper) {
        this.goodsInformationIconListGetService = goodsInformationIconListGetService;
        this.goodsInformationIconListUpdateService = goodsInformationIconListUpdateService;
        this.goodsInformationIconDeleteService = goodsInformationIconDeleteService;
        this.iconHelper = iconHelper;
    }

    /**
     * 初期処理<br/>
     *
     * @param iconModel IconModel
     * @param model     Model
     * @return 自画面
     */
    @GetMapping("/")
    @HEHandler(exception = AppLevelListException.class, returnView = "icon/index")
    protected String doLoad(IconModel iconModel, Model model) {

        clearModel(IconModel.class, iconModel, model);
        List<GoodsInformationIconDto> resultList = goodsInformationIconListGetService.execute();
        iconHelper.toPageForLoad(resultList, iconModel);

        return "icon/index";

    }

    /**
     * 表示順変更(一つ上)
     *
     * @param iconModel IconModel
     * @return 自画面
     */
    @PreAuthorize("hasAnyAuthority('SHOP:8')")
    @PostMapping(value = "/", params = "doOrderDisplayUp")
    public String doOrderDisplayUp(@Validated IconModel iconModel, BindingResult error) {

        if (error.hasErrors()) {
            return "icon/index";
        }

        // ラジオボタン選択時のみ対応
        if (iconModel.getOrderDisplay() != null) {
            int index = this.getSelectedOrderDisplay(iconModel);
            iconHelper.toPageForChangeOrderDisplay(index, index - 1, iconModel);
        }

        return "icon/index";
    }

    /**
     * 表示順変更(一つ下)
     *
     * @param iconModel IconModel
     * @return 自画面
     */
    @PreAuthorize("hasAnyAuthority('SHOP:8')")
    @PostMapping(value = "/", params = "doOrderDisplayDown")
    public String doOrderDisplayDown(@Validated IconModel iconModel, BindingResult error) {

        if (error.hasErrors()) {
            return "icon/index";
        }

        // ラジオボタン選択時のみ対応
        if (iconModel.getOrderDisplay() != null) {
            int index = this.getSelectedOrderDisplay(iconModel);
            iconHelper.toPageForChangeOrderDisplay(index, index + 1, iconModel);
        }

        return "icon/index";
    }

    /**
     * 表示順変更(一番上)
     *
     * @param iconModel IconModel
     * @return 自画面
     */
    @PreAuthorize("hasAnyAuthority('SHOP:8')")
    @PostMapping(value = "/", params = "doOrderDisplayTop")
    public String doOrderDisplayTop(@Validated IconModel iconModel, BindingResult error) {

        if (error.hasErrors()) {
            return "icon/index";
        }

        // ラジオボタン選択時のみ対応
        if (iconModel.getOrderDisplay() != null) {
            int index = this.getSelectedOrderDisplay(iconModel);
            iconHelper.toPageForChangeOrderDisplay(index, 0, iconModel);
        }

        return "icon/index";
    }

    /**
     * 表示順変更(一番下)
     *
     * @param iconModel IconModel
     * @return 自画面
     */
    @PreAuthorize("hasAnyAuthority('SHOP:8')")
    @PostMapping(value = "/", params = "doOrderDisplayBottom")
    public String doOrderDisplayBottom(@Validated IconModel iconModel, BindingResult error) {

        if (error.hasErrors()) {
            return "icon/index";
        }

        // ラジオボタン選択時のみ対応
        if (iconModel.getOrderDisplay() != null) {
            int index = this.getSelectedOrderDisplay(iconModel);
            iconHelper.toPageForChangeOrderDisplay(index, iconModel.getResultItems().size() - 1, iconModel);
        }

        return "icon/index";
    }

    /**
     * アイコン表示順をコミット
     *
     * @param iconModel          IconModel
     * @param redirectAttributes RedirectAttributes
     * @param model              Model
     * @return 自画面
     */
    @PreAuthorize("hasAnyAuthority('SHOP:8')")
    @PostMapping(value = "/", params = "doOnceOrderDisplayUpdate")
    @HEHandler(exception = AppLevelListException.class, returnView = "icon/index")
    public String doOnceOrderDisplayUpdate(IconModel iconModel, RedirectAttributes redirectAttributes, Model model) {

        List<GoodsInformationIconDto> iconDtoList =
                        iconHelper.toGoodsInformationIconDtoListForOrderDisplayUpdate(iconModel);
        goodsInformationIconListUpdateService.execute(iconDtoList);
        addInfoMessage(MSGCD_SAVE_SUCCESS, null, redirectAttributes, model);

        return "redirect:/icon/";
    }

    /**
     * アイコン削除
     *
     * @param iconModel IconModel
     * @return 自画面
     */
    @PreAuthorize("hasAnyAuthority('SHOP:8')")
    @PostMapping(value = "/", params = "doOnceIconDelete")
    @HEHandler(exception = AppLevelListException.class, returnView = "icon/index")
    public String doOnceIconDelete(IconModel iconModel) {
        // ラジオボタン選択時のみ対応
        IconModelItem item = iconModel.getResultItems().get(iconModel.getResultIndex());
        GoodsInformationIconDto iconDto = iconModel.getIconDtoMap().get(item.getIconSeq());

        goodsInformationIconDeleteService.execute(iconDto.getGoodsInformationIconEntity());

        return "redirect:/icon/";
    }

    /**
     * アイコン　登録更新ページへ移動(登録)
     *
     * @param redirectAttributes
     * @return アイコン登録更新ページ
     */
    @PreAuthorize("hasAnyAuthority('SHOP:8')")
    @PostMapping(value = "/", params = "doRegist")
    public String doRegist(RedirectAttributes redirectAttributes) {
        return "redirect:/icon/registupdate?md=new";
    }

    /**
     * アイコン　登録更新ページへ移動(更新)
     *
     * @param iconModel          IconModel
     * @param redirectAttributes RedirectAttributes
     * @return アイコン登録更新ページ
     */
    @PreAuthorize("hasAnyAuthority('SHOP:8')")
    @PostMapping(value = "/", params = "doUpdate")
    public String doUpdate(IconModel iconModel, RedirectAttributes redirectAttributes) {
        int index = iconModel.getResultIndex();
        Integer iconSeq = iconModel.getResultItems().get(index).getIconSeq();

        redirectAttributes.addFlashAttribute(FLASH_DO_UPDATE, true);

        return "redirect:/icon/registupdate/?iconSeqParam=" + iconSeq;
    }

    /**
     * 変更対象の表示順取得
     *
     * @param iconModel IconModel
     * @return 表示順
     */
    protected int getSelectedOrderDisplay(IconModel iconModel) {
        return iconModel.getOrderDisplay() - 1;
    }
}
