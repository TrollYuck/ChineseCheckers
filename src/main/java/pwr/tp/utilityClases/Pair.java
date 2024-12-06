package pwr.tp.utilityClases;

import java.util.Comparator;
import java.util.Objects;

public class Pair <F extends Comparable<F>,S extends Comparable<S>> implements Comparable {
    private final F first;
    private final S second;
    public Pair(F f, S s) {
        this.first = f;
        this.second = s;
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(first, pair.first) && Objects.equals(second, pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }


    @Override
    public int compareTo(Object o) {
        Pair<F, S> object = (Pair<F, S>) o;
        if (first.compareTo(object.first) != 0) {
            return first.compareTo(object.first);
        }
        return second.compareTo(object.second);
    }
}
