/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.questionnaire;

import jp.co.hankyuhanshin.itec.hitmall.dto.graph.PieGraphDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.questionnaire.QuestionDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionReplyTotalEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionnaireEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionnaireReplyTotalEntity;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * アンケート詳細画面Helperクラス。
 *
 * @author matsuda
 */
@Component
public class QuestionnaireDetailHelper {
    /**
     * 　変換Utilityクラス
     */
    private final ConversionUtility conversionUtility;

    @Autowired
    public QuestionnaireDetailHelper(ConversionUtility conversionUtility) {
        this.conversionUtility = conversionUtility;
    }

    /**
     * 取得結果を詳細画面に反映
     * 再検索用
     *
     * @param questionnaireEntity      アンケートエンティティ
     * @param questionnaireDetailModel 詳細ページ
     */
    public void toPageForLoadQuestionaire(QuestionnaireEntity questionnaireEntity,
                                          QuestionnaireDetailModel questionnaireDetailModel) {
        /*
         * 画面項目（基本共通設定）
         */
        /** アンケートキー */
        questionnaireDetailModel.setQuestionnaireKey(questionnaireEntity.getQuestionnaireKey());

        /** アンケートSEQ */
        questionnaireDetailModel.setQuestionnaireSeq(questionnaireEntity.getQuestionnaireSeq());

        /**登録日時 */
        questionnaireDetailModel.setRegistTime(questionnaireEntity.getRegistTime());

        /**更新日時 */
        questionnaireDetailModel.setUpdateTime(questionnaireEntity.getUpdateTime());

        /**アンケート名称 */
        questionnaireDetailModel.setName(questionnaireEntity.getName());

        /** アンケート公開開始時間*/
        questionnaireDetailModel.setOpenStartTime(questionnaireEntity.getOpenStartTime());

        /** アンケート公開終了時間 */
        questionnaireDetailModel.setOpenEndTime(questionnaireEntity.getOpenEndTime());

        // PDR Migrate Customization from here
        /**サイトマップ出力 */
        questionnaireDetailModel.setSiteMapFlag(questionnaireEntity.getSiteMapFlag().getValue());
        // PDR Migrate Customization to here

        /** 管理用メモ */
        questionnaireDetailModel.setMemo(questionnaireEntity.getMemo());

        /*
         * 画面項目（基本サイト別設定）
         */

        /** アンケート表示名PC */
        questionnaireDetailModel.setNamePc(questionnaireEntity.getNamePc());

        /**アンケート公開状態PC */
        questionnaireDetailModel.setOpenStatusPc(questionnaireEntity.getOpenStatusPc());

        /**アンケート説明文PC */
        questionnaireDetailModel.setFreeTextPc(questionnaireEntity.getFreeTextPc());

        /**アンケート回答完了文PC */
        questionnaireDetailModel.setCompleteTextPc(questionnaireEntity.getCompleteTextPc());

    }

