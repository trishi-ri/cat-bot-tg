package ru.v1as.tg.cat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;

@Slf4j
@Configuration
public class TgBotProxyConfig {
    @Value("${tg.bot.proxy.use}")
    private String useProxy;

    @Value("${tg.bot.proxy.host}")
    private String proxyHost;

    @Value("${tg.bot.proxy.port}")
    private String proxyPort;

    @Bean
    public DefaultBotOptions getBotOptions() {
        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);

        if (Boolean.parseBoolean(useProxy) && !StringUtils.isEmpty(proxyHost) && !StringUtils.isEmpty(proxyPort)) {
            botOptions.setProxyHost(proxyHost);
            botOptions.setProxyPort(Integer.parseInt(proxyPort));
            botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);
            log.info("Proxy settings was added to bot option");
        }
        log.info("Initialized");
        return botOptions;
    }
}
