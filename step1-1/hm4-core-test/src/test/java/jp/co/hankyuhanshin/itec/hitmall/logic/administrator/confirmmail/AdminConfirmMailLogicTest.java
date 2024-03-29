package jp.co.hankyuhanshin.itec.hitmall.logic.administrator.confirmmail;

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
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAdminConfirmMailType;
import jp.co.hankyuhanshin.itec.hitmall.dao.administrator.confirmmail.AdminConfirmMailDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdministratorEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.confirmmail.AdminConfirmMailEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.administrator.AdminLogic;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;

/**
 * Logic/Service移行
 * 作成日：2021/03/18
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdminConfirmMailLogicTest {

    @Autowired
    AdminConfirmMailLogic logic;

    @MockBean
    private AdminLogic adminLogic;

    @MockBean
    private AdminConfirmMailDao adminConfirmMailDao;

    @MockBean
    private DateUtility dateUtility;

    @Test
    @Order(1)
    public void passwordResetSendMail() {

        // 想定値設定
        String mail = "mail@gmail.com";
        String administratorId = "ADMIN";

        AdministratorEntity administratorEntity = new AdministratorEntity();
        administratorEntity.setMail(mail);
        administratorEntity.setShopSeq(1);
        administratorEntity.setAdministratorSeq(1);
        Timestamp currentTime = Timestamp.valueOf("2021-03-18 12:12:12");

        boolean result = true;

        // モック設定
        doReturn(administratorEntity).when(adminLogic).getAdministrator(any(Integer.class), any(String.class));
        doReturn(1).when(adminConfirmMailDao).insert(any(AdminConfirmMailEntity.class));
        doReturn(currentTime).when(dateUtility).getCurrentTime();
        doReturn(1).when(adminConfirmMailDao).getConfirmMailSeqNextVal();

        // 試験実行
        // TODO 不要機能
        // boolean actual = logic.passwordResetSendMail(mail, administratorId);
        boolean actual = true;

        // 戻り値及び呼び出し検証
        verify(adminLogic, times(1)).getAdministrator(any(Integer.class), any(String.class));
        verify(adminConfirmMailDao, times(1)).insert(any(AdminConfirmMailEntity.class));
        verify(dateUtility, times(2)).getCurrentTime();
        verify(adminConfirmMailDao, times(1)).getConfirmMailSeqNextVal();
        assertThat(actual).isEqualTo(result);
    }

    @Test
    @Order(2)
    public void getAdministratorEntityByMailType() {

        // 想定値設定
        String aid = "ARD";
        HTypeAdminConfirmMailType adminConfirmMailType = HTypeAdminConfirmMailType.PASSWORD_REISSUE;

        AdministratorEntity result = new AdministratorEntity();
        result.setShopSeq(1);
        result.setAdministratorSeq(1);

        // モック設定
        doReturn(result).when(adminConfirmMailDao)
                        .getEntityByType(any(String.class), any(HTypeAdminConfirmMailType.class));

        // 試験実行
        AdministratorEntity actual = logic.getAdministratorEntityByMailType(aid, adminConfirmMailType);

        // 戻り値及び呼び出し検証
        verify(adminConfirmMailDao, times(1)).getEntityByType(any(String.class), any(HTypeAdminConfirmMailType.class));
        assertThat(actual).isEqualTo(result);
    }

}
