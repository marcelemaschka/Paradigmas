parser grammar PortujavaParser;

options {
  language   = Java;
  tokenVocab = PortujavaLexer;
}

@header {
package br.cin.ufpe;
  
  import java.util.ArrayList;
  import br.cin.ufpe.ast.*;
}

// Todos os erros de sintaxe podem ser capturados/traduzidos
// simplesmente sobrescrevendo o método que cuida de imprimir
// a mensagem de erro e fazendo o parser jogar um RuntimeException
// que encapsula a excecao real.

@members {
@Override
public void displayRecognitionError(String[] tokenNames, RecognitionException e) {
	throw new RuntimeException(e);
}
}

programa returns [Programa rv]
@init {
ArrayList<Comando> comandos = new ArrayList<Comando>();
}
  :
  (comando 
           {
            comandos.add($comando.rv);
           })* 
               {
                rv = new Programa(comandos);
               }
  ;

bloco returns [Bloco rv]
@init {
ArrayList<Comando> comandos = new ArrayList<Comando>();
}
  :
  ECHAVE (comando 
                  {
                   comandos.add($comando.rv);
                  })* DCHAVE 
                             {
                              rv = new Bloco(comandos);
                             }
  ;

comando returns [Comando rv]
  :
  (
    cmd=comando_pontoVirgula
    | cmd=comando_fluxo
  )
  
   {
    $rv = $cmd.rv;
   }
  ;

comando_pontoVirgula returns [Comando rv]
  :
  (cmd=comando_execucao PVIRG) 
                               {
                                $rv = $cmd.rv;
                               }
  ;

comando_execucao returns [Comando rv]
  :
  (
    cmd=retornar
    | cmd=atribuicao
    | cmd=comando_expressao
  )
  
   {
    $rv = $cmd.rv;
   }
  ;

comando_fluxo returns [Comando rv]
  :
  (
    cmd=declaracao_de_funcao
    | cmd=se
    | cmd=enquanto
    | cmd=para
  )
  
   {
    $rv = $cmd.rv;
   }
  ;

comando_expressao returns [Comando rv]
  :
  (expressao) 
              {
               $rv = $expressao.rv;
              }
  ;

retornar returns [Comando rv]
  :
  RETORNAR (exp=expressao)? 
                            {
                             $rv = new Retornar($exp.rv);
                            }
  ;

declaracao_de_funcao returns [Comando rv]
@init {
ArrayList<String> parametros = new ArrayList<String>();
}
  :
  (FUNCAO nome=identificador EPAR (p1=identificador 
                                                    {
                                                     parametros.add($p1.rv.getNome());
                                                    }
      (VIRG pn=identificador 
                             {
                              parametros.add($pn.rv.getNome());
                             })*)? DPAR bloco) 
                                               {
                                                $rv = new DeclaracaoDeFuncao($nome.rv, parametros, $bloco.rv);
                                               }
  ;

atribuicao returns [Atribuicao rv]
  :
  (identificador ATRIB expressao) 
                                  {
                                   $rv = new Atribuicao($identificador.rv, $expressao.rv);
                                  }
  ;

se returns [Comando rv]
  :
  (
    SE (exp=expressao_entre_parentesis bloco1=bloco) (SENAO bloco2=bloco)?
  )
  
   {
    $rv = new Se($exp.rv, $bloco1.rv, $bloco2.rv);
   }
  ;

enquanto returns [Comando rv]
  :
  (ENQUANTO exp=expressao_entre_parentesis bloco) 
                                                  {
                                                   $rv = new Enquanto($exp.rv, $bloco.rv);
                                                  }
  ;

para returns [Comando rv]
  :
  (PARA id=identificador EM exp=expressao bl=bloco) 
                                                    {
                                                     $rv = new Para($id.rv, $exp.rv, $bl.rv);
                                                    }
  ;

// Para facilitar a leitura, definimos as expressoes em ordem inversa de precedencia

expressao returns [Expressao rv]
  :
  (
    exp=expressao_binaria
    | exp=funcao
  )
  
   {
    $rv = $exp.rv;
   }
  ;

