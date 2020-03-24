value(z_Firewall(Z_minIP,Z_maxIP,Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),'Z_minIP',Z_minIP).
setValue(z_Firewall(Z_minIP,Z_maxIP,Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),z_Firewall(Z_NEW_minIP,Z_maxIP,Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),'Z_minIP',Z_NEW_minIP).
value(z_Firewall(Z_minIP,Z_maxIP,Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),'Z_maxIP',Z_maxIP).
setValue(z_Firewall(Z_minIP,Z_maxIP,Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),z_Firewall(Z_minIP,Z_NEW_maxIP,Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),'Z_maxIP',Z_NEW_maxIP).
value(z_Firewall(Z_minIP,Z_maxIP,Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),'Z_input',Z_input).
setValue(z_Firewall(Z_minIP,Z_maxIP,Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),z_Firewall(Z_minIP,Z_maxIP,Z_NEW_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),'Z_input',Z_NEW_input).
value(z_Firewall(Z_minIP,Z_maxIP,Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),'Z_output',Z_output).
setValue(z_Firewall(Z_minIP,Z_maxIP,Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),z_Firewall(Z_minIP,Z_maxIP,Z_input,Z_NEW_output,Z_components,Z_connectors,Z_events,Z_ports),'Z_output',Z_NEW_output).
value(z_Firewall(Z_minIP,Z_maxIP,Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),'Z_components',Z_components).
setValue(z_Firewall(Z_minIP,Z_maxIP,Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),z_Firewall(Z_minIP,Z_maxIP,Z_input,Z_output,Z_NEW_components,Z_connectors,Z_events,Z_ports),'Z_components',Z_NEW_components).
value(z_Firewall(Z_minIP,Z_maxIP,Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),'Z_connectors',Z_connectors).
setValue(z_Firewall(Z_minIP,Z_maxIP,Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),z_Firewall(Z_minIP,Z_maxIP,Z_input,Z_output,Z_components,Z_NEW_connectors,Z_events,Z_ports),'Z_connectors',Z_NEW_connectors).
value(z_Firewall(Z_minIP,Z_maxIP,Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),'Z_events',Z_events).
setValue(z_Firewall(Z_minIP,Z_maxIP,Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),z_Firewall(Z_minIP,Z_maxIP,Z_input,Z_output,Z_components,Z_connectors,Z_NEW_events,Z_ports),'Z_events',Z_NEW_events).
value(z_Firewall(Z_minIP,Z_maxIP,Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),'Z_ports',Z_ports).
setValue(z_Firewall(Z_minIP,Z_maxIP,Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),z_Firewall(Z_minIP,Z_maxIP,Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_NEW_ports),'Z_ports',Z_NEW_ports).

z_Firewall(globalState(This,Z_State,Z_State),Z_minIP,Z_maxIP,Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports).
z_init_Firewall(globalState(This,Z_State,Z_NewState),z_Firewall(Z_minIP_DECORV,Z_maxIP_DECORV,Z_input_DECORV,Z_output_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV)):-
	new(This,'Port',Z_TEMP2),
	Z_input_DECORV=Z_TEMP2,
	setIvar(Z_input_DECORV,'Z_element',This),
	new(This,'Port',Z_TEMP3),
	Z_output_DECORV=Z_TEMP3,
	setIvar(Z_output_DECORV,'Z_element',This),
	Z_minIP_DECORV=1,
	Z_maxIP_DECORV=100,
	Z_components_DECORV=[],
	Z_connectors_DECORV=[],
	Z_events_DECORV=[checkConnection],
	Z_ports_DECORV=[Z_input_DECORV,Z_output_DECORV]
	,addChangeOp(Z_State,z_init_Firewall,[va('Z_minIP'),va('Z_maxIP'),va('Z_input'),va('Z_output'),va('Z_components'),va('Z_connectors'),va('Z_events'),va('Z_ports')],Z_NewState).

z_CheckConnectionInvalid(globalState(This,Z_State,Z_NewState),z_Firewall(Z_minIP,Z_maxIP,Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),z_Firewall(Z_minIP,Z_maxIP,Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=checkConnection,
	head(Z_inputP,Z_TEMP1),
	Ip=Z_TEMP1,
	Ip<Z_minIP,
	Z_report='not_valid_connection_or_expired_session',
	addChangeOp(Z_State,z_CheckConnectionInvalid,[],Z_NewState),
	z_Firewall(globalState(This,Z_NewState,Z_NewState),Z_minIP,Z_maxIP,Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports).

z_CheckConnectionInvalid(globalState(This,Z_State,Z_NewState),z_Firewall(Z_minIP,Z_maxIP,Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),z_Firewall(Z_minIP,Z_maxIP,Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=checkConnection,
	head(Z_inputP,Z_TEMP1),
	Ip=Z_TEMP1,
	Ip>Z_maxIP,
	Z_report='not_valid_connection_or_expired_session',
	addChangeOp(Z_State,z_CheckConnectionInvalid,[],Z_NewState),
	z_Firewall(globalState(This,Z_NewState,Z_NewState),Z_minIP,Z_maxIP,Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports).

z_CheckConnectionValid(globalState(This,Z_State,Z_NewState),z_Firewall(Z_minIP,Z_maxIP,Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),z_Firewall(Z_minIP,Z_maxIP,Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=checkConnection,
	head(Z_inputP,Z_TEMP1),
	Ip=Z_TEMP1,
	Ip>Z_minIP,
	Ip<Z_maxIP,
	Z_report='valid_connection',
	addChangeOp(Z_State,z_CheckConnectionValid,[],Z_NewState),
	z_Firewall(globalState(This,Z_NewState,Z_NewState),Z_minIP,Z_maxIP,Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Firewall(Z_minIP,Z_maxIP,Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),z_Firewall(Z_minIP_DECORV,Z_maxIP_DECORV,Z_input_DECORV,Z_output_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_CheckConnectionInvalid(globalState(This,Z_State,Z_TEMP1),z_Firewall(Z_minIP,Z_maxIP,Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),z_Firewall(Z_minIP_DECORV,Z_maxIP_DECORV,Z_input_DECORV,Z_output_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Firewall(Z_minIP,Z_maxIP,Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),z_Firewall(Z_minIP_DECORV,Z_maxIP_DECORV,Z_input_DECORV,Z_output_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_CheckConnectionValid(globalState(This,Z_State,Z_TEMP1),z_Firewall(Z_minIP,Z_maxIP,Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),z_Firewall(Z_minIP_DECORV,Z_maxIP_DECORV,Z_input_DECORV,Z_output_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

init_Firewall_stateIni(tree('Root',[tree('Firewall',['minIP','maxIP','input','output','components','connectors','events','ports'])])).
