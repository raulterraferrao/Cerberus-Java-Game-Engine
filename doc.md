# 30/03/2017 #
## Configurando LWJGL ###
Configuramos o nosso ambiente de desenvolvimento, baixamos o Slick Utils (slick.ninjacave.com/slick-util.jar) e o LightWeight Java Game Library (https://sourceforge.net/projects/java-game-lib/files/Official%20Releases##/LWJGL%202.9.1/lwjgl-2.9.1.zip/download),criamos uma pasta chamada lib e colocamos os arquivos lwjgl_util.jar e o lwjgl dentro de uma pasta que criamos dentro da lib chamada jars, também colocamos o arquivo que baixamos o slickutil.jar tbm nessa mesma pasta, e criamos outra pasta chamada natives e colocamos os arquivos natives do lwjgl que baixamos dentro dessa mesma pasta. Após isto, inicamos o eclipse e colocamos a pasta lib dentro do nosso projeto, clicamos em nosso projeto com o botão direito do mouse e clicamos em buildpath e configurar buildpath. Clicamos no botão add Jars e adicionamos todos os jars da pasta jar e depois clicamos em Library e clicamos em natives e incluimos todos os natives do nossos respectivo SO que está dentro da pasta native que criamos.

## Display ##
**renderizador**
1. *GerenciadorDeJanelas*: Criamos 3 métodos básicos o criar,atualizar e destruir. 
**executador**
1. *Main*: Inicia o metodo criar do  *GerenciadorDeJanelas* e criamos um while no qual ficará responsável em atualizar a nossa engine passo por passo. 

## VAOs e VBOs ##
**renderizador**
1. *Objeto*: Classe que contém um objeto do nosso game,contém o ID do vao que ele pertence e a quantidade de vertices que contém o objeto. 
2. *CarregadorDeObjetos*: A função principal dessa classe é receber as posições dos vertices ela cria um VAO, carrega os vertices no VBO e conecta esse VBO com o VAO em uma posição determinada e após criado retorna o Objeto objeto.
3. *Renderizador*: Limpa tudo que há no frame anterior renderizado,cria um background e Recebe o objeto ,utiliza o seu VaoID e quantidade de vertices para renderizar na tela.
 
**executador**
1. *Main*: Criamos dois Objetos das classes *CarregadorDeObjetos* e *Renderizador* e criamos um vetor que é um quadrado com todos os seus vertices. Carregamos um obj no VAO e depois dentro do while renderizamos ele , após fechar a janela liberamos todos os VAOs e VBOs da memória.

## Renderizar com Buffer de Indices ##
**renderizador**
1. *CarregadorDeObjetos*: Agora começamos a usar um novo VBO que contém os indices de cada vertice como por exemplo ao invez de repetir as posições dos vertices x,y e z da seguinte maneira (v0,v1,v3,v3,v1,v2) nós enviamos somente vertices não repetidos(v0,v1,v2,v3) e os indices de como a gpu ira renderizar(0,1,3,3,1,2) assim diminuindo a quantidade de dados que utilizaremos em nosso programa.
3. *Renderizador*: Modificamos o metodo glDrawArrays para glDrawElements com isso agora o nosso VBO de Indice funciona.
 
**executador**
1. *Main*: Não é necessário mais repetir as posições dos vertices repetidos, precisamos apenas colocar os não repetidos e adicionamos um vetor de inteiros de indices no qual diz a gpu qual vertice está conectado com qual.

## Colorindo Usando os Shaders ##
**shaders**
1. *vertextShader* : Utilizando o GLSL(OpenGL Shading Language) criamos o arquivo *vertexShader* e *fragmentShader* que lida com a coloração dos vertices e os pixels entre eles. O *vertexShader* recebe como entrada as posições dos vertices, o gl_Position() dita onde os vertices irão ser renderizados e o output do *vertexShader* são os vertices para o *fragmentShader* após algum calculo necessário.
2. *fragmentShader*: Recebe como entrada a saida do *vertexShader* faz alguns calculos e retorna uma saida. O *fragmentShader* é responsavel por colorir os pixels entre os vertices e ele faz um calculo de acordo com o valor de cada pixel e sua distancia para saber qual será o valor de cada pixel.
3. *ProgramaShaderPadrao*: é uma classe abstrata que retrata o que todo Shader precisa ter,vertexShaderID,fragmentShaderID,programShaderID. Ele pega o caminho dos shaders,carrega-os e conecta-os no programa
4. *StaticShader*: É uma extensão do *ProgramaShaderPadrao* e que contém os caminhos dos vertex e fragment shaders e também conecta os shaders com o VAO. 
 
**executador**
1. *Main*: Criamos o StaticShader e antes de renderizar nos criamos o Programa de shaders e após renderizar nós Fechamos o Programa , lembrando também que ao sair do While damos o FreePrograma para liberar os dados da memória

# 02/04/2017 #
## Texturas ##
**texturas**
1. *ModeloDeTextura* : Classe que contém todas as variáveis que contém um objeto que contém textura, ainda está muito simples e contém somente o ID da textura que estamos usando. 
**objetos**
1. *Objeto* : Movemos a classe *Objeto* do pacote **rendizador** para o pacote **objetos**
2. *ObjetoComTextura* : Contém o *Objeto* e o *ModeloDeTextura* .
**shaders**
1.*StaticShader* : Incluímos no método conectarAtributos o ID do VAO onde as coordenadas estão, no caso 1 com o in do vertexShader.
2.*vertexShader* : Recebeu uma nova in chamda coordenadasDaTextura e passa diretamente para o out , para que o fragmentShader possa lidar com a textura.
3.*fragmentShader*: Adicionamos o uniform sampler2D texturaSample a textura que ativamos no glActiveTexture no renderizador e utilizamos a função texture para colocar a textura dentro dos vertices que passamos.
**renderizador**
1.*CarregadorDeObjetos* : Criei o metodo carregarTextura no qual passa como argumento o nome do arquivo png e retorna um id que a textura terá. Também damos free na textura e criamos a lista para colocar ela dentro para dar free depois.
2.*Renderizador* : Passamos agora como argumento no renderizar um obj com textura, e damos bind glEnableVertexAttribArray na posição onde a textura esta no VAO, devemos tbm ativar a textura glBindTexture e dar bind glBindTexture e ao final dar glDisableVertexAttribArray na posição respectiva da nossa textura.
**executador**
1.*Main* : Agora passamos mais um argumento na carregarParaVAO que é a textura, chamamos o método ModeloDeTextura que carrega a textura e retorna um o id dela, após isso criamos um objcomtextura e criamos um objeto  ObjetoComTextura que contém o id do Objeto e o id da Textura. Após isso renderizamos esse objeto na classe rendizador.

## Variaveis uniformes e Matrizes de Transformação ##
**shaders**
1.*ProgramaShaderPadrao* : getLocalidadeUniform retorna onde está o uniform que iremos conectar entre o programa java e o shader. Utiliza-se getLocalidadeDeTodosUniform para pegar todas as localidades dos uniforms que temos no shader.Criamos tbm métodos com o carregarFloat para carregar as variaveis que está no progama java para o shader onde nós pegamos a sua localidade no getLocalidadeUniform.
2.*StaticShader* : Pegamos a localidadae getLocalidadeDeTodosUniform e depois carregamos a matriz na variavel uniform do shader no carregarMatrizDeTransformacao.
3.*VertexShader* : pegamos o uniform mat4 matrizDeTransformacao e multiplicamos pelas posições dos vetores para transformalos.
**ferramentas**
1.*Matematica* : Criamos um método criarMatrizDeTransformacao para criar a matriz de transformação e setar os seus valores e retornar a matriz criada.

## Entidades,Matriz de Visualizacao e de Projecao ##
**entidades**
1.*Entidades* : Classe responsável em criar as entidades no game, ela contém o modelo do objeto e as suas respectivas posições,rotação e escala.
2.*Camera* : Classe que contém um objeto camera e que o move de acordo com o WASD tem os atributos pitch,yaw e roll.
**ferramentas**
1.*Matematica* : Criamos o metodo criarMatrizDeVisualizacao que é responsável por movimentar o "mundo" de acordo com a movimentação da camera só que negativamente, ou seja, se a camera moveu para esquerda to do mundo fará o contrário e moverá para a direita.
**renderizador**
1.*Renderizador* : Criamos um construtor que iniciará a matriz de projeção logo que criamos o renderizador ja que ela é necessária somente uma vez ao iniciar o programa e não iremos mais utiliza-la, por isso ela é a primeira a multiplicar no *vertexShader*.
**shaders**
1.*StaticShader*: Pegamos a localidade da matriz de projeção e visualização e carregamos no shader.
2.*vertexShader* : recebe as matrizes uniforms e multiplica na gl_position.
**executador**
1.*Main* : Agora criamos um cubo ao invés de um quadrado modificando os vertices,indices e coordenadas de textura. Colocamos o renderizador para inicar apos o shader já que agora nós necessitamos do shader para iniciar a matriz de projeção logo no inicio quando o renderizador é criado, criamos o obj entidade e a camera.

Obs:A matriz de projeção é carregada no construtor do renderizador, a matriz de Visualização é carregada antes de chamar o metodo renderizar no Main e ela é criada dentro do método que carrega ela a matriz de transformação é chamada dentro do metodo renderizar.

## Importar Objetos Do Blender ##

## Entrada de Teclado e Mouse ##
**entradas**
1.*MeuMouse* : Classe responsável em detectar ações relacionadas ao uso do mouse, como qual botão o usuário esta clicando e onde está a posição do mouse na tela. Temos também metodos para saber se o botão foi pressionado, esta sendo pressionado  ou foi solto.
2.*MeuTeclado* : Assim como o mouse essa Classe é responsável em detectar o acionamento de teclas no Teclado.
**estruturasDeDaDos**
1.*Vector2f* : Classe responsável em lidar com vetores de float de duas dimensões, usadas em posicionamento 2D, como em Texturas, e Tela.
**renderizador**
1.*GerenciadorDeJanela* : Adicionamos a inicialização do Mouse e Teclado de LWJGL e seu destroy().
**executador**
1.*Main* : Colocamos uns SYSout só para testar o mouse e o teclado.

## Criação Das Estruturas De Dados que Usaremos em Nossa Engine ##

**estruturasDeDaDos**
1.*Vetor2f* : Classe responsável em lidar com vetores de float de duas dimensões, usadas em posicionamento 2D, como em Texturas, e Tela.
2.*Vetor3f* : Classe responsável em lidar com vetores de float de duas dimensões, usadas em posicionamento 3D, como posicionamento.
3.*Matriz4f* : Classe responsável em lidar com matrizes de transformação, utilizada para escalar,rotacionar e transladar um objeto, podendo tbm ser utilizada como movimentação de camera e matriz de projeção.
4.*Quaternio* : Quaternio é responsável em rotacionar um vetor ... muito complexo ainda, não sei definir muito bem o seu uso.

## Modificações necessárias para introduzir as estruturas de dados ##

Nessa atualização destruimos o package **matematica** e criamos um novo chamado **matrizesDeTransformacao** , nele está contido as matrizes de transformação, projeção e visualização. Agora também, não estou mais utilizando a biblioteca Vector nem a matrix4f da LWJGL. Estamos utilizando as nossas que criamos anteriormente.

## Luminosidade Difusa ##

**luminosidade**
1.*Difusa* : Classe responsável em criar um objeto do tipo Luminosidade que contém um vetor3f de posicao e outro de cor.
**objetos**
1.*CarregarObjeto* : No return do construtor colocamos as normais gerenciador.carregarParaVAO(vetorDeVertices, vetorDeTexturas,vetorDeNormais, vetorDeIndices);
**renderizador**
1.*GerenciadorDeObjetos* : Foi adicionado float[] normal no carregarParaVAO e carregada do VBO para o VAO
2.*Renderizador* : Aqui damos Enable no VAO da posição da Normal que é o 3 e depois de usa-lo damos desable.
**shaders**
1.*StaticShader* : Criamos variaves de localidade para a posicao da luz e sua cor que usaremos como uniform dentro do vertexshader e fragmentshader. Conectamos o in normais com o VAO 3 (NORMAL)
2.*vertexShader* : Criamos a variaves pos_mundo no qual diz em que posição real o objeto está após ter sido feito a transformação e criamos duas variaves de saida para o fragmentshader que é a normalDaSuperficie e o vetorPraLuz.
3.*fragmentShader* : Recebemos as normais da superficie o vetor da luz e os normalizamos para que quando fizermos o produto (dot) o tamanho não afete o resultado. Após fazer o dot nós limitamos para ser >=0 e damos como saida a cor da textura * vec4(cor_brilho)

## Luminosidade Especular ##

**luminosidade**
1.*Especular* : Classe responsável em criar as variáveis que farão com que o objeto tenha reflexo.
**objetos**
1.*CarregarObjeto* : No return do construtor colocamos as normais gerenciador.carregarParaVAO(vetorDeVertices, vetorDeTexturas,vetorDeNormais, vetorDeIndices);
**renderizador**
1.*Renderizador* : Carrega no shader a luminosidade Especular.
**shaders**
1.*StaticShader* : Criamos variaves de localidade para a Superficie Reflexiva e sua reflexividade que usaremos como uniform dentro do vertexshader e fragmentshader. E criamos a função para carregar a luminosidade especular para dentro do entro do vertexshader e fragmentshader
2.*vertexShader* : Criamos o vetorParaCamera que necessitaremos para dizer se a luz que reflete está perto de onde a camara está posicionada.
3.*fragmentShader* : Recebemos as variaveis uniforms SuperficieReflexiva e reflexividade , normalizamos o vetorParaCamera e criamos o vetorDaLuzNormalizada que é o inverso do vetorParaLuzNormalizada. Agora sabendo onde aponta o vetor para a camera e onde aponta o vetorDaLuz usamos a função de reflect do glsl para refletir o vetor que vem da luz na superficie ja que sabe qual é a sua normal. Após isso verificamos se a luz refletida está proximo a camera e fazemos o produto dot e virificamos caso for proximo maior será o reflexo do objeto e longe menor será. Utilizamos potencia para simular melhor o reflexo ja que diminiu muito quando é pequeno e diminui quase nada quando é grande.. No final o reflexo é (reflexo * reflexividade *corDaLuz) e somamos esse valor na cor de saida do fragmentShader.

## Terreno ##

Criamos novos shaders para lidar com o terreno, como vertexShaderTerreno, fragmentShaderTerreno, ShaderTerreno e RenderizadorDeTerreno

## Transparencia de Textura ##

Quando fazemos uma importação de uma textura que tem transparencia o renderizador o renderizava com o fundo preto ao invés de transparente. Para que isso não aconteça criamos um booleano na textura dizendo se ela contem transparencia ou não. Caso verdadeiro nós discartavamos o pixel quando o alpha era acima de 0.5 dentro do fragment shader. Também havia outro problema que era que o renderizador quando tem transparencia ele tem o problema de não renderizar algo atras do objeto com o CullFace e desativamos-o caso a textura seja transparente. Nós criamos a iluminosidadeFalsa que faz com que quando importamos objetos como a planta que é somente dois quadrados um perpendicular ao outro as normais eram perpendiculares tbm e isso fazia com que as plantas apareciam com a iluminosidade bastante diferentes. Para isso nos importamos a variavel que criamos na Textura para o vertexShader pela forma de uniform e verificamos caso queremos que a textura tenha uma iluminosidadeFalsa nós colocavamos todas as suas normais para cima, deste modo a diferença entre um quadrado ao outro mesmo sendo perpendicular não afetava na iluminosidade da planta.

## Múltiplas Texturas ##



## Fog ##

Para criar o fog em nossa GameEngine primeiro criamos duas constantes densidade e gradiente na qual estão no *vertexShader*, a densidade é responsável pela grossura do fog, quanto maior a densidade menos visibilidade teremos. O gradiente é responsável em fazer a transição entre o visivel e o não visivel de modo que quanto maior o gradiente menor é a suavidade entre o que é visivel e o que é não visivel. Seguindo essa expressão temos o resultado da visibilidade do fog ->  exp(-pow(distancia_vertice_camera * densidade, gradiente)); depois deste calculo enviamos o resultado para o fragment shader, com a variavel visibilidade e a uniform da CorDoFog fazemos um mix entre a cor_saida e a cor do fog com a visibilidade como parametro, ou seja quanto maior a visibilidade menor é o mix entre a cor_saida e a visibilidade. Lembrando que a corDoCeu é enviada como uniform e temos que fazer todo aquele procedimento para no *StaticShader* e também no *ShaderTerreno* , fazemos todas essas atribuições para o terreno e para os objetos.

## Pacote de Textura Pra Terreno ##

Quando queremos criar um terreno com multiplas texturas é uma tarefa bem dificil, no nosso caso nos temos que fazer os seguintes passos. Precisamos importar as texturas e uma textura de mistura (blendMap) que é uma textura que contém o desenho do caminho das texturas com RGB e preto quando tem a ausencia de cor. Quando o fragmentshader se depara com a cor vermelha(na textura de mistura) nós devemos utilizar a textura que simboliza a cor vermelha, quando encontramos a verde usamos a verde etc... E quando encontramos duas cores sobrepostas como um vermelho com verde nós utilizamos a mistura entre a textura responsável pela cor vermelha e a verde. Para utilizar texturas nos importamos elas como uniform Sample2D no fragmentshader. Fazemos o calculo vec4 corTexturaVermelha = texture(texturaVermelha, tiledTextura) * corTexturaDeMistura.r; para cada tipo de cor e no final somamos todas as cores para que o amarelo simbolize a textura verde + a textura azul. Cada textura tem um inteiro respectivo para que o shader saiba qual será a sua unidade para isso devemos no shaderTerreno enviar a localidade das texturas como a super.carregarInteiro(localidade_TexturaVermelha, 0); onde 0 é a sua unidade. Usamos essa unidade no rendizadorDeTerrenos GL13.glActiveTexture(GL13.GL_TEXTURE0); GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturas.getTexturaVermelha().getTexturaID()); Neste caso a unidade 0 receberá o id da textura vermelha. E fazemos isso para todas as texturas.
ERRO: eu obtive um erro muito estranho quando o terreno ficou totalmente preto e somente onde eu estava(camera), aparecia as texturas. Esse erro foi solucionado quando carregamos uma iluminosidade especular para o terreno já que quando não setamos a iluminosidade especular as uniforms estavam com valores arbitrários e esse que era o problema.

