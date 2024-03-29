package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * JS用商品画像 Item
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Data
@Component
@Scope("prototype")
public class JsImageItem {

    /**
     * JS用商品画像
     */
    private String goodsGroupImageForJs;

    /**
     * 画像種別内連番
     */
    private Integer imageTypeVersionNo;

}
