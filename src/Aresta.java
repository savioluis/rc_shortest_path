class Aresta {
    public int origem;
    public int destino;
    public int peso;
    public int recurso;

    public Aresta(int origem, int destino, int peso, int recurso) {
        this.origem =  origem;
        this.destino = destino;
        this.peso = peso;
        this.recurso = recurso;
    }

    public int getDestino() {
        return destino;
    }

    public int getPeso() {
        return peso;
    }

    public int getRecurso() {
        return recurso;
    }

    @Override
    public String toString() {
        return "Aresta {" +
                "origem=" + origem +
                ", destino=" + destino +
                ", peso=" + peso +
                ", recurso=" + recurso +
                '}';
    }
}
