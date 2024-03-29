package jp.co.hankyuhanshin.itec.hitmall.logic.webapi;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiResponseResultDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.member.WebApiAddDestinationResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiAddOrderInformationResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.utility.JsonUtility;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static jp.co.hankyuhanshin.itec.hitmall.logic.webapi.AbstractWebApiLogic.HTTP_STATUS_SUCCESS;
import static jp.co.hankyuhanshin.itec.hitmall.logic.webapi.AbstractWebApiLogic.WEB_API_STATUS_SUCCESS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doReturn;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WebApiAddOrderInformationLogicTest {
    @Autowired
    WebApiAddOrderInformationLogic webApiAddOrderInformationLogic;

    private MockedConstruction<URL> mockUrl;

    @Mock
    HttpURLConnection connection;

    @MockBean
    JsonUtility jsonUtility;

    final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void beginTest() {
        mockUrl = Mockito.mockConstruction(URL.class, (urlMock, context) -> {
            when(urlMock.openConnection()).thenReturn(connection);
        });
    }

    @Test
    public void executeTest() throws IOException {
        AbstractWebApiRequestDto requestDto = new AbstractWebApiRequestDto() {
            @Override
            public boolean equals(Object o) {
                return super.equals(o);
            }

        };

        AbstractWebApiResponseDto responseDto = new WebApiAddOrderInformationResponseDto();
        responseDto.setResult(new AbstractWebApiResponseResultDto());
        responseDto.getResult().setStatus(WEB_API_STATUS_SUCCESS);
        PrintStream printStream = mock(PrintStream.class);
        doReturn(HTTP_STATUS_SUCCESS).when(connection).getResponseCode();
        doReturn(outputStream).when(connection).getOutputStream();
        doReturn(IOUtils.toInputStream("some test data for my input stream", "UTF-8")).when(connection)
                                                                                      .getInputStream();
        doNothing().when(printStream).print(any(OutputStream.class));
        doReturn("{abc:abc}").when(jsonUtility).encode(any());
        doReturn(responseDto).when(jsonUtility).decode(any(), any());

        AbstractWebApiResponseDto result = webApiAddOrderInformationLogic.execute(requestDto);

        Assertions.assertEquals(result.getResult().getStatus(), responseDto.getResult().getStatus());
    }

    @AfterAll
    public void afterTest() {
        mockUrl.close();
    }
}
