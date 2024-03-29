package jp.co.hankyuhanshin.itec.hitmall.config;

import jp.co.hankyuhanshin.itec.hitmall.application.commoninfo.CommonInfo;
import jp.co.hankyuhanshin.itec.hitmall.application.commoninfo.CommonInfoAdministrator;
import jp.co.hankyuhanshin.itec.hitmall.application.commoninfo.impl.*;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberRank;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.ArrayList;

@TestConfiguration
public class CommonInfoInitConfig {

    @Bean
    public CommonInfo initCommonInfo() {
        // CommonInfoImpl
        CommonInfoImpl commonInfoImpl = new CommonInfoImpl();

        // CommonInfoAdministratorImpl
        CommonInfoAdministratorImpl commonInfoAdministratorImpl = new CommonInfoAdministratorImpl();
        //commonInfoAdministratorImpl.setLoginDivision(LoginDivision.UNLOGIN);
        commonInfoAdministratorImpl.setAdministratorSeq(0);
        commonInfoAdministratorImpl.setAdministratorId("");
        commonInfoAdministratorImpl.setAdministratorLastName("");
        commonInfoAdministratorImpl.setAdministratorFirstName("");

        // CommonInfoBaseImpl
        CommonInfoBaseImpl commonInfoBaseImpl = new CommonInfoBaseImpl();
        // commonInfoBaseImpl.setSessionDivision(SessionDivision.CONTINUE);
        commonInfoBaseImpl.setShopSeq(1001);
        commonInfoBaseImpl.setSiteType(HTypeSiteType.FRONT_PC);
        commonInfoBaseImpl.setUserAgent("");
        commonInfoBaseImpl.setDeviceType(HTypeDeviceType.PC);
        commonInfoBaseImpl.setSessionId("");
        commonInfoBaseImpl.setUrl("");
        //  commonInfoBaseImpl.setViewUrl("");
        commonInfoBaseImpl.setPageId("");
        //   commonInfoBaseImpl.setActionName("");
        //  commonInfoBaseImpl.setActionIndex(0);
        commonInfoBaseImpl.setAccessUid("1");
        commonInfoBaseImpl.setCampaignCode("");
        //  commonInfoBaseImpl.setIpAddress(IPAddress.IPADDRESS);

        // CommonInfoCartImpl
        //        CommonInfoCartImpl commonInfoCartImpl = new CommonInfoCartImpl();
        //        commonInfoCartImpl.setCartGoodsSumCount(new BigDecimal("0"));

        // CommonInfoShopImpl
        CommonInfoShopImpl commonInfoShopImpl = new CommonInfoShopImpl();
        commonInfoShopImpl.setShopNamePC("");
        // commonInfoShopImpl.setShopNameMB("");
        commonInfoShopImpl.setUrlPC("");
        //  commonInfoShopImpl.setUrlMB("");
        commonInfoShopImpl.setShopMetaKeyword("");
        commonInfoShopImpl.setShopMetaDescription("");
        commonInfoShopImpl.setCloseFlag(false);

        // CommonInfoUserImpl
        CommonInfoUserImpl commonInfoUserImpl = new CommonInfoUserImpl();
        // commonInfoUserImpl.setLoginDivision(LoginDivision.UNLOGIN);
        commonInfoUserImpl.setMemberInfoRank(HTypeMemberRank.GUEST);
        // commonInfoUserImpl.setPersonAttestation(PersonAttestation.ATTESTED);
        commonInfoUserImpl.setMemberInfoSeq(1);
        commonInfoUserImpl.setMemberInfoLastName("");
        commonInfoUserImpl.setMemberInfoFirstName("");
        commonInfoUserImpl.setMemberInfoId("");
        //   commonInfoUserImpl.setAuthenticatedSaleIdList(new ArrayList<String>());

        // commonInfoImpl.setCommonInfoAdministrator(commonInfoAdministratorImpl);
        commonInfoImpl.setCommonInfoBase(commonInfoBaseImpl);
        // commonInfoImpl.setCommonInfoCart(commonInfoCartImpl);
        commonInfoImpl.setCommonInfoShop(commonInfoShopImpl);
        //   commonInfoImpl.setCommonInfoUser(commonInfoUserImpl);

        return commonInfoImpl;
    }

}
