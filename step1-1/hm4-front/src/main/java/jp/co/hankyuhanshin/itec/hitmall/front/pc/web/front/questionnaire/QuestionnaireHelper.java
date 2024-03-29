/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.questionnaire;

import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.QuestionEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.QuestionReplyEntityRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.QuestionnaireEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.QuestionnaireReplyDisplayDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.QuestionnaireReplyEntityRequest;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReplyRequiredFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReplyType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReplyValidatePattern;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSiteMapFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.shop.questionnaire.QuestionnaireReplyDisplayDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.questionnaire.QuestionEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.questionnaire.QuestionReplyEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.questionnaire.QuestionnaireEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.questionnaire.QuestionnaireReplyEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * アンケートHelper
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class QuestionnaireHelper {

    /**
     * 改行コード CRLF
     */
    protected static final String CRLF = "\r\n";

    /**
     * 改行コード LF
     */
    protected static final String LF = "\n";

    private final ConversionUtility conversionutility;

    /**
     * コンストラクタ
     */
    @Autowired
    public QuestionnaireHelper(ConversionUtility conversionutility) {
        this.conversionutility = conversionutility;
    }

    /**
     * Modelへの変換<br/>
     * 受注Dto ⇒ 注文Model<br/>
     *
     * @param questionnaireEntity アンケートエンティティクラス
     * @param questionDtoList     アンケート設問リスト
     * @param questionnaireModel  アンケートModel
     */
    public void toPageForLoad(QuestionnaireEntity questionnaireEntity,
                              List<QuestionnaireReplyDisplayDto> questionDtoList,
                              QuestionnaireModel questionnaireModel) {

        // アンケートエンティティ情報をページにセット
        questionnaireModel.setQuestionnaireEntity(questionnaireEntity);

        /**アンケートSEQ */
        questionnaireModel.setQuestionnaireSeq(questionnaireEntity.getQuestionnaireSeq());

        /** アンケート表示名PC */
        questionnaireModel.setNamePc(questionnaireEntity.getNamePc());

        /**アンケート説明文PC */
        questionnaireModel.setFreeTextPc(questionnaireEntity.getFreeTextPc());

        /**アンケート回答完了文PC */
        questionnaireModel.setCompleteTextPc(questionnaireEntity.getCompleteTextPc());

        // 取得したアンケート回答画面表示用DTOリストをセット
        questionnaireModel.setQuestionnaireReplyDisplayDtoItems(questionDtoList);

    }

    /**
     * Modelへの変換<br/>
     *
     * @param questionnaireModel アンケートModel
     */
    public void toPageForReLoad(QuestionnaireModel questionnaireModel) {

        // アンケートエンティティ情報をページにセット
        QuestionnaireEntity questionnaireEntity = questionnaireModel.getQuestionnaireEntity();

        /**アンケートSEQ */
        questionnaireModel.setQuestionnaireSeq(questionnaireEntity.getQuestionnaireSeq());

        /** アンケート表示名PC */
        questionnaireModel.setNamePc(questionnaireEntity.getNamePc());

        /**アンケート説明文PC */
        questionnaireModel.setFreeTextPc(questionnaireEntity.getFreeTextPc());

        /**アンケート回答完了文PC */
        questionnaireModel.setCompleteTextPc(questionnaireEntity.getCompleteTextPc());

    }

    /**
     * アンケート取得結果をページに反映<br/>
     *
     * @param questionnaireModel アンケートModel
     */
    public void toPageForConfirm(QuestionnaireModel questionnaireModel) {

        // ページの値をアンケートエンティティ情報にセット
        QuestionnaireEntity questionnaireEntity = questionnaireModel.getQuestionnaireEntity();

        /**アンケートSEQ */
        questionnaireEntity.setQuestionnaireSeq(questionnaireModel.getQuestionnaireSeq());

        /** アンケート表示名PC */
        questionnaireEntity.setNamePc(questionnaireModel.getNamePc());

        /**アンケート説明文PC */
        questionnaireEntity.setFreeTextPc(questionnaireModel.getFreeTextPc());

        /**アンケート回答完了文PC */
        questionnaireEntity.setCompleteTextPc(questionnaireModel.getCompleteTextPc());

        if (questionnaireModel.getQuestionnaireReplyDisplayDtoItems() != null) {
            for (QuestionnaireReplyDisplayDto questionnaireReplyDisplayDto : questionnaireModel.getQuestionnaireReplyDisplayDtoItems()) {
                if (HTypeReplyType.TEXTAREA.equals(questionnaireReplyDisplayDto.getReplyType())) {
                    questionnaireReplyDisplayDto.setQuestionTextAreaDispItems(
                                    StringUtils.isNotEmpty(questionnaireReplyDisplayDto.getQuestionTextArea())
                                    && !questionnaireReplyDisplayDto.getQuestionTextArea().isEmpty() ?
                                                    questionnaireReplyDisplayDto.getQuestionTextArea().split(CRLF) :
                                                    null);
                }
            }
        }
    }

    /**
     * 登録するアンケート回答情報の作成<br/>
     *
     * @param questionnaireModel アンケートModel
     * @return アンケート回答情報
     */
    public QuestionnaireReplyEntity toQuestionnaireReplyEntityForRegist(QuestionnaireModel questionnaireModel) {

        // アンケート回答情報の作成
        QuestionnaireReplyEntity questionnaireReplyEntity =
                        ApplicationContextUtility.getBean(QuestionnaireReplyEntity.class);

        /** アンケートSEQ (FK)*/
        questionnaireReplyEntity.setQuestionnaireSeq(questionnaireModel.getQuestionnaireSeq());

        /** サイト種別*/
        questionnaireReplyEntity.setSiteType(questionnaireModel.getCommonInfo().getCommonInfoBase().getSiteType());

        /** デバイス種別*/
        questionnaireReplyEntity.setDeviceType(questionnaireModel.getCommonInfo().getCommonInfoBase().getDeviceType());

        /** 会員SEQ*/
        if (questionnaireModel.getCommonInfo().getCommonInfoUser().getMemberInfoSeq() != null) {
            // 本会員の場合
            questionnaireReplyEntity.setMemberInfoSeq(
                            questionnaireModel.getCommonInfo().getCommonInfoUser().getMemberInfoSeq());
        } else {
            // ゲストの場合
            questionnaireReplyEntity.setMemberInfoSeq(0);
        }

        /** 受注コード*/
        questionnaireReplyEntity.setOrderCode(null);

        questionnaireReplyEntity.setQuestionnaireKey(questionnaireModel.getQuestionnaireEntity().getQuestionnaireKey());

        return questionnaireReplyEntity;
    }

    /**
     * 登録するアンケート設問回答情報の作成<br/>
     *
     * @param questionnaireModel アンケートModel
     * @return アンケート設問回答情報
     */
    public List<QuestionReplyEntity> toQuestionReplyEntityForRegist(QuestionnaireModel questionnaireModel) {

        // アンケート回答情報リストの作成
        List<QuestionReplyEntity> questionReplyEntityList = new ArrayList<>();

        for (QuestionnaireReplyDisplayDto questionDto : questionnaireModel.getQuestionnaireReplyDisplayDtoItems()) {

            QuestionReplyEntity questionReplyEntity = ApplicationContextUtility.getBean(QuestionReplyEntity.class);

            /** アンケート設問SEQ (FK)*/
            questionReplyEntity.setQuestionSeq(questionDto.getQuestionSeq());

            /** 回答内容*/
            HTypeReplyType type = questionDto.getReplyType();
            if (HTypeReplyType.TEXTBOX.equals(type)) {
                // テキストボックスの回答内容をセット
                questionReplyEntity.setReply(questionDto.getQuestionTextBox());

            } else if (HTypeReplyType.TEXTAREA.equals(type)) {
                // テキストエリアの回答内容をセット
                questionReplyEntity.setReply(questionDto.getQuestionTextArea());

            } else if (HTypeReplyType.RADIOBUTTON.equals(type)) {
                // ラジオボタン・プルダウンの回答内容をセット
                questionReplyEntity.setReply(questionDto.getQuestionRadio());

            } else if (HTypeReplyType.PULLDOWN.equals(type)) {
                // ラジオボタン・プルダウンの回答内容をセット
                questionReplyEntity.setReply(questionDto.getQuestionPullDown());

            } else if (HTypeReplyType.CHECKBOX.equals(type)) {
                // チェックボックスの回答内容をセット
                // チェックボックスを複数選択している場合、配列を改行区切りの文字列に変換
                String[] array = questionDto.getQuestionCheckBox();
                StringBuilder builder = new StringBuilder();

                if (ObjectUtils.isNotEmpty(array) && array.length > 0) {
                    for (String questionSelectCheckStr : array) {

                        builder.append(questionSelectCheckStr).append(LF);
                    }

                    String result = builder.substring(0, builder.length() - 1);
                    questionReplyEntity.setReply(result);
                }
            }

            // 回答内容を回答情報リストに追加
            questionReplyEntityList.add(questionReplyEntity);
        }

        return questionReplyEntityList;

    }

    /**
     * アンケートEntityに変換
     *
     * @param questionnaireEntityResponse アンケートEntityレスポンス
     * @return アンケートEntity
     */
    public QuestionnaireEntity toQuestionnaireEntity(QuestionnaireEntityResponse questionnaireEntityResponse) {
        if (questionnaireEntityResponse == null) {
            return null;
        }
        QuestionnaireEntity entity = new QuestionnaireEntity();
        entity.setQuestionnaireSeq(questionnaireEntityResponse.getQuestionnaireSeq());
        entity.setShopSeq(1001);
        entity.setQuestionnaireKey(questionnaireEntityResponse.getQuestionnaireKey());
        entity.setName(questionnaireEntityResponse.getName());
        entity.setNamePc(questionnaireEntityResponse.getName());
        entity.setOpenStatusPc(EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class,
                                                             questionnaireEntityResponse.getOpenStatus()
                                                            ));
        entity.setOpenStartTime(conversionutility.toTimeStamp(questionnaireEntityResponse.getOpenStartTime()));
        entity.setOpenEndTime(conversionutility.toTimeStamp(questionnaireEntityResponse.getOpenEndTime()));
        entity.setFreeTextPc(questionnaireEntityResponse.getFreeText());
        entity.setCompleteTextPc(questionnaireEntityResponse.getCompleteText());
        entity.setMemo(questionnaireEntityResponse.getMemo());
        entity.setVersionNo(questionnaireEntityResponse.getVersionNo());
        entity.setRegistTime(conversionutility.toTimeStamp(questionnaireEntityResponse.getRegistTime()));
        entity.setUpdateTime(conversionutility.toTimeStamp(questionnaireEntityResponse.getUpdateTime()));
        entity.setSiteMapFlag(EnumTypeUtil.getEnumFromValue(HTypeSiteMapFlag.class,
                                                            questionnaireEntityResponse.getSiteMapFlag()
                                                           ));
        return entity;
    }

    /**
     * アンケート回答画面表示用DtoListに変換
     *
     * @param questionnaireReplyDisplayDtoResponseList アンケート回答画面表示用DtoListレスポンス
     * @return アンケート回答画面表示用DtoList
     */
    public List<QuestionnaireReplyDisplayDto> toQuestionnaireReplyDisplayDtoList(List<QuestionnaireReplyDisplayDtoResponse> questionnaireReplyDisplayDtoResponseList)
                    throws InvocationTargetException, IllegalAccessException {
        if (questionnaireReplyDisplayDtoResponseList == null) {
            return new ArrayList<>();
        }
        List<QuestionnaireReplyDisplayDto> questionnaireReplyDisplayDtoList = new ArrayList<>();
        for (int i = 0; i < questionnaireReplyDisplayDtoResponseList.size(); i++) {
            questionnaireReplyDisplayDtoList.add(
                            toQuestionnaireReplyDisplayDto(questionnaireReplyDisplayDtoResponseList.get(i)));
        }
        return questionnaireReplyDisplayDtoList;
    }

    /**
     * アンケート回答Entityリクエストに変換
     *
     * @param questionnaireReplyEntity アンケート回答Entity
     * @return アンケート回答Entityリクエスト
     */
    public QuestionnaireReplyEntityRequest toQuestionnaireReplyEntityRequest(QuestionnaireReplyEntity questionnaireReplyEntity) {
        if (questionnaireReplyEntity == null) {
            return null;
        }
        QuestionnaireReplyEntityRequest questionnaireReplyEntityRequest = new QuestionnaireReplyEntityRequest();
        questionnaireReplyEntityRequest.setQuestionnaireReplySeq(questionnaireReplyEntity.getQuestionnaireReplySeq());
        questionnaireReplyEntityRequest.setQuestionnaireSeq(questionnaireReplyEntity.getQuestionnaireSeq());
        if (questionnaireReplyEntity.getSiteType() != null) {
            questionnaireReplyEntityRequest.setSiteType(questionnaireReplyEntity.getSiteType().getValue());
        }
        if (questionnaireReplyEntity.getDeviceType() != null) {
            questionnaireReplyEntityRequest.setDeviceType(questionnaireReplyEntity.getDeviceType().getValue());
        }
        questionnaireReplyEntityRequest.setMemberInfoSeq(questionnaireReplyEntity.getMemberInfoSeq());
        questionnaireReplyEntityRequest.setOrderCode(questionnaireReplyEntity.getOrderCode());
        questionnaireReplyEntityRequest.setRegistTime(questionnaireReplyEntity.getRegistTime());
        questionnaireReplyEntityRequest.setQuestionnaireKey(questionnaireReplyEntity.getQuestionnaireKey());

        return questionnaireReplyEntityRequest;
    }

    /**
     * 質問返信EntityListリクエストに変換
     *
     * @param questionReplyEntityList 質問返信EntityList
     * @return 質問返信EntityListリクエスト
     */
    public List<QuestionReplyEntityRequest> toQuestionReplyEntityListRequest(List<QuestionReplyEntity> questionReplyEntityList) {
        if (questionReplyEntityList == null) {
            return new ArrayList<>();
        }
        List<QuestionReplyEntityRequest> questionReplyEntityRequestList = new ArrayList<>();
        for (int i = 0; i < questionReplyEntityList.size(); i++) {
            QuestionReplyEntityRequest questionReplyEntityRequest = new QuestionReplyEntityRequest();
            questionReplyEntityRequest.setReply(questionReplyEntityList.get(i).getReply());
            questionReplyEntityRequest.setQuestionnaireReplySeq(
                            questionReplyEntityList.get(i).getQuestionnaireReplySeq());
            questionReplyEntityRequest.setQuestionSeq(questionReplyEntityList.get(i).getQuestionSeq());
            questionReplyEntityRequest.setRegistTime(questionReplyEntityList.get(i).getRegistTime());
            questionReplyEntityRequestList.add(questionReplyEntityRequest);
        }
        return questionReplyEntityRequestList;

    }

    /**
     * アンケート回答画面表示用DTOに変換
     *
     * @param questionnaireReplyDisplayDtoResponse アンケート回答画面表示用レスポンス
     * @return アンケート回答画面表示用DTO
     */
    public QuestionnaireReplyDisplayDto toQuestionnaireReplyDisplayDto(QuestionnaireReplyDisplayDtoResponse questionnaireReplyDisplayDtoResponse) {
        if (questionnaireReplyDisplayDtoResponse == null)
            return null;

        QuestionnaireReplyDisplayDto questionnaireReplyDisplayDto = new QuestionnaireReplyDisplayDto();
        questionnaireReplyDisplayDto.setDisplayNumber(questionnaireReplyDisplayDtoResponse.getDisplayNumber());
        questionnaireReplyDisplayDto.setQuestion(questionnaireReplyDisplayDtoResponse.getQuestion());
        questionnaireReplyDisplayDto.setQuestionCheckBox(
                        questionnaireReplyDisplayDtoResponse.getQuestionCheckBox() != null ?
                                        questionnaireReplyDisplayDtoResponse.getQuestionCheckBox()
                                                                            .toArray(new String[0]) :
                                        null);
        questionnaireReplyDisplayDto.setQuestionCheckBoxDisp(
                        questionnaireReplyDisplayDtoResponse.getQuestionCheckBoxDisp());
        questionnaireReplyDisplayDto.setQuestionCheckBoxDispItems(
                        questionnaireReplyDisplayDtoResponse.getQuestionCheckBoxDispItems() != null ?
                                        questionnaireReplyDisplayDtoResponse.getQuestionCheckBoxDispItems()
                                                                            .toArray(new String[0]) :
                                        null);
        questionnaireReplyDisplayDto.setQuestionCheckBoxItems(
                        questionnaireReplyDisplayDtoResponse.getQuestionCheckBoxItems());
        questionnaireReplyDisplayDto.setQuestionEntity(
                        toQuestionEntity(questionnaireReplyDisplayDtoResponse.getQuestionEntity()));
        questionnaireReplyDisplayDto.setQuestionPullDown(questionnaireReplyDisplayDtoResponse.getQuestionPullDown());
        questionnaireReplyDisplayDto.setQuestionPullDownDisp(
                        questionnaireReplyDisplayDtoResponse.getQuestionPullDownDisp());
        questionnaireReplyDisplayDto.setQuestionPullDownItems(
                        questionnaireReplyDisplayDtoResponse.getQuestionPullDownItems());
        questionnaireReplyDisplayDto.setQuestionRadio(questionnaireReplyDisplayDtoResponse.getQuestionRadio());
        questionnaireReplyDisplayDto.setQuestionRadioItems(
                        questionnaireReplyDisplayDtoResponse.getQuestionRadioItems());
        questionnaireReplyDisplayDto.setQuestionRadioDisp(questionnaireReplyDisplayDtoResponse.getQuestionRadioDisp());
        questionnaireReplyDisplayDto.setQuestionSeq(questionnaireReplyDisplayDtoResponse.getQuestionSeq());
        questionnaireReplyDisplayDto.setQuestionTextArea(questionnaireReplyDisplayDtoResponse.getQuestionTextArea());
        questionnaireReplyDisplayDto.setQuestionTextBox(questionnaireReplyDisplayDtoResponse.getQuestionTextBox());
        questionnaireReplyDisplayDto.setReplyMaxLength(questionnaireReplyDisplayDtoResponse.getReplyMaxLength());
        questionnaireReplyDisplayDto.setReplyRequiredFlag(EnumTypeUtil.getEnumFromValue(HTypeReplyRequiredFlag.class,
                                                                                        questionnaireReplyDisplayDtoResponse
                                                                                                        .getReplyRequiredFlag()
                                                                                       ));
        questionnaireReplyDisplayDto.setReplyType(EnumTypeUtil.getEnumFromValue(HTypeReplyType.class,
                                                                                questionnaireReplyDisplayDtoResponse.getReplyType()
                                                                               ));
        questionnaireReplyDisplayDto.setReplyValidatePattern(
                        EnumTypeUtil.getEnumFromValue(HTypeReplyValidatePattern.class,
                                                      questionnaireReplyDisplayDtoResponse.getReplyValidatePattern()
                                                     ));

        return questionnaireReplyDisplayDto;
    }

    /**
     * アンケート設問エンティティに変換
     *
     * @param questionEntityResponse アンケート回答画面表示用レスポンス
     * @return アンケート設問エンティティクラ変換
     */
    public QuestionEntity toQuestionEntity(QuestionEntityResponse questionEntityResponse) {
        if (questionEntityResponse == null)
            return null;
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setChoices(questionEntityResponse.getChoices());
        questionEntity.setOpenStatus(EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class,
                                                                   questionEntityResponse.getOpenStatus().toString()
                                                                  ));
        questionEntity.setOrderDisplay(questionEntityResponse.getOrderDisplay());
        questionEntity.setQuestion(questionEntityResponse.getQuestion());
        questionEntity.setQuestionSeq(questionEntityResponse.getQuestionSeq());
        questionEntity.setQuestionnaireSeq(questionEntityResponse.getQuestionnaireSeq());
        questionEntity.setReplyMaxLength(questionEntityResponse.getReplyMaxLength());
        questionEntity.setRegistTime(conversionutility.toTimeStamp(questionEntityResponse.getRegistTime()));
        questionEntity.setReplyType(EnumTypeUtil.getEnumFromValue(HTypeReplyType.class,
                                                                  questionEntityResponse.getReplyType().toString()
                                                                 ));
        questionEntity.setReplyRequiredFlag(EnumTypeUtil.getEnumFromValue(HTypeReplyRequiredFlag.class,
                                                                          questionEntityResponse.getReplyRequiredFlag()
                                                                                                .toString()
                                                                         ));
        questionEntity.setReplyValidatePattern(EnumTypeUtil.getEnumFromValue(HTypeReplyValidatePattern.class,
                                                                             questionEntityResponse.getReplyValidatePattern()
                                                                                                   .toString()
                                                                            ));
        questionEntity.setUpdateTime(conversionutility.toTimeStamp(questionEntityResponse.getUpdateTime()));
        questionEntity.setVersionNo(questionEntity.getVersionNo());

        return questionEntity;
    }
}
