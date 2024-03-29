package jp.co.hankyuhanshin.itec.hitmall.batch.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * バッチ終了メッセージ共通処理
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
public class BatchExitMessageUtil {

    // 終了メッセージ
    public static final String exitMsg = "EXIT_MESSAGE";

    /**
     * オブジェクトからJsonに変換<br/>
     *
     * @param exitMessageDto BatchExitMessageDto
     * @return JsonString
     */
    public String convertObjectToJson(BatchExitMessageDto exitMessageDto) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(exitMessageDto);
    }

    /**
     * JsonからBatchExitMessageDtoに変換<br/>
     *
     * @param jsonString jsonString
     * @return BatchExitMessageDto
     */
    public BatchExitMessageDto convertJsonToObject(String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString, BatchExitMessageDto.class);
    }
}
