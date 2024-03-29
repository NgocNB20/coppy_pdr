package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.temp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
import jp.co.hankyuhanshin.itec.hitmall.application.commoninfo.impl.CommonInfoBaseImpl;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoDataCheckLogic;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TempMemberInfoRegistServiceTest {

    @Autowired
    TempMemberInfoRegistService service;

    @MockBean
    private MemberInfoDataCheckLogic logic;

    @Test
    @Order(1)
    public void executeTest() {

        //        MemberInfoEntity entity = new MemberInfoEntity();
        //
        //        MemberInfoEntity memberInfoEntity = ApplicationContextUtility.getBean(MemberInfoEntity.class);
        //        memberInfoEntity.setMemberInfoMail("abc");
        //        memberInfoEntity.setMemberInfoId("abc");
        //        memberInfoEntity.setShopSeq(1);

        //        CommonInfoBaseImpl cf = new CommonInfoBaseImpl();
        //        cf.setShopSeq(1);

        doNothing().when(logic).execute(any(MemberInfoEntity.class));

        boolean actual = service.execute("abc", 1);

        verify(logic, times(1)).execute(any(MemberInfoEntity.class));
        assertThat(true).isEqualTo(actual);
    }
}
