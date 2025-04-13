public class Main {
    public static void main(String[] args) {
        try {
            NewFila<Integer> fila = new NewFila<>(5);
            for (int i = 0; i <= 1000000; i++) {
                fila.guardeUmItem(i);
                System.out.println(i);
            }
            
            for (int i = 0; i <= 1000000; i++) {
                fila.removaUmItem();
                System.out.println(i);
                
            }
            System.out.println(fila.toString());

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
