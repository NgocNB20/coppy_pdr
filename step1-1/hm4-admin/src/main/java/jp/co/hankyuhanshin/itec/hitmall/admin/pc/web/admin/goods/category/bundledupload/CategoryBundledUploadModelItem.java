/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.category.bundledupload;

import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * カテゴリ一括アップロード結果ページアイテムクラス<br/>
 *
 * @author kamei
 */
// 2023-renew categoryCSV from here
@Data
@Component
@Scope("prototype")
public class CategoryBundledUploadModelItem implements Serializable {

    /**
     * シリアルバージョンID<br/>
     */
    private static final long serialVersionUID = 1L;

    /** エラー行 */
    private Integer row;

    /** エラーカラムNo */
    private String columnName;

    /** エラー内容 */
    private String message;

    /**
     * 項目名判定
     *
     * @return true=有、false=無
     */
    public boolean isColumn() {
        return !StringUtil.isEmpty(columnName);
    }
}
// 2023-renew categoryCSV to here
