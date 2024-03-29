package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.password;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoUpdateLogic;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MemberInfoPasswordUpdateServiceTest {

    @Autowired
    MemberInfoPasswordUpdateService memberInfoPasswordUpdateService;

    @MockBean
    MemberInfoUpdateLogic memberInfoUpdateLogic;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        int result = 1;

        MemberInfoEntity memberInfoEntity = new MemberInfoEntity();
        memberInfoEntity.setMemberInfoSeq(123);
        memberInfoEntity.setShopSeq(999);
        memberInfoEntity.setMemberInfoUniqueId("123");

        // モック設定
        doReturn(result).when(memberInfoUpdateLogic).execute(any(MemberInfoEntity.class));

        // 試験実行
        int actual = memberInfoPasswordUpdateService.execute(memberInfoEntity, "old-password", "new-password");

        // 戻り値及び呼び出し検証
        verify(memberInfoUpdateLogic, times(1)).execute(any(MemberInfoEntity.class));
        Assertions.assertEquals(result, actual);
    }
}