funcao returns [Expressao rv]
@init {
ArrayList<String> parametros = new ArrayList<String>();
}
  :
  (FUNCAO EPAR (p1=identificador 
                                 {
                                  parametros.add($p1.rv.getNome());
                                 }
      (VIRG pn=identificador 
                             {
                              parametros.add($pn.rv.getNome());
                             })*)? DPAR bloco) 
                                               {
                                                $rv = new Funcao(parametros, $bloco.rv);
                                               }
  ;

expressao_binaria returns [Expressao rv]
  :
  exp=disjuncao 
                {
                 $rv = $exp.rv;
                }
  ;

disjuncao returns [Expressao rv]
  :
  (esq=conjuncao 
                 {
                  $rv = $esq.rv;
                 }) (op=OU_LOGICO dir=conjuncao 
                                                {
                                                 $rv = new ExpressaoBinaria($rv, $dir.rv, $op.text);
                                                })*
  ;

conjuncao returns [Expressao rv]
  :
  (esq=comparacao_igualdade 
                            {
                             $rv = $esq.rv;
                            }) (op=E_LOGICO dir=comparacao_igualdade 
                                                                     {
                                                                      $rv = new ExpressaoBinaria($rv, $dir.rv, $op.text);
                                                                     })*
  ;

bitwise_ou returns [Expressao rv]
  :
  (esq=bitwise_e 
                 {
                  $rv = $esq.rv;
                 }) (op= (OU_BIT) dir=bitwise_e 
                                                {
                                                 $rv = new ExpressaoBinaria($rv, $dir.rv, $op.text);
                                                })*
  ;

bitwise_e returns [Expressao rv]
  :
  (esq=comparacao_igualdade 
                            {
                             $rv = $esq.rv;
                            }) (op= (E_BIT) dir=comparacao_igualdade 
                                                                     {
                                                                      $rv = new ExpressaoBinaria($rv, $dir.rv, $op.text);
                                                                     })*
  ;

comparacao_igualdade returns [Expressao rv]
  :
  (esq=comparacao_maiormenor 
                             {
                              $rv = $esq.rv;
                             })
  (
    (
      op=DIF
      | op=IGUAL
    )
    dir=comparacao_maiormenor 
                              {
                               $rv = new ExpressaoBinaria($rv, $dir.rv, $op.text);
                              }
  )*
  ;

comparacao_maiormenor returns [Expressao rv]
  :
  (esq=bitwise_shift 
                     {
                      $rv = $esq.rv;
                     })
  (
    op=
    (
      MAIOR_IGUAL
      | MAIOR
      | MENOR_IGUAL
      | MENOR
    )
    dir=bitwise_shift 
                      {
                       $rv = new ExpressaoBinaria($rv, $dir.rv, $op.text);
                      }
  )?
  ;

bitwise_shift returns [Expressao rv]
  :
  (esq=soma 
            {
             $rv = $esq.rv;
            })
  (
    op=
    (
      DSHIFT
      | ESHIFT
    )
    dir=soma 
             {
              $rv = new ExpressaoBinaria($rv, $dir.rv, $op.text);
             }
  )*
  ;

soma returns [Expressao rv]
  :
  (esq=multiplicacao 
                     {
                      $rv = $esq.rv;
                     })
  (
    op=
    (
      AD
      | SUB
    )
    dir=multiplicacao 
                      {
                       $rv = new ExpressaoBinaria($rv, $dir.rv, $op.text);
                      }
  )*
  ;

multiplicacao returns [Expressao rv]
  :
  (esq=expressao_primaria 
                          {
                           $rv = $esq.rv;
                          })
  (
    op=
    (
      MULT
      | DIV
    )
    dir=expressao_primaria 
                           {
                            $rv = new ExpressaoBinaria($rv, $dir.rv, $op.text);
                           }
  )*
  ;

