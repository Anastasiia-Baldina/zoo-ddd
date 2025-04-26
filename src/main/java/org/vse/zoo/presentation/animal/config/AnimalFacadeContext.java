package org.vse.zoo.presentation.animal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.vse.zoo.application.service.animal.AnimalService;
import org.vse.zoo.application.service.enclosure.EnclosureService;
import org.vse.zoo.application.service.feeding.FeedingOrganizationService;
import org.vse.zoo.application.transaction.TransactionManager;
import org.vse.zoo.presentation.animal.web.AnimalController;
import org.vse.zoo.presentation.animal.facade.AnimalFacadeService;
import org.vse.zoo.presentation.animal.facade.impl.AnimalFacadeServiceImpl;

@Configuration
public class AnimalFacadeContext {
    @Autowired
    private TransactionManager transactionManager;
    @Autowired
    private AnimalService animalService;
    @Autowired
    private EnclosureService enclosureService;
    @Autowired
    private FeedingOrganizationService feedingOrganizationService;

    @Bean
    public AnimalFacadeService animalFacadeService() {
        return new AnimalFacadeServiceImpl(
                transactionManager, animalService, enclosureService, feedingOrganizationService);
    }

    @Bean
    public AnimalController animalController() {
        return new AnimalController(animalFacadeService());
    }
}