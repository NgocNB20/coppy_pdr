package jp.co.hankyuhanshin.itec.hitmall.batch.offline.memberinfoupdate.tasklet;

import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageUtil;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.AbstractBatch;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAccountingType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeApproveStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBatchName;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBusinessType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCardRegistType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCashDeliveryUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeConfDocumentType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCoopId;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCreditPaymentUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDentalMonopolySalesType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDirectDebitUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDrugSalesType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMedicalEquipmentSalesType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberListType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMetalPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMonthlyPayUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOnlineLoginAdvisability;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOnlineRegistFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePasswordNeedChangeFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendDirectMailFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendFaxPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendMailPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeTransferPaymentUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.member.WebApiGetUserInformationRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.member.WebApiGetUserInformationResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.member.WebApiGetUserInformationResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.coop.CoopDateHistoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.coop.CoopDateHistoryGetUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetUserInformationLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.mail.MailSendService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoRegistService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoUpdateService;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelException;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.mail.InstantMailSetting;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.env.Environment;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

// PDR Migrate Customization from here

/**
 * 会員情報更新バッチ
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
public class MemberInfoUpdateBatch extends AbstractBatch {

    /**
     * Logger
     */
    private final Logger LOGGER = LoggerFactory.getLogger(MemberInfoUpdateBatch.class);

    /**
     * 処理結果正常終了メッセージ
     */
    private static final String UPDATE_RESULT_SUCCESS = "正常終了";

    /**
     * 処理結果異常終了メッセージ
     */
    private static final String UPDATE_RESULT_FAILED = "異常終了";

    /**
     * 処理結果更新失敗メッセージ
     */
    private static final String UPDATE_RESULT_ERROR = "【要対応】エラーあり";

    /**
     * 必須メッセージ
     */
    private static final String ISEMPTY_MSG = "は必須項目です。";

    /**
     * 桁数オーバーメッセージ
     */
    private static final String MAXLENGTH_MSG = "の桁数が不正です。";

    /**
     * 項目不正メッセージ
     */
    private static final String ITEM_ILLEGAL_MSG = "が不正です。";

    /**
     * 登録件数
     */
    private int registCount = 0;

    /**
     * 更新件数
     */
    private int updateCount = 0;

    /**
     * エラー項目件数
     */
    private int faildeItemCount = 0;

    /**
     * 処理結果
     */
    private String batchProcessResult;

    /**
     * 処理結果メール詳細メッセージ
     */
    private String mailMessageDetail;

    /**
     * 基幹連携日時履歴取得・更新ロジック
     */
    private final CoopDateHistoryGetUpdateLogic coopDateHistoryGetUpdateLogic;

    /**
     * 会員変更情報取得WEB-API連携ロジック
     */
    private final WebApiGetUserInformationLogic webApiGetUserInformationLogic;

    /**
     * 会員情報取得ロジック
     */
    private final MemberInfoGetLogic memberInfoGetLogic;

    /**
     * 会員情報登録サービス
     */
    private final MemberInfoRegistService memberInfoRegistService;

    /**
     * 会員情報更新サービス
     */
    private final MemberInfoUpdateService memberInfoUpdateService;

    /**
     * エラーチェックリスト
     */
    private List<String> failedList = new ArrayList<>();

    /**
     * dicon 設定： メール送信設定
     */
    private final InstantMailSetting mailSetting;

    /**
     * メール送信サービス
     */
    private final MailSendService mailSendService;

    /**
     * バッチ終了メッセージ共通処理
     */
    private final BatchExitMessageUtil exitMessageUtil;

    public MemberInfoUpdateBatch(Environment environment) {
        this.mailSendService = ApplicationContextUtility.getBean(MailSendService.class);
        this.coopDateHistoryGetUpdateLogic = ApplicationContextUtility.getBean(CoopDateHistoryGetUpdateLogic.class);
        this.webApiGetUserInformationLogic = ApplicationContextUtility.getBean(WebApiGetUserInformationLogic.class);
        this.exitMessageUtil = new BatchExitMessageUtil();
        this.memberInfoGetLogic = ApplicationContextUtility.getBean(MemberInfoGetLogic.class);
        this.memberInfoRegistService = ApplicationContextUtility.getBean(MemberInfoRegistService.class);
        this.memberInfoUpdateService = ApplicationContextUtility.getBean(MemberInfoUpdateService.class);

        this.mailSetting = new InstantMailSetting(environment.getProperty("mail.setting.memberinfo.update.smtp.server"),
                                                  environment.getProperty("mail.setting.memberinfo.update.mail.from"),
                                                  null, null, Collections.singletonList(
                        environment.getProperty("mail.setting.memberinfo.update.mail.receivers"))
        );
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        // コンテキスト取得
        ExecutionContext executionContext =
                        chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext();

        // バッチのジョッブ情報取得
        JobParameters jobParameters = chunkContext.getStepContext().getStepExecution().getJobParameters();
        String administratorId = jobParameters.getString("administratorId");
        String shopSeq = jobParameters.getString("shopSeq");
        if (!org.apache.commons.lang3.StringUtils.isEmpty(shopSeq)) {
            super.setShopSeq(Integer.valueOf(shopSeq));
        }

        /** 処理中顧客番号保持 */
        int checkCustomerNo = 0;

        try {

            LOGGER.debug("前回実行日時の取得処理を開始します。");

            // 前回実行日時取得(取得失敗でエラー)
            CoopDateHistoryEntity coopDateHistoryEntity =
                            coopDateHistoryGetUpdateLogic.execute(HTypeCoopId.MEMBER_CHGINFO_GET_COOP.getValue());

            // 前回実行日時
            if (coopDateHistoryEntity == null) {
                throw new RuntimeException("前回実行日時の取得に失敗しました。");
            }
            Timestamp lastCoopDate = coopDateHistoryEntity.getLastCoopDate();

            // 前回実行日時取得処理終了
            LOGGER.debug("前回実行日時の取得処理が正常終了しました。");

            // 日付関連Helper取得
            DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

            // 現在日時を取得
            Timestamp currentTime = dateUtility.getCurrentTime();

            // 会員変更情報取得処理開始
            LOGGER.debug("会員変更情報取得WEB-APIの実行を開始します。");

            // 会員変更情報リクエストDto
            WebApiGetUserInformationRequestDto reqDto =
                            ApplicationContextUtility.getBean(WebApiGetUserInformationRequestDto.class);
            // 会員変更情報取得API用引数をセット
            reqDto.setDesignationDate(lastCoopDate);
            // 会員変更情報取得WEB-API実行
            WebApiGetUserInformationResponseDto resDto =
                            (WebApiGetUserInformationResponseDto) webApiGetUserInformationLogic.execute(reqDto);

            // 連携失敗
            if (resDto == null) {
                throw new RuntimeException("会員変更情報取得連携に失敗しました。");
            }
            // 取得結果が不正なら処理終了
            if (!resDto.getResult().getStatus().equals("0")) {
                throw new RuntimeException("会員変更情報の取得に失敗しました。 ステータスコード：" + resDto.getResult().getStatus());
            }

            // 会員変更情報取得処理終了
            LOGGER.debug("会員変更情報取得WEB-APIの実行が正常終了しました。");

            if (resDto.getInfo() != null) {
                // 会員情報更新ループ処理開始
                LOGGER.debug("会員情報更新ループ処理の実行を開始します。");
                LOGGER.debug("処理対象件数：" + resDto.getInfo().size());
                // 会員情報更新
                for (WebApiGetUserInformationResponseDetailDto userInformationDetailDto : resDto.getInfo()) {

                    // 処理中顧客番号を保持する
                    checkCustomerNo = userInformationDetailDto.getMemberNo();

                    // 会員情報取得
                    MemberInfoEntity memberInfoEntity = memberInfoGetLogic.getMemberInfoEntityByCustomerNo(
                                    userInformationDetailDto.getMemberNo());

                    // 会員情報が取得できなければ登録
                    if (memberInfoEntity == null) {
                        // 会員情報登録
                        LOGGER.debug("会員情報を登録します。");
                        LOGGER.debug("顧客番号：" + userInformationDetailDto.getMemberNo());
                        // 会員情報エンティティ作成
                        MemberInfoEntity memberInfoRegistEntity =
                                        ApplicationContextUtility.getBean(MemberInfoEntity.class);

                        // 入力チェック
                        if (checkInputItem(userInformationDetailDto)) {
                            faildeItemCount++;
                            continue;
                        }

                        // 各項目をセット
                        // オンライン登録フラグを確認し、対象項目の値を判定
                        if (HTypeOnlineRegistFlag.ON.getValue()
                                                    .equals(String.valueOf(
                                                                    userInformationDetailDto.getOnlineRegistFlag()))) {
                            // 会員状態
                            memberInfoRegistEntity.setMemberInfoStatus(HTypeMemberInfoStatus.ADMISSION);
                            // パスワード変更要求フラグ
                            memberInfoRegistEntity.setPasswordNeedChangeFlag(HTypePasswordNeedChangeFlag.OFF);
                            // 承認状態
                            memberInfoRegistEntity.setApproveStatus(HTypeApproveStatus.ON);
                        } else {
                            memberInfoRegistEntity.setMemberInfoStatus(HTypeMemberInfoStatus.REMOVE);
                            memberInfoRegistEntity.setPasswordNeedChangeFlag(HTypePasswordNeedChangeFlag.ON);
                            memberInfoRegistEntity.setApproveStatus(HTypeApproveStatus.OFF);
                        }
                        // 決済代行会社カード保持種別
                        memberInfoRegistEntity.setPaymentCardRegistType(HTypeCardRegistType.UNREGISTERED);
                        // Web-APIにて取得した会員情報で各項目を上書き
                        setMemberInfo(memberInfoRegistEntity, userInformationDetailDto);

                        try {
                            // 会員情報を登録
                            /*---------- #216 start----------*/
                            memberInfoRegistService.execute(memberInfoRegistEntity, false, true, null);
                        } catch (Exception error) {
                            // 登録失敗
                            // エラーになった対象の受注番号をセット
                            LOGGER.warn("【顧客番号:" + checkCustomerNo + "】更新に失敗しました。連携項目を確認してください。", error);
                            failedList.add("【顧客番号:" + checkCustomerNo + "】更新に失敗しました。連携項目を確認してください。");
                            faildeItemCount++;
                            continue;
                        }
                        /*---------- #216 end----------*/
                        // 登録件数インクリメント
                        registCount++;
                    } else {
                        // 会員情報更新
                        LOGGER.debug("会員情報を更新します。");
                        LOGGER.debug("顧客番号：" + userInformationDetailDto.getMemberNo());

                        // 入力チェック
                        if (checkInputItem(userInformationDetailDto)) {
                            faildeItemCount++;
                            continue;
                        }

                        // 会員情報が取得できれば更新
                        // Web-APIにて取得した会員情報で各項目を上書き
                        setMemberInfo(memberInfoEntity, userInformationDetailDto);

                        try {
                            // 会員情報を更新
                            memberInfoUpdateService.execute(memberInfoEntity);
                        } catch (Exception error) {
                            // 更新失敗
                            // エラーになった対象の受注番号をセット
                            LOGGER.warn("【顧客番号:" + checkCustomerNo + "】更新に失敗しました。連携項目を確認してください。", error);
                            failedList.add("【顧客番号:" + checkCustomerNo + "】更新に失敗しました。連携項目を確認してください。");
                            faildeItemCount++;
                            continue;
                        }
                        updateCount++;
                    }
                }
                // 会員情報更新ループ処理終了
                LOGGER.debug("会員情報更新ループ処理の実行が正常終了しました。");
            }

            // 前回実行日時更新処理開始
            LOGGER.debug("前回実行日時の更新処理を開始します。");
            // 前回実行日時更新
            coopDateHistoryEntity.setLastCoopDate(currentTime);
            coopDateHistoryGetUpdateLogic.execute(coopDateHistoryEntity);
            // 前回実行日時更新処理終了
            LOGGER.debug("前回実行日時の更新処理が正常終了しました。");

            if (failedList.isEmpty()) {
                // 処理結果成功メール送信
                batchProcessResult = UPDATE_RESULT_SUCCESS;
            } else {
                // 処理結果更新失敗メール送信
                batchProcessResult = UPDATE_RESULT_ERROR;
            }

            // 処理結果詳細生成
            createSuccessMailDetail();
            // メール送信メソッド
            sendAdministratorMail(mailMessageDetail);

            // バッチ処理結果レポート登録
            this.report(mailMessageDetail);

        } catch (AppLevelListException appe) {
            // 処理結果エラーメール送信
            batchProcessResult = UPDATE_RESULT_FAILED;
            // エラーとなった対象の顧客番号をログ出力
            if (checkCustomerNo != 0) {
                LOGGER.error("エラー対象顧客番号：" + checkCustomerNo);
            }
            // エラーリスト内のメッセージをログ出力する
            for (AppLevelException e : appe.getErrorList()) {
                LOGGER.error(e.getMessage(), e);
            }
            // 処理結果詳細生成
            createFailedMailDetail(false, appe.getClass().getName());

            // メール送信メソッド
            sendAdministratorMail(mailMessageDetail);

            executionContext.put(
                            BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                                            new BatchExitMessageDto(String.valueOf(faildeItemCount),
                                                                    this.getReportString().toString()
                                            )));
            throw appe;
        } catch (Exception error) {
            LOGGER.error("例外処理が発生しました", error);

            // 処理結果エラーメール送信
            batchProcessResult = UPDATE_RESULT_FAILED;
            // エラーとなった対象の顧客番号をログ出力
            if (checkCustomerNo != 0) {
                LOGGER.error("エラー対象顧客番号：" + checkCustomerNo);
            }
            // 処理結果詳細生成
            createFailedMailDetail(false, error.getClass().getName());

            // メール送信メソッド
            sendAdministratorMail(mailMessageDetail);

            executionContext.put(
                            BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                                            new BatchExitMessageDto(String.valueOf(faildeItemCount),
                                                                    this.getReportString().toString()
                                            )));
            throw error;
        }

        executionContext.put(BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                        new BatchExitMessageDto(String.valueOf(faildeItemCount), this.getReportString().toString())));
        return RepeatStatus.FINISHED;
    }

    /**
     * 会員変更情報取得Web-APIで取得した情報で会員情報を上書き<br/>
     *
     * @param memberInfoEntity         会員情報
     * @param userInformationDetailDto 会員変更情報取得Web-APIからの情報
     * @throws Exception 実行例外
     */
    protected void setMemberInfo(MemberInfoEntity memberInfoEntity,
                                 WebApiGetUserInformationResponseDetailDto userInformationDetailDto) throws Exception {

        // 顧客番号
        memberInfoEntity.setCustomerNo(userInformationDetailDto.getMemberNo());
        // 事業所名
        memberInfoEntity.setMemberInfoLastName(userInformationDetailDto.getOfficeName());
        // 事業所名フリガナ
        memberInfoEntity.setMemberInfoLastKana(userInformationDetailDto.getOfficeKana());
        // 代表者名
        memberInfoEntity.setRepresentativeName(userInformationDetailDto.getRepresentative());
        // 顧客区分
        memberInfoEntity.setBusinessType(EnumTypeUtil.getEnumFromValue(HTypeBusinessType.class,
                                                                       String.valueOf(userInformationDetailDto.getBusinessType())
                                                                      ));
        // 顧客区分が取得できないときは、"その他施設・病院・官公庁"とする
        if (memberInfoEntity.getBusinessType() == null) {
            throw new Exception("顧客区分が不正です。 顧客区分=" + userInformationDetailDto.getBusinessType());
        }
        // 電話番号
        memberInfoEntity.setMemberInfoTel(userInformationDetailDto.getTel());
        // FAX番号
        memberInfoEntity.setMemberInfoFax(userInformationDetailDto.getFax());
        // ログイン失敗回数
        if (memberInfoEntity.getLoginFailureCount() == null) {
            // 新規登録時のみ
            memberInfoEntity.setLoginFailureCount(0);
        }
        // 郵便番号
        memberInfoEntity.setMemberInfoZipCode(userInformationDetailDto.getZipCode());
        // 住所1
        memberInfoEntity.setMemberInfoAddress1(userInformationDetailDto.getCity());
        // 住所2
        memberInfoEntity.setMemberInfoAddress2(userInformationDetailDto.getAddress());
        // 住所3
        memberInfoEntity.setMemberInfoAddress3(userInformationDetailDto.getBuilding());
        // 住所4
        memberInfoEntity.setMemberInfoAddress4(userInformationDetailDto.getOther1());
        // 住所5
        memberInfoEntity.setMemberInfoAddress5(userInformationDetailDto.getOther2());
        // 休診曜日
        memberInfoEntity.setNonConsultationDay(userInformationDetailDto.getNonConsultationDay());
        // メールによるおトク情報
        // 値のNULLチェック(値がなければOFFとする)
        if (userInformationDetailDto.getMailPermitFlag() != null &&
            EnumTypeUtil.getEnumFromValue(HTypeSendMailPermitFlag.class,
                                          userInformationDetailDto.getMailPermitFlag().toString()
                                         ) != null) {
            memberInfoEntity.setSendMailPermitFlag(EnumTypeUtil.getEnumFromValue(HTypeSendMailPermitFlag.class,
                                                                                 userInformationDetailDto.getMailPermitFlag()
                                                                                                         .toString()
                                                                                ));
        } else {
            memberInfoEntity.setSendMailPermitFlag(HTypeSendMailPermitFlag.OFF);
        }
        // FAXによるおトク情報
        // 値のNULLチェック(値がなければOFFとする)
        if (userInformationDetailDto.getFaxPermitFlag() != null &&
            EnumTypeUtil.getEnumFromValue(HTypeSendFaxPermitFlag.class,
                                          userInformationDetailDto.getFaxPermitFlag().toString()
                                         ) != null) {
            memberInfoEntity.setSendFaxPermitFlag(EnumTypeUtil.getEnumFromValue(HTypeSendFaxPermitFlag.class,
                                                                                userInformationDetailDto.getSendDirectMailFlag()
                                                                                                        .toString()
                                                                               ));
        } else {
            memberInfoEntity.setSendFaxPermitFlag(HTypeSendFaxPermitFlag.OFF);
        }
        // DMによるおトク情報
        // 値のNULLチェック(値がなければOFFとする)
        if (userInformationDetailDto.getSendDirectMailFlag() != null &&
            EnumTypeUtil.getEnumFromValue(HTypeSendDirectMailFlag.class,
                                          userInformationDetailDto.getSendDirectMailFlag().toString()
                                         ) != null) {
            memberInfoEntity.setSendDirectMailFlag(EnumTypeUtil.getEnumFromValue(HTypeSendDirectMailFlag.class,
                                                                                 userInformationDetailDto.getSendDirectMailFlag()
                                                                                                         .toString()
                                                                                ));
        } else {
            memberInfoEntity.setSendDirectMailFlag(HTypeSendDirectMailFlag.OFF);
        }
        // 歯科専売品販売区分
        // 値のNULLチェック(値がなければSALEOFFとする)
        if (userInformationDetailDto.getDentalMonopolySalesType() != null &&
            EnumTypeUtil.getEnumFromValue(HTypeDentalMonopolySalesType.class,
                                          userInformationDetailDto.getDentalMonopolySalesType().toString()
                                         ) != null) {
            memberInfoEntity.setDentalMonopolySalesType(
                            EnumTypeUtil.getEnumFromValue(HTypeDentalMonopolySalesType.class,
                                                          userInformationDetailDto.getDentalMonopolySalesType()
                                                                                  .toString()
                                                         ));
        } else {
            memberInfoEntity.setDentalMonopolySalesType(HTypeDentalMonopolySalesType.SALEOFF);
        }
        // 医療機器販売区分
        // 値のNULLチェック(値がなければSALEOFFとする)
        if (userInformationDetailDto.getMedicalEquipmentSalesType() != null &&
            EnumTypeUtil.getEnumFromValue(HTypeMedicalEquipmentSalesType.class,
                                          userInformationDetailDto.getMedicalEquipmentSalesType().toString()
                                         ) != null) {
            memberInfoEntity.setMedicalEquipmentSalesType(
                            EnumTypeUtil.getEnumFromValue(HTypeMedicalEquipmentSalesType.class,
                                                          userInformationDetailDto.getMedicalEquipmentSalesType()
                                                                                  .toString()
                                                         ));
        } else {
            memberInfoEntity.setMedicalEquipmentSalesType(HTypeMedicalEquipmentSalesType.SALEOFF);
        }
        // 医薬品注射針販売区分
        // 値のNULLチェック(値がなければOFFとする)
        if (userInformationDetailDto.getDrugSalesType() != null &&
            EnumTypeUtil.getEnumFromValue(HTypeDrugSalesType.class,
                                          userInformationDetailDto.getDrugSalesType().toString()
                                         ) != null) {
            memberInfoEntity.setDrugSalesType(EnumTypeUtil.getEnumFromValue(HTypeDrugSalesType.class,
                                                                            userInformationDetailDto.getDrugSalesType()
                                                                                                    .toString()
                                                                           ));
        } else {
            memberInfoEntity.setDrugSalesType(HTypeDrugSalesType.SALEOFF);
        }
        // クレジット決済使用可否
        // 値のNULLチェック(値がなければOFFとする)
        if (userInformationDetailDto.getCreditPaymentUseFlag() != null &&
            EnumTypeUtil.getEnumFromValue(HTypeCreditPaymentUseFlag.class,
                                          userInformationDetailDto.getCreditPaymentUseFlag().toString()
                                         ) != null) {
            memberInfoEntity.setCreditPaymentUseFlag(EnumTypeUtil.getEnumFromValue(HTypeCreditPaymentUseFlag.class,
                                                                                   userInformationDetailDto.getCreditPaymentUseFlag()
                                                                                                           .toString()
                                                                                  ));
        } else {
            memberInfoEntity.setCreditPaymentUseFlag(HTypeCreditPaymentUseFlag.OFF);
        }
        // コンビニ郵便振込使用可否
        // 値のNULLチェック(値がなければOFFとする)
        if (userInformationDetailDto.getTransferPaymentUseFlag() != null &&
            EnumTypeUtil.getEnumFromValue(HTypeTransferPaymentUseFlag.class,
                                          userInformationDetailDto.getTransferPaymentUseFlag().toString()
                                         ) != null) {
            memberInfoEntity.setTransferPaymentUseFlag(EnumTypeUtil.getEnumFromValue(HTypeTransferPaymentUseFlag.class,
                                                                                     userInformationDetailDto.getTransferPaymentUseFlag()
                                                                                                             .toString()
                                                                                    ));
        } else {
            memberInfoEntity.setTransferPaymentUseFlag(HTypeTransferPaymentUseFlag.OFF);
        }
        // 代金引換使用可否
        // 値のNULLチェック(値がなければOFFとする)
        if (userInformationDetailDto.getCashDeliveryUseFlag() != null &&
            EnumTypeUtil.getEnumFromValue(HTypeCashDeliveryUseFlag.class,
                                          userInformationDetailDto.getCashDeliveryUseFlag().toString()
                                         ) != null) {
            memberInfoEntity.setCashDeliveryUseFlag(EnumTypeUtil.getEnumFromValue(HTypeCashDeliveryUseFlag.class,
                                                                                  userInformationDetailDto.getCashDeliveryUseFlag()
                                                                                                          .toString()
                                                                                 ));
        } else {
            memberInfoEntity.setCashDeliveryUseFlag(HTypeCashDeliveryUseFlag.OFF);
        }
        // 口座自動引落使用可否
        // 値のNULLチェック(値がなければOFFとする)
        if (userInformationDetailDto.getDirectDebitUseFlag() != null &&
            EnumTypeUtil.getEnumFromValue(HTypeDirectDebitUseFlag.class,
                                          userInformationDetailDto.getDirectDebitUseFlag().toString()
                                         ) != null) {
            memberInfoEntity.setDirectDebitUseFlag(EnumTypeUtil.getEnumFromValue(HTypeDirectDebitUseFlag.class,
                                                                                 userInformationDetailDto.getDirectDebitUseFlag()
                                                                                                         .toString()
                                                                                ));
        } else {
            memberInfoEntity.setDirectDebitUseFlag(HTypeDirectDebitUseFlag.OFF);
        }
        // 月締請求使用可否
        // 値のNULLチェック(値がなければOFFとする)
        if (userInformationDetailDto.getMonthlyPayUseFlag() != null &&
            EnumTypeUtil.getEnumFromValue(HTypeMonthlyPayUseFlag.class,
                                          userInformationDetailDto.getMonthlyPayUseFlag().toString()
                                         ) != null) {
            memberInfoEntity.setMonthlyPayUseFlag(EnumTypeUtil.getEnumFromValue(HTypeMonthlyPayUseFlag.class,
                                                                                userInformationDetailDto.getMonthlyPayUseFlag()
                                                                                                        .toString()
                                                                               ));
        } else {
            memberInfoEntity.setMonthlyPayUseFlag(HTypeMonthlyPayUseFlag.OFF);
        }
        // 名簿区分
        memberInfoEntity.setMemberListType(EnumTypeUtil.getEnumFromValue(HTypeMemberListType.class,
                                                                         String.valueOf(userInformationDetailDto.getMemberListType())
                                                                        ));
        // オンライン登録フラグ
        memberInfoEntity.setOnlineRegistFlag(EnumTypeUtil.getEnumFromValue(HTypeOnlineRegistFlag.class,
                                                                           String.valueOf(userInformationDetailDto.getOnlineRegistFlag())
                                                                          ));

        // サブシステム側との連携のため、予め決済代行IDに顧客番号を設定
        memberInfoEntity.setPaymentMemberId(memberInfoEntity.getCustomerNo().toString());
        // 決済代行会社カード保持種別 カード情報登録済 を設定
        memberInfoEntity.setPaymentCardRegistType(HTypeCardRegistType.REGISTERED);

        // 診療項目
        memberInfoEntity.setMedicalTreatmentFlag(userInformationDetailDto.getTreatmentType());

        // その他診療内容
        memberInfoEntity.setMedicalTreatmentMemo(userInformationDetailDto.getTreatmentTypeMemo());

        // 金属商品価格お知らせメール
        memberInfoEntity.setMetalPermitFlag(EnumTypeUtil.getEnumFromValue(HTypeMetalPermitFlag.class,
                                                                          String.valueOf(userInformationDetailDto.getMetalPermitFlag())
                                                                         ));

        // 確認書類
        memberInfoEntity.setConfDocumentType(EnumTypeUtil.getEnumFromValue(HTypeConfDocumentType.class,
                                                                           String.valueOf(userInformationDetailDto.getKakuninShoKbn())
                                                                          ));

        // 経理区分
        memberInfoEntity.setAccountingType(EnumTypeUtil.getEnumFromValue(HTypeAccountingType.class,
                                                                         String.valueOf(userInformationDetailDto.getKeiriKbn())
                                                                        ));

        // オンラインログイン可否
        memberInfoEntity.setOnlineLoginAdvisability(EnumTypeUtil.getEnumFromValue(HTypeOnlineLoginAdvisability.class,
                                                                                  String.valueOf(userInformationDetailDto.getOnlineYesNo())
                                                                                 ));
    }

    /**
     * 処理結果メールを送信する
     *
     * @param detail 処理結果
     */
    protected boolean sendAdministratorMail(String detail) {

        try {

            MailDto mailDto = ApplicationContextUtility.getBean(MailDto.class);

            Map<String, Object> mailContentsMap = new HashMap<>();
            // メール本文の設定
            Map<String, String> valueMap = new HashMap<String, String>();

            // システム名を取得
            String systemName = PropertiesUtil.getSystemPropertiesValue("system.name");

            valueMap.put("SYSTEM", systemName);
            // バッチ名
            valueMap.put("BATCH_NAME", HTypeBatchName.BATCH_MEMBER_INFO_UPDATE.getLabel());
            // 処理結果
            valueMap.put("RESULT", detail);

            mailContentsMap.put("admin", valueMap);

            mailDto.setMailTemplateType(HTypeMailTemplateType.MEMBER_INFO_UPDATE_MAIL);
            mailDto.setFrom(this.mailSetting.getMailFrom());
            mailDto.setToList(this.mailSetting.getNotificationReceivers());
            if (batchProcessResult.equals(UPDATE_RESULT_ERROR)) {
                mailDto.setSubject(HTypeBatchName.BATCH_MEMBER_INFO_UPDATE.getLabel() + " " + UPDATE_RESULT_ERROR);
            } else if (batchProcessResult.equals(UPDATE_RESULT_FAILED)) {
                mailDto.setSubject(HTypeBatchName.BATCH_MEMBER_INFO_UPDATE.getLabel() + " " + UPDATE_RESULT_FAILED);
            } else {
                mailDto.setSubject(HTypeBatchName.BATCH_MEMBER_INFO_UPDATE.getLabel() + " " + UPDATE_RESULT_SUCCESS);
            }
            mailDto.setMailContentsMap(mailContentsMap);

            this.mailSendService.execute(mailDto);
            LOGGER.info("管理者へ通知メールを送信しました。");

            return true;
        } catch (Exception e) {
            LOGGER.warn("管理者への通知メール送信に失敗しました。", e);
            return false;
        }
    }

    /**
     * 処理結果成功内容生成
     */
    public void createSuccessMailDetail() {

        StringBuilder resultDetail = new StringBuilder();
        // 会員情報登録件数
        resultDetail.append("会員情報\n");
        resultDetail.append("追加件数：");
        resultDetail.append(registCount);
        resultDetail.append("件\n");
        // 会員情報更新件数
        resultDetail.append("更新件数：");
        resultDetail.append(updateCount);
        resultDetail.append("件\n");
        // 発送メール送信失敗件数
        resultDetail.append("\n失敗件数：");
        resultDetail.append(faildeItemCount);
        resultDetail.append("件\n");
        resultDetail.append(createSuccessMailFailed());
        // 処理結果メールメッセージに格納
        mailMessageDetail = resultDetail.toString();
    }

    /**
     * 処理結果失敗内容生成
     *
     * @return 失敗内容
     */
    public String createSuccessMailFailed() {

        StringBuilder resultFailed = new StringBuilder();
        // エラーチェックリストがある場合のみ処理結果情報に追記
        if (failedList.size() > 0) {
            resultFailed.append("\n\n▼エラー内容：\n");
        }
        for (String failedCode : failedList) {
            resultFailed.append(failedCode + "\n");
        }
        return resultFailed.toString();
    }

    /**
     * 処理結果異常終了内容生成
     *
     * @param canceledFlag  キャンセル要求があったかどうか
     * @param exceptionName 発生したException
     */
    public void createFailedMailDetail(boolean canceledFlag, String exceptionName) {

        StringBuilder resultDetail = new StringBuilder();
        // キャンセル要求チェック
        if (canceledFlag) {
            resultDetail.append("キャンセル要求があったため処理は中断されました。");
            resultDetail.append("\n詳しくはサーバログを参照してください。");
        } else {
            resultDetail.append("以下のエラーが発生しました。");
            resultDetail.append("\n詳しくはサーバログを参照してください。");
            resultDetail.append("\n\n処理中に");
            resultDetail.append(exceptionName);
            resultDetail.append("が発生しました。");
        }
        // 処理結果メールメッセージに格納
        mailMessageDetail = resultDetail.toString();
    }

    /**
     * 入力チェック
     *
     * @param userInformationDetailDto 会員変更情報取得 詳細情報
     * @return エラーフラグ true:エラー有 false:エラー無
     */
    public boolean checkInputItem(WebApiGetUserInformationResponseDetailDto userInformationDetailDto) {

        boolean errorFlag = false;
        String memberNo = userInformationDetailDto.getMemberNo() == 0 ?
                        "" :
                        String.valueOf(userInformationDetailDto.getMemberNo());
        String checkTargetStr = "【顧客番号:" + memberNo + "】";

        // 入力チェック・桁数チェック・区分値チェック
        // 顧客番号を確認
        if (userInformationDetailDto.getMemberNo() == 0) {
            failedList.add(checkTargetStr + "顧客番号" + ISEMPTY_MSG);
            errorFlag = true;
        } else if (String.valueOf(userInformationDetailDto.getMemberNo()).length() >= 10) {
            failedList.add(checkTargetStr + "顧客番号" + MAXLENGTH_MSG);
            errorFlag = true;
        } else if (!Pattern.matches("^[0-9]*$", String.valueOf(userInformationDetailDto.getMemberNo()))) {
            failedList.add(checkTargetStr + "顧客番号" + ITEM_ILLEGAL_MSG);
            errorFlag = true;
        }

        // 事業所名を確認
        if (userInformationDetailDto.getOfficeName() == null) {
            failedList.add(checkTargetStr + "事業所名" + ISEMPTY_MSG);
            errorFlag = true;
        } else if (userInformationDetailDto.getOfficeName().length() >= 41) {
            failedList.add(checkTargetStr + "事業所名" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // 事業所名フリガナを確認
        if (userInformationDetailDto.getOfficeKana() == null) {
            failedList.add(checkTargetStr + "事業所名フリガナ" + ISEMPTY_MSG);
            errorFlag = true;
        } else if (userInformationDetailDto.getOfficeKana().length() >= 31) {
            failedList.add(checkTargetStr + "事業所名フリガナ" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // 代表者名を確認
        if (userInformationDetailDto.getRepresentative() != null
            && userInformationDetailDto.getRepresentative().length() >= 31) {
            failedList.add(checkTargetStr + "代表者名" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // 顧客区分を確認
        if (userInformationDetailDto.getBusinessType() == null) {
            failedList.add(checkTargetStr + "顧客区分" + ISEMPTY_MSG);
            errorFlag = true;
        } else if (EnumTypeUtil.getEnumFromValue(
                        HTypeBusinessType.class, userInformationDetailDto.getBusinessType().toString()) == null) {
            failedList.add(checkTargetStr + "顧客区分" + ITEM_ILLEGAL_MSG);
            errorFlag = true;
        }

        // 電話番号を確認
        if (userInformationDetailDto.getTel() == null) {
            failedList.add(checkTargetStr + "電話番号" + ISEMPTY_MSG);
            errorFlag = true;
        } else if (userInformationDetailDto.getTel().length() >= 15) {
            failedList.add(checkTargetStr + "電話番号" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // FAX番号を確認
        if (userInformationDetailDto.getFax() != null && userInformationDetailDto.getFax().length() >= 15) {
            failedList.add(checkTargetStr + "FAX番号" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // 郵便番号を確認
        if (userInformationDetailDto.getZipCode() == null) {
            failedList.add(checkTargetStr + "郵便番号" + ISEMPTY_MSG);
            errorFlag = true;
        } else if (userInformationDetailDto.getZipCode().length() >= 8) {
            failedList.add(checkTargetStr + "郵便番号" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // 住所(都道府県・市区町村)を確認
        if (userInformationDetailDto.getCity() == null) {
            failedList.add(checkTargetStr + "住所(都道府県・市区町村)" + ISEMPTY_MSG);
            errorFlag = true;
        } else if (userInformationDetailDto.getCity().length() >= 41) {
            failedList.add(checkTargetStr + "住所(都道府県・市区町村)" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // 住所(丁目・番地)を確認
        if (userInformationDetailDto.getAddress() == null) {
            failedList.add(checkTargetStr + "住所(丁目・番地)" + ISEMPTY_MSG);
            errorFlag = true;
        } else if (userInformationDetailDto.getAddress().length() >= 41) {
            failedList.add(checkTargetStr + "住所(丁目・番地)" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // 住所(建物名・部屋番号)を確認
        if (userInformationDetailDto.getBuilding() != null && userInformationDetailDto.getBuilding().length() >= 41) {
            failedList.add(checkTargetStr + "住所(建物名・部屋番号)" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // 住所(方書1、2)を確認
        if (userInformationDetailDto.getOther1() != null && userInformationDetailDto.getOther1().length() >= 31) {
            failedList.add(checkTargetStr + "住所(方書1、2)" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // 休診曜日を確認
        if (userInformationDetailDto.getNonConsultationDay() != null
            && userInformationDetailDto.getNonConsultationDay().length() >= 10) {
            failedList.add(checkTargetStr + "休診曜日" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // 名簿区分を確認
        if (userInformationDetailDto.getMemberListType() == null) {
            failedList.add(checkTargetStr + "名簿区分" + ISEMPTY_MSG);
            errorFlag = true;
        } else if (EnumTypeUtil.getEnumFromValue(
                        HTypeMemberListType.class, userInformationDetailDto.getMemberListType().toString()) == null) {
            failedList.add(checkTargetStr + "名簿区分" + ITEM_ILLEGAL_MSG);
            errorFlag = true;
        }

        // オンライン登録フラグを確認
        if (userInformationDetailDto.getOnlineRegistFlag() == null) {
            failedList.add(checkTargetStr + "オンライン登録フラグ" + ISEMPTY_MSG);
            errorFlag = true;
        } else if (EnumTypeUtil.getEnumFromValue(
                        HTypeOnlineRegistFlag.class, userInformationDetailDto.getOnlineRegistFlag().toString())
                   == null) {
            failedList.add(checkTargetStr + "オンライン登録フラグ" + ITEM_ILLEGAL_MSG);
            errorFlag = true;
        }

        // 診療内容を確認
        if (userInformationDetailDto.getTreatmentType() == null) {
            failedList.add(checkTargetStr + "診療内容" + ISEMPTY_MSG);
            errorFlag = true;
        } else if (userInformationDetailDto.getTreatmentType().length() >= 11) {
            failedList.add(checkTargetStr + "診療内容" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // その他診療内容を確認
        if (userInformationDetailDto.getTreatmentTypeMemo() != null
            && userInformationDetailDto.getTreatmentTypeMemo().length() >= 31) {
            failedList.add(checkTargetStr + "その他診療内容" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // 金属メール希望フラグを確認
        if (userInformationDetailDto.getMetalPermitFlag() == null) {
            failedList.add(checkTargetStr + "金属メール希望フラグ" + ISEMPTY_MSG);
            errorFlag = true;
        } else if (EnumTypeUtil.getEnumFromValue(
                        HTypeMetalPermitFlag.class, userInformationDetailDto.getMetalPermitFlag()) == null) {
            failedList.add(checkTargetStr + "金属メール希望フラグ" + ITEM_ILLEGAL_MSG);
            errorFlag = true;
        }

        // 確認書類を確認
        if (userInformationDetailDto.getKakuninShoKbn() == null) {
            failedList.add(checkTargetStr + "確認書類" + ISEMPTY_MSG);
            errorFlag = true;
        } else if (EnumTypeUtil.getEnumFromValue(
                        HTypeConfDocumentType.class, userInformationDetailDto.getKakuninShoKbn()) == null) {
            failedList.add(checkTargetStr + "確認書類" + ITEM_ILLEGAL_MSG);
            errorFlag = true;
        }

        // 経理区分を確認
        if (userInformationDetailDto.getKeiriKbn() == null) {
            failedList.add(checkTargetStr + "経理区分" + ISEMPTY_MSG);
            errorFlag = true;
        } else if (EnumTypeUtil.getEnumFromValue(HTypeAccountingType.class, userInformationDetailDto.getKeiriKbn())
                   == null) {
            failedList.add(checkTargetStr + "経理区分" + ITEM_ILLEGAL_MSG);
            errorFlag = true;
        }

        // オンラインログイン可否を確認
        if (userInformationDetailDto.getOnlineYesNo() == null) {
            failedList.add(checkTargetStr + "オンラインログイン可否" + ISEMPTY_MSG);
            errorFlag = true;
        } else if (EnumTypeUtil.getEnumFromValue(
                        HTypeOnlineLoginAdvisability.class, userInformationDetailDto.getOnlineYesNo()) == null) {
            failedList.add(checkTargetStr + "オンラインログイン可否" + ITEM_ILLEGAL_MSG);
            errorFlag = true;
        }
        return errorFlag;
    }
}
// PDR Migrate Customization to here
