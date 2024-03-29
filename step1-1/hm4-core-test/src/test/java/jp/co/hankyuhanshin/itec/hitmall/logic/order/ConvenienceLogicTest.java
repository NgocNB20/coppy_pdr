package jp.co.hankyuhanshin.itec.hitmall.logic.order;

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
import jp.co.hankyuhanshin.itec.hitmall.dao.conveni.ConvenienceDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.conveni.ConvenienceEntity;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ConvenienceLogicTest {

    @Autowired
    ConvenienceLogic logic;

    @MockBean
    private ConvenienceDao dao;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        Integer shopSeq = 1;
        String conveniCode = "CODE";
        ConvenienceEntity result = new ConvenienceEntity();

        // モック設定
        doReturn(result).when(dao).getEntityByConveniCode(any(Integer.class), any(String.class));

        // 試験実行
        ConvenienceEntity actual = logic.execute(shopSeq, conveniCode);

        // 戻り値及び呼び出し検証
        verify(dao, times(1)).getEntityByConveniCode(any(Integer.class), any(String.class));
        assertThat(actual).isEqualTo(result);
    }

    @Test
    @Order(2)
    public void getConvenienceList() {

        // 想定値設定
        ConvenienceEntity entity = new ConvenienceEntity();
        List<ConvenienceEntity> result = new ArrayList<>();
        result.add(entity);

        // モック設定
        doReturn(result).when(dao).getConvenienceList(any(Integer.class), any(Boolean.class));

        // 試験実行
        List<ConvenienceEntity> actual = logic.getConvenienceList();

        // 戻り値及び呼び出し検証
        verify(dao, times(1)).getConvenienceList(any(Integer.class), any(Boolean.class));
        assertThat(actual).isEqualTo(result);
    }
}
