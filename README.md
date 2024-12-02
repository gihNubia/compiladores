# compiladores

## Grupo: Gisele Oliveira e Erick Rocha.

## Comandos

Compilar
```sh
javac -d out/production/lexico $(find . -name "*.java")
```

Rodar
```sh
java -cp out/production/lexico Main <file-name>.txt
```
## Implementação

O projeto está organizado em três principais diretórios:
- `out`: Destino das classes compiladas do projeto.
- `src`: Código fonte do compilador.
- `tests`: Arquivos de teste que serão usados para validar o funcionamento do compilador.

O código fonte será dividido em pacotes para cada fase da compilação, e uma classe `Main.java` na raiz. Para essa primeira entrega foi implementado o pacote lexical que tem as seguintes responsabilidades:

- Definir a classe `Token` que carrega a informação da `Tag` e é herdado pelos diferentes tipos de tokens: `constante float`, `constante int`, `literal`, `word`.
- Definir o enum `Tag` que diferencia classes de tokens, palavras reservadas e formações específicas como `(`, `,`, `&&`, `+`, `-`, `/`, `!`, `==`, etc.
- Definir tipos de erros léxicos: comentários que não terminam, literais que não fecham, números terminados em `.`, tokens inválidos. Na nossa abordagem, o token inválido não gera uma mensagem de erro específica, apenas é exposto com a tag `ERRO`. O objetivo é que o analisador sintático lide com ele no futuro.
- Implementar `Lexer` capaz de receber um arquivo e retornar uma cadeia de tokens desse arquivo por meio do método `scan`.

### Classes

1. **`Main.java`**
  - Responsável por receber os argumentos da linha de código e formatar o output. Ela chama o método `Token.scan()` repetidas vezes para obter os tokens da entrada, e os salva em uma lista de tokens. Ele capta a tabela de símbolos do Lexer e os erros, caso houver algum. Por fim, ele mostra os resultados no terminal.

2. **`Token`**
  - Token é um conjunto formado por um conteúdo e uma Tag. Eles são divididos nos tipos `Word`, `Float_C`, `Int`, `Literal`, e `InvalidToken`, que são classes filhas de Token.
  - O conteúdo de `Word` para os tokens genéricos, como os operadores, seguem o seguinte raciocínio: para o operador menor que, o valor é `<` e a `Tag` é `LT`. Para as palavras reservadas, tanto o valor quanto a Tag são a própria palavra. Para um identificador, a tag é `ID` e o valor é o nome do identificador.
  - `Float_C` representa um valor de ponto flutuante, com uma cadeia de pelo menos um número seguido por um `.` e mais uma cadeia de pelo menos um dígito.
  - `Int` são apenas um conjunto de pelo menos um dígito.
  - `Literal` identifica literais, que sempre são começados por `{` e finalizados com `}`.
  - `InvalidToken` é aquele que não pertence à gramática.

3. **`Lexer.java`**
  - Forma os tokens e os retorna, além de adicionar as palavras na tabela de símbolos (`words`) quando necessário. Quando o Lexer é instanciado, ele adiciona na tabela de símbolos as palavras reservadas, que são do tipo `Word`, mas cada uma com uma tag específica indicando o significado da palavra.
  - Identifica tokens do tipo `Word` para identificadores, palavras reservadas, operadores, e outros tokens simples como `(`, `)`, `,`, `;`.
  - Identifica tokens para `Float_C` e `Int`, sendo a diferença entre eles que `Float` possui casas decimais.
  - Identifica tokens do tipo `Literal` para todo lexema começado com `{` e terminado com `}`. Caso nenhum padrão seja identificado, retorna um `InvalidToken`, que são aqueles que não foram reconhecidos no parser.
  - Possui uma variável chamada `eof` que identifica o final de um arquivo.
  - Sobe os seguintes erros: comentários que não terminam, literais que não fecham, números terminados em `.`, tokens inválidos.

4. **`Tag.java`**
  - Enum usado para diferenciar os tipos de tokens:
    - Para tokens da classe `Word` (palavras reservadas): `START`, `EXIT`, `INT`, `FLOAT`, `STRING`, `IF`, `THEN`, `END`, `ELSE`, `DO`, `WHILE`, `SCAN`, `PRINT`.
    - Para tokens da classe `Word` (genéricos): `AT`, `EQ`, `GT`, `LT`, `GE`, `LE`, `NE`, `PLUS`, `MINUS`, `OR`, `MULTIPLY`, `DIVIDE`, `MODULUS`, `AND`, `SC`, `CL`, `NT`, `OP`, `CP`.
    - Para tokens identificadores: `ID`.
    - Para `Float_C`: `FLOAT_C`.
    - Para `Literal`: `LITERAL`.
    - Para `InvalidToken`: `ERROR`.

5. **Exceções**
  - `EndOfFileException`: Quando um comentário não é fechado até o final do arquivo.
  - `InvalidNumberFormatException`: Números terminados em `.`.
  - `StringException`: Literais que não fecham.

