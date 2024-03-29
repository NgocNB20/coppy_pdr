// 2023-renew No22 from here
package jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.image;

import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.image.MemberImageEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

/**
 * メンバーイメージ DAO クラス
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Dao
@ConfigAutowireable
public interface MemberImageDao {
    /**
     * インサート
     *
     * @param memberImageEntity メンバーイメージ
     * @return 登録件数
     */
    @Insert(excludeNull = true)
    int insert(MemberImageEntity memberImageEntity);

    /**
     * リストを選択
     *
     * @param memberInfoSeq 会員SEQ
     * @return リストメンバー画像エンティティ
     */
    @Select
    List<MemberImageEntity> getListMemberImage(Integer memberInfoSeq);

    /**
     * 実行する
     *
     * @param memberInfoSeq 会員SEQ;
     * @return 削除件数
     */
    @Delete(sqlFile = true)
    int deleteMemberImageEntity(Integer memberInfoSeq);
}
// 2023-renew No22 to here
