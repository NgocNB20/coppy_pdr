package jp.co.hankyuhanshin.itec.hitmall.service.administrator;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAdministratorStatus;
import jp.co.hankyuhanshin.itec.hitmall.dto.totaling.AccessSearchKeywordTotalDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.totaling.AccessSearchKeywordTotalSearchForConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdministratorEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.administrator.AdminLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.totaling.searchkeywordaccording.SearchKeyWordAccordingTotalingListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.totaling.searchkeywordaccording.SearchKeyWordAccordingTotalingService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdministratorLoginServiceTest {

    @Autowired
    AdministratorLoginService administratorLoginService;

    @MockBean
    AdminLogic adminLogic;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        AdministratorEntity result = new AdministratorEntity();
        result.setShopSeq(123);
        result.setAdministratorSeq(789);
        result.setAdministratorId("admin");
        result.setLoginFailureCount(2);
        result.setAdministratorStatus(HTypeAdministratorStatus.USE);

        // モック設定
        doReturn(result).when(adminLogic).getAdministrator(any(Integer.class), any(String.class));
        doReturn(1).when(adminLogic).updateFailureCount(any(String.class), any(Integer.class));

        // 試験実行
        AdministratorEntity actual = administratorLoginService.execute("admin", "passWord");

        // 戻り値及び呼び出し検証
        verify(adminLogic, times(1)).getAdministrator(any(Integer.class), any(String.class));
        Assertions.assertEquals(result, actual);
    }
}
