package jp.co.hankyuhanshin.itec.hitmall.dao.cart;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.application.commoninfo.CommonInfoCart;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.cart.CartGoodsEntity;
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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CartGoodsBatchDaoTest {

    @Autowired
    CartGoodsDao cartGoodsDao;

    @Test
    @Order(1)
    public void insert() {
        CartGoodsEntity entity = makeCartGoodsEntity(999, "1", 1);

        int result = cartGoodsDao.insert(entity);
        System.out.println("result: " + result);
        Assertions.assertEquals(999, cartGoodsDao.getEntity(999).getCartSeq());
    }

    @Test
    @Order(2)
    public void update() {
        CartGoodsEntity entity = cartGoodsDao.getEntity(999);
        entity.setGoodsSeq(999);
        int result = cartGoodsDao.update(entity);
        System.out.println("result: " + result);
        Assertions.assertEquals(999, cartGoodsDao.getEntity(999).getGoodsSeq());
    }

    @Test
    @Order(3)
    public void delete() {
        CartGoodsEntity entity = cartGoodsDao.getEntity(999);
        int result = cartGoodsDao.delete(entity);
        System.out.println("result: " + result);
        Assertions.assertNull(cartGoodsDao.getEntity(999));
    }

    @Test
    @Order(4)
    public void deleteList1() {
        int result;
        List<Integer> listDelete = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            CartGoodsEntity entity = makeCartGoodsEntity(i, String.valueOf(i), i - 1);
            result = cartGoodsDao.insert(entity);
            listDelete.add(i);
        }
        result = cartGoodsDao.deleteList(listDelete, null, 1);
        Assertions.assertNull(cartGoodsDao.getEntity(2));

        result = cartGoodsDao.delete(cartGoodsDao.getEntity(1));
    }

    @Test
    @Order(5)
    public void deleteList2() {
        int result;
        List<Integer> listDelete = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            CartGoodsEntity entity = makeCartGoodsEntity(i, String.valueOf(i), i - 1);
            result = cartGoodsDao.insert(entity);
            listDelete.add(i);
        }
        result = cartGoodsDao.deleteList(listDelete, "1", null);
        Assertions.assertNull(cartGoodsDao.getEntity(1));

        result = cartGoodsDao.delete(cartGoodsDao.getEntity(2));
    }

    @Test
    @Order(6)
    public void deleteShopMemberCart() {
        int result;
        for (int i = 1; i < 3; i++) {
            CartGoodsEntity entity = makeCartGoodsEntity(i, String.valueOf(i), i - 1);
            result = cartGoodsDao.insert(entity);
        }
        result = cartGoodsDao.deleteShopMemberCart(1, null, 1);
        Assertions.assertNull(cartGoodsDao.getEntity(2));

        result = cartGoodsDao.deleteShopMemberCart(1, "1", 0);
        Assertions.assertNull(cartGoodsDao.getEntity(1));
    }

    @Test
    @Order(7)
    public void getCartGoodsCount() {
        int result;
        List<Integer> listDelete = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            CartGoodsEntity entity;
            listDelete.add(i);
            if (i == 1) {
                entity = makeCartGoodsEntity(i, String.valueOf(i), 0);
            } else {
                entity = makeCartGoodsEntity(i, String.valueOf(i), 1);
            }
            result = cartGoodsDao.insert(entity);
        }
        Assertions.assertEquals(3, cartGoodsDao.getCartGoodsCount(1, null, 1));
        Assertions.assertEquals(1, cartGoodsDao.getCartGoodsCount(1, "1", null));
        for (int i : listDelete) {
            result = cartGoodsDao.delete(cartGoodsDao.getEntity(i));
        }
    }

    @Test
    @Order(8)
    public void updateGoodsCount() {
        int result;
        Timestamp timeStamp = Timestamp.valueOf("2099-02-03 11:11:11.0");

        CartGoodsEntity entity = makeCartGoodsEntity(999, "1", 1);
        result = cartGoodsDao.insert(entity);

        result = cartGoodsDao.updateGoodsCount(999, new BigDecimal(999), timeStamp);

        entity = cartGoodsDao.getEntity(999);
        Assertions.assertEquals(new BigDecimal(999), entity.getCartGoodsCount());
        Assertions.assertEquals(timeStamp, entity.getUpdateTime());

        result = cartGoodsDao.delete(cartGoodsDao.getEntity(999));
    }

    @Test
    @Order(9)
    public void updateMemberInfo() {
        int result;
        CartGoodsEntity entity = makeCartGoodsEntity(999, "1", 1);
        result = cartGoodsDao.insert(entity);

        result = cartGoodsDao.updateMemberInfo(1, "1", 1, 999);
        Assertions.assertEquals(999, cartGoodsDao.getEntity(999).getMemberInfoSeq());

        result = cartGoodsDao.delete(cartGoodsDao.getEntity(999));
    }

    @Test
    @Order(10)
    public void getCartGoodsList() {
        int result;
        List<Integer> listDelete = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            CartGoodsEntity entity;
            listDelete.add(i);
            if (i == 1 || i == 2) {
                entity = makeCartGoodsEntity(i, "1", 0);
            } else {
                entity = makeCartGoodsEntity(i, "1", 1);
            }
            result = cartGoodsDao.insert(entity);
        }

        CartGoodsForDaoConditionDto dto = new CartGoodsForDaoConditionDto();
        dto.setShopSeq(1);
        dto.setMemberInfoSeq(1);
        List<CartGoodsEntity> ret;

        ret = cartGoodsDao.getCartGoodsList(dto);
        Assertions.assertEquals(3, ret.size());

        dto.setShopSeq(1);
        dto.setMemberInfoSeq(0);
        dto.setAccessUid("1");

        ret = cartGoodsDao.getCartGoodsList(dto);
        Assertions.assertEquals(2, ret.size());

        for (int i : listDelete) {
            result = cartGoodsDao.delete(cartGoodsDao.getEntity(i));
        }
    }

    @Test
    @Order(11)
    public void getCartGoodsOverlapList() {
        int result;
        List<Integer> listDelete = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            listDelete.add(i);
            CartGoodsEntity entity = makeCartGoodsEntity(i, "1", 1);
            result = cartGoodsDao.insert(entity);
        }

        CartGoodsEntity entity = makeCartGoodsEntity(1, "1", 1);

        List<CartGoodsEntity> ret = cartGoodsDao.getCartGoodsOverlapList(entity);
        Assertions.assertEquals(listDelete.size(), ret.size());

        for (int i : listDelete) {
            result = cartGoodsDao.delete(cartGoodsDao.getEntity(i));
        }
    }

    @Test
    @Order(12)
    public void updateCartgoodsCountMerge() {
        int result;
        Timestamp timestamp = Timestamp.valueOf("2099-02-03 11:11:11.0");

        CartGoodsEntity entity = makeCartGoodsEntity(999, "1", 1);
        result = cartGoodsDao.insert(entity);

        entity.setCartGoodsCount(new BigDecimal(999));
        entity.setUpdateTime(timestamp);
        entity.setRegistTime(timestamp);

        result = cartGoodsDao.updateCartgoodsCountMerge(entity);
        Assertions.assertEquals(new BigDecimal(999), cartGoodsDao.getEntity(999).getCartGoodsCount());
        Assertions.assertEquals(timestamp, cartGoodsDao.getEntity(999).getUpdateTime());
        Assertions.assertEquals(timestamp, cartGoodsDao.getEntity(999).getRegistTime());

        result = cartGoodsDao.delete(cartGoodsDao.getEntity(999));
    }

    @Test
    @Order(13)
    public void deleteSameOldCartGoods() {
        int result;
        for (int i = 1; i < 3; i++) {
            result = cartGoodsDao.insert(makeCartGoodsEntity(i, "1", 1));
        }
        result = cartGoodsDao.deleteSameOldCartGoods(1, 1);

        CartGoodsForDaoConditionDto dto = new CartGoodsForDaoConditionDto();
        dto.setShopSeq(1);
        dto.setMemberInfoSeq(1);
        List<CartGoodsEntity> ret = cartGoodsDao.getCartGoodsList(dto);
        Assertions.assertEquals(1, ret.size());
        for (CartGoodsEntity entity : ret) {
            result = cartGoodsDao.delete(cartGoodsDao.getEntity(entity.getCartSeq()));
        }
    }

    @Test
    @Order(14)
    public void getCommonInfoCart() {
        int result;
        List<Integer> listDelete = new ArrayList<>();

        for (int i = 1; i < 3; i++) {
            result = cartGoodsDao.insert(makeCartGoodsEntity(i, "1", 1));
            listDelete.add(i);
        }

        CommonInfoCart commonInfoCart = cartGoodsDao.getCommonInfoCart(1, null);

        Assertions.assertEquals(new BigDecimal(2), commonInfoCart.getCartGoodsSumCount());

        for (int i : listDelete) {
            result = cartGoodsDao.delete(cartGoodsDao.getEntity(i));
        }
    }

    private CartGoodsEntity makeCartGoodsEntity(int cartSeq, String accessUid, int memberInfoSeq) {
        CartGoodsEntity entity = new CartGoodsEntity();
        entity.setCartSeq(cartSeq);
        entity.setAccessUid(accessUid);
        entity.setMemberInfoSeq(memberInfoSeq);
        entity.setGoodsSeq(1);
        entity.setCartGoodsCount(new BigDecimal(1));
        entity.setRegistTime(new Timestamp(System.currentTimeMillis()));
        entity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        entity.setShopSeq(1);
        return entity;
    }

}
