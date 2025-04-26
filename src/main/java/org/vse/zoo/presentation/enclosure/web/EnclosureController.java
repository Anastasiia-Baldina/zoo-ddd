package org.vse.zoo.presentation.enclosure.web;

import org.springframework.web.bind.annotation.*;
import org.vse.zoo.presentation.enclosure.dto.EnclosureDto;
import org.vse.zoo.presentation.enclosure.dto.EnclosureUidDto;
import org.vse.zoo.presentation.enclosure.dto.ResultDto;
import org.vse.zoo.presentation.enclosure.dto.TransferDto;
import org.vse.zoo.presentation.enclosure.facade.EnclosureFacadeService;

import java.util.List;

@RestController
@RequestMapping("/enclosure")
public class EnclosureController {
    private final EnclosureFacadeService facadeService;

    public EnclosureController(EnclosureFacadeService facadeService) {
        this.facadeService = facadeService;
    }

    @PostMapping(value = "/add", produces = "application/json")
    @ResponseBody
    public ResultDto<EnclosureDto> add(@RequestBody EnclosureDto enclosureDto) {
        return facadeService.addEnclosure(enclosureDto);
    }

    @PostMapping(value = "/delete", produces = "application/json")
    @ResponseBody
    public ResultDto<Void> delete(@RequestBody EnclosureUidDto enclosureUidDto) {
        return facadeService.removeEnclosure(enclosureUidDto.getUid());
    }

    @PostMapping(value = "/clean", produces = "application/json")
    @ResponseBody
    public ResultDto<EnclosureDto> clean(@RequestBody EnclosureUidDto enclosureUidDto) {
        return facadeService.cleanEnclosure(enclosureUidDto.getUid());
    }

    @PostMapping(value = "/transfer-animal", produces = "application/json")
    @ResponseBody
    public ResultDto<Void> transferAnimal(@RequestBody TransferDto transferDto) {
        return facadeService.transferAnimal(transferDto);
    }

    @GetMapping(value = "/list", produces = "application/json")
    @ResponseBody
    public ResultDto<List<EnclosureDto>> list() {
        return facadeService.findAll();
    }
}
