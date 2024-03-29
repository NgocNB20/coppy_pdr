package jp.co.hankyuhanshin.itec.hitmall.batch.offline.delvinfo.tasklet;

import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageUtil;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.AbstractBatch;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBatchName;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCoopId;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeliveryCompletePermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderStatus;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.mail.MailTemplateDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetShipmentInformationRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetShipmentInformationResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetShipmentInformationResponseDetailGoodsInfoDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetShipmentInformationResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.coop.CoopDateHistoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.mail.MailTemplateEntity;
import jp.co.hankyuhanshin.itec.hitmall.helper.mailtemplate.ShipmentCompleteTransformHelper;
import jp.co.hankyuhanshin.itec.hitmall.helper.mailtemplate.Transformer;
import jp.co.hankyuhanshin.itec.hitmall.logic.coop.CoopDateHistoryGetUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetShipmentInformationLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.mail.MailSendService;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelException;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.mail.InstantMailSetting;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
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

// PDR Migrate Customization from here

/**
 * 出荷情報取得バッチ
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
public class DelvInfoImportBatch extends AbstractBatch {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DelvInfoImportBatch.class);

    /**
     * メールマップ作成用トランスフォーマ
     */
    private final Transformer transformer;

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
     * 処理結果
     */
    private String batchProcessResult;

    /**
     * 処理結果メール詳細メッセージ
     */
    private String mailMessageDetail;

    /**
     * 処理件数
     */
    private int resultCount = 0;

    /**
     * エラー項目件数
     */
    private int faildeItemCount = 0;

    /**
     * 基幹連携日時履歴取得・更新ロジック
     */
    private final CoopDateHistoryGetUpdateLogic coopDateHistoryGetUpdateLogic;

    /**
     * 出荷情報取得WEB-API連携ロジック
     */
    private final WebApiGetShipmentInformationLogic webApiGetShipmentInformationLogic;

    /**
     * 受注サマリ情報取得ロジック
     */
    private final OrderSummaryGetLogic orderSummaryGetLogic;

    /**
     * 受注サマリ更新ロジック
     */
    private final OrderSummaryUpdateLogic orderSummaryUpdateLogic;

    /**
     * dicon 設定： メール送信設定
     */
    private final InstantMailSetting mailSetting;

    /**
     * 発送完了メールテンプレートタイプ
     */
    private final HTypeMailTemplateType mailTemplateType;

    /**
     * メールテンプレート DAO
     */
    private final MailTemplateDao mailTemplateDao;

    /**
     * 日付関連Utilityクラス
     */
    private final DateUtility dateUtility;

    /**
     * エラーチェックリスト
     */
    final List<String> failedList = new ArrayList<>();

    /**
     * メール送信サービス
     */
    private final MailSendService mailSendService;

    /**
     * バッチ終了メッセージ共通処理
     */
    private final BatchExitMessageUtil exitMessageUtil;

    /**
     * 出荷情報取得バッチ
     */
    private final DelvInfoImportService delvInfoImportService;

    // 2023-renew No79 from here
    /**
     * 会員情報取得ロジック
     */
    private final MemberInfoGetLogic memberInfoGetLogic;
    // 2023-renew No79 to here

    /**
     * コンストラクタ
     */
    public DelvInfoImportBatch(Environment environment) {
        this.exitMessageUtil = new BatchExitMessageUtil();
        this.mailTemplateDao = ApplicationContextUtility.getBean(MailTemplateDao.class);
        this.transformer = ApplicationContextUtility.getBean(ShipmentCompleteTransformHelper.class);
        this.coopDateHistoryGetUpdateLogic = ApplicationContextUtility.getBean(CoopDateHistoryGetUpdateLogic.class);
        this.webApiGetShipmentInformationLogic =
                        ApplicationContextUtility.getBean(WebApiGetShipmentInformationLogic.class);
        this.orderSummaryGetLogic = ApplicationContextUtility.getBean(OrderSummaryGetLogic.class);
        this.dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
        this.orderSummaryUpdateLogic = ApplicationContextUtility.getBean(OrderSummaryUpdateLogic.class);
        this.mailTemplateType = HTypeMailTemplateType.SHIPMENT_COMPLETE;
        this.mailSendService = ApplicationContextUtility.getBean(MailSendService.class);
        this.mailSetting = new InstantMailSetting(environment.getProperty("mail.setting.delvinfo.import.smtp.server"),
                                                  environment.getProperty("mail.setting.delvinfo.import.mail.from"),
                                                  null, null, Collections.singletonList(
                        environment.getProperty("mail.setting.delvinfo.import.mail.receivers"))
        );
        super.setShopSeq(environment.getProperty("shopseq", Integer.class));
        this.delvInfoImportService = ApplicationContextUtility.getBean(DelvInfoImportService.class);
        // 2023-renew No79 from here
        this.memberInfoGetLogic = ApplicationContextUtility.getBean(MemberInfoGetLogic.class);
        // 2023-renew No79 to here
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        // コンテキスト取得
        ExecutionContext executionContext =
                        chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext();

        // バッチのジョッブ情報取得
        JobParameters jobParameters = chunkContext.getStepContext().getStepExecution().getJobParameters();
        String shopSeq = jobParameters.getString("shopSeq");
        if (!StringUtils.isEmpty(shopSeq)) {
            super.setShopSeq(Integer.valueOf(shopSeq));
        }

        // 処理中受注番号保持
        Integer checkOrderNo = null;

        try {

            // 前回実行日時取得処理開始
            LOGGER.debug("前回実行日時の取得処理を開始します。");

            // 前回実行日時取得(取得失敗でエラー)
            CoopDateHistoryEntity coopDateHistoryEntity =
                            coopDateHistoryGetUpdateLogic.execute(HTypeCoopId.ORDER_DELV_INFO_IMPORT.getValue());
            // 前回実行日時
            if (coopDateHistoryEntity == null) {
                throw new RuntimeException("前回実行日時の取得に失敗しました。");
            }
            Timestamp lastCoopDate = coopDateHistoryEntity.getLastCoopDate();
            // 前回実行日時取得処理終了
            LOGGER.debug("前回実行日時の取得処理が正常終了しました。");

            // 出荷情報取得API用引数(前回実行日時から1日前を計算)
            Timestamp dayBeforeCoopDate = dateUtility.getAmountDayTimestamp(1, false, lastCoopDate);
            // 現在日時を取得
            Timestamp currentTime = dateUtility.getCurrentTime();

            // 出荷情報取得処理開始
            LOGGER.debug("出荷情報取得WEB-APIの実行を開始します。");

            // 出荷情報リクエストDto
            WebApiGetShipmentInformationRequestDto reqDto =
                            ApplicationContextUtility.getBean(WebApiGetShipmentInformationRequestDto.class);
            // 出荷情報取得API用引数をセット
            reqDto.setDesignationDate(dayBeforeCoopDate);
            // 出荷情報取得WEB-API実行
            WebApiGetShipmentInformationResponseDto responseDto =
                            (WebApiGetShipmentInformationResponseDto) webApiGetShipmentInformationLogic.execute(reqDto);

            // 連携失敗
            if (responseDto == null || responseDto.getResult() == null) {
                throw new RuntimeException("出荷情報取得連携に失敗しました。");
            }
            // 取得結果が不正なら処理終了
            if (!responseDto.getResult().getStatus().equals("0")) {
                throw new RuntimeException("出荷情報の取得に失敗しました。 ステータスコード：" + responseDto.getResult().getStatus());
            }

            // 出荷情報取得処理終了
            LOGGER.debug("出荷情報取得WEB-APIの実行が正常終了しました。");

            // 処理対象が存在するかチェック
            if (responseDto.getInfo() != null) {

                List<MailDto> mailDtoList = new ArrayList<>();

                // 受注ステータス更新ループ処理開始
                LOGGER.debug("受注ステータス更新ループ処理の実行を開始します。");
                LOGGER.debug("処理対象件数：" + responseDto.getInfo().size());
                // 受注ステータス更新
                for (WebApiGetShipmentInformationResponseDetailDto shipmentInformationDetailDto : responseDto.getInfo()) {

                    // 処理中受注番号保持
                    checkOrderNo = shipmentInformationDetailDto.getOrderNo();

                    // 受注サマリ情報取得
                    OrderSummaryEntity orderSummaryEntity = new OrderSummaryEntity();
                    if (shipmentInformationDetailDto.getOrderNo() != null) {
                        orderSummaryEntity = orderSummaryGetLogic.execute(
                                        shipmentInformationDetailDto.getOrderNo().toString());
                    }

                    if (orderSummaryEntity == null) {
                        // continue;
                        LOGGER.info("旧ECの受注です:" + shipmentInformationDetailDto.getOrderNo().toString());
                    }
                    // nullチェックとか
                    // 受注状態を確認
                    if (orderSummaryEntity != null && HTypeOrderStatus.SHIPMENT_COMPLETION.equals(
                                    orderSummaryEntity.getOrderStatus())) {
                        // 受注状態が出荷完了ならば次の出荷情報へ
                        LOGGER.info("受注ステータスが出荷完了のため更新をスキップしました。");
                        LOGGER.info("受注番号：" + shipmentInformationDetailDto.getOrderNo());
                        continue;
                    }
                    // 入力チェック
                    if (checkInputItem(shipmentInformationDetailDto)) {
                        faildeItemCount++;
                        continue;
                    }
                    // 受注ステータス更新
                    LOGGER.debug("受注ステータスを更新します。");
                    LOGGER.debug("受注番号：" + shipmentInformationDetailDto.getOrderNo());

                    // 受注状態が出荷完了以外ならば受注状態を出荷完了へ更新
                    if (orderSummaryEntity != null) {
                        int update = 0;
                        try {
                            orderSummaryEntity.setOrderStatus(HTypeOrderStatus.SHIPMENT_COMPLETION);
                            update = orderSummaryUpdateLogic.execute(orderSummaryEntity);
                        } catch (Exception error) {
                            // 更新失敗
                            LOGGER.error("例外処理が発生しました", error);
                            // エラーになった対象の受注番号をセット
                            for (WebApiGetShipmentInformationResponseDetailGoodsInfoDto goodsInfo : shipmentInformationDetailDto.getGoodsList()) {
                                LOGGER.warn("【受注番号:" + checkOrderNo + "　事業所名:"
                                            + shipmentInformationDetailDto.getOfficeName() + "】申込商品番号:"
                                            + goodsInfo.getGoodsNo() + "更新に失敗しました。連携項目を確認してください。");
                                failedList.add("【受注番号:" + checkOrderNo + "　事業所名:"
                                               + shipmentInformationDetailDto.getOfficeName() + "】申込商品番号:"
                                               + goodsInfo.getGoodsNo() + "更新に失敗しました。連携項目を確認してください。");
                                faildeItemCount++;
                            }
                            continue;
                        }
                    }

                    // 2023-renew No79 from here
                    // 会員情報取得
                    MemberInfoEntity memberInfoEntity = null;

                    if (shipmentInformationDetailDto.getMailAddress() != null) {
                        memberInfoEntity = memberInfoGetLogic.getMemberInfoEntityByMemberInfoMail(
                                        shipmentInformationDetailDto.getMailAddress());
                    }
                    if (memberInfoEntity == null) {
                        LOGGER.warn("会員の取得に失敗しました。");
                        continue;
                    }

                    // メール送信依頼Dto作成
                    if (HTypeDeliveryCompletePermitFlag.ON == memberInfoEntity.getDeliveryCompletePermitFlag()) {
                        LOGGER.info("メール送信タスクを登録します：" + shipmentInformationDetailDto.getOrderNo().toString());
                        // メールリクエストDto作成
                        LOGGER.debug("メールリクエストを作成します。");
                        mailDtoList.add(makeMailSendRequest(shipmentInformationDetailDto));
                        resultCount++;
                    }
                    // 2023-renew No79 to here

                }

                // 一括メール送信
                if (!CollectionUtils.isEmpty(mailDtoList)) {
                    delvInfoImportService.sendMailInBulk(mailDtoList);
                }

                // 処理中受注番号保持
                checkOrderNo = null;
                // 受注ステータス更新ループ処理終了
                LOGGER.debug("受注ステータス更新ループ処理の実行が正常終了しました。");
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

            this.report(mailMessageDetail);

        } catch (AppLevelListException appe) {
            // 処理結果エラーメール送信
            batchProcessResult = UPDATE_RESULT_FAILED;
            // エラーとなった対象の受注番号をログ出力
            if (checkOrderNo != null) {
                LOGGER.error("エラー対象受注番号：" + checkOrderNo);
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
            // エラーとなった対象の受注番号をログ出力
            if (checkOrderNo != null) {
                LOGGER.error("エラー対象受注番号：" + checkOrderNo);
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
                        new BatchExitMessageDto(String.valueOf(resultCount), this.getReportString().toString())));

        return RepeatStatus.FINISHED;
    }

    /**
     * メール送信依頼用Dto作成
     *
     * @param shipmentInformationDetailDto WEB-API連携取得結果DTOクラス
     * @return メール送信依頼DTO
     * @throws Exception 例外
     */
    protected MailDto makeMailSendRequest(WebApiGetShipmentInformationResponseDetailDto shipmentInformationDetailDto)
                    throws Exception {
        MailDto mailDto = createMailDtoCommon();

        try {
            Map<String, Object> mailContentsMap = new HashMap<>();

            mailDto.setToList(Collections.singletonList(shipmentInformationDetailDto.getMailAddress()));

            // メール用値マップの作成
            mailContentsMap.put("mailContentsMap", transformer.toValueMap(shipmentInformationDetailDto));

            mailDto.setMailContentsMap(mailContentsMap);

        } catch (Exception error) {
            LOGGER.error("メールリクエストの作成に失敗しました。", error);
            throw error;
        }

        return mailDto;
    }

    /**
     * 督促メール受信者DTO を生成する
     *
     * @return メール受信者 DTO
     */
    protected MailDto createMailDtoCommon() {

        // 注文者情報のメールアドレスを使用する

        MailDto mailDto = ApplicationContextUtility.getBean(MailDto.class);
        mailDto.setMailTemplateType(HTypeMailTemplateType.DELV_INFO_IMPORT_MAIL);

        MailTemplateEntity entity = this.mailTemplateDao.getEntityByType(super.getShopSeq(), this.mailTemplateType);
        mailDto.setSubject(entity.getMailTemplateSubject());
        mailDto.setFrom(entity.getMailTemplateFromAddress());
        if (entity.getMailTemplateCcAddress() != null) {
            mailDto.setCcList(Collections.singletonList(entity.getMailTemplateCcAddress()));
        }
        if (entity.getMailTemplateBccAddress() != null) {
            mailDto.setBccList(Collections.singletonList(entity.getMailTemplateBccAddress()));
        }

        return mailDto;
    }

    /**
     * 処理結果メールを送信する
     *
     * @param detail 処理結果
     */
    protected boolean sendAdministratorMail(String detail) {

        LOGGER.debug("処理結果メールの送信処理を開始します。");
        try {

            MailDto mailDto = ApplicationContextUtility.getBean(MailDto.class);

            Map<String, Object> mailContentsMap = new HashMap<>();
            // メール本文の設定
            Map<String, String> valueMap = new HashMap<String, String>();
            // システム名を取得
            String systemName = PropertiesUtil.getSystemPropertiesValue("system.name");

            // バッチ名
            valueMap.put("BATCH_NAME", HTypeBatchName.BATCH_DELV_PRICE_UPDATE.getLabel());
            // 処理結果
            valueMap.put("RESULT", batchProcessResult);
            // 処理結果詳細
            valueMap.put("DETAIL", detail);

            mailContentsMap.put("admin", valueMap);

            mailDto.setMailTemplateType(HTypeMailTemplateType.SHIPMENT_COMPLETE);
            mailDto.setFrom(this.mailSetting.getMailFrom());
            mailDto.setToList(this.mailSetting.getNotificationReceivers());
            if (batchProcessResult.equals(UPDATE_RESULT_ERROR)) {
                mailDto.setSubject(HTypeBatchName.BATCH_DELV_PRICE_UPDATE.getLabel() + UPDATE_RESULT_ERROR);
            } else if (batchProcessResult.equals(UPDATE_RESULT_FAILED)) {
                mailDto.setSubject(HTypeBatchName.BATCH_DELV_PRICE_UPDATE.getLabel() + UPDATE_RESULT_FAILED);
            } else {
                mailDto.setSubject(HTypeBatchName.BATCH_DELV_PRICE_UPDATE.getLabel() + UPDATE_RESULT_SUCCESS);
            }
            mailDto.setMailContentsMap(mailContentsMap);

            this.mailSendService.execute(mailDto);
            LOGGER.debug("処理結果メールを送信しました。");
            return true;

        } catch (Exception e) {
            LOGGER.error("処理結果メールの送信に失敗しました。", e);
            return false;
        }
    }

    /**
     * 処理結果成功内容生成
     */
    public void createSuccessMailDetail() {

        StringBuilder resultDetail = new StringBuilder();
        // 発送メール送信件数
        resultDetail.append("発送メール送信件数：");
        resultDetail.append(resultCount);
        resultDetail.append("件");
        // 発送メール送信失敗件数
        resultDetail.append("\n失敗件数：");
        resultDetail.append(faildeItemCount);
        resultDetail.append("件");
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
     * @param shipmentInformationDetailDto 出荷情報取得 詳細情報
     * @return エラーフラグ true:エラー有 false:エラー無
     */
    public boolean checkInputItem(WebApiGetShipmentInformationResponseDetailDto shipmentInformationDetailDto) {

        boolean errorFlag = false;
        String orderNo = shipmentInformationDetailDto.getOrderNo() == null ?
                        "" :
                        String.valueOf(shipmentInformationDetailDto.getOrderNo());
        String officeName = shipmentInformationDetailDto.getOfficeName() == null ?
                        "" :
                        shipmentInformationDetailDto.getOfficeName();
        String checkTargetStr = "【受注番号:" + orderNo + "　事業所名:" + officeName + "】";

        // 入力チェック・桁数チェック
        // 受注番号を確認
        if (shipmentInformationDetailDto.getOrderNo() == null) {
            failedList.add(checkTargetStr + "受注番号" + ISEMPTY_MSG);
            errorFlag = true;
        } else if (shipmentInformationDetailDto.getOrderNo().toString().length() >= 11) {
            failedList.add(checkTargetStr + "受注番号" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // 事業所名を確認
        if (shipmentInformationDetailDto.getOfficeName() == null) {
            failedList.add(checkTargetStr + "事業所名" + ISEMPTY_MSG);
            errorFlag = true;
            // 2023-renew No85-1 from here
        } else if (shipmentInformationDetailDto.getOfficeName().length() >= 41) {
            // 2023-renew No85-1 to here
            failedList.add(checkTargetStr + "事業所名" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // お届け先事業所名を確認
        if (shipmentInformationDetailDto.getReceiveOfficeName() == null) {
            failedList.add(checkTargetStr + "お届け先事業所名" + ISEMPTY_MSG);
            errorFlag = true;
            // 2023-renew No85-1 from here
        } else if (shipmentInformationDetailDto.getReceiveOfficeName().length() >= 41) {
            // 2023-renew No85-1 to here
            failedList.add("お届け先事業所名" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // お届け先郵便番号を確認
        if (shipmentInformationDetailDto.getReceiveZipcode() == null) {
            failedList.add(checkTargetStr + "お届け先郵便番号" + ISEMPTY_MSG);
            errorFlag = true;
        } else if (shipmentInformationDetailDto.getReceiveZipcode().length() >= 11) {
            failedList.add(checkTargetStr + "お届け先郵便番号" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // お届け先住所(都道府県・市区町村)を確認
        if (shipmentInformationDetailDto.getReceiveAddress1() == null) {
            failedList.add(checkTargetStr + "お届け先住所(都道府県・市区町村)" + ISEMPTY_MSG);
            errorFlag = true;
        } else if (shipmentInformationDetailDto.getReceiveAddress1().length() >= 41) {
            failedList.add(checkTargetStr + "お届け先住所(都道府県・市区町村)" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // お届け先住所(丁目・番地)を確認
        if (shipmentInformationDetailDto.getReceiveAddress2() == null) {
            failedList.add(checkTargetStr + "お届け先住所(丁目・番地)" + ISEMPTY_MSG);
            errorFlag = true;
        } else if (shipmentInformationDetailDto.getReceiveAddress2().length() >= 41) {
            failedList.add(checkTargetStr + "お届け先住所(丁目・番地)" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // お届け先住所(建物名・部屋番号)を確認
        if (shipmentInformationDetailDto.getReceiveAddress3() != null
            && shipmentInformationDetailDto.getReceiveAddress3().length() >= 41) {
            failedList.add(checkTargetStr + "お届け先住所(建物名・部屋番号)" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // お届け先住所(方書1)を確認
        if (shipmentInformationDetailDto.getReceiveAddress4() != null
            && shipmentInformationDetailDto.getReceiveAddress4().length() >= 31) {
            failedList.add(checkTargetStr + "お届け先住所(方書1)" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // お届け先住所(方書2) を確認
        if (shipmentInformationDetailDto.getReceiveAddress5() != null
            && shipmentInformationDetailDto.getReceiveAddress5().length() >= 31) {
            failedList.add(checkTargetStr + "お届け先住所(方書2)" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // 小計を確認
        if (shipmentInformationDetailDto.getSubTotalPrice() == null) {
            failedList.add(checkTargetStr + "小計" + ISEMPTY_MSG);
            errorFlag = true;
        } else if (shipmentInformationDetailDto.getSubTotalPrice().length() >= 13) {
            failedList.add(checkTargetStr + "小計" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // 値引を確認
        if (shipmentInformationDetailDto.getDiscountPrice() == null) {
            failedList.add(checkTargetStr + "値引" + ISEMPTY_MSG);
            errorFlag = true;
        } else if (shipmentInformationDetailDto.getDiscountPrice().length() >= 7) {
            failedList.add(checkTargetStr + "値引" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // 送料を確認
        if (shipmentInformationDetailDto.getShippingPrice() == null) {
            failedList.add(checkTargetStr + "送料" + ISEMPTY_MSG);
            errorFlag = true;
        } else if (shipmentInformationDetailDto.getShippingPrice().length() >= 6) {
            failedList.add(checkTargetStr + "送料" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // 消費税を確認
        if (shipmentInformationDetailDto.getTaxPrice() == null) {
            failedList.add(checkTargetStr + "消費税" + ISEMPTY_MSG);
            errorFlag = true;
        } else if (shipmentInformationDetailDto.getTaxPrice().length() >= 13) {
            failedList.add(checkTargetStr + "消費税" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // 合計金額を確認
        if (shipmentInformationDetailDto.getTotalPrice() == null) {
            failedList.add(checkTargetStr + "合計金額" + ISEMPTY_MSG);
            errorFlag = true;
        } else if (shipmentInformationDetailDto.getTotalPrice().length() >= 13) {
            failedList.add(checkTargetStr + "合計金額" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // 配送方法を確認
        if (shipmentInformationDetailDto.getDeliveryName() == null) {
            failedList.add(checkTargetStr + "配送方法" + ISEMPTY_MSG);
            errorFlag = true;
        } else if (shipmentInformationDetailDto.getDeliveryName().length() >= 41) {
            failedList.add(checkTargetStr + "配送方法" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // 支払方法を確認
        if (shipmentInformationDetailDto.getPaymentName() == null) {
            failedList.add(checkTargetStr + "支払方法" + ISEMPTY_MSG);
            errorFlag = true;
        } else if (shipmentInformationDetailDto.getPaymentName().length() >= 41) {
            failedList.add(checkTargetStr + "支払方法" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // 配達指定日を確認
        String deliveryDesignatedDay =
                        dateUtility.formatYmdWithSlash(shipmentInformationDetailDto.getDeliveryDesignatedDay());
        if (shipmentInformationDetailDto.getDeliveryDesignatedDay() != null && deliveryDesignatedDay.length() >= 15) {
            failedList.add(checkTargetStr + "配達指定日" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // 配達指定時間を確認
        if (shipmentInformationDetailDto.getDeliveryDesignatedTimeName() != null
            && shipmentInformationDetailDto.getDeliveryDesignatedTimeName().toString().length() >= 15) {
            failedList.add(checkTargetStr + "配達指定時間" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // 配送確認URLを確認
        if (shipmentInformationDetailDto.getDeliveryComfirmURL() == null) {
            failedList.add(checkTargetStr + "配送確認URL" + ISEMPTY_MSG);
            errorFlag = true;
        } else if (shipmentInformationDetailDto.getDeliveryComfirmURL().length() >= 201) {
            failedList.add(checkTargetStr + "配送確認URL" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // 送り状番号を確認
        if (shipmentInformationDetailDto.getInvoiceNo() == null) {
            failedList.add(checkTargetStr + "送り状番号" + ISEMPTY_MSG);
            errorFlag = true;
        } else if (shipmentInformationDetailDto.getInvoiceNo().length() >= 151) {
            failedList.add(checkTargetStr + "送り状番号" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // メールアドレスを確認
        if (shipmentInformationDetailDto.getMailAddress() == null) {
            failedList.add(checkTargetStr + "メールアドレス" + ISEMPTY_MSG);
            errorFlag = true;
            // 2023-renew No85-1 from here
        } else if (shipmentInformationDetailDto.getMailAddress().length() >= 257) {
            // 2023-renew No85-1 to here
            failedList.add(checkTargetStr + "メールアドレス" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // 商品リストを確認
        if (shipmentInformationDetailDto.getGoodsList() == null) {
            failedList.add(checkTargetStr + "商品リスト" + ISEMPTY_MSG);
            errorFlag = true;
        } else {

            for (WebApiGetShipmentInformationResponseDetailGoodsInfoDto goodsInfo : shipmentInformationDetailDto.getGoodsList()) {

                String goodsNoStr = "申込商品：" + goodsInfo.getGoodsNo();

                // 申込商品（商品リスト）を確認
                if (goodsInfo.getGoodsNo() == null) {
                    goodsNoStr = "申込商品：";
                    failedList.add(checkTargetStr + goodsNoStr + "　申込商品（商品リスト）" + ISEMPTY_MSG);
                    errorFlag = true;
                } else if (goodsInfo.getGoodsNo().length() >= 13) {
                    failedList.add(checkTargetStr + goodsNoStr + "　申込商品（商品リスト）" + MAXLENGTH_MSG);
                    errorFlag = true;
                }

                // 商品名（商品リスト）を確認
                if (goodsInfo.getGoodsName() == null) {
                    failedList.add(checkTargetStr + goodsNoStr + "　商品名（商品リスト）" + ISEMPTY_MSG);
                    errorFlag = true;
                } else if (goodsInfo.getGoodsName().length() >= 121) {
                    failedList.add(checkTargetStr + goodsNoStr + "　商品名（商品リスト）" + MAXLENGTH_MSG);
                    errorFlag = true;
                }

                // 数量（商品リスト）を確認
                if (goodsInfo.getCount() == null) {
                    failedList.add(checkTargetStr + goodsNoStr + "　数量（商品リスト）" + ISEMPTY_MSG);
                    errorFlag = true;
                } else if (goodsInfo.getCount().toString().length() >= 5) {
                    failedList.add(checkTargetStr + goodsNoStr + "　数量（商品リスト）" + MAXLENGTH_MSG);
                    errorFlag = true;
                }

                // 単位（商品リスト）を確認
                if (goodsInfo.getUnitName() == null) {
                    failedList.add(checkTargetStr + goodsNoStr + "　単位（商品リスト）" + ISEMPTY_MSG);
                    errorFlag = true;
                } else if (goodsInfo.getUnitName().length() >= 5) {
                    failedList.add(checkTargetStr + goodsNoStr + "　単位（商品リスト）" + MAXLENGTH_MSG);
                    errorFlag = true;
                }

                // 単価（商品リスト）を確認
                if (goodsInfo.getUnitPrice() == null) {
                    failedList.add(checkTargetStr + goodsNoStr + "　単価（商品リスト）" + ISEMPTY_MSG);
                    errorFlag = true;
                } else if (goodsInfo.getUnitPrice().length() >= 13) {
                    failedList.add(checkTargetStr + goodsNoStr + "　単価（商品リスト）" + MAXLENGTH_MSG);
                    errorFlag = true;
                }

                // 金額（商品リスト）を確認
                if (goodsInfo.getPrice() == null) {
                    failedList.add(checkTargetStr + goodsNoStr + "　金額（商品リスト）" + ISEMPTY_MSG);
                    errorFlag = true;
                } else if (goodsInfo.getPrice().length() >= 13) {
                    failedList.add(checkTargetStr + goodsNoStr + "　金額（商品リスト）" + MAXLENGTH_MSG);
                    errorFlag = true;
                }
            }
        }
        return errorFlag;
    }
}
// PDR Migrate Customization to here
