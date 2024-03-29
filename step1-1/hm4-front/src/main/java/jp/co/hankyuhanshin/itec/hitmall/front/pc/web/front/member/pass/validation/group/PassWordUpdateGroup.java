package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.pass.validation.group;

import javax.validation.GroupSequence;

@GroupSequence({ValidCommonGroup.class, ValidStringEqualGroup.class, ValidStringNotEqualGroup.class})
public interface PassWordUpdateGroup {
}
