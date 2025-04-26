package org.vse.zoo.presentation.feeding.web;

import org.springframework.web.bind.annotation.*;
import org.vse.zoo.presentation.feeding.dto.FeedingScheduleUidDto;
import org.vse.zoo.presentation.feeding.dto.ResultDto;
import org.vse.zoo.presentation.feeding.dto.FeedingScheduleDto;
import org.vse.zoo.presentation.feeding.facade.FeedingFacadeService;

import java.util.List;

@RestController
@RequestMapping("/feeding")
public class FeedingFacadeController {
    private final FeedingFacadeService facadeService;

    public FeedingFacadeController(FeedingFacadeService facadeService) {
        this.facadeService = facadeService;
    }

    @PostMapping(value = "/add", produces = "application/json")
    @ResponseBody
    public ResultDto<FeedingScheduleDto> add(@RequestBody FeedingScheduleDto scheduleDto) {
        return facadeService.addSchedule(scheduleDto);
    }

    @PostMapping(value = "/delete", produces = "application/json")
    @ResponseBody
    public ResultDto<Void> delete(@RequestBody FeedingScheduleUidDto scheduleDto) {
        return facadeService.deleteSchedule(scheduleDto.getUid());
    }

    @GetMapping(value = "/list", produces = "application/json")
    @ResponseBody
    public ResultDto<List<FeedingScheduleDto>> list() {
        return facadeService.listSchedule();
    }
}
