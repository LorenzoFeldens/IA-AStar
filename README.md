# IA – Algoritmo A*

Trabalho individual da disciplina de **Inteligência Artificial**: implementação do algoritmo de busca
**A\* (A-estrela)** em Java, com interface gráfica em Swing. O programa encontra o menor caminho entre dois
pontos de um grid com obstáculos e permite acompanhar a busca passo a passo.

O relatório do trabalho está em [`Trabalho Individual II - Lorenzo Feldens.pdf`](Trabalho%20Individual%20II%20-%20Lorenzo%20Feldens.pdf).

## Como funciona

1. Na tela inicial, você informa:
   - o **tamanho do grid** (N × N);
   - a **porcentagem de obstáculos**;
   - a **posição inicial** e a **posição final** (X, Y).
2. Os obstáculos são sorteados em posições aleatórias, sem cobrir o início nem o fim.
3. A busca A\* expande os estados pela lista de **abertos**, sempre escolhendo o de menor custo
   `f = g + h`:
   - `g`: número de passos já percorridos;
   - `h`: distância euclidiana até o objetivo (heurística).
4. Cada estado gera até 4 vizinhos (cima, baixo, esquerda e direita), ignorando obstáculos, posições fora do
   grid e posições que já estão nas listas de abertos ou fechados com custo igual ou menor.
5. Ao chegar ao objetivo, o caminho solução é reconstruído pelos pais de cada estado.

## Visualizações

| Botão | O que mostra |
|---|---|
| **Resultado Busca** | O grid com obstáculos, início, fim e o caminho encontrado |
| **Árvore Busca** | A árvore de busca, com os botões **Próximo** (executa um passo) e **Reset** |
| **Caminho Solução** | Só os estados do caminho solução |

## Estrutura do projeto

| Caminho | Descrição |
|---|---|
| `AlgoritmoAEstrela/src/algoritmoaestrela/Algoritmo.java` | Lógica do A\*: obstáculos, listas de abertos e fechados, expansão e caminho |
| `AlgoritmoAEstrela/src/algoritmoaestrela/Estado.java` | Estado da busca: posição, custo percorrido, heurística, pai e filhos |
| `AlgoritmoAEstrela/src/GUI/Menu.java` | Tela inicial com os parâmetros do problema |
| `AlgoritmoAEstrela/src/GUI/Painel.java` / `PainelArvore.java` | Desenho do grid e da árvore de busca |

## Como executar

Projeto do **NetBeans** (Ant, Java 8). Abra a pasta `AlgoritmoAEstrela` no NetBeans e execute, ou compile pela
linha de comando:

```sh
cd AlgoritmoAEstrela
ant run
```

## Tecnologias

- Java 8 + Swing
- NetBeans / Apache Ant
