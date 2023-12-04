public class Main {
    public static void main(String[] args) {
        Grafo grafo = new Grafo();

        grafo.adicionarAresta(1, 2, 1, 30);
        grafo.adicionarAresta(1, 3, 1, 10);
        grafo.adicionarAresta(2, 3, 1, 20);
        grafo.adicionarAresta(2, 4, 2, 10);
        grafo.adicionarAresta(3, 4, 5, 10);
        grafo.adicionarAresta(3, 5, 1, 1000);
        grafo.adicionarAresta(4, 6, 2, 10);
        grafo.adicionarAresta(5, 6, 1, 10);

        System.out.println("Grafo:");
        grafo.exibirGrafo();

        //Origem = Primeiro Vertice
        //Destino = Ultimo Vertice

        Data data = new Data(grafo, 100);
        Modelo modelo = new Modelo(data);
        modelo.solveModel();

    }
}
