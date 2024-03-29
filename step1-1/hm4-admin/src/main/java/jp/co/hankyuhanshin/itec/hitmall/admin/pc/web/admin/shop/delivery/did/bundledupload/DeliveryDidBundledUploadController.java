/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.delivery.did.bundledupload;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.DeliveryImpossibleDayCsvUpLoadService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.DeliveryMethodGetService;
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
@RequestMapping("/delivery/did/bundledupload")
@Controller
@SessionAttributes(value = "deliveryDidBundledUploadModel")
@PreAuthorize("hasAnyAuthority('SETTING:8')")
public class DeliveryDidBundledUploadController extends AbstractController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryDidBundledUploadController.class);

    /**
     * メッセージコード：不正操作
     */
    public static final String MSGCD_ILLEGAL_OPERATION = "AYD000601";

    /**
     * 一括アップロードHelper
     */
    private DeliveryDidBundledUploadHelper deliveryDidBundledUploadHelper;

    /**
     * CSVアップロードサービス
     */
    private DeliveryImpossibleDayCsvUpLoadService deliveryImpossibleDayCsvUpLoadService;

    /**
     * 配送方法取得サービス
     */
    private DeliveryMethodGetService deliveryMethodGetService;

    /**
     * コンストラクタ
     *
     * @param deliveryDidBundledUploadHelper
     * @param deliveryImpossibleDayCsvUpLoadService
     * @param deliveryMethodGetService
     */
    @Autowired
    public DeliveryDidBundledUploadController(DeliveryDidBundledUploadHelper deliveryDidBundledUploadHelper,
                                              DeliveryImpossibleDayCsvUpLoadService deliveryImpossibleDayCsvUpLoadService,
                                              DeliveryMethodGetService deliveryMethodGetService) {
        this.deliveryDidBundledUploadHelper = deliveryDidBundledUploadHelper;
        this.deliveryImpossibleDayCsvUpLoadService = deliveryImpossibleDayCsvUpLoadService;
        this.deliveryMethodGetService = deliveryMethodGetService;
    }

    /**
     * 画像表示処理<br/>
     * 初期表示用メソッド<br/>
     *
     * @param deliveryDidBundledUploadModel
     * @param redirectAttributes
     * @param model
     * @return 自画面
     */
    @GetMapping(value = "/")
    @HEHandler(exception = AppLevelListException.class, returnView = "delivery/did/bundledupload/upload")
    protected String doLoadIndex(@RequestParam(required = false) Optional<String> yearParam,
                                 @RequestParam(required = false) Optional<String> dmcdParam,
                                 DeliveryDidBundledUploadModel deliveryDidBundledUploadModel,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {

        boolean isNotChangeOfDmcdParam =
                        dmcdParam.isPresent() && Objects.equals(deliveryDidBundledUploadModel.getDmcd(),
                                                                Integer.parseInt(dmcdParam.get())
                                                               );
        boolean isNotChangeOfYearParam =
                        yearParam.isPresent() && Objects.equals(deliveryDidBundledUploadModel.getYear(),
                                                                Integer.parseInt(yearParam.get())
                                                               );

        if (isNotChangeOfDmcdParam && isNotChangeOfYearParam) {
            return "delivery/did/bundledupload/upload";
        }

        // モデルのクリア処理
        clearModel(DeliveryDidBundledUploadModel.class, deliveryDidBundledUploadModel, model);

        yearParam.ifPresent(item -> deliveryDidBundledUploadModel.setRedirectYear(Integer.parseInt(item)));
        dmcdParam.ifPresent(item -> deliveryDidBundledUploadModel.setRedirectDmcd(Integer.parseInt(item)));

        // 年、配送方法SEQが設定されている場合、引き継ぎ用の年にセット
        if (deliveryDidBundledUploadModel.getRedirectYear() != null
            && deliveryDidBundledUploadModel.getRedirectDmcd() != null) {
            deliveryDidBundledUploadModel.setYear(deliveryDidBundledUploadModel.getRedirectYear());
            deliveryDidBundledUploadModel.setDmcd(deliveryDidBundledUploadModel.getRedirectDmcd());
        }

        Integer year = deliveryDidBundledUploadModel.getYear();
        Integer dcmd = deliveryDidBundledUploadModel.getDmcd();

        // 年または配送方法SEQが存在しない場合、エラー画面へ遷移
        if (year == null || dcmd == null) {
            return "redirect:/error";
        }

        DeliveryMethodEntity resultEntity = deliveryMethodGetService.execute(deliveryDidBundledUploadModel.getDmcd());

        // 不正操作チェック。配送マスタは物理削除されないので、結果がないのはURLパラメータをいじられた以外ありえない。
        if (resultEntity == null) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/delivery/";

        } else {
            deliveryDidBundledUploadHelper.convertToRegistPageForResult(deliveryDidBundledUploadModel, resultEntity);
        }

        return "delivery/did/bundledupload/upload";
    }

    /**
     * アップロード<br/>
     *
     * @param deliveryDidBundledUploadModel
     * @return class
     */
    @PostMapping(value = "/", params = "doOnceUpload")
    @HEHandler(exception = AppLevelListException.class,
               returnView = "redirect:/delivery/did/bundledupload/uploadfinish/")
    public String doOnceUpload(@Validated DeliveryDidBundledUploadModel deliveryDidBundledUploadModel,
                               BindingResult error,
                               RedirectAttributes redirectAttributes,
                               Model model) {

        if (error.hasErrors()) {
            return "delivery/did/bundledupload/upload";
        }

        // CSVHelper取得
        CsvUtility csvUtility = ApplicationContextUtility.getBean(CsvUtility.class);

        // ファイル操作Helper取得
        FileOperationUtility fileOperationUtility = ApplicationContextUtility.getBean(FileOperationUtility.class);

        Integer year = deliveryDidBundledUploadModel.getYear();
        Integer dcmd = deliveryDidBundledUploadModel.getDmcd();
        if (year == null || dcmd == null) {
            throwMessage();
        }

        // アップロードファイルをテンプファイルとして保存
        String tmpFileName = csvUtility.getUploadCsvTmpFileName("did");

        try {
            fileOperationUtility.put(deliveryDidBundledUploadModel.getUploadFile(), tmpFileName);
        } catch (IOException e) {
            LOGGER.error("例外処理が発生しました", e);
            addMessage(DeliveryDidBundledUploadModel.MSGCD_FAIL_DELETE, redirectAttributes, model);
            return "redirect:/delivery/did/bundledupload/";
        }

        // ファイルを作成
        File upLoadFile = new File(tmpFileName);

        // アップロードサービス実行
        CsvUploadResult csvUploadResult = deliveryImpossibleDayCsvUpLoadService.execute(upLoadFile,
                                                                                        CsvUploadResult.CSV_UPLOAD_VALID_LIMIT,
                                                                                        year, dcmd
                                                                                       );

        // 処理結果DxoでPageへ反映
        deliveryDidBundledUploadHelper.toPageForCsvUploadResultDto(deliveryDidBundledUploadModel, csvUploadResult);

        // バリデータ・DBエラーがある場合 遷移先ページをセットして例外スロー
        if (csvUploadResult.isInValid() || csvUploadResult.isError()) {
            // 遷移先ページの指定 rollback
            throwMessage();
        }

        // 正常終了時
        return "redirect:/delivery/did/bundledupload/uploadfinish/";
    }

    /**
     * 戻る
     *
     * @param deliveryDidBundledUploadModel
     * @return class
     */
    @PostMapping(value = "/", params = "doReturn")
    public String doReturn(DeliveryDidBundledUploadModel deliveryDidBundledUploadModel,
                           RedirectAttributes redirectAttrs) {
        // アプリケーションが変わるため、選択年、配送方法SEQをリダイレクトスコープで引き渡す
        String yearDid = deliveryDidBundledUploadModel.getYear() != null ?
                        String.valueOf(deliveryDidBundledUploadModel.getYear()) :
                        "";
        String dmcdDid = deliveryDidBundledUploadModel.getDmcd() != null ?
                        String.valueOf(deliveryDidBundledUploadModel.getDmcd()) :
                        "";

        return "redirect:/delivery/did/?yearParam=" + yearDid + "&dmcdParam=" + dmcdDid;
    }

    /**
     * 一括アップロード完了画面表示
     *
     * @param deliveryDidBundledUploadModel
     * @param redirectAttributes
     * @param model
     * @return
     */
    @GetMapping(value = "/uploadfinish/")
    protected String doLoadFinish(DeliveryDidBundledUploadModel deliveryDidBundledUploadModel) {

        // ブラウザバックの場合、処理しない
        if (deliveryDidBundledUploadModel.getYear() == null || deliveryDidBundledUploadModel.getDmcd() == null) {
            return "redirect:/delivery/did/bundledupload/";
        }

        return "delivery/did/bundledupload/uploadfinish";
    }

    /**
     * インデックス画面へ戻る<br/>
     *
     * @param deliveryDidBundledUploadModel
     * @param redirectAttributes
     * @return class
     */
    @PostMapping(value = "/uploadfinish/", params = "doFinish")
    public String doFinish(DeliveryDidBundledUploadModel deliveryDidBundledUploadModel,
                           RedirectAttributes redirectAttributes) {
        // アプリケーションが変わるため、選択年、配送方法SEQをリダイレクトスコープで引き渡す
        String yearDid = deliveryDidBundledUploadModel.getYear() != null ?
                        String.valueOf(deliveryDidBundledUploadModel.getYear()) :
                        "";
        String dmcdDid = deliveryDidBundledUploadModel.getDmcd() != null ?
                        String.valueOf(deliveryDidBundledUploadModel.getDmcd()) :
                        "";

        return "redirect:/delivery/did/?yearParam=" + yearDid + "&dmcdParam=" + dmcdDid;
    }

}
