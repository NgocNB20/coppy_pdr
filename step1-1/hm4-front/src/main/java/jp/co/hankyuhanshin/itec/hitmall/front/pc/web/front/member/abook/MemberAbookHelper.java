/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.abook;

import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.AddressBookEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.AddressBookRegistRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.AddressBookUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetDestinationResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetDestinationResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeAddressBookApproveFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.memberinfo.addressbook.AddressBookSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.member.WebApiGetDestinationResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.member.WebApiGetDestinationResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.addressbook.AddressBookEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * アドレス帳 Helperクラス
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Component
public class MemberAbookHelper {

    private ConversionUtility conversionUtility;

    public MemberAbookHelper(ConversionUtility conversionUtility) {
        this.conversionUtility = conversionUtility;
    }

    // PDR Migrate Customization from here

    /**
     * ページ変換、初期表示
     *
     * @param responseGetDestinationDto アドレス帳Model
     * @param memberAbookModel          WEB-API連携レスポンスDTOクラス
     */
    public void toPageForLoad(WebApiGetDestinationResponseDto responseGetDestinationDto,
                              MemberAbookModel memberAbookModel) {

        List<MemberAbookModelItem> abookModelItemList = new ArrayList<>();

        if (responseGetDestinationDto.getInfo() != null) {
            for (WebApiGetDestinationResponseDetailDto res : responseGetDestinationDto.getInfo()) {
                MemberAbookModelItem memberAbookModelItem = new MemberAbookModelItem();
                // お届け先顧客番号
                memberAbookModelItem.setReceiveCustomerNo(res.getReceiveCustomerNo());
                // 事業所名
                memberAbookModelItem.setAddressBookName(res.getOfficeName());
                // 事業所名フリガナ
                memberAbookModelItem.setAddressBookNameKana(res.getOfficeKana());
                // 代表者名
                memberAbookModelItem.setAddressBookFirstName(res.getRepresentative());
                // 顧客区分
                memberAbookModelItem.setBusinessType(res.getBusinessType());
                // 住所録TEL
                memberAbookModelItem.setAddressBookTel(res.getTel());
                // 住所録住所-郵便番号
                memberAbookModelItem.setAddressBookZipCode(res.getZipCode());
                // 住所録住所-都道府県・市区郡
                memberAbookModelItem.setAddressBookAddress1(res.getCity());
                // 住所録住所-町村・番地
                memberAbookModelItem.setAddressBookAddress2(res.getAddress());
                // 住所録住所-それ以降の住所
                memberAbookModelItem.setAddressBookAddress3(res.getBuilding());
                // 住所録住所-方書1,2
                memberAbookModelItem.setAddressBookAddress4(res.getOther());

                abookModelItemList.add(memberAbookModelItem);
            }
        }
        memberAbookModel.setAddressBookItems(abookModelItemList);

    }
    // PDR Migrate Customization to here

    /**
     * アドレス帳検索条件Dto作成
     *
     * @param memberAbookModel アドレス帳モデル
     * @return アドレス帳検索条件Dto
     */
    public AddressBookSearchForDaoConditionDto toAddressBookConditionDtoForsearchAddressBookList(MemberAbookModel memberAbookModel) {

        // アドレス帳検索条件Dto作成
        AddressBookSearchForDaoConditionDto addressBookConditionDto =
                        ApplicationContextUtility.getBean(AddressBookSearchForDaoConditionDto.class);

        addressBookConditionDto.setMemberInfoSeq(
                        memberAbookModel.getCommonInfo().getCommonInfoUser().getMemberInfoSeq());

        return addressBookConditionDto;
    }

