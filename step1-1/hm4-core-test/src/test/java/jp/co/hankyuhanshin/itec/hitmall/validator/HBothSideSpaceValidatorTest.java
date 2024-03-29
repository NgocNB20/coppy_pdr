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
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVBothSideSpace;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.SpaceValidateMode;
import lombok.Data;

/**
 * 正常ケース、異常ケースの２パターンのみ検証<br/>
 * <p>
 * 作成日：2021/03/26
 *
 * @author kimura
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Data
public class HBothSideSpaceValidatorTest {

    // 1.正常
    private String value1 = "テスト";

    // 2.正常（先頭スペース許容）
    private String value2 = " テスト";

    // 3.正常（末尾スペース許容）
    private String value3 = "テスト　";

    // 4.正常（全てスペース許容）
    private String value4 = " テスト　";

    // 5.異常
    private String value5 = " テスト　";

    // 6.異常（先頭スペース許容）
    private String value6 = "テスト　";

    // 7.異常（末尾スペース許容）
    private String value7 = " テスト";

    private Validator validator;

    // デフォルトコンストラクタ
    public HBothSideSpaceValidatorTest() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    // 1.正常
    public void test1() {
        TestBean1 bean = new TestBean1(value1);
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), true);
    }

    @Test
    // 2.正常（先頭スペース許容）
    public void test2() {
        TestBean2 bean = new TestBean2(value2);
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), true);
    }

    @Test
    // 3.正常（末尾スペース許容）
    public void test3() {
        TestBean3 bean = new TestBean3(value3);
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), true);
    }

    @Test
    // 4.正常（全てスペース許容）
    public void test4() {
        TestBean4 bean = new TestBean4(value4);
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), true);
    }

    @Test
    // 5.異常
    public void test5() {
        TestBean5 bean = new TestBean5(value5);
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), false);
        result.forEach(r -> Assertions.assertEquals(r.getMessage(), "{HBothSideSpaceValidator.INVALID_detail}"));
    }

    @Test
    // 6.異常（先頭スペース許容）
    public void test6() {
        TestBean6 bean = new TestBean6(value6);
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), false);
        result.forEach(r -> Assertions.assertEquals(r.getMessage(),
                                                    "{HBothSideSpaceValidator.DENY_RIGHT_SIDE_SPACE.INVALID_detail}"
                                                   ));
    }

    @Test
    // 7.異常（末尾スペース許容）
    public void test7() {
        TestBean7 bean = new TestBean7(value7);
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), false);
        result.forEach(r -> Assertions.assertEquals(r.getMessage(),
                                                    "{HBothSideSpaceValidator.DENY_LEFT_SIDE_SPACE.INVALID_detail}"
                                                   ));
    }

    /**
     * アノテーションチェック
     */
    @Data
    private static class TestBean1 {
        @HVBothSideSpace()
        private String v;

        TestBean1(String v) {
            this.v = v;
        }
    }

    /**
     * アノテーションチェック
     */
    @Data
    private static class TestBean2 {
        @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE)
        private String v;

        TestBean2(String v) {
            this.v = v;
        }
    }

    /**
     * アノテーションチェック
     */
    @Data
    private static class TestBean3 {
        @HVBothSideSpace(endWith = SpaceValidateMode.ALLOW_SPACE)
        private String v;

        TestBean3(String v) {
            this.v = v;
        }
    }

    /**
     * アノテーションチェック
     */
    @Data
    private static class TestBean4 {
        @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE)
        private String v;

        TestBean4(String v) {
            this.v = v;
        }
    }

    /**
     * アノテーションチェック
     */
    @Data
    private static class TestBean5 {
        @HVBothSideSpace()
        private String v;

        TestBean5(String v) {
            this.v = v;
        }
    }

    /**
     * アノテーションチェック
     */
    @Data
    private static class TestBean6 {
        @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE)
        private String v;

        TestBean6(String v) {
            this.v = v;
        }
    }

    /**
     * アノテーションチェック
     */
    @Data
    private static class TestBean7 {
        @HVBothSideSpace(endWith = SpaceValidateMode.ALLOW_SPACE)
        private String v;

        TestBean7(String v) {
            this.v = v;
        }
    }
}
