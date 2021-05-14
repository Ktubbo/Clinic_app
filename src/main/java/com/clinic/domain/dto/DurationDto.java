package com.clinic.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;

@Getter
@Setter
@AllArgsConstructor
public class DurationDto {

    String hours;
    String minutes;

    public DurationDto(Duration duration) {

        hours= duration.toHoursPart()<9 ?
                "0" + duration.toHoursPart() :
                String.valueOf(duration.toHoursPart());

        minutes = duration.toMinutesPart()<9 ?
                "0" + duration.toMinutesPart() :
                String.valueOf(duration.toMinutesPart());
    }

    @Override
    public String toString() {
        return hours + ":" + minutes;
    }

    public Duration toDuration() {
        return Duration.ofHours(Long.parseLong(hours)).plusMinutes(Long.parseLong(minutes));
    }
}
