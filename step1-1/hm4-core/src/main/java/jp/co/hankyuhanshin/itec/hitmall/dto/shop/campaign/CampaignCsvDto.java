/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.shop.campaign;

import jp.co.hankyuhanshin.itec.hitmall.annotation.csv.CsvColumn;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVBothSideSpace;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVNumber;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.SpaceValidateMode;
import jp.co.hankyuhanshin.itec.hmbase.constant.RegularExpressionsConstants;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;
import org.seasar.doma.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * CSV ダウンロード・アップロード用の専用Dto
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 *
 */
@Entity
@Data
@Component
@Scope("prototype")
public class CampaignCsvDto implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** 正規表現エラー:ASC000411W */
    public static final String MSGCD_REGULAR_EXPRESSION_ERR = "{ASC000411W}";

    /** キャンペーンコード */
    @CsvColumn(order = 10, columnLabel = "広告コード")
    @NotEmpty
    @Pattern(regexp = RegularExpressionsConstants.CAMPAIGN_CODE_REGEX, message = MSGCD_REGULAR_EXPRESSION_ERR)
    @Length(max = 12)
    private String campaignCode;

    /** キャンペーン名 */
    @CsvColumn(order = 20, columnLabel = "広告名")
    @NotEmpty
    @Length(max = 80)
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE)
    @HVSpecialCharacter(allowPunctuation = true)
    private String campaignName;

    /** 備考 */
    @CsvColumn(order = 30, columnLabel = "メモ")
    @Length(max = 200)
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE)
    @HVSpecialCharacter(allowPunctuation = true)
    private String note;

    /** キャンペーンコスト */
    @CsvColumn(order = 40, columnLabel = "広告コスト（税込）")
    @HVNumber
    @Digits(integer = 8, fraction = 0)
    private BigDecimal campaignCost;

    /** リダイレクト先url */
    @CsvColumn(order = 50, columnLabel = "リダイレクトURL")
    @URL
    @NotEmpty
    @Length(max = 200)
    private String redirectUrl;

}
