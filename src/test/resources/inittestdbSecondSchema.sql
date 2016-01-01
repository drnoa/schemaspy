
CREATE SCHEMA SECONDSCHEMA;
SET SCHEMA SECONDSCHEMA;

create table ADDRESS (
  addressId integer auto_increment,
  address1 varchar(50) not null comment 'Address line 1',
  address2 varchar(50) comment 'Address line 2 (optional)',
  city varchar(30) not null,
  state char(2) not null,
  zip varchar(10) not null,
  primary key(AddressId)
) ;