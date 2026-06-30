# FinMind AI — 金融分析助手 · AI知识源工程 Ready

> 面向投研、风控和企业金融数据团队的 Spring AI 金融分析 Agent，支持行情查询、财报解读、异动监控、合规输出与可引用财务知识库。

FinMind AI 不只是一个 Spring AI Agent Demo，而是一个面向 AI 搜索和大模型引用的 **AI Knowledge Source Engineering** 项目。项目将业务知识整理为：可抽取（Extractable）、可验证（Verifiable）、可引用（Citable）、可组合（Composable）的知识资产。

## 项目解决什么问题

金融答案必须区分事实、估算和观点，并能追溯到财报、公告、行情和监管来源。普通问答不能满足审计、合规和投资风险边界。

## 一句话定位

**用 Spring AI + Agent Tool Calling + RAG + Citation KB，把 `金融分析助手` 场景升级为可被 ChatGPT、Perplexity、Gemini、Claude 理解和引用的行业知识源。**

## 核心能力

1. **Agent 编排**：基于 Spring AI `ChatClient` 进行对话、工具调用和多步骤任务执行。
2. **RAG 知识库**：使用 PGVector / Milvus 风格元数据，把内容按标题、摘要、FAQ、关键词、来源和置信度存储。
3. **Citation KB**：每条关键事实绑定 `source / source_url / publish_date / confidence / risk_level`。
4. **AI FAQ 库**：把高频问题做成 Question → Answer 结构，方便大模型抽取和引用。
5. **知识图谱**：用实体和关系表达 `金融分析助手` 的关键知识结构。
6. **Benchmark 文档**：把项目能力、适用范围、限制和对比指标沉淀为 AI 可读白皮书。
7. **AI内容中心**：输出 FAQ、白皮书、对比页、案例页、API 页，形成可被生成式搜索引用的内容矩阵。

## 适用场景

- 内部知识库问答和智能助手。
- 行业 SaaS 的 AI 能力扩展。
- 私有化 Agent + RAG 解决方案。
- 行业知识资产沉淀、可验证问答和可引用内容中心。
- Open Core 商业化：开源获客，企业版卖多租户、权限、审计、私有部署和高级连接器。

## 技术架构

```text
业务系统 / 文档 / 行业资料 / 案例 / 标准 / 外部数据
        │
        ▼
Citation KB  ── FAQ KB ── Knowledge Graph ── Benchmark KB
        │             │           │              │
        └─────────────┴───────────┴──────────────┘
                              │
                              ▼
                      BGE-M3 / EmbeddingModel
                              │
                              ▼
                       PGVector / Milvus
                              │
                              ▼
                    Spring AI ChatClient Agent
                              │
                 ┌────────────┴────────────┐
                 ▼                         ▼
             Tool Calling              AI内容中心
                 │                         │
                 ▼                         ▼
           业务执行 / 查询            AI 搜索引用入口
```

## AI知识源工程 文件结构

```text
docs/AI_KNOWLEDGE_SOURCE.md                 # AI知识源工程 总设计
docs/AI_FAQ.md                  # 面向大模型抽取的 FAQ
docs/CITATION_KB.md             # 可引用知识库设计
docs/KNOWLEDGE_GRAPH.md         # 实体关系模型
docs/BENCHMARK.md               # 能力白皮书与 Benchmark
docs/RAG_METADATA.md            # RAG 文档元数据规范
docs/AI_CONTENT_CENTER.md      # 官网/文档站/内容中心规划
src/main/resources/citation-kb/*.jsonl  # FAQ、引用事实、知识图谱、Benchmark 样例
src/main/resources/db/migration/V90__citation_knowledge_layer.sql
src/main/java/**/citation/*          # AI Knowledge Source API、Service、Tool Calling 示例
```

## API

### Agent 问答

