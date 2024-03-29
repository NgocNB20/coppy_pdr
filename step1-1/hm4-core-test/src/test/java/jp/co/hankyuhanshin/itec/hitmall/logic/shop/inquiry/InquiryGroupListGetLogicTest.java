package jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry;

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
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.dao.inquiry.InquiryGroupDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.inquiry.InquiryGroupSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryGroupEntity;

/**
 * Logic/Service移行：問い合わせ分類取得ロジック
 * 作成日：2021/03/10
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InquiryGroupListGetLogicTest {

    @Autowired
    InquiryGroupListGetLogic inquiryGroupListGetLogic;

    @MockBean
    private InquiryGroupDao inquiryGroupDao;

    @Test
    @Order(1)
    public void execute1Test() {

        // 想定値設定
        InquiryGroupSearchForDaoConditionDto dto = new InquiryGroupSearchForDaoConditionDto();
        dto.setShopSeq(1);
        dto.setOpenStatus(HTypeOpenDeleteStatus.OPEN);
        List<InquiryGroupEntity> result = new ArrayList<>();

        // モック設定
        doReturn(result).when(inquiryGroupDao).getInquiryGroupList(any(InquiryGroupSearchForDaoConditionDto.class));

        // 試験実行
        List<InquiryGroupEntity> actual = inquiryGroupListGetLogic.execute(dto);

        // 戻り値及び呼び出し検証
        verify(inquiryGroupDao, times(1)).getInquiryGroupList(any(InquiryGroupSearchForDaoConditionDto.class));
        assertThat(actual).isEqualTo(result);
    }

    @Test
    @Order(2)
    public void execute2Test() {

        // 想定値設定
        Integer shopSeq = 1;
        List<InquiryGroupEntity> result = new ArrayList<>();

        // モック設定
        doReturn(result).when(inquiryGroupDao).getInquiryGroupEntityList(any(Integer.class));

        // 試験実行
        List<InquiryGroupEntity> actual = inquiryGroupListGetLogic.execute(shopSeq);

        // 戻り値及び呼び出し検証
        verify(inquiryGroupDao, times(1)).getInquiryGroupEntityList(shopSeq);
        assertThat(actual).isEqualTo(result);
    }
}
