package jp.co.hankyuhanshin.itec.hitmall.logic.shop.news;

import java.util.ArrayList;
import java.util.List;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.news.NewsEntity;
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
import org.springframework.boot.test.mock.mockito.MockBean;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.news.NewsViewableMemberDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.news.NewsViewableMemberEntity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Logic/Service移行：会員に紐付く問い合わせの存在チェック
 * 作成日：2021/03/19
 *
 * @author Nguyen Hong Son (VTI VietNam Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NewsViewableMemberRegistLogicImplTest {

    @Autowired
    NewsViewableMemberRegistLogic logic;

    @MockBean
    private NewsViewableMemberDao dao;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        NewsViewableMemberEntity entity = new NewsViewableMemberEntity();
        entity.setNewsSeq(1);
        entity.setMemberInfoSeq(1);
        entity.setShopSeq(1);
        List<Integer> memberInfoSeqList = new ArrayList<>();
        memberInfoSeqList.add(1);

        doReturn(1).when(dao).insert(any(NewsViewableMemberEntity.class));

        // 試験実行
        int actual = logic.execute(1, memberInfoSeqList);

        verify(dao, times(1)).insert(any(NewsViewableMemberEntity.class));

        Assertions.assertEquals(0, actual);
    }

}
