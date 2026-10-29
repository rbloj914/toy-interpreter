package interpreter.controller;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.IStack;
import interpreter.model.stmt.IStmt;
import interpreter.repository.IRepository;

public class Controller {
    private final IRepository repo;

    public Controller(IRepository repo) {
        this.repo = repo;
    }

    public PrgState oneStep(PrgState state) throws MyException {
        IStack<IStmt> stk = state.getExeStack();
        if (stk.isEmpty()) {
            throw new MyException("Stiva de execuție este goală!");
        }
        IStmt crtStmt = stk.pop();
        return crtStmt.execute(state);
    }

    public void allStep() throws MyException {
        PrgState prg = repo.getCrtPrg();
        System.out.println("Starea inițială:");
        System.out.println(prg);
        while (!prg.getExeStack().isEmpty()) {
            oneStep(prg);
            System.out.println(prg);
        }
    }
}