value(z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),'Z_callerC',Z_callerC).
setValue(z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_NEW_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),'Z_callerC',Z_NEW_callerC).
value(z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),'Z_callerS',Z_callerS).
setValue(z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC,Z_NEW_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),'Z_callerS',Z_NEW_callerS).
value(z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),'Z_callerA',Z_callerA).
setValue(z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC,Z_callerS,Z_NEW_callerA,Z_caller,Z_components,Z_connectors,Z_ports),'Z_callerA',Z_NEW_callerA).
value(z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),'Z_caller',Z_caller).
setValue(z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_NEW_caller,Z_components,Z_connectors,Z_ports),'Z_caller',Z_NEW_caller).
value(z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),'Z_components',Z_components).
setValue(z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_NEW_components,Z_connectors,Z_ports),'Z_components',Z_NEW_components).
value(z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),'Z_connectors',Z_connectors).
setValue(z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_NEW_connectors,Z_ports),'Z_connectors',Z_NEW_connectors).
value(z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),'Z_ports',Z_ports).
setValue(z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_NEW_ports),'Z_ports',Z_NEW_ports).

z_ApplicationRouter(globalState(This,Z_State,Z_State),Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports).
z_init_ApplicationRouter(globalState(This,Z_State,Z_NewState),z_ApplicationRouter(Z_callerC_DECORV,Z_callerS_DECORV,Z_callerA_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV)):-
	new(This,'Port',Z_TEMP2),
	Z_callerC_DECORV=Z_TEMP2,
	setIvar(Z_callerC_DECORV,'Z_element',This),
	new(This,'Port',Z_TEMP3),
	Z_callerS_DECORV=Z_TEMP3,
	setIvar(Z_callerS_DECORV,'Z_element',This),
	new(This,'Port',Z_TEMP4),
	Z_callerA_DECORV=Z_TEMP4,
	setIvar(Z_callerA_DECORV,'Z_element',This),
	new(This,'Port',Z_TEMP5),
	Z_caller_DECORV=Z_TEMP5,
	setIvar(Z_caller_DECORV,'Z_element',This),
	Z_components_DECORV=[],
	Z_connectors_DECORV=[],
	Z_ports_DECORV=[Z_callerC_DECORV,Z_callerS_DECORV,Z_callerA_DECORV,Z_caller_DECORV]
	,addChangeOp(Z_State,z_init_ApplicationRouter,[va('Z_callerC'),va('Z_callerS'),va('Z_callerA'),va('Z_caller'),va('Z_components'),va('Z_connectors'),va('Z_ports')],Z_NewState).

