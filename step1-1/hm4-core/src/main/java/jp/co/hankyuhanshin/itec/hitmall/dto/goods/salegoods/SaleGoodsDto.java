package jp.co.hankyuhanshin.itec.hitmall.dto.goods.salegoods;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeFavoriteSaleStatus;
import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * セール商品Dtoクラス
 *
 * @author DtoGenerator
 */
@Entity
@Data
@Component
@Scope("prototype")
public class SaleGoodsDto {
    /**
     * 商品SEQ（必須）
     */
    private Integer goodsSeq;

    /**
     * セール状態（必須）
     */
    private HTypeFavoriteSaleStatus saleStatus;

    /**
     * 前回セールコード
     */
    private String preSaleCd;

    /**
     * セールコード
     */
    private String saleCd;

    /**
     * セール期間From
     */
    private Timestamp saleFrom;

    /**
     * セール期間To
     */
    private Timestamp saleTo;
}
