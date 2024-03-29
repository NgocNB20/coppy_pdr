/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.batch.config.commoninfo;

import jp.co.hankyuhanshin.itec.hitmall.batch.application.commoninfo.CommonInfo;
import jp.co.hankyuhanshin.itec.hitmall.batch.application.commoninfo.CommonInfoAdministrator;
import jp.co.hankyuhanshin.itec.hitmall.batch.application.commoninfo.impl.CommonInfoBatchAdministratorImpl;
import jp.co.hankyuhanshin.itec.hitmall.batch.application.commoninfo.impl.CommonInfoBatchBaseImpl;
import jp.co.hankyuhanshin.itec.hitmall.batch.application.commoninfo.impl.CommonInfoBatchShopImpl;
import jp.co.hankyuhanshin.itec.hitmall.batch.application.commoninfo.impl.CommonInfoImpl;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * CommonInfo 設定クラス
 *
 * @author yt23807
 * @version $Revision: 1.0 $
 */
@Configuration
public class BatchCommonInfoConfiguration {

    /**
     * CommonInfo Bean定義
     * Singletonスコープ
     *
     * @return
     */
    @Bean("commonInfoBatch")
    @Scope("singleton")
    public CommonInfo commonInfo() {

        // Batchoption_CommonInfo_1001.tmpl.dicon　からの移行

        // CommonInfoImpl
        CommonInfoImpl commonInfoImpl = new CommonInfoImpl() {
            /** シリアルID */
            private static final long serialVersionUID = 1L;

            @Override
            public CommonInfoAdministrator getCommonInfoAdministrator() {
                // Batch用ダミー管理者
                CommonInfoBatchAdministratorImpl adminImpl = new CommonInfoBatchAdministratorImpl();

                adminImpl.setAdministratorSeq(-1);
                adminImpl.setAdministratorId("nobody");
                adminImpl.setAdministratorLastName("batch");
                adminImpl.setAdministratorFirstName("process");

                return adminImpl;
            }
        };

        // CommonInfoBaseImpl
        CommonInfoBatchBaseImpl commonInfoBatchBaseImpl = new CommonInfoBatchBaseImpl();
        commonInfoBatchBaseImpl.setShopSeq(1001);
        commonInfoBatchBaseImpl.setSiteType(HTypeSiteType.BACK);
        commonInfoBatchBaseImpl.setUserAgent("");
        commonInfoBatchBaseImpl.setDeviceType(HTypeDeviceType.MB);
        commonInfoBatchBaseImpl.setSessionId("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
        commonInfoBatchBaseImpl.setUrl("http://localhost/");
        commonInfoBatchBaseImpl.setPageId("/view/admin/");
        commonInfoBatchBaseImpl.setAccessUid("1");

        commonInfoBatchBaseImpl.setCartGoodsSumCount(new BigDecimal("0"));
        commonInfoBatchBaseImpl.setInquiryCodeAttestationList(new ArrayList<String>());

        // CommonInfoShopImpl
        CommonInfoBatchShopImpl commonInfoBatchShopImpl = new CommonInfoBatchShopImpl();
        commonInfoBatchShopImpl.setShopNamePC("");
        commonInfoBatchShopImpl.setUrlPC("");
        commonInfoBatchShopImpl.setShopMetaKeyword("");
        commonInfoBatchShopImpl.setShopMetaDescription("");
        commonInfoBatchShopImpl.setCloseFlag(false);

        commonInfoImpl.setCommonInfoBase(commonInfoBatchBaseImpl);
        commonInfoImpl.setCommonInfoShop(commonInfoBatchShopImpl);

        return commonInfoImpl;
    }
}
