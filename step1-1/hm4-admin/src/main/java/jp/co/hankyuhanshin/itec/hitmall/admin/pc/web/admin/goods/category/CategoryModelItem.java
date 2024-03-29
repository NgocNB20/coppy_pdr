/*
 * Project Name : HIT-MALL4
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteMapFlag;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * カテゴリ管理：カテゴリ一覧
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */

@Data
@Component
@Scope("prototype")
public class CategoryModelItem implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 更新可能な行であるかどうか(TOPを除くため)
     */
    private boolean modify;

    /**
     * カテゴリID
     */
    private String categoryId;

    /**
     * カテゴリ名
     */
    private String categoryName;

    /**
     * カテゴリレベル
     */
    private Integer categoryLevel;

    /**
     * カテゴリパス
     */
    private String categoryPath;

    /**
     * カテゴリSEQパス
     */
    private String categorySeqPath;

    /**
     * カテゴリ表示順
     */
    private Integer orderDisplay;

    /**
     * カテゴリ公開状態PC
     */
    private HTypeOpenStatus categoryOpenStatusPC;

    /**
     * カテゴリ公開状態MB
     */
    private HTypeOpenStatus categoryOpenStatusMB;

    /**
     * カテゴリPC商品公開件数
     */
    private Integer categoryOpenGoodsCountPC;

    /**
     * カテゴリMB商品公開件数
     */
    private Integer categoryOpenGoodsCountMB;

    /**
     * オープンしているかどうか
     */
    private boolean openFlg;

    /**
     * 最下位フラグ(子がいないカテゴリ)
     */
    private boolean lowest;

    /**
     * レベル表示
     */
    private String levelView;

    /**
     * サイトマップ出力フラグ
     */
    private HTypeSiteMapFlag siteMapFlag;

    /**
     * 更新カウンタ
     */
    private Integer versionNo;

    /**
     * 全てカテゴリーを操作する開閉ボタンの表示/非表示フラグ
     */
    private boolean allOpenClose;

    /**
     * カテゴリー表示(スタイル)
     */
    private String categoryViewStyle;

}
