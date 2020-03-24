value(z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),'Z_ip',Z_ip).
setValue(z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_NEW_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),'Z_ip',Z_NEW_ip).
value(z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),'Z_device',Z_device).
setValue(z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_NEW_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),'Z_device',Z_NEW_device).
value(z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),'Z_callerSA',Z_callerSA).
setValue(z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_NEW_callerSA,Z_components,Z_connectors,Z_events,Z_ports),'Z_callerSA',Z_NEW_callerSA).
value(z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),'Z_components',Z_components).
setValue(z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_NEW_components,Z_connectors,Z_events,Z_ports),'Z_components',Z_NEW_components).
value(z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),'Z_connectors',Z_connectors).
setValue(z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_NEW_connectors,Z_events,Z_ports),'Z_connectors',Z_NEW_connectors).
value(z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),'Z_events',Z_events).
setValue(z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_NEW_events,Z_ports),'Z_events',Z_NEW_events).
value(z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),'Z_ports',Z_ports).
setValue(z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_NEW_ports),'Z_ports',Z_NEW_ports).

z_Admin(globalState(This,Z_State,Z_State),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).
z_init_Admin(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip_DECORV,Z_device_DECORV,Z_callerSA_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV)):-
	Z_ip_DECORV=0,
	Z_device_DECORV=0,
	new(This,'Port',Z_TEMP2),
	Z_callerSA_DECORV=Z_TEMP2,
	setIvar(Z_callerSA_DECORV,'Z_element',This),
	Z_components_DECORV=[],
	Z_connectors_DECORV=[],
	Z_events_DECORV=[],
	Z_ports_DECORV=[Z_callerSA_DECORV]
	,addChangeOp(Z_State,z_init_Admin,[va('Z_ip'),va('Z_device'),va('Z_callerSA'),va('Z_components'),va('Z_connectors'),va('Z_events'),va('Z_ports')],Z_NewState).

