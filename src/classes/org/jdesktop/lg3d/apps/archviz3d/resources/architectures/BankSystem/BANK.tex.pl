value(z_BANK(Z_bal,Z_pass,Z_log,Z_user),'Z_bal',Z_bal).
setValue(z_BANK(Z_bal,Z_pass,Z_log,Z_user),z_BANK(Z_NEW_bal,Z_pass,Z_log,Z_user),'Z_bal',Z_NEW_bal).
value(z_BANK(Z_bal,Z_pass,Z_log,Z_user),'Z_pass',Z_pass).
setValue(z_BANK(Z_bal,Z_pass,Z_log,Z_user),z_BANK(Z_bal,Z_NEW_pass,Z_log,Z_user),'Z_pass',Z_NEW_pass).
value(z_BANK(Z_bal,Z_pass,Z_log,Z_user),'Z_log',Z_log).
setValue(z_BANK(Z_bal,Z_pass,Z_log,Z_user),z_BANK(Z_bal,Z_pass,Z_NEW_log,Z_user),'Z_log',Z_NEW_log).
value(z_BANK(Z_bal,Z_pass,Z_log,Z_user),'Z_user',Z_user).
setValue(z_BANK(Z_bal,Z_pass,Z_log,Z_user),z_BANK(Z_bal,Z_pass,Z_log,Z_NEW_user),'Z_user',Z_NEW_user).

z_BANK(globalState(This,Z_State,Z_State),Z_bal,Z_pass,Z_log,Z_user).
z_init_BANK(globalState(This,Z_State,Z_NewState),z_BANK(Z_bal_DECORV,Z_pass_DECORV,Z_log_DECORV,Z_user_DECORV)):-
	Z_user_DECORV=0,
	map(1,100,Z_TEMP2),	map(2,0,Z_TEMP3),
	Z_bal_DECORV=[Z_TEMP2,Z_TEMP3],
	map(1,1111,Z_TEMP4),	map(2,2222,Z_TEMP5),
	Z_pass_DECORV=[Z_TEMP4,Z_TEMP5],
	Z_log_DECORV=[tuple(1,'deposit',100),tuple(1,'deposit',10),tuple(2,'deposit',00)]
	,addChangeOp(Z_State,z_init_BANK,[va('Z_bal'),va('Z_pass'),va('Z_log'),va('Z_user')],Z_NewState).

z_SetUserOk(globalState(This,Z_State,Z_NewState),z_BANK(Z_bal,Z_pass,Z_log,Z_user),z_BANK(Z_bal_DECORV,Z_pass_DECORV,Z_log,Z_user_DECORV),Z_id,Z_password,Z_report):-
	application(Z_pass,Z_id,Z_TEMP1),
	Z_TEMP1=Z_password,
	Z_user_DECORV=Z_id,
	Z_pass_DECORV=Z_pass,
	Z_bal_DECORV=Z_bal,
	Z_report='ok',
	addChangeOp(Z_State,z_SetUserOk,[va('Z_bal'),va('Z_pass'),va('Z_user')],Z_NewState),
	z_BANK(globalState(This,Z_NewState,Z_NewState),Z_bal_DECORV,Z_pass_DECORV,Z_log,Z_user_DECORV).

z_SetUserBad(globalState(This,Z_State,Z_NewState),z_BANK(Z_bal,Z_pass,Z_log,Z_user),z_BANK(Z_bal,Z_pass,Z_log,Z_user),Z_id,Z_password,Z_report):-
	application(Z_pass,Z_id,Z_TEMP1),
	Z_TEMP1\=Z_password,
	Z_report='not_user',
	addChangeOp(Z_State,z_SetUserBad,[],Z_NewState),
	z_BANK(globalState(This,Z_NewState,Z_NewState),Z_bal,Z_pass,Z_log,Z_user).

z_getUser(globalState(This,Z_State,Z_NewState),z_BANK(Z_bal,Z_pass,Z_log,Z_user),z_BANK(Z_bal,Z_pass,Z_log,Z_user),Z_current):-
	Z_current=Z_user,
	addChangeOp(Z_State,z_getUser,[],Z_NewState),
	z_BANK(globalState(This,Z_NewState,Z_NewState),Z_bal,Z_pass,Z_log,Z_user).

z_SetUser(globalState(This,Z_State,Z_NewState),z_BANK(Z_bal,Z_pass,Z_log,Z_user),z_BANK(Z_bal,Z_pass,Z_log,Z_user),Z_id,Z_password,Z_report):-
	z_SetUserBad(globalState(This,Z_State,Z_TEMP1),z_BANK(Z_bal,Z_pass,Z_log,Z_user),z_BANK(Z_bal,Z_pass,Z_log,Z_user),Z_id,Z_password,Z_report),
	addChangeOp(Z_TEMP1,z_SetUser,Z_NewState).

