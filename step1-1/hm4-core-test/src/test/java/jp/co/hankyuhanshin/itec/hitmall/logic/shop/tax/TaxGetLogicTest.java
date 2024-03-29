package jp.co.hankyuhanshin.itec.hitmall.logic.shop.tax;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeTaxRateType;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.tax.TaxRateDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.tax.TaxRateEntity;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaxGetLogicTest {

    @Autowired
    TaxGetLogic taxGetLogic;

    @MockBean
    TaxRateDao taxrateDao;

    @Test
    @Order(1)
    public void register() {
        List<TaxRateEntity> list = new ArrayList<TaxRateEntity>();

        TaxRateEntity entity = new TaxRateEntity();
        entity.setTaxSeq(1);

        entity.setRateType(HTypeTaxRateType.STANDARD);

        list.add(entity);

        doReturn(list).when(taxrateDao).getEffectiveTaxRateEntity(any(Integer.class));

        Map<HTypeTaxRateType, TaxRateEntity> mapTest = taxGetLogic.getEffectiveTaxRateMapByTaxSeq(1);

        verify(taxrateDao, times(1)).getEffectiveTaxRateEntity(any(Integer.class));

        Assertions.assertEquals(1, mapTest.get(HTypeTaxRateType.STANDARD).getTaxSeq());
    }
}
