package br.edu.ifpb.padroes.service.log;

public class FileLogHandler implements LogHandler {
    @Override
    public void log(String message) {
        System.out.println("Save data to a file.");
    }
}
