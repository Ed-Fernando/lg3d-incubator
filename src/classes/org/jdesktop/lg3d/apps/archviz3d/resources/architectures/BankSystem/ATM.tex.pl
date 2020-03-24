value(z_ATM(Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports),'Z_callerB',Z_callerB).
setValue(z_ATM(Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports),z_ATM(Z_NEW_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports),'Z_callerB',Z_NEW_callerB).
value(z_ATM(Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports),'Z_callerS',Z_callerS).
setValue(z_ATM(Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports),z_ATM(Z_callerB,Z_NEW_callerS,Z_user,Z_components,Z_connectors,Z_ports),'Z_callerS',Z_NEW_callerS).
value(z_ATM(Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports),'Z_user',Z_user).
setValue(z_ATM(Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports),z_ATM(Z_callerB,Z_callerS,Z_NEW_user,Z_components,Z_connectors,Z_ports),'Z_user',Z_NEW_user).
value(z_ATM(Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports),'Z_components',Z_components).
setValue(z_ATM(Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports),z_ATM(Z_callerB,Z_callerS,Z_user,Z_NEW_components,Z_connectors,Z_ports),'Z_components',Z_NEW_components).
value(z_ATM(Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports),'Z_connectors',Z_connectors).
setValue(z_ATM(Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports),z_ATM(Z_callerB,Z_callerS,Z_user,Z_components,Z_NEW_connectors,Z_ports),'Z_connectors',Z_NEW_connectors).
value(z_ATM(Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports),'Z_ports',Z_ports).
setValue(z_ATM(Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports),z_ATM(Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_NEW_ports),'Z_ports',Z_NEW_ports).

z_ATM(globalState(This,Z_State,Z_State),Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports).
z_init_ATM(globalState(This,Z_State,Z_NewState),z_ATM(Z_callerB_DECORV,Z_callerS_DECORV,Z_user_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV)):-
	Z_user_DECORV=0,
	new(This,'Port',Z_TEMP2),
	Z_callerB_DECORV=Z_TEMP2,
	setIvar(Z_callerB_DECORV,'Z_element',This),
	new(This,'Port',Z_TEMP3),
	Z_callerS_DECORV=Z_TEMP3,
	setIvar(Z_callerS_DECORV,'Z_element',This),
	Z_components_DECORV=[],
	Z_connectors_DECORV=[],
	Z_ports_DECORV=[Z_callerB_DECORV,Z_callerS_DECORV]
	,addChangeOp(Z_State,z_init_ATM,[va('Z_callerB'),va('Z_callerS'),va('Z_user'),va('Z_components'),va('Z_connectors'),va('Z_ports')],Z_NewState).

z_Connect(globalState(This,Z_State,Z_NewState),z_ATM(Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports),z_ATM(Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports)):-
	sendV(Z_callerB,execute,['z_Initiate',[lookUp,This,bankserver,Res]],_),
	new(This,'RPC',Z_TEMP1),
	Irpc=Z_TEMP1,
	ivar(Irpc,'Z_caller',Z_TEMP2),	setIvar(Z_callerS,'Z_next',Z_TEMP2),
	ivar(Irpc,'Z_caller',Z_TEMP3),	setIvar(Z_TEMP3,'Z_next',Z_callerS),
	ivar(Irpc,'Z_called',Z_TEMP4),	ivar(Res,'Z_definer',Z_TEMP5),	setIvar(Z_TEMP4,'Z_next',Z_TEMP5),
	addChangeOp(Z_State,z_Connect,[],Z_NewState),
	z_ATM(globalState(This,Z_NewState,Z_NewState),Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports).

z_SetUserOk(globalState(This,Z_State,Z_NewState),z_ATM(Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports),z_ATM(Z_callerB,Z_callerS,Z_user_DECORV,Z_components,Z_connectors,Z_ports),Z_id,Z_password,Z_report):-
	SE=[Z_id,Z_password],
	sendV(Z_callerS,execute,['z_Initiate',[validateUser,This,SE,R]],_),
	R='ok',
	Z_user_DECORV=Z_id,
	Z_report='ok',
	addChangeOp(Z_State,z_SetUserOk,[va('Z_user')],Z_NewState),
	z_ATM(globalState(This,Z_NewState,Z_NewState),Z_callerB,Z_callerS,Z_user_DECORV,Z_components,Z_connectors,Z_ports).

