/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.coupon;

import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.AbstractWebApiResponseResultDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiAddCouponResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiAddCouponResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetCouponListResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetCouponListResponseDetailDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.AbstractWebApiResponseResultDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiAddCouponResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiAddCouponResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetCouponListResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order.WebApiGetCouponListResponseDto;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * クーポン一覧 Helper
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
// 2023-renew No24 from here
public class CouponHelper {

    /**
     * 画面表示・再表示
     *
     * @param couponModel クーポン一覧Model
     * @param webApiGetCouponListResponseDetailDtoList 利用可能クーポン一覧取得 詳細情報
     */
    public void toPageForLoad(CouponModel couponModel,
                              List<WebApiGetCouponListResponseDetailDto> webApiGetCouponListResponseDetailDtoList) {

        // 取得済みクーポン一覧
        couponModel.setCouponItemList(new ArrayList<>());
        for (WebApiGetCouponListResponseDetailDto dto : webApiGetCouponListResponseDetailDtoList) {
            CouponModelItem couponModelItem = ApplicationContextUtility.getBean(CouponModelItem.class);
            couponModelItem.setCouponCode(dto.getCouponNo());
            couponModelItem.setCouponConditions(dto.getCouponConditions());
            couponModelItem.setCouponName(dto.getCouponName());
            couponModelItem.setCouponExplain(dto.getCouponExplain());
            couponModel.getCouponItemList().add(couponModelItem);
        }
    }

    /**
     * クーポン取得APIのレスポンスをHM用のDTOクラスに変換
     *
     * @param webApiAddCouponResponse クーポン取得Web-APIレスポンス
     * @return WEB-API連携レスポンスDTO クーポン取得
     */
    public WebApiAddCouponResponseDto toWebApiAddCouponResponseDto(WebApiAddCouponResponse webApiAddCouponResponse) {

        if (ObjectUtils.isEmpty(webApiAddCouponResponse)) {
            return null;
        }

        WebApiAddCouponResponseDto webApiAddCouponResponseDto = new WebApiAddCouponResponseDto();

        webApiAddCouponResponseDto.setResult(toAbstractWebApiResponseResultDto(webApiAddCouponResponse.getResult()));
        webApiAddCouponResponseDto.setInfo(toWebApiAddCouponResponseDetailDtoList(webApiAddCouponResponse.getInfo()));

        return webApiAddCouponResponseDto;
    }

    /**
     * 利用可能クーポン一覧取得APIのレスポンスをHM用のDTOクラスに変換
     *
     * @param webApiGetCouponListResponse 利用可能クーポン一覧取得Web-APIレスポンス
     * @return 利用可能クーポン一覧取得
     */
    public WebApiGetCouponListResponseDto toWebApiGetCouponListResponseDto(WebApiGetCouponListResponse webApiGetCouponListResponse) {

        if (ObjectUtils.isEmpty(webApiGetCouponListResponse)) {
            return null;
        }

        WebApiGetCouponListResponseDto webApiGetCouponListResponseDto = new WebApiGetCouponListResponseDto();

        webApiGetCouponListResponseDto.setResult(
                        toAbstractWebApiResponseResultDto(webApiGetCouponListResponse.getResult()));
        webApiGetCouponListResponseDto.setInfo(
                        toWebApiGetCouponListResponseDetailDtoList(webApiGetCouponListResponse.getInfo()));

        return webApiGetCouponListResponseDto;
    }

    /**
     * WEB-API連携取得結果DTO共通情報クラスに変換
     *
     * @param abstractWebApiResponseResultDtoResponse API連携取得結果DTO共通情報レスポンス
     * @return WEB-API連携取得結果DTO共通情報クラス
     */
    private AbstractWebApiResponseResultDto toAbstractWebApiResponseResultDto(AbstractWebApiResponseResultDtoResponse abstractWebApiResponseResultDtoResponse) {

        if (ObjectUtils.isEmpty(abstractWebApiResponseResultDtoResponse)) {
            return null;
        }

        AbstractWebApiResponseResultDto abstractWebApiResponseResultDto = new AbstractWebApiResponseResultDto();

        abstractWebApiResponseResultDto.setMessage(abstractWebApiResponseResultDtoResponse.getMessage());
        abstractWebApiResponseResultDto.setStatus(abstractWebApiResponseResultDtoResponse.getStatus());

        return abstractWebApiResponseResultDto;
    }

    /**
     * クーポン取得 (詳細情報)リストに変換
     *
     * @param webApiAddCouponResponseDetailDtoResponseList クーポン取得 詳細情報
     * @return クーポン取得 (詳細情報)リスト
     */
    private List<WebApiAddCouponResponseDetailDto> toWebApiAddCouponResponseDetailDtoList(List<WebApiAddCouponResponseDetailDtoResponse> webApiAddCouponResponseDetailDtoResponseList) {

        if (CollectionUtil.isEmpty(webApiAddCouponResponseDetailDtoResponseList)) {
            return new ArrayList<>();
        }

        List<WebApiAddCouponResponseDetailDto> webApiAddCouponResponseDetailDtoList = new ArrayList<>();

        webApiAddCouponResponseDetailDtoResponseList.forEach(item -> {
            WebApiAddCouponResponseDetailDto webApiAddCouponResponseDetailDto = new WebApiAddCouponResponseDetailDto();
            webApiAddCouponResponseDetailDto.setCouponName(item.getCouponName());
            webApiAddCouponResponseDetailDtoList.add(webApiAddCouponResponseDetailDto);
        });

        return webApiAddCouponResponseDetailDtoList;
    }

    /**
     * 利用可能クーポン一覧取得(詳細情報)リストに変換
     *
     * @param webApiGetCouponListResponseDetailDtoResponseList 利用可能クーポン一覧取得 詳細情報
     * @return 利用可能クーポン一覧取得(詳細情報)リスト
     */
    private List<WebApiGetCouponListResponseDetailDto> toWebApiGetCouponListResponseDetailDtoList(List<WebApiGetCouponListResponseDetailDtoResponse> webApiGetCouponListResponseDetailDtoResponseList) {

        if (CollectionUtil.isEmpty(webApiGetCouponListResponseDetailDtoResponseList)) {
            return new ArrayList<>();
        }

        List<WebApiGetCouponListResponseDetailDto> webApiGetCouponListResponseDetailDtoList = new ArrayList<>();

        webApiGetCouponListResponseDetailDtoResponseList.forEach(item -> {
            if (ObjectUtils.isNotEmpty(item.getCouponNo())) {
                WebApiGetCouponListResponseDetailDto webApiGetCouponListResponseDetailDto =
                                new WebApiGetCouponListResponseDetailDto();
                webApiGetCouponListResponseDetailDto.setCouponNo(item.getCouponNo());
                webApiGetCouponListResponseDetailDto.setCouponName(item.getCouponName());
                webApiGetCouponListResponseDetailDto.setCouponConditions(item.getCouponConditions());
                webApiGetCouponListResponseDetailDto.setCouponExplain(item.getCouponExplain());
                webApiGetCouponListResponseDetailDtoList.add(webApiGetCouponListResponseDetailDto);
            }
        });

        return webApiGetCouponListResponseDetailDtoList;
    }

}
// 2023-renew No24 to here
