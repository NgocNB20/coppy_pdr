/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.category.bundledupload;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVCsvHeader;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVMultipartFile;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUploadType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryCsvDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

/**
 * カテゴリ一括アップロード画面<br/>
 *
 * @author kamei
 */
// 2023-renew categoryCSV from here
@Data
@EqualsAndHashCode(callSuper = false)
public class CategoryBundledUploadModel extends AbstractModel {

    /** アップロード失敗メッセージ */
    public static final String MSGCD_FAIL_DELETE = "AGC000504";

    /**
     * アップロード商品データファイル<br/>
     */
    @HVMultipartFile(fileExtension = {"csv"})
    @HVCsvHeader(csvDtoClass = CategoryCsvDto.class)
    private MultipartFile uploadCsvFile;

    /**
     * アップロード種別<br/>
     */
    @NotEmpty
    private String uploadType;

    /** アップロード種別のコンボボックス */
    private Map<String, String> uploadTypeItems;

    /** エラーリスト */
    private List<CategoryBundledUploadModelItem> errorResultItems;

    /** アップロード件数 */
    private int uploadCount;

    /** バリデーション限度数  */
    private Integer validLimit;

    /** バリデーション限度数で終了したか否かのフラグ  */
    private boolean validLimitFlg;

    /**
     * モード判定<br/>
     *
     * @return true=登録、false=更新
     */
    public boolean isRegist() {
        return HTypeUploadType.REGIST.getValue().equals(uploadType);
    }

    /**
     * エラーメッセージ判定
     *
     * @return true=エラー、false=エラーなし
     */
    public boolean isErrMsgTable() {
        return errorResultItems != null && !errorResultItems.isEmpty();
    }

}
// 2023-renew categoryCSV to here
