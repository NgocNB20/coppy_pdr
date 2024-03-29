/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.annotation.csv.CsvColumn;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVDate;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCategoryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeManualDisplayFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteMapFlag;
import jp.co.hankyuhanshin.itec.hitmall.utility.SupplyDateTimeUtility;
import jp.co.hankyuhanshin.itec.hmbase.constant.ValidatorConstants;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.seasar.doma.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.sql.Timestamp;

import static jp.co.hankyuhanshin.itec.hitmall.utility.CsvUtility.UPLOAD_TIME_FORMAT;
import static jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility.YMD_SLASH_HMS;

/**
 * カテゴリCSVDtoクラス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Entity
@Data
@Component
@Scope("prototype")
public class CategoryCsvDto implements Serializable {

    // PDR Migrate Customization from here
    /** メッセージコード：日付バリデータエラー時に使用 */
    protected static final String NOT_TIME = "{AGC000403W}";

    /** メッセージコード：正規表現エラー時に使用 */
    protected static final String NOT_REGULAR = "{AGC000404W}";

    /** メッセージコード：画像ファイル名エラー時に使用 */
    protected static final String NOT_CORRECT_IMAGE_NAME = "{AGC000501E}";

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /** カテゴリーID */
    @CsvColumn(order = 10, columnLabel = "カテゴリーID")
    @NotEmpty
    @Pattern(regexp = ValidatorConstants.REGEX_CATEGORY_ID, message = NOT_REGULAR)
    @Length(max = ValidatorConstants.LENGTH_CATEGORY_ID_MAXIMUM)
    private String categoryId;

    /** カテゴリー名 */
    @CsvColumn(order = 20, columnLabel = "カテゴリー名")
    @NotEmpty
    @Length(max = 120)
    private String categoryName;

    /** タイプ */
    @CsvColumn(order = 30, columnLabel = "タイプ", enumOutputType = "value")
    private HTypeCategoryType categoryType;

    /** 手動表示フラグ */
    @CsvColumn(order = 40, columnLabel = "手動表示", enumOutputType = "value")
    private HTypeManualDisplayFlag manualDisplayFlag;

    /** カテゴリー設定 */
    @CsvColumn(order = 50, columnLabel = "カテゴリー設定")
    @NotEmpty
    @Pattern(regexp = ValidatorConstants.REGEX_CATEGORY_ID, message = NOT_REGULAR)
    @Length(max = ValidatorConstants.LENGTH_CATEGORY_ID_MAXIMUM)
    private String parentCategoryId;

    /** メタ説明文 */
    @CsvColumn(order = 60, columnLabel = "メタ説明文")
    @Length(max = 400)
    @HVSpecialCharacter(
                    allowCharacters = {'!', '#', '%', '(', ')', '*', '+', ',', '-', '.', '/', ':', ';', '=', '@', '[',
                                    ']', '_', '|'})
    private String metaDescription;

    /** メタキーワード */
    @CsvColumn(order = 70, columnLabel = "メタキーワード")
    @Length(max = 400)
    @HVSpecialCharacter(
                    allowCharacters = {'!', '#', '%', '(', ')', '*', '+', ',', '-', '.', '/', ':', ';', '=', '@', '[',
                                    ']', '_', '|'})
    private String metaKeyword;

    /** カテゴリー表示名PC */
    @CsvColumn(order = 80, columnLabel = "カテゴリー名PC")
    @NotEmpty
    @Length(max = 120)
    private String categoryNamePC;

    /** カテゴリ公開状態PC */
    @CsvColumn(order = 100, columnLabel = "公開状態PC", enumOutputType = "value")
    @NotNull
    private HTypeOpenStatus categoryOpenStatusPC;

    /** カテゴリ公開開始日時PC */
    @CsvColumn(order = 110, columnLabel = "公開開始日時PC", dateFormat = YMD_SLASH_HMS,
               supplyDateType = SupplyDateTimeUtility.TYPE_START)
    @HVDate(pattern = UPLOAD_TIME_FORMAT, message = NOT_TIME)
    private Timestamp categoryOpenStartTimePC;

    /** カテゴリ公開終了日時PC */
    @CsvColumn(order = 120, columnLabel = "公開終了日時PC", dateFormat = YMD_SLASH_HMS,
               supplyDateType = SupplyDateTimeUtility.TYPE_START)
    @HVDate(pattern = UPLOAD_TIME_FORMAT, message = NOT_TIME)
    private Timestamp categoryOpenEndTimePC;

    /** フリーエリア(PC) */
    @CsvColumn(order = 160, columnLabel = "フリーエリアPC")
    @Length(max = 4000)
    @HVSpecialCharacter(allowPunctuation = true)
    private String freeTextPC;

    /** カテゴリ画像PC */
    @CsvColumn(order = 190, columnLabel = "カテゴリー画像PC")
    @Length(max = 100)
    @Pattern(regexp = "^p_.*$", message = NOT_CORRECT_IMAGE_NAME)
    private String categoryImagePC;

    /** サイトマップ出力フラグ */
    @CsvColumn(order = 220, columnLabel = "サイトマップ出力", enumOutputType = "value")
    private HTypeSiteMapFlag siteMapFlag;

    // PDR Migrate Customization to here
}
