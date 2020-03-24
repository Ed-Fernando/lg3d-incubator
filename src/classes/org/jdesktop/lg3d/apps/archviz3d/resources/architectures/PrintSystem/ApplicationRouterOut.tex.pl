value(z_ApplicationRouterOut(Z_callerC,Z_callerS,Z_callerA,Z_called,Z_components,Z_connectors,Z_ports),'Z_callerC',Z_callerC).
setValue(z_ApplicationRouterOut(Z_callerC,Z_callerS,Z_callerA,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationRouterOut(Z_NEW_callerC,Z_callerS,Z_callerA,Z_called,Z_components,Z_connectors,Z_ports),'Z_callerC',Z_NEW_callerC).
value(z_ApplicationRouterOut(Z_callerC,Z_callerS,Z_callerA,Z_called,Z_components,Z_connectors,Z_ports),'Z_callerS',Z_callerS).
setValue(z_ApplicationRouterOut(Z_callerC,Z_callerS,Z_callerA,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationRouterOut(Z_callerC,Z_NEW_callerS,Z_callerA,Z_called,Z_components,Z_connectors,Z_ports),'Z_callerS',Z_NEW_callerS).
value(z_ApplicationRouterOut(Z_callerC,Z_callerS,Z_callerA,Z_called,Z_components,Z_connectors,Z_ports),'Z_callerA',Z_callerA).
setValue(z_ApplicationRouterOut(Z_callerC,Z_callerS,Z_callerA,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationRouterOut(Z_callerC,Z_callerS,Z_NEW_callerA,Z_called,Z_components,Z_connectors,Z_ports),'Z_callerA',Z_NEW_callerA).
value(z_ApplicationRouterOut(Z_callerC,Z_callerS,Z_callerA,Z_called,Z_components,Z_connectors,Z_ports),'Z_called',Z_called).
setValue(z_ApplicationRouterOut(Z_callerC,Z_callerS,Z_callerA,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationRouterOut(Z_callerC,Z_callerS,Z_callerA,Z_NEW_called,Z_components,Z_connectors,Z_ports),'Z_called',Z_NEW_called).
value(z_ApplicationRouterOut(Z_callerC,Z_callerS,Z_callerA,Z_called,Z_components,Z_connectors,Z_ports),'Z_components',Z_components).
setValue(z_ApplicationRouterOut(Z_callerC,Z_callerS,Z_callerA,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationRouterOut(Z_callerC,Z_callerS,Z_callerA,Z_called,Z_NEW_components,Z_connectors,Z_ports),'Z_components',Z_NEW_components).
value(z_ApplicationRouterOut(Z_callerC,Z_callerS,Z_callerA,Z_called,Z_components,Z_connectors,Z_ports),'Z_connectors',Z_connectors).
setValue(z_ApplicationRouterOut(Z_callerC,Z_callerS,Z_callerA,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationRouterOut(Z_callerC,Z_callerS,Z_callerA,Z_called,Z_components,Z_NEW_connectors,Z_ports),'Z_connectors',Z_NEW_connectors).
value(z_ApplicationRouterOut(Z_callerC,Z_callerS,Z_callerA,Z_called,Z_components,Z_connectors,Z_ports),'Z_ports',Z_ports).
setValue(z_ApplicationRouterOut(Z_callerC,Z_callerS,Z_callerA,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationRouterOut(Z_callerC,Z_callerS,Z_callerA,Z_called,Z_components,Z_connectors,Z_NEW_ports),'Z_ports',Z_NEW_ports).

z_ApplicationRouterOut(globalState(This,Z_State,Z_State),Z_callerC,Z_callerS,Z_callerA,Z_called,Z_components,Z_connectors,Z_ports).
z_init_ApplicationRouterOut(globalState(This,Z_State,Z_NewState),z_ApplicationRouterOut(Z_callerC_DECORV,Z_callerS_DECORV,Z_callerA_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV)):-
	new(This,'Port',Z_TEMP2),
	Z_callerC_DECORV=Z_TEMP2,
	setIvar(Z_callerC_DECORV,'Z_element',This),
	new(This,'Port',Z_TEMP3),
	Z_callerS_DECORV=Z_TEMP3,
	setIvar(Z_callerS_DECORV,'Z_element',This),
	new(This,'Port',Z_TEMP4),
	Z_callerA_DECORV=Z_TEMP4,
	setIvar(Z_callerA_DECORV,'Z_element',This),
	new(This,'Port',Z_TEMP5),
	Z_called_DECORV=Z_TEMP5,
	setIvar(Z_called_DECORV,'Z_element',This),
	Z_components_DECORV=[],
	Z_connectors_DECORV=[],
	Z_ports_DECORV=[Z_callerC_DECORV,Z_callerS_DECORV,Z_callerA_DECORV,Z_called_DECORV]
	,addChangeOp(Z_State,z_init_ApplicationRouterOut,[va('Z_callerC'),va('Z_callerS'),va('Z_callerA'),va('Z_called'),va('Z_components'),va('Z_connectors'),va('Z_ports')],Z_NewState).

z_ProcessEventRouter(globalState(This,Z_State,Z_NewState),z_ApplicationRouterOut(Z_callerC,Z_callerS,Z_callerA,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationRouterOut(Z_callerC,Z_callerS,Z_callerA,Z_called,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	sendV(Z_called,execute,['z_Initiate',[Z_event,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_ProcessEventRouter,[],Z_NewState),
	z_ApplicationRouterOut(globalState(This,Z_NewState,Z_NewState),Z_callerC,Z_callerS,Z_callerA,Z_called,Z_components,Z_connectors,Z_ports).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_ApplicationRouterOut(Z_callerC,Z_callerS,Z_callerA,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationRouterOut(Z_callerC_DECORV,Z_callerS_DECORV,Z_callerA_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_ProcessEventRouter(globalState(This,Z_State,Z_TEMP1),z_ApplicationRouterOut(Z_callerC,Z_callerS,Z_callerA,Z_called,Z_components,Z_connectors,Z_ports),z_ApplicationRouterOut(Z_callerC_DECORV,Z_callerS_DECORV,Z_callerA_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

init_ApplicationRouterOut_stateIni(tree('Root',[tree('ApplicationRouterOut',['callerC','callerS','callerA','called','components','connectors','ports'])])).
