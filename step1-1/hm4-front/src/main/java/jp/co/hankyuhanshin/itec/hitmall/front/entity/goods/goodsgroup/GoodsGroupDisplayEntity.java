/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeNewIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOutletIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReserveIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSaleIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeUnitManagementFlag;
import lombok.Data;
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
    private Integer goodsGroupSeq;

    /**
     * インフォメーションアイコンPC
     */
    private String informationIconPC;

    /**
     * 商品検索キーワード
     */
    private String searchKeyword;

    /**
     * 商品検索キーワード全角
     */
    private String searchKeywordEm;

    /**
     * 規格管理フラグ（必須）
     */
    private HTypeUnitManagementFlag unitManagementFlag = HTypeUnitManagementFlag.OFF;

    /**
     * 規格タイトル１
     */
    private String unitTitle1;

    /**
     * 規格タイトル２
     */
    private String unitTitle2;

    /**
     * meta-description
     */
    private String metaDescription;

    /**
     * meta-keyword
     */
    private String metaKeyword;

    /**
     * 商品納期
     */
    private String deliveryType;

    /**
     * 商品説明１
     */
    private String goodsNote1;

    //2023-renew No64 from here
    /**
     * 商品詳細1_公開開始日時
     */
    private Timestamp goodsNote1OpenStartTime;

    /**
     * 商品概要2
     */
    private String goodsNote1Sub;

    /**
     * 商品概要2_公開開始日時
     */
    private Timestamp goodsNote1SubOpenStartTime;
    //2023-renew No64 to here

    /**
     * 商品説明２
     */
    private String goodsNote2;

    //2023-renew No64 from here
    /**
     * 特徴_公開開始日時
     */
    private Timestamp goodsNote2OpenStartTime;

    /**
     * 特徴2
     */
    private String goodsNote2Sub;

    /**
     * 特徴2_公開開始日時
     */
    private Timestamp goodsNote2SubOpenStartTime;
    //2023-renew No64 to here

    /**
     * 商品説明３
     */
    private String goodsNote3;

    /**
     * 商品説明４
     */
    private String goodsNote4;

    //2023-renew No64 from here
    /**
     * 注意事項_公開開始日時
     */
    private Timestamp goodsNote4OpenStartTime;

    /**
     * 注意事項2
     */
    private String goodsNote4Sub;

    /**
     * 注意事項2_公開開始日時
     */
    private Timestamp goodsNote4SubOpenStartTime;
    //2023-renew No64 to here

    /**
     * 商品説明５
     */
    private String goodsNote5;

    /**
     * 商品説明６
     */
    private String goodsNote6;

    /**
     * 商品説明７
     */
    private String goodsNote7;

    /**
     * 商品説明８
     */
    private String goodsNote8;

    /**
     * 商品説明９
     */
    private String goodsNote9;

    /**
     * 商品説明１０
     */
    private String goodsNote10;

    //2023-renew No64 from here
    /**
     * シリーズPRコメントPC_公開開始日時
     */
    private Timestamp goodsNote10OpenStartTime;

    /**
     * シリーズPRコメントPC2
     */
    private String goodsNote10Sub;

    /**
     * シリーズPRコメントPC2_公開開始日時
     */
    private Timestamp goodsNote10SubOpenStartTime;
    //2023-renew No64 to here

    /**
     * 商品説明１１
     */
    private String goodsNote11;

    /**
     * 商品説明１２
     */
    private String goodsNote12;

    /**
     * 商品説明１３
     */
    private String goodsNote13;

    /**
     * 商品説明１４
     */
    private String goodsNote14;

    /**
     * 商品説明１５
     */
    private String goodsNote15;

    /**
     * 商品説明１６
     */
    private String goodsNote16;

    /**
     * 商品説明１７
     */
    private String goodsNote17;

    /**
     * 商品説明１８
     */
    private String goodsNote18;

    /**
     * 商品説明１９
     */
    private String goodsNote19;

    /**
     * 商品説明２０
     */
    private String goodsNote20;

    // 2023-renew No11 from here
    /**
     * 商品説明２１
     */
    private String goodsNote21;
    // 2023-renew No11 to here

    // 2023-renew No0 from here
    /**
     * 商品説明２２
     */
    private String goodsNote22;
    // 2023-renew No0 to here

    /**
     * 受注連携設定１
     */
    private String orderSetting1;

    /**
     * 受注連携設定２
     */
    private String orderSetting2;

    /**
     * 受注連携設定３
     */
    private String orderSetting3;

    /**
     * 受注連携設定４
     */
    private String orderSetting4;

    /**
     * 受注連携設定５
     */
    private String orderSetting5;

    /**
     * 受注連携設定６
     */
    private String orderSetting6;

    /**
     * 受注連携設定７
     */
    private String orderSetting7;

    /**
     * 受注連携設定８
     */
    private String orderSetting8;

    /**
     * 受注連携設定９
     */
    private String orderSetting9;

    /**
     * 受注連携設定１０
     */
    private String orderSetting10;

    /**
     * 登録日時（必須）
     */
    private Timestamp registTime;

    /**
     * 更新日時（必須）
     */
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
    private HTypeSaleIconFlag saleIconFlag;

    /**
     * お取りおきアイコンフラグ<br/>
     */
    private HTypeReserveIconFlag reserveIconFlag;

    /**
     * NEWアイコンフラグ<br/>
     */
    private HTypeNewIconFlag newIconFlag;

    // 2023-renew No92 from here
    /**
     * アウトレットアイコンフラグ
     */
    private HTypeOutletIconFlag outletIconFlag;
    // 2023-renew No92 to here
    // PDR Migrate Customization to here
}
