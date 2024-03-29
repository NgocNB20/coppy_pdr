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
 * 事業所の個別郵便番号データファイルに対応するDto<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class OfficeZipCodeCsvDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 大口事業所の所在地のＪＩＳコード
     */
    @NotEmpty
    @Length(min = 1, max = 5)
    @CsvColumn(order = 10)
    private String officeCode;

    /**
     * 大口事業所名（カナ）<br/>
     * システム上使用しないため、Windows31Jバリデータは解除する
     */
    @NotEmpty
    @Length(min = 1, max = 100)
    @CsvColumn(order = 20)
    private String officeKana;

    /**
     * 大口事業所名（漢字）<br/>
     * HWindows31JValidator はバッチ内でかける。<br/>
     * （エラーとせずに警告のみ行いたい為）
     */
    @NotEmpty
    @Length(min = 1, max = 80)
    @CsvColumn(order = 30)
    private String officeName;

    /**
     * 都道府県名（漢字）<br/>
     * HWindows31JValidator はバッチ内でかける。<br/>
     * （エラーとせずに警告のみ行いたい為）
     */
    @NotEmpty
    @Length(min = 1, max = 4)
    @CsvColumn(order = 40)
    private String prefectureName;

    /**
     * 市区町村名（漢字）<br/>
     * HWindows31JValidator はバッチ内でかける。<br/>
     * （エラーとせずに警告のみ行いたい為）
     */
    @NotEmpty
    @Length(min = 1, max = 12)
    @CsvColumn(order = 50)
    private String cityName;

    /**
     * 町域名 <br/>
     * HWindows31JValidator はバッチ内でかける。<br/>
     * （エラーとせずに警告のみ行いたい為）
     */
    @Length(min = 0, max = 12)
    @CsvColumn(order = 60, isConvertToBlank = true)
    private String townName;

    /**
     * 小字名、丁目、番地等（漢字）<br/>
     * HWindows31JValidator はバッチ内でかける。<br/>
     * （エラーとせずに警告のみ行いたい為）
     */
    @Length(min = 0, max = 62)
    @CsvColumn(order = 70, isConvertToBlank = true)
    private String numbers;

    /**
     * 大口事業所個別番号
     */
    @NotEmpty
    @HVNumber
    @Length(min = 7, max = 7)
    @CsvColumn(order = 80)
    private String zipCode;

    /**
     * 旧郵便番号<br/>
     * HVNumberを付与しないのは5桁未満のときにスペースが入るため。
     */
    @NotEmpty
    @Length(min = 5, max = 5)
    @CsvColumn(order = 90)
    private String oldZipCode;

    /**
     * 取扱支店名（漢字）<br/>
     * システム上使用しないため、Windows31Jバリデータは解除する
     */
    @Length(min = 1, max = 20)
    @CsvColumn(order = 100)
    private String handlingBranchName;

    /**
     * 個別番号の種別の表示<br/>
     * 0:大口事業所<br/>
     * 1:私書箱 <br/>
     */
    @Pattern(regexp = "0|1")
    @CsvColumn(order = 110)
    private String individualType;

    /**
     * 複数番号の有無<br/>
     * 0:複数番号無し<br/>
     * 1:複数番号を設定している場合の個別番号の1<br/>
     * 2:複数番号を設定している場合の個別番号の2<br/>
     * 3:複数番号を設定している場合の個別番号の3<br/>
     */
    @Pattern(regexp = "0|1|2|3")
    @CsvColumn(order = 120)
    private String anyZipFlag;

    /**
     * 修正コード<br/>
     * 0:修正なし<br/>
     * 1:新規追加<br/>
     * 5:廃止<br/>
     */
    @NotEmpty
    @Pattern(regexp = "0|1|5")
    @CsvColumn(order = 130)
    private String updateCode;

}
