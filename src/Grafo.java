import java.util.*;

class Grafo {
    public Map<Integer, List<Aresta>> listaAdjacencia;

    public Grafo() {
        this.listaAdjacencia = new HashMap<>();
    }

    public void adicionarVertice(int vertice) {
        listaAdjacencia.put(vertice, new LinkedList<>());
    }

    public void adicionarAresta(int origem, int destino, int peso, int recurso) {
        if (!listaAdjacencia.containsKey(origem))
            adicionarVertice(origem);

        if (!listaAdjacencia.containsKey(destino))
            adicionarVertice(destino);

        Aresta aresta = new Aresta(origem, destino, peso, recurso);
        listaAdjacencia.get(origem).add(aresta);
    }

    public int quantidadeArestas() {
        int contador = 0;
        for (int i = 0; i < listaAdjacencia.size(); i++) {
            contador+=  listaAdjacencia.get(i+1).size();
        }
        return contador;
    }

    public void exibirGrafo() {
        for (Map.Entry<Integer, List<Aresta>> entry : listaAdjacencia.entrySet()) {
            System.out.print(entry.getKey() + " -> ");
            List<Aresta> arestas = entry.getValue();
            for (Aresta aresta : arestas) {
                System.out.print("(" + aresta.origem + ", " + aresta.getDestino() + ", Peso: " + aresta.getPeso() + ", Recurso: " + aresta.getRecurso() + ") ");
            }
            System.out.println();
        }
    }
}