/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.delivery.did;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.AllDownloadGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.RegistUpdateGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.SearchGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.UploadGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.PageInfoHelper;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.DeliveryImpossibleDayDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryImpossibleDaySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryImpossibleDayEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryImpossibleDayGetByYearAndDateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryImpossibleDayListGetByYearLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryImpossibleDayRegistUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.DeliveryDidAllCsvDownloadService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.DeliveryMethodGetService;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.exception.FileDownloadException;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.apache.commons.lang3.time.DateUtils;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * お届け不可日検索
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("/delivery/did")
@Controller
@SessionAttributes(value = "deliveryDidModel")
@PreAuthorize("hasAnyAuthority('SETTING:8')")
public class DeliveryDidController extends AbstractController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryDidController.class);

    /**
     * デフォルトページ番号
     */
    private static final String DEFAULT_PNUM = "1";

    /**
     * デフォルト最大表示件数
     */
    private static final int DEFAULT_LIMIT = 100;

    /**
     * メッセージコード：不正操作
     */
    public static final String MSGCD_ILLEGAL_OPERATION = "AYD000601";

    /**
     * メッセージコード：年月日チェックエラー
     */
    public static final String MSGCD_DATE_CHECK_ERROR = "HM34-4001-001-A-";

    /**
     * メッセージコード：登録成功
     */
    public static final String MSGCD_REGIST_SUCCESS = "HM34-4001-002-A-";

    /**
     * メッセージコード：登録失敗
     */
    public static final String MSGCD_REGIST_FAILURE = "HM34-4001-003-A-";

    /**
     * メッセージコード：削除成功
     */
    public static final String MSGCD_DELETE_SUCCESS = "HM34-4001-004-A-";

    /**
     * メッセージコード：登録失敗
     */
    public static final String MSGCD_DELETE_FAILURE = "HM34-4001-005-A-";

    /**
     * 表示モード:「list」の場合 再検索
     */
    public static final String FLASH_MD = "md";

    /**
     * 変更・登録画面から遷移
     */
    public static final String DO_REGIST_UPDATE_PARAM = "doRegistUpdate";

    /**
     * 表示モード(md):list 検索画面の再検索実行
     */
    public static final String MODE_LIST = "list";

    /**
     * お届け不可日検索ページDxo
     */
    private final DeliveryDidHelper deliveryDidHelper;

    /**
     * お届け不可日取得ロジック
     */
    private final DeliveryImpossibleDayListGetByYearLogic deliveryImpossibleDayListGetByYearLogic;

    /**
     * お届け不可日登録ロジック
     */
    private final DeliveryImpossibleDayRegistUpdateLogic deliveryImpossibleDayRegistLogic;

    /**
     * お届け不可日取得ロジック
     */
    private final DeliveryImpossibleDayGetByYearAndDateLogic deliveryImpossibleDayGetByYearAndDateLogic;

    /**
     * お届け不可日Dao
     */
    private final DeliveryImpossibleDayDao deliveryImpossibleDayDao;

    /**
     * 配送方法取得サービス
     */
    private final DeliveryMethodGetService deliveryMethodGetService;

    private final DeliveryDidAllCsvDownloadService deliveryDidAllCsvDownloadService;

    /**
     * コンストラクタ
     *
     * @param deliveryDidHelper
     * @param deliveryImpossibleDayListGetByYearLogic
     * @param deliveryImpossibleDayRegistLogic
     * @param deliveryImpossibleDayGetByYearAndDateLogic
     * @param deliveryImpossibleDayDao
     * @param deliveryMethodGetService
     */
    @Autowired
    public DeliveryDidController(DeliveryDidHelper deliveryDidHelper,
                                 DeliveryImpossibleDayListGetByYearLogic deliveryImpossibleDayListGetByYearLogic,
                                 DeliveryImpossibleDayRegistUpdateLogic deliveryImpossibleDayRegistLogic,
                                 DeliveryImpossibleDayGetByYearAndDateLogic deliveryImpossibleDayGetByYearAndDateLogic,
                                 DeliveryImpossibleDayDao deliveryImpossibleDayDao,
                                 DeliveryMethodGetService deliveryMethodGetService,
                                 DeliveryDidAllCsvDownloadService deliveryDidAllCsvDownloadService) {
        this.deliveryDidHelper = deliveryDidHelper;
        this.deliveryImpossibleDayListGetByYearLogic = deliveryImpossibleDayListGetByYearLogic;
        this.deliveryImpossibleDayRegistLogic = deliveryImpossibleDayRegistLogic;
        this.deliveryImpossibleDayGetByYearAndDateLogic = deliveryImpossibleDayGetByYearAndDateLogic;
        this.deliveryImpossibleDayDao = deliveryImpossibleDayDao;
        this.deliveryMethodGetService = deliveryMethodGetService;
        this.deliveryDidAllCsvDownloadService = deliveryDidAllCsvDownloadService;
    }

    /**
     * 検索
     *
     * @param deliveryDidModel
     * @param model
     */
    protected void search(DeliveryDidModel deliveryDidModel, Model model) {

        // 検索条件作成
        DeliveryImpossibleDaySearchForDaoConditionDto conditionDto = deliveryDidHelper.toConditionDto(deliveryDidModel);

        // ページング検索セットアップ
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
        pageInfoHelper.setupPageInfo(conditionDto, deliveryDidModel.getPageNumber(), deliveryDidModel.getLimit());

        // 取得
        List<DeliveryImpossibleDayEntity> list = deliveryImpossibleDayListGetByYearLogic.execute(conditionDto);

        // 画面に反映
        deliveryDidHelper.toPageIndex(list, deliveryDidModel);

        // ページャーセットアップ
        pageInfoHelper.setupViewPager(conditionDto, deliveryDidModel);

        // 検索総件数をセット
        deliveryDidModel.setTotalCount(conditionDto.getTotalCount());
    }

    /**
     * 初期表示<br/>
     *
     * @param dmcdParam
     * @param md
     * @param deliveryDidModel
     * @param model
     * @param redirectAttributes
     * @return
     */
    @GetMapping(value = "/")
    @HEHandler(exception = AppLevelListException.class, returnView = "redirect:/delivery/did/")
    public String doLoadIndex(@RequestParam(required = false) Optional<String> yearParam,
                              @RequestParam(required = false) Optional<String> dmcdParam,
                              @RequestParam(required = false) Optional<String> md,
                              DeliveryDidModel deliveryDidModel,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        boolean isAfterRegistUpdate = md.isPresent() && DO_REGIST_UPDATE_PARAM.equals(md.get()) && dmcdParam.isPresent()
                                      && Objects.equals(deliveryDidModel.getDmcd(), Integer.parseInt(dmcdParam.get()));
        boolean isNotChangeOfDmcdParam = dmcdParam.isPresent() && Objects.equals(deliveryDidModel.getDmcd(),
                                                                                 Integer.parseInt(dmcdParam.get())
                                                                                );
        boolean isNotChangeOfYearParam = yearParam.isPresent() && Objects.equals(deliveryDidModel.getYear(),
                                                                                 Integer.parseInt(yearParam.get())
                                                                                );

        if (isAfterRegistUpdate || (isNotChangeOfDmcdParam && isNotChangeOfYearParam)) {
            return "delivery/did/index";
        }

        // モデルのクリア処理
        clearModel(DeliveryDidModel.class, deliveryDidModel, model);

        // 配送方法SEQ取得
        dmcdParam.ifPresent(str -> deliveryDidModel.setDmcd(Integer.parseInt(str)));

        // 年が設定されている場合、引き継ぎ用の年にセット
        yearParam.ifPresent(str -> deliveryDidModel.setYear(Integer.parseInt(str)));

        // 不正操作チェック
        if (deliveryDidModel.getDmcd() == null) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/delivery/";
        }

        DeliveryMethodEntity resultEntity = deliveryMethodGetService.execute(deliveryDidModel.getDmcd());

        // 不正操作チェック。配送マスタは物理削除されないので、結果がないのはURLパラメータをいじられた以外ありえない。
        if (resultEntity == null) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/delivery/";
        }

        deliveryDidHelper.convertToRegistPageForResult(deliveryDidModel, resultEntity);

        // 設定がない場合、今年を初期選択値として設定
        Calendar cal = Calendar.getInstance();
        if (deliveryDidModel.getYear() == null) {
            deliveryDidModel.setYear(cal.get(Calendar.YEAR));
        }

        // ページング項目初期化
        deliveryDidModel.setPageNumber(DEFAULT_PNUM);
        deliveryDidModel.setLimit(DEFAULT_LIMIT);

        // 検索を実行
        search(deliveryDidModel, model);

        return "delivery/did/index";
    }

    /**
     * 検索<br/>
     *
     * @param deliveryDidModel
     * @param model
     * @return
     */
    @PostMapping(value = "/", params = "doSearch")
    @HEHandler(exception = AppLevelListException.class, returnView = "delivery/did/index")
    public String doSearch(@Validated(SearchGroup.class) DeliveryDidModel deliveryDidModel,
                           BindingResult error,
                           Model model) {

        if (error.hasErrors()) {
            return "delivery/did/index";
        }

        // ページング項目初期化
        deliveryDidModel.setPageNumber(DEFAULT_PNUM);
        deliveryDidModel.setLimit(DEFAULT_LIMIT);

        search(deliveryDidModel, model);
        return "delivery/did/index";
    }

    /**
     * 全件CSV出力
     *
     * @param deliveryDidModel
     * @param error
     * @param model
     * @return
     */
    @PostMapping(value = "/", params = "doCsvDownload")
    @HEHandler(exception = AppLevelListException.class, returnView = "delivery/did/index")
    @HEHandler(exception = FileDownloadException.class, returnView = "delivery/did/index")
    public void doCsvDownload(@Validated(AllDownloadGroup.class) DeliveryDidModel deliveryDidModel,
                              BindingResult error,
                              HttpServletResponse response,
                              Model model) {

        if (error.hasErrors()) {
            throw new FileDownloadException(model);
        }

        // 検索条件作成
        DeliveryImpossibleDaySearchForDaoConditionDto conditionDto = deliveryDidHelper.toConditionDto(deliveryDidModel);

        try {
            this.deliveryDidAllCsvDownloadService.execute(conditionDto, response);
        } catch (IOException e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new FileDownloadException(model);
        }

    }

    /**
     * お届け不可日登録
     *
     * @param deliveryDidModel
     * @param error
     * @param redirectAttributes
     * @param model
     * @return
     * @throws ParseException
     */
    @PostMapping(value = "/", params = "doRegistUpdate")
    @HEHandler(exception = AppLevelListException.class, returnView = "delivery/did/index")
    public String doRegistUpdate(@Validated(RegistUpdateGroup.class) DeliveryDidModel deliveryDidModel,
                                 BindingResult error,
                                 RedirectAttributes redirectAttributes,
                                 Model model) throws ParseException {

        if (error.hasErrors()) {
            return "delivery/did/index";
        }

        // Pageの値をエンティティへコピー
        DeliveryImpossibleDayEntity deliveryImpossibleDayEntity =
                        deliveryDidHelper.toDeliveryImpossibleDayEntityForRegistUpdate(deliveryDidModel);

        // システム日付を取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
        Date now = dateUtility.getCurrentDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);

        // 入力した年が今年または翌月でない場合
        if (deliveryImpossibleDayEntity.getYear() != calendar.get(Calendar.YEAR)
            && deliveryImpossibleDayEntity.getYear() != calendar.get(Calendar.YEAR) + 1) {
            // エラーメッセージを設定し、更新処理は行わない
            throwMessage(MSGCD_DATE_CHECK_ERROR, new Object[] {"年月日"});
        }

        // 登録更新結果
        int result = 0;
        // 登録更新処理を実行
        result = deliveryImpossibleDayRegistLogic.execute(deliveryImpossibleDayEntity);

        if (result > 0) {
            // 登録した年を検索条件として設定しておく
            deliveryDidModel.setYear(deliveryImpossibleDayEntity.getYear());
            // 再検索を実行
            this.search(deliveryDidModel, model);
            // 成功メッセージを設定
            addMessage(MSGCD_REGIST_SUCCESS, redirectAttributes, model);
        } else {
            // エラーメッセージを設定
            addMessage(MSGCD_REGIST_FAILURE, redirectAttributes, model);
        }

        return "redirect:/delivery/did/?dmcdParam=" + deliveryDidModel.getDmcd() + "&md=doRegistUpdate";
    }

    /**
     * お届け不可日削除
     *
     * @param deliveryDidModel
     * @param redirectAttributes
     * @param model
     * @return
     * @throws ParseException
     */
    @PostMapping(value = "/", params = "doDelete")
    @HEHandler(exception = AppLevelListException.class, returnView = "delivery/did/index")
    public String doDelete(DeliveryDidModel deliveryDidModel, RedirectAttributes redirectAttributes, Model model)
                    throws ParseException {
        // 削除対象お届け不可日取得
        Date deleteDate = DateUtils.parseDate(deliveryDidModel.getDeleteDate(), new String[] {"yyyy/MM/dd"});
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(deleteDate);
        DeliveryImpossibleDayEntity deliveryImpossibleDayEntity =
                        deliveryImpossibleDayGetByYearAndDateLogic.execute(calendar.get(Calendar.YEAR), deleteDate,
                                                                           deliveryDidModel.getDmcd()
                                                                          );

        // 削除結果
        int result = 0;
        if (deliveryImpossibleDayEntity != null) {
            // 削除処理を実行
            result = deliveryImpossibleDayDao.delete(deliveryImpossibleDayEntity);
        }

        // 再検索を実行
        this.search(deliveryDidModel, model);
        if (result > 0) {
            // 成功メッセージを設定
            addInfoMessage(MSGCD_DELETE_SUCCESS, null, redirectAttributes, model);
        } else {
            // エラーメッセージを設定
            addInfoMessage(MSGCD_DELETE_FAILURE, null, redirectAttributes, model);
        }

        return "redirect:/delivery/did/?yearParam=" + deliveryDidModel.getYear() + "&dmcdParam="
               + deliveryDidModel.getDmcd();
    }

    /**
     * ソート<br/>
     *
     * @param deliveryDidModel
     * @param model
     * @return
     */
    @PostMapping(value = "/", params = "doDisplayChange")
    @HEHandler(exception = AppLevelListException.class, returnView = "delivery/did/index")
    public String doDisplayChange(DeliveryDidModel deliveryDidModel, Model model) {
        search(deliveryDidModel, model);
        return "delivery/did/index";
    }

    /**
     * アップロード画面へ遷移<br/>
     *
     * @param deliveryDidModel
     * @return class
     */
    @PostMapping(value = "/", params = "doUpload")
    public String doUpload(@Validated(UploadGroup.class) DeliveryDidModel deliveryDidModel,
                           BindingResult error,
                           RedirectAttributes redirectAttrs,
                           Model model) {

        if (error.hasErrors()) {
            return "delivery/did/index";
        }

        // アプリケーションが変わるため、選択年、配送方法SEQをリダイレクトスコープで引き渡す
        String yearDid = deliveryDidModel.getYear() != null ? String.valueOf(deliveryDidModel.getYear()) : "";
        String dmcdDid = deliveryDidModel.getDmcd() != null ? String.valueOf(deliveryDidModel.getDmcd()) : "";

        return "redirect:/delivery/did/bundledupload/?yearParam=" + yearDid + "&dmcdParam=" + dmcdDid;
    }

}
