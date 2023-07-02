## Dados de aluno

Nome: Bruno Mateus <br />
Número: a22102554 <br />


## Link video:
  

## Ecrãs:

### Dashboard
-------------
<img src="img/dashbord.png" height="50%" width="50%">

### Registo
<img src="img/registoFilme1.png" height="50%" width="50%">
<img src="img/registoFilme2.png" height="50%" width="50%">
<img src="img/registoFilme2.png" height="50%" width="50%">
<img src="img/erroCamposObg.png" height="50%" width="50%">
<img src="img/autoCompleteCinema.png" height="50%" width="50%">
<img src="img/popupData.png" height="50%" width="50%">
<img src="img/popupData.png" height="50%" width="50%">

### Lista Filmes
<img src="img/listaFilmes.png" height="50%" width="50%">
<img src="img/rodarEcra.png" height="50%" width="50%">

### Detalhe do Filme
<img src="img/detalheFilme1.png" height="50%" width="50%">
<img src="img/detalheFilme2.png" height="50%" width="50%">

### Mapa
<img src="img/mapa.png" height="50%" width="50%">

### Pesquisa de voz
<img src="img/voice1.png" height="50%" width="50%">
<img src="img/voice2.png" height="50%" width="50%">

## Critérios

### Parte I

| Critério                                           | Funcionalidades                                                                                                                                                                                                                                                           |
|----------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Dashboard                                          | Existe um ecrã dashboard que permite viualizar o numero de filmes já vistos e um top 5 dos filmes melhor classificados                                                                                                                                                    |
| Apresentação dos filmes -Lista                     | Existe um ecrã onde é representado a lista dos Filmes visualizados                                                                                                                                                                                                        |
| Apresentação dos filmes - Lista - Rotação          | É possivel rodar o ecrá desse mesmo ecrã de lista, onde acrescenta as observações de cada filme                                                                                                                                                                           |
| Apresentação dos filmes - Mapa (imagem)            | Existe um ecrã dedicado ao Mapa, onde aparece uma imagem de um mapa dos cinemas de Lisboas                                                                                                                                                                                |
| Detalhe do filme (sem fotografias)                 | É possivel verificar o detalhe de cada filme na lista, ao selecionar o mesmo irá apresentar um ecrã "Detalhe" onde representa toda a informação solicitada em enunciado de cada filme                                                                                     |
| Detalhe do filme (apenas a parte das fotografias)  | No Ecrã é tambem possivel visualizar as fotografias inseridas no registo                                                                                                                                                                                                  |
| Pesquisa de filmes por voz                         | Existe um botão com um icon de um microfone na barra superior onde ao selecionar abre uma pop-up com uma contagem de 10segundos conforme solicitado em enunciado                                                                                                          |
| Registo de filmes (sem fotografias)                | Existe um ecrã para efectuar o registo de cada filme com todos os dados pedidos em enunciado                                                                                                                                                                              |
| Registo de filmes (apenas a parte das fotografias) | Nesse mesmo ecrã é possivel registar tambem as fotografias tiradas através da camera                                                                                                                                                                                      |
| Suporte multi-idioma                               | Existe tambem suporte de multi-idioma, para as linguagens: Portugues, Frances e Ingles                                                                                                                                                                                    |
| Navegabilidade                                     | A navegação entre ecrãs é feita através de drawer                                                                                                                                                                                                                         |


### Parte II + Recurso

| Critério                                             | Funcionalidades                                                                                                                                                                                                                                                           |
|------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Regista Já                                           | É possivel ir para o ecrã de registo de uma avaliação apenas agitando o smartphone |
| Registo de filmes                                    | É possivel registar uma avaliação de um filme caso exista acesso à internet |
| Inserir corretamente na base de dados                | Os registos na base de dados são inseridos corretamente |
| Validação e obtenção dos dados do filme via API      | Os dados dos filmes são obtidos via API e guardados na base de dados local |
| Validação e obtenção dos dados do cinema via JSON    | Os dados dos cinemas são obtidos via JSON e guardados na base de dados local |
| Utilização de geo-localização                        | No fragmento Mapa é utilizada a geolocalização |
| Inserir fotografias na base de dados                 | As fotografias referentes à avaliação são guardadas na base de dados com id da avaliação |
| Apresentação dos filmes -Lista                       | Os filmes são apresentados em forma de lista |
| Apresentação dos filmes - Mapa                       | Os filmes são apresentados no mapa através de markers |
| Filtros - Apresentação em Lista                      | É possivel filtrar os filmes por pesquisa do nome do filme ou até mesmo pela proximidade de 500 ou 1000 metros, ainda é possivel filtrar os filtros em conjunto com a ordenação |
| Ordenação - Apresentação em Lista                    | É possivel ordenar os filmes pela ordem crescente ou decrescente e tambem é possivel combinar com os filtros |
| Detalhe do filme (sem fotografias)                   | O filme é apresentado sem fotografias |
| Detalhe do filme (apenas a parte das fotografias)    | O filme apresenta fotografias inseridas num recycler view |
| Pesquisa de filmes por voz - Funcionalidade Avançada | É possível procurar um filme que tenha sido avaliado por voz |
| Dashboard                                            | O ecrã dashboard que permite viualizar o numero de filmes já vistos e um top 5 dos filmes melhor classificados |
| Funcionamento Offline - Funcionalidade Avançada      | A aplicação funciona de modo offline apenas para visualização de avaliações já inseridas |
| Video                                                |  |

