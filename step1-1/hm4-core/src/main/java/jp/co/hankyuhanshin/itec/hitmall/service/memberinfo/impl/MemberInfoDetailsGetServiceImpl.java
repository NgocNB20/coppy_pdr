/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.MemberInfoDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoDetailsGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.OrderSearchOrderListGetService;
import jp.co.hankyuhanshin.itec.hitmall.utility.MemberInfoUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 会員詳細取得サービス実装クラス<br/>
 * 管理画面専用<br/>
 * <pre>
 * ・会員情報
 * ・メルマガ情報
 * </pre>
 *
 * @author negishi
 * @author Kaneko (itec) 2012/08/09 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Service
public class MemberInfoDetailsGetServiceImpl extends AbstractShopService implements MemberInfoDetailsGetService {

    /**
     * 会員情報取得サービス
     */
    private final MemberInfoGetService memberInfoGetService;

    /**
     * 受注検索（受注一覧）取得サービス
     */
    private final OrderSearchOrderListGetService orderSearchOrderListGetService;

    @Autowired
    public MemberInfoDetailsGetServiceImpl(MemberInfoGetService memberInfoGetService,
                                           OrderSearchOrderListGetService orderSearchOrderListGetService) {
        this.memberInfoGetService = memberInfoGetService;
        this.orderSearchOrderListGetService = orderSearchOrderListGetService;
    }

    /**
     * サービス実行
     *
     * @param memberInfoSeq 会員SEQ
     * @return 商品詳細DTO
     */
    @Override
    public MemberInfoDetailsDto execute(Integer memberInfoSeq) {
        // パラメータチェック
        checkParameter(memberInfoSeq);

        // 会員情報取得サービス実行
        MemberInfoEntity memberInfoEntity = memberInfoGetService.execute(memberInfoSeq);
        if (memberInfoEntity == null) {
            throwMessage(MSGCD_MEMBERINFO_GET_NULL);
        }

        String memberInfoUniqueId = memberInfoEntity.getMemberInfoUniqueId();
        // 退会されてる会員の会員一意制約用IDはnullになっている。
        if (memberInfoUniqueId == null) {
            memberInfoUniqueId = createShopUniqueId(memberInfoEntity);
        }

        // 会員詳細DTOを作成
        MemberInfoDetailsDto memberInfoDetailsDto = ApplicationContextUtility.getBean(MemberInfoDetailsDto.class);
        memberInfoDetailsDto.setMemberInfoEntity(memberInfoEntity);

        return memberInfoDetailsDto;
    }

    /**
     * パラメータチェック
     *
     * @param memberInfoSeq 会員SEQ
     */
    protected void checkParameter(Integer memberInfoSeq) {
        ArgumentCheckUtil.assertNotNull("memberInfoSeq", memberInfoSeq);
    }

    /**
     * 会員一意制約用IDを作成
     *
     * @param memberInfoEntity 会員情報エンティティ
     * @return 会員一意制約用ID
     */
    protected String createShopUniqueId(MemberInfoEntity memberInfoEntity) {
        // 会員業務Helper取得
        MemberInfoUtility memberInfoUtility = ApplicationContextUtility.getBean(MemberInfoUtility.class);

        return memberInfoUtility.createShopUniqueId(
                        memberInfoEntity.getShopSeq(), memberInfoEntity.getMemberInfoMail());
    }
}
