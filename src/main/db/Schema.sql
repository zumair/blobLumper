
DROP DATABASE IF EXISTS blobLumper;
CREATE DATABASE IF NOT EXISTS blobLumper;
USE blobLumper;

CREATE TABLE IF NOT EXISTS TempBucketDetails (
    id integer(25) NOT NULL AUTO_INCREMENT,
    mostSignificantBits  integer(25) NOT NULL,
    leastSignificantBits integer(25) NOT NULL,
    createdDate timestamp NOT NULL,
    
    PRIMARY KEY (id),
    INDEX bitsIDX (mostSignificantBits,leastSignificantBits)
);

CREATE TABLE IF NOT EXISTS DynamicApplicationProperties(
    id integer(25) NOT NULL AUTO_INCREMENT,
    name  varchar(255) NOT NULL,
    value text NOT NULL,
    description varchar(200),
    
    PRIMARY KEY (id),
    INDEX nameDAPIDX (name)
    
);

