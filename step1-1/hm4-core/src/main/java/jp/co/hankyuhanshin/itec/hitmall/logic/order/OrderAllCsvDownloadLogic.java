package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderCSVDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchConditionDto;

import java.util.stream.Stream;

/**
 * 受注CSV情報取得Logicインターフェース
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
public interface OrderAllCsvDownloadLogic {

    /**
     * CSVダウンロードを実行します。<br/>
     *
     * @param parameters ロジックへの引数
     * @return データ出力
     */
    Stream<OrderCSVDto> execute(OrderSearchConditionDto conditionDto);

}
