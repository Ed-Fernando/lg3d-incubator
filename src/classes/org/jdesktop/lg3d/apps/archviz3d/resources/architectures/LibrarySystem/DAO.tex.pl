value(z_DAO(Z_portLS,Z_callerDB,Z_components,Z_connectors,Z_events,Z_ports),'Z_portLS',Z_portLS).
setValue(z_DAO(Z_portLS,Z_callerDB,Z_components,Z_connectors,Z_events,Z_ports),z_DAO(Z_NEW_portLS,Z_callerDB,Z_components,Z_connectors,Z_events,Z_ports),'Z_portLS',Z_NEW_portLS).
value(z_DAO(Z_portLS,Z_callerDB,Z_components,Z_connectors,Z_events,Z_ports),'Z_callerDB',Z_callerDB).
setValue(z_DAO(Z_portLS,Z_callerDB,Z_components,Z_connectors,Z_events,Z_ports),z_DAO(Z_portLS,Z_NEW_callerDB,Z_components,Z_connectors,Z_events,Z_ports),'Z_callerDB',Z_NEW_callerDB).
value(z_DAO(Z_portLS,Z_callerDB,Z_components,Z_connectors,Z_events,Z_ports),'Z_components',Z_components).
setValue(z_DAO(Z_portLS,Z_callerDB,Z_components,Z_connectors,Z_events,Z_ports),z_DAO(Z_portLS,Z_callerDB,Z_NEW_components,Z_connectors,Z_events,Z_ports),'Z_components',Z_NEW_components).
value(z_DAO(Z_portLS,Z_callerDB,Z_components,Z_connectors,Z_events,Z_ports),'Z_connectors',Z_connectors).
setValue(z_DAO(Z_portLS,Z_callerDB,Z_components,Z_connectors,Z_events,Z_ports),z_DAO(Z_portLS,Z_callerDB,Z_components,Z_NEW_connectors,Z_events,Z_ports),'Z_connectors',Z_NEW_connectors).
value(z_DAO(Z_portLS,Z_callerDB,Z_components,Z_connectors,Z_events,Z_ports),'Z_events',Z_events).
setValue(z_DAO(Z_portLS,Z_callerDB,Z_components,Z_connectors,Z_events,Z_ports),z_DAO(Z_portLS,Z_callerDB,Z_components,Z_connectors,Z_NEW_events,Z_ports),'Z_events',Z_NEW_events).
value(z_DAO(Z_portLS,Z_callerDB,Z_components,Z_connectors,Z_events,Z_ports),'Z_ports',Z_ports).
setValue(z_DAO(Z_portLS,Z_callerDB,Z_components,Z_connectors,Z_events,Z_ports),z_DAO(Z_portLS,Z_callerDB,Z_components,Z_connectors,Z_events,Z_NEW_ports),'Z_ports',Z_NEW_ports).

z_DAO(globalState(This,Z_State,Z_State),Z_portLS,Z_callerDB,Z_components,Z_connectors,Z_events,Z_ports).
z_init_DAO(globalState(This,Z_State,Z_NewState),z_DAO(Z_portLS_DECORV,Z_callerDB_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV)):-
	new(This,'Port',Z_TEMP2),
	Z_portLS_DECORV=Z_TEMP2,
	setIvar(Z_portLS_DECORV,'Z_element',This),
	new(This,'Port',Z_TEMP3),
	Z_callerDB_DECORV=Z_TEMP3,
	setIvar(Z_callerDB_DECORV,'Z_element',This),
	Z_components_DECORV=[],
	Z_connectors_DECORV=[],
	Z_ports_DECORV=[Z_callerDB_DECORV,Z_portLS_DECORV],
	Z_events_DECORV=[]
	,addChangeOp(Z_State,z_init_DAO,[va('Z_portLS'),va('Z_callerDB'),va('Z_components'),va('Z_connectors'),va('Z_events'),va('Z_ports')],Z_NewState).

