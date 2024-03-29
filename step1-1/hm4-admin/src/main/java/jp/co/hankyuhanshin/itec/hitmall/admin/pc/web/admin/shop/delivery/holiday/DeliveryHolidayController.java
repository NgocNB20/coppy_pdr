/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.delivery.holiday;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.AllDownloadGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.RegistUpdateGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.SearchGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.UploadGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.PageInfoHelper;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.HolidayDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.HolidaySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.HolidayEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.HolidayGetByYearAndDateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.HolidayListGetByYearLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.HolidayRegistUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.DeliveryHolidayAllCsvDownloadService;
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
 * 休日検索
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("/delivery/holiday")
@Controller
@SessionAttributes(value = "deliveryHolidayModel")
@PreAuthorize("hasAnyAuthority('SETTING:8')")
public class DeliveryHolidayController extends AbstractController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryHolidayController.class);

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
    protected static final String MSGCD_ILLEGAL_OPERATION = "AYD000601";

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
     * 休日検索ページDxo
     */
    private final DeliveryHolidayHelper deliveryHolidayHelper;

    /**
     * 休日取得ロジック
     */
    private final HolidayListGetByYearLogic holidayListGetByYearLogic;

    /**
     * 休日登録ロジック
     */
    private final HolidayRegistUpdateLogic holidayRegistLogic;

    /**
     * 休日取得ロジック
     */
    private final HolidayGetByYearAndDateLogic holidayGetByYearAndDateLogic;

    /**
     * 休日Dao
     */
    private final HolidayDao holidayDao;

    /**
     * 配送方法取得サービス
     */
    private final DeliveryMethodGetService deliveryMethodGetService;

    /**
     * 休日CSV出力サービス
     */
    private final DeliveryHolidayAllCsvDownloadService deliveryHolidayAllCsvDownloadService;

    /**
     * コンストラクタ
     *
     * @param deliveryHolidayHelper
     * @param holidayListGetByYearLogic
     * @param holidayRegistLogic
     * @param holidayGetByYearAndDateLogic
     * @param holidayDao
     * @param deliveryMethodGetService
     * @param deliveryHolidayAllCsvDownloadService
     */
    @Autowired
    public DeliveryHolidayController(DeliveryHolidayHelper deliveryHolidayHelper,
                                     HolidayListGetByYearLogic holidayListGetByYearLogic,
                                     HolidayRegistUpdateLogic holidayRegistLogic,
                                     HolidayGetByYearAndDateLogic holidayGetByYearAndDateLogic,
                                     HolidayDao holidayDao,
                                     DeliveryMethodGetService deliveryMethodGetService,
                                     DeliveryHolidayAllCsvDownloadService deliveryHolidayAllCsvDownloadService) {
        this.deliveryHolidayHelper = deliveryHolidayHelper;
        this.holidayListGetByYearLogic = holidayListGetByYearLogic;
        this.holidayRegistLogic = holidayRegistLogic;
        this.holidayGetByYearAndDateLogic = holidayGetByYearAndDateLogic;
        this.holidayDao = holidayDao;
        this.deliveryMethodGetService = deliveryMethodGetService;
        this.deliveryHolidayAllCsvDownloadService = deliveryHolidayAllCsvDownloadService;
    }

    /**
     * 検索<br/>
     *
     * @param deliveryHolidayModel
     * @param model
     */
    protected void search(DeliveryHolidayModel deliveryHolidayModel, Model model) {

        // 検索条件作成
        HolidaySearchForDaoConditionDto conditionDto = deliveryHolidayHelper.toConditionDto(deliveryHolidayModel);

        // ページング検索セットアップ
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
        pageInfoHelper.setupPageInfo(
                        conditionDto, deliveryHolidayModel.getPageNumber(), deliveryHolidayModel.getLimit());

        // 取得
        List<HolidayEntity> list = holidayListGetByYearLogic.execute(conditionDto);

        // 画面に反映
        deliveryHolidayHelper.toPageIndex(list, deliveryHolidayModel);

        // ページャーセットアップ
        pageInfoHelper.setupViewPager(conditionDto, deliveryHolidayModel);

        // 検索総件数をセット
        deliveryHolidayModel.setTotalCount(conditionDto.getTotalCount());
    }

    /**
     * 初期表示
     *
     * @param dmcdParam
     * @param md
     * @param deliveryHolidayModel
     * @param model
     * @param redirectAttributes
     * @return
     */
    @GetMapping(value = "/")
    @HEHandler(exception = AppLevelListException.class, returnView = "delivery/holiday/index")
    public String doLoadIndex(@RequestParam(required = false) Optional<String> yearParam,
                              @RequestParam(required = false) Optional<String> dmcdParam,
                              @RequestParam(required = false) Optional<String> md,
                              DeliveryHolidayModel deliveryHolidayModel,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        boolean isAfterRegistUpdate = md.isPresent() && DO_REGIST_UPDATE_PARAM.equals(md.get()) && dmcdParam.isPresent()
                                      && Objects.equals(
                        deliveryHolidayModel.getDmcd(), Integer.parseInt(dmcdParam.get()));
        boolean isNotChangeOfDmcdParam = dmcdParam.isPresent() && Objects.equals(deliveryHolidayModel.getDmcd(),
                                                                                 Integer.parseInt(dmcdParam.get())
                                                                                );
        boolean isNotChangeOfYearParam = yearParam.isPresent() && Objects.equals(deliveryHolidayModel.getYear(),
                                                                                 Integer.parseInt(yearParam.get())
                                                                                );

        if (isAfterRegistUpdate || (isNotChangeOfDmcdParam && isNotChangeOfYearParam)) {
            return "delivery/holiday/index";
        }

        // モデルのクリア処理
        clearModel(DeliveryHolidayModel.class, deliveryHolidayModel, model);

        // 配送方法SEQ取得
        dmcdParam.ifPresent(str -> deliveryHolidayModel.setDmcd(Integer.parseInt(str)));

        // 年が設定されている場合、引き継ぎ用の年にセット
        yearParam.ifPresent(str -> deliveryHolidayModel.setYear(Integer.parseInt(str)));

        // 不正操作チェック
        if (deliveryHolidayModel.getDmcd() == null) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/delivery/";
        }

        DeliveryMethodEntity resultEntity = deliveryMethodGetService.execute(deliveryHolidayModel.getDmcd());

        // 不正操作チェック。配送マスタは物理削除されないので、結果がないのはURLパラメータをいじられた以外ありえない。
        if (resultEntity == null) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/delivery/";
        }

        deliveryHolidayHelper.convertToRegistPageForResult(deliveryHolidayModel, resultEntity);

        // 設定がない場合、今年を初期選択値として設定
        Calendar cal = Calendar.getInstance();
        if (deliveryHolidayModel.getYear() == null) {
            deliveryHolidayModel.setYear(cal.get(Calendar.YEAR));
        }

        // ページング項目初期化
        deliveryHolidayModel.setPageNumber(DEFAULT_PNUM);
        deliveryHolidayModel.setLimit(DEFAULT_LIMIT);

        // 検索を実行
        search(deliveryHolidayModel, model);

        return "delivery/holiday/index";
    }

    /**
     * 検索
     *
     * @param deliveryHolidayModel
     * @param model
     * @return
     */
    @PostMapping(value = "/", params = "doSearch")
    @HEHandler(exception = AppLevelListException.class, returnView = "delivery/holiday/index")
    public String doSearch(@Validated(SearchGroup.class) DeliveryHolidayModel deliveryHolidayModel,
                           BindingResult error,
                           Model model) {

        if (error.hasErrors()) {
            return "delivery/holiday/index";
        }

        // ページング項目初期化
        deliveryHolidayModel.setPageNumber(DEFAULT_PNUM);
        deliveryHolidayModel.setLimit(DEFAULT_LIMIT);

        search(deliveryHolidayModel, model);
        return "delivery/holiday/index";
    }

    /**
     * 全件CSV出力
     *
     * @param deliveryHolidayModel
     * @param error
     * @return
     */
    @PostMapping(value = "/", params = "doCsvDownload")
    @HEHandler(exception = AppLevelListException.class, returnView = "delivery/holiday/index")
    @HEHandler(exception = FileDownloadException.class, returnView = "delivery/holiday/index")
    public void doCsvDownload(@Validated(AllDownloadGroup.class) DeliveryHolidayModel deliveryHolidayModel,
                              BindingResult error,
                              HttpServletResponse response,
                              Model model) {

        if (error.hasErrors()) {
            throw new FileDownloadException(model);
        }

        // 検索条件作成
        HolidaySearchForDaoConditionDto conditionDto = deliveryHolidayHelper.toConditionDto(deliveryHolidayModel);

        try {
            deliveryHolidayAllCsvDownloadService.execute(conditionDto, response);
        } catch (IOException e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new FileDownloadException(model);
        }

    }

    /**
     * 休日登録
     *
     * @param deliveryHolidayModel
     * @param error
     * @param redirectAttributes
     * @param model
     * @return
     * @throws ParseException
     */
    @PostMapping(value = "/", params = "doRegistUpdate")
    @HEHandler(exception = AppLevelListException.class, returnView = "delivery/holiday/index")
    public String doRegistUpdate(@Validated(RegistUpdateGroup.class) DeliveryHolidayModel deliveryHolidayModel,
                                 BindingResult error,
                                 RedirectAttributes redirectAttributes,
                                 Model model) throws ParseException {

        if (error.hasErrors()) {
            return "delivery/holiday/index";
        }

        // Pageの値をエンティティへコピー
        HolidayEntity holidayEntity = deliveryHolidayHelper.toHolidayEntityForRegistUpdate(deliveryHolidayModel);

        // システム日付を取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
        Date now = dateUtility.getCurrentDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);

        // 入力した年が今年または翌月でない場合
        if (holidayEntity.getYear() != calendar.get(Calendar.YEAR)
            && holidayEntity.getYear() != calendar.get(Calendar.YEAR) + 1) {
            // エラーメッセージを設定し、更新処理は行わない
            throwMessage("AYH000101", new Object[] {"年月日"});
        }

        // 登録更新結果
        int result = 0;
        // 登録更新処理を実行
        result = holidayRegistLogic.execute(holidayEntity);

        if (result > 0) {
            // 登録した年を検索条件として設定しておく
            deliveryHolidayModel.setYear(holidayEntity.getYear());
            // 再検索を実行
            this.search(deliveryHolidayModel, model);
            // 成功メッセージを設定
            addInfoMessage("AYH000102", null, redirectAttributes, model);
        } else {
            // エラーメッセージを設定
            addMessage("AYH000103", redirectAttributes, model);
        }

        return "redirect:/delivery/holiday/?dmcdParam=" + deliveryHolidayModel.getDmcd() + "&md=doRegistUpdate";
    }

    /**
     * 休日削除
     *
     * @param deliveryHolidayModel
     * @param redirectAttributes
     * @param model
     * @return
     * @throws ParseException
     */
    @PostMapping(value = "/", params = "doDelete")
    @HEHandler(exception = AppLevelListException.class, returnView = "delivery/holiday/index")
    public String doDelete(DeliveryHolidayModel deliveryHolidayModel,
                           RedirectAttributes redirectAttributes,
                           Model model) throws ParseException {

        // 削除対象休日取得
        Date deleteDate = DateUtils.parseDate(deliveryHolidayModel.getDeleteDate(), new String[] {"yyyy/MM/dd"});
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(deleteDate);
        HolidayEntity holidayEntity = holidayGetByYearAndDateLogic.execute(calendar.get(Calendar.YEAR), deleteDate,
                                                                           deliveryHolidayModel.getDmcd()
                                                                          );

        // 削除結果
        int result = 0;
        if (holidayEntity != null) {
            // 削除処理を実行
            result = holidayDao.delete(holidayEntity);
        }

        // 再検索を実行
        this.search(deliveryHolidayModel, model);
        if (result > 0) {
            // 成功メッセージを設定
            addInfoMessage("AYH000104", null, redirectAttributes, model);
        } else {
            // エラーメッセージを設定
            addInfoMessage("AYH000105", null, redirectAttributes, model);
        }

        return "redirect:/delivery/holiday/?yearParam=" + deliveryHolidayModel.getYear() + "&dmcdParam="
               + deliveryHolidayModel.getDmcd();
    }

    /**
     * ソート
     *
     * @param deliveryHolidayModel
     * @param model
     * @return
     */
    @PostMapping(value = "/", params = "doDisplayChange")
    @HEHandler(exception = AppLevelListException.class, returnView = "delivery/holiday/index")
    public String doDisplayChange(DeliveryHolidayModel deliveryHolidayModel, Model model) {
        search(deliveryHolidayModel, model);
        return "delivery/holiday/index";
    }

    /**
     * アップロード画面へ遷移<br/>
     *
     * @param deliveryHolidayModel
     * @param model
     * @return class
     */
    @PostMapping(value = "/", params = "doUpload")
    public String doUpload(@Validated(UploadGroup.class) DeliveryHolidayModel deliveryHolidayModel,
                           BindingResult error,
                           RedirectAttributes redirectAttrs,
                           Model model) {

        if (error.hasErrors()) {
            return "delivery/holiday/index";
        }

        // アプリケーションが変わるため、選択年、配送方法SEQをリダイレクトスコープで引き渡す
        String yearHoliday =
                        deliveryHolidayModel.getYear() != null ? String.valueOf(deliveryHolidayModel.getYear()) : "";
        String dmcdHoliday =
                        deliveryHolidayModel.getDmcd() != null ? String.valueOf(deliveryHolidayModel.getDmcd()) : "";

        return "redirect:/delivery/holiday/bundledupload/?yearParam=" + yearHoliday + "&dmcdParam=" + dmcdHoliday;
    }

}
