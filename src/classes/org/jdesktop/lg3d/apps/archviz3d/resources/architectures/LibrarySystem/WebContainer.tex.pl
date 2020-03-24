value(z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),'Z_portLS',Z_portLS).
setValue(z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),z_WebContainer(Z_NEW_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),'Z_portLS',Z_NEW_portLS).
value(z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),'Z_fw',Z_fw).
setValue(z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),z_WebContainer(Z_portLS,Z_NEW_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),'Z_fw',Z_NEW_fw).
value(z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),'Z_dpg',Z_dpg).
setValue(z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),z_WebContainer(Z_portLS,Z_fw,Z_NEW_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),'Z_dpg',Z_NEW_dpg).
value(z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),'Z_wapF',Z_wapF).
setValue(z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_NEW_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),'Z_wapF',Z_NEW_wapF).
value(z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),'Z_htmlF',Z_htmlF).
setValue(z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_NEW_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),'Z_htmlF',Z_NEW_htmlF).
value(z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),'Z_sm',Z_sm).
setValue(z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_NEW_sm,Z_components,Z_connectors,Z_events,Z_ports),'Z_sm',Z_NEW_sm).
value(z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),'Z_components',Z_components).
setValue(z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_NEW_components,Z_connectors,Z_events,Z_ports),'Z_components',Z_NEW_components).
value(z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),'Z_connectors',Z_connectors).
setValue(z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_NEW_connectors,Z_events,Z_ports),'Z_connectors',Z_NEW_connectors).
value(z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),'Z_events',Z_events).
setValue(z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_NEW_events,Z_ports),'Z_events',Z_NEW_events).
value(z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),'Z_ports',Z_ports).
setValue(z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_NEW_ports),'Z_ports',Z_NEW_ports).

z_WebContainer(globalState(This,Z_State,Z_State),Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports).
z_init_WebContainer(globalState(This,Z_State,Z_NewState),z_WebContainer(Z_portLS_DECORV,Z_fw_DECORV,Z_dpg_DECORV,Z_wapF_DECORV,Z_htmlF_DECORV,Z_sm_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV)):-
	new(This,'Port',Z_TEMP2),
	Z_portLS_DECORV=Z_TEMP2,
	setIvar(Z_portLS_DECORV,'Z_element',This),
	new(This,'WAPFormatter',Z_TEMP3),
	Z_wapF_DECORV=Z_TEMP3,
	new(This,'HTMLFormatter',Z_TEMP4),
	Z_htmlF_DECORV=Z_TEMP4,
	new(This,'Firewall',Z_TEMP5),
	Z_fw_DECORV=Z_TEMP5,
	new(This,'SessionManager',Z_TEMP6),
	Z_sm_DECORV=Z_TEMP6,
	new(This,'DynamicPageGenerator',Z_TEMP7),
	Z_dpg_DECORV=Z_TEMP7,
	Z_components_DECORV=[Z_wapF_DECORV,Z_htmlF_DECORV,Z_fw_DECORV,Z_sm_DECORV,Z_dpg_DECORV],
	Z_connectors_DECORV=[],
	Z_events_DECORV=[gPage,openS,closeS],
	Z_ports_DECORV=[Z_portLS_DECORV]
	,addChangeOp(Z_State,z_init_WebContainer,[va('Z_portLS'),va('Z_fw'),va('Z_dpg'),va('Z_wapF'),va('Z_htmlF'),va('Z_sm'),va('Z_components'),va('Z_connectors'),va('Z_events'),va('Z_ports')],Z_NewState).

