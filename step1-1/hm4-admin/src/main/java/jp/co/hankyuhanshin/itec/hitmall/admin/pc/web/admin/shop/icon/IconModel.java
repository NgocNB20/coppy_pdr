/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.icon;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.dto.icon.GoodsInformationIconDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = false)
@Data
public class IconModel extends AbstractModel {

    /**
     * コンストラクタ<br/>
     * 初期値の設定<br/>
     */
    public IconModel() {
        super();
    }

    /**
     * 検索一覧<br/>
     */
    private List<IconModelItem> resultItems;

    /**
     * 検索結果のマップ<アイコンSEQ, アイコンDTO>
     */
    private Map<Integer, GoodsInformationIconDto> iconDtoMap;

    /**
     * 検索一覧のインデックス
     */
    private int resultIndex;

    /**
     * 表示順
     */
    @NotNull(message = "移動するアイコン を選択してください。")
    private Integer orderDisplay;

    /************************************
     **  条件判定
     ************************************/
    /**
     * 検索結果表示判定<br/>
     *
     * @return true=検索結果がnull以外(0件リスト含む), false=検索結果がnull
     */
    public boolean isResult() {
        return getResultItems() != null;
    }
}
