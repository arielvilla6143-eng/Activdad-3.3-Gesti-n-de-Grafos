/*
ARIEL VILLA OLIVAREZ
ANGEL EMILIANO REYNOSO LÓPEZ
JULIAN EMMANUEL MENDEZ RODRIGUEZ
 */
package back_end;
import java.util.*;
/**
 *
 * @author villa
 */
public class Grafo {
    private Map<String, Vertice> vertices;
    private Map<String, List<Arista>> adyacencia;

    public Grafo() {
        vertices = new LinkedHashMap<>();
        adyacencia = new LinkedHashMap<>();
    }

    public boolean agregarVertice(Vertice v) {
        if (vertices.containsKey(v.getId())) return false;
        vertices.put(v.getId(), v);
        adyacencia.put(v.getId(), new ArrayList<>());
        return true;
    }

    public boolean agregarArista(String origen, String destino, double peso) {
        if (!vertices.containsKey(origen) || !vertices.containsKey(destino)) return false;
        if (peso <= 0) return false;
        adyacencia.get(origen).add(new Arista(destino, peso));
        return true;
    }

    public boolean eliminarVertice(String id) {
        if (!vertices.containsKey(id)) return false;
        vertices.remove(id);
        adyacencia.remove(id);
        for (List<Arista> aristas : adyacencia.values()) {
            aristas.removeIf(a -> a.getIdDestino().equals(id));
        }
        return true;
    }

    public boolean eliminarArista(String origen, String destino) {
        if (!adyacencia.containsKey(origen)) return false;
        return adyacencia.get(origen).removeIf(a -> a.getIdDestino().equals(destino));
    }

    public Vertice buscarVertice(String id) {
        return vertices.get(id);
    }

    public void destruirGrafo() {
        vertices.clear();
        adyacencia.clear();
    }

    public String recorridoBFS(String inicio) {
        if (!vertices.containsKey(inicio)) return "Nodo inicial no existe.";
        StringBuilder resultado = new StringBuilder("BFS: ");
        Set<String> visitados = new HashSet<>();
        Queue<String> cola = new LinkedList<>();

        visitados.add(inicio);
        cola.add(inicio);

        while (!cola.isEmpty()) {
            String actual = cola.poll();
            resultado.append(actual).append(" ");
            for (Arista a : adyacencia.get(actual)) {
                if (!visitados.contains(a.getIdDestino())) {
                    visitados.add(a.getIdDestino());
                    cola.add(a.getIdDestino());
                }
            }
        }
        return resultado.toString();
    }

    public String recorridoDFS(String inicio) {
        if (!vertices.containsKey(inicio)) return "Nodo inicial no existe.";
        StringBuilder resultado = new StringBuilder("DFS: ");
        Set<String> visitados = new HashSet<>();
        dfsRecursivo(inicio, visitados, resultado);
        return resultado.toString();
    }

    private void dfsRecursivo(String nodo, Set<String> visitados, StringBuilder resultado) {
        visitados.add(nodo);
        resultado.append(nodo).append(" ");
        for (Arista a : adyacencia.get(nodo)) {
            if (!visitados.contains(a.getIdDestino())) {
                dfsRecursivo(a.getIdDestino(), visitados, resultado);
            }
        }
    }

    public String obtenerMatrizAdyacencia() {
        if (vertices.isEmpty()) return "El grafo está vacío.";
        List<String> claves = new ArrayList<>(vertices.keySet());
        Collections.sort(claves);
        int n = claves.size();
        double[][] matriz = new double[n][n];

        for (int i = 0; i < n; i++) {
            String origen = claves.get(i);
            for (Arista a : adyacencia.get(origen)) {
                int j = claves.indexOf(a.getIdDestino());
                matriz[i][j] = a.getPeso();
            }
        }

        StringBuilder sb = new StringBuilder("\t");
        for (String id : claves) sb.append(id).append("\t");
        sb.append("\n");

        for (int i = 0; i < n; i++) {
            sb.append(claves.get(i)).append("\t");
            for (int j = 0; j < n; j++) {
                sb.append(matriz[i][j] == 0 ? "0" : matriz[i][j]).append("\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public String evaluarConectividad() {
        if (vertices.isEmpty()) return "Grafo vacío (Desconectado).";
        int n = vertices.size();
        
        boolean fuertementeConectado = true;
        for (String id : vertices.keySet()) {
            if (alcanzables(id, true) < n) {
                fuertementeConectado = false;
                break;
            }
        }
        if (fuertementeConectado) return "El grafo está Fuertemente Conectado.";

        if (alcanzables(vertices.keySet().iterator().next(), false) == n) {
            return "El grafo está Débilmente Conectado.";
        }

        return "El grafo está Desconectado.";
    }

    private int alcanzables(String inicio, boolean dirigido) {
        Set<String> visitados = new HashSet<>();
        Queue<String> cola = new LinkedList<>();
        visitados.add(inicio);
        cola.add(inicio);

        while (!cola.isEmpty()) {
            String actual = cola.poll();
            for (Arista a : adyacencia.get(actual)) {
                if (visitados.add(a.getIdDestino())) cola.add(a.getIdDestino());
            }
            if (!dirigido) {
                for (String origen : adyacencia.keySet()) {
                    for (Arista a : adyacencia.get(origen)) {
                        if (a.getIdDestino().equals(actual) && visitados.add(origen)) {
                            cola.add(origen);
                        }
                    }
                }
            }
        }
        return visitados.size();
    }
}
