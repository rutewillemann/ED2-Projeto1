public class Comparador{
    HuffmanTree huffman = new HuffmanTree();
    
    public void comparar (String texto){
        huffman.build(texto);
        String binario = huffman.codificar(texto);
        int bitsBinario = binario.length();
        int qntChar = texto.length();
        int bitsTexto = qntChar * 8;

        System.out.println("Tamanho original: " + bitsTexto + " bits (" + bitsTexto / 8 + " bytes)");
        System.out.println("Tamanho comprimido: "+ bitsBinario + " bits(" + bitsBinario / 8 + " bytes)");
        System.out.println("Taxa de compressão: " + ((1-((double)bitsBinario/bitsTexto))*100)+"%");
    }
            
}
