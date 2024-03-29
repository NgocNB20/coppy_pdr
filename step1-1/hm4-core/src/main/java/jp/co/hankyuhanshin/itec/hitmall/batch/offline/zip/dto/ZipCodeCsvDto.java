/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.batch.offline.zip.dto;

import jp.co.hankyuhanshin.itec.hitmall.annotation.csv.CsvColumn;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVNumber;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 住所の郵便番号データファイルに対応するDto<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class ZipCodeCsvDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 全国地方公共団体コード
     */
    @NotEmpty
    @Length(min = 1, max = 6)
    @CsvColumn(order = 10)
    private String localCode;

    /**
     * (旧)郵便番号<br/>
     * HVNumberを付与しないのは5桁未満のときにスペースが入るため。
     */
    @NotEmpty
    @Length(min = 5, max = 5)
    @CsvColumn(order = 20)
    private String oldZipCode;

    /**
     * 郵便番号
     */
    @NotEmpty
    @HVNumber
    @Length(min = 7, max = 7)
    @CsvColumn(order = 30)
    private String zipCode;

    /**
     * 都道府県名（カナ）<br/>
     * システム上使用しないため、Windows31Jバリデータは解除する
     */
    @NotEmpty
    @Length(min = 1, max = 10)
    @CsvColumn(order = 40)
    private String prefectureKana;

    /**
     * 市区町村名(カナ)<br/>
     * システム上使用しないため、Windows31Jバリデータは解除する
     */
    @NotEmpty
    @Length(min = 1, max = 30)
    @CsvColumn(order = 50)
    private String cityKana;

    /**
     * 町域名(カナ)<br/>
     * システム上使用しないため、Windows31Jバリデータは解除する
     */
    @NotEmpty
    @Length(min = 1, max = 100)
    @CsvColumn(order = 60)
    private String townKana;

    /**
     * 都道府県名（漢字）<br/>
     * HWindows31JValidator はバッチ内でかける。<br/>
     * （エラーとせずに警告のみ行いたい為）
     */
    @NotEmpty
    @Length(min = 1, max = 4)
    @CsvColumn(order = 70)
    private String prefectureName;

    /**
     * 市区町村名（漢字）<br/>
     * HWindows31JValidator はバッチ内でかける。<br/>
     * （エラーとせずに警告のみ行いたい為）
     */
    @NotEmpty
    @Length(min = 1, max = 20)
    @CsvColumn(order = 80)
    private String cityName;

    /**
     * 町域名（漢字）<br/>
     * HWindows31JValidator はバッチ内でかける。<br/>
     * （エラーとせずに警告のみ行いたい為）
     */
    @NotEmpty
    @Length(min = 1, max = 100)
    @CsvColumn(order = 90)
    private String townName;

    /**
     * 一町域が二以上の郵便番号で表される場合の表示<br/>
     * 0:該当せず<br/>
     * 1:該当<br/>
     */
    @Pattern(regexp = "0|1")
    @CsvColumn(order = 100)
    private String anyZipFlag;

    /**
     * 小字毎に番地が起番されている町域の表示<br/>
     * 0:該当せず<br/>
     * 1:該当<br/>
     */
    @Pattern(regexp = "0|1")
    @CsvColumn(order = 110)
    private String numberFlag1;

    /**
     * 丁目を有する町域の場合の表示<br/>
     * 0:該当せず<br/>
     * 1:該当<br/>
     */
    @Pattern(regexp = "0|1")
    @CsvColumn(order = 120)
    private String numberFlag2;

    /**
     * 一つの郵便番号で二以上の町域を表す場合の表示<br/>
     * 0:該当せず<br/>
     * 1:該当<br/>
     */
    @Pattern(regexp = "0|1")
    @CsvColumn(order = 130)
    private String numberFlag3;

    /**
     * 更新の表示<br/>
     * 0:変更なし<br/>
     * 1:変更あり<br/>
     * 2:廃止<br/>
     */
    @Pattern(regexp = "0|1|2")
    @CsvColumn(order = 140)
    private String updateVisibleType;

    /**
     * 変更理由<br/>
     * 0:変更なし<br/>
     * 1:市政・区政・町政・分区・政令指定都市施行<br/>
     * 2:住居表示の実施<br/>
     * 3:区画整理<br/>
     * 4:郵便区調整等<br/>
     * 5:訂正<br/>
     * 6:廃止<br/>
     */
    @NotEmpty
    @Pattern(regexp = "0|1|2|3|4|5|6")
    @CsvColumn(order = 150)
    private String updateNoteType;

}
