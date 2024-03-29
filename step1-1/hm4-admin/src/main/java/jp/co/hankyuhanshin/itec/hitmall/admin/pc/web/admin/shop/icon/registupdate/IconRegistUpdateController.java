/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.icon.registupdate;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.icon.IconController;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.dto.icon.GoodsInformationIconDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsInformationIconEntity;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.icon.GoodsInformationIconGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.icon.GoodsInformationIconRegistService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.icon.GoodsInformationIconUpdateService;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.DiffUtil;
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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@SessionAttributes(value = "iconRegistUpdateModel")
@RequestMapping("/icon")
@Controller
@PreAuthorize("hasAnyAuthority('SHOP:8')")
public class IconRegistUpdateController extends AbstractController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(IconRegistUpdateController.class);

    /**
     * 確認画面から遷移
     */
    public static final String DO_CANCEL_PARAM = "doCancel";

    /**
     * Helper
     */
    private final IconRegistUpdateHelper iconRegistUpdateHelper;

    /**
     * 商品インフォメーションアイコン取得サービス
     */
    private final GoodsInformationIconGetService goodsInformationIconGetService;

    /**
     * アイコン登録処理
     */
    private final GoodsInformationIconRegistService goodsInformationIconRegistService;

    /**
     * アイコン更新処理
     */
    private final GoodsInformationIconUpdateService goodsInformationIconUpdateService;

    @Autowired
    public IconRegistUpdateController(IconRegistUpdateHelper iconRegistUpdateHelper,
                                      GoodsInformationIconGetService goodsInformationIconGetService,
                                      GoodsInformationIconRegistService goodsInformationIconRegistService,
                                      GoodsInformationIconUpdateService goodsInformationIconUpdateService) {
        this.iconRegistUpdateHelper = iconRegistUpdateHelper;
        this.goodsInformationIconGetService = goodsInformationIconGetService;
        this.goodsInformationIconRegistService = goodsInformationIconRegistService;
        this.goodsInformationIconUpdateService = goodsInformationIconUpdateService;
    }

    /**
     * 画像表示処理<br/>
     * 初期表示用メソッド<br/>
     *
     * @return ページクラス
     */
    @GetMapping(value = "/registupdate")
    @HEHandler(exception = AppLevelListException.class, returnView = "icon/registupdate/index")
    protected String doLoadIndex(@RequestParam(required = false) Optional<String> iconSeqParam,
                                 @RequestParam(required = false) Optional<String> md,
                                 IconRegistUpdateModel iconRegistUpdateModel,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {

        boolean isReturnPage = false;
        boolean isNotChangeIconSeqParam = iconSeqParam.isPresent() && String.valueOf(iconRegistUpdateModel.getIconSeq())
                                                                            .equals(iconSeqParam.get());
        boolean isDoCancelFromConfirm = md.isPresent() && DO_CANCEL_PARAM.equals(md.get());

        if (isNotChangeIconSeqParam || isDoCancelFromConfirm) {
            isReturnPage = true;
        }

        if (model.containsAttribute(IconController.FLASH_DO_UPDATE) && Objects.equals(
                        model.getAttribute(IconController.FLASH_DO_UPDATE), true)) {
            isReturnPage = false;
        }

        if (isReturnPage) {
            return "icon/registupdate/index";
        }

        // メニューの新規からきた場合，値がのこっているのでクリアする
        if (md.isPresent() && "new".equals(md.get())) {
            iconRegistUpdateHelper.init(iconRegistUpdateModel);
        }

        if (model.containsAttribute("inputingFlg")) {
            boolean inputingFlg = (boolean) model.getAttribute("inputingFlg");
            iconRegistUpdateModel.setInputingFlg(inputingFlg);
        } else {
            iconRegistUpdateModel.setInputingFlg(false);
        }

        // 登録更新中でない場合
        if (!iconRegistUpdateModel.isInputingFlg()) {
            // モデルクリアする
            clearModel(IconRegistUpdateModel.class, iconRegistUpdateModel, model);

            GoodsInformationIconDto iconDto = null;

            if (iconSeqParam.isPresent()) {
                // アイコンSEQが設定されている場合、DBから情報取得
                Integer iconSeq = Integer.parseInt(iconSeqParam.get());
                try {
                    iconDto = goodsInformationIconGetService.execute(iconSeq);
                } catch (Exception e) {
                    // 取得失敗時、エラー画面へ遷移
                    LOGGER.error("例外処理が発生しました", e);
                    return "redirect:/error";
                }
                // 変更前情報
                iconRegistUpdateModel.setOriginalIconEntity(CopyUtil.deepCopy(iconDto.getGoodsInformationIconEntity()));
            }
            // 画面に反映
            iconRegistUpdateHelper.initGoodsInformationIconDto(iconRegistUpdateModel, iconDto);
        }

        // 実行前処理
        String check = preDoAction(iconRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        iconRegistUpdateHelper.toPageForLoad(iconRegistUpdateModel);

        // 修正画面の場合、画面用アイコンSEQを設定
        if (md.isEmpty()) {
            iconRegistUpdateModel.setScIconSeq(iconRegistUpdateModel.getIconSeq());
        }

        return "icon/registupdate/index";
    }

    /**
     * 確認画面遷移処理
     *
     * @return 確認画面
     */
    @PostMapping(value = "/registupdate", params = "doConfirm")
    @HEHandler(exception = AppLevelListException.class, returnView = "icon/registupdate/index")
    public String doConfirm(@Validated IconRegistUpdateModel iconRegistUpdateModel,
                            BindingResult error,
                            RedirectAttributes redirectAttributes,
                            Model model) {

        if (error.hasErrors()) {
            return "icon/registupdate/index";
        }

        // 実行前処理
        String check = preDoAction(iconRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        // 不正操作チェック
        if (!iconRegistUpdateModel.isNormality()) {
            addMessage(IconRegistUpdateModel.MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/icon/";
        }

        // 入力値整合性チェック
        if (this.hasErrorInput(iconRegistUpdateModel)) {
            // 整合性が取れない場合、不正遷移と見なして一覧へ
            addMessage(IconRegistUpdateModel.MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/icon/";
        }

        // 問題なければ確認画面へ遷移
        return "redirect:/icon/registupdate/confirm";

    }

    /**
     * アクション実行前処理
     *
     * @param iconRegistUpdateModel
     * @param model
     * @return String
     */
    public String preDoAction(IconRegistUpdateModel iconRegistUpdateModel,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        // 不正操作チェック
        return checkIllegalOperation(iconRegistUpdateModel, redirectAttributes, model);
    }

    /**
     * 不正操作チェック
     */
    protected String checkIllegalOperation(IconRegistUpdateModel iconRegistUpdateModel,
                                           RedirectAttributes redirectAttributes,
                                           Model model) {
        Integer scIconSeq = iconRegistUpdateModel.getScIconSeq();
        Integer dbIconSeq = iconRegistUpdateModel.getIconSeq();

        boolean isError = false;

        // 登録画面にも関わらず、アイコンSEQのDB情報を保持している場合エラー
        if (scIconSeq == null && dbIconSeq != null) {
            isError = true;

            // 修正画面にも関わらず、アイコンSEQのDB情報を保持していない場合エラー
        } else if (scIconSeq != null && dbIconSeq == null) {
            isError = true;

            // 画面用アイコンSEQとDB用アイコンSEQが異なる場合エラー
        } else if (scIconSeq != null && !scIconSeq.equals(dbIconSeq)) {
            isError = true;
        }

        if (isError) {
            addMessage(IconRegistUpdateModel.MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/icon/";
        }
        return "";
    }

    /**
     * 初期処理
     *
     * @param iconRegistUpdateModel アイコン登録更新モデル
     * @return 自画面
     */
    @GetMapping(value = "/registupdate/confirm")
    protected String doLoadConfirm(IconRegistUpdateModel iconRegistUpdateModel,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {

        // 実行前処理
        String check = preDoAction(iconRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        // 入力値チェック
        if (this.hasErrorInput(iconRegistUpdateModel)) {
            // エラー時は不正とみなし、一覧画面へ遷移
            return "redirect:/icon/";
        }

        // 修正画面の場合、画面用アイコンSEQを設定
        if (iconRegistUpdateModel.getIconSeq() != null) {
            iconRegistUpdateModel.setScIconSeq(iconRegistUpdateModel.getIconSeq());

            // 入力値からエンティティを作成（変更後データ）
            iconRegistUpdateHelper.toIconDtoForRegist(iconRegistUpdateModel);

            // 変更前データと変更後データの差異リスト作成
            List<String> modifiedList = DiffUtil.diff(
                            iconRegistUpdateModel.getOriginalIconEntity(),
                            iconRegistUpdateModel.getGoodsInformationIconDto().getGoodsInformationIconEntity()
                                                     );
            iconRegistUpdateModel.setModifiedList(modifiedList);
        }

        return "icon/registupdate/confirm";
    }

    /**
     * 登録処理(SEQの有無で登録/更新の処理振り分け)
     *
     * @param iconRegistUpdateModel アイコン登録更新モデル
     * @param model                 モデル
     * @param sessionStatus         セクションステータス
     * @return アイコン一覧画面
     */
    @PostMapping(value = "/registupdate/confirm", params = "doOnceRegist")
    @HEHandler(exception = AppLevelListException.class, returnView = "icon/registupdate/confirm")
    public String doOnceRegist(IconRegistUpdateModel iconRegistUpdateModel,
                               RedirectAttributes redirectAttributes,
                               Model model,
                               SessionStatus sessionStatus) {

        // 実行前処理
        String check = preDoAction(iconRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        // 入力値反映
        iconRegistUpdateHelper.toIconDtoForRegist(iconRegistUpdateModel);

        if (iconRegistUpdateModel.getIconSeq() == null) {
            GoodsInformationIconEntity iconEntity =
                            iconRegistUpdateModel.getGoodsInformationIconDto().getGoodsInformationIconEntity();
            goodsInformationIconRegistService.execute(iconEntity);

            // アイコンSEQが設定済みであれば、更新処理を実行
        } else {
            GoodsInformationIconEntity iconEntity =
                            iconRegistUpdateModel.getGoodsInformationIconDto().getGoodsInformationIconEntity();
            goodsInformationIconUpdateService.execute(iconEntity);
        }

        // 処理終了時は一覧画面へ
        sessionStatus.setComplete();
        return "redirect:/icon/";
    }

    /**
     * キャンセル処理(前画面遷移)
     *
     * @param iconRegistUpdateModel
     * @param redirectAttributes
     * @param model
     * @return アイコン登録更新画面
     */
    @PostMapping(value = "/registupdate/confirm", params = "doCancel")
    public String doCancel(IconRegistUpdateModel iconRegistUpdateModel,
                           RedirectAttributes redirectAttributes,
                           Model model) {

        // 実行前処理
        String check = preDoAction(iconRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }
        redirectAttributes.addFlashAttribute("inputingFlg", true);
        if (iconRegistUpdateModel.getIconSeq() != null) {
            return "redirect:/icon/registupdate/?iconSeqParam=" + iconRegistUpdateModel.getIconSeq() + "&md=doCancel";
        } else {
            return "redirect:/icon/registupdate/?md=doCancel";
        }
    }

    /**
     * 必須保持値・入力項目の有無で整合性をチェックする
     *
     * @param iconRegistUpdateModel アイコン登録更新モデル
     * @return true:エラーあり false:エラーなし
     */
    protected boolean hasErrorInput(IconRegistUpdateModel iconRegistUpdateModel) {

        if (iconRegistUpdateModel.getGoodsInformationIconDto() == null) {
            return true;
        }

        if (iconRegistUpdateModel.getGoodsInformationIconDto().getGoodsInformationIconEntity() == null) {
            return true;
        }

        if (iconRegistUpdateModel.getIconName() == null) {
            return true;
        }

        return false;
    }
}
