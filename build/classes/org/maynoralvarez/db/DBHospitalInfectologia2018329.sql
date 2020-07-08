drop database DBHospitalInfectologia2018329;
create database DBHospitalInfectologia2018329; 
use DBHospitalInfectologia2018329;

create table Medicos(
codigoMedico int not null primary key auto_increment,
	licenciaMedica integer not null,
    nombres varchar(100) not null,
    apellidos varchar(100) not null,
    horaEntrada varchar (10)not null,
    horaSalida varchar (10)not null,
    turnoMaximo int default 0,
    sexo varchar(20) not null);
    
Delimiter $$
create procedure sp_AgregarMedicos (p_licenciaMedica int,p_nombres varchar(100),p_apellidos varchar(100),p_horaEntrada varchar(10),p_horaSalida varchar(10),p_sexo varchar(20))
begin
	insert into Medicos (licenciaMedica,nombres,apellidos,horaEntrada,horaSalida,sexo)
    values (p_licenciaMedica,p_nombres,p_apellidos,p_horaEntrada,p_horaSalida,p_sexo);
end $$

delimiter $$
create procedure sp_ListarMedicos()
begin
	select * from Medicos;
end $$

call sp_ListarMedicos();

delimiter $$
create procedure sp_BuscarMedicos(codMed int)
begin
	Select
	Medicos.codMedico,
    Medicos.licenciaMedica,
    Medicos.nombres,
    Medicos.apellidos,
    Medicos.horaEntrada,
    Medicos.horaSalida,
    Medicos.turnoMaximo,
    Medicos.sexo
    from Medicos where (codMedico = codMed);
end $$
delimiter ;

call sp_BuscarMedicos(1);

delimiter $$
create procedure sp_EliminarMedicos(codMed int)
begin
	delete from Medicos where (codMedico = codMed);
end $$

delimiter ;

call sp_EliminarMedicos (2);

delimiter $$
create procedure sp_EditarMedicos (p_codMedico int,p_licenciaMedica int, p_nombres varchar(100),
p_apellidos varchar(100), p_horaEntrada varchar(10), p_horaSalida varchar(10), p_sexo varchar(20))
begin
	update Medicos
		set licenciaMedica = p_licenciaMedica,
			nombres = p_nombres,
            apellidos = p_apellidos,
            horaEntrada = p_horaEntrada,
            horaSalida = p_horaSalida,
            sexo = p_sexo
			where codMedico = p_codMedico;
end $$
delimiter ;

call sp_EditarMedicos();

-- Tabla Pacientes -- 

create table Pacientes(
codigoPaciente int not null primary Key auto_increment,
	DPI varchar(50),
    apellidos varchar(100),
    nombres varchar (100),
    fechaNacimiento varchar(100),
    edad varchar (20),
    direccion varchar (100),
    ocupacion varchar (100),
    sexo varchar(20)
); 

delimiter $$ 
create procedure sp_agregarPaciente(p_DPI varchar(20),p_apellidos varchar(100),p_nombres varchar(100),
p_fechaNacimiento varchar(100),p_edad varchar (20),p_direccion varchar(100),p_ocupacion varchar(100),p_sexo varchar(120))
begin 
	insert into Pacientes (DPI, apellidos, nombres, fechaNacimiento, edad, direccion, ocupacion, sexo)
    values (p_DPI, p_apellidos, p_nombres, p_fechaNacimiento, p_edad, p_direccion, p_ocupacion, p_sexo);
end $$ 
delimiter ; 

delimiter $$
create procedure sp_ListarPacientes()
begin
	select * from Pacientes;
end $$

call sp_ListarPacientes();

delimiter $$
create procedure sp_BuscarPacientes(DPI int)
begin
	Select
	Pacientes.DPI,
    Pacientes.apellidos,
    Pacientes.nombres,
    Pacientes.fechaNacimiento,
    Pacientes.edad,
    Pacientes.direccion,
    Pacientes.Ocupacion,
    Pacientes.sexo
    from Pacientes where (DPI = DPI);
end $$
delimiter ;

call sp_BuscarPacientes(2);

