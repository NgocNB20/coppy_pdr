package jp.co.hankyuhanshin.itec.hitmall.api.memberinfo;

import io.swagger.annotations.ApiParam;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.AddressBookEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.AddressBookRegistRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.AddressBookUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CardBrandGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CardBrandGetResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CardInfoDeleteRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CardInfoDeleteResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CardInfoRegistRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CardInfoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CardInfoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.ConfirmMailEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.ConfirmMailGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CustomerNoNextValResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteDtoListResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteForCartCheckRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteForGoodsCheckRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteGoodsStockStatusGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteListDeleteRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteRegistRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.LoginAdvisabilityEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.LoginAdvisabilityGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.LoginFailureCountResetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.LoginFailureWithLockTimeUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.LoginInfoUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoAnnounceUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoByMemberInfoIdOrCustumerNoGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoByShopUniqueIdGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoByTelAndCustomerNoGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoByTelGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoConfirmScreenUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoImageDeleteRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoImageListResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoImageRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoMailScreenUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoMailUpdateSendMailRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoPasswordResetUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoPasswordUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoScreenRegistRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoScreenUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoUnregistInquirySendMailRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoUpdateMailGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberinfoBySeqAndStatusGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.OrderHistoryCancelOrderRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.OrderHistoryCancelOrderResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.OrderHistoryDetailsGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.OrderHistoryDtoListResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.OrderHistoryListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.PageInfoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.PageInfoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.PasswordResetMemberInfoGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.PasswordResetSendMailRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.ProxyAdminLoginRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.ProxyAdminLoginResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.ReceiveOrderForHistoryDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.RestockAnnounceDtoListResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.RestockAnnounceListDeleteRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.RestockAnnounceListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.RestockAnnounceRegistRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.ResultCountResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.ResultFlagResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.SendMailForLockAccountByRegistCreditErrorRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.UpdateLoginFailureUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAccessType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCancelFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeConfirmMailType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.LockAccountByRegistCreditErrorMailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.favorite.FavoriteDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.favorite.FavoriteSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.image.MemberImageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.orderhistory.CancelOrderHistoryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.orderhistory.OrderHistoryListDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.restockannounce.RestockAnnounceDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.restockannounce.RestockAnnounceSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.ComResultDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSummarySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderForHistoryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.member.WebApiRepUserFaxRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.member.WebApiRepUserMailaddressRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.member.WebApiRepUserNoticeRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiCancelOrderRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiCancelOrderResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdministratorEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.addressbook.AddressBookEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.confirmmail.ConfirmMailEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.favorite.FavoriteEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.loginadvisability.LoginAdvisabilityEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.CardBrandEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.administrator.AdminAuthGroupDetailLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.administrator.AdminLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoEcUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoGetCutomerNoNextValLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoUnregistInquirySendMailLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoUpdateSendMailLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.addressbook.AddressBookUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.cardinfo.CardInfoLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.confirmmail.ConfirmMailGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.favorite.FavoriteDataCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.favorite.FavoriteGoodsStockStatusGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.loginadvisability.LoginAdvisabilityGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.password.PasswordResetSendMailLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.CardBrandGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiCancelOrderLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiRepUserFaxLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiRepUserMailaddressLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiRepUserNoticeLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.access.AccessRegistService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoMailUpdateSendMailService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoMailUpdateService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoProcessCompleteMailSendService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoRegistService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoUpdateMailGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoUpdateService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.addressbook.AddressBookGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.addressbook.AddressBookRegistService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.card.LockAccountByRegistCreditErrorMailService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.favorite.FavoriteListDeleteService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.favorite.FavoriteListGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.favorite.FavoriteRegistService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.image.MemberInfoListImageService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.image.MemberInfoSaveImageService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.orderhistory.CancelOrderSendMailService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.orderhistory.OrderHistoryDetailsGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.orderhistory.OrderHistoryListGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.password.MemberInfoPasswordUpdateService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.password.PasswordResetMemberInfoGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.restockannounce.RestockAnnounceListDeleteService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.restockannounce.RestockAnnounceListGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.restockannounce.RestockAnnounceRegistService;
import jp.co.hankyuhanshin.itec.hitmall.service.process.AsyncService;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.utility.AsyncTaskUtility;
import jp.co.hankyuhanshin.itec.hitmall.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.web.PageInfoHelper;
import jp.co.hankyuhanshin.itec.hitmall.web.SessionParamsUtil;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelException;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationLogUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * 会員情報Controllerクラス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RestController
public class MemberInfoController extends AbstractController implements MemberinfoApi {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MemberInfoController.class);

    /**
     * 不正遷移
     */
    protected static final String MSGCD_DB_UNIQUE_CONFIRMMAIL_PASSWORD_FAIL = "APX000101";

    /**
     * DB一意制約エラー
     */
    private static final String MSGCD_DB_UNIQUE_MEMBERID_FAIL_CONFIRM = "AMR000502";

    /**
     * ログイン失敗エラー
     */
    public static final String MSGCD_ADMIN_LOGIN_ERROR = "PKG-3786-004-A-E";

    // 2023-renew AddNo2 from here
    /**
     * 会員情報失敗エラー
     */
    String MSGCD_MEMBERINFOENTITYDTO_NULL = "SMM000201";
    // 2023-renew AddNo2 to here

    // 2023-renew No0 from here
    /**
     * 会員情報変更通知メール送信失敗エラー
     */
    public static final String MSGCD_SEND_ERROR_MEMBERINFO_UPDATE_MAIL = "PDR-2023RENEW-0-003-";
    // 2023-renew No0 to here

    /**
     * 会員情報更新
     */
    private final MemberInfoUpdateLogic memberInfoUpdateLogic;

    /**
     * パスワード再設定メール送信ロジッククラス
     */
    private final PasswordResetSendMailLogic passwordResetSendMailLogic;

    /**
     * 会員情報取得サービス
     */
    private final MemberInfoGetService memberInfoGetService;

    /**
     * EC会員情報更新ロジック
     */
    private final MemberInfoEcUpdateLogic memberInfoEcUpdateLogic;

    /**
     * WEB-API連携 会員情報更新
     */
    private final WebApiRepUserMailaddressLogic webApiRepUserMailaddressLogic;

    /**
     * 商品Helper
     */
    private final MemberInfoHelper memberInfoHelper;

    /**
     * 非同期処理サービス
     */
    private final AsyncService asyncService;

    /**
     * 会員パスワード変更
     */
    private final MemberInfoPasswordUpdateService memberInfoPasswordUpdateService;

    /**
     * 会員メールアドレス変更メール送信サービス
     */
    private final MemberInfoMailUpdateSendMailService memberInfoMailUpdateSendMailService;

    /**
     * 会員メールアドレス変更サービス
     */
    private final MemberInfoMailUpdateService memberInfoMailUpdateService;

    /**
     * 変換ユーティリティクラス
     */
    private final ConversionUtility conversionUtility;

    /**
     * アドレス帳情報更新ロジック
     */
    private final AddressBookUpdateLogic addressBookUpdateLogic;

    /**
     * 会員登録完了メール送信サービス
     */
    private final MemberInfoProcessCompleteMailSendService memberInfoProcessCompleteMailSendService;

    /**
     * カード情報操作（取得・登録・削除）ロジック
     */
    private final CardInfoLogic cardInfoLogic;

    /**
     * 未登録会員照会発生メール送信 ロジック
     */
    public final MemberInfoUnregistInquirySendMailLogic memberInfoUnregistInquirySendMailLogic;

    /**
     * 会員登録サービス実装
     */
    private final MemberInfoRegistService memberInfoRegistService;

    /**
     * アクセス情報登録
     */
    private final AccessRegistService accessRegistService;

    /**
     * 会員情報更新サービス
     */
    private final MemberInfoUpdateService memberInfoUpdateService;

    /**
     * WEB-API連携 会員お知らせ情報更新
     */
    private final WebApiRepUserNoticeLogic webApiRepUserNoticeLogic;

    // 2023-renew No85-1 from here
    /**
     * WEB-API連携 会員FAX情報更新
     */
    private final WebApiRepUserFaxLogic webApiRepUserFaxLogic;
    // 2023-renew No85-1 to here

    /**
     * お気に入り情報リスト削除サービス
     */
    private final FavoriteListDeleteService favoriteListDeleteService;

    /**
     * アドレス帳情報取得サービス
     */
    private final AddressBookGetService addressBookGetService;

    /**
     * 確認メール情報取得ロジック
     */
    private final ConfirmMailGetLogic confirmMailGetLogic;

    /**
     * ログイン可否判定ロジック
     */
    private final LoginAdvisabilityGetLogic loginAdvisabilityGetLogic;

    /**
     * 会員情報取得ロジック
     */
    private final MemberInfoGetLogic memberInfoGetLogic;

    /**
     * パスワード再設定会員情報取得サービス<br/>
     */
    private final PasswordResetMemberInfoGetService passwordResetMemberInfoGetService;

    /**
     * 会員変更メールアドレス取得サービス
     */
    private final MemberInfoUpdateMailGetService memberInfoUpdateMailGetService;

    /**
     * お気に入り在庫状況表示取得
     */
    private final FavoriteGoodsStockStatusGetLogic favoriteGoodsStockStatusGetLogic;

    /**
     * お気に入りチェックロジック
     */
    private final FavoriteDataCheckLogic favoriteDataCheckLogic;

    /**
     * お気に入り情報リスト取得（ショップ指定）
     */
    private final FavoriteListGetService favoriteListGetService;

    /**
     * 注文履歴詳細情報取得サービス
     */
    private final OrderHistoryDetailsGetService orderHistoryDetailsGetService;

    /**
     * 注文履歴情報リスト取得サービス
     */
    private final OrderHistoryListGetService orderHistoryListGetService;

    /**
     * アドレス帳情報登録サービス
     */
    private final AddressBookRegistService addressBookRegistService;

    /**
     * お気に入り情報登録サービス
     */
    private final FavoriteRegistService favoriteRegistService;

    /**
     * 運営者情報操作ロジック
     */
    private final AdminLogic adminLogic;

    /**
     * 権限グループ詳細ロジック
     */
    private final AdminAuthGroupDetailLogic adminAuthGroupDetailLogic;

    /**
     * 顧客番号取得ロジック
     */
    private final MemberInfoGetCutomerNoNextValLogic memberInfoGetCutomerNoNextValLogic;

    // 2023-renew AddNo2 from here
    /**
     * 会員情報変更メール送信ロジック
     */
    public final MemberInfoUpdateSendMailLogic memberInfoUpdateSendMailLogic;
    // 2023-renew AddNo2 to here
    // 2023-renew No22 from here
    /**
     * 会員イメージサービスインターフェース
     */
    private final MemberInfoSaveImageService memberInfoSaveImageService;

    /**
     * 会員画像一覧サービスインターフェース
     */
    private final MemberInfoListImageService memberInfoListImageService;
    // 2023-renew No22 to here
    // 2023-renew No60 from here
    /**
     * カードのブランドサービス
     */
    private final CardBrandGetLogic cardBrandGetLogic;

    /**
     * アカウントロック通知（クレジット登録エラー）メール送信サービス
     */
    private final LockAccountByRegistCreditErrorMailService lockAccountByRegistCreditErrorMailService;
    // 2023-renew No60 to here
    // 2023-renew No65 from here
    /**
     * 入荷お知らせ情報登録サービス
     */
    private final RestockAnnounceRegistService restockAnnounceRegistService;

    /**
     * 入荷お知らせ情報リスト取得サービス
     */
    private final RestockAnnounceListGetService restockAnnounceListGetService;

    /**
     * 入荷お知らせ情報リスト削除サービス
     */
    private final RestockAnnounceListDeleteService restockAnnounceListDeleteService;
    // 2023-renew No65 to here
    // 2023-renew No68 from here
    /**
     * WEB-API連携クラス 注文キャンセル
     */
    private final WebApiCancelOrderLogic webApiCancelOrderLogic;

    /**
     * 注文キャンセルメール送信サービス
     */
    private final CancelOrderSendMailService cancelOrderSendMailService;

    /**
     * 受注サマリ情報取得ロジック
     */
    private final OrderSummaryGetLogic orderSummaryGetLogic;

    /**
     * 受注サマリー更新ロジック
     */
    private final OrderSummaryUpdateLogic orderSummaryUpdateLogic;

    /**
     * 日付関連Utilityクラス
     */
    private final DateUtility dateUtility;
    // 2023-renew No68 to here

    /**
     * コンストラクタ
     */
    @Autowired
    public MemberInfoController(MemberInfoUpdateLogic memberInfoUpdateLogic,
                                PasswordResetSendMailLogic passwordResetSendMailLogic,
                                MemberInfoGetService memberInfoGetService,
                                MemberInfoEcUpdateLogic memberInfoEcUpdateLogic,
                                WebApiRepUserMailaddressLogic webApiRepUserMailaddressLogic,
                                MemberInfoHelper memberInfoHelper,
                                AsyncService asyncService,
                                MemberInfoPasswordUpdateService memberInfoPasswordUpdateService,
                                MemberInfoMailUpdateSendMailService memberInfoMailUpdateSendMailService,
                                MemberInfoMailUpdateService memberInfoMailUpdateService,
                                ConversionUtility conversionUtility,
                                AddressBookUpdateLogic addressBookUpdateLogic,
                                MemberInfoProcessCompleteMailSendService memberInfoProcessCompleteMailSendService,
                                CardInfoLogic cardInfoLogic,
                                MemberInfoUnregistInquirySendMailLogic memberInfoUnregistInquirySendMailLogic,
                                MemberInfoRegistService memberInfoRegistService,
                                AccessRegistService accessRegistService,
                                MemberInfoUpdateService memberInfoUpdateService,
                                WebApiRepUserNoticeLogic webApiRepUserNoticeLogic,
                                WebApiRepUserFaxLogic webApiRepUserFaxLogic,
                                FavoriteListDeleteService favoriteListDeleteService,
                                AddressBookGetService addressBookGetService,
                                ConfirmMailGetLogic confirmMailGetLogic,
                                LoginAdvisabilityGetLogic loginAdvisabilityGetLogic,
                                MemberInfoGetLogic memberInfoGetLogic,
                                PasswordResetMemberInfoGetService passwordResetMemberInfoGetService,
                                MemberInfoUpdateMailGetService memberInfoUpdateMailGetService,
                                FavoriteGoodsStockStatusGetLogic favoriteGoodsStockStatusGetLogic,
                                FavoriteDataCheckLogic favoriteDataCheckLogic,
                                FavoriteListGetService favoriteListGetService,
                                OrderHistoryDetailsGetService orderHistoryDetailsGetService,
                                OrderHistoryListGetService orderHistoryListGetService,
                                AddressBookRegistService addressBookRegistService,
                                FavoriteRegistService favoriteRegistService,
                                AdminLogic adminLogic,
                                AdminAuthGroupDetailLogic adminAuthGroupDetailLogic,
                                MemberInfoSaveImageService memberInfoSaveImageService,
                                MemberInfoListImageService memberInfoListImageService,
                                MemberInfoGetCutomerNoNextValLogic memberInfoGetCutomerNoNextValLogic,
                                MemberInfoUpdateSendMailLogic memberInfoUpdateSendMailLogic,
                                CardBrandGetLogic cardBrandGetLogic,
                                LockAccountByRegistCreditErrorMailService lockAccountByRegistCreditErrorMailService,
                                RestockAnnounceRegistService restockAnnounceRegistService,
                                RestockAnnounceListGetService restockAnnounceListGetService,
                                RestockAnnounceListDeleteService restockAnnounceListDeleteService,
                                WebApiCancelOrderLogic webApiCancelOrderLogic,
                                CancelOrderSendMailService cancelOrderSendMailService,
                                OrderSummaryGetLogic orderSummaryGetLogic,
                                OrderSummaryUpdateLogic orderSummaryUpdateLogic,
                                DateUtility dateUtility) {
        this.memberInfoUpdateLogic = memberInfoUpdateLogic;
        this.passwordResetSendMailLogic = passwordResetSendMailLogic;
        this.memberInfoGetService = memberInfoGetService;
        this.memberInfoEcUpdateLogic = memberInfoEcUpdateLogic;
        this.webApiRepUserMailaddressLogic = webApiRepUserMailaddressLogic;
        this.memberInfoHelper = memberInfoHelper;
        this.asyncService = asyncService;
        this.memberInfoPasswordUpdateService = memberInfoPasswordUpdateService;
        this.memberInfoMailUpdateSendMailService = memberInfoMailUpdateSendMailService;
        this.memberInfoMailUpdateService = memberInfoMailUpdateService;
        this.conversionUtility = conversionUtility;
        this.addressBookUpdateLogic = addressBookUpdateLogic;
        this.memberInfoProcessCompleteMailSendService = memberInfoProcessCompleteMailSendService;
        this.cardInfoLogic = cardInfoLogic;
        this.memberInfoUnregistInquirySendMailLogic = memberInfoUnregistInquirySendMailLogic;
        this.memberInfoRegistService = memberInfoRegistService;
        this.accessRegistService = accessRegistService;
        this.memberInfoUpdateService = memberInfoUpdateService;
        this.webApiRepUserNoticeLogic = webApiRepUserNoticeLogic;
        // 2023-renew No85-1 from here
        this.webApiRepUserFaxLogic = webApiRepUserFaxLogic;
        // 2023-renew No85-1 to here
        this.favoriteListDeleteService = favoriteListDeleteService;
        this.addressBookGetService = addressBookGetService;
        this.confirmMailGetLogic = confirmMailGetLogic;
        this.loginAdvisabilityGetLogic = loginAdvisabilityGetLogic;
        this.memberInfoGetLogic = memberInfoGetLogic;
        this.passwordResetMemberInfoGetService = passwordResetMemberInfoGetService;
        this.memberInfoUpdateMailGetService = memberInfoUpdateMailGetService;
        this.favoriteGoodsStockStatusGetLogic = favoriteGoodsStockStatusGetLogic;
        this.favoriteDataCheckLogic = favoriteDataCheckLogic;
        this.favoriteListGetService = favoriteListGetService;
        this.orderHistoryDetailsGetService = orderHistoryDetailsGetService;
        this.orderHistoryListGetService = orderHistoryListGetService;
        this.addressBookRegistService = addressBookRegistService;
        this.favoriteRegistService = favoriteRegistService;
        this.adminLogic = adminLogic;
        this.adminAuthGroupDetailLogic = adminAuthGroupDetailLogic;
        this.memberInfoGetCutomerNoNextValLogic = memberInfoGetCutomerNoNextValLogic;
        // 2023-renew No22 from here
        this.memberInfoSaveImageService = memberInfoSaveImageService;
        this.memberInfoListImageService = memberInfoListImageService;
        // 2023-renew No22 to here
        // 2023-renew AddNo2 from here
        this.memberInfoUpdateSendMailLogic = memberInfoUpdateSendMailLogic;
        // 2023-renew AddNo2 to here
        // 2023-renew No60 from here
        this.cardBrandGetLogic = cardBrandGetLogic;
        this.lockAccountByRegistCreditErrorMailService = lockAccountByRegistCreditErrorMailService;
        // 2023-renew No60 to here
        // 2023-renew No65 from here
        this.restockAnnounceRegistService = restockAnnounceRegistService;
        this.restockAnnounceListGetService = restockAnnounceListGetService;
        this.restockAnnounceListDeleteService = restockAnnounceListDeleteService;
        // 2023-renew No65 to here
        // 2023-renew No68 from here
        this.webApiCancelOrderLogic = webApiCancelOrderLogic;
        this.cancelOrderSendMailService = cancelOrderSendMailService;
        this.orderSummaryGetLogic = orderSummaryGetLogic;
        this.orderSummaryUpdateLogic = orderSummaryUpdateLogic;
        this.dateUtility = dateUtility;
        // 2023-renew No68 to here
    }

    /**
     * DELETE /memberinfo/favorite/list : お気に入り情報リスト削除
     * お気に入り情報リスト削除
     *
     * @param favoriteListDeleteRequest お気に入り情報リスト削除リクエスト (required)
     * @return 結果カウントレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<ResultCountResponse> deleteFavoriteList(@Valid FavoriteListDeleteRequest favoriteListDeleteRequest) {

        int execute = favoriteListDeleteService.execute(favoriteListDeleteRequest.getMemberInfoSeq(),
                                                        favoriteListDeleteRequest.getGcd()
                                                       );

        ResultCountResponse response = new ResultCountResponse();
        response.setResultCount(execute);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /memberinfo/addressbook/{addressBookSeq} : アドレス帳情報取得
     * アドレス帳情報取得
     *
     * @param addressBookSeq       アドレス帳SEQ (required)
     * @param memberInfoGetRequest アドレス帳情報取得リクエスト (required)
     * @return アドレス帳Entityレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<AddressBookEntityResponse> getAddressbook(Integer addressBookSeq,
                                                                    @NotNull @Valid MemberInfoGetRequest memberInfoGetRequest) {
        AddressBookEntityResponse response;
        AddressBookEntity addressBookEntity =
                        addressBookGetService.execute(memberInfoGetRequest.getMemberInfoSeq(), addressBookSeq);

        try {
            response = memberInfoHelper.toAddressBookEntityResponse(addressBookEntity);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new AppLevelException(e.getMessage());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /memberinfo/confirmmail : 確認メール情報取得
     * 確認メール情報取得
     *
     * @param confirmMailGetRequest 確認メール情報取得リクエスト (optional)
     * @return 確認メールEntityレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<ConfirmMailEntityResponse> getByConfirmMail(@Valid ConfirmMailGetRequest confirmMailGetRequest) {

        ConfirmMailEntityResponse response;
        ConfirmMailEntity confirmMailEntity = confirmMailGetLogic.execute(confirmMailGetRequest.getPassword(),
                                                                          EnumTypeUtil.getEnumFromValue(
                                                                                          HTypeConfirmMailType.class,
                                                                                          confirmMailGetRequest.getConfirmMailType()
                                                                                                       )
                                                                         );

        try {
            response = memberInfoHelper.toConfirmMailEntityResponse(confirmMailEntity);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new AppLevelException(e.getMessage());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /memberinfo/loginadvisability : ログイン可否判定取得
     * ログイン可否判定取得
     *
     * @param loginAdvisabilityGetRequest ログイン可否判定取得リクエスト (optional)
     * @return ログイン可否判定Entityレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<LoginAdvisabilityEntityResponse> getByLoginAdvisability(@Valid LoginAdvisabilityGetRequest loginAdvisabilityGetRequest) {

        LoginAdvisabilityEntityResponse response;

        MemberInfoEntity memberInfoCheckEntity = memberInfoHelper.toMemberInfoEntity(loginAdvisabilityGetRequest);

        LoginAdvisabilityEntity loginAdvisabilityEntity =
                        loginAdvisabilityGetLogic.getLoginAdvisabilityPdrEntityByMemberInfo(memberInfoCheckEntity);

        try {
            response = memberInfoHelper.toLoginAdvisabilityEntityResponse(loginAdvisabilityEntity);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new AppLevelException(e.getMessage());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /memberinfo/by-memberinfoid-or-customerno : 会員情報取得
     * 会員情報取得
     *
     * @param memberInfoByMemberInfoIdOrCustumerNoGetRequest 会員情報取得リクエスト (optional)
     * @return 会員Entityレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<MemberInfoEntityResponse> getByMemberInfoOrCustomerNo(@Valid MemberInfoByMemberInfoIdOrCustumerNoGetRequest memberInfoByMemberInfoIdOrCustumerNoGetRequest) {

        MemberInfoEntityResponse response;

        MemberInfoEntity memberInfoPdrEntity = memberInfoGetLogic.getMemberInfoEntityByMemberInfoIdOrCustomerNo(
                        memberInfoByMemberInfoIdOrCustumerNoGetRequest.getMemberInfoIdOrCustomerNo());

        try {

            response = memberInfoHelper.toMemberInfoEntityResponse(memberInfoPdrEntity);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new AppLevelException(e.getMessage());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /memberinfo/{memberInfoSeq} : 会員取得取得
     * 会員取得取得
     *
     * @param memberInfoSeq 会員情報SEQ (required)
     * @return 会員Entityレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<MemberInfoEntityResponse> getByMemberInfoSeq(Integer memberInfoSeq) {

        MemberInfoEntityResponse response;

        MemberInfoEntity memberInfoEntity = memberInfoGetService.execute(memberInfoSeq);

        try {

            response = memberInfoHelper.toMemberInfoEntityResponse(memberInfoEntity);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new AppLevelException(e.getMessage());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /memberinfo/pasword/reset : パスワード再設定会員情報取得
     * パスワード再設定会員情報取得
     *
     * @param memberInfoUpdateMailGetRequest パスワード再設定会員情報取得リクエスト (optional)
     * @return 会員Entityレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<MemberInfoEntityResponse> getByPasswordReset(@Valid PasswordResetMemberInfoGetRequest memberInfoUpdateMailGetRequest) {

        MemberInfoEntityResponse response;

        MemberInfoEntity memberInfoEntity =
                        passwordResetMemberInfoGetService.execute(memberInfoUpdateMailGetRequest.getMid());

        try {
            response = memberInfoHelper.toMemberInfoEntityResponse(memberInfoEntity);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new AppLevelException(e.getMessage());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /memberinfo/by-shop-unique-id : 会員情報取得
     * 会員情報取得
     *
     * @param memberInfoByShopUniqueIdGetRequest ショップユニークId取得リクエスト (optional)
     * @return 会員Entityレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<MemberInfoEntityResponse> getByShopUniqueId(@Valid MemberInfoByShopUniqueIdGetRequest memberInfoByShopUniqueIdGetRequest) {

        MemberInfoEntityResponse response;

        MemberInfoEntity memberInfoEntity =
                        memberInfoGetLogic.execute(memberInfoByShopUniqueIdGetRequest.getShopUniqueId());

        try {
            response = memberInfoHelper.toMemberInfoEntityResponse(memberInfoEntity);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new AppLevelException(e.getMessage());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /memberinfo/by-tel : 電話番号より会員情報取得
     * 電話番号より会員情報取得
     *
     * @param memberInfoByTelGetRequest 電話番号より会員情報取得リクエスト (optional)
     * @return 会員Entityレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<MemberInfoEntityResponse> getByTel(@Valid MemberInfoByTelGetRequest memberInfoByTelGetRequest) {

        MemberInfoEntityResponse response;

        MemberInfoEntity memberInfoEntity = memberInfoGetLogic.getMemberInfoEntityByMemberInfoTel(
                        memberInfoByTelGetRequest.getMemberInfoTel());

        try {
            response = memberInfoHelper.toMemberInfoEntityResponse(memberInfoEntity);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new AppLevelException(e.getMessage());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /memberinfo/by-tel-customerno : 電話番号と顧客番号から会員情報取得
     * 電話番号と顧客番号から会員情報取得
     *
     * @param memberInfoByTelAndCustomerNoGetRequest 電話番号と顧客番号から会員情報取得リクエスト (optional)
     * @return 会員Entityレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<MemberInfoEntityResponse> getByTelCustomerNo(@Valid MemberInfoByTelAndCustomerNoGetRequest memberInfoByTelAndCustomerNoGetRequest) {

        MemberInfoEntityResponse response;

        MemberInfoEntity memberInfoEntity = memberInfoGetLogic.getMemberInfoEntityByMemberInfoTelAndCustomerNo(
                        memberInfoByTelAndCustomerNoGetRequest.getMemberInfoTel(),
                        memberInfoByTelAndCustomerNoGetRequest.getCustomerNo()
                                                                                                              );

        try {
            response = memberInfoHelper.toMemberInfoEntityResponse(memberInfoEntity);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new AppLevelException(e.getMessage());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /memberinfo/update-mail : 会員変更メールアドレス取得
     * 会員変更メールアドレス取得
     *
     * @param memberInfoUpdateMailGetRequest 会員変更メールアドレス取得リクエスト (optional)
     * @return 会員Entityレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<MemberInfoEntityResponse> getByUpdateMail(@Valid MemberInfoUpdateMailGetRequest memberInfoUpdateMailGetRequest) {

        MemberInfoEntityResponse response;

        MemberInfoEntity memberInfoEntity =
                        memberInfoUpdateMailGetService.execute(memberInfoUpdateMailGetRequest.getMid());

        try {
            response = memberInfoHelper.toMemberInfoEntityResponse(memberInfoEntity);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new AppLevelException(e.getMessage());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * POST /memberinfo/favorite/goods-stock-status/get : お気に入り情報リスト削除
     * お気に入り情報リスト削除
     *
     * @param favoriteGoodsStockStatusGetRequest お気に入り商品在庫状況取得リクエスト (required)
     * @return お気に入りDtoListレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<FavoriteDtoListResponse> getFavoriteByFavoriteGoodsStockStatus(
                    @ApiParam(value = "お気に入り商品在庫状況取得リクエスト", required = true) @Valid @RequestBody
                                    FavoriteGoodsStockStatusGetRequest favoriteGoodsStockStatusGetRequest) {

        List<FavoriteDto> favoriteDtoList;

        try {
            favoriteDtoList = memberInfoHelper.toFavoriteDtoList(favoriteGoodsStockStatusGetRequest);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new AppLevelException(e.getMessage());
        }

        List<FavoriteDto> stockFavoriteDtoList = favoriteGoodsStockStatusGetLogic.execute(favoriteDtoList);

        FavoriteDtoListResponse response = new FavoriteDtoListResponse();

        try {
            response.setFavoriteDtoListResponse(memberInfoHelper.toFavoriteDtoResponseList(stockFavoriteDtoList));
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new AppLevelException(e.getMessage());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * POST /memberinfo/favorite/for-cart-check/get : カートチェック用お気に入り情報リスト取得
     * カートチェック用お気に入り情報リスト取得
     *
     * @param favoriteForCartCheckRequest カートチェック用お気に入り情報リスト取得リクエスト (required)
     * @return お気に入りEntityListレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<List<FavoriteEntityResponse>> getFavoriteForCartCheck(
                    @ApiParam(value = "カートチェック用お気に入り情報リスト取得リクエスト", required = true) @Valid @RequestBody
                                    FavoriteForCartCheckRequest favoriteForCartCheckRequest) {

        List<FavoriteEntityResponse> response;
        List<FavoriteEntity> favoriteList = favoriteDataCheckLogic.getFavoriteEntityListForCart(
                        favoriteForCartCheckRequest.getMemberInfoSeq(),
                        memberInfoHelper.toCartDto(favoriteForCartCheckRequest.getCartDto())
                                                                                               );

        try {
            response = memberInfoHelper.toFavoriteEntityResponseList(favoriteList);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new AppLevelException(e.getMessage());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * POST /memberinfo/favorite/for-goods-check/get : 商品チェック用お気に入り情報リスト取得
     * 商品チェック用お気に入り情報リスト取得
     *
     * @param favoriteForGoodsCheckRequest 商品チェック用お気に入り情報リスト取得リクエスト (required)
     * @return お気に入りEntityListレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<List<FavoriteEntityResponse>> getFavoriteForGoodsCheck(
                    @ApiParam(value = "商品チェック用お気に入り情報リスト取得リクエスト", required = true) @Valid @RequestBody
                                    FavoriteForGoodsCheckRequest favoriteForGoodsCheckRequest) {

        List<FavoriteEntityResponse> response;

        List<GoodsDto> goodsDtoList;

        try {
            goodsDtoList = memberInfoHelper.toGoodsDtoList(favoriteForGoodsCheckRequest.getGoodsDtoList());
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new AppLevelException(e.getMessage());
        }

        List<FavoriteEntity> favoriteList = favoriteDataCheckLogic.getFavoriteEntityListForGoods(
                        favoriteForGoodsCheckRequest.getMemberInfoSeq(), goodsDtoList);

        try {
            response = memberInfoHelper.toFavoriteEntityResponseList(favoriteList);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new AppLevelException(e.getMessage());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /memberinfo/favorite/list : お気に入り情報リスト取得
     * お気に入り情報リスト取得
     *
     * @param favoriteListGetRequest お気に入り情報リスト取得リクエスト (optional)
     * @param pageInfoRequest        ページ情報リクエスト (optional)
     * @return お気に入りDtoListレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<FavoriteDtoListResponse> getFavoriteList(@Valid FavoriteListGetRequest favoriteListGetRequest,
                                                                   @Valid PageInfoRequest pageInfoRequest) {

        FavoriteSearchForDaoConditionDto favoriteConditionDto =
                        memberInfoHelper.toFavoriteConditionDto(favoriteListGetRequest);

        // ページング検索セットアップ
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);

        pageInfoHelper.setupPageInfo(favoriteConditionDto, conversionUtility.toString(pageInfoRequest.getPage()),
                                     pageInfoRequest.getLimit(), pageInfoRequest.getOrderBy(), pageInfoRequest.getSort()
                                    );

        List<FavoriteDto> favoriteDtoList =
                        favoriteListGetService.execute(favoriteConditionDto, favoriteConditionDto.getSiteType());

        // ページ情報レスポンスを設定
        PageInfoResponse pageInfoResponse = new PageInfoResponse();
        try {
            pageInfoHelper.setupResponsePager(favoriteConditionDto, pageInfoResponse);
        } catch (InvocationTargetException | IllegalAccessException e) {
            LOGGER.info("ページ情報レスポンスの設定異常： " + e.getMessage());
        }

        FavoriteDtoListResponse response = new FavoriteDtoListResponse();
        try {
            response.setFavoriteDtoListResponse(memberInfoHelper.toFavoriteDtoResponseList(favoriteDtoList));
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new AppLevelException(e.getMessage());
        }

        response.setPageInfo(pageInfoResponse);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /memberinfo/orderhistory/detail : 注文履歴詳細情報取得
     * 注文履歴詳細情報取得
     *
     * @param orderHistoryDetailsGetRequest 注文履歴詳細情報取得リクエスト (optional)
     * @return 注文履歴詳細Dto取得レスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<ReceiveOrderForHistoryDtoResponse> getOrderHistoryDetail(@Valid OrderHistoryDetailsGetRequest orderHistoryDetailsGetRequest) {

        ReceiveOrderForHistoryDtoResponse response;

        ReceiveOrderForHistoryDto receiveOrderForHistoryDto =
                        orderHistoryDetailsGetService.execute(orderHistoryDetailsGetRequest.getMemberInfoSeq(),
                                                              orderHistoryDetailsGetRequest.getOrderCode()
                                                             );

        response = memberInfoHelper.toReceiveOrderForHistoryDtoResponse(receiveOrderForHistoryDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /memberinfo/orderhistory/list : 注文履歴情報リスト取得
     * 注文履歴情報リスト取得
     *
     * @param orderHistoryListGetRequest 注文履歴情報リスト取得リクエスト (optional)
     * @param pageInfoRequest            ページ情報リクエスト (optional)
     * @return 注文履歴DtoListレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<OrderHistoryDtoListResponse> getOrderHistoryList(@Valid OrderHistoryListGetRequest orderHistoryListGetRequest,
                                                                           @Valid PageInfoRequest pageInfoRequest) {

        OrderSummarySearchForDaoConditionDto conditionDto = new OrderSummarySearchForDaoConditionDto();
        conditionDto.setMemberInfoSeq(orderHistoryListGetRequest.getMemberInfoSeq());

        // ページング検索セットアップ
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
        pageInfoHelper.setupPageInfo(conditionDto, pageInfoRequest.getPage().toString(), pageInfoRequest.getLimit());

        List<OrderHistoryListDto> resultList = orderHistoryListGetService.execute(conditionDto);

        // ページ情報レスポンスを設定
        PageInfoResponse pageInfoResponse = new PageInfoResponse();
        try {
            pageInfoHelper.setupResponsePager(conditionDto, pageInfoResponse);
        } catch (InvocationTargetException | IllegalAccessException e) {
            LOGGER.info("ページ情報レスポンスの設定異常： " + e.getMessage());
        }

        OrderHistoryDtoListResponse response = new OrderHistoryDtoListResponse();

        try {
            response.setOrderHistoryDtoListResponse(memberInfoHelper.toOrderHistoryDtoResponseList(resultList));
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new AppLevelException(e.getMessage());
        }

        response.setPageInfo(pageInfoResponse);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * POST /memberinfo/addressbook : アドレス帳情報登録
     * アドレス帳情報登録
     *
     * @param addressBookRegistRequest アドレス帳情報登録リクエスト (required)
     * @return 結果カウントレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<ResultCountResponse> registAddressbook(@Valid AddressBookRegistRequest addressBookRegistRequest) {

        ResultCountResponse response = new ResultCountResponse();
        AddressBookEntity addressBookEntity = memberInfoHelper.toAddressBookEntity(addressBookRegistRequest);

        int result = addressBookRegistService.execute(addressBookEntity);
        response.setResultCount(result);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * POST /memberinfo/favorite : お気に入り情報登録
     * お気に入り情報登録
     *
     * @param favoriteRegistRequest お気に入り情報登録リクエスト (required)
     * @return 結果カウントレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<ResultCountResponse> registFavorite(@Valid FavoriteRegistRequest favoriteRegistRequest) {

        ResultCountResponse response = new ResultCountResponse();
        int result = favoriteRegistService.execute(favoriteRegistRequest.getMemberInfoSeq(),
                                                   favoriteRegistRequest.getGcd(), HTypeSiteType.FRONT_PC
                                                  );
        response.setResultCount(result);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * POST /memberinfo/memberinfo-screen : 本会員登録画面用会員登録
     * 本会員登録画面用会員登録
     *
     * @param memberInfoScreenRegistRequest 本会員登録画面用会員登録リクエスト (required)
     * @return 成功
     */
    @Override
    // 2023-renew No22 from here
    public ResponseEntity<MemberInfoEntityResponse> registMemberInfoScreen(@Valid MemberInfoScreenRegistRequest memberInfoScreenRegistRequest) {
        // 2023-renew No22 to here

        // 会員エンティティ情報の作成
        MemberInfoEntity memberInfoEntity =
                        memberInfoHelper.toMemberInfoEntityForMemberInfoScreenRegist(memberInfoScreenRegistRequest);

        try {
            // PDR Migrate Customization from here
            // メルマガ購読者マガジンタイプ配列
            // 今回使用しないため上記の処理は削除

            // 会員登録サービスの実行
            memberInfoRegistService.execute(memberInfoEntity, memberInfoScreenRegistRequest.getOnlineFlg(),
                                            memberInfoScreenRegistRequest.getIsSiteBack(),
                                            memberInfoScreenRegistRequest.getAccessUidCommonInfo()
                                           );
            // PDR Migrate Customization to here
        } catch (DuplicateKeyException dke) {
            // Exceptionログを出力しておく
            ApplicationLogUtility appLogUtility = ApplicationContextUtility.getBean(ApplicationLogUtility.class);
            appLogUtility.writeExceptionLog(dke);

            // 万が一ユニークキー制約違反が発生した場合、再度の入力を促す
            LOGGER.error("例外処理が発生しました", dke);
            addErrorMessage(MSGCD_DB_UNIQUE_MEMBERID_FAIL_CONFIRM);
        }

        if (hasErrorMessage()) {
            throwMessage();
        }

        // PDR Migrate Customization from here
        // 会員登録完了メール送信通知
        // パラメータ設定
        Object[] args = new Object[] {memberInfoEntity.getMemberInfoSeq(),
                        HTypeMailTemplateType.MEMBER_REGIST_RECEPTION};
        Class<?>[] argsClass = new Class<?>[] {Integer.class, HTypeMailTemplateType.class};
        // 会員登録完了メールから会員登録申込受付メールに変更
        // 非同期処理を実行
        AsyncTaskUtility.executeAfterTransactionCommit(() -> {
            asyncService.asyncService(memberInfoProcessCompleteMailSendService, args, argsClass);
        });
        // PDR Migrate Customization to here

        String accessUid = memberInfoScreenRegistRequest.getAccessUidCommonInfo();
        String campaignCode = memberInfoScreenRegistRequest.getCampainCode();
        // サービス登録
        Object[] argsAccessInfo =
                        new Object[] {HTypeAccessType.MEMBER_REGIST_COUNT, 0, 1, accessUid, HTypeSiteType.FRONT_PC,
                                        campaignCode};

        Class<?>[] argsClassAccessInfo =
                        new Class<?>[] {HTypeAccessType.class, Integer.class, Integer.class, String.class,
                                        HTypeSiteType.class, String.class};

        // 非同期処理を登録
        AsyncTaskUtility.executeAfterTransactionCommit(
                        () -> asyncService.asyncService(accessRegistService, argsAccessInfo, argsClassAccessInfo));

        // PDR Migrate Customization from here
        // メルマガ登録 ※メルマガ登録・更新を行った場合は、無条件で登録する
        // メルマガは使用しないため削除
        // PDR Migrate Customization to here

        // PDR Migrate Customization for v4 from here
        //        // 会員登録したら自動ログイン処理
        //        request.setAttribute("isCheckInfo", true);
        //        hmFrontUserDetailsService.updateUserInfo(memberInfoEntity.getMemberInfoId());
        // PDR Migrate Customization for v4 to here

        // 2023-renew No22 from here
        MemberInfoEntityResponse response;

        try {
            response = memberInfoHelper.toMemberInfoEntityResponse(memberInfoEntity);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new AppLevelException(e.getMessage());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
        // 2023-renew No22 to here
    }

    /**
     * POST /memberinfo/memberinfo-unregist-inquiry-mail : 未登録会員照会発生メール送信
     * 未登録会員照会発生メール送信
     *
     * @param memberInfoUnregistInquirySendMailRequest 未登録会員照会発生メール送信リクエスト (required)
     * @return 成功
     */
    @Override
    public ResponseEntity<Void> registMemberInfoUnregistInquiryMail(@Valid MemberInfoUnregistInquirySendMailRequest memberInfoUnregistInquirySendMailRequest) {
        memberInfoUnregistInquirySendMailLogic.execute(
                        memberInfoUnregistInquirySendMailRequest.getCustomerNo(),
                        memberInfoUnregistInquirySendMailRequest.getMemberInfoTel()
                                                      );

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * PUT /memberinfo/addressbook : アドレス帳情報更新
     * アドレス帳情報更新
     *
     * @param addressBookUpdateRequest アドレス帳情報更新リクエスト (required)
     * @return 結果カウントレスポンス
     */
    @Override
    public ResponseEntity<ResultCountResponse> updateAddressbook(@Valid AddressBookUpdateRequest addressBookUpdateRequest) {

        AddressBookEntity addressBookEntity = memberInfoHelper.toAddressBookEntity(addressBookUpdateRequest);

        ResultCountResponse response = new ResultCountResponse();
        response.setResultCount(addressBookUpdateLogic.execute(addressBookEntity));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * DELETE /memberinfo/cardinfo : 指定カード情報削除
     * 指定カード情報削除
     *
     * @param cardInfoDeleteRequest 指定カード情報削除リクエスト (required)
     * @return カード情報削除レスポンス
     */
    @Override
    public ResponseEntity<CardInfoDeleteResponse> updateCardInfo(@Valid CardInfoDeleteRequest cardInfoDeleteRequest) {
        CardInfoDeleteResponse response = new CardInfoDeleteResponse();
        String result = cardInfoLogic.deleteDesignateCardInfo(
                        Integer.parseInt(cardInfoDeleteRequest.getMemberInfoSeq()), cardInfoDeleteRequest.getCardId());
        response.setCardInfoDelete(result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /memberinfo/cardinfo/{memberInfoSeq} : カード情報取得
     * カード情報取得
     *
     * @param memberInfoSeq 会員情報SEQ (required)
     * @return カード情報取得レスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<CardInfoResponse> getCardInfoByMemberInfoSeq(Integer memberInfoSeq,
                                                                       CardInfoRequest cardInfoRequest) {
        // セッションに情報を設定（ ペイジェント通信時のインタセプタ用）
        SessionParamsUtil sessionParamsUtil = new SessionParamsUtil();
        sessionParamsUtil.setToSessionParams(
                        memberInfoSeq, cardInfoRequest.getSessionId(), cardInfoRequest.getAccessUid());

        ComResultDto comResultDto = cardInfoLogic.getCardInfo(memberInfoSeq);
        CardInfoResponse response = memberInfoHelper.toCardInfoResponse(comResultDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * PUT /memberinfo/confirm-screen : 会員情報確認画面用会員更新
     * 会員情報確認画面用会員更新
     *
     * @param memberInfoConfirmScreenUpdateRequest 会員情報確認画面用会員更新リクエスト (required)
     * @return 成功
     */
    @Override
    public ResponseEntity<ResultCountResponse> updateConfirmScreen(@Valid MemberInfoConfirmScreenUpdateRequest memberInfoConfirmScreenUpdateRequest) {
        // 2023-renew AddNo2 from here
        ResultCountResponse response = new ResultCountResponse();
        MemberInfoEntity memberInfoEntity =
                        memberInfoGetLogic.execute(memberInfoConfirmScreenUpdateRequest.getMemberInfoSeq());
        if (memberInfoEntity == null) {
            throwMessage(MSGCD_MEMBERINFOENTITYDTO_NULL);
        }
        if (Objects.nonNull(memberInfoEntity)) {
            MemberInfoEntity memberInfoBeforeChange = SerializationUtils.clone(memberInfoEntity);
            memberInfoHelper.mappingEntityFromUpdateRequest(
                            memberInfoConfirmScreenUpdateRequest, memberInfoEntity, false);
            int successCount = 0;
            boolean passUpdateStage = true;
            boolean executeUpdateStage = memberInfoHelper.hasExecuteDBUpdateStage(
                            memberInfoConfirmScreenUpdateRequest.getModifiedList());
            if (executeUpdateStage) {
                // EC会員情報更新処理
                successCount = memberInfoUpdateService.execute(memberInfoEntity);
                passUpdateStage = successCount > 0;
            }
            if (passUpdateStage) {
                boolean hasExecuteNotifyMemberInfoUpdateStage = memberInfoHelper.hasExecuteApiInfoUpdateStage(
                                memberInfoConfirmScreenUpdateRequest.getModifiedList());
                if (hasExecuteNotifyMemberInfoUpdateStage) {
                    // 顧客番号
                    WebApiRepUserNoticeRequestDto reqDto =
                                    ApplicationContextUtility.getBean(WebApiRepUserNoticeRequestDto.class);
                    reqDto.setCustomerNo(memberInfoEntity.getCustomerNo());
                    reqDto.setMailPermitFlag(memberInfoEntity.getSendMailPermitFlag().getValue());
                    reqDto.setMetalPermitFlag(memberInfoEntity.getMetalPermitFlag().getValue());
                    reqDto.setNonConsultationDay(memberInfoEntity.getNonConsultationDay());
                    reqDto.setTreatmentType(memberInfoEntity.getMedicalTreatmentFlag());
                    reqDto.setTreatmentTypeMemo(memberInfoEntity.getMedicalTreatmentMemo());

                    // WEB-API連携 会員お知らせ情報更新
                    webApiRepUserNoticeLogic.execute(reqDto);
                }

                // メール送信
                try {
                    // パラメータ設定
                    MemberInfoEntity memberInfoAfterChange = SerializationUtils.clone(memberInfoEntity);
                    memberInfoHelper.mappingEntityFromUpdateRequest(
                                    memberInfoConfirmScreenUpdateRequest, memberInfoAfterChange, true);
                    Object[] args = new Object[] {memberInfoConfirmScreenUpdateRequest.getMemberInfoSeq(),
                                    memberInfoAfterChange, memberInfoBeforeChange,
                                    memberInfoConfirmScreenUpdateRequest.getImageAddCount(),
                                    memberInfoConfirmScreenUpdateRequest.getModifiedList(),
                                    memberInfoConfirmScreenUpdateRequest.getMedicalTreatmentTitleList()};
                    Class<?>[] argsClass =
                                    new Class<?>[] {Integer.class, MemberInfoEntity.class, MemberInfoEntity.class,
                                                    Integer.class, List.class, List.class};

                    // 非同期処理を実行
                    AsyncTaskUtility.executeAfterTransactionCommit(() -> {
                        asyncService.asyncService(memberInfoUpdateSendMailLogic, args, argsClass);
                    });
                } catch (Exception e) {
                    // 2023-renew No0 from here
                    throwMessage(MSGCD_SEND_ERROR_MEMBERINFO_UPDATE_MAIL);
                    // 2023-renew No0 to here
                }
            }

            response.setResultCount(successCount);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
        // 2023-renew AddNo2 to here
    }

    /**
     * PUT /memberinfo/login-failure : 会員ログイン失敗情報更新
     * 会員ログイン失敗情報更新
     *
     * @param updateLoginFailureUpdateRequest ログイン失敗回数をインクリメントリクエスト (required)
     * @return 結果カウントレスポンス (status code 200)
     */
    @Override
    public ResponseEntity<ResultCountResponse> updateLoginFailure(@Valid UpdateLoginFailureUpdateRequest updateLoginFailureUpdateRequest) {
        ResultCountResponse response = new ResultCountResponse();
        response.setResultCount(memberInfoUpdateLogic.updateLoginFailureCount(
                        updateLoginFailureUpdateRequest.getMemberInfoSeq()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * PUT /memberinfo/login-failure-with-locktime : 会員ログイン失敗情報更新
     * 会員ログイン失敗情報更新
     *
     * @param loginFailureWithLockTimeUpdateRequest 取得した会員のログイン情報を更新リクエスト (required)
     * @return 結果カウントレスポンス (status code 200)
     */
    @Override
    public ResponseEntity<ResultCountResponse> updateLoginFailureWithLocktime(@Valid LoginFailureWithLockTimeUpdateRequest loginFailureWithLockTimeUpdateRequest) {
        ResultCountResponse response = new ResultCountResponse();
        response.setResultCount(memberInfoUpdateLogic.updateLoginFailureCount(
                        loginFailureWithLockTimeUpdateRequest.getMemberInfoSeq(),
                        conversionUtility.toTimeStamp(loginFailureWithLockTimeUpdateRequest.getAccountLockTime())
                                                                             ));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * PUT /memberinfo/login-info : 会員ログイン情報更新
     * 会員ログイン情報更新
     *
     * @param loginInfoUpdateRequest 取得した会員のログイン情報を更新リクエスト (required)
     * @return 結果カウントレスポンス
     */
    @Override
    public ResponseEntity<ResultCountResponse> updateLoginInfo(@Valid LoginInfoUpdateRequest loginInfoUpdateRequest) {

        ResultCountResponse response = new ResultCountResponse();
        // 取得した会員のログイン情報を更新
        response.setResultCount(memberInfoUpdateLogic.execute(loginInfoUpdateRequest.getMemberInfoSeq(),
                                                              loginInfoUpdateRequest.getUserAgent(),
                                                              EnumTypeUtil.getEnumFromValue(HTypeDeviceType.class,
                                                                                            loginInfoUpdateRequest.getDeviceType()
                                                                                           )
                                                             ));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * PUT /memberinfo/mail : メールアドレス変更
     * メールアドレス変更
     *
     * @param memberInfoMailUpdateSendMailRequest メールアドレス変更リクエスト (required)
     * @return 結果成否レスポンス
     */
    @Override
    public ResponseEntity<ResultFlagResponse> updateMail(@Valid MemberInfoMailUpdateSendMailRequest memberInfoMailUpdateSendMailRequest) {
        ResultFlagResponse response = new ResultFlagResponse();
        response.setResultFlag(memberInfoMailUpdateSendMailService.execute(
                        memberInfoMailUpdateSendMailRequest.getMemberInfoSeq(),
                        memberInfoMailUpdateSendMailRequest.getMemberInfoNewMail()
                                                                          ));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * PUT /memberinfo/mail-screen : メールアドレス変更画面用会員メールアドレス変更
     * メールアドレス変更画面用会員メールアドレス変更
     *
     * @param memberInfoMailScreenUpdateRequest メールアドレス変更画面用会員メールアドレス変更リクエスト (required)
     * @return 成功
     */
    @Override
    public ResponseEntity<Void> updateMailScreen(@Valid MemberInfoMailScreenUpdateRequest memberInfoMailScreenUpdateRequest) {
        // PDR Migrate Customization from here
        // メールアドレスチェック 削除
        // PDR Migrate Customization to here

        // 変更会員情報
        MemberInfoEntity memberInfoEntity =
                        memberInfoHelper.toMemberInfoEntity(memberInfoMailScreenUpdateRequest.getMemberInfoEntity());
        // 会員メールアドレス変更サービス実行
        memberInfoMailUpdateService.execute(memberInfoEntity, memberInfoMailScreenUpdateRequest.getMid());

        // PDR Migrate Customization from here
        WebApiRepUserMailaddressRequestDto reqDto =
                        ApplicationContextUtility.getBean(WebApiRepUserMailaddressRequestDto.class);

        // 顧客番号
        // アクセスされたURLから取得した会員情報の顧客番号をセット(ログイン状態/未ログイン状態共に取得が必要であるため)
        reqDto.setCustomerNo(memberInfoEntity.getCustomerNo());
        // 会員メールアドレス
        reqDto.setMailAddress(memberInfoMailScreenUpdateRequest.getMemberInfoMail());

        // WEB-API連携 会員情報更新
        webApiRepUserMailaddressLogic.execute(reqDto);
        // PDR Migrate Customization to here

        // PDR Migrate Customization for v4 from here
        //        // セッションのユーザー情報を更新
        //        request.setAttribute("isCheckInfo", true);
        //        hmFrontUserDetailsService.updateUserInfo(mailModel.getMemberInfoEntity().getMemberInfoId());
        // PDR Migrate Customization for v4 to here

        // PDR Migrate Customization for v4 from here
        //        // Remember-Meトークンを更新
        //        if (StringUtils.isNotEmpty(hmFrontUserDetailsService.extractRememberMeCookie(request))) {
        //            Authentication newAuthentications = SecurityContextHolder.getContext().getAuthentication();
        //            rememberMeTokenService.loginSuccess(request, response, newAuthentications);
        //        }
        // PDR Migrate Customization for v4 to here

        // メールアドレス変更完了メール送信（非同期処理）
        // パラメータ設定
        Object[] args = new Object[] {memberInfoEntity.getMemberInfoSeq(),
                        HTypeMailTemplateType.EMAIL_MODIFICATION_COMPLETE};
        Class<?>[] argsClass = new Class<?>[] {Integer.class, HTypeMailTemplateType.class};
        // 非同期処理を実行
        AsyncTaskUtility.executeAfterTransactionCommit(() -> {
            asyncService.asyncService(memberInfoProcessCompleteMailSendService, args, argsClass);
        });

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * PUT /memberinfo/password/memberinfo-password-update : 会員パスワード変更
     * 会員パスワード変更
     *
     * @param memberInfoPasswordResetUpdateRequest パスワード再設定会員情報取得リクエスト (required)
     * @return 結果カウントレスポンス
     */
    @Override
    public ResponseEntity<ResultCountResponse> updateMemberInfoPassword(@Valid MemberInfoPasswordResetUpdateRequest memberInfoPasswordResetUpdateRequest) {

        MemberInfoEntity memberInfoEntity =
                        memberInfoHelper.toMemberInfoEntity(memberInfoPasswordResetUpdateRequest.getMemberInfoEntity());
        // 会員パスワード変更サービス実行
        int ret = memberInfoPasswordUpdateService.execute(memberInfoPasswordResetUpdateRequest.getMid(),
                                                          memberInfoEntity,
                                                          memberInfoPasswordResetUpdateRequest.getMemberInfoNewPassWord()
                                                         );
        ResultCountResponse response = new ResultCountResponse();
        response.setResultCount(ret);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * PUT /memberinfo/memberinfo-screen : 本会員登録画面用会員情報更新
     * 本会員登録画面用会員情報更新
     *
     * @param memberInfoScreenUpdateRequest 本会員登録画面用会員情報更新リクエスト (required)
     * @return 成功
     */
    @Override
    // 2023-renew No22 from here
    public ResponseEntity<MemberInfoEntityResponse> updateMemberInfoScreen(@Valid MemberInfoScreenUpdateRequest memberInfoScreenUpdateRequest) {
        // 2023-renew No22 to here

        MemberInfoEntity memberInfoEntity =
                        memberInfoHelper.toMemberInfoEntityForMemberInfoScreenUpdate(memberInfoScreenUpdateRequest);

        // 会員情報更新処理
        memberInfoEcUpdateLogic.execute(memberInfoEntity);

        // PDR Migrate Customization from here
        /// WEB-API連携 会員情報更新
        WebApiRepUserMailaddressRequestDto reqDto =
                        ApplicationContextUtility.getBean(WebApiRepUserMailaddressRequestDto.class);
        // 顧客番号
        reqDto.setCustomerNo(memberInfoEntity.getCustomerNo());
        // 会員メールアドレス
        reqDto.setMailAddress(memberInfoScreenUpdateRequest.getMemberInfoId());
        // WEB-API連携 会員情報更新実行
        webApiRepUserMailaddressLogic.execute(reqDto);

        /// WEB-API連携リクエストDTOクラス 会員お知らせ情報更新
        WebApiRepUserNoticeRequestDto reqNoticeRequestDto =
                        ApplicationContextUtility.getBean(WebApiRepUserNoticeRequestDto.class);
        // 顧客番号
        reqNoticeRequestDto.setCustomerNo(memberInfoEntity.getCustomerNo());
        // Eメールによる情報提供
        reqNoticeRequestDto.setMailPermitFlag(memberInfoEntity.getSendMailPermitFlag().getValue());
        // 金属商品価格お知らせメール
        reqNoticeRequestDto.setMetalPermitFlag(memberInfoEntity.getMetalPermitFlag().getValue());
        // 2023-renew No85-1 from here
        // 休診曜日
        reqNoticeRequestDto.setNonConsultationDay(memberInfoEntity.getNonConsultationDay());
        // 診療内容
        reqNoticeRequestDto.setTreatmentType(memberInfoEntity.getMedicalTreatmentFlag());
        // その他診療項目
        reqNoticeRequestDto.setTreatmentTypeMemo(memberInfoEntity.getMedicalTreatmentMemo());
        // 2023-renew No85-1 to here
        // WEB-API連携 会員お知らせ情報更新 実行
        webApiRepUserNoticeLogic.execute(reqNoticeRequestDto);
        // PDR Migrate Customization to here

        // 2023-renew No85-1 from here
        /// WEB-API連携リクエストDTOクラス 会員FAX情報更新
        WebApiRepUserFaxRequestDto reqFaxRequestDto =
                        ApplicationContextUtility.getBean(WebApiRepUserFaxRequestDto.class);

        reqFaxRequestDto.setCustomerNo(memberInfoEntity.getCustomerNo());
        reqFaxRequestDto.setFax(memberInfoEntity.getMemberInfoFax());

        // WEB-API連携 会員FAX情報更新 実行
        webApiRepUserFaxLogic.execute(reqFaxRequestDto);
        // 2023-renew No85-1 to here

        /// 会員登録完了メール送信通知
        // パラメータ設定
        Object[] args = new Object[] {memberInfoEntity.getMemberInfoSeq(),
                        HTypeMailTemplateType.SEND_CUSTOMERNO_PASSWORD};
        Class<?>[] argsClass = new Class<?>[] {Integer.class, HTypeMailTemplateType.class};
        // 顧客番号・パスワード通知メール送信
        AsyncTaskUtility.executeAfterTransactionCommit(() -> {
            asyncService.asyncService(memberInfoProcessCompleteMailSendService, args, argsClass);
        });
        // 2023-renew No22 from here
        MemberInfoEntityResponse response;

        try {
            response = memberInfoHelper.toMemberInfoEntityResponse(memberInfoEntity);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new AppLevelException(e.getMessage());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
        // 2023-renew No22 to here
    }

    /**
     * PUT /memberinfo/password : 会員情報パスワード変更
     * 会員情報パスワード変更
     *
     * @param memberInfoPasswordUpdateRequest 会員情報パスワード変更リクエスト (required)
     * @return 結果カウントレスポンス
     */
    @Override
    public ResponseEntity<ResultCountResponse> updatePassword(@Valid MemberInfoPasswordUpdateRequest memberInfoPasswordUpdateRequest) {
        MemberInfoEntity memberInfoEntity =
                        memberInfoHelper.toMemberInfoEntity(memberInfoPasswordUpdateRequest.getMemberInfoEntity());
        // パスワード変更サービス実行
        int ret = memberInfoPasswordUpdateService.execute(memberInfoEntity,
                                                          memberInfoPasswordUpdateRequest.getPassword(),
                                                          memberInfoPasswordUpdateRequest.getNewPassword()
                                                         );
        ResultCountResponse response = new ResultCountResponse();
        response.setResultCount(ret);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * PUT /memberinfo/password/password-reset-send-mail : パスワード再設定メール送信
     * パスワード再設定メール送信
     *
     * @param passwordResetSendMailRequest パスワード再設定メール送信リクエスト (required)
     * @return 結果成否レスポンス
     */
    @Override
    public ResponseEntity<ResultFlagResponse> updatePasswordResetSendMail(@Valid PasswordResetSendMailRequest passwordResetSendMailRequest) {
        ResultFlagResponse response = new ResultFlagResponse();

        Boolean resultFlag = null;

        // PDR Migrate Customization from here
        try {
            // パスワードリセットメール送信サービス実行
            resultFlag = passwordResetSendMailLogic.execute(passwordResetSendMailRequest.getMemberInfoMail(),
                                                            passwordResetSendMailRequest.getMemberInfoTel()
                                                           );

            // PDR Migrate Customization to here
        } catch (DuplicateKeyException dke) {
            // Exceptionログを出力しておく
            ApplicationLogUtility appLogUtility = ApplicationContextUtility.getBean(ApplicationLogUtility.class);
            appLogUtility.writeExceptionLog(dke);

            // 万が一ユニークキー制約違反が発生した場合、再度の入力を促す
            LOGGER.error("例外処理が発生しました", dke);
            throwMessage(MSGCD_DB_UNIQUE_CONFIRMMAIL_PASSWORD_FAIL);
        }

        response.setResultFlag(resultFlag);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * PUT /memberinfo/reset-login-failure-count : 会員ログイン失敗回数リセット
     * 会員ログイン失敗回数リセット
     *
     * @param loginFailureCountResetRequest 会員ログイン失敗回数リセットリクエスト (required)
     * @return 結果カウントレスポンス
     */
    @Override
    public ResponseEntity<ResultCountResponse> updateResetLoginFailureCount(@Valid LoginFailureCountResetRequest loginFailureCountResetRequest) {

        ResultCountResponse response = new ResultCountResponse();
        response.setResultCount(
                        memberInfoUpdateLogic.resetLoginFailureCount(loginFailureCountResetRequest.getMemberInfoSeq()));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /memberinfo/get-customer-no-next-val : 顧客番号取得
     * 顧客番号取得
     *
     * @return 顧客番号取得レスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<CustomerNoNextValResponse> getCustomerNoNextVal() {

        Integer customerNo = memberInfoGetCutomerNoNextValLogic.execute();

        CustomerNoNextValResponse response = new CustomerNoNextValResponse();

        response.setCustomerNo(customerNo);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /memberinfo/by-seq-status : 会員情報を取得
     * 会員情報を取得
     *
     * @param memberinfoBySeqAndStatusGetRequest 会員情報取得リクエスト (required)
     * @return 会員Entityレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<MemberInfoEntityResponse> getByMemberInfoSeqAndStatus(@NotNull
                                                                                @ApiParam(value = "会員情報取得リクエスト",
                                                                                          required = true)
                                                                                @Valid MemberinfoBySeqAndStatusGetRequest memberinfoBySeqAndStatusGetRequest) {
        MemberInfoEntityResponse response;

        MemberInfoEntity memberInfoEntity =
                        memberInfoGetLogic.execute(memberinfoBySeqAndStatusGetRequest.getMemberInfoSeq(),
                                                   EnumTypeUtil.getEnumFromValue(HTypeMemberInfoStatus.class,
                                                                                 memberinfoBySeqAndStatusGetRequest.getStatus()
                                                                                )
                                                  );
        try {
            response = memberInfoHelper.toMemberInfoEntityResponse(memberInfoEntity);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new AppLevelException(e.getMessage());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    /**
     * GET /memberinfo/authentication/proxy-admin-login : 代理ログイン
     * 代理ログイン
     *
     * @param proxyAdminLoginRequest 代理のログインリクエスト (required)
     * @return 代理のログインレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or その他エラー (status code 200)
     */
    @Override
    public ResponseEntity<ProxyAdminLoginResponse> getProxyAdmin(@NotNull
                                                                 @ApiParam(value = "代理のログインリクエスト", required = true)
                                                                 @Valid ProxyAdminLoginRequest proxyAdminLoginRequest) {
        // 管理者情報取得
        Integer shopSeq = 1001;
        AdministratorEntity administratorEntity =
                        adminLogic.getAdministrator(shopSeq, proxyAdminLoginRequest.getAdminId());
        if (administratorEntity == null) {
            throwMessage(MSGCD_ADMIN_LOGIN_ERROR);
        }

        // 管理者のログインレスポンスへの変換
        ProxyAdminLoginResponse adminLoginResponse = memberInfoHelper.toProxyAdminLoginResponse(administratorEntity);

        // ユーザIDが存在する場合に、対象ユーザーの権限リストを取得する
        List<String> authorityList =
                        adminAuthGroupDetailLogic.getAuthorityList(administratorEntity.getAdminAuthGroupSeq());

        adminLoginResponse.setAuthorityList(authorityList);

        return new ResponseEntity<>(adminLoginResponse, HttpStatus.OK);

    }

    // 2023-renew No60 from here

    /**
     * GET /memberinfo/get-card-brand-list : カードブランドリスト取得
     * カードブランドリスト取得
     *
     * @param cardBrandGetRequest カードブランドリスト取得リクエスト
     * @return カードブランドリスト取得レスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<CardBrandGetResponse> getCardBrandList(@Valid CardBrandGetRequest cardBrandGetRequest) {
        // クレジットカードブランドEntityリストを取得
        List<CardBrandEntity> cardBrandEntityList =
                        cardBrandGetLogic.execute(cardBrandGetRequest.getFrontDisplayFlag());

        // 変換して返却
        return new ResponseEntity<>(memberInfoHelper.toCardBrandGetResponse(cardBrandEntityList), HttpStatus.OK);
    }

    /**
     * POST /memberinfo/regist-card-info : カード情報登録
     * カード情報登録
     *
     * @param cardInfoRegistRequest カード情報登録リクエスト
     * @return 成功 (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<Void> registCardInfo(@Valid CardInfoRegistRequest cardInfoRegistRequest) {
        // カード情報を登録する
        cardInfoLogic.registCardInfo(memberInfoHelper.toReceiveOrderDto(cardInfoRegistRequest), true);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * POST /memberinfo/send-mail-for-lock-account-by-regist-credit-error : アカウントロック通知（クレジット登録エラー）メール送信
     * アカウントロック通知（クレジット登録エラー）メール送信
     *
     * @param sendMailForLockAccountByRegistCreditErrorRequest アカウントロック通知（クレジット登録エラー）メール送信リクエスト
     * @return 成功 (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<Void> sendMailForLockAccountByRegistCreditError(@Valid SendMailForLockAccountByRegistCreditErrorRequest sendMailForLockAccountByRegistCreditErrorRequest) {
        // DTO作成
        LockAccountByRegistCreditErrorMailDto dto =
                        ApplicationContextUtility.getBean(LockAccountByRegistCreditErrorMailDto.class);
        dto.setCustomerNo(sendMailForLockAccountByRegistCreditErrorRequest.getCustomerNo());
        dto.setOfficeName(sendMailForLockAccountByRegistCreditErrorRequest.getOfficeName());

        // アカウントロック通知（クレジット登録エラー）メール送信
        lockAccountByRegistCreditErrorMailService.execute(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 2023-renew No60 to here
    // 2023-renew No22 from here

    /**
     * POST /memberinfo/image: : アップロードした画像をデータベースに保存
     * アップロードした画像をデータベースに保存
     *
     * @param memberInfoImageRequest メンバー情報画像リクエスト (required)
     * @return 成功
     */
    @Override
    public ResponseEntity<Void> createMemberImage(@Valid MemberInfoImageRequest memberInfoImageRequest) {
        memberInfoSaveImageService.execute(memberInfoImageRequest.getMemberInfoSeq(),
                                           memberInfoImageRequest.getMemberImageNo(),
                                           memberInfoImageRequest.getMemberImageFileName(),
                                           memberInfoImageRequest.getMemberImageType()
                                          );
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * DELETE /memberinfo/image: 会員情報アップロードファイルを削除
     * 会員情報アップロードファイルを削除
     *
     * @param memberInfoImageDeleteRequest 会員情報画像削除依頼
     * @return 結果カウントレスポンス
     */
    @Override
    public ResponseEntity<ResultCountResponse> deleteMemberImage(@Valid MemberInfoImageDeleteRequest memberInfoImageDeleteRequest) {
        return MemberinfoApi.super.deleteMemberImage(memberInfoImageDeleteRequest);
    }

    /**
     * GET /memberinfo/image/{memberInfoSeq}: アップロードされたファイルをデータベースから取得する
     * アップロードされたファイルをデータベースから取得する
     *
     * @param memberInfoSeq 会員情報SEQ (required)
     * @return メンバー情報ファイルエンティティリストの応答
     */
    @Override
    public ResponseEntity<MemberInfoImageListResponse> getListMemberImage(Integer memberInfoSeq) {
        List<MemberImageDto> memberImageDtoList = memberInfoListImageService.execute(memberInfoSeq);
        MemberInfoImageListResponse response = memberInfoHelper.toMemberInfoImageListResponse(memberImageDtoList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 2023-renew No22 to here
    // 2023-renew No71 from here

    /**
     * PUT /memberinfo/announce
     * 割引商品や新商品の入荷情報をお知らせします
     *
     * @param memberInfoAnnounceUpdateRequest 割引商品や新商品の入荷情報のお知らせのリクエスト (required)
     * @return 代理のログインレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or その他エラー (status code 200)
     */
    @Override
    public ResponseEntity<Void> updateAnnounce(@Valid MemberInfoAnnounceUpdateRequest memberInfoAnnounceUpdateRequest) {
        Integer memberInfoSeq = memberInfoAnnounceUpdateRequest.getMemberInfoSeq();
        MemberInfoEntity memberInfoEntity = memberInfoGetService.execute(memberInfoSeq);

        memberInfoHelper.setDataUpdateAnnounceRequestToMemberInfoEntity(
                        memberInfoEntity, memberInfoAnnounceUpdateRequest);

        memberInfoUpdateService.execute(memberInfoEntity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 2023-renew No71 to here
    // 2023-renew No65 from here

    /**
     * GET /memberinfo/restockannounce/list : 入荷お知らせリスト取得
     * 入荷お知らせリスト取得
     *
     * @param restockAnnounceListGetRequest 入荷お知らせリスト取得リクエスト (optional)
     * @param pageInfoRequest ページ情報リクエスト (optional)
     * @return 入荷お知らせDtoListレスポンス (status code 200)
     *         or Bad Request（業務ルールエラー） (status code 400)
     *         or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     *         or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<RestockAnnounceDtoListResponse> getRestockAnnounceList(@Valid RestockAnnounceListGetRequest restockAnnounceListGetRequest,
                                                                                 @Valid PageInfoRequest pageInfoRequest) {

        RestockAnnounceSearchForDaoConditionDto restockAnnounceConditionDto =
                        memberInfoHelper.toRestockAnnounceConditionDto(restockAnnounceListGetRequest);

        // ページング検索セットアップ
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);

        pageInfoHelper.setupPageInfo(restockAnnounceConditionDto, conversionUtility.toString(pageInfoRequest.getPage()),
                                     pageInfoRequest.getLimit(), pageInfoRequest.getOrderBy(), pageInfoRequest.getSort()
                                    );

        List<RestockAnnounceDto> restockAnnounceDtoList =
                        restockAnnounceListGetService.execute(restockAnnounceConditionDto);

        // ページ情報レスポンスを設定
        PageInfoResponse pageInfoResponse = new PageInfoResponse();
        try {
            pageInfoHelper.setupResponsePager(restockAnnounceConditionDto, pageInfoResponse);
        } catch (InvocationTargetException | IllegalAccessException e) {
            LOGGER.info("ページ情報レスポンスの設定異常： " + e.getMessage());
        }

        RestockAnnounceDtoListResponse response = new RestockAnnounceDtoListResponse();
        try {
            response.setRestockAnnounceDtoListResponse(
                            memberInfoHelper.toRestockAnnounceDtoResponseList(restockAnnounceDtoList));
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new AppLevelException(e.getMessage());
        }

        response.setPageInfo(pageInfoResponse);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * POST /memberinfo/restockannounce : 入荷お知らせ登録
     * 入荷お知らせ登録
     *
     * @param restockAnnounceRegistRequest 入荷お知らせリクエスト (required)
     * @return 結果カウントレスポンス (status code 200)
     *         or Bad Request（業務ルールエラー） (status code 400)
     *         or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     *         or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<ResultCountResponse> registRestockAnnounce(@Valid RestockAnnounceRegistRequest restockAnnounceRegistRequest) {

        ResultCountResponse response = new ResultCountResponse();
        int result = restockAnnounceRegistService.execute(restockAnnounceRegistRequest.getMemberInfoSeq(),
                                                          restockAnnounceRegistRequest.getGcd(), HTypeSiteType.FRONT_PC
                                                         );
        response.setResultCount(result);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * DELETE /memberinfo/restockannounce/list : 入荷お知ら入り情報リスト削除
     * 入荷お知ら入り情報リスト削除
     *
     * @param restockAnnounceListDeleteRequest 入荷お知ら入り情報リスト削除リクエスト (required)
     * @return 結果カウントレスポンス (status code 200)
     * or Bad Request（業務ルールエラー） (status code 400)
     * or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     * or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<ResultCountResponse> deleteRestockAnnounceList(@Valid RestockAnnounceListDeleteRequest restockAnnounceListDeleteRequest) {

        int execute = restockAnnounceListDeleteService.execute(restockAnnounceListDeleteRequest.getMemberInfoSeq(),
                                                               restockAnnounceListDeleteRequest.getGcd()
                                                              );

        ResultCountResponse response = new ResultCountResponse();
        response.setResultCount(execute);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 2023-renew No65 to here
    // 2023-renew No68 from here

    /**
     * POST /memberinfo/orderhistory/cancel-order : 注文キャンセル
     * 注文キャンセル
     *
     * @param orderHistoryCancelOrderRequest 注文キャンセルリクエスト (required)
     * @return 注文キャンセルレスポンス (status code 200)
     *         or Bad Request（業務ルールエラー） (status code 400)
     *         or Internal Server Error（アサートエラー、予想外エラーの場合） (status code 500)
     *         or システムエラー (status code 200)
     */
    @Override
    public ResponseEntity<OrderHistoryCancelOrderResponse> cancelOrder(@Valid OrderHistoryCancelOrderRequest orderHistoryCancelOrderRequest) {

        // --------------------------
        // WEB-API 注文キャンセル
        // --------------------------
        // リクエストDTO作成
        WebApiCancelOrderRequestDto requestDto = ApplicationContextUtility.getBean(WebApiCancelOrderRequestDto.class);
        requestDto.setOrderNo(orderHistoryCancelOrderRequest.getOrderNo());

        // WEB-API連携
        // ※キャンセル失敗時は当Logic内でハンドリングしてthrowするので、成功時のみ後続処理に進む
        WebApiCancelOrderResponseDto cancelOrderResponseDto =
                        (WebApiCancelOrderResponseDto) webApiCancelOrderLogic.execute(requestDto);

        // --------------------------
        // 注文キャンセルメール送信
        // --------------------------
        // キャンセル日時を生成（システム日時取得）
        Timestamp cancelTime = dateUtility.getCurrentTime();

        // サービス登録
        Object[] args = new Object[] {orderHistoryCancelOrderRequest.getMemberInfoSeq(),
                        memberInfoHelper.toCancelOrderHistoryDto(
                                        orderHistoryCancelOrderRequest.getCancelOrderHistoryOrderItem(), cancelTime)};
        Class<?>[] argsClass = new Class<?>[] {Integer.class, CancelOrderHistoryDto.class};

        // 非同期処理を登録
        AsyncTaskUtility.executeAfterTransactionCommit(
                        () -> asyncService.asyncService(cancelOrderSendMailService, args, argsClass));

        // --------------------------
        // HM側の受注キャンセル処理（簡易）
        // --------------------------
        // 受注サマリを取得
        OrderSummaryEntity orderSummaryEntity =
                        orderSummaryGetLogic.execute(String.valueOf(orderHistoryCancelOrderRequest.getOrderNo()));
        if (ObjectUtils.isNotEmpty(orderSummaryEntity)) {
            // レコードが存在する場合、受注状態を更新
            orderSummaryEntity.setCancelFlag(HTypeCancelFlag.ON);
            orderSummaryEntity.setCancelTime(cancelTime);
            orderSummaryUpdateLogic.execute(orderSummaryEntity);
        } else {
            // レコードが存在しない場合、ログを出力して更新しない
            LOGGER.info("基幹側のみの受注データ（TEL・FAX注文等）のため、受注サマリ更新はスキップします。");
        }

        return new ResponseEntity<>(
                        memberInfoHelper.toOrderHistoryCancelOrderResponse(cancelOrderResponseDto.getInfo()),
                        HttpStatus.OK
        );
    }

    // 2023-renew No68 to here

}
