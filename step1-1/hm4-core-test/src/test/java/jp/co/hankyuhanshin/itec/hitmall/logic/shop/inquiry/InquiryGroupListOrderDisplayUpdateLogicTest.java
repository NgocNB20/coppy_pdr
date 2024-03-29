package jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Timestamp;
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
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.dao.inquiry.InquiryGroupDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryGroupEntity;

/**
 * Logic/Service移行：問い合わせ分類表示順更新
 * 作成日：2021/03/10
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InquiryGroupListOrderDisplayUpdateLogicTest {

    @Autowired
    InquiryGroupListOrderDisplayUpdateLogic logic;

    @MockBean
    private InquiryGroupDao inquiryGroupDao;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        List<InquiryGroupEntity> inquiryGroupEntityList = new ArrayList<>();
        InquiryGroupEntity entity = new InquiryGroupEntity();
        entity.setInquiryGroupSeq(1);
        entity.setShopSeq(2);
        entity.setInquiryGroupName("ABC");
        entity.setOpenStatus(HTypeOpenDeleteStatus.OPEN);
        entity.setOrderDisplay(2);
        inquiryGroupEntityList.add(entity);
        int mockOutput = 10;
        int result = 10;

        // モック設定
        doNothing().when(inquiryGroupDao).updateLockTableShareModeNowait();
        doReturn(mockOutput).when(inquiryGroupDao)
                            .updateOrderDisplay(any(Integer.class), any(Integer.class), any(Integer.class),
                                                any(Timestamp.class)
                                               );

        // 試験実行
        int actual = logic.execute(inquiryGroupEntityList);

        // 戻り値及び呼び出し検証
        verify(inquiryGroupDao, times(1)).updateLockTableShareModeNowait();
        verify(inquiryGroupDao, times(1)).updateOrderDisplay(
                        any(Integer.class), any(Integer.class), any(Integer.class), any(Timestamp.class));
        assertThat(actual).isEqualTo(result);
    }
}
