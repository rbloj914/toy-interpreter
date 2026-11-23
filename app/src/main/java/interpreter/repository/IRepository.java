package interpreter.repository;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;

public interface IRepository {
    PrgState getCrtPrg();
    void addPrg(PrgState newPrg);
    void logPrgStateExec(PrgState p) throws MyException;
}