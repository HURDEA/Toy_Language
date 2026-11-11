package interpreter.repository;

import interpreter.exceptions.MyException;
import interpreter.exceptions.RepositoryException;
import interpreter.model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private final List<PrgState> programs = new ArrayList<>();
    private int currentPosition = 0;
    private String logFilePath;

    public Repository(PrgState program, String logFilePath) {
        this.programs.add(program);
        this.logFilePath = logFilePath;
    }

    public Repository(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    public Repository(PrgState program) {
        this(program, "log.txt");
    }

    public Repository() {
        this("log.txt");
    }

    @Override
    public PrgState getCrtPrg() {
        return programs.get(currentPosition);
    }

    @Override
    public void addProgram(PrgState program) {
        programs.add(program);
    }

    @Override
    public void logPrgStateExec() throws MyException {
        try (var logFile = new PrintWriter(
                new BufferedWriter(new FileWriter(logFilePath, true)))) { // append
            logFile.println(getCrtPrg().toLogString());
        } catch (IOException e) {
            throw new RepositoryException("cannot write the log file " + logFilePath + ": " + e.getMessage());
        }
    }
}
