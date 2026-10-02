
import java.util.ArrayList;

public class MinHeap {

    private ArrayList<No> heap;

    public MinHeap() {
        this.heap = new ArrayList<>();
    }

    public void insert(No no) {
        heap.add(no);

        int i = heap.size() - 1;
        while (i > 0) {
            int pai = (i - 1) / 2;

            if (heap.get(i).compareTo(heap.get(pai)) < 0) {
                No temp = heap.get(i);
                heap.set(i, heap.get(pai));
                heap.set(pai, temp);
                i = pai;
            } else {
                break;
            }
        }
    }

    public No remove() {
        if (heap.isEmpty()) {
            return null;
        }

        if (heap.size() == 1) return heap.remove(0); // ← sem heapify

        No min = heap.get(0);
        No ultimo = heap.remove(heap.size() - 1);

        heap.set(0, ultimo);

        int i = 0;
        while (true) {
            int esquerda = 2 * i + 1;
            int direita = 2 * i + 2;
            int menor = i;

            if (esquerda < heap.size() && heap.get(esquerda).compareTo(heap.get(menor)) < 0) {
                menor = esquerda;
            }
            if (direita < heap.size() && heap.get(direita).compareTo(heap.get(menor)) < 0) {
                menor = direita;
            }

            if (menor != i) {
                No temp = heap.get(i);
                heap.set(i, heap.get(menor));
                heap.set(menor, temp);
                i = menor;
            } else {
                break;
            }
        }
        return min;

    }

    public No buildTree() {
        while (heap.size() > 1) {
            No no1 = remove();
            No no2 = remove();
            if (no1 == null || no2 == null) {
                break;
            }
            No pai = new No('\0', no1.frequencia + no2.frequencia);
            pai.esquerda = no1;
            pai.direita = no2;
            insert(pai);
        }
        return remove();
    }

    public String imprimir() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        for (int i = 0; i < heap.size(); i++) {
            No no = heap.get(i);
            sb.append("No('" + no.caractere + "'," + no.frequencia + ")");
            if (i < heap.size() - 1) sb.append(", ");
        }
        sb.append(" ]");
        return sb.toString();
    }
}
