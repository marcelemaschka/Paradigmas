lexer grammar PortujavaLexer;

options {
  language = Java;
}

@lexer::header {
package br.cin.ufpe;
}

@members {
@Override
public void displayRecognitionError(String[] tokenNames, RecognitionException e) {
	throw new RuntimeException(e);
}
}

// palavras-chave

PARA
  :
  'para'
  ;

EM
  :
  'em'
  ;

ENQUANTO
  :
  'enquanto'
  ;

SENAO
  :
  'senao'
  ;

SE
  :
  'se'
  ;

FUNCAO
  :
  'funcao'
  ;
  
CONSTRUIR
  :
  'novo'|'nova'
  ;
  
CLASSE
  :
  'classe'
  ;

RETORNAR
  :
  'retornar'
  ;

VERDADEIRO
  :
  'verdadeiro'
  ;

FALSO
  :
  'falso'
  ;

A
  :
  'a'
  ;
//

PONTO
  :
  '.'
  ;

ESCOPO
  :
  '@'
  ;

OU_LOGICO
  :
  '||'
  ;

E_LOGICO
  :
  '&&'
  ;

OU_BIT
  :
  '|'
  ;

E_BIT
  :
  '&'
  ;

DIF
  :
  '!='
  ;

IGUAL
  :
  '=='
  ;

ESHIFT
  :
  '<<'
  ;

DSHIFT
  :
  '>>'
  ;

MAIOR_IGUAL
  :
  '>='
  ;

MAIOR
  :
  '>'
  ;

MENOR_IGUAL
  :
  '<='
  ;

MENOR
  :
  '<'
  ;

AD
  :
  '+'
  ;

SUB
  :
  '-'
  ;

MULT
  :
  '*'
  ;

DIV
  :
  '/'
  ;

ATRIB
  :
  '='
  ;

EXCL
  :
  '!'
  ;

VIRG
  :
  ','
  ;

PVIRG
  :
  ';'
  ;

DPONT
  :
  ':'
  ;

EPAR
  :
  '('
  ;

DPAR
  :
  ')'
  ;

ECOL
  :
  '['
  ;

DCOL
  :
  ']'
  ;

ECHAVE
  :
  '{'
  ;

DCHAVE
  :
  '}'
  ;

INTEIRO
  :
  ('0'..'9')+
  ;

DECIMAL
  :
  ('0'..'9')+ '.' ('0'..'9')*
  ;

TEXTO
@init {
StringBuilder sb = new StringBuilder();
}
  :
  (
    '\''
    (
      (
        c=
        (
          '\\'
          (
            ('n' 
                {
                 sb.append("\n");
                })
            | ('\'' 
                   {
                    sb.append("'");
                   })
            | ('\\' 
                   {
                    sb.append("\\");
                   })
          )
        )
      )
      |
      (
        c=
        ~(
          '\''
          | '\\'
          | '\n'
         )
        
        {
         sb.appendCodePoint($c);
        }
      )
    )*
    '\''
  )
  
  {
   setText(sb.toString());
  }
  ;

IDENTIFICADOR
  :
  (
    'a'..'z'
    | 'A'..'Z'
  )
  (
    'a'..'z'
    | 'A'..'Z'
    | '0'..'9'
  )*
  ;

ESPACO_EM_BRANCO
  :
  (
    ' '
    | '\n'
    | '\r'
    | '\t'
  )+
  
  {
   $channel = HIDDEN;
  }
  ;

COMENTARIO_LINHA
  :
  (
    '//'
    ~(
      '\n'
      | '\r'
     )*
    '\r'? '\n'
  )
  
  {
   $channel = HIDDEN;
  }
  ;

COMENTARIO_BLOCO
  :
  ('/*' .* '*/') 
                {
                 $channel = HIDDEN;
                }
  ;
