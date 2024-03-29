/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.restock;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailDeliveryStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.restock.ReStockAnnounceMailDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.MemberInfoDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.cuenote.api.CuenoteApiAddressImportRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.cuenote.api.CuenoteApiAddressImportResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.cuenote.api.CuenoteApiDeliveryReserveRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.cuenote.api.CuenoteApiMailSetRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockAddImportListDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.restock.ReStockAnnounceMailEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.cuenote.CuenoteApiAddressImportLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.cuenote.CuenoteApiDeliveryReserveLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.cuenote.CuenoteApiMailSetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.mail.SendAdminMailLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.NoMailRequiredMemberInfoLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.loginadvisability.LoginAdvisabilityGetCanNotLoginMemberLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.apache.http.conn.ConnectTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.SocketTimeoutException;
import java.net.URI;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 商品管理：入荷お知らせ抽象コントローラー
 *
 * @author st75001
 */
public class AbstractReStockController extends AbstractController {

    /**
     * 入荷お知らせヘルパー<br/>
     */
    protected final AbstractReStockHelper abstractReStockHelper;

    /**
     * 会員情報Dao
     */
    protected final MemberInfoDao memberInfoDao;

    /**
     * 入荷お知らせメールDao
     */
    protected final ReStockAnnounceMailDao reStockAnnounceMailDao;

    /**
     * Cuenote API アドレス帳インポートAPI logic
     */
    protected final CuenoteApiAddressImportLogic cuenoteApiAddressImportLogic;

    /**
     * Cuenote API メール文書セット複製 logic
     */
    protected final CuenoteApiMailSetLogic cuenoteApiMailSetLogic;

    /**
     * Cuenote API 配信情報予約API logic
     */
    protected final CuenoteApiDeliveryReserveLogic cuenoteApiDeliveryReserveLogic;

    /**
     * ログイン不可の会員情報取得ロジック
     */
    protected LoginAdvisabilityGetCanNotLoginMemberLogic loginAdvisabilityGetCanNotLoginMemberLogic;

    /**
     * メール不要の会員情報取得ロジック
     */
    protected NoMailRequiredMemberInfoLogic noMailRequiredMemberInfoLogic;

    /**
     * 管理者メール送信 logic
     */
    protected final SendAdminMailLogic sendAdminMailLogic;

    /**
     * 日付関連Helper取得
     */
    protected final DateUtility dateUtility;

    /**
     * ロガー
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractReStockController.class);

    /**
     * メッセージコード：不正操作
     */
    public static final String MSGCD_ILLEGAL_OPERATION = "AGG000001";

    /**
     * メール文書複製タイトル
     */
    public final static String CUENOTE_MAIL_SET_TITLE_HEAD = "入荷お知らせメール_";

    /**
     * 処理名(入荷お知らせメール)
     */
    public final static String PROCESS_NAME_DELIVERY_RESERVE = "入荷お知らせメール";

    /**
     * メール配信失敗
     */
    public final static String SEND_MAIL_ERROR_MESSAGE_CODE = "PDR-2023RENEW-0517-007-E";

    /**
     * メール配信文字数オーバー
     */
    public final static String SEND_MAIL_ERROR_OVER_LENGTH_MESSAGE_CODE = "PDR-2023RENEW-0517-009-E";

    /**
     * タイムアウトエラーメッセージ
     */
    public final static String API_ERROR_TIMEOUT_MESSAGE = "/API通信でタイムアウトが発生しました。";

    /**
     * タイムアウトエラーリカバリメッセージ
     */
    public final static String API_ERROR_TIMEOUT_RECOVERY_MESSAGE = "【リカバリ】時間を空けて再実行";

    /**
     * APIエラーメッセージ
     */
    public final static String API_ERROR_MESSAGE = "/API通信でAPIエラーが発生しました。";

    /**
     * APIエラーリカバリメッセージ
     */
    public final static String API_ERROR_RECOVERY_MESSAGE = "【リカバリ】システム管理者へお問合せください。";

