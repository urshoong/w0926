package org.fs2.w0926.dao;

import lombok.Cleanup;
import org.fs2.w0926.dto.TodoDTO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public enum TodoDAO {
    INSTANCE;


    //항상 데이터가 정상적으로 처리되는 경우에는 void
    //결과를 받아서 판단해야 할 경우에는 return

    private final String LIST = "select * from tbl_todo where tno > 0 order by tno desc limit ?,?";

    private final String PAGE = "select count(tno) page from tbl_todo where tno > 0";

    private final String INSERT = "INSERT INTO tbl_todo (title, memo, duedate) VALUES (?, ?, ?)";

    private final String LAST = "select last_insert_id()";

    private final String ONE = "select * from tbl_todo where tno = ?";

    private final String UPDATE = "update tbl_todo set title = ?, memo = ?, moddate = now(), duedate = ?, complete = ? where tno = ?";

    private final String DELETE = "delete from tbl_todo where tno = ?";


    public List<TodoDTO> list(int page, int size) throws Exception{

        StringBuffer buffer =
                new StringBuffer(LIST);

        int skip = (page <= 0 ? 1 : page - 1) * size;

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement
                = connection.prepareStatement(buffer.toString());
        preparedStatement.setInt(1,skip);
        preparedStatement.setInt(2,size);

        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        List<TodoDTO> list = new ArrayList<>();

        while(resultSet.next()){
            TodoDTO dto = TodoDTO.builder()
                    .tno(resultSet.getInt("tno"))
                    .title(resultSet.getString("title"))
                    .memo(resultSet.getString("memo"))
                    .dueDate(resultSet.getDate("dueDate").toLocalDate())
                    .complete(resultSet.getBoolean("complete"))
                    .regDate(resultSet.getTimestamp("regDate").toLocalDateTime())
                    .modDate(resultSet.getTimestamp("modDate").toLocalDateTime())
                    .build();

            list.add(dto);
        }

        return list;
    }

    public Integer page() throws Exception{
        Integer result = null;
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(PAGE);

        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();

        result = resultSet.getInt(1);

        return result;
    }

    //하나의 Todo를 조회하여 리턴
    public TodoDTO selectOne(int tno) throws Exception {
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(ONE);
        preparedStatement.setInt(1, tno);
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();

        TodoDTO dto = TodoDTO.builder()
                .tno(resultSet.getInt(1))
                .title(resultSet.getString(2))
                .memo(resultSet.getString(3))
                .regDate(resultSet.getTimestamp(4).toLocalDateTime())
                .modDate(resultSet.getTimestamp(5).toLocalDateTime())
                .dueDate(resultSet.getDate(6).toLocalDate())
                .complete(resultSet.getBoolean(7))
                .build();

        return dto;
    }

    //하나의 todo를 업데이트 - title, memo, duedate, complete 수정 가능
    public Integer update(TodoDTO dto) throws Exception{
        Integer result = null;

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
        preparedStatement.setString(1, dto.getTitle());
        preparedStatement.setString(2, dto.getMemo());
        preparedStatement.setDate(3, Date.valueOf(dto.getDueDate()));
        preparedStatement.setBoolean(4, dto.isComplete());
        preparedStatement.setInt(5, dto.getTno());

        result = preparedStatement.executeUpdate();

        return result;
    }

    //하나의 todo를 삭제
    public Integer delete(TodoDTO dto) throws Exception{
        Integer result = null;

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(DELETE);

        preparedStatement.setInt(1, dto.getTno());

        result = preparedStatement.executeUpdate();

        return result;
    }

    public Integer insert(TodoDTO todoDTO) throws Exception{
        //리턴 타입부터 설정
        Integer result = null;
        //connection, statement, resultset을
        //instance variable 로 만들지 마라

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
        preparedStatement.setString(1, todoDTO.getTitle());
        preparedStatement.setString(2, todoDTO.getMemo());
        preparedStatement.setDate(3, Date.valueOf(todoDTO.getDueDate()));

        //insert/update/delete -> int
//        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
        int count = preparedStatement.executeUpdate();
        if(count != 1){
            throw new Exception("insert error");
        }
        //preparedStatement를 재사용하기 전에 close() 해줘야 한다
        preparedStatement.close();
        preparedStatement = connection.prepareStatement(LAST);

        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();

        result = resultSet.getInt(1);

        System.out.println(result);

        return result;
    }
}
