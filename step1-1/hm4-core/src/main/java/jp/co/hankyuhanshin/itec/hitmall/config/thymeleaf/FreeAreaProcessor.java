/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.config.thymeleaf;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.FreeAreaEntity;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.freearea.FreeAreaGetService;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationLogUtility;
import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IAttribute;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * フリーエリアカスタムタグプロセッサー<br/>
 * <p>
 * フリーエリアカスタムタグで指定されたkey属性を取得し、
 * フリーエリアデータを取得し、フリーエリア本文をレンダリングする
 *
 * @author yt23807
 */
public class FreeAreaProcessor extends AbstractElementTagProcessor {

    //    第3引数に処理対象のタグ名「freearea」を渡す
    public FreeAreaProcessor(String dialectPrefix) {
        super(TemplateMode.HTML, dialectPrefix, "freeArea", true, null, true, 1);

    }

    @Override
    protected void doProcess(ITemplateContext context,
                             IProcessableElementTag tag,
                             IElementTagStructureHandler structureHandler) {
        String freeAreaBody = "";
        try {
            //    key属性に関する情報を取得する
            IAttribute iAttribute = tag.getAttribute("key");
            if (iAttribute != null) {
                //    key属性の値を取得する
                String key = iAttribute.getValue();
                if (key != null && !key.isEmpty()) {
                    FreeAreaGetService freeAreaGetService = ApplicationContextUtility.getBean(FreeAreaGetService.class);
                    // フリーエリア検索実施
                    FreeAreaEntity freeAreaEntity = freeAreaGetService.execute(key);
                    if (freeAreaEntity != null) {
                        // フリーエリア本文PCを取得
                        // ※nullの場合は""に変換　⇒　こうしないと後のカスタムタグリプレース時にExcepitonが発生してしまうため
                        freeAreaBody = StringUtils.trimToEmpty(freeAreaEntity.getFreeAreaBodyPc());
                    }
                }
            }
            // カスタムタグリプレース実行
            IModelFactory modelFactory = context.getModelFactory();
            IModel model = modelFactory.createModel();
            model.add(modelFactory.createText(freeAreaBody));
            structureHandler.replaceWith(model, false);

        } catch (Exception e) {
            //例外が発生した場合、ログ出力のみ行う
            ApplicationLogUtility appLogUtility = ApplicationContextUtility.getBean(ApplicationLogUtility.class);
            appLogUtility.writeExceptionLog(e);
        }
    }
}
