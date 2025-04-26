package org.vse.zoo.presentation.statistics.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.vse.zoo.application.service.statistics.ZooStatisticsService;
import org.vse.zoo.presentation.statistics.web.StatisticsFacadeController;
import org.vse.zoo.presentation.statistics.facade.StatisticsFacadeService;
import org.vse.zoo.presentation.statistics.facade.impl.StatisticsFacadeServiceImpl;

@Configuration
public class StatisticsFacadeContext {
    @Autowired
    ZooStatisticsService statisticsService;

    @Bean
    public StatisticsFacadeService statisticsFacadeService() {
        return new StatisticsFacadeServiceImpl(statisticsService);
    }

    @Bean
    public StatisticsFacadeController statisticsFacadeController() {
        return new StatisticsFacadeController(statisticsFacadeService());
    }
}
