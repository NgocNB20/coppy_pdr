/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.icon.registupdate;

import jp.co.hankyuhanshin.itec.hitmall.dto.icon.GoodsInformationIconDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsInformationIconEntity;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.stereotype.Component;

@Component
public class IconRegistUpdateHelper {

    /**
     * 画面表示・再表示<br/>
     * 初期表示情報をページクラスにセット<br />
     *
     * @param iconRegistUpdateModel ページ
     * @param iconDto               アイコンDto
     */
    public void initGoodsInformationIconDto(IconRegistUpdateModel iconRegistUpdateModel,
                                            GoodsInformationIconDto iconDto) {
        // セットするアイコンDtoが null の場合、作成
        if (iconDto == null) {
            iconDto = ApplicationContextUtility.getBean(GoodsInformationIconDto.class);

            // 各エンティティ初期セット
            GoodsInformationIconEntity iconEntity = ApplicationContextUtility.getBean(GoodsInformationIconEntity.class);
            iconDto.setGoodsInformationIconEntity(iconEntity);

        }
        // ページに反映
        iconRegistUpdateModel.setGoodsInformationIconDto(iconDto);

    }

    /**
     * 初期処理時の画面反映
     *
     * @param iconRegistUpdateModel アイコン登録更新画面
     */
    public void toPageForLoad(IconRegistUpdateModel iconRegistUpdateModel) {

        iconRegistUpdateModel.setIconSeq(iconRegistUpdateModel.getGoodsInformationIconDto()
                                                              .getGoodsInformationIconEntity()
                                                              .getIconSeq());

        // アイコン名が未設定であれば
        if (iconRegistUpdateModel.getIconName() == null) {
            // dtoから反映
            iconRegistUpdateModel.setIconName(iconRegistUpdateModel.getGoodsInformationIconDto()
                                                                   .getGoodsInformationIconEntity()
                                                                   .getIconName());
        }

        // カラーコードが未設定であれば
        if (iconRegistUpdateModel.getColorCode() == null) {
            // dtoから反映
            iconRegistUpdateModel.setColorCode(iconRegistUpdateModel.getGoodsInformationIconDto()
                                                                    .getGoodsInformationIconEntity()
                                                                    .getColorCode());
        }

        iconRegistUpdateModel.setNormality(true);

    }

    /**
     * 初期化
     *
     * @param iconRegistUpdateModel アイコン登録更新画面
     */
    public void init(IconRegistUpdateModel iconRegistUpdateModel) {
        iconRegistUpdateModel.setIconSeq(null);
        iconRegistUpdateModel.setIconName(null);
        iconRegistUpdateModel.setColorCode(null);
        iconRegistUpdateModel.setInputingFlg(false);
    }

    /**
     * 登録ボタン押下時の反映処理
     *
     * @param iconRegistUpdateModel 登録更新確認画面
     */
    public void toIconDtoForRegist(IconRegistUpdateModel iconRegistUpdateModel) {

        // アイコン名
        iconRegistUpdateModel.getGoodsInformationIconDto()
                             .getGoodsInformationIconEntity()
                             .setIconName(iconRegistUpdateModel.getIconName());

        // カラーコード
        iconRegistUpdateModel.getGoodsInformationIconDto()
                             .getGoodsInformationIconEntity()
                             .setColorCode(iconRegistUpdateModel.getColorCode());

    }
}
