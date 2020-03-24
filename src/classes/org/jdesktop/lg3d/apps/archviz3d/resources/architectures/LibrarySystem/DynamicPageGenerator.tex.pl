value(z_DynamicPageGenerator(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),'Z_input',Z_input).
setValue(z_DynamicPageGenerator(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),z_DynamicPageGenerator(Z_NEW_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),'Z_input',Z_NEW_input).
value(z_DynamicPageGenerator(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),'Z_output',Z_output).
setValue(z_DynamicPageGenerator(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),z_DynamicPageGenerator(Z_input,Z_NEW_output,Z_components,Z_connectors,Z_events,Z_ports),'Z_output',Z_NEW_output).
value(z_DynamicPageGenerator(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),'Z_components',Z_components).
setValue(z_DynamicPageGenerator(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),z_DynamicPageGenerator(Z_input,Z_output,Z_NEW_components,Z_connectors,Z_events,Z_ports),'Z_components',Z_NEW_components).
value(z_DynamicPageGenerator(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),'Z_connectors',Z_connectors).
setValue(z_DynamicPageGenerator(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),z_DynamicPageGenerator(Z_input,Z_output,Z_components,Z_NEW_connectors,Z_events,Z_ports),'Z_connectors',Z_NEW_connectors).
value(z_DynamicPageGenerator(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),'Z_events',Z_events).
setValue(z_DynamicPageGenerator(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),z_DynamicPageGenerator(Z_input,Z_output,Z_components,Z_connectors,Z_NEW_events,Z_ports),'Z_events',Z_NEW_events).
value(z_DynamicPageGenerator(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),'Z_ports',Z_ports).
setValue(z_DynamicPageGenerator(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),z_DynamicPageGenerator(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_NEW_ports),'Z_ports',Z_NEW_ports).

z_DynamicPageGenerator(globalState(This,Z_State,Z_State),Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports).
z_init_DynamicPageGenerator(globalState(This,Z_State,Z_NewState),z_DynamicPageGenerator(Z_input_DECORV,Z_output_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV)):-
	new(This,'Port',Z_TEMP2),
	Z_input_DECORV=Z_TEMP2,
	setIvar(Z_input_DECORV,'Z_element',This),
	new(This,'Port',Z_TEMP3),
	Z_output_DECORV=Z_TEMP3,
	setIvar(Z_output_DECORV,'Z_element',This),
	Z_components_DECORV=[],
	Z_connectors_DECORV=[],
	Z_events_DECORV=[],
	Z_ports_DECORV=[Z_input_DECORV,Z_output_DECORV]
	,addChangeOp(Z_State,z_init_DynamicPageGenerator,[va('Z_input'),va('Z_output'),va('Z_components'),va('Z_connectors'),va('Z_events'),va('Z_ports')],Z_NewState).

z_Generate(globalState(This,Z_State,Z_NewState),z_DynamicPageGenerator(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),z_DynamicPageGenerator(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=generatePage,
	head(Z_inputP,Z_TEMP1),
	R=Z_TEMP1,
	GeneratedPage=R,
	Z_report=GeneratedPage,
	addChangeOp(Z_State,z_Generate,[],Z_NewState),
	z_DynamicPageGenerator(globalState(This,Z_NewState,Z_NewState),Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_DynamicPageGenerator(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),z_DynamicPageGenerator(Z_input_DECORV,Z_output_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_Generate(globalState(This,Z_State,Z_TEMP1),z_DynamicPageGenerator(Z_input,Z_output,Z_components,Z_connectors,Z_events,Z_ports),z_DynamicPageGenerator(Z_input_DECORV,Z_output_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

init_DynamicPageGenerator_stateIni(tree('Root',[tree('DynamicPageGenerator',['input','output','components','connectors','events','ports'])])).
