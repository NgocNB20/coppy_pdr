package jp.co.hankyuhanshin.itec.hitmall.service.mail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import lombok.Data;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 機能概要：新メール送信サービス
 * 作成日：2021/03/30
 *
 * @author Phan Tien VU (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MailSendServiceTest {

    @Autowired
    MailSendService mailSendService;

    @Test
    @Order(1)
    public void executeTest() throws MessagingException, UnsupportedEncodingException, JsonProcessingException {
        List<String> toList = new ArrayList<>();
        toList.add("vu@vti.com.vn");
        List<String> ccList = new ArrayList<>();
        ccList.add("ando@vti.com.vn");
        List<String> bccList = new ArrayList<>();
        bccList.add("yamauchi@vti.com.vn");

        Map<String, Object> mailContentsMap = new HashMap<>();
        SampleMailBody sampleMailBody = new SampleMailBody();
        mailContentsMap.put("order", sampleMailBody);

        MailDto mailDto = new MailDto();
        mailDto.setMailTemplateType(HTypeMailTemplateType.ORDER_CONFIRMATION);
        mailDto.setSubject(HTypeMailTemplateType.ORDER_CONFIRMATION.getLabel());
        mailDto.setFrom("vu@vti.com.vn");
        mailDto.setToList(toList);
        mailDto.setCcList(ccList);
        mailDto.setBccList(bccList);
        mailDto.setMailContentsMap(mailContentsMap);
        mailDto.setAttachFileFlag(true);
        //        mailDto.setAttachFilePath(URLDecoder.decode(this.getClass().getResource("/temp/sample-data.csv").getPath().substring(1), StandardCharsets.UTF_8));
        //        mailDto.setAttachFileByte("byte demo csv content".getBytes());

        mailSendService.execute(mailDto);

        // JSON型で依頼する場合（非同期メール送信バッチの検証用）
        // 非同期メール送信サービスでの作業
        //        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        //        String jsonString = ow.writeValueAsString(mailDto);
        //        System.out.println(jsonString);
        //
        // 一括メール送信バッチでの作業
        //        ObjectMapper objectMapper = new ObjectMapper();
        //        MailDto mailDtoFromJson = objectMapper.readValue(jsonString, MailDto.class);
        //        mailSendService.execute(mailDtoFromJson);
    }

    @Data
    class GoodsList {
        private String productName;
        private String price;
    }

    @Data
    class SampleMailBody {
        private String name;
        private List<GoodsList> goodsLists;

        public SampleMailBody() {
            this.name = "阪神 太郎";
            this.goodsLists = new ArrayList<>();
            GoodsList p1 = new GoodsList();
            p1.setProductName("りんご");
            p1.setPrice("100");
            goodsLists.add(p1);
            GoodsList p2 = new GoodsList();
            p2.setProductName("すいか");
            p2.setPrice("900");
            goodsLists.add(p2);
            GoodsList p3 = new GoodsList();
            p3.setProductName("メロン");
            p3.setPrice("1,000");
            goodsLists.add(p3);
        }
    }

}
