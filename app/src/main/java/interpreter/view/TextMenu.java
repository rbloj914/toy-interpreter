package interpreter.view;

import interpreter.controller.Controller;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ArithExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.stmt.AssignStmt;
import interpreter.model.stmt.CompStmt;
import interpreter.model.stmt.IfStmt;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.PrintStmt;
import interpreter.model.stmt.VarDeclStmt;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.repository.IRepository;
import interpreter.repository.Repository;

import java.util.Scanner;

public class TextMenu {
    public static void main(String[] args) {
        // Exemplul 1: int v; v=2; Print(v)
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));

        // Exemplul 2: int a; int b; a=2+3*5; b=a+1; Print(b)
        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp('+', new ValueExp(new IntValue(2)),
                                new ArithExp('*', new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b", new ArithExp('+', new VarExp("a"), new ValueExp(new IntValue(1)))),
                                        new PrintStmt(new VarExp("b"))))));

        // Exemplul 3: bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)
        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),
                                        new AssignStmt("v", new ValueExp(new IntValue(2))),
                                        new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                        new PrintStmt(new VarExp("v"))))));

        IStmt[] examples = {ex1, ex2, ex3};
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n MENIU INTERPRETOR ");
            System.out.println("0. Ieșire");
            for (int i = 0; i < examples.length; i++) {
                System.out.println((i + 1) + ". Execută: " + examples[i].toString());
            }
            System.out.print("Selectează opțiunea: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Te rog introdu un număr!");
                scanner.next(); // Curăță input-ul greșit
                continue;
            }
            int option = scanner.nextInt();

            if (option == 0) {
                System.out.println("La revedere!");
                break;
            }

            if (option >= 1 && option <= examples.length) {
                try {
                    IStmt selectedPrg = examples[option - 1];
                    PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), selectedPrg);
                    IRepository repo = new Repository();
                    repo.addPrg(state);
                    Controller ctrl = new Controller(repo);

                    System.out.println("\n ÎNCEPE EXECUȚIA ");
                    ctrl.allStep();
                    System.out.println(" EXECUȚIE TERMINATĂ ");
                } catch (Exception e) {
                    System.err.println("Eroare la execuție: " + e.getMessage());
                }
            } else {
                System.out.println("Opțiune invalidă!");
            }
        }
        scanner.close();
    }
}