
    create table Categoria (
        id bigint not null auto_increment,
        nombre varchar(255),
        organizacion_id bigint,
        primary key (id)
    )

    create table Ciudad (
        id bigint not null auto_increment,
        idCiudad varchar(255),
        nombre varchar(255),
        provincia_id bigint,
        primary key (id)
    )

    create table Egreso (
        id bigint not null auto_increment,
        criterioSeleccion integer,
        numero integer,
        tipo integer,
        estado integer,
        fecha date,
        totalOperacion integer,
        medio_de_pago_id bigint,
        proveedor_id bigint,
        primary key (id)
    )

    create table Egreso_Etiqueta (
        Egreso_id bigint not null,
        etiquetas_id bigint not null
    )

    create table Egreso_Usuario (
        Egreso_id bigint not null,
        revisoresDeCompra_id bigint not null
    )

    create table EntidadBase (
        id bigint not null auto_increment,
        descripcion varchar(255),
        nombreFicticio varchar(255),
        categoria_id bigint,
        organizacion_id bigint,
        entidad_juridica_id bigint,
        primary key (id)
    )

    create table EntidadBase_Egreso (
        EntidadBase_id bigint not null,
        operacionesDeEgresos_id bigint not null
    )

    create table EntidadJuridica (
        id bigint not null auto_increment,
        codigoDeInscripcionIGJ integer not null,
        cuit integer not null,
        direccionPostal integer not null,
        nombreFicticio varchar(255),
        razonSocial varchar(255),
        tipoEntidadJuridica integer,
        categoria_id bigint,
        organizacion_id bigint,
        primary key (id)
    )

    create table EntidadJuridica_Egreso (
        EntidadJuridica_id bigint not null,
        operacionesDeEgresos_id bigint not null
    )

    create table Etiqueta (
        id bigint not null auto_increment,
        nombre varchar(255),
        organizacion_id bigint,
        primary key (id)
    )

    create table Item (
        id bigint not null auto_increment,
        descrpicion varchar(255),
        nombre varchar(255),
        valor double precision not null,
        presupuesto_id bigint,
        egreso_id bigint,
        primary key (id)
    )

    create table MedioDePago (
        id bigint not null auto_increment,
        identificacion integer not null,
        tipo integer,
        primary key (id)
    )

    create table MensajeValidacionEgreso (
        id bigint not null auto_increment,
        contenido varchar(255),
        visto bit not null,
        egreso_id bigint,
        usuario_id bigint,
        primary key (id)
    )

    create table Organizacion (
        id bigint not null auto_increment,
        primary key (id)
    )

    create table Pais (
        id bigint not null auto_increment,
        idPais varchar(255),
        moneda varchar(255),
        nombre varchar(255),
        primary key (id)
    )

    create table Presupuesto (
        id bigint not null auto_increment,
        numero integer,
        tipo integer,
        total integer,
        proveedor_id bigint,
        egreso_id bigint,
        primary key (id)
    )

    create table Proveedor (
        id bigint not null auto_increment,
        cuil integer not null,
        cuit integer not null,
        altura integer not null,
        calle varchar(255),
        codigoPostal integer not null,
        dpto integer not null,
        piso integer not null,
        razonSocial varchar(255),
        ciudad_id bigint,
        pais_id bigint,
        provincia_id bigint,
        primary key (id)
    )

    create table Provincia (
        id bigint not null auto_increment,
        idProvincia varchar(255),
        nombre varchar(255),
        pais_id bigint,
        primary key (id)
    )

    create table ReglaDeNegocio (
        tipo integer not null,
        id bigint not null auto_increment,
        tipoDeRegla integer,
        montoMaximo integer,
        categoria_id bigint,
        primary key (id)
    )

    create table Usuario (
        id bigint not null auto_increment,
        password varchar(255),
        tipoUsuario integer,
        username varchar(255),
        organizacion_id bigint,
        primary key (id)
    )

    alter table EntidadBase_Egreso 
        add constraint UK_k4c5nhm9xcu5al5srs4ec4q7o  unique (operacionesDeEgresos_id)

    alter table EntidadJuridica_Egreso 
        add constraint UK_ow9bsy2bqb3s4ul2cxire3crk  unique (operacionesDeEgresos_id)

    alter table Categoria 
        add constraint FK_c9uk85m8cpqrs9e65eflxfb69 
        foreign key (organizacion_id) 
        references Organizacion (id)

    alter table Ciudad 
        add constraint FK_5ikrywkh9872kr0g4ftqfaixa 
        foreign key (provincia_id) 
        references Provincia (id)

    alter table Egreso 
        add constraint FK_ejbm4l4qvo0tq7nsixemcgscc 
        foreign key (medio_de_pago_id) 
        references MedioDePago (id)

    alter table Egreso 
        add constraint FK_f182asq2en5q5iy1slby7cf3w 
        foreign key (proveedor_id) 
        references Proveedor (id)

    alter table Egreso_Etiqueta 
        add constraint FK_2l7e12ibp8tuslu3d7te0jd3t 
        foreign key (etiquetas_id) 
        references Etiqueta (id)

    alter table Egreso_Etiqueta 
        add constraint FK_l3jfglj6ywyljnwu1wjbfstir 
        foreign key (Egreso_id) 
        references Egreso (id)

    alter table Egreso_Usuario 
        add constraint FK_qpt1ob1bc7eg2m18eajcrjivs 
        foreign key (revisoresDeCompra_id) 
        references Usuario (id)

    alter table Egreso_Usuario 
        add constraint FK_3e10pw6hd7lxctxrx723tdcy8 
        foreign key (Egreso_id) 
        references Egreso (id)

    alter table EntidadBase 
        add constraint FK_8sg4y5klsgmii4nq69p14n9uv 
        foreign key (categoria_id) 
        references Categoria (id)

    alter table EntidadBase 
        add constraint FK_jiikuladh8cfp2p1q510y54ov 
        foreign key (organizacion_id) 
        references Organizacion (id)

    alter table EntidadBase 
        add constraint FK_mfoc36913ex6fmus69kekk3ih 
        foreign key (entidad_juridica_id) 
        references EntidadJuridica (id)

    alter table EntidadBase_Egreso 
        add constraint FK_k4c5nhm9xcu5al5srs4ec4q7o 
        foreign key (operacionesDeEgresos_id) 
        references Egreso (id)

    alter table EntidadBase_Egreso 
        add constraint FK_gdpj53foeab24vwi8gljlhxs2 
        foreign key (EntidadBase_id) 
        references EntidadBase (id)

    alter table EntidadJuridica 
        add constraint FK_p9o68hde0t4jbtvw2ayglliuj 
        foreign key (categoria_id) 
        references Categoria (id)

    alter table EntidadJuridica 
        add constraint FK_4l5lyq5kpjva4p4cwg2enhplu 
        foreign key (organizacion_id) 
        references Organizacion (id)

    alter table EntidadJuridica_Egreso 
        add constraint FK_ow9bsy2bqb3s4ul2cxire3crk 
        foreign key (operacionesDeEgresos_id) 
        references Egreso (id)

    alter table EntidadJuridica_Egreso 
        add constraint FK_k4vqv62vtnf6gi00vo21j9hin 
        foreign key (EntidadJuridica_id) 
        references EntidadJuridica (id)

    alter table Etiqueta 
        add constraint FK_g9yg4sigleev3gg6y9y2mxpxs 
        foreign key (organizacion_id) 
        references Organizacion (id)

    alter table Item 
        add constraint FK_kaob51dneofur9wsdjswwopwp 
        foreign key (presupuesto_id) 
        references Presupuesto (id)

    alter table Item 
        add constraint FK_4b1tvj0bkyel3p347xe5fsm4x 
        foreign key (egreso_id) 
        references Egreso (id)

    alter table MensajeValidacionEgreso 
        add constraint FK_hv4e30g1dpikqr9teltwlki3w 
        foreign key (egreso_id) 
        references Egreso (id)

    alter table MensajeValidacionEgreso 
        add constraint FK_atphl4e605866vkbnpqxta5pw 
        foreign key (usuario_id) 
        references Usuario (id)

    alter table Presupuesto 
        add constraint FK_rn5b85n7ocgxag5ouwm26i3ig 
        foreign key (proveedor_id) 
        references Proveedor (id)

    alter table Presupuesto 
        add constraint FK_9k6p17lpxgtadue29er4wtgk6 
        foreign key (egreso_id) 
        references Egreso (id)

    alter table Proveedor 
        add constraint FK_rnpswxalbc1oqapjp70ynw8it 
        foreign key (ciudad_id) 
        references Ciudad (id)

    alter table Proveedor 
        add constraint FK_g4qillx8gsmh5f2enoxp3i7b5 
        foreign key (pais_id) 
        references Pais (id)

    alter table Proveedor 
        add constraint FK_bdyh5algbmlrgbotpvmobt5ps 
        foreign key (provincia_id) 
        references Provincia (id)

    alter table Provincia 
        add constraint FK_rnuo84qxl84o49uath6gavuqq 
        foreign key (pais_id) 
        references Pais (id)

    alter table ReglaDeNegocio 
        add constraint FK_1v706ubr8h1c24d76e4qcsbnr 
        foreign key (categoria_id) 
        references Categoria (id)

    alter table Usuario 
        add constraint FK_ns3ep771l5fjk4jhdxm0lybo8 
        foreign key (organizacion_id) 
        references Organizacion (id)
