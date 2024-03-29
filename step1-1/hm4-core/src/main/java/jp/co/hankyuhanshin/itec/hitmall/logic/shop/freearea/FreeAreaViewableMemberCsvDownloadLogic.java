package jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea;

import java.io.IOException;

/**
 * フリーエリア表示対象会員情報ダウンロードロジック<br/>
 */
public interface FreeAreaViewableMemberCsvDownloadLogic {

    void execute(Object... parameters) throws IOException;

}
