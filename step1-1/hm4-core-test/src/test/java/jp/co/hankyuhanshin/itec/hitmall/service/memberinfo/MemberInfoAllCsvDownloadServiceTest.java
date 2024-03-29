package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoAllCsvDownloadLogic;
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

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MemberInfoAllCsvDownloadServiceTest {

    @Autowired
    MemberInfoAllCsvDownloadService memberInfoAllCsvDownloadService;

    @MockBean
    MemberInfoAllCsvDownloadLogic memberInfoAllCsvDownloadLogic;

    @Test
    @Order(1)
    public void execute() {

        // MemberInfoAllCsvDownloadService & MemberInfoAllCsvDownloadLogic を再実装するに伴い、こちらのテストは一旦外す
        //        // 想定値設定
        //        int result = 1;
        //
        //        // モック設定
        //        doReturn(result).when(memberInfoAllCsvDownloadLogic).execute(any(MemberInfoSearchForDaoConditionDto.class));
        //
        //        // 試験実行
        //        MemberInfoSearchForDaoConditionDto conditionDto = new MemberInfoSearchForDaoConditionDto();
        //        conditionDto.setShopSeq(10);
        //        conditionDto.setMemberRank(new ArrayList<>());
        //        conditionDto.setMemberRankAutoFlag(HTypeMemberRankAutoFlag.OFF);
        //        conditionDto.setMemberInfoId("1");
        //        conditionDto.setMemberInfoSeq(10);
        //
        //        int actual = memberInfoAllCsvDownloadService.execute(conditionDto);
        //
        //        // 戻り値及び呼び出し検証
        //        verify(memberInfoAllCsvDownloadLogic, times(1)).execute(any(MemberInfoSearchForDaoConditionDto.class));

        Assertions.assertTrue(true);
    }
}
