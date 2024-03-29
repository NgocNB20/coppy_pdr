package jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteMapFlag;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeFreeAreaOpenStatus;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.freearea.FreeAreaDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.FreeAreaEntity;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FreeAreaCheckLogicTest {

    @Autowired
    FreeAreaCheckLogic freeAreaCheckLogic;

    @MockBean
    FreeAreaDao freeAreaDao;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        List<FreeAreaEntity> result = new ArrayList<>();

        // モック設定
        doReturn(result).when(freeAreaDao).getSameKeyOpenStartTimeEntityList(any(FreeAreaEntity.class));

        // 試験実行
        FreeAreaEntity freeAreaEntity = new FreeAreaEntity();
        freeAreaEntity.setFreeAreaSeq(1);
        freeAreaEntity.setShopSeq(1);
        freeAreaEntity.setFreeAreaKey("1");
        freeAreaEntity.setOpenStartTime(new Timestamp(new java.util.Date().getTime()));
        freeAreaEntity.setFreeAreaTitle("");
        freeAreaEntity.setFreeAreaBodyPc("");
        freeAreaEntity.setFreeAreaBodySp("");
        freeAreaEntity.setFreeAreaBodyMb("");
        freeAreaEntity.setTargetGoods("");
        freeAreaEntity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        freeAreaEntity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        freeAreaEntity.setFreeAreaOpenStatus(HTypeFreeAreaOpenStatus.OPEN_PAST);
        freeAreaEntity.setSiteMapFlag(HTypeSiteMapFlag.OFF);

        freeAreaCheckLogic.execute(freeAreaEntity);

        // 戻り値及び呼び出し検証
        verify(freeAreaDao, times(1)).getSameKeyOpenStartTimeEntityList(any(FreeAreaEntity.class));
    }
}
