package jp.co.hankyuhanshin.itec.hitmall.util.common;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAdministratorStatus;

/**
 * 区分値自動生成<br/>
 * Enum用共通処理の移行結果テスト<br/>
 * https://docs.google.com/spreadsheets/d/1acvG1WUk32e2HiGHCgOodDpUYdkcOkHkLbZ8MkGSvgA/edit#gid=759837971<br/>
 * 上記資料の使用方法に基づく記述でテスト<br/>
 * <p>
 * 作成日：2021/03/16
 *
 * @author kimura
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EnumTypeUtilTest {

    /**
     * map(key = "value", value = "label")の形で取得できていることを確認
     */
    @Test
    @Order(1)
    public void executeTest() {
        // getEnumLabelValueMap
        Map<String, String> map = EnumTypeUtil.getEnumMap(HTypeAdministratorStatus.class);
        Assertions.assertEquals(map.size(), 2);
        Assertions.assertEquals(map.containsKey("0"), true);
        Assertions.assertEquals(map.get("0"), "使用中");
        Assertions.assertEquals(map.containsKey("1"), true);
        Assertions.assertEquals(map.get("1"), "停止中");

    }
}
