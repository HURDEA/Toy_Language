package interpreter.repository;

import interpreter.model.PrgState;

public interface IRepository {
    PrgState getCrtPrg();
    void addProgram(PrgState program);
}
