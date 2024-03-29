package jp.co.hankyuhanshin.itec.hitmall.validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVRItems;
import lombok.Data;

/**
 * 正常ケース、異常ケースの２パターンのみ検証<br/>
 * 作成日：2021/03/26
 *
 * @author kimura
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HItemsRValidatorTest {

    public String value1 = "0";
    public String value2 = "A";
    public String[] value3 = {"A", "B"};
    public String[] value4 = {"0", null};
    private Validator validator;

    // デフォルトコンストラクタ
    public HItemsRValidatorTest() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    // 1.正常ケース
    public void test1() {
        TestBean1 bean = new TestBean1(value1);
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), true);
    }

    @Test
    // 2.異常ケース
    public void test2() {
        TestBean2 bean = new TestBean2(value2);
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), false);
        result.forEach(r -> Assertions.assertEquals(r.getMessage(), "{HItemsValidator.INVALID_detail}"));
    }

    @Test
    // 3.異常ケース
    public void test3() {
        TestBean3 bean = new TestBean3(value3);
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), false);
        result.forEach(r -> Assertions.assertEquals(r.getMessage(), "{HItemsValidator.INVALID_detail}"));
    }

    @Test
    // 4.異常ケース
    public void test4() {
        TestBean4 bean = new TestBean4(value4);
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), false);
        result.forEach(r -> Assertions.assertEquals(r.getMessage(), "{HItemsValidator.INVALID_detail}"));
    }

    /**
     * アノテーションチェック
     */
    @Data
    @HVRItems(target = "value", comparison = "value1")
    public class TestBean1 {
        private String value;
        private Map<String, String> value1 = new HashMap<String, String>();

        TestBean1(String value) {
            this.value = value;
            value1.put("0", "日本語表示");
            value1.put("1", "数値表示");
        }
    }

    /**
     * アノテーションチェック
     */
    @Data
    @HVRItems(target = "value", comparison = "value1")
    public class TestBean2 {
        private String value;
        private Map<String, String> value1 = new HashMap<String, String>();

        TestBean2(String value) {
            this.value = value;
            value1.put("0", "日本語表示");
            value1.put("1", "数値表示");
        }
    }

    /**
     * アノテーションチェック
     */
    @Data
    @HVRItems(target = "value", comparison = "value1")
    public class TestBean3 {
        private String[] value;
        private Map<String, String> value1 = new HashMap<String, String>();

        TestBean3(String[] value) {
            this.value = value;
            value1.put("0", "日本語表示");
            value1.put("1", "数値表示");
        }
    }

    /**
     * アノテーションチェック
     */
    @Data
    @HVRItems(target = "value", comparison = "value1")
    public class TestBean4 {
        private String[] value;
        private Map<String, String> value1 = new HashMap<String, String>();

        TestBean4(String[] value) {
            this.value = value;
            value1.put("日本語表示", "0");
            value1.put("数値表示", "1");
        }
    }
}