z_AddRecord(globalState(This,Z_State,Z_NewState),z_DAO(Z_portLS,Z_callerDB,Z_components,Z_connectors,Z_events,Z_ports),z_DAO(Z_portLS,Z_callerDB,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=addRecord,
	sendV(Z_callerDB,execute,['z_Initiate',[addRecord,This,Z_inputP,R]],_),
	R='ok',
	Z_report=R,
	addChangeOp(Z_State,z_AddRecord,[],Z_NewState),
	z_DAO(globalState(This,Z_NewState,Z_NewState),Z_portLS,Z_callerDB,Z_components,Z_connectors,Z_events,Z_ports).

z_Listing(globalState(This,Z_State,Z_NewState),z_DAO(Z_portLS,Z_callerDB,Z_components,Z_connectors,Z_events,Z_ports),z_DAO(Z_portLS,Z_callerDB,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=listing,
	sendV(Z_callerDB,execute,['z_Initiate',[listing,This,Z_inputP,R]],_),
	Z_report=R,
	addChangeOp(Z_State,z_Listing,[],Z_NewState),
	z_DAO(globalState(This,Z_NewState,Z_NewState),Z_portLS,Z_callerDB,Z_components,Z_connectors,Z_events,Z_ports).

z_SearchRecord(globalState(This,Z_State,Z_NewState),z_DAO(Z_portLS,Z_callerDB,Z_components,Z_connectors,Z_events,Z_ports),z_DAO(Z_portLS,Z_callerDB,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=searchRecord,
	sendV(Z_callerDB,execute,['z_Initiate',[searchRecord,This,Z_inputP,R]],_),
	Z_report=R,
	addChangeOp(Z_State,z_SearchRecord,[],Z_NewState),
	z_DAO(globalState(This,Z_NewState,Z_NewState),Z_portLS,Z_callerDB,Z_components,Z_connectors,Z_events,Z_ports).

z_DeleteRecord(globalState(This,Z_State,Z_NewState),z_DAO(Z_portLS,Z_callerDB,Z_components,Z_connectors,Z_events,Z_ports),z_DAO(Z_portLS,Z_callerDB,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=deleteRecord,
	sendV(Z_callerDB,execute,['z_Initiate',[deleteRecord,This,Z_inputP,R]],_),
	R='ok',
	Z_report=R,
	addChangeOp(Z_State,z_DeleteRecord,[],Z_NewState),
	z_DAO(globalState(This,Z_NewState,Z_NewState),Z_portLS,Z_callerDB,Z_components,Z_connectors,Z_events,Z_ports).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_DAO(Z_portLS,Z_callerDB,Z_components,Z_connectors,Z_events,Z_ports),z_DAO(Z_portLS_DECORV,Z_callerDB_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_AddRecord(globalState(This,Z_State,Z_TEMP1),z_DAO(Z_portLS,Z_callerDB,Z_components,Z_connectors,Z_events,Z_ports),z_DAO(Z_portLS_DECORV,Z_callerDB_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_DAO(Z_portLS,Z_callerDB,Z_components,Z_connectors,Z_events,Z_ports),z_DAO(Z_portLS_DECORV,Z_callerDB_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_SearchRecord(globalState(This,Z_State,Z_TEMP1),z_DAO(Z_portLS,Z_callerDB,Z_components,Z_connectors,Z_events,Z_ports),z_DAO(Z_portLS_DECORV,Z_callerDB_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_DAO(Z_portLS,Z_callerDB,Z_components,Z_connectors,Z_events,Z_ports),z_DAO(Z_portLS_DECORV,Z_callerDB_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_Listing(globalState(This,Z_State,Z_TEMP1),z_DAO(Z_portLS,Z_callerDB,Z_components,Z_connectors,Z_events,Z_ports),z_DAO(Z_portLS_DECORV,Z_callerDB_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_DAO(Z_portLS,Z_callerDB,Z_components,Z_connectors,Z_events,Z_ports),z_DAO(Z_portLS_DECORV,Z_callerDB_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_DeleteRecord(globalState(This,Z_State,Z_TEMP1),z_DAO(Z_portLS,Z_callerDB,Z_components,Z_connectors,Z_events,Z_ports),z_DAO(Z_portLS_DECORV,Z_callerDB_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

init_DAO_stateIni(tree('Root',[tree('DAO',['portLS','callerDB','components','connectors','events','ports'])])).
