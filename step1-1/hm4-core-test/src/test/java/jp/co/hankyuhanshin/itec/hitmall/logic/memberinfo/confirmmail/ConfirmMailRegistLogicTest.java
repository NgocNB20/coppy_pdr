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
import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.confirmmail.ConfirmMailDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.confirmmail.ConfirmMailEntity;
import jp.co.hankyuhanshin.itec.hmbase.helper.crypto.MD5Helper;

/**
 * Logic/Service移行：確認メール情報登録
 * 作成日：2021/03/11
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ConfirmMailRegistLogicTest {

    @Autowired
    ConfirmMailRegistLogic logic;

    @MockBean
    private ConfirmMailDao dao;

    @MockBean
    private MD5Helper helper;

    @Test
    @Order(1)
    public void execute1Test() {

        // 想定値設定
        ConfirmMailEntity confirmMailEntity = new ConfirmMailEntity();
        Integer confirmMailSeq = 1;
        String password = "whjfojaiowfjaiof";
        int result = 1;

        // モック設定
        doReturn(result).when(dao).insert(any(ConfirmMailEntity.class));
        doReturn(confirmMailSeq).when(dao).getConfirmMailSeqNextVal();
        doReturn(password).when(helper).createHash(any(String.class));

        // 試験実行
        int actual = logic.execute(confirmMailEntity);

        // 戻り値及び呼び出し検証
        verify(dao, times(1)).insert(any(ConfirmMailEntity.class));
        verify(dao, times(1)).getConfirmMailSeqNextVal();
        verify(helper, times(1)).createHash(any(String.class));
        assertThat(actual).isEqualTo(result);
    }

}
