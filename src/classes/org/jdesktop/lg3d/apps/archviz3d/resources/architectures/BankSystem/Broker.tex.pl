value(z_Broker(Z_definer,Z_services,Z_components,Z_connectors,Z_events,Z_ports),'Z_definer',Z_definer).
setValue(z_Broker(Z_definer,Z_services,Z_components,Z_connectors,Z_events,Z_ports),z_Broker(Z_NEW_definer,Z_services,Z_components,Z_connectors,Z_events,Z_ports),'Z_definer',Z_NEW_definer).
value(z_Broker(Z_definer,Z_services,Z_components,Z_connectors,Z_events,Z_ports),'Z_services',Z_services).
setValue(z_Broker(Z_definer,Z_services,Z_components,Z_connectors,Z_events,Z_ports),z_Broker(Z_definer,Z_NEW_services,Z_components,Z_connectors,Z_events,Z_ports),'Z_services',Z_NEW_services).
value(z_Broker(Z_definer,Z_services,Z_components,Z_connectors,Z_events,Z_ports),'Z_components',Z_components).
setValue(z_Broker(Z_definer,Z_services,Z_components,Z_connectors,Z_events,Z_ports),z_Broker(Z_definer,Z_services,Z_NEW_components,Z_connectors,Z_events,Z_ports),'Z_components',Z_NEW_components).
value(z_Broker(Z_definer,Z_services,Z_components,Z_connectors,Z_events,Z_ports),'Z_connectors',Z_connectors).
setValue(z_Broker(Z_definer,Z_services,Z_components,Z_connectors,Z_events,Z_ports),z_Broker(Z_definer,Z_services,Z_components,Z_NEW_connectors,Z_events,Z_ports),'Z_connectors',Z_NEW_connectors).
value(z_Broker(Z_definer,Z_services,Z_components,Z_connectors,Z_events,Z_ports),'Z_events',Z_events).
setValue(z_Broker(Z_definer,Z_services,Z_components,Z_connectors,Z_events,Z_ports),z_Broker(Z_definer,Z_services,Z_components,Z_connectors,Z_NEW_events,Z_ports),'Z_events',Z_NEW_events).
value(z_Broker(Z_definer,Z_services,Z_components,Z_connectors,Z_events,Z_ports),'Z_ports',Z_ports).
setValue(z_Broker(Z_definer,Z_services,Z_components,Z_connectors,Z_events,Z_ports),z_Broker(Z_definer,Z_services,Z_components,Z_connectors,Z_events,Z_NEW_ports),'Z_ports',Z_NEW_ports).

z_Broker(globalState(This,Z_State,Z_State),Z_definer,Z_services,Z_components,Z_connectors,Z_events,Z_ports).
z_init_Broker(globalState(This,Z_State,Z_NewState),z_Broker(Z_definer_DECORV,Z_services_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV)):-
	new(This,'Port',Z_TEMP2),
	Z_definer_DECORV=Z_TEMP2,
	setIvar(Z_definer_DECORV,'Z_element',This),
	Z_services_DECORV=[],
	Z_components_DECORV=[],
	Z_connectors_DECORV=[],
	Z_events_DECORV=[registry,lookUp],
	Z_ports_DECORV=[Z_definer_DECORV]
	,addChangeOp(Z_State,z_init_Broker,[va('Z_definer'),va('Z_services'),va('Z_components'),va('Z_connectors'),va('Z_events'),va('Z_ports')],Z_NewState).

z_ProcessEvent1(globalState(This,Z_State,Z_NewState),z_Broker(Z_definer,Z_services,Z_components,Z_connectors,Z_events,Z_ports),z_Broker(Z_definer,Z_services_DECORV,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=registry,
	head(Z_inputP,Z_TEMP1),
	Name=Z_TEMP1,
	tail(Z_inputP,Z_TEMP2),
	head(Z_TEMP2,Z_TEMP3),
	Component=Z_TEMP3,
	map(Name,Component,Z_TEMP4),
	oplus(Z_services,[Z_TEMP4],Z_TEMP5),
	Z_services_DECORV=Z_TEMP5,
	Z_outputP=ok,
	addChangeOp(Z_State,z_ProcessEvent1,[va('Z_services')],Z_NewState),
	z_Broker(globalState(This,Z_NewState,Z_NewState),Z_definer,Z_services_DECORV,Z_components,Z_connectors,Z_events,Z_ports).

z_ProcessEvent2(globalState(This,Z_State,Z_NewState),z_Broker(Z_definer,Z_services,Z_components,Z_connectors,Z_events,Z_ports),z_Broker(Z_definer,Z_services,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=lookUp,
	application(Z_services,Z_inputP,Z_TEMP1),
	Z_outputP=Z_TEMP1,
	addChangeOp(Z_State,z_ProcessEvent2,[],Z_NewState),
	z_Broker(globalState(This,Z_NewState,Z_NewState),Z_definer,Z_services,Z_components,Z_connectors,Z_events,Z_ports).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Broker(Z_definer,Z_services,Z_components,Z_connectors,Z_events,Z_ports),z_Broker(Z_definer_DECORV,Z_services_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_ProcessEvent1(globalState(This,Z_State,Z_TEMP1),z_Broker(Z_definer,Z_services,Z_components,Z_connectors,Z_events,Z_ports),z_Broker(Z_definer_DECORV,Z_services_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Broker(Z_definer,Z_services,Z_components,Z_connectors,Z_events,Z_ports),z_Broker(Z_definer_DECORV,Z_services_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_ProcessEvent2(globalState(This,Z_State,Z_TEMP1),z_Broker(Z_definer,Z_services,Z_components,Z_connectors,Z_events,Z_ports),z_Broker(Z_definer_DECORV,Z_services_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

init_Broker_stateIni(tree('Root',[tree('Broker',['definer','services','components','connectors','events','ports'])])).
