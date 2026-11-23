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
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @TempDir
    Path tempDir;

    @Test
    void example1ExecutesAndPrintsCorrectly() throws MyException {
        Path logPath = tempDir.resolve("exec_log.txt");

        // int v; v=2; Print(v)
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));

        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), ex1);
        IRepository repo = new Repository(logPath.toString());
        repo.addPrg(state);
        Controller ctrl = new Controller(repo);

        ctrl.allStep();

        assertTrue(state.getExeStack().isEmpty());
        assertEquals("[2]", state.getOut().toString());
    }

    @Test
    void logFileContainsAllFourSectionsInOrder() throws MyException, IOException {
        Path logPath = tempDir.resolve("sections_log.txt");

        IStmt prg = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));

        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), prg);
        IRepository repo = new Repository(logPath.toString());
        repo.addPrg(state);
        Controller ctrl = new Controller(repo);

        ctrl.allStep();

        String content = Files.readString(logPath);
        int idxStack = content.indexOf("ExeStack:");
        int idxSym = content.indexOf("SymTable:");
        int idxOut = content.indexOf("Out:");
        int idxFile = content.indexOf("FileTable:");

        assertTrue(idxStack != -1 && idxSym != -1 && idxOut != -1 && idxFile != -1);
        assertTrue(idxStack < idxSym && idxSym < idxOut && idxOut < idxFile);
    }
}