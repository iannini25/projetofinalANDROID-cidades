# Validação do Projeto Final Android — Cidades

**Data da revisão:** 29/10/2026  
**Registro da revisão:** José Augusto — [corexemp-spec](https://github.com/corexemp-spec) (`corexemp@gmail.com`)  
**Base de comparação:** Atividade Projeto Final — Desenvolvimento Mobile Android, 3ª série, 3ª etapa (4,0 pontos).  
**Escopo:** análise estática do código e dos arquivos da branch `main`. **Não foram realizados build, execução em emulador nem testes instrumentados nesta revisão.**

## Resultado

O repositório apresenta a estrutura e os componentes necessários para os cinco critérios técnicos da atividade. **O funcionamento completo ainda depende da execução dos testes previstos pelo professor.** A participação da equipe ainda precisa de comprovação por commits de todos os integrantes.

| Requisito da atividade | Evidência localizada | Situação |
| --- | --- | --- |
| API REST com Retrofit e coroutines (0,7) | `network/CidadeApi.kt`, `network/RetrofitInstancia.kt`, `data/CidadeRepository.kt` e `viewmodel/CidadesViewModel.kt` | Implementado no código; chamada real e estados de erro precisam de teste no dispositivo |
| Login, cadastro, sessão e senha com hash (0,7) | `data/Usuario.kt`, `data/UsuarioDao.kt`, `data/Hash.kt`, `data/CidadeRepository.kt`, `userinterface/TelaLogin.kt` e `userinterface/TelaCadastro.kt` | Implementado no código; persistência da sessão precisa de teste |
| Persistência local com Room e dados por usuário (0,7) | `data/BancoDados.kt`, `data/Favorita.kt`, `data/FavoritaDao.kt` e consultas filtradas por `usuarioId` | Implementado no código; persistência e isolamento precisam de teste |
| Navigation Compose, 3 abas e detalhe type-safe (0,7) | `userinterface/TelaPainel.kt`, `userinterface/Rotas.kt` e `userinterface/TelaDetalhe.kt` | Implementado no código; navegação precisa de teste |
| Organização em pacotes (0,7) | Pacotes `network`, `data`, `viewmodel` e `userinterface`; acesso à API e aos DAOs concentrado no Repository | Conforme na inspeção estática |
| Processo em equipe (0,5) | `README.md` com divisão e aprendizado dos participantes; quatro commits iniciais atribuídos a Bernardo Iannini | **Pendente:** commits de todos os integrantes e formulários individuais |

## Verificações realizadas

- A Open-Meteo Geocoding API foi configurada com URL base `https://geocoding-api.open-meteo.com/v1/`, rotas `search` e `get`, sem chave.
- `CidadeApi` usa métodos `suspend`. O `CidadesViewModel` chama o Repository em `viewModelScope.launch` e representa os estados de início, carregamento, sucesso e erro da pesquisa.
- O cadastro valida formato de e-mail, senha com pelo menos seis caracteres e e-mail único; o login não revela qual credencial está incorreta.
- O armazenamento de senha usa SHA-256, conforme o exemplo fornecido pela atividade.
- A sessão usa `SharedPreferences` e a tela inicial consulta o identificador armazenado. O botão Sair limpa essa sessão.
- As cidades favoritas são armazenadas no Room e consultadas pelo identificador do usuário.
- A barra inferior contém **Buscar, Favoritas e Perfil**. A rota serializável `Rota.Detalhe(cidadeId)` transmite o identificador da cidade.
- O `AndroidManifest.xml` declara a permissão de internet e a `MainActivity` no pacote esperado pelo `namespace`.
- O README possui a API utilizada, seis imagens de telas, divisão de tarefas e uma frase de aprendizado de cada integrante.

## Pontos de atenção antes da entrega

1. **Build e emulador:** compilar e executar no Android Studio. Verificar especialmente a disponibilidade dos ícones Compose (`Search`, `Favorite`, `Person`) utilizados nas telas e a necessidade de declarar `material-icons-extended`, se a compilação acusar referência não resolvida.
2. **Favoritos duplicados:** a entidade `Favorita` usa uma chave primária gerada automaticamente, mas não possui uma restrição única para o par `(usuarioId, cidadeId)`. Evitar entradas repetidas em operações concorrentes/toques rápidos.
3. **Troca de usuário:** verificar se a lista de favoritas não mostra temporariamente os dados do usuário anterior enquanto o fluxo de dados do novo usuário é atualizado; limpar o estado visual na troca de conta, se necessário.
4. **Protótipo:** o README registra que José Augusto criou o protótipo no Figma, mas não inclui link ou evidência do protótipo. Incluir o link ou material correspondente, se disponível.
5. **Histórico de equipe:** garantir contribuições reais e commits dos dois integrantes; cada um também deve preencher individualmente o formulário da página 6 da atividade.

## Checklist de teste manual (ainda não executado nesta revisão)

- [ ] Abrir o aplicativo no emulador sem falhas de compilação ou inicialização.
- [ ] Cadastrar uma conta com e-mail válido e senha de seis ou mais caracteres.
- [ ] Conferir erros de e-mail inválido, senha curta, cadastro duplicado e login incorreto.
- [ ] Pesquisar uma cidade, abrir os detalhes e verificar se o identificador da rota corresponde à cidade selecionada.
- [ ] Ativar o modo avião e verificar o estado de erro da busca.
- [ ] Salvar uma favorita, fechar/reabrir o aplicativo e verificar sessão e dados persistidos.
- [ ] Sair, entrar com outra conta e confirmar que os favoritos da primeira não aparecem.
- [ ] Conferir no Database Inspector que a senha foi salva como hash e que os registros pertencem ao usuário correto.
- [ ] Verificar o README e a participação de todos no histórico de commits.
- [ ] Preencher o feedback individual de cada integrante.

**Conclusão:** a análise estática identificou implementação dos requisitos técnicos centrais e boa separação de responsabilidades. Não corresponde a uma homologação funcional ou à garantia de nota integral; a validação final exige os testes acima.
