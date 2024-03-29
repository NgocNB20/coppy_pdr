package jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Timestamp;

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
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeInquiryStatus;
import jp.co.hankyuhanshin.itec.hitmall.dao.inquiry.InquiryDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryEntity;

/**
 * Logic/Service移行：問い合わせ更新ロジック
 * 作成日：2021/03/10
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InquiryUpdateLogicTest {

    @Autowired
    InquiryUpdateLogic logic;

    @MockBean
    private InquiryDao inquiryDao;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        InquiryEntity inquiryEntity = new InquiryEntity();
        inquiryEntity.setInquirySeq(1);
        inquiryEntity.setShopSeq(2);
        inquiryEntity.setInquiryCode("ABC");
        inquiryEntity.setInquiryLastName("NAME");
        Timestamp timestamp = Timestamp.valueOf("2021-03-10 12:12:12");
        inquiryEntity.setInquiryTime(timestamp);
        inquiryEntity.setInquiryStatus(HTypeInquiryStatus.SENDING);
        inquiryEntity.setInquiryGroupSeq(1);
        int result = 1;

        // モック設定
        doReturn(result).when(inquiryDao).update(any(InquiryEntity.class));

        // 試験実行
        int actual = logic.execute(inquiryEntity);

        // 戻り値及び呼び出し検証
        verify(inquiryDao, times(1)).update(any(InquiryEntity.class));
        assertThat(actual).isEqualTo(result);
    }
}
