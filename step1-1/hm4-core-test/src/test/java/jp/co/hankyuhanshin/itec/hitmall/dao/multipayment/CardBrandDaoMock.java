package jp.co.hankyuhanshin.itec.hitmall.dao.multipayment;

import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.CardBrandEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.boot.ConfigAutowireable;

@Dao
@ConfigAutowireable
public interface CardBrandDaoMock {
    @Delete
    int delete(CardBrandEntity entity);
}
