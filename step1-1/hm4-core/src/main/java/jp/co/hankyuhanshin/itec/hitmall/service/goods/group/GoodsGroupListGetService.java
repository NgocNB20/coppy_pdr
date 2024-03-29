// 2023-renew AddNo5 from here

package jp.co.hankyuhanshin.itec.hitmall.service.goods.group;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;

import java.util.List;

/**
 * 商品グループ情報リスト取得<br/>
 *
 * @author Doan Thang (VTI Japan Co. Ltd)
 */
public interface GoodsGroupListGetService {

    /**
     *  商品グループ情報リスト取得
     *
     * @param goodsGroupCodes 商品グループコードリスト
     * @return 商品グループ情報DTOリスト
     */
    List<GoodsGroupDto> execute(List<String> goodsGroupCodes);

}
// 2023-renew AddNo5 to here
