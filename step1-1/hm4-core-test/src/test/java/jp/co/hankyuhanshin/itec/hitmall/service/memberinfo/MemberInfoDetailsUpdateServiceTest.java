package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.MemberInfoDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.favorite.FavoriteListDeleteLogic;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MemberInfoDetailsUpdateServiceTest {

    @Autowired
    MemberInfoDetailsUpdateService memberInfoDetailsUpdateService;

    @MockBean
    MemberInfoUpdateService memberInfoUpdateService;

    @MockBean
    FavoriteListDeleteLogic favoriteListDeleteLogic;

    @MockBean
    MemberInfoGetLogic memberInfoGetLogic;

    @Test
    @Order(1)
    public void execute() {
        MemberInfoDetailsDto memberInfoDetailsDto = new MemberInfoDetailsDto();
        MemberInfoEntity memberInfoEntity = new MemberInfoEntity();
        memberInfoEntity.setMemberInfoSeq(1000);
        memberInfoEntity.setMemberInfoStatus(HTypeMemberInfoStatus.REMOVE);
        memberInfoDetailsDto.setMemberInfoEntity(memberInfoEntity);

        MemberInfoEntity memberInfoEntityBase = new MemberInfoEntity();
        memberInfoEntityBase.setMemberInfoSeq(1000);
        memberInfoEntityBase.setMemberInfoStatus(HTypeMemberInfoStatus.ADMISSION);

        doReturn(memberInfoEntityBase).when(memberInfoGetLogic).execute(anyInt());

        int processeCountExpect = 1;
        doReturn(processeCountExpect).when(memberInfoUpdateService).execute(any(), any());

        int actual = memberInfoDetailsUpdateService.execute(memberInfoDetailsDto);
        verify(memberInfoGetLogic, times(1)).execute(anyInt());
        verify(favoriteListDeleteLogic, times(1)).execute(anyInt());
        verify(memberInfoUpdateService, times(1)).execute(any(), any());

        Assertions.assertEquals(processeCountExpect, actual);

    }

    @Test
    @Order(2)
    public void executeNo2() {
        MemberInfoDetailsDto memberInfoDetailsDto = new MemberInfoDetailsDto();
        MemberInfoEntity memberInfoEntity = new MemberInfoEntity();
        memberInfoEntity.setMemberInfoSeq(1000);
        memberInfoEntity.setMemberInfoStatus(HTypeMemberInfoStatus.ADMISSION);
        memberInfoDetailsDto.setMemberInfoEntity(memberInfoEntity);

        MemberInfoEntity memberInfoEntityBase = new MemberInfoEntity();
        memberInfoEntityBase.setMemberInfoSeq(1000);
        memberInfoEntityBase.setMemberInfoStatus(HTypeMemberInfoStatus.REMOVE);

        doReturn(memberInfoEntityBase).when(memberInfoGetLogic).execute(anyInt());

        int processeCountExpect = 1;
        doReturn(processeCountExpect).when(memberInfoUpdateService).execute(any(), any());

        int actual = memberInfoDetailsUpdateService.execute(memberInfoDetailsDto);

        verify(memberInfoGetLogic, times(1)).execute(anyInt());
        verify(memberInfoUpdateService, times(1)).execute(any(), any());
        Assertions.assertEquals(processeCountExpect, actual);

    }
}
