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
  (   
    exp=expressao_binaria  
  )
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
  (esq=atomo { $rv = $esq.rv; }) 
  (op=('*'|'/') dir=atomo { $rv = new ExpressaoBinaria($rv, $dir.rv, $op.text) ;})* 
  ;  
  
atomo returns [Expressao rv]
  :
  (
    exp=expressao_entre_parentesis
  | exp=expressao_unaria 
  | exp=valor 
  )
  { $rv = $exp.rv; }
  ; 
       
expressao_entre_parentesis returns [Expressao rv]
  :
  '(' exp=expressao_binaria ')'
  { $rv = $exp.rv; }
  ;
  
expressao_unaria returns [Expressao rv]
  :
  (
    op=('+'|'-') exp=atomo
    { $rv = new ExpressaoUnaria($op.text, $exp.rv); }
  )
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
