package org.vse.zoo.presentation.enclosure.facade;

import org.vse.zoo.presentation.enclosure.dto.EnclosureDto;
import org.vse.zoo.presentation.enclosure.dto.ResultDto;
import org.vse.zoo.presentation.enclosure.dto.TransferDto;

import java.util.List;
import java.util.UUID;

public interface EnclosureFacadeService {

    ResultDto<List<EnclosureDto>> findAll();

    ResultDto<EnclosureDto> addEnclosure(EnclosureDto enclosureDto);

    ResultDto<Void> removeEnclosure(UUID enclosureUid);

    ResultDto<EnclosureDto> cleanEnclosure(UUID enclosureUid);

    ResultDto<Void> transferAnimal(TransferDto transferDto);
}
