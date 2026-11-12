package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.stmt.*;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.repository.IRepository;
import interpreter.repository.Repository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    @Test
    void example1ExecutesAndPrintsCorrectly() throws MyException {
        // int v; v=2; Print(v)
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));

        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex1);
        IRepository repo = new Repository();
        repo.addPrg(state);
        Controller ctrl = new Controller(repo);

        ctrl.allStep(); // Rulăm tot programul

        // Verificăm dacă programul s-a terminat (stiva goală) și a printat "2"
        assertTrue(state.getExeStack().isEmpty());
        assertEquals("[2]", state.getOut().toString());
    }
}