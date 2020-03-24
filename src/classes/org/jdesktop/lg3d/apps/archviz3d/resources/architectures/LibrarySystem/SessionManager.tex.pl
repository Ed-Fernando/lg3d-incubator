value(z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),'Z_sessionsCount',Z_sessionsCount).
setValue(z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),z_SessionManager(Z_NEW_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),'Z_sessionsCount',Z_NEW_sessionsCount).
value(z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),'Z_sessions',Z_sessions).
setValue(z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),z_SessionManager(Z_sessionsCount,Z_NEW_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),'Z_sessions',Z_NEW_sessions).
value(z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),'Z_input',Z_input).
setValue(z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),z_SessionManager(Z_sessionsCount,Z_sessions,Z_NEW_input,Z_components,Z_connectors,Z_events,Z_ports),'Z_input',Z_NEW_input).
value(z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),'Z_components',Z_components).
setValue(z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_NEW_components,Z_connectors,Z_events,Z_ports),'Z_components',Z_NEW_components).
value(z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),'Z_connectors',Z_connectors).
setValue(z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_NEW_connectors,Z_events,Z_ports),'Z_connectors',Z_NEW_connectors).
value(z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),'Z_events',Z_events).
setValue(z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_NEW_events,Z_ports),'Z_events',Z_NEW_events).
value(z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),'Z_ports',Z_ports).
setValue(z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_NEW_ports),'Z_ports',Z_NEW_ports).

z_SessionManager(globalState(This,Z_State,Z_State),Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports).
z_init_SessionManager(globalState(This,Z_State,Z_NewState),z_SessionManager(Z_sessionsCount_DECORV,Z_sessions_DECORV,Z_input_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV)):-
	Z_sessionsCount_DECORV=1,
	new(This,'Port',Z_TEMP2),
	Z_input_DECORV=Z_TEMP2,
	setIvar(Z_input_DECORV,'Z_element',This),
	Z_sessions_DECORV=[],
	Z_components_DECORV=[],
	Z_connectors_DECORV=[],
	Z_ports_DECORV=[Z_input_DECORV],
	Z_events_DECORV=[]
	,addChangeOp(Z_State,z_init_SessionManager,[va('Z_sessionsCount'),va('Z_sessions'),va('Z_input'),va('Z_components'),va('Z_connectors'),va('Z_events'),va('Z_ports')],Z_NewState).