    /**
     * 処理結果メール詳細メッセージ
     */
    public String mailMessageDetail;

    /**
     * メール配信件数
     */
    Integer sendMailRequestCount = 0;

    /**
     * 配信予約件数
     */
    Integer sendMailMemberCount = 0;

    /**
     * 商品コード除外リスト
     */
    List<String> skipGoodsCodeList = new ArrayList<>();

    /**
     *  顧客番号除外リスト
     */
    List<Integer> skipCustomerNoList = new ArrayList<>();

    /**
     * エラー詳細リスト
     */
    List<String> errDetailsList = new ArrayList<>();

    /**
     * コンストラクター
     */
    @Autowired
    public AbstractReStockController(AbstractReStockHelper abstractReStockHelper,
                                     MemberInfoDao memberInfoDao,
                                     ReStockAnnounceMailDao reStockAnnounceMailDao,
                                     CuenoteApiAddressImportLogic cuenoteApiAddressImportLogic,
                                     CuenoteApiMailSetLogic cuenoteApiMailSetLogic,
                                     CuenoteApiDeliveryReserveLogic cuenoteApiDeliveryReserveLogic,
                                     SendAdminMailLogic sendAdminMailLogic,
                                     LoginAdvisabilityGetCanNotLoginMemberLogic loginAdvisabilityGetCanNotLoginMemberLogic,
                                     NoMailRequiredMemberInfoLogic noMailRequiredMemberInfoLogic) {
        this.abstractReStockHelper = abstractReStockHelper;
        this.memberInfoDao = memberInfoDao;
        this.reStockAnnounceMailDao = reStockAnnounceMailDao;
        this.cuenoteApiAddressImportLogic = cuenoteApiAddressImportLogic;
        this.cuenoteApiMailSetLogic = cuenoteApiMailSetLogic;
        this.cuenoteApiDeliveryReserveLogic = cuenoteApiDeliveryReserveLogic;
        this.sendAdminMailLogic = sendAdminMailLogic;
        this.loginAdvisabilityGetCanNotLoginMemberLogic = loginAdvisabilityGetCanNotLoginMemberLogic;
        this.noMailRequiredMemberInfoLogic = noMailRequiredMemberInfoLogic;
        this.dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
    }

