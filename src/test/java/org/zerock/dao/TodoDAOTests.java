package org.zerock.dao;

import org.fs2.w0926.dao.TodoDAO;
import org.fs2.w0926.dto.TodoDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.stream.IntStream;

public class TodoDAOTests {

    @Test
    public void testList() throws Exception {
        for (TodoDTO dto : TodoDAO.INSTANCE.list(2, 10)) {
            System.out.println(dto);
        }
    }


    @Test
    public void testInserts(){
        IntStream.rangeClosed(1, 1).forEach(i -> {
            TodoDTO dto = TodoDTO.builder()
                    .title("test"+i)
                    .memo("Test...")
                    .dueDate(LocalDate.now())
                    .build();

            try {
                TodoDAO.INSTANCE.insert(dto);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
