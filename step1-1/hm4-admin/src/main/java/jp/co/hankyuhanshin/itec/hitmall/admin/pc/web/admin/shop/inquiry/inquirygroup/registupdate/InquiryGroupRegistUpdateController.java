/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.inquiry.inquirygroup.registupdate;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry.InquiryGroupRegistUpdateCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry.InquiryGroupGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry.InquiryGroupRegistService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry.InquiryGroupUpdateService;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.DiffUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
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
import java.util.Optional;

@RequestMapping("inquiry/inquirygroup")
@Controller
@SessionAttributes(value = "inquiryGroupRegistUpdateModel")
@PreAuthorize("hasAnyAuthority('SHOP:8')")
public class InquiryGroupRegistUpdateController extends AbstractController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(InquiryGroupRegistUpdateController.class);

    /**
     * メッセージコード：不正操作
     */
    protected static final String MSGCD_ILLEGAL_OPERATION = "ASI000501";

    /**
     * helper
     */
    private final InquiryGroupRegistUpdateHelper inquiryGroupRegistUpdateHelper;

    /**
     * 問い合わせ分類取得サービス
     */
    private final InquiryGroupGetService inquiryGroupGetService;

    /**
     * 問い合わせ分類登録更新チェックロジック
     */
    private final InquiryGroupRegistUpdateCheckLogic inquiryGroupCheckLogic;

    /**
     * 問い合わせ分類登録サービス
     */
    private final InquiryGroupRegistService inquiryGroupRegistService;

    /**
     * 問い合わせ分類更新サービス
     */
    private final InquiryGroupUpdateService inquiryGroupUpdateService;

    @Autowired
    public InquiryGroupRegistUpdateController(InquiryGroupRegistUpdateHelper inquiryGroupRegistUpdateHelper,
                                              InquiryGroupGetService inquiryGroupGetService,
                                              InquiryGroupRegistUpdateCheckLogic inquiryGroupCheckLogic,
                                              InquiryGroupRegistService inquiryGroupRegistService,
                                              InquiryGroupUpdateService inquiryGroupUpdateService) {
        this.inquiryGroupRegistUpdateHelper = inquiryGroupRegistUpdateHelper;
        this.inquiryGroupGetService = inquiryGroupGetService;
        this.inquiryGroupCheckLogic = inquiryGroupCheckLogic;
        this.inquiryGroupRegistService = inquiryGroupRegistService;
        this.inquiryGroupUpdateService = inquiryGroupUpdateService;
    }

    /**
     * 初期処理
     *
     * @return 自画面
     */
    @GetMapping(value = "/registupdate")
    @HEHandler(exception = AppLevelListException.class, returnView = "inquiry/inquirygroup/registupdate/index")
    protected String doLoadIndex(@RequestParam(required = false) Optional<Integer> seq,
                                 InquiryGroupRegistUpdateModel inquiryGroupRegistUpdateModel,
                                 Model model) {

        initDropDownValue(inquiryGroupRegistUpdateModel);
        InquiryGroupEntity inquiryGroupEntity = null;

        // 確認画面からの遷移でなければ
        if (!inquiryGroupRegistUpdateModel.isFromConfirm()) {
            clearModel(InquiryGroupRegistUpdateModel.class, inquiryGroupRegistUpdateModel, model);
            initDropDownValue(inquiryGroupRegistUpdateModel);

            // パラメータの問い合わせ分類SEQを取得
            if (seq.isPresent()) {

                // 指定時、対象データ取得
                try {
                    inquiryGroupEntity = inquiryGroupGetService.execute(seq.get());
                } catch (Exception e) {
                    // 取得失敗時はエラー画面へ
                    LOGGER.error("例外処理が発生しました", e);
                    return "redirect:/error";
                }
                // 変更前情報
                inquiryGroupRegistUpdateModel.setOriginalInquiryGroupEntity(CopyUtil.deepCopy(inquiryGroupEntity));
            } else {
                // 指定がない場合、新規登録として処理
                inquiryGroupEntity = ApplicationContextUtility.getBean(InquiryGroupEntity.class);
            }
        }
        // ページ反映
        inquiryGroupRegistUpdateHelper.toPageForLoad(inquiryGroupRegistUpdateModel, inquiryGroupEntity);

        inquiryGroupRegistUpdateModel.setFromConfirm(false);

        return "inquiry/inquirygroup/registupdate/index";
    }

    /**
     * 確認画面へ遷移
     *
     * @return 確認画面
     */
    @PostMapping(value = "/registupdate", params = "doConfirm")
    @HEHandler(exception = AppLevelListException.class, returnView = "inquiry/inquirygroup/registupdate/index")
    public String doConfirm(@Validated InquiryGroupRegistUpdateModel inquiryGroupRegistUpdateModel,
                            BindingResult error,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        checkInquiryGroupSeq(inquiryGroupRegistUpdateModel, redirectAttributes, model);
        if (error.hasErrors()) {
            return "inquiry/inquirygroup/registupdate/index";
        }

        // 登録内容チェック
        inquiryGroupRegistUpdateHelper.toInquiryGroupEntityForInquiryGroupRegist(inquiryGroupRegistUpdateModel);
        inquiryGroupCheckLogic.execute(inquiryGroupRegistUpdateModel.getInquiryGroupEntity());
        return "redirect:/inquiry/inquirygroup/registupdate/confirm";
    }

    /**
     * 問い合わせ分類情報整合性チェック<br/>
     * チェックメソッド<br/>
     *
     * @return エラーの場合のみエラーページを返す
     */
    public String checkInquiryGroupSeq(InquiryGroupRegistUpdateModel inquiryGroupRegistUpdateModel,
                                       RedirectAttributes redirectAttributes,
                                       Model model) {
        // 問い合わせ分類SEQが取得できない場合は、データ不整合とみなしエラーとする
        if (!inquiryGroupRegistUpdateModel.isNormality()) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/inquiry/inquirygroup/";
        }
        return "inquiry/inquirygroup/registupdate/index";
    }

    /**
     * 初期処理
     *
     * @return 自画面 or エラー画面
     */
    @GetMapping(value = "/registupdate/confirm")
    protected String doLoadConfirm(InquiryGroupRegistUpdateModel inquiryGroupRegistUpdateModel) {

        // ブラウザバックの場合、処理しない
        if (inquiryGroupRegistUpdateModel.getInquiryGroupEntity() == null) {
            return "redirect:/error";
        }

        // 整合性チェック
        if (this.hasErrorInput(inquiryGroupRegistUpdateModel)) {
            return "redirect:/error";
        }

        // 入力値からエンティティを作成（変更後データ）
        InquiryGroupEntity modifiedInquiryGroupEntity = inquiryGroupRegistUpdateModel.getInquiryGroupEntity();

        // 変更前データと変更後データの差異リスト作成
        if (inquiryGroupRegistUpdateModel.getInquiryGroupSeq() != null) {
            List<String> modifiedList = DiffUtil.diff(inquiryGroupRegistUpdateModel.getOriginalInquiryGroupEntity(),
                                                      modifiedInquiryGroupEntity
                                                     );
            inquiryGroupRegistUpdateModel.setModifiedList(modifiedList);
        } else {
            inquiryGroupRegistUpdateModel.setModifiedList(null);
        }
        return "inquiry/inquirygroup/registupdate/confirm";
    }

    /**
     * キャンセル
     *
     * @return 問い合わせ分類登録更新画面
     */
    @PostMapping(value = "/registupdate/confirm", params = "doCancel")
    public String doCancel(InquiryGroupRegistUpdateModel inquiryGroupRegistUpdateModel) {
        inquiryGroupRegistUpdateModel.setFromConfirm(true);

        return "redirect:/inquiry/inquirygroup/registupdate";
    }

    /**
     * 問い合わせ分類登録/更新
     * 正常終了後は問い合わせ分類一覧画面へ遷移
     *
     * @return 問い合わせ分類一覧画面
     */
    @PostMapping(value = "/registupdate/confirm", params = "doOnceInquiryGroupRegist")
    @HEHandler(exception = AppLevelListException.class, returnView = "inquiry/inquirygroup/registupdate/confirm")
    public String doOnceInquiryGroupRegist(InquiryGroupRegistUpdateModel inquiryGroupRegistUpdateModel,
                                           SessionStatus sessionStatus) {

        // 整合性チェック
        if (this.hasErrorInput(inquiryGroupRegistUpdateModel)) {
            return "redirect:/error";
        }

        // 画面内容反映
        InquiryGroupEntity inquiryGroupEntity = inquiryGroupRegistUpdateModel.getInquiryGroupEntity();

        // 登録内容チェック
        inquiryGroupCheckLogic.execute(inquiryGroupEntity);

        Integer inquiryGroupSeq = inquiryGroupEntity.getInquiryGroupSeq();
        if (inquiryGroupSeq == null) {
            // SEQ未設定時
            inquiryGroupRegistService.execute(inquiryGroupEntity);
        } else {
            // SEQ指定時
            inquiryGroupUpdateService.execute(inquiryGroupEntity);
        }

        // Modelをセッションより破棄
        sessionStatus.setComplete();

        return "redirect:/inquiry/inquirygroup";
    }

    /**
     * 必須入力項目が存在するかチェック
     *
     * @return エラーがあった場合:true // 正常な場合:false
     */
    protected boolean hasErrorInput(InquiryGroupRegistUpdateModel inquiryGroupRegistUpdateModel) {

        if (inquiryGroupRegistUpdateModel.getInquiryGroupEntity() == null) {
            // 登録・更新問わずエンティティはセットされてるはず
            return true;

        } else if (inquiryGroupRegistUpdateModel.getInquiryGroupName() == null
                   || inquiryGroupRegistUpdateModel.getOpenStatus() == null) {
            // 入力必須項目
            return true;
        }

        return false;
    }

    /**
     * プルダウンアイテム情報を取得
     *
     * @param inquiryGroupRegistUpdateModel フリーエリア登録・更新画面
     */
    private void initDropDownValue(InquiryGroupRegistUpdateModel inquiryGroupRegistUpdateModel) {
        inquiryGroupRegistUpdateModel.setOpenStatusItems(EnumTypeUtil.getEnumMap(HTypeOpenStatus.class));
    }
}
