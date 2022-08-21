


drop database test;
create database test;
use test ;

CREATE TABLE i_r(
id INT auto_increment,
referencee VARCHAR(20),
descriptionne VARCHAR(500),
unit VARCHAR(10),
PRIMARY KEY(id)
);



CREATE TABLE transport_internal(
id INT auto_increment,
transport DOUBLE DEFAULT 0,
couler DOUBLE DEFAULT 0,
vehicle_service DOUBLE DEFAULT 0,
total DOUBLE DEFAULT 0,
PRIMARY KEY(id)
);

CREATE TABLE transport_external(
id INT auto_increment,
echange DOUBLE DEFAULT 0,
surstarie DOUBLE DEFAULT 0,
transport DOUBLE DEFAULT 0,
taux_change DOUBLE DEFAULT 0,
total DOUBLE DEFAULT 0,
PRIMARY KEY(id)
);

CREATE TABLE transport_costs(
id INT auto_increment,
external_total DOUBLE DEFAULT 0,
internal_total DOUBLE DEFAULT 0,
total DOUBLE DEFAULT 0,
external_id INT,
internal_id INT,
PRIMARY KEY(id),
CONSTRAINT FK_TRANSPORT_external FOREIGN KEY(external_id)
REFERENCES transport_external(id),
CONSTRAINT FK_TRANSPORT_internal FOREIGN KEY(internal_id)
REFERENCES transport_internal(id)
);

CREATE TABLE transition_costs(
id INT auto_increment,
transition_costs DOUBLE DEFAULT 0,
servier DOUBLE DEFAULT 0,
epa DOUBLE DEFAULT 0,
sil DOUBLE DEFAULT 0,
parc DOUBLE DEFAULT 0,
variable DOUBLE DEFAULT 0,
total DOUBLE DEFAULT 0,
PRIMARY KEY(id)
);

CREATE TABLE banc_costs(
id INT auto_increment,
reglement_def DOUBLE DEFAULT 0,
frais_swift DOUBLE DEFAULT 0,
frais_disblocage DOUBLE DEFAULT 0,
domic_document1 DOUBLE DEFAULT 0,
domic_document2 DOUBLE DEFAULT 0,
domic_document3 DOUBLE DEFAULT 0,
frais_corresponant DOUBLE DEFAULT 0,
total DOUBLE DEFAULT 0,
PRIMARY KEY(id)
);

CREATE TABLE customs_costs(
id INT auto_increment,
tcs DOUBLE DEFAULT 0,
d_douane DOUBLE DEFAULT 0,
tva DOUBLE DEFAULT 0,
q_douane DOUBLE DEFAULT 0,
cx DOUBLE DEFAULT 0,
total DOUBLE DEFAULT 0,
ctotal DOUBLE DEFAULT 0,
PRIMARY KEY(id)
);

CREATE TABLE variable_costs(
id INT auto_increment,
frais_mission DOUBLE DEFAULT 0,
clarque DOUBLE DEFAULT 0,
servier DOUBLE DEFAULT 0,
repat DOUBLE DEFAULT 0,
acts DOUBLE DEFAULT 0,
variable DOUBLE DEFAULT 0,
total DOUBLE DEFAULT 0,
PRIMARY KEY(id)
);

CREATE TABLE folders(
id INT auto_increment,
n_facture VARCHAR(20),
banc VARCHAR(50) DEFAULT "",
porte VARCHAR(50) DEFAULT "",
datetim DATETIME(6),
total DOUBLE DEFAULT 0,
transport_id INT,
transit_id INT,
banc_id INT,
customs_id INT,
variable_id INT,
PRIMARY KEY(id),
CONSTRAINT FK_folders_TRANSPORT FOREIGN KEY(transport_id)
REFERENCES transport_costs(id),
CONSTRAINT FK_folders_TRANSIT FOREIGN KEY(transit_id)
REFERENCES transition_costs(id),
CONSTRAINT FK_folders_banc FOREIGN KEY(banc_id)
REFERENCES banc_costs(id),
CONSTRAINT FK_folders_customs FOREIGN KEY(customs_id)
REFERENCES customs_costs(id),
CONSTRAINT FK_folders_VARIABLE FOREIGN KEY(variable_id)
REFERENCES variable_costs(id)
);

CREATE TABLE fournisseurs(
id INT auto_increment,
nom VARCHAR(20),
pay VARCHAR(50),
compagnie VARCHAR(50),
PRIMARY KEY(id)
);

CREATE TABLE ir_register_dachats(
id INT auto_increment,
i_r_id INT,
folder_id INT,
datetim DATETIME(6),
fournisseur_id INT,
s_amount DOUBLE DEFAULT 0,
amount_on_s DOUBLE DEFAULT 0,
amount DOUBLE DEFAULT 0,
unit_priceda DOUBLE DEFAULT 0,
unit_price DOUBLE DEFAULT 0,
taux DOUBLE DEFAULT 0,
total DOUBLE DEFAULT 0,
PRIMARY KEY(id),
CONSTRAINT FK_FOLDERS_REGISTERDACHATS FOREIGN KEY(folder_id)
REFERENCES folders(id),
CONSTRAINT FK_IR_REGISTERDACHATS FOREIGN KEY(i_r_id)
REFERENCES i_r(id),
CONSTRAINT FK_FOURNISSEURS_REGISTERDACHATS FOREIGN KEY(fournisseur_id)
REFERENCES fournisseurs(id)
);


CREATE TABLE employee(
id INT auto_increment,
nom VARCHAR(50),
phone_number VARCHAR(14),
begining_date DATE,
salary DOUBLE DEFAULT 0,
post VARCHAR(50),

PRIMARY KEY(id)
);

CREATE TABLE products(
id INT auto_increment,
referencee VARCHAR(50),
descriptionne VARCHAR(14),
PRIMARY KEY(id)
);

CREATE TABLE factory(
id INT auto_increment,

daily_p INT DEFAULT 0,
yearly_p INT DEFAULT 0,
employee_n INT DEFAULT 0,
e_c DOUBLE DEFAULT 0,
f_c DOUBLE DEFAULT 0,
rep_c DOUBLE DEFAULT 0,
r_c DOUBLE DEFAULT 0,
PRIMARY KEY(id)
);

INSERT INTO factory()
VALUES();



