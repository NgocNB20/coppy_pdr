package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.confirmmail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeConfirmMailType;
import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.confirmmail.ConfirmMailDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.confirmmail.ConfirmMailEntity;

/**
 * Logic/Service移行：確認メール情報取得
 * 作成日：2021/03/11
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ConfirmMailGetLogicTest {

    @Autowired
    ConfirmMailGetLogic logic;

    @MockBean
    private ConfirmMailDao dao;

    @Test
    @Order(1)
    public void execute1Test() {

        // 想定値設定
        String password = "test@gmail.com";
        ConfirmMailEntity result = new ConfirmMailEntity();

        // モック設定
        doReturn(result).when(dao).getEntityByPassword(any(String.class));

        // 試験実行
        ConfirmMailEntity actual = logic.execute(password);

        // 戻り値及び呼び出し検証
        verify(dao, times(1)).getEntityByPassword(any(String.class));
        assertThat(actual).isEqualTo(result);
    }

    @Test
    @Order(2)
    public void execute2Test() {

        // 想定値設定
        String password = "test@gmail.com";
        HTypeConfirmMailType confirmMailType = HTypeConfirmMailType.MEMBERINFO_MAIL_UPDATE;
        ConfirmMailEntity result = new ConfirmMailEntity();

        // モック設定
        doReturn(result).when(dao).getEntityByType(any(String.class), any(HTypeConfirmMailType.class));

        // 試験実行
        ConfirmMailEntity actual = logic.execute(password, confirmMailType);

        // 戻り値及び呼び出し検証
        verify(dao, times(1)).getEntityByType(any(String.class), any(HTypeConfirmMailType.class));
        assertThat(actual).isEqualTo(result);
    }
}
