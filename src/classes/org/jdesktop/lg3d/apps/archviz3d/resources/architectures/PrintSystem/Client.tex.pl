value(z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),'Z_called',Z_called).
setValue(z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_NEW_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),'Z_called',Z_NEW_called).
value(z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),'Z_caller',Z_caller).
setValue(z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_NEW_caller,Z_components,Z_connectors,Z_events,Z_ports),'Z_caller',Z_NEW_caller).
value(z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),'Z_components',Z_components).
setValue(z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_NEW_components,Z_connectors,Z_events,Z_ports),'Z_components',Z_NEW_components).
value(z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),'Z_connectors',Z_connectors).
setValue(z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_NEW_connectors,Z_events,Z_ports),'Z_connectors',Z_NEW_connectors).
value(z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),'Z_events',Z_events).
setValue(z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_NEW_events,Z_ports),'Z_events',Z_NEW_events).
value(z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),'Z_ports',Z_ports).
setValue(z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_NEW_ports),'Z_ports',Z_NEW_ports).

z_Client(globalState(This,Z_State,Z_State),Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports).
z_init_Client(globalState(This,Z_State,Z_NewState),z_Client(Z_called_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV)):-
	new(This,'Port',Z_TEMP2),
	Z_called_DECORV=Z_TEMP2,
	setIvar(Z_called_DECORV,'Z_element',This),
	new(This,'Port',Z_TEMP3),
	Z_caller_DECORV=Z_TEMP3,
	setIvar(Z_caller_DECORV,'Z_element',This),
	Z_components_DECORV=[],
	Z_connectors_DECORV=[],
	Z_events_DECORV=[addUser,deleteUser,listUsers,listPrinters,jobToPrinter,jobToPrintersPool,printerState,verifyPrinter,verifyPermission,firstPrinterAvailable,firstPrinterOccupied,addPrinter,deletePrinter],
	Z_ports_DECORV=[Z_called_DECORV,Z_caller_DECORV]
	,addChangeOp(Z_State,z_init_Client,[va('Z_called'),va('Z_caller'),va('Z_components'),va('Z_connectors'),va('Z_events'),va('Z_ports')],Z_NewState).

z_AddUserOk(globalState(This,Z_State,Z_NewState),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),Z_nick,Z_password,Z_user,Z_permission,Z_report):-
	SE=[Z_nick,Z_password,Z_user,Z_permission],
	sendV(Z_called,execute,['z_Initiate',[addUser,This,SE,R]],_),
	R='add_user_ok',
	Z_report='add_user_ok',
	addChangeOp(Z_State,z_AddUserOk,[],Z_NewState),
	z_Client(globalState(This,Z_NewState,Z_NewState),Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports).

z_AddUserFailed(globalState(This,Z_State,Z_NewState),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),Z_nick,Z_password,Z_user,Z_permission,Z_report):-
	SE=[Z_nick,Z_password,Z_user,Z_permission],
	sendV(Z_called,execute,['z_Initiate',[addUser,This,SE,R]],_),
	R='exist_user',
	Z_report='exist_user',
	addChangeOp(Z_State,z_AddUserFailed,[],Z_NewState),
	z_Client(globalState(This,Z_NewState,Z_NewState),Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports).

z_AddUser(globalState(This,Z_State,Z_NewState),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),Z_nick,Z_password,Z_user,Z_permission,Z_report):-
	z_AddUserOk(globalState(This,Z_State,Z_TEMP1),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),Z_nick,Z_password,Z_user,Z_permission,Z_report),
	addChangeOp(Z_TEMP1,z_AddUser,Z_NewState).

z_AddUser(globalState(This,Z_State,Z_NewState),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),Z_nick,Z_password,Z_user,Z_permission,Z_report):-
	z_AddUserFailed(globalState(This,Z_State,Z_TEMP1),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),Z_nick,Z_password,Z_user,Z_permission,Z_report),
	addChangeOp(Z_TEMP1,z_AddUser,Z_NewState).

