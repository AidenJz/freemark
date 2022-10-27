package com.example.freemark.controller;

import com.example.freemark.utils.FreemarkerUtil;
import com.lowagie.text.pdf.BaseFont;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/freemarker/pdf")
public class Freemarker2PDFController {

    /**
     * 从模板文件中加载
     * @param response
     * @throws Exception
     */
    @GetMapping("/template/file")
    public void loadFromTemplate(HttpServletResponse response) throws Exception {

        String templateFileName = "report_history.ftl";

        Map<String, String> data = new HashMap<>();
        data.put("userName", "孙悟空");
        data.put("userNum", "110101199003074071");
        data.put("year", "2022");
        data.put("month", "01");
        data.put("day", "24");
        data.put("siteName", "帅链平台");
        data.put("ticketTitle", "帅票");
        data.put("tenEntName", "郑州市公共交通集团有限公司");
        data.put("indexTitle", "帅链科技");

        String html = loadTemplateFromFile(templateFileName, data);

        ByteArrayOutputStream baos = createPDFFromHtml(html);
        response.setContentType("application/pdf");
        OutputStream out = response.getOutputStream();
        baos.writeTo(out);
        baos.close();

    }

    /**
     * 从存储单元中加载
     * @param response
     * @throws Exception
     */
    @GetMapping("/template/storage")
    public void loadFromStorage(HttpServletResponse response) throws Exception {

        Map<String, String> data = new HashMap<>();
        data.put("userName", "孙悟空");
        data.put("userNum", "110101199003074071");
        data.put("year", "2022");
        data.put("month", "01");
        data.put("day", "24");
        data.put("siteName", "帅链平台");
        data.put("ticketTitle", "帅票");
        data.put("tenEntName", "郑州市公共交通集团有限公司");
        data.put("indexTitle", "帅链科技");

        String templateContent = "<html><head><style>span{border-bottom:1px solid black}p{line-height:1.5}@page{size:210mm 297mm;margin-bottom:1cm;padding:1em;@top-center{content:\"页眉中读取存储单元间位置\";font-family:SimSun;font-size:15px;color:#000};@bottom-center{content:\"页脚中间位置\";font-family:SimSun;font-size:15px;color:#000};@bottom-right{content:\"第\"counter(page)\"页 共\"counter(pages)\"页\";font-family:SimSun;font-size:15px;color:#000}}#pagenumber:before{content:counter(page)}#pagecount:before{content:counter(pages)}</style></head><body style=\"font-family: SimSun; \"><p><h1 style=\"text-align: center;\">公司授权委托书</h1></p><br/><br/><p>致：<span>${tenEntName!}</span></p><p>&#160;&#160;&#160;&#160;我单位现委托<span>${userName!}</span>作为我单位合法委托代理人，授权其代表我单位进行<span>${indexTitle!}</span>账户相关管理工作。该委托代理人的授权范围为:代表我单位在<span>${indexTitle!}</span>上注册、签署文件、使用<span>${indexTitle!}</span>、资金交易、融资等与<span>${indexTitle!}</span>有关的一切事务。在整个<span>${indexTitle!}</span>使用过程中，该代理人的一切行为，均代表本单位，与本单位的行为具有同等法律效力。本单位将承担该代理人行为带来的全部法律后果和法律责任。</p><p>&#160;&#160;&#160;&#160;以上授权委托有效期自盖章之日（含当日）起至<span>${indexTitle!}</span>账户注销和/或<span>${indexTitle!}</span>全部融资款项结算完毕终止。</p><p>&#160;&#160;&#160;&#160;代理人无权转换代理权。</p><p>&#160;&#160;&#160;&#160;特此委托。</p><p>&#160;&#160;&#160;&#160;代理人姓名:<span>${userName!}</span></p><p>&#160;&#160;&#160;&#160;身份证号码:<span>${userNum!}</span></p><br/><br/><p>&#160;&#160;&#160;&#160;委托人(盖章):</p><p>&#160;&#160;&#160;&#160;日期:<span>${year!}</span>年<span>${month!}</span>月<span>${day!}</span>日</p><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><p>附:委托代理人身份证复印件（<span>正反面、</span>加盖公章）</p><div id=\"header\"></div></body></html>";

        String html = loadTemplateFromStorage(templateContent, data);

        ByteArrayOutputStream baos = createPDFFromHtml(html);
        response.setContentType("application/pdf");
        OutputStream out = response.getOutputStream();
        baos.writeTo(out);
        baos.close();

    }

    public static String loadTemplateFromFile(String templateFileName, Map<String, String> data) throws IOException, TemplateException {

        // 创建一个FreeMarker实例, 负责管理FreeMarker模板的Configuration实例
        Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        // 指定FreeMarker模板文件的位置
        cfg.setClassForTemplateLoading(FreemarkerUtil.class,"/templates/freemarkers");

        // 设置模板的编码格式
        cfg.setEncoding(Locale.CHINA, "UTF-8");
        // 获取模板文件
        Template template = cfg.getTemplate(templateFileName, "UTF-8");
        StringWriter writer = new StringWriter();

        // 将数据输出到html中
        template.process(data, writer);
        writer.flush();

        String html = writer.toString();

        return html;
    }

    /**
     * 从存储单元中中加载
     * @param templateFileContent
     * @param data
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public static String loadTemplateFromStorage(String templateFileContent, Map<String, String> data) throws IOException, TemplateException {

        String templateName = "自定义模板名称";

        StringWriter stringWriter = new StringWriter();

        StringTemplateLoader loader = new StringTemplateLoader();
        loader.putTemplate(templateName, templateFileContent);

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setTemplateLoader(loader);
        cfg.setDefaultEncoding("UTF-8");
        Template template = cfg.getTemplate(templateName);
        template.process(data, stringWriter);

        String html = stringWriter.toString();

        return html;
    }

    public static ByteArrayOutputStream createPDFFromHtml(String html) throws Exception {

        ITextRenderer renderer = new ITextRenderer();
        OutputStream out = new ByteArrayOutputStream();

        // 设置 css中 的字体样式（暂时仅支持宋体和黑体） 必须，不然中文不显示
        renderer.getFontResolver().addFont("/font/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        // 把html代码传入渲染器中
        renderer.setDocumentFromString(html);

//            // 设置模板中的图片路径 （这里的images在resources目录下） 模板中img标签src路径需要相对路径加图片名 如<img src="images/xh.jpg"/>
//            String url = PDFTemplateUtil.class.getClassLoader().getResource("images").toURI().toString();
//            renderer.getSharedContext().setBaseURL(url);
        renderer.layout();

        renderer.createPDF(out, false);
        renderer.finishPDF();
        out.flush();

        ByteArrayOutputStream byteArrayOutputStream = (ByteArrayOutputStream) out;

        return byteArrayOutputStream;
    }


}


