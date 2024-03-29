package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.common;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * カテゴリItem
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Data
@Component
@Scope("prototype")
public class CategoryItem {

    /**
     * カテゴリID
     */
    private String cid;
    /**
     * カテゴリ表示名PC
     */
    private String categoryName;

}
