package com.tekmez.geniepic.config;

import com.tekmez.geniepic.interfaces.IBackgroundRemoverService;
import com.tekmez.geniepic.interfaces.IRetourcherService;
import com.tekmez.geniepic.service.falAi.QueueService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfiguration {

    @Bean
    public QueueService<IBackgroundRemoverService> backgroundRemoverQueueService(IBackgroundRemoverService service) {
        return new QueueService<>(service);
    }

    @Bean
    public QueueService<IRetourcherService> retoucherQueueService(IRetourcherService service) {
        return new QueueService<>(service);
    }
    
    // Yeni servisler eklediğinizde, sadece buraya yeni bean tanımları ekleyin
} 