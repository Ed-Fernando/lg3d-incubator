value(z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),'Z_caller',Z_caller).
setValue(z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_NEW_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),'Z_caller',Z_NEW_caller).
value(z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),'Z_called',Z_called).
setValue(z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller,Z_NEW_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),'Z_called',Z_NEW_called).
value(z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),'Z_components',Z_components).
setValue(z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller,Z_called,Z_NEW_components,Z_connectors,Z_users,Z_printers,Z_ports),'Z_components',Z_NEW_components).
value(z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),'Z_connectors',Z_connectors).
setValue(z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller,Z_called,Z_components,Z_NEW_connectors,Z_users,Z_printers,Z_ports),'Z_connectors',Z_NEW_connectors).
value(z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),'Z_users',Z_users).
setValue(z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_NEW_users,Z_printers,Z_ports),'Z_users',Z_NEW_users).
value(z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),'Z_printers',Z_printers).
setValue(z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_NEW_printers,Z_ports),'Z_printers',Z_NEW_printers).
value(z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),'Z_ports',Z_ports).
setValue(z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_NEW_ports),'Z_ports',Z_NEW_ports).

z_Repository(globalState(This,Z_State,Z_State),Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports).
z_init_Repository(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV)):-
	new(This,'Port',Z_TEMP2),
	Z_caller_DECORV=Z_TEMP2,
	setIvar(Z_caller_DECORV,'Z_element',This),
	new(This,'Port',Z_TEMP3),
	Z_called_DECORV=Z_TEMP3,
	setIvar(Z_called_DECORV,'Z_element',This),
	Z_users_DECORV=[tuple(1,1111,adrian,2),tuple(2,2222,sergio,1)],
	Z_printers_DECORV=[tuple(1,1,color,available,inkjet),tuple(2,1,bn,occupied,laserjet)],
	Z_components_DECORV=[],
	Z_connectors_DECORV=[],
	Z_ports_DECORV=[Z_caller_DECORV,Z_called_DECORV]
	,addChangeOp(Z_State,z_init_Repository,[va('Z_caller'),va('Z_called'),va('Z_components'),va('Z_connectors'),va('Z_users'),va('Z_printers'),va('Z_ports')],Z_NewState).

