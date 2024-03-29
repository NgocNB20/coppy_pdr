// 2023-renew No21 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.registupdate;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeRegisterMethodType;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ZenHanConversionUtility;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 商品管理：商品詳細ページ よく一緒に購入されている商品アイテム<br/>
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class GoodsRegistUpdateTogetherBuyItem {

    /**
     * シリアルバージョンID<br/>
     */
    private static final long serialVersionUID = 1L;

    /**
     * よく一緒に購入されているNo<br/>
     */
    private Integer togetherBuyDspNo;

    /**
     * よく一緒に購入されている商品グループコード<br/>
     */
    private String togetherBuyGoodsGroupCode;

    /**
     * よく一緒に購入されている商品グループ名<br/>
     */
    private String togetherBuyGoodsGroupName;

    /**
     * 登録方法<br/>
     */
    private HTypeRegisterMethodType registmethod;

    /**
     * 表示タイプ
     *
     * @return true=集計バッチ、false=管理画面
     */
    public boolean isBatchRegist() {
        return HTypeRegisterMethodType.BATCH.equals(registmethod);
    }

    /**
     * 全角よく一緒に購入されているNo.
     *
     * @return 全角No
     */
    public String togetherBuyZenkakuNo() {
        // 全角、半角の変換Helper取得
        ZenHanConversionUtility zenHanConversionUtility =
                        ApplicationContextUtility.getBean(ZenHanConversionUtility.class);
        if (isBatchRegist()) {
            int no = togetherBuyDspNo - 20;
            return zenHanConversionUtility.toZenkaku(Integer.toString(no));
        } else {
            return zenHanConversionUtility.toZenkaku(Integer.toString(togetherBuyDspNo));
        }
    }

    /************************************
     ** Onclick属性書き換え
     ************************************/
    /**
     * doDeleteGoodsTogetherBuyGroup<br/>
     *
     * @return OnClick属性
     */
    public String getDoDeleteGoodsTogetherBuyGroupOnClick() {
        return "setSelectGoodsTogetherBuyGroupCode('" + togetherBuyGoodsGroupCode + "');";
    }

    /**
     * doUpGoodsTogetherBuyGroup<br/>
     *
     * @return OnClick属性
     */
    public String getDoUpGoodsTogetherBuyGroupOnClick() {
        return "setSelectGoodsTogetherBuyGroupCode('" + togetherBuyGoodsGroupCode + "');";
    }

    /**
     * doDownGoodsTogetherBuyGroup<br/>
     *
     * @return OnClick属性
     */
    public String getDoDownGoodsTogetherBuyGroupOnClick() {
        return "setSelectGoodsTogetherBuyGroupCode('" + togetherBuyGoodsGroupCode + "');";
    }
}
// 2023-renew No21 to here