expressao_primaria returns [Expressao rv]
  // regra que serve para identificar as expressoes de maior precedencia na linguagem
  // por enquanto somente Atomos e chamadas de funcao estão nesta categoria, futuramente
  // pode ser usada para outras operaçoes, tipo accessar o atributos de uma instancia
  :
  atomo 
        {
         $rv = $atomo.rv;
        }
  ( (EPAR l=lista_de_expressoes DPAR 
                                     {
                                      $rv = new ChamadaDeFuncao($rv, $l.rv);
                                     }))*
  ;

atomo returns [Expressao rv]
  // Nessa regra temos que atribuir o valor de retorno dentro de cada caso
  // pois a regra 'identificador' retorna um objeto do tipo 'Identificador'
  // e as outras regras retornam objetos do tipo 'Expressao'. Normalmente isso
  // nao seria problema pois um 'Identificador' é tambem uma 'Expressao' mas
  // o antlr nao aceita isso
  :
  (
    (lista 
           {
            $rv = $lista.rv;
           })
    | (expressao_geradora 
                          {
                           $rv = $expressao_geradora.rv;
                          })
    | (expressao_entre_parentesis 
                                  {
                                   $rv = $expressao_entre_parentesis.rv;
                                  })
    | (expressao_unaria 
                        {
                         $rv = $expressao_unaria.rv;
                        })
    | (valor 
             {
              $rv = $valor.rv;
             })
    | (identificador 
                     {
                      $rv = $identificador.rv;
                     })
  )
  ;

lista returns [Expressao rv]
  :
  ECOL exp=gerador DCOL 
                        {
                         $rv = new Lista($exp.rv);
                        }
  ;

expressao_geradora returns [Expressao rv]
  // regra que retorna iteradores
  :
  DPONT exp=gerador DPONT 
                          {
                           $rv = $exp.rv;
                          }
  ;

gerador returns [Expressao rv]
  :
  (
    exp=lista_de_expressoes
    | exp=intervalo
  )
  
   {
    $rv = $exp.rv;
   }
  ;

lista_de_expressoes returns [Expressao rv]
@init {
ArrayList<Expressao> expressoes = new ArrayList<Expressao>();
}
// TODO: Corrigir esse warning
  :
  (exp1=expressao 
                  {
                   expressoes.add($exp1.rv);
                  }
    (VIRG expn=expressao 
                         {
                          expressoes.add($expn.rv);
                         })*)? 
                               {
                                $rv = new ListaDeExpressoes(expressoes);
                               }
  ;

intervalo returns [Expressao rv]
  :
  (inicio=inteiro A fim=inteiro (VIRG passo=inteiro)?) 
                                                       {
                                                        $rv = new Intervalo($inicio.rv, $fim.rv, $passo.rv);
                                                       }
  ;

expressao_entre_parentesis returns [Expressao rv]
  :
  EPAR exp=expressao_binaria DPAR 
                                  {
                                   $rv = $exp.rv;
                                  }
  ;

expressao_unaria returns [Expressao rv]
  :
  (
    op=
    (
      AD
      | SUB
      | EXCL
    )
    exp=atomo 
              {
               $rv = new ExpressaoUnaria($op.text, $exp.rv);
              }
  )
  ;

valor returns [Expressao rv]
  :
  (
    exp=inteiro
    | exp=decimal
    | exp=texto
    | exp=booleano
  )
  
   {
    $rv = $exp.rv;
   }
  ;

identificador returns [Identificador rv]
  :
  (
    t=IDENTIFICADOR
    | t=A
  )
  
   {
    $rv = new Identificador($t.text);
   }
  ;

decimal returns [Expressao rv]
  :
  t=DECIMAL 
            {
             $rv = new Decimal($t.text);
            }
  ;

inteiro returns [Inteiro rv]
  :
  t=INTEIRO 
            {
             $rv = new Inteiro($t.text);
            }
  ;

texto returns [Expressao rv]
  :
  t=TEXTO 
          {
           $rv = new Texto($t.text);
          }
  ;

booleano returns [Expressao rv]
  :
  (
    t=VERDADEIRO
    | t=FALSO
  )
  
   {
    $rv = new Booleano($t.text);
   }
  ;
