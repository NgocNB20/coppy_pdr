// 2023-renew No21 from here
package jp.co.hankyuhanshin.itec.hitmall.service.goods.togetherbuy.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeExcludeFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeRegisterMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsTogetherBuyGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.togetherbuy.GoodsTogetherBuyGroupListGetForFrontLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.togetherbuy.GoodsTogetherBuyGroupListGetForFrontService;
import jp.co.hankyuhanshin.itec.hitmall.web.PageInfo;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * よく一緒に購入される商品 グループリストを取得してフロントサービスを実施する
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Component
public class GoodsTogetherBuyGroupListGetForFrontServiceImpl implements GoodsTogetherBuyGroupListGetForFrontService {

    /**
     * よく一緒に購入される商品 グループリストを購入 フロントサービスを利用する
     *
     */
    private final GoodsTogetherBuyGroupListGetForFrontLogic goodsTogetherBuyGroupListGetForFrontService;

    /**
     * 商品グループリスト取得
     */
    private final GoodsGroupListGetLogic goodsGroupListGetLogic;

    @Autowired
    public GoodsTogetherBuyGroupListGetForFrontServiceImpl(GoodsTogetherBuyGroupListGetForFrontLogic goodsTogetherBuyGroupListGetForFrontService,
                                                           GoodsGroupListGetLogic goodsGroupListGetLogic) {
        this.goodsTogetherBuyGroupListGetForFrontService = goodsTogetherBuyGroupListGetForFrontService;
        this.goodsGroupListGetLogic = goodsGroupListGetLogic;
    }

    /**
     * 商品グループDtoリスト取得
     *
     * @param goodsGroupSeq 商品グループSEQ
     * @param limit         最大取得件数
     * @return 商品グループDto
     */
    @Override
    public List<GoodsGroupDto> execute(Integer goodsGroupSeq, int limit) {
        ArgumentCheckUtil.assertNotNull("goodsGroupSeq", goodsGroupSeq);

        List<GoodsTogetherBuyGroupEntity> goodsTogetherBuyGroupEntityList =
                        goodsTogetherBuyGroupListGetForFrontService.execute(goodsGroupSeq,
                                                                            HTypeOpenDeleteStatus.OPEN.getValue(),
                                                                            HTypeExcludeFlag.OFF.getValue(),
                                                                            HTypeGoodsSaleStatus.SALE.getValue()
                                                                           );
        // 手動登録と被っている商品は非表示とする
        List<GoodsTogetherBuyGroupEntity> goodsTogetherBuyGroupEntities =
                        clearDuplicate(goodsTogetherBuyGroupEntityList);

        if (CollectionUtil.isEmpty(goodsTogetherBuyGroupEntities)) {
            return null;
        }

        List<Integer> goodsGroupSeqList = goodsTogetherBuyGroupEntities.stream()
                                                                       .map(GoodsTogetherBuyGroupEntity::getGoodsTogetherBuyGroupSeq)
                                                                       .collect(Collectors.toList());

        GoodsGroupSearchForDaoConditionDto conditionDto = new GoodsGroupSearchForDaoConditionDto();

        conditionDto.setGoodsGroupSeqList(goodsGroupSeqList);
        conditionDto.setSiteType(HTypeSiteType.FRONT_PC);
        conditionDto.setOpenStatus(HTypeOpenDeleteStatus.OPEN);

        PageInfo pageInfo = ApplicationContextUtility.getBean(PageInfo.class);
        pageInfo.setLimit(limit);
        pageInfo.setSkipCountFlg(true);
        pageInfo.setupSelectOptions();
        conditionDto.setPageInfo(pageInfo);

        // よく一緒に購入される商品の商品詳細情報リスト取得処理を実行する
        List<GoodsGroupDto> goodsGroupDtoList = goodsGroupListGetLogic.execute(conditionDto);

        goodsGroupDtoList.sort((o1, o2) -> {
            int index1 = goodsGroupSeqList.indexOf(o1.getGoodsGroupEntity().getGoodsGroupSeq());
            int index2 = goodsGroupSeqList.indexOf(o2.getGoodsGroupEntity().getGoodsGroupSeq());
            return Integer.compare(index1, index2);
        });

        return goodsGroupDtoList;
    }

    /**
     * 手動登録と被っている商品は非表示とする
     *
     * @param goodsTogetherBuyGroupEntityList よく一緒に購入される商品クラスリスト
     */
    private List<GoodsTogetherBuyGroupEntity> clearDuplicate(List<GoodsTogetherBuyGroupEntity> goodsTogetherBuyGroupEntityList) {
        return goodsTogetherBuyGroupEntityList.stream()
                                              .filter(item -> !(HTypeRegisterMethodType.BATCH.equals(
                                                              item.getRegistMethod()) && isDuplicate(
                                                              goodsTogetherBuyGroupEntityList,
                                                              item.getGoodsTogetherBuyGroupSeq()
                                                                                                    )))
                                              .collect(Collectors.toList());

    }

    /**
     * 手動登録された商品を優先的に表示する
     *
     * @param goodsTogetherBuyGroupEntityList よく一緒に購入される商品クラスリスト
     * @return true:重複、false:重複しない
     */
    private boolean isDuplicate(List<GoodsTogetherBuyGroupEntity> goodsTogetherBuyGroupEntityList,
                                Integer goodsTogetherBuyGroupSeq) {
        for (GoodsTogetherBuyGroupEntity goodsTogetherBuyGroupEntity : goodsTogetherBuyGroupEntityList) {
            if (HTypeRegisterMethodType.BACK.equals(goodsTogetherBuyGroupEntity.getRegistMethod())
                && goodsTogetherBuyGroupEntity.getGoodsTogetherBuyGroupSeq().equals(goodsTogetherBuyGroupSeq)) {
                return true;
            }
        }
        return false;
    }
}
// 2023-renew No21 to here
