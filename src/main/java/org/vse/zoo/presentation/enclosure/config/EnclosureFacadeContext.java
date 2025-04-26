package org.vse.zoo.presentation.enclosure.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.vse.zoo.application.service.enclosure.AnimalTransferService;
import org.vse.zoo.application.service.enclosure.EnclosureService;
import org.vse.zoo.application.transaction.TransactionManager;
import org.vse.zoo.presentation.enclosure.web.EnclosureController;
import org.vse.zoo.presentation.enclosure.facade.EnclosureFacadeService;
import org.vse.zoo.presentation.enclosure.facade.impl.EnclosureFacadeServiceImpl;

@Configuration
public class EnclosureFacadeContext {
    @Autowired
    private TransactionManager transactionManager;
    @Autowired
    private AnimalTransferService transferService;
    @Autowired
    private EnclosureService enclosureService;

    @Bean
    public EnclosureFacadeService enclosureFacadeService() {
        return new EnclosureFacadeServiceImpl(
                transactionManager, enclosureService, transferService);
    }

    @Bean
    public EnclosureController enclosureController() {
        return new EnclosureController(enclosureFacadeService());
    }
}
