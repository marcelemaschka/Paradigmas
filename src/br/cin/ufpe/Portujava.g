grammar Portujava;


options {
  language = Java;
}

@header {
  package br.cin.ufpe;
  
  import br.cin.ufpe.ast.*;
}

@lexer::header {
  package br.cin.ufpe;
}

programa returns [Expressao rv]
  :
  exp=expressao
  { $rv = $exp.rv; }
  ;

expressao returns [Expressao rv]
  :
  exp=expressao_binaria
  { $rv = $exp.rv; }
  ;

expressao_binaria returns [Expressao rv]
  :
  exp=soma
  { $rv = $exp.rv; }
  ;

soma returns [Expressao rv]
  :
  (esq=multiplicacao { $rv = $esq.rv; }) 
  (op=('+'|'-') dir=multiplicacao { $rv = new ExpressaoBinaria($rv, $dir.rv, $op.text) ;})*
  ;
  
multiplicacao returns [Expressao rv]
  :
  (esq=valor { $rv = $esq.rv; }) 
  (op=('*'|'/') dir=valor { $rv = new ExpressaoBinaria($rv, $dir.rv, $op.text) ;})* 
  ;  
  
    
valor returns [Expressao rv]
  :
  (exp=decimal|exp=inteiro) 
  { $rv = $exp.rv; } 
  ;
  
decimal returns [Expressao rv]
  :
  t=DECIMAL
  { $rv = new Decimal($t.text); }
  ;
  
inteiro returns [Expressao rv]
  :
  t=INTEIRO
  { $rv = new Inteiro($t.text); }
  ;

DECIMAL
  :
   ('0'..'9')+ '.' ('0'..'9')*
  ;

INTEIRO
  :
  ('0'..'9')+
  ;

