// 2023-renew No92 from here
package jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**x
 * PDR#015 12_WebAPI<br/>
 *
 * <pre>
 * WEB-API連携取得結果DTOクラス
 * 後継品代替品情報
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class WebApiGetOtherGoodsResponseDetailDto implements Serializable {
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 商品コード */
    private String goodsCode;

    /** 後継品商品コード */
    private String otherGoodsCode;
}
// 2023-renew No92 to here