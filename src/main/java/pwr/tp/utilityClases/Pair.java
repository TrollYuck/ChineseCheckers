/**
 * A generic class representing a pair of two comparable elements.
 *
 * @param <F> the type of the first element, which must be comparable
 * @param <S> the type of the second element, which must be comparable
 */
package pwr.tp.utilityClases;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

public class Pair<F extends Comparable<F>, S extends Comparable<S>> implements Comparable<Pair<F, S>>, Serializable {
    private final F first;
    private final S second;
    /**
     * Constructs a new Pair with the specified elements.
     *
     * @param f the first element
     * @param s the second element
     */
    public Pair(F f, S s) {
        this.first = f;
        this.second = s;
    }

    public Pair(String str) {
        String[] parts = str.split(",");

        this.first = (F) parts[0].trim();
        this.second = (S) parts[1].trim();
    }

    /**
     * Returns the first element of the pair.
     *
     * @return the first element
     */
    public F getFirst() {
        return first;
    }

    /**
     * Returns the second element of the pair.
     *
     * @return the second element
     */
    public S getSecond() {
        return second;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o the reference object with which to compare
     * @return true if this object is the same as the obj argument; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return pair.first.equals(first) && pair.second.equals(second);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }


    /**
     * Compares this pair with the specified pair for order.
     *
     * @param o the pair to be compared
     * @return a negative integer, zero, or a positive integer as this pair is less than, equal to, or greater than the specified pair
     */
    @Override
    public int compareTo(Pair<F, S> o) {
        if (first.compareTo(o.first) != 0) {
            return first.compareTo(o.first);
        }
        return second.compareTo(o.second);
    }

    /**
     * Returns a string representation of the pair.
     *
     * @return a string representation of the pair
     */
    @Override
    public String toString() {
        return first + "," + second;
    }

}
