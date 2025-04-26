package org.vse.zoo.presentation.feeding;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.vse.zoo.infrastructure.feeding.FeedingRepository;
import org.vse.zoo.presentation.DataUtils;
import org.vse.zoo.presentation.feeding.dto.ResultDto;
import org.vse.zoo.presentation.feeding.dto.FeedingScheduleDto;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FeedingControllerTest {
    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    FeedingRepository feedingRepository;

    @Test
    public void should_create_feeding() {
        var scheduleDto = DataUtils.scheduleDto(UUID.randomUUID());

        var rsp = restTemplate.postForObject("/feeding/add", scheduleDto, ScheduleResponseDto.class);

        assertTrue(rsp.isSuccess());
        assertNotNull(rsp.getValue());
    }

    @Test
    public void should_remove_feeding() {
        var scheduleDto = DataUtils.scheduleDto(UUID.randomUUID());
        var rspAdd = restTemplate.postForObject("/feeding/add", scheduleDto, ScheduleResponseDto.class);
        assertNotNull(rspAdd.getValue());

        var rspDel = restTemplate.postForObject(
                "/feeding/delete",
                DataUtils.feedingScheduleUidDto(rspAdd.getValue().getUid()),
                ResultDto.class);

        assertTrue(rspAdd.isSuccess());
    }

    @Test
    public void should_list_feeding() {
        var scheduleDto = DataUtils.scheduleDto(UUID.randomUUID());
        var rspAdd = restTemplate.postForObject("/feeding/add", scheduleDto, ScheduleResponseDto.class);
        assertTrue(rspAdd.isSuccess());

        var rsp = restTemplate.getForObject("/feeding/list", ScheduleListResponseDto.class);

        assertTrue(rsp.isSuccess());
        assertNotNull(rsp.getValue());
        assertEquals(1, rsp.getValue().size());
    }

    public static class ScheduleResponseDto extends ResultDto<FeedingScheduleDto> {
    }

    public static class ScheduleListResponseDto extends ResultDto<List<FeedingScheduleDto>> {
    }
}
