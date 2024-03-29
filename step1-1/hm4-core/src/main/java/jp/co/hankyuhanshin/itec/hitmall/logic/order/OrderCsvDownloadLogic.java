package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderCSVDto;

import java.util.List;
import java.util.stream.Stream;

/**
 * 受注CSV情報取得Logicインターフェース
 * <p>
 * Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
public interface OrderCsvDownloadLogic {

    Stream<OrderCSVDto> execute(List<Integer> orderSeqList, Integer shopSeq);

}
