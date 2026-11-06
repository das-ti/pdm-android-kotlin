# AppCompose — Calculadora de Desconto

Aplicativo Android (Kotlin + Jetpack Compose) em que o usuário informa o preço de um produto e o percentual de desconto, e o app mostra o preço final.

Exemplo: preço R$ 500,00 e desconto de 15% → **Preço final: R$ 425,00**.

## Estrutura

- `MainActivity.kt` — ponto de entrada; chama a tela.
- `CalculadoraDescontoScreen.kt` — interface (dois `TextField`, botão e resultado) e estados.
- `CalculadoraDesconto.kt` — regra de negócio (`calcularPrecoComDesconto`).

## Perguntas e respostas

**1. O que é entrada em um sistema computacional? Dê dois exemplos.**
Entrada são os dados que o sistema recebe do mundo externo para processar. Exemplos: o preço digitado no `TextField` e o percentual de desconto digitado pelo usuário (também: toque na tela, leitura de um sensor).

**2. Qual é a diferença entre processamento e saída?**
Processamento é a transformação dos dados de entrada (aqui, o cálculo `preco - preco * percentual / 100` feito por `calcularPrecoComDesconto`). Saída é o resultado apresentado ao usuário (o texto "Preço final: R$ 425,00" na tela).

**3. Por que o valor digitado em um TextField é inicialmente tratado como texto?**
Porque o teclado produz caracteres, e o `TextField` trabalha com `String`. O usuário pode digitar letras, vírgula, campo vazio etc. Por isso o texto precisa ser convertido (`toDoubleOrNull`) e validado antes de ser usado em cálculos.

**4. Qual é a finalidade de remember e mutableStateOf?**
`mutableStateOf` cria um estado observável: quando o valor muda, o Compose é avisado e redesenha a tela. `remember` guarda esse estado entre recomposições; sem ele, a variável seria recriada com o valor inicial a cada redesenho e o texto digitado se perderia.

**5. O que é um evento? Cite os eventos utilizados nesta aplicação.**
Evento é uma ação do usuário ou do sistema que dispara um código. Nesta aplicação: `onValueChange` dos `TextField` (cada alteração do texto, atualizando `precoTexto` e `descontoTexto`) e `onClick` do `Button` CALCULAR (executa a conversão, validação e cálculo).

**6. O que significa recomposição no Jetpack Compose?**
É a re-execução das funções `@Composable` que dependem de um estado que mudou, para atualizar a interface. O Compose redesenha apenas as partes afetadas (por exemplo, o `Text` do resultado ao mudar `resultado`).

**7. Qual é a responsabilidade da MainActivity neste projeto?**
Ser o ponto de entrada do app: configura a janela (`enableEdgeToEdge`) e define o conteúdo com `setContent`, chamando `CalculadoraDescontoScreen()`. Não contém lógica de cálculo nem detalhes da interface.

**8. Qual é a responsabilidade de CalculadoraDescontoScreen?**
Montar a interface e gerenciar seus estados: exibir os campos, ler o texto digitado, converter e validar os valores, chamar `calcularPrecoComDesconto` ao clicar no botão e mostrar o resultado ou a mensagem de erro.

**9. Por que calcularPrecoComDesconto foi colocada em outro arquivo?**
Para separar responsabilidades: a interface cuida da apresentação e a regra de negócio fica isolada. Isso deixa o código mais organizado, fácil de localizar e de reutilizar.

**10. Qual é a vantagem de uma função de regra de negócio não depender do Compose?**
Ela pode ser testada com testes unitários simples (sem emulador nem interface), reutilizada em outras telas ou plataformas e mantida sem risco de quebrar a UI. Se a interface mudar, a regra continua igual.