    /**
     * cuenote配信予約
     * @param adImportReqDtoList 入荷お知らせメールアドレス帳登録リスト
     * @param reStockAnnounceMailEntityList 入荷お知らせメールEntityリスト
     */
    protected void cuenoteMailReserve(List<ReStockAddImportListDto> adImportReqDtoList,
                                      List<ReStockAnnounceMailEntity> reStockAnnounceMailEntityList) {

        Timestamp currentTime = dateUtility.getCurrentTime();

        // Cuenote API アドレス帳インポートAPIリクエストデータ作成
        List<CuenoteApiAddressImportRequestDto> cuenoteApiAddressImportRequestDtoList =
                        createCuenoteApiAddressImportRequest(adImportReqDtoList, currentTime,
                                                             reStockAnnounceMailEntityList
                                                            );

        if (cuenoteApiAddressImportRequestDtoList.size() > 0) {

            String exceptionName;
            try {
                // アドレス帳インポートAPI呼出
                CuenoteApiAddressImportResponseDto cuenoteApiAddressImportResponseDto =
                                cuenoteApiAddressImportLogic.execute(cuenoteApiAddressImportRequestDtoList,
                                                                     PropertiesUtil.getSystemPropertiesValue(
                                                                                     "cuenote.api.path.goodsReceivedAddressImport"));
                // エラーの場合
                if (cuenoteApiAddressImportResponseDto == null) {
                    LOGGER.error("APIエラー");
                    throwMessage();
                }
            } catch (SocketTimeoutException | ConnectTimeoutException e) {
                LOGGER.error("タイムアウト例外処理が発生しました", e);

                exceptionName = "アドレス帳レコードインポート" + API_ERROR_TIMEOUT_MESSAGE;
                mailMessageDetail =
                                sendAdminMailLogic.createFailedMailDetail(exceptionName, PROCESS_NAME_DELIVERY_RESERVE,
                                                                          API_ERROR_TIMEOUT_RECOVERY_MESSAGE
                                                                         );
                // メール送信
                sendAdminMailLogic.execute(
                                mailMessageDetail, PROCESS_NAME_DELIVERY_RESERVE,
                                HTypeMailTemplateType.RESTOCK_SEND_ERROR_MAIL, true
                                          );
                throwMessage(SEND_MAIL_ERROR_MESSAGE_CODE);
            } catch (Exception e) {
                LOGGER.error("例外処理が発生しました", e);

                exceptionName = "アドレス帳レコードインポート" + API_ERROR_MESSAGE;
                mailMessageDetail =
                                sendAdminMailLogic.createFailedMailDetail(exceptionName, PROCESS_NAME_DELIVERY_RESERVE,
                                                                          API_ERROR_RECOVERY_MESSAGE
                                                                         );
                // メール送信
                sendAdminMailLogic.execute(
                                mailMessageDetail, PROCESS_NAME_DELIVERY_RESERVE,
                                HTypeMailTemplateType.RESTOCK_SEND_ERROR_MAIL, true
                                          );
                throwMessage(SEND_MAIL_ERROR_MESSAGE_CODE);
            }

            // メール文書複製API呼出
            String mailSetLocation = null;
            try {
                mailSetLocation = cuenoteApiMailSetLogic.execute(createCuenoteApiMailSetRequest());
                // エラーの場合
                if (StringUtil.isEmpty(mailSetLocation)) {
                    LOGGER.error("APIエラー");
                    throwMessage();
                }
            } catch (SocketTimeoutException | ConnectTimeoutException e) {
                LOGGER.error("タイムアウト例外処理が発生しました", e);

                exceptionName = "メール文書複製" + API_ERROR_TIMEOUT_MESSAGE;
                mailMessageDetail =
                                sendAdminMailLogic.createFailedMailDetail(exceptionName, PROCESS_NAME_DELIVERY_RESERVE,
                                                                          API_ERROR_TIMEOUT_RECOVERY_MESSAGE
                                                                         );
                // メール送信
                sendAdminMailLogic.execute(
                                mailMessageDetail, PROCESS_NAME_DELIVERY_RESERVE,
                                HTypeMailTemplateType.RESTOCK_SEND_ERROR_MAIL, true
                                          );
                throwMessage(SEND_MAIL_ERROR_MESSAGE_CODE);
            } catch (Exception e) {
                LOGGER.error("例外処理が発生しました", e);

                exceptionName = "メール文書複製" + API_ERROR_MESSAGE;
                mailMessageDetail =
                                sendAdminMailLogic.createFailedMailDetail(exceptionName, PROCESS_NAME_DELIVERY_RESERVE,
                                                                          API_ERROR_RECOVERY_MESSAGE
                                                                         );

                // メール送信
                sendAdminMailLogic.execute(
                                mailMessageDetail, PROCESS_NAME_DELIVERY_RESERVE,
                                HTypeMailTemplateType.RESTOCK_SEND_ERROR_MAIL, true
                                          );
                throwMessage(SEND_MAIL_ERROR_MESSAGE_CODE);
            }

            // 配信情報予約API呼出
            String deliveryReserveLocation = null;
            try {
                deliveryReserveLocation = cuenoteApiDeliveryReserveLogic.execute(
                                createCuenoteApiDeliveryReserveRequest(mailSetLocation));

                // エラーの場合
                if (StringUtil.isEmpty(deliveryReserveLocation)) {
                    LOGGER.error("APIエラー");
                    throwMessage();
                }
            } catch (SocketTimeoutException | ConnectTimeoutException e) {
                LOGGER.error("タイムアウト例外処理が発生しました", e);

                exceptionName = "配信情報予約" + API_ERROR_TIMEOUT_MESSAGE;
                mailMessageDetail =
                                sendAdminMailLogic.createFailedMailDetail(exceptionName, PROCESS_NAME_DELIVERY_RESERVE,
                                                                          API_ERROR_TIMEOUT_RECOVERY_MESSAGE
                                                                         );
                // メール送信
                sendAdminMailLogic.execute(
                                mailMessageDetail, PROCESS_NAME_DELIVERY_RESERVE,
                                HTypeMailTemplateType.RESTOCK_SEND_ERROR_MAIL, true
                                          );
                throwMessage(SEND_MAIL_ERROR_MESSAGE_CODE);
            } catch (Exception e) {
                LOGGER.error("例外処理が発生しました", e);

                exceptionName = "配信情報予約" + API_ERROR_MESSAGE;
                mailMessageDetail =
                                sendAdminMailLogic.createFailedMailDetail(exceptionName, PROCESS_NAME_DELIVERY_RESERVE,
                                                                          API_ERROR_RECOVERY_MESSAGE
                                                                         );

                // メール送信
                sendAdminMailLogic.execute(
                                mailMessageDetail, PROCESS_NAME_DELIVERY_RESERVE,
                                HTypeMailTemplateType.RESTOCK_SEND_ERROR_MAIL, true
                                          );
                throwMessage(SEND_MAIL_ERROR_MESSAGE_CODE);
            }

            URI deliveryReserveUri = null;
            try {
                deliveryReserveUri = new URI(deliveryReserveLocation);
            } catch (Exception e) {
                // URIを利用する場合はtry,catchが必要な為囲む
                LOGGER.error("入荷お知らせメール更新時に例外処理が発生しました", e);
                throwMessage();
            }
            String deliveryReservePath = deliveryReserveUri.getPath();
            String[] deliveryReserveParts = deliveryReservePath.split("/");
            String deliveryId = deliveryReserveParts[deliveryReserveParts.length - 1];

            // 入荷お知らせメール更新
            for (ReStockAnnounceMailEntity reStockAnnounceMailEntity : reStockAnnounceMailEntityList) {
                reStockAnnounceMailEntity.setDeliveryId(deliveryId);
                reStockAnnounceMailEntity.setDeliveryStatus(HTypeMailDeliveryStatus.DELIVERING);
                reStockAnnounceMailEntity.setUpdateTime(currentTime);
                reStockAnnounceMailDao.update(reStockAnnounceMailEntity);
            }
        }

        // 処理結果詳細生成
        // メールメッセージ初期化
        mailMessageDetail = sendAdminMailLogic.createSuccessDeliveryReserveMailDetail(sendMailRequestCount,
                                                                                      sendMailMemberCount,
                                                                                      skipGoodsCodeList,
                                                                                      skipCustomerNoList, errDetailsList
                                                                                     );
        // メール送信
        sendAdminMailLogic.execute(mailMessageDetail, PROCESS_NAME_DELIVERY_RESERVE,
                                   HTypeMailTemplateType.RESTOCK_SEND_MAIL, false);
    }

