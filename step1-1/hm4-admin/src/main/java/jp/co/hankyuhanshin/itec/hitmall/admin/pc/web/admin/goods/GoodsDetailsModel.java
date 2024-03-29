// CHECKSTYLE:OFF
// ファイルサイズオーバーを許容：単純に項目が多いため。
// CHECKSTYLE:ON
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.registupdate.GoodsCategoryTreeItem;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDentalMonopolySalesFlg;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeExcludeFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsClassType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGroupPriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGroupSalePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeNewIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOutletIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReserveIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSaleIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * 商品管理：商品詳細ページ
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GoodsDetailsModel extends AbstractGoodsRegistUpdateModel {

    /**
     * コンストラクタ<br/>
     * 初期値の設定<br/>
     */
    public GoodsDetailsModel() {
        super();
    }

    /**
     * 再検索フラグ
     */
    private String md;

    /************************************
     ** 削除処理時の退避用
     ************************************/
    /**
     * 公開状態PC<br/>
     */
    private HTypeOpenDeleteStatus oldGoodsOpenStatusPC;

    /************************************
     ** 表示項目（商品基本設定部分）
     ************************************/
    // 2023-renew No64 from here
    /**
     * 商品グループ名（管理用）<br/>
     */
    private String goodsGroupNameAdmin;
    // 2023-renew No64 to here
    /**
     * 商品グループ名<br/>
     */
    private String goodsGroupName;
    // PDR Migrate Customization from here
    //2023-renew No64 from here
    /**
     * 商品グループ名1<br/>
     */
    private String goodsGroupName1;

    /**
     * 商品グループ名1公開開始日時<br/>
     */
    private Timestamp goodsGroupName1OpenStartDate;

    /**
     * 商品グループ名1公開開始日時<br/>
     */
    private Timestamp goodsGroupName1OpenStartTime;

    /**
     * 商品グループ名２<br/>
     */
    private String goodsGroupName2;

    /**
     * 商品グループ名2公開開始日時<br/>
     */
    private Timestamp goodsGroupName2OpenStartDate;

    /**
     * 商品グループ名2公開開始日時<br/>
     */
    private Timestamp goodsGroupName2OpenStartTime;
    //2023-renew No64 to here

    /**
     * 商品販売区分⇒薬品区分
     */
    private HTypeGoodsClassType goodsClassType;

    /**
     * カタログ表示順
     */
    private Integer catalogDisplayOrder;

    /**
     * 歯科専売可否フラグ
     */
    private HTypeDentalMonopolySalesFlg dentalMonopolySalesFlg;
    // PDR Migrate Customization to here
    /**
     * 単価＝商品単価（税抜）<br/>
     */
    private BigDecimal goodsPrice;

    /**
     * 税率
     */
    private BigDecimal taxRate;
    // PDR Migrate Customization from here
    /**
     * シリーズ価格記号表示フラグ
     */
    private HTypeGroupPriceMarkDispFlag groupPriceMarkDispFlag;

    /**
     * シリーズセール価格記号表示フラグ
     */
    private HTypeGroupSalePriceMarkDispFlag groupSalePriceMarkDispFlag;

    /**
     * SALEアイコンフラグ
     */
    private HTypeSaleIconFlag saleIconFlag;

    /**
     * お取りおきアイコンフラグ
     */
    private HTypeReserveIconFlag reserveIconFlag;

    /**
     * NEWアイコンフラグ
     */
    private HTypeNewIconFlag newIconFlag;

    /**
     * SALEアイコン画像パス
     */
    private String imageFilePathSaleIcon;

    /**
     * お取りおきアイコン画像パス
     */
    private String imageFilePathReserveIcon;

    /**
     * NEWアイコン画像パス
     */
    private String imageFilePathNewIcon;
    // PDR Migrate Customization to here
    /**
     * 酒類フラグ<br/>
     */
    private String alcoholFlag;

    /**
     * SNS連携フラグ
     */
    private String snsLinkFlag;

    /**
     * 登録日時<br/>
     */
    private Timestamp registTime;

    /**
     * 更新日時<br/>
     */
    private Timestamp updateTime;

    /**
     * 新着日時<br/>
     */
    private String whatsnewDate;

    /**
     * 個別配送<br/>
     */
    private String individualDeliveryType;

    /**
     * 無料配送<br/>
     */
    private String freeDeliveryFlag;

    /**
     * 公開状態PC<br/>
     */
    private String goodsOpenStatusPC;

    /**
     * 公開開始日PC<br/>
     */
    private Timestamp openStartDatePC;

    /**
     * 公開終了日PC<br/>
     */
    private Timestamp openEndDatePC;

    /**
     * 公開開始時間PC<br/>
     */
    private Timestamp openStartTimePC;

    /**
     * 公開終了時間PC<br/>
     */
    private Timestamp openEndTimePC;

    /************************************
     ** 表示項目(商品グループ在庫状態)
     ************************************/

    /**
     * 商品グループ在庫状態PC（在庫状況更新バッチ実行時点）<br/>
     */
    private String batchUpdateStockStatusPc;

    /**
     * 商品グループ在庫状態PC（リアルタイム）<br/>
     */
    private String realTimeStockStatusPc;

    /************************************
     ** 表示項目（カテゴリ設定部分）
     ************************************/
    /**
     * 登録カテゴリリスト<br/>
     */
    private List<CategoryEntity> registCategoryEntityList;

    /**
     * カテゴリリスト<br/>
     */
    private List<CategoryEntity> categoryEntityList;

    /**
     * 登録カテゴリリスト（画面表示用 改行付き）<br/>
     */
    private String registCategory;

    // 2023-renew searchKeyword-addition from here
    /**
     * 商品検索キーワード<br/>
     */
    private String searchKeyword;
    // 2023-renew searchKeyword-addition to here

    /**
     * metaDescription<br/>
     */
    private String metaDescription;

    /**
     * metaKeyword<br/>
     */
    private String metaKeyword;

    /************************************
     ** 表示項目（商品詳細テキスト設定）
     ************************************/
    /**
     * 商品納期<br/>
     */
    private String deliveryType;

    /**
     * 商品説明01<br/>
     */
    private String goodsNote1;

    //2023-renew No64 from here
    /**
     * 商品説明01公開開始日時<br/>
     */
    private Timestamp goodsNote1OpenStartTimeDate;

    /**
     * 商品説明01公開開始日時<br/>
     */
    private Timestamp goodsNote1OpenStartTimeTime;

    /**
     * 商品説明01sub<br/>
     */
    private String goodsNote1Sub;

    /**
     * 商品説明01sub公開開始日時<br/>
     */
    private Timestamp goodsNote1SubOpenStartTimeDate;

    /**
     * 商品説明01sub公開開始日時<br/>
     */
    private Timestamp goodsNote1SubOpenStartTimeTime;
    //2023-renew No64 to here

    /**
     * 商品説明02<br/>
     */
    private String goodsNote2;

    //2023-renew No64 from here
    /**
     * 商品説明02公開開始日時<br/>
     */
    private Timestamp goodsNote2OpenStartTimeDate;

    /**
     * 商品説明02公開開始日時<br/>
     */
    private Timestamp goodsNote2OpenStartTimeTime;

    /**
     * 商品説明02sub<br/>
     */
    private String goodsNote2Sub;

    /**
     * 商品説明02sub公開開始日時<br/>
     */
    private Timestamp goodsNote2SubOpenStartTimeDate;

    /**
     * 商品説明02sub公開開始日時<br/>
     */
    private Timestamp goodsNote2SubOpenStartTimeTime;
    //2023-renew No64 to here

    /**
     * 商品説明03<br/>
     */
    private String goodsNote3;

    /**
     * 商品説明04<br/>
     */
    private String goodsNote4;

    //2023-renew No64 from here
    /**
     * 商品説明04公開開始日時<br/>
     */
    private Timestamp goodsNote4OpenStartTimeDate;

    /**
     * 商品説明04公開開始日時<br/>
     */
    private Timestamp goodsNote4OpenStartTimeTime;

    /**
     * 商品説明04sub<br/>
     */
    private String goodsNote4Sub;

    /**
     * 商品説明04sub公開開始日時<br/>
     */
    private Timestamp goodsNote4SubOpenStartTimeDate;

    /**
     * 商品説明04sub公開開始日時<br/>
     */
    private Timestamp goodsNote4SubOpenStartTimeTime;
    //2023-renew No64 to here

    /**
     * 商品説明05<br/>
     */
    private String goodsNote5;

    /**
     * 商品説明06<br/>
     */
    private String goodsNote6;

    /**
     * 商品説明07<br/>
     */
    private String goodsNote7;

    /**
     * 商品説明08<br/>
     */
    private String goodsNote8;

    /**
     * 商品説明09<br/>
     */
    private String goodsNote9;

    /**
     * 商品説明10<br/>
     */
    private String goodsNote10;

    //2023-renew No64 from here
    /**
     * 商品説明10公開開始日時<br/>
     */
    private Timestamp goodsNote10OpenStartTimeDate;

    /**
     * 商品説明10公開開始日時<br/>
     */
    private Timestamp goodsNote10OpenStartTimeTime;

    /**
     * 商品説明10sub<br/>
     */
    private String goodsNote10Sub;

    /**
     * 商品説明10sub公開開始日時<br/>
     */
    private Timestamp goodsNote10SubOpenStartTimeDate;

    /**
     * 商品説明10sub公開開始日時<br/>
     */
    private Timestamp goodsNote10SubOpenStartTimeTime;
    //2023-renew No64 to here

    /**
     * 商品説明11<br/>
     */
    private String goodsNote11;

    /**
     * 商品説明12<br/>
     */
    private String goodsNote12;

    /**
     * 商品説明13<br/>
     */
    private String goodsNote13;

    /**
     * 商品説明14<br/>
     */
    private String goodsNote14;

    /**
     * 商品説明15<br/>
     */
    private String goodsNote15;

    /**
     * 商品説明16<br/>
     */
    private String goodsNote16;

    /**
     * 商品説明17<br/>
     */
    private String goodsNote17;

    /**
     * 商品説明18<br/>
     */
    private String goodsNote18;

    /**
     * 商品説明19<br/>
     */
    private String goodsNote19;

    /**
     * 商品説明20<br/>
     */
    private String goodsNote20;

    // 2023-renew No11 from here
    /**
     * 商品説明21<br/>
     */
    private String goodsNote21;
    // 2023-renew No11 to here

    // 2023-renew No0 from here
    /**
     * 商品説明22<br/>
     */
    private String goodsNote22;
    // 2023-renew No0 to here

    /************************************
     ** 表示項目（受注連携設定）
     ************************************/
    /**
     * 受注連携設定01<br/>
     */
    private String orderSetting1;

    /**
     * 受注連携設定02<br/>
     */
    private String orderSetting2;

    /**
     * 受注連携設定03<br/>
     */
    private String orderSetting3;

    /**
     * 受注連携設定04<br/>
     */
    private String orderSetting4;

    /**
     * 受注連携設定05<br/>
     */
    private String orderSetting5;

    /**
     * 受注連携設定06<br/>
     */
    private String orderSetting6;

    /**
     * 受注連携設定07<br/>
     */
    private String orderSetting7;

    /**
     * 受注連携設定08<br/>
     */
    private String orderSetting8;

    /**
     * 受注連携設定09<br/>
     */
    private String orderSetting9;

    /**
     * 受注連携設定10<br/>
     */
    private String orderSetting10;

    /************************************
     ** 表示項目（商品規格部分）
     ************************************/
    /**
     * 規格管理フラグ<br/>
     */
    private String unitManagementFlag;

    /**
     * 規格１表示名<br/>
     */
    private String unitTitle1;

    /**
     * 規格２表示名<br/>
     */
    private String unitTitle2;

    /**
     * 値引きコメント
     */
    private String goodsPreDiscountPrice;

    /************************************
     ** リスト項目（商品規格設定）
     ************************************/
    /**
     * 商品規格リスト<br/>
     */
    private List<GoodsDetailsUnitItem> unitItems;

    /**
     * 商品規格画像リスト<br/>
     */
    private List<GoodsDetailsUnitImageItem> unitItemsImageList;

    /************************************
     ** 表示項目（商品在庫部分）
     ************************************/
    /**
     * 在庫管理フラグ<br/>
     */
    private String stockManagementFlag;

    /************************************
     ** リスト項目（商品在庫部分）
     ************************************/
    /**
     * 商品規格リスト<br/>
     */
    private List<GoodsDetailsStockItem> stockItems;

    /************************************
     ** リスト項目（関連商品部分）
     ************************************/
    /**
     * 関連商品リスト<br/>
     */
    private List<GoodsDetailsRelationItem> relationItems;

    /**
     * 関連商品リスト（空）<br/>
     */
    private List<GoodsDetailsRelationItem> relationNoItems;

    // 2023-renew No92 from here
    /**
     * アウトレットアイコンフラグ<br/>
     */
    private HTypeOutletIconFlag outletIconFlag;

    /**
     * アウトレットアイコン画像パス
     */
    private String imageFilePathOutletIcon;
    // 2023-renew No92 to here

    // 2023-renew No21 from here
    /**
     * よく一緒に購入される商品リスト<br/>
     */
    private List<GoodsTogetherBuyItem> togetherBuyItems;

    /**
     * 除外フラグ
     */
    private HTypeExcludeFlag excludingFlag;
    // 2023-renew No21 to here

    /**
     * 商品ステータス判定<br/>
     * 公開状態が削除の場合は、<br/>
     * 「削除」ボタンを非表示にする<br/>
     *
     * @return false=ステータス「削除」
     */
    public boolean isDeleteGoods() {
        // PC公開状態が「削除」の場合
        if (this.goodsOpenStatusPC.equals(HTypeOpenDeleteStatus.DELETED.getValue())) {
            return false;
        }
        return true;
    }

    /************************************
     ** 商品画像項目 ここから
     ************************************/
    /**
     * 商品画像アイテムリスト
     */
    private List<GoodsDetailsImageItem> detailsPageDetailsImageItems;

    /**
     * 選択した商品Seq
     */
    private Integer selectGoodsSeq;

    /**
     * 商品カテゴリーアイテムリスト
     */
    private List<GoodsCategoryTreeItem> categoryTrees;

    // PDR Migrate Customization from here

    /**
     * SALEアイコン表示判定<br/>
     *
     * @return true:○を表示 false:-を表示
     */
    public boolean isCheckedSaleIcon() {
        return HTypeSaleIconFlag.ON.equals(this.saleIconFlag);
    }

    /**
     * お取りおきアイコン表示判定<br/>
     *
     * @return true:○を表示 false:-を表示
     */
    public boolean isCheckedReserveIcon() {
        return HTypeReserveIconFlag.ON.equals(this.reserveIconFlag);
    }

    /**
     * NEWアイコン表示判定<br/>
     *
     * @return true:○を表示 false:-を表示
     */
    public boolean isCheckedNewIcon() {
        return HTypeNewIconFlag.ON.equals(this.newIconFlag);
    }

    // 2023-renew No92 from here

    /**
     * アウトレットアイコン表示判定<br/>
     *
     * @return 0:非表示 1:表示
     */
    public boolean isCheckedOutletIcon() {
        return HTypeOutletIconFlag.ON.equals(this.outletIconFlag);
    }
    // 2023-renew No92 to here
    // PDR Migrate Customization to here
}
