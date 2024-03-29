/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeNewIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOutletIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReserveIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSaleIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendMailPermitFlag;
import lombok.Data;
import org.seasar.doma.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 入荷お知らせメールアドレス帳登録Dtoクラス
 *
 * @author st75001
 */
@Entity
@Data
@Component
@Scope("prototype")
public class ReStockAddImportListDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 会員SEQ
     */
    private Integer memberInfoSeq;

    /**
     * 顧客番号
     */
    private Integer customerNo;

    /**
     * 更新カウンタ
     */
    private Integer versionNo;

    /**
     * メールアドレス
     */
    private String memberInfoMail;

    /**
     * 事業所名
     */
    private String memberInfoLastName;

    /**
     * メール配信希望フラグ
     */
    private HTypeSendMailPermitFlag sendMailPermitFlag;
    /* 商品情報 */
    /**
     * 商品画像
     */
    private String imageFileName;

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
     * アウトレットアイコンフラグ
     */
    private HTypeOutletIconFlag outletIconFlag;

    /**
     * 商品SEQ
     */
    private Integer goodsSeq;

    /**
     * 商品番号
     */
    private String goodsCode;

    /**
     * 商品名
     */
    private String goodsName;

    /**
     * 規格タイトル1
     */
    private String unitTitle1;

    /**
     * 規格値1
     */
    private String unitValue1;

    /**
     * 規格タイトル2
     */
    private String unitTitle2;

    /**
     * 規格値2
     */
    private String unitValue2;

    /**
     * セールコメント
     */
    private String goodsPreDiscountPrice;

    /**
     * 価格（最低）
     */
    private BigDecimal goodsPriceInTaxLow;

    /**
     * 価格（最高）
     */
    private BigDecimal goodsPriceInTaxHight;

    /**
     * セール価格（最低）
     */
    private BigDecimal preDiscountPriceLow;

    /**
     * セール価格（最高）
     */
    private BigDecimal preDiscountPriceHight;

    /**
     * 商品販売状態
     */
    private HTypeGoodsSaleStatus saleStatusPc;

    /**
     * 商品販売開始日
     */
    private Timestamp saleStartTimePc;

    /**
     * 商品販売終了日
     */
    private Timestamp saleEndTimePc;

    /**
     * 商品公開状態
     */
    private HTypeOpenDeleteStatus goodsOpenStatusPc;

    /**
     * 商品公開開始日
     */
    private Timestamp openStartTimePc;

    /**
     * 商品公開終了日
     */
    private Timestamp openEndTimePc;

    /**
     * 入荷お知らせ商品SEQ
     */
    private Integer reStockGoodsSeq;

}
