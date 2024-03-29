/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeNewIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOutletIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReserveIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSaleIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUnitManagementFlag;
import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 商品グループ表示クラス
 *
 * @author EntityGenerator
 * @version $Revision: 1.0 $
 */
@Entity
@Table(name = "GoodsGroupDisplay")
@Data
@Component
@Scope("prototype")
public class GoodsGroupDisplayEntity implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品グループSEQ (FK)（必須）
     */
    @Column(name = "goodsGroupSeq")
    @Id
    private Integer goodsGroupSeq;

    /**
     * インフォメーションアイコンPC
     */
    @Column(name = "informationIconPC")
    private String informationIconPC;

    /**
     * 商品検索キーワード
     */
    @Column(name = "searchKeyword")
    private String searchKeyword;

    /**
     * 商品検索キーワード全角
     */
    @Column(name = "searchKeywordEm")
    private String searchKeywordEm;

    /**
     * 規格管理フラグ（必須）
     */
    @Column(name = "unitManagementFlag")
    private HTypeUnitManagementFlag unitManagementFlag = HTypeUnitManagementFlag.OFF;

    /**
     * 規格タイトル１
     */
    @Column(name = "unitTitle1")
    private String unitTitle1;

    /**
     * 規格タイトル２
     */
    @Column(name = "unitTitle2")
    private String unitTitle2;

    /**
     * meta-description
     */
    @Column(name = "metaDescription")
    private String metaDescription;

    /**
     * meta-keyword
     */
    @Column(name = "metaKeyword")
    private String metaKeyword;

    /**
     * 商品納期
     */
    @Column(name = "deliveryType")
    private String deliveryType;

    /**
     * 商品説明１
     */
    @Column(name = "goodsNote1")
    private String goodsNote1;

    //2023-renew No64 from here
    /**
     * 商品概要_公開開始日時
     */
    @Column(name = "goodsNote1OpenStartTime")
    private Timestamp goodsNote1OpenStartTime;

    /**
     * 商品概要2
     */
    @Column(name = "goodsNote1Sub")
    private String goodsNote1Sub;

    /**
     * 商品概要2_公開開始日時
     */
    @Column(name = "goodsNote1SubOpenStartTime")
    private Timestamp goodsNote1SubOpenStartTime;
    //2023-renew No64 to here

    /**
     * 商品説明２
     */
    @Column(name = "goodsNote2")
    private String goodsNote2;

    //2023-renew No64 from here
    /**
     * 特徴_公開開始日時
     */
    @Column(name = "goodsNote2OpenStartTime")
    private Timestamp goodsNote2OpenStartTime;

    /**
     * 特徴2
     */
    @Column(name = "goodsNote2Sub")
    private String goodsNote2Sub;

    /**
     * 特徴2_公開開始日時
     */
    @Column(name = "goodsNote2SubOpenStartTime")
    private Timestamp goodsNote2SubOpenStartTime;
    //2023-renew No64 to here

    /**
     * 商品説明３
     */
    @Column(name = "goodsNote3")
    private String goodsNote3;

    /**
     * 商品説明４
     */
    @Column(name = "goodsNote4")
    private String goodsNote4;

    //2023-renew No64 from here
    /**
     * 注意事項_公開開始日時
     */
    @Column(name = "goodsNote4OpenStartTime")
    private Timestamp goodsNote4OpenStartTime;

    /**
     * 注意事項2
     */
    @Column(name = "goodsNote4Sub")
    private String goodsNote4Sub;

    /**
     * 注意事項2_公開開始日時
     */
    @Column(name = "goodsNote4SubOpenStartTime")
    private Timestamp goodsNote4SubOpenStartTime;
    //2023-renew No64 to here

    /**
     * 商品説明５
     */
    @Column(name = "goodsNote5")
    private String goodsNote5;

    /**
     * 商品説明６
     */
    @Column(name = "goodsNote6")
    private String goodsNote6;

    /**
     * 商品説明７
     */
    @Column(name = "goodsNote7")
    private String goodsNote7;

    /**
     * 商品説明８
     */
    @Column(name = "goodsNote8")
    private String goodsNote8;

    /**
     * 商品説明９
     */
    @Column(name = "goodsNote9")
    private String goodsNote9;

    /**
     * 商品説明１０
     */
    @Column(name = "goodsNote10")
    private String goodsNote10;

    //2023-renew No64 from here
    /**
     * シリーズPRコメントPC_公開開始日時
     */
    @Column(name = "goodsNote10OpenStartTime")
    private Timestamp goodsNote10OpenStartTime;

    /**
     * シリーズPRコメントPC2
     */
    @Column(name = "goodsNote10Sub")
    private String goodsNote10Sub;

    /**
     * シリーズPRコメントPC2_公開開始日時
     */
    @Column(name = "goodsNote10SubOpenStartTime")
    private Timestamp goodsNote10SubOpenStartTime;
    //2023-renew No64 to here

    /**
     * 商品説明１１
     */
    @Column(name = "goodsNote11")
    private String goodsNote11;

    /**
     * 商品説明１２
     */
    @Column(name = "goodsNote12")
    private String goodsNote12;

    /**
     * 商品説明１３
     */
    @Column(name = "goodsNote13")
    private String goodsNote13;

    /**
     * 商品説明１４
     */
    @Column(name = "goodsNote14")
    private String goodsNote14;

    /**
     * 商品説明１５
     */
    @Column(name = "goodsNote15")
    private String goodsNote15;

    /**
     * 商品説明１６
     */
    @Column(name = "goodsNote16")
    private String goodsNote16;

    /**
     * 商品説明１７
     */
    @Column(name = "goodsNote17")
    private String goodsNote17;

    /**
     * 商品説明１８
     */
    @Column(name = "goodsNote18")
    private String goodsNote18;

    /**
     * 商品説明１９
     */
    @Column(name = "goodsNote19")
    private String goodsNote19;

    /**
     * 商品説明２０
     */
    @Column(name = "goodsNote20")
    private String goodsNote20;

    // 2023-renew No11 from here
    /**
     * 商品説明２１
     */
    @Column(name = "goodsNote21")
    private String goodsNote21;
    // 2023-renew No11 to here

    // 2023-renew No0 from here
    /**
     * 商品説明２２
     */
    @Column(name = "goodsNote22")
    private String goodsNote22;
    // 2023-renew No0 to here

    /**
     * 受注連携設定１
     */
    @Column(name = "orderSetting1")
    private String orderSetting1;

    /**
     * 受注連携設定２
     */
    @Column(name = "orderSetting2")
    private String orderSetting2;

    /**
     * 受注連携設定３
     */
    @Column(name = "orderSetting3")
    private String orderSetting3;

    /**
     * 受注連携設定４
     */
    @Column(name = "orderSetting4")
    private String orderSetting4;

    /**
     * 受注連携設定５
     */
    @Column(name = "orderSetting5")
    private String orderSetting5;

    /**
     * 受注連携設定６
     */
    @Column(name = "orderSetting6")
    private String orderSetting6;

    /**
     * 受注連携設定７
     */
    @Column(name = "orderSetting7")
    private String orderSetting7;

    /**
     * 受注連携設定８
     */
    @Column(name = "orderSetting8")
    private String orderSetting8;

    /**
     * 受注連携設定９
     */
    @Column(name = "orderSetting9")
    private String orderSetting9;

    /**
     * 受注連携設定１０
     */
    @Column(name = "orderSetting10")
    private String orderSetting10;

    /**
     * 登録日時（必須）
     */
    @Column(name = "registTime", updatable = false)
    private Timestamp registTime;

    /**
     * 更新日時（必須）
     */
    @Column(name = "updateTime")
    private Timestamp updateTime;

    // PDR Migrate Customization from here
    //    /**
    //     * PDR#10 07_データ連携（商品情報）新規情報を商品グループ表示情報に追加<br/>
    //     * <pre>
    //     * ・SALEアイコンフラグを追加
    //     * ・お取りおきアイコンフラグを追加
    //     * ・NEWアイコンフラグを追加
    //     * </pre>
    //     *
    //     */
    /**
     * SALEアイコンフラグ<br/>
     */
    @Column(name = "saleIconFlag")
    private HTypeSaleIconFlag saleIconFlag;

    /**
     * お取りおきアイコンフラグ<br/>
     */
    @Column(name = "reserveIconFlag")
    private HTypeReserveIconFlag reserveIconFlag;

    /**
     * NEWアイコンフラグ<br/>
     */
    @Column(name = "newIconFlag")
    private HTypeNewIconFlag newIconFlag;

    // 2023-renew No92 from here
    /**
     * アウトレットアイコンフラグ<br/>
     */
    @Column(name = "outletIconFlag")
    private HTypeOutletIconFlag outletIconFlag;
    // 2023-renew No92 to here
    // PDR Migrate Customization to here
}
