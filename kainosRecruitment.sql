use team_A;

create table jobRoles(jobRoleID mediumint primary key auto_increment,
 roleName varchar(50),
 Specification varchar(500),
 Link varchar(300)); 
 
create table capabilities(capabilityID smallint primary key auto_increment,
 capabilityName varchar(50),
 jobRoleID mediumint,
 foreign key (jobRoleID) references jobRoles(jobRoleID));
 
create table band(bandID smallint primary key auto_increment,
 bandName varchar(50),
 jobRoleID mediumint,
 foreign key (jobRoleID) references jobRoles(jobRoleID));
 
