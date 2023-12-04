import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Data {

    Grafo grafo;
    int recursoTotal;
    List<Aresta> arestas;

    public Data(Grafo grafo, int recursoTotal) {
        this.recursoTotal = recursoTotal;
        this.grafo = grafo;
        this.arestas = new ArrayList<Aresta>();

        for (int i = 1; i < grafo.listaAdjacencia.size()+1; i++) {
            arestas.addAll(grafo.listaAdjacencia.get(i));
        }
    }
}