## Movimentação de Objetos (Player) ##

Para introduzir a movimentação do Jogador devemos nos preocupar com medir o tempo decorrente entre um frame e outro para que quando o jogo rodar a 120fps ou a 15 fps não afetar na velocidade de movimentação do objeto. Deste modo mudamos um pouco o GerenciadorDeTempo para conter o Delta de modo em que este calcula o tempo decorrido entre um frame e outro. Criamos a entidade Player que extends a classe entidade e nele contém o metodo de movimento, de pular e as entradas no teclado.

## Camera 3ª Pessoa ##

Para fins de introduzir uma camera que segue algum objeto e de acordo com a movimentação do mouse ela pode dar zoom,movimentar horizontalmente e verticalmente criamos uma classe chamada CameraTerceiraPessoa damos um extends de Camera. Nessa classe há 3 atributos a mais que é distanciaDoJogador( é a distancia que a camera fica do jogador, ou seja o raio) o anguloEmVoltaDoJogador(utilizado para saber onde ela ficará posicionada horizontalmente do jogador) e a sensitivididade(utilizado para dizer a velocidade no qual movimentando o mouse ou a bolinha do mouse a camera ira se movimentar). No loop principal do jogo é chamado o metodo de mover() que calculará o zoom, angulo vertical, angulo horizontal e a posicao da camera. O zoom é calculado com base na movimentação da rodinha do mouse e quanto mais pra cima menor é a distanciaDoJogador. O angulo vertical é calculado com base no DeltaY do mouse(posicionamento anterior Y e o posicionamento atual Y) quanto maior menor será o pitch(atributo da classe estendida Camera). O angulo horizontal é calculado do mesmo modo do angulo vertical porém com o uso do DeltaX e o atributo que modifica é o anguloEmVoltaDoJogador. Agora para se obter o calculo do posicionamento da camera é mais complicado pois teremos que obter o x,y e z da camera e como faremos isso? Primeiramente devesse calcular a distancia horizontal da camera em relação ao jogador e para isso é calculado o cosseno utilizando a hipotenusa e o angulo. Neste caso a hipontenusa é distanciaDoJogador e o angulo é o pitch desse modo temos dxz =  cos(pitch) * distanciaDoJogador. Para calcular a distanciaVertical é a mesma coisa basta fazer com o seno. Mas como estamos em um ambiente 3D esse dxz calculado é uma distancia no plano x e z no qual devemos novamente fazer o calculo de cos e seno para saber exatamente a posicao x e z. Desse modo sabemos o anguloEmVoltaDoJogador no qual a camera está porém como nós queremos que a camera sempre fique exatamente atrás do jogador quando o jogador rotacionar a camera irá também rotacionar. Desse modo quando o anguloEmVoltaDoJogador for 0 ela sempre ficará atrás do jogador. Ou seja a rotação do jogador será colocado em conta no nosso calculo para saber onde a camera deve estar posicionada. Então o angulo teta tera o valor da soma da rotação Y do jogador e o anguloEmVoltaDoJogador assim quando o anguloEmVoltaDoJogador for 0 e ele rotacionar a camera continuará atras dele. Agora calcularemos o coseno e o seno do mesmo modo que calculamos para o Y. dx =  cos(teta) * dxz e dy =  sen(teta) * dxz. Agora basta subtrair a posição do jogador.x e o dx , a posição do jogador.z e o dz e somar a pos.y do jogador e a distanciaVertical. Porque subtraimos ? Nós subtraimos pq os objetos quando criados quando a rotação é 0 ele é criado olhando para o eixo Z positivo e a camera quando criada ela é virada para o eixo Z negativo. Desse modo quando calculamos o offset dx e dz eles estão no lado negativo do eixo x se olhado de cima e no lado negativo do eixo z se olhado de cima. Agora há um outro problema quando se rotaciona a camera ela não seguira a olhando para o jogador, ela continuará olhando para o mesmo lado de quando foi criada ou seja nós teremos que modificar o atributo Yaw que é responsável da angulatura horizontal da camera. Quando criada nós queremos que ela olhe para as costas do jogador ou seja ela tera que estar rotacionada 180 graus(lembra que eu falei que a camera começa olhando para o eixo z negativo? como nos posicionamos a camera nas costas do jogador e ele esta olhando para o z positivo devemos dar uma volta de 180) porém quando o jogador rotacionar a camera irá movimentar para uma nova posição mas não rotacionará ou seja devemos subtrair a rotação incial e a rotação que o player fez e também lembrar que podemos rotacionar a camera em volta do jogador(anguloEmVoltaDoJogador) e desse modo tbm devemos modificar o yaw para subtrair esse nova rotação. Desse modo está finalizado a camera 3 pessoa.

