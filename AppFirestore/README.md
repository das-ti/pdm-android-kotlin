# AppFirestore: Cadastro de Produtos

Tarefa da Aula 11 (Kotlin Android + Firestore): uma tela para **cadastrar produtos** e outra para **listar todos os produtos**.

- Coleção: `Produtos`
- Documento: ID gerado automaticamente (`collection.add()`)
- Campos: `nome` (string), `categoria` (string), `preco` (number)

## Estrutura

```
app/src/main/java/br/edu/fatecpg/appfirebase/
├── model/Produto.kt                 # data class mapeada para o documento
├── data/ProdutoRepository.kt        # Singleton + Facade de acesso ao Firestore
└── ui/
    ├── CadastroProdutoActivity.kt   # tela 1: formulário (tela inicial)
    ├── ListaProdutosActivity.kt     # tela 2: lista em tempo real (Observer)
    └── ProdutoAdapter.kt            # RecyclerView ListAdapter
```

## Configurar o Firebase na sua conta

O arquivo `app/google-services.json` **não está no repositório**: o antigo apontava para o projeto do professor (`appkotlin-9418b`). Gere o seu:

1. Acesse <https://console.firebase.google.com> com a sua conta Google.
2. **Adicionar projeto** → nome, por exemplo `AppFirestore` → pode desativar o Google Analytics → **Criar projeto**.
3. Na visão geral do projeto, clique no ícone **Android** para registrar o app:
   - Nome do pacote: `br.edu.fatecpg.appfirebase` (precisa ser exatamente este)
   - Apelido: `AppFirestore` (opcional)
   - **Registrar app** → **Baixar google-services.json**
4. Copie o arquivo baixado para `AppFirestore/app/google-services.json`.
   Pule os passos de "Adicionar SDK": o projeto já está configurado.
5. No menu lateral: **Criação → Firestore Database → Criar banco de dados**.
   - Local: `southamerica-east1 (São Paulo)`
   - Escolha **Iniciar no modo de teste** → **Criar**.
6. (Recomendado) Na aba **Regras**, troque pelo conteúdo de [`firestore.rules`](firestore.rules) e clique em **Publicar**.
   O modo de teste expira em 30 dias; estas regras liberam só a coleção `Produtos` e validam os campos.
7. No Android Studio: **File → Sync Project with Gradle Files** e rode o app.
   Cadastre um produto e confira em **Firestore Database → Dados → Produtos**.

## Correções feitas no projeto original

| Problema | Correção |
|---|---|
| `->` solto no bloco `plugins` de `app/build.gradle.kts` (erro de sintaxe) | Removido |
| AGP 8.13.2 com Gradle 8.10.2 (o AGP 8.13 exige Gradle 8.13+), e daemon em JDK 25, que o Gradle 8.10 não suporta | Alinhado aos seus outros projetos: AGP 9.3.2 + Gradle 9.5.0 |
| Plugin `org.jetbrains.kotlin.android` + `kotlinOptions` (o AGP 9 já tem Kotlin embutido e não aceita os dois) | Removidos |
| `jvmTarget = "21"` diferente de `JavaVersion.VERSION_11` (alvos JVM inconsistentes) | Usa só `compileOptions` (Java 11) |
| `proguard-rules.pro` referenciado, mas inexistente | Usa `optimization { enable = false }` + `keepRules/`, igual aos outros projetos |
| `google-services.json` do projeto do professor | Removido: gere o seu (passos acima) |
| `local.properties` com o caminho do SDK de outro PC | Removido (é gerado pelo Android Studio) |
| `enableEdgeToEdge()` sem tratar os insets (conteúdo atrás da status bar) | Insets aplicados em cada tela |
| Escrita fixa no `onCreate` (`colecao_cliente/doc_cliente2`) | Substituída pelas telas de cadastro e de listagem |
