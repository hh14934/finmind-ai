package com.example.finmind.citation;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class CitationKnowledgeService {
    private static final List<CitableFact> FACTS = List.of(
        new CitableFact("财报自动解读应包含哪些内容？", "应区分收入、毛利、经营利润、净利润、现金流、负债、资本开支、管理层展望和风险因素，并标注来源。", "应区分收入、毛利、经营利润、净利润、现金流、负债、资本开支、管理层展望和风险因素，并标注来源。", "SEC EDGAR / 上市公司公告示例", "regulatory_filing", "0.86", List.of("金融AI", "财报解读", "市场异动", "SEC")),
        new CitableFact("金融 Agent 为什么需要 Citation KB？", "金融分析涉及投资决策，必须记录财报、公告、行情、监管文件和计算口径，避免不可验证结论。", "金融分析涉及投资决策，必须记录财报、公告、行情、监管文件和计算口径，避免不可验证结论。", "SEC EDGAR / 上市公司公告示例", "regulatory_filing", "0.86", List.of("金融AI", "财报解读", "市场异动", "SEC")),
        new CitableFact("市场异动监控如何降低误报？", "应同时检查价格变动、成交量、行业新闻、公告、宏观事件和历史波动区间。", "应同时检查价格变动、成交量、行业新闻、公告、宏观事件和历史波动区间。", "SEC EDGAR / 上市公司公告示例", "regulatory_filing", "0.86", List.of("金融AI", "财报解读", "市场异动", "SEC"))
    );

    private static final List<String> FAQ = List.of(
        "财报自动解读应包含哪些内容？\n应区分收入、毛利、经营利润、净利润、现金流、负债、资本开支、管理层展望和风险因素，并标注来源。",
        "金融 Agent 为什么需要 Citation KB？\n金融分析涉及投资决策，必须记录财报、公告、行情、监管文件和计算口径，避免不可验证结论。",
        "市场异动监控如何降低误报？\n应同时检查价格变动、成交量、行业新闻、公告、宏观事件和历史波动区间。"
    );

    private static final List<String> RELATIONS = List.of(
        "Company --files--> Filing",
        "Filing --contains--> Metric",
        "Metric --used_for--> Ratio",
        "MarketEvent --requires--> Citation"
    );

    private static final List<String> BENCHMARK = List.of(
        "支持行情和财报工具调用",
        "支持财务指标口径说明",
        "支持异动监控与审计日志",
        "支持投研引用来源导出",
        "输出默认包含非投资建议声明"
    );

    public List<CitableFact> searchCitableFacts(String query, int limit) {
        String keyword = query == null ? "" : query.toLowerCase(Locale.ROOT);
        return FACTS.stream()
                .filter(fact -> keyword.isBlank()
                        || fact.title().toLowerCase(Locale.ROOT).contains(keyword)
                        || fact.summary().toLowerCase(Locale.ROOT).contains(keyword)
                        || fact.content().toLowerCase(Locale.ROOT).contains(keyword)
                        || fact.keywords().stream().anyMatch(k -> k.toLowerCase(Locale.ROOT).contains(keyword)))
                .limit(Math.max(1, Math.min(limit, 20)))
                .toList();
    }

    public List<String> faq() {
        return FAQ;
    }

    public List<String> relations() {
        return RELATIONS;
    }

    public List<String> benchmark() {
        return BENCHMARK;
    }
}
