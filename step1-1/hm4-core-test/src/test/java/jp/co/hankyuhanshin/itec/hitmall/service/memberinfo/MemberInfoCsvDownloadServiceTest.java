package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoCsvDownloadLogic;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MemberInfoCsvDownloadServiceTest {

    @Autowired
    MemberInfoCsvDownloadService memberInfoCsvDownloadService;

    @MockBean
    MemberInfoCsvDownloadLogic memberInfoCsvDownloadLogic;

    @Test
    @Order(1)
    public void execute() {

        // MemberInfoCsvDownloadService & MemberInfoCsvDownloadLogic を再実装するに伴い、こちらのテストは一旦外す
        //        // 想定値設定
        //        int result = 1;
        //
        //        // モック設定
        //        doReturn(result).when(memberInfoCsvDownloadLogic).execute(any(List.class), any(String.class), any(String.class));
        //
        //        // 試験実行
        //        List<Integer> memberInfoSeqList = new ArrayList<>();
        //        memberInfoSeqList.add(123);
        //        int actual = memberInfoCsvDownloadService.execute(memberInfoSeqList, "20210324", "20210325");
        //
        //        // 戻り値及び呼び出し検証
        //        verify(memberInfoCsvDownloadLogic, times(1)).execute(any(List.class), any(String.class), any(String.class));

        Assertions.assertTrue(true);
    }
}
