package org.vse.zoo.application.service;

import java.util.UUID;

public interface AnimalTransferService {
    void transfer(UUID animalUid, UUID destEnclosureUid);
}
