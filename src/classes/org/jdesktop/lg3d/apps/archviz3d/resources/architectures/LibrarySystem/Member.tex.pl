value(z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),'Z_ip',Z_ip).
setValue(z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_NEW_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),'Z_ip',Z_NEW_ip).
value(z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),'Z_device',Z_device).
setValue(z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip,Z_NEW_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),'Z_device',Z_NEW_device).
value(z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),'Z_callerSU',Z_callerSU).
setValue(z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip,Z_device,Z_NEW_callerSU,Z_components,Z_connectors,Z_events,Z_ports),'Z_callerSU',Z_NEW_callerSU).
value(z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),'Z_components',Z_components).
setValue(z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip,Z_device,Z_callerSU,Z_NEW_components,Z_connectors,Z_events,Z_ports),'Z_components',Z_NEW_components).
value(z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),'Z_connectors',Z_connectors).
setValue(z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_NEW_connectors,Z_events,Z_ports),'Z_connectors',Z_NEW_connectors).
value(z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),'Z_events',Z_events).
setValue(z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_NEW_events,Z_ports),'Z_events',Z_NEW_events).
value(z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),'Z_ports',Z_ports).
setValue(z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_NEW_ports),'Z_ports',Z_NEW_ports).

z_Member(globalState(This,Z_State,Z_State),Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports).
z_init_Member(globalState(This,Z_State,Z_NewState),z_Member(Z_ip_DECORV,Z_device_DECORV,Z_callerSU_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV)):-
	Z_ip_DECORV=0,
	Z_device_DECORV=0,
	new(This,'Port',Z_TEMP2),
	Z_callerSU_DECORV=Z_TEMP2,
	setIvar(Z_callerSU_DECORV,'Z_element',This),
	Z_components_DECORV=[],
	Z_connectors_DECORV=[],
	Z_events_DECORV=[],
	Z_ports_DECORV=[Z_callerSU_DECORV]
	,addChangeOp(Z_State,z_init_Member,[va('Z_ip'),va('Z_device'),va('Z_callerSU'),va('Z_components'),va('Z_connectors'),va('Z_events'),va('Z_ports')],Z_NewState).

