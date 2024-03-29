/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailDeliveryStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReStockStatus;
import jp.co.hankyuhanshin.itec.hmbase.dto.AbstractConditionDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

/**
 * 入荷お知らせ商品Dao用検索条件（管理機能）Dtoクラス
 *
 * @author DtoGenerator
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Component
@Scope("prototype")
public class ReStockSearchForBackDaoConditionDto extends AbstractConditionDto {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品管理番号
     */
    private String goodsGroupCode;

    /**
     * 商品番号
     */
    private String goodsCode;

    /**
     * 商品名
     */
    private String goodsName;

    /**
     * 顧客番号
     */
    private Integer customerNo;

    /**
     * 入荷状態
     */
    private HTypeReStockStatus reStockStatus;

    /**
     * 配信ID
     */
    private String deliveryId;

    /**
     * 入荷状態リスト
     */
    private List<String> reStockStatusList;

    /**
     * 入荷お知らせメール送信状況
     */
    private HTypeMailDeliveryStatus deliveryStatus;

    /**
     * 入荷お知らせメール送信状況リスト
     */
    private List<String> deliveryStatusList;

    /**
     * 入荷日時From
     */
    private Timestamp reStockTimeFrom;

    /**
     * 入荷日時To
     */
    private Timestamp reStockTimeTo;

    /**
     * 複数商品番号検索コード
     */
    private List<String> multiCodeList;

}
