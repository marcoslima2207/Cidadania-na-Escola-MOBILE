# Cidadania na Escola — Mobile

Protótipo visual Android em **Java**, com layouts XML, baseado na imagem de referência fornecida. Azul-escuro, verde e amarelo compõem a identidade visual; as telas utilizam componentes Material e ilustrações vetoriais locais.

## Abrir e visualizar

1. Abra esta pasta no Android Studio e sincronize o Gradle.
2. Execute o módulo `app` em um emulador ou aparelho com Android 8.0 (API 26) ou superior.
3. Na abertura, toque em **Explorar sem entrar** para percorrer as telas sem login.

O projeto mantém a configuração original de SDK 37, Gradle 9.6.0 e Java 25 para execução do Gradle. O código do aplicativo utiliza a compatibilidade Java 11 configurada no módulo.

## Telas

- Abertura, login, cadastro e recuperação de senha.
- Início com atalhos e conteúdo em destaque.
- Explorar com busca visual, categorias e cartões educativos.
- Detalhes de cidadania, democracia, direitos trabalhistas, desinformação e checagem.
- Quiz com exemplo fixo de uma pergunta respondida.
- Perfil, meus conteúdos, curtidas, comentários e configurações.
- Publicações, formulário de nova publicação, materiais educativos e sobre o projeto.

## Escopo desta versão

Somente a **navegação entre telas** está conectada. Não há autenticação, integração com API, banco de dados, permissões de rede, pesquisa, filtros, pontuação, upload, download ou persistência. Os dados de João Silva, publicações, contagens e coleções são ilustrativos. Os botões de envio ficam desativados para não simular uma operação concluída. Os campos são apenas elementos de apresentação e não enviam informações.

## Organização para edição

- `MainActivity.java`: navegação, cabeçalho, menu inferior e tratamento das áreas do sistema/teclado.
- `ScreenFragment.java`: ligações visuais e composição das telas complementares.
- `ContentSamples.java`: conteúdo demonstrativo local, separado da navegação.
- `res/layout/screen_*.xml`: layouts principais, editáveis no Android Studio.
- `res/values/colors.xml` e `themes.xml`: cores, tipografia e estilos.
- `res/drawable`: ícones e ilustrações vetoriais sem dependência de downloads.

Os argumentos dos fragments e a pilha de navegação são preservados pelo Android na recriação da Activity. Formulários não têm salvamento de dados implementado.

## Verificação

```powershell
./gradlew.bat assembleDebug lintDebug
```

APK debug gerado em `app/build/outputs/apk/debug/app-debug.apk`. Compilação e lint concluídos sem erros. As prévias capturadas no emulador de 360 × 640 dp estão em [`docs/previews`](docs/previews).

| Abertura | Login | Início | Conteúdos | Quiz | Perfil |
| --- | --- | --- | --- | --- | --- |
| [Ver](docs/previews/01-abertura.png) | [Ver](docs/previews/02-login.png) | [Ver](docs/previews/03-inicio.png) | [Ver](docs/previews/04-conteudos.png) | [Ver](docs/previews/05-quiz.png) | [Ver](docs/previews/06-perfil.png) |

Referências do projeto: [Mobile](https://github.com/marcoslima2207/Cidadania-na-Escola-MOBILE) e [site principal](https://github.com/marcoslima2207/Cidadania-na-Escola).
