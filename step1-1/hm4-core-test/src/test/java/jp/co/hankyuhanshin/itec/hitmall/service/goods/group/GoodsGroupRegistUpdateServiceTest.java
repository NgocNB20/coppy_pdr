package jp.co.hankyuhanshin.itec.hitmall.service.goods.group;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryGoodsRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.*;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.relation.GoodsRelationRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockSettingRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.impl.StockRegistLogicImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupImageRegistUpdateDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsRelationEntity;

/**
 * Logic/Service移行：商品グループ登録更新
 * 作成日：2021/03/23
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodsGroupRegistUpdateServiceTest {

    @Autowired
    GoodsGroupRegistUpdateService service;

    @MockBean
    private GoodsGroupDataCheckLogic goodsGroupDataCheckLogic;

    @MockBean
    private GoodsGroupRegistLogic goodsGroupRegistLogic;

    @MockBean
    private GoodsGroupDisplayRegistLogic goodsGroupDisplayRegistLogic;

    @MockBean
    private GoodsGroupPopularityRegistLogic goodsGroupPopularityRegistLogic;

    @MockBean
    private CategoryGoodsRegistLogic categoryGoodsRegistLogic;

    @MockBean
    private GoodsRelationRegistLogic goodsRelationRegistLogic;

    @MockBean
    private GoodsGroupImageRegistDataOnlyLogic goodsGroupImageRegistDataOnlyLogic;

    @MockBean
    private GoodsRegistLogic goodsRegistLogic;

    @MockBean
    StockSettingRegistLogic stockSettingRegistLogic;

    @MockBean
    private StockRegistLogicImpl stockRegistLogicImpl;

    @SuppressWarnings("unchecked")
    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        GoodsEntity goodsEntity = new GoodsEntity();
        GoodsDto goodsDto = new GoodsDto();
        goodsDto.setGoodsEntity(goodsEntity);
        List<GoodsDto> listGoodsDto = new ArrayList<GoodsDto>();
        listGoodsDto.add(goodsDto);
        GoodsGroupImageEntity goodsGroupImageEntity = new GoodsGroupImageEntity();
        List<GoodsGroupImageEntity> listGoodsGroupImageEntity = new ArrayList<>();
        listGoodsGroupImageEntity.add(goodsGroupImageEntity);
        GoodsGroupDisplayEntity goodsGroupDisplayEntity = new GoodsGroupDisplayEntity();
        GoodsGroupEntity goodsGroupEntity = new GoodsGroupEntity();
        goodsGroupEntity.setGoodsGroupSeq(1);
        GoodsGroupDto inputGoodsGroupDto = new GoodsGroupDto();
        inputGoodsGroupDto.setGoodsGroupEntity(goodsGroupEntity);
        inputGoodsGroupDto.setGoodsGroupDisplayEntity(goodsGroupDisplayEntity);
        inputGoodsGroupDto.setGoodsGroupImageEntityList(listGoodsGroupImageEntity);
        inputGoodsGroupDto.setGoodsDtoList(listGoodsDto);
        List<GoodsRelationEntity> inputGoodsRelationEntityList = new ArrayList<>();
        List<GoodsGroupImageRegistUpdateDto> inputGoodsGroupImageRegistUpdateDtoList = new ArrayList<>();
        int processType = 1;

        // モック設定
        doNothing().when(goodsGroupDataCheckLogic)
                   .execute(any(GoodsGroupDto.class), any(List.class), any(Integer.class));

        try {

            // 試験実行
            service.execute(
                            inputGoodsGroupDto, inputGoodsRelationEntityList, inputGoodsGroupImageRegistUpdateDtoList,
                            processType
                           );
            fail("例外がスローされなかった。");
        } catch (Exception e) {

            assertThat(e).isInstanceOf(RuntimeException.class);
            assertThat(e.getMessage()).isEqualTo("goodsGroupCode");
        }

        // 戻り値及び呼び出し検証
        verify(goodsGroupDataCheckLogic, times(1)).execute(
                        any(GoodsGroupDto.class), any(List.class), any(Integer.class));
    }

    @Test
    @Order(2)
    public void executeTest_no2() {

        //   stockRegistLogic = (StockRegistLogic) mock(AbstractShopLogic.class);
        // 想定値設定
        GoodsEntity goodsEntity = new GoodsEntity();
        goodsEntity.setGoodsSeq(1);
        GoodsDto goodsDto = new GoodsDto();
        goodsDto.setGoodsEntity(goodsEntity);
        StockDto stockDto = new StockDto();
        goodsDto.setStockDto(stockDto);
        List<GoodsDto> listGoodsDto = new ArrayList<GoodsDto>();
        listGoodsDto.add(goodsDto);
        GoodsGroupImageEntity goodsGroupImageEntity = new GoodsGroupImageEntity();
        List<GoodsGroupImageEntity> listGoodsGroupImageEntity = new ArrayList<>();
        listGoodsGroupImageEntity.add(goodsGroupImageEntity);
        GoodsGroupDisplayEntity goodsGroupDisplayEntity = new GoodsGroupDisplayEntity();
        GoodsGroupEntity goodsGroupEntity = new GoodsGroupEntity();
        // goodsGroupEntity.setGoodsGroupSeq(1);
        GoodsGroupDto inputGoodsGroupDto = new GoodsGroupDto();
        inputGoodsGroupDto.setGoodsGroupEntity(goodsGroupEntity);
        inputGoodsGroupDto.setGoodsGroupDisplayEntity(goodsGroupDisplayEntity);
        inputGoodsGroupDto.setGoodsGroupImageEntityList(listGoodsGroupImageEntity);
        inputGoodsGroupDto.setGoodsDtoList(listGoodsDto);
        List<GoodsRelationEntity> inputGoodsRelationEntityList = new ArrayList<>();
        List<GoodsGroupImageRegistUpdateDto> inputGoodsGroupImageRegistUpdateDtoList = new ArrayList<>();
        int processType = 1;

        // モック設定
        doNothing().when(goodsGroupDataCheckLogic)
                   .execute(any(GoodsGroupDto.class), any(List.class), any(Integer.class));
        doReturn(1).when(goodsGroupRegistLogic).execute(any());
        doReturn(1).when(goodsGroupDisplayRegistLogic).execute(any());
        doReturn(1).when(goodsGroupPopularityRegistLogic).execute(any());

        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put(CategoryGoodsRegistLogic.CATEGORY_GOODS_REGIST, 1);
        resultMap.put(CategoryGoodsRegistLogic.CATEGORY_GOODS_DELETE, 0);

        resultMap.put(GoodsRelationRegistLogic.GOODS_RELATION_REGIST, 1);
        resultMap.put(GoodsRelationRegistLogic.GOODS_RELATION_UPDATE, 0);
        resultMap.put(GoodsRelationRegistLogic.GOODS_RELATION_DELETE, 0);

        resultMap.put(GoodsRegistLogic.GOODS_REGIST, 1);
        resultMap.put(GoodsRegistLogic.GOODS_UPDATE, 0);

        resultMap.put(StockSettingRegistLogic.STOCK_SETTING_REGIST, 1);
        resultMap.put(StockSettingRegistLogic.STOCK_SETTING_UPDATE, 0);

        Map<String, Object> resultMap2 = new HashMap<>();
        resultMap2.put(GoodsGroupImageRegistLogic.GOODS_GROUP_IMAGE_REGIST, 1);
        resultMap2.put(GoodsGroupImageRegistLogic.GOODS_GROUP_IMAGE_UPDATE, 0);
        resultMap2.put(GoodsGroupImageRegistLogic.GOODS_GROUP_IMAGE_DELETE, 0);
        resultMap2.put(GoodsGroupImageRegistLogic.DELETE_IMAGE_FILE_PATH_LIST, new ArrayList<>());

        doReturn(resultMap).when(categoryGoodsRegistLogic).execute(any(), any());
        doReturn(resultMap).when(goodsRelationRegistLogic).execute(any(), any());
        doReturn(resultMap2).when(goodsGroupImageRegistDataOnlyLogic).execute(any(), any(), any());

        doReturn(resultMap).when(goodsRegistLogic).execute(any(), any());
        doReturn(resultMap).when(stockSettingRegistLogic).execute(any(), any());

        doNothing().when(stockRegistLogicImpl).setCommonInfo(any());
        doReturn(1).when(stockRegistLogicImpl).execute(any());
        // 試験実行
        Map<String, Object> result = service.execute(inputGoodsGroupDto, inputGoodsRelationEntityList,
                                                     inputGoodsGroupImageRegistUpdateDtoList, processType, true
                                                    );

        Assertions.assertEquals(result.get(CategoryGoodsRegistLogic.CATEGORY_GOODS_REGIST), 1);

    }
}