    /**
     * アドレス帳レコードインポートリクエストデータ作成
     *
     * @param adImportReqDtoList 入荷お知らせメールアドレス帳登録Dtoリスト
     * @param currentTime 当日日付
     * @param mainReStockAnnounceMailEntityList 入荷お知らせメールEntityリスト
     * @return Cuenote API アドレス帳インポートAPIリクエストDTOリスト
     */
    protected List<CuenoteApiAddressImportRequestDto> createCuenoteApiAddressImportRequest(List<ReStockAddImportListDto> adImportReqDtoList,
                                                                                           Timestamp currentTime,
                                                                                           List<ReStockAnnounceMailEntity> mainReStockAnnounceMailEntityList) {

        List<CuenoteApiAddressImportRequestDto> cuenoteApiAddressImportRequestDtoList = new ArrayList<>();
        // リクエストデータ作成
        ArrayList<String> goodsHtml = new ArrayList<>();

        // 会員情報リスト作成
        Set<Integer> uniqueCustomerNoList = new HashSet<>();
        for (ReStockAddImportListDto adImportReqDto : adImportReqDtoList) {
            if (adImportReqDto.getCustomerNo() != null) {
                uniqueCustomerNoList.add(adImportReqDto.getCustomerNo());
            }
        }
        List<Integer> customerNoList = new ArrayList<>(uniqueCustomerNoList);

        // メール不要の会員を取得
        List<Integer> skipNoMailRequiredCustomerNoList =
                        noMailRequiredMemberInfoLogic.getNoMailRequiredMemberInfoLogic(customerNoList);
        // オンラインログイン不可の会員を取得
        List<Integer> skipCantLoginCustomerNoList =
                        loginAdvisabilityGetCanNotLoginMemberLogic.getLoginAdvisabilityGetCanNotLoginMemberLogic(
                                        customerNoList);

        for (int i = 0; i < adImportReqDtoList.size(); i++) {

            // 次のデータを取得
            ReStockAddImportListDto reStockAddImportListDto = adImportReqDtoList.get(i);
            ReStockAddImportListDto nextData;
            if (i + 1 < adImportReqDtoList.size()) {
                nextData = adImportReqDtoList.get(i + 1);
            } else {
                nextData = null;
            }

            String customerNo = null;
            if (reStockAddImportListDto.getCustomerNo() != null) {
                customerNo = reStockAddImportListDto.getCustomerNo().toString();
            }
            String nextCustomerNo = null;
            if (nextData != null) {
                if (nextData.getCustomerNo() != null) {
                    nextCustomerNo = nextData.getCustomerNo().toString();
                }
            } else {
                // 次のデータがない場合は無効な顧客IDを設定
                nextCustomerNo = "-1";
            }
            // 会員が切り替わるタイミングでカウントアップ
            if (!(customerNo == null || customerNo.equals(nextCustomerNo))) {
                // 配信予約件数カウントアップ
                sendMailMemberCount = sendMailMemberCount + 1;
            }

            if (customerNo == null || !chkReStockSendMailSkipGoods(reStockAddImportListDto)) {
                // メール配信ステータスを更新
                updateReStockMailExclusion(reStockAddImportListDto, currentTime, mainReStockAnnounceMailEntityList);
            } else {
                // 商品情報をセット
                goodsHtml.add(abstractReStockHelper.setHtml(reStockAddImportListDto));
            }

            // 会員が切り替わる直前のタイミングで処理を実行
            if (customerNo == null || customerNo.equals(nextCustomerNo)) {
                continue;
            } else {
                if (!goodsHtml.isEmpty()) {
                    // メール配信除外チェック
                    if (!chkReStockSendMailSkipMember(
                                    reStockAddImportListDto, skipNoMailRequiredCustomerNoList,
                                    skipCantLoginCustomerNoList
                                                     )) {
                        // メール配信ステータスを更新
                        updateReStockMailExclusionMember(reStockAddImportListDto, currentTime,
                                                         mainReStockAnnounceMailEntityList);
                        // 商品情報を初期化
                        goodsHtml = new ArrayList<>();
                        continue;
                    }

                    // メール配信予約成功件数カウントアップ
                    sendMailRequestCount = sendMailRequestCount + 1;

                    String goodsInfo = abstractReStockHelper.createAddImportHtml(goodsHtml);
                    String csvEscapedValue = "\"" + goodsInfo.replace("\"", "\"\"") + "\"";
                    // 文字数チェック
                    if (csvEscapedValue.length() >= PropertiesUtil.getSystemPropertiesValueToInt(
                                    "cuenote.api.limit.body.length")) {
                        throwMessage(SEND_MAIL_ERROR_OVER_LENGTH_MESSAGE_CODE);
                    }
                    // APIのリクエストデータ作成
                    CuenoteApiAddressImportRequestDto adImportReqDto = new CuenoteApiAddressImportRequestDto();
                    adImportReqDto.setEmail(reStockAddImportListDto.getMemberInfoMail());
                    adImportReqDto.setOfficeName(reStockAddImportListDto.getMemberInfoLastName());
                    adImportReqDto.setGoodsInfo(csvEscapedValue);
                    cuenoteApiAddressImportRequestDtoList.add(adImportReqDto);

                    // 商品情報を初期化
                    goodsHtml = new ArrayList<>();
                }

            }
        }
        return cuenoteApiAddressImportRequestDtoList;
    }

