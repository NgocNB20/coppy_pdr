package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.favorite;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.favorite.FavoriteDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.favorite.FavoriteSearchForDaoConditionDto;

/**
 * Logic/Service移行：会員に紐付く問い合わせの存在チェック
 * 作成日：2021/03/23
 *
 * @author Nguyen Hong Son (VTI VietNam Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FavoriteListGetServiceTest {

    @Autowired
    FavoriteListGetService service;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        FavoriteSearchForDaoConditionDto favoriteConditionDto = new FavoriteSearchForDaoConditionDto();
        favoriteConditionDto.setPageInfo(new PageInfo());
        favoriteConditionDto.getPageInfo().setPnum("1");
        favoriteConditionDto.getPageInfo().setupSelectOptions();

        favoriteConditionDto.setShopSeq(1);

        // 試験実行
        List<FavoriteDto> list = service.execute(favoriteConditionDto);
        Assertions.assertEquals(0, list.size());
    }
}
