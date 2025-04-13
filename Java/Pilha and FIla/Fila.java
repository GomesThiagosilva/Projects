public class Fila<X> implements Cloneable {
    private Object[] vetor;
    private int topo;
    
    public Fila(int capacidade) throws Exception {
        if (capacidade <= 0) throw new Exception("Capacidade inválida");
        this.vetor = new Object[capacidade];
        this.topo = -1;
    }

    public Fila() {
        this.vetor = new Object[10];
        this.topo = -1;
    }

    public void reedimensioneSe(int novaCap) {
        Object[] novo = new Object[novaCap]; 
        for (int i = 0; i <= this.topo; i++) {
            novo[i] = this.vetor[i];
        }
        this.vetor = novo;
    }

    public void guardeUmItem(X item) throws Exception {
        if (item == null) throw new Exception("Item vago");

        if (isCheia()) {
            reedimensioneSe(this.vetor.length * 2);
        }

        this.vetor[++this.topo] = item;
    }

    public X recupereUmItem() throws Exception {
        if (isVazia()) throw new Exception("O vetor está vazio");
        return (X) this.vetor[0];
    }

    public void removaUmItem() throws Exception {
        if (isVazia()) throw new Exception("O vetor está vazio");

        for (int i = 0; i < this.topo; i++) {
            this.vetor[i] = this.vetor[i + 1];
        }
        this.vetor[this.topo] = null;
        this.topo--;

        if (this.topo < this.vetor.length * 0.25) {
            reedimensioneSe(Math.max(this.vetor.length / 2, 5));
        }
    }

    public boolean isCheia() {
        return this.topo + 1 == this.vetor.length;
    }

    public boolean isVazia() {
        return this.topo == -1;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder("Vetor: [");
        for (int i = 0; i <= this.topo; i++) {
            ret.append(this.vetor[i]);
            if (i < this.topo) ret.append(", ");
        }
        ret.append("]");
        return ret.toString();
    }
}