### Autoavaliação = 17

## Classes de Lógica de Negócio 

#### Classe Avaliacao:
- Atributos: <br />
-- id - String <br />
-- filme - Filme <br />
-- cinema - Cinema <br />
-- avaliacao - Int <br />
-- dataVisualizacao - Long <br />
-- fotos - List<File>? <br />
-- observacoes - String <br />

#### Classe Filme:
- Atributos: <br />
-- id - String <br />
-- nome - String <br />
-- generoImbd - String <br />
-- dataImbd - Long <br />
-- avaliacaoImbd - String <br />
-- imgImbd - String <br />
-- sinopse - String? <br />

#### Classe Cinema:
- Atributos: <br />
-- cinema_id - Int <br />
-- cinema_name - String <br />
-- latitude - Double <br />
-- longitud - Double <br />
-- morada - String <br />
-- localidade - String <br />
  
  #### Classe abstrata Operações:
- Métodos: <br />
- Filmes: <br />
-- getAllFilmes(onFinished: (Result<List<Avaliacao>>) -> Unit) <br />
-- inserirFilme(filme: Filme, avaliacao: Avaliacao, onFinished: () -> Unit) <br />
-- getFilmeIMDB(nome: String, onFinished: (Result<Filme>) -> Unit) <br />
-- getFilme(id: String, onFinished: (Result<Filme>) -> Unit) <br />
-- verificarFilme(nome: String, onFinished: (Int) -> Unit) <br />
- Avaliações: <br />
-- getAllAvaliacoes(onFinished: (Result<List<Avaliacao>>) -> Unit) <br />
-- getAvaliacao(id : String, onFinished: (Result<Avaliacao>) -> Unit) <br />
-- inserirAvaliacao(filme: Filme, avaliacao: Avaliacao, onFinished: (Result<Filme>) -> Unit) <br />
-- getAvaliacaoIdFromFilmeName(nome: String, onFinished: (Result<String>) -> Unit) <br />
-- inserirFotosAvaliacao(fotos: List<File>, idAvaliacao: String, onFinished: () -> Unit) <br />
-- getAllFotosFromAvaliacao(id: String, onFinished: (Result<List<File>>) -> Unit) <br />
-- getAvaliacaoCheckCinema(idCinema: Int, onFinished: (Result<Int>) -> Unit) <br />
-- countAvaliacoes(onFinished: (Result<Int>) -> Unit) <br />
-- top5Avaliacoes(onFinished: (Result<List<Avaliacao>>) -> Unit) <br />
- Cinemas: <br />
-- getCinemasJSON(onFinished: (Result<List<Cinema>>) -> Unit) <br />
-- inserirCinemas(cinemas : List<Cinema>, onFinished: () -> Unit) <br />
-- getCinemaByNome(cinema : String, onFinished: (Result<Cinema>) -> Unit) <br />
-- getCinemaById(idCinema : Int, onFinished: (Result<Cinema>) -> Unit) <br />
-- verificarCinema(nome : String, onFinished: (Int) -> Unit) <br />
-- getAllCinemasNomes(onFinished: (Result<List<String>>) -> Unit) <br />
-- clearAllCinemas(onFinished: () -> Unit) <br />
  

## Idioma gerado pelo Chat GPT assim como os screenshots das prompts:

<img src="img/inglesFrances1.png" height="50%" width="50%">
<img src="img/inglesFrances2.png" height="50%" width="50%">


## Fontes de informação:
- Chat GPT
- stackoverflow
- youtube


