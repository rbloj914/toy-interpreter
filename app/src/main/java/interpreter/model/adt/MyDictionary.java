package interpreter.model.adt;
import interpreter.exceptions.ADTException;
import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K, V> implements IDictionary<K, V> {
    private final Map<K, V> map;

    public MyDictionary() {
        this.map = new HashMap<>();
    }

    @Override
    public boolean isDefined(K key) {
        return map.containsKey(key);
    }

    @Override
    public V lookup(K key) throws ADTException {
        if (!isDefined(key)) throw new ADTException("Cheia " + key + " nu este definită!");
        return map.get(key);
    }

    @Override
    public void put(K key, V value) {
        map.put(key, value);
    }

    @Override
    public void update(K key, V value) throws ADTException {
        if (!isDefined(key)) throw new ADTException("Cheia " + key + " nu există pentru a fi actualizată!");
        map.put(key, value);
    }

    @Override
    public String toString() {
        return map.toString();
    }
}