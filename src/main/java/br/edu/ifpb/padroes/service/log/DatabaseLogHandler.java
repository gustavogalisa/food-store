package br.edu.ifpb.padroes.service.log;

public class DatabaseLogHandler implements LogHandler {
    @Override
    public void log(String message) {
        System.out.println("Save data to database.");
    }
}
