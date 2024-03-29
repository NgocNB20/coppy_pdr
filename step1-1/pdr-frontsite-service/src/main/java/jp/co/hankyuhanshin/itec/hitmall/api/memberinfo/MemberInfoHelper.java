package jp.co.hankyuhanshin.itec.hitmall.api.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.AddressBookEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.AddressBookRegistRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.AddressBookUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.AdminAuthGroupDetailtResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.AdminAuthGroupResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CancelOrderHistoryGoodsItemRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CancelOrderHistoryOrderItemRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CardBrandGetResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CardBrandResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CardInfoRegistRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CardInfoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CartDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CartGoodsDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CartGoodsEntityRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CheckMessageDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.ConfirmMailEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteEntityRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteGoodsStockStatusGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.FavoriteListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.GoodsDetailsDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.GoodsDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.GoodsDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.GoodsGroupImageEntityRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.GoodsGroupImageEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.GoodsImageEntityRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.GoodsImageEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.LoginAdvisabilityEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.LoginAdvisabilityGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoAnnounceUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoConfirmScreenUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoEntityRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoImageEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoImageListResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoScreenRegistRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoScreenUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.OrderHistoryCancelOrderResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.OrderHistoryDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.OrderSummaryEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.ProxyAdminLoginResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.ReceiveOrderForHistoryDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.RestockAnnounceDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.RestockAnnounceEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.RestockAnnounceListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.SettlementMethodEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.StockDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.WebApiGetConsumptionTaxRateResponseDetailDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.WebApiGetDiscountsResponseDetailDtoRequest;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAccountingType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAddressBookApproveFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAlcoholFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeApproveStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBusinessType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCardRegistType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCashDeliveryUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeConfDocumentType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCoolSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCreditPaymentUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeliveryCompletePermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDentalMonopolySalesFlg;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDentalMonopolySalesType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDirectDebitUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDisplayStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDrugSalesType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEmotionPriceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeFreeDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsClassType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsTaxType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeIndividualDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeLandSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMedicalEquipmentSalesType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberListType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberRank;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMetalPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMonthlyPayUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeNewIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeNoAntiSocialFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOnlineLoginAdvisability;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOnlineRegistFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderCompletePermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOutletIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePasswordNeedChangeFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReserveDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReserveFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReserveIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSaleAnnounceWatchFlg;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSaleIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalePriceIntegrityFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendDirectMailFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendFaxPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendMailPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSexUnnecessaryAnswer;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSnsLinkFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockAnnounceWatchFlg;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockStatusType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeTopSaleAnnounceFlg;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeTopStockAnnounceFlg;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeTransferPaymentUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUnitManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.favorite.FavoriteDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.favorite.FavoriteSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.image.MemberImageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.orderhistory.CancelOrderHistoryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.orderhistory.CancelOrderHistoryGoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.orderhistory.OrderHistoryListDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.restockannounce.RestockAnnounceDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.restockannounce.RestockAnnounceSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.ComResultDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderTempDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderForHistoryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiCancelOrderResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetConsumptionTaxRateResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetDiscountsResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdminAuthGroupDetailEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdministratorEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.cart.CartGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.addressbook.AddressBookEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.confirmmail.ConfirmMailEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.favorite.FavoriteEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.loginadvisability.LoginAdvisabilityEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.restockannounce.RestockAnnounceEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.CardBrandEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.orderperson.OrderPersonEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoGetService;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.utility.NonConsultationDayUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Helperクラス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class MemberInfoHelper {

    /**
     * 休診曜日用Utility
     */
    public final NonConsultationDayUtility nonConsultationDayUtility;

    /**
     * 変換ユーティリティクラス
     */
    private final ConversionUtility conversionUtility;

    /**
     * コンストラクタ
     *
     * @param nonConsultationDayUtility データ連携（顧客情報）休診曜日判定用Utilityクラス
     * @param conversionUtility         変換ユーティリティクラス
     */
    @Autowired
    public MemberInfoHelper(NonConsultationDayUtility nonConsultationDayUtility, ConversionUtility conversionUtility) {
        this.nonConsultationDayUtility = nonConsultationDayUtility;
        this.conversionUtility = conversionUtility;
    }

    /**
     * 会員に変換
     *
     * @param memberInfoEntityRequest 会員Entityリクエスト
     * @return 会員
     */
    public MemberInfoEntity toMemberInfoEntity(MemberInfoEntityRequest memberInfoEntityRequest) {
        if (memberInfoEntityRequest == null) {
            return null;
        }

        // 元会員情報取得
        MemberInfoEntity memberInfoEntity = new MemberInfoEntity();
        if (memberInfoEntityRequest.getMemberInfoSeq() != null) {
            MemberInfoGetService memberInfoGetService = ApplicationContextUtility.getBean(MemberInfoGetService.class);
            memberInfoEntity = memberInfoGetService.execute(memberInfoEntityRequest.getMemberInfoSeq());
        }

        memberInfoEntity.setMemberInfoSeq(memberInfoEntityRequest.getMemberInfoSeq());
        memberInfoEntity.setMemberInfoStatus(EnumTypeUtil.getEnumFromValue(HTypeMemberInfoStatus.class,
                                                                           memberInfoEntityRequest.getMemberInfoStatus()
                                                                          ));
        memberInfoEntity.setMemberInfoUniqueId(memberInfoEntityRequest.getMemberInfoUniqueId());
        memberInfoEntity.setMemberInfoId(memberInfoEntityRequest.getMemberInfoId());
        memberInfoEntity.setMemberInfoPassword(memberInfoEntityRequest.getMemberInfoPassword());
        memberInfoEntity.setMemberInfoLastName(memberInfoEntityRequest.getMemberInfoLastName());
        memberInfoEntity.setMemberInfoFirstName(memberInfoEntityRequest.getMemberInfoFirstName());
        memberInfoEntity.setMemberInfoLastKana(memberInfoEntityRequest.getMemberInfoLastKana());
        memberInfoEntity.setMemberInfoFirstKana(memberInfoEntityRequest.getMemberInfoFirstKana());
        memberInfoEntity.setMemberInfoSex(EnumTypeUtil.getEnumFromValue(HTypeSexUnnecessaryAnswer.class,
                                                                        memberInfoEntityRequest.getMemberInfoSex()
                                                                       ));
        memberInfoEntity.setMemberInfoBirthday(
                        conversionUtility.toTimeStamp(memberInfoEntityRequest.getMemberInfoBirthday()));
        memberInfoEntity.setMemberInfoTel(memberInfoEntityRequest.getMemberInfoTel());
        memberInfoEntity.setMemberInfoContactTel(memberInfoEntityRequest.getMemberInfoContactTel());
        memberInfoEntity.setMemberInfoZipCode(memberInfoEntityRequest.getMemberInfoZipCode());
        memberInfoEntity.setMemberInfoPrefecture(memberInfoEntityRequest.getMemberInfoPrefecture());

        memberInfoEntity.setPrefectureType(EnumTypeUtil.getEnumFromValue(HTypePrefectureType.class,
                                                                         memberInfoEntityRequest.getMemberInfoPrefecture()
                                                                        ));
        memberInfoEntity.setMemberInfoAddress1(memberInfoEntityRequest.getMemberInfoAddress1());
        memberInfoEntity.setMemberInfoAddress2(memberInfoEntityRequest.getMemberInfoAddress2());
        memberInfoEntity.setMemberInfoAddress3(memberInfoEntityRequest.getMemberInfoAddress3());
        memberInfoEntity.setMemberInfoMail(memberInfoEntityRequest.getMemberInfoMail());
        memberInfoEntity.setShopSeq(1001);
        memberInfoEntity.setAccessUid(memberInfoEntityRequest.getAccessUid());
        memberInfoEntity.setVersionNo(memberInfoEntityRequest.getVersionNo());
        memberInfoEntity.setAdmissionYmd(memberInfoEntityRequest.getAdmissionYmd());
        memberInfoEntity.setSecessionYmd(memberInfoEntityRequest.getSecessionYmd());
        memberInfoEntity.setMemo(memberInfoEntityRequest.getMemo());
        memberInfoEntity.setMemberInfoFax(memberInfoEntityRequest.getMemberInfoFax());
        memberInfoEntity.setPaymentMemberId(memberInfoEntityRequest.getPaymentMemberId());
        memberInfoEntity.setLastLoginTime(conversionUtility.toTimeStamp(memberInfoEntityRequest.getLastLoginTime()));
        memberInfoEntity.setLastLoginUserAgent(memberInfoEntityRequest.getLastLoginUserAgent());
        memberInfoEntity.setPaymentMemberId(memberInfoEntityRequest.getPaymentMemberId());

        memberInfoEntity.setPaymentCardRegistType(EnumTypeUtil.getEnumFromValue(HTypeCardRegistType.class,
                                                                                memberInfoEntityRequest.getPaymentCardRegistType()
                                                                               ));
        memberInfoEntity.setPasswordNeedChangeFlag(EnumTypeUtil.getEnumFromValue(HTypePasswordNeedChangeFlag.class,
                                                                                 memberInfoEntityRequest.getPasswordNeedChangeFlag()
                                                                                ));
        memberInfoEntity.setLastLoginDeviceType(EnumTypeUtil.getEnumFromValue(HTypeDeviceType.class,
                                                                              memberInfoEntityRequest.getLastLoginDeviceType()
                                                                             ));
        memberInfoEntity.setLoginFailureCount(memberInfoEntityRequest.getLoginFailureCount());
        memberInfoEntity.setRegistTime(conversionUtility.toTimeStamp(memberInfoEntityRequest.getRegistTime()));
        memberInfoEntity.setUpdateTime(conversionUtility.toTimeStamp(memberInfoEntityRequest.getUpdateTime()));
        memberInfoEntity.setAccountLockTime(
                        conversionUtility.toTimeStamp(memberInfoEntityRequest.getAccountLockTime()));
        memberInfoEntity.setCustomerNo(memberInfoEntityRequest.getCustomerNo());
        memberInfoEntity.setRepresentativeName(memberInfoEntityRequest.getRepresentativeName());
        memberInfoEntity.setMemberInfoAddress4(memberInfoEntityRequest.getMemberInfoAddress4());
        memberInfoEntity.setMemberInfoAddress5(memberInfoEntityRequest.getMemberInfoAddress5());
        memberInfoEntity.setBusinessType(EnumTypeUtil.getEnumFromValue(HTypeBusinessType.class,
                                                                       memberInfoEntityRequest.getBusinessType()
                                                                      ));

        // FAXによるおトク情報希望フラグ
        memberInfoEntity.setSendFaxPermitFlag(EnumTypeUtil.getEnumFromValue(HTypeSendFaxPermitFlag.class,
                                                                            memberInfoEntityRequest.getSendFaxPermitFlag()
                                                                           ));

        // DMによるおトク情報希望フラグ
        memberInfoEntity.setSendDirectMailFlag(EnumTypeUtil.getEnumFromValue(HTypeSendDirectMailFlag.class,
                                                                             memberInfoEntityRequest.getSendDirectMailFlag()
                                                                            ));
        memberInfoEntity.setNonConsultationDay(memberInfoEntityRequest.getNonConsultationDay());

        memberInfoEntity.setApproveStatus(EnumTypeUtil.getEnumFromValue(HTypeApproveStatus.class,
                                                                        memberInfoEntityRequest.getApproveStatus()
                                                                       ));
        memberInfoEntity.setDrugSalesType(EnumTypeUtil.getEnumFromValue(HTypeDrugSalesType.class,
                                                                        memberInfoEntityRequest.getDrugSalesType()
                                                                       ));
        memberInfoEntity.setMedicalEquipmentSalesType(
                        EnumTypeUtil.getEnumFromValue(HTypeMedicalEquipmentSalesType.class,
                                                      memberInfoEntityRequest.getMedicalEquipmentSalesType()
                                                     ));
        memberInfoEntity.setDentalMonopolySalesType(EnumTypeUtil.getEnumFromValue(HTypeDentalMonopolySalesType.class,
                                                                                  memberInfoEntityRequest.getDentalMonopolySalesType()
                                                                                 ));
        memberInfoEntity.setCreditPaymentUseFlag(EnumTypeUtil.getEnumFromValue(HTypeCreditPaymentUseFlag.class,
                                                                               memberInfoEntityRequest.getCreditPaymentUseFlag()
                                                                              ));
        memberInfoEntity.setTransferPaymentUseFlag(EnumTypeUtil.getEnumFromValue(HTypeTransferPaymentUseFlag.class,
                                                                                 memberInfoEntityRequest.getTransferPaymentUseFlag()
                                                                                ));
        memberInfoEntity.setCashDeliveryUseFlag(EnumTypeUtil.getEnumFromValue(HTypeCashDeliveryUseFlag.class,
                                                                              memberInfoEntityRequest.getCashDeliveryUseFlag()
                                                                             ));
        memberInfoEntity.setDirectDebitUseFlag(EnumTypeUtil.getEnumFromValue(HTypeDirectDebitUseFlag.class,
                                                                             memberInfoEntityRequest.getDirectDebitUseFlag()
                                                                            ));
        memberInfoEntity.setMonthlyPayUseFlag(EnumTypeUtil.getEnumFromValue(HTypeMonthlyPayUseFlag.class,
                                                                            memberInfoEntityRequest.getMonthlyPayUseFlag()
                                                                           ));
        memberInfoEntity.setMemberListType(EnumTypeUtil.getEnumFromValue(HTypeMemberListType.class,
                                                                         memberInfoEntityRequest.getMemberListType()
                                                                        ));
        memberInfoEntity.setOnlineRegistFlag(EnumTypeUtil.getEnumFromValue(HTypeOnlineRegistFlag.class,
                                                                           memberInfoEntityRequest.getOnlineRegistFlag()
                                                                          ));
        memberInfoEntity.setConfDocumentType(EnumTypeUtil.getEnumFromValue(HTypeConfDocumentType.class,
                                                                           memberInfoEntityRequest.getConfDocumentType()
                                                                          ));
        memberInfoEntity.setMedicalTreatmentFlag(memberInfoEntityRequest.getMedicalTreatmentFlag());
        memberInfoEntity.setMedicalTreatmentMemo(memberInfoEntityRequest.getMedicalTreatmentMemo());
        memberInfoEntity.setMetalPermitFlag(EnumTypeUtil.getEnumFromValue(HTypeMetalPermitFlag.class,
                                                                          memberInfoEntityRequest.getMetalPermitFlag()
                                                                         ));
        // 2023-renew No79 from here
        memberInfoEntity.setOrderCompletePermitFlag(EnumTypeUtil.getEnumFromValue(HTypeOrderCompletePermitFlag.class,
                                                                                  memberInfoEntityRequest.getOrderCompletePermitFlag()
                                                                                 ));
        memberInfoEntity.setDeliveryCompletePermitFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeDeliveryCompletePermitFlag.class,
                                                      memberInfoEntityRequest.getDeliveryCompletePermitFlag()
                                                     ));
        // 2023-renew No79 to here
        memberInfoEntity.setAccountingType(EnumTypeUtil.getEnumFromValue(HTypeAccountingType.class,
                                                                         memberInfoEntityRequest.getAccountingType()
                                                                        ));
        memberInfoEntity.setOnlineLoginAdvisability(EnumTypeUtil.getEnumFromValue(HTypeOnlineLoginAdvisability.class,
                                                                                  memberInfoEntityRequest.getOnlineLoginAdvisability()
                                                                                 ));
        memberInfoEntity.setSendMailPermitFlag(EnumTypeUtil.getEnumFromValue(HTypeSendMailPermitFlag.class,
                                                                             memberInfoEntityRequest.getSendMailPermitFlag()
                                                                            ));
        memberInfoEntity.setSendMailStartTime(
                        conversionUtility.toTimeStamp(memberInfoEntityRequest.getSendMailStartTime()));
        memberInfoEntity.setSendMailStopTime(
                        conversionUtility.toTimeStamp(memberInfoEntityRequest.getSendMailStopTime()));
        memberInfoEntity.setNoAntiSocialFlag(EnumTypeUtil.getEnumFromValue(HTypeNoAntiSocialFlag.class,
                                                                           memberInfoEntityRequest.getNoAntiSocialFlag()
                                                                          ));

        return memberInfoEntity;

    }

    /**
     * アドレス帳に変換
     *
     * @param addressBookUpdateRequest アドレス帳情報更新リクエスト
     * @return アドレス帳
     */
    protected AddressBookEntity toAddressBookEntity(AddressBookUpdateRequest addressBookUpdateRequest) {
        if (addressBookUpdateRequest == null) {
            return null;
        }
        AddressBookEntity addressBookEntity = ApplicationContextUtility.getBean(AddressBookEntity.class);
        addressBookEntity.setAddressBookSeq(addressBookUpdateRequest.getAddressBookSeq());
        addressBookEntity.setMemberInfoSeq(addressBookUpdateRequest.getMemberInfoSeq());
        addressBookEntity.setAddressBookName(addressBookUpdateRequest.getAddressBookName());
        addressBookEntity.setAddressBookFirstName(addressBookUpdateRequest.getAddressBookFirstName());
        addressBookEntity.setAddressBookLastName(addressBookUpdateRequest.getAddressBookLastName());
        addressBookEntity.setAddressBookFirstKana(addressBookUpdateRequest.getAddressBookFirstKana());
        addressBookEntity.setAddressBookLastKana(addressBookUpdateRequest.getAddressBookLastKana());
        addressBookEntity.setAddressBookZipCode(addressBookUpdateRequest.getAddressBookZipCode());
        addressBookEntity.setAddressBookPrefecture(addressBookUpdateRequest.getAddressBookPrefecture());
        addressBookEntity.setAddressBookAddress1(addressBookUpdateRequest.getAddressBookAddress1());
        addressBookEntity.setAddressBookAddress2(addressBookUpdateRequest.getAddressBookAddress2());
        addressBookEntity.setAddressBookAddress3(addressBookUpdateRequest.getAddressBookAddress3());
        addressBookEntity.setAddressBookAddress4(addressBookUpdateRequest.getAddressBookAddress4());
        addressBookEntity.setAddressBookAddress5(addressBookUpdateRequest.getAddressBookAddress5());
        addressBookEntity.setCustomerNo(addressBookUpdateRequest.getCustomerNo());
        addressBookEntity.setAddressBookTel(addressBookUpdateRequest.getAddressBookTel());
        addressBookEntity.setAddressBookApproveFlagPdr(EnumTypeUtil.getEnumFromValue(HTypeAddressBookApproveFlag.class,
                                                                                     addressBookUpdateRequest.getAddressBookApproveFlagPdr()
                                                                                    ));
        addressBookEntity.setRegistTime(conversionUtility.toTimeStamp(addressBookUpdateRequest.getRegistTime()));
        addressBookEntity.setUpdateTime(conversionUtility.toTimeStamp(addressBookUpdateRequest.getUpdateTime()));

        return addressBookEntity;
    }

    /**
     * 本会員登録画面用会員登録から会員に変換
     *
     * @param memberInfoScreenRegistRequest 本会員登録画面用会員登録リクエスト
     * @return 会員情報Entity
     */
    public MemberInfoEntity toMemberInfoEntityForMemberInfoScreenRegist(MemberInfoScreenRegistRequest memberInfoScreenRegistRequest) {

        if (memberInfoScreenRegistRequest == null) {
            return null;
        }

        MemberInfoEntity memberInfoEntity = new MemberInfoEntity();
        memberInfoEntity.setMemberInfoSeq(memberInfoScreenRegistRequest.getMemberInfoSeq());
        memberInfoEntity.setMemberInfoStatus(EnumTypeUtil.getEnumFromValue(HTypeMemberInfoStatus.class,
                                                                           memberInfoScreenRegistRequest.getMemberInfoStatus()
                                                                          ));
        memberInfoEntity.setMemberInfoUniqueId(memberInfoScreenRegistRequest.getMemberInfoUniqueId());
        memberInfoEntity.setMemberInfoId(memberInfoScreenRegistRequest.getMemberInfoId());
        memberInfoEntity.setMemberInfoPassword(memberInfoScreenRegistRequest.getMemberInfoPassword());
        memberInfoEntity.setMemberInfoLastName(memberInfoScreenRegistRequest.getMemberInfoLastName());
        memberInfoEntity.setMemberInfoFirstName(memberInfoScreenRegistRequest.getMemberInfoFirstName());
        memberInfoEntity.setMemberInfoLastKana(memberInfoScreenRegistRequest.getMemberInfoLastKana());
        memberInfoEntity.setMemberInfoFirstKana(memberInfoScreenRegistRequest.getMemberInfoFirstKana());
        memberInfoEntity.setMemberInfoSex(EnumTypeUtil.getEnumFromValue(HTypeSexUnnecessaryAnswer.class,
                                                                        memberInfoScreenRegistRequest.getMemberInfoSex()
                                                                       ));
        memberInfoEntity.setMemberInfoBirthday(
                        conversionUtility.toTimeStamp(memberInfoScreenRegistRequest.getMemberInfoBirthday()));
        memberInfoEntity.setMemberInfoTel(memberInfoScreenRegistRequest.getMemberInfoTel());
        memberInfoEntity.setMemberInfoContactTel(memberInfoScreenRegistRequest.getMemberInfoContactTel());
        memberInfoEntity.setMemberInfoZipCode(memberInfoScreenRegistRequest.getMemberInfoZipCode());
        memberInfoEntity.setMemberInfoPrefecture(memberInfoScreenRegistRequest.getMemberInfoPrefecture());

        memberInfoEntity.setPrefectureType(EnumTypeUtil.getEnumFromValue(HTypePrefectureType.class,
                                                                         memberInfoScreenRegistRequest.getMemberInfoPrefecture()
                                                                        ));
        memberInfoEntity.setMemberInfoAddress1(memberInfoScreenRegistRequest.getMemberInfoAddress1());
        memberInfoEntity.setMemberInfoAddress2(memberInfoScreenRegistRequest.getMemberInfoAddress2());
        memberInfoEntity.setMemberInfoAddress3(memberInfoScreenRegistRequest.getMemberInfoAddress3());
        memberInfoEntity.setMemberInfoMail(memberInfoScreenRegistRequest.getMemberInfoMail());
        memberInfoEntity.setShopSeq(1001);
        memberInfoEntity.setAccessUid(memberInfoScreenRegistRequest.getAccessUid());
        memberInfoEntity.setVersionNo(memberInfoScreenRegistRequest.getVersionNo());
        memberInfoEntity.setAdmissionYmd(memberInfoScreenRegistRequest.getAdmissionYmd());
        memberInfoEntity.setSecessionYmd(memberInfoScreenRegistRequest.getSecessionYmd());
        memberInfoEntity.setMemo(memberInfoScreenRegistRequest.getMemo());
        memberInfoEntity.setMemberInfoFax(memberInfoScreenRegistRequest.getMemberInfoFax());
        memberInfoEntity.setPaymentMemberId(memberInfoScreenRegistRequest.getPaymentMemberId());
        memberInfoEntity.setLastLoginTime(
                        conversionUtility.toTimeStamp(memberInfoScreenRegistRequest.getLastLoginTime()));
        memberInfoEntity.setLastLoginUserAgent(memberInfoScreenRegistRequest.getLastLoginUserAgent());
        memberInfoEntity.setPaymentMemberId(memberInfoScreenRegistRequest.getPaymentMemberId());

        memberInfoEntity.setPaymentCardRegistType(EnumTypeUtil.getEnumFromValue(HTypeCardRegistType.class,
                                                                                memberInfoScreenRegistRequest.getPaymentCardRegistType()
                                                                               ));
        memberInfoEntity.setPasswordNeedChangeFlag(EnumTypeUtil.getEnumFromValue(HTypePasswordNeedChangeFlag.class,
                                                                                 memberInfoScreenRegistRequest.getPasswordNeedChangeFlag()
                                                                                ));
        memberInfoEntity.setLastLoginDeviceType(EnumTypeUtil.getEnumFromValue(HTypeDeviceType.class,
                                                                              memberInfoScreenRegistRequest.getLastLoginDeviceType()
                                                                             ));
        memberInfoEntity.setLoginFailureCount(memberInfoScreenRegistRequest.getLoginFailureCount());
        memberInfoEntity.setRegistTime(conversionUtility.toTimeStamp(memberInfoScreenRegistRequest.getRegistTime()));
        memberInfoEntity.setUpdateTime(conversionUtility.toTimeStamp(memberInfoScreenRegistRequest.getUpdateTime()));
        memberInfoEntity.setAccountLockTime(
                        conversionUtility.toTimeStamp(memberInfoScreenRegistRequest.getAccountLockTime()));
        memberInfoEntity.setCustomerNo(memberInfoScreenRegistRequest.getCustomerNo());
        memberInfoEntity.setRepresentativeName(memberInfoScreenRegistRequest.getRepresentativeName());
        memberInfoEntity.setMemberInfoAddress4(memberInfoScreenRegistRequest.getMemberInfoAddress4());
        memberInfoEntity.setMemberInfoAddress5(memberInfoScreenRegistRequest.getMemberInfoAddress5());
        memberInfoEntity.setBusinessType(EnumTypeUtil.getEnumFromValue(HTypeBusinessType.class,
                                                                       memberInfoScreenRegistRequest.getBusinessType()
                                                                      ));

        // FAXによるおトク情報希望フラグ
        memberInfoEntity.setSendFaxPermitFlag(EnumTypeUtil.getEnumFromValue(HTypeSendFaxPermitFlag.class,
                                                                            memberInfoScreenRegistRequest.getSendFaxPermitFlag()
                                                                           ));

        // DMによるおトク情報希望フラグ
        memberInfoEntity.setSendDirectMailFlag(EnumTypeUtil.getEnumFromValue(HTypeSendDirectMailFlag.class,
                                                                             memberInfoScreenRegistRequest.getSendDirectMailFlag()
                                                                            ));
        memberInfoEntity.setNonConsultationDay(memberInfoScreenRegistRequest.getNonConsultationDay());

        memberInfoEntity.setApproveStatus(EnumTypeUtil.getEnumFromValue(HTypeApproveStatus.class,
                                                                        memberInfoScreenRegistRequest.getApproveStatus()
                                                                       ));
        memberInfoEntity.setDrugSalesType(EnumTypeUtil.getEnumFromValue(HTypeDrugSalesType.class,
                                                                        memberInfoScreenRegistRequest.getDrugSalesType()
                                                                       ));
        memberInfoEntity.setMedicalEquipmentSalesType(
                        EnumTypeUtil.getEnumFromValue(HTypeMedicalEquipmentSalesType.class,
                                                      memberInfoScreenRegistRequest.getMedicalEquipmentSalesType()
                                                     ));
        memberInfoEntity.setDentalMonopolySalesType(EnumTypeUtil.getEnumFromValue(HTypeDentalMonopolySalesType.class,
                                                                                  memberInfoScreenRegistRequest.getDentalMonopolySalesType()
                                                                                 ));
        memberInfoEntity.setCreditPaymentUseFlag(EnumTypeUtil.getEnumFromValue(HTypeCreditPaymentUseFlag.class,
                                                                               memberInfoScreenRegistRequest.getCreditPaymentUseFlag()
                                                                              ));
        memberInfoEntity.setTransferPaymentUseFlag(EnumTypeUtil.getEnumFromValue(HTypeTransferPaymentUseFlag.class,
                                                                                 memberInfoScreenRegistRequest.getTransferPaymentUseFlag()
                                                                                ));
        memberInfoEntity.setCashDeliveryUseFlag(EnumTypeUtil.getEnumFromValue(HTypeCashDeliveryUseFlag.class,
                                                                              memberInfoScreenRegistRequest.getCashDeliveryUseFlag()
                                                                             ));
        memberInfoEntity.setDirectDebitUseFlag(EnumTypeUtil.getEnumFromValue(HTypeDirectDebitUseFlag.class,
                                                                             memberInfoScreenRegistRequest.getDirectDebitUseFlag()
                                                                            ));
        memberInfoEntity.setMonthlyPayUseFlag(EnumTypeUtil.getEnumFromValue(HTypeMonthlyPayUseFlag.class,
                                                                            memberInfoScreenRegistRequest.getMonthlyPayUseFlag()
                                                                           ));
        memberInfoEntity.setMemberListType(EnumTypeUtil.getEnumFromValue(HTypeMemberListType.class,
                                                                         memberInfoScreenRegistRequest.getMemberListType()
                                                                        ));
        memberInfoEntity.setOnlineRegistFlag(EnumTypeUtil.getEnumFromValue(HTypeOnlineRegistFlag.class,
                                                                           memberInfoScreenRegistRequest.getOnlineRegistFlag()
                                                                          ));
        memberInfoEntity.setConfDocumentType(EnumTypeUtil.getEnumFromValue(HTypeConfDocumentType.class,
                                                                           memberInfoScreenRegistRequest.getConfDocumentType()
                                                                          ));
        memberInfoEntity.setMedicalTreatmentFlag(memberInfoScreenRegistRequest.getMedicalTreatmentFlag());
        memberInfoEntity.setMedicalTreatmentMemo(memberInfoScreenRegistRequest.getMedicalTreatmentMemo());
        memberInfoEntity.setMetalPermitFlag(EnumTypeUtil.getEnumFromValue(HTypeMetalPermitFlag.class,
                                                                          memberInfoScreenRegistRequest.getMetalPermitFlag()
                                                                         ));
        // 2023-renew No79 from here
        memberInfoEntity.setOrderCompletePermitFlag(EnumTypeUtil.getEnumFromValue(HTypeOrderCompletePermitFlag.class,
                                                                                  memberInfoScreenRegistRequest.getOrderCompletePermitFlag()
                                                                                 ));
        memberInfoEntity.setDeliveryCompletePermitFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeDeliveryCompletePermitFlag.class,
                                                      memberInfoScreenRegistRequest.getDeliveryCompletePermitFlag()
                                                     ));
        // 2023-renew No79 to here
        memberInfoEntity.setAccountingType(EnumTypeUtil.getEnumFromValue(HTypeAccountingType.class,
                                                                         memberInfoScreenRegistRequest.getAccountingType()
                                                                        ));
        memberInfoEntity.setOnlineLoginAdvisability(EnumTypeUtil.getEnumFromValue(HTypeOnlineLoginAdvisability.class,
                                                                                  memberInfoScreenRegistRequest.getOnlineLoginAdvisability()
                                                                                 ));
        memberInfoEntity.setSendMailPermitFlag(EnumTypeUtil.getEnumFromValue(HTypeSendMailPermitFlag.class,
                                                                             memberInfoScreenRegistRequest.getSendMailPermitFlag()
                                                                            ));
        memberInfoEntity.setSendMailStartTime(
                        conversionUtility.toTimeStamp(memberInfoScreenRegistRequest.getSendMailStartTime()));
        memberInfoEntity.setSendMailStopTime(
                        conversionUtility.toTimeStamp(memberInfoScreenRegistRequest.getSendMailStopTime()));
        memberInfoEntity.setNoAntiSocialFlag(EnumTypeUtil.getEnumFromValue(HTypeNoAntiSocialFlag.class,
                                                                           memberInfoScreenRegistRequest.getNoAntiSocialFlag()
                                                                          ));

        return memberInfoEntity;
    }

    /**
     * 会員エンティティに変換
     *
     * @param memberInfoScreenUpdateRequest 本会員登録画面用会員情報更新リクエスト
     * @return 会員情報Entity
     */
    public MemberInfoEntity toMemberInfoEntityForMemberInfoScreenUpdate(MemberInfoScreenUpdateRequest memberInfoScreenUpdateRequest) {

        if (memberInfoScreenUpdateRequest == null) {
            return null;
        }

        // 元会員情報コピー
        MemberInfoEntity memberInfoEntity = ApplicationContextUtility.getBean(MemberInfoEntity.class);
        if (memberInfoScreenUpdateRequest.getMemberInfoSeq() != null) {
            MemberInfoGetService memberInfoGetService = ApplicationContextUtility.getBean(MemberInfoGetService.class);
            memberInfoEntity = memberInfoGetService.execute(memberInfoScreenUpdateRequest.getMemberInfoSeq());
        }

        memberInfoEntity.setMemberInfoSeq(memberInfoScreenUpdateRequest.getMemberInfoSeq());
        memberInfoEntity.setMemberInfoStatus(EnumTypeUtil.getEnumFromValue(HTypeMemberInfoStatus.class,
                                                                           memberInfoScreenUpdateRequest.getMemberInfoStatus()
                                                                          ));
        memberInfoEntity.setMemberInfoUniqueId(memberInfoScreenUpdateRequest.getMemberInfoUniqueId());
        memberInfoEntity.setMemberInfoId(memberInfoScreenUpdateRequest.getMemberInfoId());
        memberInfoEntity.setMemberInfoPassword(memberInfoScreenUpdateRequest.getMemberInfoPassword());
        memberInfoEntity.setMemberInfoLastName(memberInfoScreenUpdateRequest.getMemberInfoLastName());
        memberInfoEntity.setMemberInfoFirstName(memberInfoScreenUpdateRequest.getMemberInfoFirstName());
        memberInfoEntity.setMemberInfoLastKana(memberInfoScreenUpdateRequest.getMemberInfoLastKana());
        memberInfoEntity.setMemberInfoFirstKana(memberInfoScreenUpdateRequest.getMemberInfoFirstKana());
        memberInfoEntity.setMemberInfoSex(EnumTypeUtil.getEnumFromValue(HTypeSexUnnecessaryAnswer.class,
                                                                        memberInfoScreenUpdateRequest.getMemberInfoSex()
                                                                       ));
        memberInfoEntity.setMemberInfoBirthday(
                        conversionUtility.toTimeStamp(memberInfoScreenUpdateRequest.getMemberInfoBirthday()));
        memberInfoEntity.setMemberInfoTel(memberInfoScreenUpdateRequest.getMemberInfoTel());
        memberInfoEntity.setMemberInfoContactTel(memberInfoScreenUpdateRequest.getMemberInfoContactTel());
        memberInfoEntity.setMemberInfoZipCode(memberInfoScreenUpdateRequest.getMemberInfoZipCode());
        memberInfoEntity.setMemberInfoPrefecture(memberInfoScreenUpdateRequest.getMemberInfoPrefecture());
        memberInfoEntity.setPrefectureType(EnumTypeUtil.getEnumFromValue(HTypePrefectureType.class,
                                                                         memberInfoScreenUpdateRequest.getPrefectureType()
                                                                        ));
        memberInfoEntity.setMemberInfoAddress1(memberInfoScreenUpdateRequest.getMemberInfoAddress1());
        memberInfoEntity.setMemberInfoAddress2(memberInfoScreenUpdateRequest.getMemberInfoAddress2());
        memberInfoEntity.setMemberInfoAddress3(memberInfoScreenUpdateRequest.getMemberInfoAddress3());
        memberInfoEntity.setMemberInfoMail(memberInfoScreenUpdateRequest.getMemberInfoMail());
        memberInfoEntity.setAccessUid(memberInfoScreenUpdateRequest.getAccessUid());
        memberInfoEntity.setVersionNo(memberInfoScreenUpdateRequest.getVersionNo());
        memberInfoEntity.setAdmissionYmd(memberInfoScreenUpdateRequest.getAdmissionYmd());
        memberInfoEntity.setSecessionYmd(memberInfoScreenUpdateRequest.getSecessionYmd());
        memberInfoEntity.setMemo(memberInfoScreenUpdateRequest.getMemo());
        memberInfoEntity.setMemberInfoFax(memberInfoScreenUpdateRequest.getMemberInfoFax());
        memberInfoEntity.setLastLoginTime(
                        conversionUtility.toTimeStamp(memberInfoScreenUpdateRequest.getLastLoginTime()));
        memberInfoEntity.setLastLoginUserAgent(memberInfoScreenUpdateRequest.getLastLoginUserAgent());
        memberInfoEntity.setPaymentMemberId(memberInfoScreenUpdateRequest.getPaymentMemberId());
        memberInfoEntity.setPaymentCardRegistType(EnumTypeUtil.getEnumFromValue(HTypeCardRegistType.class,
                                                                                memberInfoScreenUpdateRequest.getPaymentCardRegistType()
                                                                               ));
        memberInfoEntity.setPasswordNeedChangeFlag(EnumTypeUtil.getEnumFromValue(HTypePasswordNeedChangeFlag.class,
                                                                                 memberInfoScreenUpdateRequest.getPasswordNeedChangeFlag()
                                                                                ));
        memberInfoEntity.setLastLoginDeviceType(EnumTypeUtil.getEnumFromValue(HTypeDeviceType.class,
                                                                              memberInfoScreenUpdateRequest.getLastLoginDeviceType()
                                                                             ));
        memberInfoEntity.setLoginFailureCount(memberInfoScreenUpdateRequest.getLoginFailureCount());
        memberInfoEntity.setAccountLockTime(
                        conversionUtility.toTimeStamp(memberInfoScreenUpdateRequest.getAccountLockTime()));
        memberInfoEntity.setRegistTime(conversionUtility.toTimeStamp(memberInfoScreenUpdateRequest.getRegistTime()));
        memberInfoEntity.setUpdateTime(conversionUtility.toTimeStamp(memberInfoScreenUpdateRequest.getUpdateTime()));
        memberInfoEntity.setCustomerNo(memberInfoScreenUpdateRequest.getCustomerNo());
        memberInfoEntity.setRepresentativeName(memberInfoScreenUpdateRequest.getRepresentativeName());
        memberInfoEntity.setMemberInfoAddress4(memberInfoScreenUpdateRequest.getMemberInfoAddress4());
        memberInfoEntity.setMemberInfoAddress5(memberInfoScreenUpdateRequest.getMemberInfoAddress5());
        memberInfoEntity.setBusinessType(EnumTypeUtil.getEnumFromValue(HTypeBusinessType.class,
                                                                       memberInfoScreenUpdateRequest.getBusinessType()
                                                                      ));
        memberInfoEntity.setSendFaxPermitFlag(EnumTypeUtil.getEnumFromValue(HTypeSendFaxPermitFlag.class,
                                                                            memberInfoScreenUpdateRequest.getSendFaxPermitFlag()
                                                                           ));
        memberInfoEntity.setSendDirectMailFlag(EnumTypeUtil.getEnumFromValue(HTypeSendDirectMailFlag.class,
                                                                             memberInfoScreenUpdateRequest.getSendDirectMailFlag()
                                                                            ));
        memberInfoEntity.setNonConsultationDay(memberInfoScreenUpdateRequest.getNonConsultationDay());
        memberInfoEntity.setApproveStatus(EnumTypeUtil.getEnumFromValue(HTypeApproveStatus.class,
                                                                        memberInfoScreenUpdateRequest.getApproveStatus()
                                                                       ));
        memberInfoEntity.setDrugSalesType(EnumTypeUtil.getEnumFromValue(HTypeDrugSalesType.class,
                                                                        memberInfoScreenUpdateRequest.getDrugSalesType()
                                                                       ));
        memberInfoEntity.setMedicalEquipmentSalesType(
                        EnumTypeUtil.getEnumFromValue(HTypeMedicalEquipmentSalesType.class,
                                                      memberInfoScreenUpdateRequest.getMedicalEquipmentSalesType()
                                                     ));
        memberInfoEntity.setDentalMonopolySalesType(EnumTypeUtil.getEnumFromValue(HTypeDentalMonopolySalesType.class,
                                                                                  memberInfoScreenUpdateRequest.getDentalMonopolySalesType()
                                                                                 ));
        memberInfoEntity.setCreditPaymentUseFlag(EnumTypeUtil.getEnumFromValue(HTypeCreditPaymentUseFlag.class,
                                                                               memberInfoScreenUpdateRequest.getCreditPaymentUseFlag()
                                                                              ));
        memberInfoEntity.setTransferPaymentUseFlag(EnumTypeUtil.getEnumFromValue(HTypeTransferPaymentUseFlag.class,
                                                                                 memberInfoScreenUpdateRequest.getTransferPaymentUseFlag()
                                                                                ));
        memberInfoEntity.setCashDeliveryUseFlag(EnumTypeUtil.getEnumFromValue(HTypeCashDeliveryUseFlag.class,
                                                                              memberInfoScreenUpdateRequest.getCashDeliveryUseFlag()
                                                                             ));
        memberInfoEntity.setDirectDebitUseFlag(EnumTypeUtil.getEnumFromValue(HTypeDirectDebitUseFlag.class,
                                                                             memberInfoScreenUpdateRequest.getDirectDebitUseFlag()
                                                                            ));
        memberInfoEntity.setMonthlyPayUseFlag(EnumTypeUtil.getEnumFromValue(HTypeMonthlyPayUseFlag.class,
                                                                            memberInfoScreenUpdateRequest.getMonthlyPayUseFlag()
                                                                           ));
        memberInfoEntity.setMemberListType(EnumTypeUtil.getEnumFromValue(HTypeMemberListType.class,
                                                                         memberInfoScreenUpdateRequest.getMemberListType()
                                                                        ));
        memberInfoEntity.setOnlineRegistFlag(EnumTypeUtil.getEnumFromValue(HTypeOnlineRegistFlag.class,
                                                                           memberInfoScreenUpdateRequest.getOnlineRegistFlag()
                                                                          ));
        memberInfoEntity.setConfDocumentType(EnumTypeUtil.getEnumFromValue(HTypeConfDocumentType.class,
                                                                           memberInfoScreenUpdateRequest.getConfDocumentType()
                                                                          ));
        memberInfoEntity.setMedicalTreatmentFlag(memberInfoScreenUpdateRequest.getMedicalTreatmentFlag());
        memberInfoEntity.setMedicalTreatmentMemo(memberInfoScreenUpdateRequest.getMedicalTreatmentMemo());
        memberInfoEntity.setMetalPermitFlag(EnumTypeUtil.getEnumFromValue(HTypeMetalPermitFlag.class,
                                                                          memberInfoScreenUpdateRequest.getMetalPermitFlag()
                                                                         ));
        // 2023-renew No79 from here
        memberInfoEntity.setOrderCompletePermitFlag(EnumTypeUtil.getEnumFromValue(HTypeOrderCompletePermitFlag.class,
                                                                                  memberInfoScreenUpdateRequest.getOrderCompletePermitFlag()
                                                                                 ));
        memberInfoEntity.setDeliveryCompletePermitFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeDeliveryCompletePermitFlag.class,
                                                      memberInfoScreenUpdateRequest.getDeliveryCompletePermitFlag()
                                                     ));
        // 2023-renew No79 to here
        memberInfoEntity.setAccountingType(EnumTypeUtil.getEnumFromValue(HTypeAccountingType.class,
                                                                         memberInfoScreenUpdateRequest.getAccountingType()
                                                                        ));
        memberInfoEntity.setOnlineLoginAdvisability(EnumTypeUtil.getEnumFromValue(HTypeOnlineLoginAdvisability.class,
                                                                                  memberInfoScreenUpdateRequest.getOnlineLoginAdvisability()
                                                                                 ));
        memberInfoEntity.setSendMailPermitFlag(EnumTypeUtil.getEnumFromValue(HTypeSendMailPermitFlag.class,
                                                                             memberInfoScreenUpdateRequest.getSendMailPermitFlag()
                                                                            ));
        memberInfoEntity.setSendMailStartTime(
                        conversionUtility.toTimeStamp(memberInfoScreenUpdateRequest.getSendMailStartTime()));
        memberInfoEntity.setSendMailStopTime(
                        conversionUtility.toTimeStamp(memberInfoScreenUpdateRequest.getSendMailStopTime()));
        memberInfoEntity.setNoAntiSocialFlag(EnumTypeUtil.getEnumFromValue(HTypeNoAntiSocialFlag.class,
                                                                           memberInfoScreenUpdateRequest.getNoAntiSocialFlag()
                                                                          ));

        // 会員情報更新完了ページに表示するため、メールアドレスを上書きする。
        memberInfoScreenUpdateRequest.setMemberInfoMail(memberInfoScreenUpdateRequest.getInputMemberInfoMail());

        return memberInfoEntity;
    }

    /**
     * アドレス帳Entityレスポンスに変換
     *
     * @param addressBookEntity アドレス帳クラス
     * @return アドレス帳Entityレスポンス
     */
    public AddressBookEntityResponse toAddressBookEntityResponse(AddressBookEntity addressBookEntity)
                    throws InvocationTargetException, IllegalAccessException {

        if (addressBookEntity == null) {
            return null;
        }

        AddressBookEntityResponse addressBookEntityResponse = new AddressBookEntityResponse();

        addressBookEntityResponse.setAddressBookSeq(addressBookEntity.getAddressBookSeq());
        addressBookEntityResponse.setMemberInfoSeq(addressBookEntity.getMemberInfoSeq());
        addressBookEntityResponse.setAddressBookName(addressBookEntity.getAddressBookName());
        addressBookEntityResponse.setAddressBookLastName(addressBookEntity.getAddressBookLastName());
        addressBookEntityResponse.setAddressBookFirstName(addressBookEntity.getAddressBookFirstName());
        addressBookEntityResponse.setAddressBookLastKana(addressBookEntity.getAddressBookLastKana());
        addressBookEntityResponse.setAddressBookFirstKana(addressBookEntity.getAddressBookFirstKana());
        addressBookEntityResponse.setAddressBookTel(addressBookEntity.getAddressBookTel());
        addressBookEntityResponse.setAddressBookZipCode(addressBookEntity.getAddressBookZipCode());
        addressBookEntityResponse.setAddressBookPrefecture(addressBookEntity.getAddressBookPrefecture());
        addressBookEntityResponse.setAddressBookAddress1(addressBookEntity.getAddressBookAddress1());
        addressBookEntityResponse.setAddressBookAddress2(addressBookEntity.getAddressBookAddress2());
        addressBookEntityResponse.setAddressBookAddress3(addressBookEntity.getAddressBookAddress3());
        addressBookEntityResponse.setRegistTime(addressBookEntity.getRegistTime());
        addressBookEntityResponse.setUpdateTime(addressBookEntity.getUpdateTime());
        addressBookEntityResponse.setAddressBookAddress4(addressBookEntity.getAddressBookAddress4());
        addressBookEntityResponse.setAddressBookAddress5(addressBookEntity.getAddressBookAddress5());
        addressBookEntityResponse.setCustomerNo(addressBookEntity.getCustomerNo());

        if (addressBookEntity.getAddressBookApproveFlagPdr() != null) {
            addressBookEntityResponse.setAddressBookApproveFlag(
                            addressBookEntity.getAddressBookApproveFlagPdr().getValue());
        }

        return addressBookEntityResponse;
    }

    /**
     * 確認メールEntityレスポンスに変換
     *
     * @param confirmMailEntity 確認メールクラス
     * @return 確認メールEntityレスポンス
     */
    public ConfirmMailEntityResponse toConfirmMailEntityResponse(ConfirmMailEntity confirmMailEntity)
                    throws InvocationTargetException, IllegalAccessException {

        if (confirmMailEntity == null) {
            return null;
        }

        ConfirmMailEntityResponse confirmMailEntityResponse = new ConfirmMailEntityResponse();

        confirmMailEntityResponse.setConfirmMailSeq(confirmMailEntity.getConfirmMailSeq());
        confirmMailEntityResponse.setMemberInfoSeq(confirmMailEntity.getMemberInfoSeq());
        confirmMailEntityResponse.setOrderSeq(confirmMailEntity.getOrderSeq());
        confirmMailEntityResponse.setConfirmMail(confirmMailEntity.getConfirmMail());
        confirmMailEntityResponse.setConfirmMailPassword(confirmMailEntity.getConfirmMailPassword());
        confirmMailEntityResponse.setEffectiveTime(confirmMailEntity.getEffectiveTime());
        confirmMailEntityResponse.setRegistTime(confirmMailEntity.getRegistTime());
        confirmMailEntityResponse.setUpdateTime(confirmMailEntity.getUpdateTime());

        if (confirmMailEntity.getConfirmMailType() != null) {
            confirmMailEntityResponse.setConfirmMailType(confirmMailEntity.getConfirmMailType().getValue());
        }

        return confirmMailEntityResponse;
    }

    /**
     * 会員クラスに変換
     *
     * @param loginAdvisabilityGetRequest ログイン可否判定取得リクエスト
     * @return 会員クラス
     */
    public MemberInfoEntity toMemberInfoEntity(LoginAdvisabilityGetRequest loginAdvisabilityGetRequest) {

        if (loginAdvisabilityGetRequest == null) {
            return null;
        }

        MemberInfoEntity memberInfoEntity = new MemberInfoEntity();
        memberInfoEntity.setMemberInfoStatus(EnumTypeUtil.getEnumFromValue(HTypeMemberInfoStatus.class,
                                                                           loginAdvisabilityGetRequest.getMemberInfoStatus()
                                                                          ));
        memberInfoEntity.setApproveStatus(EnumTypeUtil.getEnumFromValue(HTypeApproveStatus.class,
                                                                        loginAdvisabilityGetRequest.getApproveStatus()
                                                                       ));
        memberInfoEntity.setOnlineLoginAdvisability(EnumTypeUtil.getEnumFromValue(HTypeOnlineLoginAdvisability.class,
                                                                                  loginAdvisabilityGetRequest.getOnlineloginAdvisability()
                                                                                 ));
        memberInfoEntity.setMemberListType(EnumTypeUtil.getEnumFromValue(HTypeMemberListType.class,
                                                                         loginAdvisabilityGetRequest.getMemberListType()
                                                                        ));
        memberInfoEntity.setAccountingType(EnumTypeUtil.getEnumFromValue(HTypeAccountingType.class,
                                                                         loginAdvisabilityGetRequest.getAccountingType()
                                                                        ));

        return memberInfoEntity;
    }

    /**
     * ログイン可否判定Entityレスポンスに変換
     *
     * @param loginAdvisabilityEntity ログイン可否判定Entity
     * @return ログイン可否判定Entityレスポンス
     */
    public LoginAdvisabilityEntityResponse toLoginAdvisabilityEntityResponse(LoginAdvisabilityEntity loginAdvisabilityEntity)
                    throws InvocationTargetException, IllegalAccessException {

        if (loginAdvisabilityEntity == null) {
            return null;
        }

        LoginAdvisabilityEntityResponse loginAdvisabilityEntityResponse = new LoginAdvisabilityEntityResponse();

        loginAdvisabilityEntityResponse.setLoginAdvisabilityseq(loginAdvisabilityEntity.getLoginAdvisabilityseq());
        loginAdvisabilityEntityResponse.setMemberInfoStatus(loginAdvisabilityEntity.getMemberInfoStatus());
        loginAdvisabilityEntityResponse.setApproveStatus(loginAdvisabilityEntity.getApproveStatus());
        loginAdvisabilityEntityResponse.setOnlineloginAdvisability(
                        loginAdvisabilityEntity.getOnlineloginAdvisability());
        loginAdvisabilityEntityResponse.setMemberListType(loginAdvisabilityEntity.getMemberListType());
        loginAdvisabilityEntityResponse.setAccountingType(loginAdvisabilityEntity.getAccountingType());
        loginAdvisabilityEntityResponse.setLoginAdvisabilitytype(loginAdvisabilityEntity.getLoginAdvisabilitytype());

        return loginAdvisabilityEntityResponse;
    }

    /**
     * 会員Entityレスポンスに変換
     *
     * @param memberInfoEntity 会員クラス
     * @return 会員Entityレスポンス
     */
    public MemberInfoEntityResponse toMemberInfoEntityResponse(MemberInfoEntity memberInfoEntity)
                    throws InvocationTargetException, IllegalAccessException {

        if (memberInfoEntity == null) {
            return null;
        }

        MemberInfoEntityResponse memberInfoEntityResponse = new MemberInfoEntityResponse();

        memberInfoEntityResponse.setMemberInfoSeq(memberInfoEntity.getMemberInfoSeq());
        memberInfoEntityResponse.setMemberInfoUniqueId(memberInfoEntity.getMemberInfoUniqueId());
        memberInfoEntityResponse.setMemberInfoId(memberInfoEntity.getMemberInfoId());
        memberInfoEntityResponse.setMemberInfoPassword(memberInfoEntity.getMemberInfoPassword());
        memberInfoEntityResponse.setMemberInfoLastName(memberInfoEntity.getMemberInfoLastName());
        memberInfoEntityResponse.setMemberInfoFirstName(memberInfoEntity.getMemberInfoFirstName());
        memberInfoEntityResponse.setMemberInfoLastKana(memberInfoEntity.getMemberInfoLastKana());
        memberInfoEntityResponse.setMemberInfoFirstKana(memberInfoEntity.getMemberInfoFirstKana());
        memberInfoEntityResponse.setMemberInfoBirthday(memberInfoEntity.getMemberInfoBirthday());
        memberInfoEntityResponse.setMemberInfoTel(memberInfoEntity.getMemberInfoTel());
        memberInfoEntityResponse.setMemberInfoContactTel(memberInfoEntity.getMemberInfoContactTel());
        memberInfoEntityResponse.setMemberInfoZipCode(memberInfoEntity.getMemberInfoZipCode());
        memberInfoEntityResponse.setMemberInfoPrefecture(memberInfoEntity.getMemberInfoPrefecture());
        memberInfoEntityResponse.setMemberInfoAddress1(memberInfoEntity.getMemberInfoAddress1());
        memberInfoEntityResponse.setMemberInfoAddress2(memberInfoEntity.getMemberInfoAddress2());
        memberInfoEntityResponse.setMemberInfoAddress3(memberInfoEntity.getMemberInfoAddress3());
        memberInfoEntityResponse.setMemberInfoMail(memberInfoEntity.getMemberInfoMail());
        memberInfoEntityResponse.setAccessUid(memberInfoEntity.getAccessUid());
        memberInfoEntityResponse.setVersionNo(memberInfoEntity.getVersionNo());
        memberInfoEntityResponse.setAdmissionYmd(memberInfoEntity.getAdmissionYmd());
        memberInfoEntityResponse.setSecessionYmd(memberInfoEntity.getSecessionYmd());
        memberInfoEntityResponse.setMemo(memberInfoEntity.getMemo());
        memberInfoEntityResponse.setMemberInfoFax(memberInfoEntity.getMemberInfoFax());
        memberInfoEntityResponse.setLastLoginTime(memberInfoEntity.getLastLoginTime());
        memberInfoEntityResponse.setLastLoginUserAgent(memberInfoEntity.getLastLoginUserAgent());
        memberInfoEntityResponse.setPaymentMemberId(memberInfoEntity.getPaymentMemberId());
        memberInfoEntityResponse.setLoginFailureCount(memberInfoEntity.getLoginFailureCount());
        memberInfoEntityResponse.setAccountLockTime(memberInfoEntity.getAccountLockTime());
        memberInfoEntityResponse.setRegistTime(memberInfoEntity.getRegistTime());
        memberInfoEntityResponse.setUpdateTime(memberInfoEntity.getUpdateTime());
        memberInfoEntityResponse.setCustomerNo(memberInfoEntity.getCustomerNo());
        memberInfoEntityResponse.setRepresentativeName(memberInfoEntity.getRepresentativeName());
        memberInfoEntityResponse.setMemberInfoAddress4(memberInfoEntity.getMemberInfoAddress4());
        memberInfoEntityResponse.setMemberInfoAddress5(memberInfoEntity.getMemberInfoAddress5());
        memberInfoEntityResponse.setNonConsultationDay(memberInfoEntity.getNonConsultationDay());
        memberInfoEntityResponse.setMedicalTreatmentFlag(memberInfoEntity.getMedicalTreatmentFlag());
        memberInfoEntityResponse.setMedicalTreatmentMemo(memberInfoEntity.getMedicalTreatmentMemo());
        memberInfoEntityResponse.setSendMailStartTime(memberInfoEntity.getSendMailStartTime());
        memberInfoEntityResponse.setSendMailStopTime(memberInfoEntity.getSendMailStopTime());

        if (memberInfoEntity.getMemberInfoStatus() != null) {
            memberInfoEntityResponse.setMemberInfoStatus(memberInfoEntity.getMemberInfoStatus().getValue());
        }
        if (memberInfoEntity.getMemberInfoSex() != null) {
            memberInfoEntityResponse.setMemberInfoSex(memberInfoEntity.getMemberInfoSex().getValue());
        }
        if (memberInfoEntity.getPrefectureType() != null) {
            memberInfoEntityResponse.setPrefectureType(memberInfoEntity.getPrefectureType().getValue());
        }
        if (memberInfoEntity.getPaymentCardRegistType() != null) {
            memberInfoEntityResponse.setPaymentCardRegistType(memberInfoEntity.getPaymentCardRegistType().getValue());
        }
        if (memberInfoEntity.getPasswordNeedChangeFlag() != null) {
            memberInfoEntityResponse.setPasswordNeedChangeFlag(memberInfoEntity.getPasswordNeedChangeFlag().getValue());
        }
        if (memberInfoEntity.getLastLoginDeviceType() != null) {
            memberInfoEntityResponse.setLastLoginDeviceType(memberInfoEntity.getLastLoginDeviceType().getValue());
        }
        if (memberInfoEntity.getBusinessType() != null) {
            memberInfoEntityResponse.setBusinessType(memberInfoEntity.getBusinessType().getValue());
        }
        if (memberInfoEntity.getSendFaxPermitFlag() != null) {
            memberInfoEntityResponse.setSendFaxPermitFlag(memberInfoEntity.getSendFaxPermitFlag().getValue());
        }
        if (memberInfoEntity.getSendDirectMailFlag() != null) {
            memberInfoEntityResponse.setSendDirectMailFlag(memberInfoEntity.getSendDirectMailFlag().getValue());
        }
        if (memberInfoEntity.getApproveStatus() != null) {
            memberInfoEntityResponse.setApproveStatus(memberInfoEntity.getApproveStatus().getValue());
        }
        if (memberInfoEntity.getDrugSalesType() != null) {
            memberInfoEntityResponse.setDrugSalesType(memberInfoEntity.getDrugSalesType().getValue());
        }
        if (memberInfoEntity.getMedicalEquipmentSalesType() != null) {
            memberInfoEntityResponse.setMedicalEquipmentSalesType(
                            memberInfoEntity.getMedicalEquipmentSalesType().getValue());
        }
        if (memberInfoEntity.getDentalMonopolySalesType() != null) {
            memberInfoEntityResponse.setDentalMonopolySalesType(
                            memberInfoEntity.getDentalMonopolySalesType().getValue());
        }
        if (memberInfoEntity.getCreditPaymentUseFlag() != null) {
            memberInfoEntityResponse.setCreditPaymentUseFlag(memberInfoEntity.getCreditPaymentUseFlag().getValue());
        }
        if (memberInfoEntity.getTransferPaymentUseFlag() != null) {
            memberInfoEntityResponse.setTransferPaymentUseFlag(memberInfoEntity.getTransferPaymentUseFlag().getValue());
        }
        if (memberInfoEntity.getCashDeliveryUseFlag() != null) {
            memberInfoEntityResponse.setCashDeliveryUseFlag(memberInfoEntity.getCashDeliveryUseFlag().getValue());
        }
        if (memberInfoEntity.getDirectDebitUseFlag() != null) {
            memberInfoEntityResponse.setDirectDebitUseFlag(memberInfoEntity.getDirectDebitUseFlag().getValue());
        }
        if (memberInfoEntity.getMonthlyPayUseFlag() != null) {
            memberInfoEntityResponse.setMonthlyPayUseFlag(memberInfoEntity.getMonthlyPayUseFlag().getValue());
        }
        if (memberInfoEntity.getMemberListType() != null) {
            memberInfoEntityResponse.setMemberListType(memberInfoEntity.getMemberListType().getValue());
        }
        if (memberInfoEntity.getOnlineRegistFlag() != null) {
            memberInfoEntityResponse.setOnlineRegistFlag(memberInfoEntity.getOnlineRegistFlag().getValue());
        }
        if (memberInfoEntity.getConfDocumentType() != null) {
            memberInfoEntityResponse.setConfDocumentType(memberInfoEntity.getConfDocumentType().getValue());
        }
        if (memberInfoEntity.getMetalPermitFlag() != null) {
            memberInfoEntityResponse.setMetalPermitFlag(memberInfoEntity.getMetalPermitFlag().getValue());
        }
        // 2023-renew No79 from here
        if (memberInfoEntity.getOrderCompletePermitFlag() != null) {
            memberInfoEntityResponse.setOrderCompletePermitFlag(
                            memberInfoEntity.getOrderCompletePermitFlag().getValue());
        }
        if (memberInfoEntity.getDeliveryCompletePermitFlag() != null) {
            memberInfoEntityResponse.setDeliveryCompletePermitFlag(
                            memberInfoEntity.getDeliveryCompletePermitFlag().getValue());
        }
        // 2023-renew No79 to here
        if (memberInfoEntity.getAccountingType() != null) {
            memberInfoEntityResponse.setAccountingType(memberInfoEntity.getAccountingType().getValue());
        }
        if (memberInfoEntity.getOnlineLoginAdvisability() != null) {
            memberInfoEntityResponse.setOnlineLoginAdvisability(
                            memberInfoEntity.getOnlineLoginAdvisability().getValue());
        }
        if (memberInfoEntity.getSendMailPermitFlag() != null) {
            memberInfoEntityResponse.setSendMailPermitFlag(memberInfoEntity.getSendMailPermitFlag().getValue());
        }
        if (memberInfoEntity.getNoAntiSocialFlag() != null) {
            memberInfoEntityResponse.setNoAntiSocialFlag(memberInfoEntity.getNoAntiSocialFlag().getValue());
        }
        // 2023-renew No71 from here
        //  TOP画面にセール対象商品が存在する旨を通知するフラグ
        if (memberInfoEntity.getTopSaleAnnounceFlg() != null) {
            memberInfoEntityResponse.setTopSaleAnnounceFlg(memberInfoEntity.getTopSaleAnnounceFlg().getValue());
        }
        // セール通知の既読状況
        if (memberInfoEntity.getSaleAnnounceWatchFlg() != null) {
            memberInfoEntityResponse.setSaleAnnounceWatchFlg(memberInfoEntity.getSaleAnnounceWatchFlg().getValue());
        }
        // トップ入荷通知フラグ
        if (memberInfoEntity.getTopStockAnnounceFlg() != null) {
            memberInfoEntityResponse.setTopStockAnnounceFlg(memberInfoEntity.getTopStockAnnounceFlg().getValue());
        }
        // 入荷通知既読フラグ
        if (memberInfoEntity.getStockAnnounceWatchFlg() != null) {
            memberInfoEntityResponse.setStockAnnounceWatchFlg(memberInfoEntity.getStockAnnounceWatchFlg().getValue());
        }
        // 2023-renew No71 to here

        return memberInfoEntityResponse;
    }

    /**
     * お気に入りDtoクラスリストに変換
     *
     * @param favoriteGoodsStockStatusGetRequest お気に入り商品在庫状況取得リクエスト
     * @return お気に入りDtoクラスリスト
     */
    public List<FavoriteDto> toFavoriteDtoList(FavoriteGoodsStockStatusGetRequest favoriteGoodsStockStatusGetRequest)
                    throws InvocationTargetException, IllegalAccessException {

        if (favoriteGoodsStockStatusGetRequest == null
            || favoriteGoodsStockStatusGetRequest.getFavoriteDtoListRequest() == null) {
            return new ArrayList<>();
        }

        List<FavoriteDto> favoriteDtoList = new ArrayList<>();

        for (FavoriteDtoRequest favoriteDtoRequest : favoriteGoodsStockStatusGetRequest.getFavoriteDtoListRequest()) {
            FavoriteDto favoriteDto = new FavoriteDto();

            if (favoriteDtoRequest.getFavoriteEntityRequest() != null) {
                FavoriteEntity favoriteEntity = new FavoriteEntity();
                FavoriteEntityRequest favoriteEntityRequest = favoriteDtoRequest.getFavoriteEntityRequest();

                favoriteEntity.setMemberInfoSeq(favoriteEntityRequest.getMemberInfoSeq());
                favoriteEntity.setGoodsSeq(favoriteEntityRequest.getGoodsSeq());
                favoriteEntity.setRegistTime(conversionUtility.toTimeStamp(favoriteEntityRequest.getRegistTime()));
                favoriteEntity.setUpdateTime(conversionUtility.toTimeStamp(favoriteEntityRequest.getUpdateTime()));

                favoriteDto.setFavoriteEntity(favoriteEntity);
            }

            if (favoriteDtoRequest.getGoodsDetailsDtoRequest() != null) {
                GoodsDetailsDto goodsDetailsDto = toGoodsDetailsDto(favoriteDtoRequest.getGoodsDetailsDtoRequest());

                favoriteDto.setGoodsDetailsDto(goodsDetailsDto);
            }

            if (CollectionUtil.isNotEmpty(favoriteDtoRequest.getGoodsGroupImageEntityListRequest())) {
                List<GoodsGroupImageEntity> goodsGroupImageEntityList =
                                toGoodsGroupImageEntityList(favoriteDtoRequest.getGoodsGroupImageEntityListRequest());

                favoriteDto.setGoodsGroupImageEntityList(goodsGroupImageEntityList);
            }

            favoriteDtoList.add(favoriteDto);
        }

        return favoriteDtoList;
    }

    /**
     * お気に入りDtoListレスポンスに変換
     *
     * @param stockFavoriteDtoList お気に入りDtoクラスリスト
     * @return お気に入りDtoListレスポンス
     */
    public List<FavoriteDtoResponse> toFavoriteDtoResponseList(List<FavoriteDto> stockFavoriteDtoList)
                    throws InvocationTargetException, IllegalAccessException {

        if (CollectionUtil.isEmpty(stockFavoriteDtoList)) {
            return new ArrayList<>();
        }

        List<FavoriteDtoResponse> favoriteDtoResponseList = new ArrayList<>();

        for (FavoriteDto favoriteDto : stockFavoriteDtoList) {
            FavoriteDtoResponse favoriteDtoResponse = new FavoriteDtoResponse();

            if (favoriteDto.getFavoriteEntity() != null) {
                FavoriteEntityResponse favoriteEntityResponse = new FavoriteEntityResponse();
                FavoriteEntity favoriteEntity = favoriteDto.getFavoriteEntity();

                favoriteEntityResponse.setMemberInfoSeq(favoriteEntity.getMemberInfoSeq());
                favoriteEntityResponse.setGoodsSeq(favoriteEntity.getGoodsSeq());
                favoriteEntityResponse.setRegistTime(favoriteEntity.getRegistTime());
                favoriteEntityResponse.setUpdateTime(favoriteEntity.getUpdateTime());

                favoriteDtoResponse.setFavoriteEntityResponse(favoriteEntityResponse);
            }

            if (favoriteDto.getGoodsDetailsDto() != null) {
                GoodsDetailsDtoResponse goodsDetailsDtoResponse = new GoodsDetailsDtoResponse();

                GoodsDetailsDto goodsDetailsDto = favoriteDto.getGoodsDetailsDto();

                goodsDetailsDtoResponse.setGoodsSeq(goodsDetailsDto.getGoodsSeq());
                goodsDetailsDtoResponse.setGoodsGroupSeq(goodsDetailsDto.getGoodsGroupSeq());
                goodsDetailsDtoResponse.setVersionNo(goodsDetailsDto.getVersionNo());
                goodsDetailsDtoResponse.setRegistTime(goodsDetailsDto.getRegistTime());
                goodsDetailsDtoResponse.setUpdateTime(goodsDetailsDto.getUpdateTime());
                goodsDetailsDtoResponse.setGoodsCode(goodsDetailsDto.getGoodsCode());
                goodsDetailsDtoResponse.setGoodsGroupMaxPrice(goodsDetailsDto.getGoodsGroupMaxPrice());
                goodsDetailsDtoResponse.setGoodsGroupMinPrice(goodsDetailsDto.getGoodsGroupMinPrice());
                goodsDetailsDtoResponse.setPreDiscountMinPrice(goodsDetailsDto.getPreDiscountMinPrice());
                goodsDetailsDtoResponse.setPreDiscountMaxPrice(goodsDetailsDto.getPreDiscountMaxPrice());
                goodsDetailsDtoResponse.setTaxRate(goodsDetailsDto.getTaxRate());
                goodsDetailsDtoResponse.setGoodsPriceInTax(goodsDetailsDto.getGoodsPriceInTax());
                goodsDetailsDtoResponse.setGoodsPrice(goodsDetailsDto.getGoodsPrice());
                goodsDetailsDtoResponse.setDeliveryType(goodsDetailsDto.getDeliveryType());
                goodsDetailsDtoResponse.setSaleStartTime(goodsDetailsDto.getSaleStartTimePC());
                goodsDetailsDtoResponse.setSaleEndTime(goodsDetailsDto.getSaleEndTimePC());
                goodsDetailsDtoResponse.setPurchasedMax(goodsDetailsDto.getPurchasedMax());
                goodsDetailsDtoResponse.setOrderDisplay(goodsDetailsDto.getOrderDisplay());
                goodsDetailsDtoResponse.setUnitValue1(goodsDetailsDto.getUnitValue1());
                goodsDetailsDtoResponse.setUnitValue2(goodsDetailsDto.getUnitValue2());
                goodsDetailsDtoResponse.setPreDiscountPrice(goodsDetailsDto.getPreDiscountPrice());
                goodsDetailsDtoResponse.setPreDisCountPriceInTax(goodsDetailsDto.getPreDisCountPriceInTax());
                goodsDetailsDtoResponse.setJanCode(goodsDetailsDto.getJanCode());
                goodsDetailsDtoResponse.setCatalogCode(goodsDetailsDto.getCatalogCode());
                goodsDetailsDtoResponse.setSalesPossibleStock(goodsDetailsDto.getSalesPossibleStock());
                goodsDetailsDtoResponse.setRealStock(goodsDetailsDto.getRealStock());
                goodsDetailsDtoResponse.setOrderReserveStock(goodsDetailsDto.getOrderReserveStock());
                goodsDetailsDtoResponse.setRemainderFewStock(goodsDetailsDto.getRemainderFewStock());
                goodsDetailsDtoResponse.setOrderPointStock(goodsDetailsDto.getOrderPointStock());
                goodsDetailsDtoResponse.setSafetyStock(goodsDetailsDto.getSafetyStock());
                goodsDetailsDtoResponse.setGoodsGroupCode(goodsDetailsDto.getGoodsGroupCode());
                goodsDetailsDtoResponse.setWhatsnewDate(goodsDetailsDto.getWhatsnewDate());
                goodsDetailsDtoResponse.setOpenStartTime(goodsDetailsDto.getOpenStartTimePC());
                goodsDetailsDtoResponse.setOpenEndTime(goodsDetailsDto.getOpenEndTimePC());
                goodsDetailsDtoResponse.setGoodsGroupName(goodsDetailsDto.getGoodsGroupName());
                goodsDetailsDtoResponse.setUnitTitle1(goodsDetailsDto.getUnitTitle1());
                goodsDetailsDtoResponse.setUnitTitle2(goodsDetailsDto.getUnitTitle2());
                goodsDetailsDtoResponse.setGoodsPreDiscountPrice(goodsDetailsDto.getGoodsPreDiscountPrice());
                goodsDetailsDtoResponse.setMetaDescription(goodsDetailsDto.getMetaDescription());
                goodsDetailsDtoResponse.setGoodsNote1(goodsDetailsDto.getGoodsNote1());
                goodsDetailsDtoResponse.setGoodsNote2(goodsDetailsDto.getGoodsNote2());
                goodsDetailsDtoResponse.setGoodsNote3(goodsDetailsDto.getGoodsNote3());
                goodsDetailsDtoResponse.setGoodsNote4(goodsDetailsDto.getGoodsNote4());
                goodsDetailsDtoResponse.setGoodsNote5(goodsDetailsDto.getGoodsNote5());
                goodsDetailsDtoResponse.setGoodsNote6(goodsDetailsDto.getGoodsNote6());
                goodsDetailsDtoResponse.setGoodsNote7(goodsDetailsDto.getGoodsNote7());
                goodsDetailsDtoResponse.setGoodsNote8(goodsDetailsDto.getGoodsNote8());
                goodsDetailsDtoResponse.setGoodsNote9(goodsDetailsDto.getGoodsNote9());
                goodsDetailsDtoResponse.setGoodsNote10(goodsDetailsDto.getGoodsNote10());
                goodsDetailsDtoResponse.setOrderSetting1(goodsDetailsDto.getOrderSetting1());
                goodsDetailsDtoResponse.setOrderSetting2(goodsDetailsDto.getOrderSetting2());
                goodsDetailsDtoResponse.setOrderSetting3(goodsDetailsDto.getOrderSetting3());
                goodsDetailsDtoResponse.setOrderSetting4(goodsDetailsDto.getOrderSetting4());
                goodsDetailsDtoResponse.setOrderSetting5(goodsDetailsDto.getOrderSetting5());
                goodsDetailsDtoResponse.setOrderSetting6(goodsDetailsDto.getOrderSetting6());
                goodsDetailsDtoResponse.setOrderSetting7(goodsDetailsDto.getOrderSetting7());
                goodsDetailsDtoResponse.setOrderSetting8(goodsDetailsDto.getOrderSetting8());
                goodsDetailsDtoResponse.setOrderSetting9(goodsDetailsDto.getOrderSetting9());
                goodsDetailsDtoResponse.setOrderSetting10(goodsDetailsDto.getOrderSetting10());
                goodsDetailsDtoResponse.setGoodsOptionDisplayName(goodsDetailsDto.getGoodsOptionDisplayName());
                goodsDetailsDtoResponse.setCoolSendFrom(goodsDetailsDto.getCoolSendFrom());
                goodsDetailsDtoResponse.setCoolSendTo(goodsDetailsDto.getCoolSendTo());
                goodsDetailsDtoResponse.setTax(goodsDetailsDto.getTax());
                goodsDetailsDtoResponse.setUnit(goodsDetailsDto.getUnit());
                goodsDetailsDtoResponse.setGoodsManagementCode(goodsDetailsDto.getGoodsManagementCode());
                goodsDetailsDtoResponse.setGoodsDivisionCode(goodsDetailsDto.getGoodsDivisionCode());
                goodsDetailsDtoResponse.setGoodsCategory1(goodsDetailsDto.getGoodsCategory1());
                goodsDetailsDtoResponse.setGoodsCategory2(goodsDetailsDto.getGoodsCategory2());
                goodsDetailsDtoResponse.setGoodsCategory3(goodsDetailsDto.getGoodsCategory3());
                goodsDetailsDtoResponse.setSaleYesNo(goodsDetailsDto.getSaleYesNo());
                goodsDetailsDtoResponse.setSaleNgMessage(goodsDetailsDto.getSaleNgMessage());
                goodsDetailsDtoResponse.setDeliveryYesNo(goodsDetailsDto.getDeliveryYesNo());
                goodsDetailsDtoResponse.setUnitImage(toUnitImageResponse(goodsDetailsDto.getUnitImage()));
                if (goodsDetailsDto.getGoodsTaxType() != null) {
                    goodsDetailsDtoResponse.setGoodsTaxType(goodsDetailsDto.getGoodsTaxType().getValue());
                }
                if (goodsDetailsDto.getAlcoholFlag() != null) {
                    goodsDetailsDtoResponse.setAlcoholFlag(goodsDetailsDto.getAlcoholFlag().getValue());
                }
                if (goodsDetailsDto.getSaleStatusPC() != null) {
                    goodsDetailsDtoResponse.setSaleStatus(goodsDetailsDto.getSaleStatusPC().getValue());
                }
                if (goodsDetailsDto.getUnitManagementFlag() != null) {
                    goodsDetailsDtoResponse.setUnitManagementFlag(goodsDetailsDto.getUnitManagementFlag().getValue());
                }
                if (goodsDetailsDto.getStockManagementFlag() != null) {
                    goodsDetailsDtoResponse.setStockManagementFlag(goodsDetailsDto.getStockManagementFlag().getValue());
                }
                if (goodsDetailsDto.getIndividualDeliveryType() != null) {
                    goodsDetailsDtoResponse.setIndividualDeliveryType(
                                    goodsDetailsDto.getIndividualDeliveryType().getValue());
                }
                if (goodsDetailsDto.getFreeDeliveryFlag() != null) {
                    goodsDetailsDtoResponse.setFreeDeliveryFlag(goodsDetailsDto.getFreeDeliveryFlag().getValue());
                }
                if (goodsDetailsDto.getGoodsOpenStatusPC() != null) {
                    goodsDetailsDtoResponse.setGoodsOpenStatus(goodsDetailsDto.getGoodsOpenStatusPC().getValue());
                }

                if (goodsDetailsDto.getGoodsGroupImageEntityList() != null) {
                    List<GoodsGroupImageEntityResponse> goodsGroupImageEntityList = new ArrayList<>();
                    for (GoodsGroupImageEntity goodsGroupImageEntity : goodsDetailsDto.getGoodsGroupImageEntityList()) {
                        GoodsGroupImageEntityResponse goodsGroupImageEntityResponse =
                                        new GoodsGroupImageEntityResponse();

                        goodsGroupImageEntityResponse.setGoodsGroupSeq(goodsGroupImageEntity.getGoodsGroupSeq());
                        goodsGroupImageEntityResponse.setImageTypeVersionNo(
                                        goodsGroupImageEntity.getImageTypeVersionNo());
                        goodsGroupImageEntityResponse.setImageFileName(goodsGroupImageEntity.getImageFileName());
                        goodsGroupImageEntityResponse.setRegistTime(goodsGroupImageEntity.getRegistTime());
                        goodsGroupImageEntityResponse.setUpdateTime(goodsGroupImageEntity.getUpdateTime());

                        goodsGroupImageEntityList.add(goodsGroupImageEntityResponse);
                    }
                    goodsDetailsDtoResponse.setGoodsGroupImageEntityList(goodsGroupImageEntityList);
                }
                if (goodsDetailsDto.getSnsLinkFlag() != null) {
                    goodsDetailsDtoResponse.setSnsLinkFlag(goodsDetailsDto.getSnsLinkFlag().getValue());
                }
                if (goodsDetailsDto.getStockStatusPc() != null) {
                    goodsDetailsDtoResponse.setStockStatus(goodsDetailsDto.getStockStatusPc().getValue());
                }
                if (goodsDetailsDto.getGoodsClassType() != null) {
                    goodsDetailsDtoResponse.setGoodsClassType(goodsDetailsDto.getGoodsClassType().getValue());
                }
                if (goodsDetailsDto.getDentalMonopolySalesFlg() != null) {
                    goodsDetailsDtoResponse.setDentalMonopolySalesFlg(
                                    goodsDetailsDto.getDentalMonopolySalesFlg().getValue());
                }
                if (goodsDetailsDto.getSaleIconFlag() != null) {
                    goodsDetailsDtoResponse.setSaleIconFlag(goodsDetailsDto.getSaleIconFlag().getValue());
                }
                if (goodsDetailsDto.getReserveIconFlag() != null) {
                    goodsDetailsDtoResponse.setReserveIconFlag(goodsDetailsDto.getReserveIconFlag().getValue());
                }
                if (goodsDetailsDto.getNewIconFlag() != null) {
                    goodsDetailsDtoResponse.setNewIconFlag(goodsDetailsDto.getNewIconFlag().getValue());
                }
                // 2023-renew No92 from here
                if (goodsDetailsDto.getOutletIconFlag() != null) {
                    goodsDetailsDtoResponse.setOutletIconFlag(goodsDetailsDto.getOutletIconFlag().getValue());
                }
                // 2023-renew No92 to here
                if (goodsDetailsDto.getLandSendFlag() != null) {
                    goodsDetailsDtoResponse.setLandSendFlag(goodsDetailsDto.getLandSendFlag().getValue());
                }
                if (goodsDetailsDto.getCoolSendFlag() != null) {
                    goodsDetailsDtoResponse.setCoolSendFlag(goodsDetailsDto.getCoolSendFlag().getValue());
                }
                if (goodsDetailsDto.getPriceMarkDispFlag() != null) {
                    goodsDetailsDtoResponse.setPriceMarkDispFlag(goodsDetailsDto.getPriceMarkDispFlag().getValue());
                }
                if (goodsDetailsDto.getSalePriceMarkDispFlag() != null) {
                    goodsDetailsDtoResponse.setSalePriceMarkDispFlag(
                                    goodsDetailsDto.getSalePriceMarkDispFlag().getValue());
                }
                if (goodsDetailsDto.getSalePriceIntegrityFlag() != null) {
                    goodsDetailsDtoResponse.setSalePriceIntegrityFlag(
                                    goodsDetailsDto.getSalePriceIntegrityFlag().getValue());
                }
                if (goodsDetailsDto.getEmotionPriceType() != null) {
                    goodsDetailsDtoResponse.setEmotionPriceType(goodsDetailsDto.getEmotionPriceType().getValue());
                }
                // 2023-renew AddNo5 from here
                goodsDetailsDtoResponse.setGoodsPriceInTaxLow(goodsDetailsDto.getGoodsPriceInTaxLow());
                goodsDetailsDtoResponse.setGoodsPriceInTaxHight(goodsDetailsDto.getGoodsPriceInTaxHight());
                goodsDetailsDtoResponse.setPreDiscountPriceLow(goodsDetailsDto.getPreDiscountPriceLow());
                goodsDetailsDtoResponse.setPreDiscountPriceHight(goodsDetailsDto.getPreDiscountPriceHight());
                // 2023-renew AddNo5 to here

                favoriteDtoResponse.setGoodsDetailsDtoResponse(goodsDetailsDtoResponse);
            }

            if (CollectionUtil.isNotEmpty(favoriteDto.getGoodsGroupImageEntityList())) {
                List<GoodsGroupImageEntityResponse> goodsGroupImageEntityListResponse = new ArrayList<>();
                for (GoodsGroupImageEntity goodsGroupImageEntity : favoriteDto.getGoodsGroupImageEntityList()) {
                    GoodsGroupImageEntityResponse goodsGroupImageEntityResponse = new GoodsGroupImageEntityResponse();

                    goodsGroupImageEntityResponse.setGoodsGroupSeq(goodsGroupImageEntity.getGoodsGroupSeq());
                    goodsGroupImageEntityResponse.setImageTypeVersionNo(goodsGroupImageEntity.getImageTypeVersionNo());
                    goodsGroupImageEntityResponse.setImageFileName(goodsGroupImageEntity.getImageFileName());
                    goodsGroupImageEntityResponse.setRegistTime(goodsGroupImageEntity.getRegistTime());
                    goodsGroupImageEntityResponse.setUpdateTime(goodsGroupImageEntity.getUpdateTime());

                    goodsGroupImageEntityListResponse.add(goodsGroupImageEntityResponse);
                }
                favoriteDtoResponse.setGoodsGroupImageEntityListResponse(goodsGroupImageEntityListResponse);
            }

            favoriteDtoResponse.setStockStatus(favoriteDto.getStockStatus());

            favoriteDtoResponseList.add(favoriteDtoResponse);
        }

        return favoriteDtoResponseList;
    }

    /**
     * 商品画像レスポンスに変換
     *
     * @param unitImage 商品グループ画像Entity
     * @return 商品画像レスポンス
     */
    private GoodsImageEntityResponse toUnitImageResponse(GoodsImageEntity unitImage) {
        if (ObjectUtils.isEmpty(unitImage)) {
            return null;
        }

        GoodsImageEntityResponse goodsImageEntityResponse = new GoodsImageEntityResponse();

        goodsImageEntityResponse.setGoodsGroupSeq(unitImage.getGoodsGroupSeq());
        goodsImageEntityResponse.setGoodsSeq(unitImage.getGoodsSeq());
        goodsImageEntityResponse.setImageFileName(unitImage.getImageFileName());
        goodsImageEntityResponse.setDisplayFlag(unitImage.getDisplayFlag().getValue());
        goodsImageEntityResponse.setRegistTime(conversionUtility.toTimeStamp(unitImage.getRegistTime()));
        goodsImageEntityResponse.setUpdateTime(conversionUtility.toTimeStamp(unitImage.getUpdateTime()));
        goodsImageEntityResponse.setTmpFilePath(unitImage.getTmpFilePath());

        return goodsImageEntityResponse;
    }

    /**
     * カートDtoクラスに変換
     *
     * @param cartDtoRequest カートDtoリクエスト
     * @return カートDtoクラス
     */
    public CartDto toCartDto(CartDtoRequest cartDtoRequest) {

        if (cartDtoRequest == null) {
            return null;
        }

        CartDto cartDto = new CartDto();

        cartDto.setGoodsTotalCount(cartDtoRequest.getGoodsTotalCount());
        cartDto.setGoodsTotalPrice(cartDtoRequest.getGoodsTotalPrice());
        cartDto.setGoodsTotalPriceInTax(cartDtoRequest.getGoodsTotalPriceInTax());
        cartDto.setBeforeTransferEmotionGoodsCodeMap(cartDtoRequest.getBeforeTransferEmotionGoodsCodeMap());
        cartDto.setTotalPriceTax(cartDtoRequest.getTotalPriceTax());

        cartDto.setCartGoodsDtoList(toCartGoodsDtoList(cartDtoRequest.getCartGoodsDtoList()));
        cartDto.setSettlementMethodType(EnumTypeUtil.getEnumFromValue(HTypeSettlementMethodType.class,
                                                                      cartDtoRequest.getSettlementMethodType()
                                                                     ));
        cartDto.setDiscountsResponseDetailMap(
                        toDiscountsResponseDetailMap(cartDtoRequest.getDiscountsResponseDetailMap()));
        cartDto.setConsumptionTaxRateMap(toConsumptionTaxRateMap(cartDtoRequest.getConsumptionTaxRateMap()));

        return cartDto;
    }

    /**
     * カート商品Dtoリストに変換
     *
     * @param cartGoodsDtoRequestList カート商品Dtoリクエストリスト
     * @return カート商品Dtoリスト
     */
    private List<CartGoodsDto> toCartGoodsDtoList(List<CartGoodsDtoRequest> cartGoodsDtoRequestList) {

        if (CollectionUtil.isEmpty(cartGoodsDtoRequestList)) {
            return new ArrayList<>();
        }

        List<CartGoodsDto> cartGoodsDtoList = new ArrayList<>();

        cartGoodsDtoRequestList.forEach(cartGoodsDtoRequest -> {
            CartGoodsDto cartGoodsDto = new CartGoodsDto();

            cartGoodsDto.setCartGoodsEntity(toCartGoodsEntity(cartGoodsDtoRequest.getCartGoodsEntity()));
            cartGoodsDto.setGoodsDetailsDto(toGoodsDetailsDto(cartGoodsDtoRequest.getGoodsDetailsDto()));
            cartGoodsDto.setGoodsPriceSubtotal(cartGoodsDtoRequest.getGoodsPriceSubtotal());
            cartGoodsDto.setGoodsPriceInTaxSubtotal(cartGoodsDtoRequest.getGoodsPriceInTaxSubtotal());

            cartGoodsDtoList.add(cartGoodsDto);
        });

        return cartGoodsDtoList;
    }

    /**
     * カート商品に変換
     *
     * @param cartGoodsEntityRequest カート商品エンティティリクエスト
     * @return カート商品
     */
    private CartGoodsEntity toCartGoodsEntity(CartGoodsEntityRequest cartGoodsEntityRequest) {

        if (ObjectUtils.isEmpty(cartGoodsEntityRequest)) {
            return null;
        }

        CartGoodsEntity cartGoodsEntity = new CartGoodsEntity();

        cartGoodsEntity.setCartSeq(cartGoodsEntityRequest.getCartSeq());
        cartGoodsEntity.setAccessUid(cartGoodsEntityRequest.getAccessUid());
        cartGoodsEntity.setMemberInfoSeq(cartGoodsEntityRequest.getMemberInfoSeq());
        cartGoodsEntity.setGoodsSeq(cartGoodsEntityRequest.getGoodsSeq());
        cartGoodsEntity.setCartGoodsCount(cartGoodsEntityRequest.getCartGoodsCount());
        cartGoodsEntity.setRegistTime(conversionUtility.toTimeStamp(cartGoodsEntityRequest.getRegistTime()));
        cartGoodsEntity.setUpdateTime(conversionUtility.toTimeStamp(cartGoodsEntityRequest.getUpdateTime()));
        // 2023-renew No14 from here
        cartGoodsEntity.setReserveFlag(EnumTypeUtil.getEnumFromValue(HTypeReserveDeliveryFlag.class,
                                                                     cartGoodsEntityRequest.getReserveFlag()
                                                                    ));
        cartGoodsEntity.setReserveDeliveryDate(
                        conversionUtility.toTimeStamp(cartGoodsEntityRequest.getReserveDeliveryDate()));
        // 2023-renew No14 to here
        return cartGoodsEntity;
    }

    /**
     * 商品詳細Dtoに変換
     *
     * @param goodsDetailsDtoRequest 商品詳細Dtoクラスリクエスト
     * @return 商品詳細Dto
     */
    private GoodsDetailsDto toGoodsDetailsDto(GoodsDetailsDtoRequest goodsDetailsDtoRequest) {

        if (ObjectUtils.isEmpty(goodsDetailsDtoRequest)) {
            return null;
        }

        GoodsDetailsDto goodsDetailsDto = new GoodsDetailsDto();

        goodsDetailsDto.setGoodsSeq(goodsDetailsDtoRequest.getGoodsSeq());
        goodsDetailsDto.setGoodsGroupSeq(goodsDetailsDtoRequest.getGoodsGroupSeq());
        goodsDetailsDto.setVersionNo(goodsDetailsDtoRequest.getVersionNo());
        goodsDetailsDto.setRegistTime(conversionUtility.toTimeStamp(goodsDetailsDtoRequest.getRegistTime()));
        goodsDetailsDto.setUpdateTime(conversionUtility.toTimeStamp(goodsDetailsDtoRequest.getUpdateTime()));
        goodsDetailsDto.setGoodsCode(goodsDetailsDtoRequest.getGoodsCode());
        goodsDetailsDto.setGoodsGroupMaxPrice(goodsDetailsDtoRequest.getGoodsGroupMaxPrice());
        goodsDetailsDto.setGoodsGroupMinPrice(goodsDetailsDtoRequest.getGoodsGroupMinPrice());
        goodsDetailsDto.setPreDiscountMinPrice(goodsDetailsDtoRequest.getPreDiscountMinPrice());
        goodsDetailsDto.setPreDiscountMaxPrice(goodsDetailsDtoRequest.getPreDiscountMaxPrice());
        goodsDetailsDto.setTaxRate(goodsDetailsDtoRequest.getTaxRate());
        goodsDetailsDto.setGoodsPriceInTax(goodsDetailsDtoRequest.getGoodsPriceInTax());
        goodsDetailsDto.setGoodsPrice(goodsDetailsDtoRequest.getGoodsPrice());
        goodsDetailsDto.setDeliveryType(goodsDetailsDtoRequest.getDeliveryType());
        goodsDetailsDto.setSaleStartTimePC(conversionUtility.toTimeStamp(goodsDetailsDtoRequest.getSaleStartTime()));
        goodsDetailsDto.setSaleEndTimePC(conversionUtility.toTimeStamp(goodsDetailsDtoRequest.getSaleEndTime()));
        goodsDetailsDto.setPurchasedMax(goodsDetailsDtoRequest.getPurchasedMax());
        goodsDetailsDto.setOrderDisplay(goodsDetailsDtoRequest.getOrderDisplay());
        goodsDetailsDto.setUnitValue1(goodsDetailsDtoRequest.getUnitValue1());
        goodsDetailsDto.setUnitValue2(goodsDetailsDtoRequest.getUnitValue2());
        goodsDetailsDto.setPreDiscountPrice(goodsDetailsDtoRequest.getPreDiscountPrice());
        goodsDetailsDto.setPreDisCountPriceInTax(goodsDetailsDtoRequest.getPreDisCountPriceInTax());
        goodsDetailsDto.setJanCode(goodsDetailsDtoRequest.getJanCode());
        goodsDetailsDto.setCatalogCode(goodsDetailsDtoRequest.getCatalogCode());
        goodsDetailsDto.setSalesPossibleStock(goodsDetailsDtoRequest.getSalesPossibleStock());
        goodsDetailsDto.setRealStock(goodsDetailsDtoRequest.getRealStock());
        goodsDetailsDto.setOrderReserveStock(goodsDetailsDtoRequest.getOrderReserveStock());
        goodsDetailsDto.setRemainderFewStock(goodsDetailsDtoRequest.getRemainderFewStock());
        goodsDetailsDto.setOrderPointStock(goodsDetailsDtoRequest.getOrderPointStock());
        goodsDetailsDto.setSafetyStock(goodsDetailsDtoRequest.getSafetyStock());
        goodsDetailsDto.setGoodsGroupCode(goodsDetailsDtoRequest.getGoodsGroupCode());
        goodsDetailsDto.setWhatsnewDate(conversionUtility.toTimeStamp(goodsDetailsDtoRequest.getWhatsnewDate()));
        goodsDetailsDto.setOpenStartTimePC(conversionUtility.toTimeStamp(goodsDetailsDtoRequest.getOpenStartTime()));
        goodsDetailsDto.setOpenEndTimePC(conversionUtility.toTimeStamp(goodsDetailsDtoRequest.getOpenEndTime()));
        goodsDetailsDto.setGoodsGroupName(goodsDetailsDtoRequest.getGoodsGroupName());
        goodsDetailsDto.setUnitTitle1(goodsDetailsDtoRequest.getUnitTitle1());
        goodsDetailsDto.setUnitTitle2(goodsDetailsDtoRequest.getUnitTitle2());
        goodsDetailsDto.setGoodsPreDiscountPrice(goodsDetailsDtoRequest.getGoodsPreDiscountPrice());
        goodsDetailsDto.setGoodsGroupImageEntityList(
                        toGoodsGroupImageEntityList(goodsDetailsDtoRequest.getGoodsGroupImageEntityList()));
        goodsDetailsDto.setMetaDescription(goodsDetailsDtoRequest.getMetaDescription());
        goodsDetailsDto.setGoodsNote1(goodsDetailsDtoRequest.getGoodsNote1());
        goodsDetailsDto.setGoodsNote2(goodsDetailsDtoRequest.getGoodsNote2());
        goodsDetailsDto.setGoodsNote3(goodsDetailsDtoRequest.getGoodsNote3());
        goodsDetailsDto.setGoodsNote4(goodsDetailsDtoRequest.getGoodsNote4());
        goodsDetailsDto.setGoodsNote5(goodsDetailsDtoRequest.getGoodsNote5());
        goodsDetailsDto.setGoodsNote6(goodsDetailsDtoRequest.getGoodsNote6());
        goodsDetailsDto.setGoodsNote7(goodsDetailsDtoRequest.getGoodsNote7());
        goodsDetailsDto.setGoodsNote8(goodsDetailsDtoRequest.getGoodsNote8());
        goodsDetailsDto.setGoodsNote9(goodsDetailsDtoRequest.getGoodsNote9());
        goodsDetailsDto.setGoodsNote10(goodsDetailsDtoRequest.getGoodsNote10());
        goodsDetailsDto.setOrderSetting1(goodsDetailsDtoRequest.getOrderSetting1());
        goodsDetailsDto.setOrderSetting2(goodsDetailsDtoRequest.getOrderSetting2());
        goodsDetailsDto.setOrderSetting3(goodsDetailsDtoRequest.getOrderSetting3());
        goodsDetailsDto.setOrderSetting4(goodsDetailsDtoRequest.getOrderSetting4());
        goodsDetailsDto.setOrderSetting5(goodsDetailsDtoRequest.getOrderSetting5());
        goodsDetailsDto.setOrderSetting6(goodsDetailsDtoRequest.getOrderSetting6());
        goodsDetailsDto.setOrderSetting7(goodsDetailsDtoRequest.getOrderSetting7());
        goodsDetailsDto.setOrderSetting8(goodsDetailsDtoRequest.getOrderSetting8());
        goodsDetailsDto.setOrderSetting9(goodsDetailsDtoRequest.getOrderSetting9());
        goodsDetailsDto.setOrderSetting10(goodsDetailsDtoRequest.getOrderSetting10());
        goodsDetailsDto.setUnitImage(toUnitImage(goodsDetailsDtoRequest.getUnitImage()));
        goodsDetailsDto.setGoodsOptionDisplayName(goodsDetailsDtoRequest.getGoodsOptionDisplayName());
        goodsDetailsDto.setCoolSendFrom(goodsDetailsDtoRequest.getCoolSendFrom());
        goodsDetailsDto.setCoolSendTo(goodsDetailsDtoRequest.getCoolSendTo());
        goodsDetailsDto.setTax(goodsDetailsDtoRequest.getTax());
        goodsDetailsDto.setUnit(goodsDetailsDtoRequest.getUnit());
        goodsDetailsDto.setGoodsManagementCode(goodsDetailsDtoRequest.getGoodsManagementCode());
        goodsDetailsDto.setGoodsDivisionCode(goodsDetailsDtoRequest.getGoodsDivisionCode());
        goodsDetailsDto.setGoodsCategory1(goodsDetailsDtoRequest.getGoodsCategory1());
        goodsDetailsDto.setGoodsCategory2(goodsDetailsDtoRequest.getGoodsCategory2());
        goodsDetailsDto.setGoodsCategory3(goodsDetailsDtoRequest.getGoodsCategory3());
        goodsDetailsDto.setSaleYesNo(goodsDetailsDtoRequest.getSaleYesNo());
        goodsDetailsDto.setSaleNgMessage(goodsDetailsDtoRequest.getSaleNgMessage());
        goodsDetailsDto.setDeliveryYesNo(goodsDetailsDtoRequest.getDeliveryYesNo());

        goodsDetailsDto.setSaleStatusPC(EnumTypeUtil.getEnumFromValue(HTypeGoodsSaleStatus.class,
                                                                      goodsDetailsDtoRequest.getSaleStatus()
                                                                     ));
        goodsDetailsDto.setStockStatusPc(EnumTypeUtil.getEnumFromValue(HTypeStockStatusType.class,
                                                                       goodsDetailsDtoRequest.getStockStatus()
                                                                      ));
        goodsDetailsDto.setGoodsTaxType(EnumTypeUtil.getEnumFromValue(HTypeGoodsTaxType.class,
                                                                      goodsDetailsDtoRequest.getGoodsTaxType()
                                                                     ));
        goodsDetailsDto.setAlcoholFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeAlcoholFlag.class, goodsDetailsDtoRequest.getAlcoholFlag()));
        goodsDetailsDto.setUnitManagementFlag(EnumTypeUtil.getEnumFromValue(HTypeUnitManagementFlag.class,
                                                                            goodsDetailsDtoRequest.getUnitManagementFlag()
                                                                           ));
        goodsDetailsDto.setStockManagementFlag(EnumTypeUtil.getEnumFromValue(HTypeStockManagementFlag.class,
                                                                             goodsDetailsDtoRequest.getStockManagementFlag()
                                                                            ));
        goodsDetailsDto.setIndividualDeliveryType(EnumTypeUtil.getEnumFromValue(HTypeIndividualDeliveryType.class,
                                                                                goodsDetailsDtoRequest.getIndividualDeliveryType()
                                                                               ));
        goodsDetailsDto.setFreeDeliveryFlag(EnumTypeUtil.getEnumFromValue(HTypeFreeDeliveryFlag.class,
                                                                          goodsDetailsDtoRequest.getFreeDeliveryFlag()
                                                                         ));
        goodsDetailsDto.setGoodsOpenStatusPC(EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class,
                                                                           goodsDetailsDtoRequest.getGoodsOpenStatus()
                                                                          ));
        goodsDetailsDto.setSnsLinkFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeSnsLinkFlag.class, goodsDetailsDtoRequest.getSnsLinkFlag()));
        goodsDetailsDto.setGoodsClassType(EnumTypeUtil.getEnumFromValue(HTypeGoodsClassType.class,
                                                                        goodsDetailsDtoRequest.getGoodsClassType()
                                                                       ));
        goodsDetailsDto.setDentalMonopolySalesFlg(EnumTypeUtil.getEnumFromValue(HTypeDentalMonopolySalesFlg.class,
                                                                                goodsDetailsDtoRequest.getDentalMonopolySalesFlg()
                                                                               ));
        goodsDetailsDto.setSaleIconFlag(EnumTypeUtil.getEnumFromValue(HTypeSaleIconFlag.class,
                                                                      goodsDetailsDtoRequest.getSaleIconFlag()
                                                                     ));
        goodsDetailsDto.setReserveIconFlag(EnumTypeUtil.getEnumFromValue(HTypeReserveIconFlag.class,
                                                                         goodsDetailsDtoRequest.getReserveIconFlag()
                                                                        ));
        goodsDetailsDto.setNewIconFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeNewIconFlag.class, goodsDetailsDtoRequest.getNewIconFlag()));
        // 2023-renew No92 from here
        goodsDetailsDto.setOutletIconFlag(EnumTypeUtil.getEnumFromValue(HTypeOutletIconFlag.class,
                                                                        goodsDetailsDtoRequest.getOutletIconFlag()
                                                                       ));
        // 2023-renew No92 to here
        goodsDetailsDto.setLandSendFlag(EnumTypeUtil.getEnumFromValue(HTypeLandSendFlag.class,
                                                                      goodsDetailsDtoRequest.getLandSendFlag()
                                                                     ));
        goodsDetailsDto.setCoolSendFlag(EnumTypeUtil.getEnumFromValue(HTypeCoolSendFlag.class,
                                                                      goodsDetailsDtoRequest.getCoolSendFlag()
                                                                     ));
        goodsDetailsDto.setReserveFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeReserveFlag.class, goodsDetailsDtoRequest.getReserveFlag()));
        goodsDetailsDto.setPriceMarkDispFlag(EnumTypeUtil.getEnumFromValue(HTypePriceMarkDispFlag.class,
                                                                           goodsDetailsDtoRequest.getPriceMarkDispFlag()
                                                                          ));
        goodsDetailsDto.setSalePriceMarkDispFlag(EnumTypeUtil.getEnumFromValue(HTypeSalePriceMarkDispFlag.class,
                                                                               goodsDetailsDtoRequest.getSalePriceMarkDispFlag()
                                                                              ));
        goodsDetailsDto.setSalePriceIntegrityFlag(EnumTypeUtil.getEnumFromValue(HTypeSalePriceIntegrityFlag.class,
                                                                                goodsDetailsDtoRequest.getSalePriceIntegrityFlag()
                                                                               ));
        goodsDetailsDto.setEmotionPriceType(EnumTypeUtil.getEnumFromValue(HTypeEmotionPriceType.class,
                                                                          goodsDetailsDtoRequest.getEmotionPriceType()
                                                                         ));
        // 2023-renew AddNo5 from here
        goodsDetailsDto.setGoodsPriceInTaxLow(goodsDetailsDtoRequest.getGoodsPriceInTaxLow());
        goodsDetailsDto.setGoodsPriceInTaxHight(goodsDetailsDtoRequest.getGoodsPriceInTaxHight());
        goodsDetailsDto.setPreDiscountPriceLow(goodsDetailsDtoRequest.getPreDiscountPriceLow());
        goodsDetailsDto.setPreDiscountPriceHight(goodsDetailsDtoRequest.getPreDiscountPriceHight());
        // 2023-renew AddNo5 to here

        return goodsDetailsDto;
    }

    /**
     * 商品グループ画像リストに変換
     *
     * @param goodsGroupImageEntityRequestList 商品グループ画像リクエストリスト
     * @return 商品グループ画像リスト
     */
    private List<GoodsGroupImageEntity> toGoodsGroupImageEntityList(List<GoodsGroupImageEntityRequest> goodsGroupImageEntityRequestList) {

        if (CollectionUtil.isEmpty(goodsGroupImageEntityRequestList)) {
            return new ArrayList<>();
        }

        List<GoodsGroupImageEntity> goodsGroupImageEntityList = new ArrayList<>();

        goodsGroupImageEntityRequestList.forEach(goodsGroupImageEntityRequest -> {
            GoodsGroupImageEntity goodsGroupImageEntity = new GoodsGroupImageEntity();

            goodsGroupImageEntity.setGoodsGroupSeq(goodsGroupImageEntityRequest.getGoodsGroupSeq());
            goodsGroupImageEntity.setImageTypeVersionNo(goodsGroupImageEntityRequest.getImageTypeVersionNo());
            goodsGroupImageEntity.setImageFileName(goodsGroupImageEntityRequest.getImageFileName());
            goodsGroupImageEntity.setRegistTime(
                            conversionUtility.toTimeStamp(goodsGroupImageEntityRequest.getRegistTime()));
            goodsGroupImageEntity.setUpdateTime(
                            conversionUtility.toTimeStamp(goodsGroupImageEntityRequest.getUpdateTime()));

            goodsGroupImageEntityList.add(goodsGroupImageEntity);
        });

        return goodsGroupImageEntityList;
    }

    /**
     * 商品画像に変換
     *
     * @param goodsImageEntityRequest 商品グループ画像リクエスト
     * @return 商品画像
     */
    private GoodsImageEntity toUnitImage(GoodsImageEntityRequest goodsImageEntityRequest) {

        if (ObjectUtils.isEmpty(goodsImageEntityRequest)) {
            return null;
        }

        GoodsImageEntity goodsImageEntity = new GoodsImageEntity();

        goodsImageEntity.setGoodsGroupSeq(goodsImageEntityRequest.getGoodsGroupSeq());
        goodsImageEntity.setGoodsSeq(goodsImageEntityRequest.getGoodsSeq());
        goodsImageEntity.setImageFileName(goodsImageEntityRequest.getImageFileName());
        goodsImageEntity.setDisplayFlag(EnumTypeUtil.getEnumFromValue(HTypeDisplayStatus.class,
                                                                      goodsImageEntityRequest.getDisplayFlag()
                                                                     ));
        goodsImageEntity.setRegistTime(conversionUtility.toTimeStamp(goodsImageEntityRequest.getRegistTime()));
        goodsImageEntity.setUpdateTime(conversionUtility.toTimeStamp(goodsImageEntityRequest.getUpdateTime()));
        goodsImageEntity.setTmpFilePath(goodsImageEntityRequest.getTmpFilePath());

        return goodsImageEntity;
    }

    /**
     * 割引適用結果取得 詳細情報Mapに変換
     *
     * @param discountsResponseDetailMapRequest 割引適用結果MAP
     * @return 割引適用結果取得 詳細情報Map
     */
    private Map<String, WebApiGetDiscountsResponseDetailDto> toDiscountsResponseDetailMap(Map<String, WebApiGetDiscountsResponseDetailDtoRequest> discountsResponseDetailMapRequest) {

        if (MapUtils.isEmpty(discountsResponseDetailMapRequest)) {
            return new HashMap<>();
        }

        Map<String, WebApiGetDiscountsResponseDetailDto> discountsResponseDetailDtoMap = new HashMap<>();

        for (Map.Entry<String, WebApiGetDiscountsResponseDetailDtoRequest> entry : discountsResponseDetailMapRequest.entrySet()) {

            WebApiGetDiscountsResponseDetailDtoRequest webApiGetDiscountsResponseDetailDtoRequest = entry.getValue();
            WebApiGetDiscountsResponseDetailDto webApiGetDiscountsResponseDetailDto =
                            new WebApiGetDiscountsResponseDetailDto();

            webApiGetDiscountsResponseDetailDto.setGoodsCode(webApiGetDiscountsResponseDetailDtoRequest.getGoodsCode());
            webApiGetDiscountsResponseDetailDto.setSaleType(webApiGetDiscountsResponseDetailDtoRequest.getSaleType());
            webApiGetDiscountsResponseDetailDto.setSaleGroupCode(
                            webApiGetDiscountsResponseDetailDtoRequest.getSaleGroupCode());
            webApiGetDiscountsResponseDetailDto.setSalePrice(webApiGetDiscountsResponseDetailDtoRequest.getSalePrice());
            webApiGetDiscountsResponseDetailDto.setQuantity(webApiGetDiscountsResponseDetailDtoRequest.getQuantity());
            webApiGetDiscountsResponseDetailDto.setSaleCode(webApiGetDiscountsResponseDetailDtoRequest.getSaleCode());
            webApiGetDiscountsResponseDetailDto.setNote(webApiGetDiscountsResponseDetailDtoRequest.getNote());
            webApiGetDiscountsResponseDetailDto.setHints(webApiGetDiscountsResponseDetailDtoRequest.getHints());
            webApiGetDiscountsResponseDetailDto.setOrderDisplay(
                            webApiGetDiscountsResponseDetailDtoRequest.getOrderDisplay());

            discountsResponseDetailDtoMap.put(entry.getKey(), webApiGetDiscountsResponseDetailDto);
        }

        return discountsResponseDetailDtoMap;
    }

    /**
     * 消費税率取得 詳細情報Mapに変換
     *
     * @param consumptionTaxRateMapRequest 割引適用結果MAP
     * @return 消費税率取得 詳細情報Map
     */
    private Map<String, WebApiGetConsumptionTaxRateResponseDetailDto> toConsumptionTaxRateMap(Map<String, WebApiGetConsumptionTaxRateResponseDetailDtoRequest> consumptionTaxRateMapRequest) {

        if (MapUtils.isEmpty(consumptionTaxRateMapRequest)) {
            return new HashMap<>();
        }

        Map<String, WebApiGetConsumptionTaxRateResponseDetailDto> consumptionTaxRateMap = new HashMap<>();

        for (Map.Entry<String, WebApiGetConsumptionTaxRateResponseDetailDtoRequest> entry : consumptionTaxRateMapRequest.entrySet()) {

            WebApiGetConsumptionTaxRateResponseDetailDtoRequest webApiGetConsumptionTaxRateResponseDetailDtoRequest =
                            entry.getValue();
            WebApiGetConsumptionTaxRateResponseDetailDto webApiGetConsumptionTaxRateResponseDetailDto =
                            new WebApiGetConsumptionTaxRateResponseDetailDto();

            webApiGetConsumptionTaxRateResponseDetailDto.setGoodsCode(
                            webApiGetConsumptionTaxRateResponseDetailDtoRequest.getGoodsCode());
            webApiGetConsumptionTaxRateResponseDetailDto.setTaxRate(
                            webApiGetConsumptionTaxRateResponseDetailDtoRequest.getTaxRate());

            consumptionTaxRateMap.put(entry.getKey(), webApiGetConsumptionTaxRateResponseDetailDto);
        }

        return consumptionTaxRateMap;
    }

    /**
     * お気に入りEntityListレスポンスに変換
     *
     * @param favoriteList お気に入りクラスリスト
     * @return お気に入りEntityListレスポンス
     */
    public List<FavoriteEntityResponse> toFavoriteEntityResponseList(List<FavoriteEntity> favoriteList)
                    throws InvocationTargetException, IllegalAccessException {

        if (CollectionUtil.isEmpty(favoriteList)) {
            return new ArrayList<>();
        }

        List<FavoriteEntityResponse> favoriteEntityResponseList = new ArrayList<>();

        for (FavoriteEntity favoriteEntity : favoriteList) {
            FavoriteEntityResponse favoriteEntityResponse = new FavoriteEntityResponse();

            favoriteEntityResponse.setMemberInfoSeq(favoriteEntity.getMemberInfoSeq());
            favoriteEntityResponse.setGoodsSeq(favoriteEntity.getGoodsSeq());
            favoriteEntityResponse.setRegistTime(favoriteEntity.getRegistTime());
            favoriteEntityResponse.setUpdateTime(favoriteEntity.getUpdateTime());

            favoriteEntityResponseList.add(favoriteEntityResponse);
        }

        return favoriteEntityResponseList;
    }

    /**
     * 商品Dtoリストに変換
     *
     * @param goodsDtoRequestList 商品DTOリストリクエスト
     * @return 商品Dtoリスト
     */
    public List<GoodsDto> toGoodsDtoList(List<GoodsDtoRequest> goodsDtoRequestList)
                    throws InvocationTargetException, IllegalAccessException {

        if (CollectionUtil.isEmpty(goodsDtoRequestList)) {
            return new ArrayList<>();
        }

        List<GoodsDto> goodsDtoList = new ArrayList<>();

        for (GoodsDtoRequest goodsDtoRequest : goodsDtoRequestList) {
            GoodsDto goodsDto = new GoodsDto();

            if (goodsDtoRequest.getGoodsEntity() != null) {
                GoodsEntity goodsEntity = new GoodsEntity();

                goodsEntity.setGoodsSeq(goodsDtoRequest.getGoodsEntity().getGoodsSeq());
                goodsEntity.setGoodsGroupSeq(goodsDtoRequest.getGoodsEntity().getGoodsGroupSeq());
                goodsEntity.setGoodsCode(goodsDtoRequest.getGoodsEntity().getGoodsCode());
                goodsEntity.setJanCode(goodsDtoRequest.getGoodsEntity().getJanCode());
                goodsEntity.setCatalogCode(goodsDtoRequest.getGoodsEntity().getCatalogCode());
                goodsEntity.setSaleStartTimePC(
                                conversionUtility.toTimeStamp(goodsDtoRequest.getGoodsEntity().getSaleStartTime()));
                goodsEntity.setSaleEndTimePC(
                                conversionUtility.toTimeStamp(goodsDtoRequest.getGoodsEntity().getSaleEndTime()));
                goodsEntity.setGoodsPrice(goodsDtoRequest.getGoodsEntity().getGoodsPrice());
                goodsEntity.setPreDiscountPrice(goodsDtoRequest.getGoodsEntity().getPreDiscountPrice());
                goodsEntity.setUnitValue1(goodsDtoRequest.getGoodsEntity().getUnitValue1());
                goodsEntity.setUnitValue2(goodsDtoRequest.getGoodsEntity().getUnitValue2());
                goodsEntity.setPurchasedMax(goodsDtoRequest.getGoodsEntity().getPurchasedMax());
                goodsEntity.setOrderDisplay(goodsDtoRequest.getGoodsEntity().getOrderDisplay());
                goodsEntity.setShopSeq(1001);
                goodsEntity.setVersionNo(goodsDtoRequest.getGoodsEntity().getVersionNo());
                goodsEntity.setRegistTime(
                                conversionUtility.toTimeStamp(goodsDtoRequest.getGoodsEntity().getRegistTime()));
                goodsEntity.setUpdateTime(
                                conversionUtility.toTimeStamp(goodsDtoRequest.getGoodsEntity().getUpdateTime()));
                goodsEntity.setUnit(goodsDtoRequest.getGoodsEntity().getUnit());
                goodsEntity.setGoodsManagementCode(goodsDtoRequest.getGoodsEntity().getGoodsManagementCode());
                goodsEntity.setGoodsDivisionCode(goodsDtoRequest.getGoodsEntity().getGoodsDivisionCode());
                goodsEntity.setGoodsCategory1(goodsDtoRequest.getGoodsEntity().getGoodsCategory1());
                goodsEntity.setGoodsCategory2(goodsDtoRequest.getGoodsEntity().getGoodsCategory2());
                goodsEntity.setGoodsCategory3(goodsDtoRequest.getGoodsEntity().getGoodsCategory3());
                goodsEntity.setCoolSendFrom(goodsDtoRequest.getGoodsEntity().getCoolSendFrom());
                goodsEntity.setCoolSendTo(goodsDtoRequest.getGoodsEntity().getCoolSendTo());

                goodsEntity.setSaleStatusPC(EnumTypeUtil.getEnumFromValue(HTypeGoodsSaleStatus.class,
                                                                          goodsDtoRequest.getGoodsEntity()
                                                                                         .getSaleStatus()
                                                                         ));
                goodsEntity.setIndividualDeliveryType(EnumTypeUtil.getEnumFromValue(HTypeIndividualDeliveryType.class,
                                                                                    goodsDtoRequest.getGoodsEntity()
                                                                                                   .getIndividualDeliveryType()
                                                                                   ));
                goodsEntity.setFreeDeliveryFlag(EnumTypeUtil.getEnumFromValue(HTypeFreeDeliveryFlag.class,
                                                                              goodsDtoRequest.getGoodsEntity()
                                                                                             .getFreeDeliveryFlag()
                                                                             ));
                goodsEntity.setUnitManagementFlag(EnumTypeUtil.getEnumFromValue(HTypeUnitManagementFlag.class,
                                                                                goodsDtoRequest.getGoodsEntity()
                                                                                               .getUnitManagementFlag()
                                                                               ));
                goodsEntity.setStockManagementFlag(EnumTypeUtil.getEnumFromValue(HTypeStockManagementFlag.class,
                                                                                 goodsDtoRequest.getGoodsEntity()
                                                                                                .getStockManagementFlag()
                                                                                ));
                goodsEntity.setReserveFlag(EnumTypeUtil.getEnumFromValue(HTypeReserveFlag.class,
                                                                         goodsDtoRequest.getGoodsEntity()
                                                                                        .getReserveFlag()
                                                                        ));
                goodsEntity.setPriceMarkDispFlag(EnumTypeUtil.getEnumFromValue(HTypePriceMarkDispFlag.class,
                                                                               goodsDtoRequest.getGoodsEntity()
                                                                                              .getPriceMarkDispFlag()
                                                                              ));
                goodsEntity.setSalePriceMarkDispFlag(EnumTypeUtil.getEnumFromValue(HTypeSalePriceMarkDispFlag.class,
                                                                                   goodsDtoRequest.getGoodsEntity()
                                                                                                  .getSalePriceMarkDispFlag()
                                                                                  ));
                goodsEntity.setLandSendFlag(EnumTypeUtil.getEnumFromValue(HTypeLandSendFlag.class,
                                                                          goodsDtoRequest.getGoodsEntity()
                                                                                         .getLandSendFlag()
                                                                         ));
                goodsEntity.setCoolSendFlag(EnumTypeUtil.getEnumFromValue(HTypeCoolSendFlag.class,
                                                                          goodsDtoRequest.getGoodsEntity()
                                                                                         .getCoolSendFlag()
                                                                         ));
                goodsEntity.setSalePriceIntegrityFlag(EnumTypeUtil.getEnumFromValue(HTypeSalePriceIntegrityFlag.class,
                                                                                    goodsDtoRequest.getGoodsEntity()
                                                                                                   .getSalePriceIntegrityFlag()
                                                                                   ));
                goodsEntity.setEmotionPriceType(EnumTypeUtil.getEnumFromValue(HTypeEmotionPriceType.class,
                                                                              goodsDtoRequest.getGoodsEntity()
                                                                                             .getEmotionPriceType()
                                                                             ));

                goodsDto.setGoodsEntity(goodsEntity);
            }

            if (goodsDtoRequest.getStockDto() != null) {
                StockDto stockDto = new StockDto();
                StockDtoRequest stockDtoRequest = goodsDtoRequest.getStockDto();

                stockDto.setGoodsSeq(stockDtoRequest.getGoodsSeq());
                stockDto.setShopSeq(1001);
                stockDto.setSalesPossibleStock(stockDtoRequest.getSalesPossibleStock());
                stockDto.setRealStock(stockDtoRequest.getRealStock());
                stockDto.setOrderReserveStock(stockDtoRequest.getOrderReserveStock());
                stockDto.setRemainderFewStock(stockDtoRequest.getRemainderFewStock());
                stockDto.setSupplementCount(stockDtoRequest.getSupplementCount());
                stockDto.setOrderPointStock(stockDtoRequest.getOrderPointStock());
                stockDto.setSafetyStock(stockDtoRequest.getSafetyStock());
                stockDto.setRegistTime(conversionUtility.toTimeStamp(stockDtoRequest.getRegistTime()));
                stockDto.setUpdateTime(conversionUtility.toTimeStamp(stockDtoRequest.getUpdateTime()));

                goodsDto.setStockDto(stockDto);
            }

            goodsDto.setDeleteFlg(goodsDtoRequest.getDeleteFlg());
            goodsDto.setStockStatusPc(EnumTypeUtil.getEnumFromValue(HTypeStockStatusType.class,
                                                                    goodsDtoRequest.getStockStatus()
                                                                   ));
            goodsDto.setUnitImage(toGoodsImageEntity(goodsDtoRequest.getUnitImage()));

            goodsDtoList.add(goodsDto);
        }

        return goodsDtoList;
    }

    /**
     * 商品画像に変換
     *
     * @param goodsImageEntityRequest 商品グループ画像リクエスト
     * @return 商品画像
     */
    public GoodsImageEntity toGoodsImageEntity(GoodsImageEntityRequest goodsImageEntityRequest) {
        if (ObjectUtils.isEmpty(goodsImageEntityRequest)) {
            return null;
        }
        GoodsImageEntity imageEntity = new GoodsImageEntity();

        imageEntity.setGoodsSeq(goodsImageEntityRequest.getGoodsSeq());
        imageEntity.setGoodsGroupSeq(goodsImageEntityRequest.getGoodsGroupSeq());
        imageEntity.setImageFileName(goodsImageEntityRequest.getImageFileName());
        imageEntity.setDisplayFlag(EnumTypeUtil.getEnumFromValue(HTypeDisplayStatus.class,
                                                                 goodsImageEntityRequest.getDisplayFlag()
                                                                ));
        imageEntity.setTmpFilePath(goodsImageEntityRequest.getTmpFilePath());
        imageEntity.setRegistTime(conversionUtility.toTimeStamp(goodsImageEntityRequest.getRegistTime()));
        imageEntity.setUpdateTime(conversionUtility.toTimeStamp(goodsImageEntityRequest.getUpdateTime()));

        return imageEntity;
    }

    /**
     * お気に入りDtoListレスポンスに変換
     *
     * @param favoriteListGetRequest お気に入り情報リスト取得リクエスト
     * @return お気に入りDtoListレスポンス
     */
    public FavoriteSearchForDaoConditionDto toFavoriteConditionDto(FavoriteListGetRequest favoriteListGetRequest) {

        if (favoriteListGetRequest == null) {
            return null;
        }

        FavoriteSearchForDaoConditionDto favoriteSearchForDaoConditionDto = new FavoriteSearchForDaoConditionDto();
        favoriteSearchForDaoConditionDto.setShopSeq(1001);
        favoriteSearchForDaoConditionDto.setMemberInfoSeq(favoriteListGetRequest.getMemberInfoSeq());

        if (CollectionUtil.isNotEmpty(favoriteListGetRequest.getExclusionGoodsSeqList())) {
            favoriteSearchForDaoConditionDto.setExclusionGoodsSeqList(
                            favoriteListGetRequest.getExclusionGoodsSeqList());
        }

        favoriteSearchForDaoConditionDto.setSiteType(
                        EnumTypeUtil.getEnumFromValue(HTypeSiteType.class, favoriteListGetRequest.getSiteType()));
        favoriteSearchForDaoConditionDto.setGoodsOpenStatus(EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class,
                                                                                          favoriteListGetRequest.getGoodsOpenStatus()
                                                                                         ));
        favoriteSearchForDaoConditionDto.setMemberRank(
                        EnumTypeUtil.getEnumFromValue(HTypeMemberRank.class, favoriteListGetRequest.getMemberRank()));

        return favoriteSearchForDaoConditionDto;
    }

    /**
     * 注文履歴詳細Dto取得レスポンスに変換
     *
     * @param receiveOrderForHistoryDto 注文履歴用注文Dtoクラス
     * @return 注文履歴詳細Dto取得レスポンス
     */
    public ReceiveOrderForHistoryDtoResponse toReceiveOrderForHistoryDtoResponse(ReceiveOrderForHistoryDto receiveOrderForHistoryDto) {

        if (receiveOrderForHistoryDto == null) {
            return null;
        }

        ReceiveOrderForHistoryDtoResponse receiveOrderForHistoryDtoResponse = new ReceiveOrderForHistoryDtoResponse();

        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        String receiveOrderForHistoryDtoJson = conversionUtility.toJson(receiveOrderForHistoryDto);

        receiveOrderForHistoryDtoResponse.setReceiveOrderForHistoryDto(receiveOrderForHistoryDtoJson);

        return receiveOrderForHistoryDtoResponse;
    }

    /**
     * 注文履歴DtoListレスポンスに変換
     *
     * @param orderHistoryListDtoList 注文履歴一覧Dtoクラスリスト
     * @return 注文履歴DtoListレスポンス
     */
    public List<OrderHistoryDtoResponse> toOrderHistoryDtoResponseList(List<OrderHistoryListDto> orderHistoryListDtoList)
                    throws InvocationTargetException, IllegalAccessException {

        if (CollectionUtil.isEmpty(orderHistoryListDtoList)) {
            return new ArrayList<>();
        }

        List<OrderHistoryDtoResponse> orderHistoryDtoResponseList = new ArrayList<>();

        for (OrderHistoryListDto orderHistoryListDto : orderHistoryListDtoList) {
            OrderHistoryDtoResponse orderHistoryDtoResponse = new OrderHistoryDtoResponse();

            if (orderHistoryListDto.getOrderSummaryEntity() != null) {
                OrderSummaryEntityResponse orderSummaryEntityResponse = new OrderSummaryEntityResponse();

                OrderSummaryEntity orderSummaryEntity = orderHistoryListDto.getOrderSummaryEntity();

                orderSummaryEntityResponse.setOrderSeq(orderSummaryEntity.getOrderSeq());
                orderSummaryEntityResponse.setOrderVersionNo(orderSummaryEntity.getOrderVersionNo());
                orderSummaryEntityResponse.setOrderCode(orderSummaryEntity.getOrderCode());
                orderSummaryEntityResponse.setOrderTime(orderSummaryEntity.getOrderTime());
                orderSummaryEntityResponse.setSalesTime(orderSummaryEntity.getSalesTime());
                orderSummaryEntityResponse.setCancelTime(orderSummaryEntity.getCancelTime());
                orderSummaryEntityResponse.setBeforeDiscountOrderPrice(
                                orderSummaryEntity.getBeforeDiscountOrderPrice());
                orderSummaryEntityResponse.setOrderPrice(orderSummaryEntity.getOrderPrice());
                orderSummaryEntityResponse.setReceiptPriceTotal(orderSummaryEntity.getReceiptPriceTotal());
                orderSummaryEntityResponse.setPeriodicOrderSeq(orderSummaryEntity.getPeriodicOrderSeq());
                orderSummaryEntityResponse.setSettlementMethodSeq(orderSummaryEntity.getSettlementMethodSeq());
                orderSummaryEntityResponse.setTaxSeq(orderSummaryEntity.getTaxSeq());
                orderSummaryEntityResponse.setMemberInfoSeq(orderSummaryEntity.getMemberInfoSeq());
                orderSummaryEntityResponse.setUserAgent(orderSummaryEntity.getUserAgent());
                orderSummaryEntityResponse.setOrderPointAddBasePrice(orderSummaryEntity.getOrderPointAddBasePrice());
                orderSummaryEntityResponse.setOrderPointAddRate(orderSummaryEntity.getOrderPointAddRate());
                orderSummaryEntityResponse.setVersionNo(orderSummaryEntity.getVersionNo());
                orderSummaryEntityResponse.setRegistTime(orderSummaryEntity.getRegistTime());
                orderSummaryEntityResponse.setUpdateTime(orderSummaryEntity.getUpdateTime());
                orderSummaryEntityResponse.setOrderGoodsVersionNo(orderSummaryEntity.getOrderGoodsVersionNo());
                orderSummaryEntityResponse.setPointSeq(orderSummaryEntity.getPointSeq());
                orderSummaryEntityResponse.setPointVersionNo(orderSummaryEntity.getPointVersionNo());
                orderSummaryEntityResponse.setCouponDiscountPrice(orderSummaryEntity.getCouponDiscountPrice());
                orderSummaryEntityResponse.setUsePoint(orderSummaryEntity.getUsePoint());
                orderSummaryEntityResponse.setPointDiscountPrice(orderSummaryEntity.getPointDiscountPrice());
                orderSummaryEntityResponse.setPoint(orderSummaryEntity.getPoint());
                orderSummaryEntityResponse.setTotalAcquisitionPoint(orderSummaryEntity.getTotalAcquisitionPoint());
                orderSummaryEntityResponse.setSettlementMethodName(orderSummaryEntity.getSettlementMethodName());
                orderSummaryEntityResponse.setSettlementMethodDisplayNamePC(
                                orderSummaryEntity.getSettlementMethodDisplayNamePC());
                orderSummaryEntityResponse.setReceiverTimeZone(orderSummaryEntity.getReceiverTimeZone());
                orderSummaryEntityResponse.setOrderName(orderSummaryEntity.getOrderName());
                orderSummaryEntityResponse.setOrderKana(orderSummaryEntity.getOrderKana());
                orderSummaryEntityResponse.setOrderTel(orderSummaryEntity.getOrderTel());
                orderSummaryEntityResponse.setOrderContactTel(orderSummaryEntity.getOrderContactTel());
                orderSummaryEntityResponse.setOrderMail(orderSummaryEntity.getOrderMail());
                orderSummaryEntityResponse.setReceiverName(orderSummaryEntity.getReceiverName());
                orderSummaryEntityResponse.setReceiverKana(orderSummaryEntity.getReceiverKana());
                orderSummaryEntityResponse.setReceiverTel(orderSummaryEntity.getReceiverTel());
                orderSummaryEntityResponse.setOrderConsecutiveNo(orderSummaryEntity.getOrderConsecutiveNo());
                orderSummaryEntityResponse.setDeliveryCode(orderSummaryEntity.getDeliveryCode());
                orderSummaryEntityResponse.setShipmentStatus(orderSummaryEntity.getShipmentStatus());
                orderSummaryEntityResponse.setDeliveryNote(orderSummaryEntity.getDeliveryNote());
                orderSummaryEntityResponse.setReceiptTime(orderSummaryEntity.getReceiptTime());
                orderSummaryEntityResponse.setPaymentStatus(orderSummaryEntity.getPaymentStatus());
                orderSummaryEntityResponse.setDeliveryMethodName(orderSummaryEntity.getDeliveryMethodName());
                orderSummaryEntityResponse.setOrderStatusForSearchResult(
                                orderSummaryEntity.getOrderStatusForSearchResult());

                if (orderSummaryEntity.getOrderType() != null) {
                    orderSummaryEntityResponse.setOrderType(orderSummaryEntity.getOrderType().getValue());
                }
                if (orderSummaryEntity.getSalesFlag() != null) {
                    orderSummaryEntityResponse.setSalesFlag(orderSummaryEntity.getSalesFlag().getValue());
                }
                if (orderSummaryEntity.getCancelFlag() != null) {
                    orderSummaryEntityResponse.setCancelFlag(orderSummaryEntity.getCancelFlag().getValue());
                }
                if (orderSummaryEntity.getWaitingFlag() != null) {
                    orderSummaryEntityResponse.setWaitingFlag(orderSummaryEntity.getWaitingFlag().getValue());
                }
                if (orderSummaryEntity.getOrderStatus() != null) {
                    orderSummaryEntityResponse.setOrderStatus(orderSummaryEntity.getOrderStatus().getValue());
                }
                if (orderSummaryEntity.getOrderSiteType() != null) {
                    orderSummaryEntityResponse.setOrderSiteType(orderSummaryEntity.getOrderSiteType().getValue());
                }
                if (orderSummaryEntity.getOrderDeviceType() != null) {
                    orderSummaryEntityResponse.setOrderDeviceType(orderSummaryEntity.getOrderDeviceType().getValue());
                }
                if (orderSummaryEntity.getCarrierType() != null) {
                    orderSummaryEntityResponse.setCarrierType(orderSummaryEntity.getCarrierType().getValue());
                }
                if (orderSummaryEntity.getMemberRank() != null) {
                    orderSummaryEntityResponse.setMemberRank(orderSummaryEntity.getMemberRank().getValue());
                }
                if (orderSummaryEntity.getPrefectureType() != null) {
                    orderSummaryEntityResponse.setPrefectureType(orderSummaryEntity.getPrefectureType().getValue());
                }
                if (orderSummaryEntity.getOrderSex() != null) {
                    orderSummaryEntityResponse.setOrderSex(orderSummaryEntity.getOrderSex().getValue());
                }
                if (orderSummaryEntity.getOrderAgeType() != null) {
                    orderSummaryEntityResponse.setOrderAgeType(orderSummaryEntity.getOrderAgeType().getValue());
                }
                if (orderSummaryEntity.getRepeatPurchaseType() != null) {
                    orderSummaryEntityResponse.setRepeatPurchaseType(
                                    orderSummaryEntity.getRepeatPurchaseType().getValue());
                }
                if (orderSummaryEntity.getSettlementMailRequired() != null) {
                    orderSummaryEntityResponse.setSettlementMailRequired(
                                    orderSummaryEntity.getSettlementMailRequired().getValue());
                }
                if (orderSummaryEntity.getReminderSentFlag() != null) {
                    orderSummaryEntityResponse.setReminderSentFlag(orderSummaryEntity.getReminderSentFlag().getValue());
                }
                if (orderSummaryEntity.getExpiredSentFlag() != null) {
                    orderSummaryEntityResponse.setExpiredSentFlag(orderSummaryEntity.getExpiredSentFlag().getValue());
                }
                if (orderSummaryEntity.getPointActivateFlag() != null) {
                    orderSummaryEntityResponse.setPointActivateFlag(
                                    orderSummaryEntity.getPointActivateFlag().getValue());
                }
                if (orderSummaryEntity.getPointType() != null) {
                    orderSummaryEntityResponse.setPointType(orderSummaryEntity.getPointType().getValue());
                }
                if (orderSummaryEntity.getEmergencyFlag() != null) {
                    orderSummaryEntityResponse.setEmergencyFlag(orderSummaryEntity.getEmergencyFlag().getValue());
                }

                orderHistoryDtoResponse.setOrderSummaryEntity(orderSummaryEntityResponse);
            }

            if (orderHistoryListDto.getSettlementMethodEntity() != null) {
                SettlementMethodEntityResponse settlementMethodEntityResponse = new SettlementMethodEntityResponse();

                SettlementMethodEntity settlementMethodEntity = orderHistoryListDto.getSettlementMethodEntity();

                settlementMethodEntityResponse.setSettlementMethodSeq(settlementMethodEntity.getSettlementMethodSeq());
                settlementMethodEntityResponse.setSettlementMethodName(
                                settlementMethodEntity.getSettlementMethodName());
                settlementMethodEntityResponse.setSettlementMethodDisplayName(
                                settlementMethodEntity.getSettlementMethodDisplayNamePC());
                settlementMethodEntityResponse.setSettlementNote(settlementMethodEntity.getSettlementNotePC());
                settlementMethodEntityResponse.setDeliveryMethodSeq(settlementMethodEntity.getDeliveryMethodSeq());
                settlementMethodEntityResponse.setEqualsCommission(settlementMethodEntity.getEqualsCommission());
                settlementMethodEntityResponse.setLargeAmountDiscountPrice(
                                settlementMethodEntity.getLargeAmountDiscountPrice());
                settlementMethodEntityResponse.setLargeAmountDiscountCommission(
                                settlementMethodEntity.getLargeAmountDiscountCommission());
                settlementMethodEntityResponse.setOrderDisplay(settlementMethodEntity.getOrderDisplay());
                settlementMethodEntityResponse.setMaxPurchasedPrice(settlementMethodEntity.getMaxPurchasedPrice());
                settlementMethodEntityResponse.setPaymentTimeLimitDayCount(
                                settlementMethodEntity.getPaymentTimeLimitDayCount());
                settlementMethodEntityResponse.setMinPurchasedPrice(settlementMethodEntity.getMinPurchasedPrice());
                settlementMethodEntityResponse.setCancelTimeLimitDayCount(
                                settlementMethodEntity.getCancelTimeLimitDayCount());
                settlementMethodEntityResponse.setRegistTime(settlementMethodEntity.getRegistTime());
                settlementMethodEntityResponse.setUpdateTime(settlementMethodEntity.getUpdateTime());

                if (settlementMethodEntity.getSettlementMethodType() != null) {
                    settlementMethodEntityResponse.setOpenStatus(settlementMethodEntity.getOpenStatusPC().getValue());
                }

                if (settlementMethodEntity.getSettlementMethodType() != null) {
                    settlementMethodEntityResponse.setSettlementMethodType(
                                    settlementMethodEntity.getSettlementMethodType().getValue());
                }
                if (settlementMethodEntity.getSettlementMethodCommissionType() != null) {
                    settlementMethodEntityResponse.setSettlementMethodCommissionType(
                                    settlementMethodEntity.getSettlementMethodCommissionType().getValue());
                }
                if (settlementMethodEntity.getBillType() != null) {
                    settlementMethodEntityResponse.setBillType(settlementMethodEntity.getBillType().getValue());
                }
                if (settlementMethodEntity.getSettlementMethodPriceCommissionFlag() != null) {
                    settlementMethodEntityResponse.setSettlementMethodPriceCommissionFlag(
                                    settlementMethodEntity.getSettlementMethodPriceCommissionFlag().getValue());
                }
                if (settlementMethodEntity.getSettlementMailRequired() != null) {
                    settlementMethodEntityResponse.setSettlementMailRequired(
                                    settlementMethodEntity.getSettlementMailRequired().getValue());
                }
                if (settlementMethodEntity.getEnableCardNoHolding() != null) {
                    settlementMethodEntityResponse.setEnableCardNoHolding(
                                    settlementMethodEntity.getEnableCardNoHolding().getValue());
                }
                if (settlementMethodEntity.getEnableSecurityCode() != null) {
                    settlementMethodEntityResponse.setEnableSecurityCode(
                                    settlementMethodEntity.getEnableSecurityCode().getValue());
                }
                if (settlementMethodEntity.getEnable3dSecure() != null) {
                    settlementMethodEntityResponse.setEnable3dSecure(
                                    settlementMethodEntity.getEnable3dSecure().getValue());
                }
                if (settlementMethodEntity.getEnableInstallment() != null) {
                    settlementMethodEntityResponse.setEnableInstallment(
                                    settlementMethodEntity.getEnableInstallment().getValue());
                }
                if (settlementMethodEntity.getEnableInstallment() != null) {
                    settlementMethodEntityResponse.setEnableBonusSinglePayment(
                                    settlementMethodEntity.getEnableBonusSinglePayment().getValue());
                }

                if (settlementMethodEntity.getEnableBonusInstallment() != null) {
                    settlementMethodEntityResponse.setEnableBonusInstallment(
                                    settlementMethodEntity.getEnableBonusInstallment().getValue());
                }

                if (settlementMethodEntity.getEnableRevolving() != null) {
                    settlementMethodEntityResponse.setEnableRevolving(
                                    settlementMethodEntity.getEnableRevolving().getValue());
                }
            }

            orderHistoryDtoResponseList.add(orderHistoryDtoResponse);
        }

        return orderHistoryDtoResponseList;
    }

    /**
     * アドレス帳クラスに変換
     *
     * @param addressBookRegistRequest アドレス帳情報登録リクエスト
     * @return アドレス帳クラス
     */
    public AddressBookEntity toAddressBookEntity(AddressBookRegistRequest addressBookRegistRequest) {

        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        if (addressBookRegistRequest == null) {
            return null;
        }

        AddressBookEntity addressBookEntity = new AddressBookEntity();
        addressBookEntity.setAddressBookSeq(addressBookRegistRequest.getAddressBookSeq());
        addressBookEntity.setMemberInfoSeq(addressBookRegistRequest.getMemberInfoSeq());
        addressBookEntity.setAddressBookName(addressBookRegistRequest.getAddressBookName());
        addressBookEntity.setAddressBookLastName(addressBookRegistRequest.getAddressBookLastName());
        addressBookEntity.setAddressBookFirstName(addressBookRegistRequest.getAddressBookFirstName());
        addressBookEntity.setAddressBookLastKana(addressBookRegistRequest.getAddressBookLastKana());
        addressBookEntity.setAddressBookFirstKana(addressBookRegistRequest.getAddressBookFirstKana());
        addressBookEntity.setAddressBookTel(addressBookRegistRequest.getAddressBookTel());
        addressBookEntity.setAddressBookZipCode(addressBookRegistRequest.getAddressBookZipCode());
        addressBookEntity.setAddressBookPrefecture(addressBookRegistRequest.getAddressBookPrefecture());
        addressBookEntity.setAddressBookAddress1(addressBookRegistRequest.getAddressBookAddress1());
        addressBookEntity.setAddressBookAddress2(addressBookRegistRequest.getAddressBookAddress2());
        addressBookEntity.setAddressBookAddress3(addressBookRegistRequest.getAddressBookAddress3());
        addressBookEntity.setAddressBookAddress4(addressBookRegistRequest.getAddressBookAddress4());
        addressBookEntity.setAddressBookAddress5(addressBookRegistRequest.getAddressBookAddress5());
        addressBookEntity.setRegistTime(conversionUtility.toTimeStamp(addressBookRegistRequest.getRegistTime()));
        addressBookEntity.setUpdateTime(conversionUtility.toTimeStamp(addressBookRegistRequest.getUpdateTime()));
        addressBookEntity.setCustomerNo(addressBookRegistRequest.getCustomerNo());
        addressBookEntity.setAddressBookApproveFlagPdr(EnumTypeUtil.getEnumFromValue(HTypeAddressBookApproveFlag.class,
                                                                                     addressBookRegistRequest.getAddressBookApproveFlagPdr()
                                                                                    ));

        return addressBookEntity;
    }

    /**
     * 代理のログインレスポンスに変換
     *
     * @param administratorEntity 管理者クラスエンティティ
     * @return 代理のログインレスポンス
     */
    public ProxyAdminLoginResponse toProxyAdminLoginResponse(AdministratorEntity administratorEntity) {
        ProxyAdminLoginResponse response = new ProxyAdminLoginResponse();
        response.setAdministratorSeq(administratorEntity.getAdministratorSeq());
        if (administratorEntity.getAdministratorStatus() != null) {
            response.setAdministratorStatus(administratorEntity.getAdministratorStatus().getValue());
        }
        response.setAdministratorId(administratorEntity.getAdministratorId());
        response.setAdministratorPassword(administratorEntity.getAdministratorPassword());
        response.setMail(administratorEntity.getMail());
        response.setAdministratorLastName(administratorEntity.getAdministratorLastName());
        response.setAdministratorFirstName(administratorEntity.getAdministratorFirstName());
        response.setAdministratorLastKana(administratorEntity.getAdministratorLastKana());
        response.setAdministratorFirstKana(administratorEntity.getAdministratorFirstKana());
        response.setUseStartDate(administratorEntity.getUseStartDate());
        response.setUseEndDate(administratorEntity.getUseEndDate());
        response.setAdminAuthGroupSeq(administratorEntity.getAdminAuthGroupSeq());
        response.setRegistTime(administratorEntity.getRegistTime());
        response.setUpdateTime(administratorEntity.getUpdateTime());
        response.setPasswordChangeTime(administratorEntity.getPasswordChangeTime());
        response.setPasswordExpiryDate(administratorEntity.getPasswordExpiryDate());
        response.setLoginFailureCount(administratorEntity.getLoginFailureCount());
        response.setAccountLockTime(administratorEntity.getAccountLockTime());
        if (administratorEntity.getPasswordNeedChangeFlag() != null) {
            response.setPasswordNeedChangeFlag(administratorEntity.getPasswordNeedChangeFlag().getValue());
        }
        if (administratorEntity.getPasswordSHA256EncryptedFlag() != null) {
            response.setPasswordSHA256EncryptedFlag(administratorEntity.getPasswordSHA256EncryptedFlag().getValue());
        }

        if (administratorEntity.getAdminAuthGroup() != null) {
            AdminAuthGroupResponse adminAuthGroupResponse = new AdminAuthGroupResponse();
            adminAuthGroupResponse.setAdminAuthGroupSeq(administratorEntity.getAdminAuthGroup().getAdminAuthGroupSeq());
            adminAuthGroupResponse.setAuthGroupDisplayName(
                            administratorEntity.getAdminAuthGroup().getAuthGroupDisplayName());
            adminAuthGroupResponse.setRegistTime(administratorEntity.getAdminAuthGroup().getRegistTime());
            adminAuthGroupResponse.setUpdateTime(administratorEntity.getAdminAuthGroup().getUpdateTime());
            adminAuthGroupResponse.setUnmodifiableGroup(administratorEntity.getAdminAuthGroup().getUnmodifiableGroup());

            if (administratorEntity.getAdminAuthGroup().getAdminAuthGroupDetailList() != null) {
                List<AdminAuthGroupDetailtResponse> adminAuthGroupDetailList = new ArrayList<>();
                for (AdminAuthGroupDetailEntity adminAuthGroupDetailEntity : administratorEntity.getAdminAuthGroup()
                                                                                                .getAdminAuthGroupDetailList()) {
                    AdminAuthGroupDetailtResponse adminAuthGroupDetailtResponse = new AdminAuthGroupDetailtResponse();
                    adminAuthGroupDetailtResponse.setAdminAuthGroupSeq(
                                    adminAuthGroupDetailEntity.getAdminAuthGroupSeq());
                    adminAuthGroupDetailtResponse.setAuthTypeCode(adminAuthGroupDetailEntity.getAuthTypeCode());
                    adminAuthGroupDetailtResponse.setAuthLevel(adminAuthGroupDetailEntity.getAuthLevel());
                    adminAuthGroupDetailtResponse.setRegistTime(adminAuthGroupDetailEntity.getRegistTime());
                    adminAuthGroupDetailtResponse.setUpdateTime(adminAuthGroupDetailEntity.getUpdateTime());
                }
                adminAuthGroupResponse.setAdminAuthGroupDetailList(adminAuthGroupDetailList);
            }
            response.setAdminAuthGroup(adminAuthGroupResponse);
        }

        return response;
    }

    /**
     * カード情報取得レスポンスに変換
     *
     * @param resultDto 決済通信結果返却用Dto
     * @return カード情報取得レスポンス
     */
    public CardInfoResponse toCardInfoResponse(ComResultDto resultDto) {
        if (ObjectUtils.isEmpty(resultDto)) {
            return null;
        }

        CardInfoResponse response = new CardInfoResponse();
        response.setResultMapList(resultDto.getResultMapList());
        response.setResponseDetail(resultDto.getResponseDetail());
        response.setResponseCode(resultDto.getResponseCode());
        response.setResultMap(resultDto.getResultMap());
        response.setMessageList(toCheckMessageDtoResponse(resultDto.getMessageList()));
        response.setResultStatus(resultDto.getResultStatus());
        return response;
    }

    /**
     * チェックメッセージDtoクラスレスポンスに変換
     *
     * @param checkMessageDtoList チェックメッセージDtoクラス
     * @return チェックメッセージDtoクラスレスポンス
     */
    public List<CheckMessageDtoResponse> toCheckMessageDtoResponse(List<CheckMessageDto> checkMessageDtoList) {
        if (CollectionUtil.isEmpty(checkMessageDtoList)) {
            return new ArrayList<>();
        }
        List<CheckMessageDtoResponse> responses = new ArrayList<>();
        for (CheckMessageDto dto : checkMessageDtoList) {
            CheckMessageDtoResponse response = new CheckMessageDtoResponse();
            response.setMessage(dto.getMessage());
            response.setMessageId(dto.getMessageId());
            response.setArgs(Arrays.asList(dto.getArgs()));
            response.setOrderConsecutiveNo(dto.getOrderConsecutiveNo());
            responses.add(response);
        }
        return responses;
    }

    // 2023-renew No60 from here

    /**
     * カードブランドレスポンスエンティティリストに変換
     *
     * @param cardBrandEntityList クレジットカードブランドEntityリスト
     * @return カードブランドレスポンスエンティティリスト
     */
    public CardBrandGetResponse toCardBrandGetResponse(List<CardBrandEntity> cardBrandEntityList) {

        CardBrandGetResponse cardBrandGetResponse = new CardBrandGetResponse();
        cardBrandGetResponse.setCardBrandList(new ArrayList<>());

        if (CollectionUtil.isEmpty(cardBrandEntityList)) {
            return cardBrandGetResponse;
        }

        for (CardBrandEntity cardBrandDto : cardBrandEntityList) {
            CardBrandResponse cardBrandResponse = new CardBrandResponse();
            cardBrandResponse.setCardBrandSeq(cardBrandDto.getCardBrandSeq());
            cardBrandResponse.setCardBrandCode(cardBrandDto.getCardBrandCode());
            cardBrandResponse.setCardBrandName(cardBrandDto.getCardBrandName());
            cardBrandResponse.setCardBrandDisplay(cardBrandDto.getCardBrandDisplayPc());
            cardBrandResponse.setOrderDisplay(cardBrandDto.getOrderDisplay());
            cardBrandResponse.setInstallment(cardBrandDto.getInstallment());
            cardBrandResponse.setBounusSingle(cardBrandDto.getBounusSingle());
            cardBrandResponse.setBounusInstallment(cardBrandDto.getBounusInstallment());
            cardBrandResponse.setRevolving(cardBrandDto.getRevolving());
            cardBrandResponse.setInstallmentCounts(cardBrandDto.getInstallmentCounts());
            cardBrandResponse.setFrontDisplayFlag(cardBrandDto.getFrontDisplayFlag().getValue());
            cardBrandGetResponse.getCardBrandList().add(cardBrandResponse);
        }

        return cardBrandGetResponse;
    }

    /**
     * 受注Dtoクラスに変換
     * ※CardInfoLogic#registCardInfoの引数用に必要な値だけセットする
     *
     * @param cardInfoRegistRequest カード情報登録リクエスト
     * @return 受注Dtoクラス
     */
    public ReceiveOrderDto toReceiveOrderDto(CardInfoRegistRequest cardInfoRegistRequest) {

        if (ObjectUtils.isEmpty(cardInfoRegistRequest)) {
            return null;
        }

        // 受注サマリクラス
        OrderSummaryEntity orderSummaryEntity = ApplicationContextUtility.getBean(OrderSummaryEntity.class);
        orderSummaryEntity.setMemberInfoSeq(cardInfoRegistRequest.getMemberInfoSeq());

        // 受注一時情報Dtoクラス
        OrderTempDto orderTempDto = ApplicationContextUtility.getBean(OrderTempDto.class);
        orderTempDto.setCardNo(cardInfoRegistRequest.getCardNumber());
        orderTempDto.setExpire(
                        cardInfoRegistRequest.getExpirationDateYear() + cardInfoRegistRequest.getExpirationDateMonth());
        orderTempDto.setKeepToken(cardInfoRegistRequest.getKeepToken());

        // 受注ご注文主クラス
        OrderPersonEntity orderPersonEntity = ApplicationContextUtility.getBean(OrderPersonEntity.class);
        orderPersonEntity.setCustomerNo(Integer.valueOf(cardInfoRegistRequest.getCustomerNo()));

        // 受注Dtoクラス
        ReceiveOrderDto receiveOrderDto = ApplicationContextUtility.getBean(ReceiveOrderDto.class);
        receiveOrderDto.setOrderSummaryEntity(orderSummaryEntity);
        receiveOrderDto.setOrderTempDto(orderTempDto);
        receiveOrderDto.setOrderPersonEntity(orderPersonEntity);

        return receiveOrderDto;
    }

    // 2023-renew No60 to here
    // 2023-renew AddNo2 from here

    /**
     * チェックメッセージDtoクラスレスポンスに変換
     *
     * @param source
     * @param target
     * @param isSendMail
     * @return
     */
    public void mappingEntityFromUpdateRequest(MemberInfoConfirmScreenUpdateRequest source,
                                               MemberInfoEntity target,
                                               boolean isSendMail) {
        if (Objects.nonNull(target) && Objects.nonNull(source)) {
            target.setNonConsultationDay(source.getNonConsultationDay());
            target.setMedicalTreatmentFlag(source.getMedicalTreatmentFlag());
            target.setMedicalTreatmentMemo(source.getMedicalTreatmentMemo());
            // メールお得情報
            target.setSendMailPermitFlag(EnumTypeUtil.getEnumFromValue(HTypeSendMailPermitFlag.class,
                                                                       source.getSendMailPermitFlag()
                                                                      ));
            // 金属商品価格お知らせメール
            target.setMetalPermitFlag(
                            EnumTypeUtil.getEnumFromValue(HTypeMetalPermitFlag.class, source.getMetalPermitFlag()));
            // 2023-renew No79 from here
            target.setOrderCompletePermitFlag(EnumTypeUtil.getEnumFromValue(HTypeOrderCompletePermitFlag.class,
                                                                            source.getOrderCompletePermitFlag()
                                                                           ));
            target.setDeliveryCompletePermitFlag(EnumTypeUtil.getEnumFromValue(HTypeDeliveryCompletePermitFlag.class,
                                                                               source.getDeliveryCompletePermitFlag()
                                                                              ));
            // 2023-renew No79 to here
            if (isSendMail) {
                target.setMemberInfoLastName(source.getMemberInfoLastName());
                target.setMemberInfoLastKana(source.getMemberInfoLastKana());
                target.setRepresentativeName(source.getRepresentativeName());
                target.setMemberInfoTel(source.getMemberInfoTel());
                target.setMemberInfoFax(source.getMemberInfoFax());
                target.setMemberInfoZipCode(source.getMemberInfoZipCode());
                target.setMemberInfoAddress1(source.getMemberInfoAddress1());
                target.setMemberInfoAddress2(source.getMemberInfoAddress2());
                target.setMemberInfoAddress3(source.getMemberInfoAddress3());
            }
        }
    }

    /**
     * DB登録の変更された項目チェック
     *
     * @param modifiedList 変更されたリスト
     * @return true：変更あり / false：変更なし
     */
    public boolean hasExecuteDBUpdateStage(List<String> modifiedList) {
        if (CollectionUtil.isEmpty(modifiedList)) {
            return false;
        }
        List<String> executeDBFields = List.of("nonConsultationDay", "medicalTreatmentFlag", "medicalTreatmentMemo",
                                               "sendMailPermitFlag", "metalPermitFlag",
                                               // 2023-renew No79 from here
                                               "orderCompletePermitFlag", "deliveryCompletePermitFlag"
                                              );
        // 2023-renew No79 to here
        return executeDBFields.stream()
                              .anyMatch(executeDBFieldName -> this.fieldHasChange(executeDBFieldName, modifiedList));
    }

    /**
     * APIで連携の変更された項目チェック
     *
     * @param modifiedList 変更されたリスト
     * @return true：変更あり / false：変更なし
     */
    public boolean hasExecuteApiInfoUpdateStage(List<String> modifiedList) {
        if (CollectionUtil.isEmpty(modifiedList)) {
            return false;
        }
        List<String> executeApiFields = List.of("nonConsultationDay", "medicalTreatmentFlag", "medicalTreatmentMemo",
                                                "sendMailPermitFlag", "metalPermitFlag"
                                               );
        return executeApiFields.stream()
                               .anyMatch(executeApiFieldName -> this.fieldHasChange(executeApiFieldName, modifiedList));
    }

    /**
     * 変更された項目チェック
     *
     * @param fieldName 項目名
     * @param modifiedList 変更されたリスト
     * @return true：変更あり / false：変更なし
     */
    private boolean fieldHasChange(String fieldName, List<String> modifiedList) {
        return modifiedList.stream().anyMatch(modifiedField -> modifiedField.contains(fieldName));
    }

    // 2023-renew AddNo2 to here
    // 2023-renew No71 from here

    /**
     * データ設定
     * @param memberInfoEntity 会員クラス
     * @param memberInfoAnnounceUpdateRequest セール通知および入荷通知の更新リクエスト
     */
    public void setDataUpdateAnnounceRequestToMemberInfoEntity(MemberInfoEntity memberInfoEntity,
                                                               MemberInfoAnnounceUpdateRequest memberInfoAnnounceUpdateRequest) {
        //        TOP画面にセール対象商品が存在する旨を通知するフラグ
        if (memberInfoAnnounceUpdateRequest.getTopSaleAnnounceFlg() != null) {
            memberInfoEntity.setTopSaleAnnounceFlg(EnumTypeUtil.getEnumFromValue(HTypeTopSaleAnnounceFlg.class,
                                                                                 memberInfoAnnounceUpdateRequest.getTopSaleAnnounceFlg()
                                                                                ));
        }

        //        セール通知の既読状況
        if (memberInfoAnnounceUpdateRequest.getSaleAnnounceWatchFlg() != null) {
            memberInfoEntity.setSaleAnnounceWatchFlg(EnumTypeUtil.getEnumFromValue(HTypeSaleAnnounceWatchFlg.class,
                                                                                   memberInfoAnnounceUpdateRequest.getSaleAnnounceWatchFlg()
                                                                                  ));
        }

        //        トップ入荷通知フラグ
        if (memberInfoAnnounceUpdateRequest.getTopStockAnnounceFlg() != null) {
            memberInfoEntity.setTopStockAnnounceFlg(EnumTypeUtil.getEnumFromValue(HTypeTopStockAnnounceFlg.class,
                                                                                  memberInfoAnnounceUpdateRequest.getTopStockAnnounceFlg()
                                                                                 ));
        }

        //        入荷通知既読フラグ
        if (memberInfoAnnounceUpdateRequest.getStockAnnounceWatchFlg() != null) {
            memberInfoEntity.setStockAnnounceWatchFlg(EnumTypeUtil.getEnumFromValue(HTypeStockAnnounceWatchFlg.class,
                                                                                    memberInfoAnnounceUpdateRequest.getStockAnnounceWatchFlg()
                                                                                   ));
        }
    }

    // 2023-renew No71 to here
    // 2023-renew No22 from here
    public MemberInfoImageListResponse toMemberInfoImageListResponse(List<MemberImageDto> memberImageDtoList) {
        MemberInfoImageListResponse memberInfoImageListResponse = new MemberInfoImageListResponse();
        List<MemberInfoImageEntityResponse> memberInfoImageEntityResponseList = new ArrayList<>();
        for (MemberImageDto memberImageDto : memberImageDtoList) {
            MemberInfoImageEntityResponse memberInfoImageEntityResponse = new MemberInfoImageEntityResponse();
            memberInfoImageEntityResponse.setMemberInfoSeq(memberImageDto.getMemberInfoSeq());
            memberInfoImageEntityResponse.setMemberImageNo(memberImageDto.getMemberImageNo());
            memberInfoImageEntityResponse.setMemberImageFileName(memberImageDto.getMemberImageFileName());
            memberInfoImageEntityResponse.setMemberImageType(memberImageDto.getMemberImageType());
            memberInfoImageEntityResponseList.add(memberInfoImageEntityResponse);
        }
        memberInfoImageListResponse.setMemberInfoImageEntityResponse(memberInfoImageEntityResponseList);
        return memberInfoImageListResponse;
    }
    // 2023-renew No22 to here
    // 2023-renew No65 from here

    /**
     * 入荷お知らせDtoListレスポンスに変換
     *
     * @param restockAnnounceListGetRequest 入荷お知らせ情報リスト取得リクエスト
     * @return 入荷お知らせDtoListレスポンス
     */
    public RestockAnnounceSearchForDaoConditionDto toRestockAnnounceConditionDto(RestockAnnounceListGetRequest restockAnnounceListGetRequest) {

        if (restockAnnounceListGetRequest == null) {
            return null;
        }

        RestockAnnounceSearchForDaoConditionDto restockAnnounceSearchForDaoConditionDto =
                        new RestockAnnounceSearchForDaoConditionDto();
        restockAnnounceSearchForDaoConditionDto.setMemberInfoSeq(restockAnnounceListGetRequest.getMemberInfoSeq());

        if (CollectionUtil.isNotEmpty(restockAnnounceListGetRequest.getExclusionGoodsSeqList())) {
            restockAnnounceSearchForDaoConditionDto.setExclusionGoodsSeqList(
                            restockAnnounceListGetRequest.getExclusionGoodsSeqList());
        }

        restockAnnounceSearchForDaoConditionDto.setRestockstatus(restockAnnounceListGetRequest.getRestockstatus());

        return restockAnnounceSearchForDaoConditionDto;
    }

    /**
     * 入荷お知らせDtoListレスポンスに変換
     *
     * @param restockAnnounceDtoList 入荷お知らせDtoクラスリスト
     * @return 入荷お知らせDtoListレスポンス
     */
    public List<RestockAnnounceDtoResponse> toRestockAnnounceDtoResponseList(List<RestockAnnounceDto> restockAnnounceDtoList) {

        if (CollectionUtil.isEmpty(restockAnnounceDtoList)) {
            return new ArrayList<>();
        }

        List<RestockAnnounceDtoResponse> restockAnnounceDtoResponseList = new ArrayList<>();

        for (RestockAnnounceDto restockAnnounceDto : restockAnnounceDtoList) {

            RestockAnnounceDtoResponse restockAnnounceDtoResponse = new RestockAnnounceDtoResponse();

            if (restockAnnounceDto.getRestockAnnounceEntity() != null) {
                RestockAnnounceEntityResponse restockAnnounceEntityResponse = new RestockAnnounceEntityResponse();
                RestockAnnounceEntity restockAnnounceEntity = restockAnnounceDto.getRestockAnnounceEntity();

                restockAnnounceEntityResponse.setMemberInfoSeq(restockAnnounceEntity.getMemberInfoSeq());
                restockAnnounceEntityResponse.setGoodsSeq(restockAnnounceEntity.getGoodsSeq());
                restockAnnounceEntityResponse.setRegistTime(restockAnnounceEntity.getRegistTime());
                restockAnnounceEntityResponse.setUpdateTime(restockAnnounceEntity.getUpdateTime());

                restockAnnounceDtoResponse.setRestockAnnounceEntityResponse(restockAnnounceEntityResponse);
            }

            if (restockAnnounceDto.getGoodsDetailsDto() != null) {
                GoodsDetailsDtoResponse goodsDetailsDtoResponse = new GoodsDetailsDtoResponse();

                GoodsDetailsDto goodsDetailsDto = restockAnnounceDto.getGoodsDetailsDto();

                goodsDetailsDtoResponse.setGoodsSeq(goodsDetailsDto.getGoodsSeq());
                goodsDetailsDtoResponse.setGoodsGroupSeq(goodsDetailsDto.getGoodsGroupSeq());
                goodsDetailsDtoResponse.setVersionNo(goodsDetailsDto.getVersionNo());
                goodsDetailsDtoResponse.setRegistTime(goodsDetailsDto.getRegistTime());
                goodsDetailsDtoResponse.setUpdateTime(goodsDetailsDto.getUpdateTime());
                goodsDetailsDtoResponse.setGoodsCode(goodsDetailsDto.getGoodsCode());
                goodsDetailsDtoResponse.setGoodsGroupMaxPrice(goodsDetailsDto.getGoodsGroupMaxPrice());
                goodsDetailsDtoResponse.setGoodsGroupMinPrice(goodsDetailsDto.getGoodsGroupMinPrice());
                goodsDetailsDtoResponse.setPreDiscountMinPrice(goodsDetailsDto.getPreDiscountMinPrice());
                goodsDetailsDtoResponse.setPreDiscountMaxPrice(goodsDetailsDto.getPreDiscountMaxPrice());
                goodsDetailsDtoResponse.setTaxRate(goodsDetailsDto.getTaxRate());
                goodsDetailsDtoResponse.setGoodsPriceInTax(goodsDetailsDto.getGoodsPriceInTax());
                goodsDetailsDtoResponse.setGoodsPrice(goodsDetailsDto.getGoodsPrice());
                goodsDetailsDtoResponse.setDeliveryType(goodsDetailsDto.getDeliveryType());
                goodsDetailsDtoResponse.setSaleStartTime(goodsDetailsDto.getSaleStartTimePC());
                goodsDetailsDtoResponse.setSaleEndTime(goodsDetailsDto.getSaleEndTimePC());
                goodsDetailsDtoResponse.setPurchasedMax(goodsDetailsDto.getPurchasedMax());
                goodsDetailsDtoResponse.setOrderDisplay(goodsDetailsDto.getOrderDisplay());
                goodsDetailsDtoResponse.setUnitValue1(goodsDetailsDto.getUnitValue1());
                goodsDetailsDtoResponse.setUnitValue2(goodsDetailsDto.getUnitValue2());
                goodsDetailsDtoResponse.setPreDiscountPrice(goodsDetailsDto.getPreDiscountPrice());
                goodsDetailsDtoResponse.setPreDisCountPriceInTax(goodsDetailsDto.getPreDisCountPriceInTax());
                goodsDetailsDtoResponse.setJanCode(goodsDetailsDto.getJanCode());
                goodsDetailsDtoResponse.setCatalogCode(goodsDetailsDto.getCatalogCode());
                goodsDetailsDtoResponse.setSalesPossibleStock(goodsDetailsDto.getSalesPossibleStock());
                goodsDetailsDtoResponse.setRealStock(goodsDetailsDto.getRealStock());
                goodsDetailsDtoResponse.setOrderReserveStock(goodsDetailsDto.getOrderReserveStock());
                goodsDetailsDtoResponse.setRemainderFewStock(goodsDetailsDto.getRemainderFewStock());
                goodsDetailsDtoResponse.setOrderPointStock(goodsDetailsDto.getOrderPointStock());
                goodsDetailsDtoResponse.setSafetyStock(goodsDetailsDto.getSafetyStock());
                goodsDetailsDtoResponse.setGoodsGroupCode(goodsDetailsDto.getGoodsGroupCode());
                goodsDetailsDtoResponse.setWhatsnewDate(goodsDetailsDto.getWhatsnewDate());
                goodsDetailsDtoResponse.setOpenStartTime(goodsDetailsDto.getOpenStartTimePC());
                goodsDetailsDtoResponse.setOpenEndTime(goodsDetailsDto.getOpenEndTimePC());
                goodsDetailsDtoResponse.setGoodsGroupName(goodsDetailsDto.getGoodsGroupName());
                goodsDetailsDtoResponse.setUnitTitle1(goodsDetailsDto.getUnitTitle1());
                goodsDetailsDtoResponse.setUnitTitle2(goodsDetailsDto.getUnitTitle2());
                goodsDetailsDtoResponse.setGoodsPreDiscountPrice(goodsDetailsDto.getGoodsPreDiscountPrice());
                goodsDetailsDtoResponse.setMetaDescription(goodsDetailsDto.getMetaDescription());
                goodsDetailsDtoResponse.setGoodsNote1(goodsDetailsDto.getGoodsNote1());
                goodsDetailsDtoResponse.setGoodsNote2(goodsDetailsDto.getGoodsNote2());
                goodsDetailsDtoResponse.setGoodsNote3(goodsDetailsDto.getGoodsNote3());
                goodsDetailsDtoResponse.setGoodsNote4(goodsDetailsDto.getGoodsNote4());
                goodsDetailsDtoResponse.setGoodsNote5(goodsDetailsDto.getGoodsNote5());
                goodsDetailsDtoResponse.setGoodsNote6(goodsDetailsDto.getGoodsNote6());
                goodsDetailsDtoResponse.setGoodsNote7(goodsDetailsDto.getGoodsNote7());
                goodsDetailsDtoResponse.setGoodsNote8(goodsDetailsDto.getGoodsNote8());
                goodsDetailsDtoResponse.setGoodsNote9(goodsDetailsDto.getGoodsNote9());
                goodsDetailsDtoResponse.setGoodsNote10(goodsDetailsDto.getGoodsNote10());
                goodsDetailsDtoResponse.setOrderSetting1(goodsDetailsDto.getOrderSetting1());
                goodsDetailsDtoResponse.setOrderSetting2(goodsDetailsDto.getOrderSetting2());
                goodsDetailsDtoResponse.setOrderSetting3(goodsDetailsDto.getOrderSetting3());
                goodsDetailsDtoResponse.setOrderSetting4(goodsDetailsDto.getOrderSetting4());
                goodsDetailsDtoResponse.setOrderSetting5(goodsDetailsDto.getOrderSetting5());
                goodsDetailsDtoResponse.setOrderSetting6(goodsDetailsDto.getOrderSetting6());
                goodsDetailsDtoResponse.setOrderSetting7(goodsDetailsDto.getOrderSetting7());
                goodsDetailsDtoResponse.setOrderSetting8(goodsDetailsDto.getOrderSetting8());
                goodsDetailsDtoResponse.setOrderSetting9(goodsDetailsDto.getOrderSetting9());
                goodsDetailsDtoResponse.setOrderSetting10(goodsDetailsDto.getOrderSetting10());
                goodsDetailsDtoResponse.setGoodsOptionDisplayName(goodsDetailsDto.getGoodsOptionDisplayName());
                goodsDetailsDtoResponse.setCoolSendFrom(goodsDetailsDto.getCoolSendFrom());
                goodsDetailsDtoResponse.setCoolSendTo(goodsDetailsDto.getCoolSendTo());
                goodsDetailsDtoResponse.setTax(goodsDetailsDto.getTax());
                goodsDetailsDtoResponse.setUnit(goodsDetailsDto.getUnit());
                goodsDetailsDtoResponse.setGoodsManagementCode(goodsDetailsDto.getGoodsManagementCode());
                goodsDetailsDtoResponse.setGoodsDivisionCode(goodsDetailsDto.getGoodsDivisionCode());
                goodsDetailsDtoResponse.setGoodsCategory1(goodsDetailsDto.getGoodsCategory1());
                goodsDetailsDtoResponse.setGoodsCategory2(goodsDetailsDto.getGoodsCategory2());
                goodsDetailsDtoResponse.setGoodsCategory3(goodsDetailsDto.getGoodsCategory3());
                goodsDetailsDtoResponse.setSaleYesNo(goodsDetailsDto.getSaleYesNo());
                goodsDetailsDtoResponse.setSaleNgMessage(goodsDetailsDto.getSaleNgMessage());
                goodsDetailsDtoResponse.setDeliveryYesNo(goodsDetailsDto.getDeliveryYesNo());
                goodsDetailsDtoResponse.setUnitImage(toUnitImageResponse(goodsDetailsDto.getUnitImage()));
                if (goodsDetailsDto.getGoodsTaxType() != null) {
                    goodsDetailsDtoResponse.setGoodsTaxType(goodsDetailsDto.getGoodsTaxType().getValue());
                }
                if (goodsDetailsDto.getAlcoholFlag() != null) {
                    goodsDetailsDtoResponse.setAlcoholFlag(goodsDetailsDto.getAlcoholFlag().getValue());
                }
                if (goodsDetailsDto.getSaleStatusPC() != null) {
                    goodsDetailsDtoResponse.setSaleStatus(goodsDetailsDto.getSaleStatusPC().getValue());
                }
                if (goodsDetailsDto.getUnitManagementFlag() != null) {
                    goodsDetailsDtoResponse.setUnitManagementFlag(goodsDetailsDto.getUnitManagementFlag().getValue());
                }
                if (goodsDetailsDto.getStockManagementFlag() != null) {
                    goodsDetailsDtoResponse.setStockManagementFlag(goodsDetailsDto.getStockManagementFlag().getValue());
                }
                if (goodsDetailsDto.getIndividualDeliveryType() != null) {
                    goodsDetailsDtoResponse.setIndividualDeliveryType(
                                    goodsDetailsDto.getIndividualDeliveryType().getValue());
                }
                if (goodsDetailsDto.getFreeDeliveryFlag() != null) {
                    goodsDetailsDtoResponse.setFreeDeliveryFlag(goodsDetailsDto.getFreeDeliveryFlag().getValue());
                }
                if (goodsDetailsDto.getGoodsOpenStatusPC() != null) {
                    goodsDetailsDtoResponse.setGoodsOpenStatus(goodsDetailsDto.getGoodsOpenStatusPC().getValue());
                }

                if (goodsDetailsDto.getGoodsGroupImageEntityList() != null) {
                    List<GoodsGroupImageEntityResponse> goodsGroupImageEntityList = new ArrayList<>();
                    for (GoodsGroupImageEntity goodsGroupImageEntity : goodsDetailsDto.getGoodsGroupImageEntityList()) {
                        GoodsGroupImageEntityResponse goodsGroupImageEntityResponse =
                                        new GoodsGroupImageEntityResponse();

                        goodsGroupImageEntityResponse.setGoodsGroupSeq(goodsGroupImageEntity.getGoodsGroupSeq());
                        goodsGroupImageEntityResponse.setImageTypeVersionNo(
                                        goodsGroupImageEntity.getImageTypeVersionNo());
                        goodsGroupImageEntityResponse.setImageFileName(goodsGroupImageEntity.getImageFileName());
                        goodsGroupImageEntityResponse.setRegistTime(goodsGroupImageEntity.getRegistTime());
                        goodsGroupImageEntityResponse.setUpdateTime(goodsGroupImageEntity.getUpdateTime());

                        goodsGroupImageEntityList.add(goodsGroupImageEntityResponse);
                    }
                    goodsDetailsDtoResponse.setGoodsGroupImageEntityList(goodsGroupImageEntityList);
                }
                if (goodsDetailsDto.getSnsLinkFlag() != null) {
                    goodsDetailsDtoResponse.setSnsLinkFlag(goodsDetailsDto.getSnsLinkFlag().getValue());
                }
                if (goodsDetailsDto.getStockStatusPc() != null) {
                    goodsDetailsDtoResponse.setStockStatus(goodsDetailsDto.getStockStatusPc().getValue());
                }
                if (goodsDetailsDto.getGoodsClassType() != null) {
                    goodsDetailsDtoResponse.setGoodsClassType(goodsDetailsDto.getGoodsClassType().getValue());
                }
                if (goodsDetailsDto.getDentalMonopolySalesFlg() != null) {
                    goodsDetailsDtoResponse.setDentalMonopolySalesFlg(
                                    goodsDetailsDto.getDentalMonopolySalesFlg().getValue());
                }
                if (goodsDetailsDto.getSaleIconFlag() != null) {
                    goodsDetailsDtoResponse.setSaleIconFlag(goodsDetailsDto.getSaleIconFlag().getValue());
                }
                if (goodsDetailsDto.getReserveIconFlag() != null) {
                    goodsDetailsDtoResponse.setReserveIconFlag(goodsDetailsDto.getReserveIconFlag().getValue());
                }
                if (goodsDetailsDto.getNewIconFlag() != null) {
                    goodsDetailsDtoResponse.setNewIconFlag(goodsDetailsDto.getNewIconFlag().getValue());
                }
                if (goodsDetailsDto.getOutletIconFlag() != null) {
                    goodsDetailsDtoResponse.setOutletIconFlag(goodsDetailsDto.getOutletIconFlag().getValue());
                }
                if (goodsDetailsDto.getLandSendFlag() != null) {
                    goodsDetailsDtoResponse.setLandSendFlag(goodsDetailsDto.getLandSendFlag().getValue());
                }
                if (goodsDetailsDto.getCoolSendFlag() != null) {
                    goodsDetailsDtoResponse.setCoolSendFlag(goodsDetailsDto.getCoolSendFlag().getValue());
                }
                if (goodsDetailsDto.getPriceMarkDispFlag() != null) {
                    goodsDetailsDtoResponse.setPriceMarkDispFlag(goodsDetailsDto.getPriceMarkDispFlag().getValue());
                }
                if (goodsDetailsDto.getSalePriceMarkDispFlag() != null) {
                    goodsDetailsDtoResponse.setSalePriceMarkDispFlag(
                                    goodsDetailsDto.getSalePriceMarkDispFlag().getValue());
                }
                if (goodsDetailsDto.getSalePriceIntegrityFlag() != null) {
                    goodsDetailsDtoResponse.setSalePriceIntegrityFlag(
                                    goodsDetailsDto.getSalePriceIntegrityFlag().getValue());
                }
                if (goodsDetailsDto.getEmotionPriceType() != null) {
                    goodsDetailsDtoResponse.setEmotionPriceType(goodsDetailsDto.getEmotionPriceType().getValue());
                }

                goodsDetailsDtoResponse.setGoodsPriceInTaxLow(goodsDetailsDto.getGoodsPriceInTaxLow());
                goodsDetailsDtoResponse.setGoodsPriceInTaxHight(goodsDetailsDto.getGoodsPriceInTaxHight());
                goodsDetailsDtoResponse.setPreDiscountPriceLow(goodsDetailsDto.getPreDiscountPriceLow());
                goodsDetailsDtoResponse.setPreDiscountPriceHight(goodsDetailsDto.getPreDiscountPriceHight());
                restockAnnounceDtoResponse.setGoodsDetailsDtoResponse(goodsDetailsDtoResponse);
            }

            if (CollectionUtil.isNotEmpty(restockAnnounceDto.getGoodsGroupImageEntityList())) {
                List<GoodsGroupImageEntityResponse> goodsGroupImageEntityListResponse = new ArrayList<>();
                for (GoodsGroupImageEntity goodsGroupImageEntity : restockAnnounceDto.getGoodsGroupImageEntityList()) {
                    GoodsGroupImageEntityResponse goodsGroupImageEntityResponse = new GoodsGroupImageEntityResponse();

                    goodsGroupImageEntityResponse.setGoodsGroupSeq(goodsGroupImageEntity.getGoodsGroupSeq());
                    goodsGroupImageEntityResponse.setImageTypeVersionNo(goodsGroupImageEntity.getImageTypeVersionNo());
                    goodsGroupImageEntityResponse.setImageFileName(goodsGroupImageEntity.getImageFileName());
                    goodsGroupImageEntityResponse.setRegistTime(goodsGroupImageEntity.getRegistTime());
                    goodsGroupImageEntityResponse.setUpdateTime(goodsGroupImageEntity.getUpdateTime());

                    goodsGroupImageEntityListResponse.add(goodsGroupImageEntityResponse);
                }
                restockAnnounceDtoResponse.setGoodsGroupImageEntityListResponse(goodsGroupImageEntityListResponse);
            }

            restockAnnounceDtoResponse.setStockStatus(restockAnnounceDto.getStockStatus());

            restockAnnounceDtoResponseList.add(restockAnnounceDtoResponse);
        }

        return restockAnnounceDtoResponseList;
    }

    // 2023-renew No65 to here
    // 2023-renew No68 from here

    /**
     * 注文履歴キャンセルDtoに変換
     *
     * @param cancelOrderHistoryOrderItemRequest 注文キャンセル情報リクエスト
     * @param cancelTime キャンセル日時
     * @return 注文履歴キャンセルDtoクラス
     */
    public CancelOrderHistoryDto toCancelOrderHistoryDto(CancelOrderHistoryOrderItemRequest cancelOrderHistoryOrderItemRequest,
                                                         Timestamp cancelTime) {

        if (ObjectUtils.isEmpty(cancelOrderHistoryOrderItemRequest)) {
            return null;
        }

        CancelOrderHistoryDto cancelOrderHistoryDto = ApplicationContextUtility.getBean(CancelOrderHistoryDto.class);

        cancelOrderHistoryDto.setOfficeName(cancelOrderHistoryOrderItemRequest.getOfficeName());
        cancelOrderHistoryDto.setOrderDate(
                        conversionUtility.toTimeStamp(cancelOrderHistoryOrderItemRequest.getOrderTime()));
        cancelOrderHistoryDto.setOrderNo(cancelOrderHistoryOrderItemRequest.getOrderCode());
        cancelOrderHistoryDto.setCancelTime(cancelTime);
        cancelOrderHistoryDto.setReceiveDate(cancelOrderHistoryOrderItemRequest.getReceiveDate());
        cancelOrderHistoryDto.setReceiveOfficeName(cancelOrderHistoryOrderItemRequest.getReceiveOfficeName());
        cancelOrderHistoryDto.setReceiveZipcode(cancelOrderHistoryOrderItemRequest.getReceiveZipCode());
        cancelOrderHistoryDto.setReceiveAddress1(cancelOrderHistoryOrderItemRequest.getReceiveAddress1());
        cancelOrderHistoryDto.setReceiveAddress2(cancelOrderHistoryOrderItemRequest.getReceiveAddress2());
        cancelOrderHistoryDto.setReceiveAddress3(cancelOrderHistoryOrderItemRequest.getReceiveAddress3());
        cancelOrderHistoryDto.setReceiveAddress4(cancelOrderHistoryOrderItemRequest.getReceiveAddress4());
        cancelOrderHistoryDto.setReceiveAddress5(cancelOrderHistoryOrderItemRequest.getReceiveAddress5());
        cancelOrderHistoryDto.setPaymentType(cancelOrderHistoryOrderItemRequest.getPaymentType());
        cancelOrderHistoryDto.setCouponNo(cancelOrderHistoryOrderItemRequest.getCouponCode());
        cancelOrderHistoryDto.setCouponName(cancelOrderHistoryOrderItemRequest.getCouponName());
        cancelOrderHistoryDto.setPaymentTotalPrice(cancelOrderHistoryOrderItemRequest.getPaymentTotalPrice());
        cancelOrderHistoryDto.setGoodsList(toCancelOrderHistoryGoodsDtoList(
                        cancelOrderHistoryOrderItemRequest.getCancelOrderHistoryGoodsItems()));

        return cancelOrderHistoryDto;
    }

    /**
     * 注文履歴キャンセル商品Dtoクラスリストに変換
     *
     * @param cancelOrderHistoryGoodsItemRequestList 注文キャンセル商品情報リクエストリスト
     * @return 注文履歴キャンセル商品Dtoクラスリスト
     */
    public List<CancelOrderHistoryGoodsDto> toCancelOrderHistoryGoodsDtoList(List<CancelOrderHistoryGoodsItemRequest> cancelOrderHistoryGoodsItemRequestList) {

        if (CollectionUtil.isEmpty(cancelOrderHistoryGoodsItemRequestList)) {
            return new ArrayList<>();
        }

        List<CancelOrderHistoryGoodsDto> cancelOrderHistoryGoodsDtoList = new ArrayList<>();

        cancelOrderHistoryGoodsItemRequestList.forEach(item -> {
            CancelOrderHistoryGoodsDto goodsDto = ApplicationContextUtility.getBean(CancelOrderHistoryGoodsDto.class);
            goodsDto.setGoodsCode(item.getGoodsCode());
            goodsDto.setGoodsName(item.getGoodsName());
            goodsDto.setGoodsCount(String.valueOf(item.getGoodsCount()));
            goodsDto.setUnitName(item.getUnitName());
            goodsDto.setDiscountFlag(item.getDiscountFlag());
            cancelOrderHistoryGoodsDtoList.add(goodsDto);
        });

        return cancelOrderHistoryGoodsDtoList;
    }

    /**
     * 注文キャンセルレスポンスに変換
     *
     * @param info 注文キャンセルレスポンスDTOリスト
     * @return 注文キャンセルレスポンス
     */
    public OrderHistoryCancelOrderResponse toOrderHistoryCancelOrderResponse(List<WebApiCancelOrderResponseDetailDto> info) {

        OrderHistoryCancelOrderResponse response = new OrderHistoryCancelOrderResponse();

        for (WebApiCancelOrderResponseDetailDto detailDto : info) {
            response.setCancelResult(detailDto.getCancelResult());
            response.setOrderNo(detailDto.getOrderNo());
            response.setReceiveDate(detailDto.getReceiveDate());
            break;
        }

        return response;
    }

    // 2023-renew No68 to here

}