## MipMapping ##

O MipMapping serve para que quando uma textura esteja utilizando menos pixel na tela do que o seu tamanho original essa textura seja mapeada com uma menor resolução e isso tem um grande ganho de performace. Por exemplo faço um upload de uma textura 128x128 mas quando ela está no fim do horizonta essa textura ocupa somente 16x16 pixel na tela desse modo não é interessante colocar uma textura 128x128 em um espaço de 16x16 pois não terá sentido visto que lidamos com pixels e um pixel não pode ser subdividido em outros. Desta maneira geramos o mipmap dessa textura. Para mais detalhes visite https://www.3dgep.com/texturing-and-lighting-with-opengl-and-glsl/#Mipmaps

## Mapa de Alturas (Height Maps) e detecção de colisão utilizado em terrenos ##

Antes utilizávamos um terreno plano ou sejá não continha nenhuma elevação mas com o uso de uma imagem que contém um mapa de alturas podemos construir um terreno bastante irregular e com o molde que queremos. Para isso na criação de terreno devemos passar como parametro a imagem carregada do mapa de alturas que será usada no gerarTerreno() para criar o modelo do terreno. Essa imagem contém 256*256*256 em cada pixel que se trata do RGB porém fazemos um calculo para que cada pixel se reduza a um valor de -1 a 1 sendo o -1 a altura minima do terreno e 1 a altura maxima do terreno. Ou seja passamos um mapa De altura de 128x128 (lembrando que deve ser tamanho 2^n) com isso criamos os vertices do terreno no qual terá uma quantidade de vertices 128x128. Fazemos um for para que percorre a imagem e criar o vertice[i][j] com a altura relativa ao pixel. Fazemos também o calculo da normal baseada na altura dos vertices adjacentes. Para fazer o calculo da altura entre os vertices é um pouco mais complicado  e será esse calculo que faremos para se obter detecção de colisão, veja que dado um vertice A com altura H1 e um vertice B com altura H2 quando eles se conectam há uma interpolação de altura entre esses dois vertices ou seja se um tem o valor 0 e o outro tem valor 1 no meio entre esses dois vertices terá o valor 0.5 de altura. Esse calculo é usado para que quando um objeto passar do vertice A para o vertice B ele não salte de 0 para 1 o que ficaria nada realistico. Obs: Imagine você vendo o terreno de cima veja que o vertice[0][0] do terreno é o primeiro vertice do lado superior esquerdo e o vertice[qtdVertice-1][qtdVertice-1] é o vertice inferior direito. E o tamanho do terreno do vertice[0][0] ao vertice[0][qtdVertice-1] é TAMANHO e por sua vez o tamanho do terreno do vertice[0][0] ao vertice[qtdVertice-1][0] é TAMANHO ou seja o terreno tem um tamanho de TAMANHO x TAMANHO contendo em seu decorrer uma quantidade de vertices da largura da imagem de Altura x altura da imagem de Altura. Fim da Obs . Desse modo fazemos o seguinte calculo primeiro verificaremos dado os valores MundoX e MundoY passados como parametros(que é a posição x e y dos objetos no mundo) pegamos esse valor e subtraimos da posição x e posição y do terreno, desse modo obtemos o posicionamento do jogador em relação ao terreno. Ou seja suponha que o jogador está na posição x 801 e posicao z 0 se dado um terreno ele tem a malhax com o valor 1 e o TAMANHO de 800 logo o seu posicionamento x é dado por malhax * TAMANHO isto diz que ele começará a ser construido apartir da posição x(800) do mundo. E quando subtraimos o valor MundoX do jogador(801) e a pos.x do terreno(800) nós obtemos o valor 1 que é a sua posição em relação a posição do terreno(relativamente irá de 0 a TAMANHO de largura e 0 a TAMANHO de altura). Agora para saber a quantidade de grids que o terreno tem basta calcular TAMANHO / (quantidade de vertice - 1) e para saber em qual grid o jogador está basta calcular piso(posição relativa x / a quantidade de grids que o terreno é feito). O calculo da vertical é  mesma coisa basta utilizar a relativa z. Ou seja no nosso exemplo como o jogador está com 1 em x e 0 em z ele ficará na grid (0,0). Agora que sabemos em qual grid que o nosso objeto está nós devemos saber qual é a posição de interpolação do objeto ou seja onde ele está dentro da grid. Devemos fazer o resto entre a posição relativa x e a quantidade de grid e depois dividir esse valor pela quantidade de grid, desse modo saberemos exatamente em que posição dentro da grid ele está. Se a grid tiver um tamanho de 20 o valor será de 0 a 19. Fazemos isso com o x e z e apartir dai fazemos o calculo da posição baricentrica do triangulo em que o objeto está em cima(lembrando que a grid é formada por triangulos mas ela é um quadrado formado por 2 triangulos, para saber em qual triangulo o objeto está basta fazer o calculo (se a cordenada x é menor que 1 - cordenada Z então é o triangulo superior se não será o triangulo inferior) Isso é sabido já que a reta que corta o quadrado na diagonal do conto inferior esquerdo até o canto superior direito é tido como x = z ou seja, se esta na parte superior esquerda o z deve ser maior que o x e se esta na parte inferior direita o x deve ser maior que o z. Desse modo utilizamos um metodo que passado os tres vertices do triangulo e a posição x,z do objeto que está dentro dele descobre qual seria a sua posição y.


 






 





