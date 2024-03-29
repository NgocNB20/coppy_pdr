package jp.co.hankyuhanshin.itec.hitmall.validator;

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
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVDate;
import lombok.Data;

/**
 * 正常ケース、異常ケースの２パターンのみ検証<br/>
 * <p>
 * 作成日：2021/03/29
 *
 * @author kimura
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Data
public class HVDateValidatorTest {

    private Validator validator;

    // デフォルトコンストラクタ
    public HVDateValidatorTest() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    // 1.正常
    public void test1() {
        TestBean1 bean = new TestBean1();
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), true);
    }

    @Test
    // 2.異常
    public void test2() {
        TestBean2 bean = new TestBean2();
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), false);
        result.forEach(r -> Assertions.assertEquals(r.getMessage(), "{HDateValidator.NOT_DATE_detail}"));
    }

    @Test
    // 3.正常
    public void test3() {
        TestBean3 bean = new TestBean3();
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), true);
    }

    @Test
    // 4.異常
    public void test4() {
        TestBean4 bean = new TestBean4();
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), false);
        result.forEach(r -> Assertions.assertEquals(r.getMessage(), "{HDateValidator.NOT_DATE_detail}"));
    }

    @Test
    // 5.正常
    public void test5() {
        TestBean5 bean = new TestBean5();
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), true);
    }

    @Test
    // 6.正常
    public void test6() {
        TestBean6 bean = new TestBean6();
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), true);
    }

    /**
     * アノテーションチェック
     */
    @Data
    private static class TestBean1 {
        @HVDate
        private String v = "1996/04/16";
    }

    /**
     * アノテーションチェック
     */
    @Data
    private static class TestBean2 {
        @HVDate
        private String v = "19960416";
    }

    /**
     * アノテーションチェック
     */
    @Data
    private static class TestBean3 {
        @HVDate(pattern = "yyyyMMdd")
        private String v = "19960416";
    }

    /**
     * アノテーションチェック
     */
    @Data
    private static class TestBean4 {
        @HVDate(pattern = "yyyyMMdd")
        private String v = "testtest";
    }

    /**
     * アノテーションチェック
     */
    @Data
    private static class TestBean5 {
        @HVDate(pattern = "yyyyMMdd")
        private String v = "";
    }

    /**
     * アノテーションチェック
     */
    @Data
    private static class TestBean6 {
        @HVDate(pattern = "yyyyMMdd")
        private String v = null;
    }
}
