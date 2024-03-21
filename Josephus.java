import TAD.No;
import TAD.ListaDuplamenteLigadaCircular;
/**
 * Classe que possui a função de iniciar a simulação do problema josephus e que possui a lista duplamente ligada circular
 * que representa a roda de pessoas.
 * 
 * @author Beatriz Lopes Rizzo 
 * @author Guilherme Diniz Leocadio
 * @author João Pedro de Souza Oliveira
 * @author Lucas Lombardi de Brito
 * @version 19/06/23
 */
public class Josephus
{
    public int nIndividuos;
    public int passo;
    ListaDuplamenteLigadaCircular roda = new ListaDuplamenteLigadaCircular();
    int cont = 0;
    No posAtual;
    long idPosicaoAtual;
    int[] remocao; //Ordem de remoção dos individuos
    
    /**
     * Josephus Construtor
     *
     * @param nIndividuos Um parâmetro - numero de individuos (nos) que a lista duplamente ligada circular terá
     * @param passo Um parâmetro - numero que define a contagem de individuos até a morte do proximo
     */
    public Josephus(int nIndividuos, int passo){
        setNIndividuos(nIndividuos);
        setPasso(passo);
        int[] remocao = new int[nIndividuos];
        setRemocao(remocao);
    }

    private void setPosicaoAtual(ListaDuplamenteLigadaCircular roda){
        posAtual = roda.getInicio();
    }

    private void setRemocao(int[] remocao){
        this.remocao = remocao;
    }

    private void setNIndividuos(int nIndividuos){
        this.nIndividuos = nIndividuos;
    }

    public int getNIndividuos(){
        return this.nIndividuos;
    }

    public int getPasso(){
        return this.passo;
    }

    private void setPasso(int passo){
        this.passo = passo;
    }

    private long proximoMorto(ListaDuplamenteLigadaCircular roda, No posAtual, int passo){
        for(int i = 1; i < passo; i++){
            posAtual = posAtual.getProximo();
        }
        this.posAtual = posAtual.getProximo();
        return posAtual.getId();
    }

    public void processo(){
        for(int i = 0; i < getNIndividuos(); i++){
            cont++;
            roda.inserirFim(cont);
        }
        setPosicaoAtual(roda);
        //System.out.println("roda INICIAL:" + roda);
        cont = 0;
        while(roda.getQtdNos() != 1){
            idPosicaoAtual = proximoMorto(roda, posAtual, passo);
            No aux = posAtual.getAnterior();
            roda.remover(idPosicaoAtual);
            remocao[cont] = (int)aux.getConteudo();
            //System.out.println("roda "+ cont + ":" + roda);
            cont++;
        }
    }
    
    public int getOrdemRemocao(int i){
        return remocao[i];
    }
    
    public String getStringOrdemRemocao(){
        String listaMortos = "";
        for (int i = 0; i < getNIndividuos()-1; i++) {
            listaMortos += remocao[i];
            if (i < remocao.length - 2) {
                listaMortos += ", "; // Adiciona uma vírgula e um espaço entre os elementos
            }
        }
        return listaMortos;
    }
}