
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException; 

public class Main {

    public static void main(String args[]) {
        if (args.length < 3) {
            System.out.println("Uso:");
            System.out.println("  Comprimir:    java -jar huffman.jar -c <arquivo_original> <arquivo_comprimido>");
            System.out.println("  Descomprimir: java -jar huffman.jar -d <arquivo_comprimido> <arquivo_restaurado>");
            return;
        }

        String modo = args[0]; // -c ou -d
        String arquivoEntrada = args[1];
        String arquivoSaida = args[2];

        try {

            String texto = Files.readString(Path.of(arquivoEntrada));
            HuffmanTree huffman = new HuffmanTree();
            huffman.build(texto);

            if (modo.equals("-c")) {
                String binario = huffman.codificar(texto);
                Files.writeString(Path.of(arquivoSaida), huffman.getFrequencias() + "\n---\n" + binario);
                System.out.println("Arquivo codificado com sucesso. Resultado: " + binario);

                huffman.imprimir();

                Comparador comparador = new Comparador();

                comparador.comparar(texto);

            } else if (modo.equals("-d")) {
                String conteudo = Files.readString(Path.of(arquivoEntrada));
                String[] partes = conteudo.split("\n---\n");  // agora funciona!
                huffman.buildFromFrequencias(partes[0]);
                String decodificado = huffman.decodificar(partes[1]);
                Files.writeString(Path.of(arquivoSaida), decodificado);
                System.out.println("Arquivo decodificado com sucesso. Resultado: " + decodificado);
            }

        } catch (IOException e) {
            System.out.println("Arquivo não encontrado!");
        }

    }
}
