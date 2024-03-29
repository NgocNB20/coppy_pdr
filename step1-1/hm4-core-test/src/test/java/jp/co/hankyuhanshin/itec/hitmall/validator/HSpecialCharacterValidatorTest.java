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
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVSpecialCharacter;
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
public class HSpecialCharacterValidatorTest {

    private Validator validator;

    // デフォルトコンストラクタ
    public HSpecialCharacterValidatorTest() {
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
        result.forEach(r -> Assertions.assertEquals(r.getMessage(), "{HSpecialCharacterValidator.INVALID_detail}"));
    }

    @Test
    // 3.正常（キャラクタ許容）
    public void test3() {
        TestBean3 bean = new TestBean3();
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), true);
    }

    @Test
    // 4.正常（タブ許容）
    public void test4() {
        TestBean4 bean = new TestBean4();
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), true);
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
        @HVSpecialCharacter
        private String v = "test";
    }

    /**
     * アノテーションチェック
     */
    @Data
    private static class TestBean2 {
        @HVSpecialCharacter
        private String v = "\\x1F";
    }

    /**
     * アノテーションチェック
     */
    @Data
    private static class TestBean3 {
        @HVSpecialCharacter(allowCharacters = '!')
        private String v = "!";
    }

    /**
     * アノテーションチェック
     */
    @Data
    private static class TestBean4 {
        @HVSpecialCharacter(allowPunctuation = true)
        private String v = "\\x0A";
    }

    /**
     * アノテーションチェック
     */
    @Data
    private static class TestBean5 {
        @HVSpecialCharacter(allowPunctuation = true)
        private String v = null;
    }

    /**
     * アノテーションチェック
     */
    @Data
    private static class TestBean6 {
        @HVSpecialCharacter(allowPunctuation = true)
        private String v = "";
    }
}
