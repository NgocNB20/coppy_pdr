/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.icon.registupdate;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.dto.icon.GoodsInformationIconDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsInformationIconEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
public class IconRegistUpdateModel extends AbstractModel {

    /**
     * メッセージコード：不正操作
     */
    protected static final String MSGCD_ILLEGAL_OPERATION = "ASM000202";

    /**
     * アップロードCSVファイル削除失敗メッセージ
     */
    public static final String MSGCD_FAIL_DELETE = "ASM000203";

    /**
     * コンストラクタ<br/>
     * 初期値の設定<br/>
     */
    public IconRegistUpdateModel() {
        super();
        this.inputingFlg = false;
    }

    /**
     * 変更前商品インフォメーションアイコンエンティティ
     */
    private GoodsInformationIconEntity originalIconEntity;

    /**
     * 変更箇所リスト
     */
    private List<String> modifiedList;

    /**
     * 処理モード<br/>
     * 新規登録時の先頭画面でmd=newのパラメータを受け取る。<br/>
     */
    private String md;

    /**
     * アイコンSEQ
     */
    private Integer iconSeq;

    /**
     * アイコンSEQ(画面用)
     */
    private Integer scIconSeq;

    /**
     * アイコン名
     */
    @HVSpecialCharacter(allowPunctuation = true)
    @NotEmpty
    @Length(min = 1, max = 60)
    private String iconName;

    /**
     * カラーコード
     */
    @NotEmpty
    @Length(min = 1, max = 20)
    private String colorCode;

    /**
     * アイコンDto
     */
    private GoodsInformationIconDto goodsInformationIconDto;

    /**
     * 登録更新中フラグ<br/>
     */
    private boolean inputingFlg;

    /**
     * 不正操作を判断するためのフラグ
     */
    private boolean normality;

    /************************************
     **  条件判定
     ************************************/

    public boolean isRegist() {
        return iconSeq == null;
    }
}
