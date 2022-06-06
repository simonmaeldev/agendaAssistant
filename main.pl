append([], L, L).
append([H|T], L2, [H|L3]) :- append(T, L2, L3).

addTime(time(D, H, M), A, time(RD, RH, RM)) :-
        Madd is M + A,
        RM is mod(Madd, 60),
        AH is Madd // 60,
        Hadd is H + AH,
        RH is mod(Hadd, 24),
	AD is Hadd // 24,
	RD is D + AD.

nextTime(Start, NewStart) :-
    timeUnit(T),
    addTime(Start, T, NewStart).

% X < Y
lt(time(D1, H1, M1), time(D2, H2, M2)) :-
    D1 < D2;
    D1 = D2, H1 < H2;
    D1 = D2, H1 = H2, M1 < M2.

% X =< Y
lte(X, Y) :-
    X = Y;
    lt(X, Y).

% X < Y < Z
betweenS(X, Y, Z) :-
    lt(X, Y), lt(Y, Z).
% X =< Y =< Z
between(X, Y, Z) :-
    lte(X, Y), lte(Y, Z).

:- dynamic borne/1.
:- dynamic avoid/2.

generateHoursAcc(Start, Stop, _, Acc, Acc) :- lt(Stop, Start), !.

generateHoursAcc(Start, Stop, [avoid(Limit, NewStart)|Avoid], Res, Acc) :-
    between(Limit, Start, NewStart),
    generateHoursAcc(NewStart, Stop, Avoid, Res, [borne(Limit) | Acc]).

generateHoursAcc(Start, Stop, Avoid, Res, Acc) :-
    (Avoid = [] ; Avoid = [avoid(S, _)|_], S \= Start),
    nextTime(Start, NewStart),
    generateHoursAcc(NewStart, Stop, Avoid, Res, [borne(Start) | Acc]).

% Avoid doit etre une liste de plages horaires impossibles, triees dans l'ordre croissant
% avoid(start, end)
% l'heure de debut et de fin seront comprises dans les heures generees
% start, stop et avoides sous le format time(Day, Hour, Minutes)

generateHours(Start, Stop, Avoid) :- maplist(assert, Avoid), generateHoursAcc(Start, Stop, Avoid, Res, []), maplist(asserta, Res).

affecterAllAcc([], Ltaches, Ltaches).
affecterAllAcc([Name|Tail], Ltaches, AccTaches) :-
    tpsNecessaire(Name, T),
    borne(S),
    addTime(S, T, E),
    borne(E),
    not(overrideAvoid(S, E)),
    dontConflicts(S, E, AccTaches),
    append(AccTaches, [tache(Name, S, E)], NewAccTaches),
    affecterAllAcc(Tail, Ltaches, NewAccTaches).

affecterAll(Lnom, Ltaches) :-
    affecterAllAcc(Lnom, Ltaches, []).

dontConflicts(_, _, []).
dontConflicts(S, E, [tache(_, Sl, El)|Ltaches]) :-
    (lte(E,Sl); lte(El,S)), dontConflicts(S, E, Ltaches).

overrideAvoid(S, E) :-
    avoid(Sa, Ea),
    (
	S = Sa;
	E = Ea;
	betweenS(S, Sa, E);
	betweenS(S, Ea, E)
    ).

% user defined
%timeUnit(5).
%tpsNecessaire(coder, 120).
%tpsNecessaire(vaisselle, 60).

% executed dynamically
%generateHours(time(1, 17, 0), time(1, 23, 0), [avoid(time(1, 20, 0), time(1, 22, 0))]).
%affecterAll([coder, vaisselle], X).
