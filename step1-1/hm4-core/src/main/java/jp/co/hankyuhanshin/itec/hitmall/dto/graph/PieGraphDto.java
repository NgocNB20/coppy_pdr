/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.graph;

import jp.co.hankyuhanshin.itec.hitmall.helper.json.JsonHelper;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.BigDecimalConversionUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 円グラフ表示用Dto
 *
 * @author kk32102
 */
@Data
@Component
@Scope("prototype")
public class PieGraphDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * グラフのタイトル
     */
    private String graphTitle;

    /**
     * グラフのデータリスト
     */
    private ArrayList<PieGraphDataDto> graphDataList;

    /**
     * グラフのデータリスト（画面出力用にJSON形式で保持）
     */
    private String graphDataJson;

    /**
     * グラフデータを追加する
     *
     * @param label データラベル
     * @param value データ値
     */
    public void addGraphData(String label, Object value) {
        if (graphDataList == null) {
            graphDataList = new ArrayList<>();
        }
        PieGraphDataDto graphData = ApplicationContextUtility.getBean(PieGraphDataDto.class);
        graphData.setGraphDataLabel(label);
        graphData.setGraphDataValue(BigDecimalConversionUtil.toBigDecimal(value));
        graphDataList.add(graphData);
    }

    /**
     * グラフのデータリストをJSON形式に変換する
     */
    public void convertGraphDataJson() {
        JsonHelper jsonHelper = ApplicationContextUtility.getBean(JsonHelper.class);
        graphDataJson = jsonHelper.encode(graphDataList);
    }

}
