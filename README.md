# Cidades

App pra buscar cidades do mundo todo, ver os dados de cada uma e salvar as favoritas na sua conta.

## API utilizada

Open-Meteo Geocoding API - https://geocoding-api.open-meteo.com/v1/

Usamos duas rotas dela:
- `search?name=...` pra buscar as cidades pelo nome (lista)
- `get?id=...` pra pegar os dados de uma cidade pelo id (tela de detalhe)

Nao precisa de cadastro nem de chave.

## Prints

| Login | Cadastro | Lista |
|-------|----------|-------|
| ![login](prints/login.png) | ![cadastro](prints/cadastro.png) | ![lista](prints/lista.png) |

| Detalhe | Favoritas | Erro (modo aviao) |
|---------|-----------|-------------------|
| ![detalhe](prints/detalhe.png) | ![favoritas](prints/favoritas.png) | ![erro](prints/erro.png) |

## Protótipo no Figma

**[Abrir o protótipo visual do aplicativo Cidades no Figma](https://www.figma.com/design/rxn8PTYtuaUo4qL3lDsNg7)**

Protótipo editável desenvolvido para documentar o fluxo do aplicativo Android em Kotlin/Jetpack Compose e os requisitos do projeto final. Contém oito telas/estados:

1. Login;
2. Cadastro;
3. Busca de cidades com lista de resultados;
4. Detalhes de uma cidade e opção de favoritar;
5. Cidades favoritas;
6. Perfil com botão Sair;
7. Erro de conexão;
8. Carregamento dos dados.

O desenho utiliza a linguagem Material 3, inclui os três destinos da navegação inferior (Buscar, Favoritas e Perfil) e elementos reutilizáveis. **Trata-se de um protótipo visual editável, ainda sem ligações clicáveis entre as telas**; o fluxo funcional está implementado no aplicativo Android.

**Contribuição:** José Augusto — [corexemp-spec](https://github.com/corexemp-spec), prototipação de telas e validação dos requisitos.

## Divisao de tarefas

| Integrante | O que fez |
|------------|-----------|
| Bernardo Iannini | Codigo do app: network, data (Room, usuarios, favoritas, Repository), viewmodel e telas/navegacao |
| Jose Augusto | [Protótipo visual editável no Figma](https://www.figma.com/design/rxn8PTYtuaUo4qL3lDsNg7), fluxo das telas e [análise/validação técnica](VALIDACAO_PROJETO.md) |

## O que aprendemos

- Bernardo Iannini: aprendi a juntar Retrofit, Room e navegacao num app so, deixando cada coisa no seu pacote.
- Jose Augusto: aprendi a planejar as telas e o fluxo do app antes de comecar a programar.

## Como rodar

1. Abrir a pasta do projeto no Android Studio
2. Esperar o gradle sincronizar
3. Rodar no emulador ou no celular (precisa de internet pra buscar as cidades)

## Organizacao

```
com.bernardojose.cidades
 ├─ network        CidadeApi (retrofit) e RetrofitInstancia
 ├─ data           Cidade, Usuario, Favorita, os Dao, BancoDados, CidadeRepository e o sha256
 ├─ viewmodel      CidadesViewModel e os estados (EstadoBusca, EstadoDetalhe)
 └─ userinterface  MainActivity, rotas, navegacao e as telas
```