delimiter $$
create procedure sp_EliminarPacientes(DPI int)
begin
	delete from Pacientes where (DPI = DPI);
end $$
delimiter ;

call sp_EliminarPacientes (2);

delimiter $$
create procedure sp_EditarPacientes (p_DPI varchar(20),p_apellidos varchar(100),p_nombres varchar(100),
p_fechaNacimiento varchar(100),p_edad varchar (20),p_direccion varchar(100),p_ocupacion varchar(100),p_sexo varchar(120))
begin
	update Pacientes
		set apellidos = p_apellidos, 
        nombres =p_nombres,
        fechaNacimiento =p_fechaNacimiento , 
        edad =p_edad ,
        direccion = p_direccion, 
        ocupacion = p_ocupacion,
        sexo = p_sexo 
			where DPI = p_DPI;
end $$
delimiter ;

call sp_EditarMedicos();

-- Tabla Turnos --

create table Turnos (
	codigoTurno int not null primary key auto_increment,
    fechaTurno varchar(100),
    fechaCita varchar(100),
    valorCita decimal (10,2),
    codigoMedicoEspecialidad int, 
    codigoResponsableTurno int,
    codigoPaciente int,
    foreign key (codigoMedicoEspecialidad) references Medico_Especialidad(codigoMedicoEspecialidad),
    foreign key (codigoResponsableTurno) references ResponsableTurno(codigoResponsableTurno),
    foreign key (codigoPaciente) references Pacientes(codigoPaciente)
);

DELIMITER $$ 
create procedure sp_agregarTurno(p_fechaTurno varchar(100), p_fechaCita varchar(100), p_valorCita decimal(10,2),
p_codigoMedicoEspecialidad int(10), p_codigoResponsableTurno int(10), p_codigoPaciente int(10))
begin
	insert into Turnos(fechaTurno, fechaCita, valorCita, codigoMedicoEspecialidad, codigoResponsableTurno, codigoPaciente)
			values(p_fechaTurno, p_fechaCita, p_valorCita, p_codigoMedicoEspecialidad, p_codigoResponsableTurno, p_codigoPaciente);
end $$ 
delimiter ; 

delimiter $$
create procedure sp_ListarTurnos()
begin
	select * from Turnos;
end $$

call sp_ListarTurnos();
 
DELIMITER $$ 
create procedure sp_buscarTurno(CodigoTurno int)
begin 
	select Turnos.codigoTurno,
    Turnos.fechaTurno,
    Turnos.fechaCita,
    Turnos.valorCita,
    Turnos.codigoMedicoEspecialidad,
    Turnos.codigoResponsableTurno,
    Turnos.codigoPaciente
    from Turnos where (codigoTurno = codigoTurno);
end $$ 
DELIMITER ;

call sp_BuscarTurnos(2);

delimiter $$
create procedure sp_EliminarTurnos(codigoTurno int)
begin
	delete from Turnos where (codigoTurno = codigoTurno);
end $$
delimiter ;

call sp_EliminarTurnos (2);

DELIMITER $$ 
create procedure sp_editarTurno(p_fechaTurno varchar(100), p_fechaCita varchar(100), p_valorCita decimal(10,2),
p_codigoMedicoEspecialidad int(10), p_codigoResponsableTurno int(10), p_codigoPaciente int(10))
begin
	update Turnos
    set fechaTurno = p_FechaTurno,
    fechaCita = p_fechaCita,
    valorCita = p_valorCita,
    codigoMedicoEspecialidad = p_codigoMedicoEspecialidad,
    codigoResponsableTurno = p_codigoResponsableTurno,
    codigoPaciente = p_codigoPaciente
    where codigoTurno = p_codigoTurno;
end $$
DELIMITER ; 

call sp_editarTurno(); 


-- tabla Medico_especialidad -- 

create table Medico_Especialidad (
	codigoMedicoEspecialidad int not null primary key auto_increment,
    codigoMedico int, 
    codigoEspecialidad int, 
    codigoHorario int,
    foreign key (codigoMedico) references Medicos(codigoMedico),
    foreign key (codigoEspecialidad) references Especialidades(codigoEspecialidad),
    foreign key (codigoHorario) references Horarios(codigoHorario)
); 

