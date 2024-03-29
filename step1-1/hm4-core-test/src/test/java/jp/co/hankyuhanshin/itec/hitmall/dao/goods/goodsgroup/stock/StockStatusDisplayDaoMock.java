package jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.stock;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.stock.StockStatusDisplayEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.boot.ConfigAutowireable;

@Dao
@ConfigAutowireable
public interface StockStatusDisplayDaoMock {
    @Insert
    int insert(StockStatusDisplayEntity entity);

    @Delete
    int delete(StockStatusDisplayEntity entity);
}
