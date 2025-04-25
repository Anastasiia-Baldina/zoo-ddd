package org.vse.zoo.presentation.feeding.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.vse.zoo.application.service.FeedingOrganizationService;
import org.vse.zoo.application.transaction.TransactionManager;
import org.vse.zoo.presentation.feeding.controller.FeedingFacadeController;
import org.vse.zoo.presentation.feeding.facade.FeedingFacadeService;
import org.vse.zoo.presentation.feeding.facade.impl.FeedingFacadeServiceImpl;

@Configuration
public class FeedingFacadeContext {
    @Autowired
    TransactionManager transactionManager;
    @Autowired
    FeedingOrganizationService feedingOrganizationService;

    @Bean
    FeedingFacadeService feedingFacadeService() {
        return new FeedingFacadeServiceImpl(transactionManager, feedingOrganizationService);
    }

    @Bean
    FeedingFacadeController feedingFacadeController() {
        return  new FeedingFacadeController(feedingFacadeService());
    }
}