z_ProcessEventAddUser(globalState(This,Z_State,Z_NewState),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=addUser,
	sendV(Z_callerC,execute,['z_Initiate',[addUser,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_ProcessEventAddUser,[],Z_NewState),
	z_ApplicationRouter(globalState(This,Z_NewState,Z_NewState),Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports).

z_ProcessEventDeleteUser(globalState(This,Z_State,Z_NewState),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=deleteUser,
	sendV(Z_callerC,execute,['z_Initiate',[deleteUser,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_ProcessEventDeleteUser,[],Z_NewState),
	z_ApplicationRouter(globalState(This,Z_NewState,Z_NewState),Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports).

z_ProcessEventVerifyPermission(globalState(This,Z_State,Z_NewState),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=verifyPermission,
	sendV(Z_callerC,execute,['z_Initiate',[verifyPermission,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_ProcessEventVerifyPermission,[],Z_NewState),
	z_ApplicationRouter(globalState(This,Z_NewState,Z_NewState),Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports).

z_ProcessEventListUsers(globalState(This,Z_State,Z_NewState),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=listUsers,
	sendV(Z_callerC,execute,['z_Initiate',[listUsers,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_ProcessEventListUsers,[],Z_NewState),
	z_ApplicationRouter(globalState(This,Z_NewState,Z_NewState),Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports).

z_ProcessEventListPrinters(globalState(This,Z_State,Z_NewState),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=listPrinters,
	sendV(Z_callerA,execute,['z_Initiate',[listPrinters,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_ProcessEventListPrinters,[],Z_NewState),
	z_ApplicationRouter(globalState(This,Z_NewState,Z_NewState),Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports).

z_ProcessEventVerifyPrinter(globalState(This,Z_State,Z_NewState),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=verifyPrinter,
	sendV(Z_callerA,execute,['z_Initiate',[verifyPrinter,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_ProcessEventVerifyPrinter,[],Z_NewState),
	z_ApplicationRouter(globalState(This,Z_NewState,Z_NewState),Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports).

z_ProcessEventPrinterState(globalState(This,Z_State,Z_NewState),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=printerState,
	sendV(Z_callerA,execute,['z_Initiate',[printerState,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_ProcessEventPrinterState,[],Z_NewState),
	z_ApplicationRouter(globalState(This,Z_NewState,Z_NewState),Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports).

z_ProcessEventFirstPrinterAvailable(globalState(This,Z_State,Z_NewState),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=firstPrinterAvailable,
	sendV(Z_callerA,execute,['z_Initiate',[firstPrinterAvailable,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_ProcessEventFirstPrinterAvailable,[],Z_NewState),
	z_ApplicationRouter(globalState(This,Z_NewState,Z_NewState),Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports).

z_ProcessEventFirstPrinterOccupied(globalState(This,Z_State,Z_NewState),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=firstPrinterOccupied,
	sendV(Z_callerA,execute,['z_Initiate',[firstPrinterOccupied,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_ProcessEventFirstPrinterOccupied,[],Z_NewState),
	z_ApplicationRouter(globalState(This,Z_NewState,Z_NewState),Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports).

z_AddPrinter(globalState(This,Z_State,Z_NewState),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=addPrinter,
	sendV(Z_callerA,execute,['z_Initiate',[addPrinter,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_AddPrinter,[],Z_NewState),
	z_ApplicationRouter(globalState(This,Z_NewState,Z_NewState),Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports).

z_DeletePrinter(globalState(This,Z_State,Z_NewState),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=deletePrinter,
	sendV(Z_callerA,execute,['z_Initiate',[deletePrinter,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_DeletePrinter,[],Z_NewState),
	z_ApplicationRouter(globalState(This,Z_NewState,Z_NewState),Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports).

z_ProcessEventJobToPrinter(globalState(This,Z_State,Z_NewState),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=jobToPrinter,
	sendV(Z_callerA,execute,['z_Initiate',[jobToPrinter,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_ProcessEventJobToPrinter,[],Z_NewState),
	z_ApplicationRouter(globalState(This,Z_NewState,Z_NewState),Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports).

z_ProcessEventJobToPrintersPool(globalState(This,Z_State,Z_NewState),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),Z_event,Z_instance,Z_inputP,Z_outputP):-
	Z_event=jobToPrintersPool,
	sendV(Z_callerA,execute,['z_Initiate',[jobToPrintersPool,This,Z_inputP,Z_outputP]],_),
	addChangeOp(Z_State,z_ProcessEventJobToPrintersPool,[],Z_NewState),
	z_ApplicationRouter(globalState(This,Z_NewState,Z_NewState),Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC_DECORV,Z_callerS_DECORV,Z_callerA_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_ProcessEventAddUser(globalState(This,Z_State,Z_TEMP1),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC_DECORV,Z_callerS_DECORV,Z_callerA_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC_DECORV,Z_callerS_DECORV,Z_callerA_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_ProcessEventDeleteUser(globalState(This,Z_State,Z_TEMP1),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC_DECORV,Z_callerS_DECORV,Z_callerA_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC_DECORV,Z_callerS_DECORV,Z_callerA_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_ProcessEventVerifyPermission(globalState(This,Z_State,Z_TEMP1),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC_DECORV,Z_callerS_DECORV,Z_callerA_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC_DECORV,Z_callerS_DECORV,Z_callerA_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_ProcessEventListUsers(globalState(This,Z_State,Z_TEMP1),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC_DECORV,Z_callerS_DECORV,Z_callerA_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC_DECORV,Z_callerS_DECORV,Z_callerA_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_ProcessEventListPrinters(globalState(This,Z_State,Z_TEMP1),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC_DECORV,Z_callerS_DECORV,Z_callerA_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC_DECORV,Z_callerS_DECORV,Z_callerA_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_ProcessEventVerifyPrinter(globalState(This,Z_State,Z_TEMP1),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC_DECORV,Z_callerS_DECORV,Z_callerA_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC_DECORV,Z_callerS_DECORV,Z_callerA_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_ProcessEventPrinterState(globalState(This,Z_State,Z_TEMP1),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC_DECORV,Z_callerS_DECORV,Z_callerA_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC_DECORV,Z_callerS_DECORV,Z_callerA_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_ProcessEventFirstPrinterAvailable(globalState(This,Z_State,Z_TEMP1),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC_DECORV,Z_callerS_DECORV,Z_callerA_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC_DECORV,Z_callerS_DECORV,Z_callerA_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_ProcessEventFirstPrinterOccupied(globalState(This,Z_State,Z_TEMP1),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC_DECORV,Z_callerS_DECORV,Z_callerA_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC_DECORV,Z_callerS_DECORV,Z_callerA_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_AddPrinter(globalState(This,Z_State,Z_TEMP1),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC_DECORV,Z_callerS_DECORV,Z_callerA_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC_DECORV,Z_callerS_DECORV,Z_callerA_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_DeletePrinter(globalState(This,Z_State,Z_TEMP1),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC_DECORV,Z_callerS_DECORV,Z_callerA_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC_DECORV,Z_callerS_DECORV,Z_callerA_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_ProcessEventJobToPrinter(globalState(This,Z_State,Z_TEMP1),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC_DECORV,Z_callerS_DECORV,Z_callerA_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC_DECORV,Z_callerS_DECORV,Z_callerA_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP):-
	z_ProcessEventJobToPrintersPool(globalState(This,Z_State,Z_TEMP1),z_ApplicationRouter(Z_callerC,Z_callerS,Z_callerA,Z_caller,Z_components,Z_connectors,Z_ports),z_ApplicationRouter(Z_callerC_DECORV,Z_callerS_DECORV,Z_callerA_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_outputP),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

init_ApplicationRouter_stateIni(tree('Root',[tree('ApplicationRouter',['callerC','callerS','callerA','caller','components','connectors','ports'])])).
