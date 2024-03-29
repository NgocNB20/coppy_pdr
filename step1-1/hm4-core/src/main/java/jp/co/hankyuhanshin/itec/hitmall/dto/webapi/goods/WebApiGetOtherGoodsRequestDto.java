// 2023-renew No92 from here
package jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods;

import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiRequestDto;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * PDR#015 12_WebAPI<br/>
 *
 * <pre>
 * WEB-API連携リクエストDTOクラス
 * 後継品代替品情報
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class WebApiGetOtherGoodsRequestDto extends AbstractWebApiRequestDto {
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 申込商品 (複数パイプ区切り) */
    private String goodsCode;
}
// 2023-renew No92 to here
