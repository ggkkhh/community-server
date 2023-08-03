package com.roydon.openai.util;

import com.roydon.openai.domain.OpenAi;
import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 调用OpenAi的49中方法
 */
public class OpenAiUtils {
    public static final Map<String, OpenAi> PARMS = new HashMap<>();

    static {
        PARMS.put("OpenAi01", new OpenAi("OpenAi01", "问&答", "依据现有知识库问&答", "text-davinci-003", "Q: %s\nA:", 0.0, 1.0, 1.0, 0.0, 0.0, "\n"));
        PARMS.put("OpenAi02", new OpenAi("OpenAi02", "语法纠正", "将句子转换成标准的英语，输出结果始终是英文", "text-davinci-003", "%s", 0.0, 1.0, 1.0, 0.0, 0.0, ""));
        PARMS.put("OpenAi03", new OpenAi("OpenAi03", "内容概况", "将一段话，概况中心", "text-davinci-003", "Summarize this for a second-grade student:\n%s", 0.7, 1.0, 1.0, 0.0, 0.0, ""));
        PARMS.put("OpenAi04", new OpenAi("OpenAi04", "生成OpenAi的代码", "一句话生成OpenAi的代码", "code-davinci-002", "\"\"\"\nUtil exposes the following:\nutil.openai() -> authenticates & returns the openai module, which has the following functions:\nopenai.Completion.create(\n    prompt=\"<my prompt>\", # The prompt to start completing from\n    max_tokens=123, # The max number of tokens to generate\n    temperature=1.0 # A measure of randomness\n    echo=True, # Whether to return the prompt in addition to the generated completion\n)\n\"\"\"\nimport util\n\"\"\"\n%s\n\"\"\"\n\n", 0.0, 1.0, 1.0, 0.0, 0.0, "\"\"\""));
        PARMS.put("OpenAi05", new OpenAi("OpenAi05", "程序命令生成", "一句话生成程序的命令，目前支持操作系统指令比较多", "text-davinci-003", "Convert this text to a programmatic command:\n\nExample: Ask Constance if we need some bread\nOutput: send-msg `find constance` Do we need some bread?\n\n%s", 0.0, 1.0, 1.0, 0.2, 0.0, ""));
        PARMS.put("OpenAi06", new OpenAi("OpenAi06", "语言翻译", "把一种语法翻译成其它几种语言", "text-davinci-003", "Translate this into %s:\n%s", 0.3, 1.0, 1.0, 0.0, 0.0, ""));
        PARMS.put("OpenAi07", new OpenAi("OpenAi07", "Stripe国际API生成", "一句话生成Stripe国际支付API", "code-davinci-002", "\"\"\"\nUtil exposes the following:\n\nutil.stripe() -> authenticates & returns the stripe module; usable as stripe.Charge.create etc\n\"\"\"\nimport util\n\"\"\"\n%s\n\"\"\"", 0.0, 1.0, 1.0, 0.0, 0.0, "\"\"\""));
        PARMS.put("OpenAi08", new OpenAi("OpenAi08", "SQL语句生成", "依据上下文中的表信息，生成SQL语句", "code-davinci-002", "### %s SQL tables, 表字段信息如下:\n%s\n#\n### %s\n %s", 0.0, 1.0, 1.0, 0.0, 0.0, "# ;"));
        PARMS.put("OpenAi09", new OpenAi("OpenAi09", "结构化生成", "对于非结构化的数据抽取其中的特征生成结构化的表格", "text-davinci-003", "A table summarizing, use Chinese:\n%s\n", 0.0, 1.0, 1.0, 0.0, 0.0, ""));
        PARMS.put("OpenAi10", new OpenAi("OpenAi10", "信息分类", "把一段信息继续分类", "text-davinci-003", "%s\n分类:", 0.0, 1.0, 1.0, 0.0, 0.0, ""));
        PARMS.put("OpenAi11", new OpenAi("OpenAi11", "Python代码解释", "把代码翻译成文字，用来解释程序的作用", "code-davinci-002", "# %s \n %s \n\n# 解释代码作用\n\n#", 0.0, 1.0, 1.0, 0.0, 0.0, ""));
        PARMS.put("OpenAi12", new OpenAi("OpenAi12", "文字转表情符号", "将文本编码成表情服务", "text-davinci-003", "转换文字为表情。\n%s：", 0.8, 1.0, 1.0, 0.0, 0.0, "\n"));
        PARMS.put("OpenAi13", new OpenAi("OpenAi13", "时间复杂度计算", "求一段代码的时间复杂度", "text-davinci-003", "%s\n\"\"\"\n函数的时间复杂度是", 0.0, 1.0, 1.0, 0.0, 0.0, "\n"));
        PARMS.put("OpenAi14", new OpenAi("OpenAi14", "程序代码翻译", "把一种语言的代码翻译成另外一种语言的代码", "code-davinci-002", "##### 把这段代码从%s翻译成%s\n### %s\n    \n   %s\n    \n### %s", 0.0, 1.0, 1.0, 0.0, 0.0, "###"));
        PARMS.put("OpenAi15", new OpenAi("OpenAi15", "高级情绪评分", "支持批量列表的方式检查情绪", "text-davinci-003", "对下面内容进行情感分类:\n%s\"\n情绪评级:", 0.0, 1.0, 1.0, 0.0, 0.0, ""));
        PARMS.put("OpenAi16", new OpenAi("OpenAi16", "代码解释", "对一段代码进行解释", "code-davinci-002", "代码:\n%s\n\"\"\"\n上面的代码在做什么:\n1. ", 0.0, 1.0, 1.0, 0.0, 0.0, "\"\"\""));
        PARMS.put("OpenAi17", new OpenAi("OpenAi17", "关键字提取", "提取一段文本中的关键字", "text-davinci-003", "抽取下面内容的关键字:\n%s", 0.5, 1.0, 1.0, 0.8, 0.0, ""));
        PARMS.put("OpenAi18", new OpenAi("OpenAi18", "问题解答", "类似解答题", "text-davinci-003", "Q: %s\nA: ?", 0.0, 1.0, 1.0, 0.0, 0.0, ""));
        PARMS.put("OpenAi19", new OpenAi("OpenAi19", "广告设计", "给一个产品设计一个广告", "text-davinci-003", "为下面的产品创作一个创业广告，用于投放到抖音上：\n产品:%s.", 0.5, 1.0, 1.0, 0.0, 0.0, ""));
        PARMS.put("OpenAi20", new OpenAi("OpenAi20", "产品取名", "依据产品描述和种子词语，给一个产品取一个好听的名字", "text-davinci-003", "产品描述: %s.\n种子词: %s.\n产品名称: ", 0.8, 1.0, 1.0, 0.0, 0.0, ""));
        PARMS.put("OpenAi21", new OpenAi("OpenAi21", "句子简化", "把一个长句子简化成一个短句子", "text-davinci-003", "%s\nTl;dr: ", 0.7, 1.0, 1.0, 0.0, 1.0, ""));
        PARMS.put("OpenAi22", new OpenAi("OpenAi22", "修复代码Bug", "自动修改代码中的bug", "code-davinci-002", "##### 修复下面代码的bug\n### %s\n %s\n###  %s\n", 0.0, 1.0, 1.0, 0.0, 0.0, "###"));
        PARMS.put("OpenAi23", new OpenAi("OpenAi23", "表格填充数据", "自动为一个表格生成数据", "text-davinci-003", "spreadsheet ,%s rows:\n%s\n", 0.5, 1.0, 1.0, 0.0, 0.0, ""));
        PARMS.put("OpenAi24", new OpenAi("OpenAi24", "语言聊天机器人", "各种开发语言的两天机器人", "code-davinci-002", "You: %s\n%s机器人:", 0.0, 1.0, 1.0, 0.5, 0.0, "You: "));
        PARMS.put("OpenAi25", new OpenAi("OpenAi25", "机器学习机器人", "机器学习模型方面的机器人", "text-davinci-003", "You: %s\nML机器人:", 0.3, 1.0, 1.0, 0.5, 0.0, "You: "));
        PARMS.put("OpenAi26", new OpenAi("OpenAi26", "清单制作", "可以列出各方面的分类列表，比如歌单", "text-davinci-003", "列出10%s:", 0.5, 1.0, 1.0, 0.52, 0.5, "11.0"));
        PARMS.put("OpenAi27", new OpenAi("OpenAi27", "文本情绪分析", "对一段文字进行情绪分析", "text-davinci-003", "推断下面文本的情绪是积极的, 中立的, 还是消极的.\n文本: \"%s\"\n观点:", 0.0, 1.0, 1.0, 0.5, 0.0, ""));
        PARMS.put("OpenAi28", new OpenAi("OpenAi28", "航空代码抽取", "抽取文本中的航空diam信息", "text-davinci-003", "抽取下面文本中的航空代码：\n文本：\"%s\"\n航空代码：", 0.0, 1.0, 1.0, 0.0, 0.0, "\n"));
        PARMS.put("OpenAi29", new OpenAi("OpenAi29", "生成SQL语句", "无上下文，语句描述生成SQL", "text-davinci-003", "%s", 0.3, 1.0, 1.0, 0.0, 0.0, ""));
        PARMS.put("OpenAi30", new OpenAi("OpenAi30", "抽取联系信息", "从文本中抽取联系方式", "text-davinci-003", "从下面文本中抽取%s:\n%s", 0.0, 1.0, 1.0, 0.0, 0.0, ""));
        PARMS.put("OpenAi31", new OpenAi("OpenAi31", "程序语言转换", "把一种语言转成另外一种语言", "code-davinci-002", "#%s to %s:\n%s:%s\n\n%s:", 0.0, 1.0, 1.0, 0.0, 0.0, ""));
        PARMS.put("OpenAi32", new OpenAi("OpenAi32", "好友聊天", "模仿好友聊天", "text-davinci-003", "You: %s\n好友:", 0.5, 1.0, 1.0, 0.5, 0.0, "You:"));
        PARMS.put("OpenAi33", new OpenAi("OpenAi33", "颜色生成", "依据描述生成对应颜色", "text-davinci-003", "%s:\nbackground-color: ", 0.0, 1.0, 1.0, 0.0, 0.0, ";"));
        PARMS.put("OpenAi34", new OpenAi("OpenAi34", "程序文档生成", "自动为程序生成文档", "code-davinci-002", "# %s\n \n%s\n# 上述代码的详细、高质量文档字符串：\n\"\"\"", 0.0, 1.0, 1.0, 0.0, 0.0, "#\"\"\""));
        PARMS.put("OpenAi35", new OpenAi("OpenAi35", "段落创作", "依据短语生成相关文短", "text-davinci-003", "为下面短语创建一个中文段:\n%s:\n", 0.5, 1.0, 1.0, 0.0, 0.0, ""));
        PARMS.put("OpenAi36", new OpenAi("OpenAi36", "代码压缩", "把多行代码简单的压缩成一行", "code-davinci-002", "将下面%s代码转成一行:\n%s\n%s一行版本:", 0.0, 1.0, 1.0, 0.0, 0.0, ";"));
        PARMS.put("OpenAi37", new OpenAi("OpenAi37", "故事创作", "依据一个主题创建一个故事", "text-davinci-003", "主题: %s\n故事创作:", 0.8, 1.0, 1.0, 0.5, 0.0, ""));
        PARMS.put("OpenAi38", new OpenAi("OpenAi38", "人称转换", "第一人称转第3人称", "text-davinci-003", "把下面内容从第一人称转为第三人称 (性别女):\n%s\n", 0.0, 1.0, 1.0, 0.0, 0.0, ""));
        PARMS.put("OpenAi39", new OpenAi("OpenAi39", "摘要说明", "依据笔记生成摘要说明", "text-davinci-003", "将下面内容转换成将下%s摘要：\n%s", 0.0, 1.0, 1.0, 0.0, 0.0, ""));
        PARMS.put("OpenAi40", new OpenAi("OpenAi40", "头脑风暴", "给定一个主题，让其生成一些主题相关的想法", "text-davinci-003", "头脑风暴一些关于%s的想法：", 0.6, 1.0, 1.0, 1.0, 1.0, ""));
        PARMS.put("OpenAi41", new OpenAi("OpenAi41", "ESRB文本分类", "按照ESRB进行文本分类", "text-davinci-003", "Provide an ESRB rating for the following text:\\n\\n\\\"%s\"\\n\\nESRB rating:", 0.3, 1.0, 1.0, 0.0, 0.0, "\n"));
        PARMS.put("OpenAi42", new OpenAi("OpenAi42", "提纲生成", "按照提示为相关内容生成提纲", "text-davinci-003", "为%s提纲:", 0.3, 1.0, 1.0, 0.0, 0.0, ""));
        PARMS.put("OpenAi43", new OpenAi("OpenAi43", "美食制作（后果自负）", "依据美食名称和材料生成美食的制作步骤", "text-davinci-003", "依据下面成分和美食，生成制作方法:\n%s\n成分:\n%s\n制作方法:", 0.3, 1.0, 1.0, 0.0, 0.0, ""));
        PARMS.put("OpenAi44", new OpenAi("OpenAi44", "AI聊天", "与AI机器进行聊天", "text-davinci-003", "Human: %s", 0.9, 1.0, 1.0, 0.0, 0.6, "Human:AI:"));
        PARMS.put("OpenAi45", new OpenAi("OpenAi45", "摆烂聊天", "与讽刺机器进行聊天", "text-davinci-003", "Marv不情愿的回答问题.\nYou:%s\nMarv:", 0.5, 0.3, 1.0, 0.5, 0.0, ""));
        PARMS.put("OpenAi46", new OpenAi("OpenAi46", "分解步骤", "把一段文本分解成几步来完成", "text-davinci-003", "为下面文本生成次序列表，并增加列表数子: \n%s\n", 0.3, 1.0, 1.0, 0.0, 0.0, ""));
        PARMS.put("OpenAi47", new OpenAi("OpenAi47", "点评生成", "依据文本内容自动生成点评", "text-davinci-003", "依据下面内容，进行点评:\n%s\n点评:", 0.5, 1.0, 1.0, 0.0, 0.0, ""));
        PARMS.put("OpenAi48", new OpenAi("OpenAi48", "知识学习", "可以为学习知识自动解答", "text-davinci-003", "%s", 0.3, 1.0, 1.0, 0.0, 0.0, ""));
        PARMS.put("OpenAi49", new OpenAi("OpenAi49", "面试", "生成面试题", "text-davinci-003", "创建10道%s相关的面试题（中文）：\n", 0.5, 1.0, 10.0, 0.0, 0.0, ""));
    }

