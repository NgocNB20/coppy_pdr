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
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVREitherOneRequired;
import lombok.Data;

/**
 * 正常ケース、異常ケースの２パターンのみ検証<br/>
 * 作成日：2021/03/29
 *
 * @author kimura
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HEitherOneRequiredValidatorTest {

    private Validator validator;

    // デフォルトコンストラクタ
    public HEitherOneRequiredValidatorTest() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    // 1.正常ケース
    public void test1() {
        TestBean1 bean = new TestBean1();
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), true);
    }

    @Test
    // 2.異常ケース
    public void test2() {
        TestBean2 bean = new TestBean2();
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), false);
        result.forEach(r -> Assertions.assertEquals(r.getMessage(), "{HEitherOneRequiredValidator.INPUT}"));
    }

    @Test
    // 3.異常ケース
    public void test3() {
        TestBean3 bean = new TestBean3();
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), false);
        result.forEach(r -> Assertions.assertEquals(r.getMessage(), "{HEitherOneRequiredValidator.INPUT}"));
    }

    /**
     * アノテーションチェック
     */
    @Data
    @HVREitherOneRequired(fields = {"value", "value1", "value2", "value3"})
    public class TestBean1 {
        private String value = "テスト";
        private String value1 = "テスト1";
        private String value2 = "テスト2";
        private String value3 = "テスト3";
    }

    /**
     * アノテーションチェック
     */
    @Data
    @HVREitherOneRequired(fields = {"value", "value1", "value2", "value3"}, errorField = "value2")
    public class TestBean2 {
        private String value = "";
        private String value1 = "";
        private String value2 = "";
        private String value3 = "";

    }

    /**
     * アノテーションチェック
     */
    @Data
    @HVREitherOneRequired(fields = {"value", "value1", "value2", "value3"}, errorField = "value3")
    public class TestBean3 {
        private String value = null;
        private String value1 = null;
        private String value2 = null;
        private String value3 = null;
    }
}
