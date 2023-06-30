package com.example.fetchrewardsreceiptprocessor;

import com.example.fetchrewardsreceiptprocessor.databases.ReceiptDb;
import com.example.fetchrewardsreceiptprocessor.repositories.IReceiptRepository;
import com.example.fetchrewardsreceiptprocessor.repositories.ReceiptRepository;
import com.example.fetchrewardsreceiptprocessor.serviecs.IReceiptService;
import com.example.fetchrewardsreceiptprocessor.serviecs.ReceiptService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ReceiptDb receiptsDb() {
        return new ReceiptDb();
    }

    @Bean
    public IReceiptRepository iReceiptsRepository(ReceiptDb receiptDb) {
        return new ReceiptRepository(receiptDb);
    }

    @Bean
    public IReceiptService iReceiptService(IReceiptRepository receiptRepository) {
        return new ReceiptService(receiptRepository);
    }
}