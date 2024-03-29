package jp.co.hankyuhanshin.itec.hitmall.logic.goods.relation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsRelationDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsRelationEntity;

/**
 * Logic/Service移行:関連商品登録
 * 作成日：2021/03/10
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodsRelationRegistLogicTest {

    @Autowired
    GoodsRelationRegistLogic logic;

    @MockBean
    private GoodsRelationDao dao;

    @Test
    @Order(1)
    public void execute_insertTest() {

        // 想定値設定
        Integer goodsGroupSeq = 1;
        List<GoodsRelationEntity> goodsRelationEntityList = new ArrayList<>();
        GoodsRelationEntity entity = new GoodsRelationEntity();
        entity.setGoodsGroupSeq(1);
        entity.setOrderDisplay(2);
        entity.setRegistTime(Timestamp.valueOf("2021-03-10 12:12:12"));
        goodsRelationEntityList.add(entity);

        List<GoodsRelationEntity> listGoodsRelationEntity = new ArrayList<>();
        GoodsRelationEntity goodsRelationEntity = new GoodsRelationEntity();
        goodsRelationEntity.setGoodsRelationGroupSeq(1);
        goodsRelationEntity.setOrderDisplay(2);
        goodsRelationEntity.setRegistTime(Timestamp.valueOf("2021-03-10 12:12:12"));
        listGoodsRelationEntity.add(goodsRelationEntity);

        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("GoodsRelationDelete", 1);
        resultMap.put("GoodsRelationUpdate", 0);
        resultMap.put("GoodsRelationRegist", 1);

        // モック設定
        doReturn(listGoodsRelationEntity).when(dao).getGoodsRelationEntityListByGoodsGroupSeq(any(Integer.class));
        doReturn(1).when(dao).delete(any(GoodsRelationEntity.class));
        doReturn(1).when(dao).insert(any(GoodsRelationEntity.class));

        // 試験実行
        Map<String, Integer> actual = logic.execute(goodsGroupSeq, goodsRelationEntityList);

        // 戻り値及び呼び出し検証
        verify(dao, times(1)).getGoodsRelationEntityListByGoodsGroupSeq(any(Integer.class));
        verify(dao, times(1)).delete(any(GoodsRelationEntity.class));
        verify(dao, times(0)).update(any(GoodsRelationEntity.class));
        verify(dao, times(1)).insert(any(GoodsRelationEntity.class));
        assertThat(actual).isEqualTo(resultMap);
    }

    @Test
    @Order(1)
    public void execute_updateTest() {

        // 想定値設定
        Integer goodsGroupSeq = 1;
        List<GoodsRelationEntity> goodsRelationEntityList = new ArrayList<>();
        GoodsRelationEntity entity = new GoodsRelationEntity();
        entity.setGoodsRelationGroupSeq(0);
        entity.setGoodsGroupSeq(1);
        entity.setOrderDisplay(2);
        entity.setRegistTime(Timestamp.valueOf("2021-03-10 12:12:12"));
        goodsRelationEntityList.add(entity);

        List<GoodsRelationEntity> listGoodsRelationEntity = new ArrayList<>();
        GoodsRelationEntity goodsRelationEntity = new GoodsRelationEntity();
        goodsRelationEntity.setGoodsRelationGroupSeq(0);
        goodsRelationEntity.setOrderDisplay(3);
        goodsRelationEntity.setRegistTime(Timestamp.valueOf("2021-03-10 12:12:12"));
        listGoodsRelationEntity.add(goodsRelationEntity);

        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("GoodsRelationDelete", 0);
        resultMap.put("GoodsRelationUpdate", 1);
        resultMap.put("GoodsRelationRegist", 0);

        // モック設定
        doReturn(listGoodsRelationEntity).when(dao).getGoodsRelationEntityListByGoodsGroupSeq(any(Integer.class));
        doReturn(1).when(dao).update(any(GoodsRelationEntity.class));

        // 試験実行
        Map<String, Integer> actual = logic.execute(goodsGroupSeq, goodsRelationEntityList);

        // 戻り値及び呼び出し検証
        verify(dao, times(1)).getGoodsRelationEntityListByGoodsGroupSeq(any(Integer.class));
        verify(dao, times(0)).delete(any(GoodsRelationEntity.class));
        verify(dao, times(1)).update(any(GoodsRelationEntity.class));
        verify(dao, times(0)).insert(any(GoodsRelationEntity.class));
        assertThat(actual).isEqualTo(resultMap);
    }
}
