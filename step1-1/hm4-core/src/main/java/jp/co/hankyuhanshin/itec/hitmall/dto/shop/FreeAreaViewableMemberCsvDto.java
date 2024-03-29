package jp.co.hankyuhanshin.itec.hitmall.dto.shop;

import jp.co.hankyuhanshin.itec.hitmall.annotation.csv.CsvColumn;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVNumber;
import lombok.Data;
import org.seasar.doma.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * フリーエリア表示対象会員CSVDtoクラス
 */
@Entity
@Data
@Component
@Scope("prototype")
public class FreeAreaViewableMemberCsvDto implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** 顧客番号 */
    @CsvColumn(order = 10, columnLabel = "顧客番号")
    @NotNull
    @HVNumber
    public Integer customerNo;
}
