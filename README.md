## 🛠️ Como Usar o Projeto

Siga os passos abaixo para configurar e executar o `petshop` em sua máquina local.

---

### Pré-requisitos

Certifique-se de que você tem as seguintes ferramentas instaladas em seu ambiente de desenvolvimento:

* **Java Development Kit (JDK) 17 ou superior:**
    * Você pode baixar o JDK em [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) ou [OpenJDK](https://openjdk.org/install/).
* **MySQL Server 8.0 ou superior:**
    * Baixe e instale o MySQL Server em [MySQL Downloads](https://dev.mysql.com/downloads/mysql/).
* **MySQL Workbench:**
    * Recomendado para gerenciar seu banco de dados ([MySQL Workbench](https://www.mysql.com/products/workbench/)).
* **Apache Maven 3.x:**
    * Baixe e instale o Maven [Apache Maven](https://maven.apache.org/download.cgi).
* **Git:**
    * Instale o Git para clonar o repositório [Git-SCM](https://git-scm.com/downloads/).
* **IDE:**
   * Recomendavel ter uma IDE (Ambiente de desenvolvimento integrado) como o IntelliJ, Eclipse, NetBeans, etc. <br>
     Fiz o projeto inteiro utilizando a IDE IntelliJ da JETBRAINS. Link: [IntellijIDEA](https://www.jetbrains.com/idea/#).
---

### 📥 Configuração do Banco de Dados

1. **Abra seu WorkBech**
   Depois de instalado o Banco de dados MySQL na sua maquina você deve abrir o WorkBench e realizar as configurações locais da sua maquina no banco de dados!

2.  **Crie o Banco de Dados:**
   Abra o seu WorkBench, copie e cole o conteúdo do `database.sql` que está no projeto!

   *Nota: É para criar o banco de dados com o nome padrão que deixei como super_pet_shop, e fazer a criação de todas tabelas e inserção dos métodos de pagamentos!*

---

### ⚙️ Configuração do Projeto

1.  **Clone o Repositório:**
    Abra seu terminal ou prompt de comando e clone o projeto do GitHub:

    ```bash
    git clone [https://github.com/lucaslp25/petshop.git](https://github.com/lucaslp25/petshop.git)
    cd petshop
    ```

2.  **Configurar as Propriedades do Banco de Dados:**
    O projeto utiliza um arquivo de propriedades para a conexão com o banco de dados. <br>

    * Na raiz do projeto, você encontrará um arquivo `db-example.properties`. **Renomeie-o para `db.properties`**.
    * Abra o arquivo `db.properties` e atualize as informações de conexão com seu banco de dados MySQL:
  
    *O Arquivo `db-example.properties` está bem intuitivo pedindo para substituir o nome de usuario pelo seu, a senha e o nome do banho de dados, que se for o padrão sera super_pet_shop*

3.  **Construa o Projeto com Maven:**
    Navegue até a raiz do projeto (`petshop`) no terminal e execute o comando Maven para construir o projeto e baixar as dependências:

    ```bash
    mvn clean install
    ```

---

### ▶️ Como Executar

Após a configuração bem-sucedida, você pode executar a aplicação:

1.  **Via IDE (IntelliJ IDEA, Eclipse, etc.):**
    * Importe o projeto Maven para sua IDE.
    * Localize a classe principal da aplicação (o caminho dela é --> `src/main/java/application/Main.java`).
    * Execute o método `main` da classe principal.

2.  **Via Linha de Comando (JAR Executável):**
    Se o `mvn clean install` gerou um JAR executável (verifique seu `pom.xml` para a configuração do plugin `maven-jar-plugin`), você pode executá-lo com:

    ```bash
    java -jar target/petshop.jar # Substitua 'petshop.jar' pelo nome real do seu JAR
    ```

    *Obs: Se o projeto não gerar um JAR executável por padrão, a execução via IDE é a forma mais simples.*
