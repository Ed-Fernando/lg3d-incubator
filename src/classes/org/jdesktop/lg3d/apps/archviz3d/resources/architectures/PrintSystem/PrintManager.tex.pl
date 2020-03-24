value(z_PrintManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),'Z_caller',Z_caller).
setValue(z_PrintManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_PrintManager(Z_NEW_caller,Z_called,Z_components,Z_connectors,Z_ports),'Z_caller',Z_NEW_caller).
value(z_PrintManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),'Z_called',Z_called).
setValue(z_PrintManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_PrintManager(Z_caller,Z_NEW_called,Z_components,Z_connectors,Z_ports),'Z_called',Z_NEW_called).
value(z_PrintManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),'Z_components',Z_components).
setValue(z_PrintManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_PrintManager(Z_caller,Z_called,Z_NEW_components,Z_connectors,Z_ports),'Z_components',Z_NEW_components).
value(z_PrintManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),'Z_connectors',Z_connectors).
setValue(z_PrintManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_PrintManager(Z_caller,Z_called,Z_components,Z_NEW_connectors,Z_ports),'Z_connectors',Z_NEW_connectors).
value(z_PrintManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),'Z_ports',Z_ports).
setValue(z_PrintManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_PrintManager(Z_caller,Z_called,Z_components,Z_connectors,Z_NEW_ports),'Z_ports',Z_NEW_ports).

z_PrintManager(globalState(This,Z_State,Z_State),Z_caller,Z_called,Z_components,Z_connectors,Z_ports).
z_init_PrintManager(globalState(This,Z_State,Z_NewState),z_PrintManager(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV)):-
	new(This,'Port',Z_TEMP2),
	Z_caller_DECORV=Z_TEMP2,
	setIvar(Z_caller_DECORV,'Z_element',This),
	new(This,'Port',Z_TEMP3),
	Z_called_DECORV=Z_TEMP3,
	setIvar(Z_called_DECORV,'Z_element',This),
	Z_components_DECORV=[],
	Z_connectors_DECORV=[],
	Z_ports_DECORV=[Z_caller_DECORV,Z_called_DECORV]
	,addChangeOp(Z_State,z_init_PrintManager,[va('Z_caller'),va('Z_called'),va('Z_components'),va('Z_connectors'),va('Z_ports')],Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_PrintManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),z_PrintManager(Z_caller,Z_called,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP).
init_PrintManager_stateIni(tree('Root',[tree('PrintManager',['caller','called','components','connectors','ports'])])).
