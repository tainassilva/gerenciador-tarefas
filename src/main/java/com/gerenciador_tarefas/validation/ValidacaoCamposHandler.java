package com.gerenciador_tarefas.validation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gerenciador_tarefas.response.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Classe para validação de campos... Retorno de um objeto que vamos criar
// Vai se comunicar com a classe de erros

@ControllerAdvice
/*Tratamento de exceções*/
public class ValidacaoCamposHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> tratarValidacoes(MethodArgumentNotValidException ex){

        List<Map<String, String>> listaErros = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(erro -> {

                    // Campo e a descrição do erro
                    Map<String, String> erros = new HashMap<>();
                    erros.put("campo", obterNomePropriedade(erro));
                    erros.put("descricao", erro.getDefaultMessage());

                    return erros;

                })
                .toList();

        ErrorResponse response = ErrorResponse
                .builder()
                .status(HttpStatus.BAD_REQUEST.toString())
                .errors(listaErros)
                .build();

        // Vai passsar o response e o status
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    private String obterNomePropriedade(final FieldError error){

        // dentro desse if vai checar a classe
        if (error.contains(ConstraintViolation.class)) {

            try {
                final ConstraintViolation<?> violacao = error.unwrap(ConstraintViolation.class);
                final Field campo;

                // pega o erro
                campo = violacao.getRootBeanClass().getDeclaredField(error.getField());

                final JsonProperty anotacao = campo.getAnnotation(JsonProperty.class);

                // se a anotação não for nula ou vazia...
                if (anotacao != null && anotacao.value() != null && !anotacao.value().isEmpty()) {
                    return anotacao.value();
                }

            } catch (Exception e) {
            }
        }
        return error.getField();
    }
}
