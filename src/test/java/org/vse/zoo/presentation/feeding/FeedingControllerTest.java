package org.vse.zoo.presentation.feeding;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.vse.zoo.presentation.DataUtils;
import org.vse.zoo.presentation.feeding.dto.ResultDto;
import org.vse.zoo.presentation.feeding.dto.FeedingScheduleDto;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FeedingControllerTest {
    @Autowired
    TestRestTemplate restTemplate;

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

    public static class ScheduleResponseDto extends ResultDto<FeedingScheduleDto> {
    }
}