DELIMITER $$ 
create procedure sp_agregarMedico_Especialidad(p_codigoMedico int,p_codigoEspecialidad int,p_codigoHorario int)
begin 
	insert into Medico_Especialidad( codigoMedico, codigoEspecialidad, codigoHorario)
    values (p_codigoMedico,p_codigoEspecialidad,p_codigoHorario);
end $$ 
DELIMITER ;

DELIMITER $$ 
create procedure sp_listarMedico_Especialidad()
begin 
	select * from Medico_Especialidad;
end $$ 
DELIMITER ;

call sp_listarMedico_especialidad();

DELIMITER $$ 
create procedure sp_buscarMedico_Especialidad(p_codigoMedico_Especialidad int)
begin 
	select Medico_especialidad.codigoMedico_especialidad,
    Medico_especialidad.codigoMedico,
    Medico_especialidad.codigoEspecialidad,
    Medico_especialidad.codigoHorario
    where codigoMedico_Especialidad = p_codigoMedico_Especialidad;
end $$ 
DELIMITER ; 

call sp_buscarMedico_Especialidad();

DELIMITER $$ 
create procedure sp_eliminarMedico_Especialidad(codigoMedicoEspecialidad int)
begin 
	delete from Medico_Especialidad where (codigoMedicoEspecialidad = codigoMedicoEspecialidad);
end $$ 
DELIMITER ;

call sp_eliminarMedico_especialidad();

DELIMITER $$ 
create procedure sp_editarMedico_Especialidad(p_codigoMedico int,p_codigoEspecialidad int,p_codigoHorario int)
begin 
	update Medico_Especialidad
    set codigoMedico = p_codigoMedico,
    codigoEspecialidad = p_codigoEspecialidad ,
    codigoHorario = p_codigoHorario
    where codigoMedicoEspecialidad = p_codigoMedicoEspecialidad;
end $$ 
DELIMITER ; 

call sp_EditarMedico_Especialidad();

-- tabla ResponsableTurno -- 

create table ResponsableTurno(
	codigoResponsableTurno int not null primary key auto_increment,
    nombreResponsable varchar(100),
    apellidosResponsable varchar(100),
    telefonoPersonal varchar(100), 
    codigoArea int ,
    codigoCargo int, 
    foreign key (codigoArea) references Areas(codigoArea),
    foreign key (codigoCargo) references Cargos(codigoCargo)
);

DELIMITER $$ 
create procedure sp_ResponsableTurno (p_nombreResponsable varchar(100),p_apellidosResponsable varchar(100),p_telefonoPersonal varchar(100), 
p_codigoArea int ,p_codigoCargo int)
begin 
	insert into ResponsableTurno(nombreResponsable, apellidosResponsable, telefonoPersonal, codigoArea, codigoCargo)
    values (p_nombreResponsable, p_apellidosResponsable, p_telefonoPersonal, p_codigoArea, p_codigoCargo);
end $$ 
DELIMITER ;

DELIMITER $$ 
create procedure sp_listarResponsableTurno()
begin 
	select * from ResponsableTurno;
end $$ 
DELIMITER ;

call sp_listarResponsableTurno; 

DELIMITER $$ 
create procedure sp_buscarResponsableTurno(p_codigoReponsableTurno int)
begin 
	select ResponsableTurno.codigoResponsableTurno,
    ResponsableTurno.nombreResponsable,
    ResponsableTurno.apellidosResponsable,
    ResponsableTurno.telefonoPersonal,
    ResponsableTurno.codigoArea,
    ResponsableTurno.codigoCargo
    where codigoResponsableTurno = p_codigoResponsableTurno;
end $$ 

call sp_buscarResponsableTurno();

DELIMITER $$ 
create procedure sp_eliminarResponsableTurno(p_codigoResponsableTurno int)
begin 
	delete from ResponsableTurno where (codigoResponsableTurno = p_codigoResponsableTurno);
end $$ 
DELIMITER ; 

