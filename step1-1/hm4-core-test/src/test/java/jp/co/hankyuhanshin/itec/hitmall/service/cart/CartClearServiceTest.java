package jp.co.hankyuhanshin.itec.hitmall.service.cart;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.cart.CartGoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsForTakeOverDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.cart.CartGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

/**
 * Logic/Service移行：カート管理のリハーサル
 * 作成日：2021/02/26
 *
 * @author Phan Tien VU (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CartClearServiceTest extends AbstractShopService {

    @Autowired
    CartClearService cartClearService;

    @Autowired
    CartGoodsDao cartGoodsDao;

    @Test
    @Order(1)
    public void executeTest() {
        cartClearService.execute();
    }

    @Test
    @Order(2)
    public void executeCartGoodsListTest() {
        try (MockedStatic<PropertiesUtil> propertiesUtil = Mockito.mockStatic(PropertiesUtil.class)) {
            propertiesUtil.when(() -> PropertiesUtil.getSystemPropertiesValue("cartgoods.merge")).thenReturn(any());
            cartClearService.execute(makeRegistCartGoodsList());
        }

    }

    private List<CartGoodsForTakeOverDto> makeRegistCartGoodsList() {
        List<CartGoodsForTakeOverDto> cartGoodsForTakeOverDtoList = new ArrayList<>();

        CartGoodsEntity entity = makeCartGoodsEntity();
        cartGoodsDao.insert(entity);

        CartGoodsForTakeOverDto cartGoodsForTakeOverDto = new CartGoodsForTakeOverDto();

        cartGoodsForTakeOverDto.setGoodsGroupSeq(999);
        cartGoodsForTakeOverDto.setGoodsSeq(entity.getGoodsSeq());
        cartGoodsForTakeOverDto.setGoodsCode("goodsCode999");
        cartGoodsForTakeOverDto.setGoodsCount(entity.getCartGoodsCount());

        cartGoodsForTakeOverDtoList.add(cartGoodsForTakeOverDto);

        return cartGoodsForTakeOverDtoList;
    }

    private CartGoodsEntity makeCartGoodsEntity() {
        CartGoodsEntity entity = new CartGoodsEntity();
        entity.setCartSeq(999);
        entity.setAccessUid(getCommonInfo().getCommonInfoBase().getAccessUid());
        entity.setMemberInfoSeq(getCommonInfo().getCommonInfoUser().getMemberInfoSeq());
        entity.setGoodsSeq(1);
        entity.setCartGoodsCount(new BigDecimal(1));
        entity.setRegistTime(new Timestamp(System.currentTimeMillis()));
        entity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        entity.setShopSeq(getCommonInfo().getCommonInfoBase().getShopSeq());
        return entity;
    }

}
