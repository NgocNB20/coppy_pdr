/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */
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
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVRDateCombo;
import lombok.Data;

/**
 * 正常ケース、異常ケースの２パターンのみ検証<br/>
 * 作成日：2021/03/29
 * <p>
 * ※注意<br/>
 * テスト作成時はコンバータが未対応のため、値が「1999/6/12」だとフォーマットエラーとなる<br/>
 * 正しくは「1999/06/12」
 *
 * @author kimura
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HDateComboValidatorTest {

    private Validator validator;

    // デフォルトコンストラクタ
    public HDateComboValidatorTest() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    // 1.正常ケース
    public void test1() {
        TestBean1 bean = new TestBean1();
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), true);
    }

    // 2.正常ケース
    @Test
    public void test2() {
        TestBean2 bean = new TestBean2();
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), true);
    }

    // 3.異常ケース
    @Test
    public void test3() {
        TestBean3 bean = new TestBean3();
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), false);
        result.forEach(r -> Assertions.assertEquals(r.getMessage(), "{HDateComboValidator.UNPARSABLE_detail}"));
    }

    // 4.異常ケース
    @Test
    public void test4() {
        TestBean4 bean = new TestBean4();
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), false);
        result.forEach(r -> Assertions.assertEquals(r.getMessage(), "{HDateComboValidator.LESS_EQUAL_detail}"));
    }

    // 5.異常ケース
    @Test
    public void test5() {
        TestBean5 bean = new TestBean5();
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), false);
        result.forEach(r -> Assertions.assertEquals(r.getMessage(), "{HDateComboValidator.LESS_EQUAL_detail}"));
    }

    // 6.正常ケース
    @Test
    public void test6() {
        TestBean6 bean = new TestBean6();
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), true);
    }

    // 7.正常ケース
    @Test
    public void test7() {
        TestBean7 bean = new TestBean7();
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), true);
    }

    // 8.正常ケース
    @Test
    public void test8() {
        TestBean8 bean = new TestBean8();
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), true);
    }

    // 9.異常ケース
    @Test
    public void test9() {
        TestBean9 bean = new TestBean9();
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), false);
        result.forEach(r -> Assertions.assertEquals(r.getMessage(), "{HDateComboValidator.UNPARSABLE_detail}"));
    }

    // 10.正常ケース
    @Test
    public void test10() {
        TestBean10 bean = new TestBean10();
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), true);
    }

    /**
     * アノテーションチェック
     */
    @Data
    @HVRDateCombo(dateLeftTarget = "value", timeLeftTarget = "value1", dateRightTarget = "value2",
                  timeRightTarget = "value3")
    public class TestBean1 {
        private String value = "1996/04/16";
        private String value1 = "22:22:22";
        private String value2 = "1996/05/16";
        private String value3 = "00:00:00";
    }

    /**
     * アノテーションチェック
     */
    @Data
    @HVRDateCombo(dateLeftTarget = "value", timeLeftTarget = "value1", dateRightTarget = "value2",
                  timeRightTarget = "value3")
    public class TestBean2 {
        private String value = "1996/04/16";
        private String value1 = "00:00:00";
        private String value2 = "1996/04/16";
        private String value3 = "22:22:22";
    }

    /**
     * アノテーションチェック
     */
    @Data
    @HVRDateCombo(dateLeftTarget = "value", timeLeftTarget = "value1", dateRightTarget = "value2",
                  timeRightTarget = "value3")
    public class TestBean3 {
        private String value = "test";
        private String value1 = "00:00:00";
        private String value2 = "1996/04/16";
        private String value3 = "22:22:22";
    }

    /**
     * アノテーションチェック
     */
    @Data
    @HVRDateCombo(dateLeftTarget = "value", timeLeftTarget = "value1", dateRightTarget = "value2",
                  timeRightTarget = "value3")
    public class TestBean4 {
        private String value = "1996/04/16";
        private String value1 = "";
        private String value2 = "1996/04/15";
        private String value3 = "";
    }

    /**
     * アノテーションチェック
     */
    @Data
    @HVRDateCombo(dateLeftTarget = "value", timeLeftTarget = "value1", dateRightTarget = "value2",
                  timeRightTarget = "value3")
    public class TestBean5 {
        private String value = "1996/04/16";
        private String value1 = "22:22:22";
        private String value2 = "1996/04/16";
        private String value3 = "00:00:00";
    }

    /**
     * アノテーションチェック
     */
    @Data
    @HVRDateCombo(dateLeftTarget = "value", timeLeftTarget = "value1", dateRightTarget = "value2",
                  timeRightTarget = "value3", operator = ">=")
    public class TestBean6 {
        private String value = "1996/04/16";
        private String value1 = "22:22:22";
        private String value2 = "1996/04/16";
        private String value3 = "00:00:00";
    }

    /**
     * アノテーションチェック
     */
    @Data
    @HVRDateCombo(dateLeftTarget = "value", timeLeftTarget = "value1", dateRightTarget = "value2",
                  timeRightTarget = "value3")
    public class TestBean7 {
        private String value = "";
        private String value1 = "";
        private String value2 = "";
        private String value3 = "";
    }

    /**
     * アノテーションチェック
     */
    @Data
    @HVRDateCombo(dateLeftTarget = "value", timeLeftTarget = "value1", dateRightTarget = "value2",
                  timeRightTarget = "value3")
    public class TestBean8 {
        private String value = null;
        private String value1 = null;
        private String value2 = null;
        private String value3 = null;
    }

    /**
     * アノテーションチェック
     */
    @Data
    @HVRDateCombo(dateLeftTarget = "value", timeLeftTarget = "value1", dateRightTarget = "value2",
                  timeRightTarget = "value3")
    public class TestBean9 {
        private String value = "";
        private String value1 = "22:22:21";
        private String value2 = "";
        private String value3 = "22:22:22";
    }

    /**
     * アノテーションチェック
     */
    @Data
    @HVRDateCombo(dateLeftTarget = "value", timeLeftTarget = "value1", dateRightTarget = "value2",
                  timeRightTarget = "value3", acceptOrphanedTime = true)
    public class TestBean10 {
        private String value = "";
        private String value1 = "22:22:21";
        private String value2 = "";
        private String value3 = "22:22:22";
    }

}