z_SetUserBad(globalState(This,Z_State,Z_NewState),z_ATM(Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports),z_ATM(Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports),Z_id,Z_password,Z_report):-
	SE=[Z_id,Z_password],
	sendV(Z_callerS,execute,['z_Initiate',[validateUser,This,SE,R]],_),
	R='not_user',
	Z_report=R,
	addChangeOp(Z_State,z_SetUserBad,[],Z_NewState),
	z_ATM(globalState(This,Z_NewState,Z_NewState),Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports).

z_getUser(globalState(This,Z_State,Z_NewState),z_ATM(Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports),z_ATM(Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports),Z_current):-
	Z_current=Z_user,
	addChangeOp(Z_State,z_getUser,[],Z_NewState),
	z_ATM(globalState(This,Z_NewState,Z_NewState),Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports).

z_SetUser(globalState(This,Z_State,Z_NewState),z_ATM(Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports),z_ATM(Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports),Z_id,Z_password,Z_report):-
	z_SetUserBad(globalState(This,Z_State,Z_TEMP1),z_ATM(Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports),z_ATM(Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports),Z_id,Z_password,Z_report),
	addChangeOp(Z_TEMP1,z_SetUser,Z_NewState).

z_SetUser(globalState(This,Z_State,Z_NewState),z_ATM(Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports),z_ATM(Z_callerB_DECORV,Z_callerS_DECORV,Z_user_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_id,Z_password,Z_report):-
	z_SetUserOk(globalState(This,Z_State,Z_TEMP1),z_ATM(Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports),z_ATM(Z_callerB_DECORV,Z_callerS_DECORV,Z_user_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_id,Z_password,Z_report),
	addChangeOp(Z_TEMP1,z_SetUser,Z_NewState).

z_EndUser(globalState(This,Z_State,Z_NewState),z_ATM(Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports),z_ATM(Z_callerB,Z_callerS,Z_user_DECORV,Z_components,Z_connectors,Z_ports),Z_report):-
	Z_user\=0,
	Z_user_DECORV=0,
	Z_report='ok',
	addChangeOp(Z_State,z_EndUser,[va('Z_user')],Z_NewState),
	z_ATM(globalState(This,Z_NewState,Z_NewState),Z_callerB,Z_callerS,Z_user_DECORV,Z_components,Z_connectors,Z_ports).

z_Balance(globalState(This,Z_State,Z_NewState),z_ATM(Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports),z_ATM(Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports),Z_src,Z_amount):-
	sendV(Z_callerS,execute,['z_Initiate',[balance,This,Z_src,R]],_),
	Z_amount=R,
	addChangeOp(Z_State,z_Balance,[],Z_NewState),
	z_ATM(globalState(This,Z_NewState,Z_NewState),Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports).

z_Transfer(globalState(This,Z_State,Z_NewState),z_ATM(Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports),z_ATM(Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports),Z_amount,Z_src,Z_dst,Z_report):-
	SE=[Z_amount,Z_src,Z_dst],
	sendV(Z_callerS,execute,['z_Initiate',[transfer,This,SE,R]],_),
	Z_report=R,
	addChangeOp(Z_State,z_Transfer,[],Z_NewState),
	z_ATM(globalState(This,Z_NewState,Z_NewState),Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports).

z_Credit(globalState(This,Z_State,Z_NewState),z_ATM(Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports),z_ATM(Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports),Z_amount,Z_dst,Z_report):-
	SE=[Z_amount,Z_dst],
	sendV(Z_callerS,execute,['z_Initiate',[credit,This,SE,R]],_),
	Z_report=R,
	addChangeOp(Z_State,z_Credit,[],Z_NewState),
	z_ATM(globalState(This,Z_NewState,Z_NewState),Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports).

z_Widthdraw(globalState(This,Z_State,Z_NewState),z_ATM(Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports),z_ATM(Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports),Z_amount,Z_src,Z_report):-
	SE=[Z_amount,Z_src],
	sendV(Z_callerS,execute,['z_Initiate',[widthdraw,This,SE,R]],_),
	Z_report=R,
	addChangeOp(Z_State,z_Widthdraw,[],Z_NewState),
	z_ATM(globalState(This,Z_NewState,Z_NewState),Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports).

z_Listing(globalState(This,Z_State,Z_NewState),z_ATM(Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports),z_ATM(Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports),Z_src,Z_amount):-
	sendV(Z_callerS,execute,['z_Initiate',[listing,This,Z_src,R]],_),
	Z_amount=R,
	addChangeOp(Z_State,z_Listing,[],Z_NewState),
	z_ATM(globalState(This,Z_NewState,Z_NewState),Z_callerB,Z_callerS,Z_user,Z_components,Z_connectors,Z_ports).

init_ATM_stateIni(tree('Root',[tree('ATM',['callerB','callerS','user','components','connectors','ports'])])).