z_SetUser(globalState(This,Z_State,Z_NewState),z_BANK(Z_bal,Z_pass,Z_log,Z_user),z_BANK(Z_bal_DECORV,Z_pass_DECORV,Z_log_DECORV,Z_user_DECORV),Z_id,Z_password,Z_report):-
	z_SetUserOk(globalState(This,Z_State,Z_TEMP1),z_BANK(Z_bal,Z_pass,Z_log,Z_user),z_BANK(Z_bal_DECORV,Z_pass_DECORV,Z_log_DECORV,Z_user_DECORV),Z_id,Z_password,Z_report),
	addChangeOp(Z_TEMP1,z_SetUser,Z_NewState).

z_EndUser(globalState(This,Z_State,Z_NewState),z_BANK(Z_bal,Z_pass,Z_log,Z_user),z_BANK(Z_bal,Z_pass,Z_log,Z_user_DECORV),Z_report):-
	Z_user\=0,
	Z_user_DECORV=0,
	Z_report='ok',
	addChangeOp(Z_State,z_EndUser,[va('Z_user')],Z_NewState),
	z_BANK(globalState(This,Z_NewState,Z_NewState),Z_bal,Z_pass,Z_log,Z_user_DECORV).

z_Balance(globalState(This,Z_State,Z_NewState),z_BANK(Z_bal,Z_pass,Z_log,Z_user),z_BANK(Z_bal,Z_pass,Z_log,Z_user),Z_src,Z_amount):-
	application(Z_bal,Z_src,Z_TEMP1),
	Z_amount=Z_TEMP1,
	addChangeOp(Z_State,z_Balance,[],Z_NewState),
	z_BANK(globalState(This,Z_NewState,Z_NewState),Z_bal,Z_pass,Z_log,Z_user).

z_TransferOk(globalState(This,Z_State,Z_NewState),z_BANK(Z_bal,Z_pass,Z_log,Z_user),z_BANK(Z_bal_DECORV,Z_pass_DECORV,Z_log,Z_user_DECORV),Z_amount,Z_src,Z_dst,Z_report):-
	Z_src\=Z_dst,
	application(Z_bal,Z_src,Z_TEMP1),
	Z_TEMP1>=Z_amount,
	application(Z_bal,Z_src,Z_TEMP2),
	Z_TEMP3 is Z_TEMP2-Z_amount,
	map(Z_src,Z_TEMP3,Z_TEMP4),	application(Z_bal,Z_dst,Z_TEMP5),
	Z_TEMP6 is Z_TEMP5+Z_amount,
	map(Z_dst,Z_TEMP6,Z_TEMP7),
	oplus(Z_bal,[Z_TEMP4,Z_TEMP7],Z_TEMP8),
	Z_bal_DECORV=Z_TEMP8,
	Z_pass_DECORV=Z_pass,
	Z_user_DECORV=Z_user,
	Z_report='ok',
	addChangeOp(Z_State,z_TransferOk,[va('Z_bal'),va('Z_pass'),va('Z_user')],Z_NewState),
	z_BANK(globalState(This,Z_NewState,Z_NewState),Z_bal_DECORV,Z_pass_DECORV,Z_log,Z_user_DECORV).

z_SameAcct(globalState(This,Z_State,Z_NewState),z_BANK(Z_bal,Z_pass,Z_log,Z_user),z_BANK(Z_bal,Z_pass,Z_log,Z_user),Z_amount,Z_src,Z_dst,Z_report):-
	Z_src=Z_dst,
	Z_report='same_account_for_src_and_dst',
	addChangeOp(Z_State,z_SameAcct,[],Z_NewState),
	z_BANK(globalState(This,Z_NewState,Z_NewState),Z_bal,Z_pass,Z_log,Z_user).

z_NotEnoughTransfer(globalState(This,Z_State,Z_NewState),z_BANK(Z_bal,Z_pass,Z_log,Z_user),z_BANK(Z_bal,Z_pass,Z_log,Z_user),Z_amount,Z_src,Z_dst,Z_report):-
	application(Z_bal,Z_src,Z_TEMP1),
	Z_TEMP1<Z_amount,
	Z_report='not_enough_money_in_src',
	addChangeOp(Z_State,z_NotEnoughTransfer,[],Z_NewState),
	z_BANK(globalState(This,Z_NewState,Z_NewState),Z_bal,Z_pass,Z_log,Z_user).

z_Transfer(globalState(This,Z_State,Z_NewState),z_BANK(Z_bal,Z_pass,Z_log,Z_user),z_BANK(Z_bal,Z_pass,Z_log,Z_user),Z_amount,Z_src,Z_dst,Z_report):-
	z_NotEnoughTransfer(globalState(This,Z_State,Z_TEMP1),z_BANK(Z_bal,Z_pass,Z_log,Z_user),z_BANK(Z_bal,Z_pass,Z_log,Z_user),Z_amount,Z_src,Z_dst,Z_report),
	addChangeOp(Z_TEMP1,z_Transfer,Z_NewState).

