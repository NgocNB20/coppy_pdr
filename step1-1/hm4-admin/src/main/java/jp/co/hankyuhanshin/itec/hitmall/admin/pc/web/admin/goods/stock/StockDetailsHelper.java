/*
 * Project Name : HIT-MALL4
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.stock;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockResultSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.stock.StockResultEntity;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 在庫詳細Helper<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class StockDetailsHelper {

    /**
     * 日付のフォーマット
     */
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    /**
     * 入庫実績リスト取得条件の作成<br/>
     *
     * @param stockDetailsModel 在庫詳細ページ
     * @return 入庫実績Dao用検索条件DTO
     */
    public StockResultSearchForDaoConditionDto toStockResultSearchForDaoConditionDtoForSearch(StockDetailsModel stockDetailsModel) {

        StockResultSearchForDaoConditionDto stockResultSearchForDaoConditionDto =
                        ApplicationContextUtility.getBean(StockResultSearchForDaoConditionDto.class);

        // 入庫実績
        stockResultSearchForDaoConditionDto.setGoodsSeq(stockDetailsModel.getGoodsSeq());

        return stockResultSearchForDaoConditionDto;
    }

    /**
     * 在庫詳細画面編集<br/>
     *
     * @param stockResultEntityList 入庫実績エンティティリスト
     * @param stockDetailsDto       在庫詳細Dto
     * @param stockDetailsModel     在庫詳細ページ
     * @param conditionDto          入庫実績Dao用検索条件Dto
     */
    public void toPageForStockResult(List<StockResultEntity> stockResultEntityList,
                                     StockDetailsDto stockDetailsDto,
                                     StockDetailsModel stockDetailsModel,
                                     StockResultSearchForDaoConditionDto conditionDto) {

        // 入庫実績画面設定
        List<StockDetailsModelItem> resultItemList = new ArrayList<>();
        for (StockResultEntity stockResultEntity : stockResultEntityList) {
            StockDetailsModelItem detailsPageItem = ApplicationContextUtility.getBean(StockDetailsModelItem.class);
            detailsPageItem.setResultSupplementTime(stockResultEntity.getSupplementTime());
            detailsPageItem.setResultPersonSeq(stockResultEntity.getProcessPersonName());
            detailsPageItem.setResultNote(stockResultEntity.getNote());
            detailsPageItem.setResultRealStock(stockResultEntity.getRealStock());
            detailsPageItem.setResultSupplementCount(stockResultEntity.getSupplementCount());
            detailsPageItem.setResultSupplementTimeStr(convertTime(stockResultEntity.getSupplementTime()));
            resultItemList.add(detailsPageItem);
        }
        stockDetailsModel.setStockResultItems(resultItemList);
        stockDetailsModel.setTotalCount(conditionDto.getTotalCount());

        // 商品詳細画面設定
        stockDetailsModel.setGoodsGroupCode(stockDetailsDto.getGoodsGroupCode());
        stockDetailsModel.setGoodsCode(stockDetailsDto.getGoodsCode());
        stockDetailsModel.setUnitTitle1(stockDetailsDto.getUnitTitle1());
        stockDetailsModel.setUnitTitle2(stockDetailsDto.getUnitTitle2());
        stockDetailsModel.setGoodsPreDiscountPrice(stockDetailsDto.getGoodsPreDiscountPrice());
        stockDetailsModel.setUnitValue1(stockDetailsDto.getUnitValue1());
        stockDetailsModel.setUnitValue2(stockDetailsDto.getUnitValue2());
        stockDetailsModel.setPreDiscountPrice(stockDetailsDto.getPreDiscountPrice());
    }

    /**
     * 日付のフォーマット
     *
     * @param ts
     * @return String formattedDate
     */
    private String convertTime(Timestamp ts) {
        return ts.toLocalDateTime().format(FORMATTER);
    }

}
