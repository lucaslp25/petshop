package application;

public class Main{
    public static void main(String[] args) {

        //primeira coisa para criar um cliente precisa de um endereço! Para ter a referencia no DB

        //Notas sobre metodos implementados!
        /*Tive que fazer um metodo especifo em funcionario para buscar o ID, um metodo que pega o id pelo nome e cargo, pois no funcionario so tem cargo e nome de atributos, o que deixa muito generico, e pode gerar muitos resultados de ID se for buscar apenas por um dos atributo, então fiz esse metodo que busca pela combinação desses dois atributos, e la no banco de dados eu tambem fiz a logica da linha ser unica por nome e cargo, ou seja: uma linha não pode ter o mesmo nome e cargo iguais de outra linha, se não o banco de dados lança um erro no meu programa, isso deixa mais fácil de administrar os ids, e a forma tambem de como vou lidar com as outras entidades do meu programa, para eu não precisar ficar buscando manualmente no banco de dados op id de cada entidade que eu precisar para fazer cada CRUD!*/

        //Ponto importante sobre integridade dos dados no banco de dados, sobre o metodo de deletar.. eu estava chamando dentro do metodo da subclasse junto o deleteById da superclasse de Serviços, e isso estava causando erro de integridade, pois o banco de dados diz que não pode ter nada nas tabelas dependentes da coluna que tu for deletar... então tive que tirar a chamada dos metodos e deixar para fazer na ordem correta, deletar primeiro o serviço especifico, e depois o serviço no todo, para não dar erro de integridade no programa!

        //sistema para agendamentos, preciso dessas classes todas instanciadas necessariamente! Depois inserir todas elas no banco de dados e recuperalas com seu respectivo ID para conseguir colocar no agendamento !

        //fazer um commit e partir explorar jUnit para testes unitarios!

        //nova fase do projeto, toda estrutura adaptada ao maven e jUnits para fazer alguns testes no banco de dados isoladamente!

    }
}