package jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry;

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
import jp.co.hankyuhanshin.itec.hitmall.dto.inquiry.InquiryDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryDetailEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryEntity;

/**
 * Logic/Service移行：問い合わせ登録ロジック
 * 作成日：2021/03/10
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InquiryRegistLogicTest {

    @Autowired
    InquiryRegistLogic logic;

    @MockBean
    private InquiryDao inquiryDao;
    @MockBean
    private InquiryDetailDao inquiryDetailDao;
    @MockBean
    private NewInquirySeqGetLogic newInquirySeqGetLogic;

    @Test
    @Order(1)
    public void executeTest_insert() {

        // 想定値設定
        Integer inquirySeq = 1;
        InquiryEntity inquiryEntity = new InquiryEntity();
        inquiryEntity.setInquirySeq(null);
        InquiryDetailEntity inquiryDetail = new InquiryDetailEntity();
        List<InquiryDetailEntity> listInquiryDetail = new ArrayList<>();
        listInquiryDetail.add(inquiryDetail);
        InquiryDetailsDto inquiryDetailsDto = new InquiryDetailsDto();
        inquiryDetailsDto.setInquiryEntity(inquiryEntity);
        inquiryDetailsDto.setInquiryDetailEntityList(listInquiryDetail);
        int result = 1;

        // モック設定
        doReturn(inquirySeq).when(newInquirySeqGetLogic).execute();
        doReturn(result).when(inquiryDao).insert(any(InquiryEntity.class));
        doReturn(result).when(inquiryDetailDao).insert(any(InquiryDetailEntity.class));

        // 試験実行
        logic.executeInquiryRegist(inquiryDetailsDto);

        // 戻り値及び呼び出し検証
        verify(newInquirySeqGetLogic, times(1)).execute();
        verify(inquiryDao, times(1)).insert(any(InquiryEntity.class));
        verify(inquiryDetailDao, times(1)).insert(any(InquiryDetailEntity.class));
    }

    @Test
    @Order(2)
    public void executeTest_update() {

        // 想定値設定
        InquiryEntity inquiryEntity = new InquiryEntity();
        inquiryEntity.setInquirySeq(1);
        InquiryDetailEntity inquiryDetail = new InquiryDetailEntity();
        List<InquiryDetailEntity> listInquiryDetail = new ArrayList<>();
        listInquiryDetail.add(inquiryDetail);
        InquiryDetailsDto inquiryDetailsDto = new InquiryDetailsDto();
        inquiryDetailsDto.setInquiryEntity(inquiryEntity);
        inquiryDetailsDto.setInquiryDetailEntityList(listInquiryDetail);
        int result = 1;

        // モック設定
        doReturn(result).when(inquiryDao).update(any(InquiryEntity.class));
        doReturn(result).when(inquiryDetailDao).insert(any(InquiryDetailEntity.class));

        // 試験実行
        logic.executeInquiryRegist(inquiryDetailsDto);

        // 戻り値及び呼び出し検証
        verify(newInquirySeqGetLogic, times(0)).execute();
        verify(inquiryDao, times(0)).insert(any(InquiryEntity.class));
        verify(inquiryDao, times(1)).update(any(InquiryEntity.class));
        verify(inquiryDetailDao, times(1)).insert(any(InquiryDetailEntity.class));
    }
}