call sp_eliminarResponsableTurno();


DELIMITER $$ 
create procedure sp_editarResponsableTurno(p_nombreResponsable varchar(100),p_apellidosResponsable varchar(100),p_telefonoPersonal varchar(100), 
p_codigoArea int ,p_codigoCargo int)
begin 
	update ResponsableTurno 
    set nombreResponsable = p_nombreResponsable,
    apellidosResponsable = p_apellidosResponsable,
    telefonoPersonal = p_telefonoPersonal
    where codigoResponsableTurno = p_codigoResponsableTurno;
end $$ 
DELIMITER ; 

call sp_editarResponsableTurno();

-- tabla Areas -- 

create table Areas(
	codigoArea int not null primary key auto_increment,
    nombreArea varchar (100)
);

DELIMITER $$ 
create procedure sp_agregarAreas(p_codigoArea int, p_nombrArea varchar(100))
begin 
	insert into Areas(codigoArea, nombrArea)
    values (p_codigoArea, p_nombrArea);
end $$ 
DELIMITER ;

DELIMITER $$ 
create procedure sp_listarAreas()
begin 
	select * from Areas;
end $$
DELIMITER ;

call sp_listarAreas (); 

DELIMITER $$ 
create procedure sp_buscarAreas(p_codigoArea int)
begin 
	select Areas.codigoArea,
    Areas.NombrArea
    where codigoArea = p_codigoArea;
end $$ 
DELIMITER ; 

call sp_buscarAreas();

DELIMITER $$ 
create procedure sp_eliminarAreas(p_codigoArea int)
begin 
	delete from Areas where codigoArea = p_codigoArea; 
end $$
DELIMITER ;

call sp_eliminarAreas();

DELIMITER $$ 
create procedure sp_editarAreas(p_codigoArea int, p_nombrArea varchar(100))
begin 
	update Areas
    set nombrArea = p_nombrArea
    where codigoArea = p_codigoArea;
end $$
DELIMITER ; 

call sp_editarArea();

-- tabla Cargos -- 

create table Cargos(
	codigoCargo int not null primary key auto_increment,
    nombreCargo varchar(100)
);

DELIMITER $$ 
create procedure sp_agregarCargos(p_nombreCargo varchar(100))
begin 
	insert into Cargos (nombreCargo ) 
    values (p_nombreCargo );
end $$
DELIMITER ; 

DELIMITER $$ 
create procedure sp_listarCargos()
begin 
	select * from Cargos;
end $$
DELIMITER ;

call sp_listarCargos();

DELIMITER $$ 
create procedure sp_buscarCargos(p_codigoCargos int)
begin 
	select Cargos.codigoCargos,
    Cargos.nombreCargo
    where codigoCargos = p_codigoCargos;
end $$ 
DELIMITER ;

call sp_buscarCargos();

DELIMITER $$
create procedure sp_eliminarCargos(p_codigoCargos int)
begin 
	delete from Cargos where (codigoCargos = p_codigoCargos);
end $$ 
DELIMITER ;

call sp_eliminarCargos();

DELIMITER $$ 
create procedure sp_editarCargos(p_codigoCargo int,p_nombreCargo varchar(100))
begin 
	update Cargo 
    set nombreCargo = p_nombreCargo
    where codigoCargo = p_codigoCargo;
end $$
DELIMITER ;

call sp_editarCargos();

-- tabla Especialidades -- 

create table Especialidades (
	codigoEspecialidad int not null primary key auto_increment,
    nombreEspecialidad varchar(100)
); 

DELIMITER $$ 
create procedure sp_agregarEspecialidades(p_codigoEspecialidad int,p_nombreEspecialidad varchar(100))
begin 
	insert into Especialidades(codigoEspecialidad ,nombreEspecialidad)
    values (p_codigoEspecialidad, p_nombreEspecialidad);
end $$ 
DELIMITER ; 

DELIMITER $$ 
create procedure sp_listarEspecialidades()
begin  
	select * from Especialidades; 
end $$ 
DELIMITER ; 

call sp_listarEspecialidades;