    /**
     * 送信除外更新
     * @param reStockAddImportListDto 入荷お知らせメールアドレス帳登録Dto
     * @param currentTime 当日日付
     * @param mainReStockAnnounceMailEntityList 入荷お知らせメールEntityリスト
     */
    protected void updateReStockMailExclusion(ReStockAddImportListDto reStockAddImportListDto,
                                              Timestamp currentTime,
                                              List<ReStockAnnounceMailEntity> mainReStockAnnounceMailEntityList) {
        // メール配信ステータスを更新
        ReStockAnnounceMailEntity reStockAnnounceMailEntity = reStockAnnounceMailDao.getEntity(
                        reStockAddImportListDto.getGoodsSeq(), reStockAddImportListDto.getMemberInfoSeq(),
                        reStockAddImportListDto.getVersionNo()
                                                                                              );
        // 更新対象から削除
        mainReStockAnnounceMailEntityList.remove(reStockAnnounceMailEntity);
        reStockAnnounceMailEntity.setDeliveryStatus(HTypeMailDeliveryStatus.EXCLUSION);
        reStockAnnounceMailEntity.setUpdateTime(currentTime);
        reStockAnnounceMailDao.update(reStockAnnounceMailEntity);
    }

    /**
     * 送信除外更新(会員)
     * @param reStockAddImportListDto 入荷お知らせメールアドレス帳登録Dto
     * @param currentTime 当日日付
     * @param mainReStockAnnounceMailEntityList 入荷お知らせメールEntityリスト
     */
    protected void updateReStockMailExclusionMember(ReStockAddImportListDto reStockAddImportListDto,
                                                    Timestamp currentTime,
                                                    List<ReStockAnnounceMailEntity> mainReStockAnnounceMailEntityList) {
        // メール配信ステータスを更新
        List<ReStockAnnounceMailEntity> reStockAnnounceMailEntityList =
                        reStockAnnounceMailDao.getReStockAnnounceMailEntityMemberInfoSeqListForNotDelivery(
                                        reStockAddImportListDto.getMemberInfoSeq());
        // 更新対象から全て削除
        for (ReStockAnnounceMailEntity reStockAnnounceMailEntity : reStockAnnounceMailEntityList) {
            mainReStockAnnounceMailEntityList.remove(reStockAnnounceMailEntity);
            reStockAnnounceMailEntity.setDeliveryStatus(HTypeMailDeliveryStatus.EXCLUSION);
            reStockAnnounceMailEntity.setUpdateTime(currentTime);
            reStockAnnounceMailDao.update(reStockAnnounceMailEntity);
        }
    }

