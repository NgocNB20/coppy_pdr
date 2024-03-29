/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.delivery.sca;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.delivery.validation.group.OnceZipCodeAddGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.delivery.validation.group.ReDisplayGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.delivery.validation.group.ZipCodeSearchGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCHankaku;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCNumber;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVNumber;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVRItems;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeliveryMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hmbase.constant.RegularExpressionsConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Map;

/**
 * 配送方法設定：特別料金エリア設定検索画面用モデル
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EqualsAndHashCode(callSuper = false)
@Data
@HVRItems(target = "prefectureName", comparison = "prefectureNameItems", groups = {ReDisplayGroup.class})
public class DeliveryScaModel extends AbstractModel {

    /************************************
     ** GETパラメータ項目
     ************************************/
    /**
     * 画面表示モード
     */
    private String md;

    /**
     * 配送方法SEQ
     */
    private Integer dmcd;

    /************************************
     ** 一覧／登録-共通表示情報（上部）
     ************************************/
    /**
     * 配送方法名
     */
    private String deliveryMethodName;

    /**
     * 公開状態PC
     */
    private HTypeOpenDeleteStatus openStatusPC = HTypeOpenDeleteStatus.NO_OPEN;

    /**
     * 配送方法種別
     */
    private HTypeDeliveryMethodType deliveryMethodType;

    /************************************
     ** 一覧画面
     ************************************/
    /**
     * エリア(select)
     */
    private String prefectureName;

    /**
     * エリア(select) オプション
     */
    private Map<String, String> prefectureNameItems;

    /**
     * 総件数
     */
    private int totalCount;

    /**
     * 行番号
     */
    private int resultDataIndex;

    /**
     * 検索結果リスト
     */
    private List<DeliveryScaModelItem> resultItems;

    /**
     * ページ番号
     */
    private String pageNumber;

    /**
     * 最大表示件数
     */
    private int limit;

    /**
     * 検索結果表示判定<br/>
     *
     * @return true=検索結果がnull以外(0件リスト含む), false=検索結果がnull
     */
    public boolean isResult() {
        return resultItems != null && !resultItems.isEmpty();
    }

    /************************************
     ** 登録画面
     ************************************/
    /**
     * 郵便番号（必須）
     */
    @NotEmpty(groups = {ZipCodeSearchGroup.class, OnceZipCodeAddGroup.class})
    @Length(min = 1, max = 7, groups = {ZipCodeSearchGroup.class, OnceZipCodeAddGroup.class})
    @Pattern(regexp = RegularExpressionsConstants.ZIP_CODE_REGEX, message = "{HZipCodeValidator.INVALID_detail}",
             groups = {ZipCodeSearchGroup.class, OnceZipCodeAddGroup.class})
    @HCHankaku
    private String zipCode;

    /**
     * 送料
     */
    @HCNumber
    @NotEmpty(groups = {OnceZipCodeAddGroup.class})
    @HVNumber(groups = {OnceZipCodeAddGroup.class})
    @Length(min = 1, max = 8, groups = {OnceZipCodeAddGroup.class})
    @Digits(integer = 10, fraction = 0, groups = {OnceZipCodeAddGroup.class})
    private String carriage;

    /**
     * 住所
     */
    private String address;
}
