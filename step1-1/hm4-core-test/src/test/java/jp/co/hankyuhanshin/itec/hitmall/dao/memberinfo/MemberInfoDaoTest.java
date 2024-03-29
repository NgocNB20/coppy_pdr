package jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCardRegistType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSex;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSexUnnecessaryAnswer;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.MemberCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.MemberInfoSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MemberInfoDaoTest {

    @Autowired
    MemberInfoDao memberInfoDao;

    @Test
    @Order(1)
    public void insert() {
        MemberInfoEntity entity = makeMemberInfoEntity();
        int result = memberInfoDao.insert(entity);
        Assertions.assertNotNull(memberInfoDao.getEntity(999));
    }

    @Test
    @Order(2)
    public void get() {
        MemberInfoSearchForDaoConditionDto dto = new MemberInfoSearchForDaoConditionDto();
        dto.setShopSeq(999);
        dto.setMemberInfoSeq(999);
        dto.setMemberInfoSex(HTypeSex.MALE);
        List<MemberInfoEntity> result = memberInfoDao.getMemberInfoConditionDtoList(dto, SelectOptions.get());
        Assertions.assertNotNull(result);
    }

    @Test
    @Order(3)
    public void delete() {
        MemberInfoEntity entity = memberInfoDao.getEntity(999);
        int result = memberInfoDao.delete(entity);
        Assertions.assertNull(memberInfoDao.getEntity(999));
    }

    @Test
    @Order(4)
    public void getStream() {
        MemberInfoSearchForDaoConditionDto dto = new MemberInfoSearchForDaoConditionDto();
        dto.setShopSeq(1001);
        Stream<MemberCsvDto> result = memberInfoDao.exportCsvByMemberInfoSearchForDaoConditionDtoStream(dto);

        Iterator<MemberCsvDto> resultIterator = result.iterator();
        while (resultIterator.hasNext()) {
            System.out.println(resultIterator.next().getMemberInfoSeq());
        }
        result.close();
        Assertions.assertNotNull(result);
    }

    private MemberInfoEntity makeMemberInfoEntity() {
        MemberInfoEntity entity = new MemberInfoEntity();
        entity.setMemberInfoSeq(999);
        entity.setMemberInfoStatus(HTypeMemberInfoStatus.ADMISSION);
        entity.setMemberInfoUniqueId("1");
        entity.setMemberInfoId("1");
        entity.setMemberInfoPassword("1");
        entity.setMemberInfoLastName("1");
        entity.setMemberInfoFirstName("1");
        entity.setMemberInfoLastKana("1");
        entity.setMemberInfoFirstKana("1");
        entity.setMemberInfoSex(HTypeSexUnnecessaryAnswer.MALE);
        entity.setMemberInfoBirthday(Timestamp.valueOf("2099-02-03 11:11:11.0"));
        entity.setMemberInfoTel("1");
        entity.setMemberInfoContactTel("1");
        entity.setMemberInfoZipCode("1");
        entity.setMemberInfoPrefecture("1");
        entity.setPrefectureType(HTypePrefectureType.YAMAGATA);
        entity.setMemberInfoAddress1("1");
        entity.setMemberInfoAddress2("1");
        entity.setMemberInfoAddress3("1");
        entity.setMemberInfoMail("1");
        entity.setShopSeq(999);
        entity.setAccessUid("1");
        entity.setVersionNo(999);
        entity.setAdmissionYmd("1");
        entity.setSecessionYmd("1");
        entity.setMemo("1");
        entity.setMemberInfoFax("1");
        entity.setLastLoginTime(Timestamp.valueOf("2099-02-03 11:11:11.0"));
        entity.setLastLoginUserAgent("1");
        entity.setPaymentMemberId("1");
        entity.setPaymentCardRegistType(HTypeCardRegistType.REGISTERED);
        entity.setRegistTime(Timestamp.valueOf("2099-02-03 11:11:11.0"));
        entity.setUpdateTime(Timestamp.valueOf("2099-02-03 11:11:11.0"));

        return entity;
    }
}