z_DeleteUserOk(globalState(This,Z_State,Z_NewState),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),Z_nick,Z_password,Z_user,Z_permission,Z_report):-
	SE=[Z_nick,Z_password,Z_user,Z_permission],
	sendV(Z_called,execute,['z_Initiate',[deleteUser,This,SE,R]],_),
	R='delete_user_ok',
	Z_report='delete_user_ok',
	addChangeOp(Z_State,z_DeleteUserOk,[],Z_NewState),
	z_Client(globalState(This,Z_NewState,Z_NewState),Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports).

z_DeleteUserFailed(globalState(This,Z_State,Z_NewState),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),Z_nick,Z_password,Z_user,Z_permission,Z_report):-
	SE=[Z_nick,Z_password,Z_user,Z_permission],
	sendV(Z_called,execute,['z_Initiate',[deleteUser,This,SE,R]],_),
	R='not_exist_user',
	Z_report='not_exist_user',
	addChangeOp(Z_State,z_DeleteUserFailed,[],Z_NewState),
	z_Client(globalState(This,Z_NewState,Z_NewState),Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports).

z_DeleteUser(globalState(This,Z_State,Z_NewState),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),Z_nick,Z_password,Z_user,Z_permission,Z_report):-
	z_DeleteUserOk(globalState(This,Z_State,Z_TEMP1),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),Z_nick,Z_password,Z_user,Z_permission,Z_report),
	addChangeOp(Z_TEMP1,z_DeleteUser,Z_NewState).

z_DeleteUser(globalState(This,Z_State,Z_NewState),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),Z_nick,Z_password,Z_user,Z_permission,Z_report):-
	z_DeleteUserFailed(globalState(This,Z_State,Z_TEMP1),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),Z_nick,Z_password,Z_user,Z_permission,Z_report),
	addChangeOp(Z_TEMP1,z_DeleteUser,Z_NewState).

z_VerifyPermission(globalState(This,Z_State,Z_NewState),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),Z_printer,Z_user,Z_report):-
	SE=[Z_printer,Z_user],
	sendV(Z_called,execute,['z_Initiate',[verifyPermission,This,SE,R]],_),
	Z_report=R,
	addChangeOp(Z_State,z_VerifyPermission,[],Z_NewState),
	z_Client(globalState(This,Z_NewState,Z_NewState),Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports).

z_ListUsers(globalState(This,Z_State,Z_NewState),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),Z_report):-
	SE=[],
	sendV(Z_called,execute,['z_Initiate',[listUsers,This,SE,R]],_),
	Z_report=R,
	addChangeOp(Z_State,z_ListUsers,[],Z_NewState),
	z_Client(globalState(This,Z_NewState,Z_NewState),Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports).

z_ListingUsers(globalState(This,Z_State,Z_NewState),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_report):-
	z_ListUsers(globalState(This,Z_State,Z_TEMP1),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_report),
	addChangeOp(Z_TEMP1,z_ListingUsers,Z_NewState).

z_ListPrinters(globalState(This,Z_State,Z_NewState),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),Z_report):-
	SE=[],
	sendV(Z_called,execute,['z_Initiate',[listPrinters,This,SE,R]],_),
	Z_report=R,
	addChangeOp(Z_State,z_ListPrinters,[],Z_NewState),
	z_Client(globalState(This,Z_NewState,Z_NewState),Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports).

z_ListingPrinters(globalState(This,Z_State,Z_NewState),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_report):-
	z_ListPrinters(globalState(This,Z_State,Z_TEMP1),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_report),
	addChangeOp(Z_TEMP1,z_ListingPrinters,Z_NewState).

z_VerifyPrinterOk(globalState(This,Z_State,Z_NewState),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),Z_printer,Z_report):-
	SE=[Z_printer],
	sendV(Z_called,execute,['z_Initiate',[verifyPrinter,This,SE,R]],_),
	R='printer_exist',
	Z_report='printer_exist',
	addChangeOp(Z_State,z_VerifyPrinterOk,[],Z_NewState),
	z_Client(globalState(This,Z_NewState,Z_NewState),Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports).