z_LoginMemberOK(globalState(This,Z_State,Z_NewState),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=login,
	head(Z_inputP,Z_TEMP1),
	Id=Z_TEMP1,
	tail(Z_inputP,Z_TEMP2),
	head(Z_TEMP2,Z_TEMP3),
	Password=Z_TEMP3,
	SE=[Z_ip,Id,Password,Z_device],
	sendV(Z_callerSU,execute,['z_Initiate',[validateUser,This,SE,R]],_),
	R='ok',
	Z_report=R,
	addChangeOp(Z_State,z_LoginMemberOK,[],Z_NewState),
	z_Member(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports).

z_LoginMemberFailed(globalState(This,Z_State,Z_NewState),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=login,
	head(Z_inputP,Z_TEMP1),
	Id=Z_TEMP1,
	tail(Z_inputP,Z_TEMP2),
	head(Z_TEMP2,Z_TEMP3),
	Password=Z_TEMP3,
	SE=[Z_ip,Id,Password,Z_device],
	sendV(Z_callerSU,execute,['z_Initiate',[validateUser,This,SE,R]],_),
	R='not_valid_user',
	Z_report=R,
	addChangeOp(Z_State,z_LoginMemberFailed,[],Z_NewState),
	z_Member(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports).

z_LoginMemberInvalidConnection(globalState(This,Z_State,Z_NewState),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=login,
	head(Z_inputP,Z_TEMP1),
	Id=Z_TEMP1,
	SE=[Ip],
	sendV(Z_callerSU,execute,['z_Initiate',[validateUser,This,SE,R]],_),
	R='not_valid_connection_or_expired_session',
	Z_report=R,
	addChangeOp(Z_State,z_LoginMemberInvalidConnection,[],Z_NewState),
	z_Member(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports).

z_LoginMemberInexistent(globalState(This,Z_State,Z_NewState),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=login,
	head(Z_inputP,Z_TEMP1),
	Id=Z_TEMP1,
	tail(Z_inputP,Z_TEMP2),
	head(Z_TEMP2,Z_TEMP3),
	Password=Z_TEMP3,
	SE=[Z_ip,Id,Password],
	sendV(Z_callerSU,execute,['z_Initiate',[validateUser,This,SE,R]],_),
	R='inexistent_record',
	Z_report=R,
	addChangeOp(Z_State,z_LoginMemberInexistent,[],Z_NewState),
	z_Member(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports).

z_AssignIP(globalState(This,Z_State,Z_NewState),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip_DECORV,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=assignIP,
	head(Z_inputP,Z_TEMP1),
	IpAssigned=Z_TEMP1,
	Z_ip_DECORV=IpAssigned,
	Z_report='ok',
	addChangeOp(Z_State,z_AssignIP,[va('Z_ip')],Z_NewState),
	z_Member(globalState(This,Z_NewState,Z_NewState),Z_ip_DECORV,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports).

z_GetIP(globalState(This,Z_State,Z_NewState),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=getIP,
	Z_report=Z_ip,
	addChangeOp(Z_State,z_GetIP,[],Z_NewState),
	z_Member(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports).

z_AssignDevice(globalState(This,Z_State,Z_NewState),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip,Z_device_DECORV,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=assignDevice,
	head(Z_inputP,Z_TEMP1),
	DeviceAssigned=Z_TEMP1,
	Z_device_DECORV=DeviceAssigned,
	Z_report='ok',
	addChangeOp(Z_State,z_AssignDevice,[va('Z_device')],Z_NewState),
	z_Member(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device_DECORV,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports).

z_GetDevice(globalState(This,Z_State,Z_NewState),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=getDevice,
	Z_report=Z_device,
	addChangeOp(Z_State,z_GetDevice,[],Z_NewState),
	z_Member(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports).

z_Logout(globalState(This,Z_State,Z_NewState),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip_DECORV,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=logout,
	SD=[Z_ip],
	sendV(Z_callerSU,execute,['z_Initiate',[logout,This,SD,R]],_),
	R='ok',
	Z_ip_DECORV=0,
	Z_report=R,
	addChangeOp(Z_State,z_Logout,[va('Z_ip')],Z_NewState),
	z_Member(globalState(This,Z_NewState,Z_NewState),Z_ip_DECORV,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports).

z_SearchBookOK(globalState(This,Z_State,Z_NewState),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=searchBook,
	head(Z_inputP,Z_TEMP1),
	BookName=Z_TEMP1,
	SE=[Z_ip,BookName],
	sendV(Z_callerSU,execute,['z_Initiate',[searchBook,This,SE,R]],_),
	R='ok',
	Z_report=R,
	addChangeOp(Z_State,z_SearchBookOK,[],Z_NewState),
	z_Member(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports).

z_SearchBookInvalidConnection(globalState(This,Z_State,Z_NewState),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=searchBook,
	SE=[Z_ip],
	sendV(Z_callerSU,execute,['z_Initiate',[searchBook,This,SE,R]],_),
	R='not_valid_connection_or_expired_session',
	Z_report=R,
	addChangeOp(Z_State,z_SearchBookInvalidConnection,[],Z_NewState),
	z_Member(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip_DECORV,Z_device_DECORV,Z_callerSU_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_AssignIP(globalState(This,Z_State,Z_TEMP1),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip_DECORV,Z_device_DECORV,Z_callerSU_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip_DECORV,Z_device_DECORV,Z_callerSU_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_GetIP(globalState(This,Z_State,Z_TEMP1),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip_DECORV,Z_device_DECORV,Z_callerSU_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip_DECORV,Z_device_DECORV,Z_callerSU_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_AssignDevice(globalState(This,Z_State,Z_TEMP1),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip_DECORV,Z_device_DECORV,Z_callerSU_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip_DECORV,Z_device_DECORV,Z_callerSU_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_GetDevice(globalState(This,Z_State,Z_TEMP1),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip_DECORV,Z_device_DECORV,Z_callerSU_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip_DECORV,Z_device_DECORV,Z_callerSU_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_LoginMemberOK(globalState(This,Z_State,Z_TEMP1),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip_DECORV,Z_device_DECORV,Z_callerSU_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip_DECORV,Z_device_DECORV,Z_callerSU_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_LoginMemberFailed(globalState(This,Z_State,Z_TEMP1),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip_DECORV,Z_device_DECORV,Z_callerSU_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip_DECORV,Z_device_DECORV,Z_callerSU_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_LoginMemberInvalidConnection(globalState(This,Z_State,Z_TEMP1),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip_DECORV,Z_device_DECORV,Z_callerSU_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip_DECORV,Z_device_DECORV,Z_callerSU_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_LoginMemberInexistent(globalState(This,Z_State,Z_TEMP1),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip_DECORV,Z_device_DECORV,Z_callerSU_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip_DECORV,Z_device_DECORV,Z_callerSU_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_Logout(globalState(This,Z_State,Z_TEMP1),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip_DECORV,Z_device_DECORV,Z_callerSU_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	z_SearchBookOK(globalState(This,Z_State,Z_TEMP1),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	z_SearchBookInvalidConnection(globalState(This,Z_State,Z_TEMP1),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),z_Member(Z_ip,Z_device,Z_callerSU,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

init_Member_stateIni(tree('Root',[tree('Member',['ip','device','callerSU','components','connectors','events','ports'])])).