    /**
     * メール文書複製APIリクエストデータ作成
     *
     * @return Cuenote API メール文書複製APIリクエストDTO
     */
    protected CuenoteApiMailSetRequestDto createCuenoteApiMailSetRequest() {
        CuenoteApiMailSetRequestDto cuenoteApiMailSetRequestDto = new CuenoteApiMailSetRequestDto();
        cuenoteApiMailSetRequestDto.setOriginalMailId(
                        PropertiesUtil.getSystemPropertiesValue("cuenote.api.template.goodsReceived"));
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        cuenoteApiMailSetRequestDto.setTitle(CUENOTE_MAIL_SET_TITLE_HEAD + now.format(formatter));

        return cuenoteApiMailSetRequestDto;
    }

    /**
     * 配信情報予約APIリクエストデータ作成
     * @param mailSetLocation メール文書複製APIの戻り値
     *
     * @return Cuenote API 配信情報予約APIリクエストDTO
     */
    protected CuenoteApiDeliveryReserveRequestDto createCuenoteApiDeliveryReserveRequest(String mailSetLocation) {
        CuenoteApiDeliveryReserveRequestDto cuenoteApiDeliveryReserveRequestDto =
                        new CuenoteApiDeliveryReserveRequestDto();
        URI uri = null;
        try {
            uri = new URI(mailSetLocation);
        } catch (Exception e) {
            // URIを利用する場合はtry,catchが必要な為囲む
            LOGGER.error("入荷お知らせメール更新時に例外処理が発生しました", e);
        }
        String path = uri.getPath();
        String[] parts = path.split("/");
        Integer setMailId = Integer.parseInt(parts[parts.length - 1]);
        LocalDateTime fiveMinutesLater = LocalDateTime.now()
                                                      .plus(
                                                                      PropertiesUtil.getSystemPropertiesValueToInt(
                                                                                      "cuenote.api.goodsReceived.send.mail.extension"),
                                                                      ChronoUnit.MINUTES
                                                           );
        DateTimeFormatter formatterZ = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss+09:00");
        cuenoteApiDeliveryReserveRequestDto.setMailId(setMailId);
        cuenoteApiDeliveryReserveRequestDto.setAdBookId(
                        PropertiesUtil.getSystemPropertiesValue("cuenote.api.addressId.goodsReceived"));
        cuenoteApiDeliveryReserveRequestDto.setDeliveryTime(fiveMinutesLater.format(formatterZ));

        return cuenoteApiDeliveryReserveRequestDto;
    }