z_GenerateDynamicPageWAP(globalState(This,Z_State,Z_NewState),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=generatePage,
	head(Z_inputP,Z_TEMP1),
	Ip=Z_TEMP1,
	tail(Z_inputP,Z_TEMP2),
	head(Z_TEMP2,Z_TEMP3),
	Page=Z_TEMP3,
	SE=[Ip],
	sendV(Z_sm,execute,['z_ProcessEvent',[getDevice,This,SE,R]],_),
	head(R,Z_TEMP4),
	nth(tuple(Z_TEMP4,3),Z_TEMP5),
	Z_TEMP5='wap_Device',
	SE1=[Page],
	sendV(Z_dpg,execute,['z_ProcessEvent',[generatePage,This,SE1,PageGenerated]],_),
	SE2=[PageGenerated],
	sendV(Z_wapF,execute,['z_ProcessEvent',[format,This,SE2,PageFormatted]],_),
	Z_report=PageFormatted,
	addChangeOp(Z_State,z_GenerateDynamicPageWAP,[],Z_NewState),
	z_WebContainer(globalState(This,Z_NewState,Z_NewState),Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports).

z_GenerateDynamicPageHTML(globalState(This,Z_State,Z_NewState),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=generatePage,
	head(Z_inputP,Z_TEMP1),
	Ip=Z_TEMP1,
	tail(Z_inputP,Z_TEMP2),
	head(Z_TEMP2,Z_TEMP3),
	Page=Z_TEMP3,
	SE=[Ip],
	sendV(Z_sm,execute,['z_ProcessEvent',[getDevice,This,SE,R]],_),
	head(R,Z_TEMP4),
	nth(tuple(Z_TEMP4,3),Z_TEMP5),
	Z_TEMP5='html_Device',
	SE1=[Page],
	sendV(Z_dpg,execute,['z_ProcessEvent',[generatePage,This,SE1,PageGenerated]],_),
	SE2=[PageGenerated],
	sendV(Z_htmlF,execute,['z_ProcessEvent',[format,This,SE2,PageFormatted]],_),
	Z_report=PageFormatted,
	addChangeOp(Z_State,z_GenerateDynamicPageHTML,[],Z_NewState),
	z_WebContainer(globalState(This,Z_NewState,Z_NewState),Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports).

z_CheckConnection(globalState(This,Z_State,Z_NewState),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=checkConnection,
	sendV(Z_fw,execute,['z_ProcessEvent',[Z_event,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_CheckConnection,[],Z_NewState),
	z_WebContainer(globalState(This,Z_NewState,Z_NewState),Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports).

z_CheckConnectionValidSessionValid(globalState(This,Z_State,Z_NewState),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=checkConnectionSession,
	head(Z_inputP,Z_TEMP1),
	Ip=Z_TEMP1,
	SD=[Ip],
	sendV(Z_fw,execute,['z_ProcessEvent',[checkConnection,This,SD,R]],_),
	R='valid_connection',
	sendV(Z_sm,execute,['z_ProcessEvent',[checkSession,This,SD,RS]],_),
	RS='valid_connection_and_active_session',
	Z_report=RS,
	addChangeOp(Z_State,z_CheckConnectionValidSessionValid,[],Z_NewState),
	z_WebContainer(globalState(This,Z_NewState,Z_NewState),Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports).

z_CheckConnectionSessionInvalid(globalState(This,Z_State,Z_NewState),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=checkConnectionSession,
	head(Z_inputP,Z_TEMP1),
	Ip=Z_TEMP1,
	SD=[Ip],
	sendV(Z_fw,execute,['z_ProcessEvent',[checkConnection,This,SD,R]],_),
	R='not_valid_connection_or_expired_session',
	Z_report=R,
	addChangeOp(Z_State,z_CheckConnectionSessionInvalid,[],Z_NewState),
	z_WebContainer(globalState(This,Z_NewState,Z_NewState),Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports).

z_CheckConnectionValidSessionInvalid(globalState(This,Z_State,Z_NewState),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=checkConnectionSession,
	head(Z_inputP,Z_TEMP1),
	Ip=Z_TEMP1,
	SD=[Ip],
	sendV(Z_fw,execute,['z_ProcessEvent',[checkConnection,This,SD,R]],_),
	R='valid_connection',
	sendV(Z_sm,execute,['z_ProcessEvent',[checkSession,This,SD,RS]],_),
	RS='not_valid_connection_or_expired_session',
	Z_report=R,
	addChangeOp(Z_State,z_CheckConnectionValidSessionInvalid,[],Z_NewState),
	z_WebContainer(globalState(This,Z_NewState,Z_NewState),Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports).

z_OpenSession(globalState(This,Z_State,Z_NewState),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=openSession,
	sendV(Z_sm,execute,['z_ProcessEvent',[Z_event,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_OpenSession,[],Z_NewState),
	z_WebContainer(globalState(This,Z_NewState,Z_NewState),Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports).

z_CloseSession(globalState(This,Z_State,Z_NewState),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=closeSession,
	sendV(Z_sm,execute,['z_ProcessEvent',[Z_event,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_CloseSession,[],Z_NewState),
	z_WebContainer(globalState(This,Z_NewState,Z_NewState),Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),z_WebContainer(Z_portLS_DECORV,Z_fw_DECORV,Z_dpg_DECORV,Z_wapF_DECORV,Z_htmlF_DECORV,Z_sm_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_CheckConnectionValidSessionValid(globalState(This,Z_State,Z_TEMP1),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),z_WebContainer(Z_portLS_DECORV,Z_fw_DECORV,Z_dpg_DECORV,Z_wapF_DECORV,Z_htmlF_DECORV,Z_sm_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),z_WebContainer(Z_portLS_DECORV,Z_fw_DECORV,Z_dpg_DECORV,Z_wapF_DECORV,Z_htmlF_DECORV,Z_sm_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_CheckConnectionSessionInvalid(globalState(This,Z_State,Z_TEMP1),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),z_WebContainer(Z_portLS_DECORV,Z_fw_DECORV,Z_dpg_DECORV,Z_wapF_DECORV,Z_htmlF_DECORV,Z_sm_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),z_WebContainer(Z_portLS_DECORV,Z_fw_DECORV,Z_dpg_DECORV,Z_wapF_DECORV,Z_htmlF_DECORV,Z_sm_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_CheckConnectionValidSessionInvalid(globalState(This,Z_State,Z_TEMP1),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),z_WebContainer(Z_portLS_DECORV,Z_fw_DECORV,Z_dpg_DECORV,Z_wapF_DECORV,Z_htmlF_DECORV,Z_sm_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),z_WebContainer(Z_portLS_DECORV,Z_fw_DECORV,Z_dpg_DECORV,Z_wapF_DECORV,Z_htmlF_DECORV,Z_sm_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_CheckConnection(globalState(This,Z_State,Z_TEMP1),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),z_WebContainer(Z_portLS_DECORV,Z_fw_DECORV,Z_dpg_DECORV,Z_wapF_DECORV,Z_htmlF_DECORV,Z_sm_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),z_WebContainer(Z_portLS_DECORV,Z_fw_DECORV,Z_dpg_DECORV,Z_wapF_DECORV,Z_htmlF_DECORV,Z_sm_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_OpenSession(globalState(This,Z_State,Z_TEMP1),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),z_WebContainer(Z_portLS_DECORV,Z_fw_DECORV,Z_dpg_DECORV,Z_wapF_DECORV,Z_htmlF_DECORV,Z_sm_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),z_WebContainer(Z_portLS_DECORV,Z_fw_DECORV,Z_dpg_DECORV,Z_wapF_DECORV,Z_htmlF_DECORV,Z_sm_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_CloseSession(globalState(This,Z_State,Z_TEMP1),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),z_WebContainer(Z_portLS_DECORV,Z_fw_DECORV,Z_dpg_DECORV,Z_wapF_DECORV,Z_htmlF_DECORV,Z_sm_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),z_WebContainer(Z_portLS_DECORV,Z_fw_DECORV,Z_dpg_DECORV,Z_wapF_DECORV,Z_htmlF_DECORV,Z_sm_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_GenerateDynamicPageHTML(globalState(This,Z_State,Z_TEMP1),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),z_WebContainer(Z_portLS_DECORV,Z_fw_DECORV,Z_dpg_DECORV,Z_wapF_DECORV,Z_htmlF_DECORV,Z_sm_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),z_WebContainer(Z_portLS_DECORV,Z_fw_DECORV,Z_dpg_DECORV,Z_wapF_DECORV,Z_htmlF_DECORV,Z_sm_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_GenerateDynamicPageWAP(globalState(This,Z_State,Z_TEMP1),z_WebContainer(Z_portLS,Z_fw,Z_dpg,Z_wapF,Z_htmlF,Z_sm,Z_components,Z_connectors,Z_events,Z_ports),z_WebContainer(Z_portLS_DECORV,Z_fw_DECORV,Z_dpg_DECORV,Z_wapF_DECORV,Z_htmlF_DECORV,Z_sm_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

init_WebContainer_stateIni(tree('Root',[tree('WebContainer',['portLS','fw','dpg','wapF','htmlF','sm','components','connectors','events','ports'])])).
