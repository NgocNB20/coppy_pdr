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
import jp.co.hankyuhanshin.itec.hitmall.dao.inquiry.InquiryDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.inquiry.InquiryDetailDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.inquiry.InquiryGroupDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.inquiry.InquiryDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryDetailEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryGroupEntity;

/**
 * Logic/Service移行：問い合わせ取得
 * 作成日：2021/03/09
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InquiryGetLogicTest {

    @Autowired
    InquiryGetLogic inquiryGetLogic;

    /**
     * 問い合わせDao
     */
    @MockBean
    private InquiryDao inquiryDao;

    /**
     * 問い合わせ内容Dao
     */
    @MockBean
    private InquiryDetailDao inquiryDetailDao;

    /**
     * 問い合わせ分類Dao
     */
    @MockBean
    private InquiryGroupDao inquiryGroupDao;

    @Test
    @Order(1)
    public void execute1Test() {

        // 想定値設定
        Integer mockInquirySeq = 1;
        Integer mockshopSeq = 1;
        InquiryEntity result = new InquiryEntity();

        // モック設定
        doReturn(result).when(inquiryDao).getEntityByShopSeq(any(Integer.class), any(Integer.class));

        // 試験実行
        InquiryEntity actual = inquiryGetLogic.execute(mockInquirySeq, mockshopSeq);

        // 戻り値及び呼び出し検証
        verify(inquiryDao, times(1)).getEntityByShopSeq(mockInquirySeq, mockshopSeq);
        assertThat(actual).isEqualTo(result);
    }

    @Test
    @Order(2)
    public void execute2Test() {

        // 想定値設定
        String mockInquiryCode = "AB1234";
        InquiryEntity inquiryEntity = new InquiryEntity();
        inquiryEntity.setInquiryGroupSeq(0);
        inquiryEntity.setInquirySeq(0);
        List<InquiryDetailEntity> listEntity = new ArrayList<>();
        InquiryGroupEntity inquiryGroupEntity = new InquiryGroupEntity();
        inquiryGroupEntity.setInquiryGroupName("BB");

        InquiryDetailsDto result = new InquiryDetailsDto();
        result.setInquiryEntity(inquiryEntity);
        result.setInquiryDetailEntityList(listEntity);
        result.setInquiryGroupName("BB");

        // モック設定
        doReturn(inquiryEntity).when(inquiryDao).getEntityByInquiryCode(any(String.class), any(Integer.class));
        doReturn(listEntity).when(inquiryDetailDao).getInquiryDetailsListByInquirySeq(any(Integer.class));
        doReturn(inquiryGroupEntity).when(inquiryGroupDao).getEntityByShopSeq(any(Integer.class), any(Integer.class));

        // 試験実行
        InquiryDetailsDto actual = inquiryGetLogic.execute(mockInquiryCode);

        // 戻り値及び呼び出し検証
        verify(inquiryDao, times(1)).getEntityByInquiryCode(any(String.class), any(Integer.class));
        verify(inquiryDetailDao, times(1)).getInquiryDetailsListByInquirySeq(any(Integer.class));
        verify(inquiryGroupDao, times(1)).getEntityByShopSeq(any(Integer.class), any(Integer.class));
        assertThat(actual).isEqualTo(result);
    }

    @Test
    @Order(3)
    public void execute3Test() {

        // 想定値設定
        Integer inquirySeq = 1;
        InquiryEntity inquiryEntity = new InquiryEntity();
        inquiryEntity.setInquiryGroupSeq(0);
        inquiryEntity.setInquirySeq(0);
        List<InquiryDetailEntity> listEntity = new ArrayList<>();
        InquiryGroupEntity inquiryGroupEntity = new InquiryGroupEntity();
        inquiryGroupEntity.setInquiryGroupName("BB");

        InquiryDetailsDto result = new InquiryDetailsDto();
        result.setInquiryEntity(inquiryEntity);
        result.setInquiryDetailEntityList(listEntity);
        result.setInquiryGroupName("BB");

        // モック設定
        doReturn(inquiryEntity).when(inquiryDao).getEntityByShopSeq(any(Integer.class), any(Integer.class));
        doReturn(listEntity).when(inquiryDetailDao).getInquiryDetailsListByInquirySeq(any(Integer.class));
        doReturn(inquiryGroupEntity).when(inquiryGroupDao).getEntityByShopSeq(any(Integer.class), any(Integer.class));

        // 試験実行
        InquiryDetailsDto actual = inquiryGetLogic.execute(inquirySeq);

        // 戻り値及び呼び出し検証
        verify(inquiryDao, times(1)).getEntityByShopSeq(any(Integer.class), any(Integer.class));
        verify(inquiryDetailDao, times(1)).getInquiryDetailsListByInquirySeq(any(Integer.class));
        verify(inquiryGroupDao, times(1)).getEntityByShopSeq(any(Integer.class), any(Integer.class));
        assertThat(actual).isEqualTo(result);
    }

    @Test
    @Order(4)
    public void execute4Test() {

        // 想定値設定
        String inquiryCode = "CODE";
        String inquiryTel = "TEL";
        InquiryEntity result = new InquiryEntity();

        // モック設定
        doReturn(result).when(inquiryDao)
                        .getEntityForInquiryLogin(any(String.class), any(String.class), any(Integer.class));

        // 試験実行
        InquiryEntity actual = inquiryGetLogic.execute(inquiryCode, inquiryTel);

        // 戻り値及び呼び出し検証
        verify(inquiryDao, times(1)).getEntityForInquiryLogin(any(String.class), any(String.class), any(Integer.class));
        assertThat(actual).isEqualTo(result);
    }
}
