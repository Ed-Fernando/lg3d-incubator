value(z_Pipe(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),'Z_input',Z_input).
setValue(z_Pipe(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),z_Pipe(Z_NEW_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),'Z_input',Z_NEW_input).
value(z_Pipe(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),'Z_output',Z_output).
setValue(z_Pipe(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),z_Pipe(Z_input,Z_NEW_output,Z_components,Z_connectors,Z_events,Z_ports),'Z_output',Z_NEW_output).
value(z_Pipe(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),'Z_components',Z_components).
setValue(z_Pipe(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),z_Pipe(Z_input,Z_output,Z_NEW_components,Z_connectors,Z_events,Z_ports),'Z_components',Z_NEW_components).
value(z_Pipe(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),'Z_connectors',Z_connectors).
setValue(z_Pipe(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),z_Pipe(Z_input,Z_output,Z_components,Z_NEW_connectors,Z_events,Z_ports),'Z_connectors',Z_NEW_connectors).
value(z_Pipe(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),'Z_events',Z_events).
setValue(z_Pipe(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),z_Pipe(Z_input,Z_output,Z_components,Z_connectors,Z_NEW_events,Z_ports),'Z_events',Z_NEW_events).
value(z_Pipe(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),'Z_ports',Z_ports).
setValue(z_Pipe(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),z_Pipe(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_NEW_ports),'Z_ports',Z_NEW_ports).

z_Pipe(globalState(This,Z_State,Z_State),Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports):-
	0=0.

z_init_Pipe(globalState(This,Z_State,Z_NewState),z_Pipe(Z_input_DECORV,Z_output_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV)):-
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
	,addChangeOp(Z_State,z_init_Pipe,[va('Z_input'),va('Z_output'),va('Z_components'),va('Z_connectors'),va('Z_events'),va('Z_ports')],Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Pipe(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),z_Pipe(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=in,
	sendV(Z_output,command,['z_Initiate',[Z_event,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_ProcessEvent,[],Z_NewState),
	z_Pipe(globalState(This,Z_NewState,Z_NewState),Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports).

init_Pipe_stateIni(tree('Root',[tree('Pipe',['input','output','components','connectors','events','ports'])])).
