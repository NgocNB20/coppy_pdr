/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.batch.application.commoninfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdministratorEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import lombok.Getter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

/**
 * Spring-Security Admin用管理者情報クラス
 * DB認証用カスタマイズ
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Getter
public class HmAdminUserDetails extends User {

    private static final long serialVersionUID = 1L;

    /**
     * 管理者クラス
     */
    private final AdministratorEntity administratorEntity;

    /**
     * 会員クラス
     */
    private final MemberInfoEntity memberInfoEntity;

    /**
     * isAdminLoginAsMember
     */
    private final Boolean isAdminLoginAsMember;

    /**
     * コンストラクタ
     *
     * @param administratorEntity
     * @param authorityList
     */
    public HmAdminUserDetails(MemberInfoEntity memberInfoEntity,
                              boolean isAdminLoginAsMember,
                              AdministratorEntity administratorEntity,
                              String[] authorityList) {

        // 認証用ユーザ情報設定
        super(
                        administratorEntity.getAdministratorId(), administratorEntity.getAdministratorPassword(),
                        AuthorityUtils.createAuthorityList(authorityList)
             );

        // DI設定
        this.administratorEntity = administratorEntity;
        this.memberInfoEntity = memberInfoEntity;
        this.isAdminLoginAsMember = isAdminLoginAsMember;
    }

}
