:- multifile(clause1/2).

 readFile(X,N):- 
	     open(X,read,Str),
         read(Str,N), 
         close(Str).
 eval:- 
	working_directory(DIR, DIR),
    atom_concat(DIR, 'resources/files/AML_rules.pl', AML_RULE),
    atom_concat(DIR, 'resources/files/semi1.pl', SEMI1),
    atom_concat(DIR, 'resources/files/edb.txt', EDB),

    readFile(EDB,X),
       
 assert(amlPredicates(
 [
 'sameRoleClassLib','sameRoleClass','sameInterfaceClass','sameSystemUnitClass',
 'eClassClassificationAtt','eClassVersionAtt','eClassIRDIAtt','sameAttribute','concatString','sameIdentifier' 
 ] 
 )),    
 consult(SEMI1),
 consult(AML_RULE),
 consult(X),
 %consult('d:/Deutch/development/Rules4AMLIntegration/resources/files/TestData.pl'),
 tdb.       
                  
 writePredicates:-
	working_directory(DIR, DIR),
    atom_concat(DIR, 'resources/files/writeRules.pl', WRITE_RULE), 
    atom_concat(DIR, 'resources/files/output.txt', OUTPUT), 
    
  readFile(OUTPUT,Z),
  open(Z,write,FileName),
 consult(WRITE_RULE),
 myWrite(FileName),close(FileName).             
 
