package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.favorite;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.favorite.FavoriteDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.favorite.FavoriteEntity;

/**
 * Logic/Service移行：お気に入りデータチェックロジック
 * 作成日：2021/03/11
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FavoriteDataCheckLogicTest {

    @Autowired
    FavoriteDataCheckLogic logic;

    @MockBean
    private FavoriteDao dao;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        FavoriteEntity favoriteEntity = new FavoriteEntity();
        favoriteEntity.setMemberInfoSeq(1);
        favoriteEntity.setGoodsSeq(1);
        List<Integer> goodsSeqList = new ArrayList<Integer>();
        goodsSeqList.add(1);

        // モック設定
        doReturn(goodsSeqList).when(dao).getGoodsSeqList(any(Integer.class));

        // 試験実行
        logic.execute(favoriteEntity);

        // 戻り値及び呼び出し検証
        verify(dao, times(1)).getGoodsSeqList(any(Integer.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    @Order(2)
    public void getFavoriteEntityListForGoodsTest() {

        // 想定値設定
        Integer memberInfoSeq = 1;
        List<GoodsDto> goodsDtoList = new ArrayList<GoodsDto>();
        GoodsDto dto = new GoodsDto();
        GoodsEntity entity = new GoodsEntity();
        entity.setGoodsSeq(1);
        dto.setGoodsEntity(entity);
        goodsDtoList.add(dto);

        FavoriteEntity favoriteEntity = new FavoriteEntity();
        favoriteEntity.setMemberInfoSeq(1);
        favoriteEntity.setGoodsSeq(1);
        List<FavoriteEntity> result = new ArrayList<>();
        result.add(favoriteEntity);

        // モック設定
        doReturn(result).when(dao).getFavoriteEntityList(any(Integer.class), any(List.class));

        // 試験実行
        List<FavoriteEntity> actual = logic.getFavoriteEntityListForGoods(memberInfoSeq, goodsDtoList);

        // 戻り値及び呼び出し検証
        verify(dao, times(1)).getFavoriteEntityList(any(Integer.class), any(List.class));
        assertThat(actual).isEqualTo(result);
    }

    @SuppressWarnings("unchecked")
    @Test
    @Order(3)
    public void getFavoriteEntityListForCartTest() {

        // 想定値設定
        Integer memberInfoSeq = 1;
        CartDto cartDto = new CartDto();
        List<CartGoodsDto> listCartGoodsDto = new ArrayList<>();
        CartGoodsDto cartGoodsDto = new CartGoodsDto();
        GoodsDetailsDto goodsDetailsDto = new GoodsDetailsDto();
        goodsDetailsDto.setGoodsSeq(1);
        cartGoodsDto.setGoodsDetailsDto(goodsDetailsDto);
        listCartGoodsDto.add(cartGoodsDto);
        cartDto.setCartGoodsDtoList(listCartGoodsDto);

        FavoriteEntity favoriteEntity = new FavoriteEntity();
        favoriteEntity.setMemberInfoSeq(1);
        favoriteEntity.setGoodsSeq(1);
        List<FavoriteEntity> result = new ArrayList<>();
        result.add(favoriteEntity);

        // モック設定
        doReturn(result).when(dao).getFavoriteEntityList(any(Integer.class), any(List.class));

        // 試験実行
        List<FavoriteEntity> actual = logic.getFavoriteEntityListForCart(memberInfoSeq, cartDto);

        // 戻り値及び呼び出し検証
        verify(dao, times(1)).getFavoriteEntityList(any(Integer.class), any(List.class));
        assertThat(actual).isEqualTo(result);
    }

}
