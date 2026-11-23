package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.IExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileStatementsTest {

    @TempDir
    Path tempDir;

    @Test
    void openReadAndCloseWorkCorrectly() throws IOException, MyException {
        Path testFile = tempDir.resolve("test.in");
        Files.writeString(testFile, "15\n50\n");

        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new NopStmt());
        IExp fileExp = new ValueExp(new StringValue(testFile.toString()));

        // Open
        new OpenRFileStmt(fileExp).execute(state);
        assertTrue(state.getFileTable().isDefined(new StringValue(testFile.toString())));

        // Read prima linie (15) și a doua linie (50)
        new VarDeclStmt("v", new IntType()).execute(state);
        new ReadFileStmt(fileExp, "v").execute(state);
        assertEquals(new IntValue(15), state.getSymTable().lookup("v"));

        new ReadFileStmt(fileExp, "v").execute(state);
        assertEquals(new IntValue(50), state.getSymTable().lookup("v"));

        // Read la final de fișier (EOF) trebuie să dea 0
        new ReadFileStmt(fileExp, "v").execute(state);
        assertEquals(new IntValue(0), state.getSymTable().lookup("v"));

        // Close
        new CloseRFileStmt(fileExp).execute(state);
        assertFalse(state.getFileTable().isDefined(new StringValue(testFile.toString())));
    }

    @Test
    void fileErrorsThrowStatementException() throws IOException, MyException {
        Path testFile = tempDir.resolve("test.in");
        Files.writeString(testFile, "10\n");

        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new NopStmt());
        IExp validFile = new ValueExp(new StringValue(testFile.toString()));
        IExp missingFile = new ValueExp(new StringValue(tempDir.resolve("inexistent.in").toString()));

        // Eroare la deschiderea unui fișier inexistent
        assertThrows(StatementException.class, () -> new OpenRFileStmt(missingFile).execute(state));

        // Eroare la citire dintr-un fișier care nu e deschis încă
        new VarDeclStmt("v", new IntType()).execute(state);
        assertThrows(StatementException.class, () -> new ReadFileStmt(validFile, "v").execute(state));

        // Deschidem fișierul, apoi testăm deschiderea lui a doua oară (eroare)
        new OpenRFileStmt(validFile).execute(state);
        assertThrows(StatementException.class, () -> new OpenRFileStmt(validFile).execute(state));

        // Închidem și testăm închiderea a doua oară (eroare)
        new CloseRFileStmt(validFile).execute(state);
        assertThrows(StatementException.class, () -> new CloseRFileStmt(validFile).execute(state));
    }
}