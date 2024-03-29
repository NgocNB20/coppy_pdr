package jp.co.hankyuhanshin.itec.hitmall.service.goods.goods;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEmotionPriceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUploadType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryGoodsTableLockLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryTableLockLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsTableLockLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupDisplayTableLockLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupImageTableLockLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupTableLockLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.relation.GoodsRelationTableLockLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockSettingTableLockLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvReaderUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvUploadResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.group.GoodsGroupGetService;

/**
 * Logic/Service移行：商品一括アップロード
 * 作成日：2021/03/23
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodsCsvUpLoadAsynchronousServiceTest {

    @Autowired
    GoodsCsvUpLoadAsynchronousService service;

    @MockBean
    CsvReaderUtil csvReaderUtil;

    @MockBean
    GoodsGroupGetService goodsGroupGetService;

    @MockBean
    GoodsGroupTableLockLogic goodsGroupTableLockLogic;

    @MockBean
    CategoryTableLockLogic categoryTableLockLogic;

    @MockBean
    GoodsGroupDisplayTableLockLogic goodsGroupDisplayTableLockLogic;

    @MockBean
    CategoryGoodsTableLockLogic categoryGoodsTableLockLogic;

    @MockBean
    GoodsRelationTableLockLogic goodsRelationTableLockLogic;

    @MockBean
    GoodsGroupImageTableLockLogic goodsGroupImageTableLockLogic;

    @MockBean
    GoodsTableLockLogic goodsTableLockLogic;

    @MockBean
    StockSettingTableLockLogic stockSettingTableLockLogic;

    @Test
    @Order(1)
    public void executeTest() throws IOException {

        // 想定値設定
        Map<String, Object> commonCsvInfoMap = new HashMap<>();
        String goodsGroupCode = "CODE";
        GoodsGroupDto goodsGroupDto = new GoodsGroupDto();

        // モック設定
        doReturn(goodsGroupDto).when(goodsGroupGetService).execute(any(String.class));

        // 試験実行
        GoodsGroupDto actual = ReflectionTestUtils.invokeMethod(service, "goodsGroupInfoGetProcess", commonCsvInfoMap,
                                                                goodsGroupCode
                                                               );

        List<GoodsCsvDto> goodsCsvDtoList = new ArrayList<>();
        GoodsCsvDto goodsCsvDto = new GoodsCsvDto();
        goodsCsvDto.setGoodsCode("goodsCode");
        goodsCsvDto.setEmotionPriceType(HTypeEmotionPriceType.NORMAL);
        goodsCsvDto.setGoodsGroupCode("GoodsGroupCode");
        goodsCsvDtoList.add(goodsCsvDto);
        doReturn(goodsCsvDtoList).when(csvReaderUtil).readCsv(any(), any(), any(), any());
        doNothing().when(goodsGroupTableLockLogic).execute();
        doNothing().when(categoryTableLockLogic).execute();
        doNothing().when(goodsGroupDisplayTableLockLogic).execute();
        doNothing().when(categoryGoodsTableLockLogic).execute();
        doNothing().when(goodsRelationTableLockLogic).execute();
        doNothing().when(goodsGroupImageTableLockLogic).execute();
        doNothing().when(goodsTableLockLogic).execute();
        doNothing().when(stockSettingTableLockLogic).execute();

        File file = Mockito.mock(File.class);
        CsvUploadResult csvUploadResult = null;
        try (MockedStatic<PropertiesUtil> propertiesUtil = Mockito.mockStatic(PropertiesUtil.class)) {
            propertiesUtil.when(() -> PropertiesUtil.getSystemPropertiesValue("goods.relation.amount"))
                          .thenReturn("10");
            csvUploadResult = service.execute(file, HTypeUploadType.REGIST);
        }

        Assertions.assertEquals(0, csvUploadResult.getRecordCount());

        // 戻り値及び呼び出し検証
        verify(goodsGroupGetService, times(2)).execute(any(String.class));
        assertThat(actual).isEqualTo(goodsGroupDto);
    }
}
