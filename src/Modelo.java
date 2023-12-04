import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;

public class Modelo {
    Data data;
    double infinity = Double.POSITIVE_INFINITY;

    public Modelo(Data data) {
        this.data = data;
    }

    public void solveModel() {
        Loader.loadNativeLibraries();
        MPSolver solver = MPSolver.createSolver("SCIP");

        int quantidadeArestas = data.grafo.quantidadeArestas();

        MPVariable[] variaveis = new MPVariable[quantidadeArestas];

        //CRIANDO AS VARIAVEIS DE DECISAO E COLOCANDO EM UM ARRAY
        // X12 = X0
        // X13 = X1
        // X23 = X2
        // X24 = X3
        // X34 = X4
        // X35 = X5
        // X46 = X6
        // X56 = X7

        for (int i = 0; i < quantidadeArestas; i++) {
            variaveis[i] = solver.makeBoolVar("X " + (i));
        }

        //CRIANDO A FUNCAO OBJETIVO
        // x12 + x13 + x23 + 2x24 + 5x34 ...
        // x0 + x1 + x2 + 2x3 + 5x4
        MPObjective fObjetivo = solver.objective();
        for (int i = 0; i < quantidadeArestas; i++) {
            fObjetivo.setCoefficient(variaveis[i], data.arestas.get(i).peso);
        }
        fObjetivo.setMinimization();

        //RESTRICAO DA ORIGEM
        // X12 + X13 = 1
        // X0 + X1 = 1
        MPConstraint restricaoOrigem = solver.makeConstraint(1, 1, "Restricao Origem");
        for (int i = 0; i < data.grafo.listaAdjacencia.get(1).size(); i++) {
            restricaoOrigem.setCoefficient(variaveis[i], 1);
        }

        //RESTRICAO DO DESTINO
        // X46 + X56 = 1
        // X6 + X7 = 1
        MPConstraint restricaoDestino = solver.makeConstraint(1, 1, "Restricao Destino");
        for (int i = 0; i < quantidadeArestas; i++) {
            if (data.arestas.get(i).destino == 6) {
                restricaoDestino.setCoefficient(variaveis[i], 1);
            }
        }

        // RESTRICAO DEMAIS ARCOS
        // X12 = X23 + X24
        // X0 - X2 - X3 = 0

        // X13 + X23 = X34 + X35
        // X1 + X2 - X4 - X5 = 0

        // X24 + X34 = X46
        // X3 + X4 - X6 = 0

        // X35 = X56
        // X5 - X7 = 0

        for (int i = 2; i < data.grafo.listaAdjacencia.size(); i++) {
            MPConstraint restricaoFluxo = solver.makeConstraint(0, 0, "Restricao Fluxo " + (i-1));
            for (int j = 0; j < quantidadeArestas; j++) {
                if (data.arestas.get(j).destino == i) {
                    restricaoFluxo.setCoefficient(variaveis[j], 1);
                }
            }
            for (int j = 0; j < quantidadeArestas; j++) {
                if (data.arestas.get(j).origem == i) {
                    restricaoFluxo.setCoefficient(variaveis[j], -1);
                }
            }
        }

        // RESTRICAO RECURSO
        // 30X12 + 10X13 + 20X23 ... <= 100
        // 30X0 + 10X1 + 20X2 ... <= 100

        MPConstraint restricaoRecurso = solver.makeConstraint(-infinity, data.recursoTotal, "Restricao Recurso");
        for (int i = 0; i < quantidadeArestas; i++) {
            restricaoRecurso.setCoefficient(variaveis[i], data.arestas.get(i).recurso);
        }

        System.out.println("Numero de restricoes = " + solver.numConstraints());

        MPSolver.ResultStatus resultStatus = solver.solve();

        if (resultStatus == MPSolver.ResultStatus.OPTIMAL) {

            System.out.println("Custo da funcao objetivo = " + fObjetivo.value());
            System.out.println("Solucao:");
            for (int i = 0; i < quantidadeArestas; i++) {
                System.out.println("X" + i + " "  + variaveis[i].solutionValue());
            }
            System.out.println();
            System.out.println("Tempo de resolucao = " + solver.wallTime() + " milissegundos\n");
            for (int i = 0; i < quantidadeArestas; i++) {
                if (variaveis[i].solutionValue() == 1) {
                    System.out.println("X" + i + " = "  + data.arestas.get(i));
                }
            }
            System.out.println();
            System.out.println(solver.exportModelAsLpFormat());
        } else {
            System.out.println("Solucao otima nao encontrada!");
        }
    }

}
