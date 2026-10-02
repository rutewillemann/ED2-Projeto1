public class ArvoreBuscaBinaria {
  class No {
    int valor;
    No esquerda;
    No direita;

    public No(int valor) {
      this.valor = valor;
      this.esquerda = null;
      this.direita = null;
    }
  }

  private No raiz;

  public void inserir(int valor) {
    No novoNo = new No(valor);
    if (raiz == null) {
      raiz = novoNo;
    } else {
      inserirRecursivo(raiz, valor);
    }
  }

  private void inserirRecursivo(No n, int valor) {
    if (valor == n.valor)
      return;
    if (valor < n.valor) {
      // inserir na esquerda
      if (n.esquerda == null) {
        n.esquerda = new No(valor);
        return;
      }
      inserirRecursivo(n.esquerda, valor);
    } else {
      if (n.direita == null) {
        n.direita = new No(valor);
        return;
      }
      inserirRecursivo(n.direita, valor);
    }
  }

  public boolean buscar(int valor) {
    return buscarRecursivo(raiz, valor);
  }

  private boolean buscarRecursivo(No n, int valor) {
    // return n==null ? false : n.valor == valor ? true : valor < n.valor ?
    // buscarRecursivo(n.esquerda, valor) : buscarRecursivo(n.direita, valor);

    if (n == null)
      return false;
    if (n.valor == valor) {
      return true;
    }
    if (valor < n.valor) {
      return buscarRecursivo(n.esquerda, valor);
    }
    return buscarRecursivo(n.direita, valor);

  }

  public void remover(int valor) {
    raiz = removerRecursivo(raiz, valor);
  }

  private No removerRecursivo(No n, int valor) {
    if (n == null)
      return null;
    if (valor < n.valor) {
      n.esquerda = removerRecursivo(n.esquerda, valor);
    } else if (valor > n.valor) {
      n.direita = removerRecursivo(n.direita, valor);
    } else {
      // No encontrado
      if (n.esquerda == null)
        return n.direita;
      else if (n.direita == null)
        return n.esquerda;

      No sucessor = encontrarMinimo(n.direita);
      n.valor = sucessor.valor;
      n.direita = removerRecursivo(n.direita, sucessor.valor);
    }
    return n;
  }

  private No encontrarMinimo(No n) {
    while (n.esquerda != null) {
      n = n.esquerda;
    }
    return n;
  }

  public void imprimirEmOrdem() {
    imprimirEmOrdemRecursivo(raiz);
  }

  private void imprimirEmOrdemRecursivo(No n) {
    if (n == null)
      return;

    imprimirEmOrdemRecursivo(n.esquerda);
    System.out.println(n.valor + ",");
    imprimirEmOrdemRecursivo(n.direita);
  }
}