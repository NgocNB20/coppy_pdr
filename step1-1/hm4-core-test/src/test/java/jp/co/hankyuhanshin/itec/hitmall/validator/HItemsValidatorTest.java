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
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVItems;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeFreeAreaOpenStatus;

/**
 * 区分値不正チェック<br/>
 * バリデータチェック＋アノテーションの動作確認<br/>
 * ※この時点ではConstraintValidatorContextを未使用のため、nullで検証<br/>
 * https://docs.google.com/spreadsheets/d/1acvG1WUk32e2HiGHCgOodDpUYdkcOkHkLbZ8MkGSvgA/edit#gid=759837971<br/>
 * 上記資料の使用方法に基づく記述でテスト<br/>
 * <p>
 * 作成日：2021/03/17
 *
 * @author kimura
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HItemsValidatorTest {

    // 1.正常
    private String value = "0";

    // 2.異常（不正値）
    private String value_illegal = "999";

    // 3.正業（空） ※チェックされないため
    private String value_illegal2 = "";

    // 4.正常（null） ※チェックされないため
    private String value_illegal3 = null;

    // 5.複数正常
    private String[] values = {"0", "1", "2"};

    // 6.複数正常
    private String[] values2 = {"0", "1"};

    // 7.複数異常（不正値）
    private String[] values_illegal = {"999", "1", "2"};

    // 8.複数異常（不正値）
    private String[] values_illegal2 = {"0", "999", "2"};

    // 9.複数異常（不正値）
    private String[] values_illegal3 = {"0", "1", "999"};

    // 10.複数異常（空）
    private String[] values_illegal4 = {"0", "", "2"};

    // 11.複数異常（null）
    private String[] values_illegal5 = {"0", null, "2"};

    // 12.複数正常（順番）
    private String[] values_order = {"0", "2", "1",};

    // 13.複数正常（順番）
    private String[] values_order2 = {"1", "2"};

    // 14.複数異常（不正値＋順番）
    private String[] values_illegalAndOrder = {"0", "2", "999"};

    // 15.複数異常（数不一致）
    private String[] values_illegalElementCount = {"0", "1", "2", "999"};

    private Validator validator;

    // デフォルトコンストラクタ
    public HItemsValidatorTest() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    // 1.正常 "0"
    public void test1() {
        TestBean bean = new TestBean(value);
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), true);
    }

    @Test
    // 2.異常（不正値） "999"
    public void test2() {
        TestBean bean = new TestBean(value_illegal);
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), false);
        // result.forEach(r -> Assertions.assertEquals(r.getMessage(),
        // "選択した区分値が不正です。"));
    }

    @Test
    // 3.正常（空） ※チェックされないため
    public void test3() {
        TestBean bean = new TestBean(value_illegal2);
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), true);
    }

    @Test
    // 4.正常（null） ※チェックされないため
    public void test4() {
        TestBean bean = new TestBean(value_illegal3);
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), true);
    }

    @Test
    // 5.複数正常 { "0", "1", "2" }
    public void test5() {
        TestBean2 bean = new TestBean2(values);
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), true);
    }

    @Test
    // 6.複数正常 { "0", "1" }
    public void test6() {
        TestBean2 bean = new TestBean2(values2);
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), true);
    }

    @Test
    // 7.複数異常（不正値） { "999", "1", "2" }
    public void test7() {
        TestBean2 bean = new TestBean2(values_illegal);
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), false);
        // result.forEach(r -> Assertions.assertEquals(r.getMessage(),
        // "選択した区分値が不正です。"));
    }

    @Test
    // 8.複数異常（不正値） { "0", "999", "2" }
    public void test8() {
        TestBean2 bean = new TestBean2(values_illegal2);
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), false);
        // result.forEach(r -> Assertions.assertEquals(r.getMessage(),
        // "選択した区分値が不正です。"));
    }

    @Test
    // 9.複数異常（不正値） { "0", "1", "999" }
    public void test9() {
        TestBean2 bean = new TestBean2(values_illegal3);
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), false);
        // esult.forEach(r -> Assertions.assertEquals(r.getMessage(),
        // "選択した区分値が不正です。"));
    }

    @Test
    // 10.複数異常（空） { "0", "", "2" }
    public void test10() {
        TestBean2 bean = new TestBean2(values_illegal4);
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), false);
        // result.forEach(r -> Assertions.assertEquals(r.getMessage(),
        // "選択した区分値が不正です。"));
    }

    @Test
    // 11.複数異常（null） { "0", null, "2" }
    public void test11() {
        TestBean2 bean = new TestBean2(values_illegal5);
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), false);
        // result.forEach(r -> Assertions.assertEquals(r.getMessage(),
        // "選択した区分値が不正です。"));
    }

    @Test
    // 12.複数正常（順番） { "0", "2", "1", }
    public void test12() {
        TestBean2 bean = new TestBean2(values_order);
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), true);
    }

    @Test
    // 13.複数正常（順番） { "1", "2" }
    public void test13() {
        TestBean2 bean = new TestBean2(values_order2);
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), true);
    }

    @Test
    // 14.複数異常（不正値＋順番） { "0", "2", "999" }
    public void test14() {
        TestBean2 bean = new TestBean2(values_illegalAndOrder);
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), false);
        // result.forEach(r -> Assertions.assertEquals(r.getMessage(),
        // "選択した区分値が不正です。"));
    }

    @Test
    // 15.複数異常（数不一致） { "0", "1", "2", "999" }
    public void test15() {
        TestBean2 bean = new TestBean2(values_illegalElementCount);
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), false);
        // result.forEach(r -> Assertions.assertEquals(r.getMessage(),
        // "選択した区分値が不正です。"));
    }

    /**
     * アノテーションチェック
     */
    private static class TestBean {
        @HVItems(target = HTypeFreeAreaOpenStatus.class)
        private String labebl;

        TestBean(String labebl) {
            this.labebl = labebl;
        }
    }

    /**
     * アノテーションチェック ※複数パターン
     */
    private static class TestBean2 {
        @HVItems(target = HTypeFreeAreaOpenStatus.class)
        private String[] labebl;

        TestBean2(String[] labebl) {
            this.labebl = labebl;
        }
    }
}
