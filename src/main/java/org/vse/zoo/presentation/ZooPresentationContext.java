package org.vse.zoo.presentation;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.vse.zoo.presentation.animal.config.AnimalFacadeContext;
import org.vse.zoo.presentation.enclosure.config.EnclosureFacadeContext;
import org.vse.zoo.presentation.feeding.config.FeedingFacadeContext;
import org.vse.zoo.presentation.statistics.config.StatisticsFacadeContext;

@Configuration
@Import({
        AnimalFacadeContext.class,
        EnclosureFacadeContext.class,
        FeedingFacadeContext.class,
        StatisticsFacadeContext.class
})
public class ZooPresentationContext {
}