    /**
     * ページ変換、初期表示
     *
     * @param addressBookEntity アドレス帳エンティティ
     * @param memberAbookModel  アドレス帳モデル
     */
    public void toPageForLoad(AddressBookEntity addressBookEntity, MemberAbookModel memberAbookModel) {

        memberAbookModel.setAddressBookSeq(addressBookEntity.getAddressBookSeq());
        memberAbookModel.setMemberInfoSeq(addressBookEntity.getMemberInfoSeq());

        memberAbookModel.setAddressBookName(addressBookEntity.getAddressBookName());
        memberAbookModel.setFirstName(addressBookEntity.getAddressBookFirstName());
        memberAbookModel.setLastName(addressBookEntity.getAddressBookLastName());
        memberAbookModel.setFirstKana(addressBookEntity.getAddressBookFirstKana());
        memberAbookModel.setLastKana(addressBookEntity.getAddressBookLastKana());

        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        String[] zipCode = conversionUtility.toZipCodeArray(addressBookEntity.getAddressBookZipCode());
        memberAbookModel.setZipCode1(zipCode[0]);
        memberAbookModel.setZipCode2(zipCode[1]);

        memberAbookModel.setPrefecture(addressBookEntity.getAddressBookPrefecture());
        memberAbookModel.setAddress1(addressBookEntity.getAddressBookAddress1());
        memberAbookModel.setAddress2(addressBookEntity.getAddressBookAddress2());
        memberAbookModel.setAddress3(addressBookEntity.getAddressBookAddress3());
        memberAbookModel.setTel(addressBookEntity.getAddressBookTel());

        memberAbookModel.setRegistTime(addressBookEntity.getRegistTime());
        memberAbookModel.setUpdateTime(addressBookEntity.getUpdateTime());

    }

    /**
     * アドレス帳エンティティ変換、登録
     *
     * @param memberAbookModel アドレス帳モデル
     * @return アドレス帳エンティティ
     */
    public AddressBookEntity toAddressBookEntityForAddressBookRegist(MemberAbookModel memberAbookModel) {

        AddressBookEntity addressBookEntity = toEntityFromPage(memberAbookModel);

        // 会員SEQの設定
        addressBookEntity.setMemberInfoSeq(memberAbookModel.getCommonInfo().getCommonInfoUser().getMemberInfoSeq());

        return addressBookEntity;
    }

    /**
     * アドレス帳エンティティ変換、更新
     *
     * @param memberAbookModel アドレス帳モデル
     * @return アドレス帳エンティティ
     */
    public AddressBookEntity toAddressBookEntityForAddressBookUpdate(MemberAbookModel memberAbookModel) {
        return toEntityFromPage(memberAbookModel);
    }

    /**
     * エンティティ変換
     *
     * @param memberAbookModel アドレス帳モデル
     * @return アドレス帳エンティティ
     */
    protected AddressBookEntity toEntityFromPage(MemberAbookModel memberAbookModel) {

        AddressBookEntity addressBookEntity = ApplicationContextUtility.getBean(AddressBookEntity.class);
        addressBookEntity.setAddressBookSeq(memberAbookModel.getAddressBookSeq());
        addressBookEntity.setMemberInfoSeq(memberAbookModel.getMemberInfoSeq());
        addressBookEntity.setAddressBookName(memberAbookModel.getAddressBookName());
        addressBookEntity.setAddressBookFirstName(memberAbookModel.getFirstName());
        addressBookEntity.setAddressBookLastName(memberAbookModel.getLastName());
        addressBookEntity.setAddressBookFirstKana(memberAbookModel.getFirstKana());
        addressBookEntity.setAddressBookLastKana(memberAbookModel.getLastKana());
        addressBookEntity.setAddressBookZipCode(memberAbookModel.getZipCode1() + memberAbookModel.getZipCode2());
        addressBookEntity.setAddressBookPrefecture(memberAbookModel.getPrefecture());
        addressBookEntity.setAddressBookAddress1(memberAbookModel.getAddress1());
        addressBookEntity.setAddressBookAddress2(memberAbookModel.getAddress2());
        addressBookEntity.setAddressBookAddress3(memberAbookModel.getAddress3());
        addressBookEntity.setAddressBookTel(memberAbookModel.getTel());
        addressBookEntity.setRegistTime(memberAbookModel.getRegistTime());
        addressBookEntity.setUpdateTime(memberAbookModel.getUpdateTime());

        return addressBookEntity;
    }

