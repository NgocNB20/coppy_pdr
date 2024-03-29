/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.category.bundledupload;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUploadType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryCsvUploadResult;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryCsvUpLoadService;
import jp.co.hankyuhanshin.itec.hitmall.utility.CsvUtility;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvUploadError;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 商品管理 カテゴリ一括アップロード<br/>
 *
 * @author kamei
 */
// 2023-renew categoryCSV from here
@RequestMapping("/goods/category/bundledupload")
@Controller
@SessionAttributes(value = "categoryBundledUploadModel")
@PreAuthorize("hasAnyAuthority('GOODS:8')")
public class CategoryBundledUploadController extends AbstractController {

    /**
     * ロガー<br/>
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryBundledUploadController.class);

    /**
     * カテゴリCSV一括アップロードHelper<br/>
     */
    private final CategoryBundledUploadHelper categoryBundledUploadHelper;

    /**
     * カテゴリーCSV一括アップロードサービス<br/>
     */
    private final CategoryCsvUpLoadService categoryCsvUpLoadService;

    /**
     * コンストラクタ
     *
     *   @param categoryBundledUploadHelper
     *   @param categoryCsvUpLoadService
     */
    @Autowired
    public CategoryBundledUploadController(CategoryBundledUploadHelper categoryBundledUploadHelper,
                                           CategoryCsvUpLoadService categoryCsvUpLoadService) {
        this.categoryBundledUploadHelper = categoryBundledUploadHelper;
        this.categoryCsvUpLoadService = categoryCsvUpLoadService;
    }

    /**
     * アップロード<br/>
     *
     * @param categoryBundledUploadModel
     * @param error
     * @param redirectAttributes
     * @param model
     * @return 遷移先ページ
     */
    @PostMapping(value = "/csv/", params = "doOnceUpload")
    @HEHandler(exception = AppLevelListException.class,
               returnView = "redirect:/goods/category/bundledupload/csvUploadComplete/")
    public String doOnceUpload(@Validated CategoryBundledUploadModel categoryBundledUploadModel,
                               BindingResult error,
                               RedirectAttributes redirectAttributes,
                               Model model) {

        if (error.hasErrors()) {
            return "goods/category/bundledupload/csvUpload";
        }

        // モデルのエラー表示項目を初期化
        initFlag(categoryBundledUploadModel);
        categoryBundledUploadModel.setErrorResultItems(new ArrayList<>());

        // CSVHelper取得
        CsvUtility csvUtility = ApplicationContextUtility.getBean(CsvUtility.class);

        // ファイル操作Helper取得
        FileOperationUtility fileOperationUtility = ApplicationContextUtility.getBean(FileOperationUtility.class);

        // アップロードファイルをテンプファイルとして保存
        String tmpFileName = csvUtility.getUploadCsvTmpFileName("category");
        try {
            if (!fileOperationUtility.put(categoryBundledUploadModel.getUploadCsvFile(), tmpFileName)) {
                addMessage(CategoryBundledUploadModel.MSGCD_FAIL_DELETE, redirectAttributes, model);
                return "goods/category/bundledupload/csvUpload";
            }
        } catch (IOException e) {
            LOGGER.error("例外処理が発生しました", e);
            addMessage(CategoryBundledUploadModel.MSGCD_FAIL_DELETE, redirectAttributes, model);
            return "goods/category/bundledupload/csvUpload";
        }
        // ファイルを作成
        File upLoadFile = new File(tmpFileName);

        // モード
        boolean registFlg = false;
        if (HTypeUploadType.REGIST.getValue().equals(categoryBundledUploadModel.getUploadType())) {
            registFlg = true;
        }

        // アップロードサービス実行
        CategoryCsvUploadResult csvUploadResult = (CategoryCsvUploadResult) categoryCsvUpLoadService.execute(upLoadFile,
                                                                                                             CsvUploadResult.CSV_UPLOAD_VALID_LIMIT,
                                                                                                             registFlg
                                                                                                            );

        // 処理結果HelperでPageへ反映
        categoryBundledUploadHelper.toPageForCsvUploadResultDto(categoryBundledUploadModel, csvUploadResult);

        // 通知メッセージありの場合
        for (CsvUploadError info : csvUploadResult.getInfoMessageList()) {
            List<Object> argList = new ArrayList<>();
            argList.add(info.getRow());
            argList.addAll(Arrays.asList(info.getArgs()));
            this.addInfoMessage(info.getMessageCode(), argList.toArray(), redirectAttributes, model);
        }

        // バリデータ・DBエラーがある場合 遷移先ページをセットして例外スロー
        if (csvUploadResult.isInValid() || csvUploadResult.isError()) {
            throwMessage();
        }
        // 正常終了時
        return "redirect:/goods/category/bundledupload/csvUploadComplete/";
    }

    /**
     * 完了画面からカテゴリ一覧画面へ戻る<br/>
     *
     * @param sessionStatus
     * @return 遷移元画面
     */
    @PostMapping(value = "/csvUploadComplete/", params = "doReturn")
    public String doReturn(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "redirect:/goods/category/bundledupload/csv/";
    }

    /**
     * 初期表示<br/>
     *
     * @param categoryBundledUploadModel
     * @param redirectAttributes
     * @param model
     * @return カテゴリー一括アップロード画面
     */
    @GetMapping(value = "/csv/")
    protected String doLoadIndex(CategoryBundledUploadModel categoryBundledUploadModel,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {

        // アップロードモードのデフォルト値を設定
        categoryBundledUploadModel.setUploadType("0");

        return "goods/category/bundledupload/csvUpload";
    }

    /**
     * 結果画面ロード<br/>
     *
     * @return 完了画面
     */
    @GetMapping(value = "/csvUploadComplete/")
    protected String doLoadComplete(CategoryBundledUploadModel categoryBundledUploadModel,
                                    RedirectAttributes redirectAttributes,
                                    Model model) {

        // ブラウザバック時はカテゴリCSV登録画面へ遷移
        if (categoryBundledUploadModel.getUploadCsvFile() == null) {
            return "redirect:/goods/category/bundledupload/csv/";
        }

        return "goods/category/bundledupload/csvUploadComplete";
    }

    /**
     * フラグ初期化<br/>
     *
     * @param categoryBundledUploadModel
     */
    protected void initFlag(CategoryBundledUploadModel categoryBundledUploadModel) {
        categoryBundledUploadModel.setValidLimitFlg(false);
    }
}
// 2023-renew categoryCSV to here