    public static String OPENAPI_TOKEN = "";
    public static Integer TIMEOUT = null;

    /**
     * 获取ai
     *
     * @param openAi
     * @param prompt
     * @return
     */
    public static List<CompletionChoice> getAiResult(OpenAi openAi, String prompt) {
        if (TIMEOUT == null || TIMEOUT < 1000) {
            TIMEOUT = 3000;
        }
        OpenAiService service = new OpenAiService(OPENAPI_TOKEN, TIMEOUT);
        CompletionRequest.CompletionRequestBuilder builder = CompletionRequest.builder().model(openAi.getModel()).prompt(prompt).temperature(openAi.getTemperature()).maxTokens(1000).topP(openAi.getTopP()).frequencyPenalty(openAi.getFrequencyPenalty()).presencePenalty(openAi.getPresencePenalty());
        if (!StringUtils.isEmpty(openAi.getStop())) {
            builder.stop(Arrays.asList(openAi.getStop().split(",")));
        }
        CompletionRequest completionRequest = builder.build();
        return service.createCompletion(completionRequest).getChoices();
    }

    /**
     * 问答
     *
     * @param question
     * @return
     */
    public static List<CompletionChoice> getQuestionAnswer(String question) {
        OpenAi openAi = PARMS.get("OpenAi01");
        return getAiResult(openAi, String.format(openAi.getPrompt(), question));
    }