    /**
     * 取得結果を詳細画面に反映
     * 再検索用
     *
     * @param questionDetailsDtoList   アンケート設問詳細画面用Dtoリスト
     * @param questionnaireDetailModel 詳細ページ
     */
    public void toPageForLoadQuestion(List<QuestionDetailsDto> questionDetailsDtoList,
                                      QuestionnaireDetailModel questionnaireDetailModel) {
        List<QuestionnaireDetailModelItem> resultList = new ArrayList<>();
        List<PieGraphDto> pieGraphItems = new ArrayList<>();

        /* アンケート設問情報を画面に反映する */
        for (QuestionDetailsDto questionDetailsDto : questionDetailsDtoList) {

            QuestionnaireDetailModelItem detailsPageItem =
                            ApplicationContextUtility.getBean(QuestionnaireDetailModelItem.class);
            QuestionEntity questionEntity = questionDetailsDto.getQuestionEntity();
            /*
             * 画面項目（設問設定一覧）データ項目
             */
            /** 設問設定一覧データ：表示順 */
            detailsPageItem.setOrderDisplay(questionEntity.getOrderDisplay());

            /** 設問設定一覧データ：設問内容 */
            detailsPageItem.setQuestion(questionEntity.getQuestion());

            /** 設問設定一覧データ：公開状態 */
            detailsPageItem.setOpenStatus(questionEntity.getOpenStatus());

            /** 設問設定一覧データ：必須 */
            detailsPageItem.setReplyRequiredFlag(questionEntity.getReplyRequiredFlag());

            /** 設問設定一覧データ：形式 */
            detailsPageItem.setReplyType(questionEntity.getReplyType());

            /** 設問設定一覧データ：文字種 */
            detailsPageItem.setReplyValidatePattern(questionEntity.getReplyValidatePattern());

            /** 設問設定一覧データ：桁数 */
            detailsPageItem.setReplyMaxLength(questionEntity.getReplyMaxLength());

            /** 設問設定一覧データ：選択肢 */
            detailsPageItem.setChoices(questionEntity.getChoices());

            /** 設問設定一覧データ：選択肢(画面表示用) */
            detailsPageItem.setChoicesDispItems(conversionUtility.toDivArray(questionEntity.getChoices()));

            resultList.add(detailsPageItem);

            // 選択形式の設問の場合、回答集計の円グラフ情報を作成
            if (questionEntity.getReplyType().isSelectType()) {
                pieGraphItems.add(createPieGraphItem(questionDetailsDto));
            }
        }
        // 設問設定 リストのセット
        questionnaireDetailModel.setQuestionnaireDetailModelItems(resultList);

        // アンケート設問回答の集計グラフを2列表示にする為、Itemsをネストさせる
        List<List<PieGraphDto>> pieGraphItemsItems = new ArrayList<>();
        List<PieGraphDto> tmpItems = null;
        for (int i = 0; i < pieGraphItems.size(); i++) {
            if (i % 2 == 0) {
                tmpItems = new ArrayList<>();
                pieGraphItemsItems.add(tmpItems);
            }
            tmpItems = pieGraphItemsItems.get(pieGraphItemsItems.size() - 1);
            tmpItems.add(pieGraphItems.get(i));
        }
        questionnaireDetailModel.setPieGraphItemsItems(pieGraphItemsItems);
    }

    /**
     * 回答集計の円グラフ情報を作成
     *
     * @param questionDetailsDto アンケート情報
     * @return 円グラフ情報
     */
    protected PieGraphDto createPieGraphItem(QuestionDetailsDto questionDetailsDto) {
        PieGraphDto pieGraphDto = ApplicationContextUtility.getBean(PieGraphDto.class);
        QuestionEntity questionEntity = questionDetailsDto.getQuestionEntity();
        pieGraphDto.setGraphTitle(questionEntity.getQuestion());
        // 選択肢の順にグラフを表示する為、選択肢順にListに格納する
        String[] choices = conversionUtility.toDivArray(questionEntity.getChoices());
        for (String choice : choices) {
            for (QuestionReplyTotalEntity questionReplyTotalEntity : questionDetailsDto.getQuestionReplyTotalEntityList()) {
                if (choice.equals(questionReplyTotalEntity.getReplyChoice())) {
                    pieGraphDto.addGraphData(
                                    questionReplyTotalEntity.getReplyChoice(),
                                    questionReplyTotalEntity.getReplyChoiceCount()
                                            );
                }
            }
        }
        pieGraphDto.convertGraphDataJson();
        return pieGraphDto;
    }

    /**
     * アンケート回答集計情報を画面に反映する
     *
     * @param questionnaireReplyTotalEntity アンケート回答集計エンティティ
     * @param questionnaireDetailModel      詳細ページ
     */
    public void toPageForLoadQuestionnaireReplyTotal(QuestionnaireReplyTotalEntity questionnaireReplyTotalEntity,
                                                     QuestionnaireDetailModel questionnaireDetailModel) {
        if (questionnaireReplyTotalEntity != null) {
            /** 回答数 */
            questionnaireDetailModel.setReplyCount(questionnaireReplyTotalEntity.getReplyCount());

            /** 集計日時 */
            questionnaireDetailModel.setTotalRegistTime(questionnaireReplyTotalEntity.getRegistTime());
        }
    }
}
