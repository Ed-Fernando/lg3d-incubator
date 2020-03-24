value(z_FilterOp(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),'Z_input',Z_input).
setValue(z_FilterOp(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),z_FilterOp(Z_NEW_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),'Z_input',Z_NEW_input).
value(z_FilterOp(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),'Z_output',Z_output).
setValue(z_FilterOp(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),z_FilterOp(Z_input,Z_NEW_output,Z_components,Z_connectors,Z_events,Z_ports),'Z_output',Z_NEW_output).
value(z_FilterOp(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),'Z_components',Z_components).
setValue(z_FilterOp(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),z_FilterOp(Z_input,Z_output,Z_NEW_components,Z_connectors,Z_events,Z_ports),'Z_components',Z_NEW_components).
value(z_FilterOp(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),'Z_connectors',Z_connectors).
setValue(z_FilterOp(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),z_FilterOp(Z_input,Z_output,Z_components,Z_NEW_connectors,Z_events,Z_ports),'Z_connectors',Z_NEW_connectors).
value(z_FilterOp(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),'Z_events',Z_events).
setValue(z_FilterOp(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),z_FilterOp(Z_input,Z_output,Z_components,Z_connectors,Z_NEW_events,Z_ports),'Z_events',Z_NEW_events).
value(z_FilterOp(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),'Z_ports',Z_ports).
setValue(z_FilterOp(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),z_FilterOp(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_NEW_ports),'Z_ports',Z_NEW_ports).

z_FilterOp(globalState(This,Z_State,Z_State),Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports).
z_init_FilterOp(globalState(This,Z_State,Z_NewState),z_FilterOp(Z_input_DECORV,Z_output_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV)):-
	new(This,'Port',Z_TEMP2),
	Z_input_DECORV=Z_TEMP2,
	setIvar(Z_input_DECORV,'Z_element',This),
	new(This,'Port',Z_TEMP3),
	Z_output_DECORV=Z_TEMP3,
	setIvar(Z_output_DECORV,'Z_element',This),
	Z_components_DECORV=[],
	Z_connectors_DECORV=[],
	Z_events_DECORV=[in],
	Z_ports_DECORV=[Z_input_DECORV,Z_output_DECORV]
	,addChangeOp(Z_State,z_init_FilterOp,[va('Z_input'),va('Z_output'),va('Z_components'),va('Z_connectors'),va('Z_events'),va('Z_ports')],Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_FilterOp(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),z_FilterOp(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=in,
fAll([	mem(X,Z_inputP),
	nth(tuple(X,2),Z_TEMP1),
	Z_TEMP1='deposit'
],X,Z_TEMP2),
	Report=Z_TEMP2,
	sendV(Z_output,execute,['z_Initiate',[Z_event,This,Report,Z_outputP]],_),
	addChangeOp(Z_State,z_ProcessEvent,[],Z_NewState),
	z_FilterOp(globalState(This,Z_NewState,Z_NewState),Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports).

init_FilterOp_stateIni(tree('Root',[tree('FilterOp',['input','output','components','connectors','events','ports'])])).
