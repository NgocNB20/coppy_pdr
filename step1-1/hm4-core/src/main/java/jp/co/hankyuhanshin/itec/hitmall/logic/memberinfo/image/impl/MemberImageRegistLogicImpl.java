// 2023-renew No22 from here
package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.image.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.image.MemberImageDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.image.MemberImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.image.MemberImageRegistLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * メンバーイメージ作成ロジックの実装
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Component
@RequiredArgsConstructor
public class MemberImageRegistLogicImpl extends AbstractShopLogic implements MemberImageRegistLogic {

    private final MemberImageDao memberImageDao;

    @Override
    public int execute(MemberImageEntity memberImageEntity) {
        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("memberImageEntity", memberImageEntity);

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // 登録日時・更新日時の設定
        Timestamp currentTime = dateUtility.getCurrentTime();
        memberImageEntity.setRegistTime(currentTime);
        memberImageEntity.setUpdateTime(currentTime);

        // 会員画像登録を実行する
        return memberImageDao.insert(memberImageEntity);
    }
}
// 2023-renew No22 to here
