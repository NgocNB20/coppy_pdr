/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.restock;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeNewIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOutletIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReserveIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSaleIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockAddImportListDto;
import jp.co.hankyuhanshin.itec.hitmall.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 商品管理：入荷お知らせ抽象ページHelper
 *
 * @author st75001
 */
@Component
public class AbstractReStockHelper {

    /**
     * ロガー
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractReStockController.class);

    protected static final GoodsUtility goodsUtility = ApplicationContextUtility.getBean(GoodsUtility.class);

    /**
     * チェックされた入荷お知らせ商品のアドレス帳に送信するhtmlを作成<br/>
     *
     * @param reStockAddImportListDto 入荷お知らせメールアドレス帳登録Dto
     * @return html
     */
    public String setHtml(ReStockAddImportListDto reStockAddImportListDto) {
        StringBuilder sb = new StringBuilder();
        String webSiteUrl = PropertiesUtil.getSystemPropertiesValue("secure.connect.url");
        String spaceIcon = webSiteUrl + "/d_images/icon/space.gif";
        String goodsUrl = webSiteUrl + "/goods/index.html?gcd=" + reStockAddImportListDto.getGoodsCode();
        String goodsIconUrl = webSiteUrl + "/d_images/icon";
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        sb.append("              <table class=\"bk\" align=\"center\" valign=\"top\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;max-width:600px;margin:0;border:0\" width=\"600\">")
          .append("                <tbody>")
          .append("                  <tr>")
          .append("                    <td class=\"bk-inner\" style=\"font-size: 0px;\">")
          .append("                      <div class=\"col col12\" style=\"width: 600px; display: inline-block; vertical-align: top;\">")
          .append("                        <table class=\"sp\" valign=\"top\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"table-layout:fixed\">")
          .append("                          <tbody>")
          .append("                            <tr>")
          .append("                              <td class=\"td\" width=\"15\" height=\"30\" style=\"vertical-align:bottom;font-size:0;width:15px;height:30px\"><img width=\"15\" height=\"30\" style=\"vertical-align:bottom;font-size:0;width:15px;height:30px\" src=\"")
          .append(spaceIcon)
          .append("\"></td>")
          .append("                            </tr>")
          .append("                          </tbody>")
          .append("                        </table>")
          .append("                      </div>")
          .append("                    </td>")
          .append("                  </tr>")
          .append("                </tbody>")
          .append("              </table>")
          .append("              <table class=\"bk\" align=\"center\" valign=\"top\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;max-width:600px;margin:0;border:0\" width=\"600\">")
          .append("                <tbody>")
          .append("                  <tr>")
          .append("                    <td class=\"bk-inner\" style=\"font-size: 0px;\">")
          .append("                      <div class=\"col col12\" style=\"width: 600px; display: inline-block; vertical-align: top;\">")
          .append("                        <table class=\"sp\" valign=\"top\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"table-layout:fixed\">")
          .append("                          <tbody>")
          .append("                            <tr>")
          .append("                              <td class=\"td\" width=\"15\" height=\"30\" style=\"vertical-align:bottom;font-size:0;width:15px;height:30px\"><img width=\"15\" height=\"30\" style=\"vertical-align:bottom;font-size:0;width:15px;height:30px\" src=\"")
          .append(spaceIcon)
          .append("\"></td>")
          .append("                              <td class=\"in\" width=\"*\" height=\"*\"><div class=\"cn hr\" style=\"text-align: -webkit-center; line-height: 0;\">")
          .append("                                <table class=\"hr-table\" align=\"center\" width=\"100\" style=\"width:100%;font-size:0;line-height:0\">")
          .append("                                    <tbody><tr><td style=\"border-top: 1px solid #e4e4e4;\">&nbsp;</td></tr></tbody>")
          .append("                                </table></div>")
          .append("                              </td>")
          .append("                              <td class=\"td\" width=\"15\" height=\"30\" style=\"vertical-align:bottom;font-size:0;width:15px;height:30px\"><img width=\"15\" height=\"30\" style=\"vertical-align:bottom;font-size:0;width:15px;height:30px\" src=\"")
          .append(spaceIcon)
          .append("\"></td>")
          .append("                            </tr>")
          .append("                          </tbody>")
          .append("                        </table>")
          .append("                      </div>")
          .append("                    </td>")
          .append("                  </tr>")
          .append("                </tbody>")
          .append("              </table>")
          .append("              <table class=\"bk\" align=\"center\" valign=\"top\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;max-width:600px;margin:0;border:0\" width=\"600\">")
          .append("                <tbody>")
          .append("                  <tr>")
          .append("                    <td class=\"col col5\" style=\"width: 210px;  vertical-align: top;\">")
          .append("                      <table class=\"sp\" valign=\"top\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"table-layout:fixed\">")
          .append("                        <tbody>")
          .append("                          <tr>")
          .append("                            <td class=\"td\" height=\"*\" width=\"15\" style=\"vertical-align:bottom;font-size:0;width:15px\"><img height=\"1\" width=\"15\" style=\"vertical-align:bottom;font-size:0;width:15px\" src=\"")
          .append(spaceIcon)
          .append("\"></td>")
          .append("                            <td class=\"in\" width=\"*\" height=\"*\">")
          .append("                              <div>")
          .append("                                <table class=\"sp\" valign=\"top\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"table-layout:fixed\">")
          .append("                                  <tbody>")
          .append("                                    <tr>")
          .append("                                      <td class=\"in\" width=\"*\" height=\"*\">");
        String goodsImageUrl =
                        webSiteUrl + goodsUtility.getFrontGoodsImagePath(reStockAddImportListDto.getImageFileName());
        sb.append("                                        <div class=\"cn image\" style=\"text-align:center;text-align:-webkit-center\"><a target=\"_blank\" href=")
          .append(goodsUrl)
          .append("\"><img class=\"img\" width=\"180\" style=\"display:inline-block;vertical-align:top;max-width:100%;max-width:calc(100% - 0);height:auto;mso-border-alt:none;width:auto\" src=\"")
          .append(goodsImageUrl)
          .append("\"></a></div>");
        sb.append("                                      </td>")
          .append("                                    </tr>")
          .append("                                  </tbody>")
          .append("                                </table>")
          .append("                              </div>")
          .append("                            </td>")
          .append("                            <td class=\"td\" height=\"*\" width=\"15\" style=\"vertical-align:bottom;font-size:0;width:15px\"><img height=\"1\" width=\"15\" style=\"vertical-align:bottom;font-size:0;width:15px\" src=\"")
          .append(spaceIcon)
          .append("\"></td>")
          .append("                          </tr>")
          .append("                        </tbody>")
          .append("                      </table>")
          .append("                    </td>")
          .append("                    <td class=\"col col7\" style=\"width: 390px; vertical-align: top;\">")
          .append("                      <table class=\"sp\" valign=\"top\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"table-layout:fixed\">")
          .append("                        <tbody>")
          .append("                          <tr>")
          .append("                            <td class=\"td\" height=\"*\" width=\"15\" style=\"vertical-align:bottom;font-size:0;width:15px\"><img height=\"1\" width=\"15\" style=\"vertical-align:bottom;font-size:0;width:15px\" src=\"")
          .append(spaceIcon)
          .append("\"></td>")
          .append("                            <td class=\"in\" width=\"*\" height=\"*\">")
          .append("                              <div>")
          .append("                                <table class=\"sp\" valign=\"top\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"table-layout:fixed\">")
          .append("                                  <tbody>")
          .append("                                    <tr>")
          .append("                                      <td class=\"in\" width=\"*\" height=\"*\">")
          .append("                                        <div class=\"cn\" style=\"text-align: left; font-size: 0px; line-height: 1;\">");
        if (HTypeReserveIconFlag.ON.equals(reStockAddImportListDto.getReserveIconFlag())) {
            sb.append("                                          <span style=\"display: inline-block; font-size: 0px; line-height: 1;\"><img style=\"display:inline-block\" src=\"")
              .append(goodsIconUrl)
              .append("/icon-reserve.gif\" width=\"145\" height=\"29\"><img style=\"display:inline-block;width:5px;height:24px\" src=\"")
              .append(spaceIcon)
              .append("\" width=\"5\" height=\"29\"></span>");
        }
        if (HTypeNewIconFlag.ON.equals(reStockAddImportListDto.getNewIconFlag())) {
            sb.append("                                          <span style=\"display: inline-block; font-size: 0px; line-height: 1;\">")
              .append("<img style=\"display:inline-block\" src=\"")
              .append(goodsIconUrl)
              .append("/icon-new.gif\" width=\"70\" height=\"29\"><img style=\"display:inline-block;width:5px;height:24px\" src=\"")
              .append(spaceIcon)
              .append("\" width=\"5\" height=\"29\">")
              .append("</span>");
        }
        if (HTypeSaleIconFlag.ON.equals(reStockAddImportListDto.getSaleIconFlag())) {
            sb.append("                                          <span style=\"display: inline-block; font-size: 0px; line-height: 1;\"><img style=\"display:inline-block\" src=\"")
              .append(goodsIconUrl)
              .append("/icon-sale.gif\" width=\"70\" height=\"29\"><img style=\"display:inline-block;width:5px;height:24px\" src=\"")
              .append(spaceIcon)
              .append("\" width=\"5\" height=\"29\"></span>");
        }
        if (HTypeOutletIconFlag.ON.equals(reStockAddImportListDto.getOutletIconFlag())) {
            sb.append("                                          <span style=\"display: inline-block; font-size: 0px; line-height: 1;\"><img style=\"display:inline-block\" src=\"")
              .append(goodsIconUrl)
              .append("/icon-outlet.gif\" width=\"70\" height=\"29\"><img style=\"display:inline-block;width:5px;height:24px\" src=\"")
              .append(spaceIcon)
              .append("\" width=\"5\" height=\"29\"></span>");
        }
        sb.append("                                        </div>")
          .append("                                      </td>")
          .append("                                    </tr>")
          .append("                                    <tr>")
          .append("                                      <td class=\"in\" width=\"*\" height=\"*\">")
          .append("                                        <div class=\"cn text\" style=\"text-align: left; word-break: break-all; font-family: &quot;Hiragino Sans&quot;, &quot;メイリオ&quot;, Meiryo, Osaka, &quot;ＭＳ Ｐゴシック&quot;, &quot;MS PGothic&quot;, &quot;Hiragino Kaku Gothic ProN&quot;, sans-serif; font-size: 15px; line-height: 1.5; width: 360px;\">")
          .append("                                          <p style=\"margin: 0px; font-size: 15px;\">")
          .append(reStockAddImportListDto.getGoodsCode())
          .append("</p>")
          .append("                                          <p style=\"margin: 0px; font-size: 16px;\"><b>")
          .append(reStockAddImportListDto.getGoodsName())
          .append("</b></p>")
          .append("                                        </div>")
          .append("                                      </td>")
          .append("                                    </tr>")
          .append("                                    <tr>")
          .append("                                      <td class=\"td\" height=\"5\" width=\"*\" style=\"vertical-align:bottom;font-size:0;height:5px\"><img height=\"5\" width=\"1\" style=\"vertical-align:bottom;font-size:0;height:5px\" src=\"")
          .append(spaceIcon)
          .append("\"></td>")
          .append("                                    </tr>")
          .append("                                    <tr>")
          .append("                                      <td class=\"in\" width=\"*\" height=\"*\">")
          .append("                                        <div class=\"cn text\" style=\"text-align: left; word-break: break-all; font-family: &quot;Hiragino Sans&quot;, &quot;メイリオ&quot;, Meiryo, Osaka, &quot;ＭＳ Ｐゴシック&quot;, &quot;MS PGothic&quot;, &quot;Hiragino Kaku Gothic ProN&quot;, sans-serif; font-size: 15px; line-height: 1.5; width: 360px;\">")
          .append("                                          <p style=\"margin: 0px; font-size: 15px;\">");
        if (StringUtil.isNotEmpty(reStockAddImportListDto.getUnitTitle1())) {
            sb.append(reStockAddImportListDto.getUnitTitle1())
              .append("：")
              .append(reStockAddImportListDto.getUnitValue1())
              .append("<br>");
        }
        if (StringUtil.isNotEmpty(reStockAddImportListDto.getUnitTitle2())) {
            sb.append(reStockAddImportListDto.getUnitTitle2())
              .append("：")
              .append(reStockAddImportListDto.getUnitValue2());
        }
        sb.append("</p>")
          .append("                                        </div>")
          .append("                                      </td>")
          .append("                                    </tr>")
          .append("                                    <tr>")
          .append("                                      <td class=\"td\" height=\"8\" width=\"*\" style=\"vertical-align:bottom;font-size:0;height:8px\"><img height=\"8\" width=\"1\" style=\"vertical-align:bottom;font-size:0;height:8px\" src=\"")
          .append(spaceIcon)
          .append("\"></td>")
          .append("                                    </tr>")
          .append("                                    <tr>")
          .append("                                      <td class=\"in\" width=\"*\" height=\"*\">")
          .append("                                        <div class=\"cn text\" style=\"text-align: left; word-break: break-all; font-family: &quot;Hiragino Sans&quot;, &quot;メイリオ&quot;, Meiryo, Osaka, &quot;ＭＳ Ｐゴシック&quot;, &quot;MS PGothic&quot;, &quot;Hiragino Kaku Gothic ProN&quot;, sans-serif; font-size: 15px; line-height: 1.5; width: 360px;\">")
          .append("                                          <p class=\"sale\" style=\"margin: 0px; font-size: 16px; color: #ff0000;\">");
        if (StringUtil.isNotEmpty(reStockAddImportListDto.getGoodsPreDiscountPrice())) {
            sb.append("                                            <span style=\"font-size:14px\">")
              .append(reStockAddImportListDto.getGoodsPreDiscountPrice())
              .append("</span><br>");
        }

        if (HTypeSaleIconFlag.ON.equals(reStockAddImportListDto.getSaleIconFlag())) {
            if (reStockAddImportListDto.getPreDiscountPriceLow()
                                       .compareTo(reStockAddImportListDto.getPreDiscountPriceHight()) == 0) {
                sb.append("                                            <span style=\"font-size:14px\">セール：</span><b>")
                  .append(decimalFormat.format(reStockAddImportListDto.getPreDiscountPriceLow()))
                  .append("</b>&nbsp;<span style=\"font-size:14px\">円</span>");
            } else {
                sb.append("                                            <span style=\"font-size:14px\">セール：</span><b>")
                  .append(decimalFormat.format(reStockAddImportListDto.getPreDiscountPriceLow()))
                  .append("</b>&nbsp;円&nbsp;～&nbsp;<b>")
                  .append(decimalFormat.format(reStockAddImportListDto.getPreDiscountPriceHight()))
                  .append("</b>&nbsp;<span style=\"font-size:14px\">円</span>");
            }
        }
        sb.append("                                          </p>");
        if (reStockAddImportListDto.getGoodsPriceInTaxLow().compareTo(reStockAddImportListDto.getGoodsPriceInTaxHight())
            == 0) {
            sb.append("                                          <p style=\"margin: 0px; font-size: 16px;\"><b>")
              .append(decimalFormat.format(reStockAddImportListDto.getGoodsPriceInTaxLow()))
              .append("</b>&nbsp;<span style=\"font-size:14px\">円</span></p>");
        } else {
            sb.append("                                          <p class=\"regular\" style=\"margin: 3px 0px 0px 0px; font-size: 14px;\">通常価格：<span>")
              .append(decimalFormat.format(reStockAddImportListDto.getGoodsPriceInTaxLow()))
              .append("</span>円&nbsp;～&nbsp;<span>")
              .append(decimalFormat.format(reStockAddImportListDto.getGoodsPriceInTaxHight()))
              .append("</span>円</p>");
        }
        sb.append("                                        </div>")
          .append("                                      </td>")
          .append("                                    </tr>")
          .append("                                    <tr>")
          .append("                                      <td class=\"td\" height=\"10\" width=\"*\" style=\"vertical-align:bottom;font-size:0;height:10px\"><img height=\"10\" width=\"1\" style=\"vertical-align:bottom;font-size:0;height:10px\" src=\"")
          .append(spaceIcon)
          .append("\"></td>")
          .append("                                    </tr>")
          .append("                                    <tr>")
          .append("                                      <td class=\"in\" width=\"*\" height=\"*\">")
          .append("                                        <div>")
          .append("                                          <table valign=\"top\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">")
          .append("                                            <tbody>")
          .append("                                              <tr>")
          .append("                                                <td class=\"in\" width=\"*\" height=\"*\" align=\"center\" style=\"background-color:#f36b30; border-radius:18px;\">")
          .append("                                                  <a href=")
          .append(goodsUrl)
          .append("\" target=\"_blank\" style=\"font-size: 14px; color: #ffffff; text-decoration: none; background-color: #f36b30; display: block; min-width: 80px; font-family:&quot;Hiragino Sans&quot;, &quot;メイリオ&quot;, Meiryo, Osaka, &quot;ＭＳ Ｐゴシック&quot;, &quot;MS PGothic&quot;, &quot;Hiragino Kaku Gothic ProN&quot;, sans-serif; font-weight: bold; word-break: break-word; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding: 7px 32px; border: 1px solid #f36b30; line-height:21px; mso-line-height-rule: exactly;\">")
          .append("                                                    商品はこちら")
          .append("                                                </a>")
          .append("                                                </td>")
          .append("                                              </tr>")
          .append("                                            </tbody>")
          .append("                                          </table>")
          .append("                                        </div>")
          .append("                                      </td>")
          .append("                                    </tr>")
          .append("                                    <tr>")
          .append("                                      <td class=\"td\" height=\"10\" width=\"*\" style=\"vertical-align:bottom;font-size:0;height:10px\"><img height=\"10\" width=\"1\" style=\"vertical-align:bottom;font-size:0;height:10px\" src=\"")
          .append(spaceIcon)
          .append("\"></td>")
          .append("                                    </tr>")
          .append("                                  </tbody>")
          .append("                                </table>")
          .append("                              </div>")
          .append("                            </td>")
          .append("                            <td class=\"td\" height=\"*\" width=\"15\" style=\"vertical-align:bottom;font-size:0;width:15px\"><img height=\"1\" width=\"15\" style=\"vertical-align:bottom;font-size:0;width:15px\" src=\"")
          .append(spaceIcon)
          .append("\"></td>")
          .append("                          </tr>")
          .append("                        </tbody>")
          .append("                      </table>")
          .append("                    </td>")
          .append("                  </tr>")
          .append("                </tbody>")
          .append("              </table>");
        return sb.toString();
    }

    /**
     * アドレス帳インポートAPIに使用するhtmlを作成<br/>
     *
     * @param goodsHtml 商品情報
     * @return html
     */
    public String createAddImportHtml(List<String> goodsHtml) {
        StringBuilder htmlBuilder = new StringBuilder();
        // 商品情報をセット
        for (String goods : goodsHtml) {
            htmlBuilder.append(goods);
        }
        return htmlBuilder.toString();

    }
}
