%
% semi.pl
% EVALUADOR SEMI-NAIVE, Bottom-Up
% Maria Esther Vidal y Sandra Zabala
%

:-dynamic(belongs/2).
 

tdb:- tdb(0),build_model.

tdb(I):- clause1(H,B), valid(B,I), 
	 (\+(belongs(H,_)) ->  
                   I1 is I+1,  
                   assert(belongs(H,I1)), 
		   fail).

tdb(I):- I1 is I+1, belongs(_,I1), tdb(I1).

tdb(_).

valid(B,I):- valid_db(B,I), valid_delta(B,I).

valid_db(true,_).

valid_db((A,B),I):- valid_db(A,I), valid_db(B,I).

valid_db(A,I):- (belongs(A,I1), I >= I1 ; A).

valid_delta(true,0).

valid_delta((A,B),I):- valid_delta(A,I) ; valid_delta(B,I).

valid_delta(A,I):- (belongs(A,I);A).

build_model:-belongs(H,I),assert(H),retract(belongs(H,I)),fail.
build_model.
















