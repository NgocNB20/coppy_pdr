package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.multipayment.CardBrandDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.CardBrandEntity;
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
public class CardBrandRegistLogicTest {

    @Autowired
    CardBrandRegistLogic cardBrandRegistLogic;

    @MockBean
    CardBrandDao cardBrandDao;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        int result = 1;

        // モック設定
        doReturn(result).when(cardBrandDao).insert(any(CardBrandEntity.class));

        // 試験実行
        CardBrandEntity cardBrandEntity = new CardBrandEntity();
        cardBrandEntity.setCardBrandName("test");
        int actual = cardBrandRegistLogic.execute(cardBrandEntity);

        // 戻り値及び呼び出し検証
        verify(cardBrandDao, times(1)).insert(any(CardBrandEntity.class));
        Assertions.assertEquals(result, actual);
    }
}
