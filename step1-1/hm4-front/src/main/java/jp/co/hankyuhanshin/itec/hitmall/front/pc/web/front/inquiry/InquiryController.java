package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.inquiry;
// 廃止不要機能
//
//import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
//import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
//import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
//import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
//import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMailTemplateType;
//import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMemberInfoStatus;
//import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDto;
//import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goodsgroup.GoodsGroupDto;
//import jp.co.hankyuhanshin.itec.hitmall.front.dto.inquiry.InquiryDetailsDto;
//import jp.co.hankyuhanshin.itec.hitmall.front.entity.inquiry.InquiryGroupEntity;
//import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
//import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.inquiry.InquiryHelper;
//import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.inquiry.InquiryModel;
//import jp.co.hankyuhanshin.itec.hitmall.front.utility.AsyncTaskUtility;
//import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
//import jp.co.hankyuhanshin.itec.hitmall.front.utility.InquiryUtility;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.support.SessionStatus;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.lang.reflect.InvocationTargetException;

///*
// * Project Name : HIT-MALL4
// *
// * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
// *
// */
//
//
//import java.lang.reflect.InvocationTargetException;
//import java.util.List;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.SessionAttributes;
//import org.springframework.web.bind.support.SessionStatus;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
//import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMailTemplateType;
//import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMemberInfoStatus;
//import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDto;
//import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goodsgroup.GoodsGroupDto;
//import jp.co.hankyuhanshin.itec.hitmall.front.dto.inquiry.InquiryDetailsDto;
//import jp.co.hankyuhanshin.itec.hitmall.front.entity.inquiry.InquiryGroupEntity;
//import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
//import jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry.InquiryRegistLogic;
//import jp.co.hankyuhanshin.itec.hitmall.service.goods.group.OpenGoodsGroupDetailsGetService;
//import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoGetService;
//import jp.co.hankyuhanshin.itec.hitmall.service.process.AsyncService;
//import jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry.InquiryGroupListGetService;
//import jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry.InquirySendMailService;
//import jp.co.hankyuhanshin.itec.hitmall.front.utility.AsyncTaskUtility;
//import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
//import jp.co.hankyuhanshin.itec.hitmall.front.utility.InquiryUtility;

import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
//import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
//import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;

/**
 * お問い合わせ画面 Controller<br/>
 *
 * @author kaneda
 */
// 廃止不要機能
//@SessionAttributes(value = "inquiryModel")
@RequestMapping("/inquiry")
@Controller
public class InquiryController extends AbstractController {

    // 廃止不要機能
    //    /**
    //     * エラーメッセージコード：不正操作
    //     */
    //    public static final String MSGCD_ILLEGAL_OPERATION = "AIX000101";
    //
    //    /**
    //     * Key for InquiryGroup Goods
    //     */
    //    public static final String INQUIRYGROUP_GOODS = "inquiry.inquiryGroup.goods";
    //
    //    /**
    //     * 前画面が確認画面の「修正する」からであるかを判断するフラグ
    //     */
    //    public static final String FLASH_FROMCONFIRM = "fromConfirm";

    /**
     * フラッシュスコープパラメータ：商品詳細画面から商品グループ番号受取用
     */
    public static final String FLASH_GGCD = "ggcd";

    /**
     * フラッシュスコープパラメータ：商品詳細画面から商品番号受取用
     */
    public static final String FLASH_GCD = "gcd";

    /**
     * フラッシュスコープパラメータ：商品詳細画面から商品規格1受取用
     */
    public static final String FLASH_UNIT = "redirectU1Lbl";

