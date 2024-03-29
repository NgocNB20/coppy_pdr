// 2023-renew No92 from here
package jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods;

import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiResponseDto;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * PDR#015 12_WebAPI<br/>
 *
 * <pre>
 * WEB-API連携取得結果DTOクラス
 * 後継品代替品情報
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
@Scope("prototype")
@Data
public class WebApiGetOtherGoodsResponseDto extends AbstractWebApiResponseDto {
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 後継品代替品情報 詳細情報 */
    private List<WebApiGetOtherGoodsResponseDetailDto> info;
}
// 2023-renew No92 to here
