package interpreter.repository;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private final List<PrgState> myPrgStates;
    private final String logFilePath;

    public Repository() {
        this.myPrgStates = new ArrayList<>();
        this.logFilePath = "log.txt";
    }

    public Repository(String logFilePath) {
        this.myPrgStates = new ArrayList<>();
        this.logFilePath = logFilePath;
    }

    @Override
    public void addPrg(PrgState newPrg) {
        myPrgStates.add(newPrg);
    }

    @Override
    public PrgState getCrtPrg() {
        return myPrgStates.get(myPrgStates.size() - 1);
    }

    @Override
    public void logPrgStateExec(PrgState p) throws MyException {
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))) {
            logFile.println("ExeStack:\n" + p.getExeStack());
            logFile.println("SymTable:\n" + p.getSymTable());
            logFile.println("Out:\n" + p.getOut());
            logFile.println("FileTable:\n" + p.getFileTable());
        } catch (Exception e) {
            throw new MyException("Eroare la scrierea în fișierul de log: " + e.getMessage());
        }
    }
}