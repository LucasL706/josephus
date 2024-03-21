package TAD;
/**
 * Interface do Tipo Abstrato de Dado (TAD)
 * 
 * @author Beatriz Lopes Rizzo 
 * @author Guilherme Diniz Leocadio
 * @author João Pedro de Souza Oliveira
 * @author Lucas Lombardi de Brito
 * @version 19/06/23
 */
public interface IListaDuplamenteLigadaCircular{
    public boolean estaVazia(); 
    
    public void inserirInicio(Object novo); 

    public void inserirFim(Object novo);
    
    public boolean inserirApos(long chave, Object novo);

    public Object removerInicio();

    public Object removerFim();
    
    public Object remover(long chave);
    
    public String toStringDoFim();
}