    /**
     * 入荷お知らせメール送信判定（商品）
     *
     * @param reStockAddImportListDto 入荷お知らせメールアドレス帳登録Dto
     * @return true：送信要 false：送信不要
     */
    protected boolean chkReStockSendMailSkipGoods(ReStockAddImportListDto reStockAddImportListDto) {
        boolean ret = true;

        if (!skipGoodsCodeList.contains(reStockAddImportListDto.getGoodsCode())) {
            // 商品販売判定
            if (!chkGoodsSaleStatus(reStockAddImportListDto)) {
                ret = false;
                skipGoodsCodeList.add(reStockAddImportListDto.getGoodsCode());
                errDetailsList.add("商品コード：" + reStockAddImportListDto.getGoodsCode() + "は非販売のため除外しました。");
            }

            // 商品公開判定
            if (!chkGoodsOpenStatus(reStockAddImportListDto)) {
                ret = false;
                skipGoodsCodeList.add(reStockAddImportListDto.getGoodsCode());
                errDetailsList.add("商品コード：" + reStockAddImportListDto.getGoodsCode() + "は非公開のため除外しました。");
            }
        } else {
            ret = false;
        }

        // 入荷お知らせ未登録会員
        if (memberNotReStock(reStockAddImportListDto)) {
            ret = false;
            errDetailsList.add("顧客番号：" + reStockAddImportListDto.getCustomerNo() + "は、" + "商品コード："
                               + reStockAddImportListDto.getGoodsCode() + "の入荷お知らせを解除しため除外しました。");
        }

        return ret;
    }

