package jp.co.hankyuhanshin.itec.hitmall.api.user;

import jp.co.hankyuhanshin.itec.hitmall.api.user.param.AccessUidResponse;
import org.springframework.stereotype.Component;

/**
 * ユーザー Helper
 *
 * @author VINH NGUYEN VAN
 */
@Component
public class UserHelper {

    /**
     * 端末識別情報レスポンスに変換
     *
     * @param accessUid 端末識別情報
     * @return 端末識別情報レスポンス
     */
    public AccessUidResponse toAccessUidResponse(StringBuilder accessUid) {
        AccessUidResponse accessUidResponse = new AccessUidResponse();

        accessUidResponse.setAccessUid(accessUid.toString());

        return accessUidResponse;
    }
}
