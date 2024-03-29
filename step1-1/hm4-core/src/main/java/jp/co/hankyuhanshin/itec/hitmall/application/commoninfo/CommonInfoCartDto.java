package jp.co.hankyuhanshin.itec.hitmall.application.commoninfo;

import lombok.Data;
import org.seasar.doma.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * カート情報Dto
 *
 * @author thang
 */
@Entity
@Data
@Component
@Scope("prototype")
public class CommonInfoCartDto {

    /**
     * 商品合計点数
     */
    BigDecimal cartGoodsSumCount;

    /**
     * 商品合計金額
     */
    BigDecimal cartGoodsSumPrice;
}
