package interpreter.repository;
import interpreter.model.PrgState;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private final List<PrgState> myPrgStates;

    public Repository() {
        this.myPrgStates = new ArrayList<>();
    }

    @Override
    public void addPrg(PrgState newPrg) {
        myPrgStates.add(newPrg);
    }

    @Override
    public PrgState getCrtPrg() {
        // Returnează cel mai recent program adăugat
        return myPrgStates.get(myPrgStates.size() - 1);
    }
}