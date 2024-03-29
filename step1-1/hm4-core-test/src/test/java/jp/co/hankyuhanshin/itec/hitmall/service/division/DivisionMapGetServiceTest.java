/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */
package jp.co.hankyuhanshin.itec.hitmall.service.division;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;

/**
 * 区分値自動生成<br/>
 * DB用共通処理の移行結果<br/>
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
public class DivisionMapGetServiceTest {

    @Autowired
    public DivisionMapGetService divisionMapGetService;

    /**
     * map(key = "value", value = "label")の形で取得できていることを確認<br/>
     * 「お問い合わせ分類」などが該当
     */
    @Test
    public void executeTest() {

        Map<String, String> map = divisionMapGetService.getInquiryGroupMapList();
        Assertions.assertEquals(map.size(), 4);
        Assertions.assertEquals(map.containsKey("1002"), true);
        Assertions.assertEquals(map.get("1002"), "支払い方法について");
        Assertions.assertEquals(map.containsKey("1001"), true);
        Assertions.assertEquals(map.get("1001"), "商品について");
        Assertions.assertEquals(map.containsKey("1003"), true);
        Assertions.assertEquals(map.get("1003"), "配送について");
        Assertions.assertEquals(map.containsKey("1004"), true);
        Assertions.assertEquals(map.get("1004"), "非公開");
    }
}
