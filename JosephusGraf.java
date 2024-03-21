
/**
 * Classe que cria toda a interface grafica e é responsável pela interação com o usuario
 * 
 * @author Beatriz Lopes Rizzo 
 * @author Guilherme Diniz Leocadio
 * @author João Pedro de Souza Oliveira
 * @author Lucas Lombardi de Brito
 * @version 19/06/23
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
public class JosephusGraf extends JFrame{
    private Josephus josephus;
    private List<JPanel> individuos;
    private List<JLabel> labels;
    private JButton botaoComecar,botaoReiniciar, BotaoAlterarEntrada;
    private JLabel lblQtdIndividuos, lblPasso;
    private JTextField txfQtdIndividuos, txfPasso;
    private int qtdIndividuos, passo;
    /**
     * JosephusGraf Construtor
     *
     * @param nIndividuos Um parâmetro que contem o numero de individuos (nos) que a lista duplamente ligada circular ira conter
     * @param passo Um parâmetro que contem o numero que sera utilizado para contar qual individuo ira morrer em seguida
     */
    public JosephusGraf() {    
        individuos = new ArrayList<>();
        labels = new ArrayList<>();
    }

    /**
     * Método setJosephus - atualiza a variavel de instancia josephus
     *
     * @param josephus Um parâmetro que sera o novo valor da variavel de instancia josephus
     */
    private void setJosephus(Josephus josephus){
        this.josephus = josephus;
    }

    /**
     * Método criarInicio - Permite o usuario escolher a quantidade de individuos e o passo da simulacao
     *
     */
    public void criarInicio(){
        boolean teste = false;
        do{
            var escolha = JOptionPane.showConfirmDialog (null, criarJanelaInicial() , "Josephus", JOptionPane.OK_CANCEL_OPTION);
            if(escolha != JOptionPane.OK_OPTION){
                System.exit(0);
            }else{
                try{
                    this.qtdIndividuos = Integer.parseInt (txfQtdIndividuos.getText());
                    this.passo = Integer.parseInt (txfPasso.getText());
                    if(qtdIndividuos < 2 || passo < 2){
                        throw new qtdIndividuosEPassoException();
                    }
                    teste = true;
                }catch(qtdIndividuosEPassoException e){
                    if(passo < 2 && qtdIndividuos < 2){
                        JOptionPane.showMessageDialog(null, "Quantidade de individuos invalida e passo invalido");
                    }else if(passo < 2 && qtdIndividuos >= 2){
                        JOptionPane.showMessageDialog(null, "Passo invalido");
                    }else{
                        JOptionPane.showMessageDialog(null, "Quantidade de individuos invalida");
                    }
                }catch(NumberFormatException e){
                    JOptionPane.showMessageDialog(null, "Entrada(s) invalida(s)");
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, "Erro inesperado");
                }
            }
        }while(teste != true);
        Josephus josephus = new Josephus(qtdIndividuos, passo);
        setJosephus(josephus);
        josephus.processo();
        criacaoJanelaGrafica();
    }
    
    /**
     * Método criarJanelaInicial - Cria a janela inicial do programa
     *
     * @return O valor de retorno retorna a janela inicial criada
     */
    private JPanel criarJanelaInicial(){
        lblQtdIndividuos = new JLabel("Quantidade de individuos ", JLabel.LEFT);
        lblPasso = new JLabel("Passo ", JLabel.LEFT);

        txfQtdIndividuos = new JTextField();
        txfPasso = new JTextField();
        
        //Criação do botaoAjuda
        JButton botaoAjuda = new JButton("Ajuda");
        botaoAjuda.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null, "Quantidade de individuos mínima é 2 e o passo mínimo é 2");
                }
            });
        
        JPanel info = new JPanel(new BorderLayout());
        info.setLayout(new GridLayout(4, 4));
        info.add(lblQtdIndividuos);
        info.add(txfQtdIndividuos);
        info.add(lblPasso);
        info.add(txfPasso);
        
        JPanel inicio = new JPanel(new BorderLayout());
        inicio.add(botaoAjuda, BorderLayout.NORTH);
        inicio.add(info, BorderLayout.CENTER);
        
        return inicio;
    }

    /**
     * Método criacaoJanelaGrafica - Cria a janela em que o usuario utilizara o programa
     *
     */
    private void criacaoJanelaGrafica() {
        setTitle("Simulacao Josephus");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);

        int numIndividuosPorLinha = (int) Math.ceil((double) josephus.getNIndividuos() / 10);
        setLayout(new GridLayout(numIndividuosPorLinha, 10));

        createIndividuos();
        createBotaoComecar();
        createBotaoReiniciar();
        createBotaoAlterarEntrada();
        botaoReiniciar.setEnabled(false);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(criarListaMortos(), BorderLayout.NORTH);
        contentPane.add(criarPainelBotoes(), BorderLayout.SOUTH);
        contentPane.add(criarConteinerIndividuos(), BorderLayout.CENTER);
        setContentPane(contentPane);

        int windowSize = 600;
        setSize(windowSize, windowSize);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Método criarListaMortos - Permite o usuario enchergar a ordem de execucao dos individuos
     *
     * @return O valor de retorno retorna a lista que contem a ordem de execucao dos individuos
     */
    private JTextArea criarListaMortos(){
        JTextArea listaMortos = new JTextArea();
        listaMortos.setEditable(false);
        listaMortos.setLineWrap(true);
        listaMortos.setWrapStyleWord(true);

        String texto = "Ordem de execucoes: " + josephus.getStringOrdemRemocao();
        listaMortos.setText(texto);

        return listaMortos;
    }

    /**
     * Método criarPainelBotoes - Disponibiliza o acesso a dois botoes para o usuario
     *
     * @return O valor de retorno retorna o painel de botoes que o usuario podera clicar
     */
    private JPanel criarPainelBotoes(){
        JPanel painelBotoes = new JPanel(); // Painel para os botões
        painelBotoes.setLayout(new BorderLayout()); // Define o layout do painelBotoes como BorderLayout
        painelBotoes.add(botaoComecar, BorderLayout.NORTH); // Adiciona o botaoComecar no norte do painelBotoes
        painelBotoes.add(botaoReiniciar, BorderLayout.SOUTH); // Adiciona o botaoReiniciar no sul do painelBotoes
        painelBotoes.add(BotaoAlterarEntrada, BorderLayout.CENTER); // Adiciona o botaoAlterarEntrada no centro do painelBotoes
        return painelBotoes;
    }

    /**
     * Método criarConteinerIndividuos
     *
     * @return O valor de retorno retorna um painel que permitira o usuario visualizar todos os individuos
     */
    private JPanel criarConteinerIndividuos() {
        JPanel individuosContainer = new JPanel(new GridLayout(0, 10));
        for (JPanel individuo : individuos) {
            individuosContainer.add(individuo);
        }
        return individuosContainer;
    }

    /**
     * Método createIndividuos - Cria a representacao dos individuos na interface grafica
     * nesse caso os individuos sao quadrados
     */
    private void createIndividuos() {
        individuos.clear(); //limpa o array dinamico
        labels.clear(); //limpa o array dinamico
        int numIndividuos = josephus.getNIndividuos();
        for (int i = 1; i <= numIndividuos; i++) {
            JPanel individuo = new JPanel();
            individuo.setBackground(Color.GREEN);
            individuo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            individuo.setPreferredSize(new Dimension(50, 50));

            JLabel label = new JLabel(String.valueOf(i));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);

            individuo.setLayout(new BorderLayout());
            individuo.add(label, BorderLayout.CENTER);

            individuos.add(individuo);
            labels.add(label);

            add(individuo);
        }
    }

    /**
     * Método createBotaoComecar - Cria o botao comecar na interface grafica
     *
     */
    private void createBotaoComecar() {
        botaoComecar = new JButton("Começar");
        botaoComecar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    iniciaJosephus();
                }
            });
    }

    /**
     * Método createBotaoAlterarEntrada - Cria o botão Alterar entradas na interface grafica
     *
     */
    private void createBotaoAlterarEntrada(){
        BotaoAlterarEntrada = new JButton("Alterar entradas");
        BotaoAlterarEntrada.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    criarInicio();
                }
            });
    }

    /**
     * Método createBotaoReiniciar - Cria o botão reiniciar na interface grafica
     *
     */
    private void createBotaoReiniciar() {
        botaoReiniciar = new JButton("Reiniciar");
        botaoReiniciar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    criacaoJanelaGrafica();
                    iniciaJosephus();
                }
            });
    }

    /**
     * Método iniciaJosephus - Desabilita todos os botoes enquanto o processo do josephus eh realizado
     * e apos a conclusao habilita o botao de reiniciar o processo
     */
    private void iniciaJosephus() {
        botaoComecar.setEnabled(false); // Desabilita o botão enquanto a simulacao é executada
        botaoReiniciar.setEnabled(false);
        BotaoAlterarEntrada.setEnabled(false);
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    processoJosephusGraf();
                    return null;
                }

                @Override
                protected void done() {
                    individuos.get(0).setBackground(Color.GREEN);
                    BotaoAlterarEntrada.setEnabled(true); // Habilita o botão após o termino da sumilacao
                    botaoReiniciar.setEnabled(true);
                }
            };

        worker.execute();
    }

    /**
     * Método processoJosephusGraf - Realiza o processo que permite o usuario visualizar graficamente o andamento do
     * processo josephus
     */
    private void processoJosephusGraf() {
        int qtdIndividuos = josephus.getNIndividuos();
        int individuoMorto = 0;
        while (qtdIndividuos != 1) {
            individuoMorto = (individuoMorto + josephus.getPasso() - 1) % qtdIndividuos; //qualcula o quadrado que tera a cor trocada
            individuos.get(individuoMorto).setBackground(Color.RED); //troca a cor do quadrado para vermelho
            labels.get(individuoMorto).setForeground(Color.WHITE); //troca a cor do numero do quadrado para branco
            repaint();

            try {
                Thread.sleep(500); //congela o programa
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            individuos.remove(individuoMorto); //tira o individuo do array dinamico
            labels.remove(individuoMorto); //tira o individuo do array dinamico
            qtdIndividuos--;
        }
    }
}
