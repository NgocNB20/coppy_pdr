/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.shopinfo;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.ShopGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.ShopUpdateService;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/shopinfo")
@Controller
@SessionAttributes(value = "shopInfoModel")
@PreAuthorize("hasAnyAuthority('SETTING:4')")
public class ShopInfoController extends AbstractController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ShopInfoController.class);

    /**
     * helper
     */
    private final ShopInfoHelper shopInfoHelper;

    /**
     * ショップ情報取得サービス
     */
    private final ShopGetService shopInfoGetService;

    /**
     * ショップ情報更新サービス
     */
    private final ShopUpdateService shopUpdateService;

    public static final String MSGCD_ILLEGAL_OPERATION = "AOX000301";

    /**
     * エラーメッセージコード：時間帯の値をいじられた
     */
    public static final String MSGCD_ILLEGAL_TIME = "AOX000302";

    @Autowired
    public ShopInfoController(ShopInfoHelper shopInfoHelper,
                              ShopGetService shopInfoGetService,
                              ShopUpdateService shopUpdateService) {
        this.shopInfoGetService = shopInfoGetService;
        this.shopInfoHelper = shopInfoHelper;
        this.shopUpdateService = shopUpdateService;
    }

    /**
     * 初期処理
     *
     * @return 自画面(エラー時 、 検索画面)
     */
    @GetMapping("/")
    @HEHandler(exception = AppLevelListException.class, returnView = "shopinfo/index")
    protected String doLoadIndex(ShopInfoModel shopInfoModel, Model model) {
        clearModel(ShopInfoModel.class, shopInfoModel, model);
        try {
            shopInfoModel.setShopEntity(shopInfoGetService.execute());
        } catch (Exception e) {
            // 取得失敗時、エラー画面へ遷移
            LOGGER.error("例外処理が発生しました", e);
            return "redirect:/error";
        }

        // ページ反映
        shopInfoHelper.toPageForLoadUpdate(shopInfoModel, shopInfoModel.getShopEntity());
        return "shopinfo/index";
    }

    /**
     * 店舗情報設定画面へ遷移
     *
     * @return 更新画面
     */
    @PreAuthorize("hasAnyAuthority('SETTING:8')")
    @PostMapping(value = "/update", params = "doUpdatePage")
    public String doUpdatePage() {
        return "redirect:/shopinfo/update";
    }

    @GetMapping(value = "/update")
    @HEHandler(exception = AppLevelListException.class, returnView = "shopinfo/update")
    @PreAuthorize("hasAnyAuthority('SETTING:8')")
    protected String doLoadUpdate(ShopInfoModel shopInfoModel, Model model) {

        // ブラウザバックの場合、処理しない
        if (shopInfoModel.getShopEntity() == null) {
            return "redirect:/shopinfo/";
        }
        clearModel(ShopInfoModel.class, shopInfoModel, model);
        initDropDownValue(shopInfoModel);

        // ショップ情報・会社情報を取得しなおす
        try {
            shopInfoModel.setShopEntity(shopInfoGetService.execute());
        } catch (Exception e) {
            // 取得失敗時、エラー画面へ遷移
            LOGGER.error("例外処理が発生しました", e);
            return "redirect:/error";
        }

        // ページ反映
        shopInfoHelper.toPageForLoadUpdate(shopInfoModel, shopInfoModel.getShopEntity());
        return "shopinfo/update";
    }

    /**
     * 店舗情報更新処理
     * 正常終了後は店舗情報詳細画面へ遷移
     *
     * @return 店舗情報詳細画面
     */
    @PreAuthorize("hasAnyAuthority('SETTING:8')")
    @PostMapping(value = "/", params = "doOnceUpdate")
    @HEHandler(exception = AppLevelListException.class, returnView = "redirect:/error")
    public String doOnceUpdate(@Validated ShopInfoModel shopInfoModel,
                               BindingResult error,
                               RedirectAttributes redirectAttributes,
                               Model model) {

        if (error.hasErrors()) {
            return "shopinfo/update";
        }
        // 入力情報チェック
        //        checkData(shopInfoModel, model);
        // 不正遷移チェック
        checkPageDto(shopInfoModel, model);
        // 更新前Action内チェック
        checkInput(shopInfoModel, model);

        // 更新用Dtoを生成して画面にセット
        shopInfoHelper.toShopEntityForUpdate(shopInfoModel);

        shopUpdateService.execute(shopInfoModel.getShopEntity());

        // 更新処理完了後、詳細画面に戻る
        return "redirect:/shopinfo/";
    }

    /**
     * キャンセル処理
     * 店舗情報詳細画面へ遷移
     *
     * @return 店舗情報詳細画面
     */
    @PreAuthorize("hasAnyAuthority('SETTING:8')")
    @PostMapping(value = "/", params = "doCancel")
    public String doCancel() {
        // 詳細画面に戻る
        return "redirect:/shopinfo/";
    }

    /**
     * 不正遷移かどうかをチェック<br/>
     */
    protected void checkPageDto(ShopInfoModel shopInfoModel, Model model) {
        if (shopInfoModel.getShopEntity() == null || shopInfoModel.getShopEntity().getShopSeq() == null) {
            throwMessage("ASC000102");
        }
    }

    /**
     * Enumに不正値が設定されていないかをチェック<br/>
     */
    protected void checkInput(ShopInfoModel shopInfoMode, Model model) {
        if (EnumTypeUtil.getEnumFromValue(HTypeOpenStatus.class, shopInfoMode.getShopOpenStatusPC()) == null
            || EnumTypeUtil.getEnumFromValue(HTypeOpenStatus.class, shopInfoMode.getShopOpenStatusMB()) == null) {
            throwMessage("ASC000102");
        }
    }

    /**
     * プルダウンアイテム情報を取得
     *
     * @param freeareaRegistUpdateModel フリーエリア登録・更新画面
     */
    private void initDropDownValue(ShopInfoModel shopInfoMode) {
        shopInfoMode.setShopOpenStatusPCItems(EnumTypeUtil.getEnumMap(HTypeOpenStatus.class));
    }
}
