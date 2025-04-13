public class NewFila <X> implements Cloneable{
    private Object[] elem;
    private final int tamanhoInicial;
    private int qtd =0; 
    private int fim;
    private int inicial;
    private Clonador<X> clonador;

    public NewFila(int capacidade) throws Exception
    {
        if(capacidade<=0)throw new Exception ("Tamanho invalido");

        this.elem = new Object[capacidade];
        this.tamanhoInicial = capacidade;
        this.inicial = 0;
        this.fim = 0; 
        this.clonador = new Clonador <X> ();
    }

    public NewFila(){
        this.elem = new Object[10];
        this.tamanhoInicial = 10;
        this.inicial = 0;
        this.fim = 0;
        this.clonador = new Clonador <X> ();
    }

public void reedimensioneSe(float fator) {
    Object[] novo = new Object[(int) (this.elem.length * fator)];
    int j = 0;

    for (int i = this.inicial; i < this.elem.length; i++) {
        novo[j++] = this.elem[i];
        this.elem[i] = null;
    }

    if (this.fim < this.inicial) {
        for (int i = 0; i < this.fim; i++) {
            novo[j++] = this.elem[i];
            this.elem[i] = null; 
        }
    }
    this.elem = novo;
    this.inicial = 0;
    this.fim = this.qtd;
}


    public void guardeUmItem (X item) throws Exception
    {
        if (item == null) throw new Exception ("Item esta vizio");
        if(this.qtd == this.elem.length)this.reedimensioneSe(2);
        if(item instanceof Cloneable ){
            Clonador<X> clonador = new Clonador<X>();
            this.elem[this.fim] = clonador.clone (item);
        }

        this.elem[this.fim] = item; 
        this.qtd ++;
        this.fim = (this.fim + 1) % this.elem.length;
    }
    public void removaUmItem() throws Exception
    {
        if (this.qtd==0) throw new Exception ("a lista easta vazia");

        this.elem[this.inicial] = null;
        this.inicial= (this.inicial + 1) % this.elem.length;
        this.qtd--;

        if (this.elem.length>this.tamanhoInicial &&
            this.fim + 1<=(int)(this.elem.length*0.25F))
            this.reedimensioneSe (0.5F);

    }

    public X recupereUmItem() throws Exception
    {
        if(this.qtd==0) throw new Exception("Não ha elemento na lista");

        X ret = null;
        if(this.elem[this.inicial] instanceof Cloneable)
            ret = this.clonador.clone((X)this.elem[this.inicial]);
        else 
            ret = (X)this.elem[this.inicial];
        return ret;
    }
    public boolean isCheia()
    {
        if(this.elem.length==this.qtd) return true;
        return false;
    }
    public boolean isVazia()
    {
        if(this.qtd==0) return true;
        return false;
    }

    @Override
    public String toString() {
        String ret = this.qtd + " elemento(s)";

        if (this.qtd != 0)
            ret += "\nSendo o primeiro número " + this.elem[this.inicial] +
                "\nE o último " + this.elem[(this.fim - 1 + this.elem.length) % this.elem.length];

        return ret;
    }

    @Override
    public boolean equals (Object obj)
    {
        if(obj==null) return false;
        if(obj==this) return true;
        if(this.getClass()!=obj.getClass()) return false;

        NewFila<X> nFila = (NewFila<X>)obj;

        if(this.fim!=nFila.fim)return false;
        if(this.inicial!=nFila.fim) return false;
        if(this.qtd!=nFila.qtd)return false;
        if(this.tamanhoInicial!=nFila.tamanhoInicial)return false;
        for(int i=0;i<=this.elem.length;i++)
        {
            if(!this.elem[i].equals(nFila.elem[i])) return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int ret = 1;

        ret += ret * 2 +((Integer)(this.fim           )).hashCode();
        ret += ret * 2 +((Integer)(this.inicial       )).hashCode();
        ret += ret * 2 +((Integer)(this.qtd           )).hashCode();
        ret += ret * 2 +((Integer)(this.tamanhoInicial)).hashCode();
        for(int i = 0;i<=this.elem.length;i++)
        {
            ret += ret * 2 + ((Object)this.elem[i]).hashCode();
        }
        if(ret<0) ret = -ret;
        return ret;
    }

    public NewFila (NewFila<X> modelo) throws Exception
    {
        if(modelo==null) throw new Exception("Modelo esta invalido");
        this.tamanhoInicial = modelo.tamanhoInicial;
        this.fim = modelo.fim;
        this.inicial = modelo.inicial;
        this.qtd = modelo.qtd;

        for(int i=0;i<=this.elem.length;i++)
        {
            this.elem[i] = modelo.elem[i];
        }
    }

    @Override
    public Object clone(){
        NewFila<X> ret = null;
        try{
            ret = new NewFila<X>(this);
        }
        catch(Exception erro){}
        return ret;
    }
}