z_VerifyPrinterFailed(globalState(This,Z_State,Z_NewState),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),Z_printer,Z_report):-
	SE=[Z_printer],
	sendV(Z_called,execute,['z_Initiate',[verifyPrinter,This,SE,R]],_),
	R='printer_not_exist',
	Z_report='printer_not_exist',
	addChangeOp(Z_State,z_VerifyPrinterFailed,[],Z_NewState),
	z_Client(globalState(This,Z_NewState,Z_NewState),Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports).

z_VerifyPrinter(globalState(This,Z_State,Z_NewState),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),Z_printer,Z_report):-
	z_VerifyPrinterOk(globalState(This,Z_State,Z_TEMP1),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),Z_printer,Z_report),
	addChangeOp(Z_TEMP1,z_VerifyPrinter,Z_NewState).

z_VerifyPrinter(globalState(This,Z_State,Z_NewState),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),Z_printer,Z_report):-
	z_VerifyPrinterFailed(globalState(This,Z_State,Z_TEMP1),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),Z_printer,Z_report),
	addChangeOp(Z_TEMP1,z_VerifyPrinter,Z_NewState).

z_PrinterState(globalState(This,Z_State,Z_NewState),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),Z_printer,Z_report):-
	SE=[Z_printer],
	sendV(Z_called,execute,['z_Initiate',[printerState,This,SE,R]],_),
	Z_report=R,
	addChangeOp(Z_State,z_PrinterState,[],Z_NewState),
	z_Client(globalState(This,Z_NewState,Z_NewState),Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports).

z_FirstPrinterAvailable(globalState(This,Z_State,Z_NewState),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),Z_report):-
	SE=[],
	sendV(Z_called,execute,['z_Initiate',[firstPrinterAvailable,This,SE,R]],_),
	Z_report=R,
	addChangeOp(Z_State,z_FirstPrinterAvailable,[],Z_NewState),
	z_Client(globalState(This,Z_NewState,Z_NewState),Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports).

z_FirstPrinterOccupied(globalState(This,Z_State,Z_NewState),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),Z_report):-
	SE=[],
	sendV(Z_called,execute,['z_Initiate',[firstPrinterOccupied,This,SE,R]],_),
	Z_report=R,
	addChangeOp(Z_State,z_FirstPrinterOccupied,[],Z_NewState),
	z_Client(globalState(This,Z_NewState,Z_NewState),Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports).

z_AddPrinterOk(globalState(This,Z_State,Z_NewState),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),Z_printer,Z_bandeja,Z_type,Z_state,Z_typePrinter,Z_report):-
	SE=[Z_printer,Z_bandeja,Z_type,Z_state,Z_typePrinter],
	sendV(Z_called,execute,['z_Initiate',[addPrinter,This,SE,R]],_),
	R='add_printer_ok',
	Z_report='add_printer_ok',
	addChangeOp(Z_State,z_AddPrinterOk,[],Z_NewState),
	z_Client(globalState(This,Z_NewState,Z_NewState),Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports).

z_AddPrinterFailed(globalState(This,Z_State,Z_NewState),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),Z_printer,Z_bandeja,Z_type,Z_state,Z_typePrinter,Z_report):-
	SE=[Z_printer,Z_bandeja,Z_type,Z_state,Z_typePrinter],
	sendV(Z_called,execute,['z_Initiate',[addPrinter,This,SE,R]],_),
	R='printer_exist',
	Z_report='printer_exist',
	addChangeOp(Z_State,z_AddPrinterFailed,[],Z_NewState),
	z_Client(globalState(This,Z_NewState,Z_NewState),Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports).

z_AddPrinter(globalState(This,Z_State,Z_NewState),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),Z_printer,Z_bandeja,Z_type,Z_state,Z_typePrinter,Z_report):-
	z_AddPrinterOk(globalState(This,Z_State,Z_TEMP1),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),Z_printer,Z_bandeja,Z_type,Z_state,Z_typePrinter,Z_report),
	addChangeOp(Z_TEMP1,z_AddPrinter,Z_NewState).

z_AddPrinter(globalState(This,Z_State,Z_NewState),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),Z_printer,Z_bandeja,Z_type,Z_state,Z_typePrinter,Z_report):-
	z_AddPrinterFailed(globalState(This,Z_State,Z_TEMP1),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),Z_printer,Z_bandeja,Z_type,Z_state,Z_typePrinter,Z_report),
	addChangeOp(Z_TEMP1,z_AddPrinter,Z_NewState).