    /**
     * 入荷お知らせメール送信判定(会員)
     *
     * @param reStockAddImportListDto 入荷お知らせメールアドレス帳登録Dto
     * @param skipNoMailRequiredCustomerNoList メール不要会員リスト
     * @param skipCantLoginCustomerNoList オンラインログイン不可の会員リスト
     * @return true：送信要 false：送信不要
     */
    protected boolean chkReStockSendMailSkipMember(ReStockAddImportListDto reStockAddImportListDto,
                                                   List<Integer> skipNoMailRequiredCustomerNoList,
                                                   List<Integer> skipCantLoginCustomerNoList) {
        boolean ret = true;

        // 会員のメール希望判定
        if (sendMailPermit(reStockAddImportListDto.getCustomerNo(), skipNoMailRequiredCustomerNoList)) {
            ret = false;
            skipCustomerNoList.add(reStockAddImportListDto.getCustomerNo());
            errDetailsList.add("顧客番号：" + reStockAddImportListDto.getCustomerNo() + "はメールによるおトク情報を希望しないため除外しました。");
        }

        // ログイン可否判定
        if (memberLoginAdvisability(reStockAddImportListDto.getCustomerNo(), skipCantLoginCustomerNoList)) {
            ret = false;
            skipCustomerNoList.add(reStockAddImportListDto.getCustomerNo());
            errDetailsList.add("顧客番号：" + reStockAddImportListDto.getCustomerNo() + "はログイン不可のため除外しました。");
        }

        return ret;
    }

    /**
     * 商品販売判定
     *
     * @param reStockAddImportListDto 入荷お知らせメールアドレス帳登録Dto
     * @return true：販売 false：非販売
     */
    protected boolean chkGoodsSaleStatus(ReStockAddImportListDto reStockAddImportListDto) {
        // 商品系Helper取得
        GoodsUtility goodsUtility = ApplicationContextUtility.getBean(GoodsUtility.class);
        return goodsUtility.isGoodsSales(
                        reStockAddImportListDto.getSaleStatusPc(), reStockAddImportListDto.getSaleStartTimePc(),
                        reStockAddImportListDto.getSaleEndTimePc()
                                        );
    }

    /**
     * 商品公開判定
     *
     * @param reStockAddImportListDto 入荷お知らせメールアドレス帳登録Dto
     * @return true：公開 false：非公開
     */
    protected boolean chkGoodsOpenStatus(ReStockAddImportListDto reStockAddImportListDto) {
        // 商品系Helper取得
        GoodsUtility goodsUtility = ApplicationContextUtility.getBean(GoodsUtility.class);
        return goodsUtility.isGoodsOpen(
                        reStockAddImportListDto.getGoodsOpenStatusPc(), reStockAddImportListDto.getOpenStartTimePc(),
                        reStockAddImportListDto.getOpenEndTimePc()
                                       );
    }

    /**
     * 入荷お知らせ未登録会員
     *
     * @param reStockAddImportListDto 入荷お知らせメールアドレス帳登録Dto
     * @return true：入荷お知らせ未登録 false：入荷お知らせ登録済
     */
    protected boolean memberNotReStock(ReStockAddImportListDto reStockAddImportListDto) {
        // nullの場合、入荷お知らせ未登録
        return reStockAddImportListDto.getReStockGoodsSeq() == null;
    }

    /**
     * 会員メール希望判定
     *
     * @param customerNo 顧客番号
     * @param skipNoMailRequiredCustomerNoList メール不要会員リスト
     * @return true：希望しない false：希望する
     */
    protected boolean sendMailPermit(Integer customerNo, List<Integer> skipNoMailRequiredCustomerNoList) {
        // 含まれている場合メール不要
        return skipNoMailRequiredCustomerNoList.contains(customerNo);
    }

    /**
     * ログイン可否判定
     *
     * @param customerNo 顧客番号
     * @param skipCantLoginCustomerNoList オンラインログイン不可の会員リスト
     * @return ログイン可否 true：ログイン不可 false：ログイン可
     */
    protected boolean memberLoginAdvisability(Integer customerNo, List<Integer> skipCantLoginCustomerNoList) {

        // 含まれている場合ログイン不可
        return skipCantLoginCustomerNoList.contains(customerNo);
    }
}