    /**
     * 语法纠错
     *
     * @param text
     * @return
     */
    public static List<CompletionChoice> getGrammarCorrection(String text) {
        OpenAi openAi = PARMS.get("OpenAi02");
        return getAiResult(openAi, String.format(openAi.getPrompt(), text));
    }

    /**
     * 将一段话，概况中心
     *
     * @param text
     * @return
     */
    public static List<CompletionChoice> getSummarize(String text) {
        OpenAi openAi = PARMS.get("OpenAi03");
        return getAiResult(openAi, String.format(openAi.getPrompt(), text));
    }

    /**
     * 一句话生成OpenAi的代码
     *
     * @param text
     * @return
     */
    public static List<CompletionChoice> getOpenAiApi(String text) {
        OpenAi openAi = PARMS.get("OpenAi04");
        return getAiResult(openAi, String.format(openAi.getPrompt(), text));
    }

    /**
     * 一句话生成程序的命令，目前支持操作系统指令比较多
     *
     * @param text
     * @return
     */
    public static List<CompletionChoice> getTextToCommand(String text) {
        OpenAi openAi = PARMS.get("OpenAi05");
        return getAiResult(openAi, String.format(openAi.getPrompt(), text));
    }

    /**
     * 把一种语法翻译成其它几种语言
     *
     * @param text
     * @return
     */
    public static List<CompletionChoice> getTranslatesLanguages(String text, String translatesLanguages) {
        if (StringUtils.isEmpty(translatesLanguages)) {
            translatesLanguages = "  1. French, 2. Spanish and 3. English";
        }
        OpenAi openAi = PARMS.get("OpenAi06");
        return getAiResult(openAi, String.format(openAi.getPrompt(), translatesLanguages, text));
    }

