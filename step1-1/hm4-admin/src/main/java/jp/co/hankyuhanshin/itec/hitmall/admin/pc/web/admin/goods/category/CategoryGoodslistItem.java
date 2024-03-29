/*
 * Project Name : HIT-MALL4
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.category;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * カテゴリ管理
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class CategoryGoodslistItem implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 選択された行
     */
    private int resultIndex;

    /**
     * 連番
     */
    private Integer resultNo;

    /**
     * 商品グループコード
     */
    private String goodsGroupCode;

    /**
     * 商品グループ名
     */
    private String goodsGroupName;

    /**
     * 商品公開状態(PC)
     */
    private String goodsOpenStatusPC;

    /**
     * 商品公開開始日時(PC)
     */
    private Timestamp goodsOpenFromDateTimePC;

    /**
     * 商品公開終了日時(PC)
     */
    private Timestamp goodsOpenToDateTimePC;

    /**
     * 並び順
     */
    private String orderDisplay;
}
