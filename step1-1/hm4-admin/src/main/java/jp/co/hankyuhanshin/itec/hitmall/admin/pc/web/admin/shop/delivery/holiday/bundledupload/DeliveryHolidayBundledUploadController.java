/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.delivery.holiday.bundledupload;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.DeliveryMethodGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.HolidayCsvUpLoadService;
import jp.co.hankyuhanshin.itec.hitmall.utility.CsvUtility;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvUploadResult;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.FileOperationUtility;
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

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

/**
 * 一括アップロード<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("/delivery/holiday/bundledupload")
@Controller
@SessionAttributes(value = "deliveryHolidayBundledUploadModel")
@PreAuthorize("hasAnyAuthority('SETTING:8')")
public class DeliveryHolidayBundledUploadController extends AbstractController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryHolidayBundledUploadController.class);

    /**
     * メッセージコード：不正操作
     */
    public static final String MSGCD_ILLEGAL_OPERATION = "AYD000601";

    /**
     * 一括アップロードHelper
     */
    private final DeliveryHolidayBundledUploadHelper deliveryHolidayBundledUploadHelper;

    /**
     * CSVアップロードサービス
     */
    private final HolidayCsvUpLoadService holidayCsvUpLoadService;

    /**
     * 配送方法取得サービス
     */
    private final DeliveryMethodGetService deliveryMethodGetService;

    /**
     * コンストラクタ
     *
     * @param deliveryHolidayBundledUploadHelper
     * @param holidayCsvUpLoadService
     * @param deliveryMethodGetService
     */
    @Autowired
    public DeliveryHolidayBundledUploadController(DeliveryHolidayBundledUploadHelper deliveryHolidayBundledUploadHelper,
                                                  HolidayCsvUpLoadService holidayCsvUpLoadService,
                                                  DeliveryMethodGetService deliveryMethodGetService) {
        this.deliveryHolidayBundledUploadHelper = deliveryHolidayBundledUploadHelper;
        this.holidayCsvUpLoadService = holidayCsvUpLoadService;
        this.deliveryMethodGetService = deliveryMethodGetService;
    }

    /**
     * 画像表示処理<br/>
     * 初期表示用メソッド<br/>
     *
     * @param deliveryHolidayBundledUploadModel
     * @param redirectAttributes
     * @param model
     * @return 自画面
     */
    @GetMapping(value = "/")
    @HEHandler(exception = AppLevelListException.class, returnView = "delivery/holiday/bundledupload/upload")
    protected String doLoadIndex(@RequestParam(required = false) Optional<String> yearParam,
                                 @RequestParam(required = false) Optional<String> dmcdParam,
                                 DeliveryHolidayBundledUploadModel deliveryHolidayBundledUploadModel,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {

        boolean isNotChangeOfDmcdParam =
                        dmcdParam.isPresent() && Objects.equals(deliveryHolidayBundledUploadModel.getDmcd(),
                                                                Integer.parseInt(dmcdParam.get())
                                                               );
        boolean isNotChangeOfYearParam =
                        yearParam.isPresent() && Objects.equals(deliveryHolidayBundledUploadModel.getYear(),
                                                                Integer.parseInt(yearParam.get())
                                                               );

        if (isNotChangeOfDmcdParam && isNotChangeOfYearParam) {
            return "delivery/holiday/bundledupload/upload";
        }

        // モデルのクリア処理
        clearModel(DeliveryHolidayBundledUploadModel.class, deliveryHolidayBundledUploadModel, model);

        yearParam.ifPresent(item -> deliveryHolidayBundledUploadModel.setRedirectYear(Integer.parseInt(item)));
        dmcdParam.ifPresent(item -> deliveryHolidayBundledUploadModel.setRedirectDmcd(Integer.parseInt(item)));

        // 年、配送方法SEQが設定されている場合、引き継ぎ用の年にセット
        if (deliveryHolidayBundledUploadModel.getRedirectYear() != null
            && deliveryHolidayBundledUploadModel.getRedirectDmcd() != null) {
            deliveryHolidayBundledUploadModel.setYear(deliveryHolidayBundledUploadModel.getRedirectYear());
            deliveryHolidayBundledUploadModel.setDmcd(deliveryHolidayBundledUploadModel.getRedirectDmcd());
        }

        Integer year = deliveryHolidayBundledUploadModel.getYear();
        Integer dcmd = deliveryHolidayBundledUploadModel.getDmcd();

        // 年または配送方法SEQが存在しない場合、エラー画面へ遷移
        if (year == null || dcmd == null) {
            return "redirect:/error";
        }

        DeliveryMethodEntity resultEntity =
                        deliveryMethodGetService.execute(deliveryHolidayBundledUploadModel.getDmcd());

        // 不正操作チェック。配送マスタは物理削除されないので、結果がないのはURLパラメータをいじられた以外ありえない。
        if (resultEntity == null) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/delivery/";

        } else {
            deliveryHolidayBundledUploadHelper.convertToRegistPageForResult(
                            deliveryHolidayBundledUploadModel, resultEntity);
        }

        return "delivery/holiday/bundledupload/upload";
    }

    /**
     * アップロード<br/>
     *
     * @param deliveryHolidayBundledUploadModel
     * @return class
     */
    @PostMapping(value = "/", params = "doOnceUpload")
    @HEHandler(exception = AppLevelListException.class,
               returnView = "redirect:/delivery/holiday/bundledupload/uploadfinish/")
    public String doOnceUpload(@Validated DeliveryHolidayBundledUploadModel deliveryHolidayBundledUploadModel,
                               BindingResult error,
                               RedirectAttributes redirectAttributes,
                               Model model) {

        if (error.hasErrors()) {
            return "delivery/holiday/bundledupload/upload";
        }

        // CSVHelper取得
        CsvUtility csvUtility = ApplicationContextUtility.getBean(CsvUtility.class);

        // ファイル操作Helper取得
        FileOperationUtility fileOperationUtility = ApplicationContextUtility.getBean(FileOperationUtility.class);

        Integer year = deliveryHolidayBundledUploadModel.getYear();
        Integer dcmd = deliveryHolidayBundledUploadModel.getDmcd();
        if (year == null || dcmd == null) {
            throwMessage();
        }

        // アップロードファイルをテンプファイルとして保存
        String tmpFileName = csvUtility.getUploadCsvTmpFileName("holiday");

        try {
            fileOperationUtility.put(deliveryHolidayBundledUploadModel.getUploadFile(), tmpFileName);
        } catch (IOException e) {
            LOGGER.error("例外処理が発生しました", e);
            addMessage(DeliveryHolidayBundledUploadModel.MSGCD_FAIL_DELETE, redirectAttributes, model);
            return "redirect:/delivery/holiday/bundledupload/";
        }

        // ファイルを作成
        File upLoadFile = new File(tmpFileName);

        // アップロードサービス実行
        CsvUploadResult csvUploadResult =
                        holidayCsvUpLoadService.execute(upLoadFile, CsvUploadResult.CSV_UPLOAD_VALID_LIMIT, year, dcmd);

        // 処理結果DxoでPageへ反映
        deliveryHolidayBundledUploadHelper.toPageForCsvUploadResultDto(
                        deliveryHolidayBundledUploadModel, csvUploadResult);

        // バリデータ・DBエラーがある場合 遷移先ページをセットして例外スロー
        if (csvUploadResult.isInValid() || csvUploadResult.isError()) {
            // 遷移先ページの指定 rollback
            throwMessage();
        }

        // 正常終了時
        return "redirect:/delivery/holiday/bundledupload/uploadfinish/";
    }

    /**
     * 戻る
     *
     * @param deliveryHolidayBundledUploadModel
     * @param redirectAttrs
     * @return class
     */
    @PostMapping(value = "/", params = "doReturn")
    public String doReturn(DeliveryHolidayBundledUploadModel deliveryHolidayBundledUploadModel,
                           RedirectAttributes redirectAttrs) {
        // アプリケーションが変わるため、選択年、配送方法SEQをリダイレクトスコープで引き渡す
        String yearHoliday = deliveryHolidayBundledUploadModel.getYear() != null ?
                        String.valueOf(deliveryHolidayBundledUploadModel.getYear()) :
                        "";
        String dmcdHoliday = deliveryHolidayBundledUploadModel.getDmcd() != null ?
                        String.valueOf(deliveryHolidayBundledUploadModel.getDmcd()) :
                        "";

        return "redirect:/delivery/holiday/?yearParam=" + yearHoliday + "&dmcdParam=" + dmcdHoliday;
    }

    /**
     * 一括アップロード完了画面表示
     *
     * @param deliveryHolidayBundledUploadModel
     * @param redirectAttributes
     * @param model
     * @return
     */
    @GetMapping(value = "/uploadfinish/")
    protected String doLoadFinish(DeliveryHolidayBundledUploadModel deliveryHolidayBundledUploadModel) {

        // ブラウザバックの場合、処理しない
        if (deliveryHolidayBundledUploadModel.getYear() == null
            || deliveryHolidayBundledUploadModel.getDmcd() == null) {
            return "redirect:/delivery/holiday/bundledupload/";
        }

        return "delivery/holiday/bundledupload/uploadfinish";
    }

    /**
     * インデックス画面へ戻る<br/>
     *
     * @param deliveryHolidayBundledUploadModel
     * @return class
     */
    @PostMapping(value = "/uploadfinish/", params = "doFinish")
    public String doFinish(DeliveryHolidayBundledUploadModel deliveryHolidayBundledUploadModel,
                           RedirectAttributes redirectAttributes) {
        // アプリケーションが変わるため、選択年、配送方法SEQをリダイレクトスコープで引き渡す
        String yearHoliday = deliveryHolidayBundledUploadModel.getYear() != null ?
                        String.valueOf(deliveryHolidayBundledUploadModel.getYear()) :
                        "";
        String dmcdHoliday = deliveryHolidayBundledUploadModel.getDmcd() != null ?
                        String.valueOf(deliveryHolidayBundledUploadModel.getDmcd()) :
                        "";

        return "redirect:/delivery/holiday/?yearParam=" + yearHoliday + "&dmcdParam=" + dmcdHoliday;
    }

}
