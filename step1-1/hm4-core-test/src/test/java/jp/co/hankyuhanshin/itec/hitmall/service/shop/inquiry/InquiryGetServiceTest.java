package jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry;

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
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry.InquiryGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry.InquiryGroupGetLogic;

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
public class InquiryGetServiceTest {

    @Autowired
    InquiryGetService service;

    @MockBean
    private InquiryGetLogic inquiryGetLogic;
    @MockBean
    private InquiryGroupGetLogic inquiryGroupGetLogic;
    @MockBean
    private InquiryDao inquiryDao;
    @MockBean
    private InquiryDetailDao inquiryDetailDao;
    @MockBean
    private InquiryGroupDao inquiryGroupDao;

    @Test
    @Order(1)
    public void execute1Test() {

        // 想定値設定
        Integer inquirySeq = 1;
        InquiryEntity inquiryEntity = new InquiryEntity();
        inquiryEntity.setInquiryGroupSeq(1);
        inquiryEntity.setInquirySeq(2);
        InquiryGroupEntity inquiryGroupEntity = new InquiryGroupEntity();
        inquiryGroupEntity.setInquiryGroupName("ABC");
        List<InquiryDetailEntity> listInquiryDetailEntity = new ArrayList<>();
        InquiryDetailsDto dtoResult = new InquiryDetailsDto();
        dtoResult.setInquiryEntity(inquiryEntity);
        dtoResult.setInquiryGroupName("ABC");
        dtoResult.setInquiryDetailEntityList(listInquiryDetailEntity);

        // モック設定
        doReturn(inquiryEntity).when(inquiryGetLogic).execute(any(Integer.class), any(Integer.class));
        doReturn(inquiryGroupEntity).when(inquiryGroupGetLogic).execute(any(Integer.class), any(Integer.class));
        doReturn(listInquiryDetailEntity).when(inquiryDetailDao).getInquiryDetailsListByInquirySeq(any(Integer.class));

        // 試験実行
        InquiryDetailsDto actual = service.execute(inquirySeq);

        // 戻り値及び呼び出し検証
        verify(inquiryGetLogic, times(1)).execute(any(Integer.class), any(Integer.class));
        verify(inquiryGroupGetLogic, times(1)).execute(any(Integer.class), any(Integer.class));
        verify(inquiryDetailDao, times(1)).getInquiryDetailsListByInquirySeq(any(Integer.class));
        assertThat(actual).isEqualTo(dtoResult);
    }

    @Test
    @Order(2)
    public void execute2Test() {

        // 想定値設定
        String orderCode = "YYY";
        boolean asc = true;
        List<InquiryEntity> listInquiryEntity = new ArrayList<>();
        InquiryEntity inquiryEntity = new InquiryEntity();
        inquiryEntity.setInquiryGroupSeq(1);
        inquiryEntity.setInquirySeq(2);
        listInquiryEntity.add(inquiryEntity);

        InquiryGroupEntity inquiryGroupEntity = new InquiryGroupEntity();
        inquiryGroupEntity.setInquiryGroupName("ABC");
        List<InquiryDetailEntity> listInquiryDetailEntity = new ArrayList<>();

        List<InquiryDetailsDto> listDtoResult = new ArrayList<>();
        InquiryDetailsDto dtoResult = new InquiryDetailsDto();
        dtoResult.setInquiryEntity(inquiryEntity);
        dtoResult.setInquiryGroupName("ABC");
        dtoResult.setInquiryDetailEntityList(listInquiryDetailEntity);
        listDtoResult.add(dtoResult);

        // モック設定
        doReturn(listInquiryEntity).when(inquiryDao)
                                   .getEntityListByOrderCode(any(String.class), any(Integer.class), any(Boolean.class));
        doReturn(listInquiryDetailEntity).when(inquiryDetailDao).getInquiryDetailsListByInquirySeq(any(Integer.class));
        doReturn(inquiryGroupEntity).when(inquiryGroupDao).getEntityByShopSeq(any(Integer.class), any(Integer.class));

        // 試験実行
        List<InquiryDetailsDto> actual = service.execute(orderCode, asc);

        // 戻り値及び呼び出し検証
        verify(inquiryDao, times(1)).getEntityListByOrderCode(
                        any(String.class), any(Integer.class), any(Boolean.class));
        verify(inquiryDetailDao, times(1)).getInquiryDetailsListByInquirySeq(any(Integer.class));
        verify(inquiryGroupDao, times(1)).getEntityByShopSeq(any(Integer.class), any(Integer.class));
        assertThat(actual).isEqualTo(listDtoResult);
    }

    @Test
    @Order(3)
    public void execute3Test() {

        // 想定値設定
        String orderCode = "YYY";
        List<InquiryEntity> listInquiryEntity = new ArrayList<>();
        InquiryEntity inquiryEntity = new InquiryEntity();
        inquiryEntity.setInquiryGroupSeq(1);
        inquiryEntity.setInquirySeq(2);
        listInquiryEntity.add(inquiryEntity);

        InquiryGroupEntity inquiryGroupEntity = new InquiryGroupEntity();
        inquiryGroupEntity.setInquiryGroupName("ABC");
        List<InquiryDetailEntity> listInquiryDetailEntity = new ArrayList<>();

        List<InquiryDetailsDto> listDtoResult = new ArrayList<>();
        InquiryDetailsDto dtoResult = new InquiryDetailsDto();
        dtoResult.setInquiryEntity(inquiryEntity);
        dtoResult.setInquiryGroupName("ABC");
        dtoResult.setInquiryDetailEntityList(listInquiryDetailEntity);
        listDtoResult.add(dtoResult);

        // モック設定
        doReturn(listInquiryEntity).when(inquiryDao)
                                   .getEntityListByOrderCode(any(String.class), any(Integer.class), any(Boolean.class));
        doReturn(listInquiryDetailEntity).when(inquiryDetailDao).getInquiryDetailsListByInquirySeq(any(Integer.class));
        doReturn(inquiryGroupEntity).when(inquiryGroupDao).getEntityByShopSeq(any(Integer.class), any(Integer.class));

        // 試験実行
        List<InquiryDetailsDto> actual = service.execute(orderCode);

        // 戻り値及び呼び出し検証
        verify(inquiryDao, times(1)).getEntityListByOrderCode(
                        any(String.class), any(Integer.class), any(Boolean.class));
        verify(inquiryDetailDao, times(1)).getInquiryDetailsListByInquirySeq(any(Integer.class));
        verify(inquiryGroupDao, times(1)).getEntityByShopSeq(any(Integer.class), any(Integer.class));
        assertThat(actual).isEqualTo(listDtoResult);
    }
}
