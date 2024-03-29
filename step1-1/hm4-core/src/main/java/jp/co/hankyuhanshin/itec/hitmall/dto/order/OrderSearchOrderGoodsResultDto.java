package jp.co.hankyuhanshin.itec.hitmall.dto.order;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeIndividualDeliveryType;
import lombok.Data;
import org.seasar.doma.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 受注検索受注商品結果用クラス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Entity
@Data
@Component
@Scope("prototype")
public class OrderSearchOrderGoodsResultDto {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** 商品SEQ */
    private Integer goodsSeq;

    /** 商品管理番号 */
    private String goodsGroupCode;

    /** 商品コード */
    private String goodsCode;

    /** JANコード */
    private String janCode;

    /** 商品名 */
    private String goodsGroupName;

    /** 規格１ */
    private String unitValue1;

    /** 規格２ */
    private String unitValue2;

    /** 商品個別配送種別 */
    private HTypeIndividualDeliveryType individualDeliveryType;

    /** 単価 */
    private BigDecimal goodsPrice;

    /** 商品点数 */
    private Integer goodsCount;

    /** カタログコード */
    private String catalogCode;
}
