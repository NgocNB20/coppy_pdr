package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.MemberInfoDao;
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
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MemberInfoResultSearchLogicTest {

    @Autowired
    MemberInfoResultSearchLogic memberInfoResultSearchLogic;

    @MockBean
    MemberInfoDao memberInfoDao;

    @Test
    @Order(1)
    public void execute() {
        List<MemberInfoEntity> memberInfoEntityList = new ArrayList<>();
        MemberInfoEntity entity = new MemberInfoEntity();
        entity.setMemberInfoSeq(999);
        memberInfoEntityList.add(entity);

        doReturn(memberInfoEntityList).when(memberInfoDao)
                                      .getMemberInfoConditionDtoList(any(MemberInfoSearchForDaoConditionDto.class),
                                                                     any(SelectOptions.class)
                                                                    );

        MemberInfoSearchForDaoConditionDto dto = new MemberInfoSearchForDaoConditionDto();
        List<MemberInfoEntity> result = memberInfoResultSearchLogic.execute(dto);

        verify(memberInfoDao, times(1)).getMemberInfoConditionDtoList(
                        any(MemberInfoSearchForDaoConditionDto.class), any(SelectOptions.class));
        Assertions.assertEquals(memberInfoEntityList, result);
    }
}
