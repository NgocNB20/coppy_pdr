package jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.favorite;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.favorite.FavoriteEntity;
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

import java.sql.Timestamp;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FavoriteDaoTest {

    @Autowired
    FavoriteDao favoriteDao;

    @Test
    @Order(1)
    public void insert() {
        FavoriteEntity entity = makeFavoriteEntity();
        int result = favoriteDao.insert(entity);
        Assertions.assertNotNull(favoriteDao.getEntity(999, 999));
    }

    @Test
    @Order(2)
    public void delete() {
        FavoriteEntity entity = favoriteDao.getEntity(999, 999);
        int result = favoriteDao.delete(entity);
        Assertions.assertNull(favoriteDao.getEntity(999, 999));
    }

    private FavoriteEntity makeFavoriteEntity() {
        FavoriteEntity entity = new FavoriteEntity();
        entity.setMemberInfoSeq(999);
        entity.setGoodsSeq(999);
        entity.setRegistTime(Timestamp.valueOf("2099-02-03 11:11:11.0"));
        entity.setUpdateTime(Timestamp.valueOf("2099-02-03 11:11:11.0"));

        return entity;
    }
}
