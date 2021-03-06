package com.wallet.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

/**
 * This DTO is used as a store log details of a method.
 *
 * @author Malinda
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoggerMessageDTO {

    private String className;
    private String methodName;
    private Object[] methodArgs;
    private Long elapsedTimeInMillis;
    private Object result;

    @SneakyThrows
    @Override
    //toString for logger
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String out ;
        try{
            out = mapper.writeValueAsString(this.result);
        }catch (Exception ex){
            out = this.result.toString();
        }
        return "LoggerMessage{" +
                "className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", methodArgs='" + methodArgs + '\'' +
                ", elapsedTimeInMillis=" + elapsedTimeInMillis +
                ", result=" + out +
                '}';
    }
}
