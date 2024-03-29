package jp.co.hankyuhanshin.itec.hitmall.api.user;

import jp.co.hankyuhanshin.itec.hitmall.api.user.param.AccessUidResponse;
import jp.co.hankyuhanshin.itec.hitmall.dao.common.AccessUidDao;
import jp.co.hankyuhanshin.itec.hitmall.web.AbstractController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.Calendar;

/**
 * ユーザーコントロール
 *
 * @author VINH NGUYEN VAN
 */
@RestController
public class UserController extends AbstractController implements UsersApi {
    /**
     * AccessUidDao
     */
    private final AccessUidDao accessUidDao;

    /**
     ユーザー Helper
     */
    private final UserHelper userHelper;

    public UserController(AccessUidDao accessUidDao, UserHelper userHelper) {
        this.accessUidDao = accessUidDao;
        this.userHelper = userHelper;
    }

    /**
     * GET /users/accessUid : 端末識別番号作成
     * 端末識別番号作成
     *
     * @return 端末識別情報レスポンス (status code 200)
     * or システムエラー (status code 500)
     */
    @Override
    public ResponseEntity<AccessUidResponse> getAccessUid() {

        // 4桁SEQ + 現在日時
        StringBuilder accessUid = new StringBuilder(16);
        accessUid.append(new DecimalFormat("0000").format(this.getAccessUidSeq()));

        // 年2桁 + 月2桁 + 日2桁 + 時間2桁 + 分2桁 + 秒2桁 = 12桁
        Calendar current = Calendar.getInstance();
        Format format = new DecimalFormat("00");
        accessUid.append(String.valueOf(current.get(Calendar.YEAR)).substring(2));
        accessUid.append(format.format((current.get(Calendar.MONTH) + 1)));
        accessUid.append(format.format(current.get(Calendar.DAY_OF_MONTH)));
        accessUid.append(format.format(current.get(Calendar.HOUR_OF_DAY)));
        accessUid.append(format.format(current.get(Calendar.MINUTE)));
        accessUid.append(format.format(current.get(Calendar.SECOND)));

        AccessUidResponse accessUidResponse = userHelper.toAccessUidResponse(accessUid);

        return new ResponseEntity<>(accessUidResponse, HttpStatus.OK);
    }

    /**
     * 端末識別SEQを取得<br/>
     *
     * @return 端末識別SEQ
     */
    protected Integer getAccessUidSeq() {
        return Integer.parseInt(accessUidDao.getNextVal());
    }
}
