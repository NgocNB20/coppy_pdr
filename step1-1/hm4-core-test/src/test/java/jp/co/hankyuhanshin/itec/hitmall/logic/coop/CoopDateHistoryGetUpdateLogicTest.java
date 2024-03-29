package jp.co.hankyuhanshin.itec.hitmall.logic.coop;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCoopId;
import jp.co.hankyuhanshin.itec.hitmall.entity.coop.CoopDateHistoryEntity;
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
public class CoopDateHistoryGetUpdateLogicTest {

    @Autowired
    CoopDateHistoryGetUpdateLogic coopDateHistoryGetUpdateLogic;

    @Test
    @Order(1)
    public void executeTest() {

        CoopDateHistoryEntity coopDateHistoryEntity = coopDateHistoryGetUpdateLogic.execute("IDG001");
        Assertions.assertEquals(HTypeCoopId.GOODS_CHGINFO_GET_COOP, coopDateHistoryEntity.getCoopId());
    }

    @Test
    @Order(2)
    public void executeTest2() {
        CoopDateHistoryEntity coopDateHistoryEntity = new CoopDateHistoryEntity();

        coopDateHistoryEntity.setCoopId(HTypeCoopId.GOODS_CHGINFO_GET_COOP);
        coopDateHistoryEntity.setLastCoopDate(new Timestamp(new java.util.Date().getTime()));
        coopDateHistoryEntity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        coopDateHistoryEntity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));

        coopDateHistoryGetUpdateLogic.execute(coopDateHistoryEntity);
    }
}
