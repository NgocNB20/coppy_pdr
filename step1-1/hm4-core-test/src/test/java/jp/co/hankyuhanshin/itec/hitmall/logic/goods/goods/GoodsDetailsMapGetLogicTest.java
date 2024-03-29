package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goods.GoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goods.GoodsImageDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupImageGetLogic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodsDetailsMapGetLogicTest {

    @Autowired
    GoodsDetailsMapGetLogic goodsDetailsMapGetLogic;

    @MockBean
    GoodsDao goodsDao;

    @MockBean
    GoodsImageDao goodsImageDao;

    @MockBean
    GoodsGroupImageGetLogic goodsGroupImageGetLogic;

    @Test
    @Order(1)
    public void execute01() {
        List<Integer> goodsSeqList = new ArrayList<>();
        goodsSeqList.add(999);
        Map<Integer, GoodsDetailsDto> goodsDetailsDtoMap = goodsDetailsMapGetLogic.execute(goodsSeqList);
        Assertions.assertEquals(0, goodsDetailsDtoMap.size());
    }

    @Test
    @Order(1)
    public void execute02() {
        List<String> goodsSeqList = new ArrayList<>();
        goodsSeqList.add("10137467");
        List<GoodsDetailsDto> goodsDetailsDtoList = new ArrayList<>();
        List<GoodsImageEntity> goodsImageEntityList = new ArrayList<>();
        Map<Integer, List<GoodsGroupImageEntity>> map = new HashMap<>();
        List<GoodsGroupImageEntity> goodsGroupImageEntityList = new ArrayList<>();
        GoodsDetailsDto goodsDetailsDto = new GoodsDetailsDto();
        GoodsImageEntity goodsImageEntity = new GoodsImageEntity();
        GoodsGroupImageEntity goodsGroupImageEntity = new GoodsGroupImageEntity();
        goodsDetailsDto.setGoodsGroupSeq(10034116);
        goodsDetailsDto.setGoodsSeq(10137467);
        goodsDetailsDto.setGoodsCode("10137467");
        goodsImageEntity.setGoodsGroupSeq(10034116);
        goodsImageEntity.setGoodsSeq(10137467);
        goodsGroupImageEntity.setGoodsGroupSeq(10034116);
        goodsDetailsDtoList.add(goodsDetailsDto);
        goodsImageEntityList.add(goodsImageEntity);
        goodsGroupImageEntityList.add(goodsGroupImageEntity);
        map.put(10034116, goodsGroupImageEntityList);

        // モック設定
        doReturn(goodsDetailsDtoList).when(goodsDao).getGoodsDetailsListByGoodsCodeList(any());
        doReturn(goodsImageEntityList).when(goodsImageDao).getGoodsImageListByGoodsSeqList(any());
        doReturn(map).when(goodsGroupImageGetLogic).execute(any());

        Map<String, GoodsDetailsDto> goodsDetailsDtoMap = goodsDetailsMapGetLogic.executeByGoodsCode(goodsSeqList);
        Assertions.assertEquals(1, goodsDetailsDtoMap.size());
    }

    @Test
    @Order(1)
    public void execute03() {
        List<String> goodsSeqList = new ArrayList<>();
        goodsSeqList.add("10137467");
        List<GoodsDetailsDto> goodsDetailsDtoList = new ArrayList<>();
        List<GoodsImageEntity> goodsImageEntityList = new ArrayList<>();
        Map<Integer, List<GoodsGroupImageEntity>> map = new HashMap<>();
        List<GoodsGroupImageEntity> goodsGroupImageEntityList = new ArrayList<>();
        GoodsDetailsDto goodsDetailsDto = new GoodsDetailsDto();
        GoodsImageEntity goodsImageEntity = new GoodsImageEntity();
        GoodsGroupImageEntity goodsGroupImageEntity = new GoodsGroupImageEntity();
        goodsDetailsDto.setGoodsGroupSeq(10034116);
        goodsDetailsDto.setGoodsSeq(10137467);
        goodsDetailsDto.setGoodsCode("10137467");
        goodsImageEntity.setGoodsGroupSeq(10034116);
        goodsImageEntity.setGoodsSeq(10137467);
        goodsGroupImageEntity.setGoodsGroupSeq(10034116);
        goodsDetailsDtoList.add(goodsDetailsDto);
        goodsImageEntityList.add(goodsImageEntity);
        goodsGroupImageEntityList.add(goodsGroupImageEntity);
        map.put(10034116, goodsGroupImageEntityList);

        // モック設定
        doReturn(goodsDetailsDtoList).when(goodsDao).getGoodsDetailsListByGoodsCodeList(any());
        doReturn(goodsImageEntityList).when(goodsImageDao).getGoodsImageListByGoodsSeqList(any());
        doReturn(map).when(goodsGroupImageGetLogic).execute(any());

        Map<String, GoodsDetailsDto> goodsDetailsDtoMap = goodsDetailsMapGetLogic.executeByExistGoodsCode(goodsSeqList);
        Assertions.assertEquals(1, goodsDetailsDtoMap.size());
    }

    @Test
    @Order(1)
    public void execute04() {
        List<String> goodsSeqList = new ArrayList<>();
        goodsSeqList.add("10137467");
        List<GoodsDetailsDto> goodsDetailsDtoList = new ArrayList<>();
        List<GoodsImageEntity> goodsImageEntityList = new ArrayList<>();
        Map<Integer, List<GoodsGroupImageEntity>> map = new HashMap<>();
        List<GoodsGroupImageEntity> goodsGroupImageEntityList = new ArrayList<>();
        GoodsDetailsDto goodsDetailsDto = new GoodsDetailsDto();
        GoodsImageEntity goodsImageEntity = new GoodsImageEntity();
        GoodsGroupImageEntity goodsGroupImageEntity = new GoodsGroupImageEntity();
        goodsDetailsDto.setGoodsGroupSeq(10034116);
        goodsDetailsDto.setGoodsSeq(10137467);
        goodsDetailsDto.setGoodsCode("10137467");
        goodsImageEntity.setGoodsGroupSeq(10034116);
        goodsImageEntity.setGoodsSeq(10137467);
        goodsGroupImageEntity.setGoodsGroupSeq(10034116);
        goodsDetailsDtoList.add(goodsDetailsDto);
        goodsImageEntityList.add(goodsImageEntity);
        goodsGroupImageEntityList.add(goodsGroupImageEntity);
        map.put(10034116, goodsGroupImageEntityList);

        // モック設定
        doReturn(goodsDetailsDtoList).when(goodsDao).getGoodsDetailsListByGoodsCodeList(any());
        doReturn(goodsImageEntityList).when(goodsImageDao).getGoodsImageListByGoodsSeqList(any());
        doReturn(map).when(goodsGroupImageGetLogic).execute(any());

        Map<String, GoodsDetailsDto> goodsDetailsDtoMap =
                        goodsDetailsMapGetLogic.executeByResponseGoodsCode(goodsSeqList);
        Assertions.assertEquals(1, goodsDetailsDtoMap.size());
    }
}