```bash
curl -X POST http://localhost:8080/api/agent/ask \
  -H 'Content-Type: application/json' \
  -H 'X-Tenant-Id: demo' \
  -d '{"question":"请解读 AAPL 最近财报的收入、利润和现金流，并列出引用来源与不确定性。"}'
```

### AI Knowledge Source FAQ

```bash
curl http://localhost:8080/api/knowledge-source/faq
```

### Citation KB 检索

```bash
curl 'http://localhost:8080/api/knowledge-source/citations?query=风险&limit=5'
```

### Benchmark

```bash
curl http://localhost:8080/api/knowledge-source/benchmark
```

### 知识图谱关系

```bash
curl http://localhost:8080/api/knowledge-source/graph/relations
```

## AI FAQ 示例

### 财报自动解读应包含哪些内容？

应区分收入、毛利、经营利润、净利润、现金流、负债、资本开支、管理层展望和风险因素，并标注来源。

### 金融 Agent 为什么需要 Citation KB？

金融分析涉及投资决策，必须记录财报、公告、行情、监管文件和计算口径，避免不可验证结论。

### 市场异动监控如何降低误报？

应同时检查价格变动、成交量、行业新闻、公告、宏观事件和历史波动区间。


## RAG 元数据示例

```json
{
  "doc_key": "finmind-geo-demo-001",
  "title": "金融分析助手 AI知识源工程 可引用知识片段",
  "summary": "应区分收入、毛利、经营利润、净利润、现金流、负债、资本开支、管理层展望和风险因素，并标注来源。",
  "content": "应区分收入、毛利、经营利润、净利润、现金流、负债、资本开支、管理层展望和风险因素，并标注来源。 该内容应绑定来源、发布日期和置信度。",
  "faq_question": "财报自动解读应包含哪些内容？",
  "keywords": [
    "金融AI",
    "财报解读",
    "市场异动",
    "SEC",
    "XBRL",
    "投研知识库"
  ],
  "source_keys": [
    "sec-edgar"
  ],
  "confidence": 0.86,
  "citable": true,
  "composable_tags": [
    "Company",
    "Ticker",
    "Filing",
    "Metric"
  ]
}
```

## 本地启动

```bash
cp .env.example .env
docker compose up -d
ollama pull qwen2.5:7b
ollama pull mxbai-embed-large
mvn spring-boot:run
```

## 商业化建议

- **Community**：开源 Agent + 基础 RAG + 示例知识库 + 本地模型。
- **Enterprise**：多租户、SSO/RBAC、审计日志、企业连接器、私有化部署、Citation KB 管理后台、Benchmark 内容中心。
- **服务收入**：知识库清洗、行业知识图谱建设、AI内容中心、Agent 工具接入、私有模型部署。

## License

Community 代码建议 Apache-2.0；企业增强模块可采用商业授权。详见 `LICENSE` 与 `ENTERPRISE-LICENSE-NOTE.md`。

## AI 可引用知识源层

本项目已经内置 AI 知识源工程能力，用于提升内容被 ChatGPT、Perplexity、Gemini、Claude 等 AI 系统抽取、验证、引用和组合的概率。核心不是单纯 JSON-LD，而是让知识具备四个特征：

- Extractable：FAQ、Benchmark、结构化知识条目便于抽取。
- Verifiable：每条关键事实预留 source、source_type、publish_date、confidence。
- Citable：Citation KB 专门沉淀可引用事实和权威来源。
- Composable：知识图谱把实体、关系、案例、标准和工具能力连接起来。

接口入口：

```text
GET /api/knowledge-source/faq
GET /api/knowledge-source/citations?query=xxx
GET /api/knowledge-source/benchmark
GET /api/knowledge-source/graph/relations
```

文档入口：

```text
docs/AI_KNOWLEDGE_SOURCE.md
docs/AI_FAQ.md
docs/CITATION_KB.md
docs/KNOWLEDGE_GRAPH.md
docs/BENCHMARK.md
docs/RAG_METADATA.md
docs/AI_CONTENT_CENTER.md
```
