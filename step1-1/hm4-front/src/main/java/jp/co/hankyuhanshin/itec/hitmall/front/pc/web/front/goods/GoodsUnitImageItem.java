package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 製品標準部品イメージ Item
 */
@Data
@Component
@Scope("prototype")
public class GoodsUnitImageItem {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品コード
     */
    private String goodsCode;

    /**
     * 製品標準部品イメージ名
     */
    private String goodsUnitImageName;

    /**
     * 商品画像パス
     */
    private String imagePath;
}
