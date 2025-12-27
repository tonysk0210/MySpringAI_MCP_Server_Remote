package com.example.MySpringAI_MCP_Server.config;

import com.example.MySpringAI_MCP_Server.tool.HelpDeskTicketTool;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class McpServerConfig {
    // ToolCallback 是 Spring AI 在「模型呼叫工具」時，自動回傳工具執行結果的機制 「有 @Tool ≠ 一定會被模型使用」
    @Bean
    List<ToolCallback> toolCallbacks(HelpDeskTicketTool helpDeskTicketTool) {
        return List.of(ToolCallbacks.from(helpDeskTicketTool));
    }

    //@Tool 方法
    //   ↓
    //ToolCallback   ←（真正被呼叫） ToolCallback =「模型呼叫某個 tool 時，實際被執行的 Java 物件」
    //   ↓
    //ToolCallbackProvider  ←（負責收集 / 提供） ToolCallbackProvider =「告訴 Spring AI：我這裡有哪些 ToolCallback 可以用」
    //   ↓
    //ChatClient / MCP Server
    //   ↓
    //LLM
}