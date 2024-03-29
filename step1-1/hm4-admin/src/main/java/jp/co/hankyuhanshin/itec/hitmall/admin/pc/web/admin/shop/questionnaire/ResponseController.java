/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.questionnaire;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.AllDownloadGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.questionnaire.QuestionnaireReplyCsvSearchDto;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.questionnaire.QuestionnaireAllCsvDownloadService;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.exception.FileDownloadException;
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
import java.util.Optional;

/**
 * アンケート回答出力画面アクションクラス。
 *
 * @author matsuda
 */
@RequestMapping("/questionnaire/response")
@Controller
@SessionAttributes(value = "responseModel")
@PreAuthorize("hasAnyAuthority('SHOP:4')")
public class ResponseController extends AbstractController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseController.class);

    /**
     * アンケート回答出力画面Helperクラス
     */
    private final ResponseHelper responseHelper;

    private final QuestionnaireAllCsvDownloadService questionnaireAllCsvDownloadService;

    /**
     * コンストラクター
     *
     * @param responseHelper
     * @param questionnaireAllCsvDownloadService
     */
    @Autowired
    public ResponseController(ResponseHelper responseHelper,
                              QuestionnaireAllCsvDownloadService questionnaireAllCsvDownloadService) {
        this.responseHelper = responseHelper;
        this.questionnaireAllCsvDownloadService = questionnaireAllCsvDownloadService;
    }

    /**
     * 画像表示処理<br/>
     * 初期表示用メソッド<br/>
     *
     * @param responseModel
     * @param error
     * @param redirectAttrs
     * @param model
     * @return
     */
    @GetMapping(value = "/")
    public String doLoadIndex(@RequestParam(required = false) Optional<String> questionnaireSeq,
                              ResponseModel responseModel,
                              BindingResult error,
                              RedirectAttributes redirectAttrs,
                              Model model) {
        clearModel(ResponseModel.class, responseModel, model);

        if (questionnaireSeq.isPresent()) {
            responseModel.setSearchQuestionnaireSeq(questionnaireSeq.get());
        }
        initDropDownValue(responseModel);
        return "questionnaire/response";
    }

    /**
     * CSV全件出力
     *
     * @return 自画面
     */
    @PreAuthorize("hasAnyAuthority('SHOP:8')")
    @PostMapping(value = "/")
    @HEHandler(exception = AppLevelListException.class, returnView = "questionnaire/response")
    @HEHandler(exception = FileDownloadException.class, returnView = "questionnaire/response")
    public void doAllDownload(@Validated(AllDownloadGroup.class) ResponseModel responseModel,
                              BindingResult error,
                              HttpServletResponse response,
                              Model model) {

        if (error.hasErrors()) {
            throw new FileDownloadException(model);
        }

        // 検索条件作成
        QuestionnaireReplyCsvSearchDto conditionDto =
                        responseHelper.toQuestionnaireReplySearchForBackDto(responseModel);

        /* 各種全件出力処理 */
        try {
            this.questionnaireAllCsvDownloadService.execute(conditionDto, response);
        } catch (IOException e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new FileDownloadException(model);
        }

    }

    /**
     * プルダウンアイテム情報を取得
     *
     * @param responseModel 商品検索モデル
     */
    protected void initDropDownValue(ResponseModel responseModel) {

        // プルダウンアイテム情報を取得
        responseModel.setSearchOpenStatusItems(EnumTypeUtil.getEnumMap(HTypeOpenStatus.class));
        responseModel.setSearchDeviceTypeArrayItems(EnumTypeUtil.getEnumMap(HTypeDeviceType.class));
    }
}
