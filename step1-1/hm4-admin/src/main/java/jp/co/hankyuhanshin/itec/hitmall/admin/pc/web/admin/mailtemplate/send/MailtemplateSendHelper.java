package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.mailtemplate.send;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateDefaultFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.mail.MailTemplateIndexDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.mail.MailTemplateEntity;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static jp.co.hankyuhanshin.itec.hitmall.constant.TransmissionFormatForMagazine.INT_PC_HTML;

@Component
public class MailtemplateSendHelper {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MailtemplateSendHelper.class);

    /**
     * 画面表示用 PageItem を作成する
     *
     * @param dtoList               元ねた
     * @param mailtemplateSendModel セットするページ
     */
    public void toPageSelect(List<MailTemplateIndexDto> dtoList, MailtemplateSendModel mailtemplateSendModel) {

        List<MailtemplateSelectItem> itemList = new ArrayList<>();
        mailtemplateSendModel.setIndexItems(itemList);

        Map<HTypeMailTemplateType, Integer> memberCountMap = new HashMap<>();
        for (MailTemplateIndexDto dto : dtoList) {
            Integer count = memberCountMap.get(dto.getMailTemplateType());
            if (count == null) {
                count = 0;
            }
            count = count + 1;
            memberCountMap.put(dto.getMailTemplateType(), count);
        }

        Set<HTypeMailTemplateType> foundSet = new HashSet<>();

        for (MailTemplateIndexDto dto : dtoList) {
            MailtemplateSelectItem item = ApplicationContextUtility.getBean(MailtemplateSelectItem.class);
            item.setMailTemplateDefaultFlag(dto.getMailTemplateDefaultFlag() == HTypeMailTemplateDefaultFlag.ON);
            item.setMailTemplateName(dto.getMailTemplateName());
            item.setMailTemplateSeq(dto.getMailTemplateSeq());
            item.setMailTemplateTypeName(dto.getMailTemplateType().getLabel());
            item.setMailTemplateType(dto.getMailTemplateType().getValue());
            item.setMailTemplateTypeRowRowspan(memberCountMap.get(dto.getMailTemplateType()));

            // テンプレート内先頭のレコードかどうか
            if (!foundSet.contains(dto.getMailTemplateType())) {
                item.setNewTemplateType(true);
                foundSet.add(dto.getMailTemplateType());
            } else {
                item.setNewTemplateType(false);
            }

            // テンプレートがまだ登録されていない種別かどうか
            if (dto.getMailTemplateSeq() == -1) {
                item.setEmptyTemplate(true);
            }

            itemList.add(item);

        }
    }

    /**
     * ロード時のＤＴＯ→ページ設定
     *
     * @param entity MailTemplateEntity
     * @param model  MailtemplateEditModel
     */
    public void toPageForLoad(MailTemplateEntity entity, MailtemplateSendModel model) {

        model.setShowingPreview(false);

        if (entity != null) {
            // 既に登録済みのテンプレート
            model.setMailTemplateType(entity.getMailTemplateType().getValue());
            model.setMailTemplateTypeName(entity.getMailTemplateType().getLabel());

            // テンプレート名
            model.setMailTemplateName(entity.getMailTemplateName());

            // 送信者&BCC
            model.setFromAddress(entity.getMailTemplateFromAddress());
            model.setBccAddress(entity.getMailTemplateBccAddress());

            model.setMailTitle(entity.getMailTemplateSubject());
        }

        model.setEditingVersion(INT_PC_HTML);
        model.setLastEditingVersion(INT_PC_HTML);

    }
}
