public class Pilha<X> implements Cloneable
{
    private Object[] vetor;
    private int topo;
    
    
    public Pilha(int capacidade) throws Exception
    {
        if(capacidade<=0)throw new Exception("Valor invalido");
        this.vetor = new Object[capacidade];
        this.topo = -1;
    }
    public Pilha(){
        this.vetor = new Object[10];
        this.topo = -1;
    }
    public void redimensioneSe(int novaCap)
    {
        Object novo[] = new Object[novaCap];
        for(int i=0;i<=this.topo;i++)
        {
            novo[i] = this.vetor[i];
        }
        this.vetor=novo;
    }
    public void guardeUmItem (X item)throws Exception
    {
        if(item == null)throw new Exception("Item invalido");
        /*for(int i=0;i<=this.topo;i++){
            if(item.equals(this.vetor[i]))throw new Exception("Valor ja existente");
        }*/
        
        if(item instanceof Cloneable){
            Clonador<X> clonador = new Clonador<X>();
            this.vetor[this.topo] = clonador.clone (item);
        }
        if(isCheia()){
            redimensioneSe(this.vetor.length*2);
        }
        this.vetor[++this.topo] = item;
    }
    public X recupereUmItem()throws Exception
    {
        if(isVazia())throw new Exception ("O vetor esta vazio");
        if(this.vetor[this.topo] instanceof Cloneable){
            Clonador<X> clonador = new Clonador<X> (); 
            return clonador.clone ((X)this.vetor[this.topo]);
        }
        return (X)this.vetor[this.topo];
    }
    public void removaUmItem()throws Exception 
    {
        if(isVazia()) throw new  Exception("O vetor esta vazio");
        this.vetor[this.topo] = null;
        this.topo--;
        if(this.topo <= this.vetor.length * 0.25)

        {
            this.redimensioneSe(this.vetor.length/2);
        }
    }
    public boolean isCheia(){
        return this.topo+1 == this.vetor.length;
        
    }
    public boolean isVazia(){
        if(this.topo==-1){
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    public String toString(){
        String ret ="[";
        if(this.topo<0) return"não existe vetorentos";
        ret+=this.vetor[0];
        for(int i=1;i<=this.topo;i++){
            ret+=","+this.vetor[i];
        }
        ret +="]";
        return ret;
    }
    @Override
    public boolean equals(Object obj){
        if(obj==null) return false;
        if(this==obj) return true;
        if(this.getClass()!=obj.getClass())return false;
        
        Pilha<X> pilha = (Pilha<X>)obj;
        
        if(this.topo!=pilha.topo) return false;
        
        for(int i=0;i<=this.topo;i++)
        {
            if(this.vetor[i].equals((X)pilha.vetor[i])) return false;
        }
        return true;
    }
    @Override
    public int hashCode(){
        int ret = 1;
        
        ret = ret * 2 + ((Integer)this.topo).hashCode();
        for(int i = 0;i<=this.topo;i++)
        {
            ret = ret * 2 + ((X)this.vetor[i]).hashCode();
        }
        if(ret<0) ret= -ret;
        return ret;
    }
    public Pilha(Pilha<X> modelo)throws Exception
    {
        if(modelo == null )throw new Exception("Modelo invalido");
        
        this.topo = modelo.topo;
        this.vetor = new Object[modelo.vetor.length];
        
        for(int i=0;i<=this.topo;i++){
            this.vetor[i] = modelo.vetor[i];
        }
    }
    @Override
    public Object clone()
    {
        Pilha<X> copia =null;
        try{ 
            copia = new Pilha<X>(this);
        }catch(Exception erro)
        {}
        return copia;
    }
}