    /**
     * WEB-API連携レスポンスDTOクラス
     * お届け先参照
     *
     * @param response 数量割引情報取得レスポンス
     * @return お届け先参照
     */
    public WebApiGetDestinationResponseDto toWebApiGetDestinationResponseDto(WebApiGetDestinationResponse response) {
        if (response.getInfo() == null) {
            return null;
        }

        WebApiGetDestinationResponseDto responseDto = new WebApiGetDestinationResponseDto();
        List<WebApiGetDestinationResponseDetailDto> list = new ArrayList<>();
        for (WebApiGetDestinationResponseDetailDtoResponse dto : response.getInfo()) {
            WebApiGetDestinationResponseDetailDto res = new WebApiGetDestinationResponseDetailDto();
            res.setReceiveCustomerNo(dto.getReceiveCustomerNo());
            res.setOfficeName(dto.getOfficeName());
            res.setOfficeKana(dto.getOfficeKana());
            res.setRepresentative(dto.getRepresentative());
            res.setBusinessType(dto.getBusinessType());
            res.setTel(dto.getTel());
            res.setZipCode(dto.getZipCode());
            res.setCity(dto.getCity());
            res.setAddress(dto.getAddress());
            res.setBuilding(dto.getBuilding());
            res.setOther(dto.getOther());
            res.setApprovalFlag(dto.getApprovalFlag());
            list.add(res);
        }
        responseDto.setInfo(list);

        return responseDto;
    }

    /**
     * アドレス帳クラス
     *
     * @param res アドレス帳Entityレスポンス
     * @return アドレス帳クラス
     */
    public AddressBookEntity toAddressBookEntity(AddressBookEntityResponse res) {
        if (res == null) {
            return null;
        }
        AddressBookEntity addressBookEntity = new AddressBookEntity();
        addressBookEntity.setAddressBookSeq(res.getAddressBookSeq());
        addressBookEntity.setMemberInfoSeq(res.getMemberInfoSeq());
        addressBookEntity.setAddressBookName(res.getAddressBookName());
        addressBookEntity.setAddressBookLastName(res.getAddressBookLastName());
        addressBookEntity.setAddressBookFirstName(res.getAddressBookFirstName());
        addressBookEntity.setAddressBookLastKana(res.getAddressBookLastKana());
        addressBookEntity.setAddressBookFirstKana(res.getAddressBookFirstKana());
        addressBookEntity.setAddressBookTel(res.getAddressBookTel());
        addressBookEntity.setAddressBookZipCode(res.getAddressBookZipCode());
        addressBookEntity.setAddressBookPrefecture(res.getAddressBookPrefecture());
        addressBookEntity.setAddressBookAddress1(res.getAddressBookAddress1());
        addressBookEntity.setAddressBookAddress2(res.getAddressBookAddress2());
        addressBookEntity.setAddressBookAddress3(res.getAddressBookAddress3());
        addressBookEntity.setAddressBookAddress4(res.getAddressBookAddress4());
        addressBookEntity.setAddressBookAddress5(res.getAddressBookAddress5());
        addressBookEntity.setRegistTime(conversionUtility.toTimeStamp(res.getRegistTime()));
        addressBookEntity.setUpdateTime(conversionUtility.toTimeStamp(res.getUpdateTime()));
        addressBookEntity.setCustomerNo(res.getCustomerNo());
        addressBookEntity.setAddressBookApproveFlagPdr(EnumTypeUtil.getEnumFromValue(HTypeAddressBookApproveFlag.class,
                                                                                     res.getAddressBookApproveFlag()
                                                                                    ));
        return addressBookEntity;
    }

