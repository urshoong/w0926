package org.fs2.w0926.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TodoDTO {
    private Integer tno;
    private String title;
    private String memo;
    private LocalDate dueDate;
    private boolean complete;
    private LocalDateTime regDate, modDate;

}