DELIMITER $$ 
create procedure sp_buscarEspecialidades(p_codigoEspecialidades int)
begin 
	select Especialidades.codigoEspecialidades,
    Especialidades.nombresEspecialidad
    where codigoEspecialidades = p_codigoEspecialidade; 
end $$ 
DELIMITER ;

call sp_buscarEspecialidades();

DELIMITER $$ 
create procedure sp_eliminarEspecialidades(p_codigoEspecialidades int)
begin 
	delete from Especialidades where codigoEspecialidades = p_codigoEspecialidades;
end $$ 
DELIMITER ; 

call sp_eliminarEspecialidades(); 

DELIMITER $$ 
create procedure sp_editarEspecialidades(p_codigoEspecialidad int,p_nombreEspecialidad varchar(100))
begin 
	update Especialidades 
    set nombreEspecialidad = p_nombreEspecialidad
    where codigoEspecialidad = p_codigoEspecialidad; 
end $$ 
DELIMITER ; 

call sp_editarEspecialidades();

-- tabla Horario -- 

create table Horarios (
	codigoHorario int not null primary key auto_increment,
    horarioInicio varchar(100),
    horarioSalida varchar(100),
    lunes tinyint,
    martes tinyint,
    miercoles tinyint,
    jueves tinyint,
    viernes tinyint
);

DELIMITER $$ 
create procedure sp_agregarHorarios(p_horarioInicio varchar(100),p_horarioSalida varchar(100),
p_lunes tinyint,p_martes tinyint,p_miercoles tinyint,p_jueves tinyint,p_viernes tinyint)
begin 
	insert into Horarios (horarioInicio, horarioSalida, lunes, martes, miercoles, jueves, viernes)
    values (p_codigoHorario, p_horarioInicio, p_horarioSalida, p_lunes, p_martes, p_miercoles, p_jueves, p_viernes);
end $$ 
DELIMITER ;

DELIMITER $$ 
create procedure  sp_listarHorarios()
begin 
	select * from Horarios; 
end $$ 
DELIMITER ; 

call sp_listarHorarios;

DELIMITER $$ 
create procedure sp_buscarHorarios(p_codigoHorario int )
begin 
	select Horarios.codigoHorario,
    Horarios.horarioInicio,
    Horarios.horarioSalida,
    Horarios.lunes,
    Horarios.martes,
    Horarios.miercoles,
    Horarios.jueves,
    Horarios.viernes
    where codigoHorario = p_codigoHorario;
end $$ 
DELIMITER ;

call sp_buscarHorarios();

DELIMITER $$ 
create procedure sp_eliminarHorarios(p_codigoHorario int)
begin 
	delete from Horarios where codigoHorario = p_codigoHorario;
end $$ 
DELIMITER ;

call sp_eliminarHorarios();

DELIMITER $$ 
create procedure sp_editarHorarios(p_codigoHorario int, p_horarioInicio varchar(100),p_horarioSalida varchar(100),
p_lunes tinyint,p_martes tinyint,p_miercoles tinyint,p_jueves tinyint,p_viernes tinyint)
begin 
	update Horarios
    set horarioInicio = p_horarioInicio,
    horarioSalida = p_horarioSalida,
    lunes = p_lunes,
    martes = p_martes,
    miercoles = p_miercoles,
    jueves = p_jueves, 
    vieres = p_viernes
    where codigoHorario = p_codigoHorario;
end $$ 
DELIMITER ;

call sp_editarHorarios ();

-- tabla TelefonosMedicos

create table TelefonosMedico(
	codigoTelefonoMedico int not null primary key auto_increment,
    telefonoPersonal varchar(15),
    telefonoTrabajo varchar(15),
    codigoMedico int,
    foreign key (codigoMedico) references Medicos (codigoMedico)
);

DELIMITER $$ 
create procedure sp_agregarTelefonosMedico(p_telefonoPersonal varchar(15),
p_telefonoTrabajo varchar(15),p_codigoMedico int)
begin 
	insert into TelefonosMedico( telefonoPersonal, telefonoTrabajo, codigoMedico)
    values (p_telefonoPersonal,p_telefonoTrabajo ,p_codigoMedico );