    /**
     * アドレス帳情報登録リクエスト
     *
     * @param addressBookEntity アドレス帳クラス
     * @return アドレス帳情報登録リクエスト
     */
    public AddressBookRegistRequest toAddressBookRegistRequest(AddressBookEntity addressBookEntity) {
        if (addressBookEntity == null) {
            return null;
        }
        AddressBookRegistRequest addressBookRegistRequest = new AddressBookRegistRequest();
        addressBookRegistRequest.setAddressBookSeq(addressBookEntity.getAddressBookSeq());
        addressBookRegistRequest.setMemberInfoSeq(addressBookEntity.getMemberInfoSeq());
        addressBookRegistRequest.setAddressBookName(addressBookEntity.getAddressBookName());
        addressBookRegistRequest.setAddressBookFirstName(addressBookEntity.getAddressBookFirstName());
        addressBookRegistRequest.setAddressBookLastName(addressBookEntity.getAddressBookLastName());
        addressBookRegistRequest.setAddressBookFirstKana(addressBookEntity.getAddressBookFirstKana());
        addressBookRegistRequest.setAddressBookLastKana(addressBookEntity.getAddressBookLastKana());
        addressBookRegistRequest.setAddressBookZipCode(addressBookEntity.getAddressBookZipCode());
        addressBookRegistRequest.setAddressBookPrefecture(addressBookEntity.getAddressBookPrefecture());
        addressBookRegistRequest.setAddressBookAddress1(addressBookEntity.getAddressBookAddress1());
        addressBookRegistRequest.setAddressBookAddress2(addressBookEntity.getAddressBookAddress2());
        addressBookRegistRequest.setAddressBookAddress3(addressBookEntity.getAddressBookAddress3());
        addressBookRegistRequest.setAddressBookAddress4(addressBookEntity.getAddressBookAddress4());
        addressBookRegistRequest.setAddressBookAddress5(addressBookEntity.getAddressBookAddress5());
        addressBookRegistRequest.setAddressBookTel(addressBookEntity.getAddressBookTel());
        addressBookRegistRequest.setRegistTime(addressBookEntity.getRegistTime());
        addressBookRegistRequest.setUpdateTime(addressBookEntity.getUpdateTime());
        addressBookRegistRequest.setCustomerNo(addressBookEntity.getCustomerNo());
        if (addressBookEntity.getAddressBookApproveFlagPdr() != null) {
            addressBookRegistRequest.setAddressBookApproveFlagPdr(
                            addressBookEntity.getAddressBookApproveFlagPdr().getValue());
        }
        return addressBookRegistRequest;
    }

    /**
     * アドレス帳情報更新リクエスト
     *
     * @param entity アドレス帳クラス
     * @return アドレス帳情報更新リクエスト
     */
    public AddressBookUpdateRequest toAddressBookUpdateRequest(AddressBookEntity entity) {
        if (entity == null) {
            return null;
        }
        AddressBookUpdateRequest addressBookUpdateRequest = new AddressBookUpdateRequest();
        addressBookUpdateRequest.setAddressBookSeq(entity.getAddressBookSeq());
        addressBookUpdateRequest.setMemberInfoSeq(entity.getMemberInfoSeq());
        addressBookUpdateRequest.setAddressBookName(entity.getAddressBookName());
        addressBookUpdateRequest.setAddressBookFirstName(entity.getAddressBookFirstName());
        addressBookUpdateRequest.setAddressBookLastName(entity.getAddressBookLastName());
        addressBookUpdateRequest.setAddressBookFirstKana(entity.getAddressBookFirstKana());
        addressBookUpdateRequest.setAddressBookLastKana(entity.getAddressBookLastKana());
        addressBookUpdateRequest.setAddressBookZipCode(entity.getAddressBookZipCode());
        addressBookUpdateRequest.setAddressBookPrefecture(entity.getAddressBookPrefecture());
        addressBookUpdateRequest.setAddressBookAddress1(entity.getAddressBookAddress1());
        addressBookUpdateRequest.setAddressBookAddress2(entity.getAddressBookAddress2());
        addressBookUpdateRequest.setAddressBookAddress3(entity.getAddressBookAddress3());
        addressBookUpdateRequest.setAddressBookAddress4(entity.getAddressBookAddress4());
        addressBookUpdateRequest.setAddressBookAddress5(entity.getAddressBookAddress5());
        addressBookUpdateRequest.setAddressBookTel(entity.getAddressBookTel());
        addressBookUpdateRequest.setRegistTime(entity.getRegistTime());
        addressBookUpdateRequest.setUpdateTime(entity.getUpdateTime());
        addressBookUpdateRequest.setCustomerNo(entity.getCustomerNo());
        if (entity.getAddressBookApproveFlagPdr() != null) {
            addressBookUpdateRequest.setAddressBookApproveFlagPdr(entity.getAddressBookApproveFlagPdr().getValue());
        }
        return addressBookUpdateRequest;
    }
}