    /**
     * 一句话生成Stripe国际支付API
     *
     * @param text
     * @return
     */
    public static List<CompletionChoice> getStripeApi(String text) {
        OpenAi openAi = PARMS.get("OpenAi07");
        return getAiResult(openAi, String.format(openAi.getPrompt(), text));
    }


    /**
     * 依据上下文中的表信息，生成SQL语句
     *
     * @param databaseType 数据库类型
     * @param tables       上午依赖的表和字段 Employee(id, name, department_id)
     * @param text         SQL描述
     * @param sqlType      sql类型，比如SELECT
     * @return
     */
    public static List<CompletionChoice> getStripeApi(String databaseType, List<String> tables, String text, String sqlType) {
        OpenAi openAi = PARMS.get("OpenAi08");
        StringJoiner joiner = new StringJoiner("\n");
        for (int i = 0; i < tables.size(); i++) {
            joiner.add("# " + tables);
        }
        return getAiResult(openAi, String.format(openAi.getPrompt(), databaseType, joiner.toString(), text, sqlType));
    }

    /**
     * 对于非结构化的数据抽取其中的特征生成结构化的表格
     *
     * @param text 非结构化的数据
     * @return
     */
    public static List<CompletionChoice> getUnstructuredData(String text) {
        OpenAi openAi = PARMS.get("OpenAi09");
        return getAiResult(openAi, String.format(openAi.getPrompt(), text));
    }