z_SessionsListing(globalState(This,Z_State,Z_NewState),z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=listing,
	Z_report=Z_sessions,
	addChangeOp(Z_State,z_SessionsListing,[],Z_NewState),
	z_SessionManager(globalState(This,Z_NewState,Z_NewState),Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports).

z_OpenSession(globalState(This,Z_State,Z_NewState),z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),z_SessionManager(Z_sessionsCount_DECORV,Z_sessions_DECORV,Z_input,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=openSession,
	head(Z_inputP,Z_TEMP1),
	Ip=Z_TEMP1,
	tail(Z_inputP,Z_TEMP2),
	head(Z_TEMP2,Z_TEMP3),
	Device=Z_TEMP3,
	User=Z_sessionsCount,
	Z_TEMP4 is Z_sessionsCount+1,
	Z_sessionsCount_DECORV=Z_TEMP4,
	uni(Z_sessions,[tuple(Ip,User,Device)],Z_TEMP5),
	Z_sessions_DECORV=Z_TEMP5,
	Z_report='ok',
	addChangeOp(Z_State,z_OpenSession,[va('Z_sessionsCount'),va('Z_sessions')],Z_NewState),
	z_SessionManager(globalState(This,Z_NewState,Z_NewState),Z_sessionsCount_DECORV,Z_sessions_DECORV,Z_input,Z_components,Z_connectors,Z_events,Z_ports).

z_CloseSession(globalState(This,Z_State,Z_NewState),z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),z_SessionManager(Z_sessionsCount_DECORV,Z_sessions_DECORV,Z_input,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=closeSession,
	head(Z_inputP,Z_TEMP1),
	Ip=Z_TEMP1,
fAll([	mem(X,Z_sessions),
	nth(tuple(X,1),Z_TEMP2),
	Z_TEMP2=Ip
],X,Z_TEMP3),
	Session=Z_TEMP3,
	head(Session,Z_TEMP4),
	nth(tuple(Z_TEMP4,1),Z_TEMP5),
	Ip1=Z_TEMP5,
	head(Session,Z_TEMP6),
	nth(tuple(Z_TEMP6,2),Z_TEMP7),
	User1=Z_TEMP7,
	head(Session,Z_TEMP8),
	nth(tuple(Z_TEMP8,3),Z_TEMP9),
	Device1=Z_TEMP9,
	setminus(Z_sessions,[tuple(Ip1,User1,Device1)],Z_TEMP10),
	Z_sessions_DECORV=Z_TEMP10,
	Z_TEMP11 is Z_sessionsCount-1,
	Z_sessionsCount_DECORV=Z_TEMP11,
	Z_report='ok',
	addChangeOp(Z_State,z_CloseSession,[va('Z_sessionsCount'),va('Z_sessions')],Z_NewState),
	z_SessionManager(globalState(This,Z_NewState,Z_NewState),Z_sessionsCount_DECORV,Z_sessions_DECORV,Z_input,Z_components,Z_connectors,Z_events,Z_ports).

z_CheckSessionInvalid(globalState(This,Z_State,Z_NewState),z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=checkSession,
	head(Z_inputP,Z_TEMP1),
	Ip=Z_TEMP1,
fAll([	mem(X,Z_sessions),
	nth(tuple(X,1),Z_TEMP2),
	Z_TEMP2=Ip
],X,Z_TEMP3),
	Session=Z_TEMP3,
	Session=[],
	Z_report='not_valid_connection_or_expired_session',
	addChangeOp(Z_State,z_CheckSessionInvalid,[],Z_NewState),
	z_SessionManager(globalState(This,Z_NewState,Z_NewState),Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports).

z_CheckSessionValid(globalState(This,Z_State,Z_NewState),z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=checkSession,
	head(Z_inputP,Z_TEMP1),
	Ip=Z_TEMP1,
fAll([	mem(X,Z_sessions),
	nth(tuple(X,1),Z_TEMP2),
	Z_TEMP2=Ip
],X,Z_TEMP3),
	Session=Z_TEMP3,
	Session\=[],
	Z_report='valid_connection_and_active_session',
	addChangeOp(Z_State,z_CheckSessionValid,[],Z_NewState),
	z_SessionManager(globalState(This,Z_NewState,Z_NewState),Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports).

z_SessionExpired(globalState(This,Z_State,Z_NewState),z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),z_SessionManager(Z_sessionsCount_DECORV,Z_sessions_DECORV,Z_input,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=timeElapsed,
	head(Z_inputP,Z_TEMP1),
	Ip=Z_TEMP1,
fAll([	mem(X,Z_sessions),
	nth(tuple(X,1),Z_TEMP2),
	Z_TEMP2=Ip
],X,Z_TEMP3),
	Session=Z_TEMP3,
	head(Session,Z_TEMP4),
	nth(tuple(Z_TEMP4,1),Z_TEMP5),
	Ip1=Z_TEMP5,
	head(Session,Z_TEMP6),
	nth(tuple(Z_TEMP6,2),Z_TEMP7),
	User1=Z_TEMP7,
	head(Session,Z_TEMP8),
	nth(tuple(Z_TEMP8,3),Z_TEMP9),
	Device1=Z_TEMP9,
	setminus(Z_sessions,[tuple(Ip1,User1,Device1)],Z_TEMP10),
	Z_sessions_DECORV=Z_TEMP10,
	Z_TEMP11 is Z_sessionsCount-1,
	Z_sessionsCount_DECORV=Z_TEMP11,
	Z_report='ok',
	addChangeOp(Z_State,z_SessionExpired,[va('Z_sessionsCount'),va('Z_sessions')],Z_NewState),
	z_SessionManager(globalState(This,Z_NewState,Z_NewState),Z_sessionsCount_DECORV,Z_sessions_DECORV,Z_input,Z_components,Z_connectors,Z_events,Z_ports).

z_GetSessionDevice(globalState(This,Z_State,Z_NewState),z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=getDevice,
	head(Z_inputP,Z_TEMP1),
	Ip=Z_TEMP1,
fAll([	mem(X,Z_sessions),
	nth(tuple(X,1),Z_TEMP2),
	Z_TEMP2=Ip
],X,Z_TEMP3),
	Device=Z_TEMP3,
	R=Device,
	Z_report=R,
	addChangeOp(Z_State,z_GetSessionDevice,[],Z_NewState),
	z_SessionManager(globalState(This,Z_NewState,Z_NewState),Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),z_SessionManager(Z_sessionsCount_DECORV,Z_sessions_DECORV,Z_input_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_SessionsListing(globalState(This,Z_State,Z_TEMP1),z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),z_SessionManager(Z_sessionsCount_DECORV,Z_sessions_DECORV,Z_input_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),z_SessionManager(Z_sessionsCount_DECORV,Z_sessions_DECORV,Z_input_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_CheckSessionValid(globalState(This,Z_State,Z_TEMP1),z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),z_SessionManager(Z_sessionsCount_DECORV,Z_sessions_DECORV,Z_input_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),z_SessionManager(Z_sessionsCount_DECORV,Z_sessions_DECORV,Z_input_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_CheckSessionInvalid(globalState(This,Z_State,Z_TEMP1),z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),z_SessionManager(Z_sessionsCount_DECORV,Z_sessions_DECORV,Z_input_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),z_SessionManager(Z_sessionsCount_DECORV,Z_sessions_DECORV,Z_input_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_OpenSession(globalState(This,Z_State,Z_TEMP1),z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),z_SessionManager(Z_sessionsCount_DECORV,Z_sessions_DECORV,Z_input_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),z_SessionManager(Z_sessionsCount_DECORV,Z_sessions_DECORV,Z_input_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_CloseSession(globalState(This,Z_State,Z_TEMP1),z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),z_SessionManager(Z_sessionsCount_DECORV,Z_sessions_DECORV,Z_input_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),z_SessionManager(Z_sessionsCount_DECORV,Z_sessions_DECORV,Z_input_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_SessionExpired(globalState(This,Z_State,Z_TEMP1),z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),z_SessionManager(Z_sessionsCount_DECORV,Z_sessions_DECORV,Z_input_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),z_SessionManager(Z_sessionsCount_DECORV,Z_sessions_DECORV,Z_input_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_GetSessionDevice(globalState(This,Z_State,Z_TEMP1),z_SessionManager(Z_sessionsCount,Z_sessions,Z_input,Z_components,Z_connectors,Z_events,Z_ports),z_SessionManager(Z_sessionsCount_DECORV,Z_sessions_DECORV,Z_input_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

init_SessionManager_stateIni(tree('Root',[tree('SessionManager',['sessionsCount','sessions','input','components','connectors','events','ports'])])).