    // 廃止不要機能
    //
    //    /**
    //     * helper
    //     */
    //    private InquiryHelper inquiryHelper;
    //
    //    /**
    //     * お問い合わせ分類リスト取得サービス
    //     */
    //    private InquiryGroupListGetService inquiryGroupListGetService;
    //
    //    /**
    //     * 会員情報取得サービス
    //     */
    //    private MemberInfoGetService memberInfoGetService;
    //
    //    /**
    //     * 公開商品グループ詳細情報取得サービス
    //     */
    //    private OpenGoodsGroupDetailsGetService openGoodsGroupDetailsGetService;
    //
    //    /**
    //     * お問い合わせ登録ロジック
    //     */
    //    private InquiryRegistLogic inquiryRegistLogic;
    //
    //    /**
    //     * お問い合わせ受付メール送信サービス
    //     */
    //    private InquirySendMailService inquirySendMailService;
    //
    //    /**
    //     * 非同期処理サービス
    //     */
    //    private AsyncService asyncService;
    //
    //    /**
    //     * InquiryUtility
    //     */
    //    private InquiryUtility inquiryUtility;
    //
    //    /**
    //     * コンストラクタ
    //     *
    //     * @param inquiryHelper
    //     * @param inquiryGroupListGetService
    //     * @param memberInfoGetService
    //     * @param openGoodsGroupDetailsGetService
    //     * @param inquiryRegistLogic
    //     * @param inquirySendMailService
    //     * @param asyncService
    //     * @param inquiryUtility
    //     */
    //    @Autowired
    //    public InquiryController(InquiryHelper inquiryHelper, InquiryGroupListGetService inquiryGroupListGetService, MemberInfoGetService memberInfoGetService, OpenGoodsGroupDetailsGetService openGoodsGroupDetailsGetService, InquiryRegistLogic inquiryRegistLogic, InquirySendMailService inquirySendMailService, AsyncService asyncService, InquiryUtility inquiryUtility) {
    //        this.inquiryHelper = inquiryHelper;
    //        this.inquiryGroupListGetService = inquiryGroupListGetService;
    //        this.memberInfoGetService = memberInfoGetService;
    //        this.openGoodsGroupDetailsGetService = openGoodsGroupDetailsGetService;
    //        this.inquiryRegistLogic = inquiryRegistLogic;
    //        this.inquirySendMailService = inquirySendMailService;
    //        this.asyncService = asyncService;
    //        this.inquiryUtility = inquiryUtility;
    //
    //    }
    //

    /**
     * 入力画面：初期処理
     * <p>
     * // 廃止不要機能
     * //     * @param inquiryModel
     * //     * @param redirectAttributes
     * //     * @param model
     *
     * @return 入力画面
     */
    // PDR Migrate Customization from here
    @GetMapping(value = {"/", "/index.html"})
    // PDR Migrate Customization to here
    // 廃止不要機能
    //    @HEHandler(exception = AppLevelListException.class, returnView = "inquiry/index")
    protected String doLoadIndex(
                    //            InquiryModel inquiryModel,
                    RedirectAttributes redirectAttributes, Model model) {
        // 廃止不要機能
        //        try {
        //            prerender(inquiryModel, model);
        //        } catch (IllegalAccessException | InvocationTargetException e) {
        //            throwMessage(MSGCD_ILLEGAL_OPERATION);
        //        }
        //
        //        // 動的コンポーネント作成
        //        initComponents(inquiryModel);

        return "inquiry/index";
    }

