package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.search;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * コンテンツItem
 *
 * @author tk32120
 */
@Data
@Component
@Scope("prototype")
public class ContentsItem {
    /**
     * コンテンツ名
     */
    private String contentName;

    /**
     * 遷移先URL
     */
    private String transitionUrl;

    /**
     * コンテンツ画像
     */
    private String contentImageUrl;
}