z_DeletePrinterOk(globalState(This,Z_State,Z_NewState),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),Z_printer,Z_bandeja,Z_type,Z_state,Z_typePrinter,Z_report):-
	SE=[Z_printer,Z_bandeja,Z_type,Z_state,Z_typePrinter],
	sendV(Z_called,execute,['z_Initiate',[deletePrinter,This,SE,R]],_),
	R='delete_printer_ok',
	Z_report='delete_printer_ok',
	addChangeOp(Z_State,z_DeletePrinterOk,[],Z_NewState),
	z_Client(globalState(This,Z_NewState,Z_NewState),Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports).

z_DeletePrinterFailed(globalState(This,Z_State,Z_NewState),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),Z_printer,Z_bandeja,Z_type,Z_state,Z_typePrinter,Z_report):-
	SE=[Z_printer,Z_bandeja,Z_type,Z_state,Z_typePrinter],
	sendV(Z_called,execute,['z_Initiate',[deletePrinter,This,SE,R]],_),
	R='printer_not_exist',
	Z_report='printer_not_exist',
	addChangeOp(Z_State,z_DeletePrinterFailed,[],Z_NewState),
	z_Client(globalState(This,Z_NewState,Z_NewState),Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports).

z_DeletePrinter(globalState(This,Z_State,Z_NewState),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),Z_printer,Z_bandeja,Z_type,Z_state,Z_typePrinter,Z_report):-
	z_DeletePrinterOk(globalState(This,Z_State,Z_TEMP1),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),Z_printer,Z_bandeja,Z_type,Z_state,Z_typePrinter,Z_report),
	addChangeOp(Z_TEMP1,z_DeletePrinter,Z_NewState).

z_DeletePrinter(globalState(This,Z_State,Z_NewState),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),Z_printer,Z_bandeja,Z_type,Z_state,Z_typePrinter,Z_report):-
	z_DeletePrinterFailed(globalState(This,Z_State,Z_TEMP1),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),Z_printer,Z_bandeja,Z_type,Z_state,Z_typePrinter,Z_report),
	addChangeOp(Z_TEMP1,z_DeletePrinter,Z_NewState).

z_JobToPrinter(globalState(This,Z_State,Z_NewState),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),Z_printer,Z_user,Z_job,Z_report):-
	SE=[Z_printer,Z_user,Z_job],
	sendV(Z_called,execute,['z_Initiate',[jobToPrinter,This,SE,R]],_),
	Z_report=R,
	addChangeOp(Z_State,z_JobToPrinter,[],Z_NewState),
	z_Client(globalState(This,Z_NewState,Z_NewState),Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports).

z_JobToPrintersPool(globalState(This,Z_State,Z_NewState),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),Z_user,Z_job,Z_report):-
	SE=[Z_user,Z_job],
	sendV(Z_called,execute,['z_Initiate',[jobToPrintersPool,This,SE,R]],_),
	Z_report=R,
	addChangeOp(Z_State,z_JobToPrintersPool,[],Z_NewState),
	z_Client(globalState(This,Z_NewState,Z_NewState),Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports).

z_ExecuteJobToPrinter(globalState(This,Z_State,Z_NewState),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_printer,Z_user,Z_job,Z_report):-
	z_JobToPrinter(globalState(This,Z_State,Z_TEMP1),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_printer,Z_user,Z_job,Z_report),
	addChangeOp(Z_TEMP1,z_ExecuteJobToPrinter,Z_NewState).

z_ExecuteJobToPrinter(globalState(This,Z_State,Z_NewState),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_user,Z_job,Z_report):-
	z_JobToPrintersPool(globalState(This,Z_State,Z_TEMP1),z_Client(Z_called,Z_caller,Z_components,Z_connectors,Z_events,Z_ports),z_Client(Z_called_DECORV,Z_caller_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_user,Z_job,Z_report),
	addChangeOp(Z_TEMP1,z_ExecuteJobToPrinter,Z_NewState).

init_Client_stateIni(tree('Root',[tree('Client',['called','caller','components','connectors','events','ports'])])).