    /**
     * 把一段信息继续分类
     *
     * @param text 要分类的文本
     * @return
     */
    public static List<CompletionChoice> getTextCategory(String text) {
        OpenAi openAi = PARMS.get("OpenAi10");
        return getAiResult(openAi, String.format(openAi.getPrompt(), text));
    }

    /**
     * 把一段信息继续分类
     *
     * @param codeType 代码类型，比如Python
     * @param code     要解释的代码
     * @return
     */
    public static List<CompletionChoice> getCodeExplain(String codeType, String code) {
        OpenAi openAi = PARMS.get("OpenAi11");
        return getAiResult(openAi, String.format(openAi.getPrompt(), codeType, code));
    }

    /**
     * 将文本编码成表情服务
     *
     * @param text 文本
     * @return
     */
    public static List<CompletionChoice> getTextEmoji(String text) {
        OpenAi openAi = PARMS.get("OpenAi12");
        return getAiResult(openAi, String.format(openAi.getPrompt(), text));
    }

    /**
     * 求一段代码的时间复杂度
     *
     * @param code 代码
     * @return
     */
    public static List<CompletionChoice> getTimeComplexity(String code) {
        OpenAi openAi = PARMS.get("OpenAi13");
        return getAiResult(openAi, String.format(openAi.getPrompt(), code));
    }


    /**
     * 把一种语言的代码翻译成另外一种语言的代码
     *
     * @param fromLanguage 要翻译的代码语言
     * @param toLanguage   要翻译成的代码语言
     * @param code         代码
     * @return
     */
    public static List<CompletionChoice> getTranslateProgramming(String fromLanguage, String toLanguage, String code) {
        OpenAi openAi = PARMS.get("OpenAi14");
        return getAiResult(openAi, String.format(openAi.getPrompt(), fromLanguage, toLanguage, fromLanguage, code, toLanguage));
    }

