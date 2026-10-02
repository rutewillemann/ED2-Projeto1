public class HuffmanTree {

    private No raiz;
    private String tabelaFrequencias;
    private String heapInicial;
    private String texto;
    private int[] frequencias = new int[256]; // ← virou atributo para reutilizar

    public void build(String texto) {
        this.texto = texto;
        for (char c : texto.toCharArray()) {
            frequencias[c]++;
        }

        // Monta etapa 1
        StringBuilder sb1 = new StringBuilder();
        MinHeap heap = new MinHeap();
        for (int i = 0; i < 256; i++) {
            if (frequencias[i] > 0) {
                sb1.append("Caractere '" + (char) i + "' (ASCII: " + i + "): " + frequencias[i] + "\n");
                heap.insert(new No((char) i, frequencias[i]));
            }
        }
        tabelaFrequencias = sb1.toString();

        // Monta etapa 2
        heapInicial = heap.imprimir();

        // Consome o heap
        raiz = heap.buildTree();
    }

    // Retorna as frequências no formato "65:3,66:1,78:2"
    public String getFrequencias() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 256; i++) {
            if (frequencias[i] > 0) {
                sb.append(i + ":" + frequencias[i] + ",");
            }
        }
        return sb.toString();
    }

    // Reconstrói a árvore a partir das frequências salvas
    public void buildFromFrequencias(String freqString) {
        frequencias = new int[256];
        for (String par : freqString.split(",")) {
            if (par.isEmpty()) continue;
            String[] partes = par.split(":");
            int ascii = Integer.parseInt(partes[0]);
            int freq  = Integer.parseInt(partes[1]);
            frequencias[ascii] = freq;
        }

        MinHeap heap = new MinHeap();
        for (int i = 0; i < 256; i++) {
            if (frequencias[i] > 0) {
                heap.insert(new No((char) i, frequencias[i]));
            }
        }

        raiz = heap.buildTree();
    }

    public void imprimir() {
        System.out.println("--------------------------------------------------");
        System.out.println(" ETAPA 1: Tabela de Frequencia de Caracteres");
        System.out.println("--------------------------------------------------");
        System.out.println(tabelaFrequencias);

        System.out.println("--------------------------------------------------");
        System.out.println(" ETAPA 2: Min-Heap Inicial (Vetor)");
        System.out.println("--------------------------------------------------");
        System.out.println(heapInicial);

        System.out.println("--------------------------------------------------");
        System.out.println(" ETAPA 3: Arvore de Huffman");
        System.out.println("--------------------------------------------------");
        System.out.println(imprimirArvore());

        System.out.println("--------------------------------------------------");
        System.out.println(" ETAPA 4: Tabela de Códigos de Huffman");
        System.out.println("--------------------------------------------------");
        imprimirRecursivo(raiz, "");

        System.out.println("--------------------------------------------------");
        System.out.println(" ETAPA 5: Resumo da Compressao");
        System.out.println("--------------------------------------------------");
    }

    private String imprimirRecursivo(No no, String codigo) {
        StringBuilder sb = new StringBuilder();
        if (no == null) return "";
        int ascii = (int) no.caractere;

        if (no.esquerda == null && no.direita == null) {
            System.out.println("Caractere '" + no.caractere + "' -> " + codigo);
            sb.append(codigo);
        }

        sb.append(imprimirRecursivo(no.esquerda, codigo + "0"));
        sb.append(imprimirRecursivo(no.direita, codigo + "1"));

        return sb.toString();
    }

    public String codificar(String texto) {
        StringBuilder sb = new StringBuilder();
        for (char c : texto.toCharArray()) {
            sb.append(buscarCodigo(raiz, c, ""));
        }
        return sb.toString();
    }

    public String decodificar(String binario) {
        StringBuilder sb = new StringBuilder();
        No atual = raiz;

        for (char bit : binario.toCharArray()) {
            if (bit == '0') {
                atual = atual.esquerda;
            } else {
                atual = atual.direita;
            }

            if (atual.esquerda == null && atual.direita == null) {
                sb.append(atual.caractere);
                atual = raiz;
            }
        }

        return sb.toString();
    }

    private String buscarCodigo(No no, char c, String codigo) {
        if (no == null) return "";

        if (no.esquerda == null && no.direita == null) {
            if (no.caractere == c) return codigo;
            return "";
        }

        String esquerda = buscarCodigo(no.esquerda, c, codigo + "0");
        if (!esquerda.isEmpty()) return esquerda;

        return buscarCodigo(no.direita, c, codigo + "1");
    }

    public String imprimirArvore() {
        StringBuilder sb = new StringBuilder();
        imprimirPreOrdem(raiz, sb, true);
        return sb.toString();
    }

    private int i = 1;

    private void imprimirPreOrdem(No no, StringBuilder sb, boolean ehRaiz) {
        if (no == null) return;

        if (ehRaiz) {
            sb.append("(RAIZ, " + no.frequencia + ")");
        } else if (no.esquerda == null && no.direita == null) {
            sb.append("   -(" + no.caractere + ", " + no.frequencia + ")");
        } else {
            sb.append(" -(N" + i + ", " + no.frequencia + ")");
            i++;
        }

        if (no.esquerda != null || no.direita != null) sb.append("\n");
        imprimirPreOrdem(no.esquerda, sb, false);

        if (no.esquerda != null && no.direita != null) sb.append("\n");
        imprimirPreOrdem(no.direita, sb, false);
    }
}