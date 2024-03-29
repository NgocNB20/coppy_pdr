package jp.co.hankyuhanshin.itec.hitmall.dto.ukapi;

import jp.co.hankyuhanshin.itec.hitmall.annotation.csv.CsvColumn;
import lombok.Data;
import org.seasar.doma.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * コンテンツフィードTsvDto
 */
@Entity
@Data
@Component
@Scope("prototype")
public class UkContentsFeedTsvDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * フリーエリアキー
     */
    @CsvColumn(order = 10, columnLabel = "content_id")
    @NotEmpty
    private String freeAreaKey;

    /**
     * フリーエリアタイトル
     */
    @CsvColumn(order = 20, columnLabel = "content_name")
    @NotEmpty
    private String freeAreaTitle;

    /**
     * UK-遷移先URL
     */
    @CsvColumn(order = 30, columnLabel = "transition_url")
    @NotEmpty
    private String ukTransitionUrl;

    /**
     * UK-コンテンツ画像URL
     */
    @CsvColumn(order = 40, columnLabel = "content_image_url")
    @NotEmpty
    private String ukContentImageUrl;

    /**
     * 検索キーワード（tmp）
     */
    private String searchKeyword;

    /**
     * 検索キーワード（連携用）
     */
    @CsvColumn(order = 50, columnLabel = "search_keyword")
    @NotEmpty
    private String ukSearchKeyword;

}