z_AssignIP(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip_DECORV,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=assignIP,
	head(Z_inputP,Z_TEMP1),
	IpAssigned=Z_TEMP1,
	Z_ip_DECORV=IpAssigned,
	Z_report='ok',
	addChangeOp(Z_State,z_AssignIP,[va('Z_ip')],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip_DECORV,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_GetIP(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=getIP,
	Z_report=Z_ip,
	addChangeOp(Z_State,z_GetIP,[],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_AssignDevice(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device_DECORV,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=assignDevice,
	head(Z_inputP,Z_TEMP1),
	DeviceAssigned=Z_TEMP1,
	Z_device_DECORV=DeviceAssigned,
	Z_report='ok',
	addChangeOp(Z_State,z_AssignDevice,[va('Z_device')],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device_DECORV,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_GetDevice(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=getDevice,
	Z_report=Z_device,
	addChangeOp(Z_State,z_GetDevice,[],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_LoginAdminOK(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=login,
	head(Z_inputP,Z_TEMP1),
	Id=Z_TEMP1,
	tail(Z_inputP,Z_TEMP2),
	head(Z_TEMP2,Z_TEMP3),
	Password=Z_TEMP3,
	SE=[Z_ip,Id,Password,Z_device],
	sendV(Z_callerSA,execute,['z_Initiate',[validateUser,This,SE,R]],_),
	R='ok',
	Z_report=R,
	addChangeOp(Z_State,z_LoginAdminOK,[],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_LoginAdminFailed(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=login,
	head(Z_inputP,Z_TEMP1),
	Id=Z_TEMP1,
	tail(Z_inputP,Z_TEMP2),
	head(Z_TEMP2,Z_TEMP3),
	Password=Z_TEMP3,
	SE=[Z_ip,Id,Password],
	sendV(Z_callerSA,execute,['z_Initiate',[validateUser,This,SE,R]],_),
	R='not_valid_user',
	Z_report=R,
	addChangeOp(Z_State,z_LoginAdminFailed,[],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_LoginAdminInvalidConnection(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=login,
	SE=[Z_ip],
	sendV(Z_callerSA,execute,['z_Initiate',[validateUser,This,SE,R]],_),
	R='not_valid_connection_or_expired_session',
	Z_report=R,
	addChangeOp(Z_State,z_LoginAdminInvalidConnection,[],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_LoginAdminInexistent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=login,
	head(Z_inputP,Z_TEMP1),
	Id=Z_TEMP1,
	tail(Z_inputP,Z_TEMP2),
	head(Z_TEMP2,Z_TEMP3),
	Password=Z_TEMP3,
	SE=[Z_ip,Id,Password],
	sendV(Z_callerSA,execute,['z_Initiate',[validateUser,This,SE,R]],_),
	R='inexistent_record',
	Z_report=R,
	addChangeOp(Z_State,z_LoginAdminInexistent,[],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_Logout(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip_DECORV,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=logout,
	SD=[Z_ip],
	sendV(Z_callerSA,execute,['z_Initiate',[logout,This,SD,R]],_),
	R='ok',
	Z_ip_DECORV=0,
	Z_report=R,
	addChangeOp(Z_State,z_Logout,[va('Z_ip')],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip_DECORV,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_SearchMemberOK(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=searchMember,
	head(Z_inputP,Z_TEMP1),
	Id=Z_TEMP1,
	SE=[Z_ip,Id],
	sendV(Z_callerSA,execute,['z_Initiate',[searchMember,This,SE,R]],_),
	Z_report=R,
	addChangeOp(Z_State,z_SearchMemberOK,[],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_SearchMemberInvalidConnection(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=searchMember,
	SE=[Z_ip],
	sendV(Z_callerSA,execute,['z_Initiate',[searchMember,This,SE,R]],_),
	R='not_valid_connection_or_expired_session',
	Z_report=R,
	addChangeOp(Z_State,z_SearchMemberInvalidConnection,[],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_SearchBookOK(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=searchBook,
	head(Z_inputP,Z_TEMP1),
	BookName=Z_TEMP1,
	SE=[Z_ip,BookName],
	sendV(Z_callerSA,execute,['z_Initiate',[searchBook,This,SE,R]],_),
	Z_report=R,
	addChangeOp(Z_State,z_SearchBookOK,[],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_SearchBookInvalidConnection(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=searchBook,
	SE=[Z_ip],
	sendV(Z_callerSA,execute,['z_Initiate',[searchBook,This,SE,R]],_),
	R='not_valid_connection_or_expired_session',
	Z_report=R,
	addChangeOp(Z_State,z_SearchBookInvalidConnection,[],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_AddLoanOK(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=addLoan,
	head(Z_inputP,Z_TEMP1),
	IdM=Z_TEMP1,
	tail(Z_inputP,Z_TEMP2),
	head(Z_TEMP2,Z_TEMP3),
	IdB=Z_TEMP3,
	SE=[Z_ip,IdM,IdB],
	sendV(Z_callerSA,execute,['z_Initiate',[addLoan,This,SE,R]],_),
	R='ok',
	Z_report=R,
	addChangeOp(Z_State,z_AddLoanOK,[],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_AddLoanInvalidConnection(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=addLoan,
	SE=[Z_ip],
	sendV(Z_callerSA,execute,['z_Initiate',[addLoan,This,SE,R]],_),
	R='not_valid_connection_or_expired_session',
	Z_report=R,
	addChangeOp(Z_State,z_AddLoanInvalidConnection,[],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_AddLoanInvalidData(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=addLoan,
	head(Z_inputP,Z_TEMP1),
	IdM=Z_TEMP1,
	tail(Z_inputP,Z_TEMP2),
	head(Z_TEMP2,Z_TEMP3),
	IdB=Z_TEMP3,
	SE=[Z_ip,IdM,IdB],
	sendV(Z_callerSA,execute,['z_Initiate',[addLoan,This,SE,R]],_),
	R='not_valid_data',
	Z_report=R,
	addChangeOp(Z_State,z_AddLoanInvalidData,[],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_AddLoanNotEnoughBooks(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=addLoan,
	head(Z_inputP,Z_TEMP1),
	IdM=Z_TEMP1,
	tail(Z_inputP,Z_TEMP2),
	head(Z_TEMP2,Z_TEMP3),
	IdB=Z_TEMP3,
	SE=[Z_ip,IdM,IdB],
	sendV(Z_callerSA,execute,['z_Initiate',[addLoan,This,SE,R]],_),
	R='not_enough_books',
	Z_report=R,
	addChangeOp(Z_State,z_AddLoanNotEnoughBooks,[],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_BooksRankingOK(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=booksRanking,
	SE=[Z_ip],
	sendV(Z_callerSA,execute,['z_Initiate',[booksRanking,This,SE,R]],_),
	Z_report=R,
	addChangeOp(Z_State,z_BooksRankingOK,[],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_BooksRankingInvalidConnection(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=booksRanking,
	SE=[Z_ip],
	sendV(Z_callerSA,execute,['z_Initiate',[booksRanking,This,SE,R]],_),
	R='not_valid_connection_or_expired_session',
	Z_report=R,
	addChangeOp(Z_State,z_BooksRankingInvalidConnection,[],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_SearchLoanOK(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=searchLoan,
	head(Z_inputP,Z_TEMP1),
	Id=Z_TEMP1,
	SE=[Z_ip,Id],
	sendV(Z_callerSA,execute,['z_Initiate',[searchLoan,This,SE,R]],_),
	R='ok',
	Z_report=R,
	addChangeOp(Z_State,z_SearchLoanOK,[],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_SearchLoanInvalidConnection(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=searchLoan,
	SE=[Z_ip],
	sendV(Z_callerSA,execute,['z_Initiate',[searchLoan,This,SE,R]],_),
	R='not_valid_connection_or_expired_session',
	Z_report=R,
	addChangeOp(Z_State,z_SearchLoanInvalidConnection,[],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_ReturnBookInvalidConnection(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=returnBook,
	SE=[Z_ip],
	sendV(Z_callerSA,execute,['z_Initiate',[returnBook,This,SE,R]],_),
	R='not_valid_connection_or_expired_session',
	Z_report=R,
	addChangeOp(Z_State,z_ReturnBookInvalidConnection,[],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_ReturnBookOK(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=returnBook,
	head(Z_inputP,Z_TEMP1),
	IdM=Z_TEMP1,
	tail(Z_inputP,Z_TEMP2),
	head(Z_TEMP2,Z_TEMP3),
	IdB=Z_TEMP3,
	SE=[Z_ip,IdM,IdB],
	sendV(Z_callerSA,execute,['z_Initiate',[returnBook,This,SE,R]],_),
	R='ok',
	Z_report=R,
	addChangeOp(Z_State,z_ReturnBookOK,[],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_ReturnBookInvalidData(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=returnBook,
	head(Z_inputP,Z_TEMP1),
	IdM=Z_TEMP1,
	tail(Z_inputP,Z_TEMP2),
	head(Z_TEMP2,Z_TEMP3),
	IdB=Z_TEMP3,
	SE=[Z_ip,IdM,IdB],
	sendV(Z_callerSA,execute,['z_Initiate',[returnBook,This,SE,R]],_),
	R='not_valid_data',
	Z_report=R,
	addChangeOp(Z_State,z_ReturnBookInvalidData,[],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_AddMemberOK(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=addMember,
	head(Z_inputP,Z_TEMP1),
	Id=Z_TEMP1,
	tail(Z_inputP,Z_TEMP2),
	head(Z_TEMP2,Z_TEMP3),
	Password=Z_TEMP3,
	tail(Z_inputP,Z_TEMP4),
	tail(Z_TEMP4,Z_TEMP5),
	head(Z_TEMP5,Z_TEMP6),
	MemberName=Z_TEMP6,
	SE=[Z_ip,Id,Password,MemberName],
	sendV(Z_callerSA,execute,['z_Initiate',[addMember,This,SE,R]],_),
	R='ok',
	Z_report=R,
	addChangeOp(Z_State,z_AddMemberOK,[],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_AddMemberInvalidConnection(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=addMember,
	SE=[Z_ip],
	sendV(Z_callerSA,execute,['z_Initiate',[addMember,This,SE,R]],_),
	R='not_valid_connection_or_expired_session',
	Z_report=R,
	addChangeOp(Z_State,z_AddMemberInvalidConnection,[],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_AddMemberInvalidData(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=addMember,
	head(Z_inputP,Z_TEMP1),
	Id=Z_TEMP1,
	tail(Z_inputP,Z_TEMP2),
	head(Z_TEMP2,Z_TEMP3),
	Password=Z_TEMP3,
	tail(Z_inputP,Z_TEMP4),
	tail(Z_TEMP4,Z_TEMP5),
	head(Z_TEMP5,Z_TEMP6),
	MemberName=Z_TEMP6,
	SE=[Z_ip,Id,Password,MemberName],
	sendV(Z_callerSA,execute,['z_Initiate',[addMember,This,SE,R]],_),
	R='not_valid_data',
	Z_report=R,
	addChangeOp(Z_State,z_AddMemberInvalidData,[],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_AddMemberAlreadyExistent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=addMember,
	head(Z_inputP,Z_TEMP1),
	Id=Z_TEMP1,
	tail(Z_inputP,Z_TEMP2),
	head(Z_TEMP2,Z_TEMP3),
	Password=Z_TEMP3,
	tail(Z_inputP,Z_TEMP4),
	tail(Z_TEMP4,Z_TEMP5),
	head(Z_TEMP5,Z_TEMP6),
	MemberName=Z_TEMP6,
	SE=[Z_ip,Id,Password,MemberName],
	sendV(Z_callerSA,execute,['z_Initiate',[addMember,This,SE,R]],_),
	R='member_already_existent',
	Z_report=R,
	addChangeOp(Z_State,z_AddMemberAlreadyExistent,[],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_AddBookOK(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=addBook,
	head(Z_inputP,Z_TEMP1),
	Id=Z_TEMP1,
	tail(Z_inputP,Z_TEMP2),
	head(Z_TEMP2,Z_TEMP3),
	BookName=Z_TEMP3,
	tail(Z_inputP,Z_TEMP4),
	tail(Z_TEMP4,Z_TEMP5),
	head(Z_TEMP5,Z_TEMP6),
	Amount=Z_TEMP6,
	SE=[Z_ip,Id,BookName,Amount],
	sendV(Z_callerSA,execute,['z_Initiate',[addBook,This,SE,R]],_),
	R='ok',
	Z_report=R,
	addChangeOp(Z_State,z_AddBookOK,[],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_AddBookInvalidConnection(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=addBook,
	SE=[Z_ip],
	sendV(Z_callerSA,execute,['z_Initiate',[addBook,This,SE,R]],_),
	R='not_valid_connection_or_expired_session',
	Z_report=R,
	addChangeOp(Z_State,z_AddBookInvalidConnection,[],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_AddBookInvalidData(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=addBook,
	head(Z_inputP,Z_TEMP1),
	Id=Z_TEMP1,
	tail(Z_inputP,Z_TEMP2),
	head(Z_TEMP2,Z_TEMP3),
	BookName=Z_TEMP3,
	tail(Z_inputP,Z_TEMP4),
	tail(Z_TEMP4,Z_TEMP5),
	head(Z_TEMP5,Z_TEMP6),
	Amount=Z_TEMP6,
	SE=[Z_ip,Id,BookName,Amount],
	sendV(Z_callerSA,execute,['z_Initiate',[addBook,This,SE,R]],_),
	R='not_valid_data',
	Z_report=R,
	addChangeOp(Z_State,z_AddBookInvalidData,[],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_AddBookAlreadyExistent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=addBook,
	head(Z_inputP,Z_TEMP1),
	Id=Z_TEMP1,
	tail(Z_inputP,Z_TEMP2),
	head(Z_TEMP2,Z_TEMP3),
	BookName=Z_TEMP3,
	tail(Z_inputP,Z_TEMP4),
	tail(Z_TEMP4,Z_TEMP5),
	head(Z_TEMP5,Z_TEMP6),
	Amount=Z_TEMP6,
	SE=[Z_ip,Id,BookName,Amount],
	sendV(Z_callerSA,execute,['z_Initiate',[addBook,This,SE,R]],_),
	R='book_already_existent',
	Z_report=R,
	addChangeOp(Z_State,z_AddBookAlreadyExistent,[],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_DeleteMemberOK(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=deleteMember,
	head(Z_inputP,Z_TEMP1),
	Id=Z_TEMP1,
	SE=[Z_ip,Id],
	sendV(Z_callerSA,execute,['z_Initiate',[deleteMember,This,SE,R]],_),
	R='ok',
	Z_report=R,
	addChangeOp(Z_State,z_DeleteMemberOK,[],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_DeleteMemberInvalidConnection(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=deleteMember,
	SE=[Z_ip],
	sendV(Z_callerSA,execute,['z_Initiate',[deleteMember,This,SE,R]],_),
	R='not_valid_connection_or_expired_session',
	Z_report=R,
	addChangeOp(Z_State,z_DeleteMemberInvalidConnection,[],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_DeleteMemberInvalidData(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=deleteMember,
	head(Z_inputP,Z_TEMP1),
	Id=Z_TEMP1,
	SE=[Z_ip,Id],
	sendV(Z_callerSA,execute,['z_Initiate',[deleteMember,This,SE,R]],_),
	R='not_valid_data',
	Z_report=R,
	addChangeOp(Z_State,z_DeleteMemberInvalidData,[],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_DeleteMemberInexistent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=deleteMember,
	head(Z_inputP,Z_TEMP1),
	Id=Z_TEMP1,
	SE=[Z_ip,Id],
	sendV(Z_callerSA,execute,['z_Initiate',[deleteMember,This,SE,R]],_),
	R='inexistent_record',
	Z_report=R,
	addChangeOp(Z_State,z_DeleteMemberInexistent,[],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_DeleteBookOK(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=deleteBook,
	head(Z_inputP,Z_TEMP1),
	Id=Z_TEMP1,
	SE=[Z_ip,Id],
	sendV(Z_callerSA,execute,['z_Initiate',[deleteBook,This,SE,R]],_),
	R='ok',
	Z_report=R,
	addChangeOp(Z_State,z_DeleteBookOK,[],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_DeleteBookWithLoans(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=deleteBook,
	head(Z_inputP,Z_TEMP1),
	Id=Z_TEMP1,
	SE=[Z_ip,Id],
	sendV(Z_callerSA,execute,['z_Initiate',[deleteBook,This,SE,R]],_),
	R='ok',
	Z_report=R,
	addChangeOp(Z_State,z_DeleteBookWithLoans,[],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_DeleteBookInvalidConnection(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=deleteBook,
	head(Z_inputP,Z_TEMP1),
	Id=Z_TEMP1,
	SE=[Z_ip,Id],
	sendV(Z_callerSA,execute,['z_Initiate',[deleteBook,This,SE,R]],_),
	R='not_valid_connection_or_expired_session',
	Z_report=R,
	addChangeOp(Z_State,z_DeleteBookInvalidConnection,[],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_DeleteBookInvalidData(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=deleteBook,
	head(Z_inputP,Z_TEMP1),
	Id=Z_TEMP1,
	SE=[Z_ip,Id],
	sendV(Z_callerSA,execute,['z_Initiate',[deleteBook,This,SE,R]],_),
	R='not_valid_data',
	Z_report=R,
	addChangeOp(Z_State,z_DeleteBookInvalidData,[],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_DeleteBookInexistent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	Z_event=deleteBook,
	head(Z_inputP,Z_TEMP1),
	Id=Z_TEMP1,
	SE=[Z_ip,Id],
	sendV(Z_callerSA,execute,['z_Initiate',[deleteBook,This,SE,R]],_),
	R='inexistent_record',
	Z_report=R,
	addChangeOp(Z_State,z_DeleteBookInexistent,[],Z_NewState),
	z_Admin(globalState(This,Z_NewState,Z_NewState),Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip_DECORV,Z_device_DECORV,Z_callerSA_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_AssignIP(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip_DECORV,Z_device_DECORV,Z_callerSA_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip_DECORV,Z_device_DECORV,Z_callerSA_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_GetIP(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip_DECORV,Z_device_DECORV,Z_callerSA_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip_DECORV,Z_device_DECORV,Z_callerSA_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_AssignDevice(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip_DECORV,Z_device_DECORV,Z_callerSA_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip_DECORV,Z_device_DECORV,Z_callerSA_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_GetDevice(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip_DECORV,Z_device_DECORV,Z_callerSA_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip_DECORV,Z_device_DECORV,Z_callerSA_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_LoginAdminOK(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip_DECORV,Z_device_DECORV,Z_callerSA_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip_DECORV,Z_device_DECORV,Z_callerSA_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_LoginAdminFailed(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip_DECORV,Z_device_DECORV,Z_callerSA_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip_DECORV,Z_device_DECORV,Z_callerSA_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_LoginAdminInvalidConnection(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip_DECORV,Z_device_DECORV,Z_callerSA_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip_DECORV,Z_device_DECORV,Z_callerSA_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_LoginAdminInexistent(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip_DECORV,Z_device_DECORV,Z_callerSA_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip_DECORV,Z_device_DECORV,Z_callerSA_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report):-
	z_Logout(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip_DECORV,Z_device_DECORV,Z_callerSA_DECORV,Z_components_DECORV,Z_connectors_DECORV,Z_events_DECORV,Z_ports_DECORV),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	z_SearchMemberOK(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	z_SearchMemberInvalidConnection(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	z_SearchBookOK(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	z_SearchBookInvalidConnection(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	z_SearchLoanOK(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	z_SearchLoanInvalidConnection(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	z_BooksRankingOK(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	z_BooksRankingInvalidConnection(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	z_AddLoanInvalidConnection(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	z_AddLoanOK(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	z_AddLoanInvalidData(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	z_AddLoanNotEnoughBooks(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	z_ReturnBookInvalidConnection(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	z_ReturnBookOK(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	z_ReturnBookInvalidData(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	z_AddMemberAlreadyExistent(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	z_AddMemberInvalidConnection(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	z_AddMemberOK(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	z_AddMemberInvalidData(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	z_DeleteMemberInvalidConnection(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	z_DeleteMemberOK(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	z_DeleteMemberInvalidData(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	z_DeleteMemberInexistent(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	z_DeleteBookInvalidConnection(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	z_DeleteBookOK(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	z_DeleteBookInvalidData(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	z_DeleteBookInexistent(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	z_DeleteBookWithLoans(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	z_AddBookInvalidConnection(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	z_AddBookOK(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	z_AddBookInvalidData(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

z_ProcessEvent(globalState(This,Z_State,Z_NewState),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report):-
	z_AddBookAlreadyExistent(globalState(This,Z_State,Z_TEMP1),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),z_Admin(Z_ip,Z_device,Z_callerSA,Z_components,Z_connectors,Z_events,Z_ports),Z_event,Z_instance,Z_inputP,Z_report),
	addChangeOp(Z_TEMP1,z_ProcessEvent,Z_NewState).

init_Admin_stateIni(tree('Root',[tree('Admin',['ip','device','callerSA','components','connectors','events','ports'])])).
