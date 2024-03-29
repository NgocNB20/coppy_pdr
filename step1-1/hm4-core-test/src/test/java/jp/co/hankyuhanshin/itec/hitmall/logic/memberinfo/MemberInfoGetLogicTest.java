package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.MemberInfoDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.MemberInfoForMemberMemnuDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Timestamp;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MemberInfoGetLogicTest {

    @Autowired
    MemberInfoGetLogic memberInfoGetLogic;

    @MockBean
    MemberInfoDao memberInfoDao;

    @Test
    @Order(1)
    public void execute() {
        MemberInfoEntity entity = new MemberInfoEntity();
        entity.setMemberInfoSeq(999);
        doReturn(entity).when(memberInfoDao).getEntity(any(Integer.class));

        MemberInfoEntity memberInfoEntity = memberInfoGetLogic.execute(999);
        verify(memberInfoDao, times(1)).getEntity(999);
        Assertions.assertEquals(entity, memberInfoEntity);
    }

    @Test
    public void executeGetStatus() {
        // Setup data
        MemberInfoEntity entity = new MemberInfoEntity();
        entity.setMemberInfoSeq(999);
        entity.setMemberInfoStatus(HTypeMemberInfoStatus.ADMISSION);

        // Mock
        doReturn(entity).when(memberInfoDao).getEntityByStatus(entity.getMemberInfoSeq(), entity.getMemberInfoStatus());

        // Verify
        MemberInfoEntity actual = memberInfoGetLogic.execute(entity.getMemberInfoSeq(), entity.getMemberInfoStatus());
        Assertions.assertEquals(entity, actual);
    }

    @Test
    void executeGetByIdBirthDay() {
        // Setup data
        Integer shopSeq = 1001;
        MemberInfoEntity entity = new MemberInfoEntity();
        entity.setMemberInfoId("test.@itec.hankyu-hanshin.co.jp");
        entity.setMemberInfoBirthday(new Timestamp(System.currentTimeMillis()));

        // Mock
        doReturn(entity).when(memberInfoDao)
                        .getEntityByIdBirthDay(shopSeq, entity.getMemberInfoId(), entity.getMemberInfoBirthday());

        // Verify
        MemberInfoEntity actual =
                        memberInfoGetLogic.execute(shopSeq, entity.getMemberInfoId(), entity.getMemberInfoBirthday());
        Assertions.assertEquals(entity, actual);
    }

    @Test
    void executeGetByIdStatus() {
        // Setup data
        Integer shopSeq = 1001;
        MemberInfoEntity entity = new MemberInfoEntity();
        entity.setMemberInfoSeq(999);
        entity.setMemberInfoId("test.@itec.hankyu-hanshin.co.jp");
        entity.setMemberInfoStatus(HTypeMemberInfoStatus.ADMISSION);
        entity.setMemberInfoBirthday(new Timestamp(System.currentTimeMillis()));

        // Mock
        doReturn(entity).when(memberInfoDao)
                        .getEntityByIdStatus(shopSeq, entity.getMemberInfoId(), entity.getMemberInfoStatus());

        // Verify
        MemberInfoEntity actual =
                        memberInfoGetLogic.execute(shopSeq, entity.getMemberInfoId(), entity.getMemberInfoStatus());
        Assertions.assertEquals(entity, actual);
    }

    @Test
    void executeGetByAccessUid() {
        // Setup data
        Integer shopSeq = 1001;
        String accessUid = "1574230823153641";

        MemberInfoEntity entity = new MemberInfoEntity();
        entity.setAccessUid(accessUid);

        // Mock
        doReturn(entity).when(memberInfoDao).getEntityByAccessUid(shopSeq, accessUid);

        // Verify
        MemberInfoEntity actual = memberInfoGetLogic.execute(shopSeq, accessUid);
        Assertions.assertEquals(entity, actual);
    }

    @Test
    void executeGetByMemberInfoUniqueId() {
        // Setup data
        String memberInfoUniqueId = "1001test.@itec.hankyu-hanshin.co.jp";
        MemberInfoEntity entity = new MemberInfoEntity();
        entity.setMemberInfoUniqueId(memberInfoUniqueId);

        // Mock
        doReturn(entity).when(memberInfoDao).getEntityByMemberInfoUniqueId(memberInfoUniqueId);

        // Verify
        MemberInfoEntity actual = memberInfoGetLogic.execute(memberInfoUniqueId);
        Assertions.assertEquals(entity, actual);
    }

    @Test
    void getMemberMenuDto() {
        // Setup data
        MemberInfoForMemberMemnuDto memberInfoForMemberMemnuDto = new MemberInfoForMemberMemnuDto();

        try (MockedStatic<ApplicationContextUtility> applicationContextUtility = Mockito.mockStatic(
                        ApplicationContextUtility.class)) {
            // Mock
            applicationContextUtility.when(() -> ApplicationContextUtility.getBean(MemberInfoForMemberMemnuDto.class))
                                     .thenReturn(memberInfoForMemberMemnuDto);

            // Verify
            Assertions.assertEquals(memberInfoForMemberMemnuDto, memberInfoGetLogic.getMemberMenuDto(10144290));
        }
    }

    @Test
    void executeByMailStatus() {
        // Setup data
        String memberInfoMail = "test+memseq10000000.@itec.hankyu-hanshin.co.jp";
        HTypeMemberInfoStatus status = HTypeMemberInfoStatus.ADMISSION;

        MemberInfoEntity entity = new MemberInfoEntity();
        entity.setMemberInfoMail(memberInfoMail);
        entity.setMemberInfoStatus(status);

        // Mock
        doReturn(entity).when(memberInfoDao).getEntityByMailStatus(memberInfoMail, status);

        // Verify
        MemberInfoEntity actual = memberInfoGetLogic.executeByMailStatus(memberInfoMail, status);
        Assertions.assertEquals(entity, actual);
    }

    @Test
    void executeForProvisional() {
        // Setup data
        String memberInfoUniqueId = "1001test.@itec.hankyu-hanshin.co.jp";
        String memberInfoMail = "test+memseq10000000.@itec.hankyu-hanshin.co.jp";
        HTypeMemberInfoStatus status = HTypeMemberInfoStatus.ADMISSION;

        MemberInfoEntity entity = new MemberInfoEntity();
        entity.setMemberInfoMail(memberInfoMail);
        entity.setMemberInfoStatus(status);

        // Mock
        doReturn(null).when(memberInfoDao).getEntityByMemberInfoUniqueId(memberInfoUniqueId);
        doReturn(entity).when(memberInfoDao).getEntityByMailStatus(memberInfoMail, status);

        // Verify
        MemberInfoEntity actual = memberInfoGetLogic.executeForProvisional(memberInfoUniqueId, memberInfoMail);
        Assertions.assertEquals(entity, actual);
    }

    @Test
    void executeForProvisionalReturnNull() {
        // Setup data
        String memberInfoUniqueId = "1001test.@itec.hankyu-hanshin.co.jp";
        String memberInfoMail = "test+memseq10000000.@itec.hankyu-hanshin.co.jp";
        HTypeMemberInfoStatus status = HTypeMemberInfoStatus.ADMISSION;

        MemberInfoEntity entity = new MemberInfoEntity();
        entity.setMemberInfoMail(memberInfoMail);
        entity.setMemberInfoStatus(status);
        entity.setMemberInfoUniqueId(memberInfoUniqueId);

        // Mock
        doReturn(entity).when(memberInfoDao).getEntityByMemberInfoUniqueId(memberInfoUniqueId);
        doReturn(entity).when(memberInfoDao).getEntityByMailStatus(memberInfoMail, status);

        // Verify
        MemberInfoEntity actual = memberInfoGetLogic.executeForProvisional(memberInfoUniqueId, memberInfoMail);
        Assertions.assertEquals(null, actual);
    }

    @Test
    void getMemberInfoEntityByMemberInfoIdOrCustomerNo1() {
        // Setup data
        String memberInfoIdOrCustomerNo = "test+memseq10000000@itec.hankyu-hanshin.co.jp";

        MemberInfoEntity entity = new MemberInfoEntity();
        entity.setMemberInfoId(memberInfoIdOrCustomerNo);

        // Mock
        doReturn(entity).when(memberInfoDao).getEntityByMemberInfoMailOrCustomerNo(memberInfoIdOrCustomerNo, null);

        // Verify
        MemberInfoEntity actual =
                        memberInfoGetLogic.getMemberInfoEntityByMemberInfoIdOrCustomerNo(memberInfoIdOrCustomerNo);
        Assertions.assertEquals(entity, actual);
    }

    @Test
    void getMemberInfoEntityByMemberInfoIdOrCustomerNo2() {
        // Setup data
        String memberInfoIdOrCustomerNo = "10000000";
        String memberInfoId = "test+memseq10000000@itec.hankyu-hanshin.co.jp";

        MemberInfoEntity entity = new MemberInfoEntity();
        entity.setMemberInfoId(memberInfoId);

        // Mock
        doReturn(entity).when(memberInfoDao).getEntityByMemberInfoMailOrCustomerNo(memberInfoIdOrCustomerNo, 10000000);

        // Verify
        MemberInfoEntity actual =
                        memberInfoGetLogic.getMemberInfoEntityByMemberInfoIdOrCustomerNo(memberInfoIdOrCustomerNo);
        Assertions.assertEquals(entity, actual);
    }

    @Test
    void getMemberInfoEntityByMemberInfoIdOrCustomerNoException() {
        // Setup data
        String memberInfoIdOrCustomerNo = "+10000.000";

        // Verify
        MemberInfoEntity actual =
                        memberInfoGetLogic.getMemberInfoEntityByMemberInfoIdOrCustomerNo(memberInfoIdOrCustomerNo);
        Assertions.assertEquals(null, actual);
    }

    @Test
    void getMemberInfoEntityByMemberInfoTel() {
        // Setup data
        String memberInfoTel = "0123456789";

        MemberInfoEntity entity = new MemberInfoEntity();
        entity.setMemberInfoTel(memberInfoTel);

        // Mock
        doReturn(entity).when(memberInfoDao).getEntityByMemberInfoTel(memberInfoTel);

        // Verify
        MemberInfoEntity actual = memberInfoGetLogic.getMemberInfoEntityByMemberInfoTel(memberInfoTel);
        Assertions.assertEquals(entity, actual);
    }

    @Test
    void getMemberInfoEntityByMemberInfoTelAndCustomerNo() {
        // Setup data
        Integer customerNo = 123456789;
        String memberInfoTel = "0123456789";

        MemberInfoEntity entity = new MemberInfoEntity();
        entity.setCustomerNo(customerNo);

        // Mock
        doReturn(entity).when(memberInfoDao).getEntityByMemberInfoTelAndCustomerNo(memberInfoTel, customerNo);

        // Verify
        MemberInfoEntity actual = memberInfoGetLogic.getMemberInfoEntityByMemberInfoTelAndCustomerNo(memberInfoTel,
                                                                                                     customerNo.toString()
                                                                                                    );
        Assertions.assertEquals(entity, actual);

        memberInfoDao.getEntityByCustomerNo(customerNo);
    }

    @Test
    void getMemberInfoEntityByCustomerNo() {

        // Setup data
        Integer customerNo = 123456789;

        MemberInfoEntity entity = new MemberInfoEntity();
        entity.setCustomerNo(customerNo);

        // Mock
        doReturn(entity).when(memberInfoDao).getEntityByCustomerNo(customerNo);

        // Verify
        MemberInfoEntity actual = memberInfoGetLogic.getMemberInfoEntityByCustomerNo(customerNo);
        Assertions.assertEquals(entity, actual);

        memberInfoDao.getEntityByCustomerNo(customerNo);
    }
}
