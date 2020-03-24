value(z_ClientAdministrator(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),'Z_caller',Z_caller).
setValue(z_ClientAdministrator(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ClientAdministrator(Z_NEW_caller,Z_called,Z_components,Z_connectors,Z_ports),'Z_caller',Z_NEW_caller).
value(z_ClientAdministrator(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),'Z_called',Z_called).
setValue(z_ClientAdministrator(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ClientAdministrator(Z_caller,Z_NEW_called,Z_components,Z_connectors,Z_ports),'Z_called',Z_NEW_called).
value(z_ClientAdministrator(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),'Z_components',Z_components).
setValue(z_ClientAdministrator(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ClientAdministrator(Z_caller,Z_called,Z_NEW_components,Z_connectors,Z_ports),'Z_components',Z_NEW_components).
value(z_ClientAdministrator(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),'Z_connectors',Z_connectors).
setValue(z_ClientAdministrator(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ClientAdministrator(Z_caller,Z_called,Z_components,Z_NEW_connectors,Z_ports),'Z_connectors',Z_NEW_connectors).
value(z_ClientAdministrator(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),'Z_ports',Z_ports).
setValue(z_ClientAdministrator(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ClientAdministrator(Z_caller,Z_called,Z_components,Z_connectors,Z_NEW_ports),'Z_ports',Z_NEW_ports).

z_ClientAdministrator(globalState(This,Z_State,Z_State),Z_caller,Z_called,Z_components,Z_connectors,Z_ports).
z_init_ClientAdministrator(globalState(This,Z_State,Z_NewState),z_ClientAdministrator(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV)):-
	new(This,'Port',Z_TEMP2),
	Z_caller_DECORV=Z_TEMP2,
	setIvar(Z_caller_DECORV,'Z_element',This),
	new(This,'Port',Z_TEMP3),
	Z_called_DECORV=Z_TEMP3,
	setIvar(Z_called_DECORV,'Z_element',This),
	Z_components_DECORV=[],
	Z_connectors_DECORV=[],
	Z_ports_DECORV=[Z_caller_DECORV,Z_called_DECORV]
	,addChangeOp(Z_State,z_init_ClientAdministrator,[va('Z_caller'),va('Z_called'),va('Z_components'),va('Z_connectors'),va('Z_ports')],Z_NewState).

z_AddUserOk(globalState(This,Z_State,Z_NewState),z_ClientAdministrator(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ClientAdministrator(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=addUser,
	head(Z_inputP,Z_TEMP1),
	Nick=Z_TEMP1,
	SE=[Nick],
	sendV(Z_called,execute,['z_Initiate',[validateUser,This,SE,VR]],_),
	VR='not_exist_user',
	sendV(Z_called,execute,['z_Initiate',[addUser,This,Z_inputP,R]],_),
	R='add_user_ok',
	Z_report='add_user_ok',
	addChangeOp(Z_State,z_AddUserOk,[],Z_NewState),
	z_ClientAdministrator(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_ports).

z_AddUserFailed(globalState(This,Z_State,Z_NewState),z_ClientAdministrator(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ClientAdministrator(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=addUser,
	head(Z_inputP,Z_TEMP1),
	Nick=Z_TEMP1,
	SE=[Nick],
	sendV(Z_called,execute,['z_Initiate',[validateUser,This,SE,VR]],_),
	VR='exist_user',
	Z_report='exist_user',
	addChangeOp(Z_State,z_AddUserFailed,[],Z_NewState),
	z_ClientAdministrator(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_ports).

z_DeleteUserOk(globalState(This,Z_State,Z_NewState),z_ClientAdministrator(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ClientAdministrator(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=deleteUser,
	sendV(Z_called,execute,['z_Initiate',[validateUser,This,Z_inputP,VR]],_),
	VR='exist_user',
	sendV(Z_called,execute,['z_Initiate',[deleteUser,This,Z_inputP,R]],_),
	R='delete_user_ok',
	Z_report='delete_user_ok',
	addChangeOp(Z_State,z_DeleteUserOk,[],Z_NewState),
	z_ClientAdministrator(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_ports).

z_DeleteUserFailed(globalState(This,Z_State,Z_NewState),z_ClientAdministrator(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ClientAdministrator(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=deleteUser,
	sendV(Z_called,execute,['z_Initiate',[validateUser,This,Z_inputP,VR]],_),
	VR='not_exist_user',
	Z_report='not_exist_user',
	addChangeOp(Z_State,z_DeleteUserFailed,[],Z_NewState),
	z_ClientAdministrator(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_ports).

z_VerifyPermission(globalState(This,Z_State,Z_NewState),z_ClientAdministrator(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ClientAdministrator(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=verifyPermission,
	sendV(Z_called,execute,['z_Initiate',[verifyPermission,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_VerifyPermission,[],Z_NewState),
	z_ClientAdministrator(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_ports).

z_ListUsers(globalState(This,Z_State,Z_NewState),z_ClientAdministrator(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ClientAdministrator(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=listUsers,
	sendV(Z_called,execute,['z_Initiate',[listUsers,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_ListUsers,[],Z_NewState),
	z_ClientAdministrator(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_ports).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_ClientAdministrator(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ClientAdministrator(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_AddUserOk(globalState(This,Z_State,Z_TEMP1),z_ClientAdministrator(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ClientAdministrator(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_ClientAdministrator(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ClientAdministrator(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_AddUserFailed(globalState(This,Z_State,Z_TEMP1),z_ClientAdministrator(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ClientAdministrator(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_ClientAdministrator(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ClientAdministrator(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_DeleteUserOk(globalState(This,Z_State,Z_TEMP1),z_ClientAdministrator(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ClientAdministrator(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_ClientAdministrator(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ClientAdministrator(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_DeleteUserFailed(globalState(This,Z_State,Z_TEMP1),z_ClientAdministrator(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ClientAdministrator(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_ClientAdministrator(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ClientAdministrator(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_VerifyPermission(globalState(This,Z_State,Z_TEMP1),z_ClientAdministrator(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ClientAdministrator(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_ClientAdministrator(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ClientAdministrator(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_ListUsers(globalState(This,Z_State,Z_TEMP1),z_ClientAdministrator(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_ClientAdministrator(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

init_ClientAdministrator_stateIni(tree('Root',[tree('ClientAdministrator',['caller','called','components','connectors','ports'])])).