    /**
     * 支持批量列表的方式检查情绪
     *
     * @param texts 文本
     * @return
     */
    public static List<CompletionChoice> getBatchTweetClassifier(List<String> texts) {
        OpenAi openAi = PARMS.get("OpenAi15");
        StringJoiner stringJoiner = new StringJoiner("\n");
        for (int i = 0; i < texts.size(); i++) {
            stringJoiner.add((i + 1) + ". " + texts.get(i));
        }
        return getAiResult(openAi, String.format(openAi.getPrompt(), stringJoiner.toString()));
    }

    /**
     * 对一段代码进行解释
     *
     * @param code 文本
     * @return
     */
    public static List<CompletionChoice> getExplainCOde(String code) {
        OpenAi openAi = PARMS.get("OpenAi16");
        return getAiResult(openAi, String.format(openAi.getPrompt(), code));
    }

    /**
     * 提取一段文本中的关键字
     *
     * @param text 文本
     * @return
     */
    public static List<CompletionChoice> getTextKeywords(String text) {
        OpenAi openAi = PARMS.get("OpenAi17");
        return getAiResult(openAi, String.format(openAi.getPrompt(), text));
    }

    /**
     * 事实回答答题
     *
     * @param text 文本
     * @return
     */
    public static List<CompletionChoice> getFactualAnswering(String text) {
        OpenAi openAi = PARMS.get("OpenAi18");
        return getAiResult(openAi, String.format(openAi.getPrompt(), text));
    }

    /**
     * 给一个产品设计一个广告
     *
     * @param text 文本
     * @return
     */
    public static List<CompletionChoice> getAd(String text) {
        OpenAi openAi = PARMS.get("OpenAi19");
        return getAiResult(openAi, String.format(openAi.getPrompt(), text));
    }

    /**
     * 依据产品描述和种子词语，给一个产品取一个好听的名字
     *
     * @param productDescription 产品描述
     * @param seedWords          种子词语
     * @return
     */
    public static List<CompletionChoice> getProductName(String productDescription, String seedWords) {
        OpenAi openAi = PARMS.get("OpenAi20");
        return getAiResult(openAi, String.format(openAi.getPrompt(), productDescription, seedWords));
    }

    /**
     * 把一个长句子简化成一个短句子
     *
     * @param text 长句子
     * @return
     */
    public static List<CompletionChoice> getProductName(String text) {
        OpenAi openAi = PARMS.get("OpenAi21");
        return getAiResult(openAi, String.format(openAi.getPrompt(), text));
    }

    /**
     * 自动修改代码中的bug
     *
     * @param codeType 语言类型
     * @param code     代码
     * @return
     */
    public static List<CompletionChoice> getBugFixer(String codeType, String code) {
        OpenAi openAi = PARMS.get("OpenAi22");
        return getAiResult(openAi, String.format(openAi.getPrompt(), codeType, code, codeType));
    }

    /**
     * 自动为一个表格生成数据
     *
     * @param rows    生成的行数
     * @param headers 数据表头，格式如：姓名| 年龄|性别|生日
     * @return
     */
    public static List<CompletionChoice> getFillData(int rows, String headers) {
        OpenAi openAi = PARMS.get("OpenAi23");
        return getAiResult(openAi, String.format(openAi.getPrompt(), rows, headers));
    }

    /**
     * 各种开发语言的两天机器人
     *
     * @param question             你的问题
     * @param programmingLanguages 语言 比如Java JavaScript
     * @return
     */
    public static List<CompletionChoice> getProgrammingLanguageChatbot(String question, String programmingLanguages) {
        OpenAi openAi = PARMS.get("OpenAi24");
        return getAiResult(openAi, String.format(openAi.getPrompt(), question, programmingLanguages));
    }

    /**
     * 机器学习模型方面的机器人
     *
     * @param question 你的问题
     * @return
     */
    public static List<CompletionChoice> getMLChatbot(String question) {
        OpenAi openAi = PARMS.get("OpenAi25");
        return getAiResult(openAi, String.format(openAi.getPrompt(), question));
    }

