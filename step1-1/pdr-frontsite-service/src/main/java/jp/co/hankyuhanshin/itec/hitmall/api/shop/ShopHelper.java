package jp.co.hankyuhanshin.itec.hitmall.api.shop;

/**
 * ショップ Helper
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */

import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.AccessSearchKeywordRegistRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.CalendarNotSelectDateEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.CampaignEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.DeliveryImpossibleAreaResultDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.DeliveryMethodDetailsDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.DeliveryMethodEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.DeliveryMethodTypeCarriageEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.DeliverySpecialChargeAreaResultDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.FreeAreaEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.NewsEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.OpenNewsListGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.QuestionEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.QuestionReplyEntityRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.QuestionnaireEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.QuestionnaireReplyDisplayDtoListResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.QuestionnaireReplyDisplayDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.QuestionnaireReplyEntityRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.SettlementMethodResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.ShopResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.ZipCodeAddressDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.NewsSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.ZipCodeAddressDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryImpossibleAreaResultDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryMethodDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliverySpecialChargeAreaResultDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.questionnaire.QuestionnaireReplyDisplayDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.access.AccessSearchKeywordEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.CalendarNotSelectDateEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.FreeAreaEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.ShopEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.campaign.CampaignEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodTypeCarriageEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.news.NewsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionReplyEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionnaireEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionnaireReplyEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ショップHelper
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class ShopHelper {

    /**
     * ショップ情報レスポンスに変換
     *
     * @param shopEntity ショップエンティティ
     * @return ショップ情報レスポンス
     */
    public ShopResponse toShopResponse(ShopEntity shopEntity) {

        ShopResponse shopResponse = new ShopResponse();

        shopResponse.setShopId(shopEntity.getShopId());
        shopResponse.setShopNamePC(shopEntity.getShopNamePC());
        shopResponse.setUrlPC(shopEntity.getUrlPC());
        shopResponse.setMetaDescription(shopEntity.getMetaDescription());
        shopResponse.setMetaKeyword(shopEntity.getMetaKeyword());
        shopResponse.setVersionNo(shopEntity.getVersionNo());
        if (shopEntity.getAutoApprovalFlag() != null) {
            shopResponse.setAutoApprovalFlag(shopEntity.getAutoApprovalFlag().getValue());
        }

        if (shopEntity.getShopOpenStatusPC() != null) {
            shopResponse.setShopOpenStatus(shopEntity.getShopOpenStatusPC().getValue());
        }

        shopResponse.setShopOpenStartTime(shopEntity.getShopOpenStartTimePC());
        shopResponse.setShopOpenEndTime(shopEntity.getShopOpenEndTimePC());
        // 2023-renew No60 from here
        shopResponse.setCreditErrorCount(shopEntity.getCreditErrorCount());
        // 2023-renew No60 to here

        return shopResponse;
    }

    /**
     * 郵便番号住所Dtoレスポンスに変換
     *
     * @param zipCodeAddressDto 郵便番号住所情報Dto
     * @return 郵便番号住所Dtoレスポンス
     */
    public ZipCodeAddressDtoResponse toZipCodeAddressDtoResponse(ZipCodeAddressDto zipCodeAddressDto) {
        if (zipCodeAddressDto == null) {
            return null;
        }
        ZipCodeAddressDtoResponse response = new ZipCodeAddressDtoResponse();
        response.setZipCode(zipCodeAddressDto.getZipCode());
        response.setPrefectureName(zipCodeAddressDto.getPrefectureName());
        response.setPrefectureNameKana(zipCodeAddressDto.getPrefectureNameKana());
        response.setCityNameKana(zipCodeAddressDto.getCityNameKana());
        response.setCityName(zipCodeAddressDto.getCityName());
        response.setTownName(zipCodeAddressDto.getTownName());
        response.setTownNameKana(zipCodeAddressDto.getTownNameKana());
        response.setNumbers(zipCodeAddressDto.getNumbers());
        response.setZipCodeType(zipCodeAddressDto.getZipCodeType());

        return response;
    }

    /**
     * アンケートEntityレスポンスに変換
     *
     * @param entity アンケートエンティティクラス
     * @return アンケートEntityレスポンス
     */
    public QuestionnaireEntityResponse toQuestionnaireEntityResponse(QuestionnaireEntity entity) {
        if (entity == null) {
            return null;
        }

        QuestionnaireEntityResponse response = new QuestionnaireEntityResponse();
        response.setQuestionnaireSeq(entity.getQuestionnaireSeq());
        response.setQuestionnaireKey(entity.getQuestionnaireKey());
        response.setName(entity.getName());
        if (entity.getOpenStatusPc() != null) {
            response.setOpenStatus(entity.getOpenStatusPc().getValue());
        }
        response.setOpenStartTime(entity.getOpenStartTime());
        response.setOpenEndTime(entity.getOpenEndTime());
        response.setFreeText(entity.getFreeTextPc());
        response.setCompleteText(entity.getCompleteTextPc());
        response.setMemo(entity.getMemo());
        response.setVersionNo(entity.getVersionNo());
        response.setRegistTime(entity.getRegistTime());
        response.setUpdateTime(entity.getUpdateTime());
        if (entity.getSiteMapFlag() != null) {
            response.setSiteMapFlag(entity.getSiteMapFlag().getValue());
        }
        return response;
    }

    /**
     * アンケート回答画面表示用DtoListレスポンスに変換
     *
     * @param list アンケート回答画面にアンケート設問を表示するために必要なプロパティをひとまとめにしたDTOList
     * @return アンケート回答画面表示用DtoListレスポンス
     */
    public QuestionnaireReplyDisplayDtoListResponse toQuestionnaireReplyDisplayDtoListResponse(List<QuestionnaireReplyDisplayDto> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        QuestionnaireReplyDisplayDtoListResponse response = new QuestionnaireReplyDisplayDtoListResponse();
        List<QuestionnaireReplyDisplayDtoResponse> listResponse = new ArrayList<>();
        for (QuestionnaireReplyDisplayDto dto : list) {
            QuestionnaireReplyDisplayDtoResponse res = new QuestionnaireReplyDisplayDtoResponse();
            res.setQuestionEntity(toQuestionEntityResponse(dto.getQuestionEntity()));
            res.setQuestionSeq(dto.getQuestionEntity().getQuestionSeq());
            res.setDisplayNumber(dto.getDisplayNumber());
            res.setQuestion(dto.getQuestion());
            res.setQuestionTextBox(dto.getQuestionTextBox());
            res.setQuestionTextArea(dto.getQuestionTextArea());
            res.setQuestionRadio(dto.getQuestionRadio());
            res.setQuestionRadioItems(dto.getQuestionRadioItems());
            res.setQuestionRadioDisp(dto.getQuestionRadioDisp());
            res.setQuestionPullDown(dto.getQuestionPullDown());
            res.setQuestionPullDownItems(dto.getQuestionPullDownItems());
            res.setQuestionPullDownDisp(dto.getQuestionPullDownDisp());
            res.setQuestionCheckBoxItems(dto.getQuestionCheckBoxItems());

            String[] arrayQuestionCheckBox = dto.getQuestionCheckBox();
            if (arrayQuestionCheckBox != null) {
                List<String> listQuestionCheckBox = Arrays.asList(arrayQuestionCheckBox);
                res.setQuestionCheckBox(listQuestionCheckBox);
            }

            String[] arrayQuestionCheckBoxDispItems = dto.getQuestionCheckBoxDispItems();
            if (arrayQuestionCheckBoxDispItems != null) {
                List<String> listQuestionCheckBoxDispItems = Arrays.asList(arrayQuestionCheckBoxDispItems);
                res.setQuestionCheckBoxDispItems(listQuestionCheckBoxDispItems);
            }

            res.setQuestionCheckBoxDisp(dto.getQuestionCheckBoxDisp());
            if (dto.getReplyRequiredFlag() != null) {
                res.setReplyRequiredFlag(dto.getReplyRequiredFlag().getValue());
            }
            if (dto.getReplyType() != null) {
                res.setReplyType(dto.getReplyType().getValue());
            }
            if (dto.getReplyValidatePattern() != null) {
                res.setReplyValidatePattern(dto.getReplyValidatePattern().getValue());
            }
            res.setReplyMaxLength(dto.getReplyMaxLength());
            listResponse.add(res);
        }

        response.setQuestionnaireReplyDisplayDtoResponse(listResponse);
        return response;
    }

    /**
     * アンケートEntityレスポンスに変換
     *
     * @param entity アンケートエンティティクラス
     * @return アンケートEntityレスポンス
     */
    public QuestionEntityResponse toQuestionEntityResponse(QuestionEntity entity) {
        if (entity == null) {
            return null;
        }

        QuestionEntityResponse response = new QuestionEntityResponse();
        response.setQuestionSeq(entity.getQuestionSeq());
        response.setQuestionnaireSeq(entity.getQuestionnaireSeq());
        response.setOrderDisplay(entity.getOrderDisplay());
        response.setQuestion(entity.getQuestion());
        if (entity.getOpenStatus() != null) {
            response.setOpenStatus(Integer.valueOf(entity.getOpenStatus().getValue()));
        }
        if (entity.getReplyRequiredFlag() != null) {
            response.setReplyRequiredFlag(Integer.valueOf(entity.getReplyRequiredFlag().getValue()));
        }
        if (entity.getReplyType() != null) {
            response.setReplyType(Integer.valueOf(entity.getReplyType().getValue()));
        }
        if (entity.getReplyValidatePattern() != null) {
            response.setReplyValidatePattern(Integer.valueOf(entity.getReplyValidatePattern().getValue()));
        }
        response.setReplyMaxLength(entity.getReplyMaxLength());
        response.setChoices(entity.getChoices());
        response.setVersionNo(entity.getVersionNo());
        response.setRegistTime(entity.getRegistTime());
        response.setUpdateTime(entity.getUpdateTime());
        return response;
    }

    /**
     * ニュースEntityレスポンスに変換
     *
     * @param newsEntity ニュースクラス
     * @return ニュースEntityレスポンス
     */
    public NewsEntityResponse toNewsEntityResponse(NewsEntity newsEntity) {
        if (newsEntity == null) {
            return null;
        }

        NewsEntityResponse response = new NewsEntityResponse();
        response.setNewsSeq(newsEntity.getNewsSeq());
        response.setRegistTime(newsEntity.getRegistTime());
        response.setUpdateTime(newsEntity.getUpdateTime());
        response.setNewsTime(newsEntity.getNewsTime());
        response.setNewsNote(newsEntity.getNewsNotePC());
        response.setTitle(newsEntity.getTitlePC());
        response.setNewsBody(newsEntity.getNewsBodyPC());
        response.setNewsOpenEndTime(newsEntity.getNewsOpenEndTimePC());
        response.setNewsOpenStartTime(newsEntity.getNewsOpenStartTimePC());
        if (newsEntity.getNewsOpenStatusPC() != null) {
            response.setNewsOpenStatus(newsEntity.getNewsOpenStatusPC().getValue());
        }
        response.setNewsNote(newsEntity.getNewsNotePC());
        return response;
    }

    /**
     * フリーエリアEntityレスポンスに変換
     *
     * @param freeAreaEntity フリーエリアクラス
     * @return フリーエリアEntityレスポンス
     */
    public FreeAreaEntityResponse toFreeAreaEntityResponse(FreeAreaEntity freeAreaEntity) {
        if (freeAreaEntity == null) {
            return null;
        }

        FreeAreaEntityResponse response = new FreeAreaEntityResponse();
        response.setFreeAreaSeq(freeAreaEntity.getFreeAreaSeq());
        response.setFreeAreaKey(freeAreaEntity.getFreeAreaKey());
        response.setOpenStartTime(freeAreaEntity.getOpenStartTime());
        response.setFreeAreaTitle(freeAreaEntity.getFreeAreaTitle());
        response.setFreeAreaBody(freeAreaEntity.getFreeAreaBodyPc());
        response.setTargetGoods(freeAreaEntity.getTargetGoods());
        response.setRegistTime(freeAreaEntity.getRegistTime());
        response.setUpdateTime(freeAreaEntity.getUpdateTime());
        if (freeAreaEntity.getFreeAreaOpenStatus() != null) {
            response.setFreeAreaOpenStatus(freeAreaEntity.getFreeAreaOpenStatus().getValue());
        }
        if (freeAreaEntity.getSiteMapFlag() != null) {
            response.setSiteMapFlag(freeAreaEntity.getSiteMapFlag().getValue());
        }
        return response;
    }

    /**
     * ニュースDao用検索条件Dtoクラスに変換
     *
     * @param openNewsListGetRequest 公開ニュースリスト情報取得リクエスト
     * @return ニュースDao用検索条件Dtoクラス
     */
    public NewsSearchForDaoConditionDto toNewsSearchForDaoConditionDto(OpenNewsListGetRequest openNewsListGetRequest) {
        NewsSearchForDaoConditionDto dto = new NewsSearchForDaoConditionDto();
        dto.setShopSeq(1001);
        dto.setMemberInfoSeq(openNewsListGetRequest.getMemberInfoSeq());
        dto.setSiteType(HTypeSiteType.FRONT_PC);
        dto.setNewsNotePc(openNewsListGetRequest.getNewsNote());
        dto.setOpenStatus(EnumTypeUtil.getEnumFromValue(HTypeOpenStatus.class, openNewsListGetRequest.getOpenStatus()));
        return dto;
    }

    /**
     * ニュースEntityListレスポンスに変換
     *
     * @param newsEntityList ニュースクラス
     * @return ニュースEntityListレスポンス
     */
    public List<NewsEntityResponse> toNewsEntityListResponse(List<NewsEntity> newsEntityList) {

        if (CollectionUtils.isEmpty(newsEntityList)) {
            return new ArrayList<>();
        }

        List<NewsEntityResponse> list = new ArrayList<>();

        for (NewsEntity dto : newsEntityList) {
            NewsEntityResponse res = new NewsEntityResponse();
            res.setNewsSeq(dto.getNewsSeq());
            res.setTitle(dto.getTitlePC());
            res.setNewsBody(dto.getNewsBodyPC());
            res.setNewsUrl(dto.getNewsUrlPC());
            if (dto.getNewsOpenStatusPC() != null) {
                res.setNewsOpenStatus(dto.getNewsOpenStatusPC().getValue());
            }
            res.setNewsOpenStartTime(dto.getNewsOpenStartTimePC());
            res.setNewsOpenEndTime(dto.getNewsOpenEndTimePC());
            res.setNewsTime(dto.getNewsTime());
            res.setRegistTime(dto.getRegistTime());
            res.setUpdateTime(dto.getUpdateTime());
            res.setNewsNote(dto.getNewsNotePC());
            list.add(res);
        }

        return list;
    }

    /**
     * 配送方法詳細Dtoレスポンスに変換
     *
     * @param deliveryMethodDetailsDtoMap 配送方法詳細Dtoクラス
     * @return 配送方法詳細Dtoレスポンス
     */
    public Map<String, DeliveryMethodDetailsDtoResponse> toDeliveryMethodDetailsDtoMapResponse(Map<Integer, DeliveryMethodDetailsDto> deliveryMethodDetailsDtoMap) {
        if (deliveryMethodDetailsDtoMap == null) {
            return new HashMap<>();
        }

        Map<String, DeliveryMethodDetailsDtoResponse> map = new HashMap<>();

        for (Map.Entry<Integer, DeliveryMethodDetailsDto> entry : deliveryMethodDetailsDtoMap.entrySet()) {
            String newKey = String.valueOf(entry.getKey());
            DeliveryMethodDetailsDtoResponse newValue = toDeliveryMethodDetailsDto(entry.getValue());
            map.put(newKey, newValue);
        }

        return map;
    }

    /**
     * 配送方法詳細Dtoレスポンスに変換
     *
     * @param deliveryMethodDetailsDto 配送方法詳細Dtoクラス
     * @return 配送方法詳細Dtoレスポンス
     */
    private DeliveryMethodDetailsDtoResponse toDeliveryMethodDetailsDto(DeliveryMethodDetailsDto deliveryMethodDetailsDto) {
        if (deliveryMethodDetailsDto == null) {
            return null;
        }

        DeliveryMethodDetailsDtoResponse response = new DeliveryMethodDetailsDtoResponse();
        response.setDeliveryMethodEntity(
                        toDeliveryMethodEntityResponse(deliveryMethodDetailsDto.getDeliveryMethodEntity()));
        response.setDeliveryMethodTypeCarriageEntityList(toDeliveryMethodTypeCarriageEntityResponse(
                        deliveryMethodDetailsDto.getDeliveryMethodTypeCarriageEntityList()));
        response.setDeliverySpecialChargeAreaCount(deliveryMethodDetailsDto.getDeliverySpecialChargeAreaCount());
        response.setDeliveryImpossibleAreaCount(deliveryMethodDetailsDto.getDeliveryImpossibleAreaCount());
        response.setDeliveryImpossibleAreaResultDtoList(toDeliveryImpossibleAreaResultDtoResponse(
                        deliveryMethodDetailsDto.getDeliveryImpossibleAreaResultDtoList()));
        response.setDeliverySpecialChargeAreaResultDtoList(toDeliverySpecialChargeAreaResultDtoResponse(
                        deliveryMethodDetailsDto.getDeliverySpecialChargeAreaResultDtoList()));
        return response;
    }

    /**
     * フリーエリアEntityレスポンスに変換
     *
     * @param entity 配送方法クラス
     * @return フリーエリアEntityレスポンス
     */
    private DeliveryMethodEntityResponse toDeliveryMethodEntityResponse(DeliveryMethodEntity entity) {
        if (entity == null) {
            return null;
        }

        DeliveryMethodEntityResponse response = new DeliveryMethodEntityResponse();
        response.setDeliveryMethodSeq(entity.getDeliveryMethodSeq());
        response.setDeliveryMethodName(entity.getDeliveryMethodName());
        response.setDeliveryMethodDisplayName(entity.getDeliveryMethodDisplayNamePC());
        if (entity.getOpenStatusPC() != null) {
            response.setOpenStatus(entity.getOpenStatusPC().getValue());
        }
        response.setDeliveryNote(entity.getDeliveryNotePC());
        if (entity.getDeliveryMethodType() != null) {
            response.setDeliveryMethodType(entity.getDeliveryMethodType().getValue());
        }
        response.setEqualsCarriage(entity.getEqualsCarriage());
        response.setLargeAmountDiscountPrice(entity.getLargeAmountDiscountPrice());
        response.setLargeAmountDiscountCarriage(entity.getLargeAmountDiscountCarriage());
        if (entity.getShortfallDisplayFlag() != null) {
            response.setShortfallDisplayFlag(entity.getShortfallDisplayFlag().getValue());
        }
        response.setDeliveryLeadTime(entity.getDeliveryLeadTime());
        response.setDeliveryChaseURL(entity.getDeliveryChaseURL());
        response.setDeliveryChaseURLDisplayPeriod(entity.getDeliveryChaseURLDisplayPeriod());
        response.setPossibleSelectDays(entity.getPossibleSelectDays());
        response.setReceiverTimeZone1(entity.getReceiverTimeZone1());
        response.setReceiverTimeZone2(entity.getReceiverTimeZone2());
        response.setReceiverTimeZone3(entity.getReceiverTimeZone3());
        response.setReceiverTimeZone4(entity.getReceiverTimeZone4());
        response.setReceiverTimeZone5(entity.getReceiverTimeZone5());
        response.setReceiverTimeZone6(entity.getReceiverTimeZone6());
        response.setReceiverTimeZone7(entity.getReceiverTimeZone7());
        response.setReceiverTimeZone8(entity.getReceiverTimeZone8());
        response.setReceiverTimeZone9(entity.getReceiverTimeZone9());
        response.setReceiverTimeZone10(entity.getReceiverTimeZone10());
        response.setOrderDisplay(entity.getOrderDisplay());
        response.setRegistTime(entity.getRegistTime());
        response.setUpdateTime(entity.getUpdateTime());
        return response;
    }

    /**
     * 配送区分別送料クラスに変換
     *
     * @param deliveryMethodTypeCarriageEntityList 配送区分別送料クラス
     * @return 配送区分別送料クラス
     */
    private List<DeliveryMethodTypeCarriageEntityResponse> toDeliveryMethodTypeCarriageEntityResponse(List<DeliveryMethodTypeCarriageEntity> deliveryMethodTypeCarriageEntityList) {
        if (CollectionUtils.isEmpty(deliveryMethodTypeCarriageEntityList)) {
            return new ArrayList<>();
        }

        List<DeliveryMethodTypeCarriageEntityResponse> response = new ArrayList<>();
        for (DeliveryMethodTypeCarriageEntity dto : deliveryMethodTypeCarriageEntityList) {
            DeliveryMethodTypeCarriageEntityResponse res = new DeliveryMethodTypeCarriageEntityResponse();
            res.setDeliveryMethodSeq(dto.getDeliveryMethodSeq());
            if (dto.getPrefectureType() != null) {
                res.setPrefectureType(dto.getPrefectureType().getValue());
            }
            res.setMaxPrice(dto.getMaxPrice());
            res.setCarriage(dto.getCarriage());
            res.setRegistTime(dto.getRegistTime());
            res.setUpdateTime(dto.getUpdateTime());
            response.add(res);
        }
        return response;
    }

    /**
     * 配送不可能エリア詳細Dtoクラスレスポンスに変換
     *
     * @param deliveryImpossibleAreaResultDtoList 配送不可能エリア詳細Dtoクラス
     * @return 配送不可能エリア詳細Dtoクラスレスポンス
     */
    private List<DeliveryImpossibleAreaResultDtoResponse> toDeliveryImpossibleAreaResultDtoResponse(List<DeliveryImpossibleAreaResultDto> deliveryImpossibleAreaResultDtoList) {
        if (CollectionUtils.isEmpty(deliveryImpossibleAreaResultDtoList)) {
            return new ArrayList<>();
        }

        List<DeliveryImpossibleAreaResultDtoResponse> response = new ArrayList<>();
        for (DeliveryImpossibleAreaResultDto dto : deliveryImpossibleAreaResultDtoList) {
            DeliveryImpossibleAreaResultDtoResponse res = new DeliveryImpossibleAreaResultDtoResponse();
            res.setDeliveryMethodSeq(dto.getDeliveryMethodSeq());
            res.setZipcode(dto.getZipcode());
            res.setPrefecture(dto.getPrefecture());
            res.setCity(dto.getCity());
            res.setTown(dto.getTown());
            res.setNumbers(dto.getNumbers());
            res.setAddressList(dto.getAddressList());
            response.add(res);
        }
        return response;
    }

    /**
     * 配送特別料金エリア詳細Dtoクラスレスポンスに変換
     *
     * @param deliverySpecialChargeAreaResultDtoList 配送特別料金エリア詳細Dtoクラス
     * @return 配送特別料金エリア詳細Dtoクラスレスポンス
     */
    private List<DeliverySpecialChargeAreaResultDtoResponse> toDeliverySpecialChargeAreaResultDtoResponse(List<DeliverySpecialChargeAreaResultDto> deliverySpecialChargeAreaResultDtoList) {
        if (CollectionUtils.isEmpty(deliverySpecialChargeAreaResultDtoList)) {
            return new ArrayList<>();
        }

        List<DeliverySpecialChargeAreaResultDtoResponse> response = new ArrayList<>();
        for (DeliverySpecialChargeAreaResultDto dto : deliverySpecialChargeAreaResultDtoList) {
            DeliverySpecialChargeAreaResultDtoResponse res = new DeliverySpecialChargeAreaResultDtoResponse();
            res.setDeliveryMethodSeq(dto.getDeliveryMethodSeq());
            res.setZipcode(dto.getZipcode());
            res.setCarriage(dto.getCarriage());
            res.setPrefecture(dto.getPrefecture());
            res.setCity(dto.getCity());
            res.setTown(dto.getTown());
            res.setNumbers(dto.getNumbers());
            res.setAddressList(dto.getAddressList());
            response.add(res);
        }
        return response;
    }

    /**
     * アンケート回答エンティティクラスに変換
     *
     * @param request アンケート回答エンティティクラス
     * @return アンケート回答エンティティクラス
     */
    public QuestionnaireReplyEntity toQuestionnaireReplyEntityRequest(QuestionnaireReplyEntityRequest request) {
        if (request == null) {
            return null;
        }

        QuestionnaireReplyEntity questionnaireReplyEntityRequest = new QuestionnaireReplyEntity();
        questionnaireReplyEntityRequest.setQuestionnaireSeq(request.getQuestionnaireSeq());
        questionnaireReplyEntityRequest.setQuestionnaireReplySeq(request.getQuestionnaireReplySeq());
        questionnaireReplyEntityRequest.setSiteType(
                        EnumTypeUtil.getEnumFromValue(HTypeSiteType.class, request.getSiteType()));
        questionnaireReplyEntityRequest.setDeviceType(
                        EnumTypeUtil.getEnumFromValue(HTypeDeviceType.class, request.getDeviceType()));
        questionnaireReplyEntityRequest.setMemberInfoSeq(request.getMemberInfoSeq());
        questionnaireReplyEntityRequest.setOrderCode(request.getOrderCode());
        questionnaireReplyEntityRequest.setRegistTime(toTimeStamp(request.getRegistTime()));
        questionnaireReplyEntityRequest.setQuestionnaireKey(request.getQuestionnaireKey());
        return questionnaireReplyEntityRequest;
    }

    /**
     * アンケート設問回答エンティティクラスに変換
     *
     * @param requestList アンケート設問回答エンティティクラス
     * @return アンケート設問回答エンティティクラス
     */
    public List<QuestionReplyEntity> toQuestionReplyEntityRequest(List<QuestionReplyEntityRequest> requestList) {
        if (CollectionUtils.isEmpty(requestList)) {
            return new ArrayList<>();
        }

        List<QuestionReplyEntity> questionReplyEntityList = new ArrayList<>();
        for (QuestionReplyEntityRequest req : requestList) {
            QuestionReplyEntity questionReplyEntity = new QuestionReplyEntity();
            questionReplyEntity.setQuestionnaireReplySeq(req.getQuestionnaireReplySeq());
            questionReplyEntity.setQuestionSeq(req.getQuestionSeq());
            questionReplyEntity.setReply(req.getReply());
            questionReplyEntity.setRegistTime(toTimeStamp(req.getRegistTime()));
            questionReplyEntityList.add(questionReplyEntity);
        }
        return questionReplyEntityList;
    }

    /**
     * アクセス検索キーワードクラスに変換
     *
     * @param request カテゴリ木構造取得リクエスト
     * @return アクセス検索キーワードクラス
     */
    public AccessSearchKeywordEntity toAccessSearchKeywordEntity(AccessSearchKeywordRegistRequest request) {
        AccessSearchKeywordEntity res = new AccessSearchKeywordEntity();
        res.setAccessTime(toTimeStamp(request.getAccessTime()));
        res.setSearchkeyword(request.getSearchkeyword());
        res.setShopSeq(1001);
        res.setRegistTime(toTimeStamp(request.getRegistTime()));
        res.setPageId(request.getPageId());
        res.setSearchCategorySeq(request.getSearchCategorySeq());
        res.setSearchPriceFrom(request.getSearchPriceFrom());
        res.setSearchPriceTo(request.getSearchPriceTo());
        res.setSearchResultCount(request.getSearchResultCount());
        res.setSiteType(HTypeSiteType.FRONT_PC);
        res.setAccessUid(request.getAccessUid());
        res.setUpdateTime(toTimeStamp(request.getUpdateTime()));
        return res;
    }

    /**
     * Date⇒TimeStamp変換
     *
     * @param date 日時
     * @return Timestamp
     */
    public Timestamp toTimeStamp(Date date) {
        if (date != null) {
            return new Timestamp(date.getTime());
        }
        return null;
    }

    /**
     * 決済方法レスポンスに変換
     *
     * @param settlementMethodEntity 決済方法クラス
     * @return 決済方法レスポンス
     */
    public SettlementMethodResponse toSettlementMethodResponse(SettlementMethodEntity settlementMethodEntity) {

        if (ObjectUtils.isEmpty(settlementMethodEntity)) {
            return null;
        }

        SettlementMethodResponse settlementMethodResponse = new SettlementMethodResponse();
        settlementMethodResponse.setSettlementMethodSeq(settlementMethodEntity.getSettlementMethodSeq());
        settlementMethodResponse.setSettlementMethodName(settlementMethodEntity.getSettlementMethodName());
        settlementMethodResponse.settlementMethodDisplayName(settlementMethodEntity.getSettlementMethodName());
        if (settlementMethodEntity.getOpenStatusPC() != null) {
            settlementMethodResponse.setOpenStatus(settlementMethodEntity.getOpenStatusPC().getValue());
        }
        settlementMethodResponse.settlementNote(settlementMethodEntity.getSettlementNotePC());
        if (settlementMethodEntity.getSettlementMethodType() != null) {
            settlementMethodResponse.settlementMethodType(settlementMethodEntity.getSettlementMethodType().getValue());
        }
        if (settlementMethodEntity.getSettlementMethodCommissionType() != null) {
            settlementMethodResponse.setSettlementMethodCommissionType(
                            settlementMethodEntity.getSettlementMethodCommissionType().getValue());
        }
        if (settlementMethodEntity.getBillType() != null) {
            settlementMethodResponse.setBillType(settlementMethodEntity.getBillType().getValue());
        }
        settlementMethodResponse.setDeliveryMethodSeq(settlementMethodEntity.getDeliveryMethodSeq());
        settlementMethodResponse.setEqualsCommission(settlementMethodEntity.getEqualsCommission());
        if (settlementMethodEntity.getSettlementMethodPriceCommissionFlag() != null) {
            settlementMethodResponse.setSettlementMethodPriceCommissionFlag(
                            settlementMethodEntity.getSettlementMethodPriceCommissionFlag().getValue());
        }
        settlementMethodResponse.setLargeAmountDiscountPrice(settlementMethodEntity.getLargeAmountDiscountPrice());
        settlementMethodResponse.setLargeAmountDiscountCommission(
                        settlementMethodEntity.getLargeAmountDiscountCommission());
        settlementMethodResponse.setOrderDisplay(settlementMethodEntity.getOrderDisplay());
        settlementMethodResponse.setMaxPurchasedPrice(settlementMethodEntity.getMaxPurchasedPrice());
        settlementMethodResponse.setPaymentTimeLimitDayCount(settlementMethodEntity.getPaymentTimeLimitDayCount());
        settlementMethodResponse.setMinPurchasedPrice(settlementMethodEntity.getMinPurchasedPrice());
        settlementMethodResponse.setCancelTimeLimitDayCount(settlementMethodEntity.getCancelTimeLimitDayCount());
        if (settlementMethodEntity.getSettlementMailRequired() != null) {
            settlementMethodResponse.setSettlementMailRequired(
                            settlementMethodEntity.getSettlementMailRequired().getValue());
        }
        if (settlementMethodEntity.getEnableCardNoHolding() != null) {
            settlementMethodResponse.setEnableCardNoHolding(settlementMethodEntity.getEnableCardNoHolding().getValue());
        }
        if (settlementMethodEntity.getEnableSecurityCode() != null) {
            settlementMethodResponse.setEnableSecurityCode(settlementMethodEntity.getEnableSecurityCode().getValue());
        }
        if (settlementMethodEntity.getEnable3dSecure() != null) {
            settlementMethodResponse.setEnable3dSecure(settlementMethodEntity.getEnable3dSecure().getValue());
        }
        if (settlementMethodEntity.getEnableInstallment() != null) {
            settlementMethodResponse.setEnableInstallment(settlementMethodEntity.getEnableInstallment().getValue());
        }
        if (settlementMethodEntity.getEnableBonusSinglePayment() != null) {
            settlementMethodResponse.setEnableBonusSinglePayment(
                            settlementMethodEntity.getEnableBonusSinglePayment().getValue());
        }
        if (settlementMethodEntity.getEnableBonusInstallment() != null) {
            settlementMethodResponse.setEnableBonusInstallment(
                            settlementMethodEntity.getEnableBonusInstallment().getValue());
        }
        if (settlementMethodEntity.getEnableRevolving() != null) {
            settlementMethodResponse.setEnableRevolving(settlementMethodEntity.getEnableRevolving().getValue());
        }

        settlementMethodResponse.setRegistTime(settlementMethodEntity.getRegistTime());
        settlementMethodResponse.setUpdateTime(settlementMethodEntity.getUpdateTime());

        return settlementMethodResponse;
    }

    /**
     * キャンペーンレスポンスに変換
     *
     * @param campaignEntity キャンペーンクラス
     * @return キャンペーンレスポンス
     */
    public CampaignEntityResponse toCampaignEntityResponse(CampaignEntity campaignEntity) {
        if (campaignEntity == null) {
            return null;
        }
        CampaignEntityResponse response = new CampaignEntityResponse();
        response.setCampaignSeq(campaignEntity.getCampaignSeq());
        response.setShopSeq(1001);
        response.setCampaignCode(campaignEntity.getCampaignCode());
        response.setCampaignName(campaignEntity.getCampaignName());
        response.setCampaignStartDate(campaignEntity.getCampaignStartDate());
        response.setCampaignEndDate(campaignEntity.getCampaignEndDate());
        response.setRedirectUrl(campaignEntity.getRedirectUrl());
        response.setNote(campaignEntity.getNote());
        response.setCampaignCost(campaignEntity.getCampaignCost());
        if (campaignEntity.getDeleteFlag() != null) {
            response.setDeleteFlag(campaignEntity.getDeleteFlag().getValue());
        }
        response.setRegistTime(campaignEntity.getRegistTime());
        response.setUpdateTime(campaignEntity.getUpdateTime());

        return response;
    }

    // 2023-renew No14 from here

    /**
     * カレンダー選択不可日付EntityListレスポンスに変換
     *
     * @param calendarNotSelectDateEntityList カレンダー選択不可日付EntityList
     * @return カレンダー選択不可日付EntityListレスポンス
     */
    public List<CalendarNotSelectDateEntityResponse> toCalendarNotSelectDateEntityResponseList(List<CalendarNotSelectDateEntity> calendarNotSelectDateEntityList) {

        if (CollectionUtil.isEmpty(calendarNotSelectDateEntityList)) {
            return new ArrayList<>();
        }

        List<CalendarNotSelectDateEntityResponse> responseList = new ArrayList<>();

        calendarNotSelectDateEntityList.forEach(item -> {
            CalendarNotSelectDateEntityResponse response = new CalendarNotSelectDateEntityResponse();
            response.setNotPossibleReserveDate(item.getNotPossibleReserveDate());
            responseList.add(response);
        });

        return responseList;
    }

    // 2023-renew No14 from here
}
