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
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVMailAddressExtended;
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
public class HMailAddressExtendedValidatorTest {

    private Validator validator;

    // デフォルトコンストラクタ
    public HMailAddressExtendedValidatorTest() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    // 1.異常
    public void test1() {
        TestBean1 bean = new TestBean1();
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), false);
        result.forEach(r -> Assertions.assertEquals(r.getMessage(), "{HMailAddressExtendedValidator.INVALID_detail}"));
    }

    @Test
    // 2.正常
    public void test2() {
        TestBean2 bean = new TestBean2();
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), true);
    }

    @Test
    // 3.異常
    public void test3() {
        TestBean3 bean = new TestBean3();
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), false);
        result.forEach(r -> Assertions.assertEquals(
                        r.getMessage(), "{HMailAddressExtendedValidator.NOT_HANKAKU_detail}"));
    }

    @Test
    // 4.異常
    public void test4() {
        TestBean4 bean = new TestBean4();
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), false);
        result.forEach(r -> Assertions.assertEquals(r.getMessage(), "{HMailAddressExtendedValidator.INVALID_detail}"));
    }

    /**
     * アノテーションチェック
     */
    @Data
    private static class TestBean1 {
        @HVMailAddressExtended
        private String v = "kimura-sh@itec.hankyu-hanshin.co.jp,kimura-shtest@itec.hankyu-hanshin.co.jp";
    }

    /**
     * アノテーションチェック
     */
    @Data
    private static class TestBean2 {
        @HVMailAddressExtended
        private String v = "管理<info@dev3.hit-mall.jp>";
    }

    /**
     * アノテーションチェック
     */
    @Data
    private static class TestBean3 {
        @HVMailAddressExtended
        private String v = "テスト@itec.hankyu-hanshin.co.jp";
    }

    /**
     * アノテーションチェック
     */
    @Data
    private static class TestBean4 {
        @HVMailAddressExtended
        private String v = "test";
    }

}
