## Dados de aluno

Nome: Bruno Mateus <br />
Número: a22102554 <br />
Nome: Diogo Moreira <br />
Número: a22103941 <br />

## Filmes hardcoded:
John Wick 4 <br />
Avatar 2 <br />
Shazam <br />
Homem-Aranha 3 <br />

| Critério                                           | Funcionalidades                                                                                                                                                                       |
|----------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Dashboard                                          | Existe um ecrã dashboard que permite viualizar o numero de filmes já vistos e um top 5 dos filmes melhor classificados                                                                |
| Apresentação dos filmes -Lista                     | Existe um ecrã onde é representado a lista dos Filmes visualizados                                                                                                                    |
| Apresentação dos filmes - Lista - Rotação          | É possivel rodar o ecrá desse mesmo ecrã de lista, onde acrescenta as observações de cada filme                                                                                       |
| Apresentação dos filmes - Mapa (imagem)            | Existe um ecrã dedicado ao Mapa, onde aparece uma imagem de um mapa dos cinemas de Lisboas                                                                                            |
| Detalhe do filme (sem fotografias)                 | É possivel verificar o detalhe de cada filme na lista, ao selecionar o mesmo irá apresentar um ecrã "Detalhe" onde representa toda a informação solicitada em enunciado de cada filme |
| Detalhe do filme (apenas a parte das fotografias)  | No Ecrã é tambem possivel visualizar as fotografias inseridas no registo                                                                                                              |
| Pesquisa de filmes por voz                         | Existe um botão com um icon de um microfone na barra superior onde ao selecionar abre uma pop-up com uma contagem de 10segundos conforme solicitado em enunciado                      |
| Registo de filmes (sem fotografias)                | Existe um ecrã para efectuar o registo de cada filme com todos os dados pedidos em enunciado                                                                                          |
| Registo de filmes (apenas a parte das fotografias) | Nesse mesmo ecrã é possivel registar tambem as fotografias tiradas através da camera                                                                                                  |
| Suporte multi-idioma                               | Existe tambem suporte de multi-idioma, para as linguagens: Portugues, Frances e Ingles                                                                                                |
| Navegabilidade                                     | A navegação entre ecrãs é feita através de drawer                                                                                                                                     |

### Autoavaliação = 17

## Classes de Lógica de Negócio 

#### Classe Filme:
- Atributos: <br />
-- nome - String <br />
-- cinema - String <br />
-- avaliacao - Int <br />
-- dataVisualizacao - Calendar <br />
-- fotos - List<File> <br />
-- observacoes - String <br />
-- listImgGet - List<File> <br />
- Métodos: <br />
-- listImgGet() : List<File> <br />

#### Classe Filmes:
- Atributos: <br />
-- listImg - mutableListOf<File> <br />
-- listImgGet - List <br />
-- history - mutableListOf<Filme> <br />
- Métodos: <br />
-- listImgGet() : List<File> <br />
-- imagemSet(imgFile : File) <br />
-- imagensListClear() <br />
-- history() : List<Filme> <br />
-- historySet (nome:String, cinema:String, avaliacao:Int, data: Calendar, imgList: List<File>, obs:String):Int <br />
-- top5Filmes() : List<Filme> <br />
-- countFilmes() : Int <br />

#### Classe Cinema:
- Atributos: <br />
-- cinemaName - String <br />

#### Classe Cinemas:
- Atributos: <br />
-- cinema1 - Cinema <br />
-- cinema2 - Cinema <br />
-- cinema3 - Cinema <br />
-- cinema4 - Cinema <br />
-- cinema5 - Cinema <br />
-- cinema6 - Cinema <br />
-- cinema7 - Cinema <br />
-- cinema8 - Cinema <br />
-- cinema9 - Cinema <br />
-- cinema10 - Cinema <br />
-- cinema11 - Cinema <br />
-- listCinemas - mutableListOf<Cinema> <br />
-- getListCinemas - List<Cinema> <br />
- Métodos: <br />
-- getListCinemas : List<Cinema> <br />
-- nomesCinemasGet : List<String> <br />

#### Classe FilmeIMDB:
- Atributos: <br />
-- nomeImdb - String <br />
-- generoImdb - String <br />
-- dataImdb - String <br />
-- avaliacaoImdb - String <br />
-- imgImdb - String <br />
-- sinopse - String <br />

#### Classe FilmesIMDB:
- Atributos: <br />
-- filme1 - FilmeIMDB <br />
-- filme2 - FilmeIMDB <br />
-- filme3 - FilmeIMDB <br />
-- filme4 - FilmeIMDB <br />
-- listFilmesImdb - mutableListOf<FilmeIMDB> <br />
-- getListFilmesImdb - List<FilmeIMDB> <br />
- Métodos: <br />
-- getListFilmesImdb : List<FilmeIMDB> <br />
-- nomesFilmesGet : List<String> <br />

## Idioma gerado pelo Chat GPT assim como os screenshots das prompts:

- Inglês:
<img src="img/ingles1.png" height="50%" width="50%">
<img src="img/ingles2.png" height="50%" width="50%">
- <img src="img/ingles3.png" height="50%" width="50%">
- Francês:
<img src="img/frances1.png" height="50%" width="50%">
<img src="img/frances2.png" height="50%" width="50%">
- <img src="img/frances3.png" height="50%" width="50%">

## Fontes de informação:
- Chat GPT
- stackoverflow
- youtube