z_AddUserOk(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users_DECORV,Z_printers,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=addUser,
	head(Z_inputP,Z_TEMP1),
	Nick=Z_TEMP1,
	tail(Z_inputP,Z_TEMP2),
	head(Z_TEMP2,Z_TEMP3),
	Password=Z_TEMP3,
	tail(Z_inputP,Z_TEMP4),
	tail(Z_TEMP4,Z_TEMP5),
	head(Z_TEMP5,Z_TEMP6),
	User=Z_TEMP6,
	tail(Z_inputP,Z_TEMP7),
	tail(Z_TEMP7,Z_TEMP8),
	tail(Z_TEMP8,Z_TEMP9),
	head(Z_TEMP9,Z_TEMP10),
	Permission=Z_TEMP10,
	uni(Z_users,[tuple(Nick,Password,User,Permission)],Z_TEMP11),
	Z_users_DECORV=Z_TEMP11,
	Z_report='add_user_ok',
	addChangeOp(Z_State,z_AddUserOk,[va('Z_users')],Z_NewState),
	z_Repository(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_users_DECORV,Z_printers,Z_ports).

z_DeleteUserOk(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users_DECORV,Z_printers,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=deleteUser,
	head(Z_inputP,Z_TEMP1),
	Nick=Z_TEMP1,
	tail(Z_inputP,Z_TEMP2),
	head(Z_TEMP2,Z_TEMP3),
	Password=Z_TEMP3,
	tail(Z_inputP,Z_TEMP4),
	tail(Z_TEMP4,Z_TEMP5),
	head(Z_TEMP5,Z_TEMP6),
	User=Z_TEMP6,
	tail(Z_inputP,Z_TEMP7),
	tail(Z_TEMP7,Z_TEMP8),
	tail(Z_TEMP8,Z_TEMP9),
	head(Z_TEMP9,Z_TEMP10),
	Permission=Z_TEMP10,
	setminus(Z_users,[tuple(Nick,Password,User,Permission)],Z_TEMP11),
	Z_users_DECORV=Z_TEMP11,
	Z_report='delete_user_ok',
	addChangeOp(Z_State,z_DeleteUserOk,[va('Z_users')],Z_NewState),
	z_Repository(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_users_DECORV,Z_printers,Z_ports).

z_VerifyPermissionOk1(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=verifyPermission,
	head(Z_inputP,Z_TEMP1),
	Printer=Z_TEMP1,
	tail(Z_inputP,Z_TEMP2),
	head(Z_TEMP2,Z_TEMP3),
	User=Z_TEMP3,
fAll([	mem(X,Z_printers),
	nth(tuple(X,1),Z_TEMP4),
	Z_TEMP4=Printer
],X,Z_TEMP5),
	NL=Z_TEMP5,
	NL\=[],
	head(NL,Z_TEMP6),
	P=Z_TEMP6,
	nth(tuple(P,5),Z_TEMP7),
	Z_TEMP7=inkjet,
fAll([	mem(X,Z_users),
	nth(tuple(X,1),Z_TEMP8),
	Z_TEMP8=User
],X,Z_TEMP9),
	NU=Z_TEMP9,
	NU\=[],
	head(NU,Z_TEMP10),
	T=Z_TEMP10,
	nth(tuple(T,4),Z_TEMP11),
	Z_TEMP11=1,
	Z_report='permission_accepted',
	addChangeOp(Z_State,z_VerifyPermissionOk1,[],Z_NewState),
	z_Repository(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports).

z_VerifyPermissionFailed1(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=verifyPermission,
	head(Z_inputP,Z_TEMP1),
	Printer=Z_TEMP1,
	tail(Z_inputP,Z_TEMP2),
	head(Z_TEMP2,Z_TEMP3),
	User=Z_TEMP3,
fAll([	mem(X,Z_printers),
	nth(tuple(X,1),Z_TEMP4),
	Z_TEMP4=Printer
],X,Z_TEMP5),
	NL=Z_TEMP5,
	NL\=[],
	head(NL,Z_TEMP6),
	P=Z_TEMP6,
	nth(tuple(P,5),Z_TEMP7),
	Z_TEMP7=laserjet,
fAll([	mem(X,Z_users),
	nth(tuple(X,1),Z_TEMP8),
	Z_TEMP8=User
],X,Z_TEMP9),
	NU=Z_TEMP9,
	NU\=[],
	head(NU,Z_TEMP10),
	T=Z_TEMP10,
	nth(tuple(T,4),Z_TEMP11),
	Z_TEMP11=1,
	Z_report='permission_rejected',
	addChangeOp(Z_State,z_VerifyPermissionFailed1,[],Z_NewState),
	z_Repository(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports).

z_VerifyPermissionOk2(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=verifyPermission,
	tail(Z_inputP,Z_TEMP1),
	head(Z_TEMP1,Z_TEMP2),
	User=Z_TEMP2,
fAll([	mem(X,Z_users),
	nth(tuple(X,1),Z_TEMP3),
	Z_TEMP3=User
],X,Z_TEMP4),
	NU=Z_TEMP4,
	NU\=[],
	head(NU,Z_TEMP5),
	T=Z_TEMP5,
	nth(tuple(T,4),Z_TEMP6),
	Z_TEMP6=2,
	Z_report='permission_accepted',
	addChangeOp(Z_State,z_VerifyPermissionOk2,[],Z_NewState),
	z_Repository(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports).

z_ValidateUserOk(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=validateUser,
	head(Z_inputP,Z_TEMP1),
	Nick=Z_TEMP1,
fAll([	mem(X,Z_users),
	nth(tuple(X,1),Z_TEMP2),
	Z_TEMP2=Nick
],X,Z_TEMP3),
	NL=Z_TEMP3,
	NL\=[],
	Z_report='exist_user',
	addChangeOp(Z_State,z_ValidateUserOk,[],Z_NewState),
	z_Repository(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports).

z_ValidateUserBad(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=validateUser,
	head(Z_inputP,Z_TEMP1),
	Nick=Z_TEMP1,
fAll([	mem(X,Z_users),
	nth(tuple(X,1),Z_TEMP2),
	Z_TEMP2=Nick
],X,Z_TEMP3),
	NL=Z_TEMP3,
	NL=[],
	Z_report='not_exist_user',
	addChangeOp(Z_State,z_ValidateUserBad,[],Z_NewState),
	z_Repository(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports).

z_ListUsers(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=listUsers,
	Z_report=Z_users,
	addChangeOp(Z_State,z_ListUsers,[],Z_NewState),
	z_Repository(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports).

z_ListPrinters(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=listPrinters,
	Z_report=Z_printers,
	addChangeOp(Z_State,z_ListPrinters,[],Z_NewState),
	z_Repository(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports).

z_VerifyPrinterOk(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=verifyPrinter,
	head(Z_inputP,Z_TEMP1),
	Printer=Z_TEMP1,
fAll([	mem(X,Z_printers),
	nth(tuple(X,1),Z_TEMP2),
	Z_TEMP2=Printer
],X,Z_TEMP3),
	NL=Z_TEMP3,
	NL\=[],
	Z_report='printer_exist',
	addChangeOp(Z_State,z_VerifyPrinterOk,[],Z_NewState),
	z_Repository(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports).

z_VerifyPrinterFailed(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=verifyPrinter,
	head(Z_inputP,Z_TEMP1),
	Printer=Z_TEMP1,
fAll([	mem(X,Z_printers),
	nth(tuple(X,1),Z_TEMP2),
	Z_TEMP2=Printer
],X,Z_TEMP3),
	NL=Z_TEMP3,
	NL=[],
	Z_report='printer_not_exist',
	addChangeOp(Z_State,z_VerifyPrinterFailed,[],Z_NewState),
	z_Repository(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports).

z_PrinterStateAvailable(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=printerState,
	head(Z_inputP,Z_TEMP1),
	Printer=Z_TEMP1,
fAll([	mem(X,Z_printers),
	nth(tuple(X,1),Z_TEMP2),
	Z_TEMP2=Printer
],X,Z_TEMP3),
	NL=Z_TEMP3,
	NL\=[],
fAll([	mem(X,NL),
	nth(tuple(X,4),Z_TEMP4),
	Z_TEMP4=available
],X,Z_TEMP5),
	JR=Z_TEMP5,
	JR\=[],
	Z_report='printer_available',
	addChangeOp(Z_State,z_PrinterStateAvailable,[],Z_NewState),
	z_Repository(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports).

z_PrinterStateNonAvailable(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=printerState,
	head(Z_inputP,Z_TEMP1),
	Printer=Z_TEMP1,
fAll([	mem(X,Z_printers),
	nth(tuple(X,1),Z_TEMP2),
	Z_TEMP2=Printer
],X,Z_TEMP3),
	NL=Z_TEMP3,
	NL\=[],
fAll([	mem(X,NL),
	nth(tuple(X,4),Z_TEMP4),
	Z_TEMP4=nonavailable
],X,Z_TEMP5),
	JR=Z_TEMP5,
	JR\=[],
	Z_report='printer_non_available',
	addChangeOp(Z_State,z_PrinterStateNonAvailable,[],Z_NewState),
	z_Repository(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports).

z_PrinterStateOccupied(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=printerState,
	head(Z_inputP,Z_TEMP1),
	Printer=Z_TEMP1,
fAll([	mem(X,Z_printers),
	nth(tuple(X,1),Z_TEMP2),
	Z_TEMP2=Printer
],X,Z_TEMP3),
	NL=Z_TEMP3,
	NL\=[],
fAll([	mem(X,NL),
	nth(tuple(X,4),Z_TEMP4),
	Z_TEMP4=occupied
],X,Z_TEMP5),
	JR=Z_TEMP5,
	JR\=[],
	Z_report='printer_occupied',
	addChangeOp(Z_State,z_PrinterStateOccupied,[],Z_NewState),
	z_Repository(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports).

z_FirstPrinterAvailableOk(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=firstPrinterAvailable,
fAll([	mem(X,Z_printers),
	nth(tuple(X,4),Z_TEMP1),
	Z_TEMP1=available
],X,Z_TEMP2),
	NL=Z_TEMP2,
	NL\=[],
	head(NL,Z_TEMP3),
	P=Z_TEMP3,
	nth(tuple(P,1),Z_TEMP4),
	T=Z_TEMP4,
	Z_report=T,
	addChangeOp(Z_State,z_FirstPrinterAvailableOk,[],Z_NewState),
	z_Repository(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports).

z_FirstPrinterAvailableFailed(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=firstPrinterAvailable,
fAll([	mem(X,Z_printers),
	nth(tuple(X,4),Z_TEMP1),
	Z_TEMP1=available
],X,Z_TEMP2),
	NL=Z_TEMP2,
	NL=[],
	Z_report=NL,
	addChangeOp(Z_State,z_FirstPrinterAvailableFailed,[],Z_NewState),
	z_Repository(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports).

z_FirstPrinterOccupiedOk(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=firstPrinterOccupied,
fAll([	mem(X,Z_printers),
	nth(tuple(X,4),Z_TEMP1),
	Z_TEMP1=occupied
],X,Z_TEMP2),
	NL=Z_TEMP2,
	NL\=[],
	head(NL,Z_TEMP3),
	P=Z_TEMP3,
	nth(tuple(P,1),Z_TEMP4),
	T=Z_TEMP4,
	Z_report=T,
	addChangeOp(Z_State,z_FirstPrinterOccupiedOk,[],Z_NewState),
	z_Repository(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports).

z_FirstPrinterOccupiedFailed(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=firstPrinterOccupied,
fAll([	mem(X,Z_printers),
	nth(tuple(X,4),Z_TEMP1),
	Z_TEMP1=occupied
],X,Z_TEMP2),
	NL=Z_TEMP2,
	NL=[],
	Z_report=NL,
	addChangeOp(Z_State,z_FirstPrinterOccupiedFailed,[],Z_NewState),
	z_Repository(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports).

z_AddPrinterOk(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers_DECORV,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=addPrinter,
	head(Z_inputP,Z_TEMP1),
	Printer=Z_TEMP1,
	tail(Z_inputP,Z_TEMP2),
	head(Z_TEMP2,Z_TEMP3),
	Bandeja=Z_TEMP3,
	tail(Z_inputP,Z_TEMP4),
	tail(Z_TEMP4,Z_TEMP5),
	head(Z_TEMP5,Z_TEMP6),
	Color=Z_TEMP6,
	tail(Z_inputP,Z_TEMP7),
	tail(Z_TEMP7,Z_TEMP8),
	tail(Z_TEMP8,Z_TEMP9),
	head(Z_TEMP9,Z_TEMP10),
	State=Z_TEMP10,
	tail(Z_inputP,Z_TEMP11),
	tail(Z_TEMP11,Z_TEMP12),
	tail(Z_TEMP12,Z_TEMP13),
	tail(Z_TEMP13,Z_TEMP14),
	head(Z_TEMP14,Z_TEMP15),
	TypePrinter=Z_TEMP15,
	uni(Z_printers,[tuple(Printer,Bandeja,Color,State,TypePrinter)],Z_TEMP16),
	Z_printers_DECORV=Z_TEMP16,
	Z_report='add_printer_ok',
	addChangeOp(Z_State,z_AddPrinterOk,[va('Z_printers')],Z_NewState),
	z_Repository(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers_DECORV,Z_ports).

z_DeletePrinterOk(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers_DECORV,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=deletePrinter,
	head(Z_inputP,Z_TEMP1),
	Printer=Z_TEMP1,
	tail(Z_inputP,Z_TEMP2),
	head(Z_TEMP2,Z_TEMP3),
	Bandeja=Z_TEMP3,
	tail(Z_inputP,Z_TEMP4),
	tail(Z_TEMP4,Z_TEMP5),
	head(Z_TEMP5,Z_TEMP6),
	Color=Z_TEMP6,
	tail(Z_inputP,Z_TEMP7),
	tail(Z_TEMP7,Z_TEMP8),
	tail(Z_TEMP8,Z_TEMP9),
	head(Z_TEMP9,Z_TEMP10),
	State=Z_TEMP10,
	tail(Z_inputP,Z_TEMP11),
	tail(Z_TEMP11,Z_TEMP12),
	tail(Z_TEMP12,Z_TEMP13),
	tail(Z_TEMP13,Z_TEMP14),
	head(Z_TEMP14,Z_TEMP15),
	TypePrinter=Z_TEMP15,
	setminus(Z_printers,[tuple(Printer,Bandeja,Color,State,TypePrinter)],Z_TEMP16),
	Z_printers_DECORV=Z_TEMP16,
	Z_report='delete_printer_ok',
	addChangeOp(Z_State,z_DeletePrinterOk,[va('Z_printers')],Z_NewState),
	z_Repository(globalState(This,Z_NewState,Z_NewState),Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers_DECORV,Z_ports).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_ValidateUserOk(globalState(This,Z_State,Z_TEMP1),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_ValidateUserBad(globalState(This,Z_State,Z_TEMP1),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_AddUserOk(globalState(This,Z_State,Z_TEMP1),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_DeleteUserOk(globalState(This,Z_State,Z_TEMP1),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_VerifyPermissionOk1(globalState(This,Z_State,Z_TEMP1),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_VerifyPermissionFailed1(globalState(This,Z_State,Z_TEMP1),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_VerifyPermissionOk2(globalState(This,Z_State,Z_TEMP1),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_ListUsers(globalState(This,Z_State,Z_TEMP1),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_ListPrinters(globalState(This,Z_State,Z_TEMP1),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_VerifyPrinterOk(globalState(This,Z_State,Z_TEMP1),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_VerifyPrinterFailed(globalState(This,Z_State,Z_TEMP1),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_PrinterStateAvailable(globalState(This,Z_State,Z_TEMP1),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_PrinterStateNonAvailable(globalState(This,Z_State,Z_TEMP1),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_PrinterStateOccupied(globalState(This,Z_State,Z_TEMP1),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_FirstPrinterAvailableOk(globalState(This,Z_State,Z_TEMP1),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_FirstPrinterAvailableFailed(globalState(This,Z_State,Z_TEMP1),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_FirstPrinterOccupiedOk(globalState(This,Z_State,Z_TEMP1),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_FirstPrinterOccupiedFailed(globalState(This,Z_State,Z_TEMP1),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_AddPrinterOk(globalState(This,Z_State,Z_TEMP1),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_DeletePrinterOk(globalState(This,Z_State,Z_TEMP1),z_Repository(Z_caller,Z_called,Z_components,Z_connectors,Z_users,Z_printers,Z_ports),z_Repository(Z_caller_DECORV,Z_called_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_users_DECORV,Z_printers_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

init_Repository_stateIni(tree('Root',[tree('Repository',['caller','called','components','connectors','users','printers','ports'])])).
