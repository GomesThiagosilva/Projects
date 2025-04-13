public class Main {
    public static void main(String[] args) {
        try {
            Fila<Integer> fila = new Fila<>(5);
            for (int i = 0; i <= 1000000; i++) {
                fila.guardeUmItem(i);
            }
            
            for (int i = 0; i <= 1000000; i++) {
                fila.removaUmItem();
                
            }
            System.out.println(fila.toString());
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}
