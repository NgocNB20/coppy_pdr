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
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVRStringEqual;
import lombok.Data;

/**
 * 正常ケース、異常ケースの２パターンのみ検証<br/>
 * 作成日：2021/03/25
 *
 * @author kimura
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HStringEqualValidatorTest {

    private String value = "テスト";

    private Validator validator;

    // デフォルトコンストラクタ
    public HStringEqualValidatorTest() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    // 1.正常ケース
    public void test1() {
        TestBean1 bean = new TestBean1(value);
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), true);
    }

    @Test
    // 2.異常ケース
    public void test2() {
        TestBean2 bean = new TestBean2(value);
        Set<ConstraintViolation<Object>> result = validator.validate(bean);
        Assertions.assertEquals(result.isEmpty(), false);
        result.forEach(r -> Assertions.assertEquals(r.getMessage(), "{HStringEqualValidator.NOT_EQUAL_detail}"));
    }

    /**
     * アノテーションチェック
     */
    @Data
    @HVRStringEqual(target = "value", comparison = "value1")
    public class TestBean1 {
        private String value;
        private String value1 = "テスト";

        TestBean1(String value) {
            this.value = value;
        }
    }

    /**
     * アノテーションチェック
     */
    @Data
    @HVRStringEqual(target = "value", comparison = "value2")
    public class TestBean2 {
        private String value;
        private String value2 = "テスト1";

        TestBean2(String value) {
            this.value = value;
        }
    }
}
