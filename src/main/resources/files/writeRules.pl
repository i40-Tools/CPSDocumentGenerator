% To write the rules into a file
myWrite(FileName):-listAMLPredicates(P),Z=..[P,X,Y],retract(Z),Z=..[P,X,Y],X\==Y,Z1=..[P,Y,X],retract(Z1),write(FileName, Z), writeln(FileName,'.'), writeln(FileName), fail.
myWrite(_).

listAMLPredicates(P):-amlPredicates(L),member(P,L).

%writeSRCL(FileName):-sameRoleClassLib(X,Y),X\==Y,Z=..['sameRoleClassLib',X ,Y], writeln(FileName, Z), writeln(FileName), fail.
%writeSRCL(_).   