package jp.co.hankyuhanshin.itec.hitmall.logic.goods.relation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberRank;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsRelationDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsRelationSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsRelationEntity;

/**
 * Logic/Service移行<br/>
 * 関連商品詳細情報リスト取得<br/>
 * 対象商品の関連商品リスト（商品Dtoリスト）を取得する。<br/>
 * 作成日：2021/03/10
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodsRelationListGetLogicTest {

    @Autowired
    GoodsRelationListGetLogic logic;

    @MockBean
    private GoodsRelationDao dao;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        GoodsRelationSearchForDaoConditionDto dto = new GoodsRelationSearchForDaoConditionDto();
        dto.setMemberRank(HTypeMemberRank.GOLD);
        dto.setSaleId(new ArrayList<>());
        dto.setSiteType(HTypeSiteType.BACK);
        dto.setOpenStatus(HTypeOpenDeleteStatus.OPEN);
        List<GoodsRelationEntity> listGoodsRelationEntity = new ArrayList<>();
        GoodsRelationEntity entity = new GoodsRelationEntity();
        entity.setGoodsGroupCode("ABC");
        listGoodsRelationEntity.add(entity);
        List<GoodsGroupDto> listResult = new ArrayList<>();

        // モック設定
        doReturn(listGoodsRelationEntity).when(dao)
                                         .getSearchGoodsRelation(any(GoodsRelationSearchForDaoConditionDto.class),
                                                                 any(SelectOptions.class)
                                                                );

        // 試験実行
        List<GoodsGroupDto> actual = logic.execute(dto);

        // 戻り値及び呼び出し検証
        verify(dao, times(1)).getSearchGoodsRelation(
                        any(GoodsRelationSearchForDaoConditionDto.class), any(SelectOptions.class));
        assertThat(actual).isEqualTo(listResult);
    }
}