z_Transfer(globalState(This,Z_State,Z_NewState),z_BANK(Z_bal,Z_pass,Z_log,Z_user),z_BANK(Z_bal,Z_pass,Z_log,Z_user),Z_amount,Z_src,Z_dst,Z_report):-
	z_SameAcct(globalState(This,Z_State,Z_TEMP1),z_BANK(Z_bal,Z_pass,Z_log,Z_user),z_BANK(Z_bal,Z_pass,Z_log,Z_user),Z_amount,Z_src,Z_dst,Z_report),
	addChangeOp(Z_TEMP1,z_Transfer,Z_NewState).

z_Transfer(globalState(This,Z_State,Z_NewState),z_BANK(Z_bal,Z_pass,Z_log,Z_user),z_BANK(Z_bal_DECORV,Z_pass_DECORV,Z_log_DECORV,Z_user_DECORV),Z_amount,Z_src,Z_dst,Z_report):-
	z_TransferOk(globalState(This,Z_State,Z_TEMP1),z_BANK(Z_bal,Z_pass,Z_log,Z_user),z_BANK(Z_bal_DECORV,Z_pass_DECORV,Z_log_DECORV,Z_user_DECORV),Z_amount,Z_src,Z_dst,Z_report),
	addChangeOp(Z_TEMP1,z_Transfer,Z_NewState).

z_Credit(globalState(This,Z_State,Z_NewState),z_BANK(Z_bal,Z_pass,Z_log,Z_user),z_BANK(Z_bal_DECORV,Z_pass,Z_log,Z_user),Z_amount,Z_dst,Z_report):-
	application(Z_bal,Z_dst,Z_TEMP1),
	Z_TEMP2 is Z_TEMP1+Z_amount,
	map(Z_dst,Z_TEMP2,Z_TEMP3),
	oplus(Z_bal,[Z_TEMP3],Z_TEMP4),
	Z_bal_DECORV=Z_TEMP4,
	Z_report='ok',
	addChangeOp(Z_State,z_Credit,[va('Z_bal')],Z_NewState),
	z_BANK(globalState(This,Z_NewState,Z_NewState),Z_bal_DECORV,Z_pass,Z_log,Z_user).

z_WidthdrawOk(globalState(This,Z_State,Z_NewState),z_BANK(Z_bal,Z_pass,Z_log,Z_user),z_BANK(Z_bal_DECORV,Z_pass,Z_log,Z_user),Z_amount,Z_src,Z_report):-
	application(Z_bal,Z_src,Z_TEMP1),
	Z_TEMP1>=Z_amount,
	application(Z_bal,Z_src,Z_TEMP2),
	Z_TEMP3 is Z_TEMP2-Z_amount,
	map(Z_src,Z_TEMP3,Z_TEMP4),
	oplus(Z_bal,[Z_TEMP4],Z_TEMP5),
	Z_bal_DECORV=Z_TEMP5,
	Z_report='ok',
	addChangeOp(Z_State,z_WidthdrawOk,[va('Z_bal')],Z_NewState),
	z_BANK(globalState(This,Z_NewState,Z_NewState),Z_bal_DECORV,Z_pass,Z_log,Z_user).

z_NotEnough(globalState(This,Z_State,Z_NewState),z_BANK(Z_bal,Z_pass,Z_log,Z_user),z_BANK(Z_bal,Z_pass,Z_log,Z_user),Z_amount,Z_src,Z_report):-
	application(Z_bal,Z_src,Z_TEMP1),
	Z_TEMP1<Z_amount,
	Z_report='not_enough_money_in_src',
	addChangeOp(Z_State,z_NotEnough,[],Z_NewState),
	z_BANK(globalState(This,Z_NewState,Z_NewState),Z_bal,Z_pass,Z_log,Z_user).

z_Widthdraw(globalState(This,Z_State,Z_NewState),z_BANK(Z_bal,Z_pass,Z_log,Z_user),z_BANK(Z_bal,Z_pass,Z_log,Z_user),Z_amount,Z_src,Z_report):-
	z_NotEnough(globalState(This,Z_State,Z_TEMP1),z_BANK(Z_bal,Z_pass,Z_log,Z_user),z_BANK(Z_bal,Z_pass,Z_log,Z_user),Z_amount,Z_src,Z_report),
	addChangeOp(Z_TEMP1,z_Widthdraw,Z_NewState).

z_Widthdraw(globalState(This,Z_State,Z_NewState),z_BANK(Z_bal,Z_pass,Z_log,Z_user),z_BANK(Z_bal_DECORV,Z_pass_DECORV,Z_log_DECORV,Z_user_DECORV),Z_amount,Z_src,Z_report):-
	z_WidthdrawOk(globalState(This,Z_State,Z_TEMP1),z_BANK(Z_bal,Z_pass,Z_log,Z_user),z_BANK(Z_bal_DECORV,Z_pass_DECORV,Z_log_DECORV,Z_user_DECORV),Z_amount,Z_src,Z_report),
	addChangeOp(Z_TEMP1,z_Widthdraw,Z_NewState).

init_BANK_stateIni(tree('Root',[tree('BANK',['bal','pass','log','user'])])).