end $$ 
DELIMITER ;

DELIMITER $$ 
create procedure sp_listarTelefonosMedico()
begin 
	select * from TelefonosMedico;
end $$ 
DELIMITER ;

call sp_listarTelefonosMedico;

DELIMITER $$ 
create procedure sp_buscarTelefonosMedico(p_codigoTelefonoMedico int )
begin 
	select TelefonosMedico.codigoTelefonoMedico,
    TelefonosMedico.telefonoPersonal,
    TelefonosMedico.telefonoTrabajo,
    TelefonosMedico.CodigoMedico
    where codigoTelefonoMedico = p_codigoTelefonoMedico;
end $$ 
DELIMITER ;

call sp_buscarTelefonosMedico();

DELIMITER $$ 
create procedure sp_eliminarTelefonosMedico(p_codigoTelefonoMedico int)
begin 
delete from TelefonosMedico where codigoTelefonoMedico = p_codigoTelefonoMedico;
end $$ 
DELIMITER ;

call sp_eliminarTelefonosMedico();

DELIMITER $$ 
create procedure sp_editarTelefonosMedico(p_codigoTelefonoMedico int ,p_telefonoPersonal varchar(15),
p_telefonoTrabajo varchar(15),p_codigoMedico int)
begin 
	update TelefonosMedico
    set telefonoPersonal = p_telefonoPersonal,
    telefonoTrabajo = p_telefonoTrabajo,
    codigoMedico = p_codigoMedico
    where codigoTelefonoMedico = p_codigoTelefonoMedico;
end $$ 
DELIMITER 

call sp_editarTelefonosMedico();

-- tabla ContactoUrgencia --

create table ContactoUrgencia(
	codigoContactoUrgencia int not null primary key auto_increment,
    nombres varchar (100),
    apellidos varchar (100),
    numeroContacto varchar(100),
    codigoPaciente int,
    foreign key (codigoPaciente) references Pacientes(codigoPaciente)
);


DELIMITER $$ 
create procedure sp_agregarContactoUrgencia(p_nombres varchar (100),p_apellidos varchar (100),p_numeroContacto varchar(100),p_codigoPaciente int)
begin 
	insert into ContactoUrgencia (nombres, apellidos, numeroContacto, codigoPaciente)
    values (p_nombres,p_apellidos ,p_numeroContacto ,p_codigoPaciente);
end $$ 
DELIMITER ; 

DELIMITER $$ 
create procedure sp_listarContactoUrgencia()
begin 
	select * from ContactoUrgencia;
end $$ 
DELIMITER ;

call sp_listarContactoUrgencia;

DELIMITER $$ 
create procedure sp_buscarContactoUrgencia(p_codigoContactoUrgencia int)
begin 
	select ContactoUrgencia.CodigoContactoUrgencia,
    ContactoUrgencia.nombres,
    ContactoUrgencia.apellidos,
    ContactoUrgencia.numeroContacto,
    ContactoUrgencia.codigoPaciente
    where codigoContactoUrgencia = p_codigoContactoUrgencia;
end $$ 
DELIMITER ;

call sp_buscarContactoUrgencia ();

DELIMITER $$ 
create procedure sp_eliminarContactoUrgencia(p_codigoContactoUrgencia int)
begin 
	delete from ContactoUrgencia where codigoContactoUrgencia = P_codigoContactoUrgencia; 
end $$ 
DELIMITER $$ 

call sp_eliminarContactoUrgencia();

DELIMITER $$ 
create procedure sp_editarContactoUrgencia(	p_codigoContactoUrgencia int,p_nombres varchar (100),p_apellidos varchar (100),
p_numeroContacto varchar(100),p_codigoPaciente int)
begin 
	update ContactoUrgencia 
    set nombres = p_nombres,
    apellidos = p_apellidos,
    numeroContacto = p_numeroContacto,
    codigoPaciente = p_codigoPaciente
    where codigoContatoUrgencia = p_codigoContactoUrgencia;
end $$ 
DELIMITER ;