    // 廃止不要機能
    //
    //    /**
    //     * 入力画面：問い合わせ確認画面に遷移
    //     *
    //     * @param inquiryModel
    //     * @param error
    //     * @param redirectAttributes
    //     * @param model
    //     * @return 問い合わせ確認画面
    //     */
    //    @PostMapping(value = {"/", "/index.html"}, params = "doConfirm")
    //    @HEHandler(exception = AppLevelListException.class, returnView = "inquiry/index")
    //    public String doConfirm(@Validated InquiryModel inquiryModel, BindingResult error, RedirectAttributes redirectAttributes, Model model) {
    //
    //        // 何らかの不正操作でセッションがクリアされている
    //        if (inquiryModel.getInquiryGroupItems() == null) {
    //            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
    //            return "redirect:/inquiry/index.html";
    //        }
    //
    //        if (error.hasErrors()) {
    //            return "inquiry/index";
    //        }
    //
    //        // ページに変換。
    //        inquiryHelper.toPageForConfirm(inquiryModel);
    //
    //        // 問い合わせ確認画面に遷移
    //        return "redirect:/inquiry/confirm.html";
    //    }
    //
    //    /**
    //     * 確認画面：初期処理
    //     *
    //     * @param inquiryModel
    //     * @param redirectAttributes
    //     * @param model
    //     * @return 確認画面
    //     */
    //    @GetMapping(value = {"/confirm", "/confirm.html"})
    //    protected String doLoadConfirm(InquiryModel inquiryModel, RedirectAttributes redirectAttributes, Model model) {
    //
    //        // 何らかの不正操作でセッションがクリアされている
    //        if (inquiryModel.getInquiryGroup() == null) {
    //            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
    //            return "redirect:/inquiry/index.html";
    //        }
    //        return "inquiry/confirm";
    //    }
    //
    //    /**
    //     * 確認画面：入力画面に遷移
    //     *
    //     * @param inquiryModel
    //     * @param redirectAttributes
    //     * @param model
    //     * @return お問い合わせ入力画面
    //     */
    //    @PostMapping(value = {"/confirm", "/confirm.html"}, params = "doIndex")
    //    public String doIndexConfirm(InquiryModel inquiryModel, RedirectAttributes redirectAttributes, Model model) {
    //
    //        // 遷移元フラグ設定
    //        redirectAttributes.addFlashAttribute(FLASH_FROMCONFIRM, null);
    //
    //        return "redirect:/inquiry/";
    //    }
    //
    //    /**
    //     * ２重サブミットの防止<br />
    //     * 確認画面：DBに登録内容の登録<br />
    //     * 問い合わせ完了画面への遷移
    //     *
    //     * @param inquiryModel
    //     * @param sessionStatus
    //     * @param redirectAttributes
    //     * @param model
    //     * @return 問い合わせ完了画面
    //     */
    //    // @TakeOver(type = TakeOverType.NEVER)
    //    @PostMapping(value = {"/confirm", "/confirm.html"}, params = "doOnceRegist")
    //    @HEHandler(exception = AppLevelListException.class, returnView = "inquiry/confirm")
    //    public String doOnceRegist(InquiryModel inquiryModel, SessionStatus sessionStatus, RedirectAttributes redirectAttributes, Model model) {
    //
    //        // 問い合わせエンティティDTOに変換
    //        InquiryDetailsDto inquiryDetailsDto;
    //        try {
    //            inquiryDetailsDto = inquiryHelper.toEntityDtoForPage(inquiryModel);
    //        } catch (IllegalAccessException | InvocationTargetException e) {
    //            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
    //            return "redirect:/inquiry/";
    //        }
    //
    //        // お問い合わせ登録サービス実行
    //        inquiryRegistLogic.executeInquiryRegist(inquiryDetailsDto);
    //
    //        // お問い合わせ受付メール送信（非同期処理）
    //        // パラメータ設定
    //        Object[] args = new Object[]{inquiryDetailsDto.getInquiryEntity().getInquiryCode(), HTypeMailTemplateType.INQUIRY_RECEPTION};
    //        Class<?>[] argsClass = new Class<?>[]{String.class, HTypeMailTemplateType.class};
    //        // 非同期処理を実行
    //        AsyncTaskUtility.executeAfterTransactionCommit(() -> {
    //            asyncService.asyncService(inquirySendMailService, args, argsClass);
    //        });
    //
    //        // Modelをセッションより破棄
    //        sessionStatus.setComplete();
    //
    //        // お問い合わせ完了画面に遷移
    //        return "redirect:/inquiry/complete";
    //    }
    //
    //    /**
    //     * 完了画面：画面表示処理
    //     *
    //     * @return 完了画面
    //     */
    //    @GetMapping(value = {"/complete", "/complete.html"})
    //    protected String doLoadComplete() {
    //        return "inquiry/complete";
    //    }
    //
    //    /**
    //     * 表示前処理<br/>
    //     *
    //     * @param inquiryModel
    //     * @param model
    //     * @return null;
    //     * @throws InvocationTargetException
    //     * @throws IllegalAccessException
    //     */
    //    public Class<?> prerender(InquiryModel inquiryModel, Model model) throws IllegalAccessException, InvocationTargetException {
    //
    //        if (!model.containsAttribute(FLASH_FROMCONFIRM)) {
    //            // 確認画面からの遷移でなければ入力値をクリアする
    //            clearModel(InquiryModel.class, inquiryModel, model);
    //            // 会員情報取得、表示
    //            setMemberInfo(inquiryModel, model);
    //
    //            setGoodsInfo(inquiryModel, model);
    //        }
    //        return null;
    //    }
    //
    //    /**
    //     * 動的コンポーネント作成
    //     *
    //     * @param inquiryModel
    //     */
    //    protected void initComponents(InquiryModel inquiryModel) {
    //        createInquiryGroupRadio(inquiryModel);
    //    }
    //
    //    /**
    //     * 会員情報を取得
    //     *
    //     * @param inquiryModel
    //     * @param model
    //     * @throws InvocationTargetException
    //     * @throws IllegalAccessException
    //     */
    //    protected void setMemberInfo(InquiryModel inquiryModel, Model model) throws IllegalAccessException, InvocationTargetException {
    //        // ログイン状態のとき、会員であるとみなして会員情報を取得
    //        CommonInfoUtility commonInfoUtility = ApplicationContextUtility.getBean(CommonInfoUtility.class);
    //        if (commonInfoUtility.isLogin(getCommonInfo())) {
    //            Integer mSeq = getCommonInfo().getCommonInfoUser().getMemberInfoSeq();
    //            MemberInfoEntity memberInfoEntity = memberInfoGetService.execute(mSeq, HTypeMemberInfoStatus.ADMISSION);
    //            inquiryHelper.toPageForLoad(memberInfoEntity, inquiryModel);
    //        }
    //    }
    //
    //    /**
    //     * 問い合わせ分類ラジオボタン作成
    //     *
    //     * @param inquiryModel
    //     */
    //    protected void createInquiryGroupRadio(InquiryModel inquiryModel) {
    //        List<InquiryGroupEntity> inquiryGroupEntityList = inquiryGroupListGetService.execute();
    //
    //        inquiryModel.setInquiryGroupItems(inquiryUtility.makeInquiryGroupMap(inquiryGroupEntityList));
    //    }
    //
    //    /**
    //     * Sets goodsInfo if from goods page
    //     *
    //     * @param inquiryModel
    //     * @param model
    //     */
    //    protected void setGoodsInfo(InquiryModel inquiryModel, Model model) {
    //
    //        // 別サブアプリから受け取ったパラメータ設定
    //        if (model.containsAttribute(FLASH_GGCD) && model.containsAttribute(FLASH_GCD)) {
    //            inquiryModel.setGgcd((String) model.getAttribute(FLASH_GGCD));
    //            inquiryModel.setGcd((String) model.getAttribute(FLASH_GCD));
    //            if (model.containsAttribute(FLASH_UNIT)) {
    //                inquiryModel.setRedirectU1Lbl((String) model.getAttribute(FLASH_UNIT));
    //            }
    //        }
    //
    //        // ggcdに値があるのは 商品詳細 ページから遷移したものです。
    //        if (StringUtils.isNotEmpty(inquiryModel.getGgcd())) {
    //
    //            GoodsGroupDto goodsGroupDto = openGoodsGroupDetailsGetService.execute(inquiryModel.getGgcd(), inquiryModel.getGcd());
    //            inquiryModel.setGoodsGroupName(goodsGroupDto.getGoodsGroupEntity().getGoodsGroupName());
    //            inquiryModel.setUnitTitle1(goodsGroupDto.getGoodsGroupDisplayEntity().getUnitTitle1());
    //
    //            if (StringUtils.isNotEmpty(inquiryModel.getGcd())) {
    //                inquiryModel.setUnitTitle2(goodsGroupDto.getGoodsGroupDisplayEntity().getUnitTitle2());
    //
    //                for (GoodsDto goodsDto : goodsGroupDto.getGoodsDtoList()) {
    //                    if (inquiryModel.getGcd().equals(goodsDto.getGoodsEntity().getGoodsCode())) {
    //                        inquiryModel.setUnitSelect2Label(goodsDto.getGoodsEntity().getUnitValue2());
    //                        break;
    //                    }
    //                }
    //            }
    //
    //            inquiryModel.setInquiryGroup(PropertiesUtil.getSystemPropertiesValue(INQUIRYGROUP_GOODS));
    //        }
    //    }
}