    /**
     * 可以列出各方面的分类列表，比如歌单
     *
     * @param text 清单描述
     * @return
     */
    public static List<CompletionChoice> getListMaker(String text) {
        OpenAi openAi = PARMS.get("OpenAi26");
        return getAiResult(openAi, String.format(openAi.getPrompt(), text));
    }

    /**
     * 对一段文字进行情绪分析
     *
     * @param text
     * @return
     */
    public static List<CompletionChoice> getTweetClassifier(String text) {
        OpenAi openAi = PARMS.get("OpenAi27");
        return getAiResult(openAi, String.format(openAi.getPrompt(), text));
    }

    /**
     * 抽取文本中的航空代码信息
     *
     * @param text
     * @return
     */
    public static List<CompletionChoice> getAirportCodeExtractor(String text) {
        OpenAi openAi = PARMS.get("OpenAi28");
        return getAiResult(openAi, String.format(openAi.getPrompt(), text));
    }

    /**
     * 无上下文，语句描述生成SQL
     *
     * @param text
     * @return
     */
    public static List<CompletionChoice> getSQL(String text) {
        OpenAi openAi = PARMS.get("OpenAi29");
        return getAiResult(openAi, String.format(openAi.getPrompt(), text));
    }

    /**
     * 从文本中抽取联系方式
     *
     * @param extractContent 抽取内容描述
     * @param text
     * @return 从下面文本中抽取邮箱和电话:\n教育行业A股IPO第一股（股票代码 003032）\n全国咨询/投诉热线：400-618-4000    举报邮箱:mc@itcast.cn
     */
    public static List<CompletionChoice> getExtractContactInformation(String extractContent, String text) {
        OpenAi openAi = PARMS.get("OpenAi30");
        return getAiResult(openAi, String.format(openAi.getPrompt(), extractContent, text));
    }

    /**
     * 把一种语言转成另外一种语言代码
     *
     * @param fromCodeType 当前代码类型
     * @param toCodeType   转换的代码类型
     * @param code
     * @return
     */
    public static List<CompletionChoice> getTransformationCode(String fromCodeType, String toCodeType, String code) {
        OpenAi openAi = PARMS.get("OpenAi31");
        return getAiResult(openAi, String.format(openAi.getPrompt(), fromCodeType, toCodeType, fromCodeType, code, toCodeType));
    }

    /**
     * 模仿好友聊天
     *
     * @param question
     * @return
     */
    public static List<CompletionChoice> getFriendChat(String question) {
        OpenAi openAi = PARMS.get("OpenAi32");
        return getAiResult(openAi, String.format(openAi.getPrompt(), question));
    }

    /**
     * 依据描述生成对应颜色
     *
     * @param text
     * @return
     */
    public static List<CompletionChoice> getMoodToColor(String text) {
        OpenAi openAi = PARMS.get("OpenAi33");
        return getAiResult(openAi, String.format(openAi.getPrompt(), text));
    }

    /**
     * 自动为程序生成文档
     *
     * @param codeType 语言
     * @param code
     * @return
     */
    public static List<CompletionChoice> getCodeDocument(String codeType, String code) {
        OpenAi openAi = PARMS.get("OpenAi34");
        return getAiResult(openAi, String.format(openAi.getPrompt(), codeType, code));
    }

    /**
     * 依据短语生成相关文短
     *
     * @param text 短语
     * @return
     */
    public static List<CompletionChoice> getCreateAnalogies(String text) {
        OpenAi openAi = PARMS.get("OpenAi35");
        return getAiResult(openAi, String.format(openAi.getPrompt(), text));
    }

    /**
     * 把多行代码简单的压缩成一行
     *
     * @param codeType 语言
     * @param code
     * @return
     */
    public static List<CompletionChoice> getCodeLine(String codeType, String code) {
        OpenAi openAi = PARMS.get("OpenAi36");
        return getAiResult(openAi, String.format(openAi.getPrompt(), codeType, code, codeType));
    }

    /**
     * 依据一个主题创建一个故事
     *
     * @param topic 创作主题
     * @return
     */
    public static List<CompletionChoice> getStory(String topic) {
        OpenAi openAi = PARMS.get("OpenAi37");
        return getAiResult(openAi, String.format(openAi.getPrompt(), topic));
    }

