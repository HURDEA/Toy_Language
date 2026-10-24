package interpreter.repository;

import interpreter.model.PrgState;

import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private final List<PrgState> programs = new ArrayList<>();
    private int currentPosition = 0;

    public Repository(PrgState program) {
        this.programs.add(program);
    }

    public Repository() {
    }

    @Override
    public PrgState getCrtPrg() {
        return programs.get(currentPosition);
    }

    @Override
    public void addProgram(PrgState program) {
        programs.add(program);
    }
}
