value(z_DAO(Z_caller,Z_callerD,Z_called,Z_components,Z_connectors,Z_ports),'Z_caller',Z_caller).
setValue(z_DAO(Z_caller,Z_callerD,Z_called,Z_components,Z_connectors,Z_ports),z_DAO(Z_NEW_caller,Z_callerD,Z_called,Z_components,Z_connectors,Z_ports),'Z_caller',Z_NEW_caller).
value(z_DAO(Z_caller,Z_callerD,Z_called,Z_components,Z_connectors,Z_ports),'Z_callerD',Z_callerD).
setValue(z_DAO(Z_caller,Z_callerD,Z_called,Z_components,Z_connectors,Z_ports),z_DAO(Z_caller,Z_NEW_callerD,Z_called,Z_components,Z_connectors,Z_ports),'Z_callerD',Z_NEW_callerD).
value(z_DAO(Z_caller,Z_callerD,Z_called,Z_components,Z_connectors,Z_ports),'Z_called',Z_called).
setValue(z_DAO(Z_caller,Z_callerD,Z_called,Z_components,Z_connectors,Z_ports),z_DAO(Z_caller,Z_callerD,Z_NEW_called,Z_components,Z_connectors,Z_ports),'Z_called',Z_NEW_called).
value(z_DAO(Z_caller,Z_callerD,Z_called,Z_components,Z_connectors,Z_ports),'Z_components',Z_components).
setValue(z_DAO(Z_caller,Z_callerD,Z_called,Z_components,Z_connectors,Z_ports),z_DAO(Z_caller,Z_callerD,Z_called,Z_NEW_components,Z_connectors,Z_ports),'Z_components',Z_NEW_components).
value(z_DAO(Z_caller,Z_callerD,Z_called,Z_components,Z_connectors,Z_ports),'Z_connectors',Z_connectors).
setValue(z_DAO(Z_caller,Z_callerD,Z_called,Z_components,Z_connectors,Z_ports),z_DAO(Z_caller,Z_callerD,Z_called,Z_components,Z_NEW_connectors,Z_ports),'Z_connectors',Z_NEW_connectors).
value(z_DAO(Z_caller,Z_callerD,Z_called,Z_components,Z_connectors,Z_ports),'Z_ports',Z_ports).
setValue(z_DAO(Z_caller,Z_callerD,Z_called,Z_components,Z_connectors,Z_ports),z_DAO(Z_caller,Z_callerD,Z_called,Z_components,Z_connectors,Z_NEW_ports),'Z_ports',Z_NEW_ports).

z_DAO(globalState(This,Z_State,Z_State),Z_caller,Z_callerD,Z_called,Z_components,Z_connectors,Z_ports).
z_init_DAO(globalState(This,Z_State,Z_NewState),z_DAO(Z_caller_DECORV,Z_callerD_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV)):-
	new(This,'Port',Z_TEMP2),
	Z_caller_DECORV=Z_TEMP2,
	setIvar(Z_caller_DECORV,'Z_element',This),
	new(This,'Port',Z_TEMP3),
	Z_callerD_DECORV=Z_TEMP3,
	setIvar(Z_callerD_DECORV,'Z_element',This),
	new(This,'Port',Z_TEMP4),
	Z_called_DECORV=Z_TEMP4,
	setIvar(Z_called_DECORV,'Z_element',This),
	Z_components_DECORV=[],
	Z_connectors_DECORV=[],
	Z_events_DECORV=[],
	Z_ports_DECORV=[Z_caller_DECORV,Z_callerD_DECORV,Z_called_DECORV]
	,addChangeOp(Z_State,z_init_DAO,[va('Z_caller'),va('Z_callerD'),va('Z_called'),va('Z_components'),va('Z_connectors'),va('Z_ports')],Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_DAO(Z_caller,Z_callerD,Z_called,Z_components,Z_connectors,Z_ports),z_DAO(Z_caller,Z_callerD,Z_called,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	sendV(Z_called,execute,['z_Initiate',[Z_event,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_ProcessEvent,[],Z_NewState),
	z_DAO(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_callerD,Z_called,Z_components,Z_connectors,Z_ports).

init_DAO_stateIni(tree('Root',[tree('DAO',['caller','callerD','called','components','connectors','ports'])])).