    /**
     * 第一人称转第3人称
     *
     * @param text
     * @return
     */
    public static List<CompletionChoice> getStoryCreator(String text) {
        OpenAi openAi = PARMS.get("OpenAi38");
        return getAiResult(openAi, String.format(openAi.getPrompt(), text));
    }

    /**
     * 依据笔记生成摘要说明
     *
     * @param scene 生成的摘要场景
     * @param note  记录的笔记
     * @return
     */
    public static List<CompletionChoice> getNotesToSummary(String scene, String note) {
        OpenAi openAi = PARMS.get("OpenAi39");
        return getAiResult(openAi, String.format(openAi.getPrompt(), note));
    }

    /**
     * 给定一个主题，让其生成一些主题相关的想法
     *
     * @param topic 头脑风暴关键词
     * @return
     */
    public static List<CompletionChoice> getIdeaGenerator(String topic) {
        OpenAi openAi = PARMS.get("OpenAi40");
        return getAiResult(openAi, String.format(openAi.getPrompt(), topic));
    }

    /**
     * 按照ESRB进行文本分类
     *
     * @param text 文本
     * @return
     */
    public static List<CompletionChoice> getESRBRating(String text) {
        OpenAi openAi = PARMS.get("OpenAi41");
        return getAiResult(openAi, String.format(openAi.getPrompt(), text));
    }

    /**
     * 按照提示为相关内容生成提纲
     *
     * @param text 场景，比如 数据库软件生成大学毕业论文
     * @return
     */
    public static List<CompletionChoice> getEssayOutline(String text) {
        OpenAi openAi = PARMS.get("OpenAi42");
        return getAiResult(openAi, String.format(openAi.getPrompt(), text));
    }

    /**
     * 依据美食名称和材料生成美食的制作步骤
     *
     * @param name        美食名称
     * @param ingredients 美食食材
     * @return
     */
    public static List<CompletionChoice> getRecipeCreator(String name, List<String> ingredients) {
        OpenAi openAi = PARMS.get("OpenAi43");
        StringJoiner joiner = new StringJoiner("\n");
        for (String ingredient : ingredients) {
            joiner.add(ingredient);
        }
        return getAiResult(openAi, String.format(openAi.getPrompt(), name, joiner.toString()));
    }

    /**
     * 与AI机器进行聊天
     *
     * @param question
     * @return
     */
    public static List<CompletionChoice> getAiChatbot(String question) {
        OpenAi openAi = PARMS.get("OpenAi44");
        return getAiResult(openAi, String.format(openAi.getPrompt(), question));
    }

    /**
     * 与讽刺机器进行聊天，聊天的机器人是一种消极情绪
     *
     * @param question
     * @return
     */
    public static List<CompletionChoice> getMarvChatbot(String question) {
        OpenAi openAi = PARMS.get("OpenAi45");
        return getAiResult(openAi, String.format(openAi.getPrompt(), question));
    }

    /**
     * 把一段文本分解成几步来完成
     *
     * @param text
     * @return
     */
    public static List<CompletionChoice> getTurnDirection(String text) {
        OpenAi openAi = PARMS.get("OpenAi46");
        return getAiResult(openAi, String.format(openAi.getPrompt(), text));
    }

    /**
     * 依据文本内容自动生成点评
     *
     * @param text
     * @return
     */
    public static List<CompletionChoice> getReviewCreator(String text) {
        OpenAi openAi = PARMS.get("OpenAi47");
        return getAiResult(openAi, String.format(openAi.getPrompt(), text));
    }

    /**
     * 可以为学习知识自动解答
     *
     * @param text
     * @return
     */
    public static List<CompletionChoice> getStudyNote(String text) {
        OpenAi openAi = PARMS.get("OpenAi48");
        return getAiResult(openAi, String.format(openAi.getPrompt(), text));
    }

    /**
     * 生成面试题
     *
     * @param text
     * @return
     */
    public static List<CompletionChoice> getInterviewQuestion(String text) {
        OpenAi openAi = PARMS.get("OpenAi49");
        System.out.println(String.format(openAi.getPrompt(), text));
        return getAiResult(openAi, String.format(openAi.getPrompt(), text));
    }

}
